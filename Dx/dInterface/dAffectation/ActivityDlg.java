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
  private Vector _noVisibleVec, _visibleVec;
  private JButton _show, _cancel;
  private JPanel _listsPanel, _buttonsPanel;
  private JList _noVisibleList, _visibleList;

  /**
   * Dafault constructor
   * @param dApplic The application object (for extracting the JFrame)
   */
  public ActivityDlg(DApplication dApplic) {
     super(dApplic.getJFrame(), "Liste des activités");
     _dApplic = dApplic;
     jbInit();
     setLocationRelativeTo(dApplic.getJFrame());
     setVisible(true);
     this.actionManager();
     //new DoNothingDlg(dApplic,"Nothing");

  }



  /**
   * Initialize the dialog
   */

  public void jbInit(){
    _show = new JButton("Afficher");
    _show.setPreferredSize(new Dimension(75,22));
    _cancel = new JButton("Annuler");
    _cancel.setPreferredSize(new Dimension(75,22));
    _listsPanel = new JPanel();
    //left panel
    JPanel leftPanel = new JPanel();
    JScrollPane leftSPane = new JScrollPane();
    setVectors();
    _noVisibleList = new JList(_noVisibleVec);
    leftSPane.setPreferredSize(new Dimension(150,300));
    leftSPane.getViewport().add(_noVisibleList);
    leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(new JLabel(_noVisibleVec.size() + " " + "Non placée(s)"), BorderLayout.NORTH);
    leftPanel.add(leftSPane, BorderLayout.CENTER);
    //right panel
    JPanel rightPanel = new JPanel();
    JScrollPane rightSPane = new JScrollPane();
    _visibleList = new JList(_visibleVec);
    rightSPane = new JScrollPane();
    rightSPane.setPreferredSize(new Dimension(150,300));
    rightSPane.getViewport().add(_visibleList);
    rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(new JLabel(_visibleVec.size() + " " + "placée(s)"), BorderLayout.NORTH);
    rightPanel.add(rightSPane, BorderLayout.CENTER);
    //placing the panels into the _listsPanel
    _listsPanel.add(leftPanel, BorderLayout.EAST);
    _listsPanel.add(rightPanel, BorderLayout.WEST);
    //buttons panel
    _buttonsPanel = new JPanel();
    _buttonsPanel.add(_show);
    _buttonsPanel.add(_cancel);

    //placing the elements into the JDialog
    setSize(320, 340);
    setResizable(false);
    getContentPane().add(_listsPanel, BorderLayout.CENTER);
    getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    //setVisible(true);
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
      _visibleVec = activities.getIDByVisibility(true);
      _noVisibleVec = activities.getIDByVisibility(false);
    } //end if (_dApplic.getDMediator().getCurrentDoc() == null)

  }



  private void actionManager(){
    _cancel.addActionListener(this);
    _show.addActionListener(this);
    /*
    _cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {


      }//end actionPerformed
    });//end addActionListener
    */
  }

  public void actionPerformed(ActionEvent e){
    String command = e.getActionCommand();
    if (command.equals("Annuler"))
        dispose();
    if (command.equals("Afficher"))
        dispose();
  }



}