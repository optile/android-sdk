/*
 * Copyright (c) 2020 Payoneer Germany GmbH
 * https://www.payoneer.com
 *
 * This file is open source and available under the MIT license.
 * See the LICENSE file for more information.
 */

package com.payoneer.mrs.examplesdk;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;

import com.payoneer.mrs.payment.model.InteractionCode;
import com.payoneer.mrs.payment.model.InteractionReason;
import com.payoneer.mrs.payment.ui.page.PaymentListActivity;
import com.payoneer.mrs.sharedtest.service.ListService;
import com.payoneer.mrs.sharedtest.view.ActivityHelper;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

class AbstractTest {

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }

    void enterListUrl(String listUrl) {
        onView(withId(R.id.input_listurl)).perform(typeText(listUrl));
    }

    IdlingResource getResultIdlingResource() {
        ExampleSdkActivity activity = (ExampleSdkActivity) ActivityHelper.getCurrentActivity();
        return activity.getResultHandledIdlingResource();
    }

    void matchResultInteraction(String interactionCode, String interactionReason) {
        onView(ViewMatchers.withId(R.id.text_interactioncode)).check(matches(withText(interactionCode)));
        onView(ViewMatchers.withId(R.id.text_interactionreason)).check(matches(withText(interactionReason)));
    }


    String createListUrl() {
        String baseUrl = BuildConfig.paymentapi_baseurl;
        String authHeader = BuildConfig.paymentapi_authheader;
        return ListService.createListUrl(com.payoneer.mrs.examplesdk.test.R.raw.listtemplate, false, baseUrl, authHeader);
    }

    void clickActionButton() {
        onView(withId(R.id.button_action)).perform(click());
        intended(hasComponent(PaymentListActivity.class.getName()));
        onView(withId(R.id.layout_paymentlist)).check(matches(isDisplayed()));
    }

    void register(IdlingResource resource) {
        IdlingRegistry.getInstance().register(resource);
    }

    void unregister(IdlingResource resource) {
        IdlingRegistry.getInstance().unregister(resource);
    }
}
