package it.unibo.crabinv.Model.Enemies.wavetypes;

import it.unibo.crabinv.Model.Enemies.EnemyFactory;
import it.unibo.crabinv.Model.Enemies.EnemyType;
import it.unibo.crabinv.Model.Enemies.RewardsService;
import it.unibo.crabinv.Model.Enemies.WaveImpl;

import java.util.List;

public final class WaveAlpha extends WaveImpl {

    public WaveAlpha(EnemyFactory enemyFactory,
                     RewardsService rewardsService,
                     double worldWidth,
                     double worldHeight) {
        super(  List.of(EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT),
                enemyFactory,
                rewardsService,
                worldWidth,
                worldHeight);
    }
}
