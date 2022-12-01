package client.scenes;

import client.service.MessageLogicService;
import client.usecase.RememberUsernamesUseCase;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class UsernameCtrl extends AbstractCtrl {

	/**
	 * This controller class refers to the username input screen for starting a singleplayer game.
	 * For multiplayer game refer to JoinWaitingRoomCtrl.
	 */
	private final MessageLogicService messageService;
	private final MainCtrl mainCtrl;
	private final RememberUsernamesUseCase rememberUsernamesUseCase;

	@FXML
	private TextField username;

	@FXML
	private Label errorMessage;

	@FXML
	private ComboBox comboBox;


	@Inject
	public UsernameCtrl(MessageLogicService messageService,
						MainCtrl mainCtrl,
						RememberUsernamesUseCase rememberUsernamesUseCase) {
		this.messageService = messageService;
		this.mainCtrl = mainCtrl;
		this.rememberUsernamesUseCase = rememberUsernamesUseCase;
	}

	@Override
	public void init() {
		super.init();
		clearField();
		comboBox.getSelectionModel().clearSelection();

		initializeDropdownMenu();
		comboBox.setOnAction(e -> setUsername((String) comboBox.getValue()));
	}


	public void start() {
		this.errorMessage.setText("");
		String username = this.username.getText();

		if (username != null && !username.isEmpty()) {
			this.messageService.startSingleGame(username);
			errorMessage.setText("");
			rememberUsernamesUseCase.writeUsername(username);
		} else {
			this.errorMessage.setText("Please enter a valid username: ");
		}
		this.username.clear();
	}

	public void clearField() {
		username.clear();
		errorMessage.setText("");
	}

	public void cancel() {
		mainCtrl.showHome();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getCode()) {
			case ENTER:
				start();
				break;
			case ESCAPE:
				clearField();
				break;
			default:
				break;
		}
	}


	/**
	 * Reads previously used usernames from the usernames.txt file
	 * and adds them to the dropdown menu.
	 */
	private void initializeDropdownMenu() {
		comboBox.setPromptText("Choose username");

		List<String> usernames = rememberUsernamesUseCase.readUsernames();

		for (String name : usernames) {
			if (comboBox.getItems().contains(name)) {
				continue;
			}
			comboBox.getItems().add(name);
		}
	}

	private void setUsername(String name) {
		username.setText(name);
	}
}
