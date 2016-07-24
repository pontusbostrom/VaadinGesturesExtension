package com.vaadin.pontus.hammergestures;

import com.vaadin.ui.AbstractComponent;

public interface HammerEventConstructor {

    HammerEvent construct(AbstractComponent source, HammerEventData eventData);

}
