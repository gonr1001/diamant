package dInterfaceTest;

import java.util.LinkedList;

import dInternal.DResource;
import dInternal.dTimeTable.Cycle;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.TTStructure;
import dInternal.dUtil.DisplayAttributs;

public class IHM {

	private Grille _grille;
	private TTStructure _tts;
	private DisplayAttributs[][] _toDisplay;

	public IHM(TTStructure tts){
		_tts = tts;
		_toDisplay = _tts.getCurrentCycle().getAttributesToDisplay();//_tts.getPeriodLenght());
		_grille = new Grille(/*nombre de jours*/_toDisplay.length);
		LinkedList<String> listeDeJours = new LinkedList<String>();
		//TODO recupere la derniere heure de la journee
		//_tts.getCurrentCycle().get
		
		for (int i = 0; i < _toDisplay.length; i++) {
			Cycle cycle;
			cycle = _tts.getCurrentCycle();
			DResource days = cycle.getSetOfDays().getResourceAt(i);
			listeDeJours.add(days.getID());
			
			for (int j = 0; j < _toDisplay[0].length; j++) {
				if (_toDisplay[i][j].getPeriodKey() != "" && _toDisplay[i][j].getPeriodType()) {
					Period period = _tts.getCurrentCycle().getPeriodByPeriodKey(_toDisplay[i][j].getPeriodKey());
					dInterfaceTest.Period c = new dInterfaceTest.Period((period.getEndHour()[0]-period.getBeginHour()[0]+0.01*(period.getEndHour()[1]-period.getBeginHour()[1])), period.getBeginHour()[0]+0.01*period.getBeginHour()[1],i+1);
					if(_grille.getHOUR()<period.getEndHour()[0]+0.01*period.getEndHour()[1]){
						//TODO look here 
//						if(period.getEndHour()[1]!=0){
//							_grille.setHOUR(period.getEndHour()[0]+1);
//						}
//						else _grille.setHOUR(period.getEndHour()[0]);
					}
					c.setConflit1(period.getNbStudConflict());
					c.setConflit2(period.getNbInstConflict());
					c.setConflit3(period.getNbRoomConflict());
					_grille.add(c);
				}
			}
		}
		_grille.setListeDeJours(listeDeJours);
	}

	
	
	public Grille getGrille(){
		return _grille;
	}
	
}
