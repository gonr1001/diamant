package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import dInternal.dTimeTable.Period;

public interface Condition {

  /**
   *
   * @param per
   * @param eventKey
   * @param int operation -1= remove event, 0= do nothing, 1= add event
   * @return
   */
  /*public int executeTest(Period per, String eventKey, int operation);*/

  /**
   *
   * @param per
   * @param eventKey
   * @param int operation -1= remove event, 0= do nothing, 1= add event
   * @return
   */
  public int executeTest(int[] perKey, Period per, String eventKey, int operation);

}