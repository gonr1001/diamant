///**
// * Created on Jul 26, 2006
// * 
// * TODO To change the class description for this generated file
// * 
// * Project Dx
// * Title: DxReadActivitiesSites1dot5.java 
// * 
// *
// * Copyright (c) 2001 by rgr.
// * All rights reserved.
// *
// *
// * This software is the confidential and proprietary information
// * of rgr. ("Confidential Information").  You
// * shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement
// * you entered into with rgr.
// */
//
//package dInternal.dData.dActivities;
//
//import java.util.StringTokenizer;
//
//import dConstants.DConst;
//import dInternal.DResource;
//import dInternal.DataExchange;
//import dInternal.dData.ByteArrayMsg;
//import dInternal.dUtil.DXToolsMethods;
//
//public class DxReadActivitiesSites1dot5 implements DxActivitiesSitesReader {
//
//    private DataExchange _deActivities;
//
//    private int _nPeriodLength;
//
//    final static public int _COURSENAMELENGTH = 6;
//
//    public DxReadActivitiesSites1dot5(DataExchange de, int nPeriodLength) {
//        _deActivities = de;
//        _nPeriodLength = nPeriodLength;
//    }
//
//    public DxSetOfActivitiesSites readSetOfActivitiesSites() throws Exception {
//
//        String token;
//        // String sousString; //auxiliar String for stocking a substring of a
//        // line
//        StringTokenizer st = new StringTokenizer(_deActivities.getContents(),
//                "\r\n");
//        StringTokenizer stLine = null; // auxiliar StringTokenizer for reading
//        // subStrings in a line
//
//        // Starting position of the finite state machine
//        int nPosition = 0;
//        int line = 1;
//        int numberOfUnity = 0;
//        int counter = 0;
//        DxSetOfActivitiesSites dxsoasSites = new DxSetOfActivitiesSites();
//        DxActivitySite dxasCurrentActSite;
//
//        String activityName = "";
//        String instructorName = "";
//        DxSetOfActivitiesSites dxsoasAllSites = new DxSetOfActivitiesSites();
//        DxActivitySite dxasCurentSite = new DxActivitySite(DConst.ACTIVITY_STANDARD_SITE);
//        DxActivity dxaActivity = null;
//        DxType dxtType = null;
//        DxSection dxsSection = null;
//        DxUnity dxuUnity = null;
//        DxAssignement dxassAssign = null;
//        
//        while (st.hasMoreElements()) {
//            token = st.nextToken();
//            line++;
//            switch (nPosition) {
//            case 0:// activity name
//                activityName = token.substring(0, _COURSENAMELENGTH);
//
//                nPosition = 1;
//                break;
//            case 1:// activity visibility
//                activityResource = this.getResource(activityName.substring(0,
//                        _COURSENAMELENGTH));
//                if (activityResource == null)
//                    activityResource = new DResource(activityName.substring(0,
//                            _COURSENAMELENGTH), new Activity());
//                activity = (Activity) activityResource.getAttach();
//                // Resource nature;
//                if (Integer.parseInt(token.trim()) == 1)
//                    activity.setActivityVisibility(true);
//                else
//                    activity.setActivityVisibility(false);
//                activity.addType(activityName.substring(_COURSENAMELENGTH,
//                        _COURSENAMELENGTH + 1));
//                // nature =
//                // activity.getNature(activityName.substring(_COURSENAMELENGTH,_COURSENAMELENGTH+1));
//                nPosition = 2;
//                break;
//            case 2:// number of activities
//                nPosition = 3;
//                break;
//            case 3:// teachers' names
//                instructorName = token;
//                nPosition = 4;
//                line += 2;
//                break;
//            case 4:// number of blocs
//                typeResource = activity.getType(activityName.substring(
//                        _COURSENAMELENGTH, _COURSENAMELENGTH + 1));
//                section = new Section();
//                numberOfUnity = Integer.parseInt(token.trim());
//                for (int i = 1; i <= numberOfUnity; i++)
//                    section.addUnity(Integer.toString(i));
//                ((Type) typeResource.getAttach()).addSection(DXToolsMethods
//                        .getToken(activityName, " ", 1), section);
//                nPosition = 5;
//                break;
//            case 5:// duration of blocs
//                stLine = new StringTokenizer(token);
//                counter = 1;
//                while (stLine.hasMoreElements()) {
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    Unity unity = (Unity) unityResource.getAttach();
//                    unity.setDuration(Integer.parseInt(stLine.nextToken()
//                            .trim())
//                            * _nPeriodLength)/* RGRRGRRGR was60 */;
//                    unityResource.setAttach(unity);
//                    section.setUnity(unityResource);
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//                nPosition = 6;
//                break;
//            case 6:// days and periods of blocs
//                stLine = new StringTokenizer(token);
//                counter = 1;
//                while (stLine.hasMoreElements()) {
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    Unity bloc = (Unity) unityResource.getAttach();
//                    Assignment cycleAss = new Assignment();
//                    int typeOfData = DXToolsMethods.countTokens(token, ".");
//                    if (typeOfData == 1) {
//                        String day = stLine.nextToken().trim();
//                        String period = stLine.nextToken().trim();
//                        int dayKey = Integer.parseInt(day);
//                        int[] time = DXToolsMethods.convertSTIPeriods(Integer
//                                .parseInt(period));
//                        cycleAss.setDateAndTime(dayKey, time[0], time[1]);
//                    } else {// else if(typeOfData==1)
//                        String period = stLine.nextToken().trim();
//                        cycleAss.setPeriodKey(period);
//                    }// end else if(typeOfData==1)
//                    // cycleAss.addInstructorName(DXToolsMethods.getToken(instructorName,";",counter-1));
//                    for (int i = 1; i <= _NUMBEROFCYCLE; i++)
//                        bloc.addAssignment(new DResource(Integer.toString(i),
//                                cycleAss));
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//                nPosition = 7;
//                break;
//            case 7:// fixed rooms
//                stLine = new StringTokenizer(token);
//                counter = 1;
//                while (stLine.hasMoreElements()) {
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    int fixed = Integer.parseInt(stLine.nextToken().trim());
//                    Unity bloc = (Unity) unityResource.getAttach();
//                    for (int i = 1; i <= _NUMBEROFCYCLE; i++)
//                        ((Assignment) bloc.getAssignment(Integer.toString(i))
//                                .getAttach()).setRoomState(fixed == 1);
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//                nPosition = 8;
//                break;
//            case 8:// Preferred rooms
//                stLine = new StringTokenizer(token);
//                counter = 1;
//                String inst = instructorName;
//                if (!_open) {
//                    for (int i = 1; i < stLine.countTokens(); i++)
//                        instructorName += ";" + inst;
//                }
//                StringTokenizer instLine = new StringTokenizer(instructorName,
//                        ";");
//                while (stLine.hasMoreElements()) {
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    Unity bloc = (Unity) unityResource.getAttach();
//                    String room = stLine.nextToken().trim();
//
//                    if (instLine.hasMoreElements())
//                        inst = instLine.nextToken().trim();
//                    for (int i = 1; i <= _NUMBEROFCYCLE; i++) {
//                        ((Assignment) bloc.getAssignment(Integer.toString(i))
//                                .getAttach()).setRoom(room);
//                        ((Assignment) bloc.getAssignment(Integer.toString(i))
//                                .getAttach()).addInstructorName(inst);
//                    }
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//                nPosition = 9;
//                break;
//            case 9:// type of rooms
//                stLine = new StringTokenizer(token);
//                counter = 1;
//                while (stLine.hasMoreElements()) {
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    Unity bloc = (Unity) unityResource.getAttach();
//                    String roomType = stLine.nextToken().trim();
//                    bloc.addPreferFunctionRoom(roomType);
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//                nPosition = 10;
//                break;
//            case 10:// idem
//                activity.setIdemLine(token.trim());
//                nPosition = 11;
//                break;
//            case 11:// activity is fixed or not
//                StringTokenizer visiToken = new StringTokenizer(new String(
//                        token), ";");
//                int nbTokens = visiToken.countTokens();
//                stLine = new StringTokenizer(visiToken.nextToken());
//                StringTokenizer assignLine = null;
//                if (nbTokens == 2)
//                    assignLine = new StringTokenizer(visiToken.nextToken());
//                counter = 1;
//                while (stLine.hasMoreElements()) {
//                    int fix = Integer.parseInt(stLine.nextToken().trim());
//                    unityResource = section.getUnity(Integer.toString(counter));
//                    ((Unity) unityResource.getAttach()).setPermanent(fix == 1);
//                    if ((nbTokens == 2) && (assignLine.hasMoreElements())) {
//                        int fix1 = Integer.parseInt(assignLine.nextToken()
//                                .trim());
//                        ((Unity) unityResource.getAttach())
//                                .setAssign(fix1 == 1);
//                    } else
//                        ((Unity) unityResource.getAttach()).setAssign(fix == 1);
//                    counter++;
//                }// end while(stLine.hasMoreElements())
//
//                nPosition = 0;
//                this.addResource(activityResource, 1);
//                break;
//
//            }// end switch (position)
//        }// end while (st.hasMoreElements())
//        return dxsoasSites;
//    }
//}
