/**
 *
 * Title: Room 
 * Description: Room is a class used as a data structure container.
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
 * 
 */
package dInternal.dData.dRooms;

import java.util.StringTokenizer;

import dConstants.DConst;
import dInternal.DObject;
import dInternal.DResource;
import dInternal.DataExchange;
import dInternal.dUtil.DXToolsMethods;

public class Room extends DResource {

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param obj
	 */
	public Room(String id, DObject obj) {
		super(id, obj);
	}

	/**
	 * @param ex
	 * @param position
	 */
	public void build(DataExchange ex){//, int position) {
////		position += 0;
//		RoomAttach roomAt = (RoomAttach) getAttach();
//		String line = ex.getContents();
//		String str = DXToolsMethods.getToken(line, ";",
//				DConst.ROOM_CAPACITY_TOKEN).trim();
//		// _capacity = new Integer(str).intValue();
//		roomAt.setCapacity(new Integer(str).intValue());
//		str = DXToolsMethods.getToken(line, ";", DConst.ROOM_FUNCTION_TOKEN)
//				.trim();
//		// _function = new Integer(str).intValue();
//		roomAt.setFunction(new Integer(str).intValue());
//		str = DXToolsMethods.getToken(line, ";",
//				DConst.ROOM_CARACTERICTICS_TOKEN).trim();
//		buildSetOfCaracteristics(str);
//		str = DXToolsMethods.getToken(line, ";", DConst.ROOM_DESCRIPTION_TOKEN)
//				.trim();
//		roomAt.setDescription(str);
//
//		int nbTokens = DXToolsMethods.countTokens(line, ";");
//		if (nbTokens <= 7)
//			roomAt.setStandardAvailability();
//		else {
//			str = DXToolsMethods.getToken(line, ";", 7).trim();
//			if (str.length() == 0)
//				roomAt.setStandardAvailability();
//			else
//				buildAvailability(str);
//		}
	}

	/**
	 * 
	 * @param str
	 */
	private void buildSetOfCaracteristics(String str) {
//		StringTokenizer caract = new StringTokenizer(str.trim(), ",");
//		while (caract.hasMoreTokens()) {
//			((RoomAttach) getAttach()).addCaracteristics(Integer
//					.parseInt(caract.nextToken().trim()));
//		}
	}

	/**
	 * 
	 * @param str
	 */
	private void buildAvailability(String str) {
//		StringTokenizer availToken = new StringTokenizer(str, ",");
//		int nbAvailT = availToken.countTokens();
//		for (int i = 0; i < nbAvailT; i++)
//			((RoomAttach) getAttach()).addAvailability(availToken.nextToken());

	}

	/**
	 * 
	 * @return
	 */
	public int getRoomCapacity(){
		return 11111;//((RoomAttach)getAttach()).getCapacity();
	}

	/**
	 * @return
	 */
	public int getRoomFunction() {
		// TODO Auto-generated method stub
		return 0;
	}

}
