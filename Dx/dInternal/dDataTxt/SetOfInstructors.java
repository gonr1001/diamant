package dInternal;

/**
 * <p>Title: Proto</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;
import com.iLib.gDialog.FatalProblemDlg;

public class InstructorsList extends ResourceList{

//private Vector instructorsList;// contains list of Instructor
private byte[] _dataloaded; //_st;// instructors in text format
private int _numberOfLines;// represent number of days
private int _numberOfColumns;// represent number of period a day.

 /**
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay,
  * */
 public InstructorsList( byte[]  dataloaded, int nbDay, int nbPerDay) {
   super(dataloaded, nbDay, nbPerDay,2);
   _dataloaded = dataloaded;
   _numberOfLines = nbDay;
   _numberOfColumns = nbPerDay;
  }
  /**
   * methode analyse st, a stringtokenizer variable
   * INPUT:
   * OUTPUT: Vector
   */
  public boolean analyseTokens(){
    String token;
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    int state = 0;
    int line=0;
    int stateDispo =1;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (state){
        case 0:
          try{
            (new Integer (token.trim())).intValue();
          }catch (NumberFormatException exc){
            new FatalProblemDlg(
            "Wrong number of instructors in the instructor file:" +
            "\n" + "I was in analyseInstructor class and in analyseTokens method ");
            System.exit(1);
          }
          state=1;
          break;
        case 1:
          if ((new StringTokenizer(token)).countTokens()==0){
            new FatalProblemDlg(
           "Wrong name of instructor at line: "+line+" in the instructor file:" +
           "\n" + "I was in analyseInstructor class and in analyseTokens method ");
            System.exit(1);
          }
          state =2;
          stateDispo =1;
          break;
        case 2:
          // traitement des colonnes
          StringTokenizer tokenDispo = new StringTokenizer(token);
          if(tokenDispo.countTokens() != _numberOfColumns){
            new FatalProblemDlg(
           "Wrong number of instructor availabilities periods per day at line: "+line
           +" in the instructor file:" +
           "\n" + "I was in analyseInstructor class and in analyseTokens method ");
            System.exit(1);
          }
          // traitement de la description de la disponibilité
          while (tokenDispo.hasMoreElements()){
            String dispo = tokenDispo.nextToken();
            if ((!dispo.equalsIgnoreCase("1")) && (!dispo.equalsIgnoreCase("5"))){
              new FatalProblemDlg(
                  "Wrong descrition of instructor availability at line: "+line
                  +" in the instructor file:" +
                  "\n" + "I was in analyseInstructor class and in analyseTokens method ");
              System.exit(1);
            }
          }

          stateDispo++;
          if (stateDispo> _numberOfLines)
            state =1;
          break;
      }
    }
    return true;
  }
  /**
   *build instructors list.
   *use StringTokenizer st: instructors in text format
   *
   */
  public void buildInstructorsList(){
    StringTokenizer st = new StringTokenizer(new String (_dataloaded),"\r\n" );
    String token;
    Vector avail= new Vector();
    String instID="";
    int state = 0;
    int stateDispo =1;
    //Resource resc;
    //Instructor instruc;
    while (st.hasMoreElements()){

      token = st.nextToken();
      switch (state){
        case 0:
          //number of instructors in the file
           state=1;
          break;
        case 1:
          // instructor name
          instID= token;
          avail= new Vector();
          state =2;
          stateDispo =1;
          break;
        case 2:
          // instructor availabilities
         avail.add(token);
         stateDispo++;
         if (stateDispo> _numberOfLines){
           Instructor inst = new Instructor();
           inst.setInstructorDisp(avail);
           this.addResource(new Resource( instID, inst));
           state =1;
         }
         break;
      }
    }
  }

  /**
   * created a list of instructor
   * OUTPUT: Vector of String
   */
 // public Vector getNamesVector() {
 //   return new Vector();
 // }

}