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


import dInternal.dData.SetOfRooms;
import dResources.DConst;


public class SetOfRoomsTest  extends TestCase{

  public SetOfRoomsTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfRoomsTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the empty room name
   * in the rooms file
   * */
  public void test_analyseTokens(){
    String tokens= ";32;211;08,11,14,57;laboratoire de chimie;xx;"+"\r\n"+
                   "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfRooms setOfRooms= new SetOfRooms(tokens.getBytes(),5,14);
    setOfRooms.analyseTokens(3);
    assertEquals("test_analyseTokens: assertEquals", DConst.ROOM_TEXT3,
                 setOfRooms.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }

  /**
   * test_analyseTokens, test that analyse the capacity of the room
   * in the rooms file
   * */
  public void test1_analyseTokens(){
    String tokens= "D13012;32x;211;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                   "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                   "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                   "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                   "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                   "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

    SetOfRooms setOfRooms= new SetOfRooms(tokens.getBytes(),5,14);
    setOfRooms.analyseTokens(3);
    assertEquals("test1_analyseTokens: assertEquals", DConst.ROOM_TEXT2,
                 setOfRooms.getError().substring(0,DConst.ROOM_TEXT2.length()));

  }

  /**
  * test2_analyseTokens, test that analyse the function of the room
  * in the rooms file
  * */
 public void test2_analyseTokens(){
   String tokens= "D13012;32;211x;08,11,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfRooms setOfRooms= new SetOfRooms(tokens.getBytes(),5,14);
   setOfRooms.analyseTokens(3);
   assertEquals("test2_analyseTokens: assertEquals", DConst.ROOM_TEXT3,
                setOfRooms.getError().substring(0,DConst.ROOM_TEXT3.length()));

  }

  /**
  * test3_analyseTokens, test that analyse the caracteristic of the room
  * in the rooms file
  * */
 public void test3_analyseTokens(){
   String tokens= "D13012;32;211;08,11x,14,57;laboratoire de chimie;"+"\r\n"+
                  "D13013;40;211;08,11,57;laboratoire de chimie;"+"\r\n"+
                  "D13014;20;211;08,44,57;laboratoire de chimie;"+"\r\n"+
                  "D20051;30;210;8,11,27,44;laboratoire informatique;"+"\r\n"+
                  "D22048;15;211;08,53;laboratoire de physique;"+"\r\n"+
                  "D32028;48;211;08,11,55;laboratoire de microbiologie;"+"\r\n";

   SetOfRooms setOfRooms= new SetOfRooms(tokens.getBytes(),5,14);
   setOfRooms.analyseTokens(3);
   assertEquals("test3_analyseTokens: assertEquals", DConst.ROOM_TEXT4,
                setOfRooms.getError().substring(0,DConst.ROOM_TEXT4.length()));

  }


}