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
package client.scenes;

import client.model.QuestionData;

import commons.model.LeaderboardEntry;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.util.Pair;

import client.model.QuestionTypes;
import commons.model.Question.ComparisonQuestion;
import commons.model.Question.EstimationQuestion;
import commons.model.Question.MultiChoiceQuestion;
import commons.model.Question.PickEnergyQuestion;

import java.util.List;

public class MainCtrl {

	private Stage primaryStage;

	private LeaderboardCtrl leaderboardCtrl;
	private Scene leaderboard;

	private OpeningCtrl openingCtrl;
	private Scene home;

	private UsernameCtrl usernameCtrl;
	private Scene username;

	private JoinWaitingroomCtrl joinWaitingroomCtrl;
	private Scene joinWaitingroom;

	private WaitingroomCtrl waitingroomCtrl;
	private Scene waitingroom;

	private ServerAddressCtrl serverAddressCtrl;
	private Scene serverAddress;

	private ComparisonScreenCtrl comparisonScreenCtrl;
	private Scene comparisonScreen;

	private EstimationScreenCtrl estimationScreenCtrl;
	private Scene estimationScreen;

	private MultiChoiceScreenCtrl multiChoiceScreenCtrl;
	private Scene multiChoiceScreen;

	private PickEnergyScreenCtrl pickEnergyScreenCtrl;
	private Scene pickEnergyScreen;

	private AdminCtrl adminCtrl;
	private Scene adminScreen;

	private IntermediateLeaderboardCtrl intermediateLeaderboardCtrl;
	private Scene intermediateLeaderboardScreen;

	private EndingScreenCtrl endingScreenCtrl;
	private Scene endingScreen;

	public static final String DEFAULT_SERVER_ADDRESS = "localhost:8080";


	public void initialize(Stage primaryStage,
		Pair<LeaderboardCtrl, Parent> leaderboardCtrl,
		Pair<OpeningCtrl, Parent> openingCtrl,
		Pair<UsernameCtrl, Parent> usernameCtrl,
		Pair<JoinWaitingroomCtrl, Parent> joinWaitingroomCtrl,
		Pair<WaitingroomCtrl, Parent> waitingroomCtrl,
		Pair<ServerAddressCtrl, Parent> serverAddressCtrl,
		Pair<ComparisonScreenCtrl, Parent> comparisonScreenCtrl,
		Pair<EstimationScreenCtrl, Parent> estimationScreenCtrl,
		Pair<MultiChoiceScreenCtrl, Parent> multiChoiceScreenCtrl,
		Pair<PickEnergyScreenCtrl, Parent> pickEnergyScreenCtrl,
		Pair<AdminCtrl, Parent> adminCtrlParentPair,
		Pair<IntermediateLeaderboardCtrl, Parent> intermediateLeaderboardCtrlParentPair,
		Pair<EndingScreenCtrl, Parent> endingScreenCtrlParentPair) {

		//String css = this.getClass().getResource("client/styles.css").toExternalForm();
		this.primaryStage = primaryStage;

		this.leaderboardCtrl = leaderboardCtrl.getKey();
		this.leaderboard = new Scene(leaderboardCtrl.getValue());
		this.leaderboard.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.openingCtrl = openingCtrl.getKey();
		this.home = new Scene(openingCtrl.getValue());
		this.home.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.usernameCtrl = usernameCtrl.getKey();
		this.username = new Scene(usernameCtrl.getValue());
		this.username.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.joinWaitingroomCtrl = joinWaitingroomCtrl.getKey();
		this.joinWaitingroom = new Scene(joinWaitingroomCtrl.getValue());
		this.joinWaitingroom.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.waitingroomCtrl = waitingroomCtrl.getKey();
		this.waitingroom = new Scene(waitingroomCtrl.getValue());
		this.waitingroom.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.serverAddressCtrl = serverAddressCtrl.getKey();
		this.serverAddress = new Scene(serverAddressCtrl.getValue());
		this.serverAddress.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.comparisonScreenCtrl = comparisonScreenCtrl.getKey();
		this.comparisonScreen = new Scene(comparisonScreenCtrl.getValue());
		this.comparisonScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.estimationScreenCtrl = estimationScreenCtrl.getKey();
		this.estimationScreen = new Scene(estimationScreenCtrl.getValue());
		this.estimationScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.multiChoiceScreenCtrl = multiChoiceScreenCtrl.getKey();
		this.multiChoiceScreen = new Scene(multiChoiceScreenCtrl.getValue());
		this.multiChoiceScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.pickEnergyScreenCtrl = pickEnergyScreenCtrl.getKey();
		this.pickEnergyScreen = new Scene(pickEnergyScreenCtrl.getValue());
		this.pickEnergyScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.adminCtrl = adminCtrlParentPair.getKey();
		this.adminScreen = new Scene(adminCtrlParentPair.getValue());
		this.adminScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.endingScreenCtrl = endingScreenCtrlParentPair.getKey();
		this.endingScreen = new Scene(endingScreenCtrlParentPair.getValue());
		this.endingScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		this.intermediateLeaderboardCtrl = intermediateLeaderboardCtrlParentPair.getKey();
		this.intermediateLeaderboardScreen = new Scene(intermediateLeaderboardCtrlParentPair.getValue());
		this.intermediateLeaderboardScreen.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		showServerAddress();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void showAdminPanel() {
		adminCtrl.init();
		adminScreen.setOnKeyPressed(e -> adminCtrl.keyPressed(e));
		primaryStage.setTitle("Admin panel");

		primaryStage.setScene(adminScreen);
	}

	public void showIntermediateLeaderboard(List<LeaderboardEntry> leaderboardEntryList) {
		intermediateLeaderboardCtrl.setIntermediateList(leaderboardEntryList);

		intermediateLeaderboardCtrl.init();

		primaryStage.setTitle("Intermediate Leaderboard");

		primaryStage.setScene(intermediateLeaderboardScreen);
	}

	public void showLeaderboard() {
		leaderboardCtrl.init();

		primaryStage.setTitle("All-time Leaderboard");

		primaryStage.setScene(leaderboard);

		leaderboardCtrl.refresh();
	}

	public void showHome() {
		openingCtrl.init();

		primaryStage.setTitle("Quizz: home");

		primaryStage.setScene(home);
	}

	public void showUsername() {
		usernameCtrl.init();

		username.setOnKeyPressed(e -> usernameCtrl.keyPressed(e));

		primaryStage.setTitle("Enter username");

		primaryStage.setScene(username);
	}

	public void showJoinWaitingroom() {
		joinWaitingroomCtrl.init();

		joinWaitingroom.setOnKeyPressed(e -> joinWaitingroomCtrl.keyPressed(e));

		primaryStage.setTitle("Join a waiting room");

		primaryStage.setScene(joinWaitingroom);
	}

	public void showWaitingroom(int noOfPeople) {
		waitingroomCtrl.init();

		waitingroomCtrl.updateWaitingroomState(noOfPeople);

		waitingroom.setOnKeyPressed(e -> waitingroomCtrl.keyPressed(e));

		primaryStage.setTitle("Join a waiting room");

		primaryStage.setScene(waitingroom);
	}

	public void showServerAddress() {
		serverAddressCtrl.init();

		serverAddress.setOnKeyPressed(e -> serverAddressCtrl.keyPressed(e));

		primaryStage.setTitle("Join a server");

		primaryStage.setScene(serverAddress);
	}

	public void showComparisonQuestion(QuestionData<ComparisonQuestion> questionData) {
		comparisonScreenCtrl.init();
		comparisonScreen.setOnKeyPressed(e -> comparisonScreenCtrl.keyPressed(e));
		comparisonScreenCtrl.setQuestion(questionData);

		primaryStage.setTitle("Question " + (questionData.questionNumber() + 1) + " of 20");

		primaryStage.setScene(comparisonScreen);
	}

	public void showEstimationQuestion(QuestionData<EstimationQuestion> questionData) {
		estimationScreenCtrl.init();
		estimationScreen.setOnKeyPressed(e -> estimationScreenCtrl.keyPressed(e));
		estimationScreenCtrl.setQuestion(questionData);

		primaryStage.setTitle("Question " + (questionData.questionNumber() + 1) + " of 20");

		primaryStage.setScene(estimationScreen);
	}

	public void showMultiChoiceQuestion(QuestionData<MultiChoiceQuestion> questionData) {
		multiChoiceScreenCtrl.init();

		multiChoiceScreenCtrl.setQuestion(questionData);

		primaryStage.setTitle("Question " + (questionData.questionNumber() + 1) + " of 20");

		primaryStage.setScene(multiChoiceScreen);
	}

	public void showPickEnergyQuestion(QuestionData<PickEnergyQuestion> questionData) {
		pickEnergyScreenCtrl.init();

		pickEnergyScreenCtrl.setQuestion(questionData);

		primaryStage.setTitle("Question " + (questionData.questionNumber() + 1) + " of 20");

		primaryStage.setScene(pickEnergyScreen);
	}

	public void showAnswer(QuestionTypes type, Number correctAnswer, int scoreIncrement, int numberOfPlayersScored) {
		switch (type) {
			case COMPARISON -> comparisonScreenCtrl.showAnswer(correctAnswer, scoreIncrement, numberOfPlayersScored);
			case ESTIMATION -> estimationScreenCtrl.showAnswer(correctAnswer, scoreIncrement, numberOfPlayersScored);
			case MULTI_CHOICE -> multiChoiceScreenCtrl.showAnswer((int) correctAnswer, numberOfPlayersScored);
			case PICK_ENERGY -> pickEnergyScreenCtrl.showAnswer((int) correctAnswer, numberOfPlayersScored);
		}
	}

	public void notifyReduceTimePlayed(QuestionTypes type, long timeLeftMs) {
		switch (type) {
			case COMPARISON -> comparisonScreenCtrl.notifyReduceTimePlayed(timeLeftMs);
			case ESTIMATION -> estimationScreenCtrl.notifyReduceTimePlayed(timeLeftMs);
			case MULTI_CHOICE -> multiChoiceScreenCtrl.notifyReduceTimePlayed(timeLeftMs);
			case PICK_ENERGY -> pickEnergyScreenCtrl.notifyReduceTimePlayed(timeLeftMs);
		}
	}

	public void notifyEmojiPlayed(QuestionTypes type, int emojiType) {
		switch (type) {
			case COMPARISON -> comparisonScreenCtrl.notifyEmojiPlayed(emojiType);
			case ESTIMATION -> estimationScreenCtrl.notifyEmojiPlayed(emojiType);
			case MULTI_CHOICE -> multiChoiceScreenCtrl.notifyEmojiPlayed(emojiType);
			case PICK_ENERGY -> pickEnergyScreenCtrl.notifyEmojiPlayed(emojiType);
		}
	}

	public void showEndingScreen(int score) {
		endingScreenCtrl.init();

		endingScreenCtrl.setScore(score);

		primaryStage.setTitle("Game over");

		primaryStage.setScene(endingScreen);
	}

	public void showUsernameBusyError() {
		this.joinWaitingroomCtrl.showUsernameBusyError();
	}

	public void showNotEnoughPlayersError() {
		this.waitingroomCtrl.showNotEnoughPlayersError();
	}

}
