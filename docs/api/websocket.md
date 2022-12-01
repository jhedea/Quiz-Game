# Websocket API

## Client -> Server

### Single-player game start request
Sent by the client as a first message after connecting.
```
{
    "username": ""
}
```
### Join multi-player waiting room
Can either receive a waiting room state update or an error message if it was unsuccessful. 
```
{
    "username": ""
}
```

### Start multi-player game from waiting room
```
{

}
```

### Questions answer
Submits the answer to the server.
```
{
    "answerInt": 3,
    "answerFloat": 3.1
}
```
### Joker played 
```
{
    "jokerType":"eliminateJoker" //string
}
```
### Emoji sent 
```
{
    "emojiNumber":2 //number denoting the emoji
}
```


## Server -> Client

### Question
Informs the client about a new question.
```
{
    "question": {...}, // See Question model
    "questionNumber": 19 // Indexed from 0
    "eliminateJokerAvailable": true,
    "doublePointsAvailable":false,
    "reduceTimeAvailable":true
}
```
### Correct answer
Informs the client about the question result.
```
{
    "questionScore": 66,
    "totalScore": 666,
}
```
### End of game
Informs client that the final question has been sent and the game can be ended. 
```
{

}
```

### Waiting room state
```
{
    "noOfPeopleInRoom": "",
}
```
### Intermediate leaderboard
```
{
    "LeaderboardEntries": []  //list of LeaderboardEntries in order
}
```


### Reduce time played
Informs players in a multi-player game that their time has been reduced due to another player using a joker
```
{
    "timeLeftMs":
}
```

### Emoji played
```
{
    "emojiNumber":2 //number denoting the emoji
}
```

### Error message
```
{
    "messageText": "",
}
```

