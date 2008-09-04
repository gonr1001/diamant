package ca.sixs.util.pref;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FileFilterPrefTest extends TestCase implements
		ConstantsForFileFilter {

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(FileFilterPrefTest.class);
	} // end suite

	public void test_FileFilterChars() {
		FileFilterPref ff = new FileFilterPref();
		String aux = ff.getFileFilterChars();
		assertEquals("FileFilterPref  aux", aux, ff.getFileFilterChars());
		ff.putFileFilterChars(aux + "#");
		assertEquals("FileFilterPref  aux", aux + "#", ff.getFileFilterChars());
		ff.putFileFilterChars(aux);
		assertEquals("FileFilterPref  aux", aux , ff.getFileFilterChars());

	}

}