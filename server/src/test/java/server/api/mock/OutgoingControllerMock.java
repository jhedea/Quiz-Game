package server.api.mock;

import commons.servermessage.*;
import server.api.OutgoingController;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of outgoing controller. Registers all sent messages
 */
public class OutgoingControllerMock implements OutgoingController {
	private record SentMessage(Object message, int playerId) { }

	private final List<SentMessage> sentMessages = new ArrayList<>();

	@Override
	public void sendQuestion(QuestionMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendScore(ScoreMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendEndOfGame(EndOfGameMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendWaitingRoomState(WaitingRoomStateMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendIntermediateLeaderboard(IntermediateLeaderboardMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendError(ErrorMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendTimeReduced(ReduceTimePlayedMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	@Override
	public void sendEmojiPlayed(EmojiPlayedMessage message, List<Integer> players) {
		saveMessage(message, players);
	}

	private void saveMessage(Object message, List<Integer> players) {
		players.forEach(p -> sentMessages.add(new SentMessage(message, p)));
	}

	/**
	 * Returns all captured messages
	 *
	 * @return all messages
	 */
	public List<Object> getSentMessages() {
		return sentMessages.stream().map(sm -> sm.message).toList();
	}

	/**
	 * Returns all captured messages for a given player
	 *
	 * @param playerId id of the player
	 * @return messages sent to the player
	 */
	public List<Object> getSentMessagesForPlayer(int playerId) {
		return sentMessages.stream().filter(sm -> sm.playerId == playerId).map(sm -> sm.message).toList();
	}

	/**
	 * Discards all saved messages
	 */
	public void reset() {
		sentMessages.clear();
	}
}
