/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.payment.ui.list;

import com.payoneer.mrs.payment.ui.model.PaymentCard;

/**
 * Class representing an item in the PaymentList
 */
abstract class ListItem {

    final int viewType;

    ListItem(int viewType) {
        this.viewType = viewType;
    }

    boolean hasPaymentCard() {
        return false;
    }

    PaymentCard getPaymentCard() {
        return null;
    }
}
