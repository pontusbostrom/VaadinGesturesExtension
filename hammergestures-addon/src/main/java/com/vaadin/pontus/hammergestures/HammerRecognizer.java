package com.vaadin.pontus.hammergestures;

import com.google.gson.annotations.SerializedName;

public enum HammerRecognizer {
    @SerializedName("pan")
    PAN, @SerializedName("swipe")
    SWIPE, @SerializedName("rotate")
    ROTATE, @SerializedName("pinch")
    PINCH;

}
