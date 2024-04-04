package com.gospace.spacetrip.domain;

/**
 * @author rumidipto
 * @since 3/17/24
 */
public enum Manufacturer {

    SPACE_X("Space X"),
    ROCKET_LAB("Rocket Lab"),
    SIERRA_SPACE("Sierra Space"),
    BLUE_ORIGIN("Blue Origin");

    private String label;

    Manufacturer(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static Manufacturer fromLabel(String label) {
        for (Manufacturer mf : Manufacturer.values()) {
            if (label.equalsIgnoreCase(mf.getLabel())) {
                return mf;
            }
        }

        return null;
    }
}
