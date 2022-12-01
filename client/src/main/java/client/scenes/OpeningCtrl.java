package client.scenes;

import com.google.inject.Inject;

public class OpeningCtrl extends AbstractCtrl {

	private final MainCtrl mainCtrl;

	@Inject
	public OpeningCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}


	public void goToLeaderboard() {
		mainCtrl.showLeaderboard();
	}


	public void startNewSingleGame() {
		mainCtrl.showUsername();
	}


	public void startNewMultiGame() {
		mainCtrl.showJoinWaitingroom();
	}


	public void returnHome() {
		mainCtrl.showHome();
	}


	public void adminOption() {
		mainCtrl.showAdminPanel();
	}


	public void connectToServer() {
		mainCtrl.showServerAddress();
	}


}
