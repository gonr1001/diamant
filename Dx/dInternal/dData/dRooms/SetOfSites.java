/**
*
* Title: SetOfSites $Revision: 1.5 $  $Date: 2005-01-25 16:35:42 $
* Description: SetOfSites is a class used as a data structure container.
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
* @version $Revision: 1.5 $
* @author  $Author: syay1801 $
* @since JDK1.3
*/

package dInternal.dData.dRooms;

import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
//import dInternal.dDataTxt.Resource;
//import dInternal.dDataTxt.SetOfResources;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
//import dInternal.dDataTxt.Resource;
import dInternal.dData.ByteArrayMsg;
import dInternal.dUtil.DXToolsMethods;

public class SetOfSites extends DSetOfResources{
	
	private String _error;
	private Vector _soSitesListeners;
	private RoomsAttributesInterpretor _roomsAttributesInterpretor;


	/**
	 * 
	 * @param dataloaded an array of bytes which is the file contents
	 *        for rooms
	 *
	 */
	public SetOfSites(){ 
		super();
		_error ="";
		_soSitesListeners= new Vector();
		_roomsAttributesInterpretor = null;
	}

	public boolean analyseTokens(DataExchange de, int beginPosition) {

	    if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	return analyseTokens1_6(de.getContents().getBytes(), beginPosition);
	    } //else if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_7)) {
	    return analyseTokens1_5(de.getContents().getBytes(), beginPosition);
		//return false;
	      
	} //end analyseTokens
	
	
	
	public void buildSetOfResources(DataExchange de, int beginPosition) {
	    if (de.getHeader().equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	buildSetOfResources1_6(de.getContents().getBytes(), beginPosition);
	    } else {
	    	buildSetOfResources1_5(de.getContents().getBytes(), beginPosition);
	    }
	      
	} //end analyseTokens

	
	public String getError() {
		return _error;
	}

	  /**
	   *
	   * @param component
	   */
	 public void sendEvent(Component component) {
	   SetOfSitesEvent event = new SetOfSitesEvent(this);
	   for (int i=0; i< _soSitesListeners.size(); i++) {
	     SetOfSitesListener sorl = (SetOfSitesListener) _soSitesListeners.elementAt(i);
	     sorl.changeInSetOfRooms(event, component);
	   }
	  }

	  /**
	   *
	   * @param dml
	   */
	  public synchronized void addSetOfSitesListener(SetOfSitesListener soSitesl) {
	    if (_soSitesListeners.contains(soSitesl)){
	      return;
	    }
	    _soSitesListeners.addElement(soSitesl);
	  }

		/**
		 * 
		 * @param attr
		 */
		public void setAttributesInterpretor(RoomsAttributesInterpretor attr){
			_roomsAttributesInterpretor = attr;
		}
		
		/**
		 * 
		 * @return _roomsAttributesInterpretor
		 */
		public RoomsAttributesInterpretor getAttributesInterpretor(){
			return _roomsAttributesInterpretor;
		}
		
		

		/**
		 *This object (which is already a string!) is itself returned.
		 * @return the string itself
		 * */
		public  String toWrite(){
			String reslist="";
			Vector resourceList = getSetOfResources();
			if(resourceList.size()>0){
				SetOfCategories setOfCat;
				for (int i=0; i< resourceList.size()-1; i++){
					setOfCat = (SetOfCategories)((DResource)resourceList.get(i)).getAttach();
					reslist+= setOfCat.toWrite();
				}
				setOfCat = (SetOfCategories)((DResource)resourceList.get(resourceList.size()-1)).getAttach();
				reslist+= setOfCat.toWrite();
			}// end if(_resourceList.size()>0)
			return reslist;
			
		}
	/**
	 * analyseTokens1_5 will analyse the tokens of files
	 *        without any header like Diamant1.6
	 * @param beginPosition indicates from where start to read in the
	 *        array
	 * 
	 * @return true <p>if no errors in _dataloaded </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1_5(byte[]  dataloaded, int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
		int state;
		int position = beginPosition;
		position++;
		int line=0;
		_error="";
	    while (st.hasMoreElements()){
	      state =0;
	      token = st.nextToken();
	      line++;
	      StringTokenizer currentLine = new StringTokenizer(token,";" );
	      //position++;
	      if (!isErrorEmpty())
        	return false;
	      switch (position){
	      	case 1:// faculty
	      		position++;
	            break;
	      	case 2:// description
	            line++;
	            position++;
	            break;
	      	case 3:// description
	            line++;
	            position++;
	            break;
	      	case 4:
	      		int nbTokens= currentLine.countTokens();
	            analyseTokenCase4(currentLine, line);
	            while (currentLine.hasMoreElements()){
	                token = currentLine.nextToken().trim();
	                analyseTokenState0(token, line);
	                state++;
	                if (!isErrorEmpty())
	                	return false;
	                switch (state){
	                  case 1: analyseTokenState1(token, line);
	                  	break;
	                  case 2: isIntValue(token.trim(),DConst.ROOM_TEXT2+line);
	                  	break;
	                  case 3: isIntValue(token.trim(),DConst.ROOM_TEXT3+line);
	              		break;
	                  case 4: analyseTokenState4(token, line);
	                  	break;
	                  case 5:
	                  	if(nbTokens==6)
	                  		state = 6;
	                  	else
	                  		state =1;
	                  	break;
	                  case 6:
	                  	state =0;
	                  	break;	                
	              }// end switch (state)
	            }// end while(currentline)
	            break;	            
	      }// end switch(position)
	    }// end while (st.hasMoreElements())
	    return true;
	} //end analyseTokens1_5

	private boolean analyseTokens1_6(byte[]  dataloaded, int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
		st.nextToken();
		int state;
		int position = beginPosition;
		int line=1;
		_error="";
	    while (st.hasMoreElements()){
	      state =0;
	      token = st.nextToken();
	      line++;
	      StringTokenizer currentLine = new StringTokenizer(token,";" );
	      position++;
	      if (!isErrorEmpty())
        	return false;
	      switch (position){
	      	case 1:// faculty
	            break;
	      	case 2:// description
	            line++;
	            break;
	      	case 3:// description
	            line++;
	            break;
	      	case 4:
	      		int nbTokens= currentLine.countTokens();
	            analyseTokenCase4(currentLine, line);
	            while (currentLine.hasMoreElements()){
	                token = currentLine.nextToken().trim();
	                analyseTokenState0(token, line);
	                state++;
	                if (!isErrorEmpty())
	                	return false;
	                switch (state){
	                  case 1: analyseTokenState1(token, line);
	                  	break;
	                  case 2: isIntValue(token.trim(),DConst.ROOM_TEXT2+line);
	                  	break;
	                  case 3: isIntValue(token.trim(),DConst.ROOM_TEXT3+line);
	              		break;
	                  case 4: analyseTokenState4(token, line);
	                  	break;
	                  case 5:
	                  	if(nbTokens==6)
	                  		state = 6;
	                  	else
	                  		state =1;
	                  	break;
	                  case 6:
	                  	state =0;
	                  	break;	                
	              }// end switch (state)
	            }// end while(currentline)
	            break;	            
	      }// end switch(position)
	    }// end while (st.hasMoreElements())
	    return true;
	} //end analyseTokens1_6
	
	private void analyseTokenCase4(StringTokenizer st, int line){
	int nbTokens= st.countTokens();
    if(nbTokens< DConst.ROOM_NUM_TOKENS){
        _error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
              DConst.CR_LF + DConst.ROOM_TEXT6;
        
      }
	}
	
	private void analyseTokenState0(String str, int line) {
	if(str.length()==0) {
    	_error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
    	DConst.CR_LF + DConst.ROOM_TEXT6;
    	
    }
	}
	
	private void analyseTokenState1(String str, int line) {
		if ((new StringTokenizer(str)).countTokens()==0){
            _error= DConst.ROOM_TEXT1+line+DConst.ROOM_TEXT5 +
            DConst.CR_LF + DConst.ROOM_TEXT6;
		}
	}
	
	private void analyseTokenState4(String str, int line) {
	 StringTokenizer caracteristics = new StringTokenizer(str,"," );
     while(caracteristics.hasMoreElements()){
       String currentCaracteristic = caracteristics.nextToken();
       isIntValue(currentCaracteristic.trim(), DConst.ROOM_TEXT4+line);
     }
	}
     
	/**
	 * buildSetOfResources
	 * @param beginPosition indicates from where start to read
	 * 
	 */
	private void buildSetOfResources1_5(byte[]  dataloaded, int beginPosition){
		
		String currentLine;
		//String str="";
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
		int position = beginPosition+1;
		_error="";
		//String roomID="";
	    while (st.hasMoreElements()){
		      currentLine = st.nextToken(); // a line in the file
		      //StringTokenizer currentLine = new StringTokenizer(token,";" );		      
		      switch (position){
		      	case 1:// faculty
		      		position++;
		            break;
		      	case 2:// description
		      		position++;
		            break;
		      	case 3:// description
		      		position++;
		            break;
		      	case 4:			
				
		      		//String line1_5= DXToolsMethods.getToken(currentLine, ";",DConst.ROOM_SITE_TOKEN).trim();
		      		int nbToken=  DXToolsMethods.countTokens(currentLine, ";");
		      		String line1_6="";
		      		for (int i=0; i< nbToken; i++){
		      			if(i==DConst.ROOM_SITE_TOKEN)
		      				line1_6+=DConst.ROOM_STANDARD_SITE+";"+
							DConst.ROOM_STANDARD_CAT+";";
		      			line1_6+=DXToolsMethods.getToken(currentLine, ";",i)+";";
		      		}
		      		String site= DConst.ROOM_STANDARD_SITE;
		      		DResource siteRsc= getResource(site);
		      		if(siteRsc==null){
		      			siteRsc=new DResource(site,new SetOfCategories());
		      			addResource(siteRsc,1);
		      		}
		      		SetOfCategories setOfCat= ((SetOfCategories)siteRsc.getAttach());
		      		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, line1_6);
		      		setOfCat.buildSetOfResources(dEx,0);
				break;
			}// end switch(position)
		     
		}// end while (st.hasMoreElements())
	} //end builSetOfResources

	
	/**
	 * buildSetOfResources
	 * @param beginPosition indicates from where start to read
	 * 
	 * 
	 */
	private void buildSetOfResources1_6(byte[]  dataloaded, int beginPosition){
		String currentLine;
		StringTokenizer st = new StringTokenizer(new String (dataloaded), DConst.CR_LF );
		st.nextToken();
		int position = beginPosition + 1;
		_error="";
	    while (st.hasMoreElements()){
		      currentLine = st.nextToken(); // a line in the file
		      switch (position){
		      	case 1:// faculty
		      		position++;
		            break;
		      	case 2:// description
		      		position++;
		            break;		            
		      	case 3:// description
		      		position++;
		            break;
		      	case 4:	
		      		String site = DXToolsMethods.getToken(currentLine, ";",DConst.ROOM_SITE_TOKEN).trim();
		      		DResource siteRsc= getResource(site);
		      		if(siteRsc==null){
		      			siteRsc=new DResource(site,new SetOfCategories());
		      			addResource(siteRsc,1);
		      		}
		      		SetOfCategories setOfCat= ((SetOfCategories)siteRsc.getAttach());
		      		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, currentLine);
		      		setOfCat.buildSetOfResources(dEx,0);
				break;
			}// end switch(position)
		}// end while (st.hasMoreElements())
	} //end builSetOfResources


  /**
   * */
  private boolean isIntValue(String value, String message){
    try{
      (new Integer (value.trim())).intValue();
    }catch (NumberFormatException exc){
      _error = message +DConst.ROOM_TEXT5 +
      DConst.CR_LF + DConst.ROOM_TEXT6;
      return false;
    }
    return true;
  }
  private boolean isErrorEmpty() {
  	return _error.length()==0;
  }

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {

	return 0;
}
  


	
}