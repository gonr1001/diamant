
package dTest.dInternal.dData.dRooms;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */

//public class RoomAttachTest {
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dData.dRooms.Room;
import dInternal.dData.dRooms.RoomAttach;

public class DRoomTest extends TestCase {
	//private StudentAttach _student;
	
	public DRoomTest(String name) {
		super(name);
	}
	
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DRoomTest.class);
	} // end suite
	
	
	public void test_addAvailability(){
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5,1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Vector v = new Vector();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		v.add("1 1 1 5 5");
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		assertEquals("test_addAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
	}
	
	public void test_removeAvailability(){
		Vector v = new Vector();
		v.add("1 1 1 1 5");
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		//roomAttach.addAvailability("1 1 1 1 5");
		//roomAttach.addAvailability("1 1 1 5 5");
		//assertEquals("Instructor Dispo equals :", _inst.getVectorAvailability(), v);
		roomAttach.removeAvailDay(2);
		assertEquals("test_removeAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
	}
	
	public void test_setAvailability(){
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		Vector v = new Vector();
		v.add("1 1 1 1 1");
		v.add("1 1 1 1 1");
		//RoomAttach roomAttach = new RoomAttach();
		roomAttach.setAvailability(v);
		assertEquals("test_setAvailability: assertEquals", roomAttach.getVectorAvailability(), v);
	}
	
	public void test1_setAvailability(){
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		Vector v = new Vector();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		//RoomAttach roomAttach = new RoomAttach();
		int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
		roomAttach.setAvailability(availMatrix);
		assertEquals("test1_getAvailability: assertEquals", v , roomAttach.getVectorAvailability());
	}
	
	public void test_getAvailability(){
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		Vector v = new Vector();
		v.add("1 1 1 1 5");
		v.add("1 1 1 5 5");
		//RoomAttach roomAttach = new RoomAttach();
		int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
		roomAttach.setAvailability(v);
		assertEquals("test_getAvailability: assertEquals", true, compare(availMatrix, roomAttach.getMatrixAvailability()));
	}
	
	public void test1_getAvailability(){
		String str="Z7-2001;80;211;08,11,14,57;SHE;CAT1;Multimedia;1 1 1 1 5," +
		"1 1 1 5 5;"+"\r\n";
		DataExchange dex= new ByteArrayMsg("Dia1.6",str);
		Room room = new Room("Room", new RoomAttach());
		room.build(dex,0);
		RoomAttach roomAttach = (RoomAttach)room.getAttach();
		Vector v = new Vector();
		v.add("1 1 1 1 5 1");
		v.add("1 1 1 5 5");
		//RoomAttach roomAttach = new RoomAttach();
		int [][] availMatrix={{1,1,1,1,5},{1,1,1,5,5}};
		roomAttach.setAvailability(v);
		assertEquals("test1_getAvailability: assertEquals", false, compare(availMatrix, roomAttach.getMatrixAvailability()));
	}
	
	
	
	/**
	 * compare matrix1 and matrix2
	 * @param int[][] the matrix1
	 * @param int[][] the matrix2
	 * @return boolean the result
	 * */
	private boolean compare(int[][] matrix1, int[][] matrix2){
		if(matrix1.length== matrix2.length){
			for (int i=0; i< matrix1.length; i++){
				if (matrix1[i].length== matrix2[i].length){
					for(int j=0; j< matrix1[i].length; j++)
						if (matrix1[i][j]!=matrix2[i][j])
							return false;
				}else
					return false;
			}
		}
		else
			return false;
		return true;
	}
	
}