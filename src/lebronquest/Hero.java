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
    private float desiredPositionX;
    private float desiredPositionY;    
    private float desiredVelocityX;
    private float desiredVelocityY;
    private boolean onGround;
    private boolean isJumping;
    private boolean isBlockedToTheRight;
    private boolean isBlockedToTheLeft;
    private boolean isBlockedAbove;
    
    private boolean keyReleased = true;
    
  

    public Hero(String imageFile, int initialX, int initialY, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection, boolean onGround) {
        super(imageFile, initialX, initialY, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = onGround;
        isJumping = false;
        positionX = initialX;
        positionY = initialY;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
        desiredPositionX = positionX;
        desiredPositionY = positionY;
        desiredVelocityX = velocityX;
        desiredVelocityY = velocityY;
System.out.println("CREATED       "+this);      
        updatePositionX(initialX);
        updatePositionY(initialY);
        
    }
    
    public Hero(String imageFile, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, int health, Direction facingDirection, boolean onGround) {
        super(imageFile, 0, 0, isVisible, viewportsCoords);
        this.health = health;
        this.facingDirection = facingDirection;
        this.onGround = onGround;
        isJumping = false;
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = LeBronQuest.GRAVITY;
        desiredPositionX = positionX;
        desiredPositionY = positionY;
        desiredVelocityX = velocityX;
        desiredVelocityY = velocityY;
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
        desiredVelocityX = accelerationX * dt + velocityX;
        desiredVelocityY = accelerationY * dt + velocityY;
        
        float dt2 = (float) Math.pow(dt, 2);
        desiredPositionX = accelerationX * dt2 / 2 + velocityX * dt + positionX;
        desiredPositionY = accelerationY * dt2 / 2 + velocityY * dt + positionY;      
        
System.out.println("UPDATED       "+this);

        //boundaries
        if(imageView.getTranslateX() < 0 ){
            desiredPositionX = 0;
            desiredVelocityX = 0;
        }
        if(imageView.getTranslateX() > LeBronQuest.GAME_WIDTH){
            desiredPositionX = LeBronQuest.GAME_WIDTH;
            desiredVelocityX = 0;
        }
        if(imageView.getTranslateY() < 0) {
            desiredPositionY = 0;
            desiredVelocityY = 0;
        }
        if(imageView.getTranslateY() > LeBronQuest.GAME_HEIGHT){
            desiredPositionY = LeBronQuest.GAME_HEIGHT;
            desiredVelocityY = 0;
        }
        
        //done by the game after checking for collision
        //imageView.setTranslateX(positionX);
        //imageView.setTranslateY(positionY);
        
        
        //animating hero
        if(desiredVelocityX != 0){
            viewportCounter =  (++viewportCounter) % (viewports.size());
            imageView.setViewport(viewports.get(viewportCounter));
        }
    }
    
    @Override
    public void handle(KeyEvent event) {
System.out.println("              isBlockedToTheRight="+isBlockedToTheRight);
        if(event.getEventType() == KeyEvent.KEY_PRESSED  && keyReleased){
            keyReleased = false;
            viewportCounter = ++viewportCounter % viewports.size();
            if(event.getCode() == KeyCode.LEFT){
                //velocityX = velocityX - speed;
                accelerationX -= dAccelerationX;
                imageView.setScaleX(-1);//flips the image
                facingDirection = Direction.WEST;
System.out.println("HANDLED KEY PR"+this);
            } else if(event.getCode() == KeyCode.RIGHT && !isBlockedToTheRight){
                //velocityX = velocityX + speed;
                accelerationX += dAccelerationX;
                imageView.setScaleX(1);//flips the image
                facingDirection = Direction.EAST;
            } else if(event.getCode() == KeyCode.UP){
                facingDirection = Direction.UP;
                
                
                isJumping = true;
System.out.println("jumping?"+onGround);
                if(onGround){
                    setAccelerationY(getAccelerationY() - GRAVITY);
                }
            } else if(event.getCode() == KeyCode.DOWN){
                facingDirection = Direction.DOWN;
                //velocityY = velocityY + speed;
            }else if(event.getCode() == KeyCode.SPACE) {
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
System.out.println("HANDLED KEY PR"+this);
 
        } else if(event.getEventType() == KeyEvent.KEY_RELEASED){
            keyReleased = true;
            if(event.getCode() == KeyCode.LEFT){
                //velocityX = velocityX + speed;
                accelerationX += dAccelerationX;
            } else if(event.getCode() == KeyCode.RIGHT  && !isBlockedToTheRight){
                //velocityX = velocityX - speed;
                accelerationX -= dAccelerationX;
            }else if(event.getCode() == KeyCode.UP){
System.out.println("jumping?"+onGround);
                if(onGround){
                    setAccelerationY(getAccelerationY() + GRAVITY);
                }
                //velocityY = velocityY + speed;
                velocityY = 0;
            } else if(event.getCode() == KeyCode.DOWN){
                //velocityY = velocityY - speed;
                velocityY = 0;
            }
 
System.out.println("HANDLED KEY RE"+this);
        }

    }
    
    
    public float getAccelerationX() {
        return accelerationX;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

    public float getDesiredPositionX() {
        return desiredPositionX;
    }

    public float getDesiredPositionY() {
        return desiredPositionY;
    }

    public float getDesiredVelocityX() {
        return desiredVelocityX;
    }

    public float getDesiredVelocityY() {
        return desiredVelocityY;
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

    
    
    public void updatePositionX(float translateX){
        imageView.setTranslateX(translateX);
        positionX = translateX;
        
    }
    
    public void updatePositionY(float translateY){
        imageView.setTranslateY(translateY);
        positionY = translateY;
    }

    @Override
    public String toString() {
        return "Hero{" + "health=" + health + ", facingDirection=" + facingDirection +  ", positionX=" + positionX + ", positionY=" + positionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + ", desiredPositionX=" + desiredPositionX + ", desiredPositionY=" + desiredPositionY + ", desiredVelocityX=" + desiredVelocityX + ", desiredVelocityY=" + desiredVelocityY + ", onGround=" + onGround + '}';
    }
    
    
    
    
    
}
