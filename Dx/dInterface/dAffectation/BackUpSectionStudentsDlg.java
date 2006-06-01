/**
 * Created on Jun 21, 2005
 * 
 * TODO To change the class description for this generated file
 * 
 * Project Dx
 * Title: BackUpSectionStudentsDlg.java 
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
 * 
 * @since JDK1.3
 */

package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DXJComboBox;
import dInterface.dUtil.DXTools;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DSetOfResources;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dActivities.Type;
import eLib.exit.dialog.InformationDlg;

/**
 * @author : Ruben Gonzalez-Rubio
 *
 * Description: BackUpSectionStudentsDlg.java is a class used to: 
 * <p>
 *
 */
public class BackUpSectionStudentsDlg extends JDialog implements ActionListener {

	private DApplication _dApplic;

	private int _currentAssignedGroup;

	private DXJComboBox _activitiesCombo;

	private DXJComboBox _typeCombo;

	private DXJComboBox _sortCombo;

	private JPanel _arrowsPanel;

	private JPanel _assignedPanel;

	private JPanel _notAssignedPanel;

	private ButtonsPanel _applyPanel;

	private String[] _arrowsNames;
	
//	private int DOUBLE_CLICK = 2;
//	private int FIXED_IN_GROUP_LIST_POSITION = 4;

	/**
	 * <p>
	 * Constructor
	 * <p>
	 * It initialize the student section dialog
	 * 
	 * @param dApplic
	 */
	public BackUpSectionStudentsDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.SECTION_DLG_TITLE, true);
		_dApplic = dApplic;
		_currentAssignedGroup = -1;
		if (_dApplic.getCurrentDoc() == null)
			return; //nothing happens if no CurrentDocument
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		SetOfStudents students = _dApplic.getCurrentDModel().getSetOfStudents();
		_arrowsNames = new String[] { DConst.TO_RIGHT, DConst.TO_LEFT };
		if (activities != null && students != null) {
			students.sortSetOfResourcesByID();
			initializeDlg();
			int x = _dApplic.getJFrame().getX();
			int y = _dApplic.getJFrame().getY();
			this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET);
			this.pack();
			this.setResizable(true);
			this.setVisible(true);
		} //end if
	}//end SectionDlg

	// XXXX Pascal: Fix Java 1.5->1.4
	public Dimension getMinimumSize() {
		return new Dimension(200, 200);
	}
   
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	public Dimension getMaximumSize() {
		return new Dimension(1000, 1500);
	}
	/**
	 * <p>
	 * Invoked when an action occurs in the panel
	 * 
	 * @param ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		//if activity combo box
		if (e.getSource().equals(_activitiesCombo)) {
			this.changeInActivityComboBox(activities);
			//_sortCombo.setSelectedIndex(0);
		}
		//if type combo box
		if (e.getSource().equals(_typeCombo)) {
			this.changeInTypeComboBox();
			_sortCombo.setSelectedIndex(0);
		}
		//if sort button
		if (e.getSource().equals(_sortCombo)) {
			changeInSortComboBox();
		}
		//buttons
		//if Button CLOSE is pressed
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		//if Button APPLY is pressed
		if (command.equals(DConst.BUT_APPLY)) {
			setStudentsInGroups();			
			_activitiesCombo.setEnabled(true);
			_typeCombo.setEnabled(true);
			_applyPanel.setFirstDisable();					
			_dApplic.getCurrentDModel().changeInDModelByStudentsDlg(this);
		}
		// Arrows
		if ((command.equals(_arrowsNames[1]) || command.equals(_arrowsNames[0]))
				&& _currentAssignedGroup > -1) {
			doClickOnArrowButton(command);
		}//end if ((command.equals(_arrowsNames[1]) || command.equals(_arrowsNames[0]))
	}//end method
	
	/**
	 * Initialize student section dialog
	 * <p>
	 * It add in the contentPane three panels
	 * <p>
	 * The first panel is the top panel
	 * <p>
	 * The second panel is the apply Panel
	 * <p>
	 * The third panel is the center Panel
	 */
	private void initializeDlg() {
		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(new Dimension(550, 550));
		this.setResizable(false);
		this.getContentPane().add(initTopPanel(), BorderLayout.NORTH);

		//_applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, a);
		
		_activitiesCombo.setEnabled(true);
		_typeCombo.setEnabled(true);
		//_sortCombo.enable();
		_applyPanel.setFirstDisable();
		this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);

		getContentPane().add(initCenterPanel(), BorderLayout.CENTER);
		setAllLists();
	} // initializeDlg
	
	/**
	 * <p>
	 * Initialize the top panel of the student section dialog
	 * 
	 * @return JPanel containing three panels
	 *         <p>
	 *         The first panel is the activity panel ComboBox
	 *         <p>
	 *         The second panel is the Type Panel ComboBox
	 *         <p>
	 *         The third panel is the sort Panel ComboBox
	 */
	private JPanel initTopPanel() {
		JPanel activityPanel = initActivityPanel();
		JPanel typePanel = initTypePanel();
		JPanel sortPanel = initSortPanel();
		JPanel topPanel = new JPanel();
		topPanel.add(activityPanel);
		topPanel.add(typePanel);
		topPanel.add(sortPanel);
		return topPanel;
	} //end initTopPanel

	/**
	 * <p>
	 * Initialize the center panel of the student section dialog
	 * 
	 * @return JPanel containing three panels
	 *         <p>
	 *         The first panel is the not assigned panel
	 *         <p>
	 *         The second panel is the arrows Panel
	 *         <p>
	 *         The third panel is the assigned Panel
	 */
	private JPanel initCenterPanel() {
		_notAssignedPanel = initNotAssignedPanel();
		_arrowsPanel = DXTools.arrowsPanel(this, _arrowsNames, true);
		_assignedPanel = initAssignedPanel();
		JPanel centerPanel = new JPanel();
		centerPanel.add(_notAssignedPanel);
		centerPanel.add(_arrowsPanel);
		centerPanel.add(_assignedPanel);
		return centerPanel;
	}// end initCenterPanel
	
	/**
	 * <p>
	 * Initialize the activities combo box of the top panel
	 * 
	 * @return JPanel the Activity Panel
	 */
	private JPanel initActivityPanel() {
		Vector activitiesVector = new Vector();
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		activitiesVector = activities.getIDsByField(3, "true");
		//panel of activities
		_activitiesCombo = new DXJComboBox(activitiesVector);
		_activitiesCombo.addActionListener(this);
		JPanel activityPanel = new JPanel();
		activityPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACTIVITY));
		activityPanel.add(_activitiesCombo);
		return activityPanel;
	} //end initActivityPanel

	/**
	 * Initialize the Type combo box of the top panel
	 * 
	 * @return JPanel the Type Panel
	 */
	private JPanel initTypePanel() {
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		Vector typeVector = ((Activity) (activities
				.getResource(selectedActivity).getAttach())).getSetOfTypes()
				.getNamesVector(1);
		_typeCombo = new DXJComboBox(typeVector);
		_typeCombo.addActionListener(this);
		JPanel typePanel = new JPanel();
		typePanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.TYPE));
		typePanel.add(_typeCombo);
		return typePanel;
	} //end initTypePanel

	/**
	 * <p>
	 * Initialize the activities combo box of the top panel
	 * 
	 * @return JPanel the Type Panel
	 */
	private JPanel initSortPanel() {
		_sortCombo = new DXJComboBox(buildSortVector());
		JPanel sortPanel = new JPanel();
		sortPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.SORT_TITLE));
		sortPanel.add(_sortCombo);
		int sortIndex = 0;
		_sortCombo.setSelectedIndex(sortIndex);
		_sortCombo.addActionListener(this);
		return sortPanel;
	} //end initSortPanel

	/**
	 * <p>
	 * Initialize the not assigned panel which shows the list of
	 * <p>
	 * the not assigned students
	 * 
	 * @return JPanel the not assigned panel
	 */
	private JPanel initNotAssignedPanel() {
		Dimension dialogDim = new Dimension(550, 550);
		Dimension panelDim = new Dimension(
				(int) ((dialogDim.getWidth() - 50) * 0.40), (int) dialogDim
				.getHeight() - 150);
		JPanel notAssignedPanel = new JPanel();
		JList[] jlist = { new JList(new Vector(1)), new JList(new Vector(1)),
				new JList(new Vector(1)) };
		for (int i = 0; i < jlist.length; i++)
			jlist[i].addMouseListener(mouseListenerLists);
		notAssignedPanel = listPanel(notAssignedPanel, jlist,
				(int) (panelDim.getWidth() - 25),
				(int) panelDim.getHeight() - 35);
		notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACT_STUD_NOT_ASSIGNED));
		notAssignedPanel.setPreferredSize(panelDim);
		return notAssignedPanel;
	} //end initNotAssignedPanel
	
	/**
	 * <p>
	 * Initialize the assigned panel which contains the lists
	 * <p>
	 * of assigned students
	 * 
	 * @return JPanel the assigned panel
	 */
	private JPanel initAssignedPanel() {
		Dimension panelDim = getPanelDimension();
		Dimension scrollDim = getScrollPaneDimension();
	
		JPanel assignedPanel = new JPanel();
		assignedPanel = new JPanel(new BorderLayout());
		assignedPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACT_STUD_ASSIGNED));
		assignedPanel.setPreferredSize(panelDim);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(scrollDim);
		assignedPanel.add(scrollPane);
		setScrollPane(scrollPane, scrollDim, true);	
		return assignedPanel;
	} //end initAssignedPanel
	
	/**
	 * <p>
	 * Action to do when an event occurs in the activity combo box
	 * 
	 * @param activities
	 *            the set of activities
	 */
	private void changeInActivityComboBox(SetOfActivities activities) {
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		if (!_applyPanel.isFirstEnable()) {
			Vector typeVector = ((Activity) (activities
					.getResource(selectedActivity).getAttach()))
					.getSetOfTypes().getNamesVector(1);
			_typeCombo.disableActionListeners();
			_typeCombo.removeAllItems();
			_typeCombo.enableActionListeners();
			for (int i = 0; i < typeVector.size(); i++) {
				_typeCombo.addItem(typeVector.elementAt(i));
			}//end for
			JScrollPane scrollPane = getAssignedScrollPanel();
			setScrollPane(scrollPane, getScrollPaneDimension(),true);
			setAllLists();
			_sortCombo.setSelectedIndex(0);
		} else {
			new InformationDlg(this, DConst.INFORMATION_DLG_MES,
					DConst.INFORMATION_DLG_NAME);
			_activitiesCombo.setSelectedItem(selectedActivity);
		}
	}
	
	
	

	/**
	 * <p>
	 * Action to do when an event occurs in the Type combo box
	 */
	private void changeInTypeComboBox() {
		String selectedType = (String) _typeCombo.getSelectedItem();
		if (!_applyPanel.isFirstEnable()) {
			JScrollPane scrollPane = getAssignedScrollPanel();
			setScrollPane(scrollPane, getScrollPaneDimension(),true);
			setAllLists();
		} else {
			new InformationDlg(this, DConst.INFORMATION_DLG_MES,
					DConst.INFORMATION_DLG_NAME);
			_typeCombo.setSelectedItem(selectedType);
		}
	}

	/**
	 * <p>
	 * Action to do when an event occurs in the sort combo box
	 */
	private void changeInSortComboBox() {
		sortAllPanel();
	}
	
	/**
	 * Sort not assigned panel and assigned panel
	 *
	 */
	private void sortAllPanel(){
		this.sortNotAssignedPanel();
		this.sortAssignedPanel();
	}
	
	/**
	 * Sort not assigned panel
	 *
	 */
	private void sortNotAssignedPanel(){
		if (_notAssignedPanel != null) {
			int[] selectedIndice = this.getAListFromNotAssignedPanel(1).getSelectedIndices();
			JList [] list = new JList[3];
			Vector v= new Vector();
			for(int i=1; i<4; i++){
				list[i-1] = this.getAListFromNotAssignedPanel(i);
				Vector vectmp= new Vector(1);
				for(int k=0; k< list[i-1].getModel().getSize(); k++)
					vectmp.add(list[i-1].getModel().getElementAt(k));
				v.add(vectmp);
			}// end for(int i=1; i<4; i++)
			DSetOfResources sof= SetOfStudents.createAStudentInstance(v);// sort by key
			sortResource(sof);
			v= SetOfStudents.createAVectorInstance(sof);
			for(int i=1; i<4; i++){
				list[i-1] = this.getAListFromNotAssignedPanel(i);
				list[i-1].setListData((Vector)v.get(i-1));
				list[i-1].setSelectedIndices(selectedIndice);
			}// end
		}// end if (_notAssignedPanel != null)
	}
	
	/**
	 * Sort all group panel of the assigned panel
	 *
	 */
	private void sortAssignedPanel(){
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		String selectedType = (String) _typeCombo.getSelectedItem();
		Type type = activities.getType(selectedActivity, selectedType);
		int[] selectedIndices = this.getAListFromAssignedPanel(1, 0).getSelectedIndices();
		if (_assignedPanel != null) {
			for (int i = 0; i < type.getSetOfSections().size(); i++) {
				JList [] list = new JList[4];
				Vector v= new Vector();
				for(int j=1; j<5; j++){
					list[j-1] = this.getAListFromAssignedPanel(j, i);
					Vector vectmp= new Vector();
					for(int k=0; k< list[j-1].getModel().getSize(); k++)
						vectmp.add(list[j-1].getModel().getElementAt(k));
					v.add(vectmp);
				}// end for(int i=1; i<4; i++)
				//sort by key set of student
				DSetOfResources sof= SetOfStudents.createAStudentInstance(v);// sort by key
				sortResource(sof);
				v= SetOfStudents.createAVectorInstance(sof);
				for(int j=1; j<5; j++){
					list[j-1] = this.getAListFromAssignedPanel(j, i);
					list[j-1].setListData((Vector)v.get(j-1));
					list[j-1].setSelectedIndices(selectedIndices);
				}// end
			}// end for (int i = 0; i < type.getSetOfSections().size(); i++)
		}// end if (_notAssignedPanel != null)
		
	}
	
	/**
	 * sort a set of resources using the selected item from the sort combo box  
	 * @param sof the resource to sort
	 */
	private void sortResource(DSetOfResources sof){
		String selectedItem = (String)_sortCombo.getSelectedItem();
		if(selectedItem.equalsIgnoreCase(DConst.SORT_BY_NAME)){
			sof.sortSetOfResourcesByID();
		}else if(selectedItem.equalsIgnoreCase(DConst.SORT_BY_MATRICUL)){
			sof.sortSetOfResourcesByKey();
		}else if(selectedItem.equalsIgnoreCase(DConst.SORT_BY_PROGRAM)){
			sof.sortSetOfResourcesBySelectedAttachField(1);
		}
	}

	/**
	 * <p>
	 * Action to do when an event occurs in the left and rigth arrow button
	 */
	private void doClickOnArrowButton(String selectedArrow) {
		if( selectedArrow.equalsIgnoreCase(DConst.TO_RIGHT)){
			doClickOnArrowToRight();
		}else if( selectedArrow.equalsIgnoreCase(DConst.TO_LEFT)){
			doClickOnArrowToLeft();
		}
		sortAllPanel();
		
		_activitiesCombo.setEnabled(false);
		_typeCombo.setEnabled(false);
		_applyPanel.setFirstEnable();
		//_sortCombo.disable();
		//_arrowsPanel.disableActionListeners();
		
	}
	
	
	/**
	 * The method is call when mouse listener has detect a double click in the <p>
	 * assigned students panel. It put or remove a star at the end of the selected student 
	 * @param list the student fixed in group list
	 * 
	 */
/*	private void doDoubleClickOnLineInAAssPanel(){
		JList list = this.getAListFromAssignedPanel(FIXED_IN_GROUP_LIST_POSITION, _currentAssignedGroup);
		int selectedIndex = list.getSelectedIndex();
		Vector assDataM =  getListVector(list);
		String fixed_in_group = (String)assDataM.get(selectedIndex);
		if(fixed_in_group.equalsIgnoreCase(DConst.CHAR_FIXED_IN_GROUP))
			assDataM.setElementAt(DConst.CHAR_NOTFIXED_IN_GROUP,selectedIndex);
		else
			assDataM.setElementAt(DConst.CHAR_FIXED_IN_GROUP,selectedIndex);
		list.setListData(assDataM);
		_applyPanel.setFirstEnable();
	}
*/	
	

	
	/**
	 * <p>
	 * Action to do when an event occurs in the left and rigth arrow button
	 */
	private void doClickOnArrowToRight() {
		this.getAListFromAssignedPanel(1, _currentAssignedGroup);
		JList[] list = new JList[4];
		JList[] listNotAss = new JList[4];
		Vector assDataM = new Vector();
		Vector notAssDataM = new Vector();
		int endSize = 0;
		/*int[] verif = new int[4];
		for (int j = 0; j < listNotAss.length-1; j++) {
			listNotAss[j] = this.getAListFromNotAssignedPanel(j + 1);
			verif[j] = listNotAss[j].getSelectedIndex();
		}*/
		if (goodSelectionInNotAssigned(listNotAss)) {
			for (int j = 0; j < (list.length - 1); j++) {
				list[j] = this.getAListFromAssignedPanel(j + 1,
						_currentAssignedGroup);
				listNotAss[j] = this.getAListFromNotAssignedPanel(j + 1);
				Object[] notAssignedValue = listNotAss[j].getSelectedValues();
				assDataM = getListVector(list[j]);
				notAssDataM = getListVector(listNotAss[j]);
				for (int k = 0; k < notAssignedValue.length; k++) {
					notAssDataM.remove(notAssignedValue[k]);
					assDataM.add(notAssignedValue[k]);
				}// end for(int k=0; k< assignedIndice.length; k++)
				endSize = assDataM.size();
				list[j].setListData(assDataM);
				list[j].setSelectedValue(notAssignedValue, true);
				if (listNotAss[j] != null)
					listNotAss[j].setListData(notAssDataM);
			}// end for(int j = 0; j < list.length ; j++){

			list[3] = this.getAListFromAssignedPanel(4, _currentAssignedGroup);
			assDataM = getListVector(list[3]);
			int beginSize = assDataM.size();
			for (int i = beginSize; i < endSize; i++)
				assDataM.add(DConst.CHAR_FIXED_IN_GROUP);
			list[3].setListData(assDataM);
			setNumOfEltOfGpPanelFromAssigned(_currentAssignedGroup, String
					.valueOf(assDataM.size()));
		} else { //end if ((verif[0] == verif[1]) && (verif[0] == verif[2]))
			new InformationDlg(_dApplic.getJFrame(),"Mauvaise selection");			
		} //end else  if ((verif[0] == verif[1]) && (verif[0] == verif[2]))
	}
	
	/**
	 * <p>
	 * Action to do when an event occurs in the left and rigth arrow button
	 */
	private void doClickOnArrowToLeft() {
		this.getAListFromAssignedPanel(1, _currentAssignedGroup);
		JList[] list = new JList[4];
		JList[] listNotAss = new JList[4];
		Vector assDataM = new Vector(1);
		Vector notAssDataM = new Vector(1);
		int[] verif = new int[4];
		for (int j = 1; j < (list.length + 1); j++) {
			list[j - 1] = this.getAListFromAssignedPanel(j,
					_currentAssignedGroup);
			verif[j - 1] = list[j - 1].getSelectedIndex();
		}
		if ((verif[0] == verif[1]) && (verif[0] == verif[2])
				&& (verif[0] == verif[3])) {
			for (int j = 1; j < 5; j++) {
				list[j - 1] = this.getAListFromAssignedPanel(j,
						_currentAssignedGroup);
				Object[] assignedValue = list[j - 1].getSelectedValues();
				listNotAss[j - 1] = this.getAListFromNotAssignedPanel(j);
				assDataM = getListVector(list[j - 1]);
				notAssDataM = getListVector(listNotAss[j - 1]);
				for (int k = 0; k < assignedValue.length; k++) {
					notAssDataM.add(assignedValue[k]);
					assDataM.remove(assignedValue[k]);
				}// end for(int k=0; k< assignedIndice.length; k++)
				list[j - 1].setListData(assDataM);
				list[j - 1].setSelectedValue(assignedValue, true);
				if (listNotAss[j - 1] != null)
					listNotAss[j - 1].setListData(notAssDataM);
			}// end for(int j=1; j<4; j++)
			setNumOfEltOfGpPanelFromAssigned(_currentAssignedGroup, String
					.valueOf(assDataM.size()));
		} else {
			new InformationDlg(_dApplic.getJFrame(),"Mauvaise selection");
		}
	}
	
	/**
	 * get the data model of a JList in a Vector format
	 * @param jlist
	 * @return
	 */
	private Vector getListVector(JList jlist){
		Vector vec =  new Vector();
		if(jlist != null){
			for (int i=0; i< jlist.getModel().getSize(); i++){
				vec.add(jlist.getModel().getElementAt(i));
			}// end for (int i=0; i< jlist.getComponentCount(); i++)
		}// end if(jlist != null)
		return vec;
	}









	/**
	 * <p>
	 * Get the dimension of the scrollpane
	 * 
	 * @return Dimenion
	 */
	private Dimension getScrollPaneDimension() {
		return new Dimension((int) getPanelDimension().getWidth() - 10,
				(int) getPanelDimension().getHeight() - 5);
	}



	/**
	 * <p>
	 * Get the panel dimension of the student section dialog
	 * 
	 * @return Dimension
	 */
	private Dimension getPanelDimension() {
		return new Dimension(
				(int) ((getDialogDimension().getWidth() - 50) * 0.55),
				(int) getDialogDimension().getHeight() - 150);
	}

	/**
	 * <p>
	 * Get the student dialog dimension
	 * 
	 * @return
	 */
	private Dimension getDialogDimension() {
		return new Dimension(550, 550);
	}

	/**
	 * <p>
	 * Set the scrollPane that contains the insidePanel
	 * <p>
	 * corresponding to the groups in a type of activity
	 * 
	 * @param scrollPane
	 * @param ScrollPaneDim
	 * @param updateInsidePanel tell if we need to redraw inside pane
	 * <p> updateInsidePanel = false if we only want to sort list
	 * <p> and true otherwise
	 */
	private void setScrollPane(JScrollPane scrollPane, Dimension ScrollPaneDim, boolean updateInsidePanel) {
		JPanel insidePanel;
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		String selectedType = (String) _typeCombo.getSelectedItem();
		Type type = activities.getType(selectedActivity, selectedType);
		int numberOfSection = type.getSetOfSections().size();
		int insideWidth = (int) ScrollPaneDim.getWidth() - 20;
		int scrollHeight = (int) ((ScrollPaneDim.getHeight() - 20) / 2);
		JViewport viewPort = scrollPane.getViewport();

		if (viewPort.getComponentCount() == 0) {
			insidePanel = new JPanel(new GridLayout(numberOfSection, 1));
		} else {
			insidePanel = (JPanel) viewPort.getComponents()[0];
		}
		Dimension insideDim = new Dimension(insideWidth, scrollHeight
				* numberOfSection + 10);

		JLabel[] lNumberOfElements = new JLabel[numberOfSection];
		if (insidePanel == null)
			insidePanel = new JPanel();
		if(updateInsidePanel){
			insidePanel.setPreferredSize(insideDim);
			insidePanel.removeAll();
			insidePanel.setLayout(new GridLayout(numberOfSection, 1));
			
			for (int i = 0; i < numberOfSection; i++) {
				JPanel groupPanel = setGroupPanel(scrollPane, insidePanel, i,
						lNumberOfElements[i]);
				insidePanel.add(groupPanel);
			}// end for (int i = 0; i < numberOfSection; i++) 
		}//end if(updateInsidePanel)
		scrollPane.setViewportView(insidePanel);
		_currentAssignedGroup = -1;
	}//end method setScrollPane

	/**
	 * <p>
	 * Sets the panel containing the list of students of a section
	 * 
	 * @param scrollPane
	 * @param insidePane
	 * @param groupNumber
	 * @param lNumberOfElements
	 * @return
	 */
	private JPanel setGroupPanel(JScrollPane scrollPane, JPanel insidePane,
			int groupNumber, JLabel lNumberOfElements) {

		int insideWidth = (int) insidePane.getPreferredSize().getWidth();
		int GroupPanelHeight = (int) ((scrollPane.getPreferredSize()
				.getHeight()) / 2);
		int infoPanelHeight = 25;
		Dimension groupPanelDim = new Dimension(insideWidth, GroupPanelHeight);
		JPanel groupPanel =  getSelectedPanelFromAssigned(groupNumber);
		if(groupPanel ==  null)
			groupPanel = new JPanel();
		groupPanel.setPreferredSize(groupPanelDim);
		JPanel scrollContainer;
		if(groupPanel.getComponents().length > 1)
			scrollContainer = (JPanel) groupPanel.getComponents()[1];
		else
			scrollContainer = new JPanel(new BorderLayout());
		
		JList[] jlist = this.getAllListsFromAssignedPanel(groupNumber);
		if(jlist ==  null){
			jlist = new JList[]{ new JList(new Vector(1)), new JList(new Vector(1)),
				new JList(new Vector(1)), new JList(new Vector(1)) };
			for (int i = 0; i < jlist.length; i++)
				jlist[i].addMouseListener(mouseListenerLists);
		}
		scrollContainer = DXTools.listPanel(scrollContainer, jlist, (insideWidth - 20),
				GroupPanelHeight - infoPanelHeight - 20);
		JPanel infoPanel = initInfoPanelFromAssPanel(insideWidth,
				infoPanelHeight, groupNumber, lNumberOfElements);

		//adding the sub-panels to the panel
		groupPanel.add(infoPanel);
		groupPanel.add(scrollContainer);
		return groupPanel;
	}

	/**
	 * <p>
	 * Initialize info panel of a group panel in the
	 * <p>
	 * assigned panel
	 * 
	 * @param insideWidth
	 * @param infoPanelHeight
	 * @param groupNumber
	 * @param lNumberOfElements
	 * @return JPanel the info panel
	 */
	private JPanel initInfoPanelFromAssPanel(int insideWidth,
			int infoPanelHeight, int groupNumber, JLabel lNumberOfElements) {
		int numberOfElements = 0;
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(insideWidth - 10,
				infoPanelHeight));
		JLabel lGroup = new JLabel(DConst.SECTION);
		//add label in the infoPanel
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		String selectedType = (String) _typeCombo.getSelectedItem();
		Type type = activities.getType(selectedActivity, selectedType);
		JLabel lGroupID = new JLabel(type.getSetOfSections().getResourceAt(
				groupNumber).getID());
		lGroupID.setForeground(DConst.COLOR_QUANTITY_DLGS);
		JLabel lNumber = new JLabel(DConst.NUMBER_OF_ELEMENTS + " ");
		lNumberOfElements = new JLabel(String.valueOf(numberOfElements));
		lNumberOfElements.setForeground(DConst.COLOR_QUANTITY_DLGS);
		infoPanel.add(lGroup);
		infoPanel.add(lGroupID);
		infoPanel.add(new JLabel(" - "));
		infoPanel.add(lNumber);
		infoPanel.add(lNumberOfElements);
		return infoPanel;
	}



	/**
	 * <p>
	 * Invoked when the mouse button has been clicked
	 * <p>
	 * (pressed and released) on a List
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {

			if ((e.getClickCount() == 1) && 
					(e.getSource() instanceof JList)) 
					//System.out.println("clicking once the mouse " + e.getSource() );
			
			if ((e.getClickCount() == 2) && 
				(e.getSource() instanceof JList)) {
				if (!_applyPanel.isFirstEnable()) {
					//System.out.println("you doubleclick");				
					changeFixedInGroup(((JList) e.getSource()).getSelectedIndex());
					//_applyPanel.setFirstEnable();
				} else {
					//System.out.println("Il faut faire appliquer");
					new InformationDlg(e.getComponent(), DConst.INFORMATION_DLG_MES,
							DConst.INFORMATION_DLG_NAME);
				}
		        
			} if (e.getSource() instanceof JList) {

			

				boolean isPanelSel = selectLineInANotAssPanel((JList) e.getSource());
				if(!isPanelSel){
					/*if (e.getClickCount() == DOUBLE_CLICK) {
						doDoubleClickOnLineInAAssPanel();
				      }//end if (e.getClickCount() == 2)*/
					selectLineInAAssPanel((JList) e.getSource());
				}
			}//end if (e.getSource() instanceof JList)
			

		}// end public void mouseClicked
//		public void mouseDragged(MouseEvent e){
//			System.out.println("dragging the mouse " + e.getSource() );
//			//
//		}
	};//end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * <p>
	 * Selects a set of cells in each list of the _notAssignedPanel
	 * <p>
	 * if these cells has been selected using mouse
	 * 
	 * @param selectList
	 */
	private boolean selectLineInANotAssPanel(JList selectedList) {
		boolean found = false;
		int inc = 1;
		while (!found && inc < 4) {
			JList notAssList = this.getAListFromNotAssignedPanel(inc++);
			if (selectedList.equals(notAssList)) {
				found = true;
				int[] selectedIndices = selectedList.getSelectedIndices();
				for (int i = 1; i < 4; i++)
					getAListFromNotAssignedPanel(i)
							.setSelectedIndices(selectedIndices);
			} 
		}// end while(!found && inc < 4)
		return found;
	}

	/**
	 * <p>
	 * Selects a set of cells in each list of the _assignedPanel
	 * <p>
	 * if these cells has been selected using mouse
	 * 
	 * @param selectList
	 */
	private void selectLineInAAssPanel(JList selectedList) {
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		String selectedType = (String) _typeCombo.getSelectedItem();
		Type type = activities.getType(selectedActivity, selectedType);
		for (int i = 0; i < type.getSetOfSections().size(); i++) {
			boolean found = false;
			int inc = 1;
			while (!found && inc < 5) {
				JList assList = this.getAListFromAssignedPanel(inc++, i);
				if (selectedList.equals(assList)) {
					found = true;
					int[] selectedIndices = selectedList.getSelectedIndices();
					JPanel groupPanel = this.getSelectedPanelFromAssigned(i);
					groupPanel.setBorder(BorderFactory
							.createLineBorder(DConst.COLOR_QUANTITY_DLGS));
					_currentAssignedGroup = i;
					for (int j = 1; j < 5; j++)
						getAListFromAssignedPanel(j, i)
								.setSelectedIndices(selectedIndices);

				} else {
					assList.clearSelection();
					JPanel groupPanel = this.getSelectedPanelFromAssigned( i);
					groupPanel.setBorder(null);
				}
			}// end while(!found && inc < 4)
		}// end for(int i=0; i< this.getNumberOfSections(type); i++)
	}

	/**
	 * <p>
	 * Set all lists of the not assigned panel and the assigned panel
	 * <p>
	 * using set of students from the model
	 */
	private void setAllLists() {
		SetOfStudents students = _dApplic.getCurrentDModel().getSetOfStudents();
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		this.setNotAssignedLists(activities, students);
		String selectedType = (String) _typeCombo.getSelectedItem();
		Type type = activities.getType(selectedActivity, selectedType);
		this.setAssignedLists(type, students, selectedActivity);
		
	}//end setAllLists

	/**
	 * <p>
	 * Set all lists of the the assigned panel
	 * <p>
	 * using set of students from the model
	 * 
	 * @param type
	 * @param students
	 * @param selectedActivity
	 */
	private void setAssignedLists(Type type, SetOfStudents students,
			String selectedActivity) {
		for (int i = 0; i < type.getSetOfSections().size(); i++) {
			int group = DXTools.STIConvertGroupToInt(type.getSetOfSections()
					.getResourceAt(i).getID());
			String selectedType = (String) _typeCombo.getSelectedItem();
			int sortIndex = 1;// order Key, ID, Program
			Vector assignedVect = students.getStudentsByGroupTable(
					selectedActivity, selectedType, group, sortIndex);
			int value = ((Vector) assignedVect.get(0)).size();
			setNumOfEltOfGpPanelFromAssigned(i, String.valueOf(value));
			if (_assignedPanel != null) {
				JList firstList = this.getAListFromAssignedPanel(1, i);
				
				firstList.setListData((Vector) assignedVect.get(0));
				firstList.setDragEnabled(false);
				firstList.setValueIsAdjusting(false);
				JList secondList = this.getAListFromAssignedPanel(2, i);
				secondList.setListData((Vector) assignedVect.get(1));
				secondList.setDragEnabled(false);
				JList thirdList = this.getAListFromAssignedPanel(3, i);
				thirdList.setListData((Vector) assignedVect.get(2));
				thirdList.setDragEnabled(false);
				JList fourthList = this.getAListFromAssignedPanel(4, i);
				fourthList.setListData((Vector) assignedVect.get(3));
				fourthList.setDragEnabled(false);
			}
		}
	} // end setAssignedLists

	/**
	 * <p>
	 * Set all lists of the not assigned panel
	 * <p>
	 * using set of students from the model
	 * 
	 * @param activities
	 * @param students
	 */
	private void setNotAssignedLists(SetOfActivities activities,
			SetOfStudents students) {
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		Vector typeVector = ((Activity) (activities
				.getResource(selectedActivity).getAttach())).getSetOfTypes()
				.getNamesVector(1);
		int sortIndex = 1;// order Key, ID, Program
		Vector notAssVect = students.getStudentsByGroupTable(selectedActivity,
				(String) typeVector.elementAt(_typeCombo.getSelectedIndex()),
				-1, sortIndex);
		if (_notAssignedPanel != null) {
			JList firstList = this.getAListFromNotAssignedPanel(1);
			JList secondList = this.getAListFromNotAssignedPanel(2);
			JList thirdList = this.getAListFromNotAssignedPanel(3);
			firstList.setListData((Vector) notAssVect.get(0));
			secondList.setListData((Vector) notAssVect.get(1));
			thirdList.setListData((Vector) notAssVect.get(2));
		}
	} // setNotAssignedLists

	/**
	 * <p>
	 * Get a given list from the not assigned panel
	 * 
	 * @param panel
	 * @param listPosition
	 * @return JList the list
	 */
	private JList getAListFromNotAssignedPanel(int listPosition) {

		JViewport scrollPane = ((JScrollPane) _notAssignedPanel.getComponents()[0])
				.getViewport();
		Box boxes = (Box) scrollPane.getComponents()[0];
		int position = 0;
		for (int i = 0; i < boxes.getComponentCount(); i++) {
			if (boxes.getComponents()[i] instanceof JList) {
				JList list = (JList) boxes.getComponents()[i];
				position++;
				if (position == listPosition)
					return list;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Get the selected group panel from the assigned panel
	 * 
	 * @param panel the assigned panel
	 * @param groupIndex
	 * @return JPanel the group Panel
	 */
	private JPanel getSelectedPanelFromAssigned(int groupIndex) {
		if (_assignedPanel != null) {
			JViewport viewPort = ((JScrollPane) _assignedPanel.getComponents()[0])
					.getViewport();
			JPanel insideContainer = (JPanel) viewPort.getComponents()[0];
			//for (int i = 0; i < insideContainer.getComponentCount(); i++) {
			if (groupIndex < insideContainer.getComponentCount()) {
				JPanel container = (JPanel) insideContainer.getComponents()[groupIndex];
				return container;
			}
		}// end if (panel != null)
		return null;
	}

	/**
	 * <p>
	 * Set the number of elements in a group panel from the assigned panel
	 * 
	 * @param groupIndex
	 * @param value
	 */
	private void setNumOfEltOfGpPanelFromAssigned(int groupIndex, String value) {
		JPanel groupPanel = this.getSelectedPanelFromAssigned(groupIndex);
		JPanel topGroupPanel = (JPanel) groupPanel.getComponents()[0];
		JLabel label = (JLabel) topGroupPanel.getComponents()[4];
		label.setText(value);
	}

	/**
	 * <p>
	 * Get the scrollPanel from the assigned panel
	 * 
	 * @return JScrollPanel the scrollpane
	 */
	private JScrollPane getAssignedScrollPanel() {
		return (JScrollPane) _assignedPanel.getComponents()[0];
	}

	/**
	 * <p>
	 * Get a given list from the assigned panel
	 * 
	 * @param listPosition
	 * @return
	 */
	private JList getAListFromAssignedPanel(int listPosition,
			int groupIndex) {
		if (_assignedPanel != null) {
			JViewport viewPort = ((JScrollPane) _assignedPanel.getComponents()[0])
					.getViewport();
			JPanel insideContainer = (JPanel) viewPort.getComponents()[0];
			if (groupIndex < insideContainer.getComponentCount()) {
				JPanel container = (JPanel) insideContainer.getComponents()[groupIndex];
				JPanel groupPane = (JPanel) container.getComponents()[1];
				JScrollPane scrollPane = (JScrollPane) groupPane
						.getComponents()[0];
				JViewport insideViewPort = scrollPane.getViewport();

				Box boxes = (Box) insideViewPort.getComponents()[0];
				int position = 0;
				for (int j = 0; j < boxes.getComponentCount(); j++) {
					if (boxes.getComponents()[j] instanceof JList) {
						JList list = (JList) boxes.getComponents()[j];
						position++;
						if (position == listPosition)
							return list;
					}
				}
			}//end if(groupIndex < insideContainer.getComponentCount())
		}
		return null;
	} // end getAListFromAssignedPanel
	
	/**
	 * <p>
	 * Get all the lists of the assigned panel
	 * 
	 * @return
	 */
	private JList[] getAllListsFromAssignedPanel(int groupIndex) {
		JList[] allLists = new JList[DConst.NUM_OF_LISTS_IN_ASSIGNED_PANEL];
		if (_assignedPanel != null) {
			JViewport viewPort = ((JScrollPane) _assignedPanel.getComponents()[0])
					.getViewport();
			JPanel insideContainer = (JPanel) viewPort.getComponents()[0];
			//for (int i = 0; i < insideContainer.getComponentCount(); i++) {
			if (groupIndex < insideContainer.getComponentCount()) {
				JPanel container = (JPanel) insideContainer.getComponents()[groupIndex];
				JPanel groupPane = (JPanel) container.getComponents()[1];
				JScrollPane scrollPane = (JScrollPane) groupPane
						.getComponents()[0];
				JViewport insideViewPort = scrollPane.getViewport();
				Box boxes = (Box) insideViewPort.getComponents()[0];
				int position = 0;
				for (int j = 0; j < boxes.getComponentCount(); j++) {
					if (boxes.getComponents()[j] instanceof JList) {
						allLists[position++] = (JList) boxes.getComponents()[j];
						if (position > DConst.NUM_OF_LISTS_IN_ASSIGNED_PANEL)
							return allLists;
					}
				}
			}//end if(groupIndex < insideContainer.getComponentCount())
		}
		return null;
	}

	/**
	 * <p>
	 * Set students of all lists in the groups indicated by the JLists
	 */
	private void setStudentsInGroups() {
		String selectedActivity = (String) _activitiesCombo.getSelectedItem();
		String selectedType = (String) _typeCombo.getSelectedItem();
		String course = selectedActivity + selectedType;
		SetOfStudents students = _dApplic.getCurrentDModel().getSetOfStudents();
		JList notAssList = this.getAListFromNotAssignedPanel(1);
		Vector notAssVec = getListVector(notAssList);
		//put
		for(int i=0; i< notAssVec.size(); i++){
/*			long matricule = Long.parseLong((String)notAssVec.get(i));
			students.getStudent(matricule).setInGroup(course,-1,false);
			SetOfActivities activities = _dApplic.getDModel().getSetOfActivities();
/*			Type type = activities.getType(selectedActivity, selectedType);
/*			for (int j = 0; j < type.getSetOfSections().size(); j++) {
				//int group = DXTools.STIConvertGroupToInt(type.getSetOfSections()
				//		.getResourceAt(j).getID());
				JList assList = this.getAListFromAssignedPanel(1, j);
				Vector assVec = getListVector(assList);
				JList nList = this.getAListFromAssignedPanel(2, j);
				Vector nVec = getListVector(nList);
				JList pList = this.getAListFromAssignedPanel(3, j);
				Vector pVec = getListVector(pList);
				JList fixedList = this.getAListFromAssignedPanel(4, j);
				Vector fVec = getListVector(fixedList);
				for(int m=0; m< assVec.size(); m++){
					long matriculeAss = Long.parseLong((String)assVec.get(m));
					if (matricule == matriculeAss) {
						assVec.remove(m);
						nVec.remove(m);
						pVec.remove(m);
						fVec.remove(m);
					}
					//boolean fixed = ((String)fixedVec.get(m)).equalsIgnoreCase(DConst.CHAR_FIXED_IN_GROUP);
					//students.getStudent(matricule).setInGroup(course,group,fixed);
				}// end for(int i=0; i< notAssVec.size(); i++)
			}*/
		}// end for(int i=0; i< notAssVec.size(); i++)
		
		
		SetOfActivities activities = _dApplic.getCurrentDModel().getSetOfActivities();
		Type type = activities.getType(selectedActivity, selectedType);
		for (int i = 0; i < type.getSetOfSections().size(); i++) {
			int group = DXTools.STIConvertGroupToInt(type.getSetOfSections()
					.getResourceAt(i).getID());
			JList assList = this.getAListFromAssignedPanel(1, i);
			Vector assVec = getListVector(assList);
			JList fixedList = this.getAListFromAssignedPanel(4, i);
			Vector fixedVec = getListVector(fixedList);
			for(int j=0; j< assVec.size(); j++){
				long matricule = Long.parseLong((String)assVec.get(j));
				boolean fixed = ((String)fixedVec.get(j)).equalsIgnoreCase(DConst.CHAR_FIXED_IN_GROUP);
				students.getStudent(matricule).setInGroup(course,group,fixed);
			}// end for(int i=0; i< notAssVec.size(); i++)
		}
		
	}//end method
	 /**
	  * Builds a JPanel containing just a JList
	  * @param theList
	  * @param panelWidth
	  * @param panelHeight
	  * @return
	  */
	 public static JPanel listPanel(JList theList, int panelWidth, int panelHeight){
	   JPanel panel = new JPanel(new BorderLayout());
	   panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
	   JScrollPane scrollPane = new JScrollPane();
	   scrollPane.setPreferredSize(new Dimension(panelWidth,panelHeight));
	   scrollPane.getViewport().add(theList);
	   panel.add(scrollPane);
	   return panel;
	 }
	 
	 private static JPanel listPanel(JPanel panel, JList[] lists,  int panelWidth, int panelHeight){
	 	panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
	 	Container hBox = Box.createHorizontalBox(); 
	 	int nameListPosition = 1;
	 	if(lists.length>0){
	 		hBox.add(lists[0]);
	 		for(int i=1; i< lists.length; i++){
	 			hBox.add(Box.createHorizontalStrut(2));
	 			hBox.add(lists[i]);
	 			if(i == nameListPosition){
	 				Dimension dim=new Dimension(45,80);
	 				lists[i].setPreferredSize(dim);
	 			}
	 				
	 		}// end for(int i=1; i< lists.length; i++)
	 	}// end if(lists.length>0)
	 	JScrollPane scrollPane = new JScrollPane();
	 	scrollPane.setPreferredSize(new Dimension(panelWidth,panelHeight));
	 	scrollPane.getViewport().add(hBox);
	 	//scrollPane.getViewport().add(aList);
	 	panel.add(scrollPane);
	 	return panel;
	  }
	 /**
	  * Builds a JPanel containing just a JList
	  * @param theList
	  * @param panelWidth
	  * @param panelHeight
	  * @return
	  */
	 public static JPanel listPanel(JList theList){
	   JPanel panel = new JPanel(new BorderLayout());
	   //panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
	   JScrollPane scrollPane = new JScrollPane();
	   //scrollPane.setPreferredSize(new Dimension(panelWidth,panelHeight));
	   scrollPane.getViewport().add(theList);
	   panel.add(scrollPane);
	   return panel;
	 }

	 /**
	  * Creates a Panel containing a valued title plus a listPanel
	  * @param panelDim The panel dimension
	  * @param list the list
	  * @param vec the vector source for the list
	  * @param labelsInfo The array containing the Strings to be displayed
	  * @param ml the Mouselistener for the list
	  * @return
	  */

	 public static JPanel setListPanel(Dimension panelDim, JList list, Vector vec, String [] labelsInfo, MouseListener ml){
	   Dimension infoPanelDim = new Dimension((int)panelDim.getWidth(), 20);
	   Dimension listPanelDim = new Dimension((int)panelDim.getWidth(), (int)(panelDim.getHeight() - infoPanelDim.getHeight()));
	   list.setListData(vec);
	   list.addMouseListener(ml);
	   JPanel listPanel = listPanel(list, (int)listPanelDim.getWidth(), (int)listPanelDim.getHeight());
	   //the panel
	   JPanel panel = new JPanel();
	   panel.setPreferredSize(panelDim);
	   panel.add(setInfoPanel(infoPanelDim, labelsInfo));
	   panel.add(listPanel);
	   //panel.setBorder(BorderFactory.createLineBorder(Color.black));
	   return panel;
	 }
	 
	  /**
	   * Builds a JPanel containig several labels arranged by couples.
	   * The first label of the couple represents the name of a quantity, the second
	   * label of the couple represents the value of that quantity. If there is
	   * only the names, the labels represents the titles
	   * @param parentDim The dimension of the panel containing this infoPanel
	   * @param items the items to be displayed
	   * @return the infoPanel
	   */
	  public static JPanel setInfoPanel(Dimension panelDim, String [] items){
	    JPanel infoPanel = new JPanel();
	    JLabel [] labels = new JLabel[items.length];
	    infoPanel.setPreferredSize(panelDim);
	    for (int i = 0; i < items.length; i++){
	      labels[i] = new JLabel(items[i]+ " ");
	      if (i%2 != 0){
	        labels[i].setForeground(DConst.COLOR_QUANTITY_DLGS);
	      }
	      infoPanel.add(labels[i]);
	    }
	    return infoPanel;
	  }

	/**
	 * build the list of the sort combo box
	 * @return
	 */
	private Vector buildSortVector() {
		Vector v = new Vector();
		v.add(DConst.SORT_BY_MATRICUL);
		v.add(DConst.SORT_BY_NAME);
		v.add(DConst.SORT_BY_PROGRAM);
		return v;
	}
	
	private void changeFixedInGroup(int index) {
				
	   	System.out.println("you changeFixedInGroup");
	   	System.out.println("index " + index);
	   	this.getAListFromAssignedPanel(1, _currentAssignedGroup);
		JList [] list = new JList[4];
		//JList [] listNotAss = new JList[4];
		Vector assDataM = new Vector();
		//Vector notAssDataM = new Vector();
		//int endSize=0;
		for(int j = 0; j < (list.length-1) ; j++){
			list[j] = this.getAListFromAssignedPanel(j+1, _currentAssignedGroup);
			//listNotAss[j] = this.getAListFromNotAssignedPanel(j+1);
			//Object[] notAssignedValue = listNotAss[j].getSelectedValues();
			assDataM =  getListVector(list[j]);
			/*notAssDataM =  getListVector(listNotAss[j]);
			for(int k=0; k< notAssignedValue.length; k++){
				notAssDataM.remove(notAssignedValue[k]);
				assDataM.add(notAssignedValue[k]);
			}// end for(int k=0; k< assignedIndice.length; k++)
			*/
			//endSize = assDataM.size();
			list[j].setListData(assDataM);
			/*list[j].setSelectedValue(notAssignedValue,true);
			if(listNotAss[j]!= null)
				listNotAss[j].setListData(notAssDataM);*/
		}// end for(int j = 0; j < list.length ; j++){
		
		list[3] = this.getAListFromAssignedPanel(4, _currentAssignedGroup);
		assDataM =  getListVector(list[3]);
		/*int beginSize = assDataM.size();
		for (int i = beginSize; i < endSize; i++)
			assDataM.add(DConst.CHAR_FIXED_IN_GROUP);
			
		list[3].setListData(assDataM);	*/
		String str = (String)assDataM.get(index);
		if (str.equals(DConst.CHAR_FIXED_IN_GROUP)) {
			assDataM.remove(index);
			assDataM.insertElementAt(" ",index);
		} else {
			assDataM.remove(index);
			assDataM.insertElementAt(DConst.CHAR_FIXED_IN_GROUP,index);
		}
		list[3].setListData(assDataM);	
		System.out.println("Elem at index " + str);
		setNumOfEltOfGpPanelFromAssigned(_currentAssignedGroup, String.valueOf(assDataM.size()));
		//sortAllPanel();
		setStudentsInGroups();
		_dApplic.getCurrentDModel().changeInDModelByStudentsDlg(this);
		//setAllLists();
		
      // String studentData = "";
       /*for (int i = 0; i < obj.length; i++) {
           studentData = (String) obj[i];
           //index = _assignedVectors[group].indexOf(studentData);
           if (studentData.endsWith(DConst.CHAR_FIXED_IN_GROUP)) {
               studentData = studentData.substring(0, studentData.length()
                       - DConst.CHAR_FIXED_IN_GROUP.length());
           } else {
               studentData = studentData + DConst.CHAR_FIXED_IN_GROUP;
           }
           //_assignedVectors[group].setElementAt(studentData, index);
       }*/
       //end for
       //_notAssignedList.setListData(_notAssignedVector);
   }
	
	private boolean goodSelectionInNotAssigned(JList [] listNotAss){
		int[] verif = new int[4];
		for (int j = 0; j < listNotAss.length-1; j++) {
			listNotAss[j] = this.getAListFromNotAssignedPanel(j + 1);
			verif[j] = listNotAss[j].getSelectedIndex();
		}
		return (verif[0] == verif[1]) && (verif[0] == verif[2]);
	}
	
}//end class
