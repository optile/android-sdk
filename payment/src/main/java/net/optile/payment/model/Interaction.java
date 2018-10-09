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

package net.optile.payment.model;

/**
 * This class is designed to hold interaction information that prescribes further reaction of merchant portal to this transaction or operation.
 */
public class Interaction {
    /** Simple API, always present */
    @InteractionCode.Definition
    private String code;
    /** Simple API, always present */
    @InteractionReason.Definition
    private String reason;

    /**
     * Gets value of code.
     *
     * @return the code.
     */
    @InteractionCode.Definition
    public String getCode() {
        return code;
    }

    /**
     * Sets value of code.
     *
     * @param code the code to set.
     */
    public void setCode(@InteractionCode.Definition String code) {
        this.code = code;
    }

    /**
     * Gets value of reason.
     *
     * @return the reason.
     */
    @InteractionReason.Definition
    public String getReason() {
        return reason;
    }

    /**
     * Sets value of reason.
     *
     * @param reason the reason to set.
     */
    public void setReason(@InteractionReason.Definition String reason) {
        this.reason = reason;
    }
}