package dInterface.dData;

/**
 *
 * Title: ReportOptionsDlg $Revision: 1.33 $  $Date: 2008-02-25 00:23:33 $
 * Description: ReportOptionsDlg is a class used to display
 *              a dialog to chose the fields to include in a report
 *              also the order of fields can be defined by the dialog
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
 * @version $Revision: 1.33 $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxTools;
import dInterface.dUtil.TwoButtonsPanel;
import developer.DxFlags;

public class ReportOptionsDlg extends JDialog implements ActionListener {
	private ViewReport _parentDlg;

	private JList _rightList, _leftList;

	private Vector <String> _rightVec, _leftVec;

	//  private JPanel _centerPanel, _arrowsPanel;
	private ButtonsPanel _applyPanel;

	//  private boolean _modified = false;

	private String[] _arrowsNames = { DConst.TO_RIGHT, DConst.TO_LEFT,
			DConst.TO_UP, DConst.TO_DOWN };

	/**
	 * Constructor
	 * @param dApplic to link with the Parent JFrame
	 * @param parentDlg changes in the dialog are sent to the parentDialog
	 * @param options containing all the report fields, the first half is showed
	 *                in the left pane the second halh in the right pane
	 * @param elements is the number of elements in the first half
	 */
	public ReportOptionsDlg(DApplication dApplic, ViewReport parentDlg,
			Vector <String> options, int elements) {
		super(dApplic.getJFrame(), DConst.REPORT_OPTIONS_DLG_TITLE, true);
		_parentDlg = parentDlg;
		_leftVec = left(options, elements);
		_rightVec = rigth(options, elements);
		_leftList = new JList(_leftVec);
		_rightList = new JList(_rightVec);

		reportOptionsDlgInit();
		setLocationRelativeTo(dApplic.getJFrame());
		setResizable(false);
		setVisible(true);
	}//end constructor

	/**
	 * Initialize the dialog
	 */
	private void reportOptionsDlgInit() {
		Dimension dlgDim = new Dimension(DConst.DIALOG_DIM, DConst.DIALOG_DIM);
		Dimension centerPanelDim = new Dimension((int) dlgDim.getWidth()
				- DConst.CENTER_WIDTH, (int) dlgDim.getHeight()
				- DConst.CENTER_HEIGHT);
		Dimension listPanelDim = new Dimension((int) centerPanelDim.getWidth()
				/ 2 - DConst.LIST_LENGHT, (int) centerPanelDim.getHeight());

		//String[] leftLabelsInfo = {DConst.REPORT_OP_FIELDS_NOT_CHOICED};

		//String[] rightLabelsInfo = {DConst.REPORT_OP_FIELDS__CHOICED};
		//the centerPanel
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(centerPanelDim);
		centerPanel.add(setListPanel(listPanelDim, _leftList,
				DConst.REPORT_OP_FIELDS_NOT_CHOICED));

		centerPanel.add(DxTools.arrowsPanel(this, _arrowsNames, true));
		centerPanel.add(setListPanel(listPanelDim, _rightList,
				DConst.REPORT_OP_FIELDS__CHOICED));

		//buttonsPanel
		//_applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, a);
		//Setting the button APPLY disable
		_applyPanel.setFirstDisable();
		//placing the elements into the JDialog
		setSize(dlgDim);
		setResizable(false);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(_applyPanel, BorderLayout.SOUTH);
	}//end method

	private Vector<String> rigth(Vector<String> v, int e) {
		Vector <String>res = new Vector<String>();
		for (int i = e; i < v.size(); i++) {
			res.add(v.get(i));
		}
		return res;
	}// end rigth

	private Vector <String> left(Vector <String> v, int e) {
		Vector<String> res = new Vector<String>();
		for (int i = 0; i < e; i++) {
			res.add(v.get(i));
		}
		return res;
	}// end left

	private static JPanel setListPanel(Dimension panelDim, JList theList,
			String labelsInfo) {
		Dimension infoPanelDim = new Dimension((int) panelDim.getWidth(), 20);
		Dimension listPanelDim = new Dimension((int) panelDim.getWidth(),
				(int) (panelDim.getHeight() - infoPanelDim.getHeight()));

		JPanel panelList = new JPanel(new BorderLayout());
		panelList.setPreferredSize(listPanelDim);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(listPanelDim);
		scrollPane.getViewport().add(theList);
		panelList.add(scrollPane);

		//the panel
		JPanel panel = new JPanel();
		panel.setPreferredSize(panelDim);
		panel.add(new JLabel(labelsInfo));
		panel.add(panelList);
		//panel.setBorder(BorderFactory.createLineBorder(Color.black));
		return panel;
	}

	private void listTransfers(Object[] elementsToTransfer, Vector <String> sourceIn,
			JList s, Vector <String> destinationIn, JList d, boolean left) {
		Vector <String> source = sourceIn;
		Vector <String> destination = destinationIn;
		if (elementsToTransfer.length != 0) {
			for (int i = 0; i < elementsToTransfer.length; i++) {
				source.remove(elementsToTransfer[i]);
				destination.add((String)elementsToTransfer[i]);
			}
			if (left)
				destination = DxTools.sortVector(destination);
			else
				source = DxTools.sortVector(source);
			int[] indexes = DxTools.getIndicesOfIntersection(destination,
					elementsToTransfer);
			d.setListData(destination);
			d.setSelectedIndices(indexes);
			s.setListData(source);
			s.clearSelection();
		}//end if
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		//If buttons CLOSE
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		//if button OK
		if (command.equals(DConst.BUT_APPLY)) {
//			if (DxFlags.newPref) {	
				_parentDlg.doSavePref(_rightVec);	 
//			} else {
//				_parentDlg.doSave(_rightVec);
//			}
			_parentDlg.showReport();
			//_modified = false;
			_applyPanel.setFirstDisable();
			//dispose();
		}
		if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1])) {
			//toLeft button
			//_modified = true;
			_applyPanel.setFirstEnable();
			if (command.equals(_arrowsNames[1])) {

				listTransfers(_rightList.getSelectedValues(), _rightVec,
						_rightList, _leftVec, _leftList, true);

			} else {

				//toRight button
				listTransfers(_leftList.getSelectedValues(), _leftVec,
						_leftList, _rightVec, _rightList, false);
			}

		}//end if (command.equals(_arrowsNames[0]) || command.equals(_arrowsNames[1]))
		if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3])) {
			int i = -1;
			//_modified = true;
			_applyPanel.setFirstEnable();
			if (_rightList.getSelectedIndices().length == 1) {
				String selectedValue = (String) _rightList.getSelectedValue();
				i = _rightList.getSelectedIndex();
				//toUp button
				if (command.equals(_arrowsNames[2]))
					swap(_rightVec, i, i - 1);
				//toDown button
				else
					swap(_rightVec, i, i + 1);
				_rightList.setListData(_rightVec);
				_rightList.setSelectedValue(selectedValue, true);
			}
		}//end if (command.equals(_arrowsNames[2]) || command.equals(_arrowsNames[3]))
	}

	private void swap(Vector <String> v, int s, int d) {
		String aux = v.get(s);
		if (d >= 0 && d < v.size()) {
			v.setElementAt(v.get(d), s);
			v.setElementAt(aux, d);
		}
	}
}