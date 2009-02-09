package dInternal.dData.dRooms;

import java.io.File;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.DxLoadData;
import dInternal.dData.ByteArrayMsg;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxSetOfSitesTest extends TestCase {

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator;

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxSetOfSitesTest.class);
	} // end suite

	public void test_getSetOfSitesSingleSite() {
		try {
			DxLoadData ld = new DxLoadData();
			byte[] dataloaded = ld.filterBadChars(_pathForFiles + "locaux.txt");

			DataExchange de = ld.insertHeader(dataloaded);
			DxSiteReader dxsr = new DxReadSite1dot5(de);
			DxSetOfSites dxsosSingle = dxsr.readSetOfSites();

			assertEquals("test_1_getSetOfSitesSingleSite: asserEquals", 1,
					dxsosSingle.getSiteCount());
			assertEquals("test_2_getSetOfSitesSingleSite: asserEquals", 1,
					dxsosSingle.getCatCount(DConst.ROOM_DEFAULT_SITE));
			assertEquals("test_3_getSetOfSitesSingleSite: asserEquals", 44,
					dxsosSingle.getRoomCount(DConst.ROOM_DEFAULT_SITE,
							DConst.ROOM_STANDARD_CAT));
			assertEquals("test_4_getSetOfSitesSingleSite: assertEquals",
					DConst.ROOM_DEFAULT_SITE, dxsosSingle.getSitesNamesSorted()
							.elementAt(0));
			// assertEquals("test_5_getSetOfSitesSingleSite: asserEquals",
			// DConst.ROOM_STANDARD_CAT, dxsosSingle.getCatName(1, 1));
			// assertEquals("test_6_getSetOfSitesSingleSite: asserEquals",
			// "D13012", dxsosSingle.getRoomName(1, 1, 1));
			// assertEquals("test_7_getSetOfSitesSingleSite: asserEquals",
			// "D13000", dxsosSingle.getRoomName(1, 1, 44));
			//
			// assertEquals("test_8_getSetOfSitesSingleSite: asserEquals", 20,
			// dxsosSingle.getRoomCapacity(1, 1, 44));

			assertEquals("test_9_getSetOfSitesSingleSite: asserEquals", 61,
					dxsosSingle.getRoomCapacity(DConst.ROOM_DEFAULT_SITE,
							DConst.ROOM_STANDARD_CAT, "D72007"));

			assertNotNull("test_10_getSetOfSitesSingleSite: asserNotNull",
					dxsosSingle.getAllDxRooms().getRoom("D13012"));
			assertNotNull("test_11_getSetOfSitesSingleSite: asserNotNull",
					dxsosSingle.getAllDxRooms().getRoom("D40023"));
			assertNotNull("test_12_getSetOfSitesSingleSite: asserNotNull",
					dxsosSingle.getAllDxRooms().getRoom("D13000"));

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("DxSetOfSitesTest: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: DxSetOfSitesTest");
			e.printStackTrace();
		}
	}

	public void test_getSetOfSitesMultiSite() {
		DxLoadData ld = new DxLoadData();
		byte[] dataloaded = null;
		try {
			dataloaded = ld.filterBadChars(_pathForFiles
					+ "locauxINFIRComplet.txt");
			DataExchange de = ld.insertHeader(dataloaded);
			DxSiteReader dxsr = new DxReadSite1dot6(de);
			DxSetOfSites dxsosMulti = dxsr.readSetOfSites();
			de = new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(
					dataloaded));

			assertEquals("test_1_getSetOfSitesMultiSite: asserEquals", 3,
					dxsosMulti.getSiteCount());

			assertEquals("test_2_getSetOfSitesMultiSite: asserEquals", 2,
					dxsosMulti.getSitesSortedByKey()[0].getCatCount());
			assertEquals("test_2_1_getSetOfSitesMultiSite: asserEquals", 1,
					dxsosMulti.getSitesSortedByKey()[2].getCatCount());
			assertEquals("test_3_getSetOfSitesMultiSite: asserEquals", 2,
					dxsosMulti.getCatCount(DConst.ROOM_DEFAULT_SITE));
			assertEquals("test_4_getSetOfSitesMultiSite: asserEquals", 7,
					dxsosMulti.getSitesSortedByKey()[0].getCat("CAT1")
							.getRoomCount());
			assertEquals("test_5_getSetOfSitesMultiSite: asserEquals", -1,
					dxsosMulti.getRoomCount("COW", "CAT1"));
			assertEquals("test_6_getSetOfSitesMultiSite: assertEquals",
					DConst.ROOM_DEFAULT_SITE,
					dxsosMulti.getSitesSortedByKey()[0].getName());
			assertEquals("test_6_1_getSetOfSitesMultiSite: assertEquals",
					"LON", dxsosMulti.getSitesSortedByKey()[1].getName());
			assertEquals("test_7_getSetOfSitesMultiSite: asserEquals", "CAT1",
					dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
							.getCatsSortedByKey()[0].getName());
			assertEquals("test_7_1_getSetOfSitesMultiSite: asserEquals",
					"CAT2", dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
							.getCatsSortedByKey()[0].getName());
			assertEquals("test_8_getSetOfSitesMultiSite: asserEquals",
					"Z7-2001", dxsosMulti.getSitesSortedByKey()[0]
							.getSetOfCat().getCatsSortedByKey()[0]
							.getSetOfRooms().getRoomsSortedByKey()[0].getName());
			assertEquals("test_9_1_getSetOfSitesMultiSite: asserEquals", "101",
					dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
							.getCatsSortedByKey()[0].getSetOfRooms()
							.getRoomsSortedByKey()[0].getName());
			assertEquals("test_10_getSetOfSitesMultiSite: asserEquals", 80,
					dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
							.getCatsSortedByKey()[0].getSetOfRooms()
							.getRoomsSortedByKey()[0].getCapacity());
			assertEquals("test_11_getSetOfSitesMultiSite: asserEquals", 60,
					dxsosMulti.getSitesSortedByKey()[1].getSetOfCat()
							.getCatsSortedByKey()[1].getSetOfRooms()
							.getRoomsSortedByKey()[1].getCapacity());
			assertEquals("test_12_getSetOfSitesMultiSite: asserEquals", 40,
					dxsosMulti.getRoomCapacity("SHE", "CAT2", "FM-3207"));
			assertNotNull("test_13_getSetOfSitesSingleSite: asserNotNull",
					dxsosMulti.getAllDxRooms().getRoom("Z7-2001"));
			assertNotNull("test_14_getSetOfSitesSingleSite: asserNotNull",
					dxsosMulti.getAllDxRooms().getRoom("Z7-2003"));
			assertNotNull("test_15_getSetOfSitesSingleSite: asserNotNull",
					dxsosMulti.getAllDxRooms().getRoom("130-7"));
			assertNotNull("test_16_getSetOfSitesSingleSite: asserNotNull",
					dxsosMulti.getAllDxRooms().getRoom("130-6"));
			assertNotNull("test_17_getSetOfSitesSingleSite: asserNotNull",
					dxsosMulti.getAllDxRooms().getRoom("101"));
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_getSetOfSitesMultiSite: exception",
					"nullPointer", e.toString());
			System.out.println("Exception in: test_getSetOfSitesMultiSite");
			e.printStackTrace();
		}

	}

	public void test_getSetOfSitesMultiCat() {
		final String CLASSE = "CLASSE";
		final String LAB = "LAB";
		DxLoadData ld = new DxLoadData();
		int jours = 7;
		int per = 4;
		try {
			DxSiteReader dxSites = new DxReadSitedotDia(ld.insertHeader(ld
					.filterBadChars(_pathForFiles + "locauxFlsh.txt")), jours,
					per);

			DxSetOfSites dxsosFlsh = dxSites.readSetOfSites();
			assertEquals("t_readSetOfSitesMultiCat: siteCount", 1, dxsosFlsh
					.getSiteCount());
			assertEquals("t_readSetOfSitesMultiCat: defaultSite", 4, dxsosFlsh
					.getCatCount(DConst.ROOM_DEFAULT_SITE));

			assertEquals("t_readSetOfSitesMultiCat: defaultSite", 29, dxsosFlsh
					.getAllDxRooms().size());
			long sitekey = dxsosFlsh.getSiteKey(DConst.ROOM_DEFAULT_SITE);
			assertEquals("t_readSetOfSitesMultiCat: siteCount_defaultSite", 4,
					dxsosFlsh.getCatCount(sitekey));
			long catkey = dxsosFlsh.getCatKey(sitekey, CLASSE);
			assertEquals("t_readSetOfSitesMultiCat: CatName", CLASSE, dxsosFlsh
					.getCatName(sitekey, catkey));

			catkey = dxsosFlsh.getCatKey(sitekey, CLASSE);
			// assertEquals("t_readSetOfSitesMultiCat: Classe 1", 7, catkey);
			// assertEquals("t_readSetOfSitesMultiCat: Classe 1", 79, catkey);

			DxSetOfRooms dxSor = dxsosFlsh.getAllDxRooms();
			long roomkey = dxsosFlsh.getRoomKey(sitekey, catkey, "A3-004");
			assertEquals("t_readSetOfSitesMultiCat: roomName A3-004", "A3-004",
					dxSor.getRoomName(roomkey));

			catkey = dxsosFlsh.getCatKey(sitekey, LAB);

			roomkey = dxsosFlsh.getRoomKey(sitekey, catkey, "A6-2004");
			assertEquals("t_readSetOfSitesMultiCat: roomName A6-2004",
					"A6-2004", dxSor.getRoomName(roomkey));

			assertEquals("test_2test1_getSetOfSitesMultiCat: asserEquals", 4,
					dxsosFlsh.getSitesSortedByKey()[0].getCatCount());
			assertEquals("test_2_1_getSetOfSitesMultiCat: asserEquals", 4,
					dxsosFlsh.getSitesSortedByKey()[0].getCatCount());
			assertEquals("test_3_getSetOfSitesMultiSite: asserEquals", 4,
					dxsosFlsh.getCatCount(DConst.ROOM_DEFAULT_SITE));

		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_getSetOfSitesMultiCat: exception",
					"nullPointer", e.toString());
			System.out.println("Exception in: test_getSetOfSitesMultiCats");
			e.printStackTrace();
		}

	}

}
