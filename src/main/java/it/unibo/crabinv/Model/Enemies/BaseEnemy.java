package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.Enemy;
import it.unibo.crabinv.Model.Entity;
import it.unibo.crabinv.Model.Movable;
import it.unibo.crabinv.Model.Utils.Position;

public class BaseEnemy implements Entity, Enemy, Movable {
    private int health = 1;
    private int coordinateX;
    private int coordinateY;



    @Override
    public EnemyType getEnemyType() {
        return EnemyType.SERVANT;
    }

    @Override
    public int getHealthPoints() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        if(this.health <= 0){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public Position getCoordinates() {
        return null;
    }

    @Override
    public void onCollisionWith(Entity otherEntity) {

    }

    @Override
    public void move() {

    }
}
