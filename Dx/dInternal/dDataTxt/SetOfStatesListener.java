package dInternal.dDataTxt;

import java.util.EventListener;


public interface SetOfStatesListener extends EventListener{
  void changeInStateBar(SetOfStatesEvent e);
}