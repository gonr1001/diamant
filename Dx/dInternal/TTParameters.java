package dInternal;

import java.util.Vector;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */

public class TTParameters {
  private Vector _ttpListeners = new Vector();
  String _str;
  int _dim1;
  int _dim2;

  public TTParameters() {
     _str = "";
     _dim1 = 5;
     _dim2 = 10;
  }

  public void setStr(String str) {
    _str = str;
  }

  public String getStr() {
    return _str;
  }

  public int getDim1() {
    return _dim1;
  }

  public int getDim2() {
    return _dim2;
  }

  public void setValues(int [] a) {
    _dim1 = a[0];
    _dim2 = a[1];
    TTParametersEvent event = new TTParametersEvent(this);
    for (int i=0; i<_ttpListeners.size(); i++) {
      TTParametersListener l = (TTParametersListener) _ttpListeners.elementAt(i);
      l.chageInTTParameters(event);
    }
  }

  public synchronized void addTTParametersListener(TTParametersListener ttpl) {
    if (_ttpListeners.contains(ttpl)){
      return;
    }
    _ttpListeners.addElement(ttpl);
  }

  public synchronized void removeTTParametersListener(TTParametersListener ttpl) {
    _ttpListeners.removeElement(ttpl);
  }
}