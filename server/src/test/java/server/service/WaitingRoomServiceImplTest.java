package server.service;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import server.api.OutgoingController;

import commons.servermessage.ErrorMessage;
import commons.servermessage.ErrorMessage.Type;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WaitingRoomServiceImplTest {

	@Mock
	private OutgoingController outgoingController;
	@Mock
	private GameService gameService;

	@Test
	public void should_not_start_multiplayer_with_one_player() {
		var service = new WaitingRoomServiceImpl(outgoingController, gameService);
		service.joinWaitingRoom("name1", 1);
		service.startMultiplayerGame();

		verify(outgoingController).sendError(
				new ErrorMessage(Type.NOT_ENOUGH_PLAYERS),
				List.of(1)
		);
	}
}
