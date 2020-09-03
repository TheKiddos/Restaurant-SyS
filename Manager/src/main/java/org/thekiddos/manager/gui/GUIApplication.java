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

// TODO create refreshable interface or something
// TODO a lot of the controllers share the same logic abstract it

// TODO Add test for gui with testFX
// TODO Chat between Manager and Waiter
// TODO Active Reservation that span two days #81
// TODO Fix timezone issues with date and time in the website #74
public final class GUIApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray( new String[0] );

        this.applicationContext = new SpringApplicationBuilder()
                .sources( ManagerApplication.class )
                .run( args );
    }

    @Override
    public void start(Stage primaryStage) {
        Util.initialize();

        Stage invoiceStage = Util.createWindowContainer( InvoiceController.class, null, "Invoice Summary" ).getStage();
        invoiceStage.initModality( Modality.NONE );

        Stage payCheckStage = Util.createWindowContainer( PayCheckController.class, null, "Pay Check" ).getStage();
        payCheckStage.initModality( Modality.NONE );

        Stage orderStage = Util.createWindowContainer( OrderController.class, null, "Order Summary" ).getStage();
        orderStage.initModality( Modality.NONE );

        Stage addReservationGUIStage = Util.createWindowContainer( AddReservationGUIController.class, null, "Add Reservation" ).getStage();
        addReservationGUIStage.initModality( Modality.NONE );

        Stage addItemGUIStage = Util.createWindowContainer( AddItemController.class, null, "Add Item" ).getStage();
        addItemGUIStage.initModality( Modality.NONE );

        Stage addEmployeeStage = Util.createWindowContainer( AddEmployeeController.class, null, "Add Employee" ).getStage();
        addEmployeeStage.initModality( Modality.NONE );

        Util.createWindowContainer( TableController.class, null, "Tables" );
        Util.createWindowContainer( CustomerController.class, null, "Customers" );
        Util.createWindowContainer( ItemController.class, null, "Items" );
        Util.createWindowContainer( EmployeeController.class, null, "Employees" );
        Util.createWindowContainer( PayrollController.class, null,"Payroll" );
        Util.createWindowContainer( DeliveryController.class, null,"Delivery" );

        primaryStage = Util.createWindowContainer( GUIController.class, null, "Digital Restaurant Manager" ).getStage();
        primaryStage.setOnCloseRequest( e -> Platform.exit() );
        primaryStage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
