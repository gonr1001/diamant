/*
 * Created on May 19, 2005
 *
 */
package dInterface.selectiveSchedule.filters;

import java.util.Iterator;

import java.util.Set;
import java.util.HashSet;

import dInternal.DResource;

/**
 * Repr�sentation d'un ensemble.  Le nom "FilterSet" vient du fait que
 * les �l�ments de l'ensemble ont pour effet de filtrer l'affichage de
 * la grille horaire d�taill�e.
 * 
 * @author Pascal
 *  
 */
public class FilterSet {

    /**
     * Identificateur propre � ce FilterSet
     * 
     */
    private FilterSetIdentifier _fsi;

    /**
     * Ensemble des �l�lements de ce FilterSet
     * @associates DResource
     * 
     */
    private Set <DResource> _set;

    /**
     *  
     */
    public FilterSet(FilterSetIdentifier id) {
    	_set = null;
        _fsi = id;
        _set = new HashSet <DResource>();
    }

    public boolean addFilter(DResource element) {
        return _set.add(element);
    }

    public boolean removeFilter(DResource element) {
        return _set.remove(element);
    }
    
    public void clearFilterSet() {
        _set.clear();
    }

    public Iterator getIterator() {
        return _set.iterator();
    }

    public FilterSetIdentifier getFilterSetIdentifier() {
        return _fsi;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _fsi.getFilterSetName();
    }
}
