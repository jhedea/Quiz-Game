package server.api;

import org.springframework.lang.Nullable;

/**
 * Players connections registry
 * Provides mapping between connection id (communication layer) and player id (business logic layer).
 * At a given moment:
 * - one connection id corresponds to only one player id
 * - one player id corresponds to only one connection id
 */
public interface ConnectionRegistry {
	/**
	 * Creates a new connection id
	 *
	 * @return created connection id
	 */
	String createConnectionId();

	/**
	 * Creates a player id and assigns it to the provided connection id
	 *
	 * @param connectionId connection id
	 * @return created player id
	 */
	int createPlayerIdForConnectionId(String connectionId);

	/**
	 * Returns connection id associated with given player id.
	 * The connection may not be found if for example the client started another game (which assigns a new player id)
	 *
	 * @param playerId player id
	 * @return connection id or null if not found
	 */
	@Nullable
	String getConnectionIdByPlayerId(int playerId);

	/**
	 * Returns player id associated with given connection id.
	 * Will throw an exception if the connection id is invalid.
	 *
	 * @param connectionId connection id
	 * @return player id
	 */
	int getPlayerIdByConnectionId(String connectionId);
}
