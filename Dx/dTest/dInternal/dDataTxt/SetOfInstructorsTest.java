package dTest.dInternal.dDataTxt;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

import junit.framework.*;

import dInternal.dDataTxt.SetOfInstructors;
import dConstants.DConst;



public class SetOfInstructorsTest  extends TestCase{

  public SetOfInstructorsTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(SetOfInstructorsTest.class);
  } // end suite

  /**
   * test_analyseTokens, test that analyse the first line (the number of instructors)
   * of instructors file
   * */
  public void test_analyseTokens(){
    String tokens= "    1k"+"\r\n"+
                   "ADM111"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n";
    SetOfInstructors instructorsList= new SetOfInstructors(tokens.getBytes(),5,14);
    instructorsList.analyseTokens(0);
    assertEquals("test_analyseTokens: assertEquals", DConst.INST_TEXT1,
                 instructorsList.getError().substring(0,DConst.INST_TEXT1.length()));

  }

  /**
   * test1_analyseTokens, test that analyse the empty instructor name
   * in the instructors file
   * */
  public void test1_analyseTokens(){
    String tokens= "    2"+"\r\n"+
                   ""+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "POLM"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n";
    SetOfInstructors instructorsList= new SetOfInstructors(tokens.getBytes(),5,14);
    instructorsList.analyseTokens(0);
    assertEquals("test1_analyseTokens: assertEquals", DConst.INST_TEXT3,
                 instructorsList.getError().substring(0,DConst.INST_TEXT3.length()));

  }

  /**
   * test2_analyseTokens, test that analyse the wrong number of instructors
   * in the instructors file
   * */
  public void test2_analyseTokens(){
    String tokens= "    3"+"\r\n"+
                   "JAC"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "POLM"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n";
    SetOfInstructors instructorsList= new SetOfInstructors(tokens.getBytes(),5,14);
    instructorsList.analyseTokens(0);
    assertEquals("test2_analyseTokens: assertEquals", DConst.INST_TEXT1,
                 instructorsList.getError().substring(0,DConst.INST_TEXT1.length()));
  }

  /**
   * test3_analyseTokens, test the number of instructor availabilities
   * periods per day in the instructors file
   * */
  public void test3_analyseTokens(){
    String tokens= "    2"+"\r\n"+
                   ""+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "POLM"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1 6"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n";
    SetOfInstructors instructorsList= new SetOfInstructors(tokens.getBytes(),5,14);
    instructorsList.analyseTokens(0);
    assertEquals("test3_analyseTokens: assertEquals", DConst.INST_TEXT3,
                 instructorsList.getError().substring(0,DConst.INST_TEXT3.length()));
  }

  /**
   * test4_analyseTokens, test that analyse the wrong description of instructors
   * availability in the instructors file
   * */
  public void test4_analyseTokens(){
    String tokens= "    2"+"\r\n"+
                   "JAC"+"\r\n"+
                   "1 2 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "POLM"+"\r\n"+
                   "1 1 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
                   "1 5 1 5 1 5 1 5 1 5 1 5 1 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 1 1 1"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n"+
                   "1 5 5 5 5 5 5 5 5 5 5 5 5 5"+"\r\n";
    SetOfInstructors instructorsList= new SetOfInstructors(tokens.getBytes(),5,14);
    instructorsList.analyseTokens(0);
    assertEquals("test4_analyseTokens: assertEquals", DConst.INST_TEXT4,
                 instructorsList.getError().substring(0,DConst.INST_TEXT4.length()));
  }

}