/**
 * Created on Jun 19, 2006
 * 
 * 
 * Title: DxConflicLimits.java 
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
package dInternal;

import java.util.StringTokenizer;

import dConstants.DConst;

/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxConflicLimits is a class used to:
 * <p>
 * TODO:insert comments
 * <p> 
 * 
 */
public class DxConflictLimits {
	
	private int _mStudConfBetweenTwoEvents;
	
	private int _mInstConfBetweenTwoEvents;
	
	private int _mRoomConfBetweenTwoEvents;
	
	private int _mAllowedPriority;
	
	private int _mNumOfEventsInPeriod;
	
	private int _minPeriodSpacing;
	
	private int _roomBookingRate;

	
	public DxConflictLimits() {
		super();
	}
	
	
	public void readLimits(String str){
		StringTokenizer st = new StringTokenizer(str,DConst.CONFLICT_LIMITS_SEPARATOR);
		st.nextToken().trim();
		_mStudConfBetweenTwoEvents = Integer.parseInt(st.nextToken().trim());
		_mInstConfBetweenTwoEvents = Integer.parseInt(st.nextToken().trim());
		_mRoomConfBetweenTwoEvents = Integer.parseInt(st.nextToken().trim());
		_mAllowedPriority = Integer.parseInt(st.nextToken().trim());
		_mNumOfEventsInPeriod = Integer.parseInt(st.nextToken().trim());
		_minPeriodSpacing = Integer.parseInt(st.nextToken().trim());
		_roomBookingRate = Integer.parseInt(st.nextToken().trim());
	}
	
//	public void setMStudConfBetweenTwoEvents(int i){
//		_mStudConfBetweenTwoEvents = i;
//	}
		
	public int getMStudConfBetweenTwoEvents() {
		return _mStudConfBetweenTwoEvents;
	}
	
//	public void setMInstConfBetweenTwoEvents(int i){
//		_mInstConfBetweenTwoEvents = i;
//	}
		
	public int getMInstConfBetweenTwoEvents() {
		return _mInstConfBetweenTwoEvents;
	}
	
//	public void setMRoomConfBetweenTwoEvents(int i){
//		_mRoomConfBetweenTwoEvents = i;
//	}
		
	public int getMRoomConfBetweenTwoEvents() {
		return _mRoomConfBetweenTwoEvents;
	}
	

//	public void setMAllowedPriority(int i){
//		_mAllowedPriority = i;
//	}

	public int getMAllowedPriority() {
		return _mAllowedPriority;
	}	

//	public void setMNumOfEventsInPeriod(int i){
//	_mNumOfEventsInPeriod = i;
//}
	
	public int getMNumOfEventsInPeriod() {
		return _mNumOfEventsInPeriod;
	}

	
//	public void setMinPeriodSpacing(int i){
//		_minPeriodSpacing = i;
//	}
		
	public int getMinPeriodSpacing() {
		return _minPeriodSpacing;
	}
	
	
//	public void setRoomBookingRate(int i){
//		_roomBookingRate = i;
//	}
		
	public int getRoomBookingRate() {
		return _roomBookingRate;
	}
	
	public String toString() {
		StringBuffer strB = new StringBuffer("conflictLimits" + ";");
		strB.append( _mStudConfBetweenTwoEvents + ";");
		strB.append( _mInstConfBetweenTwoEvents + ";");
		strB.append( _mRoomConfBetweenTwoEvents + ";");
		strB.append( _mAllowedPriority + ";");
		strB.append( _mNumOfEventsInPeriod + ";");
		strB.append( _minPeriodSpacing + ";");
		strB.append( _roomBookingRate + ";");
		
		return strB.toString();
	}
}
