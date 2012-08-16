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
public class StiInstructorIDTest extends TestCase implements InstructorConst {

	public void testStiInstructor() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		hm.put(ID, "423486");
		
		StiInstructor si = new StiInstructor(hm);

		assertEquals("testStiInstructor id: ", si.getInstructorID(), "423486");
	}
}
