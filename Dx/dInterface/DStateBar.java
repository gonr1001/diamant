package dInterface;
/**
 *
 * Title: DStateBar $Revision: 1.6 $  $Date: 2003-10-28 13:26:49 $
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
 * @version $Revision: 1.6 $
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


import dResources.DConst;
import dInternal.dData.Resource;
import dInternal.dData.State;
import dInternal.dData.SetOfStates;


public class DStateBar extends JPanel {
  private JLabel _theLabels[];

  public DStateBar(SetOfStates s) {
    boolean first = true;
    _theLabels = new JLabel [s.size()];
    for (int i = 0; i < s.size(); i++) {
      _theLabels[i] = new JLabel();
    }
    showDStateBar(s, first);
  }

  public void upDateDStateBar(SetOfStates s) {
    boolean first = false;
    showDStateBar(s, first);
  }

  private void showDStateBar(SetOfStates s, boolean first) {
    for(int i = 0; i < s.size(); i++) {
      Resource r =  (Resource) s.getResourceAt(i);
      if ( ((State)r.getAttach()).getColor() != null) {
        _theLabels[i].setForeground(((State)r.getAttach()).getColor());
      } else {
        _theLabels[i].setForeground(DConst.COLOR_BLACK);
      }
      if ( ((State)r.getAttach()).getValue() >= 0 ) {
        _theLabels[i].setText(r.getID() + " : " + " " + ((State)r.getAttach()).getValue() + " ");
      } else {
        _theLabels[i].setText(r.getID() + " : " +  "  ");
      }
      if (first) {
        this.add(_theLabels[i]);
      }
    } // end for
  } // end showDStateBar

} // end class DSTateBar