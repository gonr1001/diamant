package dTest.dInternal.dData.dRooms;

import java.io.File;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.DLoadData;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxReadSite1dot6;
import dInternal.dData.dRooms.DxReadSitedotDia;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import eLib.exit.exception.DxException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxSetOfSitesTest extends TestCase {
	DxSetOfSites _dxsosSingle;

	DxSetOfSites _dxsosMulti;

	DxSetOfSites _dxsosFlsh;

	public DxSetOfSitesTest(String name) {
		super(name);

		String path = "." + File.separator + "dataTest" + File.separator
				+ "locaux.txt";
		DLoadData ld = new DLoadData();
		byte[] dataloaded = null;
		try {
			dataloaded = ld.filterBadChars(path);
		} catch (DxException e1) {
			// normally no exception
			e1.printStackTrace();
		}

		DataExchange de = ld.buildDataExchange(dataloaded);
		DxSiteReader dxsr = new DxReadSite1dot5(de);

		try {
			_dxsosSingle = dxsr.readSetOfSites();
		} catch (DxException e) {
			// normally no exception
			e.printStackTrace();
		}

		path = "." + File.separator + "dataTest" + File.separator
				+ "locauxINFIRComplet.txt";
		try {
			dataloaded = ld.filterBadChars(path);
		} catch (DxException e1) {
			// normally no exception
			e1.printStackTrace();
		}
		de = new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(dataloaded));
		dxsr = new DxReadSite1dot6(de);

		try {
			_dxsosMulti = dxsr.readSetOfSites();
		} catch (DxException e) {
			// normally no exception
			e.printStackTrace();
		}

		path = "." + File.separator + "dataTest" + File.separator
				+ "locauxFlsh.txt";
		try {
			dataloaded = ld.filterBadChars(path);
		} catch (DxException e1) {
			// normally no exception
			e1.printStackTrace();
		}
		de = new ByteArrayMsg(DConst.FILE_VER_NAME1_6, new String(dataloaded));
		dxsr = new DxReadSite1dot6(de);

		try {
			_dxsosFlsh = dxsr.readSetOfSites();
		} catch (DxException e) {
			// normally no exception
			e.printStackTrace();
		}
		/*
		 * path = "." + File.separator + "dataTest" + File.separator +
		 * "loadData7j.dia"; dataloaded = preLoad(path); st = new
		 * StringTokenizer(new String(dataloaded), DConst.SAVE_SEPARATOR);
		 * st.nextToken(); // Skips time table definition st.nextToken(); //
		 * Skips instructors de = new ByteArrayMsg(DConst.FILE_VER_NAME1_5,
		 * st.nextToken().trim()); ////7 days and 12 periods in this file, would
		 * normaly begiven by the time table dxsr = new DxReadSitedotDia(de, 7,
		 * 12);
		 * 
		 * _dxsosDia = dxsr.getSetOfSite();
		 */
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxSetOfSitesTest.class);
	} // end suite

	public void test_getSetOfSitesSingleSite() {
		assertEquals("test_1_getSetOfSitesSingleSite: asserEquals", 1,
				_dxsosSingle.getSiteCount());
		assertEquals("test_2_getSetOfSitesSingleSite: asserEquals", 1,
				_dxsosSingle.getCatCount(1));
		assertEquals("test_2_1_getSetOfSitesSingleSite: asserEquals", 1,
				_dxsosSingle.getCatCount(DConst.ROOM_DEFAULT_SITE));
		assertEquals("test_3_getSetOfSitesSingleSite: asserEquals", 44,
				_dxsosSingle.getRoomCount(1, 1));
		assertEquals("test_3_1_getSetOfSitesSingleSite: asserEquals", 44,
				_dxsosSingle.getRoomCount(DConst.ROOM_DEFAULT_SITE,
						DConst.ROOM_STANDARD_CAT));

		assertEquals("test_4_getSetOfSitesSingleSite: assertEquals",
				DConst.ROOM_DEFAULT_SITE, _dxsosSingle.getSiteName(1));
		assertEquals("test_5_getSetOfSitesSingleSite: asserEquals",
				DConst.ROOM_STANDARD_CAT, _dxsosSingle.getCatName(1, 1));
		assertEquals("test_6_getSetOfSitesSingleSite: asserEquals", "D13012",
				_dxsosSingle.getRoomName(1, 1, 1));
		assertEquals("test_7_getSetOfSitesSingleSite: asserEquals", "D13000",
				_dxsosSingle.getRoomName(1, 1, 44));

		assertEquals("test_8_getSetOfSitesSingleSite: asserEquals", 20,
				_dxsosSingle.getRoomCapacity(1, 1, 44));

		assertEquals("test_9_getSetOfSitesSingleSite: asserEquals", 61,
				_dxsosSingle.getRoomCapacity(DConst.ROOM_DEFAULT_SITE,
						DConst.ROOM_STANDARD_CAT, "D72007"));

		assertNotNull("test_10_getSetOfSitesSingleSite: asserNotNull",
				_dxsosSingle.getAllRooms().getRoom("D13012"));
		assertNotNull("test_11_getSetOfSitesSingleSite: asserNotNull",
				_dxsosSingle.getAllRooms().getRoom("D40023"));
		assertNotNull("test_12_getSetOfSitesSingleSite: asserNotNull",
				_dxsosSingle.getAllRooms().getRoom("D13000"));

	}

	public void test_getSetOfSitesMultiSite() {
		assertEquals("test_1_getSetOfSitesMultiSite: asserEquals", 3,
				_dxsosMulti.getSiteCount());

		assertEquals("test_2_getSetOfSitesMultiSite: asserEquals", 2,
				_dxsosMulti.getSitesSortedByKey()[0].getCatCount());
		assertEquals("test_2_1_getSetOfSitesMultiSite: asserEquals", 1,
				_dxsosMulti.getSitesSortedByKey()[2].getCatCount());
		assertEquals("test_3_getSetOfSitesMultiSite: asserEquals", 2,
				_dxsosMulti.getCatCount(DConst.ROOM_DEFAULT_SITE));
		assertEquals("test_4_getSetOfSitesMultiSite: asserEquals", 7,
				_dxsosMulti.getSitesSortedByKey()[0].getCat("CAT1")
						.getRoomCount());
		assertEquals("test_5_getSetOfSitesMultiSite: asserEquals", -1,
				_dxsosMulti.getRoomCount("COW", "CAT1"));
		assertEquals("test_6_getSetOfSitesMultiSite: assertEquals",
				DConst.ROOM_DEFAULT_SITE, _dxsosMulti.getSitesSortedByKey()[0]
						.getName());
		assertEquals("test_6_1_getSetOfSitesMultiSite: assertEquals", "LON",
				_dxsosMulti.getSitesSortedByKey()[1].getName());
		assertEquals("test_7_getSetOfSitesMultiSite: asserEquals", "CAT1",
				_dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
						.getCatsSortedByKey()[0].getName());
		assertEquals("test_7_1_getSetOfSitesMultiSite: asserEquals", "CAT2",
				_dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
						.getCatsSortedByKey()[0].getName());
		assertEquals("test_8_getSetOfSitesMultiSite: asserEquals", "Z7-2001",
				_dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
						.getCatsSortedByKey()[0].getSetOfRooms()
						.getRoomsSortedByKey()[0].getName());
		assertEquals("test_9_1_getSetOfSitesMultiSite: asserEquals", "101",
				_dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
						.getCatsSortedByKey()[0].getSetOfRooms()
						.getRoomsSortedByKey()[0].getName());
		assertEquals("test_10_getSetOfSitesMultiSite: asserEquals", 80,
				_dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
						.getCatsSortedByKey()[0].getSetOfRooms()
						.getRoomsSortedByKey()[0].getCapacity());
		assertEquals("test_11_getSetOfSitesMultiSite: asserEquals", 60,
				_dxsosMulti.getSitesSortedByKey()[1].getSetOfCat()
						.getCatsSortedByKey()[1].getSetOfRooms()
						.getRoomsSortedByKey()[1].getCapacity());
		assertEquals("test_12_getSetOfSitesMultiSite: asserEquals", 40,
				_dxsosMulti.getRoomCapacity("SHE", "CAT2", "FM-3207"));

		assertNotNull("test_13_getSetOfSitesSingleSite: asserNotNull",
				_dxsosMulti.getAllRooms().getRoom("Z7-2001"));
		assertNotNull("test_14_getSetOfSitesSingleSite: asserNotNull",
				_dxsosMulti.getAllRooms().getRoom("Z7-2003"));
		assertNotNull("test_15_getSetOfSitesSingleSite: asserNotNull",
				_dxsosMulti.getAllRooms().getRoom("130-7"));
		assertNotNull("test_16_getSetOfSitesSingleSite: asserNotNull",
				_dxsosMulti.getAllRooms().getRoom("130-6"));
		assertNotNull("test_17_getSetOfSitesSingleSite: asserNotNull",
				_dxsosMulti.getAllRooms().getRoom("101"));

	}

	public void test_getSetOfSitesMultiCat() {

		String path = "." + File.separator + "dataTest" + File.separator
				+ "locauxFlsh.txt";
		DLoadData ld = new DLoadData();
		// byte[] dataloaded = ;
		//
		// try {
		// dataloaded = ld.filterBadChars(path);
		// } catch (DxException e1) {
		// // normally no exception
		// e1.printStackTrace();
		// }
		//
		// int jours = 7;
		// int per = 4;
		// DxSiteReader dxSites = new DxReadSitedotDia(ld
		// .buildDataExchange(dataloaded), jours, per);
		DxSiteReader dxSites = null;
		DxSetOfSites dxsosFlsh = null;

		try {

			int jours = 7;
			int per = 4;
			dxSites = new DxReadSitedotDia(ld.buildDataExchange(ld
					.filterBadChars(path)), jours, per);

			dxsosFlsh = dxSites.readSetOfSites();
			assertEquals("t_readSetOfSitesMultiCat: siteCount", 1, dxsosFlsh
					.getSiteCount());
			assertEquals("t_readSetOfSitesMultiCat: defaultSite", 4, dxsosFlsh
					.getCatCount(DConst.ROOM_DEFAULT_SITE));

			assertEquals("t_readSetOfSitesMultiCat: defaultSite", 29, dxsosFlsh
					.getAllDxRooms().size());
			// long sitekey = dxsosFlsh.getSiteKey(DConst.ROOM_DEFAULT_SITE);
			// assertEquals("t_readSetOfSitesMultiCat: siteCount_defaultSite",
			// 4,
			// dxsosFlsh.getCatCount(sitekey));
			// long catkey = dxsosFlsh.getCatKey(sitekey, "Classe");
			// assertEquals("t_readSetOfSitesMultiCat: CatName", "Classe",
			// dxsosFlsh.getCatName(sitekey, catkey));
			//
			// catkey = dxsosFlsh.getCatKey(sitekey, "Classe");
			// assertEquals("t_readSetOfSitesMultiCat: Classe 1", 1, catkey);
			//
			// DxSetOfRooms dxSor = dxsosFlsh.getAllDxRooms();
			// long roomkey = dxsosFlsh.getRoomKey(sitekey, catkey, "A3-004");
			// assertEquals("t_readSetOfSitesMultiCat: roomName A3-004",
			// "A3-004",
			// dxSor.getRoomName(roomkey));
			//
			// catkey = dxsosFlsh.getCatKey(sitekey, "Lab");
			// assertEquals("t_readSetOfSitesMultiCat: Lab 4", 4, catkey);
			//
			// roomkey = dxsosFlsh.getRoomKey(sitekey, catkey, "A6-2004");
			// assertEquals("t_readSetOfSitesMultiCat: roomName A6-2004",
			// "A6-2004", dxSor.getRoomName(roomkey));

			// assertEquals("test_2test1_getSetOfSitesMultiCat: asserEquals", 1,
			// dxsosFlsh.getSitesSortedByKey()[0].getCatCount());
			// assertEquals("test_2_1_getSetOfSitesMultiCat: asserEquals", 5,
			// dxsosFlsh.getSitesSortedByKey()[0].getCatCount());
			// assertEquals("test_3_getSetOfSitesMultiSite: asserEquals", 2,
			// dxsosFlsh.getCatCount(DConst.ROOM_DEFAULT_SITE));
			// assertEquals("test_4_getSetOfSitesMultiSite: asserEquals", 7,
			// _dxsosMulti.getSitesSortedByKey()[0].getCat("CAT1")
			// .getRoomCount());
			// assertEquals("test_5_getSetOfSitesMultiSite: asserEquals", -1,
			// _dxsosMulti.getRoomCount("COW", "CAT1"));
			// assertEquals("test_6_getSetOfSitesMultiSite: assertEquals",
			// DConst.ROOM_DEFAULT_SITE, _dxsosMulti.getSitesSortedByKey()[0]
			// .getName());
			// assertEquals("test_6_1_getSetOfSitesMultiSite: assertEquals",
			// "LON",
			// _dxsosMulti.getSitesSortedByKey()[1].getName());
			// assertEquals("test_7_getSetOfSitesMultiSite: asserEquals",
			// "CAT1",
			// _dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
			// .getCatsSortedByKey()[0].getName());
			// assertEquals("test_7_1_getSetOfSitesMultiSite: asserEquals",
			// "CAT2",
			// _dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
			// .getCatsSortedByKey()[0].getName());
			// assertEquals("test_8_getSetOfSitesMultiSite: asserEquals",
			// "Z7-2001",
			// _dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
			// .getCatsSortedByKey()[0].getSetOfRooms()
			// .getRoomsSortedByKey()[0].getName());
			// assertEquals("test_9_1_getSetOfSitesMultiSite: asserEquals",
			// "101",
			// _dxsosMulti.getSitesSortedByKey()[2].getSetOfCat()
			// .getCatsSortedByKey()[0].getSetOfRooms()
			// .getRoomsSortedByKey()[0].getName());
			// assertEquals("test_10_getSetOfSitesMultiSite: asserEquals", 80,
			// _dxsosMulti.getSitesSortedByKey()[0].getSetOfCat()
			// .getCatsSortedByKey()[0].getSetOfRooms()
			// .getRoomsSortedByKey()[0].getCapacity());
			// assertEquals("test_11_getSetOfSitesMultiSite: asserEquals", 60,
			// _dxsosMulti.getSitesSortedByKey()[1].getSetOfCat()
			// .getCatsSortedByKey()[1].getSetOfRooms()
			// .getRoomsSortedByKey()[1].getCapacity());
			// assertEquals("test_12_getSetOfSitesMultiSite: asserEquals", 40,
			// _dxsosMulti.getRoomCapacity("SHE", "CAT2", "FM-3207"));
			//
			// assertNotNull("test_13_getSetOfSitesSingleSite: asserNotNull",
			// _dxsosMulti.getAllRooms().getRoom("Z7-2001"));
			// assertNotNull("test_14_getSetOfSitesSingleSite: asserNotNull",
			// _dxsosMulti.getAllRooms().getRoom("Z7-2003"));
			// assertNotNull("test_15_getSetOfSitesSingleSite: asserNotNull",
			// _dxsosMulti.getAllRooms().getRoom("130-7"));
			// assertNotNull("test_16_getSetOfSitesSingleSite: asserNotNull",
			// _dxsosMulti.getAllRooms().getRoom("130-6"));
			// assertNotNull("test_17_getSetOfSitesSingleSite: asserNotNull",
			// _dxsosMulti.getAllRooms().getRoom("101"));
		} catch (DxException e) {
			// normally no exception
			e.printStackTrace();
		}

	}

}
