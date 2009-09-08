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

import ca.sixs.util.pref.ParametersPref;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInterface.DxDocument;
import dInterface.DxTTableDoc;
import dInternal.DModel;
//import dInternal.DxConflictLimits;
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

	private final String _pathForFiles = "." + File.separator + "dataTest"
			+ File.separator + "refFiles" + File.separator + "facs"
			+ File.separator + "tAlgorithmes" + File.separator;

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
		DxDocument dxDocument1 = new DxTTableDoc();
		ParametersPref pp = new ParametersPref();
		try {
			pp.savePrefBeforeTest();
			DModel dm1 = new DModel(dxDocument1, _pathForFiles
					+ "scNoAssigned.dia");
			dm1.changeInDModel(new String("DxAssign"));

			assertEquals("test_buildScNoAssigned: activities size", 140, dm1
					.getSetOfActivities().size());
			assertEquals("test_buildScNoAssigned: events size", 275, dm1
					.getSetOfEvents().size());
			DxAssignAllAlg alg = new DxAssignAllAlg(dm1);
			alg.doWork();
			assertEquals("test_buildScNoAssigned: number of Events", 254, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_buildScNoAssigned");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
		
	}

	/**
	 * 
	 * 
	 */
	public void test_buildGenNoAssigned() {

		DxDocument dxDocument1 = new DxTTableDoc();
		ParametersPref pp = new ParametersPref();

		try {
			pp.savePrefBeforeTest();
			DModel dm1 = new DModel(dxDocument1, _pathForFiles
					+ "genNoAssigned.dia");
			dm1.changeInDModel(new Object());
			assertEquals("test_buildScNoAssigned: activities size", 140, dm1
					.getSetOfActivities().size());
			assertEquals("test_buildScNoAssigned: events size", 293, dm1
					.getSetOfEvents().size());

			DxAssignAllAlg alg = new DxAssignAllAlg(dm1);
			alg.doWork();
			assertEquals("test_buildScNoAssigned: number of Events", 245, dm1
					.getSetOfEvents().getNumberOfEventToAssign());
		} catch (Exception e) {
			// Should not fail in tests, but if file not there gives a failure
			assertEquals("test_buildScNoAssigned: exception", "nullPointer", e
					.toString());
			System.out.println("Exception in: test_buildGenNoAssigned");
			e.printStackTrace();
		} finally {
			pp.restorePrefAfterTest();
		}
	}

} // end DxAssignAllAlgTest
