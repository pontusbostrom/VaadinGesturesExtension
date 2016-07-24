package com.vaadin.pontus.hammergestures;

public enum PinchEvent implements HammerEventValue {
    PINCH("pinch"), PINCHSTART("pinchstart"), PINCHEND("pinchend"), PINCHCANCEL(
            "pincancel"), PINCHMOVE("pinchmove"), PINCHIN("pinchin"), PINCHOUT(
            "pinchout");

    private String value;

    PinchEvent(String v) {
        value = v;
    }

    @Override
    public String value() {
        return value;
    }

}
