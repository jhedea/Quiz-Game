package server.api;

import commons.servermessage.*;

import java.util.List;

/**
 * Controller for messages from server to client
 */
public interface OutgoingController {
	/**
	 * Sends a new question to the client
	 * @param message - the message containing the question
	 * @param players - the players that receive the message
	 */
	void sendQuestion(QuestionMessage message, List<Integer> players);

	/**
	 * Sends the client's current score to the client
	 * @param message - the message containing the score
	 * @param players - the players that receive the message
	 */
	void sendScore(ScoreMessage message, List<Integer> players);

	/**
	 * Sends an empty message to the client, signifying that the game has ended
	 * and no more questions will be sent by the server
	 * @param message - empty end of game message
	 * @param players - the players that receive the message
	 */
	void sendEndOfGame(EndOfGameMessage message, List<Integer> players);

	/**
	 * Sends the client the waiting room state
	 * @param message The message that has to be sent
	 * @param listOfPlayers The list of players that has to be passed as parameter
	 */
	void sendWaitingRoomState(WaitingRoomStateMessage message, List<Integer> listOfPlayers);

	/**
	 * Sends the intermediate leaderboard to the client
	 * @param message - list of (maximum) ten LeaderboardEntry objects sorted in descending order
	 * @param listOfPlayers - the players that receive the message
	*/
	void sendIntermediateLeaderboard(IntermediateLeaderboardMessage message, List<Integer> listOfPlayers);

	/**
	 * Sends error to the client
	 * @param message error message
	 * @param players the players that receive the message
	 */
	void sendError(ErrorMessage message, List<Integer> players);

	default void sendError(ErrorMessage.Type errorType, List<Integer> players) {
		sendError(new ErrorMessage(errorType), players);
	}

	/**
	 * Sends REDUCE_TIME Joker usage message to client
	 * @param message REDUCE_TIME JokerPlayed message
	 * @param players the players that receive the message
	 */
	void sendTimeReduced(ReduceTimePlayedMessage message, List<Integer> players);

	/**
	 * Sends EmojiPlayedMessage to client
	 *
	 * @param message EmojiPlayed message
	 * @param players the players that receive the message
	 */
	void sendEmojiPlayed(EmojiPlayedMessage message, List<Integer> players);
}
