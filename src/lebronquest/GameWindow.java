/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author transflorida
 */
public class GameWindow {
    private final static Logger LOGGER = Logger.getLogger(GameWindow.class.getName());
    
    private static final Color GAME_BACKGROUND_COLOR = Color.AQUA;
    public static final int SCENE_WIDTH = 30 * TileType.TILE_WIDTH; //Tile size is 32x32
    public static final int SCENE_HEIGHT = 22 * TileType.TILE_WIDTH; //Tile size is 32x32
    private static final String GAME_ICON = "resources/img/icon.png";
    private static final String GAME_TITLE = "Lebron Quest";
    private LeBronQuest application;
    private Group gameRoot;
    private Scene scene;
    private Button soundButton;
    private ImageView soundOffImageView;
    private ImageView soundOnImageView;
    private Label finalLabel;
    private Label healthLabel;    
    private Label scoreLabel;
    private Button restartButton;

    public GameWindow(LeBronQuest application) {
        this.application = application;
        gameRoot = new Group();
        LOGGER.setLevel(Level.OFF);
    }
    
    public void show(){
        
        application.getPrimaryStage().hide();
        application.stopSound();

        
        Stage gameWindow = new Stage();

        createGameScene();

        application.playSound(application.GAME_MUSIC_FILE);

        gameWindow.setTitle(GAME_TITLE);
        gameWindow.setScene(scene);
         //Get icon from jar
        InputStream iconStream = ClassLoader.getSystemClassLoader().getResourceAsStream(GAME_ICON);
        gameWindow.getIcons().add(new Image(iconStream));
        gameWindow.initModality(Modality.APPLICATION_MODAL);
        gameWindow.show();

        
    }
    
    private void createGameScene() {
        
        scene = new Scene(gameRoot, SCENE_WIDTH, SCENE_HEIGHT, GAME_BACKGROUND_COLOR);
        scene.getStylesheets().add("resources/styles.css");

        createSoundButtons();
        
        createTopPanel();
        
        createFinalComponents();

    }

    public Group getGameRoot() {
        return gameRoot;
    }

    void setOnKeyPressed(Hero hero) {
        scene.setOnKeyPressed(hero);
    }

    void setOnKeyReleased(Hero hero) {
        scene.setOnKeyReleased(hero);
    }
    
    public void createSoundButtons() {       

        //sound imageView
        soundButton = new Button();
        soundOffImageView = new ImageView(new Image("img/soundOff.png"));
        soundOnImageView = new ImageView(new Image("img/soundOn.png"));
        soundButton.setGraphic(soundOffImageView);
        soundButton.toFront();

        soundButton.setTranslateX(SCENE_WIDTH - soundOnImageView.getBoundsInParent().getWidth() - 20);
        soundButton.setTranslateY(SCENE_HEIGHT - soundOnImageView.getBoundsInParent().getHeight() - 20);
        soundButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (application.isSoundIsPlaying()) {
                    application.stopSound();
                    soundButton.setGraphic(soundOnImageView);
                } else {
                    if(application.isGameLost() || application.isGameWon()){
                        application.playSound(application.GAME_OVER_MUSIC_FILE);
                    } else {
                        application.playSound(application.GAME_MUSIC_FILE);
                    }
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

    Hero createHero() {
        //create hero
        

        Hero hero = new Hero();
        gameRoot.applyCss();
        gameRoot.layout();
        hero.setPositionY(hero.getPositionY() - (int) hero.getHeight());
        hero.setTranslateY(hero.getImageView().getTranslateY() - (int) hero.getHeight());
        LOGGER.info("CREATED       " + hero);
        gameRoot.getChildren().add(hero.getImageView());//hero node added to scene graph
        return hero;
        
    }

    void showFinalScene(boolean gameWon, int score) {
        if (gameWon) {
            finalLabel.setText("Game Over. \nYou Won! ");
        } else {
            finalLabel.setText("Game Over!");
        }
        gameRoot.applyCss();
        gameRoot.layout();
        
        finalLabel.setTranslateX(SCENE_WIDTH / 2 - finalLabel.getBoundsInParent().getWidth()/ 2);
        finalLabel.setTranslateY(SCENE_HEIGHT / 2 - finalLabel.getBoundsInParent().getHeight() / 2 );
        restartButton.setTranslateX(SCENE_WIDTH / 2 - restartButton.getBoundsInParent().getWidth() / 2 );
        restartButton.setTranslateY(SCENE_HEIGHT / 2 - restartButton.getBoundsInParent().getHeight()/ 2 + 100);
        
        finalLabel.setVisible(true);
        finalLabel.toFront();
        restartButton.setVisible(true);
        restartButton.toFront();
    }
    
    void hideFinalScene() {
        
        finalLabel.setVisible(false);
        restartButton.setVisible(false);
        finalLabel.toBack();
        restartButton.toBack();
    }
    
    void createFinalComponents(){

        finalLabel = new Label();
        
        //css
        finalLabel.setId("gameOver-text");
        gameRoot.getChildren().add(finalLabel);
        
        finalLabel.setVisible(false);
        
        restartButton = new Button("Play again");
        restartButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                LOGGER.info("**************************************RESTART*****************************************");
                if (application.isSoundIsPlaying()) {
                    application.stopSound();
                    application.playSound(application.GAME_MUSIC_FILE);
                } 
                application.restart();
                hideFinalScene();
            }
        });

        gameRoot.getChildren().add(restartButton);
        restartButton.setVisible(false);
    }

    void createTopPanel(){

        healthLabel = new Label("Health: " + Hero.MAX_HEALTH);        
        //css
        healthLabel.setId("health-text");
        healthLabel.setTranslateX(10);
        healthLabel.setTranslateY(10);    
        healthLabel.setVisible(true);    
        healthLabel.toFront();
        gameRoot.getChildren().add(healthLabel);    
        
        
        scoreLabel = new Label("Score: " + 0);        
        //css
        scoreLabel.setId("score-text");
        scoreLabel.setTranslateY(10);    
        scoreLabel.setVisible(true);    
        scoreLabel.toFront();
        gameRoot.getChildren().add(scoreLabel); 
       
        gameRoot.applyCss();
        gameRoot.layout();
        
        
        scoreLabel.setTranslateX(SCENE_WIDTH  - scoreLabel.getWidth() - 30);
        
    }
    
    public void updateHealthLabel(int health){
        healthLabel.setText("Health: " + health);
    }

    public void updateScoreLabel(int score){
        scoreLabel.setText("Score: " + score);
    }
}
