/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author transflorida
 */
public class SplashWindow implements EventHandler<KeyEvent> {
    
    private static final int SPLASH_WIDTH = 720;
    private static final int SPLASH_HEIGHT = 550;    
    private static final String SPLASH_BUTTON_TEXT = "Start Game";    
    private static final String SPLASH_IMAGE = "img/lbj simple.png";
    private static final String SPLASH_ICON = "img/icon.png";
    private static final String SPLASH_TITLE = "Lebron Quest";
    private static final String INTRO_MUSIC_FILE = "src/resources/bgm_17.mp3";//https://downloads.khinsider.com/game-soundtracks/album/i-live-in-a-different-world-android-game-music/bgm_12.mp3
    
    private LeBronQuest application;
    

    public SplashWindow(LeBronQuest application) {
        this.application = application;
    }
    
    public void show(){
        Scene splashScene = createSplashScene();
        
        application.playSound(INTRO_MUSIC_FILE);
        
        application.getPrimaryStage().setTitle(SPLASH_TITLE);
        application.getPrimaryStage().setScene(splashScene);
        application.getPrimaryStage().getIcons().add(new Image(SPLASH_ICON));
        application.getPrimaryStage().show();
    }
    
    private Scene createSplashScene() {
        Group splashRoot = new Group();        
        Scene scene = new Scene(splashRoot, SPLASH_WIDTH, SPLASH_HEIGHT); 

        //Add Button
        Button startButton = new Button();
        startButton.setText(SPLASH_BUTTON_TEXT);
        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                application.createGameWindow();
                
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
        
        scene.setOnKeyPressed(this);
        scene.setOnKeyReleased(this);

        return scene;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            application.createGameWindow();
        }
    }
}
