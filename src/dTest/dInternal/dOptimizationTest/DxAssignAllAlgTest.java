/**
 * Created on June 16, 2006
 * 
 * 
 * Title: DxAssignAllAlgTest.java 
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
package dTest.dInternal.dOptimizationTest;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
import dInternal.DxConflictLimits;
import dInternal.dOptimization.DxAssignAllAlg;


/**
 * Ruben Gonzalez-Rubio
 * 
 * Description: DxAssignAllAlgTest is a class used to:
 * <p>
 * test the assignment of events to the tt
 * <p>
 * 
 */
public class DxAssignAllAlgTest extends TestCase {

//	StringBuffer fileName = new StringBuffer("." + File.separator);
//	fileName.append("dataTest" + File.separator);
//	fileName.append("refFiles" + File.separator);
//	fileName.append("facs" + File.separator);
//	fileName.append("sciBase" + File.separator);
//	fileName.append("scNoAssigned.dia");
	private final String _pathForFiles = "." + File.separator + "dataTest"
	+ File.separator + "refFiles" + File.separator + "facs" + File.separator + "sciBase" + File.separator;
	
	private final String _pathForOutputFiles = "." + File.separator + "forOutputTests"
	+ File.separator;
	/**
	 * 
	 * 
	 */
	public static Test suite() {
		// the type safe way is in SimpleTest
		// the dynamic way :
		return new TestSuite(DxAssignAllAlgTest.class);
	} // end suite

	/**
	 * 
	 * 
	 */
	public void test_buildScNoAssigned() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);
		DxDocument dxDocument1 = new DxTTableDoc();
	

		try {
			DModel dm1 = new DModel(dxDocument1, _pathForFiles + "scNoAssigned.dia");
			dm1.changeInDModel(new String("DxAssign"));

			assertEquals("test_buildScNoAssigned: activities size", 140, dm1
					.getSetOfActivities().size());
			assertEquals("test_buildScNoAssigned: events size", 275, dm1
					.getSetOfEvents().size());
			DxAssignAllAlg alg = new DxAssignAllAlg(dm1, dxCL);
			alg.doWork();
			assertEquals("test_buildScNoAssigned: number of Events", 254, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_buildScNoAssigned");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */
	public void test_buildGenNoAssigned() {
		DxConflictLimits dxCL = new DxConflictLimits();
		String str = "conflictLimits;0;0;0;0;30;0;100;";
		dxCL.readLimits(str);

		DxDocument dxDocument1 = new DxTTableDoc();

		try {
			DModel dm1 = new DModel(dxDocument1,_pathForFiles + "genNoAssigned.dia");
			dm1.changeInDModel(new Object());
			assertEquals("test_buildScNoAssigned: activities size", 140, dm1
					.getSetOfActivities().size());
			assertEquals("test_buildScNoAssigned: events size", 293, dm1
					.getSetOfEvents().size());
			DxAssignAllAlg alg = new DxAssignAllAlg(dm1, dxCL);
			alg.doWork();
			assertEquals("test_buildScNoAssigned: number of Events", 245, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e.toString());
			System.out.println("Exception in: test_buildGenNoAssigned");
			e.printStackTrace();
		}
	}

} // end DxAssignAllAlgTest
