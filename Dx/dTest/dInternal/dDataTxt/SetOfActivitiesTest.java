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
import java.io.File;
import dInternal.dData.Activity;
import dInternal.dData.Type;
import com.iLib.gIO.FilterFile;

import dInternal.dData.SetOfActivities;
import dInternal.dData.Resource;
import dInternal.dData.Section;
import dInternal.dData.Unity;
import dInternal.dData.Assignment;
import dResources.DConst;


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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
    assertEquals("test_analyseTokens: assertEquals", DConst.ACTI_TEXT1,
                 setOfActivities.getError().substring(0,DConst.ACTI_TEXT1.length()));

  }

  /**
  * test_analyseTokens, test that analyse the line where error is detected
  * in the activities file
  * */
 public void test0_analyseTokens(){

   String fileName =System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"cours1.sig";

   SetOfActivities setOfActivities= new SetOfActivities(preLoad(fileName),false);
   setOfActivities.analyseTokens(1);
   assertEquals("test0_analyseTokens: assertEquals", 30,setOfActivities.getLine());

  }

  /**
  * test_analyseTokens, test that analyse the number of line
  * in the activities file
  * */
 public void test00_analyseTokens(){

   String fileName = System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"cours2.sig";
   SetOfActivities setOfActivities= new SetOfActivities(preLoad(fileName),false);
   setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

  SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
  setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

    SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
    setOfActivities.analyseTokens(1);
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

   SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
   setOfActivities.analyseTokens(1);
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

      SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
      setOfActivities.analyseTokens(1);
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

      SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
      setOfActivities.analyseTokens(1);
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

     SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
     setOfActivities.analyseTokens(1);
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

     SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
     setOfActivities.analyseTokens(1);
     if(setOfActivities.getError().length()==0) {

       setOfActivities.buildSetOfActivities(1);
       System.out.println(setOfActivities.toString());
     }
     assertEquals("test0_addActivity: assertEquals 0", 2,setOfActivities.size());
     Resource activityResc = setOfActivities.getResource("ADM111");
     assertEquals("test1_addActivity: assertEquals 1", 1,activityResc.getKey());
     assertEquals("test2_addActivity: assertEquals 2", 2,((Activity)activityResc.getAttach()).getSetOfTypes().size());
     Resource typeResc = ((Activity)activityResc.getAttach()).getSetOfTypes().getResource("1");
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

     SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
     setOfActivities.analyseTokens(1);
     if(setOfActivities.getError().length()==0)
       setOfActivities.buildSetOfActivities(1);
     assertEquals("test0_addActivitDiffRooms: assertEquals 0", 2, setOfActivities.size());
     Resource activityResc = setOfActivities.getResource("GEI441");
     assertEquals("test1_addActivitDiffRooms: assertEquals 1", 2, activityResc.getKey());
     assertEquals("test2_addActivitDiffRooms: assertEquals 2", 1,((Activity)activityResc.getAttach()).getSetOfTypes().size());
     Resource typeResc = ((Activity)activityResc.getAttach()).getSetOfTypes().getResource("1");
     assertEquals("test3_addActivitDiffRooms: assertEquals 3", 1,((Type)typeResc.getAttach()).getSetOfSections().size());
     Resource sectionResc= ((Type)typeResc.getAttach()).getSetOfSections().getResource(1);
     assertEquals("test4_addActivitDiffRooms: assertEquals 4", 2,((Section)sectionResc.getAttach()).getSetOfUnities().size());
     Resource unitResc1= ((Section)sectionResc.getAttach()).getSetOfUnities().getResource(1);
     assertEquals("test5_addActivitDiffRooms: assertEquals 5", 1,((Unity)unitResc1.getAttach()).getSetOfAssignments().size());
     Resource unitResc2= ((Section)sectionResc.getAttach()).getSetOfUnities().getResource(2);
     assertEquals("test6_addActivitDiffRooms: assertEquals 6", 1,((Unity)unitResc2.getAttach()).getSetOfAssignments().size());
     Resource assignRes1=  ((Unity)unitResc1.getAttach()).getSetOfAssignments().getResource(1);// Unity)unityResource.getAttach();
     assertEquals("test7_addActivitDiffRooms: assertEquals 7", "C1-387",(String)((Assignment)assignRes1.getAttach()).getRoomName());
     Resource assignRes2=  ((Unity)unitResc2.getAttach()).getSetOfAssignments().getResource(1);// Unity)unityResource.getAttach();
     assertEquals("test8_addActivitDiffRooms: assertEquals 8", "xxxxx",(String)((Assignment)assignRes2.getAttach()).getRoomName());
   }

   /**
    * test instructorEqualsBlocs,
    *
    *
    * */
   public void test_instructorEqualsBlocs(){
     String tokens= "ADM1111  01"+"\r\n"+
                    "1"+"\r\n"+
                    "1"+"\r\n"+
                    "rgr ; ys" + "\r\n"+
                    "2"+"\r\n"+
                    "2 1"+"\r\n"+
                    "1 12 1 1"+"\r\n"+
                    "1 1"+"\r\n"+
                    "C1-387 C1-387"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
                    "0 0"+"\r\n"+
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

     SetOfActivities setOfActivities= new SetOfActivities(tokens.getBytes(),false);
     setOfActivities.analyseTokens(1);
     assertEquals("test_instructorEqualsBlocs: assertEquals","", /* DConst.ACTI_TEXT5,*/
                  setOfActivities.getError());//.substring(0,DConst.ACTI_TEXT5.length()));
   }


  private byte[] preLoad(String str) {
    FilterFile filter = new FilterFile();
    filter.appendToCharKnown("‘ÀÈ-',; ()Í.‡");
    if (filter.validFile(str)) {
      return filter.getByteArray();
    } else return null;

  }



}