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

public class JoinWaitingroomCtrl extends AbstractCtrl {
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
	public JoinWaitingroomCtrl(MessageLogicService messageService,
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

	//Links to the cancel button
	public void returnHome() {
		mainCtrl.showHome();
	}

	//Links to the join button
	public void join() {
		String username = this.username.getText();

		if (username != null && !username.isEmpty()) {
			messageService.joinWaitingRoom(username);
			errorMessage.setText("");
			rememberUsernamesUseCase.writeUsername(username);

		} else {
			this.errorMessage.setText("Please enter a valid username: ");
		}
		this.username.clear();
	}

	public void showUsernameBusyError() {
		errorMessage.setText("This username is already taken!");
	}

	public void clearField() {
		username.clear();
		errorMessage.setText("");
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

	/**
	 * Adds key functionality to the text fields - enter to trigger join,
	 * escape to return to home screen
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getCode()) {
			case ENTER:
				join();
				break;
			case ESCAPE:
				returnHome();
				break;
			default:
				break;
		}
	}

	private void setUsername(String name) {
		username.setText(name);
	}

}
