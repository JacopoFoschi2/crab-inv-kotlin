package it.unibo.crabinv.Controller.bullets;

import it.unibo.crabinv.Controller.entity.EntityController;
import it.unibo.crabinv.Controller.entity.EntityNotCapableOfInputController;

/**
 * Provides any bulletController with the methods it should implement by combining EntityController and EntityNotCapableOfInputController
 */
public interface BulletController extends EntityController, EntityNotCapableOfInputController {

}
