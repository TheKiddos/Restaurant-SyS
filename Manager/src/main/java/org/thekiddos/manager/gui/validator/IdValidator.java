package org.thekiddos.manager.gui.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty("icon")
public abstract class IdValidator extends ValidatorBase {
    public IdValidator() {
        this.setMessage("Value must be a unique integer");
    }

    public IdValidator( String message) {
        super(message);
    }

    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalTextInputField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();

        try {
            this.hasErrors.set(false);
            if (!text.isEmpty()) {
                long value = Long.parseLong(text);
                if ( value < 1L || !isUnique( value ) )
                    this.hasErrors.set(true);
            }
        } catch (Exception var4) {
            this.hasErrors.set(true);
        }

    }

    abstract boolean isUnique( Long id );
}
