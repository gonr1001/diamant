/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;

import java.awt.Component;
import java.util.EventListener;

public interface SetOfActivitiesListener extends EventListener{
  void changeInSetOfActivities(SetOfActivitiesEvent e, Component c);
}