package org.thekiddos.manager.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.thekiddos.manager.ManagerApplication;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.controllers.*;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.payroll.transactions.AddHourlyEmployeeTransaction;
import org.thekiddos.manager.payroll.transactions.AddSalariedEmployeeTransaction;
import org.thekiddos.manager.transactions.*;

// TODO some Controllers are not using the transactions make sure all of them do
// TODO a lot of the controllers share the same logic abstract it
public final class GUIApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray( new String[0] );

        this.applicationContext = new SpringApplicationBuilder()
                .sources( ManagerApplication.class )
                .run(args);
    }

    @Override
    public void start(Stage primaryStage) {
        fillDatabase();

        Stage invoiceStage = Util.createWindowContainer( InvoiceController.class, null, "Invoice Summary" ).getStage();
        invoiceStage.initModality( Modality.NONE );

        Stage payCheckStage = Util.createWindowContainer( PayCheckController.class, null, "Pay Check" ).getStage();
        payCheckStage.initModality( Modality.NONE );

        Stage orderStage = Util.createWindowContainer( OrderController.class, null, "Order Summary" ).getStage();
        orderStage.initModality( Modality.NONE );

        Stage addReservationGUIStage = Util.createWindowContainer( AddReservationGUIController.class, null, "Add Reservation" ).getStage();
        addReservationGUIStage.initModality( Modality.WINDOW_MODAL );

        Stage addItemGUIStage = Util.createWindowContainer( AddItemController.class, null, "Add Item" ).getStage();
        addItemGUIStage.initModality( Modality.NONE );

        Stage addEmployeeStage = Util.createWindowContainer( AddEmployeeController.class, null, "Add Employee" ).getStage();
        addEmployeeStage.initModality( Modality.NONE );

        Util.createWindowContainer( TableController.class, null, "Tables" );
        Util.createWindowContainer( CustomerController.class, null, "Customers" );
        Util.createWindowContainer( ItemController.class, null, "Items" );
        Util.createWindowContainer( EmployeeController.class, null, "Employees" );
        Util.createWindowContainer( PayrollController.class, null,"Payroll" );

        primaryStage = Util.createWindowContainer( GUIController.class, null, "Digital Restaurant Manager" ).getStage();
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

        AddItemsToReservationTransaction serviceTransaction = new AddItemsToReservationTransaction( 1L );
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

        new AddSalariedEmployeeTransaction( 1L, "Zahlt", 1000.0 ).execute();
        new AddHourlyEmployeeTransaction( 2L, "Django", 8.0 ).execute();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    public static void main( String[] args) {
        launch(args);
    }
}
