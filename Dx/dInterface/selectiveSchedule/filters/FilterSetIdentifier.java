/*
 * Created on 27 mai 2005
 *
 */
package dInterface.selectiveSchedule.filters;

import dInterface.selectiveSchedule.SelectiveScheduleManager;

/**
 * Identificateur d'un ensemble d'elements.
 * 
 * @author Pascal
 *  
 */
public class FilterSetIdentifier {

    /**
     * Le nom du FilterSet qui contient ce FilterSetIdentifier
     *  
     */
    private String _filterSetName = null;

    /**
     * Identifie si le FilterSet qui est composé de ce FilterSetIdentifier est
     * actif ou non
     *  
     */
    private boolean _active = false;

    public FilterSetIdentifier() {
    	super();
    }

    public String getFilterSetName() {
        return _filterSetName;
    }

    public void setFilterSetName(String name) throws FilterSetNameException {
        if (SelectiveScheduleManager.getInstance().isFilterSetNameAvailable(name) == false) {
            throw new FilterSetNameException("Le nom " + name + " est déjà utilisé");
        }

        _filterSetName = name;
    }

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean active) {
        this._active = active;
    }
}
