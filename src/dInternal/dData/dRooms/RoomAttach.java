///**
// *
// * Title: RoomAttach 
// * Description: RoomAttach is a class used to
// *
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// *
// *
// */
//package dInternal.dData.dRooms;
//
//import java.util.StringTokenizer;
//import java.util.Vector;
//
//import dInternal.DObject;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.DValue;
//
////import dInternal.DSetOfResources;
//
//import dInternal.dData.AvailabilityAttach;
//
//
//public class RoomAttach extends AvailabilityAttach {
//
//	private int _capacity;
//
//	private String _description;
//
//	private int _function;
//
//	private DSetOfResources _setOfCaracteristics;
//
//	/**
//	 * @associates String
//	 */
//	public RoomAttach() {
//		_capacity = 0;
//		_description = "";
//		_function = -1;
//		_ressourceAvailability = new Vector<String>();
//		_setOfCaracteristics = new SetOfCaracteristics();
//	}
//
//	/**
//	 * add an availability day in roomDisp
//	 */
//	public void addAvailability(String disp) {
//		_ressourceAvailability.add(disp);
//	}
//
//	/***************************************************************************
//	 * add function INPUT: funct (a string)
//	 */
//	public void setFunction(int funct) {
//		_function = funct;
//	}
//
//	/***************************************************************************
//	 * set capacity INPUT: capa (an integer)
//	 */
//	public void setCapacity(int capa) {
//		_capacity = capa;
//	}
//
//	/***************************************************************************
//	 * set description INPUT: desc (a string)
//	 */
//	public void setDescription(String desc) {
//		_description = desc;
//	}
//
//	/***************************************************************************
//	 * add caracteristics
//	 * 
//	 * @param int
//	 *            the caracrteristic to add in the room
//	 */
//	public boolean addCaracteristics(int carac) {
//		if (carac != -1) {
//			_setOfCaracteristics.setCurrentKey(carac);
//			return _setOfCaracteristics.addResource(new DResource("",
//					new DValue()), 0);
//		}
//		return false;
//	}
//
//	/**
//	 * Remove an availibility day INPUT: day number. day =1 equals roomDisp
//	 * position = 0
//	 */
//	public boolean removeAvailDay(int day) {
//		if (day > 0)
//			if (day <= _ressourceAvailability.size()) {
//				_ressourceAvailability.remove(day - 1);
//				return true;
//			}
//		return false;
//	}
//
//	/**
//	 * clear and set roomDisp INPUT: Vector of new room availability (roomDisp)
//	 */
//	public void setAvailability(Vector<String> roomDisp) {
//		_ressourceAvailability = new Vector<String>();
//		_ressourceAvailability =(Vector<String>) roomDisp.clone();
//	}
//
//	/**
//	 * 
//	 */
//	public Vector getVectorAvailability() {
//		return _ressourceAvailability;
//	}
//
//	/*
//	 */
//	public int getCapacity() {
//		return _capacity;
//	}
//
//	/*
//	 */
//	public String getDescription() {
//		return _description;
//	}
//
//	/**
//	 * */
//	public int getFunction() {
//		return _function;
//	}
//
//	/**
//	 * */
//	public DSetOfResources getSetOfCaracteristics() {
//		return _setOfCaracteristics;
//	}
//
//	public int[][] getMatrixAvailability() {
//		String jour = _ressourceAvailability.get(0);
//		StringTokenizer st = new StringTokenizer(jour);
//		int[][] a = new int[_ressourceAvailability.size()][st.countTokens()];
//		int nbOfTokens = st.countTokens();
//		for (int i = 0; i < _ressourceAvailability.size(); i++) {
//			jour = _ressourceAvailability.get(i);
//			st = new StringTokenizer(jour);
//			nbOfTokens = st.countTokens();
//			for (int j = 0; j < nbOfTokens; j++) {
//				a[i][j] = Integer.parseInt(st.nextToken());
//			} // end for j
//		} // end for i
//		return a;
//	}
//
//	public void setAvailability(int[][] a) {
//		_ressourceAvailability = new Vector<String>();
//		String str = "";
//		for (int i = 0; i < a.length; i++) {
//			for (int j = 0; j < a[i].length; j++) {
//				str += a[i][j];
//				if (j < a[i].length - 1)
//					str += " ";
//			} // end for j
//			_ressourceAvailability.add(str);
//			str = "";
//		} // end for i
//	}
//
//	/**
//	 * return the value of the selected key INPUT: choice, an integer. choice =
//	 * 0 return _capacity OUTPUT: an integer. it return -1 if choice is invalid
//	 */
//	public long getSelectedField(int choice) {
//		switch (choice) {
//		case 0:
//			return _capacity;
//		}
//		return -1;
//	}
//
//	public void setFullAvailability() {
//		String str = "";
//		for (int i = 0; i < _ressourceAvailability.size(); i++) {
//			str = _ressourceAvailability.get(i).toString();
//			str = str.replaceAll("5", "1");
//			_ressourceAvailability.setElementAt(str, i);
//		}
//	}
//
//	/**
//	 * Print local information OUTPUT: String of roomID and room availability
//	 */
//	public String toWrite() {
//		String caracteristics = "";
//		for (int i = 0; i < _setOfCaracteristics.size(); i++) {
//			caracteristics += _setOfCaracteristics.getResourceAt(i).getKey();
//			if (i < _setOfCaracteristics.size() - 1)
//				caracteristics += ",";
//		}
//
//		String avail = "";
//		for (int i = 0; i < _ressourceAvailability.size() - 1; i++){
//			avail += _ressourceAvailability.get(i) + ",";
//		}		
//		avail += _ressourceAvailability.get(_ressourceAvailability
//				.size() - 1);
//		String roomInfo = _capacity + ";" + _function + ";" + caracteristics 
//				+ ";" + _description + ";" + avail + ";";
//		return roomInfo;
//	}
//
//	/**
//	 * 
//	 */
//	public void setStandardAvailability() {
//		String stAvail = "1 1 1 1 1 1 1 1 1 1 1 1";
//		for (int i = 0; i < 5; i++)
//			_ressourceAvailability.add(stAvail);
//	}
//
//	/**
//	 * compare this resource with the specified resource
//	 * 
//	 * @param resource
//	 *            the specified resource
//	 * @return bolean true if this resource and the specified resource are
//	 *         equals false if they are not equals
//	 */
//	public boolean isEquals(DObject room) {
//		RoomAttach roomAttach = (RoomAttach) room;
//		if (_capacity != roomAttach._capacity)
//			return false;
//		else if (_function != roomAttach._function)
//			return false;
//		else if (!_description.equals(roomAttach._description))
//			return false;
//		else if (!_ressourceAvailability
//				.equals(roomAttach._ressourceAvailability))
//			return false;
//		else if (!_setOfCaracteristics
//				.isEquals(roomAttach._setOfCaracteristics))
//			return false;
//		return true;
//	}
//
//	/*
//	 * public void build(DataExchange ex, int position) { position+=0; String
//	 * line = ex.getContents(); String str = DXToolsMethods.getToken(line,
//	 * ";",DConst.ROOM_CAPACITY_TOKEN).trim(); _capacity = new
//	 * Integer(str).intValue(); str = DXToolsMethods.getToken(line,
//	 * ";",DConst.ROOM_FUNCTION_TOKEN).trim(); _function = new
//	 * Integer(str).intValue(); str = DXToolsMethods.getToken(line,
//	 * ";",DConst.ROOM_CARACTERICTICS_TOKEN).trim();
//	 * buildSetOfCaracteristics(str); _description =
//	 * DXToolsMethods.getToken(line, ";",DConst.ROOM_DESCRIPTION_TOKEN).trim();
//	 * 
//	 * int nbTokens = DXToolsMethods.countTokens(line, ";"); if(nbTokens <= 7)
//	 * setStandardAvailability(); else { str = DXToolsMethods.getToken(line,
//	 * ";",7).trim(); buildAvailability(str); }
//	 *  }
//	 * 
//	 * 
//	 * private void buildSetOfCaracteristics( String str) { StringTokenizer
//	 * caract= new StringTokenizer(str.trim(),",");
//	 * while(caract.hasMoreTokens()){
//	 * addCaracteristics(Integer.parseInt(caract.nextToken().trim())); } }
//	 * 
//	 * 
//	 * private void buildAvailability(String str) { StringTokenizer availToken =
//	 * new StringTokenizer(str,","); int nbAvailT= availToken.countTokens();
//	 * for(int i=0; i < nbAvailT; i++) addAvailability(availToken.nextToken());
//	 *  }
//	 */
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see dInternal.DObject#getSelectedField()
//	 */
//	public long getSelectedField() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//}