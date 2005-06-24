/*
 * Created on 29 mai 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

import dInternal.DResource;
import dInternal.dData.dActivities.Activity;
import dInternal.dOptimization.EventAttach;

/**
 * Une implémentation de RelationTester.  Permet de vérifier si
 * l'Activity attachée au DResource passé en paramètre est en relation
 * avec le EventAttach de l'autre DResource passé en paramètre.
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
        
        /* Vérification des types */
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

        /* Vérification de la relation et retour du résultat */
        return eventAttach.getID().startsWith(activity.getID());
    }
}
