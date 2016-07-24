package com.vaadin.pontus.hammergestures;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.vaadin.annotations.JavaScript;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.ui.AbstractComponent;

import elemental.json.JsonArray;

/**
 * Extension that provides gesture support to arbitrary Vaadin components. The
 * gestures supported are:pan, swipe, rotate and zoom. Each gesture type also
 * have subtypes. The user can choose to only register recognizers that listens
 * for certain subtypes.
 *
 * The extension works by first registering a desired recognizer that listens
 * for the desired gestures. An event listener can then be registered to listen
 * for the generated events.
 *
 * The extension is based on the Hammer library, see {@linktourl
 * http://hammerjs.github.io}. This extension provides essentially a somewhat
 * simplified server-side api to that library.
 *
 * @author Pontus Boström
 *
 */
@JavaScript({
        "http://cdn.rawgit.com/hammerjs/touchemulator/0.0.2/touch-emulator.js",
        "gestures.js", "http://hammerjs.github.io/dist/hammer.min.js" })
public class HammerExtension extends AbstractJavaScriptExtension {

    /**
     * The component to extend
     */
    private AbstractComponent component;

    public HammerExtension(AbstractComponent component) {
        super(component);
        this.component = component;

        callFunction("extend");

    }

    /**
     * Adds a recognizer on the client-side for the chosen pan events. The
     *
     * @param events
     *            The events to handle
     * @param options
     *            The options for the recognizer. See the Hammer library
     *            documentation for possible options.
     * @param recognizeWith
     *            The recognizer that should be active with this one
     */
    public void addPanRecognizer(Collection<PanEvent> events,
            RecognizerOptions options, List<HammerRecognizer> recognizeWith) {
        addRecognizer(events, options, "addPanRecognizer", "panEventHandler",
                recognizeWith,
                (AbstractComponent comp, HammerEventData data) -> {
                    return new HammerPanEvent(component, data);
                });
    }

    /**
     * Adds a recognizer on the client-side for the chosen pinch events.
     *
     * @param events
     * @param options
     * @param recognizeWith
     */
    public void addPinchRecognizer(Collection<PinchEvent> events,
            RecognizerOptions options, List<HammerRecognizer> recognizeWith) {
        addRecognizer(events, options, "addPinchRecognizer",
                "pinchEventHandler", recognizeWith, (AbstractComponent comp,
                        HammerEventData data) -> {
                    return new HammerPinchEvent(component, data);
                });
    }

    /**
     * Adds a recognizer on the client-side for the chosen rotate events.
     *
     * @param events
     * @param options
     * @param recognizeWith
     */
    public void addRotateRecognizer(Collection<RotateEvent> events,
            RecognizerOptions options, List<HammerRecognizer> recognizeWith) {
        addRecognizer(events, options, "addRotateRecognizer",
                "rotateEventHandler", recognizeWith, (AbstractComponent comp,
                        HammerEventData data) -> {
                    return new HammerRotateEvent(component, data);
                });
    }

    /**
     * Adds a recognizer on the client-side for the chosen swipe events.
     *
     * @param events
     * @param options
     * @param recognizeWith
     */
    public void addSwipeRecognizer(Collection<SwipeEvent> events,
            RecognizerOptions options, List<HammerRecognizer> recognizeWith) {
        addRecognizer(events, options, "addSwipeRecognizer",
                "swipeEventHandler", recognizeWith, (AbstractComponent comp,
                        HammerEventData data) -> {
                    return new HammerSwipeEvent(component, data);
                });
    }

    private void addRecognizer(Collection<? extends HammerEventValue> events,
            RecognizerOptions options, String addRecognizerFunction,
            String eventHandlerFunction, List<HammerRecognizer> recognizeWith,
            HammerEventConstructor contructor) {
        Set<String> optionSet = options.getOptions();

        String eventStr = events.stream().map((HammerEventValue e) -> {
            return e.value();
        }).reduce("", (String a1, String a2) -> {
            return a1 + " " + a2;
        });
        Gson gson = new Gson();
        if (optionSet.isEmpty()) {
            callFunction(addRecognizerFunction, eventStr.trim(),
                    gson.toJson(recognizeWith));
        } else {
            callFunction(addRecognizerFunction, eventStr.trim(),
                    gson.toJson(recognizeWith), options.toJson(gson));
        }
        addFunction(
                eventHandlerFunction,
                (JsonArray args) -> {
                    if (args.length() == 1) {
                        HammerEventData data = new Gson()
                                .<HammerEventData> fromJson(args.get(0)
                                        .toJson(), HammerEventData.class);
                        fireEvent(contructor.construct(component, data));
                    } else {
                        throw new RuntimeException(
                                "A malformed event was sent from client-side with "
                                        + args.length() + " arguments");
                    }

                });
    }

    /**
     * Adds a listener for events generated by the recognizer. Note that only
     * events that have been registered by a recognizer will ever by received
     * here.
     *
     * @param listener
     *            The listener for the events
     */
    public void addPanListener(PanEventListener listener) {
        try {
            addListener(HammerPanEvent.class, listener,
                    PanEventListener.class.getMethod("handlePan",
                            HammerPanEvent.class));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(
                    "Tried to register non-extisting or inaccessible method");
        }
    }

    public void addRotateListener(RotateEventListener listener) {
        try {
            addListener(HammerRotateEvent.class, listener,
                    RotateEventListener.class.getMethod("handleRotate",
                            HammerRotateEvent.class));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(
                    "Tried to register non-extisting or inaccessible method");
        }
    }

    public void addSwipeListener(SwipeEventListener listener) {
        try {
            addListener(HammerSwipeEvent.class, listener,
                    SwipeEventListener.class.getMethod("handleSwipe",
                            HammerSwipeEvent.class));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(
                    "Tried to register non-extisting or inaccessible method");
        }
    }

    public void addPinchListener(PinchEventListener listener) {
        try {
            addListener(HammerPinchEvent.class, listener,
                    PinchEventListener.class.getMethod("handlePinch",
                            HammerPinchEvent.class));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(
                    "Tried to register non-extisting or inaccessible method");
        }
    }

    /**
     * Removes a recognizer from client-side. Events from the recognizer will
     * thus not be sent anymore
     *
     * @param recognizers
     *            The recognizers to remove
     */
    public void removeRecognizers(List<HammerRecognizer> recognizers) {
        callFunction("removeRecognizers", new Gson().toJson(recognizers));
        for (HammerRecognizer req : recognizers) {
            switch (req) {
            case PAN:
                removeListenersForEvent(HammerPanEvent.class);
                break;
            case SWIPE:
                removeListenersForEvent(HammerSwipeEvent.class);
                break;
            case ROTATE:
                removeListenersForEvent(HammerRotateEvent.class);
                break;
            case PINCH:
                removeListenersForEvent(HammerPinchEvent.class);
                break;
            default:
                break;
            }

        }
    }

    private void removeListenersForEvent(Class<? extends HammerEvent> event) {
        Collection<?> listeners = getListeners(event);
        for (Object listener : listeners) {
            removeListener(event, listener);
        }
    }
}
