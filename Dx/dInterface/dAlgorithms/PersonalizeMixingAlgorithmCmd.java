  package dInterface.dAlgorithms;

 /**
  * <p>Title: Proto</p>
  * <p>Description:  timetable construction</p>
  * <p>Copyright: Copyright (c) 2002</p>
  * <p>Company: UdeS</p>
  * @author unascribed
  * @version 1.0
  */

 import com.iLib.gDialog.InformationDlg;

 import dConstants.DConst;
 import dInterface.Command;
 import dInterface.DApplication;
 import dInternal.dOptimization.SelectAlgorithm;


 public class PersonalizeMixingAlgorithmCmd implements Command{

   int _selectedContext=3;// context for optimize mixing algorithm
   boolean _USER_TEST_ACTIV = false;

   /**
    *
    */
   public PersonalizeMixingAlgorithmCmd(boolean USER_TEST_ACTIV) {
     _USER_TEST_ACTIV = USER_TEST_ACTIV;
   }

   /**
    *
    * @param dApplic
    */
   public void execute(DApplication dApplic) {
     DConst.USER_TEST_ACTIV= _USER_TEST_ACTIV;
     //new PersonalizeMixingAlgorithmDlg();
     PersonalizeMixingAlgorithmDlg perso= new PersonalizeMixingAlgorithmDlg("8");
     String input= perso.showInputDialog();
     if(input!=null){
       int personalizeAcceptableVariation=Integer.parseInt(input);
       (new SelectAlgorithm(personalizeAcceptableVariation,dApplic.getDMediator().getCurrentDoc().getDM())).execute();
       new InformationDlg(dApplic.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
     }
   }


}