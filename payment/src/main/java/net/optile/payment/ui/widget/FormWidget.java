/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.widget;

import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import net.optile.payment.R;
import net.optile.payment.core.PaymentException;
import net.optile.payment.form.Charge;
import net.optile.payment.validation.ValidationResult;

/**
 * The base InputWidget
 */
public abstract class FormWidget {

    public final static int VALIDATION_UNKNOWN = 0x00;
    public final static int VALIDATION_ERROR = 0x01;
    public final static int VALIDATION_OK = 0x02;

    final View rootView;

    final String name;

    final ImageView icon;

    WidgetPresenter presenter;

    int state;

    String error;

    FormWidget(String name, View rootView) {
        this.name = name;
        this.rootView = rootView;
        this.icon = rootView.findViewById(R.id.image_icon);
    }

    public void setPresenter(WidgetPresenter presenter) {
        this.presenter = presenter;
    }

    public View getRootView() {
        return rootView;
    }

    public String getName() {
        return name;
    }

    public String getString(int resId) {
        return rootView.getContext().getString(resId);
    }

    public void setIconResource(int resId) {

        if (icon != null) {
            icon.setImageResource(resId);
            setIconColor(this.state);
        }
    }

    public boolean isValid() {
        return this.state == VALIDATION_OK;
    }

    public void setVisible(boolean visible) {
        rootView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public boolean setLastImeOptionsWidget() {
        return false;
    }

    public void putValue(Charge charge) throws PaymentException {
    }

    public boolean validate() {
        setState(VALIDATION_OK);
        return true;
    }

    void setState(int state) {
        this.state = state;
        setIconColor(state);
    }

    private void setIconColor(int state) {

        if (icon == null) {
            return;
        }
        int colorResId = R.color.validation_ok;
        switch (state) {
            case VALIDATION_OK:
                colorResId = R.color.validation_ok;
                break;
            case VALIDATION_ERROR:
                colorResId = R.color.validation_error;
                break;
            default:
                colorResId = R.color.validation_unknown;
        }
        icon.setColorFilter(ContextCompat.getColor(rootView.getContext(), colorResId));
    }

    /**
     * Each Widget may have a presenter set that can be used to validate the Widget input values.
     */
    public interface WidgetPresenter {

        /**
         * This method will be called when i.e. the Pay Button has been clicked
         */
        void onActionClicked();

        /**
         * Request from this widget that the keyboard should be hidden
         */
        void hideKeyboard();

        /**
         * Request from this widget that the keyboard should be shown
         */
        void showKeyboard();
        
        /**
         * Request to show this DialogFragment to the user
         *
         * @param dialog to be shown to the user
         * @param tag to identify the DialogFragment
         */
        void showDialogFragment(DialogFragment dialog, String tag);

        /**
         * Widgets call this method to validate their input values. The first value is mandatory, the second is optional.
         * I.e. A Date widget may use both values to validate the month and year values at the same time.
         *
         * @param type type of the value to be validated
         * @param value1 mandatory first value to validate
         * @param value2 optional second value to validate
         * @return ValidationResult holding the result of the validation
         */
        ValidationResult validate(String type, String value1, String value2);
    }
}
