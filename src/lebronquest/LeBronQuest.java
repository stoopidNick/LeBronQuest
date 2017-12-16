package lebronquest;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author nicot
 */
public class LeBronQuest extends Application {

    private static final String GAME_OVER_MUSIC_FILE = "src/resources/bgm_17.mp3";//https://downloads.khinsider.com/game-soundtracks/album/i-live-in-a-different-world-android-game-music/bgm_12.mp3
    private final static Logger LOGGER = Logger.getLogger(LeBronQuest.class.getName());
    public static final double GRAVITY = 3;
    private static final int FRAMES_PER_SECOND = 20;//20

    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private Timeline timeline;

    private GameWindow gameWindow;

    private boolean gameWon = false;
    private boolean gameLost = false;

    private World world;
    private Hero hero;

    private boolean soundIsPlaying;

    private boolean isXScrolling = false;
    private boolean isYScrolling = false;

    private int score = 0;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        SplashWindow splashWindow = new SplashWindow(this);
        splashWindow.show();
    }

    public void createGameWindow() {
        
        gameWindow = new GameWindow(this);        

        world = new World(gameWindow.getGameRoot());
        
        gameWindow.show();

        hero = gameWindow.createHero();

        gameWindow.setOnKeyPressed(hero);
        gameWindow.setOnKeyReleased(hero);

        startGameLoop();
    }

    public void playSound(String musicFile) {
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        soundIsPlaying = true;
    }

    public void stopSound() {
        soundIsPlaying = false;
        mediaPlayer.pause();
    }

    public void startGameLoop() {
        //game loop
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);//runs forever
        timeline.setAutoReverse(false);//The Animation does not reverse direction on alternating cycles.
        Duration durationBetweenFrames = Duration.millis(1000 / FRAMES_PER_SECOND);// => FRAMES_PER_SECOND frames per second
        double dt = durationBetweenFrames.toMillis() / 100;
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                updateIsScrolling();

                updateForcesOnHero();

                updateSprites(dt);

                handleCollisions();

                checkIfGameOver();
                
                gameWindow.updateHealthLabel(hero.getHealth());
                gameWindow.updateScoreLabel(score);
            }

        };

        KeyFrame keyframe = new KeyFrame(durationBetweenFrames, onFinished);

        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }

    private void updateIsScrolling() {
        if (hero.getPositionX() < (GameWindow.SCENE_WIDTH / 2) || hero.getPositionX() > (World.GAME_WIDTH - GameWindow.SCENE_WIDTH / 2)) {
            isXScrolling = false;
        } else {
            isXScrolling = true;
        }
        if (hero.getPositionY() < (GameWindow.SCENE_HEIGHT / 2) || hero.getPositionY() > (World.GAME_HEIGHT - GameWindow.SCENE_HEIGHT / 2)) {
            isYScrolling = false;
        } else {
            isYScrolling = true;
        }
    }

    private void updateForcesOnHero() {
        Tile tileBelowHero = world.getTileBelow(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileAboveHero = world.getTileAbove(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileToTheRightOfHero = world.getTileToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileToTheLeftOfHero = world.getTileToTheLeft(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileAboveToTheRightOfHero = world.getTileAboveToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileAboveToTheLeftOfHero = world.getTileAboveToTheLeft(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileBelowToTheRightOfHero = world.getTileBelowToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        Tile tileBelowToTheLeftOfHero = world.getTileBelowToTheLeft(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        //vertically        
        if (tileBelowHero != null && tileBelowHero.getType().isIsSolidTop() 
                && tileBelowHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) {//BLOCK BELOW
            LOGGER.info("$$$$$$$$$$$$$$$$$$ BLOCK BELOW");
            if (!hero.isOnGround()) {
                hero.setOnGround(true);
                hero.setAccelerationY(0);
                hero.setVelocityY(0);
                double deltaTranslateY = hero.getImageView().getTranslateY() - (tileBelowHero.getImageView().getTranslateY() - hero.getHeight() + 1);
                if (isYScrolling) {
                    world.translateY(deltaTranslateY);
                } else {
                    hero.setTranslateY(-hero.getImageView().getTranslateY() - deltaTranslateY);
                }
                hero.setPositionY(hero.getPositionY() - deltaTranslateY);
            }
        } else {//NO BLOCK BELOW
            hero.setOnGround(false);
            hero.setAccelerationY(GRAVITY);
        }

        //horizontally        
        if ((tileToTheRightOfHero != null && tileToTheRightOfHero.getType().isIsSolidLeft() 
                && tileToTheRightOfHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent()))
                || hero.getPositionX() > (world.GAME_WIDTH - hero.width)) {//BLOCK TO  THE RIGHT
            hero.setIsBlockedToTheRight(true);
            if (hero.getVelocityX() > 0) {//COLLISION TO THE RIGHT
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            }
        } else { // NO  BLOCK TO  THE RIGHT
            hero.setIsBlockedToTheRight(false);
        }

        if ((tileToTheLeftOfHero != null && tileToTheLeftOfHero.getType().isIsSolidRight() 
                && tileToTheLeftOfHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent()))
                || hero.getPositionX() < 0) { //BLOCK TO  THE LEFT
            hero.setIsBlockedToTheLeft(true);
            if (hero.getVelocityX() < 0) {//COLLISION TO THE LEFT
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            }
        } else { // NO  BLOCK TO  THE  LEFT
            hero.setIsBlockedToTheLeft(false);
        }

        if ((tileAboveHero != null && tileAboveHero.getType().isIsSolidBottom() 
                && tileAboveHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent()))
                || hero.getPositionY() < 0) { //BLOCK ABOVE
            hero.setIsBlockedAbove(true);
            if (hero.getVelocityY() < 0) {//COLLISION TO THE TOP
                hero.setVelocityY(0);
            }
        } else { // NO  BLOCK TO  THE  ABOVE
            hero.setIsBlockedAbove(false);
        }
        LOGGER.info("UPDATED_FORCES" + hero);
    }

    private void updateSprites(double dt) {
        //calculate  hero physics
        hero.update(dt);
        if (isXScrolling) {
            world.translateX(-hero.getDeltaPositionX());
        } else {
            hero.setTranslateX(hero.getImageView().getTranslateX() + hero.getDeltaPositionX());
        }
        if (isYScrolling) {
            world.translateY(-hero.getDeltaPositionY());
        } else {
            hero.setTranslateY(hero.getImageView().getTranslateY() + hero.getDeltaPositionY());
        }
        LOGGER.info("UPDATED       " + hero);

        /*
        //animate zombies
        for (Zombie zombie : zombieArray) {
            zombie.animate();
        }

        

        //animate arrows
        for (Arrow arrow : arrowArray) {
            arrow.animate();
        }
         */
    }

    private void handleCollisions() {
        /*ImageView imageViewBelowHero = world.getTileImageViewBelow(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewAboveHero = world.getTileImageViewAbove(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewToTheRightOfHero = world.getTileImageViewToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewToTheLeftOfHero = world.getTileImageViewToTheLeft(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewAboveToTheRightOfHero = world.getTileImageViewAboveToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewAboveToTheLeftOfHero = world.getTileImageViewAboveToTheLeft(hero.getPositionX(), hero.getPositionY(),  hero.getWidth(), hero.getHeight());
        ImageView imageViewBelowToTheRightOfHero = world.getTileImageViewBelowToTheRight(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        ImageView imageViewBelowToTheLeftOfHero = world.getTileImageViewBelowToTheLeft(hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
         */
//tiles and hero

//           hero.updatePositionX(hero.getDesiredPositionX());
//           hero.setVelocityX(hero.getDesiredVelocityX());
        //LEFT
        /*
        if((tileToTheLeftOfHero != null && tileToTheLeftOfHero.isIsSolid() ) || hero.getPositionX() < 0 ){
            hero.setIsBlockedToTheLeft(true);
LOGGER.info("$$$$$$$$$$$$$$$$$$ BLOCK TO  THE LEFT");
            if( hero.getFacingDirection() == Direction.WEST){
LOGGER.info("$$$$$$$$$$$$$$$$$$ COLLISION TO  THE LEFT");
           // if(hero.getImageView().getBoundsInParent().intersects(imageViewToTheRightOfHero.getBoundsInParent())){
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            //}
            
            } else {
LOGGER.info("$$$$$$$$$$$$$$$$$$ NO COLLISION TO  THE LEFT");
                hero.updatePositionX(hero.getDesiredPositionX());
                hero.setVelocityX(hero.getDesiredVelocityX());
            }
        } else {

LOGGER.info("$$$$$$$$$$$$$$$$$$ NO BLOCK TO  THE LEFT");
            hero.setIsBlockedToTheLeft(false);
            hero.updatePositionX(hero.getDesiredPositionX());
            hero.setVelocityX(hero.getDesiredVelocityX());
        }
        
         */
//        hero.updatePositionY(hero.getDesiredPositionY());
//               
//        hero.setVelocityY(hero.getDesiredVelocityY());
        LOGGER.info("HANDLED COLLIS" + hero);
        LOGGER.info("--------------------------------------------------------------------------------------------------------------------------");

        /*
        for (Zombie zombie : zombieArray) {
            //collisions zombie-hero
            if (zombie.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) {
                hero.setHealth(hero.getHealth() - 1);
                int numRedHearts = Math.abs(MAX_HEARTS * hero.getHealth() / MAX_HEALTH);
                for (int k = numRedHearts + 1; k <= MAX_HEARTS; k++) {
                    hearts.get(k - 1).setImage(new Image("grayHeart.png"));
                }
                if (hero.getHealth() <= 0) {
                    gameLost = true;
                    break;
                }
            }
            for (Arrow arrow : arrowArray) {
                if (arrow.getImageView().getBoundsInParent().intersects(zombie.getImageView().getBoundsInParent())) {
                    zombie.setIsDying(true);
                    arrow.setOutOfView(true);
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
        //remove dying zombies
        for (int i = 0; i < zombieArray.size(); i++) {
            if (zombieArray.get(i).isDying()) {
                root.getChildren().remove(zombieArray.get(i).getImageView());
                zombieArray.remove(zombieArray.get(i));
            }
        }
        //remove used arrows
        for (int j = 0; j < arrowArray.size(); j++) {
            if (arrowArray.get(j).isOutOfView()) {
                root.getChildren().remove(arrowArray.get(j).getImageView());
                arrowArray.remove(arrowArray.get(j));
            }
        }
        
        for (int k = 0; k < diamonds.size(); k++) {
            if (hero.getImageView().getBoundsInParent()
                    .intersects(diamonds.get(k).getBoundsInParent())) {
                root.getChildren().remove(diamonds.get(k));
                diamonds.remove(diamonds.get(k));
                k--;
                score += 5;
                scoreLabel.setText("Score: " + score);
            }
        }

        if (zombieArray.isEmpty()) {
            gameWon = true;
            LOGGER.info("You Win!");
        }
         */
    }

    private void checkIfGameOver() {
        if(hero.getHealth() <= 0 || hero.getPositionY() > World.GAME_WIDTH)
            gameLost = true;
        if (gameLost || gameWon) {
            timeline.stop();
            showFinalScene();
        }
    }

    private void showFinalScene() {
        if (isSoundIsPlaying()) {
            stopSound();
            playSound(GAME_OVER_MUSIC_FILE);
        }
        if (gameWon) {
            
            gameWindow.showFinalScene(true, score);
        } else {
            gameWindow.showFinalScene(false, score);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.setLevel(Level.OFF);
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean isSoundIsPlaying() {
        return soundIsPlaying;
    }

    void restart() {
        score = 0;
        gameLost = false;
        gameWon = false;
        hero.reset();
        world.reset();
        gameWindow.hideFinalScene();
        timeline.play();
    }

}
