package org.thekiddos.manager.bot;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.TelegramUser;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.EmailServiceImpl;
import org.thekiddos.manager.transactions.ImmediateDeliveryTransaction;
import org.thekiddos.manager.transactions.ScheduledReservationTransaction;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class KDRSBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger( KDRSBot.class );
    private TelegramUser currentTelegramUser;
    private SendMessage response;
    private SendPhoto responseWithImage;

    private EmailServiceImpl emailService;

    @Autowired
    public void setEmailService( EmailServiceImpl emailService ) {
        this.emailService = emailService;
    }

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived( Update update ) {
        if ( !update.hasMessage() ) {
            return;
        }

        Message message = update.getMessage();
        String messageText = message.getText();
        Long chatId = message.getChatId();

        response = new SendMessage();
        response.setChatId( chatId );

        logger.info( String.valueOf( message.getFrom().getId() ) );
        currentTelegramUser = Database.getTelegramUser( message.getFrom().getId() );

        processRequestMessage( messageText );

        try {
            if ( response == null && responseWithImage != null )
                execute( responseWithImage ) ;
            else if ( response != null )
                execute( response );
            logger.info( "Sent message \"{}\" to {}", messageText, chatId );
        } catch (TelegramApiException e) {
            logger.error( "Failed to send message \"{}\" to {} due to error: {}", messageText, chatId, e.getMessage() );
        }
    }

    private void processRequestMessage( String messageText ) {
        // Commands must come before checking for last commands.
        if ( messageText.equals( Commands.EMAIL ) ) {
            if ( currentTelegramUser.isVerified() ) {
                sendInstructions( "Can't Change your email after it was verified" );
            }
            else {
                sendInstructions( "Please enter the same email you used to create account at our website.", Commands.EMAIL );
            }
        }
        else if ( messageText.equals( Commands.VERIFY ) ) {
            processVerificationRequest();
        }
        else if ( messageText.startsWith( Commands.ITEMS ) ) {
            sendItemsDetails( messageText );
        }
        else if ( messageText.equals( Commands.RECOMMEND ) ) {
            recommendItemsForUser();
        }
        else if ( messageText.startsWith( Commands.FREE_TABLES ) ) {
            sendFreeTablesOn( messageText.substring( Commands.FREE_TABLES.length() + 1 ) ); // + 1 for the space (/freetables 2020-02-10)
        }
        else if ( messageText.startsWith( Commands.RESERVE ) ) {
            reserveTable( messageText );
        }
        else if ( messageText.startsWith( Commands.ORDER ) ) {
            createDelivery( messageText );
        }
        else if ( currentTelegramUser.getLastCommand().equals( Commands.EMAIL ) ) {
            setEmail( messageText );
        }
        else if ( currentTelegramUser.getLastCommand().equals( Commands.VERIFY ) ) {
            try{
                int code = Integer.parseInt( messageText );
                verifyCode( code );
            }
            catch ( NumberFormatException e ) {
                sendInstructions( "That's not a number dumb ass" );
            }
        }
        else {
            sendInstructions( "Go to HELL!" );
        }
    }

    private void sendInstructions( String instructions, String lastCommand ) {
        sendInstructions( instructions );
        currentTelegramUser.setLastCommand( lastCommand );
        Database.updateTelegramUser( currentTelegramUser );
    }

    private void sendInstructions( String instructions ) {
        response.setText( instructions );
    }

    private void processVerificationRequest() {
        String email = currentTelegramUser.getEmail();

        if ( !emailExists( email ) ) {
            sendInstructions( "Please enter your email first using the /email command", Commands.NOTHING );
            return;
        }

        if ( currentTelegramUser.isVerified() ) {
            sendInstructions( "Your account is already verified.", Commands.NOTHING );
            return;
        }

        int code = generateVerificationCode();
        currentTelegramUser.setVerificationCode( code ); // the send instruction will update the user in the database
        emailService.sendEmail( email, "Verify your email", "Please enter the following code in telegram\n" + code );

        response.setText( "Please enter the code sent to your email" );
        sendInstructions( "Please enter the code sent to your email", Commands.VERIFY );
    }

    private boolean emailExists( String email ) {
        return email != null && email.length() > 0;
    }

    private int generateVerificationCode() {
        return ThreadLocalRandom.current().nextInt( 12345, 99999 );
    }

    private void sendItemsDetails( String messageText ) {
        Long itemId = extractItemId( messageText );
        if ( itemId.equals( Util.INVALID_ID ) ) {
            sendAllItems();
        }
        else {
            sendItemDetails( itemId );
        }
        currentTelegramUser.setLastCommand( Commands.ITEMS );
        Database.updateTelegramUser( currentTelegramUser );
    }

    private Long extractItemId( String messageText ) {
        try {
            return Long.parseLong( messageText.split( "\\s" )[ 1 ] );
        }
        catch ( Exception e ) {
            return Util.INVALID_ID;
        }
    }

    private void sendAllItems() {
        Set<Item> items = Database.getItems();
        response.setText( getItemsDetails( items ) );
    }

    private String getItemsDetails( Set<Item> items ) {
        StringBuilder itemDetails = new StringBuilder();
        items.forEach( item -> itemDetails.append( item.getId() ).append( "." ).append( item.getName() )
                .append( ", Price: " ).append( item.getPrice() ).append( "\n\n" ) );
        return itemDetails.toString();
    }

    @SneakyThrows
    private void sendItemDetails( Long itemId ) {
        Item item = Database.getItemById( itemId );

        responseWithImage = new SendPhoto();
        responseWithImage.setChatId( response.getChatId() );
        File image =  new File( new URI( item.getImagePath() ).toURL().getFile() );
        responseWithImage.setPhoto( image );
        responseWithImage.setCaption( item.getName() + "\nPrice: " + item.getPrice() + "\n" + item.getDescription() );

        response = null;
    }

    private void recommendItemsForUser() {
        if ( !currentTelegramUser.isVerified() ) {
            sendInstructions( "Please verify your email first." );
            return;
        }
        Set<Item> recommendedItems = Database.getRecommendationsFor( currentTelegramUser.getEmail() );
        response.setText( getItemsDetails( recommendedItems ) );

        currentTelegramUser.setLastCommand( Commands.RECOMMEND );
        Database.updateTelegramUser( currentTelegramUser );
    }

    private void sendFreeTablesOn( String dateString ) {
        try {
            LocalDate date = LocalDate.parse( dateString );

            Set<Long> freeTables = Database.getFreeTablesOn( date );
            response.setText( freeTables.toString() );

            currentTelegramUser.setLastCommand( Commands.FREE_TABLES );
            Database.updateTelegramUser( currentTelegramUser );
        }
        catch ( DateTimeParseException e ) {
            sendInstructions( "Please enter the date in the correct format YYYY-MM-DD eg: 2020-03-20", Commands.FREE_TABLES );
        }

    }

    private void reserveTable( String messageText ) {
        if ( !currentTelegramUser.isVerified() ) {
            sendInstructions( "Please verify your email first." );
            return;
        }
        try {
            String[] args = getArgumentsBySpace( messageText, 4 );

            Long tableId = Long.parseLong( args[ 1 ] );
            Long customerId = Database.getCustomerByEmail( currentTelegramUser.getEmail() ).getId();
            LocalDate reservationDate = LocalDate.parse( args[ 2 ] );
            LocalTime reservationTime = LocalTime.parse( args[ 3 ] );

            new ScheduledReservationTransaction( tableId, customerId, reservationDate, reservationTime ).execute();

            sendInstructions( "Your reservation was completed successfully!", Commands.RESERVE );
        }
        catch ( Exception e ) {
            sendInstructions( "Wrong or missing information, please enter the message in the current format\n" +
                    "TABLE_ID YYYY-MM-DD HH:MM:SS\n" +
                    "for example:\n" +
                    "/reserve 1 2020-02-03 18:00:00\n" +
                    "Also make sure that the table exists, free on the selected date, and the date/time are not in the past." );
        }
    }

    private String[] getArgumentsBySpace( String messageText, int lowerBoundOfArguments ) {
        return getArguments( messageText, lowerBoundOfArguments, "\\s" );
    }

    private String[] getArgumentsByLine( String messageText, int lowerBoundOfArguments ) {
        return getArguments( messageText, lowerBoundOfArguments, "\n" );
    }

    private String[] getArguments( String messageText, int lowerBoundOfArguments, String splitBy ) {
        String[] args = messageText.split( splitBy );
        if ( args.length < lowerBoundOfArguments )
            throw new IllegalArgumentException( "The message should contains at least " + lowerBoundOfArguments + " arguments" );
        return args;
    }


    private void createDelivery( String messageText ) {
        if ( !currentTelegramUser.isVerified() ) {
            sendInstructions( "Please verify your email first." );
            return;
        }
        try {
            String[] args = getArgumentsByLine( messageText, 3 );

            Long customerId = Database.getCustomerByEmail( currentTelegramUser.getEmail() ).getId();
            String address = args[ 1 ];
            List<Long> items = extractItemIds( args[ 2 ] );

            new ImmediateDeliveryTransaction( customerId, address, 0.0, items ).execute();

            sendInstructions( "Delivery Added Thanks for trusting us", Commands.ORDER );
        }
        catch ( Exception e ) {
            sendInstructions( "Please enter the message in the correct format first line /order, second line your address, third line item ids separated by space", Commands.ORDER );
        }
    }

    private List<Long> extractItemIds( String itemIdsString ) {
        List<Long> itemIds = new ArrayList<>();

        for ( String itemIdString : getArgumentsBySpace( itemIdsString, 1 ) )
            itemIds.add( Long.parseLong( itemIdString ) );

        return itemIds;
    }

    private void setEmail( String email ) {
        if ( !Util.validateEmail( email ) ) {
            sendInstructions( "Enter your email in the correct format." );
            return;
        }

        Customer customer = Database.getCustomerByEmail( email );
        if ( customer == null ) {
            sendInstructions( "Please register first using our website." );
            return;
        }

        currentTelegramUser.setEmail( email );
        currentTelegramUser.setVerified( false );
        sendInstructions( "Your email was successfully saved. please verify it with the /verify command", Commands.NOTHING ); // updates the user
    }

    private void verifyCode( int code ) {
        if ( code != currentTelegramUser.getVerificationCode() ) {
            clearOldVerificationCode();
            sendInstructions( "Code doesn't match, try again", Commands.NOTHING );
            return;
        }

        currentTelegramUser.setVerified( true );
        sendInstructions( "Your account was verified!.\n your account is now linked.", Commands.NOTHING );
    }

    private void clearOldVerificationCode() {
        currentTelegramUser.setVerificationCode( null );
    }

    @PostConstruct
    public void start() {
        logger.info("Starting Bot\n");
    }
}
