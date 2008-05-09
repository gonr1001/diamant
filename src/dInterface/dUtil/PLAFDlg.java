/**
 *
 * Title: PLAFDlg $Revision: 1.7 $  $Date: 2007-05-14 11:18:57 $
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
 * @version $Revision: 1.7 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
package dInterface.dUtil;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import dConstants.DConst;
import dInterface.DApplication;


/**
 * Displays a dialog for the user to select the look and feel *
 *
 */
public class PLAFDlg extends JDialog implements ActionListener {
	private DApplication _dApplic;
	private JComboBox _lafList;
  
  private final String METAL_LAF =   "MetalLookAndFeel";
  private final String MOTIF_LAF =   "MotifLookAndFeel";
  private final String WINDOWS_LAF = "WindowsLookAndFeel";
  private final String METAL =       "javax.swing.plaf.metal.MetalLookAndFeel";
  private final String MOTIF =       "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
  private final String WINDOWS =     "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  private final String NAME_METAL =  "CDE/Metal";
  private final String NAME_MOTIF =  "CDE/Motif";
  private final String NAME_WINDOWS = "Windows";

	public PLAFDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.PLAF_TD, true);
		_dApplic = dApplic;
		initialize();
		this.pack();
		this.setLocationRelativeTo(_dApplic.getJFrame());	  
	    this.setVisible(true);
	} // end PLAFDlg


	private void initialize(){
		_lafList = createsLAFList();
	    _lafList.addActionListener(this);
	    JPanel jp = new JPanel();
	    jp.setLayout( new BorderLayout());
	    jp.add(new JLabel(DConst.PLAF_D), BorderLayout.NORTH);
	    jp.add(new JLabel(" "), BorderLayout.CENTER);
	    jp.add(_lafList, BorderLayout.SOUTH);
	    this.getContentPane().add(jp, BorderLayout.CENTER);	    
	  } // end  initialize() 
  
  
	public void actionPerformed( ActionEvent ae ) {
		String lnfName = "";
		if ( ae.getSource() == _lafList ) {
			int flag =_lafList.getSelectedIndex();
			if( flag == 0 ) {
				lnfName = METAL;
			} else  if( flag == 1 ) {
						lnfName = MOTIF;
					} else {
						lnfName = WINDOWS;
					}
		} //end if
		_dApplic.updateLAF(lnfName);
		_dApplic.getDxPreferences().setLAFName(lnfName);
		_dApplic.getDxPreferences().save();
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
    //System.out.println("l&A " + lf);
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
