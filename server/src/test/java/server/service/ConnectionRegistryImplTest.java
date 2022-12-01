package server.service;

import org.junit.jupiter.api.Test;
import server.api.ConnectionRegistryImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionRegistryImplTest {
	private ConnectionRegistryImpl createRegistry() {
		return new ConnectionRegistryImpl();
	}

	@Test
	public void connection_id_should_be_unique() {
		var registry = createRegistry();

		var id1 = registry.createConnectionId();
		var id2 = registry.createConnectionId();

		assertNotEquals(id1, id2);
	}

	@Test
	public void player_id_should_be_unique() {
		var registry = createRegistry();

		var id1 = registry.createPlayerIdForConnectionId("abc");
		var id2 = registry.createPlayerIdForConnectionId("abc");

		assertNotEquals(id1, id2);
	}

	@Test
	public void connection_should_not_be_found_if_not_added() {
		var registry = createRegistry();

		assertNull(registry.getConnectionIdByPlayerId(5));
	}

	@Test
	public void player_should_not_be_found_if_not_added() {
		var registry = createRegistry();

		assertThrows(Exception.class, () -> registry.getPlayerIdByConnectionId("abc"));
	}

	@Test
	public void connection_should_be_found_after_assigning() {
		var registry = createRegistry();

		var playerId = registry.createPlayerIdForConnectionId("abc");

		assertEquals("abc", registry.getConnectionIdByPlayerId(playerId));
	}

	@Test
	public void player_should_be_found_after_assigning() {
		var registry = createRegistry();

		var playerId = registry.createPlayerIdForConnectionId("abc");

		assertEquals(playerId, registry.getPlayerIdByConnectionId("abc"));
	}
}
