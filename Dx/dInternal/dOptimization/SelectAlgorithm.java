/**
 *
 * Title: SelectAlgorithm 
 * Description: SelectAlgorithm is a class used to
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

 * @since JDK1.3
 */

package dInternal.dOptimization;

import java.util.Vector;

import dInternal.DModel;

public class SelectAlgorithm {
	
	private DModel _dm;
	private Vector _algorithmToRun;
	private int _currentAlgoToExecute=0;
	private int _acceptableVariation=0;
	
	/**
	 * constructor
	 */
	public SelectAlgorithm(DModel dm, int contexte) {
		_dm = dm;
		_algorithmToRun = new Vector(1);
		_currentAlgoToExecute = contexte;
		buildAlgorithmToRun();
	}
	
	public SelectAlgorithm(int variation, DModel dm) {
		_dm= dm;
		_algorithmToRun= new Vector(1);
		_acceptableVariation= variation;
		_currentAlgoToExecute=4;
		buildAlgorithmToRun();
	}
	
	
	/**
	 * build Algorithm To Run
	 * XXXX Pascal: case 0,1,2,3..... 
	 */
	private void buildAlgorithmToRun(){
		_algorithmToRun.removeAllElements();
		switch(_currentAlgoToExecute){
		case 0: _algorithmToRun.add(new FirstAffectAlgorithm(_dm));
		break;
		case 1: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,0));
		break;
		case 2: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,1));//intermediaire
		break;
		case 3: _algorithmToRun.add(new StudentMixingAlgorithm(_dm,2));
		break;
		case 4: _algorithmToRun.add(new StudentMixingAlgorithm(_acceptableVariation,_dm));
		break;
		}// end  switch(choice)
	}
	
	/**
	 * execute algorithm
	 */
	public void execute(){
		if(_algorithmToRun.size() != 0){
			//_dm.getDDocument().getDMediator().getDApplication().setCursorWait();
			Algorithm algo = (Algorithm)_algorithmToRun.firstElement();
			algo.build( );
			//_dm.getDDocument().getDMediator().getDApplication().setCursorDefault();
		}
	}
	
	
	
}// end class