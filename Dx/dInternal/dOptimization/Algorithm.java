package dInternal.dOptimization;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */





public interface Algorithm {


  /**
   *build the algorithm
   */
  public void build();

  /**
   *return events list to use by the algorithm
   * @return Vector list of events
   */
  //public Vector buildEventsVector();

}