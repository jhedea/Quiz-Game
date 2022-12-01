package commons.servermessage;

public record ErrorMessage(Type errorType) {
	public enum Type {
		USERNAME_BUSY,
		NOT_ENOUGH_PLAYERS
	}
}
