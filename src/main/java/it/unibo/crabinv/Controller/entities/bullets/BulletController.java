package it.unibo.crabinv.Controller.entities.bullets;

import it.unibo.crabinv.Controller.entities.entity.EntityController;
import it.unibo.crabinv.Controller.entities.entity.EntityNotCapableOfInputController;

/**
 * Provides any bulletController with the methods it should implement by combining
 * EntityController and
 * EntityNotCapableOfInputController.
 */
public interface BulletController extends EntityController, EntityNotCapableOfInputController {

}
