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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import dInternal.dConditionsTest.EventAttach;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Unity;

import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dResources.DConst;

public class EventsDlg extends JDialog implements ActionListener{

  private DApplication _dApplic;
  private Dimension _dialogDim = new Dimension(600, 400);
  private EventAttach _currEvent;
  private int buttonsPanelHeight = 80;
  private JLabel _leftLabel, _centerLabel, _rightLabel;
  private JList _leftList, _centerList, _rightList;
  private JPanel _leftPanel, _centerPanel, _rightPanel, _rightArrowsPanel, _leftArrowsPanel, _buttonsPanel;
  private Object[] selectedItems;
  private SetOfActivities _activities;
  private SetOfEvents _events;
  private String _eventFullKey;
  private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private Unity _currUnity;
  private Vector _leftVector, _centerVector, _rightVector;

  //private static Color LABEL_COLOR = DConst.COLOR_STUD;
  //private static String EVENTS_DLG_TITLE = DConst.EVENTS_DLG_TITLE;
  //private static String EVENTS_FIXED = DConst.EVENTS_FIXED;
  //private static String EVENTS_PLACED = DConst.EVENTS_PLACED;
  //private static String EVENTS_NOT_PLACED = DConst.EVENTS_NOT_PLACED;


  /**
   * Constructor
   * @param dApplic The application
   */
  public EventsDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), DConst.EVENTS_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _events = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents();
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
  }//end method


  /**
   * Initialise the dialog
   */
  private void jbInit(){
    getContentPane().setLayout(new BorderLayout());
    setSize(_dialogDim);
    setResizable(false);
    buildVectors();
    setLeftPanel();
    setCenterPanel();
    setRightPanel();
    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //setting disable the APPLY button
    _buttonsPanel.getComponent(1).setEnabled(false);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
  }


  /**
   * Sets the _centerPanel, the panel containing the _centerList and the
   * arrows panels
   */
  private void setCenterPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.5), (int)_dialogDim.getHeight()-buttonsPanelHeight);
      _centerList = new JList(_centerVector);
      _centerList.addMouseListener(mouseListenerLists);
      JLabel titleLabel = new JLabel(DConst.EVENTS_PLACED + " ");
      _centerLabel = new JLabel(String.valueOf(_centerVector.size()));
      _centerLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
      //The listContainerPanel
      JPanel listPanel = DXTools.listPanel(_centerList, (int)panelDim.getWidth()-140, (int)panelDim.getHeight()-25);
      JPanel listContainerPanel = new JPanel();
      listContainerPanel.setPreferredSize(new Dimension((int)panelDim.getWidth()-140, (int)panelDim.getHeight()));
      listContainerPanel.add(titleLabel);
      listContainerPanel.add(_centerLabel);
      listContainerPanel.add(listPanel);
      //the arrows panels
      String [] arrowsNames = {DConst.TO_RIGHT, DConst.TO_LEFT};
      _leftArrowsPanel = DXTools.arrowsPanel(this, arrowsNames);
      _rightArrowsPanel = DXTools.arrowsPanel(this, arrowsNames);
      //the _centerPanel
      _centerPanel = new JPanel();
      _centerPanel.setPreferredSize(panelDim);
      _centerPanel.add(_leftArrowsPanel);
      _centerPanel.add(listContainerPanel);
      _centerPanel.add(_rightArrowsPanel);
    getContentPane().add(_centerPanel, BorderLayout.CENTER);
  }//end method

  /**
   * Sets the _leftPanel
   */
  private void setLeftPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    _leftList = new JList(_leftVector);
    _leftList.addMouseListener(mouseListenerLists);
    JLabel titleLabel = new JLabel(DConst.EVENTS_FIXED + " ");
    _leftLabel = new JLabel(String.valueOf(_leftVector.size()));
    _leftLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JPanel listPanel = DXTools.listPanel(_leftList, (int)panelDim.getWidth(), (int)panelDim.getHeight()-25);
    //the _leftPanel
    _leftPanel = new JPanel();
    _leftPanel.setPreferredSize(panelDim);
    _leftPanel.add(titleLabel);
    _leftPanel.add(_leftLabel);
    _leftPanel.add(listPanel);
    //this panel is just for harmonise the size of all panels in the dialog
    JPanel panelContainer = new JPanel();
    panelContainer.add(_leftPanel);
    getContentPane().add(panelContainer, BorderLayout.WEST);
  }//end method


  /**
   * Sets the _rightPanel
   */
  private void setRightPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    _rightList = new JList(_rightVector);
    _rightList.addMouseListener(mouseListenerLists);
    JLabel titleLabel = new JLabel(DConst.EVENTS_NOT_PLACED + " ");
    _rightLabel = new JLabel(String.valueOf(_rightVector.size()));
    _rightLabel.setForeground(DConst.COLOR_QUANTITY_DLGS);
    JPanel listPanel = DXTools.listPanel(_rightList, (int)panelDim.getWidth(), (int)panelDim.getHeight()-25);
    //the _rightPanel
    _rightPanel = new JPanel();
    _rightPanel.setPreferredSize(panelDim);
    //_rightPanel.setPreferredSize(new Dimension((int)panelDim.getWidth(), (int)panelDim.getHeight()-10));
    _rightPanel.add(titleLabel);
    _rightPanel.add(_rightLabel);
    _rightPanel.add(listPanel);
    //this panel is just for harmonise the size of all panels in the dialog
    JPanel panelContainer = new JPanel();
    panelContainer.add(_rightPanel);
    getContentPane().add(panelContainer, BorderLayout.EAST);
  }//end method

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    //if the source is one of the the _leftArrowsPanel buttons
    if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0))) ||
         (e.getSource().equals(_leftArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_leftArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_leftList, _centerList, _leftVector, _centerVector);
      else
        DXTools.listTransfers(_centerList, _leftList, _centerVector, _leftVector);
      _leftLabel.setText(String.valueOf(_leftVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
      _buttonsPanel.getComponent(1).setEnabled(true);
    }//end if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0)))) || (e.getSource().equals(_leftArrowsPanel.getComponent(1)))) )
    //if the source is one of the the _rightArrowsPanel buttons
    if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) ||
         (e.getSource().equals(_rightArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_rightArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_centerList, _rightList, _centerVector, _rightVector);
      else
        DXTools.listTransfers(_rightList, _centerList, _rightVector, _centerVector);
      _rightLabel.setText(String.valueOf(_rightVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
      _buttonsPanel.getComponent(1).setEnabled(true);
    }//end if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) || (e.getSource().equals(_rightArrowsPanel.getComponent(1))) )
    //if Button CANCEL is pressed
    if (command.equals(_buttonsNames[2]))
      dispose();
    //if Button OK is pressed
    if (command.equals(_buttonsNames[0])){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      dispose();
    }
    //if Button APPLY is pressed
    if (command.equals(_buttonsNames[1])){
      setUnities();
      _dApplic.getDMediator().getCurrentDoc().getDM().sendEvent(this);
      _buttonsPanel.getComponent(1).setEnabled(false);
    }// end if Button APPLY
  }//end method

  /**
   * Builds the vectors _rightVector, _centerVector, _leftVector for their
   * first display
   */

  private void buildVectors(){
    _leftVector = new Vector();
    _centerVector = new Vector();
    _rightVector = new Vector();
    String _eventFullID;
    StringTokenizer stk;
    for(int i = 0; i < _events.size(); i++){
      _eventFullKey = ((EventAttach)_events.getResourceAt(i).getAttach()).
                getPrincipalRescKey();
      stk = new StringTokenizer(_eventFullKey, ".");
      _currUnity = _activities.getUnity(Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()),
                                        Long.parseLong(stk.nextToken()));
      stk = new StringTokenizer(_eventFullKey, ".");
      _eventFullID = _activities.getUnityCompleteName(Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()),
          Long.parseLong(stk.nextToken()));
      if (_currUnity.compareByField(2, "false")){
        _rightVector.add(_eventFullID);
      }else{
        if (_currUnity.compareByField(3, "true")){
          _leftVector.add(_eventFullID);
        }else{
          _centerVector.add(_eventFullID);
        }
      }//end else if (_currUnity.compareByField(2, "false"))
    }//end for
  }//end method

  /**
   * The MouseListener for the JLists
   */
  private MouseListener mouseListenerLists = new MouseAdapter(){
    public void mouseClicked(MouseEvent e) {
      if (((JList)e.getSource()).getModel().getSize() == 0)
        return;
      if (e.getSource().equals(_leftList)){
          _centerList.clearSelection();
          _rightList.clearSelection();
          selectedItems = _leftList.getSelectedValues();
      }//end if (e.getSource().equals(_leftList))
      if (e.getSource().equals(_centerList)){
          _leftList.clearSelection();
          _rightList.clearSelection();
          selectedItems = _centerList.getSelectedValues();
      }//end if (e.getSource().equals(_centerList))
      if (e.getSource().equals(_rightList)){
          _centerList.clearSelection();
          _leftList.clearSelection();
          selectedItems = _rightList.getSelectedValues();
      }//end if (e.getSource().equals(_rightList))
    }// end public void mouseClicked
  };//end definition of MouseListener mouseListener = new MouseAdapter(){

  /**
  * Set the unities with the values in each JList
  */
  private void setUnities(){
    String str = null;
      for(int i = 0; i < _leftVector.size(); i++){
        str = (String)_leftVector.elementAt(i);
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 3, "true");
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 2, "true");
      }//end for
      for(int i = 0; i < _centerVector.size(); i++){
        str = (String)_centerVector.elementAt(i);
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 3, "false");
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 2, "true");
      }//end for
      for(int i = 0; i < _rightVector.size(); i++){

        str = (String)_rightVector.elementAt(i);
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 3, "false");
        _activities.setUnityField(str.substring(0,6),
                                  str.substring(6,7),
                                  str.substring(8,9),
                                  str.substring(9,10), 2, "false");
      }//end for
  }

}//end class