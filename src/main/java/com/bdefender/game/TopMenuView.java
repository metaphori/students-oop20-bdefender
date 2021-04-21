package com.bdefender.game;

import java.io.IOException;
import java.net.URL;

import com.bdefender.AppView;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import com.bdefender.component.ImageButton;

public class TopMenuView extends AnchorPane {

    /**
     * Menu bar height in pixel.
     */
    public static final int HEIGHT = 40;
    private static final int BUTTON_SIZE = 30;
    private final ImageButton playButton;
    private final ImageButton exitButton;
    private final ImageButton shopButton;
    private final ProgressBar lifeBar;
    private final Text lifeText;


    public TopMenuView() {
        final Rectangle rec = new Rectangle();
        rec.setX(0);
        rec.setY(0);
        rec.setWidth(AppView.DEFAULT_WIDTH);
        rec.setHeight(HEIGHT);
        rec.setFill(Color.rgb(107, 86, 60));
        this.getChildren().add(rec);
        this.playButton = new ImageButton(this.loadImage(ClassLoader.getSystemResource("game/play.png")));
        this.playButton.setDisabledImage(this.loadImage(ClassLoader.getSystemResource("game/play-grey.png")));
        this.exitButton = new ImageButton(this.loadImage(ClassLoader.getSystemResource("game/back-menu.png")));
        this.shopButton = new ImageButton(this.loadImage(ClassLoader.getSystemResource("game/shopping-cart.png")));
        this.shopButton.setDisabledImage(this.loadImage(ClassLoader.getSystemResource("game/shopping-cart-grey.png")));
        //life bar
        this.lifeBar = new ProgressBar(1);
        this.lifeText = new Text("Punti vita:   ");
        this.lifeText.setFont(Font.font("MV Boli", 18));
        this.lifeText.setFill(Color.WHITE);

        this.positionElement();
    }

    private void positionElement() {
        // play button positioning
        this.playButton.setWidth(BUTTON_SIZE);
        this.playButton.setHeight(BUTTON_SIZE);
        this.playButton.setX(30);
        this.playButton.setY(20);
        // exit button positioning
        this.exitButton.setWidth(BUTTON_SIZE);
        this.exitButton.setHeight(BUTTON_SIZE);
        this.exitButton.setX(1200);
        this.exitButton.setY(20);
        // shop button positioning
        this.shopButton.setWidth(BUTTON_SIZE);
        this.shopButton.setHeight(BUTTON_SIZE);
        this.shopButton.setX(85);
        this.shopButton.setY(20);
        //life Progress Bar
        final HBox lifeIndicatorHBox = new HBox();
        lifeIndicatorHBox.getChildren().addAll(lifeText, this.lifeBar);
        lifeIndicatorHBox.setLayoutX(620);
        lifeIndicatorHBox.setLayoutY(15);

        // add all element to AnchorPane
        this.getChildren().addAll(this.playButton, this.exitButton, this.shopButton, lifeIndicatorHBox);
    }

    private Image loadImage(final URL imageFile) {
        Image image;
        try {
            image = new Image(imageFile.openStream());
        } catch (IOException e) {
            image = null;
        }
        return image;
    }

    /**
     * @return play button
     */
    public ImageButton getPlayButton() {
        return this.playButton;
    }

    /**
     * @return exit button
     */
    public ImageButton getExitButton() {
        return this.exitButton;
    }

    /**
     * @return shop button
     */
    public ImageButton getShopButton() {
        return this.shopButton;
    }

    /**
     * @return life Progress Bar
     */
    public ProgressBar getLifeProgressBar() {
        return this.lifeBar;
    }

    /**
     * set the progress bar indicator
     * @param lifeValue
     */
    public void setLifeProgressBarValue(final Double lifeValue) {
        this.lifeBar.setProgress(lifeValue);
    }
}
