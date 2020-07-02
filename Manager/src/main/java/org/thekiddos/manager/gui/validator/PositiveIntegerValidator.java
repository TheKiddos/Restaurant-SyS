package org.thekiddos.manager.gui.validator;

import com.jfoenix.validation.IntegerValidator;
import javafx.scene.control.TextInputControl;

public class PositiveIntegerValidator extends IntegerValidator {

    @Override
    protected void eval() {
        super.eval();

        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();

        if ( text.isEmpty() )
            return;

        if ( Integer.parseInt( text ) < 1 ) {
            this.hasErrors.set( true );
            message.setValue( "Integer must be positive" );
        }
    }
}
