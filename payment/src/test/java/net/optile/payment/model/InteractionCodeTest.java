/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.payment.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The InteractionCode test
 */
public class InteractionCodeTest {

    @Test
    public void isInteractionCode_invalidValue_false() {
        assertFalse(InteractionCode.isValid("foo"));
    }

    @Test
    public void isInteractionCode_validValue_true() {
        assertTrue(InteractionCode.isValid(InteractionCode.PROCEED));
        assertTrue(InteractionCode.isValid(InteractionCode.ABORT));
        assertTrue(InteractionCode.isValid(InteractionCode.TRY_OTHER_NETWORK));
        assertTrue(InteractionCode.isValid(InteractionCode.TRY_OTHER_ACCOUNT));
        assertTrue(InteractionCode.isValid(InteractionCode.RETRY));
        assertTrue(InteractionCode.isValid(InteractionCode.RELOAD));
        assertTrue(InteractionCode.isValid(InteractionCode.VERIFY));
    }
}
