package dInternal.dData;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;
import com.iLib.gDialog.FatalProblemDlg;

public class SetOfRooms extends SetOfResources{

  private byte[] _dataloaded; //_st;// rooms in text format
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.

 /***
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay (number of days), int nbPerDay (number of periods a day)
  * */
  public SetOfRooms(byte[] dataloaded, int nbDay, int nbPerDay) {
    super(nbDay, nbPerDay,3);
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
            while (currentLine.hasMoreElements()){
              token = currentLine.nextToken();
              switch (state){
                case 0: if ((new StringTokenizer(token)).countTokens()==0){
                  new FatalProblemDlg(
                      "Wrong name of room at line: "+line+" in the room file:" +
                      "\n" + "I was in roomList class and in analyseTokens method ");
                  System.exit(1);
                }
                state =1;
                break;

              case 1:
                isIntValue(token.trim(),"capacity at line "+line);
                state=2;
                break;
              case 2:
                isIntValue(token.trim(),"function at line "+line);
                state=3;
                break;
              case 3:
                StringTokenizer caracteristics = new StringTokenizer(token,"," );
                while(caracteristics.hasMoreElements()){
                  String currentCaracteristic = caracteristics.nextToken();
                  this.isIntValue(currentCaracteristic.trim(), "caracteristics at line "+line);
                }
                state = 4;
                break;
              case 4:
                state =0;
                break;
              }// end switch (state)
            }// end while(currentline)
            break;
      }// end switch(position)
    }// end while (st.hasMoreElements())
    return true;
  }

  /**
   *build rooms list.
   *use StringTokenizer st: rooms in text format
   *
   */
  public void buildSetOfRooms(int beginPosition){
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
            Room room = new Room();
            while (currentLine.hasMoreElements()){
              token = currentLine.nextToken();
              switch (state){
                case 0: roomID = token;
                  state = 1;
                  break;
                case 1:
                  room.setCapacity(new Integer(token.trim()).intValue());
                  state=2;
                  break;
                case 2:
                  room.setFunction(token.trim());
                  state=3;
                  break;
                case 3:
                  room.setCaracteristics(token.trim());
                  state = 4;
                  break;
                case 4:
                  room.setDescription(token.trim());
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
      new FatalProblemDlg("Wrong "+message +" in the room file:" +
      "\n" + "I was in roomList class and in analyseTokens method ");
      System.exit(1);
    }
    return true;
  }

}