/*
 * Created on 7 juin 2005
 *
 */
package dInterface.selectiveSchedule.dialog;

import dConstants.DConst;

import dInterface.DApplication;

import dInterface.selectiveSchedule.SelectiveScheduleManager;

import dInterface.selectiveSchedule.filters.FilterSet;
import dInterface.selectiveSchedule.filters.FilterSetNameException;

import dInternal.DResource;

import org.apache.log4j.Logger;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;


/**
 * Dialogue d'édition d'ensemble.  Ce dialogue est utilisé lors de la
 * création d'un ensemble ainsi que lors de la modification d'un
 * ensemble.
 * 
 * @author Pascal
 *
 */
public class SetEditorDlg extends JDialog {
    private static final int STATE_CREATE = 1;
    private static final int STATE_MODIFY = 2;
    private static SetEditorDlg _instance = null;
    private Logger logger = Logger.getLogger(SetEditorDlg.class);
    private int state = -1;
    private JList _availableElementsJList = null;
    private DefaultListModel _availableElementsJListModel = null;
    private JList _setElementsJList = null;
    private DefaultListModel _setElementsJListModel = null;
    private JTextField _setName = null;
    private FilterSet _modifiedFilterSet = null;
    
    /**
     * @throws java.awt.HeadlessException
     */
    private SetEditorDlg() throws HeadlessException {
        //        super();
        super(SelectiveScheduleDlg.getInstance(), "Éditeur d'ensemble", true);
        
        initialize();
    }
    
//    /**
//     * @param owner
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Dialog owner) throws HeadlessException {
//        super(owner);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param modal
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Dialog owner, boolean modal) throws HeadlessException {
//        super(owner, modal);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Frame owner) throws HeadlessException {
//        super(owner);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param modal
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Frame owner, boolean modal) throws HeadlessException {
//        super(owner, modal);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Dialog owner, String title) throws HeadlessException {
//        super(owner, title);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @param modal
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Dialog owner, String title, boolean modal)
//    throws HeadlessException {
//        super(owner, title, modal);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Frame owner, String title) throws HeadlessException {
//        super(owner, title);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @param modal
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Frame owner, String title, boolean modal)
//    throws HeadlessException {
//        super(owner, title, modal);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @param modal
//     * @param gc
//     * @throws java.awt.HeadlessException
//     */
//    private SetEditorDlg(Dialog owner, String title, boolean modal,
//            GraphicsConfiguration gc) throws HeadlessException {
//        super(owner, title, modal, gc);
//        
//        initialize();
//    }
//    
//    /**
//     * @param owner
//     * @param title
//     * @param modal
//     * @param gc
//     */
//    private SetEditorDlg(Frame owner, String title, boolean modal,
//            GraphicsConfiguration gc) {
//        super(owner, title, modal, gc);
//        
//        initialize();
//    }
    
    /**
     * Le nom d'un ensemble doit commencer par une lettre. Ensuite il peut
     * contenir un nombre illimité de caractère.
     * 
     * @param setName
     * @return
     */
    private boolean isSetNameValid(String setName) {
        return Pattern.matches("^[a-zA-Z]+.*", setName);
    }
    
    public static SetEditorDlg getInstance() {
        if (_instance == null) {
            _instance = new SetEditorDlg();
        }
        
        return _instance;
    }
    
    public void displayCreateDlg() {
        resetDlg();
        
        populateAvailableElements();
        state = STATE_CREATE;
        
        setResizable(false);
        pack();
        setVisible(true);
    }
    
//    private void createSet() {
//    }
    
    private void populateAvailableElements() {
        Iterator itrActivityNames = DApplication.getInstance().getDModel()
        .getSetOfActivities()
        .getNamesVector(1).iterator();
        
        _availableElementsJListModel.clear();
        
        Object next = null;
        
        while (itrActivityNames.hasNext()) {
            next = itrActivityNames.next();
            
            if (next == null) {
                continue;
            }
            
            _availableElementsJListModel.addElement(next);
        }
    }
    
    public void displayModifyDlg(FilterSet filterSetToModify) {
        state = STATE_MODIFY;
        
        _modifiedFilterSet = filterSetToModify;
        
        _setName.setText(filterSetToModify.getFilterSetIdentifier()
                .getFilterSetName());
        
        Iterator itrElementsOfFilterSetToModify = filterSetToModify.getIterator();
        
        populateAvailableElements();
        
        _setElementsJListModel.clear();
        
        while (itrElementsOfFilterSetToModify.hasNext()) {
            String elementID = ((DResource) itrElementsOfFilterSetToModify.next()).getID();
            _setElementsJListModel.addElement(elementID);
            _availableElementsJListModel.removeElement(elementID);
        }
        
        setResizable(false);
        pack();
        setVisible(true);
    }
    
    private void initialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        JPanel setNamePanel = new JPanel();
        setNamePanel.setLayout(new BoxLayout(setNamePanel, BoxLayout.LINE_AXIS));
        
        JPanel elementsPanel = new JPanel();
        elementsPanel.setLayout(new BoxLayout(elementsPanel, BoxLayout.LINE_AXIS));
        
        JPanel setElementsPanel = new JPanel();
        setElementsPanel.setLayout(new BoxLayout(setElementsPanel,
                BoxLayout.PAGE_AXIS));
        
        JPanel availableElementsPanel = new JPanel();
        availableElementsPanel.setLayout(new BoxLayout(availableElementsPanel,
                BoxLayout.PAGE_AXIS));
        
        JPanel transferElementsPanel = new JPanel();
        transferElementsPanel.setLayout(new BoxLayout(transferElementsPanel,
                BoxLayout.PAGE_AXIS));
        
        JPanel basicButtonsPanel = new JPanel();
        basicButtonsPanel.setLayout(new BoxLayout(basicButtonsPanel,
                BoxLayout.LINE_AXIS));
        
        JLabel setNameLabel = new JLabel("Nom de l'ensemble");
        _setName = new JTextField();
        
        JLabel setElementsLabel = new JLabel("Éléments de l'ensemble");
        _setElementsJListModel = new DefaultListModel();
        _setElementsJList = new JList();
        _setElementsJList.setModel(_setElementsJListModel);
        
        JScrollPane setElementsScrollable = new JScrollPane(_setElementsJList);
        
        JLabel availableElementsLabel = new JLabel("Éléments disponibles");
        _availableElementsJListModel = new DefaultListModel();
        _availableElementsJList = new JList();
        _availableElementsJList.setModel(_availableElementsJListModel);
        
        JScrollPane availableElementsScrollable = new JScrollPane(_availableElementsJList);
        
        JButton toAvailableButton = new ToAvailableButton().getJButton();
        
        JButton toSetButton = new ToSetButton().getJButton();
        
        JButton okButton = new OKButton().getJButton();
        
        JButton cancelButton = new CancelButton().getJButton();
        
        setNamePanel.add(Box.createHorizontalStrut(15));
        setNamePanel.add(setNameLabel);
        setNamePanel.add(Box.createHorizontalStrut(30));
        setNamePanel.add(_setName);
        setNamePanel.add(Box.createHorizontalStrut(15));
        
        setElementsPanel.add(setElementsLabel);
        setElementsPanel.add(setElementsScrollable);
        
        availableElementsPanel.add(availableElementsLabel);
        availableElementsPanel.add(availableElementsScrollable);
        
        transferElementsPanel.add(toAvailableButton);
        transferElementsPanel.add(Box.createVerticalStrut(15));
        transferElementsPanel.add(toSetButton);
        
        elementsPanel.add(Box.createHorizontalStrut(15));
        elementsPanel.add(setElementsPanel);
        elementsPanel.add(Box.createHorizontalStrut(15));
        elementsPanel.add(transferElementsPanel);
        elementsPanel.add(Box.createHorizontalStrut(15));
        elementsPanel.add(availableElementsPanel);
        elementsPanel.add(Box.createHorizontalStrut(15));
        
        basicButtonsPanel.add(Box.createHorizontalStrut(15));
        basicButtonsPanel.add(okButton);
        basicButtonsPanel.add(Box.createHorizontalStrut(30));
        basicButtonsPanel.add(cancelButton);
        basicButtonsPanel.add(Box.createHorizontalStrut(15));
        
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(setNamePanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(elementsPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(basicButtonsPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        setContentPane(mainPanel);
        
        setLocation(DApplication.getInstance().getJFrame().getX() +
                (2 * DConst.X_OFFSET),
                DApplication.getInstance().getJFrame().getY() +
                (2 * DConst.Y_OFFSET));
    }
    
    public void validateUserInput()
    throws FilterSetNameException, EmptySetException {
        if (isSetNameValid(_setName.getText()) == false) {
            throw new FilterSetNameException(
            "Le nom de l'ensemble est invalide. Il doit commencer par une lettre.");
        }
        
        if (_setElementsJList.getModel().getSize() == 0) {
            throw new EmptySetException("Il doit y avoir au moins un élément dans l'ensemble.");
        }
    }
    
    /**
     * Remet à zéro l'état du dialogue
     *
     */
    private void resetDlg() {
        _setName.setText(null);
        _setElementsJListModel.clear();
    }
    
    /**
     * @author Pascal
     *
     */
    public class ToAvailableButton implements ActionListener {
        private JButton button = null;
        /**
         *
         */
        public ToAvailableButton() {
            super();
            
            button = new JButton(DConst.TO_RIGHT);
            
            button.addActionListener(this);
        }
        
        private void transferElementFromSetToAvailable() {
            Object[] selectedElement = _setElementsJList.getSelectedValues();
            
            for (int i = 0; i < selectedElement.length; ++i) {
                _setElementsJListModel.removeElement(selectedElement[i]);
                _availableElementsJListModel.addElement(selectedElement[i]);
            }
            
            // sort
            Object[] toSort = _availableElementsJListModel.toArray();
            Arrays.sort(toSort);
            
            _availableElementsJListModel.clear();
            
            for (int i = 0; i < toSort.length; ++i) {
                _availableElementsJListModel.addElement(toSort[i]);
            }
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
        	e.toString();
            transferElementFromSetToAvailable();
        }
        
        JButton getJButton() {
            return button;
        }
    }
    
    /**
     * @author Pascal
     *
     */
    public class ToSetButton implements ActionListener {
        private JButton button = null;
        
        /**
         *
         */
        public ToSetButton() {
            super();
            
            button = new JButton(DConst.TO_LEFT);
            
            button.addActionListener(this);
        }
        
        /*
         * (non-Javadoc)
         *
         * @see dInterface.selectiveSchedule.dialog.SetEditDlgToSet#transferElementFromAvailableToSet()
         */
        private void transferElementFromAvailableToSet() {
            Object[] selectedElement = _availableElementsJList.getSelectedValues();
            
            for (int i = 0; i < selectedElement.length; ++i) {
                _availableElementsJListModel.removeElement(selectedElement[i]);
                _setElementsJListModel.addElement(selectedElement[i]);
            }
            
            // sort
            Object[] toSort = _setElementsJListModel.toArray();
            Arrays.sort(toSort);
            
            _setElementsJListModel.clear();
            
            for (int i = 0; i < toSort.length; ++i) {
                _setElementsJListModel.addElement(toSort[i]);
            }
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
        	e.toString();
            transferElementFromAvailableToSet();
        }
        
        JButton getJButton() {
            return button;
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
        public OKButton() {
            button = new JButton("Appliquer");
            
            button.addActionListener(this);
        }
        
        /*
         * (non-Javadoc)
         *
         * @see dInterface.selectiveSchedule.dialog.SetEditorDlgOKButton#updateModel()
         */
        private void updateModel()
        throws UnknownDlgStateException, FilterSetNameException, 
        EmptySetException {
            if (state == STATE_CREATE) {
                validateUserInput();
                
                FilterSet fs = SelectiveScheduleManager.getInstance()
                .createFilterSet();
                
                fs.getFilterSetIdentifier().setFilterSetName(_setName.getText());
                
                ListModel setElements = _setElementsJList.getModel();
                
                for (int i = 0; i < setElements.getSize(); ++i) {
                    fs.addFilter(DApplication.getInstance().getDModel()
                            .getSetOfActivities().getResource((String) setElements.getElementAt(
                                    i)));
                }
                
                SelectiveScheduleManager.getInstance().addFilterSet(fs);
            } else if (state == STATE_MODIFY) {
                validateUserInput();
                
                if (_modifiedFilterSet.getFilterSetIdentifier()
                        .getFilterSetName().equals(_setName.getText()) == false) {
                    _modifiedFilterSet.getFilterSetIdentifier()
                    .setFilterSetName(_setName.getText());
                }
                
                _modifiedFilterSet.clearFilterSet();
                
                for (int i = 0; i < _setElementsJListModel.getSize(); ++i) {
                    _modifiedFilterSet.addFilter(DApplication.getInstance()
                            .getDModel()
                            .getSetOfActivities()
                            .getResource((String) _setElementsJListModel.getElementAt(
                                    i)));
                }
            } else { // aucun état connu a été trouvé
                throw new UnknownDlgStateException();
            }
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            try {
            	e.toString();
                updateModel();
                /* au cas où le model a changé suite à updateModel() */
                SelectiveScheduleDlg.getInstance().updateDlg(); 
                SetEditorDlg.this.dispose();
            } catch (UnknownDlgStateException e1) {
                logger.error(e1.getMessage());
                JOptionPane.showMessageDialog(SetEditorDlg.this,
                        e1.getMessage(),
                        "Erreur interne", JOptionPane.ERROR_MESSAGE);
            } catch (FilterSetNameException e1) {
                logger.error(e1.getMessage());
                JOptionPane.showMessageDialog(SetEditorDlg.this,
                        e1.getMessage(),
                        "Nom déjà utilisé", JOptionPane.WARNING_MESSAGE);
            } catch (EmptySetException e1) {
                logger.error(e1.getMessage());
                JOptionPane.showMessageDialog(SetEditorDlg.this,
                        e1.getMessage(),
                        "Ensemble vide", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        JButton getJButton() {
            return button;
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
        public CancelButton() {
            button = new JButton("Fermer");
            
            button.addActionListener(this);
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
        	e.toString();
            SetEditorDlg.this.dispose();
        }
        
        JButton getJButton() {
            return button;
        }
    }
}
