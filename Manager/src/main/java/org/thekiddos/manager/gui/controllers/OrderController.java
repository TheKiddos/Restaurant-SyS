package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.views.ItemPane;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Order;
import org.thekiddos.manager.models.OrderedItem;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.services.ItemService;
import org.thekiddos.manager.transactions.AddItemsToReservationTransaction;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@FxmlView("order.fxml")
public class OrderController extends Controller {
    public VBox root;
    public TableView<OrderedItem> orderTable;
    public TableColumn<OrderedItem, String> itemNameColumn;
    public TableColumn<OrderedItem, Integer> quantityColumn;
    public TableColumn<OrderedItem, Double> unitPriceColumn;
    public JFXButton addOrderItemButton;
    public JFXListView<ItemPane> itemsList;
    private Reservation reservation;
    private final ItemService itemService;

    public OrderController( ItemService itemService ) {
        this.itemService = itemService;
    }

    public void initialize() {
        addOrderItemButton.disableProperty().bind( itemsList.getSelectionModel().selectedItemProperty().isNull() );
        itemNameColumn.setCellValueFactory( new PropertyValueFactory<>( "itemName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
        unitPriceColumn.setCellValueFactory( new PropertyValueFactory<>( "unitPrice" ) );
        fillItems();
        itemsList.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
    }

    private void fillItems() {
        itemsList.getItems().clear();
        Set<Item> items = Database.getItems();
        for ( Item item : items )
            itemsList.getItems().add( new ItemPane( item.getName() ,item.getId() ) );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void setReservation( Reservation reservation ) {
        this.reservation = reservation;
        refresh();
    }

    public void addOrderItem( ActionEvent actionEvent ) {
        AddItemsToReservationTransaction serviceTransaction = new AddItemsToReservationTransaction( reservation.getReservedTableId() );
        for ( ItemPane itemPane : itemsList.getSelectionModel().getSelectedItems() )
            serviceTransaction.addItem( itemPane.getItemId() );
        serviceTransaction.execute();
        refresh();
    }

    @Override
    public void refresh() {
        fillItems();
        fillOrderTable();
    }

    private void fillOrderTable() {
        orderTable.getItems().clear();
        if ( reservation == null )
            return;
        reservation = Database.getReservationById( reservation.getCustomerId(), reservation.getDate() );
        if ( reservation == null ) // Checkout will delete the reservation. so checking is required
            return;
        Order order = reservation.getOrder();
        Set<Item> items = new HashSet<>( order.getItemsQuantities().keySet() );
        for ( Item item : items ) {
            int quantity = order.getItemsQuantities().get( item );
            orderTable.getItems().add( new OrderedItem( item.getName(), quantity, item.getPrice() ) );
        }
    }

    public void showAddItemsToOrderDialog( Long tableId, List<Item> orderedItems ) {
        Alert alert = Util.createAlert("Add Order?", getOrderDetails( tableId, orderedItems ), getScene().getWindow(), ButtonType.OK, ButtonType.CANCEL );
        Optional<ButtonType> clickedButton = alert.showAndWait();
        if ( clickedButton.isPresent() && clickedButton.get().equals( ButtonType.OK ) ) {
            itemService.addItemsToOrder( tableId, orderedItems );
            refresh();
        }
    }

    private String getOrderDetails( Long tableId, List<Item> orderedItems ) {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append( "For Table: " ).append( tableId ).append( "\n\n" );
        for ( Item item : orderedItems )
            orderDetails.append( item.getName() ).append( " " ).append( item.getPrice() ).append( "\n" );
        return orderDetails.toString();
    }
}
