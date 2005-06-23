/*
 * Created on 22 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dConstants.DConst;
import dInterface.DApplication;
import dInterface.selectiveSchedule.SelectiveScheduleManager;
import dInterface.selectiveSchedule.persistance.PersistanceMismatch;
import dInterface.selectiveSchedule.persistance.PersistanceMismatchManager;

/**
 * @author Pascal
 *  
 */
public class PersistanceMismatchDlg extends JDialog {

    /**
     * @author Pascal
     *  
     */
    public class CommentPanel {
        private JPanel _panel = null;

        private JTextField _comment = null;

        /**
         *  
         */
        public CommentPanel() {
            initialize();
        }

        private void initialize() {
            _panel = new JPanel();
            _comment = new JTextField();

            _panel.add(_comment);
        }

        public void setText(String text) {
            _comment.setText(text);
        }

        public JComponent getJComponent() {
            return _comment;
        }
    }

    /**
     * @author Pascal
     *  
     */
    public class MissingElementsPanel {
        private JPanel _panel = null;

        private DefaultListModel _missingElementsListModel = null;

        /**
         *  
         */
        public MissingElementsPanel() {
            initialize();
        }

        private void initialize() {
            _panel = new JPanel();
            _missingElementsListModel = new DefaultListModel();
            JList missingElementsList = new JList(_missingElementsListModel);
            JScrollPane scrollableMissingElementsList = new JScrollPane(
                    missingElementsList);
            _panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            _panel.add(scrollableMissingElementsList);
            scrollableMissingElementsList
                    .setMinimumSize(new Dimension(450, 150));
            scrollableMissingElementsList
                    .setMaximumSize(new Dimension(450, 150));
            scrollableMissingElementsList.setPreferredSize(new Dimension(450,
                    150));
        }

        public void clearElements() {
            _missingElementsListModel.clear();
        }

        public void addElement(String elementName) {
            _missingElementsListModel.addElement(elementName);
        }

        public JComponent getJComponent() {
            return _panel;
        }
    }

    /**
     * @author Pascal
     *  
     */
    public class FaultyFilterSetsPanel implements ActionListener {
        private JPanel _panel = null;

        private JComboBox _mismatchedSetsComboBox = null;

        private DefaultComboBoxModel _mismatchedSetsComboBoxModel = null;

        /**
         *  
         */
        public FaultyFilterSetsPanel() {
            initialize();
        }

        private void initialize() {
            _panel = new JPanel();
            _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

            _mismatchedSetsComboBoxModel = new DefaultComboBoxModel();
            _mismatchedSetsComboBox = new JComboBox(
                    _mismatchedSetsComboBoxModel);
            _mismatchedSetsComboBox.addActionListener(this);

            _panel.add(_mismatchedSetsComboBox);
            _panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            populateComboBox();
        }

        private void populateComboBox() {
            PersistanceMismatchManager pmm = SelectiveScheduleManager
                    .getInstance().getPersistanceMismatchManager();

            Collection sets = pmm.getPersistanceMismatches();
            _mismatchedSetsComboBoxModel.removeAllElements();
            Iterator itr = sets.iterator();

            while (itr.hasNext()) {
                PersistanceMismatch pm = (PersistanceMismatch) itr.next();
                if (pm != null) {
                    _mismatchedSetsComboBoxModel.addElement(pm
                            .getMismatchedFilterSet().getFilterSetIdentifier()
                            .getFilterSetName());
                }
            }
        }

        public JComponent getJComponent() {
            return _panel;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            String filterSetName = (String) ((JComboBox) e.getSource())
                    .getSelectedItem();
            PersistanceMismatch pm = getPersistanceMismatch(filterSetName);

            /* Ajuste la liste des noms d'éléments manquants */
            _missingElementsPanel.clearElements();

            Iterator itrElementNames = pm.getMissingElementNames().iterator();

            while (itrElementNames.hasNext()) {
                String elementName = (String) itrElementNames.next();
                if (elementName != null) {
                    _missingElementsPanel.addElement(elementName);
                }
            }

            /* Modifie le commentaire */
            int nbOfMissingElements = pm.getMissingElementNames().size();

            if (nbOfMissingElements == pm.getExpectedNbOfElements()) {

                _commentPanel.setText("L'ensemble \"" + filterSetName
                        + "\" est vide! Il sera donc retiré.");
            } else {
                _commentPanel.setText("L'ensemble \"" + filterSetName
                        + "\" est amputé de " + nbOfMissingElements
                        + " éléments");
            }
        }
    }

    /**
     * @author Pascal
     *  
     */
    public class OKButton implements ActionListener {
        private JButton _button = null;

        /**
         *  
         */
        public OKButton() {
            _button = new JButton("OK");
            _button.addActionListener(this);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            PersistanceMismatchDlg.this.dispose();
        }

        public JComponent getJComponent() {
            return _button;
        }

    }

    private static PersistanceMismatchDlg _instance = null;

    private MissingElementsPanel _missingElementsPanel = null;

    private FaultyFilterSetsPanel _faultyFilterSetsPanel = null;

    private CommentPanel _commentPanel = null;

    /**
     *  
     */
    private PersistanceMismatchDlg() {
        super(
                DApplication.getInstance().getJFrame(),
                "Problème avec la persistance des ensembles de la grille sélective",
                true);

        initialize();
    }

    public static PersistanceMismatchDlg getInstance() {
        if (_instance == null) {
            _instance = new PersistanceMismatchDlg();
        }
        return _instance;
    }

    private void initialize() {
        JPanel contentPane = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel commentPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        /*
         * XXXX GS : très grande dépendance entre MissingElementsPanel,
         * CommentPanel et FaultyFilterSetsPanel. Le dernier a besoin des 2
         * premiers pour s'instancier. Vérifier les pointeurs null dans
         * FaultyFilterSetsPanel
         */
        _missingElementsPanel = new MissingElementsPanel();
        _commentPanel = new CommentPanel();
        _faultyFilterSetsPanel = new FaultyFilterSetsPanel();

        JLabel titleLabel = new JLabel("Ensembles fautifs");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel unresolvableElementsLabel = new JLabel("Éléments introuvables");
        unresolvableElementsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));

        infoPanel.add(titleLabel);
        infoPanel.add(_faultyFilterSetsPanel.getJComponent());
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(unresolvableElementsLabel);
        infoPanel.add(_missingElementsPanel.getJComponent());

        commentPanel.add(_commentPanel.getJComponent());

        buttonPanel.add(new OKButton().getJComponent());

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.add(infoPanel);
        contentPane.add(Box.createVerticalStrut(30));
        contentPane.add(commentPanel);
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(buttonPanel);

        this.setContentPane(contentPane);
        this.setResizable(false);
    }

    private PersistanceMismatch getPersistanceMismatch(String filterSetName) {
        Iterator itrPM = SelectiveScheduleManager.getInstance()
                .getPersistanceMismatchManager().getPersistanceMismatches()
                .iterator();

        while (itrPM.hasNext()) {
            PersistanceMismatch pm = (PersistanceMismatch) itrPM.next();
            if (pm.getMismatchedFilterSet().getFilterSetIdentifier()
                    .getFilterSetName().equals(filterSetName)) {
                return pm;
            }
        }

        return null;
    }

    public void displayDlg() {
        this.setLocation((int) DApplication.getInstance().getJFrame()
                .getLocation().getX()
                + DConst.X_OFFSET, (int) DApplication.getInstance().getJFrame()
                .getLocation().getY()
                + DConst.Y_OFFSET);
        this.pack();
        this.setVisible(true);
    }
}
