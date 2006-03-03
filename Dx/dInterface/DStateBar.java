package dInterface;
/**
 *
 * Title: DStateBar $Revision: 1.12 $  $Date: 2006-03-03 16:03:31 $
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @version $Revision: 1.12 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 *
 * Our convention is that: It's necessary to indicate explicitly
 * all Exceptions that a method can throw.
 * All Exceptions must be handled explicitly.
 */


/**
 * Description: ClassName is a class used to
 *
 */



/**
 * Description: This class is used as a template to have the header
 * of classes any where.  This file is not a CVS file
 *
 */






import javax.swing.JLabel;
import javax.swing.JPanel;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfStates;
import dInternal.DState;


public class DStateBar extends JPanel {
  private JLabel _theLabels[];

  public DStateBar(DSetOfStates s) {
    boolean first = true;
    _theLabels = new JLabel [s.size()];
    for (int i = 0; i < s.size(); i++) {
      _theLabels[i] = new JLabel();
    }
    showDStateBar(s, first);
  }

  public void upDateDStateBar(DSetOfStates s) {
    boolean first = false;
    showDStateBar(s, first);
  }

  private void showDStateBar(DSetOfStates s, boolean first) {
    for(int i = 0; i < s.size(); i++) {
      DResource r =  s.getResourceAt(i);
      if ( ((DState)r.getAttach()).getColor() != null) {
        _theLabels[i].setForeground(((DState)r.getAttach()).getColor());
      } else {
        _theLabels[i].setForeground(DConst.COLOR_BLACK);
      }
      if ( ((DState)r.getAttach()).getValue() >= 0 ) {
        _theLabels[i].setText(r.getID() + " : " + " " + ((DState)r.getAttach()).getValue() + " ");
      } else {
        _theLabels[i].setText(r.getID() + " : " +  "  ");
      }
      if (first) {
        this.add(_theLabels[i]);
      }
    } // end for
  } // end showDStateBar

} // end class DSTateBar