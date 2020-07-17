package org.thekiddos.manager.gui.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.repositories.Database;

public class EmailValidator extends ValidatorBase {
    public EmailValidator() {
        setMessage( "Please ensure the email is in the correct format and is unique" );
    }

    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl ) {
            this.evalTextInputField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();

        try {
            this.hasErrors.set(false);
            if (!text.isEmpty()) {
                if ( !Util.validateEmail( text ) || Database.getCustomerByEmail( text ) != null )
                    this.hasErrors.set( true );
            }
        } catch (Exception var4) {
            this.hasErrors.set(true);
        }

    }
}