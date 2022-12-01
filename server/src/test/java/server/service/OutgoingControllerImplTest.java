package server.service;

import commons.model.Activity;
import commons.model.Question;
import commons.servermessage.QuestionMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.api.ConnectionRegistry;
import server.api.OutgoingController;
import server.api.OutgoingControllerImpl;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OutgoingControllerImplTest {
	private static final Question FAKE_QUESTION = new Question.EstimationQuestion(new Activity(420, "a", "b", 42f), 4f);

	@Mock
	private SimpMessagingTemplate template;
	@Mock
	private ConnectionRegistry connectionRegistry;

	private OutgoingControllerImpl createController() {
		return new OutgoingControllerImpl(template, connectionRegistry);
	}

	@Test
	public void test_sending_message() {
		int playerId = 15;
		String user = "15";
		String dest = "/queue/question";

		when(connectionRegistry.getConnectionIdByPlayerId(playerId)).thenReturn(user);

		OutgoingController outgoingController = createController();
		outgoingController.sendQuestion(
				new QuestionMessage(FAKE_QUESTION, 0),
				List.of(playerId));

		verify(template).convertAndSendToUser(user, dest, new QuestionMessage(FAKE_QUESTION, 0));
	}
}
