/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.persistance;

import java.util.Collection;

/**
 * Gestionnaire des PersistanceMismatch.  Agit principalement comme
 * "conteneur" de PersistanceMismatch.
 * 
 * @author Pascal
 *
 */
public interface PersistanceMismatchManager {
    public boolean isPersistanceMismatch();
    public void addPersistanceMismatch(PersistanceMismatch mm);
    public Collection getPersistanceMismatches();
}
