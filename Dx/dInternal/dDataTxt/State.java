package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

public class State {
  int _modifCount;
  int _conflitIns;

  public State() {
    _modifCount = 0;
  }

  public void incrModif() {
    _modifCount++;
  }

  public int getModif(){
    return _modifCount;
  }
}