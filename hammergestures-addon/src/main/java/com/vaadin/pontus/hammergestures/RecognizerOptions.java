package com.vaadin.pontus.hammergestures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class RecognizerOptions {

    private Map<String, Object> options = new HashMap<>();

    public void setOption(String option, Object value) {
        options.put(option, value);
    }

    public Object getOption(String option) {
        return options.get(option);
    }

    public Set<String> getOptions() {
        return options.keySet();
    }

    public String toJson(Gson gson) {
        return gson.toJson(options);
    }

}
