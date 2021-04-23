package com.bdefender.game;

import com.bdefender.Pair;
import com.bdefender.enemy.pool.EnemiesPoolInteractor;
import com.bdefender.map.Coordinates;
import com.bdefender.tower.Tower;
import com.bdefender.tower.TowerFactory;
import com.bdefender.tower.view.TowerView;

import java.util.HashMap;
import java.util.Map;

public class TowersControllerImpl implements TowersController {

    private final Map<Tower, TowerData> towersData = new HashMap<>();
    private final TowerFactory factory = new TowerFactory();
    private final EnemiesPoolInteractor pool;
    private final TowerViewImplementation towerViewImplementation;

    public TowersControllerImpl(TowerViewImplementation viewImplementation, EnemiesPoolInteractor enemyPool) {
        this.towerViewImplementation = viewImplementation;
        this.pool = enemyPool;
    }

    private Tower getTowerByTypeName(TowerName name, Coordinates pos) {
        switch (name) {
            case FIRE_BALL:
                return factory.getTowerDirect3(this.pool, pos);
            case FIRE_ARROW:
                return factory.getTowerDirect1(this.pool, pos);
            case THUNDERBOLT:
                return factory.getTowerDirect2(this.pool, pos);
        }
        return null;
    }

    @Override
    public Tower addTower(TowerName name, Coordinates pos) {
        Tower tower = getTowerByTypeName(name, pos);
        TowerView view = towerViewImplementation.getView(tower);
        TowerThread thread = new TowerThread(tower, view);
        towersData.put(tower, new TowerData(view, thread));
        thread.start();
        return tower;
    }

    @Override
    public void removeTower(Tower tower) {
        this.towersData.get(tower).getThread().killTower();
        this.towersData.remove(tower);
    }

    @Override
    public Integer upgradeTower(Tower tower) {
        return tower.upgradeLevel();
    }

    @FunctionalInterface
    public interface TowerViewImplementation {
        TowerView getView(Tower tower);
    }
}

class TowerData {
    private final TowerView towerView;
    private final TowerThread thread;

    public TowerData(TowerView view, TowerThread thread) {
        this.towerView = view;
        this.thread = thread;
    }

    public TowerView getView() {
        return this.towerView;
    }

    public TowerThread getThread() {
        return this.thread;
    }

}

class TowerThread extends Thread {
    private final TowerView view;
    private final Tower tower;
    private boolean alive = true;

    public TowerThread(Tower tower, TowerView view) {
        this.view = view;
        this.tower = tower;
    }

    public void killTower() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (alive) {
            try {
                sleep(10000L / tower.getShootSpeed());
                Pair<Double, Double> shootTargetPos;
                synchronized (this) {
                    shootTargetPos = tower.shoot();
                }
                if (shootTargetPos != null) {
                    view.startShootAnimation(new Pair<>(shootTargetPos.getX(), shootTargetPos.getY()));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
