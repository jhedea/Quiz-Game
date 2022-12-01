package client.scenes;

import com.google.inject.Inject;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class EndingScreenCtrl extends AbstractCtrl {

	private final MainCtrl mainCtrl;

	@FXML
	private Label score;

	@Inject
	public EndingScreenCtrl(MainCtrl mainCtrl) {
		this.mainCtrl = mainCtrl;
	}

	public void returnHome() {
		mainCtrl.showHome();
	}

	public void goToLeaderboard() {
		mainCtrl.showLeaderboard();
	}

	public void setScore(int score) {
		String text = "Your score: " + score;
		this.score.setText(text);
	}

}
