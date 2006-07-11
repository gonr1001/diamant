/*
 *  Title: DxRoomAvailabilityDlg: Created on 2006-06-05
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 */
/**
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 */
package dInterface.dAssignementDlgs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.DlgIdentification;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dData.DxAvailability;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;

public class DxRoomAvailabilityDlg extends JDialog implements ActionListener,
        ItemListener, DlgIdentification {

    private int _nbOfPeriods;

    private int _nbOfDays;

    private String[] _days;

    public String[] _time;

    private ButtonsPanel _applyPanel;

    private JPanel _chooserPanel;

    private JPanel _centerPanel;

    private JComboBox _cbSites;

    private DefaultComboBoxModel _dcbmSites;

    private JComboBox _cbCategories;

    private DefaultComboBoxModel _dcbmCategories;

    private JComboBox _cbRooms;

    private DefaultComboBoxModel _dcbmRooms;

    /**
     * @associates JToggleButton
     */
    private Vector<JToggleButton> _posVect;

    private DModel _dmodel;

    private DxSetOfSites _dxsosSites;

    private DxSite _dxsCurrentSite;

    private DxCategory _dxcCurrentCat;

    private DxRoom _dxrCurrentRoom;

    private int[][] _dxaCurrentAvailbility;

    public DxRoomAvailabilityDlg(DApplication dApplic, DxSetOfSites dxsosSites) {
        super(dApplic.getJFrame(), DConst.ROOMASSIGN + "rgr", false);
   
		if (DConst.newDoc) {
			if (dApplic.getCurrentDxDoc() == null)
				return;
			_dmodel = dApplic.getCurrentDxDoc().getCurrentDModel();
		} else {
			if (dApplic.getCurrentDoc() == null)
				return;
			_dmodel = dApplic.getCurrentDModel();
		}
        
        _dxsosSites = dxsosSites;

        _time = _dmodel.getTTStructure().getCurrentCycle()
                .getHourOfPeriodsADay();

        _nbOfPeriods = _dmodel.getTTStructure().getCurrentCycle()
                .getMaxNumberOfPeriodsADay();
        _nbOfDays = _dmodel.getTTStructure().getNumberOfActiveDays();

        _days = _dmodel.getTTStructure().getDayNames();

        try {
            initialize();
            pack();
            setLocationRelativeTo(dApplic.getJFrame());
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end DxRoomAvailabilityDlg

    /**
     * Component's initialisation and placement.
     */
    private void initialize() throws Exception {
        _chooserPanel = new JPanel();

        // creates the JComboBox with the list of all sites and add an entry
        // to
        // display all sites
        _dcbmSites = new DefaultComboBoxModel(_dxsosSites
                .getSitesSortedByName());
        _cbSites = new JComboBox(_dcbmSites);
        _cbSites.addItemListener(this);
        _dxsCurrentSite = (DxSite) _cbSites.getSelectedItem();

        _dcbmCategories = new DefaultComboBoxModel(_dxsCurrentSite
                .getSetOfCat().getCatsSortedByName());
        _cbCategories = new JComboBox(_dcbmCategories);
        _cbCategories.addItemListener(this);
        _dxcCurrentCat = (DxCategory) _cbCategories.getSelectedItem();

        _dcbmRooms = new DefaultComboBoxModel(_dxcCurrentCat.getSetOfRooms()
                .getRoomsSortedByName());
        _cbRooms = new JComboBox(_dcbmRooms);
        _cbRooms.addItemListener(this);
        _dxrCurrentRoom = (DxRoom) _cbRooms.getSelectedItem();

        _dxaCurrentAvailbility = _dxrCurrentRoom.getAvailability()
                .getMatrixAvailability();

        // TODO: Create StringRes and const for labels
        _chooserPanel.add(new JLabel("Sites: "), null);
        _chooserPanel.add(_cbSites, null);
        _chooserPanel.add(new JLabel("Catégorie: "), null);
        _chooserPanel.add(_cbCategories, null);
        _chooserPanel.add(new JLabel("Local: "), null);
        _chooserPanel.add(_cbRooms, null);

        this.getContentPane().add(_chooserPanel, BorderLayout.NORTH);

        // gridPanel
        _centerPanel = makeGridPanel();
        this.getContentPane().add(_centerPanel, BorderLayout.CENTER);

        // _applyPanel
        String[] butnames = { DConst.BUT_APPLY, DConst.BUT_CLOSE };
        _applyPanel = new TwoButtonsPanel(this, butnames);
        // Setting the button APPLY disable
        _applyPanel.setFirstDisable();
        this.getContentPane().add(_applyPanel, BorderLayout.SOUTH);
    } // end initialize()

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals(DConst.BUT_CLOSE)) { // close
            dispose();
        } else if (command.equals(DConst.BUT_APPLY)) { // apply
            _applyPanel.setFirstDisable();
            
            
            _dxrCurrentRoom.setAvailability(new DxAvailability(
                    _dxaCurrentAvailbility));
			if (DConst.newDoc) {
				_dmodel.changeInDModel(this.idDlgToString());
			} else {
				_dmodel.changeInDModelByInstructorsDlg(this);
			}
            // if a button of the grid has been pressed
        } else if (_posVect.indexOf(event.getSource()) > -1) {
            int index = _posVect.indexOf(event.getSource());
            int day = index / _nbOfPeriods;
            int per = index % _nbOfPeriods;
            if (_posVect.get(index).isSelected()) {
                _dxaCurrentAvailbility[day][per] = 1;
            } else {
                _dxaCurrentAvailbility[day][per] = 5;
            }
            // modified = true;
            _applyPanel.setFirstEnable();
        }
    }

    /**
     * combobox item selected
     */
    public void itemStateChanged(ItemEvent event) {
        int nSwitch = 0;

        _applyPanel.setFirstDisable();
        if (event.getStateChange() == ItemEvent.SELECTED) {
            Object source = event.getSource();

            // Determines which combox has changed so we know which actions we
            // need to perform
            if (source.equals(_cbSites)) {
                nSwitch = 1;
            } else if (source.equals(_cbCategories)) {
                nSwitch = 2;
            } else if (source.equals(_cbRooms)) {
                nSwitch = 3;
            }

            switch (nSwitch) {
            case 1:
                _dxsCurrentSite = (DxSite) _cbSites.getSelectedItem();

                _dcbmCategories = new DefaultComboBoxModel(_dxsCurrentSite
                        .getSetOfCat().getCatsSortedByName());
                _cbCategories.setModel(_dcbmCategories);

            case 2:
                _dxcCurrentCat = (DxCategory) _cbCategories.getSelectedItem();
                _dcbmRooms = new DefaultComboBoxModel(_dxcCurrentCat
                        .getSetOfRooms().getRoomsSortedByName());
                _cbRooms.setModel(_dcbmRooms);

            case 3:
                _dxrCurrentRoom = (DxRoom) _cbRooms.getSelectedItem();

            default:
                break;
            }

            getContentPane().remove(_centerPanel);
            _dxaCurrentAvailbility = _dxrCurrentRoom.getAvailability()
                    .getMatrixAvailability();
            _centerPanel = makeGridPanel();// _currentInstr);
            getContentPane().add(_centerPanel, BorderLayout.CENTER);
            pack();
        }
    }// end itemStateChangeed

    /**
     * Creates the grid of button. The button is pressed if the instructor is
     * free at that period, depressed if not.
     * 
     * @param instr
     *            the instructor for which the grid is constructed.
     */
    private JPanel makeGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(_nbOfPeriods + 1, _nbOfDays + 1));
        gridPanel.setBorder(BorderFactory
                .createTitledBorder(DConst.AVAILABILITIES));
        _posVect = new Vector<JToggleButton>();
        _posVect.setSize((_nbOfPeriods + 1) * (_nbOfDays + 1));
        gridPanel.add(new JLabel("")); // top left corner
        for (int i = 0; i < _days.length; i++)
            // first line : name of days
            gridPanel.add(new JLabel(_days[i], SwingConstants.CENTER));

        _dxaCurrentAvailbility = _dxrCurrentRoom.getAvailability()
                .getMatrixAvailability();

        for (int j = 0; j < _nbOfPeriods; j++) {
            // first column : the time of the period

            gridPanel.add(new JLabel(_time[j], SwingConstants.RIGHT));
            // create a button for each day for the period
            // System.out.println(" DAInstructorDialog NbDays: "+nbDay+"
            // NbPerDays: "+nbPer); //DEBUG
            for (int i = 0; i < _nbOfDays; i++) {
                JToggleButton tBut = new JToggleButton();
                tBut.setSelected(_dxaCurrentAvailbility[i][j] == 1);
                tBut.addActionListener(this);
                tBut.setPreferredSize(new Dimension(50, 12));
                gridPanel.add(tBut);// , null);
                _posVect.setElementAt(tBut, (i * _nbOfPeriods) + j);
            }
        }
        return gridPanel;
    }

	public String idDlgToString() {
		return this.getClass().toString();
	}

} /* end InstructorAvailabilityDlg */