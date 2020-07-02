package org.thekiddos.manager.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.transactions.*;

public class GUIApplication extends Application {
    @Override
    public void init() throws Exception {
        fillDatabase();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage invoiceStage = Util.createWindowContainer( "templates/invoice.fxml", null, "Invoice Summary" ).getStage();
        invoiceStage.initModality( Modality.NONE );

        Stage orderStage = Util.createWindowContainer( "templates/order.fxml", null, "Order Summary" ).getStage();
        orderStage.initModality( Modality.NONE );

        Stage addReservationGUIStage = Util.createWindowContainer( "templates/add_reservation.fxml", null, "Add Reservation" ).getStage();
        addReservationGUIStage.initModality( Modality.NONE );

        Stage addItemGUIStage = Util.createWindowContainer( "templates/add_item.fxml", null, "Add Item" ).getStage();
        addItemGUIStage.initModality( Modality.NONE );

        primaryStage = Util.createWindowContainer( "templates/GUI.fxml", null, "Digital Restaurant Manager" ).getStage();
        primaryStage.setOnCloseRequest( e -> Platform.exit() );
        primaryStage.show();
    }

    /**
     * This method is for testing purposes only
     */
    void fillDatabase() {
        new AddTableTransaction( 1L ).execute();
        new AddTableTransaction( 2L ).execute();
        new AddTableTransaction( 3L ).execute();
        new AddCustomerTransaction( 1L, "Z", "X" ).execute();
        new AddCustomerTransaction( 2L, "A", "B" ).execute();
        new ImmediateReservationTransaction( 1L, 1L ).execute();

        String imagePath = "https://cms.splendidtable.org/sites/default/files/styles/w2000/public/french-fries.jpg?itok=FS-YwUYH";
        AddItemTransaction addItem = new AddItemTransaction( 1L, "French Fries", 10.0 );
        addItem.withDescription( "Well it's French Fries what else to say!" )
                .withType( Type.FOOD )
                .withType( Type.STARTER )
                .withType( Type.HOT )
                .withType( Type.SNACK )
                .withCalories( 10000.0 )
                .withFat( 51.0 )
                .withProtein( 0.4 )
                .withCarbohydrates( 0.2 )
                .withImage( imagePath );
        addItem.execute();
        new AddItemTransaction( 2L, "Syrian Fries", 15.0 ).execute();
        new AddItemTransaction( 3L, "Hasan's Fries", 25.0 ).execute();

        AddReservationServiceTransaction serviceTransaction = new AddReservationServiceTransaction( 1L );
        serviceTransaction.addItem( 1L );
        serviceTransaction.addItem( 1L );
        serviceTransaction.addItem( 1L );
        serviceTransaction.addItem( 2L );
        serviceTransaction.addItem( 3L );
        serviceTransaction.addItem( 3L );

        new AddItemTransaction( 4L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 4L );
        new AddItemTransaction( 5L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 5L );
        new AddItemTransaction( 6L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 6L );
        new AddItemTransaction( 7L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 7L );
        new AddItemTransaction( 8L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 8L );
        new AddItemTransaction( 9L, "Hasan's Fries", 25.0 ).execute();
        serviceTransaction.addItem( 9L );
        serviceTransaction.execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
