/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

	private static final Injector INJECTOR = createInjector(new MyModule());
	private static final MyFXML FXML = new MyFXML(INJECTOR);

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {

		var leaderboard = FXML.load(LeaderboardCtrl.class, "client", "scenes", "Leaderboard.fxml");

		var home = FXML.load(OpeningCtrl.class, "client", "scenes", "OpeningScreen.fxml");
		var username = FXML.load(UsernameCtrl.class, "client", "scenes", "UsernameScreen.fxml");

		var joinWaitingroom = FXML.load(JoinWaitingroomCtrl.class, "client", "scenes", "JoinWaitingroomScreen.fxml");

		var waitingroom = FXML.load(WaitingroomCtrl.class, "client", "scenes", "WaitingroomScreen.fxml");

		var ending = FXML.load(EndingScreenCtrl.class, "client", "scenes", "EndingScreen.fxml");
		var serverAddress = FXML.load(ServerAddressCtrl.class, "client", "scenes", "ServerAddressScreen.fxml");

		var comparisonScreen = FXML.load(ComparisonScreenCtrl.class, "client", "scenes", "ComparisonScreen.fxml");
		var estimationScreen = FXML.load(EstimationScreenCtrl.class, "client", "scenes", "EstimationScreen.fxml");
		var multiChoiceScreen = FXML.load(MultiChoiceScreenCtrl.class, "client", "scenes", "MultiChoiceScreen.fxml");
		var pickEnergyScreen = FXML.load(PickEnergyScreenCtrl.class, "client", "scenes", "PickEnergyScreen.fxml");

		var adminScreen = FXML.load(AdminCtrl.class, "client", "scenes", "AdminScreen.fxml");

		var intermediateLeaderboard = FXML.load(IntermediateLeaderboardCtrl.class,
				"client",
				"scenes",
				"IntermediateLeaderboard.fxml");

		var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

		mainCtrl.initialize(primaryStage,
				leaderboard,
				home,
				username,
				joinWaitingroom,
				waitingroom,
				serverAddress,
				comparisonScreen,
				estimationScreen,
				multiChoiceScreen,
				pickEnergyScreen,
				adminScreen,
				intermediateLeaderboard,
				ending);

		primaryStage.setOnCloseRequest(this.confirmCloseRequest);
	}

	private EventHandler<WindowEvent> confirmCloseRequest = event -> {
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
				"Are you sure you want to exit? Your game progress might be lost!");
		Button exitButton = (Button) confirmation.getDialogPane().lookupButton(ButtonType.OK);
		Optional<ButtonType> closeResponse = confirmation.showAndWait();
		if (!ButtonType.OK.equals(closeResponse.get())) {
			event.consume();
		} else {
			Platform.exit();
			System.exit(0);
		}
	};
}
