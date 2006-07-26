/**
*
* Title: SelectiveScheduleDlg 
* Description: SelectiveScheduleDlg is a class used to
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
* @since JDK1.3
*/
/*
 * Created on 28 mai 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

import dConstants.DConst;
import dDeveloper.DxFlags;

import dInterface.DApplication;

import dInterface.selectiveSchedule.SelectiveScheduleManager;

import dInterface.selectiveSchedule.filters.FilterSet;
import dInterface.selectiveSchedule.persistance.PersistanceMismatch;
import dInterface.selectiveSchedule.persistance.PersistanceMismatchException;

import dInternal.DResource;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Dialogue principal du module de grille sélective. C'est avec ce dialogue que
 * l'utilisateur choisi le ou les ensembles qui vont servir de "filtre(s)" lors
 * de l'affichage détaillé de la grille horaire. Via ce dialogue, l'utilisateur
 * a également la possibilité de créer, modifier et supprimer des ensembles.
 * 
 * @author Pascal
 * 
 */
public class SelectiveScheduleDlg extends JDialog {
	private static SelectiveScheduleDlg _instance = null;

	private Logger _logger = Logger.getLogger(SelectiveScheduleDlg.class);

	private FilterSetSelectorPanel _filterSetSelectorPanel = null;

	private FilterSetElementsPanel _filterSetElementsPanel = null;

	private SelectiveScheduleDlg() {
		// depend du JFrame de DApplication, et est "modal"
		// XXXX GS: Externaliser cette string
		super(DApplication.getInstance().getJFrame(), "Grille sélective", true);

		// test();
		initialize();
	}

	public static SelectiveScheduleDlg getInstance() {
		if (_instance == null) {
			_instance = new SelectiveScheduleDlg();
		}

		return _instance;
	}

	private void initialize() {
		_filterSetSelectorPanel = new FilterSetSelectorPanel();
		_filterSetElementsPanel = new FilterSetElementsPanel();

		JPanel mainPanel = new JPanel();
		JPanel setPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		setPanel.setLayout(new BoxLayout(setPanel, BoxLayout.LINE_AXIS));

		setPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		buttonsPanel
				.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));

		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mainPanel.add(setPanel);
		mainPanel.add(buttonsPanel);

		setPanel.add(_filterSetSelectorPanel.getJComponent());
		setPanel.add(_filterSetElementsPanel.getJComponent());

		buttonsPanel.add(new ActionComboBox().getJComponent());
		buttonsPanel.add(Box.createHorizontalStrut(100));
		buttonsPanel.add(new OKButton().getJButton());
		buttonsPanel.add(Box.createHorizontalStrut(35));
		buttonsPanel.add(new CancelButton().getJButton());

		this.setLocation((int) DApplication.getInstance().getJFrame()
				.getLocation().getX()
				+ DConst.X_OFFSET, (int) DApplication.getInstance().getJFrame()
				.getLocation().getY()
				+ DConst.Y_OFFSET);
		this.setContentPane(mainPanel);
	}

	public void displayDlg() {
		try {
			SelectiveScheduleManager.getInstance().readFromPersistenceSource();
		} catch (SelectiveScheduleFileIOException e1) {
			_logger.error(e1);
			JOptionPane.showMessageDialog(this, e1.getMessage(),
					"État inconnu", JOptionPane.ERROR_MESSAGE);
		} catch (PersistanceMismatchException e) {
			PersistanceMismatchDlg.getInstance().displayDlg();
			removeEmptyMismatchedFilterSet();
		}

		updateDlg();

		setResizable(false);
		pack();
		setVisible(true);
	}

	private void removeEmptyMismatchedFilterSet() {
		Iterator itrOnPM = SelectiveScheduleManager.getInstance()
				.getPersistanceMismatchManager().getPersistanceMismatches()
				.iterator();
        PersistanceMismatch pm;

		while (itrOnPM.hasNext()) {
			pm = (PersistanceMismatch) itrOnPM.next();
			SelectiveScheduleManager.getInstance().removeFilterSet(
					pm.getMismatchedFilterSet());
		}
	}

	void updateDlg() {
		synchWithSelectiveScheduleManager();
		_filterSetSelectorPanel.updateView();
	}

	private void synchWithSelectiveScheduleManager() {
		FilterSet[] allFilterSet = SelectiveScheduleManager.getInstance()
				.getAllFilterSet();

		/*
		 * 1ere passe : est-ce qu'il y a des FilterSet dans le dialogue qui ne
		 * sont plus dans le model ?
		 */
		Iterator itr = _filterSetSelectorPanel.getFilterSets().iterator();
        FilterSet fs;

		while (itr.hasNext()) {
			fs = (FilterSet) itr.next();

			boolean found = false;
			for (int i = 0; i < allFilterSet.length; ++i) {
				if (allFilterSet[i] == fs) {
					found = true;
					break;
				}
			}

			if (found == false) {
				itr.remove();
			}
		}

		/*
		 * 2e passe : est-ce qu'il y a des FilterSet de plus dans le model qu'il
		 * faut ajouter dans le dialogue ?
		 */
		for (int i = 0; i < allFilterSet.length; ++i) {
			if (_filterSetSelectorPanel.containsFilterSet(allFilterSet[i]) == false) {
				_filterSetSelectorPanel.addFilterSet(allFilterSet[i]);
			}
		}
	}

	/**
	 * Test d'un cas d'utilisation relativement simple.
	 * 
	 * Le cas d'utilisation est le suivant: un utilisateur commence, pour la
	 * premiere fois, a utiliser la fonctionnalité de grille sélective.
	 * 
	 * La séquence d'utilisation du dialogue pourrait être la suivante: (XXXX GS
	 * idéalement, je devrais donner le lien de la documentation de ce cas
	 * d'utilisation)
	 * 
	 * 1. L'utilisateur sélectionne quelques Activity
	 * 
	 * 2. Il donne un nom à l'ensemble d'Activity qu'il a sélectionné
	 * 
	 * 3. Il indique qu'il veut activer cet ensemble
	 * 
	 * 4. Il indique qu'il désire activer la fonctionnalité de grille sélective
	 * (aussitôt qu'un ensemble est activer, on considère que la fonctionnalité
	 * est active? il faut viser la simplicité avant la flexibilité)
	 * 
	 * 5. Il clique sur Apply (l'état du dialogue est vérifié et les appels
	 * nécessaires sont effectués)
	 * 
	 * 6. Il clique sur OK (le dialogue disparaît)
	 * 
	 * 7. Un rafraîchissement de l'affichage de la grille détaillée (si c'est
	 * elle qui est en fonction) est demandé
	 * 
	 */
	// private void test() {
	// // 1.
	// FilterSet fs = SelectiveScheduleManager.getInstance().createFilterSet();
	//
	// SetOfActivities soa = DApplication.getInstance().getDMediator()
	// .getCurrentDoc().getDM().getSetOfActivities();
	// DResource activity = soa.getResource("GBT120");
	//
	// try {
	// // 2.
	// fs.getFilterSetIdentifier().setFilterSetName("blah");
	// } catch (FilterSetNameException e) {
	// e.printStackTrace();
	// }
	//
	// // 3.
	// // Cette fonctionnalite n'a pas encore ete designee. Devrais-je ajouter
	// // un service setActive aux Filtres?
	// fs.addFilter(activity);
	// SelectiveScheduleManager.getInstance().addFilterSet(fs);
	//
	// // 4.
	// SelectiveScheduleManager.getInstance().setEnabled(true);
	//
	// // 5.
	// // Le SelectiveSche.. est deja dans l'etat voulu
	// // 6.
	// // 7.
	// // a implementer (car si utilisateur est deja dans sa vue detaillee...)
	// }

	/**
	 * Composant utilisé par l'utilisateur pour sélectionner les ensembles qu'il
	 * désire utiliser pour l'affichage sélectif
	 * 
	 * @author Pascal
	 * 
	 */
	public class FilterSetSelectorPanel implements ActionListener {
		private JPanel _mainPanel = null;

		private JScrollPane _scrollPane = null;

		/* panel utilisé pour afficher les JCheckBox */
		private JPanel _filterSetView = null;

		/* key=FilterSet value=Boolean (Boolean=true si FilterSet est coché) */
		private SortedMap  _checkBoxes = null;

		/**
		 * 
		 */
		FilterSetSelectorPanel() {
			initialize();
		}

		private void initialize() {
			_checkBoxes = new TreeMap(new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((FilterSet) o1).getFilterSetIdentifier()
							.getFilterSetName().compareTo(
									((FilterSet) o2).getFilterSetIdentifier()
											.getFilterSetName());
				}
			});

			_mainPanel = new JPanel();

			JLabel label = new JLabel("Ensembles");
			_filterSetView = new JPanel();
			_scrollPane = new JScrollPane(_filterSetView);

			_mainPanel
					.setLayout(new BoxLayout(_mainPanel, BoxLayout.PAGE_AXIS));
			_mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

			// _mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			// _mainPanel.setBackground(Color.BLUE);
			// label.setAlignmentX(Component.RIGHT_ALIGNMENT);
			// label.setAlignmentY(Component.CENTER_ALIGNMENT);
			// label.setBorder(BorderFactory.createLineBorder(Color.BLACK,10));
			_filterSetView.setLayout(new BoxLayout(_filterSetView,
					BoxLayout.PAGE_AXIS));
			_filterSetView.setBackground(Color.WHITE);

			// _filterSetView.setBackground(Color.YELLOW);
			// _mainPanel.setMinimumSize(new Dimension(400, 300));
			// _mainPanel.setMaximumSize(new Dimension(400, 300));
			// _mainPanel.setPreferredSize(new Dimension(400, 300));
			_mainPanel.add(label);
			_mainPanel.add(Box.createVerticalStrut(10));
			_mainPanel.add(_scrollPane);
		}

		void updateView() {
			_filterSetView.removeAll();
			_filterSetElementsPanel.clearFilterSetElements();

			Collection checkedFilterSets = getCheckedFilterSets();
			Iterator itrFS = getFilterSets().iterator();

            Object next;
            FilterSet fs;
            JCheckBox checkBox;
			while (itrFS.hasNext()) {
				next = itrFS.next();

				if (next == null) {
					continue;
				}

				fs = (FilterSet) next;

				checkBox = createJCheckBox(fs
						.getFilterSetIdentifier().getFilterSetName());

				if (checkedFilterSets.contains(fs)) {
					checkBox.setSelected(true);
					_filterSetElementsPanel.addFilterSetElements(fs);
				} else {
					checkBox.setSelected(false);
				}

				_filterSetView.add(checkBox);
			}

			/*
			 * appelle validate() parce qu'on a modifié les sous-composants de
			 * _filterSetView. appelle repaint() pour que les modifications
			 * soient visibles.
			 */
			_filterSetView.revalidate();
			_filterSetView.repaint();
			// SelectiveScheduleDlg.this.validate();
		}

		private JCheckBox createJCheckBox(String text) {
			JCheckBox cb = new JCheckBox(text);
			cb.addActionListener(this);
			cb.setBackground(Color.WHITE);

			return cb;
		}

		void checkFilterSet(FilterSet filterSetToCheck) {
			_checkBoxes.put(filterSetToCheck, Boolean.TRUE);
		}

		void uncheckFilterSet(FilterSet fitlerSetToUncheck) {

			if (_checkBoxes.containsKey(fitlerSetToUncheck)) {
				_checkBoxes.put(fitlerSetToUncheck, Boolean.FALSE);
			}
			/* XXXX GS: else, throw exception ? */
		}

		void uncheckAllFilterSets() {
			Set entrySet = _checkBoxes.entrySet();

			Iterator itr = entrySet.iterator();
            Entry mapEntry;

			while (itr.hasNext()) {
				mapEntry = (Entry) itr.next();

				if (((Boolean) mapEntry.getValue()).equals(Boolean.TRUE)) {
					mapEntry.setValue(Boolean.FALSE);
				}

			}
		}

		void addFilterSet(FilterSet filterSetToAdd) {
			_checkBoxes.put(filterSetToAdd, Boolean.FALSE);
		}

		void removeFilterSet(FilterSet filterSetToRemove) {
			_checkBoxes.remove(filterSetToRemove);
		}

		void clearFilterSet() {
			_checkBoxes.clear();
		}

		boolean containsFilterSet(FilterSet filterSet) {
			return _checkBoxes.containsKey(filterSet);
		}

		Collection getCheckedFilterSets() {
			Set entrySet = _checkBoxes.entrySet();
			Collection coll = new ArrayList();

			Iterator itr = entrySet.iterator();
            Entry entry;

			while (itr.hasNext()) {
				entry = (Entry) itr.next();
				if (((Boolean) entry.getValue()).equals(Boolean.TRUE)) {
					coll.add(entry.getKey());
				}
			}

			return coll;
		}

		Collection getFilterSets() {
			return _checkBoxes.keySet();
		}

		JComponent getJComponent() {
			return _mainPanel;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (((JCheckBox) e.getSource()).isSelected()) {
				checkFilterSet(getFilterSetFromName(e.getActionCommand()));
				_filterSetElementsPanel
						.addFilterSetElements(getFilterSetFromName(e
								.getActionCommand()));
			} else {
				uncheckFilterSet(getFilterSetFromName(e.getActionCommand()));
				_filterSetElementsPanel
						.removeFilterSetElements(getFilterSetFromName(e
								.getActionCommand()));
			}
		}

		private FilterSet getFilterSetFromName(String fileterSetName) {
			FilterSet result = null;
			Iterator itrFilterSets = _checkBoxes.keySet().iterator();
            FilterSet fs;

			while (itrFilterSets.hasNext()) {
				fs = (FilterSet) itrFilterSets.next();

				if (fs.getFilterSetIdentifier().getFilterSetName().equals(
						fileterSetName)) {
					result = fs;
				}
			}

			return result;
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class FilterSetElementsPanel {
		private JList _filterSetElementsJList = null;

		private DefaultListModel _filterSetElementsJListModel = null;

		private SortedMap _elementCounter = null;

		private JPanel _mainPanel = null;

		/**
		 * 
		 */
		FilterSetElementsPanel() {
			initialize();
		}

		private void initialize() {
			_mainPanel = new JPanel();
			_filterSetElementsJListModel = new DefaultListModel();
			_filterSetElementsJList = new JList(_filterSetElementsJListModel);
			_elementCounter = new TreeMap();

			JLabel label = new JLabel(
					"Élements du ou des ensemble(s) sélectionné(s)");

			JScrollPane scrollPane = new JScrollPane(_filterSetElementsJList);

			_mainPanel
					.setLayout(new BoxLayout(_mainPanel, BoxLayout.PAGE_AXIS));
			_mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

			_mainPanel.add(label);
			_mainPanel.add(Box.createVerticalStrut(10));
			_mainPanel.add(scrollPane);
		}

		// void updateView() {
		//
		// }

		void addFilterSetElements(FilterSet fs) {
			Iterator itr = fs.getIterator();
            DResource next;

			while (itr.hasNext()) {
				next = (DResource) itr.next();

				if (next == null) {
					continue;
				}

				incrementElementCounter(next.getID());

				/* on ajoute seulement les noms d'éléments unique */
				if (_filterSetElementsJListModel.contains(next.getID()) == false) {
					_filterSetElementsJListModel.addElement(next.getID());
				}
			}
		}

		void removeFilterSetElements(FilterSet fs) {
			Iterator itr = fs.getIterator();
            DResource next;

			while (itr.hasNext()) {
				next = (DResource) itr.next();

				if (next == null) {
					continue;
				}

				decrementElementCounter(next.getID());

				if (((Integer) _elementCounter.get(next.getID())).intValue() == 0) {
					_elementCounter.remove(next.getID());
					_filterSetElementsJListModel.removeElement(next.getID());
				}
			}
		}

		private void clearFilterSetElements() {
			_elementCounter.clear();
			_filterSetElementsJListModel.clear();
		}

		private void incrementElementCounter(Object element) {
			if (_elementCounter.containsKey(element)) {
				Integer counter = (Integer) _elementCounter.get(element);
				_elementCounter.put(element,
						new Integer(counter.intValue() + 1));
			} else {
				_elementCounter.put(element, new Integer(1));
			}
		}

		private void decrementElementCounter(Object element) {
			if (_elementCounter.containsKey(element)) {
				Integer counter = (Integer) _elementCounter.get(element);
				_elementCounter.put(element,
						new Integer(counter.intValue() - 1));
			} else {
				// XXXX GS: throw exception ?
			}
		}

		JComponent getJComponent() {
			return _mainPanel;
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class ActionComboBox implements ActionListener, PopupMenuListener {
		private JComboBox _comboBox = null;

		/**
		 * 
		 */
		public ActionComboBox() {
			_comboBox = new JComboBox(new Object[] {
					new GiveClueToUserComboBoxAction(),
					new CreateFilterSetComboBoxAction(),
					new ModifyFilterSetComboBoxAction(),
					new DeleteFilterSetComboBoxAction() });

			_comboBox.addActionListener(this);
			_comboBox.addPopupMenuListener(this);
		}

		JComponent getJComponent() {
			return _comboBox;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if ((((JComboBox) e.getSource()).getSelectedItem() instanceof ComboBoxAction) == false) {
				// XXXX GS: throw exception
				_logger.error("ActionEvent not a ComboBoxAction");
			}

			ComboBoxAction cba = (ComboBoxAction) ((JComboBox) e.getSource())
					.getSelectedItem();
			cba.execute();
		}

		/* methodes liees a PopupMenuListener */
		// non utilise
		public void popupMenuCanceled(PopupMenuEvent e) {
			e.toString();
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			e.toString();
			_comboBox.setSelectedIndex(0); // revient a l'item GiveClueToUserComboBoxAction
		}

		// non utilise
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			e.toString();
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public interface ComboBoxAction {
		void execute();
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class GiveClueToUserComboBoxAction implements ComboBoxAction {

		public GiveClueToUserComboBoxAction() {
			super();
		}

		public void execute() {
			// rien a faire
		}

		public String toString() {
			return "Cliquez ici pour choisir une action";
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class CreateFilterSetComboBoxAction implements ComboBoxAction {
		/**
		 * 
		 */
		CreateFilterSetComboBoxAction() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg.ComboBoxAction#execute()
		 */
		public void execute() {
			SetEditorDlg.getInstance().displayCreateDlg();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "Créer un ensemble";
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class ModifyFilterSetComboBoxAction implements ComboBoxAction {
		/**
		 * 
		 */
		ModifyFilterSetComboBoxAction() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg.ComboBoxAction#execute()
		 */
		public void execute() {
			Collection selectedFilterSet = _filterSetSelectorPanel
					.getCheckedFilterSets();

			try {
				if (selectedFilterSet.size() != 1) {
					throw new DialogUsageExeption(
							"Il faut sélectionner 1 ensemble");
				}

				SetEditorDlg.getInstance().displayModifyDlg(
						(FilterSet) selectedFilterSet.iterator().next());
			} catch (DialogUsageExeption e) {
				JOptionPane.showMessageDialog(SelectiveScheduleDlg.this, e
						.getMessage(), "Erreur d'utilisation du dialogue",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "Modifier un ensemble";
		}
	}

	/**
	 * 
	 * 
	 * @author Pascal
	 * 
	 */
	public class DeleteFilterSetComboBoxAction implements ComboBoxAction {
		/**
		 * 
		 */
		DeleteFilterSetComboBoxAction() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg.ComboBoxAction#execute()
		 */
		public void execute() {
			/* On nous prend pour un con ? */
			Collection checkedFilterSets = _filterSetSelectorPanel
					.getCheckedFilterSets();

			if (checkedFilterSets.size() == 0) {
				JOptionPane.showMessageDialog(SelectiveScheduleDlg.this,
						"Il faut sélectionner au moins un ensemble à détruire",
						"Erreur d'utilisation du dialogue",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			/*
			 * On demande à l'utilisateur s'il est vraiment certains de son
			 * choix
			 */
			int result = JOptionPane
					.showConfirmDialog(
							SelectiveScheduleDlg.this,
							"Êtes-vous certains de vouloir détruire le ou les ensemble(s) sélectionné(s) ?",
							"Êtes-vous certains ?", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION) {
				return;
			}

			/* L'utilisateur est certains, alors on y va... */
			Iterator itr = checkedFilterSets.iterator();
            FilterSet fsToDelete;

			while (itr.hasNext()) {
				fsToDelete = (FilterSet) itr.next();

				/* on retire le FS du model */
				SelectiveScheduleManager.getInstance().removeFilterSet(
						fsToDelete);

				/* on retire le FS dans la vue de sélection des ensembles */
				_filterSetSelectorPanel.removeFilterSet(fsToDelete);

				/*
				 * dans la vue des éléments des ensembles sélectionné, on enlève
				 * les éléments du FS retiré
				 */
				_filterSetElementsPanel.removeFilterSetElements(fsToDelete);
			}

			_filterSetSelectorPanel.updateView();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "Supprimer ou un plusieurs ensemble(s)";
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class OKButton implements ActionListener {
		private JButton button = null;

		/**
		 * 
		 */
		OKButton() {
			initialize();
		}

		private void initialize() {
			button = new JButton("Appliquer");

			button.addActionListener(this);
		}

		/**
		 * met a jour l'affichage de la grille horaire
		 */
		private void updateScheduleGUIAndDispose() {
			if(DxFlags.newDoc) {
	    		DApplication.getInstance().getCurrentDxDoc().getTTPane().updateTTPane(
						DApplication.getInstance().getCurrentDxDoc().getCurrentDModel()
						.getTTStructure());
	    	} else {
			DApplication.getInstance().getCurrentDoc()
					.getTTPane().updateTTPane(
							DApplication.getInstance().getCurrentDModel()
									.getTTStructure());
	    	}
			SelectiveScheduleDlg.this.dispose();
		}

		private void activateChoosenFilterSets() {
			Collection selectedFilterSets = _filterSetSelectorPanel
					.getCheckedFilterSets();

			Collection allFilterSets = _filterSetSelectorPanel.getFilterSets();

			Iterator itrAllFS = allFilterSets.iterator();
            Object next;

			while (itrAllFS.hasNext()) {
				next = itrAllFS.next();
				if (next == null) {
					continue;
				}

				if (selectedFilterSets.contains(next)) {
					((FilterSet) next).getFilterSetIdentifier().setActive(true);
				} else {
					((FilterSet) next).getFilterSetIdentifier()
							.setActive(false);
				}
			}
		}

		private void writeToPersistenceSink()
				throws SelectiveScheduleFileIOException {
			SelectiveScheduleManager.getInstance().writeToPersistenceSink();
		}

		JButton getJButton() {
			return button;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				e.toString();
				writeToPersistenceSink();
			} catch (SelectiveScheduleFileIOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*
			 * Si au moins un ensemble est sélectionné par l'utilisateur, alors
			 * on active le SSM
			 */
			if (_filterSetSelectorPanel.getCheckedFilterSets().size() != 0) {
				SelectiveScheduleManager.getInstance().setEnabled(true);
				activateChoosenFilterSets();
			} else {
				SelectiveScheduleManager.getInstance().setEnabled(false);
			}

			updateScheduleGUIAndDispose();
		}
	}

	/**
	 * @author Pascal
	 * 
	 */
	public class CancelButton implements ActionListener {
		private JButton button = null;

		/**
		 * 
		 */
		CancelButton() {
			initialize();
		}

		private void initialize() {
			button = new JButton("Fermer");

			button.addActionListener(this);
		}

		JButton getJButton() {
			return button;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			e.toString();
			SelectiveScheduleDlg.this.dispose();
		}
	}
}

