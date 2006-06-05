/**
 *
 * Title: TTStructure 
 * Description: 
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
package dInternal.dTimeTable;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import dInternal.DSetOfResources;
import dInternal.DResource;
import dInternal.dData.StandardCollection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dConstants.DConst;

import eLib.exit.xml.input.XMLReader;
import eLib.exit.xml.input.XMLInputFile;
import eLib.exit.xml.output.XMLWriter;
import eLib.exit.xml.output.XMLOutputFile;

public class TTStructure {

    private DSetOfResources _setOfCycles;

    // private int _nbOfStCycles=2;
    // private int _nbOfStDays=7;
    // private int _currentCycleIndex = 1;
    // DXTimeTable tag
    static final String ITEM2 = "DXTimeTable";

    // subtag
    private final String[] ITEM2_subTag = { "TTcycle", "TTdays", "TTday",
            "TTsequences", "TTsequence", "TTperiods", "TTperiod" };

    private final String[] ITEM2_subConst = { "cycleID", "pLength", "dayRef",
            "sequenceID", "priority", "BeginTime", "EndTime", "periodID",
            "dayID" };

    public static final String[] _weekTable = { "Lu", "Ma", "Me", "Je", "Ve",
            "Sa", "Di" };

    public static final String[] _priorityTable = { "Normale", "Basse", "Nulle" };

    // private String _str;
    private String _error = "";

    // private String _errorXMLFileMessage = "XML file is corrupted";
    // private int _col;
    // private int _row;
    public static int NUMBEROFACTIVESDAYS = 5;// monday to friday

    private int _numberOfDays;

    private int _periodLenght; // XXXX Pascal: Non initialise

    private int _currentCycleIndex = 0;

    // private String _errorMessage = "XML file is corrupted";
    static final String _TAGITEM = "TTcycle";

    static final String _TAGITEM1 = "cycleID";

    static final String _TAGITEM2 = "pLength";

    static final String _TAGITEM3 = "TTdays";

    // private String[] _dayNames;

    public TTStructure() {
        _setOfCycles = new StandardCollection(); // XXXX Pascal: magic number
    }

    public int getNumberOfActiveDays() {
        return _numberOfDays; // //NUMBEROFACTIVESDAYS; //
    }

    public String[] getWeekTable() {
        return _weekTable;
    }

    // public String[] getDayNames() {
    // return _dayNames;
    // }

    public int getPeriodLenght() {
        return _periodLenght;
    }

    public DSetOfResources getSetOfCycles() {
        return _setOfCycles;
    }

    public String toWrite() {
        return "";
    }

    public String getError() {
        return _error;
    }

    /**
     * */
    public int getCurrentCycleIndex() {
        return _currentCycleIndex;
    }

    public Cycle getCurrentCycle() {
        return (Cycle) _setOfCycles.getResourceAt(_currentCycleIndex)
                .getAttach();
    }

    public DResource getCurrentCycleResource() {
        return _setOfCycles.getResourceAt(_currentCycleIndex);
    }

    public void setCurrentCycleIndex(int curCyc) {
        _currentCycleIndex = curCyc;// _currentCycleIndex = curCyc;
    }

    /**
     * Create and save a standard TimeTable
     * 
     * @param String
     *            the timetable file name
     * @param int
     *            the number of cycles
     * @param int
     *            the number of days in each cycle
     * @return boolean the result of the operation
     */
    public boolean createDefaultTT(String fileName, int nbOfCycles, int nbOfDays) {
        XMLWriter wr;
        try {
            wr = new XMLWriter();
            Document doc = wr.getNewDocument();
            Element eltTT = wr.createElement(doc, ITEM2);
            Element eltCycle;
            Element eltDays;
            Element eltDay;
            Element eltSeqs;
            Element eltSeq;
            for (int cyc = 0; cyc < nbOfCycles; cyc++) {
                eltCycle = wr.createElement(doc, ITEM2_subTag[0]); // XXXX
                // Pascal:
                // Magic
                // numbers
                eltDays = wr.createElement(doc, ITEM2_subTag[1]);
                for (int day = 0; day < nbOfDays; day++) {
                    eltDay = wr.createElement(doc, ITEM2_subTag[2]);
                    eltSeqs = wr.createElement(doc, ITEM2_subTag[3]);

                    // add AM periods
                    int[] beginT = { 8, 15 }; // XXXX Pascal: Magic numbers
                    eltSeq = CreateSeqPeriods(doc, "AM", 4, 60, beginT, 0);
                    eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);
                    // add PM periods
                    beginT[0] = 13;
                    beginT[1] = 30;
                    eltSeq = CreateSeqPeriods(doc, "PM", 5, 60, beginT, 0);
                    eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);
                    // add Evening periods
                    beginT[0] = 19;
                    beginT[1] = 00;
                    eltSeq = CreateSeqPeriods(doc, "EM", 3, 60, beginT, 1);
                    eltSeqs = wr.appendChildInElement(eltSeqs, eltSeq);

                    // add sequences in a day
                    eltDay = wr.appendChildInElement(eltDay, eltSeqs);
                    Element childDay = wr.createElement(doc, ITEM2_subConst[2],
                            Integer.toString(day + 1));
                    String dayID = _weekTable[day % NUMBEROFACTIVESDAYS];
                    Element childDayID = wr.createElement(doc,
                            ITEM2_subConst[8], dayID);
                    eltDay = wr.appendChildInElement(eltDay, childDay);
                    eltDay = wr.appendChildInElement(eltDay, childDayID);
                    // eltDays= wr.appendChildInElement(eltDays, childDay);
                    eltDays = wr.appendChildInElement(eltDays, eltDay);
                }// end for (day)
                Element childCycle = wr.createElement(doc, ITEM2_subConst[0],
                        Integer.toString(cyc + 1));
                eltCycle = wr.appendChildInElement(eltCycle, childCycle);
                childCycle = wr.createElement(doc, ITEM2_subConst[1], Integer
                        .toString(60));
                eltCycle = wr.appendChildInElement(eltCycle, childCycle);
                eltCycle = wr.appendChildInElement(eltCycle, eltDays);
                eltTT = wr.appendChildInElement(eltTT, eltCycle);
            }// for (int cyc=0; cyc<3; cyc++)

            // create document and write in the file
            doc = wr.buildDocument(doc, eltTT);
            XMLOutputFile xmlOF = new XMLOutputFile();
            xmlOF.write(doc, fileName);
            return true;
        } catch (Exception e) {
            System.out.println("TTStructure: " + e);// debug
            return false;
        }
    }// end of CreateStandardTT method

    /**
     * it load the time table structure
     * 
     * @param String
     *            the xml file containing the timetable structure
     * @return String the error message, empty if it does not found error
     */

    public String loadTTStructure(int i, String fileName) {
        XMLInputFile xmlFile;
        Element root; // , item, ID;
        if (preLoad(fileName)) {
            try {
                xmlFile = new XMLInputFile();
                Document doc = xmlFile.createDocument(fileName);
                XMLReader list = new XMLReader();
                root = list.getRootElement(doc);
                if (readXMLtag(root).length() != 0) {
                    _error = DConst.ERROR_XML_FILE;
                    return _error;
                }
            } catch (Exception e) {
                System.out.println("TTStructure 1 :" + e);
                _error = e.toString();
                return e.toString();
            }
        } else {
            _error = DConst.M_FILE + " " + fileName + " Inexistant";
        }
        return _error;
    }

    /**
     * it load the time table structure
     * 
     * @param String
     *            the xml file containing the timetable structure
     * @return String the error message, empty if it does not found error
     */

    public String loadTTStructure(String str) {
        Element root;
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(str);
            XMLReader list = new XMLReader();
            root = list.getRootElement(document);
            if (readXMLtag(root).length() != 0) {
                _error = DConst.ERROR_XML_FILE;
                //return _error;
            }
        } catch (Exception e) {
            System.out.println("TTStructure 1 :" + e);
            _error = e.toString();
            //return e.toString();
        }
        return _error;
        // return null; Cannot return null, causes error because caller verifies
        // error length to determine if function caused an error. Could also
        // modify callers to verify if return is null instead
    }

    /**
     * it set the time table structure
     * 
     * @param Document
     *            doc the document containing the timetable structure
     * @return String the error message, empty if it does not found error
     */
    public String setTTStructureDocument(Document doc) {
        // ReadXMLFile xmlFile;
        Element root; // , item, ID;
        try {
            XMLReader list = new XMLReader();
            root = list.getRootElement(doc);
            if (readXMLtag(root).length() != 0) {
                _error = DConst.ERROR_XML_FILE;
                return _error;
            }
        } catch (Exception e) {
            System.out.println("TTStructure 2:" + e);
            _error = e.toString();
            return e.toString();
        }
        return _error;
    }

    /**
     * it save the time table structure
     * 
     * @param String
     *            the xml file where the timetable structure must be saved
     * @return String the error message, empty if it does not found error
     */
    public String saveTTStructure(String fileName) {
        XMLWriter wr;
        try {
            wr = new XMLWriter();
            Document doc = wr.getNewDocument();
            Element ttStruc = writeXMLtag(doc);
            // create document and write in the file
            doc = wr.buildDocument(doc, ttStruc);
            XMLOutputFile xmlOF = new XMLOutputFile();
            xmlOF.write(doc, fileName);
            return "";
        } catch (Exception e) {
            return e.toString();// debug
        }

    }

    /**
     * it get the time table structure
     * 
     * @return Document doc the document containing the timetable structure
     */
    public Document getTTStructureDocument() {
        XMLWriter wr;
        try {
            wr = new XMLWriter();
            Document doc = wr.getNewDocument();
            Element ttStruc = writeXMLtag(doc);
            // create document and write in the file
            doc = wr.buildDocument(doc, ttStruc);
            return doc;
        } catch (Exception e) {
            return null;// debug
        }

    }

    // public void modification() {
    // sendEvent();
    // System.out.println("Sending events");
    // }

    // public void sendEvent() {
    // TTStructureEvent event = new TTStructureEvent(this);
    // for (int i = 0; i < _ttsListeners.size(); i++) {
    // TTStructureListener ttsl = (TTStructureListener) _ttsListeners
    // .elementAt(i);
    // ttsl.changeInTTStructure(event);
    // //System.out.println("sendEvent: "+event.toString()+" --I:"+i);
    // System.out.println("TTstructure listener started: " + i);//debug
    // }
    // }

    /*
     * public synchronized void addTTStructureListener(TTStructureListener ttsl) {
     * if (_ttsListeners.contains(ttsl)){ return; }
     * _ttsListeners.addElement(ttsl); //System.out.println("addTTStructure
     * Listener ..."); }
     * 
     * public synchronized void removeTTStructureListener(TTStructureListener
     * ttsl) { _ttsListeners.removeElement(ttsl); }
     */
    /**
     * read a xml tag containing a set of cycle and build the resource
     * 
     * @param Element
     *            the root xml tag of the set of cycle
     */

    public String readXMLtag(Element setofCycles) {
        XMLReader list = new XMLReader();
        String ID = "";
        int size = list.getSize(setofCycles, _TAGITEM);
        if (size == 0) {
            _error = DConst.ERROR_XML_FILE;
            return _error;
        }
        // System.out.println(" Cycles Size: "+size);//debug
        for (int i = 0; i < size; i++) {
            Cycle setOfdays = new Cycle();
            Element cycle = list.getElement(setofCycles, _TAGITEM, i);
            ID = list.getElementValue(cycle, _TAGITEM1);
            _periodLenght = Integer.parseInt(list.getElementValue(cycle,
                    _TAGITEM2));
            // System.out.println(" Cycle ID: "+ID+" PeriodLenght:
            // "+_periodLenght);//debug
            Element days = list.getElement(cycle, _TAGITEM3, 0);

            if (!setOfdays.readXMLtag(days).equals("")) {
                _error = DConst.ERROR_XML_FILE;
                return _error;
            }
            _numberOfDays = setOfdays.getNumberOfDays();
            _setOfCycles.addResource(new DResource(ID, setOfdays), 0);
        }// end for (int i=0; i< size; i++)
        return _error;
    }

    /**
     * Contruct a xml element from the set of cycles
     * 
     * @param Document
     *            the root xml document
     * @Element the xml tag of the set of cycles
     */
    public Element writeXMLtag(Document doc) {
        XMLWriter xmlElt;
        try {
            xmlElt = new XMLWriter();
            Element eltCycles = xmlElt.createElement(doc, TTStructure.ITEM2);
            for (int i = 0; i < _setOfCycles.size(); i++) {
                Element eltCycle = xmlElt.createElement(doc, _TAGITEM);
                Element cycle = ((Cycle) _setOfCycles.getResourceAt(i)
                        .getAttach()).writeXMLtag(doc);
                Element cycleID = xmlElt.createElement(doc, _TAGITEM1,
                        _setOfCycles.getResourceAt(i).getID());
                Element cyclePLength = xmlElt.createElement(doc, _TAGITEM2,
                        Integer.toString(_periodLenght));
                eltCycle = xmlElt.appendChildInElement(eltCycle, cycle);
                eltCycle = xmlElt.appendChildInElement(eltCycle, cycleID);
                eltCycle = xmlElt.appendChildInElement(eltCycle, cyclePLength);
                eltCycles = xmlElt.appendChildInElement(eltCycles, eltCycle);
            }
            return eltCycles;
        } catch (Exception e) {
            System.out.println("SetOfCycle: " + e);// debug
            return null;
        }
    }

    /**
     * 
     * @param ID
     * @return
     */
    public int findIndexInWeekTable(long key) {
        DResource day = this.getCurrentCycle().getSetOfDays().getResource(key);
        if (day != null) {
            for (int i = 0; i < _weekTable.length; i++)
                if (day.getID().equalsIgnoreCase(_weekTable[i]))
                    return i;
        }
        return -1;
    }

    /**
     * 
     * @param str
     * @return
     */
    private boolean preLoad(String str) {
        File fil = new File(str);
        if (!fil.exists()) {
            System.out.println("fil.exists: " + fil.exists());
        }
        // fil.exists(); // XXXX Pascal: redondance? Cette methode est-elle
        // vraiment necessaire?
        return fil.exists();
    }

    /**
     * Create a sequence of periods
     * 
     * @param Document
     *            the xml document where we are working
     * @param String
     *            String the sequence ID (AM, PM or EM= evening)
     * @param int
     *            the number of periods in the sequence
     * @param int
     *            the lenght of each period in the sequence
     * @param int[2]
     *            the begin time of the period. the first element of the table
     *            is the our, and the second is the minutes
     * @param int
     *            the prioryti of each period
     * @return Element the sequence element
     */
    private Element CreateSeqPeriods(Document doc, String seqID,
            int nbOfPeriods, int periodLenght, int[] beginTime, int priority) {
        // add PM periods
        XMLWriter xmlElt;
        try {
            xmlElt = new XMLWriter();
            Element eltSeq = xmlElt.createElement(doc, ITEM2_subTag[4]);
            Element eltPers = xmlElt.createElement(doc, ITEM2_subTag[5]);
            int hour = beginTime[0];
            for (int i = 0; i < nbOfPeriods; i++) {
                int mn = (beginTime[1] + periodLenght * i) % 60;//
                hour = beginTime[0] + (beginTime[1] + periodLenght * i) / 60;
                String time = hour + ":" + mn;
                Element child0 = xmlElt.createElement(doc, ITEM2_subConst[5],
                        time);
                int mn1 = (mn + periodLenght) % 60;//
                int hour1 = hour + (mn + periodLenght) / 60;
                time = hour1 + ":" + mn1;
                Element child01 = xmlElt.createElement(doc, ITEM2_subConst[6],
                        time);
                Element child1 = xmlElt.createElement(doc, ITEM2_subConst[4],
                        Integer.toString(priority));
                Element child2 = xmlElt.createElement(doc, ITEM2_subConst[7],
                        Integer.toString(i + 1));//
                Element eltPer = xmlElt.createElement(doc, ITEM2_subTag[6]);
                eltPer = xmlElt.appendChildInElement(eltPer, child2);
                eltPer = xmlElt.appendChildInElement(eltPer, child0);
                eltPer = xmlElt.appendChildInElement(eltPer, child01);
                eltPer = xmlElt.appendChildInElement(eltPer, child1);
                eltPers = xmlElt.appendChildInElement(eltPers, eltPer);
            }
            Element childSeq = xmlElt.createElement(doc, ITEM2_subConst[3],
                    seqID);
            eltSeq = xmlElt.appendChildInElement(eltSeq, childSeq);
            eltSeq = xmlElt.appendChildInElement(eltSeq, eltPers);

            return eltSeq;
        } catch (Exception e) {
            System.out.println("TTStructure: " + e);// debug
            return null;
        }
    }

    /**
     * isEquals checks if this TTStructure is equals to the TTStructure gives in
     * arg
     * 
     * @param tts
     *            the TTStructure arg
     * @return
     *            <p>
     *            true if this TTStructure is equals to the TTStructure gives in
     *            arg
     *            </p>
     *            false otherwise
     */
    public boolean isEquals(TTStructure tts) {
        for (int i = 0; i < _setOfCycles.size(); i++) {
            DResource cycleR = _setOfCycles.getResourceAt(i);
            DResource cycleCloneR = tts.getSetOfCycles().getResourceAt(i);
            if (!cycleR.getID().equalsIgnoreCase(cycleCloneR.getID()))
                return false;
            if (!cycleR.getAttach().isEquals(cycleCloneR.getAttach()))
                return false;
        }
        return true;
    }

    /**
     * cloneCurrentTTSruct
     * 
     * @param dm
     * @return TTStructure containing the values of the TTStructure in dm
     */
    public TTStructure cloneCurrentTTS() {
        TTStructure ttStruct = new TTStructure();
        ttStruct.setTTStructureDocument(getTTStructureDocument());
        return ttStruct;
    }

    public String[] getDayNames() {
        Cycle cTemp = (Cycle) _setOfCycles.getResourceAt(_currentCycleIndex)
                .getAttach();
        String[] sReturn = new String[cTemp.getNumberOfDays()];
        for (int i = 0; i < cTemp.getNumberOfDays(); i++) {
            sReturn[i] = new String(cTemp.getSetOfDays().getResourceAt(i)
                    .getID());
        }
        return sReturn;
    }
}
