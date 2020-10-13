/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.shop.summary;

import net.optile.payment.model.PresetAccount;

/**
 * The interface for the SummaryView.
 */
interface SummaryView {

    /**
     * Close this summary view
     */
    void close();

    /**
     * Show the payment confirmation to the user
     */
    void showPaymentConfirmation();

    /**
     * Show the list of payments using the android-sdk
     */
    void showPaymentList();

    /**
     * Abort the payment and show a default error to the user
     */
    void stopPaymentWithErrorMessage();

    /**
     * Show the loading animation
     *
     * @param val true when the loading animation should be shown, false otherwise
     */
    void showLoading(boolean val);

    /**
     * Show preset account
     *
     * @param account to be shown in the summary page
     */
    void showPaymentDetails(PresetAccount account);

    /**
     * Get the list url from the summary view
     *
     * @return the list url
     */
    String getListUrl();
}

