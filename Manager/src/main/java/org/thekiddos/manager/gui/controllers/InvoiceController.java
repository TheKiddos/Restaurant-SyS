package org.thekiddos.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.thekiddos.manager.models.Invoice;

public class InvoiceController extends Controller {
    public VBox root;

    public void setInvoice( Invoice invoice ) {

    }

    @Override
    public Node getRoot() {
        return root;
    }
}
