/*
 * Copyright(c) 2012-2019 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 *
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 *
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */
package net.optile.payment.localization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class LocalizationTest {

    @Test
    public void setInstance() {
        Localization.setInstance(new Localization(null, null));
        assertNotNull(Localization.getInstance());
    }

    @Test
    public void setLocalizations() {
        LocalizationHolder shared = createPropLocalizationHolder("sharedKey", "sharedValue", 5);
        Map<String, LocalizationHolder> networks = new HashMap<>();

        networks.put("NETWORK", createNetworkLocalizationHolder("networkKey", "networkValue", 5, shared));
        Localization loc = new Localization(shared, networks);

        assertEquals("sharedValue2", loc.getSharedTranslation("sharedKey2"));
        assertNull(loc.getSharedTranslation("foo"));
    }

    @Test
    public void getNetworkLocalizations() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalizationHolder fallback = new LocalLocalizationHolder(context);

        Map<String, LocalizationHolder> networks = new HashMap<>();
        networks.put("VISA", createNetworkLocalizationHolder("VISA-Key", "VISA-Value", 5, fallback));
        networks.put("MASTERCARD", createNetworkLocalizationHolder("MASTERCARD-Key", "MASTERCARD-Value", 5, fallback));

        Localization loc = new Localization(null, networks);
        assertEquals("VISA-Value2", loc.getNetworkTranslation("VISA", "VISA-Key2"));
        assertNull(loc.getNetworkTranslation("MASTERCARD", "VISA-Key2"));

        assertEquals("Cancel", loc.getNetworkTranslation("VISA", LocalizationKey.BUTTON_CANCEL));
        assertNull(loc.getNetworkTranslation("VISA", "foo"));
    }

    @Test
    public void getSharedLocalizations() {
        Context context = ApplicationProvider.getApplicationContext();
        LocalizationHolder shared = new LocalLocalizationHolder(context);

        Localization loc = new Localization(shared, null);
        assertEquals("OK", loc.getSharedTranslation(LocalizationKey.BUTTON_OK));
        assertNull(loc.getSharedTranslation("foo"));
    }

    /**
     * Create a properties based localization holder with one key value pair
     *
     * @param propKey template for the key, index will be appended
     * @param propValue template for teh value, index will be appended
     * @param nrProps the number of property key/value pairs that should be added to the holder
     * @return the newly created PropLocalizationHolder
     */
    public final static PropLocalizationHolder createPropLocalizationHolder(String propKey, String propValue, int nrProps) {
        Properties prop = new Properties();
        for (int i = 0; i < nrProps; i++) {
            prop.put(propKey + i, propValue + i);
        }
        return new PropLocalizationHolder(prop);
    }

    /**
     * Create a network localization holder for testing,
     *
     * @param propKey template for the key, index will be appended
     * @param propValue template for teh value, index will be appended
     * @param nrProps the number of property key/value pairs that should be added to the holder
     * @param fallback the localization holder used as fallback
     * @return newly created MultiLocalizationHolder
     */
    public final static MultiLocalizationHolder createNetworkLocalizationHolder(String propKey, String propValue, int nrProps,
        LocalizationHolder fallback) {
        LocalizationHolder network = createPropLocalizationHolder(propKey, propValue, nrProps);
        return new MultiLocalizationHolder(network, fallback);
    }
}
