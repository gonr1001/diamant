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

public class ActivityDlg extends JDialog implements ActionListener {

  private DApplication _dApplic;
  /**
   * the vectors containing the activities ID
   */
  private Vector _noVisibleVec, _visibleVec;
  private JButton _show, _cancel, _toRight, _toLeft;
  private JPanel _listsPanel, _buttonsPanel1, _buttonsPanel2;
  private JDialog _jd;
  /**
   * the lists containing the activities ID
   */
  private JList _noVisibleList, _visibleList;

  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */
  public ActivityDlg(DApplication dApplic) {
     super(dApplic.getJFrame(), "Liste des activités", true);
     //dApplic.getJFrame().setEnabled(false);
     _dApplic = dApplic;
     _jd = this;
     jbInit();
     setLocationRelativeTo(dApplic.getJFrame());
     setVisible(true);
     triggerListeners();

     //new DoNothingDlg(dApplic,"Nothing");

  }


  /**
   * Initialize the dialog
   */

  private void jbInit(){
    _show = new JButton("Afficher");
    _show.setPreferredSize(new Dimension(75,22));
    _cancel = new JButton("Annuler");
    _cancel.setPreferredSize(new Dimension(75,22));
    //_cancel.addActionListener(this);
    _listsPanel = new JPanel();
    //left panel
    JPanel rightPanel = new JPanel();
    JScrollPane rightSPane = new JScrollPane();
    setVectors();
    _noVisibleList = new JList(_noVisibleVec);
    rightSPane.setPreferredSize(new Dimension(150,300));
    rightSPane.getViewport().add(_noVisibleList);
    rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(new JLabel(_noVisibleVec.size() + " " + "Non inclue(s)"), BorderLayout.NORTH);
    rightPanel.add(rightSPane, BorderLayout.CENTER);
    //right panel
    JPanel leftPanel = new JPanel();
    JScrollPane leftSPane = new JScrollPane();
    _visibleList = new JList(_visibleVec);
    leftSPane = new JScrollPane();
    leftSPane.setPreferredSize(new Dimension(150,300));
    leftSPane.getViewport().add(_visibleList);
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(new JLabel(_visibleVec.size() + " " + "Inclue(s)"), BorderLayout.NORTH);
    leftPanel.add(leftSPane, BorderLayout.CENTER);
    //buttons «« »» panel
    JPanel _buttonsPanel1 = new JPanel(new BorderLayout());
    _buttonsPanel1.setPreferredSize(new Dimension(50,70));
    //the buttons _toLeft and _toRight
    _toRight = new JButton("»»");
    _toRight.setPreferredSize(new Dimension(50,30));
    _toLeft = new JButton("««");
    _toLeft.setPreferredSize(new Dimension(50,30));
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
      SetOfActivities activities = _dApplic.getDMediator().getCurrentDoc().getDM().getSetOfActivities();
      //_visibleVec = activities.getIDByVisibility(true);
      _visibleVec = activities.getIDsByField(3, "true");
      _noVisibleVec = activities.getIDsByField(3, "false");
    } //end if (_dApplic.getDMediator().getCurrentDoc() == null)

  }

  /**
   * Launch the listeners
   */

  private void triggerListeners(){
    MouseListener mouseListener = new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        String currentActivity = "";
        if (e.getClickCount() == 2) {
            currentActivity = (String)((JList)e.getSource()).getSelectedValue();
            currentActivity = currentActivity.trim();
            new EditActivityDlg(_jd,_dApplic, currentActivity);
            //new DoNothingDlg(_dApplic,"Nothing");
          }//end if
        }// end public void mouseClicked
    };//end definition of MouseListener mouseListener = new MouseAdapter(){

    _cancel.addActionListener(this);
    _show.addActionListener(this);
    _toLeft.addActionListener(this);
    _toRight.addActionListener(this);
    _noVisibleList.addMouseListener(mouseListener);
    _visibleList.addMouseListener(mouseListener);

  }//end method


  /**
   *
   * @param e an event
   */
  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (command.equals("Annuler"))
        dispose();
    if (command.equals("Afficher"))
        new EditActivityDlg(this, _dApplic, "A");
    if (command.equals("««"))
        new DoNothingDlg(_dApplic,"Nothing");
    if (command.equals("»»"))
        new DoNothingDlg(_dApplic,"Nothing");
  }

}