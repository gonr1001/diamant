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
public class StiSlotTest extends TestCase implements SlotConst {

	public void testStiSlot() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		hm.put(DAY, "LU");
		hm.put(BEGIN, "08:30");
		hm.put(END, "12:30");
		hm.put(FIXED, "1");
		hm.put(ROOM, "C1-5012");
		hm.put(ROOM_FIXED, "0");

		StiSlot ss = new StiSlot(hm);

		assertEquals("testStiSlot ", "LU", ss.getDay());
		assertEquals("testStiSlot ", "08:30", ss.getBegin());
		assertEquals("testStiSlot ", "12:30", ss.getEnd());
		assertEquals("testStiSlot ", "1", ss.getFixed());
		assertEquals("testStiSlot ", "C1-5012", ss.getRoom());
		assertEquals("testStiSlot ", "0", ss.getRoomFixed());
	}
}
