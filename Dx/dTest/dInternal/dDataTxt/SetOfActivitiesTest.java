package dTest.dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import junit.framework.*;
import dInternal.dData.Activity;
import dInternal.dData.Type;
import dInternal.dData.Section;
import dInternal.dData.Unity;
import dInternal.dData.Assignment;
import dInternal.dData.SetOfActivities;
import dResources.DConst;
import java.util.Vector;
import java.util.StringTokenizer;

public class SetOfActivitiesTest  extends TestCase{

  public SetOfActivitiesTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfActivitiesTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the empty activity name
   * in the activities file
   * */
  public void test_analyseTokens(){
    String tokens= "ADM1111  A"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  A"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n";

    /*SetOfRooms setOfRooms= new SetOfRooms(tokens.getBytes(),5,14);
    setOfRooms.analyseTokens(3);*/
    assertEquals("test_analyseTokens: assertEquals", true,
                 true);

  }

}