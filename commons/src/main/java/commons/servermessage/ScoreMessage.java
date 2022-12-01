package commons.servermessage;

public record ScoreMessage(int questionScore, int totalScore, int numberOfPlayersScored) { }
