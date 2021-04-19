package com.bdefender.game;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import com.bdefender.component.ImageButton;
import com.bdefender.map.MapView;
import com.bdefender.shop.ShopManager;

public class GameView extends AnchorPane {

    private final TopMenuView topMenuView;
    private final Parent shopView;
    private ImageButton btnShop;
    private ImageButton btnExit;
    private ImageButton btnPlay;
    

    public GameView(final MapView mapView, final Parent shopView) {
        this.topMenuView = new TopMenuView();
        this.shopView = shopView;
        mapView.setLayoutY(TopMenuView.HEIGHT);
        this.shopView.setLayoutY(TopMenuView.HEIGHT);
        this.btnShop = this.topMenuView.getShopButton();
        this.btnExit = this.topMenuView.getExitButton();
        this.btnPlay = this.topMenuView.getPlayButton();
        this.getChildren().addAll(this.topMenuView, mapView, this.shopView);
    }

    /**
     * @return top menu view
     */
    public TopMenuView getTopMenuView() {
        return this.topMenuView;
    }

   /**
    * Set Action on the top bar button, to Open Shop, Start the match and go back to the menu.
    * @param openShop action we want to associate to the button Shop
    * @param backMenu action we want to associate to the button BackMenu
    */
    public final void setActionTopM(final EventHandler<MouseEvent> openShop, final EventHandler<MouseEvent> backMenu) {
        btnShop.setOnMouseClick(openShop);
        btnExit.setOnMouseClick(backMenu);
    }
    
    /**
     * @param flag true if we need to set all the button Off false if we need to set all the buttons on.
     * */

    public final void setAllButtonDisable(final boolean flag) {
       this.btnShop.setDisable(flag);
       this.btnExit.setDisable(flag);
       this.btnPlay.setDisable(flag);
    }
}
