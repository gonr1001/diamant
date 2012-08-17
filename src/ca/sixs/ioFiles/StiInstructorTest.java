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
public class StiInstructorTest extends TestCase implements InstructorConst {

	public void testStiInstructor() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		hm.put(ID_I, "423486");
		hm.put(FN_I, "François");
		hm.put(LN_I, "Gitzhofer");
		hm.put(TY, "1");

		StiInstructor si = new StiInstructor(hm);

		assertEquals("testStiInstructor ", "423486", si.getInstructorID());
		assertEquals("testStiInstructor ", "François",
				si.getinstructorFirstName());
		assertEquals("testStiInstructor ", "Gitzhofer",
				si.getInstructorLastName());
		assertEquals("testStiInstructor ", "1", si.getinstructorType());
	}
}
