/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.persistance;

import java.util.Collection;

import dInterface.selectiveSchedule.filters.FilterSet;

/**
 * @author Pascal
 *
 */
public interface PersistanceMismatch {
    public void setExpectedNbOfElements(int nb);
    public void addUnresolvedElement(String elementName);
    public FilterSet getMismatchedFilterSet();
    public Collection getMissingElementNames();
    public int getExpectedNbOfElements();
}
