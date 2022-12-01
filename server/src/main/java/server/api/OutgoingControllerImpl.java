package server.api;

import commons.servermessage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutgoingControllerImpl implements OutgoingController {
	private final SimpMessagingTemplate template;
	private final ConnectionRegistry connectionRegistry;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public OutgoingControllerImpl(SimpMessagingTemplate template, ConnectionRegistry connectionRegistry) {
		this.template = template;
		this.connectionRegistry = connectionRegistry;
	}

	private void send(Object message, List<Integer> players, String destination) {
		for (int playerId : players) {
			String connectionId = connectionRegistry.getConnectionIdByPlayerId(playerId);
			if (connectionId == null) continue;
			template.convertAndSendToUser(connectionId, "/queue/" + destination, message);
		}
	}

	@Override
	public void sendQuestion(QuestionMessage message, List<Integer> players) {
		send(message, players, "question");
		logger.debug("Question sent");
	}

	@Override
	public void sendScore(ScoreMessage message, List<Integer> players) {
		send(message, players, "score");
		logger.debug("Score sent");
	}

	@Override
	public void sendEndOfGame(EndOfGameMessage message, List<Integer> players) {
		send(message, players, "end-of-game");
		logger.debug("End of game sent");
	}

	@Override
	public void sendWaitingRoomState(WaitingRoomStateMessage message, List<Integer> listOfPlayers) {
		send(message, listOfPlayers, "waiting-room-state");
		logger.debug("Waiting room state sent");
	}

	@Override
	public void sendIntermediateLeaderboard(IntermediateLeaderboardMessage message, List<Integer> listOfPlayers) {
		send(message, listOfPlayers, "intermediate-leaderboard");
		logger.debug("Intermediate leaderboard sent");
	}

	@Override
	public void sendError(ErrorMessage message, List<Integer> players) {
		send(message, players, "error");
		logger.debug("Error sent");
	}

	@Override
	public void sendTimeReduced(ReduceTimePlayedMessage message, List<Integer> players) {
		send(message, players, "reduce-time-played");
		logger.debug("ReduceTimePlayed Message sent");
	}

	@Override
	public void sendEmojiPlayed(EmojiPlayedMessage message, List<Integer> players) {
		send(message, players, "emoji-played");
		logger.debug("EmojiPlayed Message sent");
	}
}
