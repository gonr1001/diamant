/**
 *
 * Title: ParametersPrefTest.java
 *
 * Description: DApplication is a class used to display the application GUI,
 *              The class creates the main window, and menuBar, and toolBar,
 *              and the logger
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
 *
 */
package ca.sixs.util.pref;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author rgr
 *
 */
public class ParametersPrefTest extends TestCase implements
		ConstantsForParameters {

	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(ParametersPrefTest.class);
	} // end suite

	public void test_MaxStuConfictsBetweenTwoEvents() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getMaxStuConfictsBetweenTwoEvents();
		assertEquals("test_MaxStuConfictsBetweenTwoEvents  aux", aux, pp.getMaxStuConfictsBetweenTwoEvents());
		pp.putMaxStuConfictsBetweenTwoEvents(51);
		assertEquals("test_MaxStuConfictsBetweenTwoEvents 51", 51, pp.getMaxStuConfictsBetweenTwoEvents());
		pp.putMaxStuConfictsBetweenTwoEvents(aux);
		assertEquals("test_MaxStuConfictsBetweenTwoEvents aux", aux, pp.getMaxStuConfictsBetweenTwoEvents());
	}
	
	
	public void test_MaxInsConfictsBetweenTwoEvents() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getMaxInsConfictsBetweenTwoEvents();
		assertEquals("test_MaxInsConfictsBetweenTwoEvents  aux", aux, pp.getMaxInsConfictsBetweenTwoEvents());
		pp.putMaxInsConfictsBetweenTwoEvents(51);
		assertEquals("test_MaxInsConfictsBetweenTwoEvents 51", 51, pp.getMaxInsConfictsBetweenTwoEvents());
		pp.putMaxInsConfictsBetweenTwoEvents(aux);
		assertEquals("test_MaxInsConfictsBetweenTwoEvents aux", aux, pp.getMaxInsConfictsBetweenTwoEvents());
	}
	
	
	public void test_MaxRooConfictsBetweenTwoEvents() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getMaxRooConfictsBetweenTwoEvents();
		assertEquals("test_MaxRooConfictsBetweenTwoEvents  aux", aux, pp.getMaxRooConfictsBetweenTwoEvents());
		pp.putMaxRooConfictsBetweenTwoEvents(51);
		assertEquals("test_MaxRooConfictsBetweenTwoEvents 51", 51, pp.getMaxRooConfictsBetweenTwoEvents());
		pp.putMaxRooConfictsBetweenTwoEvents(aux);
		assertEquals("test_MaxRooConfictsBetweenTwoEvents aux", aux, pp.getMaxRooConfictsBetweenTwoEvents());
	}
	
	
	public void test_AllowedPriorityLevel() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getAllowedPriorityLevel();
		assertEquals("test_AllowedPriorityLevel  aux", aux, pp.getAllowedPriorityLevel());
		pp.putAllowedPriorityLevel(1);
		assertEquals("test_AllowedPriorityLevel 1", 1, pp.getAllowedPriorityLevel());
		pp.putAllowedPriorityLevel(aux);
		assertEquals("test_AllowedPriorityLevel aux", aux, pp.getAllowedPriorityLevel());
	}
	
	
	public void test_MaxEventsInPeriod() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getMaxEventsInPeriod();
		assertEquals("test_MaxEventsInPeriod  aux", aux, pp.getMaxEventsInPeriod());
		pp.putMaxEventsInPeriod(1);
		assertEquals("test_MaxEventsInPeriod 1", 1, pp.getMaxEventsInPeriod());
		pp.putMaxEventsInPeriod(aux);
		assertEquals("test_MaxEventsInPeriod aux", aux, pp.getMaxEventsInPeriod());
	}
	
	
	public void test_MinGapBetweenPeriods() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getMinGapBetweenPeriods();
		assertEquals("test_MinGapBetweenPeriods  aux", aux, pp.getMinGapBetweenPeriods());
		pp.putMinGapBetweenPeriods(1);
		assertEquals("test_MinGapBetweenPeriods 1", 1, pp.getMinGapBetweenPeriods());
		pp.putMinGapBetweenPeriods(aux);
		assertEquals("test_MinGapBetweenPeriods aux", aux, pp.getMinGapBetweenPeriods());
	}
	
	
	public void test_AllowedRoomBookingRate() {
		ParametersPref pp = new ParametersPref();
		int aux = pp.getAllowedRoomBookingRate();
		assertEquals("test_AllowedRoomBookingRate  aux", aux, pp.getAllowedRoomBookingRate());
		pp.putAllowedRoomBookingRate(1);
		assertEquals("test_AllowedRoomBookingRate 1", 1, pp.getAllowedRoomBookingRate());
		pp.putAllowedRoomBookingRate(aux);
		assertEquals("test_AllowedRoomBookingRate aux", aux, pp.getAllowedRoomBookingRate());
	}
	
	
	
	public void test_Limits() {
		ParametersPref pp = new ParametersPref();
		assertEquals("test_Limits out low", false, pp.testText("-1", LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits in low", true, pp.testText("0", LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits in ", true, pp.testText("123", LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits out high", true, pp.testText("99999", LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits out high", false, pp.testText("100000", LOW_LIMIT, HIGH_LIMIT));
		
		assertEquals("test_Limits String null", false, pp.testText(null, LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits String empty", false, pp.testText("", LOW_LIMIT, HIGH_LIMIT));
		assertEquals("test_Limits String is not a Integer", false, pp.testText("hello", LOW_LIMIT, HIGH_LIMIT));
		
	}
	
}
