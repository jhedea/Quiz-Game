package server.service;

import commons.servermessage.ErrorMessage;
import commons.servermessage.WaitingRoomStateMessage;
import commons.servermessage.ErrorMessage.Type;

import org.springframework.stereotype.Service;
import server.api.OutgoingController;
import server.model.Player;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaitingRoomServiceImpl implements WaitingRoomService {
	private final List<Player> listOfPlayers;
	private final OutgoingController outgoingController;
	private final GameService gameService;

	public WaitingRoomServiceImpl(OutgoingController outgoingController, GameService gameService) {
		this.outgoingController = outgoingController;
		listOfPlayers = new ArrayList<>();
		this.gameService = gameService;
	}

	@Override
	public void joinWaitingRoom(String playerName, int playerId) {
		/**
		 * If the player isn't already in the waiting room,
		 * we add them to the map of players to the current waiting room
		 * and to the list of players.
		 */
		if (isInWaitingRoom(playerName)) {
			outgoingController.sendError(ErrorMessage.Type.USERNAME_BUSY, List.of(playerId));
			return;
		}
		Player currentPlayer = new Player(playerName, playerId);
		listOfPlayers.add(currentPlayer);
		broadcastNotify();
	}

	private boolean isInWaitingRoom(String playerName) {
		return listOfPlayers.stream().anyMatch(p -> p.getName().equals(playerName));
	}

	@Override
	public void startMultiplayerGame() {
		if (listOfPlayers.isEmpty()) return;
		if (listOfPlayers.size() < 2) {
			List<Integer> listOfPlayerIDs = listOfPlayers.stream().map(Player::getPlayerId).toList();
			outgoingController.sendError(new ErrorMessage(Type.NOT_ENOUGH_PLAYERS), listOfPlayerIDs);
		} else {
			gameService.startMultiPlayerGame(listOfPlayers);
			resetWaitingRoom();
		}
	}

	private void resetWaitingRoom() {
		//clears the list of players whilst resetting the number of players
		listOfPlayers.clear();
	}

	private void broadcastNotify() {
		int numberOfPlayers = listOfPlayers.size();
		WaitingRoomStateMessage waitingRoomStateMessage = new WaitingRoomStateMessage(numberOfPlayers);
		if (numberOfPlayers != 0) {
			List<Integer> listOfPlayerIds = new ArrayList<>();
			for (Player player : listOfPlayers) {
				listOfPlayerIds.add(player.getPlayerId());
			}
			outgoingController.sendWaitingRoomState(waitingRoomStateMessage, listOfPlayerIds);
		}
	}

	@Override
	public void exitWaitingRoom(int playerId) {
		for (Player p : listOfPlayers) {
			if (p.getPlayerId() == playerId) {
				listOfPlayers.remove(p);
				break;
			}
		}
		broadcastNotify();
	}
}
