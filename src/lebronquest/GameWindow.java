/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
    private static final String GAME_ICON = "img/icon.png";
    private static final String GAME_TITLE = "Lebron Quest";
    private static final String GAME_MUSIC_FILE = "src/resources/bgm_12.mp3";
    private LeBronQuest application;
    private Group gameRoot;
    private Scene scene;
    private Button soundButton;
    private ImageView soundOffImageView;
    private ImageView soundOnImageView;

    public GameWindow(LeBronQuest application) {
        this.application = application;
    }
    
    public void show(){
        
        application.getPrimaryStage().hide();
        application.stopSound();

        
        Stage gameWindow = new Stage();

        createGameScene();

        application.playSound(GAME_MUSIC_FILE);

        gameWindow.setTitle(GAME_TITLE);
        gameWindow.setScene(scene);
        gameWindow.getIcons().add(new Image(GAME_ICON));
        gameWindow.initModality(Modality.APPLICATION_MODAL);
        gameWindow.show();

        
    }
    
    private void createGameScene() {
        gameRoot = new Group();
        scene = new Scene(gameRoot, SCENE_WIDTH, SCENE_HEIGHT, GAME_BACKGROUND_COLOR);
        scene.getStylesheets().add("resources/styles.css");


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

        soundButton.setTranslateX(SCENE_WIDTH - soundOnImageView.getBoundsInParent().getWidth() - 20);
        soundButton.setTranslateY(SCENE_HEIGHT - soundOnImageView.getBoundsInParent().getHeight() - 20);
        soundButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (application.isSoundIsPlaying()) {
                    application.stopSound();
                    soundButton.setGraphic(soundOnImageView);
                } else {
                    application.playSound(GAME_MUSIC_FILE);
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
        ArrayList<Rectangle2D> heroViewPorts = new ArrayList<>();
        heroViewPorts.add(new Rectangle2D(0, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(47, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(93, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(135, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(185, 0, 28, 36));
        heroViewPorts.add(new Rectangle2D(232, 0, 28, 36));

        Hero hero = new Hero("img/hero.png", true, heroViewPorts, Direction.RIGHT);
        gameRoot.applyCss();
        gameRoot.layout();
        hero.setPositionX(28 * TileType.TILE_WIDTH / 2);
        hero.setTranslateX(28 * TileType.TILE_WIDTH / 2);
        hero.setPositionY(SCENE_HEIGHT - 4 * TileType.TILE_HEIGHT - (int) hero.getHeight());
        hero.setTranslateY(SCENE_HEIGHT - 4 * TileType.TILE_HEIGHT - (int) hero.getHeight());
        LOGGER.info("CREATED       " + hero);
        gameRoot.getChildren().add(hero.getImageView());//hero node added to scene graph
        return hero;
        
    }
    
}
