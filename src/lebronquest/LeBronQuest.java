/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.io.File;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author nicot
 */
public class LeBronQuest extends Application {
    public static final float GRAVITY = 3;
    
    private static final int FRAMES_PER_SECOND = 20;
    
    private static final String SPLASH_ICON = "img/icon.png";
    private static final String SPLASH_TITLE = "Lebron Quest";
    private static final String SPLASH_IMAGE = "img/lbj simple.png";
    private static final String SPLASH_BUTTON_TEXT = "Start Game";
    private static final String INTRO_MUSIC_FILE = "src/resources/bgm_17.mp3";//https://downloads.khinsider.com/game-soundtracks/album/i-live-in-a-different-world-android-game-music/bgm_12.mp3
    private static final int SPLASH_WIDTH = 720;
    private static final int SPLASH_HEIGHT = 550;
    private Stage splashWindow;
    
    
    private static final String GAME_ICON = "img/icon.png";
    private static final String GAME_TITLE = "Lebron Quest";
    private static final String GAME_MUSIC_FILE = "src/resources/bgm_12.mp3";
    private static final Color GAME_BACKGROUND_COLOR = Color.AQUA;
    public static final int GAME_WIDTH = 30 * TileType.TILE_WIDTH; //Tile size is 32x32
    public static final int GAME_HEIGHT = 22 * TileType.TILE_WIDTH; //Tile size is 32x32
    private Stage gameWindow;
    
    private Button soundButton;        
    private ImageView soundOffImageView;
    private ImageView soundOnImageView;
    
    public static final int MAX_HEALTH = 100; 
    
    private MediaPlayer mediaPlayer;
    
    private static boolean gameWon = false;
    private static boolean gameLost = false;
    
    private World world;
    
    private Group gameRoot;
    private Hero hero;
    private boolean soundIsPlaying;
    
    Tile tileBelowHero;
    Tile tileAboveHero;
    Tile tileToTheRightOfHero;
    Tile tileToTheLeftOfHero;
    Tile tileAboveToTheRightOfHero;
    Tile tileAboveToTheLeftOfHero;
    Tile tileBelowToTheRightOfHero;
    Tile tileBelowToTheLeftOfHero;
  

    @Override
    public void start(Stage primaryStage) {
        
        splashWindow = primaryStage;
        
        Scene splashScene = createSplashScene();
        
        playSound(INTRO_MUSIC_FILE);
        
        splashWindow.setTitle(SPLASH_TITLE);
        splashWindow.setScene(splashScene);
        splashWindow.getIcons().add(new Image(SPLASH_ICON));
        splashWindow.show();
    }
    
    private Scene createSplashScene(){
        
        Group splashRoot = new Group();
        Scene scene = new Scene(splashRoot, SPLASH_WIDTH, SPLASH_HEIGHT);     
        
        //Add Button
        Button startButton = new Button();
        startButton.setText(SPLASH_BUTTON_TEXT);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                splashWindow.hide();
                stopSound();
                
                gameWindow = new Stage();
                
                Scene gameScene = createGameScene();
        
                playSound(GAME_MUSIC_FILE);
                
                gameWindow.setTitle(GAME_TITLE);
                gameWindow.setScene(gameScene);
                gameWindow.getIcons().add(new Image(GAME_ICON));
                gameWindow.initModality(Modality.APPLICATION_MODAL);
                gameWindow.show();

                startGameLoop();
            }
        });
        splashRoot.getChildren().add(startButton);
        //Translate button
        /*
        How to obtain the width and height of the button? 
        using startButton.getWidth() now will show 0....
        https://stackoverflow.com/questions/26152642/get-the-height-of-a-node-in-javafx-generate-a-layout-pass      
        JavaFX layout calculations work by applying CSS and a layout pass. 
        Normally this occurs as part of a pulse (a kind of automated 60fps tick
        in the internal JavaFX system which checks for any dirty objects in the
        scene which need new css or layout applied to them).
        In most cases, you can just specify the changes you want to the scene 
        and let the automated pulse layout mechanism handle the layout at that 
        time; doing so is quite efficient as it means that any changes between
        a pulse are batched up and you don't need to manually trigger layout 
        passes. 
        However, when you need to actually get the size of things before the 
        layout occurs (as in your case), then you need to manually trigger the 
        CSS application and layout pass before you try to query the height and
        width extents of the node.
        */
        splashRoot.applyCss();
        splashRoot.layout();
        startButton.setTranslateX(SPLASH_WIDTH / 2 - startButton.getWidth() / 2);
        startButton.setTranslateY(SPLASH_HEIGHT - startButton.getHeight() - 5);
        
        //Add image
        ImageView splash = new ImageView(new Image(SPLASH_IMAGE));
        splashRoot.getChildren().add(splash);
        
        return scene;
    }
    
    private Scene createGameScene() {
        gameRoot = new Group();
        Scene scene = new Scene(gameRoot, GAME_WIDTH, GAME_HEIGHT, GAME_BACKGROUND_COLOR);
        scene.getStylesheets().add("resources/styles.css");
        
        world = new World(gameRoot);
        
        createSprites();
        
        scene.setOnKeyPressed(hero);
        scene.setOnKeyReleased(hero);
                
        return scene;
    }
    
    private void playSound(String musicFile){          
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        soundIsPlaying = true;
    }
    
    private void stopSound(){
        soundIsPlaying = false;
        mediaPlayer.pause();
        //mediaPlayer.stop();
    }
    
    private void startGameLoop() {
        //game loop
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);//runs forever
        timeline.setAutoReverse(false);//The Animation does not reverse direction on alternating cycles.
        Duration durationBetweenFrames = Duration.millis(1000 / FRAMES_PER_SECOND);// => 60 frames per second
        float dt = (float) durationBetweenFrames.toMillis() / 100;
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                
                updateForcesOnHero();           
        
                updateSprites(dt);

                handleCollisions();

                if (gameLost || gameWon) {
                    timeline.stop();
                    showFinalMessage();
                }
            }
        };

        KeyFrame keyframe = new KeyFrame(durationBetweenFrames, onFinished);

        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }
    
    private void createSprites() {
        
        

        //create hero
        ArrayList<Rectangle2D> heroViewPorts = new ArrayList<>();
        heroViewPorts.add(new Rectangle2D(0, 0, 24, 36));
        heroViewPorts.add(new Rectangle2D(47, 0, 21, 36));
        heroViewPorts.add(new Rectangle2D(93, 0, 19, 36));
        heroViewPorts.add(new Rectangle2D(135, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(185, 0, 20, 36));
        heroViewPorts.add(new Rectangle2D(232, 0, 19, 36));
        
        

        hero = new Hero("img/hero.png", true, heroViewPorts, MAX_HEALTH, Direction.RIGHT);
        gameRoot.applyCss();
        gameRoot.layout();
        hero.setPositionX(TileType.TILE_WIDTH / 2);
        hero.setPositionY(GAME_HEIGHT - 2 * TileType.TILE_HEIGHT - (int) hero.getImageView().getImage().getHeight());
        gameRoot.getChildren().add(hero.getImageView());//hero node added to scene graph
        
        //sound imageView
        soundButton = new Button();        
        soundOffImageView = new ImageView(new Image("img/soundOff.png"));
        soundOnImageView = new ImageView(new Image("img/soundOn.png"));
        soundButton.setGraphic(soundOffImageView);
        
        soundButton.setTranslateX(GAME_WIDTH - soundOnImageView.getBoundsInParent().getWidth() - 20);
        soundButton.setTranslateY(GAME_HEIGHT - soundOnImageView.getBoundsInParent().getHeight()- 20);              
        soundButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if(soundIsPlaying){
                    stopSound();                    
                    soundButton.setGraphic(soundOnImageView);
                } else {
                    playSound(GAME_MUSIC_FILE);
                    soundButton.setGraphic(soundOffImageView);
                }
            }
        });
        
        gameRoot.getChildren().add(soundButton);
        /*//create zombies

        ArrayList<Rectangle2D> zombieViewPorts = new ArrayList<>();
        zombieViewPorts.add(new Rectangle2D(0, 0, 23, 48));
        zombieViewPorts.add(new Rectangle2D(31, 0, 24, 48));
        zombieViewPorts.add(new Rectangle2D(63, 0, 24, 48));
        zombieViewPorts.add(new Rectangle2D(95, 0, 25, 48));
        zombieViewPorts.add(new Rectangle2D(129, 0, 24, 48));
        zombieViewPorts.add(new Rectangle2D(162, 0, 24, 48));
        zombieViewPorts.add(new Rectangle2D(194, 0, 23, 48));
        zombieViewPorts.add(new Rectangle2D(225, 0, 24, 48));

        for (int i = 0; i < NUM_ZOMBIES; i++) {
            Zombie zombie = new Zombie("opponent.png", WIDTH / 2 + random.nextInt((WIDTH / 2)),
                    HEIGHT / 2 + random.nextInt((HEIGHT / 2)), true, zombieViewPorts, ZOMBIE_SPEED);
            root.getChildren().add(zombie.getImageView());//zombie node added to scene graph
            zombieArray.add(zombie);
        }
        
        
        */

    }
 
    private void updateForcesOnHero() {
        tileBelowHero = world.getTileBelow( hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        tileAboveHero = world.getTileAbove( hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        tileToTheRightOfHero = world.getTileToTheRight( hero.getPositionX(), hero.getPositionY(),   hero.getWidth(), hero.getHeight());
        tileToTheLeftOfHero = world.getTileToTheLeft( hero.getPositionX(), hero.getPositionY(),  hero.getWidth(), hero.getHeight());
        tileAboveToTheRightOfHero = world.getTileAboveToTheRight( hero.getPositionX(), hero.getPositionY(),   hero.getWidth(), hero.getHeight());
        tileAboveToTheLeftOfHero = world.getTileAboveToTheLeft( hero.getPositionX(), hero.getPositionY(), hero.getWidth(), hero.getHeight());
        tileBelowToTheRightOfHero = world.getTileBelowToTheRight( hero.getPositionX(), hero.getPositionY(),  hero.getWidth(), hero.getHeight());
        tileBelowToTheLeftOfHero = world.getTileBelowToTheLeft( hero.getPositionX(), hero.getPositionY(),  hero.getWidth(), hero.getHeight());
System.out.println("----------------------");
        //vertically
        if(tileBelowHero != null && tileBelowHero.getType().isIsSolid() && tileBelowHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) {
 System.out.println("$$$$$$$$$$$$$$$$$$ BLOCK BELOW");
//System.out.println("%%%%%%%%tileBelow.isIsSolid()="+ tileBelowHero.isIsSolid());
            if(!hero.isOnGround()){
                System.out.println("$$$$$$$$$$$$$$$$$$ WAS A NEW BLOCK BELOW");
                hero.setOnGround(true);
                //hero.setAccelerationY(hero.getAccelerationY() - GRAVITY);
                hero.setAccelerationY(0);
                hero.setVelocityY(0);
                hero.setPositionY((float) (tileBelowHero.getImageView().getTranslateY() - hero.getHeight()));
            }
        } else {                    
 System.out.println("$$$$$$$$$$$$$$$$$$ NO BLOCK BELOW");
//System.out.println("%%%%%%%%tileBelow.isIsSolid()="+ tileBelowHero.isIsSolid());
            hero.setOnGround(false);
            //hero.setAccelerationY(hero.getAccelerationY() + GRAVITY);
            hero.setAccelerationY(GRAVITY);
        }
                
        //horizontally        
        if((tileToTheRightOfHero != null && tileToTheRightOfHero.getType().isIsSolid() && tileToTheRightOfHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) 
                || hero.getPositionX() > LeBronQuest.GAME_WIDTH ){//BLOCK TO  THE RIGHT
            hero.setIsBlockedToTheRight(true);
            System.out.println("$$$$$$$$$$$$$$$$$$ BLOCK TO  THE RIGHT");
            if( hero.getVelocityX() > 0){
                System.out.println("$$$$$$$$$$$$$$$$$$ COLLISION TO  THE RIGHT");
           // if(hero.getImageView().getBoundsInParent().intersects(imageViewToTheRightOfHero.getBoundsInParent())){
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            //}
            
            } /*else {
                System.out.println("$$$$$$$$$$$$$$$$$$ NO COLLISION TO  THE RIGHT");
                hero.updatePositionX(hero.getDesiredPositionX());
                hero.setVelocityX(hero.getDesiredVelocityX());
            }*/
        }  else { // NO  BLOCK TO  THE RIGHT
            System.out.println("$$$$$$$$$$$$$$$$$$ NO BLOCK TO  THE RIGHT ");
            hero.setIsBlockedToTheRight(false);
            //hero.updatePositionX(hero.getDesiredPositionX());
            //hero.setVelocityX(hero.getDesiredVelocityX());
        }
        
        if((tileToTheLeftOfHero != null && tileToTheLeftOfHero.getType().isIsSolid() && tileToTheLeftOfHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) 
                || hero.getPositionX() < 0 ){ //BLOCK TO  THE LEFT
            hero.setIsBlockedToTheLeft(true);
            System.out.println("$$$$$$$$$$$$$$$$$$ BLOCK TO  THE LEFT");
            //if( hero.getFacingDirection() == Direction.LEFT){
            if( hero.getVelocityX() < 0){
                System.out.println("$$$$$$$$$$$$$$$$$$ COLLISION TO  THE LEFT");
           // if(hero.getImageView().getBoundsInParent().intersects(imageViewToTheRightOfHero.getBoundsInParent())){
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            //}
            
            } /*else {
                System.out.println("$$$$$$$$$$$$$$$$$$ NO COLLISION TO  THE LEFT");
                hero.updatePositionX(hero.getDesiredPositionX());
                hero.setVelocityX(hero.getDesiredVelocityX());
            }*/
        } else { // NO  BLOCK TO  THE  LEFT
            System.out.println("$$$$$$$$$$$$$$$$$$ NO BLOCK TO  THE  LEFT");
            hero.setIsBlockedToTheLeft(false);
            //hero.updatePositionX(hero.getDesiredPositionX());
            //hero.setVelocityX(hero.getDesiredVelocityX());
        }
        
        if((tileAboveHero != null && tileAboveHero.getType().isIsSolid() && tileAboveHero.getImageView().getBoundsInParent().intersects(hero.getImageView().getBoundsInParent())) 
                 || hero.getPositionY() < 0 ){ //BLOCK TO  THE TOP
            hero.setIsBlockedAbove(true);
            System.out.println("$$$$$$$$$$$$$$$$$$ BLOCK ABOVE");
            //if( hero.getFacingDirection() == Direction.LEFT){
            if( hero.getVelocityY() < 0){
                System.out.println("$$$$$$$$$$$$$$$$$$ COLLISION ABOVE");
           // if(hero.getImageView().getBoundsInParent().intersects(imageViewToTheRightOfHero.getBoundsInParent())){
                hero.setVelocityY(0);
                //hero.setAccelerationY(0);
            //}
            
            } /*else {
                System.out.println("$$$$$$$$$$$$$$$$$$ NO COLLISION TO  THE LEFT");
                hero.updatePositionX(hero.getDesiredPositionX());
                hero.setVelocityX(hero.getDesiredVelocityX());
            }*/
        } else { // NO  BLOCK TO  THE  ABOVE
            System.out.println("$$$$$$$$$$$$$$$$$$ NO BLOCK ABOVE");
            hero.setIsBlockedAbove(false);
            //hero.updatePositionX(hero.getDesiredPositionX());
            //hero.setVelocityX(hero.getDesiredVelocityX());
        }
                
                
                
                
                

System.out.println("UPDATED_FORCES"+hero);
            }
    
    private void updateSprites(float dt) {
        //animate hero
        hero.update(dt);
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
System.out.println("$$$$$$$$$$$$$$$$$$ BLOCK TO  THE LEFT");
            if( hero.getFacingDirection() == Direction.WEST){
System.out.println("$$$$$$$$$$$$$$$$$$ COLLISION TO  THE LEFT");
           // if(hero.getImageView().getBoundsInParent().intersects(imageViewToTheRightOfHero.getBoundsInParent())){
                hero.setVelocityX(0);
                hero.setAccelerationX(0);
            //}
            
            } else {
System.out.println("$$$$$$$$$$$$$$$$$$ NO COLLISION TO  THE LEFT");
                hero.updatePositionX(hero.getDesiredPositionX());
                hero.setVelocityX(hero.getDesiredVelocityX());
            }
        } else {

System.out.println("$$$$$$$$$$$$$$$$$$ NO BLOCK TO  THE LEFT");
            hero.setIsBlockedToTheLeft(false);
            hero.updatePositionX(hero.getDesiredPositionX());
            hero.setVelocityX(hero.getDesiredVelocityX());
        }
        
        */
        
        
        
//        hero.updatePositionY(hero.getDesiredPositionY());
//               
//        hero.setVelocityY(hero.getDesiredVelocityY());
System.out.println("HANDLED COLLIS"+hero);
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
            System.out.println("You Win!");
        }
        */
    }

    
    private void showFinalMessage() {
        /*
        if (gameWon) {
            finalLabel = new Label("Game Over. \nYou Won!\n Your score: " + score);
        } else {
            finalLabel = new Label("Game Over. \nYou Lost!\n Your score: " + score);
        }
        finalLabel.setFont(Font.font("Verdana", 20));
        finalLabel.setTextFill(Color.RED);
        finalLabel.setTranslateX(WIDTH / 2 - finalLabel.getWidth() - 10);
        finalLabel.setTranslateY(HEIGHT / 2 - finalLabel.getHeight() - 10);
        root.getChildren().add(finalLabel);
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     
}
