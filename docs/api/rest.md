# REST API

## GET /api/leaderboard
Return data:
```
   "leaderboardEntries": [] //array of LeaderboardEntries
```
Entries should be sorted (BY score DESC) LIMIT 10 

## GET /api/admin
```
[
    activity, 
    activity,
    activity, 
    ...
]
```

## POST /api/activities
Adding activities
```
[
    activity, 
    activity,
    activity, 
    ...
]
```

## PUT /api/activities/{id}
Editing an activity
```
    "activity": 
```

## DELETE /api/activities
Deleting all activities
```
   
```


