package dInternal.dData.dInstructors;

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

import dConstants.DConst;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.DataExchange;
import dInternal.dUtil.DXToolsMethods;

public class SetOfInstructors extends DSetOfResources{

  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.
  private String _error="";

 /**
  * constructor
  * INPUTS: byte[]  dataloaded (information from file in byte type),
  * int nbDay,
  * */
 public SetOfInstructors(int nbDay, int nbPerDay) {
   super();
   _numberOfLines = nbDay;
   _numberOfColumns = nbPerDay;

  }



  /**
   * methode analyse st, a stringtokenizer variable
   * INPUT:
   * OUTPUT: Vector
   */
  public boolean analyseTokens(DataExchange de, int beginPosition){
    String token;
    StringTokenizer st = new StringTokenizer(de.getContents(),"\r\n" );
    int state = beginPosition;
    int line=0;
    int stateDispo =1;
    int numberOfInstructors=0;
    int countInstructor=0;
    _error="";
    while (st.hasMoreElements()){
      token = st.nextToken();
      line++;
      switch (state){
        case 0:
          try{
            numberOfInstructors = (new Integer (token.trim())).intValue();
          } catch (NumberFormatException exc){
            _error = DConst.INST_TEXT1 +
            "\n" + DConst.INST_TEXT6;
            return false;
          }
          state=1;
          break;
        case 1:
          //if ((new StringTokenizer(token)).countTokens()==0){
          if (token.length()==0){
             _error = DConst.INST_TEXT2 +  line + DConst.INST_TEXT5 +
           "\n" + DConst.INST_TEXT6;
            return false;
          }
          state =2;
          stateDispo =1;
          countInstructor++;
          break;
        case 2:
          // traitement des colonnes
        //extract first part of the line that gives availability
        	String firstPart = DXToolsMethods.getToken(token,
        			DConst.AVAILABILITY_SEPARATOR,0);
          StringTokenizer tokenDispo = new StringTokenizer(firstPart);
          if(tokenDispo.countTokens() != _numberOfColumns){
             _error = DConst.INST_TEXT3+line
           +DConst.INST_TEXT5 +
           "\n" + DConst.INST_TEXT6;
            return false;
          }
          // traitement de la description de la disponibilité
          while (tokenDispo.hasMoreElements()){
            String dispo = tokenDispo.nextToken();
            if ((!dispo.equalsIgnoreCase("1")) && (!dispo.equalsIgnoreCase("5"))
            		&& (!dispo.equalsIgnoreCase("2"))){
               _error = DConst.INST_TEXT4+line
                  +DConst.INST_TEXT5 +
                  "\n" + DConst.INST_TEXT6;
              return false;
            }
          }

          stateDispo++;
          if (stateDispo> _numberOfLines)
            state =1;
          break;
      }
    }
    if (countInstructor!=numberOfInstructors){
      _error = DConst.INST_TEXT1 +
            "\n" + DConst.INST_TEXT6;
      return false;
    }
    return true;
  }
  /**
   *build instructors list.
   *use StringTokenizer st: instructors in text format
   *
   */
  public void buildSetOfResources(DataExchange de, int beginPosition){
   	StringTokenizer st = new StringTokenizer(de.getContents(), DConst.CR_LF );
  	String token;
    Vector avail= new Vector(1);
    Vector siteAvail = new Vector(1);
    String instID="";
    int state = beginPosition;
    int stateDispo =1;
    //Resource resc;
    //Instructor instruc;
    while (st.hasMoreElements()){

      token = st.nextToken();
      switch (state){ // XXXX Pascal: Pas clair dutout...
        case 0:
          //number of instructors in the file
           state=1;
          break;
        case 1:
          // instructor name
          instID= token;
          avail= new Vector(1);
          siteAvail = new Vector(1);
          state =2;
          stateDispo =1;
          break;
        case 2:
          // instructor availabilities
        	 //extract first part of the line that gives availability
        	String firstPart = DXToolsMethods.getToken(token,
        			DConst.AVAILABILITY_SEPARATOR,0);
        	 //extract second part of the line that gives the used state
        	//in a site
        	String secondPart = DXToolsMethods.getToken(token,
        			DConst.AVAILABILITY_SEPARATOR,1);
         avail.add(firstPart);
         if(secondPart.length()!=0)
         	siteAvail.add(secondPart);
         stateDispo++;
         if (stateDispo> _numberOfLines){
           InstructorAttach inst = new InstructorAttach();
           inst.setAvailability(avail);
           inst.setAssignAvailability(siteAvail);
           this.addResource(new DResource( instID, inst),1);
           state =1;
         }
         break;
      }
    }
  }

  public String getError() {
    return _error;
  }


/* (non-Javadoc)
 * @see dInternal.DSetOfResources#toWrite()
 */
public String toWrite() {
	    String reslist="";
	    if(getSetOfResources().size()>0){	      
	        for (int i=0; i< getSetOfResources().size()-1; i++)
	          reslist+= ((DResource)getSetOfResources().get(i)).toWrite(DConst.CR_LF)+DConst.CR_LF;
	        reslist+= ((DResource)getSetOfResources().get(getSetOfResources().size()-1)).toWrite(DConst.CR_LF);
	      }	   
	    return reslist;	  
}

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}

public void remAllAssignedToASite(String site){
	for (int i = 0; i < this.size(); i++) {
		((InstructorAttach)this.getResourceAt(i).getAttach())
		.remAllAssignedToASite(site);
	}// for (int i = 0; i < _instructorSiteAvailability.size(); i++)
}

}