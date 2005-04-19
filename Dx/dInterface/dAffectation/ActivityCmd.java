/**
*
* Title: ActivityCmd $Revision: 1.9 $  $Date: 2005-04-19 20:55:47 $
=======
* Description: ActivityCmd is a class used to
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
* @version $Revision: 1.9 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/
package dInterface.dAffectation;



import dConstants.DConst;
import dInterface.Command;
import dInterface.DApplication;

// XXXX Pascal: Pourquoi le constructeur des dialogues recoivent un DApplication 
//              en parametre?  N'y a-t-il pas qu'un seul DApplication qui soit 
//              instancie en tout temps?  
//              Si oui, il serait bcq plus elegant d'utiliser le Singleton sur 
//              DApplication


public class ActivityCmd implements Command{

  public void execute(DApplication dApplic) {
    new ActivityDlg(dApplic, DConst.ACT_LIST);
  }
}