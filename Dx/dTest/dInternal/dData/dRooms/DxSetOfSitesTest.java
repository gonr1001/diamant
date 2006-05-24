package dTest.dInternal.dData.dRooms;

import java.io.File;

import dInterface.DDocument;
import dInternal.DModel;
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
        String s="." + File.separator + "dataTest"
                + File.separator + "locaux.txt";
    }
    
    
}
