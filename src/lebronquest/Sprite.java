/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lebronquest;

import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author student
 */
public abstract class Sprite {
    protected String imageFile;
    protected Image image;
    protected ImageView imageView;
    protected float width;
    protected float height;
    //protected int dx; //next x-axis translation
    //protected int dy; //next y-axis translation
    protected boolean isVisible;
    
    protected int viewportCounter;
    protected ArrayList<Rectangle2D> viewports;
    
    public Sprite(String imageFile, int initialX, int initialY, boolean isVisible, ArrayList<Rectangle2D> viewportsCoords) {
        this.image = new Image(imageFile);
        this.isVisible = isVisible;
        //this.dx = dx;
        
        imageView = new ImageView(this.image);
        imageView.setCache(true);
        imageView.setSmooth(true);
        viewports = viewportsCoords;
        viewportCounter = 0;
        imageView.setViewport(viewports.get(viewportCounter));
        width = (float) this.getImageView().getBoundsInParent().getWidth();
        height = (float) this.getImageView().getBoundsInParent().getHeight();
        imageView.setTranslateX(initialX);//initial x
        imageView.setTranslateY(initialY);//initial y
    }
    
    public abstract void update(float dt);
    
    public Image getImage() {
        return image;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    
    public void setTranslateX(float translateX){
        imageView.setTranslateX(translateX);
    }
    
    public void setTranslateY(float translateY){
        imageView.setTranslateY(translateY);
    }

    @Override
    public String toString() {
        return "Sprite{" + "imageFile=" + imageFile + ", image=" + image + ", imageView=" + imageView +/* ", dx=" + dx + ", dy=" + dy + */", isVisible=" + isVisible +  ", viewportCounter=" + viewportCounter + ", viewports=" + viewports + '}';
    }
    
    
    
}
