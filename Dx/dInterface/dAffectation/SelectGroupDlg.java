package dInterface.dAffectation;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dConstants.DConst;
import dInterface.dUtil.DXTools;
/**
 *
 * AboutDlg is a class used to display the about dialog.
 *
 */
public class SelectGroupDlg extends JDialog implements ActionListener {
  //DApplication _dApplic;
  private String [] _buttonsNames = {DConst.BUT_OK,DConst.BUT_CANCEL};
  private JPanel  _buttonsPanel;
  private JTextField _textField0;
  private Dialog _parent;
  private boolean _addGroup;
  private Vector _list;
  private SectionModifDlg _sectionMod;

  /**
   * the constructor will displays the dialog
   *
   * @param jframe    the parent of the dialog
   * @param str       the title of the window dialog
   * @since           JDK1.3
   */

  public SelectGroupDlg(Dialog parent,  Vector list, boolean add) {
    super(parent,"Choix du groupe à "+DConst.BUT_ADD);
    _list= list;
    _parent = parent;
    _sectionMod= (SectionModifDlg)parent;
    _addGroup = add;
    if(add)
      jbInitAddGroup();
    else
      jbInitRemGroup();

  } // end constructor AboutDlg

  public void actionPerformed( ActionEvent ae ) {
    String command = ae.getActionCommand();
    Vector v = new Vector();
    //If buttons CANCEL
    if (command.equals(DConst.BUT_OK)) {
      int ligne = validation();
      if( (_list.contains(_textField0.getText()))){
        JOptionPane.showMessageDialog(this,"Le groupe existe dejà");
        ligne=-1;
      }

      if (ligne == 0) {
        int nbCycle= _sectionMod.getDApplic().getDMediator().getCurrentDoc().getDM().getTTStructure().getSetOfCycles().size();
        _sectionMod.getType().addSection(_textField0.getText(),nbCycle,true);
        _sectionMod.init();
        //_sectionMod.getType().add
        //v.add(_textField0.getText());
        dispose();
      }
      else if (ligne != -1)
        JOptionPane.showMessageDialog(this,"Nom de groupe éronné ");



    } //end if



  } // end actionPerformed

  /**
   *
   */
  private void jbInitAddGroup(){
    Dimension dim = new Dimension(250,100);
    setSize(dim);
    setResizable(false);
    this.getContentPane().setLayout(new BorderLayout());
    JPanel jPanel = new JPanel();
    _textField0 = new JTextField("01");
    //jPanel.setLayout(new GridLayout(6,2));
    _textField0.setPreferredSize(new Dimension(30,20));
    jPanel.add(new JLabel("Numéro de groupe ="));
    jPanel.add(_textField0);
    //jPanel.setPreferredSize();
    //this.setLocationRelativeTo(_parent);
    setLocation(200,200);
    this.setResizable(true);

    _buttonsPanel = DXTools.buttonsPanel(this, _buttonsNames);

    this.getContentPane().add(jPanel, BorderLayout.CENTER);
    this.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /**
   *
   */
  private void jbInitRemGroup(){
    JOptionPane pane = new JOptionPane();
    Object[] options = { DConst.BUT_OK, DConst.BUT_CANCEL };
    int rest= pane.showOptionDialog(this,"Etes-vous sur de vouloir supprimer le groupe "+ _list.get(0).toString()+" ?", "Confirmation",
                          JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                          null, options, options[0]);
    if(rest==0){//BUT_OK
      _sectionMod.getType().removeSection(_list.get(0).toString());
      _sectionMod.init();
    }
    //System.out.println("Resp: "+ rest);
  }

  /**
   *
   * @return
   */
  private int validation() {
    if(!testText(_textField0.getText(), 0, 99))
      return 1;
    return 0;
  }

  /**
   *
   * @param str
   * @param inf
   * @param sup
   * @return
   */
  private boolean testText(String str, int inf , int sup) {
    boolean res = false;
    int i;
    if (str == null)
      return res;
    if(str.length()==0)
      return res;
    try {
      i = Integer.parseInt(str);
    } catch (Exception e) {
      return res;
    }
    if ( i >= inf && i <= sup)
      res = true;

    return res;
  }

} /* end class SelectGroupDlg */
