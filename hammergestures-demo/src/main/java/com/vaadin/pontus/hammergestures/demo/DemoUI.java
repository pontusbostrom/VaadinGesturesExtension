package com.vaadin.pontus.hammergestures.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.alump.scaleimage.ScaleImage;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.pontus.hammergestures.HammerEventData;
import com.vaadin.pontus.hammergestures.HammerExtension;
import com.vaadin.pontus.hammergestures.HammerPanEvent;
import com.vaadin.pontus.hammergestures.HammerPinchEvent;
import com.vaadin.pontus.hammergestures.HammerRecognizer;
import com.vaadin.pontus.hammergestures.HammerRotateEvent;
import com.vaadin.pontus.hammergestures.HammerSwipeEvent;
import com.vaadin.pontus.hammergestures.PanEvent;
import com.vaadin.pontus.hammergestures.PinchEvent;
import com.vaadin.pontus.hammergestures.RecognizerOptions;
import com.vaadin.pontus.hammergestures.RotateEvent;
import com.vaadin.pontus.hammergestures.SwipeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("HammerExtension Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "com.vaadin.pontus.hammergestures.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.addComponent(new Label("<h1> Multi-touch gesture demo</h1>",
                ContentMode.HTML));
        layout.addComponent(new Label(
                "The image can be manipulated with various gestures. "
                        + "The gestures are based on the Hammer.js library. "
                        + "Gestures supported are: pan, swipe, pinch, rotate. "
                        + "In desktop browsers, press shift to enable multi-touch "
                        + "emulation needed for pinch and rotate."));
        layout.addComponent(createContent());
        setContent(layout);

    }

    private AbstractLayout createContent() {
        VerticalLayout layout = new VerticalLayout();
        final MyScaleImage image = new MyScaleImage(new ThemeResource(
                "images/autumn.jpg"));
        image.setWidth(700, Unit.PIXELS);
        image.setHeight(500, Unit.PIXELS);
        image.setStyleProperty("backgroundRepeat", "no-repeat");
        image.setStyleProperty("backgroundSize", "100%");
        image.setStyleProperty("top", "0px");
        image.setStyleProperty("left", "0px");
        CssLayout imageContainer = new CssLayout();
        imageContainer.addStyleName("image-container");
        imageContainer.setWidth(700, Unit.PIXELS);
        imageContainer.setHeight(500, Unit.PIXELS);
        imageContainer.addComponent(image);
        layout.addComponent(imageContainer);

        HammerExtension extension = new HammerExtension(image);

        extension.addPanRecognizer(
                Arrays.asList(PanEvent.PANSTART, PanEvent.PANMOVE),
                new RecognizerOptions(), new ArrayList<>(0));

        RecognizerOptions options = new RecognizerOptions();

        extension.addPinchRecognizer(Arrays.asList(PinchEvent.PINCHSTART,
                PinchEvent.PINCHIN, PinchEvent.PINCHOUT), options, Arrays
                .asList(HammerRecognizer.PAN));

        extension.addRotateRecognizer(
                Arrays.asList(RotateEvent.ROTATESTART, RotateEvent.ROTATEMOVE),
                options,
                Arrays.asList(HammerRecognizer.PAN, HammerRecognizer.PINCH));

        extension.addSwipeRecognizer(
                Arrays.asList(SwipeEvent.SWIPELEFT, SwipeEvent.SWIPERIGHT),
                options, Arrays.asList(HammerRecognizer.PAN));

        extension.addPanListener((HammerPanEvent e) -> {
            HammerEventData data = e.getEventData();
            if (data.getType().equals(PanEvent.PANSTART.value())) {
                image.setStyleProperty("transition", "none");
                image.setInitialPosition(data.getDeltaX(), data.getDeltaY());
            } else {
                image.move(data.getDeltaX(), data.getDeltaY());
            }
        });

        extension.addPinchListener((HammerPinchEvent e) -> {
            HammerEventData data = e.getEventData();
            System.out.println("Pinch (event type) " + data.getType());

            if (data.getType().equals(PinchEvent.PINCHSTART.value())) {
                image.setStyleProperty("transition", "none");
            } else if (data.getType().equals(PinchEvent.PINCHIN.value())) {
                image.setSize(-5f);
            } else if (data.getType().equals(PinchEvent.PINCHOUT.value())) {
                image.setSize(5f);
            }
        });

        extension.addRotateListener((HammerRotateEvent e) -> {
            HammerEventData data = e.getEventData();
            int rot = (int) data.getRotation();
            if (data.getType().equals(RotateEvent.ROTATESTART.value())) {
                image.setStyleProperty("transition", "none");
                image.initialRotation(rot);
                ;
            } else {
                image.rotate(rot);
            }
            System.out.println("Rotate (rotation) " + data.getRotation());

        });

        extension.addSwipeListener((HammerSwipeEvent e) -> {
            HammerEventData data = e.getEventData();
            System.out.println("Swipe " + data.getType());
            image.setStyleProperty("top", "0px");
            image.setStyleProperty("left", "0px");
            image.setStyleProperty("transition", "transform 1.0s ease");
            int rotation = image.getRotation();
            int newRotation = (rotation + 180) % 360;
            image.initialRotation(newRotation);
            image.rotate(0);

        });

        HorizontalLayout removeButtons = new HorizontalLayout();
        removeButtons.setSpacing(true);
        List<HammerRecognizer> recognizers = Arrays.asList(
                HammerRecognizer.PAN, HammerRecognizer.PINCH,
                HammerRecognizer.ROTATE, HammerRecognizer.SWIPE);
        for (HammerRecognizer req : recognizers) {
            Button button = new Button("Remove " + req.name());
            button.addClickListener((ClickEvent e) -> {
                extension.removeRecognizers(Arrays.asList(req));
            });
            removeButtons.addComponent(button);
        }
        layout.addComponent(removeButtons);

        return layout;

    }

    static class MyScaleImage extends ScaleImage {

        private int x = 0;
        private int y = 0;
        private int rotation = 0;

        public MyScaleImage(Resource resource) {
            super(resource);
            setStyleProperty("position", "relative");
            setStyleProperty("backgroundPosition", "0px 0px");
        }

        @Override
        public void setStyleProperty(String property, String value) {
            super.setStyleProperty(property, value);
        }

        @Override
        public void clearStyleProperty(String property) {
            super.clearStyleProperty(property);
        }

        public void setInitialPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move(int nx, int ny) {
            setStyleProperty("top", (ny - y) + "px");
            setStyleProperty("left", (nx - x) + "px");
        }

        public void setSize(float delta) {
            float width = getWidth() + delta;
            float height = getHeight() + delta;

            super.setWidth(Math.max(0, width), getWidthUnits());
            super.setHeight(Math.max(0, height), getHeightUnits());
        }

        public void initialRotation(int deg) {
            rotation = deg;
        }

        public void rotate(int ndeg) {
            int deltaDeg = ndeg - rotation;

            String v = "rotate(" + deltaDeg + "deg)";
            System.out.println("Rotate value " + v);
            setStyleProperty("transform", v);
        }

        public int getRotation() {
            return rotation;
        }
    }
}
