package com.vaadin.pontus.hammergestures;

public enum SwipeEvent implements HammerEventValue {
    SWIPE("swipe"), SWIPELEFT("swipeleft"), SWIPERIGHT("swiperight"), SWIPEUP(
            "swipeup"), SWIPEDOWN("swipedown");

    private String value;

    SwipeEvent(String v) {
        value = v;
    }

    @Override
    public String value() {
        return value;
    }

}
