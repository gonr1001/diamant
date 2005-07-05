/**
*
* Title: SetOfRooms $Revision: 1.31 $  $Date: 2005-07-05 12:04:28 $
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
* @version $Revision: 1.31 $
* @author  $Author: gonzrubi $
* @since JDK1.3
*/

package dInternal.dDataTxt;


import java.util.StringTokenizer;


import dConstants.DConst;
import dInternal.dUtil.DXToolsMethods;

public class SetOfRooms extends SetOfResources{
	
	private byte[] _dataloaded; 
	private String _error;
	private RoomsAttributesInterpretor _roomsAttributesInterpretor;


	/**
	 * 
	 * @param dataloaded an array of bytes which is the file contents
	 *        for rooms
	 *
	 */
	public SetOfRooms(byte[] dataloaded){ 
		super(3);
		_error ="";
		//_sorListeners= new Vector();
		_dataloaded = dataloaded;
		_roomsAttributesInterpretor = null;
	}

	public boolean analyseTokens(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
	    token = st.nextToken().toString().trim();
	    if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	return analyseTokens1_6(beginPosition);
	    } 
	    return analyseTokens1_5(beginPosition);
	      
	} //end analyseTokens
	
	
	
	public void buildSetOfResources(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
	    token = st.nextToken().toString().trim();
	    if (token.equalsIgnoreCase(DConst.FILE_VER_NAME1_6)) {
	    	buildSetOfResources1_6(beginPosition);
	    } else {
	    	buildSetOfResources1_5(beginPosition);
	    }
	      
	} //end analyseTokens
	/**
	 * setDataToLoad
	 * @param dataloaded the data contents is in an array of bytes
	 */
	public void setDataToLoad(byte[]  dataloaded){ 
		_dataloaded = dataloaded;
	}
	
	/**
	 * buildSetOfResources creates the data structure
	 * @param beginPosition indicates from where start to read
	 * 
	 */
	public void buildSetOfResourcesOld(int beginPosition){
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF);
		int state = 0;
		int position=beginPosition;
		String roomID="";
		
		while (st.hasMoreElements()){
			token = st.nextToken();
			state =0;
			StringTokenizer currentLine = new StringTokenizer(token,";" );
			switch (position){
			case 0:// faculty
				position = 1;
				break;
			case 1:// description
				position = 2;
				break;
			case 2:// description
				position = 3;
				break;
			case 3:
				RoomAttach room = new RoomAttach();
				int nbTokens= currentLine.countTokens();
				while (currentLine.hasMoreElements()){
					token = currentLine.nextToken();
					switch (state){
					case 0: roomID = token.trim();
					state = 1;
					break;
					case 1:
						room.setCapacity(new Integer(token.trim()).intValue());
						state=2;
						break;
					case 2:
						int funtion= Integer.parseInt(token.trim());
						//if (attr.getSetOfFunctions().getIndexOfResource(funtion)!=-1)
						room.setFunction(funtion);
						state=3;
						break;
					case 3:
						StringTokenizer caract= new StringTokenizer(token.trim(),",");
						while(caract.hasMoreTokens()){
							room.addCaracteristics(Integer.parseInt(caract.nextToken().trim()));
						}
						state = 4;
						break;
					case 4:
						room.setDescription(token.trim());
						
						if(nbTokens==6)
							state =5;
						else{
							room.setStandardAvailability();
							state =0;
						}
						break;
					case 5:
						StringTokenizer availToken = new StringTokenizer(token,",");
						int nbAvailT= availToken.countTokens();
						for(int i=0; i< nbAvailT; i++)
							room.addAvailability(availToken.nextToken());
						state =0;
						break;
					}// end switch (state)
				}// end while (currentLine.hasMoreElements())
				addResource(new Resource( roomID, room),1);
				break;
			}// end switch(position)
		}// end while (st.hasMoreElements())
	} //end builSetOfResources

	

	
	/**
	 * analyseTokens1_5 will analyse the tokens of files
	 *        without any header like Diamant1.6
	 * @param beginPosition indicates from where start to read in the
	 *        array
	 * 
	 * @return true <p>if no errors in _dataloaded </p>
	 *         false otherwise
	 */
	private boolean analyseTokens1_5(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
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

	private boolean analyseTokens1_6(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
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
	private void buildSetOfResources1_5(int beginPosition){
		
		String currentLine;
		String str="";
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
		int position = beginPosition+1;
		_error="";
		String roomID="";
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
				RoomAttach room = new RoomAttach();
				roomID = DXToolsMethods.getToken(currentLine, ";",0).trim();
				str = DXToolsMethods.getToken(currentLine, ";",1).trim();
				room.setCapacity(new Integer(str).intValue());
				str = DXToolsMethods.getToken(currentLine, ";",2).trim();
				room.setFunction(new Integer(str).intValue());
				str = DXToolsMethods.getToken(currentLine, ";",3).trim();
				buildSetOfCaracteristics(room, str);
				str = DXToolsMethods.getToken(currentLine, ";",4).trim();
				room.setDescription(str);
				int nbTokens = DXToolsMethods.countTokens(currentLine, ";");
				if(nbTokens == 5)
					room.setStandardAvailability();
				else {
					str = DXToolsMethods.getToken(currentLine, ";",5).trim();
					buildAvailability(room, str);
				}
				addResource(new Resource( roomID, room),1);
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
	private void buildSetOfResources1_6(int beginPosition){
		//SetOfResources roomsBySite;
		String currentLine;
		String str="";
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
		st.nextToken();
		int position = beginPosition + 1;
		_error="";
		String roomID="";
		//String siteID="";
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
				RoomAttach room = new RoomAttach();
				roomID = DXToolsMethods.getToken(currentLine, ";",0).trim();
				str = DXToolsMethods.getToken(currentLine, ";",1).trim();
				room.setCapacity(new Integer(str).intValue());
				str = DXToolsMethods.getToken(currentLine, ";",2).trim();
				room.setFunction(new Integer(str).intValue());
				str = DXToolsMethods.getToken(currentLine, ";",3).trim();
				buildSetOfCaracteristics(room, str);
				// Treat new zone structure
				//siteID 
				str = DXToolsMethods.getToken(currentLine, ";",4).trim();
				//room.setDescription(str);
				//
				str = DXToolsMethods.getToken(currentLine, ";",5).trim();
				room.setDescription(str);
				int nbTokens = DXToolsMethods.countTokens(currentLine, ";");
				if(nbTokens == 6)
					room.setStandardAvailability();
				else {
					str = DXToolsMethods.getToken(currentLine, ";",5).trim();
					buildAvailability(room, str);
				}
				addResource(new Resource( roomID, room),1);
				break;
			}// end switch(position)
		}// end while (st.hasMoreElements())
	} //end builSetOfResources
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
	  

	private void buildSetOfCaracteristics(RoomAttach roomAt, String str) {
		
		StringTokenizer caract= new StringTokenizer(str.trim(),",");
		while(caract.hasMoreTokens()){		
			roomAt.addCaracteristics(Integer.parseInt(caract.nextToken().trim()));
		}
	}
	
	private void buildAvailability(RoomAttach roomAt, String str) {
	StringTokenizer availToken = new StringTokenizer(str,",");
	int nbAvailT= availToken.countTokens();
	for(int i=0; i< nbAvailT; i++)
		roomAt.addAvailability(availToken.nextToken());
	
	}
	
  public String getError() {
    return _error;
  }

  /**
   *
   * @param component
   */
/* public void sendEvent(Component component) {
   SetOfRoomsEvent event = new SetOfRoomsEvent(this);
   for (int i=0; i< _sorListeners.size(); i++) {
     SetOfRoomsListener sorl = (SetOfRoomsListener) _sorListeners.elementAt(i);
     sorl.changeInSetOfRooms(event, component);
   }
  }*/

  /**
   *
   * @param dml
   */
 /*
  * 
   public synchronized void addSetOfRoomsListener(SetOfRoomsListener sorl) {
    //System.out.println("SetOfActivities listener addeed: ");//debug
    if (_sorListeners.contains(sorl)){
      return;
    }
    _sorListeners.addElement(sorl);
    //System.out.println("addSetOfRooms Listener ...");//debug
  }
*/
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
  
	/**
	 * analyseTokens
	 * @param beginPosition indicates from where start to read
	 * 
	 * @return true <p>if no errors in _dataloaded </p>
	 *         false otherwise
	 */
	public boolean analyseTokensOrg(int beginPosition) {
		String token;
		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
		int state=0;
		int position = beginPosition;
		int line=0;
		_error="";
	    while (st.hasMoreElements()){
	      state =0;
	      token = st.nextToken();
	      line++;
	      StringTokenizer currentLine = new StringTokenizer(token,";" );
	      switch (position){
	        case 0:// faculty
	            position = 1;
	            break;
	          case 1:// description
	            position = 2;
	            line++;
	            break;
	          case 2:// description
	            position = 3;
	            line++;
	            break;
	          case 3:
	            int nbTokens= currentLine.countTokens();
	            if(nbTokens< DConst.ROOM_NUM_TOKENS){
	                _error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
	                      "\n" + DConst.ROOM_TEXT6;
	                return false;
	              }
	              while (currentLine.hasMoreElements()){
	                token = currentLine.nextToken().trim();
	                if(token.length()==0) {
	                	_error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
	                      "\n" + DConst.ROOM_TEXT6;
	                	return false;
	                }
	                switch (state){
	                  case 0: if ((new StringTokenizer(token)).countTokens()==0){
	                    _error= DConst.ROOM_TEXT1+line+DConst.ROOM_TEXT5 +
	                            "\n" + DConst.ROOM_TEXT6;
	                    return false;
	                  }
	                state =1;
	                break;
	
	              case 1:
	                if(!isIntValue(token.trim(),DConst.ROOM_TEXT2+line))
	                  return false;
	                state=2;
	                break;
	              case 2:
	                if(!isIntValue(token.trim(),DConst.ROOM_TEXT3+line))
	                  return false;
	                state=3;
	                break;
	              case 3:
	                StringTokenizer caracteristics = new StringTokenizer(token,"," );
	                while(caracteristics.hasMoreElements()){
	                  String currentCaracteristic = caracteristics.nextToken();
	                  if(!isIntValue(currentCaracteristic.trim(), DConst.ROOM_TEXT4+line))
	                    return false;
	                }
	                state = 4;
	                break;
	              case 4:
	                if(nbTokens==6)
	                  state =5;
	                else
	                  state =0;
	                break;
	              case 5:
	                state =0;
	                break;
	              }// end switch (state)
	            }// end while(currentline)
	            break;
	      }// end switch(position)
	    }// end while (st.hasMoreElements())
	    return true;
	} //end analyseTokens

	
}