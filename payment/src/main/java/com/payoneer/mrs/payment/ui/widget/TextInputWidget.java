/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.widget;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM;

import com.payoneer.mrs.payment.R;
import com.payoneer.mrs.payment.localization.Localization;
import com.payoneer.mrs.payment.model.InputElement;
import com.payoneer.mrs.payment.ui.widget.input.EditTextInputModeFactory;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

/**
 * Widget for handling text input
 */
public final class TextInputWidget extends InputLayoutWidget {

    /**
     * Construct a new TextInputWidget
     *
     * @param name name identifying this widget
     * @param rootView the root view of this input
     */
    public TextInputWidget(String name, View rootView) {
        super(name, rootView);
        textInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextInputChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Set the InputElement into this TextInputWidget
     *
     * @param code of the network
     * @param element to be set in this widget
     */
    public void onBind(String code, InputElement element) {
        int maxLength = presenter.getMaxLength(code, name);
        setTextInputMode(EditTextInputModeFactory.createMode(maxLength, element));
        setValidation();
        setLabel(Localization.translateAccountLabel(code, name));
        setHelperText(Localization.translateAccountPlaceholder(code, name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void handleOnFocusChange(boolean hasFocus) {
        super.handleOnFocusChange(hasFocus);
        setClearIcon(getValue(), hasFocus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void handleOnEndIconClicked() {
        textInput.getText().clear();
    }

    private void onTextInputChanged() {
        String value = getValue();
        presenter.onTextInputChanged(name, value);
        setClearIcon(value, textInput.hasFocus());
    }

    private void setClearIcon(String value, boolean hasFocus) {
        if (!TextUtils.isEmpty(value) && hasFocus) {
            setEndIcon(END_ICON_CUSTOM, R.drawable.ic_cancel);
        } else {
            removeEndIcon();
        }
    }
}
