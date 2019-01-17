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

package net.optile.payment.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import net.optile.payment.R;
import net.optile.payment.ui.PaymentUI;
import net.optile.payment.ui.theme.DialogParameters;
import net.optile.payment.util.PaymentUtils;
import android.view.WindowManager;

/**
 * Message Dialog Fragment for showing a message to the user with an action button
 */
public final class MessageDialogFragment extends DialogFragment {

    private String title;
    private String message;
    private String neutralButtonLabel;
    private String imagePrefix;
    private String imageSuffix;
    private int imageResId;
    private MessageDialogListener listener;

    /**
     * Set the title in this message dialog
     *
     * @param title shown in the top of this message dialog
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the message in this message dialog that should be shown to the user
     *
     * @param message shown in the middle of the message dialog
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set the neutral button label
     *
     * @param label the Label of the button
     */
    public void setNeutralButton(String label) {
        this.neutralButtonLabel = label;
    }

    public void setImageLabels(String imagePrefix, String imageSuffix) {
        this.imagePrefix = imagePrefix;
        this.imageSuffix = imageSuffix;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    /**
     * Set the listener to this MessageDialogFragment
     *
     * @param listener to inform of an action button click
     */
    public void setListener(MessageDialogListener listener) {
        this.listener = listener;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme()) {
        @Override
            public void onBackPressed() {
                handleBackPressed();
            }
        };
        return dialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTheme() {
        DialogParameters params = PaymentUI.getInstance().getPaymentTheme().getDialogParameters();
        int theme = params.getDialogTheme();
        return theme == 0 ? super.getTheme() : theme;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogParameters params = PaymentUI.getInstance().getPaymentTheme().getDialogParameters();
        View v = inflater.inflate(R.layout.dialogfragment_message, container, false);
        initTitle(v, params);
        initMessage(v, params);
        initImage(v, params);
        initButtons(v, params);
        return v;
    }
    
    private void initImage(View rootView, DialogParameters params) {

        View layout = rootView.findViewById(R.id.layout_image);
        if (imageResId == 0) {
            layout.setVisibility(View.GONE);
            return;
        }
        layout.setVisibility(View.VISIBLE);
        initImageLabel(rootView.findViewById(R.id.text_imageprefix), imagePrefix, params);
        initImageLabel(rootView.findViewById(R.id.text_imagesuffix), imageSuffix, params);
        ImageView view = rootView.findViewById(R.id.image_logo);
        view.setVisibility(View.VISIBLE);
        view.setImageResource(imageResId);
    }

    private void initImageLabel(TextView tv, String label, DialogParameters params) {
        if (TextUtils.isEmpty(label)) {
            tv.setVisibility(View.GONE);
            return;
        }
        PaymentUtils.setTextAppearance(tv, params.getImageLabelStyle());
        tv.setVisibility(View.VISIBLE);
        tv.setText(label);
    }


    private void initTitle(View rootView, DialogParameters params) {
        TextView tv = rootView.findViewById(R.id.text_title);
        PaymentUtils.setTextAppearance(tv, params.getMessageTitleStyle());

        if (TextUtils.isEmpty(title)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(title);
    }

    private void initMessage(View rootView, DialogParameters params) {
        TextView tvTitle = rootView.findViewById(R.id.text_message_title);
        TextView tvNoTitle = rootView.findViewById(R.id.text_message_notitle);

        PaymentUtils.setTextAppearance(tvTitle, params.getMessageDetailsStyle());
        PaymentUtils.setTextAppearance(tvNoTitle, params.getMessageDetailsNoTitleStyle());

        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
            initMessage(tvNoTitle);
        } else {
            tvNoTitle.setVisibility(View.GONE);
            initMessage(tvTitle);
        }
    }

    private void initMessage(TextView textView) {

        if (TextUtils.isEmpty(message)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
    }

    private void initButtons(View rootView, DialogParameters params) {
        TextView tv = rootView.findViewById(R.id.text_button);

        if (TextUtils.isEmpty(neutralButtonLabel)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(neutralButtonLabel);
        PaymentUtils.setTextAppearance(tv, params.getButtonLabelStyle());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNeutralButtonClick();
            }
        });
    }

    private void handleNeutralButtonClick() {
        if (this.listener != null) {
            listener.onNeutralButtonClick();
        }
        dismiss();
    }

    private void handleBackPressed() {
        if (this.listener != null) {
            listener.onCancelled();
        }
        dismiss();
    }


    private void setTextView(final View rootView, final int resId, final String value) {
        TextView tv = rootView.findViewById(resId);
        if (TextUtils.isEmpty(value)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(value);
    }

    public interface MessageDialogListener {
        void onNeutralButtonClick();

        void onCancelled();
    }
}
