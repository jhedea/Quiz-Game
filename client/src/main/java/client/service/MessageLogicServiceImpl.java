package client.service;

import client.model.GameType;
import client.model.QuestionData;
import client.model.QuestionTypes;
import client.scenes.MainCtrl;
import client.service.ServerService.ServerListener;
import client.usecase.ResolveImageUrlsUseCase;
import com.google.inject.Inject;
import commons.model.JokerType;
import commons.model.Question.ComparisonQuestion;
import commons.model.Question.EstimationQuestion;
import commons.model.Question.MultiChoiceQuestion;
import commons.model.Question.PickEnergyQuestion;
import commons.servermessage.*;

public class MessageLogicServiceImpl implements MessageLogicService, ServerListener {

	private final MainCtrl mainCtrl;
	private final ServerService server;
	private final ResolveImageUrlsUseCase resolveImageUrlsUseCase;

	private GameType gameType;
	private int score;
	private QuestionTypes currentType;
	private Number correctAnswer;

	@Inject
	public MessageLogicServiceImpl(
			MainCtrl mainCtrl,
			ServerService server,
			ResolveImageUrlsUseCase resolveImageUrlsUseCase
	) {
		this.mainCtrl = mainCtrl;
		this.server = server;
		this.resolveImageUrlsUseCase = resolveImageUrlsUseCase;

		this.score = 0;

		server.registerListener(this);
	}

	@Override
	public void startSingleGame(String username) {
		server.startSingleGame(username);
		gameType = GameType.SINGLE;
	}

	@Override
	public void joinWaitingRoom(String username) {
		server.joinWaitingRoom(username);
		gameType = GameType.MULTI;
	}

	@Override
	public void exitWaitingRoom() {
		server.exitWaitingRoom();
	}

	@Override
	public void startMultiGame() {
		server.startMultiGame();
	}

	@Override
	public void answerQuestion(Number answer) {
		server.answerQuestion(answer);
	}

	@Override
	public void sendJoker(JokerType type) {
		server.sendJoker(type);
	}

	@Override
	public void sendEmoji(int emojiType) {
		server.sendEmoji(emojiType);
	}

	/**
	 * Called when new question starts
	 * @param message message with new question details
	 */
	@Override
	public void onQuestion(QuestionMessage message) {
		var questionNumber = message.questionNumber();
		var question = resolveImageUrlsUseCase.resolveImageUrls(message.question());
		var availableJokers = message.getAvailableJokers();

		if (question instanceof ComparisonQuestion q) {
			currentType = QuestionTypes.COMPARISON;
			correctAnswer = q.correctAnswer();

			var questionData = new QuestionData<>(q, questionNumber, score, gameType, availableJokers);
			mainCtrl.showComparisonQuestion(questionData);
		}
		if (question instanceof EstimationQuestion q) {
			currentType = QuestionTypes.ESTIMATION;
			correctAnswer = q.correctAnswer();

			var questionData = new QuestionData<>(q, questionNumber, score, gameType, availableJokers);
			mainCtrl.showEstimationQuestion(questionData);
		}
		if (question instanceof MultiChoiceQuestion q) {
			currentType = QuestionTypes.MULTI_CHOICE;
			correctAnswer = q.correctAnswer();

			var questionData = new QuestionData<>(q, questionNumber, score, gameType, availableJokers);
			mainCtrl.showMultiChoiceQuestion(questionData);
		}
		if (question instanceof PickEnergyQuestion q) {
			currentType = QuestionTypes.PICK_ENERGY;
			correctAnswer = q.correctAnswer();

			var questionData = new QuestionData<>(q, questionNumber, score, gameType, availableJokers);
			mainCtrl.showPickEnergyQuestion(questionData);
		}
	}

	/**
	 * Called when question ends
	 * @param message message with player's score
	 */
	@Override
	public void onScore(ScoreMessage message) {
		this.score = message.totalScore();
		mainCtrl.showAnswer(currentType, correctAnswer, message.questionScore(), message.numberOfPlayersScored());
	}

	/**
	 * Called when waiting room state is updated
	 * @param message message with waiting room details
	 */
	@Override
	public void onWaitingRoomState(WaitingRoomStateMessage message) {
		mainCtrl.showWaitingroom(message.noOfPeopleInRoom());
	}

	/**
	 * Called when an endOfGame message is received from the server
	 */
	@Override
	public void onEndOfGame() {
		mainCtrl.showEndingScreen(score);
	}

	/**
	 * Called when error occurs at the server side
	 * @param message message with error details
	 */
	@Override
	public void onError(ErrorMessage message) {
		if (message.errorType() == ErrorMessage.Type.USERNAME_BUSY) {
			mainCtrl.showUsernameBusyError();
		}
		if (message.errorType() == ErrorMessage.Type.NOT_ENOUGH_PLAYERS) {
			mainCtrl.showNotEnoughPlayersError();
		}
	}

	/**
	 * Called to get the intermediate leaderboard
	 *
	 * @param intermediateLeaderboardMessage the message of the intermediate leaderboard
	 */
	@Override
	public void onIntermediateLeaderboard(IntermediateLeaderboardMessage intermediateLeaderboardMessage) {
		mainCtrl.showIntermediateLeaderboard(intermediateLeaderboardMessage.leaderboard());
	}

	/**
	 * Called when a time reduction joker was played by one of the players
	 * @param message message about the joker played
	 */
	@Override
	public void onReduceTimePlayed(ReduceTimePlayedMessage message) {
		mainCtrl.notifyReduceTimePlayed(currentType, message.timeLeftMs());
	}

	/**
	 * Called when an emoji was sent by one of the players
	 * @param message message about the emoji played
	 */
	@Override
	public void onEmojiPlayed(EmojiPlayedMessage message) {
		mainCtrl.notifyEmojiPlayed(currentType, message.emojiNumber());
	}
}
