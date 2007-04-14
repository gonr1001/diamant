/**
*
* Title: SetOfSites $Revision: 1.11 $  $Date: 2007-04-14 16:54:45 $
* Description: SetOfSites is a class used as a data structure container.
*              It contains the rooms and their attributes.
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
* @version $Revision: 1.11 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInternal.dData.dRooms;




import dConstants.DConst;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dUtil.DXToolsMethods;


public class SetOfRooms extends DSetOfResources {

	
	/**
	 * 
	 */
	public SetOfRooms() {
		super();
	}

//	/* (non-Javadoc)
//	 * @see dInternal.DSetOfResources#analyseTokens(byte[], int)
//	 */
//	public boolean analyseTokens(byte[] dataloaded, int beginPosition) {
//		dataloaded[0]+=0;
//		beginPosition+=0;
//		return false;
//	}

	/* (non-Javadoc)
	 * @see dInternal.DSetOfResources#buildSetOfResources(byte[], int)
	 */
	public void buildSetOfResources(DataExchange de/*, int beginPosition*/) {
		String room = DXToolsMethods.getToken(de.getContents(), ";",DConst.ROOM_NAME_TOKEN).trim();
		Room roomRsc= (Room)getResource(room);
		if(roomRsc==null){
			roomRsc=new Room(room,new RoomAttach());
			addResource(roomRsc,1);
		}
//		RoomAttach roomAttach= ((RoomAttach)roomRsc.getAttach());
		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, de.getContents());
		roomRsc.build(dEx,0);

	}

	/* (non-Javadoc)
	 * @see dInternal.DSetOfResources#getError()
	 */
	public String getError() {
		return "";
	}

	/* (non-Javadoc)
	 * @see dInternal.dUtil.DXObject#toWrite()
	 */
	public String toWrite() {
		String reslist="";
	    if(getSetOfResources().size()>0){	      
	        for (int i=0; i< getSetOfResources().size()-1; i++) {
	          reslist+= getSetOfResources().get(i).toWrite(";")+DConst.CR_LF;
	        }
	        reslist+= getSetOfResources().get(getSetOfResources().size()-1).toWrite(";")+DConst.CR_LF;
	      }	   
	    return reslist;	
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {

		return 0;
	}
	  /**
	   *
	   * @param dml
	   */
/*	  public synchronized void addSetOfRoomsListener(SetOfRoomsListener soRoomsl) {
	    if (_soSitesListeners.contains(soRoomsl)){
	      return;
	    }
	    _soSitesListeners.addElement(soRoomsl);
	  }
	  */
	  /**
	   *
	   * @param component
	   */
/*	 public void sendEvent(Component component) {
	 	SetOfRoomsEvent event = new SetOfRoomsEvent(this);
	   for (int i=0; i< _soSitesListeners.size(); i++) {
	     SetOfRoomsListener sorl = (SetOfRoomsListener) _soSitesListeners.elementAt(i);
	     sorl.changeInSetOfRooms(event, component);
	   }
	  }
*/
	  
}
