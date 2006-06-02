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

import dInternal.dData.DxAvailability;
import dInternal.dData.DxSetOfRessources;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxSetofRooms is a class used to:
 * <p>
 * Holds a collection of rooms
 * <p>
 * 
 */
public class DxSetOfRooms extends DxSetOfRessources{

    public int getRoomCount() {
        return this.size();
    }
    
    public String getRoomName(long lRoomKey) {
        try{
            return this.getRessourceName(lRoomKey);
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public int getRoomCapacity(long lRoomKey) {
        try{
            return ((DxRoom)this.getRessource(lRoomKey)).getRoomCapacity();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }

    public int getRoomCapacity(String sRoomName) {
        try{
            return ((DxRoom)this.getRessourceByName(sRoomName)).getRoomCapacity();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return 0;
        }
    }
    
    public void addRoom(DxRoom dxrRoom) {
        if(this.getRessourceKeyByName(dxrRoom.getRoomName()) == -1) {
            this.addRessource(dxrRoom);
        }

    }

    public long getRoomKeyByName(String sRoomName) {
        return this.getRessourceKeyByName(sRoomName);
    }

    public DxAvailability getRoomAvailability(long lRoomKey) {
        try{
            return ((DxRoom)this.getRessource(lRoomKey)).getRoomAvailability();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }

    public DxAvailability getRoomAvailability(String sRoomName) {
        try{
            return ((DxRoom)this.getRessourceByName(sRoomName)).getRoomAvailability();
        }catch (Exception e){
            //Null pointer exception will be thrown if room doesnt exist
            return null;
        }
    }
}
