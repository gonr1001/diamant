package dInterface.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */
import dInterface.Command;
import dInterface.DApplication;
import dInternal.dAlgorithms.SelectAlgorithm;
import java.util.Vector;

public class AlgorithmsCmd implements Command{

  public AlgorithmsCmd() {
  }

  public void execute(DApplication dApplic) {
    (new SelectAlgorithm(dApplic.getDMediator().getCurrentDoc().getDM(),0)).execute();

  }
}