/**
 *
 * Title: DToolBar
 * Description: ToolBar is a class used to display a
 *               toolbar with buttons
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr-fdl. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr-fdl.
 *
 *
 *
 */
package dInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import dConstants.DConst;
import dInterface.dTimeTable.DxPeriodPanel;
import dInterface.dUtil.DxJComboBox;
import dInternal.DResource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dTimeTable.TTStructure;

import dInternal.dUtil.DXToolsMethods;
import eLib.exit.dialog.InformationDlg;

// -------------------------------------------
/**
 * 
 * DToolBar is a class used to display and handle two toolbars with buttons and
 * JComboBoxes. One Toolbar is used to make changes in day and the other on
 * periods
 * 
 */
@SuppressWarnings("serial")
public class DToolBar extends JToolBar implements Observer, DlgIdentification {

	private DApplication _dApplic;

	private String _toolBarNames[];

	private JComboBox _toolBarSelector;

	private DxJComboBox _daySelector, _dayNameSelector, _periodSelector,
			_periodTypeSelector;

	private boolean _comboBoxStatus = true;

	private JButton _sameLine, _sameColumn;

	private JTextField _setNumberOfDays;

	private JLabel _lSetNumberOfDays, _lDaySelector, _lDayNameSelector,
			_lPeriodIndicator, _lPeriodTypeSelector;

	private TTStructure _tts;

	// -------------------------------------------
	public DToolBar(DApplication dApplic) {
		_dApplic = dApplic;
		_toolBarNames = new String[2];
		_toolBarNames[0] = DConst.TB_DAYS;
		_toolBarNames[1] = DConst.TB_PER;
		init();
		actionManager();
		setEnabledToolbar(false);
	}// end constructor

	private void init() {
		final int c = 2;
		// the labels in the bar
		_lSetNumberOfDays = new JLabel("Nombre de jours ");
		_lDaySelector = new JLabel("Jour courant ");
		_lDayNameSelector = new JLabel("Nom du jour ");

		_lPeriodIndicator = new JLabel("Index P�riode ");
		_lPeriodTypeSelector = new JLabel("Priorit� P�riode ");

		// JComboBox toolBarSelector initialization
		_toolBarSelector = new JComboBox(_toolBarNames);
		_toolBarSelector.setPreferredSize(new Dimension(200, DConst.NPT11 * c));
		_toolBarSelector.setMaximumSize(new Dimension(200, DConst.NPT11 * c));
		add(_toolBarSelector);

		// textField set number of days in ttable Structure
		_setNumberOfDays = new JTextField();
		_setNumberOfDays.setMaximumSize(new Dimension(30, DConst.NPT11 * c));

		// JComboBox daySelector initialization
		String[] daySelector = { "1", "2", "3", "4", "5", "6", "7" };
		_daySelector = new DxJComboBox(daySelector);
		_daySelector.setPreferredSize(new Dimension(50, DConst.NPT11 * c));
		_daySelector.setMaximumSize(new Dimension(50, DConst.NPT11 * c));

		// JComboBox dayNameSelector initialization
		_dayNameSelector = new DxJComboBox(TTStructure._weekTable);
		_dayNameSelector.setPreferredSize(new Dimension(50, DConst.NPT11 * c));
		_dayNameSelector.setMaximumSize(new Dimension(50, DConst.NPT11 * c));

		// JComboBox periodSelector initialization
		String[] periodSelector = { "1", "2", "3", "4", "5", "6", "7" };
		_periodSelector = new DxJComboBox(periodSelector);
		_periodSelector.setPreferredSize(new Dimension(50, DConst.NPT11 * c));
		_periodSelector.setMaximumSize(new Dimension(50, DConst.NPT11 * c));
		// _periodSelector.setEditable(true);

		String[] periodTypes = { "B", "N", "Z" };
		_periodTypeSelector = new DxJComboBox(periodTypes);
		_periodTypeSelector.setPreferredSize(new Dimension(100, DConst.NPT11
				* c));
		_periodTypeSelector
				.setMaximumSize(new Dimension(100, DConst.NPT11 * c));

		// The JButton Objects initialization
		_sameLine = new JButton("Toute la ligne");
		_sameColumn = new JButton("Toute la journ�e");
		// JComboBox periodTypeSelector initialization
		addBarOne();
		addBarTwo();
		_toolBarSelector.setSelectedIndex(0);
		selectBar(0);// index 0
	}

	public void setComboBoxStatus(boolean status) {
		_comboBoxStatus = status;
	}

	/**
	 * 
	 */
	private void actionManager() {
		_toolBarSelector.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				int i = _toolBarSelector.getSelectedIndex();
				selectBar(i);
			}// end actionPerformed
		});// end addActionListener

		// *** Actions for the elements of the bar one
		// * _setNumberOfDays
		_setNumberOfDays.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {				
				e.toString();
				String nbDays = _setNumberOfDays.getText();
				if (!DXToolsMethods.isIntValue(nbDays)) {
					new InformationDlg(_dApplic.getJFrame(), "Valeur eronn�e");
					_setNumberOfDays.setText(Integer.toString(_tts
							.getCurrentCycle().getNumberOfDays()));
				} else {
					if (Integer.parseInt(nbDays) > 0)
						selectAddRemoveDays(Integer.parseInt(nbDays));
					else
						new InformationDlg(_dApplic.getJFrame(),
								"Valeur eronn�e");
					_dApplic.getCurrentDxDoc().changeInModel(
							this.idDlgToString());
					setToolBarOne();
					setToolBarTwo();
				}
			}// end actionPerformed

			public String idDlgToString() {
				return this.getClass().toString();
			}
		});// end addActionListener

		// * _daySelector
		_daySelector.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				int item = _daySelector.getSelectedIndex();
				// if( item != -1 ){
				DResource resc = _tts.getCurrentCycle().getSetOfDays()
						.getResourceAt(item);
				_tts.getCurrentCycle().setCurrentDayIndex(item);

				_dayNameSelector.disableActionListeners();
				_dayNameSelector.setSelectedItem(resc.getID());
				_dayNameSelector.enableActionListeners();
				// }
			}// end actionPerformed
		});// end addActionListener

		// * _dayNameSelector
		_dayNameSelector.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				int index = _tts.getCurrentCycle().getCurrentDayIndex();
				DResource resc = _tts.getCurrentCycle().getSetOfDays()
						.getResourceAt(index);
				resc.setID((String) _dayNameSelector.getSelectedItem());
				_dApplic.getCurrentDxDoc().changeInModel(this.idDlgToString());

			}// end actionPerformed

			public String idDlgToString() {
				return this.getClass().toString();
			}
		});// end addActionListener

		// *** Actions for the elements of the bar two
		_periodSelector.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				String str = (String) _periodSelector.getSelectedItem();
				setPeriodSelector(str);
			}// end actionPerformed

		});// end addActionListener

		_periodTypeSelector.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				String item = (String) _periodSelector.getSelectedItem();
				DxPeriodPanel ppanel = null;
				ppanel = _dApplic.getCurrentDxDoc().getDxTTPane().getPeriodPanel(
						Integer.parseInt(item));
				Period period = _tts.getCurrentCycle().getPeriodByIndex(
						ppanel.getPeriodRef()[0], ppanel.getPeriodRef()[1],
						ppanel.getPeriodRef()[2]);
				period.setPriority(_periodTypeSelector.getSelectedIndex());
				if (_comboBoxStatus) {
					_dApplic.getCurrentDxDoc().changeInModel(
							this.idDlgToString());
				}

			}// end actionPerformed

			public String idDlgToString() {
				return this.getClass().toString();
			}
		});// end addActionListener

		_sameLine.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				String item = (String) _periodSelector.getSelectedItem();
				DxPeriodPanel ppanel = null;
				ppanel = _dApplic.getCurrentDxDoc().getDxTTPane().getPeriodPanel(
						Integer.parseInt(item));
				Cycle cycle = _tts.getCurrentCycle();
				Period period;
				for (int i = 0; i < cycle.getSetOfDays().size(); i++) {
					period = cycle.getPeriodByIndex(i,
							ppanel.getPeriodRef()[1], ppanel.getPeriodRef()[2]);
					period.setPriority(_periodTypeSelector.getSelectedIndex());
				}

				_dApplic.getCurrentDxDoc().changeInModel(this.idDlgToString());

			}// end actionPerformed

			public String idDlgToString() {
				return this.getClass().toString();
			}
		});// end addActionListener

		_sameColumn.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				e.toString();
				String item = (String) _periodSelector.getSelectedItem();
				DxPeriodPanel ppanel = null;
				ppanel = _dApplic.getCurrentDxDoc().getDxTTPane().getPeriodPanel(
						Integer.parseInt(item));
				int dayIndex = ppanel.getPeriodRef()[0];
				Day day = _tts.getCurrentCycle().getDayByIndex(dayIndex);
				Sequence seq;
				Period period;
				for (int i = 0; i < day.getSetOfSequences().size(); i++) {
					seq = day.getSequence(i);
					for (int j = 0; j < seq.getSetOfPeriods().size(); j++) {
						period = seq.getPeriodByIndex(j);
						period.setPriority(_periodTypeSelector
								.getSelectedIndex());
					}
				}

				_dApplic.getCurrentDxDoc().changeInModel(this.idDlgToString());
			}// end actionPerformed

			public String idDlgToString() {
				return this.getClass().toString();
			}
		});// end addActionListener

	}

	/**
	 * 
	 */
	public void setPeriodSelector(String item) {
		if (DXToolsMethods.isIntValue(item)) {
			DxPeriodPanel ppanel = _dApplic.getCurrentDxDoc().getDxTTPane()
					.getPeriodPanel(Integer.parseInt(item));
			Period period;
			if (ppanel != null) {
				_periodSelector.setSelectedItem(Integer.toString(ppanel
						.getPanelRefNo()));
				period = _tts.getCurrentCycle().getPeriodByIndex(
						ppanel.getPeriodRef()[0], ppanel.getPeriodRef()[1],
						ppanel.getPeriodRef()[2]);
				_periodTypeSelector.disableActionListeners();
				_periodTypeSelector
						.setSelectedItem(TTStructure._priorityTable[period
								.getPriority()]);
				_periodTypeSelector.enableActionListeners();

			} else {
				new InformationDlg(_dApplic.getJFrame(), "P�riode non trouv�e");
				_periodSelector.setSelectedIndex(0);
			}

		} else {// end if(DXToolsMethods.isIntValue(item))
			new InformationDlg(_dApplic.getJFrame(), "Valeur eronn�e");
			_periodSelector.setSelectedIndex(0);
		}
	}

	/**
	 * 
	 */
	public void setToolBars(TTStructure ttStruct) {
		_tts = ttStruct;
		setToolBarOne();
		// lgd: bug 101
		setToolBarTwo();
	}

	/**
	 * 
	 */
	private void selectBar(int choice) {
		switch (choice) {
		case 0:
			addBarOne();
			_toolBarSelector.setSelectedIndex(0);
			break;
		case 1:
			addBarTwo();
			_toolBarSelector.setSelectedIndex(1);
			break;
		}// end switch
	}

	/**
	 * 
	 */
	public void setToolBarOne() {
		_daySelector.disableActionListeners();
		int nbDays = _tts.getCurrentCycle().getNumberOfDays();
		_setNumberOfDays.setText(Integer.toString(nbDays));

		String[] days = new String[nbDays];
		// String [] nameDays= new String[nbDays];
		_daySelector.removeAllItems();

		DResource resc;
		for (int i = 0; i < nbDays; i++) {
			resc = _tts.getCurrentCycle().getSetOfDays().getResourceAt(i);
			days[i] = Integer.toString((int) resc.getKey());
			_daySelector.addItem(days[i]);
		}
		_daySelector.setSelectedIndex(0);
		// rgr resc= _tts.getCurrentCycle().getSetOfDays().getResourceAt(0);
		// _dayNameSelector.setSelectedItem(resc.getID()); //rgr
		_daySelector.enableActionListeners();
		setEnabledToolbar(true);
	}

	/**
	 * 
	 */
	public void setToolBarTwo() {
		_periodSelector.disableActionListeners();
		_comboBoxStatus = false;
		JPanel thePane = null;
		thePane = (JPanel) _dApplic.getCurrentDxDoc().getDxTTPane().getViewport()
				.getComponent(0);
		_periodSelector.removeAllItems();
		for (int i = 0; i < thePane.getComponentCount(); i++) {
			DxPeriodPanel ppanel = (DxPeriodPanel) thePane.getComponent(i);
			if (ppanel.getPanelRefNo() != 0) {
				_periodSelector.addItem(Integer
						.toString(ppanel.getPanelRefNo()));
			}
		}// end for (int i=0; i< ttPanel.getComponentCount(); i++)
		_periodTypeSelector.disableActionListeners();
		_periodTypeSelector.removeAllItems();

		for (int i = 0; i < TTStructure._priorityTable.length; i++)
			_periodTypeSelector.addItem(TTStructure._priorityTable[i]);
		// System.out.println("Nb of viewPorts:
		// "+ttPanel.getComponentCount());//debug
		_periodTypeSelector.enableActionListeners();
		_comboBoxStatus = true;
		_periodSelector.enableActionListeners();
	}

	/**
	 * 
	 */
	public void setEnabledToolbar(boolean state) {
		for (int i = 0; i < getComponentCount(); i++)
			getComponentAtIndex(i).setEnabled(state);
	}

	// -------------------------------------------
	private void addBarOne() {
		removeBar();
		addSeparator();
		add(_lSetNumberOfDays);
		add(_setNumberOfDays);
		addSeparator();
		add(_lDaySelector);
		add(_daySelector);
		addSeparator();
		add(_lDayNameSelector);
		add(_dayNameSelector);
		repaint();
	}// end methode

	// -------------------------------------------
	private void addBarTwo() {
		removeBar();
		addSeparator();
		add(_lPeriodIndicator);
		add(_periodSelector);
		addSeparator();
		add(_lPeriodTypeSelector);
		add(_periodTypeSelector);
		addSeparator();
		add(_sameColumn);
		addSeparator();
		add(_sameLine);
		repaint();
	}// end addBarTwo()

	// -------------------------------------------

	private void removeBar() {
		removeAll();
		add(_toolBarSelector);
		repaint();
	}// end removeBar()

	/**
	 * */
	private void selectAddRemoveDays(int nbDays) {
		int signe = nbDays - _tts.getCurrentCycle().getNumberOfDays();
		if (signe > 0) {
			if (JOptionPane.showConfirmDialog(_dApplic.getJFrame(),
					"Voulez-vous ajouter " + signe + " jour(s)") == JOptionPane.OK_OPTION) {
				_tts.getCurrentCycle().addDays(signe);
			}
		} else {// else if (signe>0)
			if (signe < 0) {
				if (JOptionPane.showConfirmDialog(_dApplic.getJFrame(),
						"Voulez-vous supprimer " + (-signe) + " jour(s)") == JOptionPane.OK_OPTION) {
					_tts.getCurrentCycle().removeDays(-signe);
				}
			}// end if(signe<0)

		}// end else if (signe>0)
	}

	public void update(Observable o, Object arg) {
		_dApplic.getCurrentDxDoc().update(o, arg);
	}

	public String idDlgToString() {
		return this.getClass().toString();
	}
} // end DToolBar
