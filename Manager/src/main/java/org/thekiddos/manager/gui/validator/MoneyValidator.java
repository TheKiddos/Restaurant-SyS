package org.thekiddos.manager.gui.validator;

import com.jfoenix.validation.DoubleValidator;
import javafx.scene.control.TextInputControl;

public class MoneyValidator extends DoubleValidator {
    @Override
    protected void eval() {
        super.eval();

        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();

        if ( text.isEmpty() )
            return;

        if ( Double.parseDouble( text ) < 0 ) {
            this.hasErrors.set( true );
            message.setValue( "Money must be Non-Negative" );
        }
    }
}
