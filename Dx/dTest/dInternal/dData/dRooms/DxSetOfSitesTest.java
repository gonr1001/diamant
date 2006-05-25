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
import dInternal.dData.dRooms.DxSiteReader;
import dInternal.dData.dRooms.SetOfSites;
import eLib.exit.txt.FilterFile;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DxSetOfSitesTest extends TestSuite {
    public DxSetOfSitesTest(String name) {
        super(name);
    }

    public static Test suite() {
        // the type safe way is in SimpleTest
        // the dynamic way :
        return new TestSuite(DxSetOfSitesTest.class);
    } // end suite

    public void test_getSetOfSitesSingleSite() {
        String path = "." + File.separator + "dataTest" + File.separator
                + "locaux.txt";
        byte[] dataloaded = preLoad(path);
        DLoadData ld = new DLoadData();
        DataExchange de = ld.buildDataExchange(dataloaded);
        DxSiteReader dxsr = new DxReadSite1dot5(de);

    }

    public void test_getSetOfSitesMultiSite() {
        String path = "." + File.separator + "dataTest" + File.separator
                + "locaux.txt";
        byte[] dataloaded = preLoad(path);
        DLoadData ld = new DLoadData();
        DataExchange de = ld.buildDataExchange(dataloaded);
        DxSiteReader dxsr = new DxReadSite1dot6(de);
    }

    public void test_getSetOfSitesDia() {
        String path = "." + File.separator + "dataTest" + File.separator
                + "locaux.txt";
        byte[] dataloaded = preLoad(path);
        DLoadData ld = new DLoadData();
        DataExchange de = ld.buildDataExchange(dataloaded);
        //DxSiteReader dxsr = new DxReadSitedotDia(de);

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
