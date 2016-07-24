package com.vaadin.pontus.hammergestures;

public enum RotateEvent implements HammerEventValue {
    ROTATE("rotate"), ROTATESTART("rotatestart"), ROTATEMOVE("rotatemove"), ROTATECANCEL(
            "rotatecancel"), ROTATEEND("rotateend");

    RotateEvent(String v) {
        value = v;
    }

    private String value;

    @Override
    public String value() {
        return value;
    }

}
