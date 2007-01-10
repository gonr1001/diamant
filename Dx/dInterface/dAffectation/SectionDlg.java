/**
 *
 * Title: SectionDlg 
 * Description: SectionDlg is class used
 *           to display a dialog to modifiy students assignation 
 *           in sections
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
package dInterface.dAffectation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DxJComboBox;
import dInterface.dUtil.DxTools;
import dInterface.DDialog;
import dInterface.dUtil.RightLeftInterface;
import dInterface.dUtil.RigthLeftPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.DResource;
import dInternal.dData.StandardCollection;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.DSetOfResources;
import dInternal.dData.dStudents.SetOfStudents;
import dInternal.dData.dStudents.Student;
import dInternal.dData.dActivities.Type;
import eLib.exit.dialog.InformationDlg;

/**
 * 
 * @author gonr1001
 * 
 * SectionDlg
 */
public class SectionDlg extends DDialog implements ActionListener,
		RightLeftInterface, DlgIdentification {

	private DApplication _dApplic;

	private DModel _dmodel;

	private int _numberOfSections;

	private int _currentAssignedGroup;

	private int _sortIndex;

	private DxJComboBox _activitiesCombo;

	private DxJComboBox _typeCombo;

	private DxJComboBox _sortCombo;

	private JList _notAssignedList;

	private JList _assignedLists[];

	private RigthLeftPanel _rigthLeftPanel;

	private JPanel _assignedPanel;

	private JPanel _insidePanel;

	private JPanel _notAssignedPanel;

	private ButtonsPanel _applyPanel;

	private JScrollPane _scrollPane;

	private SetOfActivities _activities;

	private SetOfStudents _students;

	private String _selectedActivity;

	private String _selectedType;

	private Type _type;

	private Vector _activitiesVector;

	private Vector _notAssignedVector;

	private Vector _typeVector;

	private Vector _assignedVectors[];

	private Vector<String> _sortVector;

	/**
	 * Constructor
	 * 
	 * @param dApplic
	 */
	public SectionDlg(DApplication dApplic) {
		super(dApplic.getJFrame(), DConst.SECTION_DLG_TITLE, true);
		_dApplic = dApplic;
		//_sortIndex = 0; // why 0?
		_currentAssignedGroup = -1; // why -1
		if (dApplic.getCurrentDxDoc() == null)
			return;
		_dmodel = dApplic.getCurrentDxDoc().getCurrentDModel();
		_activities = _dmodel.getSetOfActivities();
		_students = _dmodel.getSetOfStudents();
		_students.sortSetOfResourcesByID();// is correct
		if (_activities != null && _students != null) {
			initializeDlg();
			int x = _dApplic.getJFrame().getX();
			int y = _dApplic.getJFrame().getY();
			this.setLocation(x + DConst.X_OFFSET, y + DConst.Y_OFFSET); //_dApplic.getJFrame());
			//            this.setMinimumSize(new Dimension(200, 200));
			//            this.setPreferredSize(new Dimension(600, 600)); //the real
			//            this.setMaximumSize(new Dimension(1000, 1500)); // XXXX Pascal: lien inutile avec JDK 1.5
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

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// combo
		//if activity combo box
		if (e.getSource().equals(_activitiesCombo)) {
			if (!_applyPanel.isFirstEnable()) {
				_selectedActivity = (String) _activitiesCombo.getSelectedItem();
				_typeVector = ((Activity) (_activities
						.getResource(_selectedActivity).getAttach()))
						.getSetOfTypes().getNamesVector(1);
				_typeCombo.disableActionListeners();
				_typeCombo.removeAllItems();
				_typeCombo.enableActionListeners();
				for (int i = 0; i < _typeVector.size(); i++) {
					_typeCombo.addItem(_typeVector.elementAt(i));
				}//end for
				_selectedType = (String) _typeVector.get(0);
				_type = getType(_selectedActivity, _selectedType);
				_numberOfSections = getNumberOfSections(_type);
				setListsLoad(false);
				setScrollPane(_scrollPane.getPreferredSize());

			} else {
				new InformationDlg(this, "Appliquer ou fermer pour continuer",
						"Operation interdite");
				_activitiesCombo.setSelectedItem(_selectedActivity);
			}
		}//end if (e.getSource().equals(_actCombo))
		//if type combo box
		if (e.getSource().equals(_typeCombo)) {
			if (!_applyPanel.isFirstEnable()) {
				_selectedType = (String) _typeCombo.getSelectedItem();
				_type = getType(_selectedActivity, _selectedType);
				_numberOfSections = getNumberOfSections(_type);
				setListsLoad(false);
				setScrollPane(_scrollPane.getPreferredSize());
			} else {
				new InformationDlg(this, "Appliquer ou fermer pour continuer",
						"Operation interdite");
				_typeCombo.setSelectedItem(_selectedType);
			}
			//_applyPanel.setFirstEnable();
		}//end if (e.getSource().equals(_typeCombo))
		//if sort button
		if (e.getSource().equals(_sortCombo)) {
			//int oldIndex = _sortIndex;
			_type = getType(_selectedActivity, _selectedType);
			_numberOfSections = getNumberOfSections(_type);
			//setLists(_sortIndex, false);
			setScrollPane(_scrollPane.getPreferredSize());
			//_applyPanel.setFirstEnable();
			setLists(_sortCombo.getSelectedIndex(), true);
			_sortIndex = _sortCombo.getSelectedIndex();
		}//end if (e.getSource().equals(_typeCombo))
		//if sort button

		//buttons
		//if Button CLOSE is pressed
		if (command.equals(DConst.BUT_CLOSE))
			dispose();
		//if Button APPLY is pressed
		if (command.equals(DConst.BUT_APPLY)) {
			setStudentsInGroups();

			_sortCombo.setEnabled(true);
			_activitiesCombo.setEnabled(true);
			_typeCombo.setEnabled(true);
			_applyPanel.setFirstDisable();

			_dmodel.getConditionsTest().setMatrixBuilded(false, false);
			_dmodel.changeInDModel(this.idDlgToString());
		}

	}//end method

	/**
	 * initializeDlg
	 *  
	 */
	private void initializeDlg() {
		Dimension dialogDim = new Dimension(550, 550);
		this.getContentPane().setLayout(new BorderLayout());
		setSize(dialogDim);
		setResizable(false);
		JPanel top = initTopPanel();
		getContentPane().add(top, BorderLayout.NORTH);

		//_applyPanel
		String[] a = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
		_applyPanel = new TwoButtonsPanel(this, a);
		_applyPanel.setFirstDisable();

		getContentPane().add(_applyPanel, BorderLayout.SOUTH);
		setListsLoad(false);

		JPanel _centerPanel = initCenterPanel();
		getContentPane().add(_centerPanel, BorderLayout.CENTER);
	} // initializeDlg

	/**
	 * 
	 * @return JPanel containing three panels to select an activity, a type and
	 *         an indicator for kind of sort
	 */
	private JPanel initTopPanel() {
		JPanel activityPanel = initActivityPanel();
		JPanel typePanel = initTypePanel();
		JPanel sortPanel = initSortPanel();

		_type = getType(_selectedActivity, _selectedType);
		_numberOfSections = getNumberOfSections(_type);
		JPanel topPanel = new JPanel();
		topPanel.add(activityPanel);
		topPanel.add(typePanel);
		topPanel.add(sortPanel);

		return topPanel;
	} //end initTopPanel

	/**
	 * 
	 * initialize _activitiesVector _activitiesCombo _selectedActivity
	 * 
	 * 
	 * @return JPanel the Activity Panel
	 */
	private JPanel initActivityPanel() {
		_activitiesVector = new Vector();
		// This vector contains the activities whose their visibility member =
		// true
		_activitiesVector = _activities.getIDsByField(3, "true");
		//panel of activities
		_activitiesCombo = new DxJComboBox(_activitiesVector);
		_activitiesCombo.addActionListener(this);
		JPanel activityPanel = new JPanel();
		activityPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACTIVITY));
		activityPanel.add(_activitiesCombo);
		_selectedActivity = (String) _activitiesVector.elementAt(0);
		return activityPanel;
	} //end initActivityPanel

	/**
	 * 
	 * initialize _typeVector _typeCombo _selectedType
	 * 
	 * 
	 * @return JPanel the Type Panel
	 *  
	 */
	private JPanel initTypePanel() {
		_typeVector = ((Activity) (_activities.getResource(_selectedActivity)
				.getAttach())).getSetOfTypes().getNamesVector(1);
		_typeCombo = new DxJComboBox(_typeVector);
		_typeCombo.addActionListener(this);

		JPanel typePanel = new JPanel();
		typePanel.setBorder(new TitledBorder(new EtchedBorder(), DConst.TYPE));
		typePanel.add(_typeCombo);
		_selectedType = (String) _typeVector.elementAt(0);
		return typePanel;
	} //end initTypePanel

	/**
	 * 
	 * initialize _sortVector _sortCombo _sortIndex
	 * 
	 * 
	 * @return JPanel the Type Panel
	 *  
	 */
	private JPanel initSortPanel() {
		_sortVector = buildSortVector();
		_sortCombo = new DxJComboBox(_sortVector);
		JPanel sortPanel = new JPanel();
		sortPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.SORT_TITLE));
		sortPanel.add(_sortCombo);
		_sortIndex = 0;
		_sortCombo.setSelectedIndex(_sortIndex);
		_sortCombo.addActionListener(this); // must be added here!
		return sortPanel;
	} //end initSortPanel

	/**
	 * Set the left panel who shows the list of the not assigned students
	 */
	private JPanel initNotAssignedPanel() {
		Dimension dialogDim = new Dimension(550, 550);
		Dimension panelDim = new Dimension(
				(int) ((dialogDim.getWidth() - 50) * 0.40), (int) dialogDim
						.getHeight() - 150);
		JPanel notAssignedPanel = new JPanel();
		//notAssignedPanel = DxTools.listPanel(_notAssignedList, (int) (panelDim
		//         .getWidth() - 112), (int) panelDim.getHeight() - 35);
		//JList jlistname = new JList(studentName());     
		//JList jlistMat = new JList(studentMatricule()); 
		//notAssignedPanel = DxTools.listPanel(jlistname,jlistMat, ((int)((550-50) *0.4) - 112),  550- 150 - 35);
		notAssignedPanel = DxTools.listPanel(_notAssignedList,
				((int) ((550 - 50) * 0.4) - 112), 550 - 150 - 35);
		notAssignedPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACT_STUD_NOT_ASSIGNED));
		//+++extract viewPort+++ ((JScrollPane)notAssignedPanel.getComponents()[0]).getViewport()
		//notAssignedPanel.setPreferredSize(panelDim);
		notAssignedPanel.setPreferredSize(panelDim);
		return notAssignedPanel;
	}

	/**
	 * Set _assignedPanel, the panel containing the lists of assigned students
	 */
	private JPanel initAssignedPanel() {
		Dimension dialogDim = new Dimension(550, 550);
		Dimension panelDim = new Dimension(
				(int) ((dialogDim.getWidth() - 50) * 0.55), (int) dialogDim
						.getHeight() - 150);
		Dimension scrollDim = new Dimension((int) panelDim.getWidth() - 10,
				(int) panelDim.getHeight() - 5);

		JPanel assignedPanel = new JPanel();
		assignedPanel = new JPanel(new BorderLayout());
		assignedPanel.setBorder(new TitledBorder(new EtchedBorder(),
				DConst.ACT_STUD_ASSIGNED));
		assignedPanel.setPreferredSize(panelDim);
		_scrollPane = new JScrollPane();
		_scrollPane.setPreferredSize(scrollDim);
		_insidePanel = new JPanel(new GridLayout(_numberOfSections, 1)); //It
		// is
		// contained
		// into
		// _scrollPane
		setScrollPane(scrollDim);
		assignedPanel.add(_scrollPane);
		return assignedPanel;
	}

	/**
	 * Set the panel contained in _scrollPane. This panel contains the
	 * sub-JScrollPanes corresponding to the groups in a type of activity
	 */
	private void setScrollPane(Dimension ScrollPaneDim) {
		int insideWidth = (int) ScrollPaneDim.getWidth() - 20;
		int scrollHeight = (int) ((ScrollPaneDim.getHeight() - 20) / 2); // rgr
		// 20
		// was
		// 10
		Dimension insideDim = new Dimension(insideWidth, scrollHeight
				* _numberOfSections + 10);
		JLabel[] lNumberOfElements = new JLabel[_numberOfSections];

		if (_insidePanel == null)
			_insidePanel = new JPanel();
		_insidePanel.setPreferredSize(insideDim);
		_insidePanel.removeAll();
		_insidePanel.setLayout(new GridLayout(_numberOfSections, 1));
		for (int i = 0; i < _numberOfSections; i++) {
			_insidePanel.add(setGroupPanel(i, lNumberOfElements[i]));
		}
		_scrollPane.setViewportView(_insidePanel);
		if (_numberOfSections == 1) {
			_rigthLeftPanel.setRigthDisable();
			_rigthLeftPanel.setLeftDisable();
		} else {
			_rigthLeftPanel.setRigthEnable();
			_rigthLeftPanel.setLeftEnable();
		}
		_currentAssignedGroup = -1;
	}//end method

	/**
	 * Sets the panel containing the list of students belonging a section (a
	 * group)
	 * 
	 * @param groupNumber
	 *            The SectionID
	 * @return a panel to be inserted into _insidePanel
	 */
	private JPanel setGroupPanel(int groupNumber, JLabel lNumberOfElements) {
		JLabel lnOfEl = lNumberOfElements;
		int numberOfElements = 0;
		int insideWidth = (int) _insidePanel.getPreferredSize().getWidth();
		int GroupPanelHeight = (int) ((_scrollPane.getPreferredSize()
				.getHeight()) / 2);
		int infoPanelHeight = 25;
		Dimension groupPanelDim = new Dimension(insideWidth, GroupPanelHeight);
		JPanel groupPanel = new JPanel();
		groupPanel.setPreferredSize(groupPanelDim);
		groupPanel.addMouseListener(mouseListenerGroupPanel);
		JPanel infoPanel = new JPanel();
		//The scrollPane
		JPanel scrollContainer = new JPanel();
		scrollContainer = DxTools.listPanel(_assignedLists[groupNumber],
				(insideWidth - 20), GroupPanelHeight - infoPanelHeight - 20);
		infoPanel.setPreferredSize(new Dimension(insideWidth - 10,
				infoPanelHeight));
		numberOfElements = _assignedVectors[groupNumber].size();
		JLabel lGroup = new JLabel(DConst.SECTION);
		//The infoPanel
		JLabel lGroupID = new JLabel(_type.getSetOfSections().getResourceAt(
				groupNumber).getID());
		lGroupID.setForeground(DConst.COLOR_QUANTITY_DLGS);
		JLabel lNumber = new JLabel(DConst.NUMBER_OF_ELEMENTS + " ");
		lnOfEl = new JLabel(String.valueOf(numberOfElements));
		lnOfEl.setForeground(DConst.COLOR_QUANTITY_DLGS);
		infoPanel.add(lGroup);
		infoPanel.add(lGroupID);
		infoPanel.add(new JLabel(" - "));
		infoPanel.add(lNumber);
		infoPanel.add(lnOfEl);
		//adding the sub-panels to the panel
		groupPanel.add(infoPanel);
		groupPanel.add(scrollContainer);
		return groupPanel;
	}

	/**
	 * Set _centerPanel, the panel containing _assignedPanel, _arrowsPanel and
	 * _notAssignedPanel
	 */
	private JPanel initCenterPanel() {
		_notAssignedPanel = initNotAssignedPanel();//dialogDim);
		_rigthLeftPanel = new RigthLeftPanel(this);//_arrowsPanel = DxTools.arrowsPanel(this, _arrowsNames, true);
		_assignedPanel = initAssignedPanel();
		if (_numberOfSections == 1) {
			_rigthLeftPanel.setRigthDisable();
			_rigthLeftPanel.setLeftDisable();
		} else {
			_rigthLeftPanel.setRigthEnable();
			_rigthLeftPanel.setLeftEnable();
		}
		JPanel centerPanel = new JPanel();
		centerPanel.add(_notAssignedPanel);
		centerPanel.add(_rigthLeftPanel);
		centerPanel.add(_assignedPanel);
		return centerPanel;
	}// end initCenterPanel

	/**
	 * Set Type of _type and _numberOfSections according with the selected
	 * activity and the selected type
	 */
	private Type getType(String selectedActivity, String selectedType) {
		Activity act = (Activity) (_activities.getResource(selectedActivity)
				.getAttach());
		return (Type) act.getSetOfTypes().getResource(selectedType).getAttach();
	}

	private int getNumberOfSections(Type type) {
		return type.getSetOfSections().size();
	}

	/**
	 * The mouseListener for the JLists
	 */
	private MouseListener mouseListenerLists = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(_notAssignedList)) {
				if (_notAssignedVector.size() != 0) {
					for (int i = 0; i < _numberOfSections; i++)
						_assignedLists[i].clearSelection();
				}//end if(_notAssignedVector.size() != 0)
			}//end if (e.getSource().equals(_notAssignedList))
			for (int i = 0; i < _numberOfSections; i++) {
				if (e.getSource().equals(_assignedLists[i])) {
					_currentAssignedGroup = i;
					setGroupBorders(_currentAssignedGroup);//, Color.blue);
					if ((_assignedVectors[i]).size() != 0) {
						_notAssignedList.clearSelection();
					}
					for (int j = 0; j < _numberOfSections; j++) {
						if (j != i) {
							_assignedLists[j].clearSelection();
						}
					}
					if (e.getClickCount() == 2) {
						changeFixedInGroup(((JList) e.getSource())
								.getSelectedValues(), _currentAssignedGroup);
						_applyPanel.setFirstEnable();
					}//end if(e.getClickCount() == 2)
				}//if (e.getSource().equals(_assignedLists[i]))
			}//end for(int i = 0; i<_numberOfSections; i++)

		}// end public void mouseClicked
	};//end definition of MouseListener mouseListener = new MouseAdapter(){

	private MouseListener mouseListenerGroupPanel = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			for (int i = 0; i < _numberOfSections; i++) {
				if (e.getSource().equals(_insidePanel.getComponent(i))) {
					_currentAssignedGroup = i;
				} else {
					_assignedLists[i].clearSelection();
				}
			}//end for(int i = 0; i<_numberOfSections; i++)
			setGroupBorders(_currentAssignedGroup);//, Color.blue);
		}// end public void mouseClicked
	};//end definition of MouseListener mouseListener = new MouseAdapter(){

	/**
	 * Set the clor border of the groupPanels. Clor = blue for the panel
	 * selected, null for the other panels
	 * 
	 * @param selectedPanelID
	 * @param colorBorder
	 */
	private void setGroupBorders(int selectedPanelID) {
		JPanel panel;
		for (int i = 0; i < _numberOfSections; i++) {
			panel = (JPanel) _insidePanel.getComponent(i);
			if (i == selectedPanelID)
				panel.setBorder(BorderFactory
						.createLineBorder(DConst.COLOR_QUANTITY_DLGS));
			else
				panel.setBorder(null);
		}
	}//end method

	private SetOfStudents getSortStudents(JList list, int group) {

		SetOfStudents students = new SetOfStudents();
		if (list != null) {
			for (int i = 0; i < list.getModel().getSize(); i++) {
				String str = (String) list.getModel().getElementAt(i);
				Student studentRequest = (Student) getStudent(str);
				Student studAtt = new Student(studentRequest.getID());
				studAtt.addCourses(_selectedActivity + _selectedType);
				if (str.endsWith(DConst.CHAR_FIXED_IN_GROUP))
					studAtt.setInGroup(_selectedActivity + _selectedType,
							group, true);
				else
					studAtt.setInGroup(_selectedActivity + _selectedType,
							group, false);

				studAtt.setAuxField(studentRequest.getAuxField());
				students.setCurrentKey(studentRequest.getKey());
				students.addResource(studAtt, 0);
			}
		}
		return students;
	}

	private void setListsLoad(boolean forUpdate) {
		_notAssignedVector = _students.getStudentsByGroup(_selectedActivity,
				(String) _typeVector.elementAt(_typeCombo.getSelectedIndex()),
				-1, _sortIndex);// to change
		if (_notAssignedList == null) {
			_notAssignedList = new JList(_notAssignedVector);
			_notAssignedList.setFont(DConst.JLISTS_FONT);
			_notAssignedList.addMouseListener(mouseListenerLists);
		} else
			_notAssignedList.setListData(_notAssignedVector);
		if (!forUpdate) {
			_assignedVectors = new Vector[_numberOfSections];
			_assignedLists = new JList[_numberOfSections];
		}
		Type type = _activities.getType(_selectedActivity, _selectedType);
		for (int i = 0; i < type.getSetOfSections().size(); i++) {
			int group = DxTools.STIConvertGroupToInt(type.getSetOfSections()
					.getResourceAt(i).getID());
			//for(int i = 0; i < _numberOfSections; i++){
			_assignedVectors[i] = _students.getStudentsByGroup(
					_selectedActivity, _selectedType, group, _sortIndex);// to change
			//System.out.println("_assignedVectors[i] "+_assignedVectors[i]);
			if (!forUpdate) {
				_assignedLists[i] = new JList(_assignedVectors[i]);
				_assignedLists[i].setFont(DConst.JLISTS_FONT);
				_assignedLists[i].addMouseListener(mouseListenerLists);
			} else
				_assignedLists[i].setListData(_assignedVectors[i]);
		}
	}//end method

	private void setLists(int newIndex, boolean forUpdate) {
		_notAssignedVector = getSortStudents(_notAssignedList, -1)
				.getStudentsByGroup(_selectedActivity, _selectedType, -1,
						newIndex);
		if (_notAssignedList == null) {
			_notAssignedList = new JList(_notAssignedVector);
			_notAssignedList.setFont(DConst.JLISTS_FONT);
			_notAssignedList.addMouseListener(mouseListenerLists);
		} else
			_notAssignedList.setListData(_notAssignedVector);
		if (!forUpdate) {
			_assignedVectors = new Vector[_numberOfSections];
			_assignedLists = new JList[_numberOfSections];
		}
		//RGR RGR RGR for groups
		Type type = _activities.getType(_selectedActivity, _selectedType);
		for (int i = 0; i < type.getSetOfSections().size(); i++) {
			int group = DxTools.STIConvertGroupToInt(type.getSetOfSections()
					.getResourceAt(i).getID());
			//for(int i = 0; i < _numberOfSections; i++){
			_assignedVectors[i] = getSortStudents(_assignedLists[i],/* newIndex, */
			group).getStudentsByGroup(_selectedActivity, _selectedType, group,
					newIndex);
			//System.out.println("_assignedVectors[i] "+_assignedVectors[i]);
			if (!forUpdate) {
				_assignedLists[i] = new JList(_assignedVectors[i]);
				_assignedLists[i].setFont(DConst.JLISTS_FONT);
				_assignedLists[i].addMouseListener(mouseListenerLists);
			} else
				_assignedLists[i].setListData(_assignedVectors[i]);
		}
	}//end method

	/**
	 * Sets the students in the groups indicated by the JLists
	 */
	private void setStudentsInGroups() {
		Student s;
		String studentData;
		for (int i = 0; i < _notAssignedVector.size(); i++) {
			studentData = (String) _notAssignedVector.elementAt(i);
			s = (Student) getStudent(studentData);
			s.setInGroup(_selectedActivity + _selectedType, -1, false);
		}//end for(int i = 0; i < _notAssignedVector.size(); i++)
		Type type = _activities.getType(_selectedActivity, _selectedType);
		for (int j = 0; j < _assignedVectors.length; j++) {
			int group = DxTools.STIConvertGroupToInt(type.getSetOfSections()
					.getResourceAt(j).getID());
			for (int k = 0; k < _assignedVectors[j].size(); k++) {
				studentData = (String) _assignedVectors[j].elementAt(k);
				s = (Student) getStudent(studentData);

				if (studentData.endsWith(DConst.CHAR_FIXED_IN_GROUP))
					s
							.setInGroup(_selectedActivity + _selectedType,
									group, true);
				else
					s.setInGroup(_selectedActivity + _selectedType, group,
							false);
			}//end for(int k = 0; k < _assignedVectors[j].size(); k++)
		}//end for(int j = 0; j < _assignedVectors.length; j++)
	}//end method

	@SuppressWarnings("unchecked")
	private void changeFixedInGroup(Object[] obj, int group) {
		//boolean fixedInGroup;
		int index = 0;
		String studentData = "";
		for (int i = 0; i < obj.length; i++) {
			studentData = (String) obj[i];
			index = _assignedVectors[group].indexOf(studentData);
			if (studentData.endsWith(DConst.CHAR_FIXED_IN_GROUP)) {
				studentData = studentData.substring(0, studentData.length()
						- DConst.CHAR_FIXED_IN_GROUP.length());
			} else {
				studentData = studentData + DConst.CHAR_FIXED_IN_GROUP;
			}

			_assignedVectors[group].setElementAt(studentData, index);
		}//end for
		_notAssignedList.setListData(_notAssignedVector);
	}

	private void listTransfers(JList sourceList, JList destinationList,
			Vector sourceVector, Vector destinationVector, String chain,
			boolean toLeft, int sortIndex) {
		if (sourceList == null || destinationList == null
				|| sourceVector == null || destinationVector == null)
			return;
		DSetOfResources destinationRes = new StandardCollection();
		DResource res;
		Object[] elementsToTransfer = sourceList.getSelectedValues();
		String strElement;//, ID, key;
		if (elementsToTransfer.length != 0) {
			for (int i = 0; i < elementsToTransfer.length; i++) {
				sourceVector.remove(elementsToTransfer[i]);
				strElement = (String) elementsToTransfer[i];
				if (toLeft) {
					if (strElement.endsWith(chain))
						elementsToTransfer[i] = strElement.substring(0,
								strElement.length() - chain.length());
				} else {
					elementsToTransfer[i] = strElement + chain;
				}
				destinationVector.add(elementsToTransfer[i]);
			}
			for (int j = 0; j < destinationVector.size(); j++) {
				res = new DResource((String) destinationVector.elementAt(j),
						null);
				destinationRes.addResource(res, 1);
			}
			if (sortIndex == 0)
				destinationRes.sortSetOfResourcesByID();
			if (sortIndex == 1)
				destinationRes.sortSetOfResourcesByKey();
			destinationVector.removeAllElements();
			destinationRes.getNamesVector(destinationVector);
			sourceList.setListData(sourceVector);
			destinationList.setListData(destinationVector);
			int[] indices = DxTools.getIndicesOfIntersection(destinationVector,
					elementsToTransfer);
			destinationList.setSelectedIndices(indices);
			sourceList.clearSelection();
		}//end for
	}//end method

	private DResource getStudent(String studentData) {
		DResource s;
		String studentID = null;
		long studentKey;
		if (_sortIndex == 0)
			studentID = studentData.substring(DConst.STUDENT_ID_LENGTH + 1,
					DConst.STUDENT_ID_LENGTH + 1 + DConst.STUDENT_KEY_LENGTH)
					.trim();
		if (_sortIndex == 1)
			studentID = studentData.substring(0, DConst.STUDENT_KEY_LENGTH)
					.trim();
		if (_sortIndex == 2)
			studentID = studentData.substring(
					DConst.STUDENT_PROGRAM_LENGTH + DConst.STUDENT_ID_LENGTH
							+ 1,
					DConst.STUDENT_PROGRAM_LENGTH + DConst.STUDENT_ID_LENGTH
							+ 1 + DConst.STUDENT_KEY_LENGTH + 1).trim();

		studentKey = Long.parseLong(studentID);
		s = _students.getResource(studentKey);// to check
		return s;
	}//end method

	private Vector<String> buildSortVector() {
		Vector<String> v = new Vector<String>();
		v.add(DConst.SORT_BY_NAME);
		v.add(DConst.SORT_BY_MATRICUL);
		v.add(DConst.SORT_BY_PROGRAM);
		return v;
	}

	/* (non-Javadoc)
	 * @see dInterface.dUtil.RightLeftInterface#rightPressed()
	 */
	public void rightPressed() {
		if (_currentAssignedGroup > -1) {
			//System.out.println("rightPressed");
			listTransfers(_notAssignedList,
					_assignedLists[_currentAssignedGroup], _notAssignedVector,
					_assignedVectors[_currentAssignedGroup],
					DConst.CHAR_FIXED_IN_GROUP, false, _sortIndex);
			_applyPanel.setFirstEnable();

			_activitiesCombo.setEnabled(false);
			_typeCombo.setEnabled(false);
			//_applyClosePanel.setApplyEnable();

			((JLabel) ((JPanel) ((JPanel) _insidePanel
					.getComponent(_currentAssignedGroup)).getComponent(0))
					.getComponent(4)).setText(String
					.valueOf(_assignedVectors[_currentAssignedGroup].size()));
		}
	}

	/* (non-Javadoc)
	 * @see dInterface.dUtil.RightLeftInterface#leftPressed()
	 */
	public void leftPressed() {
		if (_currentAssignedGroup > -1) {
			//System.out.println("leftPressed");
			//			doClickOnArrowToLeft();
			listTransfers(_assignedLists[_currentAssignedGroup],
					_notAssignedList, _assignedVectors[_currentAssignedGroup],
					_notAssignedVector, DConst.CHAR_FIXED_IN_GROUP, true,
					_sortIndex);
			//_buttonsPanel.getComponent(1).setEnabled(true);
			//_sortCombo.setEnabled(false);
			_applyPanel.setFirstEnable();

			_activitiesCombo.setEnabled(false);
			_typeCombo.setEnabled(false);
			//_applyClosePanel.setApplyEnable();
			((JLabel) ((JPanel) ((JPanel) _insidePanel
					.getComponent(_currentAssignedGroup)).getComponent(0))
					.getComponent(4)).setText(String
					.valueOf(_assignedVectors[_currentAssignedGroup].size()));
		}
	}

	/* (non-Javadoc)
	 * @see dInterface.dUtil.ApplyCloseInterface#applyPressed()
	 */
	public void applyPressed() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dInterface.dUtil.ApplyCloseInterface#closePresed()
	 */
	public void closePresed() {
		// TODO Auto-generated method stub

	}

	public String idDlgToString() {
		return this.getClass().toString();
	}

}//end SectionDlg
