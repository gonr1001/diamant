package dInternal;

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

public class InstructorsList {

private Vector instructorsList;// contains list of Instructor
private StringTokenizer st;// instructors in text format
private int numberOfLines;// represent number of days
private int numberOfColumns;// represent number of period a day.

  public InstructorsList( byte[]  dataloaded, int nbDay, int ndPerDay) {
    st = new StringTokenizer(new String (dataloaded),"\r\n" );
    numberOfLines = nbDay;
    numberOfColumns = ndPerDay;
  }
  /**
   * methode analyse st, a stringtokenizer variable
   * INPUT:
   * OUTPUT: Vector
   */
  public Vector analyseTokens(){
    String token;
    int state = 0;
    int line=0;
    int stateDispo =1;
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (state){
        case 0:
          try{
            (new Integer (token)).intValue();
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
          if(tokenDispo.countTokens() != numberOfColumns){
            new FatalProblemDlg(
           "Wrong number of instructor availabilities periods per day at line: "+line
           +" in the instructor file:" +
           "\n" + "I was in analyseInstructor class and in analyseTokens method ");
            System.exit(1);
          }
          // traitement de la description de la disponibilité
          while (tokenDispo.hasMoreElements()){
            String dispo = tokenDispo.nextToken();
            if ((!dispo.equalsIgnoreCase("1")) || (!dispo.equalsIgnoreCase("5"))){
              new FatalProblemDlg(
                  "Wrong descrition of instructor availability at line: "+line
                  +" in the instructor file:" +
                  "\n" + "I was in analyseInstructor class and in analyseTokens method ");
              System.exit(1);
            }
          }

          stateDispo++;
          if (stateDispo> numberOfLines)
            state =1;
          break;
      }
    }
    return null;
  }
  /**
   *build instructors list.
   *use StringTokenizer st: instructors in text format
   *
   */
  public void buildInstructorsList(){

  }

  /**
   *
   * */
  public void addInstructor(){

  }

  /**
   *
   * */
  public void removeInstructor(String instID){

  }

  /**
   *
   * */
  public Instructor getInstructor(int key){

    return new Instructor(0,"");
  }

  /**
   *
   * */
  public Instructor getInstructor(String instID){

    return new Instructor(0,"");
  }

  /**
   *
   * */
  public void sortInstructorList(){

  }

  /**
   *
   * */
  public String toString(){

    return "";
  }


}