package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.thekiddos.manager.gui.views.ItemPane;
import org.thekiddos.manager.models.Item;
import org.thekiddos.manager.models.Order;
import org.thekiddos.manager.models.OrderedItem;
import org.thekiddos.manager.models.Reservation;
import org.thekiddos.manager.repositories.Database;
import org.thekiddos.manager.transactions.AddReservationServiceTransaction;

import java.util.HashSet;
import java.util.Set;

public class OrderController extends Controller {
    // TODO i need to refresh from the database once it's implemented but for now the reference variable is alright
    public VBox root;
    public TableView<OrderedItem> orderTable;
    public TableColumn<OrderedItem, String> itemNameColumn;
    public TableColumn<OrderedItem, Integer> quantityColumn;
    public TableColumn<OrderedItem, Double> unitPriceColumn;
    public JFXButton addOrderItemButton;
    public JFXListView<ItemPane> itemsList;
    private Reservation reservation;

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
        AddReservationServiceTransaction serviceTransaction = new AddReservationServiceTransaction( reservation.getTableId() );
        for ( ItemPane itemPane : itemsList.getSelectionModel().getSelectedItems() )
            serviceTransaction.addItem( itemPane.getItemId() );
        serviceTransaction.execute();
        refresh();
    }

    private void refresh() {
        fillItems();
        orderTable.getItems().clear();
        Order order = reservation.getOrder();
        Set<Item> items = new HashSet<>( order.getItems().keySet() );
        for ( Item item : items ) {
            int quantity = order.getItems().get( item );
            orderTable.getItems().add( new OrderedItem( item.getName(), quantity, item.getPrice() ) );
        }
    }
}
