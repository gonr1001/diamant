/**
 * Created on Feb 22, 2007
 * 
 * 
 * Title: DxEditEventDlgTest.java 
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
 * 
 * 
 */
package dTest.dInterface.dAssignementDlgs;

import javax.swing.JDialog;
import javax.swing.JFrame;

import dConstants.DConst;
import dInterface.dAssignementDlgs.DxEditEventDlg;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxEditEventDlgTest is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxEditEventDlgTest extends TestCase {
	
	private DxEditEventDlg _editor;
	
	public DxEditEventDlgTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxEditEventDlgTest.class);
	} // end suite
	
	protected void setUp() {
		JDialog parent = new JDialog();
		_editor = new DxEditEventDlg(parent);
		_editor.setVisible(true);
	}
	
	protected void tearDown() {
		_editor.dispose();
	}
	public void testCreationByJFrame() {
		JFrame parent = new JFrame();
		DxEditEventDlg editor = new DxEditEventDlg(parent);
		editor.setVisible(true);
		parent.pack();
		parent.setVisible(true);
		assertEquals("testCreation: assertEquals", true,
				editor.isVisible());
		assertEquals("testTitle: assertEquals", DConst.T_AFFEC_DLG,
				editor.getTitle());
		editor.dispose();
		parent.dispose();		
	}
	
	public void testCreationByJDialog() {		
		assertEquals("testCreation: assertEquals", true,
				_editor.isVisible());
		assertEquals("testTitle: assertEquals", DConst.T_AFFEC_DLG ,
				_editor.getTitle());				
	}


}
