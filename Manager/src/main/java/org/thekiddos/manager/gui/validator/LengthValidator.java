package org.thekiddos.manager.gui.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class LengthValidator extends ValidatorBase {
    private final int minLength;

    public LengthValidator( int minLength ) {
        this.minLength = minLength;
    }

    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl ) {
            this.evalTextInputField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();
        this.hasErrors.set(false);
        if ( text.length() < minLength )
            this.hasErrors.set( true );
    }
}
