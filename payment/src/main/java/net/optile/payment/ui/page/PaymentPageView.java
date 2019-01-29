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

package net.optile.payment.ui.page;

import net.optile.payment.ui.PaymentResult;
import net.optile.payment.ui.dialog.ThemedDialogFragment;
import net.optile.payment.ui.model.PaymentSession;

/**
 * The PaymentPageView interface is the View part of the MVP, this is implemented by the PaymentPageActivity
 */
interface PaymentPageView {

    /**
     * Is the view currently active
     *
     * @return true when active, false otherwise
     */
    boolean isActive();

    /**
     * Get the String representation given the string resource id
     *
     * @return the string resource
     */
    String getStringRes(int resId);

    /**
     * Clear the list and clear the center message
     */
    void clear();

    /**
     * Show or hide the progress animation, progress animations are shown when lists are loaded or operation requests are performed.
     *
     * @param show if true show the progress animation, hide otherwise
     * @param style the style of progress animation to be used
     */
    void showProgress(boolean show, int style);

    /**
     * Stop loading and show the PaymentSession
     *
     * @param session the payment session to be shown to the user
     */
    void showPaymentSession(PaymentSession session);

    /**
     * Show a snackbar message to the user
     *
     * @param message The message to be shown
     */
    void showSnackbar(String message);

    /**
     * Close the payment page
     */
    void closePage();

    /**
     * Show the message dialog as a modal dialog
     *
     * @param dialog message dialog to be shown
     */
    void showDialog(ThemedDialogFragment dialog);

    /**
     * Set the current activity payment result, this is either PaymentUI.RESULT_CODE_OK,
     * PaymentUI.RESULT_CODE_CANCELED, PaymentUI.RESULT_CODE_ERROR
     *
     * @param resultCode the current resultCode
     * @param result containing the Payment result state
     */
    void setPaymentResult(int resultCode, PaymentResult result);
}
