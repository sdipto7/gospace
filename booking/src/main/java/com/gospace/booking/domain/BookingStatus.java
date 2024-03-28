package com.gospace.booking.domain;

/**
 * @author rumidipto
 * @since 3/29/24
 */
public enum BookingStatus {

    NEW("New"),
    DRAFT("Draft"),
    PROCESSING_PAYMENT("Processing_Payment"),
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
