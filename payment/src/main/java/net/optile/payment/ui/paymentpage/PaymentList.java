/*
 * Copyright(c) 2012-2018 optile GmbH. All Rights Reserved.
 * https://www.optile.net
 * <p>
 * This software is the property of optile GmbH. Distribution  of  this
 * software without agreement in writing is strictly prohibited.
 * <p>
 * This software may not be copied, used or distributed unless agreement
 * has been received in full.
 */

package net.optile.payment.ui.paymentpage;

import java.util.List;

import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentTheme;

/**
 * The PaymentList showing available payment methods in a list
 */
final class PaymentList implements PaymentListAdapter.OnItemListener {

    private final static String TAG = "pay_PaymentList";
    
    private final PaymentPageActivity activity;
    
    private final PaymentListAdapter adapter;

    private final RecyclerView recyclerView;

    private int selIndex;

    PaymentList(PaymentPageActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.adapter = new PaymentListAdapter(this);
        this.adapter.setListener(this);

        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    PaymentPageActivity getActivity() {
        return activity;
    }
    
    void setItems(int selIndex, List<PaymentGroup> items) {
        this.selIndex = selIndex;
        adapter.setItems(items);
        recyclerView.scrollToPosition(selIndex);
    }

    void clearItems() {
        adapter.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionClicked(PaymentGroup item, int position) {
        Log.i(TAG, "on Action Clicked: " + position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClicked(PaymentGroup item, int position) {

        if (position == this.selIndex) {
            return;
        }
        int curIndex = this.selIndex;
        this.selIndex = position;
        hideKeyboard();

        // first, hide the current selected element
        PaymentListViewHolder holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(curIndex);
        if (holder != null) {
            holder.expand(false);
            adapter.notifyItemChanged(curIndex);
        }
        // second, expand the new selected element
        holder = (PaymentListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            holder.expand(true);
            adapter.notifyItemChanged(position);
            smoothScrollToPosition(position);
        }

    }

    int getSelected() {
        return selIndex;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
        }
    }

    private void smoothScrollToPosition(int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(activity) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }
}
