package it.unibo.crabinv.controller.entities.bullets;

import it.unibo.crabinv.controller.entities.entity.EntityController;
import it.unibo.crabinv.controller.entities.entity.EntityNotCapableOfInputController;

/**
 * Provides any bulletController with the methods it should implement by combining
 * EntityController and
 * EntityNotCapableOfInputController.
 */
public interface BulletController extends EntityController, EntityNotCapableOfInputController {

}
