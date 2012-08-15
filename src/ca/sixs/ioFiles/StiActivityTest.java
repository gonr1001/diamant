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
public class StiActivityTest extends TestCase implements ActivityConst {

	public void testStiActivity() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		hm.put(AC, "GEN170"); // nature="1" groupe="01" uaa="1808" uag="1800" lieu="SHE" max_etudiant="99999");
		hm.put(ACT_TYP, "1");
		hm.put(GRP, "01");
		hm.put(UAA, "1808");
		hm.put(LOC, "SHE");
		hm.put(MAX_S, "99999");
		
		StiActivity sa = new StiActivity(hm);

		assertEquals("StiActivityTest AC: ", sa.getActivityCode(), "GEN170");
		assertEquals("StiActivityTest Ac: ",
				sa.getType(), "1");
		assertEquals("testStiInstructor id:", sa.getGroup(),
				"01");
		assertEquals("testStiInstructor id:", sa.getUAA(), "1808");
		assertEquals("testStiInstructor id:", sa.getLocation(), "SHE");
		assertEquals("testStiInstructor id:", sa.getMaxStu(), "99999");
	}
}
