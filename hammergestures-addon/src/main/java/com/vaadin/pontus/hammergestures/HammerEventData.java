package com.vaadin.pontus.hammergestures;

public class HammerEventData {

    private String type;
    private int deltaX;
    private int deltaY;
    private int deltatime;
    private double distance;
    private double angle;
    private double velocityX;
    private double velocityY;
    private double velocity;
    private int direction;
    private int offsetDirection;
    private double rotation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    public int getDeltatime() {
        return deltatime;
    }

    public void setDeltatime(int deltatime) {
        this.deltatime = deltatime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getOffsetDirection() {
        return offsetDirection;
    }

    public void setOffsetDirection(int offsetDirection) {
        this.offsetDirection = offsetDirection;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

}
