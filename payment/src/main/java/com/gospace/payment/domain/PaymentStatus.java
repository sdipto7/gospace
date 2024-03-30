package com.gospace.payment.domain;

/**
 * @author rumidipto
 * @since 3/30/24
 */
public enum PaymentStatus {

    INITIATED("Initiated"),
    SUCCESSFUL("Successful"),
    FAILED("Failed");

    private String label;

    PaymentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static PaymentStatus fromLabel(String label) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (label.equalsIgnoreCase(paymentStatus.getLabel())) {
                return paymentStatus;
            }
        }

        return null;
    }
}
