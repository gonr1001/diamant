///**
//*
//* Title: Category $Revision: 1.8 $  $Date: 2007-10-23 18:19:51 $
//* Description: SetOfRooms is a class used as a data structure container.
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
//* @version $Revision: 1.8 $
//* @author  $Author: gonzrubi $
//* @since JDK1.3
//*/
//
//package dInternal.dData.dRooms;
//
//import java.util.StringTokenizer;
//import java.util.Vector;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DSetOfResources;
//import dInternal.DataExchange;
//import dInternal.dData.ByteArrayMsg;
//import dInternal.dUtil.DXToolsMethods;
//
//public class SetOfCategories extends DSetOfResources{
//	//private SetOfRooms _setOfRooms;
//	
//	public SetOfCategories (){
//		super();
//	}
//	
////	public boolean analyseTokens(DataExchange de, int beginPosition){
////  	beginPosition+=0;
////  	de.toString();
////  	//dataloaded[0]+=0;
////    return false;
////  }
//	
//	public  void buildSetOfResources(DataExchange de/*, int beginPosition*/){
//  	//beginPosition+=0;
//  	String cat = DXToolsMethods.getToken(de.getContents(), ";",DConst.ROOM_CATEGORY_TOKEN).trim();
//		DResource catRsc= getResource(cat);
//		if(catRsc==null){
//			catRsc=new DResource(cat,new SetOfRooms());
//			addResource(catRsc,1);
//		}
//		SetOfRooms setOfRooms= ((SetOfRooms)catRsc.getAttach());
//		DataExchange dEx= new ByteArrayMsg(DConst.FILE_VER_NAME1_6, de.getContents());
//		setOfRooms.buildSetOfResources(dEx/*,0*/);
//  	//dataloaded[0]+=0;
//  }
//	
//	public  String getError(){
//    return "";
//  }
//	
//	public String toWrite(String site){
//		String reslist="";
//		Vector resourceList = getSetOfResources();
//		if(resourceList.size()>0){
//			SetOfRooms setOfRooms;
//			DResource catRsc;
//			String roomTmp;
//			for (int i=0; i< resourceList.size()-1; i++){
//				catRsc=((DResource)resourceList.get(i));
//				setOfRooms = (SetOfRooms)catRsc.getAttach();
//				roomTmp= setOfRooms.toWrite();
//				reslist+= addCategoriesInOldFormat(roomTmp, site, catRsc.getID());
//			}
//			catRsc=((DResource)resourceList.get(resourceList.size()-1));
//			setOfRooms = (SetOfRooms)catRsc.getAttach();
//			//String roomsOLDFormat= setOfRooms.toWrite();
//			roomTmp= setOfRooms.toWrite();
//			reslist+= addCategoriesInOldFormat(roomTmp, site, catRsc.getID());
//			
//		}// end if(_resourceList.size()>0)
//		return reslist;
//	}
//
//	/**
//	 * 
//	 * @param str
//	 * @param site
//	 * @param cat
//	 * @return
//	 */
//	private String addCategoriesInOldFormat(String str, String site, String cat){
//		StringTokenizer roomsLines= new StringTokenizer(str, DConst.CR_LF);
//		StringBuffer newRoomsLines= new StringBuffer();
//		while (roomsLines.hasMoreTokens()){
//			//String line = roomsLines.nextToken();
//			StringTokenizer lineTokens= new StringTokenizer(roomsLines.nextToken(), ";");
//			int tokenPosition=0;
//			while(lineTokens.hasMoreTokens()){
//				newRoomsLines.append(lineTokens.nextToken()+";");
//				tokenPosition++;
//				if(tokenPosition==DConst.ROOM_SITE_TOKEN)
//					newRoomsLines.append(site+";"+cat+";");
//			}
//			newRoomsLines.append(DConst.CR_LF);
//		}
//		return newRoomsLines.toString();
//	}
//	
//	/* (non-Javadoc)
//	 * @see dInternal.DObject#getSelectedField()
//	 */
//	public long getSelectedField() {
//
//		return 0;
//	}
//
//	/* (non-Javadoc)
//	 * @see dInternal.DSetOfResources#toWrite()
//	 */
//	public String toWrite() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
