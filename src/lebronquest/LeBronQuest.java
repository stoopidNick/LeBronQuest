/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nicot
 */
public class LeBronQuest extends Application {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 550;

    @Override
    public void start(Stage primaryStage) {
        
        
        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello Nico!");
            }
        });
        btn.setTranslateX(WIDTH/2);
        btn.setTranslateY(HEIGHT - 40);
        
        ImageView splash = new ImageView(new Image("lbj simple.png"));
        
        
        
        Group root = new Group();
        
        root.getChildren().add(splash);
        root.getChildren().add(btn);
        
        
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        primaryStage.setTitle("Lebron Quest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
