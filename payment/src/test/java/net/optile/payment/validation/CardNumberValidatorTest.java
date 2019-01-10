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

package net.optile.payment.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardNumberValidatorTest {

    @Test
    public void isValidLuhn() {
        // visa
        assertTrue(CardNumberValidator.isValidLuhn("4556260657599841"));
        // mastercard
        assertTrue(CardNumberValidator.isValidLuhn("5386813397330555"));
        // American Express
        assertTrue(CardNumberValidator.isValidLuhn("6011206880824187"));
        // discover
        assertTrue(CardNumberValidator.isValidLuhn("376848137807457"));
        // JCB
        assertTrue(CardNumberValidator.isValidLuhn("3540173304463363"));
        // Diners club - North America 
        assertTrue(CardNumberValidator.isValidLuhn("5545674672092247"));
        // Diners Club - Card Blanche
        assertTrue(CardNumberValidator.isValidLuhn("30121723749105"));
        // Diners Club - International 
        assertTrue(CardNumberValidator.isValidLuhn("36394455633086"));
        // Maestro
        assertTrue(CardNumberValidator.isValidLuhn("6762174698488713"));
        // Visa Elektron
        assertTrue(CardNumberValidator.isValidLuhn("4917582391037711"));
        // InstaPayment
        assertTrue(CardNumberValidator.isValidLuhn("6393363361928900"));

        // these should fail
        assertFalse(CardNumberValidator.isValidLuhn(null));
        assertFalse(CardNumberValidator.isValidLuhn(""));
        assertFalse(CardNumberValidator.isValidLuhn("1234567890123456"));
    }

}