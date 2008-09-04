/**
 *
 * Title: ConstantsForParameters.java
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

/**
 * @author rgr
 *
 */
public interface ConstantsForParameters {
	
	public final String DLG_T_PARAMETERS = "Parameters";

	public final String MAX_STUDENT_CONFLICTS = "maxStudentConflicts";
	
	public final String MAX_INSTRUCTOR_CONFLICTS = "maxInstructorConflicts";
	
	public final String MAX_ROOM_CONFLICTS = "maxRoomConflicts";
	
	public final String PRIORITY_LEVEL = "priorityLevel";
	
	public final String MAX_EVENTS_IN_PERIOD = "maxEventsInPeriod";
	
	public final String MIN_GAP_BETWEEN_PERIODS = "putMinGapBetweenPeriods";
	
	public final String ROOM_BOOK_RATE = "roomBookingRate";
	
	public final int MAX_STUDENT_CONFLICTS_DEFAULT = 0;
	
	public final int MAX_INSTRUCTOR_CONFLICTS_DEFAULT = 0;
	
	public final int MAX_ROOM_CONFLICTS_DEFAULT = 0;
	
	public final int PRIORITY_LEVEL_DEFAULT = 0;
	
	public final int MAX_EVENTS_IN_PERIOD_DEFAULT = 30;
	
	public final int MIN_GAP_BETWEEN_PERIODS_DEFAULT = 0;
	
	public final int ROOM_BOOK_RATE_DEFAULT = 100;
	
	public final int LOW_LIMIT = 0;
	
	public final int HIGH_LIMIT = 99999;
	
	public final int HIGH_PRIORITY_LIMIT = 2;
	
	public final int HIGH_GAP_LIMIT = 4;
	
	public final int LOW_BOOK_RATE_LIMIT = 1;
	
	public final int HIGH_BOOK_RATE_LIMIT = 125;
	
	
	
}
