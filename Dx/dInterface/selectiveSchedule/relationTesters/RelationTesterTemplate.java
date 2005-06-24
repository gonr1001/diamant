/*
 * Created on May 19, 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

/**
 * Template que doit implémenter une instance concrète de
 * RelationTester.  Indique à l'utilisteur qu'il est fortement
 * conseillé d'utiliser le DP "Singleton" avec les implémentations de
 * RelationTester.
 * 
 * @author Pascal
 *  
 */
public abstract class RelationTesterTemplate implements RelationTester {
    protected static RelationTesterTemplate _instance = null;
}
