package dInternal.dDataTxt;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.Component;
import dConstants.DConst;

public class SetOfRooms extends SetOfResources{

  private byte[] _dataloaded; //_st;// rooms in text format
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.
  //private RoomsAttributesInterpretor _attr;
  private String _error="";
  private Vector _sorListeners= new Vector(1);
  private RoomsAttributesInterpretor _roomsAttributesInterpretor;

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfRooms(byte[] dataloaded, int nbDay, int nbPerDay) {
    super(3);
    _dataloaded = dataloaded;
    _numberOfLines = nbDay;
   _numberOfColumns = nbPerDay;
  }

  /**
   *
   * @param dataloaded
   */
  public void setDataToLoad(byte[]  dataloaded, int nbDay, int nbPerDay){
    _dataloaded = dataloaded;
    _numberOfLines = nbDay;
    _numberOfColumns = nbPerDay;
  }

  /***
   * */
  public boolean analyseTokens(int beginPosition){
    String token;
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    int state=0;
    int position=beginPosition;
    int line=0;
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
            break;
          case 2:// description
            position = 3;
            break;
          case 3:
            int nbTokens= currentLine.countTokens();
            if(nbTokens<5){
                _error= DConst.ROOM_TEXT7+line+DConst.ROOM_TEXT5 +
                      "\n" + DConst.ROOM_TEXT6;
                return false;
              }
              while (currentLine.hasMoreElements()){
                token = currentLine.nextToken();
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
                //System.out.println("Dispo: "+token);
                state =0;
                break;
              }// end switch (state)
            }// end while(currentline)
            break;
      }// end switch(position)
    }// end while (st.hasMoreElements())
    return true;
  }

  public void setAttributesInterpretor(RoomsAttributesInterpretor attr){
    _roomsAttributesInterpretor= attr;
  }

  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfResources(int beginPosition){
    String token;
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
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
                    /*int caracteristic= attr.getSetOfCaracteristics().getIndexOfResource(
                      Integer.parseInt(caract.nextToken().trim()));*/
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
  }

  /**
   * */
  private boolean isIntValue(String value, String message){
    try{
      (new Integer (value.trim())).intValue();
    }catch (NumberFormatException exc){
      _error = message +DConst.ROOM_TEXT5 +
      "\n" + DConst.ROOM_TEXT6;
      return false;
    }
    return true;
  }

  public String getError() {
    return _error;
  }

  /**
   *
   * @param component
   */
 public void sendEvent(Component component) {
   SetOfRoomsEvent event = new SetOfRoomsEvent(this);
   for (int i=0; i< _sorListeners.size(); i++) {
     SetOfRoomsListener sorl = (SetOfRoomsListener) _sorListeners.elementAt(i);
     sorl.changeInSetOfRooms(event, component);
   }
  }

  /**
   *
   * @param dml
   */
  public synchronized void addSetOfRoomsListener(SetOfRoomsListener sorl) {
    //System.out.println("SetOfActivities listener addeed: ");//debug
    if (_sorListeners.contains(sorl)){
      return;
    }
    _sorListeners.addElement(sorl);
    //System.out.println("addSetOfRooms Listener ...");//debug
  }


}