package dTest.dInternal.dData.dInstructors;

import dInternal.dData.DLoadData;
import dInternal.dData.dInstructors.DxReadInstructors;
import dInternal.dData.dInstructors.DxSetOfInstructors;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxReadInstructorsTest extends TestCase {
	public DxReadInstructorsTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxReadInstructorsTest.class);
	} // end suite

	/**
	   * test_getSetOfInstructors, verifies v1.5 analyser
	   * Should fail because of invalid instructor count format 
	   * */
	public void test_getSetOfInstructors() {
		String tokens = "    1k" + "\r\n" + "ADM111" + "\r\n"
				+ "1 1 5 1 5 1 5 1 5 1 5 1 5 1" + "\r\n"
				+ "1 5 1 5 1 5 1 5 1 5 1 5 1 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 1 1 1" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n"
				+ "1 5 5 5 5 5 5 5 5 5 5 5 5 5" + "\r\n";
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();		
		assertEquals("test_getSetOfInstructors: assertEquals",dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14),null);
	}
	
	/**
	   * test1_getSetOfInstructors, verifies v1.5 analyser
	   * invalid instructor name 
	   * */
	public void test1_getSetOfInstructors() {
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
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();		
		assertEquals("test1_getSetOfInstructors: assertEquals",dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14),null);
	}
	
	/**
	   * test2_getSetOfInstructors, verifies v1.5 analyser
	   * Suggested instructor count and actual number of instrucator in the file does not match 
	   * */
	public void test2_getSetOfInstructors() {
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
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();		
		assertEquals("test2_getSetOfInstructors: assertEquals",dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14),null);
	}
	
	/**
	   * test2_getSetOfInstructors, verifies v1.5 analyser
	   *  Should fail because of invalid number of periods in second instructor, 3rd day
	   * */
	public void test3_getSetOfInstructors() {
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
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();		
		assertEquals("test3_getSetOfInstructors: assertEquals",dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14),null);
	}
	
	/**
	   * test2_getSetOfInstructors, verifies v1.5 analyser
	   *  Make sure analyser verify nature of period availability.
	   *  3 is an invalid period availability in v1.5
	   * */
	public void test4_getSetOfInstructors() {
		String tokens= "    2"+"\r\n"+
        "JAC"+"\r\n"+
        "1 3 5 1 5 1 5 1 5 1 5 1 5 1"+"\r\n"+
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
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();		
		assertEquals("test4_getSetOfInstructors: assertEquals",dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14),null);
	}
	
	/**
	   * test2_getSetOfInstructors, verifies v1.5 builder
	   *  Make sure analyser verify nature of period availability.
	   *  3 is an invalid period availability in v1.5
	   * */
	public void test5_getSetOfInstructors() {
		String tokens= "    2"+"\r\n"+
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
		DLoadData ld = new DLoadData();
		DxReadInstructors dxriTest=new DxReadInstructors();
		DxSetOfInstructors dxsoiTemp=dxriTest.getSetOfInstructors(ld.buildDataExchange(tokens.getBytes()), 5, 14);
		assertNotSame("test5_getSetOfInstructors: assertNotSame",dxsoiTemp,null);
		assertEquals("test5_getSetOfInstructors: assertEquals",dxsoiTemp.getInstructorName(0),"JAC");
		assertEquals("test5_getSetOfInstructors: assertEquals",dxsoiTemp.getInstructorName(1),"POLM");
	}
}
