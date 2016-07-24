package com.vaadin.pontus.hammergestures;

import java.util.EventObject;

public abstract class HammerEvent extends EventObject {

    private HammerEventData data;

    public HammerEvent(Object source, HammerEventData data) {
        super(source);
        this.data = data;

    }

    public HammerEventData getEventData() {
        return data;
    }

}
