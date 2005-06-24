/*
 * Created on 29 mai 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dOptimization.EventAttach;

/**
 * Une impl�mentation de RelationTester.  Permet de v�rifier si
 * l'Activity attach�e au DResource pass� en param�tre est en relation
 * avec le EventAttach de l'autre DResource pass� en param�tre.
 *
 * @author Pascal
 * 
 */
public class RelationTester_ActivityEvent extends RelationTesterTemplate {
    public static RelationTesterTemplate getInstance() {
        if (_instance == null) {
            _instance = new RelationTester_ActivityEvent();
        }
        return _instance;
    }

    /**
     * Activity: DResource.getID() retourne, p.ex. "AMC645" EventAttach:
     * DResource.getID() retourne, p.ex. "AMC645010101"
     *  
     */
    public boolean isRelated(DResource el1, DResource el2)
            throws UnexpectedArguments {
        
        DResource activity = null;
        DResource eventAttach = null;
        
        /* V�rification des types */
        if ((el1.getAttach().getClass() == Activity.class)
                && (el2.getAttach().getClass() == EventAttach.class)) {
            activity = el1;
            eventAttach = el2;
        } else if ((el2.getAttach().getClass() == Activity.class)
                && (el1.getAttach().getClass() == EventAttach.class)) {
            activity = el2;
            eventAttach = el1;
        } else {
            throw new UnexpectedArguments();
        }

        /* V�rification de la relation et retour du r�sultat */
        return eventAttach.getID().startsWith(activity.getID());
    }
}
