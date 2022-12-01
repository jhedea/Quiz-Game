package client.service;

import commons.clientmessage.*;
import commons.model.Activity;
import commons.model.JokerType;
import commons.model.LeaderboardEntry;
import commons.servermessage.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import javafx.application.Platform;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerServiceImpl implements ServerService {
	private final List<ServerListener> serverListeners = new ArrayList<>();

	private StompSession session;
	private String serverAddress;

	private StompSession connect(String url) {
		var client = new StandardWebSocketClient();
		var stomp = new WebSocketStompClient(client);
		stomp.setMessageConverter(new MappingJackson2MessageConverter());
		try {
			return stomp.connect(url, new StompSessionHandlerAdapter() {
				@Override
				public void handleException(
						@NonNull StompSession session,
						@Nullable StompCommand command,
						@NonNull StompHeaders headers,
						@NonNull byte[] payload,
						@NonNull Throwable exception
				) {
					throw new RuntimeException("Websocket handler exception", exception);
				}
			}).get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		throw new IllegalStateException();
	}

	private <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
		session.subscribe(dest, new StompSessionHandlerAdapter() {

			@Override
			@NonNull
			public Type getPayloadType(@NonNull StompHeaders headers) {
				return type;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void handleFrame(@NonNull StompHeaders headers, Object payload) {
				consumer.accept((T) payload);
			}
		});
	}

	@Override
	public boolean connectToServer(String serverAddress) {
		this.serverAddress = serverAddress;
		String wsUrl = "ws://" + serverAddress + "/websocket";
		try {
			session = connect(wsUrl);
			registerForMessages("/user/queue/question", QuestionMessage.class, message -> {
				notifyListeners(listener -> listener.onQuestion(message));
			});
			registerForMessages("/user/queue/intermediate-leaderboard",
					IntermediateLeaderboardMessage.class, message -> {
						notifyListeners(listener -> listener.onIntermediateLeaderboard(message));
					});
			registerForMessages("/user/queue/score", ScoreMessage.class, message -> {
				notifyListeners(listener -> listener.onScore(message));
			});
			registerForMessages("/user/queue/waiting-room-state", WaitingRoomStateMessage.class, message -> {
				notifyListeners(listener -> listener.onWaitingRoomState(message));
			});
			registerForMessages("/user/queue/end-of-game", EndOfGameMessage.class, message -> {
				notifyListeners(listener -> listener.onEndOfGame());
			});
			registerForMessages("/user/queue/error", ErrorMessage.class, message -> {
				notifyListeners(listener -> listener.onError(message));
			});
			registerForMessages("/user/queue/reduce-time-played", ReduceTimePlayedMessage.class, message -> {
				notifyListeners(listener -> listener.onReduceTimePlayed(message));
			});
			registerForMessages("/user/queue/emoji-played", EmojiPlayedMessage.class, message -> {
				notifyListeners(listener -> listener.onEmojiPlayed(message));
			});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void notifyListeners(Consumer<ServerListener> consumer) {
		Platform.runLater(() -> serverListeners.forEach(consumer));
	}

	@Override
	public void startSingleGame(String username) {
		session.send("/app/start-single-player", new SinglePlayerGameStartMessage(username));
	}

	@Override
	public void joinWaitingRoom(String username) {
		session.send("/app/join-waiting-room", new WaitingRoomJoinMessage(username));
	}

	@Override
	public void startMultiGame() {
		session.send("/app/start-multi-player", "");
	}

	@Override
	public void exitWaitingRoom() {
		session.send("/app/exit-waiting-room", new WaitingRoomExitMessage());
	}

	@Override
	public void answerQuestion(Number answer) {
		Integer answerInt = answer instanceof Integer ? (Integer) answer : null;
		Float answerFloat = answer instanceof Float ? (Float) answer : null;
		session.send("/app/submit-answer", new QuestionAnswerMessage(answerInt, answerFloat));
	}

	@Override
	public void sendJoker(JokerType type) {
		session.send("/app/send-joker", new SendJokerMessage(type));
	}

	@Override
	public void sendEmoji(int emojiType) {
		session.send("/app/send-emoji", new SendEmojiMessage(emojiType));
	}

	@Override
	public void registerListener(ServerListener serverListener) {
		serverListeners.add(serverListener);
	}

	@Override
	public String getServerAddress() {
		return serverAddress;
	}

	@Override
	public List<LeaderboardEntry> getLeaderboardData() {
		return ClientBuilder
				.newClient(new ClientConfig()) //
				.target("http://" + serverAddress + "/")
				.path("api/leaderboard") //
				.request(APPLICATION_JSON) //
				.accept(APPLICATION_JSON) //
				.get(new GenericType<>() {

				});
	}

	/**
	 * Returns the list of activities
	 *
	 * @return the list of activities
	 */
	@Override
	public List<Activity> getActivities() {
		return ClientBuilder
				.newClient(new ClientConfig())
				.target("http://" + serverAddress + "/")
				.path("api/activities")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<>() { });
	}

	@Override
	public Activity addActivity(Activity activity) {
		var activities = ClientBuilder.newClient(new ClientConfig())
				.target("http://" + serverAddress + "/")
				.path("api/activities")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(List.of(activity), APPLICATION_JSON), new GenericType<List<Activity>>() { });
		return activities.size() == 1 ? activities.get(0) : null;
	}

	@Override
	public void updateActivity(Activity activity) {
		ClientBuilder.newClient(new ClientConfig())
				.target("http://" + serverAddress + "/")
				.path("api/activities/" + activity.id())
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(activity, APPLICATION_JSON));
	}

	@Override
	public void removeActivity(long id) {
		ClientBuilder.newClient(new ClientConfig())
				.target("http://" + serverAddress + "/")
				.path("api/activities/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.delete();
	}
}
