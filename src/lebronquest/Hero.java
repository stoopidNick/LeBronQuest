package lebronquest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Hero extends Sprite implements EventHandler<KeyEvent> {

    private final static Logger LOGGER = Logger.getLogger(Hero.class.getName());
    private static final double HERO_D_ACCELERATION_X = 0.8f;
    private static final double HERO_D_ACCELERATION_Y = 50f;
    public static final int MAX_HEALTH = 100;
    public static final int INITIAL_POSITION_X = 28 * TileType.TILE_WIDTH / 2;
    public static final int INITIAL_POSITION_Y = GameWindow.SCENE_HEIGHT - 4 * TileType.TILE_HEIGHT;
    public static final Direction INITIAL_DIRECTION = Direction.RIGHT;
    public static final String IMAGE_FILENAME = "resources/img/hero.png";
    public static final String JUMP_SOUND_FILENAME = "resources/sounds/jump.mp3"; //http://soundbible.com/tags-jumping.html

    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean upKeyPressed;
    private boolean downKeyPressed;

    public Hero() {
        super(IMAGE_FILENAME, INITIAL_POSITION_X, INITIAL_POSITION_Y, true, MAX_HEALTH, INITIAL_DIRECTION, HERO_D_ACCELERATION_X, HERO_D_ACCELERATION_Y);

        leftKeyPressed = false;
        rightKeyPressed = false;
        upKeyPressed = false;
        downKeyPressed = false;
        imageView.setTranslateX(INITIAL_POSITION_X);
        imageView.setTranslateY(INITIAL_POSITION_Y);

        ArrayList<Rectangle2D> heroViewPorts = new ArrayList<>();
        heroViewPorts.add(new Rectangle2D(0, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(47, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(93, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(135, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(185, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(232, 0, 28, 36));

        setViewports(heroViewPorts);

        imageView.setViewport(viewports.get(viewportCounter));
        width = this.getImageView().getBoundsInParent().getWidth();
        height = this.getImageView().getBoundsInParent().getHeight();
        LOGGER.setLevel(Level.OFF);

    }

    @Override
    public void update(double dt) {
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
        double dt2 = Math.pow(dt, 2);
        positionX = accelerationX * dt2 / 2 + velocityX * dt + positionX;
        positionY = accelerationY * dt2 / 2 + velocityY * dt + positionY;

        //animating hero
        if (leftKeyPressed || rightKeyPressed) {
            viewportCounter = (++viewportCounter) % (viewports.size());
            imageView.setViewport(viewports.get(viewportCounter));
        }
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {

            if (event.getCode() == KeyCode.LEFT && !leftKeyPressed) {
                leftKeyPressed = true;
                if (!isBlockedToTheLeft) {
                    accelerationX -= dAccelerationX;
                }
                imageView.setScaleX(-1);//flips the image
                facingDirection = Direction.LEFT;
                LOGGER.info("HAN LFT KEY  PR" + this);
            } else if (event.getCode() == KeyCode.RIGHT && !rightKeyPressed) {
                rightKeyPressed = true;
                if (!isBlockedToTheRight) {
                    accelerationX += dAccelerationX;
                }
                imageView.setScaleX(1);//flips the image
                facingDirection = Direction.RIGHT;
                LOGGER.info("HAN RGT KEY  PR" + this);
            } else if (event.getCode() == KeyCode.UP && !upKeyPressed) {

                upKeyPressed = true;
                //facingDirection = Direction.UP;
                if (onGround) {
                    accelerationY -= dAccelerationY;
                    try {
                        //Get sound path from jar
                        String soundString = ClassLoader.getSystemClassLoader().getSystemResource(JUMP_SOUND_FILENAME).toURI().toString();

                        Media sound = new Media(soundString);
                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
                        mediaPlayer.play();
                    } catch (URISyntaxException ex) {
                        LOGGER.info("Error reading sound file");
                    }
                }
                LOGGER.info("HAND UP KEY PR" + this);
            } else if (event.getCode() == KeyCode.DOWN && !downKeyPressed) {
                downKeyPressed = true;
                facingDirection = Direction.DOWN;
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
                if (accelerationX < 0) {
                    accelerationX += dAccelerationX;
                }
                LOGGER.info("HAN LFT KEY  RE" + this);
            } else if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = false;
                if (accelerationX > 0) {
                    accelerationX -= dAccelerationX;
                }
                LOGGER.info("HAN RGT KEY  RE" + this);
            } else if (event.getCode() == KeyCode.UP) {
                upKeyPressed = false;
                LOGGER.info("HAN UP KEY  RE" + this);
            } else if (event.getCode() == KeyCode.DOWN) {
                downKeyPressed = false;
                LOGGER.info("HAN DO KEY  RE" + this);
            }

        }

    }

    @Override
    public String toString() {
        return "Hero{ " + super.toString() + ",leftKeyPressed=" + leftKeyPressed + ", rightKeyPressed=" + rightKeyPressed + ", upKeyPressed=" + upKeyPressed + ", downKeyPressed=" + downKeyPressed + '}';
    }

    void reset() {
        leftKeyPressed = false;
        rightKeyPressed = false;
        upKeyPressed = false;
        downKeyPressed = false;
        health = MAX_HEALTH;
        facingDirection = INITIAL_DIRECTION;
        previousPositionX = 0;
        previousPositionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = Sprite.GRAVITY;
        viewportCounter = 0;
        imageView.setViewport(viewports.get(viewportCounter));

        setPositionX(INITIAL_POSITION_X);
        imageView.setTranslateX(INITIAL_POSITION_X);
        setPositionY(INITIAL_POSITION_Y - (int) getHeight());
        imageView.setTranslateY(INITIAL_POSITION_Y - (int) getHeight());
    }

}
