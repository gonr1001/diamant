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
package dInterface.dAssignementDlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.dUtil.ButtonsPanel;
import dInterface.dUtil.DXJComboBox;
import dInterface.dUtil.TwoButtonsPanel;
import dInternal.DModel;
import dInternal.dData.DxAvailability;
import dInternal.dData.dRooms.DxCategory;
import dInternal.dData.dRooms.DxRoom;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSite;

public class DxRoomAvailabilityDlg extends JDialog implements ActionListener,
        ItemListener {

    private int _nbOfPeriods;

    private int _nbOfDays;

    private String[] _days;

    public String[] _time;

    private ButtonsPanel _applyPanel;

    private JPanel _chooserPanel;

    private JPanel _centerPanel;

    private DXJComboBox _cbSites;

    private DXJComboBox _cbCategories;

    private DXJComboBox _cbRooms;

    /**
     * @associates JToggleButton
     */
    private Vector<JToggleButton> _posVect;

    private DModel _dmodel;

    private DxSetOfSites _dxsosSites;

    private DxSite _dxsCurrentSite;

    private DxCategory _dxcCurrentCat;

    private DxRoom _dxrCurrentRoom;

    private boolean _isMultiSite;

    private int[][] _currentAvailbility;

    public DxRoomAvailabilityDlg(DApplication dApplic, DxSetOfSites dxsosSites,
            boolean isMultisite) {
        super(dApplic.getJFrame(), DConst.ROOMASSIGN, false);
        if (dApplic.getCurrentDoc() == null)
            return;

        _isMultiSite = isMultisite;
        _dmodel = dApplic.getCurrentDModel();
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
    } // end AvailabiltyDialog

    /**
     * Component's initialisation and placement.
     */
    private void initialize() throws Exception {
        Vector<String> vTemp;
        _chooserPanel = new JPanel();

        if (_isMultiSite) {
            // creates the JComboBox with the list of all sites and add an entry
            // to
            // display all sites
            vTemp = _dxsosSites.getNamesVector();
            _cbSites = new DXJComboBox(vTemp);
            _cbSites.addItemListener(this);
            _dxsCurrentSite = _dxsosSites.getSite((String) _cbSites
                    .getSelectedItem());

            vTemp = _dxsCurrentSite.getSetOfCat().getNamesVector();
            _cbCategories = new DXJComboBox(vTemp);
            _cbCategories.addItemListener(this);
            _dxcCurrentCat = _dxsCurrentSite.getCat((String) _cbCategories
                    .getSelectedItem());

            vTemp = _dxcCurrentCat.getSetOfRooms().getNamesVector();

            _chooserPanel.add(_cbSites, null);
            _chooserPanel.add(_cbCategories, null);
        } else {
            _dxsCurrentSite = _dxsosSites.getSite(DConst.ROOM_STANDARD_SITE);
            _dxcCurrentCat = _dxsCurrentSite.getCat(DConst.ROOM_STANDARD_CAT);
        }

        vTemp = _dxcCurrentCat.getSetOfRooms().getNamesVector();
        _cbRooms = new DXJComboBox(vTemp);
        _cbRooms.addItemListener(this);
        _dxrCurrentRoom = _dxcCurrentCat.getRoom((String) _cbRooms
                .getSelectedItem());

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
            // _currentInstr.setAvailability(_currentAvailbility);
            _dmodel.changeInDModelByInstructorsDlg(this);
            // if a button of the grid has been pressed
        } else if (_posVect.indexOf(event.getSource()) > -1) {
            int index = _posVect.indexOf(event.getSource());
            int day = index / _nbOfPeriods;
            int per = index % _nbOfPeriods;
            if (_posVect.get(index).isSelected()) {
                _currentAvailbility[day][per] = 1;
            } else {
                _currentAvailbility[day][per] = 5;
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
            if (source.equals(_cbSites)) {
                nSwitch = 1;
            } else if (source.equals(_cbCategories)) {
                nSwitch = 2;
            } else if (source.equals(_cbRooms)) {
                nSwitch = 3;
            }

            switch (nSwitch) {
            case 1:
                _dxsCurrentSite = _dxsosSites.getSite((String) _cbSites
                        .getSelectedItem());
                
                Vector <String> vTemp = _dxsCurrentSite.getSetOfCat().getNamesVector();
                _cbCategories.disableActionListeners();
                _cbCategories = new DXJComboBox(vTemp);
                _cbCategories.enableActionListeners();
                _dxcCurrentCat = _dxsCurrentSite.getCat((String) _cbCategories
                        .getSelectedItem());
            case 2:
            case 3:
            default:
                break;
            }
            getContentPane().remove(_centerPanel);
            String sel = (String) _cbSites.getSelectedItem();
            // _currentInstr = (AvailabilityAttach) _setOfResources
            // .getResource(sel).getAttach();
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

        // _currentAvailbility = _currentInstr.getMatrixAvailability();

        for (int j = 0; j < _nbOfPeriods; j++) {
            // first column : the time of the period

            gridPanel.add(new JLabel(_time[j], SwingConstants.RIGHT));
            // create a button for each day for the period
            // System.out.println(" DAInstructorDialog NbDays: "+nbDay+"
            // NbPerDays: "+nbPer); //DEBUG
            for (int i = 0; i < _nbOfDays; i++) {
                JToggleButton tBut = new JToggleButton();
                if (_currentAvailbility[i][j] == 1) {
                    // Vector assignedSites =
                    // _currentInstr.isAssignedInPeriod(i,
                    // j, _dmodel.getOtherSites());
                    // if (assignedSites.size() != 0) {
                    // Color col = this.getGridColor((String) assignedSites
                    // .get(0));
                    // if (col == Color.RED || col == Color.BLUE
                    // || col == Color.GREEN) {
                    // tBut.setToolTipText(DConst.NOT_DISPO);
                    // }
                    // tBut.setBackground(col);
                    // tBut.setEnabled(false);
                    // } else
                    // tBut.setSelected(_currentAvailbility[i][j] == 1);
                }
                tBut.addActionListener(this);
                tBut.setPreferredSize(new Dimension(50, 12));
                gridPanel.add(tBut);// , null);
                _posVect.setElementAt(tBut, (i * _nbOfPeriods) + j);
            }
        }
        return gridPanel;
    }

    private Color getGridColor(String site) {
        if (site.equalsIgnoreCase(DConst.USEDSHE)) {
            return Color.RED;
        } else if (site.equalsIgnoreCase(DConst.USEDLON)) {
            return Color.BLUE;
        } else if (site.equalsIgnoreCase(DConst.USEDCOW)) {
            return Color.GREEN;
        }
        return Color.GRAY;
    }

} /* end InstructorAvailabilityDlg */