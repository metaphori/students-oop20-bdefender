package com.bdefender.map;

import java.util.stream.Collectors;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import com.bdefender.tower.view.TowerImageLoader;
import com.bdefender.component.ImageButton;
import com.bdefender.event.TowerEvent;
import com.bdefender.shop.TowerPlacementView;
import com.bdefender.event.EventHandler;

public class MapView extends AnchorPane {

    /**
     * Default map width.
     */
    public static final int MAP_WIDTH = 1280;
    /**
     * Default map height.
     */
    public static final int MAP_HEIGHT = 736;

    private static final int TOWER_WIDTH = 60;
    private static final int TOWER_HEIGHT = 60;
    private static final int TOWER_LABEL_SIZE = 60;
    private static final int TOWER_LABEL_TEXT_SIZE = 35;
    private final Map map;
    private final AnchorPane towersPane = new AnchorPane();
    private final AnchorPane enemiesPane = new AnchorPane();
    private final TowerPlacementView towerPlaceView;
    private EventHandler<TowerEvent> onTowerClick;

    public MapView(final Map map) {
        this.map = map;
        this.getChildren().addAll(new ImageView(this.map.getMapImage()), this.enemiesPane, this.towersPane);
        this.towerPlaceView = new TowerPlacementView(this.map.getTowerBoxes());
    }

    /**
     * Show or hide tower placement view.
     * @param visible
     */
    public void setTowerPlacementViewVisible(final boolean visible) {
        if (visible) {
            if (!this.getChildren().contains(this.towerPlaceView)) {
                this.getChildren().add(this.towerPlaceView);
            }
            this.towerPlaceView.reload(map.getEmptyTowerBoxes());
        } else {
            if (this.getChildren().contains(this.towerPlaceView)) {
                this.getChildren().remove(this.towerPlaceView);
            }
        }
    }

    /**
     * @return tower placement view
     */
    public TowerPlacementView getTowerPlacementView() {
        return this.towerPlaceView;
    }

    /**
     * Reload all the towers in the view to display new once.
     */
    public void reloadTowersView() {
        this.towersPane.getChildren().clear();
        this.towersPane.getChildren().addAll(this.map.getTowerBoxes().stream().filter(el -> el.getTower().isPresent()).map(el -> {
            final ImageButton tmp = new ImageButton(TowerImageLoader.getTowerImage(el.getTower().get()).get());
            tmp.setX(el.getCentralCoordinate().getCenterPixelX());
            tmp.setY(el.getCentralCoordinate().getCenterPixelY());
            tmp.setWidth(TOWER_WIDTH);
            tmp.setHeight(TOWER_HEIGHT);
            tmp.getLabel().setMinWidth(Region.USE_PREF_SIZE);
            tmp.getLabel().setMinHeight(TOWER_LABEL_SIZE);
            tmp.getLabel().setTextAlignment(TextAlignment.CENTER);
            tmp.setOnMouseClick(event -> {
                this.onTowerClick.handle(new TowerEvent(TowerEvent.TOWER_CLICKED, el.getTower().get()));
            });
            tmp.getLabel().setFont(Font.font(TOWER_LABEL_TEXT_SIZE));
            tmp.setOnMouseEntered(event -> {
                tmp.getLabel().setText(Integer.toString(el.getTower().get().getLevel()));
            });
            tmp.setOnMouseExited(event -> {
                tmp.getLabel().setText("");
            });
            return tmp;
        }).collect(Collectors.toList()));
    }

    /**
     * @return AnchorPane where enemies are rendered
     */
    public AnchorPane getEnemiesPane() {
        return this.enemiesPane;
    }

    /**
     * @return AnchorPane where towers are rendered
     */
    public AnchorPane getTowersPane() {
        return this.towersPane;
    }

    /**
     * Set handler to call when tower is clicked.
     * @param handler
     */
    public void setOnTowerClick(final EventHandler<TowerEvent> handler) {
        this.onTowerClick = handler;
    }

    /**
     * @return handler called when tower is clicked
     */
    public EventHandler<TowerEvent> getOnTowerClick() {
        return this.onTowerClick;
    }
}
