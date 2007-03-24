/**
*
* Title: SetOfSitesTest $Revision $  $Date: 2007-03-24 13:47:53 $
* Description: 	SetOfSitesTestt is a class used to test the class 
* 				SetOfSitesTest 
*
*
* Copyright (c) 2001 by rgr.
* All rights reserved.
*
*
* This software is the confidential and proprietary information
* of rgr. ("Confidential Information").  You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with rgr.
*
* @version $ $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dTest.dInternal.dData.dRooms;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dConstants.DConst;
import dInternal.dData.DLoadData;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfRooms;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import eLib.exit.exception.DxException;


public class DxSiteReaderTest  extends TestCase{

	public DxSiteReaderTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DxSiteReaderTest.class);
	} // end suite


 
 public void test2_readSetOfSites(){
	 String tokens = "Diamant1.6"+"\r\n"+
     "C1-2018;10;212;11;SHE;CAT 1;Matériaux composites;" + 
		"1 1 1 1 1 1 1 x 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," + // x bad availability
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
		"C1-3007;56;620;11,14;SHE;CAT 1;Avec console multi-média;" +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;";
	DLoadData ld = new DLoadData();
	DxSiteReader dxSites = new DxReadSitedotDia(ld
			.buildDataExchange(tokens.getBytes()), 5, 12);
	try {
		@SuppressWarnings("unused") 
		DxSetOfSites dxsor = dxSites.readSetOfSites();
		assertFalse("test2_readSetOfSites: Should have failed before",
				true);
	} catch (DxException e) {
		assertEquals("test2_readSetOfSites: assertEquals",DConst.INVALID_AVAILABILITY_AT+"2", e.getMessage());		
	}
}
 
 public void test1_readSetOfSites(){
	 String tokens = "Diamant1.6"+"\r\n"+
	        "C1-2018;10;212;11;SHE;CAT 1;Matériaux composites;" + 
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
	 		"C1-3007;620;11,14;SHE;CAT 1;Avec console multi-média;" + // no capacity !
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
	 		"C1-4030;25;211;11;SHE;CAT 1;Équipement pour photoélasticité;" +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;";
		DLoadData ld = new DLoadData();
		DxSiteReader dxSites = new DxReadSitedotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 12);
		try {
			@SuppressWarnings("unused") 
			DxSetOfSites dxsor = dxSites.readSetOfSites();
			assertFalse("test1_readSetOfSites: Should have failed before",
					true);
     } catch (DxException e) {
		assertEquals("test1_readSetOfSites: assertEquals",DConst.ROOM_TEXT7+"3", e.getMessage());
			
	}
 }
 
 public void test3_readSetOfSites(){
	 String tokens = "Diamant1.6"+"\r\n"+
     "C1-2018;10;212;11;SHE;CAT 1;Matériaux composites;" + 
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
		"C1-3007;56;620;11,14;CAT 1;Avec console multi-média;" + // No site
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
		"C1-4030;25;211;11;SHE;CAT 1;Équipement pour photoélasticité;" +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
		"D7-3016;125;620;11;SHE;CAT 1;Avec console multi-média;" +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;";
	DLoadData ld = new DLoadData();
	DxSiteReader dxSites = new DxReadSitedotDia(ld
			.buildDataExchange(tokens.getBytes()), 5, 12);
	try {
		@SuppressWarnings("unused") 
		DxSetOfSites dxsor = dxSites.readSetOfSites();
		assertFalse("test3_readSetOfSites: Should have failed before",
				true);
} catch (DxException e) {
	assertEquals("test3_readSetOfSites: assertEquals",DConst.ROOM_TEXT7+"3", e.getMessage());
		
}
 }
 
 public void test4_readSetOfSites(){
	 String tokens = "Diamant1.6"+"\r\n"+
     "C1-2018;10;212;11;SHE;CAT 1;Matériaux composites;" + 
		"1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1," + // bad availability (comma missed)
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
		"C1-3007;56;620;11,14;SHE;CAT 1;Avec console multi-média;" + 
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
		"1 1 1 1 1 1 1 1 1 1 1 1;";
	DLoadData ld = new DLoadData();
	DxSiteReader dxSites = new DxReadSitedotDia(ld
			.buildDataExchange(tokens.getBytes()), 5, 12);
	try {
		@SuppressWarnings("unused") 
		DxSetOfSites dxsor = dxSites.readSetOfSites();
		assertFalse("test4_readSetOfSites: Should have failed before",
				true);
} catch (DxException e) {
	assertEquals("test4_readSetOfSites: assertEquals",DConst.INVALID_AVAILABILITY_AT+"2", e.getMessage());
		
}
 }

 public void test1_readSetOfSitesMultiCat(){
	 String tokens = "Diamant1.6"+"\r\n"+
	        "C1-2018;10;212;11;SHE;CAT1;Matériaux composites;" + 
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
	 		"C1-3007;12;620;11,14;SHE;CAT2;Avec console multi-média;" + 
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;"+"\r\n"+
	 		"C1-4030;25;211;11;SHE;CAT3;Équipement pour photoélasticité;" +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1,1 1 1 1 1 1 1 1 1 1 1 1," +
	 		"1 1 1 1 1 1 1 1 1 1 1 1;";
		DLoadData ld = new DLoadData();
		DxSiteReader dxSites = new DxReadSitedotDia(ld
				.buildDataExchange(tokens.getBytes()), 5, 12);
		
		/* Not use a key in test!!!!
		 * 
		 */
		
		try {
			DxSetOfSites dxSos = dxSites.readSetOfSites();
			assertEquals("t_readSetOfSitesMultiCat: siteCount 1",1, dxSos.getSiteCount());
			assertEquals("t_readSetOfSitesMultiCat: catCount 3",3, dxSos.getCatCount(DConst.ROOM_DEFAULT_SITE));
			DxSetOfRooms dxSor= dxSos.getAllDxRooms();
			assertEquals("t_readSetOfSitesMultiCat: SetofRooms Size 3",3, dxSor.size());
			assertEquals("test2_readSetOfSitesMultiCat: room Cap 10",10, dxSor.getRoomCapacity("C1-2018"));
			assertEquals("test2_readSetOfSitesMultiCat: room Cap 12",12, dxSor.getRoomCapacity("C1-3007"));
			assertEquals("test2_readSetOfSitesMultiCat: room Cap 25",25, dxSor.getRoomCapacity("C1-4030"));
			assertEquals("test2_readSetOfSitesMultiCat: room Cap 0",0, dxSor.getRoomCapacity("-----"));
     } catch (DxException e) {
		//never here			
	}
 }

}