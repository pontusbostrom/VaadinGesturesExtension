package com.vaadin.pontus.hammergestures;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;

@JavaScript({ "touchemulator.js",
        "http://cdn.rawgit.com/hammerjs/touchemulator/0.0.2/touch-emulator.js" })
class TouchEmulatorLoader extends AbstractJavaScriptExtension {

    public TouchEmulatorLoader(HammerExtension extension) {
        super(extension);

        callFunction("extend");
    }
}