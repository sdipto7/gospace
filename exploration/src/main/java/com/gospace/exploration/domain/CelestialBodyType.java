package com.gospace.exploration.domain;

/**
 * @author rumidipto
 * @since 3/25/24
 */
public enum CelestialBodyType {

    PLANET("Planet"),
    MOON("Moon"),
    ASTEROID("Asteroid");

    private String label;

    CelestialBodyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static CelestialBodyType fromLabel(String label) {
        for (CelestialBodyType type : CelestialBodyType.values()) {
            if (label.equalsIgnoreCase(type.getLabel())) {
                return type;
            }
        }

        return null;
    }
}
