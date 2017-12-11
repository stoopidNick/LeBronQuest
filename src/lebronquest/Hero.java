package lebronquest;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import static lebronquest.LeBronQuest.GRAVITY;


public class Hero extends Sprite  implements EventHandler<KeyEvent>{
    private int health;   
    public Direction facingDirection;
    private float dAccelerationX = 0.8f;    
    private float dAccelerationY = 50f;
    private float positionX;
    private float positionY;
    private float previousPositionX;
    private float previousPositionY;
    private float velocityX;
    private float velocityY;
    private float accelerationX;    
    private float accelerationY;
    private boolean onGround;
    private boolean isBlockedToTheRight;
    private boolean isBlockedToTheLeft;
    private boolean isBlockedAbove;
    
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    
  

    public Hero(String imageFile, int initialX, int initialY, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection) {
        super(imageFile, initialX, initialY, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = false;
        positionX = initialX;
        positionY = initialY;
        previousPositionX = 0;
        previousPositionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;        
    }
    
    public Hero(String imageFile, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection) {
        super(imageFile, 0, 0, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = false;
        positionX = 0;
        positionY = 0;
        previousPositionX = 0;
        previousPositionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
    }

    
    public void update(float dt) {
        if(onGround){            
            if(Math.abs(accelerationX - 0) < 0.01){
                accelerationX = 0;
            } 
            if(Math.abs(velocityX - 0) < 0.15){
                velocityX = 0;
            } else { //add friction
                if(velocityX > 0){ //add friction
                    velocityX -= 0.2;
                } else if(velocityX < 0){ //add friction
                    velocityX += 0.2;
                }
            }            
        }
        //Use Physics (kinematic equations) to determine next position and velocity
        velocityX = accelerationX * dt + velocityX;
        velocityY = accelerationY * dt + velocityY;
        
        previousPositionX = positionX;
        previousPositionY = positionY;
        float dt2 = (float) Math.pow(dt, 2);
        positionX = accelerationX * dt2 / 2 + velocityX * dt + positionX;
        positionY = accelerationY * dt2 / 2 + velocityY * dt + positionY;      
        
        
        
        //HERE
        //imageView.setTranslateX(positionX);
        //imageView.setTranslateY(positionY);
        
        
        
        
System.out.println("UPDATED       "+this);

        //animating hero
        //if(desiredVelocityX != 0){
        if(leftKeyPressed || rightKeyPressed){
            viewportCounter =  (++viewportCounter) % (viewports.size());
            imageView.setViewport(viewports.get(viewportCounter));
        }
    }
    //HERE
    public void setPositionX(float positionX){
        previousPositionX = this.positionX;
        this.positionX = positionX;
        
    }
    
    public void setTranslateX(float translateX){
        imageView.setTranslateX(translateX);
        
    }
    
    public void setPositionY(float positionY){
        previousPositionY = this.positionY;
        this.positionY = positionY;
    }
    
    public void setTranslateY(float translateY){
        imageView.setTranslateY(translateY);
    }
    
    @Override
    public void handle(KeyEvent event) {
System.out.println("              isBlockedToTheRight="+isBlockedToTheRight);
System.out.println("              isBlockedToTheLeft="+isBlockedToTheLeft);

        if(event.getEventType() == KeyEvent.KEY_PRESSED ){
            
            //viewportCounter = ++viewportCounter % viewports.size();
            
            if(event.getCode() == KeyCode.LEFT  && !leftKeyPressed){
                leftKeyPressed = true;
System.out.println("      leftKeyPressed="+leftKeyPressed);
                //velocityX = velocityX - speed;
                if(!isBlockedToTheLeft){
                    accelerationX -= dAccelerationX;
                }
                imageView.setScaleX(-1);//flips the image
                facingDirection = Direction.LEFT;
System.out.println("HAN LFT KEY  PR"+this);
            } else if(event.getCode() == KeyCode.RIGHT  && !rightKeyPressed ){
                rightKeyPressed = true;
System.out.println("      rightKeyPressed="+rightKeyPressed);
                //velocityX = velocityX + speed;
                if(!isBlockedToTheRight){
                    accelerationX += dAccelerationX;
                }
                imageView.setScaleX(1);//flips the image
                facingDirection = Direction.RIGHT;
System.out.println("HAN RGT KEY  PR"+this);
            } else if(event.getCode() == KeyCode.UP){

                //facingDirection = Direction.UP;

System.out.println("onGround?"+onGround);
                if(onGround){
                    accelerationY -= dAccelerationY;
                }
System.out.println("HAND UP KEY PR"+this);
            } else if(event.getCode() == KeyCode.DOWN){
                facingDirection = Direction.DOWN;
                //velocityY = velocityY + speed;
System.out.println("HAND DO KEY PR"+this);
            } else if(event.getCode() == KeyCode.SPACE) {
                /*
                //create arrow
                Rectangle2D viewport = new Rectangle2D(2, 9, 20, 9); 
                ArrayList<Rectangle2D> arrowViewPorts = new ArrayList<>();
                arrowViewPorts.add(viewport);
                int width = (int) viewport.getWidth();
                
                Arrow arrow;
                if(goesEast){ 
                    arrow = new Arrow("arrow.png", (int)imageView.getTranslateX()+width, 
                            (int)imageView.getTranslateY(), true, arrowViewPorts, ZombieInvaders.ARROW_SPEED,true );
                } else {
                    arrow = new Arrow("arrow.png", (int)imageView.getTranslateX()-width, 
                            (int)imageView.getTranslateY(), true, arrowViewPorts, ZombieInvaders.ARROW_SPEED,false );
                }
                ZombieInvaders.addArrow(arrow);
                */
System.out.println("HAND SP KEY PR"+this);
            }
 
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED){
            

            if(event.getCode() == KeyCode.LEFT ){
                leftKeyPressed = false;
System.out.println("      leftKeyPressed="+leftKeyPressed);
                //velocityX = velocityX + speed;
                if( !isBlockedToTheLeft){
                    accelerationX += dAccelerationX;
                }
System.out.println("HAN LFT KEY  RE"+this);
            } else if(event.getCode() == KeyCode.RIGHT ){
                rightKeyPressed = false;
System.out.println("      rightKeyPressed="+rightKeyPressed);
                if(!isBlockedToTheRight){
                //velocityX = velocityX - speed;
                    accelerationX -= dAccelerationX;
                }
System.out.println("HAN RGT KEY  RE"+this);
            }else if(event.getCode() == KeyCode.UP){
//System.out.println("jumping?"+onGround);
//                if(onGround){
//                    setAccelerationY(getAccelerationY() + GRAVITY);
//                }
//                //velocityY = velocityY + speed;
//                velocityY = 0;
System.out.println("HAN UP KEY  RE"+this);
            } else if(event.getCode() == KeyCode.DOWN){
                //velocityY = velocityY - speed;
                //velocityY = 0;
System.out.println("HAN DO KEY  RE"+this);
            }
 

        }

    }
    
    public float getDeltaPositionX(){
        return positionX - previousPositionX;
    }
    
    public float getDeltaPositionY(){
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

    public void setHealth(int health){
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
        return "Hero{" + "health=" + health + ", facingDirection=" + facingDirection + ", dAccelerationX=" + dAccelerationX + ", positionX=" + positionX + ", positionY=" + positionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", onGround=" + onGround + ", isBlockedToTheRight=" + isBlockedToTheRight + ", isBlockedToTheLeft=" + isBlockedToTheLeft + ", isBlockedAbove=" + isBlockedAbove + ", XAxisKeyPressed=" + leftKeyPressed + '}';
    }
}
