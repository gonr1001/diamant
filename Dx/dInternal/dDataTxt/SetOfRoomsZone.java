///**
//*
//* Title: SetOfRoomsZone $Revision: 1.6 $  $Date: 2006-06-12 13:12:28 $
//* Description: SetOfRoomsZone is a class used as a data structure container.
//*              It contains the rooms and their attributes.
//*
//*
//* Copyright (c) 2001 by rgr.
//* All rights reserved.
//*
//*
//* This software is the confidential and proprietary information
//* of rgr. ("Confidential Information").  You
//* shall not disclose such Confidential Information and shall use
//* it only in accordance with the terms of the license agreement
//* you entered into with rgr.
//*
//* @version $Revision: 1.6 $
//* @author  $Author: gonzrubi $
//* @since JDK1.3
//*/
//
//package dInternal.dDataTxt;
//
////import java.awt.Component;
//import java.util.StringTokenizer;
////import java.util.Vector;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.dData.dRooms.RoomAttach;
//
//public class SetOfRoomsZone extends DSetOfResources{
//
//	private byte[] _dataloaded; //_st;// rooms in text format
//	private String _error;
//	//private SetOfRooms _setOfRooms;
////	private Vector _sorListeners;
////	private RoomsAttributesInterpretor _roomsAttributesInterpretor;
//
//
//	*//**
//	 * 
//	 * @param dataloaded The file contents is in an array of bytes
//	 *
//	 *//*
//	public SetOfRoomsZone(byte[] dataloaded){ //, int nbDay, int nbPerDay) {
//		super();
//		//_setOfRooms = null;
// //   _error ="";
// //   _sorListeners= new Vector(1);
//		_dataloaded = dataloaded;
// //   _roomsAttributesInterpretor = null;
//  }
//	public boolean analyseTokens(int beginPosition) {
//		String token;
//		StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF );
//		int state=0;
//		int position = beginPosition;
//		int line=0;
//		_error="";
//	    while (st.hasMoreElements()){
//	      state =0;
//	      token = st.nextToken();
//	      line++;
//	      StringTokenizer currentLine = new StringTokenizer(token,";" );
//	      switch (position){
//	        case 0:// faculty
//	            position = 1;
//	            break;
//	          case 1:// description
//	            position = 2;
//	            line++;
//	            break;
//	          case 2:// description
//	            position = 3;
//	            line++;
//	            break;
//	          case 3:
//	            int nbTokens= currentLine.countTokens();
//	            if(nbTokens< DConst.ROOM_NUM_TOKENS){
//	                _error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
//	                      "\n" + DConst.ROOM_TEXT6;
//	                return false;
//	              }
//	              while (currentLine.hasMoreElements()){
//	                token = currentLine.nextToken().trim();
//	                if(token.length()==0) {
//	                	_error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
//	                      "\n" + DConst.ROOM_TEXT6;
//	                	return false;
//	                }
//	                switch (state){
//	                  case 0: if ((new StringTokenizer(token)).countTokens()==0){
//	                    _error= DConst.ROOM_TEXT1+line+DConst.ROOM_TEXT5 +
//	                            "\n" + DConst.ROOM_TEXT6;
//	                    return false;
//	                  }
//	                state =1;
//	                break;
//	
//	              case 1:
//	                if(!isIntValue(token.trim(),DConst.ROOM_TEXT2+line))
//	                  return false;
//	                state=2;
//	                break;
//	              case 2:
//	                if(!isIntValue(token.trim(),DConst.ROOM_TEXT3+line))
//	                  return false;
//	                state=3;
//	                break;
//	              case 3:
//	                StringTokenizer caracteristics = new StringTokenizer(token,"," );
//	                while(caracteristics.hasMoreElements()){
//	                  String currentCaracteristic = caracteristics.nextToken();
//	                  if(!isIntValue(currentCaracteristic.trim(), DConst.ROOM_TEXT4+line))
//	                    return false;
//	                }
//	                state = 4;
//	                break;
//	              case 4:
//	                if(nbTokens==6)
//	                  state =5;
//	                else
//	                  state =0;
//	                break;
//	              case 5:
//	              	StringBuffer sb = new StringBuffer(deltaZone(token));
//	              	if (sb.length()==0)
//	              		_error= sb.toString()+line+DConst.ROOM_TEXT5 +
//                        "\n" + DConst.ROOM_TEXT6;
//	                break;
//	              }// end switch (state)
//	            }// end while(currentline)
//	            break;
//	      }// end switch(position)
//	    }// end while (st.hasMoreElements())
//	    return true;
//	} //end analyseTokens
//
//	
//	public String deltaZone(String str) {
//		if (str.length()==0) 
//			return DConst.ROOM_TEXT8;
//	    return "";
//	} //end analyseTokens
//
//	
//	*//**
//	 * buildSetOfResources
//	 * @param beginPosition indicates from where start to read
//	 * 
//	 *//*
//	public void buildSetOfResources(int beginPosition){
//		String token;
//    StringTokenizer st = new StringTokenizer(new String (_dataloaded), DConst.CR_LF);
//    int state = 0;
//    int position=beginPosition;
//    String roomID="";
//    while (st.hasMoreElements()){
//      token = st.nextToken();
//      state =0;
//      StringTokenizer currentLine = new StringTokenizer(token,";" );
//      switch (position){
//        case 0:// faculty
//            position = 1;
//            break;
//          case 1:// description
//            position = 2;
//            break;
//          case 2:// description
//            position = 3;
//            break;
//          case 3:
//            RoomAttach room = new RoomAttach();
//            int nbTokens= currentLine.countTokens();
//            while (currentLine.hasMoreElements()){
//              token = currentLine.nextToken();
//              switch (state){
//                case 0: roomID = token.trim();
//                  state = 1;
//                  break;
//                case 1:
//                  room.setCapacity(new Integer(token.trim()).intValue());
//                  state=2;
//                  break;
//                case 2:
//                  int funtion= Integer.parseInt(token.trim());
//                  //if (attr.getSetOfFunctions().getIndexOfResource(funtion)!=-1)
//                    room.setFunction(funtion);
//                  state=3;
//                  break;
//                case 3:
//                  StringTokenizer caract= new StringTokenizer(token.trim(),",");
//                  while(caract.hasMoreTokens()){
//                    int caracteristic= attr.getSetOfCaracteristics().getIndexOfResource(
//                      Integer.parseInt(caract.nextToken().trim()));
//                    room.addCaracteristics(Integer.parseInt(caract.nextToken().trim()));
//                  }
//                  state = 4;
//                  break;
//                case 4:
//                  room.setDescription(token.trim());
//
//                  if(nbTokens==6)
//                    state =5;
//                  else{
//                    room.setStandardAvailability();
//                    state =0;
//                  }
//                  break;
//                case 5:
//                  StringTokenizer availToken = new StringTokenizer(token,",");
//                  int nbAvailT= availToken.countTokens();
//                  for(int i=0; i< nbAvailT; i++)
//                    room.addAvailability(availToken.nextToken());
//                  state =0;
//                  break;
//              }// end switch (state)
//            }// end while (currentLine.hasMoreElements())
//            addResource(new DResource( roomID, room),1);
//            break;
//      }// end switch(position)
//    }// end while (st.hasMoreElements())
//  } //end builSetOfResources
//
//	  public String getError() {
//	    return _error;
//	  }
//	  *//**
//	   * *//*
//	  private boolean isIntValue(String value, String message){
//	    try{
//	      (new Integer (value.trim())).intValue();
//	    }catch (NumberFormatException exc){
//	      _error = message +DConst.ROOM_TEXT5 +
//	      "\n" + DConst.ROOM_TEXT6;
//	      return false;
//	    }
//	    return true;
//	  }
//	@Override
//	public String toWrite() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public long getSelectedField() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//}