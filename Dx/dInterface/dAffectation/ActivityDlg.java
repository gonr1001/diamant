package dInterface.dAffectation;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dAux.DoNothingCmd;
import dAux.DoNothingDlg;
import dInterface.DApplication;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;

public class ActivityDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  private SetOfActivities _activities;
  /**
   * The JList items choice by the user
   */
  private Object [] _currentActivities = new Object[0];
  private static String listAct = "Liste des activités";
  private static String show = "Afficher";
  private static String cancel = "Annuler";
  private static String notIncluded = "Non inclue(s)";
  private static String included = "Inclue(s)";
  private static String toLeft = "««";
  private static String toRight = "»»";
  /**
   * the vectors containing the activities ID
   */
  private Vector _noVisibleVec, _visibleVec;
  private JButton _show, _cancel, _toRight, _toLeft;
  private JDialog _jd;
  /**
   * the lists containing the activities ID
   */
  private JLabel _lVisible, _lNoVisible;
  private JList _noVisibleList, _visibleList;
  private JPanel _listsPanel, _buttonsPanel1, _buttonsPanel2;



  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */
  public ActivityDlg(DApplication dApplic) {
     super(dApplic.getJFrame(), listAct, true);
     _dApplic = dApplic;
     _jd = this;
     jbInit();
     setLocationRelativeTo(dApplic.getJFrame());
     setVisible(true);
     triggerListeners();
  }


  /**
   * Initialize the dialog
   */
  private void jbInit(){
    _show = new JButton(show);
    _show.setPreferredSize(new Dimension(80,22));
    _cancel = new JButton(cancel);
    _cancel.setPreferredSize(new Dimension(80,22));
    _listsPanel = new JPanel();
    //left panel
    JPanel rightPanel = new JPanel();
    JScrollPane rightSPane = new JScrollPane();
    setVectors();
    _noVisibleList = new JList(_noVisibleVec);
    rightSPane.setPreferredSize(new Dimension(150,300));
    rightSPane.getViewport().add(_noVisibleList);
    rightPanel = new JPanel(new BorderLayout());
    _lNoVisible = new JLabel(_noVisibleVec.size() + " " + notIncluded);
    rightPanel.add(_lNoVisible , BorderLayout.NORTH);
    rightPanel.add(rightSPane, BorderLayout.CENTER);
    //right panel
    JPanel leftPanel = new JPanel();
    JScrollPane leftSPane = new JScrollPane();
    _visibleList = new JList(_visibleVec);
    leftSPane = new JScrollPane();
    leftSPane.setPreferredSize(new Dimension(150,300));
    leftSPane.getViewport().add(_visibleList);
    leftPanel = new JPanel(new BorderLayout());
    _lVisible = new JLabel(_visibleVec.size() + " " + included);
    leftPanel.add(_lVisible, BorderLayout.NORTH);
    leftPanel.add(leftSPane, BorderLayout.CENTER);
    //buttons «« »» panel
    JPanel _buttonsPanel1 = new JPanel(new BorderLayout());
    _buttonsPanel1.setPreferredSize(new Dimension(50,70));
    //the buttons _toLeft and _toRight
    _toRight = new JButton(toRight);
    _toRight.setPreferredSize(new Dimension(50,35));
    _toLeft = new JButton(toLeft);
    _toLeft.setPreferredSize(new Dimension(50,35));
    _buttonsPanel1.add(_toRight, BorderLayout.NORTH);
    _buttonsPanel1.add(_toLeft, BorderLayout.SOUTH);
    //placing the panels and buttons into the _listsPanel
    _listsPanel.add(leftPanel, BorderLayout.EAST);
    _listsPanel.add(_buttonsPanel1, BorderLayout.CENTER);
    _listsPanel.add(rightPanel, BorderLayout.WEST);
    //buttons _show _cancel panel
    _buttonsPanel2 = new JPanel();
    _buttonsPanel2.add(_show);
    _buttonsPanel2.add(_cancel);

    //placing the elements into the JDialog
    setSize(380, 340);
    setResizable(false);
    triggerListeners();
    getContentPane().add(_listsPanel, BorderLayout.CENTER);
    getContentPane().add(_buttonsPanel2, BorderLayout.SOUTH);
  }//end method

  /**
   * Set the vectors _noVisibleVec and _visibleVec with the values found in the SetOfActivities
   */
  private void setVectors(){
    if (_dApplic.getDMediator().getCurrentDoc() == null){
      _noVisibleVec = new Vector();
      _visibleVec = new Vector();
    }else{
      _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
      _visibleVec = _activities.getIDsByField(3, "true");
      _noVisibleVec = _activities.getIDsByField(3, "false");
    } //end if (_dApplic.getDMediator().getCurrentDoc() == null)

  }

  /**
   * Launch the listeners
   */
  private void triggerListeners(){
    MouseListener mouseListenerLists = new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        if (((JList)e.getSource()).getModel().getSize() == 0)
          return;
        if (e.getSource().equals(_visibleList))
          _noVisibleList.clearSelection();
        else
          _visibleList.clearSelection();
        _currentActivities = ((JList)e.getSource()).getSelectedValues();
        if (e.getClickCount() == 2) {
            new EditActivityDlg(_jd,_dApplic, (String)_currentActivities[0]);
          }//end if
        }// end public void mouseClicked
    };//end definition of MouseListener mouseListener = new MouseAdapter(){

    _cancel.addActionListener(this);
    _show.addActionListener(this);
    _toLeft.addActionListener(this);
    _toRight.addActionListener(this);
    _noVisibleList.addMouseListener(mouseListenerLists);
    _visibleList.addMouseListener(mouseListenerLists);

  }//end triggerListeners()


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (command.equals(cancel))
        dispose();
    if (command.equals(show)){
      if (_currentActivities.length != 0)
      new EditActivityDlg(this, _dApplic, (String)_currentActivities[0]);
    }// end if (command.equals("Afficher"))
    if (command.equals(toLeft))
      actionButtonsLR(toLeft);
    if (command.equals(toRight))
      actionButtonsLR(toRight);
  }//end method

  /**
   * Execute the actions for the buttons _toLeft and _toRight
   * @param button Identifies the button pushed
   */
  private void actionButtonsLR(String button){
    boolean visible = false;
    int i = 0;
    int[] it=_visibleList.getSelectedIndices();
    if (button == toLeft)
      visible = true;
    if (_currentActivities.length != 0){
      for (i = 0; i < _currentActivities.length; i++)
        ((Activity)(_activities.getResource((String)_currentActivities[i]).getAttach())).setActivityVisibility(visible);
      setVectors();
      _noVisibleList.setListData(_noVisibleVec);
      _visibleList.setListData(_visibleVec);
    }//end if (_currentActivities.length != 0)
    //if button pressed is "_toLeft"
    if (visible == true){
      //_visibleList.setSelectedIndices(binaryIndexSearcher(true));
      _visibleList.setSelectedIndices(getIndices(true));
      _noVisibleList.clearSelection();
    }else{
      //_noVisibleList.setSelectedIndices(binaryIndexSearcher(false));
      _noVisibleList.setSelectedIndices(getIndices(false));
      _visibleList.clearSelection();
    }
    _lNoVisible.setText(_noVisibleVec.size() + " " + notIncluded);
    _lVisible.setText(_visibleVec.size() + " " + included);
  }//end method

  /**
   * Search the indices to be showed as selected in a JList. This method use a binary search
   * to find these indices
   * @param visible true if the list is the _visibleList
   * @return An array containing the indices of the items to be showed as selected
   */

  private int[] binaryIndexSearcher(boolean visible){
    Vector analyzedVec = _visibleVec;
    int [] indices = new int[_currentActivities.length];//the place fro keeping the indices to set selected
    if (visible == false)
      analyzedVec = _noVisibleVec;
    for(int i = 0; i < _currentActivities.length; i++){
      int low = 0;
      int high = analyzedVec.size()-1;
      while(low <= high){
        int mid = (low + high) / 2;
        int diff = ((String)analyzedVec.get(mid)).compareTo((String)(_currentActivities[i]));
        if (diff == 0){
          indices[i] = mid;
          System.out.println("Indices1[" + i + "}= " + indices[i]);
          break;
        }
        else if(diff < 0)
          low = mid + 1;
        else
          high = mid - 1;
      }//end while
    }//end for
    return indices;
  }

  /**
   * Search the indices to be showed as selected in a JList. The search is made in the vector that
   * contains the list items
   * @param visible true if the list is the _visibleList
   * @return An array containing the indices of the items to be showed as selected
   */

  private int[] getIndices(boolean visible){
    Vector analyzedVec = _visibleVec;
    int [] indices = new int[_currentActivities.length];//the place fro keeping the indices to set selected
    if (visible == false)
      analyzedVec = _noVisibleVec;
    for (int i = 0; i < _currentActivities.length; i++){
      indices[i] = analyzedVec.indexOf(_currentActivities[i]);
      System.out.println("Indices2[" + i + "}= " + indices[i]);
    }
    return indices;
  }

}// end class