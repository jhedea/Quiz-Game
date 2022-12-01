package client.scenes;

import client.service.ServerService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ServerAddressCtrl extends AbstractCtrl {

	private final ServerService server;
	private final MainCtrl mainCtrl;

	@Inject
	public ServerAddressCtrl(ServerService server, MainCtrl mainCtrl) {
		this.server = server;
		this.mainCtrl = mainCtrl;
	}

	@FXML
	private Label errorMessage;

	@FXML
	private TextField serverAddress;

	@FXML
	private Button ok;

	@FXML
	private Button defaultButton;

	@Override
	public void init() {
		super.init();
		clear();
	}

	public void ok() {
		var url = this.serverAddress.getText();
		sendServerAddress(url);
	}

	public void clear() {
		serverAddress.clear();
		errorMessage.setText("");
	}

	public void useDefault() {
		sendServerAddress(MainCtrl.DEFAULT_SERVER_ADDRESS);
	}

	public void sendServerAddress(String url) {
		if (url != null && !url.isEmpty()) {
			boolean result = server.connectToServer(url);
			if (result) {
				serverAddress.clear();
				mainCtrl.showHome();
			} else {
				errorMessage.setText("Please enter a valid address: ");
			}
		} else {
			errorMessage.setText("Please enter a valid address: ");
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getCode()) {
			case ENTER:
				ok();
				break;
			case ESCAPE:
				clear();
				break;
			default:
				break;
		}
	}

}
