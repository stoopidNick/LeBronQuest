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
    protected float width;//width is constant, we take the width of the first viewport
    protected float height;//same
    protected boolean isVisible;

    protected int viewportCounter;
    protected ArrayList<Rectangle2D> viewports;
    
    protected int health;
    public Direction facingDirection;
    protected static float dAccelerationX;
    protected static float dAccelerationY ;
    protected float positionX;
    protected float positionY;
    protected float previousPositionX;
    protected float previousPositionY;
    protected float velocityX;
    protected float velocityY;
    protected float accelerationX;
    protected float accelerationY;
    protected boolean onGround;
    protected boolean isBlockedToTheRight;
    protected boolean isBlockedToTheLeft;
    protected boolean isBlockedAbove;

    public Sprite(String imageFile, int initialX, int initialY, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection, float dAccelerationX, float dAccelerationY){
            //String imageFile, Image image, ImageView imageView, float width, float height, boolean isVisible, int viewportCounter, ArrayList<Rectangle2D> viewports, int health, Direction facingDirection, float positionX, float positionY, float previousPositionX, float previousPositionY, float velocityX, float velocityY, float accelerationX, float accelerationY, boolean onGround, boolean isBlockedToTheRight, boolean isBlockedToTheLeft, boolean isBlockedAbove) {
        this.imageFile = imageFile;        
        this.image = new Image(imageFile);
        imageView = new ImageView(this.image);
        imageView.setCache(true);
        imageView.setSmooth(true);
        viewports = viewportsCoords;
        viewportCounter = 0;
        imageView.setViewport(viewports.get(viewportCounter));
        width = (float) this.getImageView().getBoundsInParent().getWidth();
        height = (float) this.getImageView().getBoundsInParent().getHeight();
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
        LOGGER.setLevel(Level.INFO);
    }

    public abstract void update(float dt);

    public Image getImage() {
        return image;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
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

    public void setPositionX(float positionX) {
        previousPositionX = this.positionX;
        this.positionX = positionX;

    }

    public void setTranslateX(float translateX) {
        imageView.setTranslateX(translateX);

    }

    public void setPositionY(float positionY) {
        previousPositionY = this.positionY;
        this.positionY = positionY;
        LOGGER.info("*****************new  Position Y (because of block)=" + positionY);
    }

    public void setTranslateY(float translateY) {
        imageView.setTranslateY(translateY);
    }

    public float getDeltaPositionX() {
        return positionX - previousPositionX;
    }

    public float getDeltaPositionY() {
        return positionY - previousPositionY;
    }

    public float getAccelerationX() {
        return accelerationX;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
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

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }

    public void setAccelerationY(float accelerationY) {
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

    @Override
    public String toString() {
        return "Sprite{" + "imageFile=" + imageFile + ", image=" + image + ", imageView=" + imageView + ", width=" + width + ", height=" + height + ", isVisible=" + isVisible + ", viewportCounter=" + viewportCounter + ", viewports=" + viewports + ", health=" + health + ", facingDirection=" + facingDirection + ", positionX=" + positionX + ", positionY=" + positionY + ", previousPositionX=" + previousPositionX + ", previousPositionY=" + previousPositionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", onGround=" + onGround + ", isBlockedToTheRight=" + isBlockedToTheRight + ", isBlockedToTheLeft=" + isBlockedToTheLeft + ", isBlockedAbove=" + isBlockedAbove + '}';
    }
    
    
}
