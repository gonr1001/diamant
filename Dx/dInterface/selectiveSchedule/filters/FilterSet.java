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
 * Représentation d'un ensemble.  Le nom "FilterSet" vient du fait que
 * les éléments de l'ensemble ont pour effet de filtrer l'affichage de
 * la grille horaire détaillée.
 * 
 * @author Pascal
 *  
 */
public class FilterSet {

    /**
     * Identificateur propre à ce FilterSet
     * 
     */
    private FilterSetIdentifier _fsi = null;

    /**
     * Ensemble des élélements de ce FilterSet
     * @associates DResource
     * 
     */
    private Set _set = null;

    /**
     *  
     */
    public FilterSet(FilterSetIdentifier id) {
        _fsi = id;
        _set = new HashSet();
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
