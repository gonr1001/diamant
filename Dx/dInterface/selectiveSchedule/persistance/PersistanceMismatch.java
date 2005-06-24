/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.persistance;

import java.util.Collection;

import dInterface.selectiveSchedule.filters.FilterSet;

/**
 * Lorsque que certaines éléments d'un ensemble ne sont plus
 * disponibles dans l'exécution courante de Diamant, un
 * PersistanceMismatch est utilisée pour identifier ces derniers.  Par
 * la suite, on peut utiliser les données recueillies pour informer
 * l'utilisateur de la situation.
 * 
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
