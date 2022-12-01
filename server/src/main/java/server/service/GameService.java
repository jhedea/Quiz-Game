package server.service;

import commons.clientmessage.QuestionAnswerMessage;
import commons.clientmessage.SendEmojiMessage;
import commons.clientmessage.SendJokerMessage;
import server.model.Player;

import java.util.List;

/**
 * Game management service
 */
public interface GameService {
	/**
	 * Starts a single-player game.
	 *
	 * @param playerId player id, used to identify player throughout the game
	 * @param userName name of the player, will be used for leaderboard
	 */
	void startSinglePlayerGame(int playerId, String userName);

	/**
	 * Starts a multi-player game.
	 *
	 * @param listOfPlayers list of players participating in the game
	 */
	void startMultiPlayerGame(List<Player> listOfPlayers);

	/**
	 * Submits answer to the current question.
	 *
	 * @param playerId player id
	 * @param answer submitted answer
	 */
	void submitAnswer(int playerId, QuestionAnswerMessage answer);

	/**
	 * Send joker usage message to server
	 *
	 * @param playerId player id
	 * @param jokerMessage jokerType played
	 */
	void jokerPlayed(int playerId, SendJokerMessage jokerMessage);

	/**
	 * Send emoji usage message to server
	 *
	 * @param playerId player who played the emoji
	 * @param emojiMessage message containing which emoji is used
	 */
	void emojiPlayed(int playerId, SendEmojiMessage emojiMessage);
}
