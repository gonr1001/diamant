package dTest.dInternal.dData.dRooms;

import java.io.File;
import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DataExchange;
import dInternal.Preferences;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.DLoadData;
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
    
    public void test_getSetOfSitesSingleSite(){
     
       /* DataExchange de = new ByteArrayMsg(DConst.FILE_VER_NAME1_5, new String(preLoad("." + File.separator + "dataTest"
                + File.separator + "locaux.txt")));*/
        
        /*
         * StringTokenizer st = new StringTokenizer(new String(), DConst.CR_LF);
         * String token = st.nextToken().toString().trim();
         */
        
    }
    
private byte[] preLoad(String str) {
    Preferences preferences = new Preferences("."+ File.separator +
            "pref"
            + File.separator +
            "pref.txt");
    FilterFile filter = new FilterFile();
    filter.setCharKnown("");
    filter.appendToCharKnown(preferences._acceptedChars);
    if (filter.validFile(str)) {
        return filter.getByteArray();
    } 
    return null;
} // preLoad(String str)
}
