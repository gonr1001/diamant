package dTest.dInternal;

import java.util.Iterator;
import java.util.Vector;

import dInternal.dDeployment.DxConfigResource;
import dInternal.dDeployment.DxDeploymentManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DxDeploymentManagerTest extends TestCase {

	public DxDeploymentManagerTest(String arg0) {
		super(arg0);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxDeploymentManagerTest.class);
	} // end suite

	public void test_listeRessource() {

		// list of ressource
		DxDeploymentManager dListDeployed = new DxDeploymentManager();

		// Expected List
		Vector<DxConfigResource> vsFileNamesExpected = new Vector<DxConfigResource>();
		// directories
		DxConfigResource pref = new DxConfigResource("pref", true);
		DxConfigResource trace = new DxConfigResource("trace", true);
		// files
		vsFileNamesExpected.add(pref);
		vsFileNamesExpected.add(new DxConfigResource(pref, "pref.txt", false));
		vsFileNamesExpected.add(new DxConfigResource(pref,
				"DXcaracteristics.sig", false));
		vsFileNamesExpected.add(new DxConfigResource(pref, "DXfunctions.sig",
				false));
		vsFileNamesExpected.add(new DxConfigResource(pref, "logoDiamant.gif",
				false));

		vsFileNamesExpected.add(new DxConfigResource(pref, "StandardTTC.xml",
				false));
		vsFileNamesExpected.add(new DxConfigResource(pref, "StandardTTE.xml",
				false));
		vsFileNamesExpected.add(trace);
		vsFileNamesExpected
				.add(new DxConfigResource(trace, "log4j.conf", false));
		vsFileNamesExpected.add(new DxConfigResource(trace, "log4jreex.conf",
				false));
		vsFileNamesExpected
				.add(new DxConfigResource(trace, "trace.log", false));

		// test
		Vector<DxConfigResource> vsFileNames = dListDeployed.getListRessource();

		Iterator<DxConfigResource> itList = vsFileNames.iterator();
		Iterator<DxConfigResource> itListExpected = vsFileNamesExpected
				.iterator();
		int i = 0;

		while (itList.hasNext()) {
			i++;
			assertEquals("test list of ressources" + i + ": assertEquals",
					itList.next().getClassLoaderPath(), itListExpected.next()
							.getClassLoaderPath());
		}
	}

}
