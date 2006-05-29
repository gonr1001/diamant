package dTest.dInternal.dData.dRooms;

import java.io.File;
import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.Preferences;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.DLoadData;
import dInternal.dData.dRooms.DxReadSite1dot5;
import dInternal.dData.dRooms.DxReadSite1dot6;
import dInternal.dData.dRooms.DxSetOfSites;
import dInternal.dData.dRooms.DxSiteReader;
import eLib.exit.txt.FilterFile;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxSetOfSitesTest extends TestCase {
    DxSetOfSites _dxsosSingle, _dxsosMulti, _dxsosDia;

    public DxSetOfSitesTest(String name) {
        super(name);

        String path = "." + File.separator + "dataTest" + File.separator
                + "locaux.txt";
        byte[] dataloaded = preLoad(path);
        DLoadData ld = new DLoadData();
        DataExchange de = ld.buildDataExchange(dataloaded);
        DxSiteReader dxsr = new DxReadSite1dot5(de);

        _dxsosSingle = dxsr.getSetOfSite();

        path = "." + File.separator + "dataTest" + File.separator
                + "locauxINFIRComplet.txt";
        dataloaded = preLoad(path);
        StringTokenizer st=new StringTokenizer(new String(dataloaded),DConst.CR_LF);
        String s1dot6="";
        st.nextToken(); //Removes Diamant 1.6
        while(st.hasMoreTokens())
        {
            //Rebuild file, but without Diamant 1.6
            s1dot6+=(st.nextToken()+DConst.CR_LF);
        }
        de = new ByteArrayMsg(DConst.FILE_VER_NAME1_6, s1dot6);
        dxsr = new DxReadSite1dot6(de);

        _dxsosMulti = dxsr.getSetOfSite();
/*
        path = "." + File.separator + "dataTest" + File.separator
                + "loadData7j.dia";
        dataloaded = preLoad(path);
        st = new StringTokenizer(new String(dataloaded),
                DConst.SAVE_SEPARATOR);
        st.nextToken(); // Skips time table definition
        st.nextToken(); // Skips instructors
        de = new ByteArrayMsg(DConst.FILE_VER_NAME1_5, st.nextToken().trim());
        ////7 days and 12 periods in this file, would normaly begiven by the time table
        dxsr = new DxReadSitedotDia(de, 7, 12); 

        _dxsosDia = dxsr.getSetOfSite();*/
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
                _dxsosSingle.getCatCount(DConst.ROOM_STANDARD_SITE));
        assertEquals("test_3_getSetOfSitesSingleSite: asserEquals", 44,
                _dxsosSingle.getRoomCount(1, 1));
        assertEquals("test_3_1_getSetOfSitesSingleSite: asserEquals", 44,
                _dxsosSingle.getRoomCount(DConst.ROOM_STANDARD_SITE,
                        DConst.ROOM_STANDARD_CAT));

        assertEquals("test_4_getSetOfSitesSingleSite: assertEquals",
                DConst.ROOM_STANDARD_SITE, _dxsosSingle.getSiteName(1));
        assertEquals("test_5_getSetOfSitesSingleSite: asserEquals",
                DConst.ROOM_STANDARD_CAT, _dxsosSingle.getCatName(1, 1));
        assertEquals("test_6_getSetOfSitesSingleSite: asserEquals", "D13012",
                _dxsosSingle.getRoomName(1, 1, 1));
        assertEquals("test_7_getSetOfSitesSingleSite: asserEquals", "D13000",
                _dxsosSingle.getRoomName(1, 1, 44));
        
        assertEquals("test_8_getSetOfSitesSingleSite: asserEquals", 20,
                _dxsosSingle.getRoomCapacity(1, 1, 44));

        assertEquals("test_9_getSetOfSitesSingleSite: asserEquals", null,
                _dxsosSingle.getRoomAvailabilityByKey(1, 1, 23));

        assertEquals("test_10_getSetOfSitesSingleSite: asserEquals", 61,
                _dxsosSingle.getRoomCapacity(DConst.ROOM_STANDARD_SITE,
                        DConst.ROOM_STANDARD_CAT, "D72007"));

    }

    public void test_getSetOfSitesMultiSite() {
        assertEquals("test_1_getSetOfSitesMultiSite: asserEquals", 3,
                _dxsosMulti.getSiteCount());
        assertEquals("test_2_getSetOfSitesMultiSite: asserEquals", 2,
                _dxsosMulti.getCatCount(1));
        assertEquals("test_2_1_getSetOfSitesMultiSite: asserEquals", 1,
                _dxsosMulti.getCatCount(3));
        assertEquals("test_3_getSetOfSitesMultiSite: asserEquals", 2,
                _dxsosMulti.getCatCount(DConst.ROOM_STANDARD_SITE));
        assertEquals("test_4_getSetOfSitesMultiSite: asserEquals", 7,
                _dxsosMulti.getRoomCount(1, 1));
        assertEquals("test_5_getSetOfSitesMultiSite: asserEquals", -1,
                _dxsosMulti.getRoomCount("COW","CAT1"));

        assertEquals("test_6_getSetOfSitesMultiSite: assertEquals",
                DConst.ROOM_STANDARD_SITE, _dxsosMulti.getSiteName(1));
        assertEquals("test_6_1_getSetOfSitesMultiSite: assertEquals",
                "LON", _dxsosMulti.getSiteName(2));
        assertEquals("test_7_getSetOfSitesMultiSite: asserEquals",
                "CAT1", _dxsosMulti.getCatName(1, 1));
        assertEquals("test_7_1_getSetOfSitesMultiSite: asserEquals",
                "CAT2", _dxsosMulti.getCatName(3, 1));
        assertEquals("test_8_getSetOfSitesMultiSite: asserEquals", "Z7-2001",
                _dxsosMulti.getRoomName(1, 1, 1));
        assertEquals("test_9_getSetOfSitesMultiSite: asserEquals", null,
                _dxsosMulti.getRoomName(1, 1, 44));
        assertEquals("test_9_1_getSetOfSitesMultiSite: asserEquals", "101",
                _dxsosMulti.getRoomName(3, 1, 1));
        
        assertEquals("test_10_getSetOfSitesMultiSite: asserEquals", 80,
                _dxsosMulti.getRoomCapacity(1, 1, 1));
        
        assertEquals("test_11_getSetOfSitesMultiSite: asserEquals", 60,
                _dxsosMulti.getRoomCapacity(2, 2, 2));

        assertEquals("test_11_getSetOfSitesMultiSite: asserEquals", null,
                _dxsosMulti.getRoomAvailabilityByKey(2, 2, 23));

        assertEquals("test_12_getSetOfSitesMultiSite: asserEquals", 40,
                _dxsosMulti.getRoomCapacity("SHE","CAT2","FM-3207"));
    }

    public void test_getSetOfSitesDia() {
        //
    }

    private byte[] preLoad(String str) {
        Preferences preferences = new Preferences("." + File.separator + "pref"
                + File.separator + "pref.txt");
        FilterFile filter = new FilterFile();
        filter.setCharKnown("");
        filter.appendToCharKnown(preferences._acceptedChars);
        if (filter.validFile(str)) {
            return filter.getByteArray();
        }
        return null;
    } // preLoad(String str)
}
