/*
 * Created on 14 déc. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal;

import java.util.EventListener;

import dInternal.DSetOfStatesEvent;


public interface DSetOfStatesListener extends EventListener{
  void changeInStateBar(DSetOfStatesEvent e);
}