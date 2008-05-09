/*
 * Created on May 19, 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

/**
 * Template que doit impl�menter une instance concr�te de
 * RelationTester.  Indique � l'utilisteur qu'il est fortement
 * conseill� d'utiliser le DP "Singleton" avec les impl�mentations de
 * RelationTester.
 * 
 * @author Pascal
 *  
 */
public abstract class RelationTesterTemplate implements RelationTester {
    protected static RelationTesterTemplate _instance = null;
}
