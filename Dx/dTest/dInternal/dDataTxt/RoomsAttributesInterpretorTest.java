package dTest.dInternal.dDataTxt;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.dDataTxt.RoomsAttributesInterpretor;


public class RoomsAttributesInterpretorTest extends TestCase {
  //private StudentAttach _student;

  public RoomsAttributesInterpretorTest(String name) {
    super(name);
  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(RoomsAttributesInterpretorTest.class);
  } // end suite

  /**
   * test_loadSetOfCaracteristics, test that the caracteristics are
   * properly added in the RoomsAttributesInterpretor
   * */
  public void test_loadSetOfCaracteristics(){
    String stringOfCaracteristics = "08;info;"+"\r\n"+
                     "11;meca;"+"\r\n"+
                     "14;telecom;"+"\r\n"+
                     "57;electronique;"+"\r\n"+
                     "44;electrotech;"+"\r\n"+
                     "07;beton;"+"\r\n"+
                     "22;multimedia;"+"\r\n"+
                     "27;ordi;";
    //StudentAttach studentAttach = new StudentAttach();
    RoomsAttributesInterpretor attr = new RoomsAttributesInterpretor();
    attr.loadSetOfCaracteristics(stringOfCaracteristics.getBytes());
    assertEquals("test_loadSetOfCaracteristics: assertEquals", 07,
                 attr.getSetOfCaracteristics().getResourceAt(0).getKey());
    assertEquals("test_loadSetOfCaracteristics: assertEquals", "beton",
                 attr.getSetOfCaracteristics().getResourceAt(0).getID());

  }

  /**
   * test_loadSetOfFunction, test that the caracteristics are
   * properly added in the RoomsAttributesInterpretor
   * */
  public void test_loadSetOfFunction(){
    String stringOfFunctions = "211;laboratoire de chimie;"+"\r\n"+
                     "210;laboratoire informatique;"+"\r\n"+
                     "620;lab 1;"+"\r\n"+
                     "110;lab 2;"+"\r\n"+
                     "211;laboratoire de physique;"+"\r\n";
    //StudentAttach studentAttach = new StudentAttach();
    RoomsAttributesInterpretor attr = new RoomsAttributesInterpretor();
    attr.loadSetOfFunctions(stringOfFunctions.getBytes());
    assertEquals("test_loadSetOfFunction: assertEquals", 110,
                 attr.getSetOfFunctions().getResourceAt(0).getKey());
    assertEquals("test_loadSetOfFunction: assertEquals", "lab 2",
                 attr.getSetOfFunctions().getResourceAt(0).getID());

  }



}