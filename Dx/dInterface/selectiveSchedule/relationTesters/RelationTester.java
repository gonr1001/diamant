/*
 * Created on 5 juin 2005
 *
 */
package dInterface.selectiveSchedule.relationTesters;

import dInternal.DResource;

/**
 * @author Pascal
 *  
 */
public interface RelationTester {
    boolean isRelated(DResource el1, DResource el2) throws UnexpectedArguments;
}
