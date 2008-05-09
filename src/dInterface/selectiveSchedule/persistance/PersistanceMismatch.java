/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.persistance;

import java.util.Collection;

import dInterface.selectiveSchedule.filters.FilterSet;

/**
 * Lorsque que certaines �l�ments d'un ensemble ne sont plus
 * disponibles dans l'ex�cution courante de Diamant, un
 * PersistanceMismatch est utilis�e pour identifier ces derniers.  Par
 * la suite, on peut utiliser les donn�es recueillies pour informer
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
