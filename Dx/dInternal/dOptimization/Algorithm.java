package dInternal.dAlgorithms;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dTimeTable.*;
import dInternal.dConditionsTest.*;
import java.util.Vector;

public interface Algorithm {


  /**
   *
   */
  public void build(DModel dm, Vector vectorOfEvents);

}