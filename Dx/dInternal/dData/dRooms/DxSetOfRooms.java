/**
 * Created on May 23, 2006
 * 
 * 
 * Title: DxSetofRooms.java 
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
 * 
 * 
 */
package dInternal.dData.dRooms;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Vector;

import dConstants.DConst;
import dInternal.dData.DxAvailability;
import dInternal.dData.DxResource;
import dInternal.dData.DxSetOfResources;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofRooms is a class used to:
 * <p>
 * Holds a collection of rooms
 * <p>
 * 
 */
public class DxSetOfRooms extends DxSetOfResources {

    public void addSetOfRooms(DxSetOfRooms dxsor) {
        this.addSetOfResources(dxsor);
    }
    
    public DxResource findEquivalent(DxResource dxrSearch) {
        return this.getResource(dxrSearch.getName());
    }

    public DxRoom getRoom(long lRoomKey) {
        try {
            return (DxRoom) this.getResource(lRoomKey);
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxRoom getRoom(String sRoomName) {
        try {
            return (DxRoom) this.getResource(sRoomName);
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public int getRoomCount() {
        return this.size();
    }

    public String getRoomName(long lRoomKey) {
//    	private String getRoomName(SetOfRooms sor, long eltkey) {
		if (lRoomKey != -1) {
			return this.getResourceName(lRoomKey);
		}
		return DConst.NO_ROOM_INTERNAL;
	}
//        try {
//            return this.getResourceName(lRoomKey);
//        } catch (Exception e) {
//            // Null pointer exception will be thrown if room doesnt exist
//            return DConst.NO_ROOM_INTERNAL;
//        }
//    }

    public int getRoomCapacity(long lRoomKey) {
        try {
            return ((DxRoom) this.getResource(lRoomKey)).getCapacity();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }

    public int getRoomCapacity(String sRoomName) {
        try {
            return ((DxRoom) this.getResource(sRoomName)).getCapacity();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }

    public void addRoom(DxRoom dxrRoom) {
        if (this.getResourceKey(dxrRoom.getName()) == -1) {
            this.addResource(dxrRoom);
        }

    }

    public long getRoomKeyByName(String sRoomName) {
        return this.getResourceKey(sRoomName);
    }

    public DxAvailability getRoomAvailability(long lRoomKey) {
        try {
            return ((DxRoom) this.getResource(lRoomKey)).getAvailability();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sRoomName) {
        try {
            return ((DxRoom) this.getResource(sRoomName)).getAvailability();
        } catch (Exception e) {
            // Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxRoom[] getRoomsSortedByKey() {
        return this.getKeySortedRessources().toArray(new DxRoom[this.size()]);
    }

    public DxRoom[] getRoomsSortedByName() {
        return this.getNameSortedRessources().toArray(new DxRoom[this.size()]);
    }
    
    public void merge(DxResource dxrModify, DxResource dxrNew) {
        // Rooms remain unchanged

    }

    public String toWrite(String sSiteName, String sCatName) {
        StringBuffer sbReturn = new StringBuffer();
        Iterator itRooms = this.iterator();
        while (itRooms.hasNext()) {
            sbReturn.append(((DxRoom) itRooms.next()).toWrite(sSiteName,
                    sCatName));
            if (itRooms.hasNext()) {
                sbReturn.append(DConst.CR_LF);
            }
        }
        return sbReturn.toString();
    }

	public Vector<String> getRoomsNameSorted() {
		Vector <DxResource> vr = getNameSortedRessources();
		Vector<String> v = new Vector<String>();
		
		for (int i = 0; i < vr.size(); i++) {
			DxResource r = vr.get(i);
			v.add(r.getName());
		}
		return v; 
	}

	public boolean contains(String roomName) {
		Vector <String> v = this.getRoomsNameSorted();
			for (int i = 0; i < v.size(); i++) {
				if (v.get(i).equalsIgnoreCase(roomName)){
					return true;
				}
			}
		return false;
	}
	
	public DxSetOfRooms clone() {
		DxSetOfRooms r = new DxSetOfRooms();
		Vector <DxResource> vr = getNameSortedRessources();
				
		for (int i = 0; i < vr.size(); i++) {
			DxResource dxr = vr.get(i);
			r.addRoom((DxRoom) dxr);
		}
		return r; 

	}

	public void sortSetByType() {
//		DxRoom r..getRoomCapacity(sRoomName)
//		
		//this.s
	}

	public void sortSetByCapacity() {
		// TODO Auto-generated method stub
		//DxRoom r.getRoomCapacity(sRoomName)
	}
	
	
	public void auxPrintRooms(String oFileName) {
		StringBuffer out = new StringBuffer();
		try {
			FileOutputStream outputFile = new FileOutputStream(oFileName);
			out.append("begin rooms  " + this.size() + DConst.CR_LF);

			Iterator i = this.iterator();
			while (i.hasNext()) {
				DxRoom dr = (DxRoom) i.next();
				out.append(dr.toString() + DConst.CR_LF);
			}
			out.append("end rooms " + DConst.CR_LF);

			outputFile.write(out.toString().getBytes());

			outputFile.close();

			outputFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
