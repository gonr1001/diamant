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
public class StiActivityInStudentTest extends TestCase implements ActivityConst {

	public void testStiActivityInStudent() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(AC, "GCH108");
		hm.put(ACT_TYP, "1");
		hm.put(GRP, "00");
		hm.put(LOC, "SHE");
		hm.put(FIX_GRP, "N");
		
		StiActivityInStudent sa = new StiActivityInStudent(hm);

		assertEquals("StiActivityTest ", sa.getActivityCode(), "GCH108");
		assertEquals("StiActivityTest ", sa.getType(), "1");
		assertEquals("StiActivityTest ", sa.getGroup(), "00");
		assertEquals("StiActivityTest ", sa.getLocation(), "SHE");
		assertEquals("StiActivityTest ", sa.getFixedGroup(), "N");
	}
}
