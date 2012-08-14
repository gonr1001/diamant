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

import junit.framework.TestCase;

/**
 * @author gonr1001
 * 
 */
public class StiInstructorTest extends TestCase {

	public void testStiInstructor() {
		StiInstructor si = new StiInstructor("423486", "François", "Gitzhofer",
				"1");
		assertEquals("testStiInstructor id: ", si.getInstructorID(), "423486");
		assertEquals("testStiInstructor firstName: ",
				si.getinstructorFirstName(), "François");
		assertEquals("testStiInstructor id:", si.getInstructorLastName(),
				"Gitzhofer");
		assertEquals("testStiInstructor id:", si.getinstructorType(), "1");
	}
}
