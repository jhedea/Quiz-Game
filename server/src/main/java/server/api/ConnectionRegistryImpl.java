package server.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.UUID;

@Component
public class ConnectionRegistryImpl implements ConnectionRegistry {
	private final BiMap<String, Integer> connections = HashBiMap.create(); // Maps connectionId to playerId

	private int nextPlayerId = 0;

	@Override
	public String createConnectionId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public int createPlayerIdForConnectionId(String connectionId) {
		int playerId = nextPlayerId++;
		connections.put(connectionId, playerId);
		return playerId;
	}

	@Nullable
	@Override
	public String getConnectionIdByPlayerId(int playerId) {
		return connections.inverse().get(playerId);
	}

	@Override
	public int getPlayerIdByConnectionId(String connectionId) {
		return connections.get(connectionId);
	}
}
