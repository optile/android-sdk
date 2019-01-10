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

package net.optile.payment.ui.list;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.theme.PageParameters;
import net.optile.payment.ui.theme.PaymentTheme;
import net.optile.payment.util.PaymentUtils;

/**
 * The HeaderViewHolder holding and binding views for a header in the RecyclerView
 */
final class HeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;

    HeaderViewHolder(View parent) {
        super(parent);
        this.title = parent.findViewById(R.id.text_title);
    }

    static ViewHolder createInstance(ListAdapter adapter, ViewGroup parent) {
        View view = View.inflate(adapter.getContext(), R.layout.list_item_header, null);
        HeaderViewHolder holder = new HeaderViewHolder(view);
        holder.applyTheme(adapter.getPaymentTheme());
        return holder;
    }

    void applyTheme(PaymentTheme theme) {
        PageParameters params = theme.getPageParameters();
        PaymentUtils.setTextAppearance(title, params.getSectionHeaderLabelStyle());
    }

    void onBind(HeaderItem item) {
        title.setText(item.title);
    }
}