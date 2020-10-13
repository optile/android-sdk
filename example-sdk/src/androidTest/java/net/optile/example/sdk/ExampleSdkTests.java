/*
 * Copyright (c) 2019 optile GmbH
 * https://www.optile.net
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package net.optile.example.sdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import net.optile.payment.test.service.ListConfig;
import net.optile.payment.test.service.ListService;
import net.optile.payment.ui.page.PaymentListActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleSdkTests {
    @Rule
    public ActivityTestRule<ExampleSdkActivity> activityRule = new ActivityTestRule<>(
        ExampleSdkActivity.class);

    @Test
    public void sdkPageVisibleTest() {
        onView(withId(R.id.activity_examplesdk)).check(matches(isDisplayed()));
    }

    @Test
    public void openPaymentListTest() throws JSONException, IOException {
        Intents.init();
        openPaymentList();
        Intents.release();
    }

    private void openPaymentList() throws JSONException, IOException {
        String listUrl = createListUrl(net.optile.example.sdk.test.R.raw.listtemplate, false);
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
        onView(withId(R.id.button_action)).perform(click());

        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_paymentlist)).check(matches(isDisplayed()));
    }

    public String createListUrl(int jsonResId, boolean presetFirst) throws JSONException, IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String url = context.getString(R.string.paymentapi_url);
        String auth = context.getString(R.string.paymentapi_auth);

        ListService service = ListService.createInstance(url, auth);
        ListConfig config = service.createListConfig(jsonResId);
        config.setPresetFirst(presetFirst);
        return service.createListUrl(config);
    }
}