package dInternal.dDataTxt;



/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr
 * @version 1.0
 */

import java.util.*;
import java.awt.Component;

public interface SetOfStudentsListener extends EventListener{
  void changeInSetOfStudents(SetOfStudentsEvent e, Component c);
}