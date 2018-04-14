package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.MessageBox;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

public class GUI implements DameGUIInterface {
	
	Backend backend;
	
	
	public GUI(Backend backend) {
		
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showBoard(Board bard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeApplication() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initiateTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean showMessageBox(MessageBox msgbox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void animateMove(Stone stone, Coordinate coordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void animateStoneRemove(Stone stone) {
		// TODO Auto-generated method stub

	}

}
