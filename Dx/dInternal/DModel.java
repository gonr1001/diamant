/**
 *
 * Title: DModel $Revision: 1.2 $  $Date: 2003-02-26 17:44:07 $
 * Description: DModel is a class used to
 *
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
 * @version $Revision: 1.2 $
 * @author  $Author: rgr $
 * @since JDK1.3
 */
package dInternal;

import java.util.Vector;

public class DModel {
  private Vector _dmListeners = new Vector();
  private TTParameters _ttParameters;

  public DModel() {
    _ttParameters = new TTParameters();
  }

  public TTParameters getTTParameters() {
    return _ttParameters;
  }

  public void sendEvent() {
    DModelEvent event = new DModelEvent(this);
    for (int i=0; i< _dmListeners.size(); i++) {
      DModelListener dml = (DModelListener) _dmListeners.elementAt(i);
      dml.changeInDModel(event);
    }
  }

  public synchronized void addDModelListener(DModelListener dml) {
    if (_dmListeners.contains(dml)){
      return;
    }
    _dmListeners.addElement(dml);
  }

  public synchronized void removeTTParametersListener(DModelListener dml) {
    _dmListeners.removeElement(dml);
  }

  public void setParameters(int [] a) {
    _ttParameters.setValues(a);
    sendEvent();
  }
} /* end class DModel */
