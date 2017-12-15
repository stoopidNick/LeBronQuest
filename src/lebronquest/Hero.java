package lebronquest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Hero extends Sprite implements EventHandler<KeyEvent> {

    private final static Logger LOGGER = Logger.getLogger(Hero.class.getName());
    private static final float HERO_D_ACCELERATION_X = 0.8f;
    private static final float HERO_D_ACCELERATION_Y = 50f;    
    public static final int MAX_HEALTH = 100;

    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean upKeyPressed;
    private boolean downKeyPressed;

    public Hero(String imageFile, int initialX, int initialY, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, Direction facingDirection) {
        super(imageFile, initialX, initialY, isVisible, viewportsCoords, MAX_HEALTH, facingDirection, HERO_D_ACCELERATION_X, HERO_D_ACCELERATION_Y);
        LOGGER.setLevel(Level.INFO);
        leftKeyPressed = false;
        rightKeyPressed = false;
        upKeyPressed = false;
        downKeyPressed = false;
    }

    public Hero(String imageFile, boolean isVisible,
            ArrayList<Rectangle2D> viewportsCoords, Direction facingDirection) {
        this(imageFile, 0, 0, isVisible, viewportsCoords, facingDirection);
    }
    
    @Override
    public void update(float dt) {
        if (onGround) {
            if (Math.abs(accelerationX - 0) < 0.01) {
                accelerationX = 0;
            }
            if (Math.abs(velocityX - 0) < 0.15) {
                velocityX = 0;
            } else { //add friction
                if (velocityX > 0) { //add friction
                    velocityX -= 0.2;
                } else if (velocityX < 0) { //add friction
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
        LOGGER.info("*****************new Hero Position Y=" + positionY);

        //animating hero
        //if(desiredVelocityX != 0){
        if (leftKeyPressed || rightKeyPressed) {
            viewportCounter = (++viewportCounter) % (viewports.size());
            imageView.setViewport(viewports.get(viewportCounter));
        }
    }

    @Override
    public void handle(KeyEvent event) {
        LOGGER.info("              isBlockedToTheRight=" + isBlockedToTheRight);
        LOGGER.info("              isBlockedToTheLeft=" + isBlockedToTheLeft);

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {

            //viewportCounter = ++viewportCounter % viewports.size();
            if (event.getCode() == KeyCode.LEFT && !leftKeyPressed) {
                leftKeyPressed = true;
                LOGGER.info("      leftKeyPressed=" + leftKeyPressed);
                //velocityX = velocityX - speed;
                if (!isBlockedToTheLeft) {
                    accelerationX -= dAccelerationX;
                }
                imageView.setScaleX(-1);//flips the image
                facingDirection = Direction.LEFT;
                LOGGER.info("HAN LFT KEY  PR" + this);
            } else if (event.getCode() == KeyCode.RIGHT && !rightKeyPressed) {
                rightKeyPressed = true;
                LOGGER.info("      rightKeyPressed=" + rightKeyPressed);
                //velocityX = velocityX + speed;
                if (!isBlockedToTheRight) {
                    accelerationX += dAccelerationX;
                }
                imageView.setScaleX(1);//flips the image
                facingDirection = Direction.RIGHT;
                LOGGER.info("HAN RGT KEY  PR" + this);
            } else if (event.getCode() == KeyCode.UP && !upKeyPressed) {
                upKeyPressed = true;
                //facingDirection = Direction.UP;

                LOGGER.info("onGround?" + onGround);
                if (onGround) {
                    accelerationY -= dAccelerationY;
                }
                LOGGER.info("HAND UP KEY PR" + this);
            } else if (event.getCode() == KeyCode.DOWN && !downKeyPressed) {
                downKeyPressed = true;
                facingDirection = Direction.DOWN;
                //velocityY = velocityY + speed;
                LOGGER.info("HAND DO KEY PR" + this);
            } else if (event.getCode() == KeyCode.SPACE) {
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
                LOGGER.info("HAND SP KEY PR" + this);
            }

        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {

            if (event.getCode() == KeyCode.LEFT) {
                leftKeyPressed = false;
                LOGGER.info("      leftKeyPressed=" + leftKeyPressed);
                //velocityX = velocityX + speed;
                if (accelerationX < 0) {
                    accelerationX += dAccelerationX;
                }
                LOGGER.info("HAN LFT KEY  RE" + this);
            } else if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = false;
                LOGGER.info("      rightKeyPressed=" + rightKeyPressed);
                if (accelerationX > 0) {
                    //velocityX = velocityX - speed;
                    accelerationX -= dAccelerationX;
                }
                LOGGER.info("HAN RGT KEY  RE" + this);
            } else if (event.getCode() == KeyCode.UP) {
                upKeyPressed = false;
//LOGGER.info("jumping?"+onGround);
//                if(onGround){
//                    setAccelerationY(getAccelerationY() + GRAVITY);
//                }
//                //velocityY = velocityY + speed;
//                velocityY = 0;
                LOGGER.info("HAN UP KEY  RE" + this);
            } else if (event.getCode() == KeyCode.DOWN) {
                downKeyPressed = false;
                //velocityY = velocityY - speed;
                //velocityY = 0;
                LOGGER.info("HAN DO KEY  RE" + this);
            }

        }

    }

    @Override
    public String toString() {
        return "Hero{ " + super.toString()+ ",leftKeyPressed=" + leftKeyPressed + ", rightKeyPressed=" + rightKeyPressed + ", upKeyPressed=" + upKeyPressed + ", downKeyPressed=" + downKeyPressed + '}';
    }
    
}
