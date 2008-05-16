/*
 * Created on 5 juin 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

import dInternal.DResource;

/**
 * Interface identifiant un testeur de relation.  Le seul service
 * offert par l'interface est la vérification de la présence d'une
 * relation entre deux éléments.
 * 
 * @author Pascal
 *  
 */
public interface RelationTester {
    boolean isRelated(DResource el1, DResource el2) throws UnexpectedArguments;
}
