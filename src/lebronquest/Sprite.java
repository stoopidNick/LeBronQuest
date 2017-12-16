/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author transflorida
 */
public abstract class Sprite {

    private final static Logger LOGGER = Logger.getLogger(Sprite.class.getName());
    protected String imageFile;
    protected Image image;
    protected ImageView imageView;
    protected double width;//width is constant, we take the width of the first viewport
    protected double height;//same
    protected boolean isVisible;

    protected int viewportCounter;
    protected ArrayList<Rectangle2D> viewports;
    
    protected int health;
    public Direction facingDirection;
    protected static double dAccelerationX;
    protected static double dAccelerationY ;
    protected double positionX;
    protected double positionY;
    protected double previousPositionX;
    protected double previousPositionY;
    protected double velocityX;
    protected double velocityY;
    protected double accelerationX;
    protected double accelerationY;
    protected boolean onGround;
    protected boolean isBlockedToTheRight;
    protected boolean isBlockedToTheLeft;
    protected boolean isBlockedAbove;

    public Sprite(String imageFile, int initialX, int initialY, boolean isVisible,
             int health, Direction facingDirection, double dAccelerationX, double dAccelerationY){
        this.imageFile = imageFile;        
        this.image = new Image(imageFile);
        imageView = new ImageView(this.image);
        imageView.setCache(true);
        imageView.setSmooth(true);
        viewportCounter = 0;
        
        imageView.setTranslateX(initialX);//initial x
        imageView.setTranslateY(initialY);//initial y

        this.isVisible = isVisible;
        this.health = health;        
        this.facingDirection = facingDirection;
        
        positionX = initialX;
        positionY = initialY;
        previousPositionX = 0;
        previousPositionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
        
        Sprite.dAccelerationX = dAccelerationX;//0.8
        Sprite.dAccelerationY = dAccelerationY;//50
        LOGGER.setLevel(Level.OFF);
    }

    public abstract void update(double dt);

    public Image getImage() {
        return image;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setPositionX(double positionX) {
        previousPositionX = this.positionX;
        this.positionX = positionX;

    }

    public void setTranslateX(double translateX) {
        imageView.setTranslateX(translateX);

    }

    public void setPositionY(double positionY) {
        previousPositionY = this.positionY;
        this.positionY = positionY;
    }

    public void setTranslateY(double translateY) {
        imageView.setTranslateY(translateY);
    }

    public double getDeltaPositionX() {
        return positionX - previousPositionX;
    }

    public double getDeltaPositionY() {
        return positionY - previousPositionY;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public int getHealth() {
        return health;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }

    public void setIsBlockedToTheRight(boolean isBlockedToTheRight) {
        this.isBlockedToTheRight = isBlockedToTheRight;
    }

    public void setIsBlockedToTheLeft(boolean isBlockedToTheLeft) {
        this.isBlockedToTheLeft = isBlockedToTheLeft;
    }

    public void setIsBlockedAbove(boolean isBlockedAbove) {
        this.isBlockedAbove = isBlockedAbove;
    }

    public void setViewports(ArrayList<Rectangle2D> viewports) {
        this.viewports = viewports;
    }

    @Override
    public String toString() {
        return "Sprite{" + " positionX=" + positionX + ", positionY=" + positionY + ", previousPositionX=" + previousPositionX + ", previousPositionY=" + previousPositionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", onGround=" + onGround + ", isBlockedToTheRight=" + isBlockedToTheRight + ", isBlockedToTheLeft=" + isBlockedToTheLeft + ", isBlockedAbove=" + isBlockedAbove + ", width=" + width + ", height=" + height + ", isVisible=" + isVisible + ", viewportCounter=" + viewportCounter + ", health=" + health + ", facingDirection=" + facingDirection+'}';
    }
    
    
}
