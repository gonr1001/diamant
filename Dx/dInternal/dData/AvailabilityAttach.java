/*
 *  Title: AvailabilityAttach.java: Created on 2006-01-18
** Copyright (c) 2001 by rgr.
* All rights reserved.
*/
package dInternal.dData;

/**
 * <p>Title: Diamant 1.6</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author hara2602
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
import dInternal.DObject;

/**
 * @todo new name InstructorFields
 * @todo finish comments
 * @todo
 */

public class AvailabilityAttach extends DObject {
    /**
     * @associates String 
     */
    protected Vector _ressourceAvailability;
    protected Vector _ressourceSiteAvailability;

    private final String CR_LF = "\r\n";

    public AvailabilityAttach() {
        _ressourceAvailability = new Vector();
        _ressourceSiteAvailability = new Vector();
    }

    /**
     * add availability of instructor in the next day
     */
    public void addAvailability(String disp) {
        _ressourceAvailability.add(disp);
    }

    /**
     * Remove an availibility day
     * INPUT: day number. day =1 equals instructor Availability position = 0
     * */
    public boolean removeAvailability(int day) {
        if (day > 0)
            if (day <= _ressourceAvailability.size()) {
                _ressourceAvailability.remove(day - 1);
                return true;
            }
        return false;
    }

    /**
     * clear and set instructorDisp
     * INPUT: Vector of new instructor availability (instDisp)
     * */
    public void setAvailability(Vector instDisp) {
        _ressourceAvailability = new Vector();
        _ressourceAvailability = (Vector) instDisp.clone();
    }
    
    /**
     * clear and set instructorDisp
     * INPUT: Vector of new instructor availability (instDisp)
     * */
    public void setAssignAvailability(Vector instAssign) {
        _ressourceSiteAvailability = new Vector();
        _ressourceSiteAvailability = (Vector) instAssign.clone();
    }

    /**
     *
     * */
    public Vector getVectorAvailability() {
        return _ressourceAvailability;
    }

    public int[][] getMatrixAvailability() {
        return getMatrixAvailabilityTable(_ressourceAvailability);
    }
    
    public String[][] getMatrixAssignAvailability() {
        return getAssignAvailabilityTable(_ressourceSiteAvailability);
    }

    /**
     * 
     * @return
     */
    private int[][] getMatrixAvailabilityTable(Vector vect) {
        //String jour = (String) _ressourceAvailability.get(0);
        String jour = (String) vect.get(0);
        StringTokenizer st = new StringTokenizer(jour);
        int[][] a = new int[_ressourceAvailability.size()][st.countTokens()];
        int nbOfTokens = st.countTokens();
        for (int i = 0; i < _ressourceAvailability.size(); i++) {
            jour = (String) _ressourceAvailability.get(i);
            st = new StringTokenizer(jour);
            nbOfTokens = st.countTokens();
            for (int j = 0; j < nbOfTokens; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            } // end for j
        } //end for i
        return a;
    }
    
    /**
     * 
     * @return
     */
    private String[][] getAssignAvailabilityTable(Vector vect) {
        //String jour = (String) _ressourceAvailability.get(0);
        String jour = (String) vect.get(0);
        StringTokenizer st = new StringTokenizer(jour);
        String[][] a = new String[vect.size()][st.countTokens()];
        int nbOfTokens = st.countTokens();
        for (int i = 0; i < vect.size(); i++) {
            jour = (String) vect.get(i);
            st = new StringTokenizer(jour);
            nbOfTokens = st.countTokens();
            for (int j = 0; j < nbOfTokens; j++) {
                a[i][j] = st.nextToken();
            } // end for j
        } //end for i
        return a;
    }

    /**
     *
     * @return
     */
    

    /**
     * 
     */
    public void setAvailability(int[][] a) {
        _ressourceAvailability = new Vector();
        String str = "";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length - 1; j++) {
                str += a[i][j] + " ";
            } // end for j
            str += a[i][a[i].length - 1];
            _ressourceAvailability.add(str);
            str = "";
        } //end for i
    }
    
    private void initAssignAvailability() {
        int[][] a =  this.getMatrixAvailabilityTable(_ressourceAvailability);
        _ressourceSiteAvailability = new Vector(1);
        String site="";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length - 1; j++) {
                site += DConst.AVAILABILITY_ASSIGN_DEFAULT+" ";
            } // end for j
            site += DConst.AVAILABILITY_ASSIGN_DEFAULT;
            _ressourceSiteAvailability.add(site);
            site="";
        } //end for i
    }
    
    public void setAssignAvailability(String[][] a) {
        //_ressourceAvailability = new Vector(1);
        _ressourceSiteAvailability = new Vector(1);
        String str = "";
        //String site="";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length - 1; j++) {
                str += a[i][j] + " ";
                
            } // end for j
            str += a[i][a[i].length - 1];
            _ressourceSiteAvailability.add(str);
            str = "";
        } //end for i
    }

    public void setFullAvailability() {
        String str = "";
        for (int i = 0; i < _ressourceAvailability.size(); i++) {
            str = _ressourceAvailability.get(i).toString();
            str = str.replaceAll("5", "1");
            _ressourceAvailability.setElementAt(str, i);
        }
    }

    /**
     * return the value of the selected key
     * INPUT: choice, an integer. choice =
     * OUTPUT: an integer. it return -1 if choice is invalid
     * */
    public long getSelectedField(int choice) {
        switch (choice) {
        case 0:
            break;
        }
        return -1;
    }

    /**
     * Print local information
     * OUTPUT: String instructor availability
     * */
    public String toWrite() {
        String instInfo = "";
        for (int i = 0; i < _ressourceAvailability.size() - 1; i++){
            instInfo += (String) _ressourceAvailability.get(i);
            if(_ressourceSiteAvailability.size()==_ressourceAvailability.size())
                instInfo += DConst.AVAILABILITY_SEPARATOR+
                    (String) _ressourceSiteAvailability.get(i);
            instInfo += CR_LF;
        }
        instInfo += (String) _ressourceAvailability
                .get(_ressourceAvailability.size() - 1);
        if(_ressourceSiteAvailability.size()==_ressourceAvailability.size())
            instInfo +=DConst.AVAILABILITY_SEPARATOR+
                (String) _ressourceSiteAvailability.get(_ressourceAvailability.size() - 1);
        return instInfo;
    }

    /**
     * compare this resource with the specified resource
     * @param resource the specified resource
     * @return bolean true if this resource and the specified resource are equals
     * false if they are not equals
     * */
    public boolean isEquals(DObject inst) {
        AvailabilityAttach instAttach = (AvailabilityAttach) inst;
        if (!_ressourceAvailability.equals(instAttach._ressourceAvailability))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see dInternal.DObject#getSelectedField()
     */
    public long getSelectedField() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 
     * @param dayIndex
     * @param periodIndex
     * @param site
     */
    public void setAvailabilityOfAPeriod(int dayIndex, int periodIndex, String site) {
        if (_ressourceSiteAvailability.size()==0)
            this.initAssignAvailability();
        String[][] matrix = getAssignAvailabilityTable(_ressourceSiteAvailability);
        String defaultValue = String.valueOf(DConst.AVAILABILITY_ASSIGN_DEFAULT);
        if ((dayIndex < matrix.length))
            if (periodIndex < matrix[dayIndex].length)
                if (matrix[dayIndex][periodIndex].equalsIgnoreCase(defaultValue)){
                    matrix[dayIndex][periodIndex] += site;
                    setAssignAvailability(matrix);
                }else{// else if (matrix[dayIndex][periodIndex].equalsIgnoreCase("0"))
//                  if(!matrix[dayIndex][periodIndex].contains(site)){ // XXXX Pascal: lien inutile avec JDK 1.5
                    if(!matrix[dayIndex][periodIndex].matches(".*" + site + ".*")){
                        matrix[dayIndex][periodIndex] += /*DConst.AVAILABILITY_ASSIGN_SEPARATOR+*/ site;
                        setAssignAvailability(matrix);
                    }
                }
    }// end setAvailabilityOfAPeriod(int dayIndex, i ...
    
    /**
     * Return sites where instructor is already assigned in the given period
     * @param dayIndex
     * @param periodIndex
     * @param site
     * @return
     */
    public Vector isAssignedInPeriod(int dayIndex, int periodIndex, Vector sites){
        Vector v= new Vector(1);
        for (int i = 0; i< sites.size(); i++){
            String site = (String)sites.get(i);
            if (_ressourceSiteAvailability.size()==0)
                this.initAssignAvailability();
            String[][] matrix = getAssignAvailabilityTable(_ressourceSiteAvailability);
            if ((dayIndex < matrix.length))
                if (periodIndex < matrix[dayIndex].length)
//                  if(matrix[dayIndex][periodIndex].contains(site)) // XXXX Pascal: lien inutile avec JDK 1.5
            if(matrix[dayIndex][periodIndex].matches(".*" + site + ".*"))
                        v.add(site);
        } //end for (int i = 0; i< sites.size(); i++)
        return v;
    }
    
    
    /**
     * 
     * @param site
     */
    public void remAllAssignedToASite(String site){
        if (_ressourceSiteAvailability.size()==0)
            this.initAssignAvailability();
        for (int i = 0; i < _ressourceSiteAvailability.size(); i++) {
            String str = ((String)_ressourceSiteAvailability.get(i));
            str = str.replaceAll(site,"");
            _ressourceSiteAvailability.setElementAt(str,i);
        }// for (int i = 0; i < _ressourceSiteAvailability.size(); i++)
    }

}