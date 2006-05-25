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
import dInternal.dData.dRooms.DxReadSitedotDia;
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
        de = ld.buildDataExchange(dataloaded);
        dxsr = new DxReadSite1dot6(de);

        _dxsosMulti = dxsr.getSetOfSite();

        path = "." + File.separator + "dataTest" + File.separator
                + "loadData7j.dia";
        dataloaded = preLoad(path);
        StringTokenizer st = new StringTokenizer(new String(dataloaded),
                DConst.SAVE_SEPARATOR);
        st.nextToken(); // Skips time table definition
        st.nextToken(); // Skips instructors
        de = new ByteArrayMsg(DConst.FILE_VER_NAME1_6, st.nextToken().trim());
        dxsr = new DxReadSitedotDia(de, 7, 12);

        _dxsosDia = dxsr.getSetOfSite();
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
        assertEquals("test_2_getSetOfSitesSingleSite: asserEquals", 1,
                _dxsosSingle.getRoomCount(1, 1));

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

    }

    public void test_getSetOfSitesMultiSite() {
//
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
