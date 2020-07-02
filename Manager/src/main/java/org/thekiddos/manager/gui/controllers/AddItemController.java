package org.thekiddos.manager.gui.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.gui.validator.ItemIdValidator;
import org.thekiddos.manager.models.Type;
import org.thekiddos.manager.transactions.AddItemTransaction;

import java.io.File;
import java.net.MalformedURLException;

public class AddItemController extends Controller {
    public JFXTextField itemIdField;
    public JFXTextField itemNameField;
    public JFXTextField itemPriceField;
    public JFXTextField itemCaloriesField;
    public JFXTextField itemFatField;
    public JFXTextField itemProteinField;
    public JFXTextField itemCarbohydratesField;
    public ImageView imagePreview;
    public JFXButton imageBrowseButton;
    public JFXButton addItemButton;
    public JFXTextArea descriptionArea;
    public JFXMasonryPane typePane;
    public GridPane root;

    private FileChooser fileChooser = new FileChooser();

    public void initialize() {
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "Images", "*.png", "*jpg", "*.jpeg", "*.gif", "*.bmp" ) );
        fileChooser.setTitle( "Select Item Image" );
        fillTypeBoxes();
        setupValidators();
    }

    private void setupValidators() {
        itemIdField.setValidators( new ItemIdValidator(), new RequiredFieldValidator() );
        itemNameField.setValidators( new RequiredFieldValidator() );
        itemPriceField.setValidators( new DoubleValidator(), new RequiredFieldValidator() );
        itemCaloriesField.setValidators( new DoubleValidator() );
        itemFatField.setValidators( new DoubleValidator() );
        itemProteinField.setValidators( new DoubleValidator() );
        itemCarbohydratesField.setValidators( new DoubleValidator() );
    }

    private boolean validate() {
        return itemPriceField.validate() && itemNameField.validate() &&
                itemIdField.validate();
    }

    private void fillTypeBoxes() {
        typePane.getChildren().clear();
        for ( Type type : Type.values() ) {
            JFXCheckBox itemBox = new JFXCheckBox( type.toString() );
            typePane.getChildren().add( itemBox );
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void browseImage( ActionEvent actionEvent ) throws MalformedURLException {
        File selectedImage = fileChooser.showOpenDialog( getScene().getWindow() );
        if ( selectedImage != null ) {
            imagePreview.setImage( new Image( selectedImage.toURI().toURL().toExternalForm() ) );
            fileChooser.setInitialDirectory( selectedImage.getParentFile() );
        }
    }

    public void addItem( ActionEvent actionEvent ) {
        if ( !validate() )
            return;

        Long id = Long.parseLong( itemIdField.getText() );
        String name = itemNameField.getText();
        double price = Double.parseDouble( itemPriceField.getText() );

        AddItemTransaction addItemTransaction = new AddItemTransaction( id, name, price );

        if ( itemCaloriesField.validate() )
            addItemTransaction.withCalories( Double.parseDouble( itemCaloriesField.getText() ) );

        if ( itemFatField.validate() )
            addItemTransaction.withFat( Double.parseDouble( itemFatField.getText() ) );

        if ( itemProteinField.validate() )
            addItemTransaction.withProtein( Double.parseDouble( itemProteinField.getText() ) );

        if ( itemCarbohydratesField.validate() )
            addItemTransaction.withCarbohydrates( Double.parseDouble( itemCarbohydratesField.getText() ) );

        addItemTransaction.withDescription( descriptionArea.getText() );

        Image image = imagePreview.getImage();
        if ( image != null ) {
            addItemTransaction.withImage( image.getUrl() );
        }

        for ( Node node : typePane.getChildren() ) {
            JFXCheckBox type = (JFXCheckBox) node;
            if ( type.isSelected() )
                addItemTransaction.withType( Type.valueOf( type.getText() ) );
        }

        addItemTransaction.execute();

        getScene().getWindow().getOnCloseRequest().handle( null );
        Util.getWindowContainer( "Add Item" ).getStage().close();
    }
}
