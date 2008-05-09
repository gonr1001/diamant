/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
package dTest.dInternal.dData.dActivities;



import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import dConstants.DConst;
import dInternal.DResource;
import dInternal.dData.DLoadData;
import dInternal.dData.dActivities.Activity;
import dInternal.dData.dActivities.Assignment;
import dInternal.dData.dActivities.Section;
import dInternal.dData.dActivities.SetOfActivities;
import dInternal.dData.dActivities.SetOfActivitiesSites;
import dInternal.dData.dActivities.Type;
import dInternal.dData.dActivities.Unity;
import eLib.exit.exception.DxException;
import eLib.exit.txt.FilterFile;


public class DSetOfActivitiesTest  extends TestCase{

  public DSetOfActivitiesTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DSetOfActivitiesTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the empty activity name
   * in the activities file
   * */
  public void test_analyseTokens(){
    String tokens= ""+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test_analyseTokens: assertEquals", DConst.ACTI_TEXT1,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT1.length()));

  }

  /**
  * test_analyseTokens, test that analyse the line where error is detected
  * in the activities file
  * */
 public void test0_analyseTokens(){

   String fileName ="." + File.separator+"dataTest"+File.separator+"cours1.sig";
   String tokens="";
try {
	tokens = new String (dataPreLoad(fileName));
} catch (DxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
   DLoadData ld = new DLoadData();
   setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
   assertEquals("test0_analyseTokens: assertEquals", 30,setOfActivities.getLine());

  }

  /**
  * test_analyseTokens, test that analyse the number of line
  * in the activities file
  * */
 public void test00_analyseTokens(){

   String fileName = "." + File.separator+"dataTest"+File.separator+"cours2.sig";
   String tokens = "";
try {
	tokens = new String (dataPreLoad(fileName));
} catch (DxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
   DLoadData ld = new DLoadData();
   setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
   assertEquals("test00_analyseTokens: assertEquals", 25,setOfActivities.getLine());

  }


  /**
   * test1_analyseTokens, test that analyse the activity visibility
   * in the activities file
   * */
  public void test1_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1x"+"\r\n"+
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
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test1_analyseTokens: assertEquals", DConst.ACTI_TEXT2,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT2.length()));

  }

  /**
   * test2_analyseTokens, test that analyse the number of activities
   * in the activities file
   * */
  public void test2_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1k"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test2_analyseTokens: assertEquals", DConst.ACTI_TEXT3,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT3.length()));
  }

  /**
   * test3_analyseTokens, test that the instructor name is empty
   * in the activities file,
   * the result is ACTI_TEXT5, because the next token is used as
   * instruction name then other error will be dtected.
   *
   * */
  public void test3_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   ""+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test3_analyseTokens: assertEquals", DConst.ACTI_TEXT5,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT5.length()));
  }


  /**
   * test4_analyseTokens, test that analyse the number of blocs
   * in the activities file
   * */
  public void test4_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
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
                   "ADM1112  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1v"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n";

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test4_analyseTokens: assertEquals 1", DConst.ACTI_TEXT5,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT5.length()));
    assertEquals("test4_analyseTokens: assertEquals 2", 23,setOfActivities.getLine());

  }

  /**
   * test5_analyseTokens, test that analyse the number of blocs
   * in the activities file
   * */
  public void test5_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "2"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test5_analyseTokens: assertEquals", DConst.ACTI_TEXT5,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT5.length()));
  }

  /**
   * test6_analyseTokens, test that analyse the number of blocs
   * in the activities file
   * */
  public void test6_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3 2"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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
    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test6_analyseTokens: assertEquals", DConst.ACTI_TEXT5,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT5.length()));
  }

  /**
 * test7_analyseTokens, test that analyse the number of blocs
 * in the activities file
 * */
public void test7_analyseTokens(){
  String tokens= "ADM1111  01"+"\r\n"+
                 "1"+"\r\n"+
                 "1"+"\r\n"+
                 "LUC LAJOIE"+"\r\n"+
                 "1"+"\r\n"+
                 "3"+"\r\n"+
                 "1 12 1"+"\r\n"+
                 "1"+"\r\n"+
                 "C1-387"+"\r\n"+
                 "0"+"\r\n"+
                 "0"+"\r\n"+
                 "0"+"\r\n"+
                 "ADM1112  01"+"\r\n"+
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

  SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
  DLoadData ld = new DLoadData();
  setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
  assertEquals("test7_analyseTokens: assertEquals", DConst.ACTI_TEXT5,
               setOfActivities.getError().substring(0,DConst.ACTI_TEXT5.length()));
  }

  /**
   * test8_analyseTokens, test that analyse the duration of blocs
   * in the activities file
   * */
  public void test8_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3k"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test8_analyseTokens: assertEquals", DConst.ACTI_TEXT7,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT7.length()));
  }

  /**
   * test9_analyseTokens, test that analyse days and periods format of blocs
   * in the activities file
   * */
  public void test9_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1k 12"+"\r\n"+
                   "1"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test9_analyseTokens: assertEquals", DConst.ACTI_TEXT8,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT8.length()));
  }

  /**
   * test10_analyseTokens, test that analyse the fixed rooms state
   * in the activities file
   * */
  public void test10_analyseTokens(){
    String tokens= "ADM1111  01"+"\r\n"+
                   "1"+"\r\n"+
                   "1"+"\r\n"+
                   "LUC LAJOIE"+"\r\n"+
                   "1"+"\r\n"+
                   "3"+"\r\n"+
                   "1 12"+"\r\n"+
                   "1v"+"\r\n"+
                   "C1-387"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "0"+"\r\n"+
                   "ADM1112  01"+"\r\n"+
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

    SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
    DLoadData ld = new DLoadData();
    setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
    assertEquals("test10_analyseTokens: assertEquals", DConst.ACTI_TEXT9,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT9.length()));
  }

  /**
  * test11_analyseTokens, test that analyse the wrong room name
  * in the activities file
  * */
 public void test11_analyseTokens(){
   String tokens= "ADM1111  01"+"\r\n"+
                  "1"+"\r\n"+
                  "1"+"\r\n"+
                  "LUC LAJOIE"+"\r\n"+
                  "1"+"\r\n"+
                  "3"+"\r\n"+
                  "1 12"+"\r\n"+
                  "1"+"\r\n"+
                  "C1 387"+"\r\n"+
                  "0"+"\r\n"+
                  "0"+"\r\n"+
                  "0"+"\r\n"+
                  "ADM1112  01"+"\r\n"+
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

   SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
   DLoadData ld = new DLoadData();
   setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
   assertEquals("test11_analyseTokens: assertEquals", DConst.ACTI_TEXT10,
                setOfActivities.getError().substring(0,DConst.ACTI_TEXT10.length()));
  }

  /**
     * test12_analyseTokens, test that analyse the type of rooms
     * in the activities file
     * */
    public void test12_analyseTokens(){
      String tokens= "ADM1111  01"+"\r\n"+
                     "1"+"\r\n"+
                     "1"+"\r\n"+
                     "LUC LAJOIE"+"\r\n"+
                     "1"+"\r\n"+
                     "3"+"\r\n"+
                     "1 12"+"\r\n"+
                     "1"+"\r\n"+
                     "C1-387"+"\r\n"+
                     "0v"+"\r\n"+
                     "0"+"\r\n"+
                     "0"+"\r\n"+
                     "ADM1112  01"+"\r\n"+
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

      SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
      DLoadData ld = new DLoadData();
      setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
      assertEquals("test12_analyseTokens: assertEquals", DConst.ACTI_TEXT11,
                   setOfActivities.getError().substring(0,DConst.ACTI_TEXT11.length()));
  }

  /**
     * test13_analyseTokens, test that analyse the type of rooms idem
     * in the activities file
     * */
    public void test13_analyseTokens(){
      String tokens= "ADM1111  01"+"\r\n"+
                     "1"+"\r\n"+
                     "1"+"\r\n"+
                     "LUC LAJOIE"+"\r\n"+
                     "1"+"\r\n"+
                     "3"+"\r\n"+
                     "1 12"+"\r\n"+
                     "1"+"\r\n"+
                     "C1-387"+"\r\n"+
                     "0"+"\r\n"+
                     "0G"+"\r\n"+
                     "0"+"\r\n"+
                     "ADM1112  01"+"\r\n"+
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

      SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
      DLoadData ld = new DLoadData();
      setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
      assertEquals("test13_analyseTokens: assertEquals", DConst.ACTI_TEXT11,
                   setOfActivities.getError().substring(0,DConst.ACTI_TEXT11.length()));
  }

  /**
    * test14_analyseTokens, test that analyse the format of pre-affected rooms
    * in the activities file
    * */
   public void test14_analyseTokens(){
     String tokens= "ADM1111  01"+"\r\n"+
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
                    "0W"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
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

     SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(false, 60);
     DLoadData ld = new DLoadData();
     setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     assertEquals("test14_analyseTokens: assertEquals", DConst.ACTI_TEXT12,
                  setOfActivities.getError().substring(0,DConst.ACTI_TEXT12.length()));
  }

  /**
    * test_addActivity, test that all elements of the activity are added
    * in the activities file
    * */
   public void test_addActivity(){
     String tokens= "ADM1111  01"+"\r\n"+
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
                    "ADM1111  02"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "R…AL CAOUETTE"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1 12"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "Yannick"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1 12"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "GEI4411  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "Ruben"+"\r\n"+
                    "2"+"\r\n"+
                    "3 2"+"\r\n"+
                    "1 12 2 2"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 C1-330"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n";

     SetOfActivitiesSites setOfActivitiesSites= new SetOfActivitiesSites(false, 60);
     DLoadData ld = new DLoadData();
     setOfActivitiesSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     if(setOfActivitiesSites.getError().length()==0) {
     	setOfActivitiesSites.buildSetOfResources(ld.buildDataExchange(tokens.getBytes()),1);
     }
     SetOfActivities setOfActivities= (SetOfActivities)setOfActivitiesSites.getResource("SHE").getAttach();
     assertEquals("test0_addActivity: assertEquals 0", 2,setOfActivities.size());
     DResource activityResc = setOfActivities.getResource("ADM111");
     assertEquals("test1_addActivity: assertEquals 1", 1,activityResc.getKey());
     assertEquals("test2_addActivity: assertEquals 2", 2,((Activity)activityResc.getAttach()).getSetOfTypes().size());
     DResource typeResc = ((Activity)activityResc.getAttach()).getSetOfTypes().getResource("1");
     assertEquals("test3_addActivity: assertEquals 3", 2,((Type)typeResc.getAttach()).getSetOfSections().size());
  }
  /**
    * test_addActivity, test that all elements of the activity are added
    * in the activities file
    * */
   public void test_addActivitDiffRooms(){
     String tokens= "ADM1111  01"+"\r\n"+
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
                    "ADM1111  02"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "R…AL CAOUETTE"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1 12"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "Yannick"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1 12"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "GEI4411  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "Ruben"+"\r\n"+
                    "2"+"\r\n"+
                    "3 2"+"\r\n"+
                    "1 12 2 2"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 xxxxx"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n";

     SetOfActivitiesSites setOfActivitiesSites= new SetOfActivitiesSites(false, 60);
     DLoadData ld = new DLoadData();
     setOfActivitiesSites.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     if(setOfActivitiesSites.getError().length()==0) {
     	setOfActivitiesSites.buildSetOfResources(ld.buildDataExchange(tokens.getBytes()),1);
     }
     SetOfActivities setOfActivities= (SetOfActivities)setOfActivitiesSites.getResource("SHE").getAttach();
     assertEquals("test0_addActivitDiffRooms: assertEquals 0", 2, setOfActivities.size());
     DResource activityResc = setOfActivities.getResource("GEI441");
     assertEquals("test1_addActivitDiffRooms: assertEquals 1", 2, activityResc.getKey());
     assertEquals("test2_addActivitDiffRooms: assertEquals 2", 1,((Activity)activityResc.getAttach()).getSetOfTypes().size());
     DResource typeResc = ((Activity)activityResc.getAttach()).getSetOfTypes().getResource("1");
     assertEquals("test3_addActivitDiffRooms: assertEquals 3", 1,((Type)typeResc.getAttach()).getSetOfSections().size());
     DResource sectionResc= ((Type)typeResc.getAttach()).getSetOfSections().getResource(1);
     assertEquals("test4_addActivitDiffRooms: assertEquals 4", 2,((Section)sectionResc.getAttach()).getSetOfUnities().size());
     DResource unitResc1= ((Section)sectionResc.getAttach()).getSetOfUnities().getResource(1);
     assertEquals("test5_addActivitDiffRooms: assertEquals 5", 1,((Unity)unitResc1.getAttach()).getSetOfAssignments().size());
     DResource unitResc2= ((Section)sectionResc.getAttach()).getSetOfUnities().getResource(2);
     assertEquals("test6_addActivitDiffRooms: assertEquals 6", 1,((Unity)unitResc2.getAttach()).getSetOfAssignments().size());
     DResource assignRes1=  ((Unity)unitResc1.getAttach()).getSetOfAssignments().getResource(1);// Unity)unityResource.getAttach();
     assertEquals("test7_addActivitDiffRooms: assertEquals 7", "C1-387",((Assignment)assignRes1.getAttach()).getRoomName());
     DResource assignRes2=  ((Unity)unitResc2.getAttach()).getSetOfAssignments().getResource(1);// Unity)unityResource.getAttach();
     assertEquals("test8_addActivitDiffRooms: assertEquals 8", "xxxxx",((Assignment)assignRes2.getAttach()).getRoomName());
   }

   /**
    * test instructorEqualsBlocs,
    *
    *
    * */
   public void test_nInstructorEqualsnBlocs(){
     String tokens= "ADM1111  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "rgr, ys; rgr, ys " + "\r\n"+
                    "2"+"\r\n"+
                    "2 1"+"\r\n"+
                    "1.3.1 1.1.1"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 C1-387"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0; 0 0"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "LUC LAJOIE"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1.3.1"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0 ; 0" + "\r\n" ;

     SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(true, 60);
     DLoadData ld = new DLoadData();
     setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     assertEquals("test_nInstructorEqualsnBlocs: assertEquals", "",
                  setOfActivities.getError());
   }
   /**
    * test instructorEqualsBlocs,
    *
    *
    * */
   public void test_nSetInstructorEqualsnBlocs(){
     String tokens= "ADM1111  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "rgr: ys; rgr: ys" + "\r\n"+
                    "2"+"\r\n"+
                    "2 1"+"\r\n"+
                    "1.3.1 1.1.1"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 C1-387"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0; 0 0"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "LUC LAJOIE"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1.3.1"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0 ; 0" + "\r\n" ;

     SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(true, 60);
     DLoadData ld = new DLoadData();
     setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     assertEquals("test_nSetInstructorEqualsnBlocs: assertEquals", "",
                  setOfActivities.getError());
   }
   public void test_nInstructorNoEqualsnBlocs(){
     String tokens= "ADM1111  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "rgr, ys " + "\r\n"+
                    "2"+"\r\n"+
                    "2 1"+"\r\n"+
                    "1.3.1 1.1.1"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 C1-387"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0; 0 0"+"\r\n"+
                    "ADM1112  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "LUC LAJOIE"+"\r\n"+
                    "1"+"\r\n"+
                    "3"+"\r\n"+
                    "1.3.1"+"\r\n"+
                    "1"+"\r\n"+
                    "C1-387"+"\r\n"+
                    "0"+"\r\n"+
                    "0"+"\r\n"+
                    "0 ; 0" + "\r\n" ;

     SetOfActivitiesSites setOfActivities= new SetOfActivitiesSites(true, 60 );
     DLoadData ld = new DLoadData();
     setOfActivities.analyseTokens(ld.buildDataExchange(tokens.getBytes()),1);
     assertEquals("test_nInstructorNoEqualsnBlocs: assertEquals", DConst.ACTI_TEXT13,
                  setOfActivities.getError().substring(0,DConst.ACTI_TEXT13.length()));
   }

  private byte[] dataPreLoad(String str) throws DxException {
    FilterFile filter = new FilterFile();
    filter.setCharKnown("");
    filter.appendToCharKnown("‘ÀÈ-',:; ()Í.‡");
    if (filter.validFile(str)) {
      return filter.getByteArray();
    }
    return null;

  }



}