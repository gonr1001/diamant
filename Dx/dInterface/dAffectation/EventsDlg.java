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
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import dInternal.dData.Activity;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dConditionsTest.EventAttach;
import dInternal.dConditionsTest.SetOfEvents;
import dInternal.dData.SetOfActivities;
import dInternal.dData.Type;
import dInternal.dData.Section;
import dInternal.dData.Unity;
import dInterface.DApplication;
import dInterface.dUtil.DXTools;

import dResources.DConst;

public class EventsDlg extends JDialog implements ActionListener{

  private Activity _currActivity;
  private DApplication _dApplic;
  private Dimension _dialogDim = new Dimension(600, 400);
  private EventAttach _currEvent;
  private int buttonsPanelHeight = 80;
  private JLabel _leftLabel, _centerLabel, _rightLabel;
  private JList _leftList, _centerList, _rightList;
  private JPanel _leftPanel, _centerPanel, _rightPanel, _rightArrowsPanel, _leftArrowsPanel;
  private Object[] selectedValues;
  private Resource res;
  private Section _currSection;
  private SetOfActivities _activities;
  private SetOfEvents _events;
  private SetOfResources _leftRes, _centerRes, _rightRes;
  private String _eventFullKey;
  private String[] _buttonsNames = {DConst.BUT_OK, DConst.BUT_APPLY, DConst.BUT_CANCEL};
  private Type _currType;
  private Unity _currUnity;
  private Vector _leftVector, _centerVector, _rightVector;

  private static Color LABEL_COLOR = Color.blue;
  private static String EVENTS_DLG_TITLE = "Evenements";//DConst.EVENTS_DLG_TITLE;
  private static String EVENTS_FIXED = "Figés";//DConst.EVENTS_FIXED;
  private static String EVENTS_PLACED = "Placés";//DConst.EVENTS_PLACED;
  private static String EVENTS_NOT_PLACED = "Non placés";//DConst.EVENTS_NOT_PLACED;

  public EventsDlg(DApplication dApplic) {
    super(dApplic.getJFrame(), EVENTS_DLG_TITLE, true);
    _dApplic = dApplic;
    if (_dApplic.getDMediator().getCurrentDoc() == null)
      return;
    _activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
    _events = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfEvents();
    //_students = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfStudents();
    //if (_activities != null && _students != null){
      jbInit();
      setLocationRelativeTo(dApplic.getJFrame());
      setVisible(true);
    //}
  }//end method


  private void jbInit(){
    getContentPane().setLayout(new BorderLayout());
    setSize(_dialogDim);
    setResizable(true);
    buildVectors();
    setLeftPanel();
    setCenterPanel();
    setRightPanel();
    JPanel buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    //_buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);
    //getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    //setCenterPanel();
    /*
    String [] s = {DConst.TO_RIGHT, DConst.TO_LEFT};
    JPanel pl = DXTools.arrowsPanel(this, s);
    JPanel pr = DXTools.arrowsPanel(this, s);
    JPanel container = new JPanel();
    container.setPreferredSize(new Dimension(500, 300));
    JPanel c = new JPanel();
    c.add(new JLabel("label"));
    c.setPreferredSize(new Dimension(300, 300));

    container.add(pl);
    container.add(c);
    container.add(pr);
    getContentPane().add(container);
*/
  }


  private void setCenterPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.5), (int)_dialogDim.getHeight()-buttonsPanelHeight);
      _centerList = new JList(_centerVector);
      JLabel titleLabel = new JLabel(EVENTS_PLACED + " ");
      _centerLabel = new JLabel(String.valueOf(_centerVector.size()));
      _centerLabel.setForeground(LABEL_COLOR);
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

  private void setLeftPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    //_leftVector = _activities.getIDsByField(3, "true");
    //set the _leftVector
    //_leftVector = new Vector();
    //buildVector(_leftVector, 3, "true");
    _leftList = new JList(_leftVector);
    JLabel titleLabel = new JLabel(EVENTS_FIXED + " ");
    _leftLabel = new JLabel(String.valueOf(_leftVector.size()));
    _leftLabel.setForeground(LABEL_COLOR);
    JPanel listPanel = DXTools.listPanel(_leftList, (int)panelDim.getWidth(), (int)panelDim.getHeight()-25);
    //the _leftPanel
    _leftPanel = new JPanel();
    _leftPanel.setPreferredSize(panelDim);
    //_leftPanel.setPreferredSize(new Dimension((int)panelDim.getWidth(), (int)panelDim.getHeight()-10));
    _leftPanel.add(titleLabel);
    _leftPanel.add(_leftLabel);
    _leftPanel.add(listPanel);
    //this panel is just for harmonise the size of all panels in the dialog
    JPanel panelContainer = new JPanel();
    panelContainer.add(_leftPanel);
    getContentPane().add(panelContainer, BorderLayout.WEST);
  }//end method


  private void setRightPanel(){
    Dimension panelDim = new Dimension((int)(_dialogDim.getWidth()*0.24), (int)_dialogDim.getHeight()-buttonsPanelHeight);
    //_rightVector = new Vector();
    //buildVector(_rightVector, 3, "true");
    _rightList = new JList(_rightVector);
    JLabel titleLabel = new JLabel(EVENTS_NOT_PLACED + " ");
    _rightLabel = new JLabel(String.valueOf(_rightVector.size()));
    _rightLabel.setForeground(LABEL_COLOR);
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
    System.out.println("e.getSource() "+e.getSource());
    //if the source is one of the the _leftArrowsPanel buttons
    if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0))) || (e.getSource().equals(_leftArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_leftArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_leftList, _centerList, _leftVector, _centerVector);
      else
        DXTools.listTransfers(_centerList, _leftList, _centerVector, _leftVector);
      _leftLabel.setText(String.valueOf(_leftVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
    }//end if ( (e.getSource().equals(_leftArrowsPanel.getComponent(0)))) || (e.getSource().equals(_leftArrowsPanel.getComponent(1)))) )
    //if the source is one of the the _rightArrowsPanel buttons
    if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) || (e.getSource().equals(_rightArrowsPanel.getComponent(1))) ){
      //if "toRight" button
      if (e.getSource().equals(_rightArrowsPanel.getComponent(0)))
        DXTools.listTransfers(_centerList, _rightList, _centerVector, _rightVector);
      else
        DXTools.listTransfers(_rightList, _centerList, _rightVector, _centerVector);
      _rightLabel.setText(String.valueOf(_rightVector.size()));
      _centerLabel.setText(String.valueOf(_centerVector.size()));
    }//end if ( (e.getSource().equals(_rightArrowsPanel.getComponent(0))) || (e.getSource().equals(_rightArrowsPanel.getComponent(1))) )
    //if Button CANCEL is pressed
    if (command.equals(_buttonsNames[2]))
      dispose();
    //if Button OK is pressed
    if (command.equals(_buttonsNames[0])){
      //if (_currentActivities.length != 0)
      //new EditActivityDlg(this, _dApplic, (String)_currentActivities[0]);
    }// if (command.equals(_buttonsNames[0]))
    //if Button APPLY is pressed
    if (command.equals(_buttonsNames[1])){
      //if (_currentActivities.length != 0)
      //new EditActivityDlg(this, _dApplic, (String)_currentActivities[0]);
    }// end if (command.equals(SHOW))

  }//end method

  /**
   * Builds the vectors _rightVector, _centerVector, _leftVector for their
   * first display
   */

  private void buildVectors(){
    _leftVector = new Vector();
    _centerVector = new Vector();
    _rightVector = new Vector();
    _leftRes = new SetOfResources(0);
    _centerRes = new SetOfResources(0);
    _rightRes = new SetOfResources(0);
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
        res = new Resource(_eventFullID, null);
      if (_currUnity.compareByField(2, "false")){
        _rightVector.add(_eventFullID);
        _rightRes.addResource(res,1);
      }else{
       if (_currUnity.compareByField(3, "true")){
        _leftVector.add(_eventFullID);
       _leftRes.addResource(res,1);
       }else{
          _centerVector.add(_eventFullID);
          _centerRes.addResource(res,1);
       }
       }//end else if (_currUnity.compareByField(2, "false"))
    }//end for
  }//end method

}//end class