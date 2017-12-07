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
    private float positionX;
    private float positionY;
    private float velocityX;
    private float velocityY;
    private float accelerationX;    
    private float accelerationY;
    //private float desiredPositionX;
    //private float desiredPositionY;    
    //private float desiredVelocityX;
    //private float desiredVelocityY;
    private boolean onGround;
    private boolean isJumping;
    private boolean isBlockedToTheRight;
    private boolean isBlockedToTheLeft;
    private boolean isBlockedAbove;
    
    private boolean XAxisKeyPressed = false;
    
  

    public Hero(String imageFile, int initialX, int initialY, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection) {
        super(imageFile, initialX, initialY, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = false;
        isJumping = false;
        positionX = initialX;
        positionY = initialY;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
//        desiredPositionX = positionX;
//        desiredPositionY = positionY;
//        desiredVelocityX = velocityX;
//        desiredVelocityY = velocityY;
//System.out.println("CREATED       "+this);      
//        updatePositionX(initialX);
//        updatePositionY(initialY);
        
    }
    
    public Hero(String imageFile, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection) {
        super(imageFile, 0, 0, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = false;
        isJumping = false;
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
//        desiredPositionX = positionX;
//        desiredPositionY = positionY;
//        desiredVelocityX = velocityX;
//        desiredVelocityY = velocityY;
System.out.println("CREATED       "+this);
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
        
        float dt2 = (float) Math.pow(dt, 2);
        positionX = accelerationX * dt2 / 2 + velocityX * dt + positionX;
        positionY = accelerationY * dt2 / 2 + velocityY * dt + positionY;      
        
        imageView.setTranslateX(positionX);
        imageView.setTranslateY(positionY);
        
System.out.println("UPDATED       "+this);

        //animating hero
        //if(desiredVelocityX != 0){
        if(XAxisKeyPressed){
            viewportCounter =  (++viewportCounter) % (viewports.size());
            imageView.setViewport(viewports.get(viewportCounter));
        }
    }
    
    @Override
    public void handle(KeyEvent event) {
System.out.println("              isBlockedToTheRight="+isBlockedToTheRight);
System.out.println("              isBlockedToTheLeft="+isBlockedToTheLeft);

        if(event.getEventType() == KeyEvent.KEY_PRESSED ){
            
System.out.println("      XAxisKeyPressed="+XAxisKeyPressed);
            viewportCounter = ++viewportCounter % viewports.size();
            if(event.getCode() == KeyCode.LEFT  && !XAxisKeyPressed){
                XAxisKeyPressed = true;
                //velocityX = velocityX - speed;
                if(!isBlockedToTheLeft){
                    accelerationX -= dAccelerationX;
                }
                imageView.setScaleX(-1);//flips the image
                facingDirection = Direction.LEFT;
System.out.println("HAN LFT KEY PR"+this);
            } else if(event.getCode() == KeyCode.RIGHT  && !XAxisKeyPressed ){
                XAxisKeyPressed = true;
                //velocityX = velocityX + speed;
                if(!isBlockedToTheRight){
                    accelerationX += dAccelerationX;
                }
                imageView.setScaleX(1);//flips the image
                facingDirection = Direction.RIGHT;
System.out.println("HAN RGT KEY PR"+this);
            } else if(event.getCode() == KeyCode.UP){

                //facingDirection = Direction.UP;
                
System.out.println("isJumping?"+isJumping);
System.out.println("onGround?"+onGround);
                if(onGround){
                    accelerationY -= 4;
                    isJumping = true;
                }
System.out.println("HAND UP KEY PR"+this);
            } else if(event.getCode() == KeyCode.DOWN){
                facingDirection = Direction.DOWN;
                //velocityY = velocityY + speed;
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
            }
 
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED){
            
System.out.println("      XAxisKeyPressed="+XAxisKeyPressed);
            if(event.getCode() == KeyCode.LEFT ){
                XAxisKeyPressed = false;
                //velocityX = velocityX + speed;
                if( !isBlockedToTheLeft){
                    accelerationX += dAccelerationX;
                }
System.out.println("HAN LFT KEY RE"+this);
            } else if(event.getCode() == KeyCode.RIGHT ){
                XAxisKeyPressed = false;
                if(!isBlockedToTheRight){
                //velocityX = velocityX - speed;
                    accelerationX -= dAccelerationX;
                }
System.out.println("HAN RGT KEY RE"+this);
            }else if(event.getCode() == KeyCode.UP){
//System.out.println("jumping?"+onGround);
//                if(onGround){
//                    setAccelerationY(getAccelerationY() + GRAVITY);
//                }
//                //velocityY = velocityY + speed;
//                velocityY = 0;
            } else if(event.getCode() == KeyCode.DOWN){
                //velocityY = velocityY - speed;
                velocityY = 0;
            }
 

        }

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

    public boolean isIsJumping() {
        return isJumping;
    }  

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        isJumping = false;
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

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
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

    
    
    public void setPositionX(float translateX){
        imageView.setTranslateX(translateX);
        positionX = translateX;
        
    }
    
    public void setPositionY(float translateY){
        imageView.setTranslateY(translateY);
        positionY = translateY;
    }

    @Override
    public String toString() {
        return "Hero{" + "health=" + health + ", facingDirection=" + facingDirection + ", dAccelerationX=" + dAccelerationX + ", positionX=" + positionX + ", positionY=" + positionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", onGround=" + onGround + ", isJumping=" + isJumping + ", isBlockedToTheRight=" + isBlockedToTheRight + ", isBlockedToTheLeft=" + isBlockedToTheLeft + ", isBlockedAbove=" + isBlockedAbove + ", XAxisKeyPressed=" + XAxisKeyPressed + '}';
    }

    
    
    
    
    
    
}
