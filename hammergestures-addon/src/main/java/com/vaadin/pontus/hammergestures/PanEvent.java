package com.vaadin.pontus.hammergestures;

public enum PanEvent implements HammerEventValue {
    PAN("pan"), PANSTART("panstart"), PANEND("panend"), PANCANCEL("pancancel"), PANMOVE(
            "panmove"), PANDOWN("pandown"), PANUP("panup"), PANLEFT("panleft"), PANRIGHT(
            "panright");

    private final String value;

    PanEvent(String val) {
        value = val;
    }

    @Override
    public String value() {
        return value;
    }
}
