package com.gospace.booking.domain;

/**
 * @author rumidipto
 * @since 3/29/24
 */
public enum BookingStatus {

    PROCESSING_PAYMENT("Processing_Payment"),
    PAYMENT_FAILED("Payment_Failed"),
    CONFIRMED("Confirmed");

    private String label;

    BookingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static BookingStatus fromLabel(String label) {
        for (BookingStatus bs : BookingStatus.values()) {
            if (label.equalsIgnoreCase(bs.getLabel())) {
                return bs;
            }
        }

        return null;
    }
}
