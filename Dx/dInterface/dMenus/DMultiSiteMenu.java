/**
 * Created on Feb 26, 2006
 * 
 * 
 * Title: DMultiSiteMenu.java 
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
package dInterface.dMenus;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import dConstants.DConst;
import dInterface.DApplication;
/*
 * TODO Eliminate references to DModel and getSites() and getCurrentSite()
 */
/**
 * @author : Ruben Gonzalez-Rubio
 * 
 * Description: DMultiSiteMenu.java is a class used to:
 * <p>
 * Build the DMultiSite Menu, for each menu Item there is a Listener to call the
 * activated action in DxApplication.
 * 
 */

public class DMultiSiteMenu extends JMenu implements MenuStates {

	private DApplication _dApplication;

	/**
	 * 
	 */
	public DMultiSiteMenu(DApplication application) {
		super(DConst.MULTI_SITE);
		_dApplication = application;
		setFont(DxMenuBar.DxMB_FONT);
		buildMulti();
	}


	/**
	 * 
	 */
	private void buildMulti() {
		if (_dApplication.isMultiSite()) {
			Vector<String> v = _dApplication.getCurrentDModel().getSites();
			ButtonGroup group = new ButtonGroup();
			class MultiListener implements ItemListener {
				public void itemStateChanged(ItemEvent e) {
					JCheckBoxMenuItem source = (JCheckBoxMenuItem) (e.getSource()); // to avoid warning;				
					if (e.getStateChange() == ItemEvent.SELECTED) {
						_dApplication.changeInMulti(source.getText());
					}
				}
			}
			ItemListener multiListener = new MultiListener();
			for (int i = 0; i < v.size(); i++) {
				JCheckBoxMenuItem rbMenuItem = new JCheckBoxMenuItem( v
						.get(i));
				if (_dApplication.getCurrentDModel().getCurrentSiteName()
						.equalsIgnoreCase( v.get(i)))
					rbMenuItem.setSelected(true);
				else
					rbMenuItem.setSelected(false);
				rbMenuItem.addItemListener(multiListener);
				group.add(rbMenuItem);
				this.add(rbMenuItem);
			}
		}
	} // end buildMulti

//	public void itemStateChanged(ItemEvent e) {
//		JCheckBoxMenuItem source = (JCheckBoxMenuItem) (e.getSource());
//
//		if (e.getStateChange() == ItemEvent.SELECTED) {
//			_dApplication.getDModel().setCurrentSite(source.getText());
//			if (source.getText().equalsIgnoreCase(DConst.ALL_SITES))
//				_dApplication.getDModel().changeInDModelByAllSites(
//						_dApplication.getJFrame());
//			else
//				_dApplication.getDModel().changeInDModel(
//						_dApplication.getJFrame());
//		}
//
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#initialState()
	 */
	public void initialState() {
		this.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTable()
	 */
	public void afterNewTTable() {
		this.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterNewTTStruc()
	 */
	public void afterNewTTStruc() {
		this.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterInitialAssign()
	 */
	public void afterInitialAssign() {
		this.setEnabled(false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterOpenTTSruc()
	 */
	public void afterOpenTTSruc() {
		this.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterImport()
	 */
	public void afterImport() {
		this.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#afterInitialAssignment()
	 */
	public void afterInitialAssignment() {
		this.setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dInterface.dMenus.MenuStates#showAllMenus()
	 */
	public void showAllMenus() {
		this.setEnabled(true);
	}

}
