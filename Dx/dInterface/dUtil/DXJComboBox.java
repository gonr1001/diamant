package dInterface.dUtil;

import javax.swing.JComboBox;
import java.util.Vector;
import java.awt.event.ActionListener;

public class DXJComboBox extends JComboBox{
  ActionListener [] _actionList;
  boolean _stateDisList=false;
  /**
   * Constructor
   */
  public DXJComboBox(){
    super();
  }
  /**
   * Constructor
   */
  public DXJComboBox(Vector items){
    super(items);
  }
  /**
   * Constructor
   */
  public DXJComboBox(Object[] items){
    super(items);
  }

  /**
   *
   */
  public void disableActionListeners(){
    _stateDisList= true;
    _actionList = this.getActionListeners();
    for (int i = 0; i <_actionList.length; i++) {
      this.removeActionListener(_actionList[i]);
    }
  }

  /**
   *
   */
  public void enableActionListeners(){
    if(_stateDisList){
      _stateDisList= false;
      for (int i = 0; i <_actionList.length; i++) {
        this.addActionListener(_actionList[i]);
      }
    }
  }


}