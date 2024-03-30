package com.gospace.payment.domain;

/**
 * @author rumidipto
 * @since 3/30/24
 */
public enum PaymentMethod {

    CASH("Cash"),
    VISA_CREDIT("Visa-credit"),
    VISA_DEBIT("Visa-debit"),
    MASTERCARD_CREDIT("Mastercard-credit"),
    MASTERCARD_DEBIT("Mastercard-debit");

    private String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static PaymentMethod fromLabel(String label) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (label.equalsIgnoreCase(paymentMethod.getLabel())) {
                return paymentMethod;
            }
        }

        return null;
    }
}
