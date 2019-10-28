/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.ui.widget.mode;

import com.google.android.material.textfield.TextInputEditText;

import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import net.optile.payment.ui.widget.GroupingTextWatcher;

/**
 * InputMode for account numbers
 */
public final class AccountNumberInputMode extends TextInputMode {

    private final static String ACCOUNTNUMBER_DIGITS = "0123456789 ";
    
    /** 
     * Construct an AccountNumberInputMode 
     *
     * @param maxLength maximum length of the input field
     * @param groupSize size of the grouped digits
     */
    public AccountNumberInputMode(int maxLength, int groupSize) {
        super(maxLength, groupSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String normalize(String value) {
        return value != null ? value.replaceAll("\\s", "") : null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(TextInputEditText editText) {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(getMaxLengthForGrouping());
        editText.setFilters(filters);
        editText.setKeyListener(DigitsKeyListener.getInstance(ACCOUNTNUMBER_DIGITS));

        if (groupSize > 0) {
            editText.addTextChangedListener(new GroupingTextWatcher(groupSize, editText));
        }
    }
}
