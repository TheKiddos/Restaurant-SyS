package org.thekiddos.manager.gui;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.transactions.AddCustomerTransaction;
import org.thekiddos.manager.transactions.AddItemTransaction;
import org.thekiddos.manager.transactions.AddTableTransaction;
import org.thekiddos.manager.transactions.ImmediateReservationTransaction;

public class GUIApplication extends Application {
    @Override
    public void init() throws Exception {
        fillDatabase();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage invoiceStage = Util.createStage( "templates/invoice.fxml", null, "Invoice Summary" );
        invoiceStage.initModality( Modality.NONE );

        Stage addReservationGUIStage = Util.createStage( "templates/add_reservation.fxml", null, "Add Reservation" );
        addReservationGUIStage.initModality( Modality.NONE );

        primaryStage = Util.createStage( "templates/GUI.fxml", null, "Digital Restaurant Manager" );
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
