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

	public DxAvailability(int nDays)
	{
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
	
	public boolean setPeriodAvailability(int nDay, int nPeriod, int nAvailability)
	{
		return true;
	}
	
	public boolean addPeriodAvailability(int nDay, int nAvailability)
	{
		return false;
	}
	

	public int[] getDayAvailability(int nDay)
	{
		return null;
	}
	
	public int getPeriodAvailability(int nDay, int nPeriod)
	{
		return 0;
	}
	
	public int getPeriodCount(int nDay)
	{
		return 0;
	}
	
	public int getDayCount()
	{
		return 0;
	}

}
