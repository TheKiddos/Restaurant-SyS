package org.thekiddos.manager.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Customer;
import org.thekiddos.manager.models.TelegramUser;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.EmailServiceImpl;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

// TODO each telegram user must have his (id, lastCommand, email, isVerified, isRegistered ) in the database
@Component
public class KDRSBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(KDRSBot.class);
    private TelegramUser currentTelegramUser;
    private SendMessage response;

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
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String messageText = message.getText();
            Long chatId = message.getChatId();

            response = new SendMessage();
            response.setChatId( chatId );

            logger.info( String.valueOf( message.getFrom().getId() ) );
            currentTelegramUser = Database.getTelegramUser( message.getFrom().getId() );

            processRequestMessage( messageText );

            try {
                execute(response);
                logger.info( "Sent message \"{}\" to {}", messageText, chatId );
            } catch (TelegramApiException e) {
                logger.error( "Failed to send message \"{}\" to {} due to error: {}", messageText, chatId, e.getMessage() );
            }
        }
    }

    private void processRequestMessage( String messageText ) {
        // Commands must come before checking for last commands.
        if ( messageText.equals( Commands.EMAIL ) ) {
            response.setText( "Please enter the same email you used to create account at our website." );
            currentTelegramUser.setLastCommand( Commands.EMAIL );
            Database.updateTelegramUser( currentTelegramUser );
        }
        else if ( messageText.equals( Commands.VERIFY ) ) {
            String email = currentTelegramUser.getEmail();
            if ( email == null || email.length() == 0 ) {
                response.setText( "Please enter your email first using the /email command" );
                currentTelegramUser.setLastCommand( Commands.NOTHING );
                Database.updateTelegramUser( currentTelegramUser );
                return;
            }

            if ( currentTelegramUser.isVerified() ) {
                response.setText( "Your account is already verified." );
                currentTelegramUser.setLastCommand( Commands.NOTHING );
                Database.updateTelegramUser( currentTelegramUser );
                return;
            }

            int code = ThreadLocalRandom.current().nextInt( 12345, 99999 );
            currentTelegramUser.setVerificationCode( code );
            currentTelegramUser.setLastCommand( Commands.VERIFY );
            Database.updateTelegramUser( currentTelegramUser );
            emailService.sendEmail( email, "Verify your email", "Please enter the following code in telegram\n" + code );

            response.setText( "Please enter the code sent to your email" );
        }
        else if ( currentTelegramUser.getLastCommand().equals( Commands.EMAIL ) ) {
            String email = messageText;
            if ( !Util.validateEmail( email ) ) {
                response.setText( "Enter your email in the correct format." );
                return;
            }

            Customer customer = Database.getCustomerByEmail( email );
            if ( customer == null ) {
                response.setText( "Please register first using our website." );
                return;
            }

            response.setText( "Your email was successfully saved. please verify it with the /verify command" );
            currentTelegramUser.setEmail( email );
            currentTelegramUser.setVerified( false );
            currentTelegramUser.setLastCommand( Commands.NOTHING );
            Database.updateTelegramUser( currentTelegramUser );
        }
        else if ( currentTelegramUser.getLastCommand().equals( Commands.VERIFY ) ) {
            try {
                int code = Integer.parseInt( messageText );
                if ( code != currentTelegramUser.getVerificationCode() ) {
                    response.setText( "Code doesn't match, try again" );
                    currentTelegramUser.setLastCommand( Commands.NOTHING );
                    currentTelegramUser.setVerificationCode( null );
                    Database.updateTelegramUser( currentTelegramUser );
                    return;
                }

                currentTelegramUser.setVerified( true );
                currentTelegramUser.setLastCommand( Commands.NOTHING );
                Database.updateTelegramUser( currentTelegramUser );
                response.setText( "Your account was verified!.\n your account is now linked." );
            }
            catch ( NumberFormatException e ) {
                response.setText( "Please enter the correct verification code" );
            }
        }
        else {
            response.setText( "Go to HELL!" );
        }
    }

    @PostConstruct
    public void start() {
        logger.info("Starting Bot\n");
    }
}
