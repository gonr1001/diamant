/**
 *
 * Title: PLAFDlg $Revision: 1.3 $  $Date: 2003-10-28 14:24:53 $
 * Description: PLAFDlg is a class used to display preferences
 *              Dialogs.
 *              Look and Feel
 *              Language
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
 * @version $Revision: 1.3 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;


import dResources.DConst;
//import com.iLib.rIO.DisplayFatalProblem;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import dInterface.DApplication;

/**
 * Displays a dialog for the user to select the look and feel *
 *
 */
public class PLAFDlg extends JDialog implements ActionListener {
  private DApplication _dApplic;
  private JComboBox _lafList;

  private final String METAL_LAF = "MetalLookAndFeel";
  private final String MOTIF_LAF = "MotifLookAndFeel";
  private final String WINDOWS_LAF = "WindowsLookAndFeel";
  private final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
  private final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  private final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  private final String NAME_METAL = "CDE/Metal";
  private final String NAME_MOTIF = "CDE/Motif";
  private final String NAME_WINDOWS = "Windows";

  public PLAFDlg(DApplication dApplic, String title) {
    //_jFrame = jFrame;
    _dApplic = dApplic;
    _lafList = createsLAFList();
    JPanel jp = new JPanel();
    jp.setLayout( new BorderLayout());
    jp.add(new JLabel(DConst.PLAF_D), BorderLayout.NORTH);
    jp.add(new JLabel(" "), BorderLayout.CENTER);
    jp.add(_lafList, BorderLayout.SOUTH);
    JOptionPane jop = new JOptionPane();
    _lafList.addActionListener(this);
    jop.showMessageDialog(_dApplic.getJFrame(), jp, title,  JOptionPane.PLAIN_MESSAGE);
  }

  public void actionPerformed( ActionEvent ae ) {
    String lnfName = "";
    if ( ae.getSource() == _lafList ) {
      int flag =_lafList.getSelectedIndex();
      if( flag == 0 ) {
        lnfName = METAL;
      }
      else  if( flag == 1 ) {
        lnfName = MOTIF;
      }
      else {
        lnfName = WINDOWS;
      }
    } //end if
    _dApplic.updateLAF(lnfName);
    _dApplic.getPreferences().setLAFName(lnfName);
    _dApplic.getPreferences().save();
  } // end actionPerformed



  private JComboBox createsLAFList() {
    final int numItems = 3;
    String [] lafTable = new String[ numItems ];
    lafTable[0] = METAL_LAF;
    lafTable[1] = MOTIF_LAF;
    lafTable[2] = WINDOWS_LAF;
    JComboBox lafList = new JComboBox(lafTable);

    lafList.setBorder(BorderFactory.createEtchedBorder());

    String lf = UIManager.getLookAndFeel().getName();
    System.out.println("l&A " + lf);
    if ( lf.equals( NAME_METAL ) ) {
      lafList.setSelectedIndex(0);
    } else  if ( lf.equals( NAME_MOTIF ) ) {
      lafList.setSelectedIndex(1);
    } else if ( lf.equals( NAME_WINDOWS  ) ) {
      lafList.setSelectedIndex(2);
    }
    return  lafList ;
  } // end createsLAFList

} /* end class PLAFDlg */
