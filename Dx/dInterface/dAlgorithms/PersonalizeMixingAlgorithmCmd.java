/**
 *
 * Title: PersonalizeMixingAlgorithmCmd $Revision: 1.5 $  $Date: 2005-02-01 21:27:15 $
 * Description: PersonalizeMixingAlgorithmCmd is a class used to
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
 * @version $Revision: 1.5 $
 * @author  $Author: syay1801 $
 * @since JDK1.3
 */
package dInterface.dAlgorithms;



import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.InformationDlg;


public class PersonalizeMixingAlgorithmCmd implements Command{
	int _selectedContext;// context for optimize mixing algorithm
	boolean _userTestActiv;
	
	/**
	 *
	 */
	public PersonalizeMixingAlgorithmCmd(boolean u) {
		_userTestActiv = u;
		 _selectedContext=3;// context for optimize mixing algorithm
	}
	
	/**
	 *
	 * @param dApplic
	 */
	public void execute(DApplication dApplic) {
		DConst.USER_TEST_ACTIV= _userTestActiv;
		//new PersonalizeMixingAlgorithmDlg();
		PersonalizeMixingAlgorithmDlg perso= new PersonalizeMixingAlgorithmDlg(DConst.DEFAULT_MIX_ALGO);
		String input= perso.showInputDialog();
		if(input!=null){
			int personalizeAcceptableVariation=Integer.parseInt(input);
			(new SelectAlgorithm(personalizeAcceptableVariation,dApplic.getDModel())).execute();
			new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
		}
	}
	
	
}