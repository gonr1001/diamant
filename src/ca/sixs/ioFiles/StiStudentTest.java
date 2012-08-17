/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.ioFiles;

import java.util.HashMap;

import junit.framework.TestCase;

/**
 * @author gonr1001
 * 
 */
public class StiStudentTest extends TestCase implements StudentConst {

	public void testStiStudent() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		hm.put(ID_S, "121547402145000720123");
		hm.put(FN_S, "Jonathan");
		hm.put(LN_S, "Baril Roy");

		StiStudent ss = new StiStudent(hm);

		assertEquals("testStiStudent", "121547402145000720123",
				ss.getStudentID());
		assertEquals("testStiStudent", "Jonathan", ss.getStudentFirstName());
		assertEquals("testStiStudent", "Baril Roy", ss.getStudentrLastName());

	}

}
