/**
*
* Title: Category $Revision: 1.3 $  $Date: 2005-01-24 21:27:56 $
* Description: SetOfRooms is a class used as a data structure container.
*              It contains the rooms and their attributes.
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
* @version $Revision: 1.3 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dData.dRooms;

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dData.ByteArrayMsg;
import dInternal.dUtil.DXToolsMethods;

public class SetOfCategories extends DSetOfResources{
	//private SetOfRooms _setOfRooms;
	
	public SetOfCategories (){
		super();
	}
	
	public boolean analyseTokens(DataExchange de, int beginPosition){
  	beginPosition+=0;
  	de.toString();
  	//dataloaded[0]+=0;
    return false;
  }
	
	public  void buildSetOfResources(DataExchange de, int beginPosition){
  	beginPosition+=0;
  	String cat = DXToolsMethods.getToken(de.getContents(), ";",DConst.ROOM_CATEGORY_TOKEN).trim();
		DResource catRsc= getResource(cat);
		if(catRsc==null){
			catRsc=new DResource(cat,new SetOfRooms());
			addResource(catRsc,1);
		}
		SetOfRooms setOfRooms= ((SetOfRooms)catRsc.getAttach());
		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, de.getContents());
		setOfRooms.buildSetOfResources(dEx,0);
  	//dataloaded[0]+=0;
  }
	
	public  String getError(){
    return "";
  }
	
	public String toWrite(){
		String reslist="";
	    if(getSetOfResources().size()>0){	      
	        for (int i=0; i< getSetOfResources().size()-1; i++)
	          reslist+= ((DResource)getSetOfResources().get(i)).toWrite(DConst.CR_LF)+DConst.CR_LF;
	        reslist+= ((DResource)getSetOfResources().get(getSetOfResources().size()-1)).toWrite(DConst.CR_LF);
	      }	   
	    return reslist;	
	}

	/* (non-Javadoc)
	 * @see dInternal.DObject#getSelectedField()
	 */
	public long getSelectedField() {

		return 0;
	}
}
