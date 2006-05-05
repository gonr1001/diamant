/**
 * Created on May 4, 2006
 * 
 * 
 * Title: DxAvailability.java 
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
package dInternal.dData;

import java.util.Vector;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAvailability is a class used to:
 * <p>
 * Holds availability data and provides easy way of accessing informations.
 * DxAvailability don't make extensive verifications since, at this point,
 * data should have already been verified and correct.
 * <p> 
 * 
 */
public class DxAvailability {

	Vector [] _vDays;
	int _nDaysQty;
	int[] _nPeriodPerDay;
	
	/**
     * Constructor
     * @param nDays Specify how many days of availability this object will represent
     * */
	public DxAvailability(int nDays)
	{
		_nDaysQty=nDays;
		_vDays=new Vector[_nDaysQty];
		_nPeriodPerDay=new int[_nDaysQty];
		for(int i=0;i<_nDaysQty;i++)
		{
			_vDays[i]=new Vector();
			_nPeriodPerDay[i]=0;
		}
	}

	/**
     * compare this resource with the specified resource
     * @param resource the specified resource
     * @return bolean true if this resource and the specified resource are equals
     * false if they are not equals
     * */
	public boolean setDayAvailability(int nDay, String sAvailabilities)
	{
		return true;
	}
	
	public boolean setPeriodAvailability(int nDayIndex, int nPeriodIndex, int nAvailability)
	{
		return true;
	}
	
	public boolean addPeriodAvailability(int nDayIndex, int nAvailabilityIndex)
	{
		return false;
	}
	

	public int[] getDayAvailability(int nDayIndex)
	{
		return null;
	}
	
	public int getPeriodAvailability(int nDayIndex, int nPeriodIndex)
	{
		return 0;
	}
	
	public int getPeriodCount(int nDayIndex)
	{
		return 0;
	}
	
	public int getDayCount()
	{
		return 0;
	}

}
