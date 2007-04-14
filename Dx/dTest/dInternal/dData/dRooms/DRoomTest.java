/**
 *
 * Title: DRoomTest $Revision $  $Date: 2007-04-14 21:36:43 $
 * Description: 	DRoomTest is a class used to test the class 
 * 				DRoomTest 
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 * @version $ $
 * @author  $Author: gonzrubi $
 * @since JDK1.3
 */

package dTest.dInternal.dData.dRooms;

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.dRooms.Room;
import dInternal.dData.dRooms.RoomAttach;

public class DRoomTest extends TestCase {

	public DRoomTest(String name) {
		super(name);
	}

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DRoomTest.class);
	} // end suite

	public void test_addAvailability() {
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5,1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		v.add("1 1 1 5 5");
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		assertEquals("test_addAvailability: assertEquals", roomAttach
				.getVectorAvailability(), v);
	}

	public void test_removeAvailability() {
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 5");
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		// roomAttach.addAvailability("1 1 1 1 5");
		// roomAttach.addAvailability("1 1 1 5 5");
		// assertEquals("Instructor Dispo equals :",
		// _inst.getVectorAvailability(), v);
		roomAttach.removeAvailDay(2);
		assertEquals("test_removeAvailability: assertEquals", roomAttach
				.getVectorAvailability(), v);
	}

	public void test_setAvailability() {
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 1");
		v.add("1 1 1 1 1");
		// RoomAttach roomAttach = new RoomAttach();
		roomAttach.setAvailability(v);
		assertEquals("test_setAvailability: assertEquals", roomAttach
				.getVectorAvailability(), v);
	}

	public void test1_setAvailability() {
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		// RoomAttach roomAttach = new RoomAttach();
		int[][] availMatrix = { { 1, 1, 1, 1, 5 }, { 1, 1, 1, 5, 5 } };
		roomAttach.setAvailability(availMatrix);
		assertEquals("test1_getAvailability: assertEquals", v, roomAttach
				.getVectorAvailability());
	}

	public void test_getAvailability() {
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		// RoomAttach roomAttach = new RoomAttach();
		int[][] availMatrix = { { 1, 1, 1, 1, 5 }, { 1, 1, 1, 5, 5 } };
		roomAttach.setAvailability(v);
		assertEquals("test_getAvailability: assertEquals", true, compare(
				availMatrix, roomAttach.getMatrixAvailability()));
	}

	public void test1_getAvailability() {
		String str = "Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5,"
				+ "1 1 1 5 5;" + "\r\n";
		DataExchange dex = new ByteArrayMsg("Dia1.6", str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex);//, 0);
		RoomAttach roomAttach = (RoomAttach) room.getAttach();
		Vector<String> v = new Vector<String>();
		v.add("1 1 1 1 5 1");
		v.add("1 1 1 5 5");
		// RoomAttach roomAttach = new RoomAttach();
		int[][] availMatrix = { { 1, 1, 1, 1, 5 }, { 1, 1, 1, 5, 5 } };
		roomAttach.setAvailability(v);
		assertEquals("test1_getAvailability: assertEquals", false, compare(
				availMatrix, roomAttach.getMatrixAvailability()));
	}

	/**
	 * compare matrix1 and matrix2
	 * 
	 * @param int[][]
	 *            the matrix1
	 * @param int[][]
	 *            the matrix2
	 * @return boolean the result
	 */
	private boolean compare(int[][] matrix1, int[][] matrix2) {
		if (matrix1.length == matrix2.length) {
			for (int i = 0; i < matrix1.length; i++) {
				if (matrix1[i].length == matrix2[i].length) {
					for (int j = 0; j < matrix1[i].length; j++)
						if (matrix1[i][j] != matrix2[i][j])
							return false;
				} else
					return false;
			}
		} else
			return false;
		return true;
	}
}