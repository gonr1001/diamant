/*
 * Created on May 19, 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

/**
 * XXXX GS: reecrire cette entete... Cette classe a pour utilite de tester une
 * relation entre deux entites. Par exemple, on pourrait desirer tester si une
 * instance de 'Event' est en relation avec une instance de 'Activity'. Pour se
 * faire, on appelle la methode isRelated(EventAttach, Activity) : boolean.
 * 
 * @author Pascal
 *  
 */
public abstract class RelationTesterTemplate implements RelationTester {
    protected static RelationTesterTemplate _instance = null;
}
