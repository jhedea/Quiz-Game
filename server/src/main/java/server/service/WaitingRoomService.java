package server.service;

/**
 * Waiting room management service
 */
public interface WaitingRoomService {
	/**
	 * Puts the player in the waiting room if not already there
	 *
	 * @param playerName The name of the player
	 * @param playerId The id of the player
	 */
	void joinWaitingRoom(String playerName, int playerId);

	/**
	 * Removes a player from a waiting room if they choose to exit
	 * @param playerId
	 */
	void exitWaitingRoom(int playerId);

	/**
	 * The game of the given waiting room is started by one of the players
	 */
	void startMultiplayerGame();
}
