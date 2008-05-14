package dTest.dInternal.dUtil;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DXToolsMethods;
import java.io.File;

public class DXToolsMethodsTest extends TestCase{

	private final String _pathForFiles = "." + File.separator + "dataTest"
	 + File.separator;

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DXToolsMethodsTest.class);
  } // end suite

  public void test_resizeAvailability(){
    int [][] initialAvail = new int[8][14];
    int [][] finalAvail;
    int [] line = {1, 1, 1, 1, 5, 1, 1, 1, 5, 5, 5, 1, 1, 1};
    for (int i = 0; i < initialAvail.length; i++){
      initialAvail[i] = line;
    }
    TTStructure tts = new TTStructure();
    tts.loadTTSFromFile(_pathForFiles + "DXToolsMethodsTest_resizeAvailability.xml");
    finalAvail = DXToolsMethods.resizeAvailability(initialAvail, tts);

    assertEquals("test_resizeAvailability finalAvail.length : assertEquals", 8, finalAvail.length);
    assertEquals("test_resizeAvailability finalAvail[0].length : assertEquals", 3, finalAvail[0].length);
    assertEquals("test_resizeAvailability finalAvail[0][0].length : assertEquals", 1, finalAvail[0][0]);
    assertEquals("test_resizeAvailability finalAvail[0][1].length : assertEquals", 5, finalAvail[0][1]);
    assertEquals("test_resizeAvailability finalAvail[0][2].length : assertEquals", 1, finalAvail[0][2]);// was 5
    assertEquals("test_resizeAvailability finalAvail[1][0].length : assertEquals", 1, finalAvail[1][0]);
    assertEquals("test_resizeAvailability finalAvail[1][1].length : assertEquals", 1, finalAvail[1][1]);// was 5
    assertEquals("test_resizeAvailability finalAvail[1][2].length : assertEquals", 5, finalAvail[1][2]);
    assertEquals("test_resizeAvailability finalAvail[4][0].length : assertEquals", 1, finalAvail[4][0]);
    assertEquals("test_resizeAvailability finalAvail[4][1].length : assertEquals", 1, finalAvail[4][1]); //was 5
    assertEquals("test_resizeAvailability finalAvail[4][2].length : assertEquals", 5, finalAvail[4][2]);

  }

} //end class