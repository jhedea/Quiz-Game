# Importer

This is a tool for importing activities from Activity Bank to your database.
Activities are uploaded via the server, so it must be running in order for it to work.

## How to find activities
All the activities from the Activity Bank are released as a zip file artifact.
You can find it by going to: https://gitlab.ewi.tudelft.nl/cse1105/2021-2022/activity-bank/-/releases
and downloading `ZIP Archive with activities and images `.
Inside the zip archive there is a file `activities.json` - this file should be used for importing.
When running the importer, the path of the directory containing the `activities.json` file should be provided as an argument, not the path of the file itself.

## How to import
In order to import activities, run `importer` with following arguments:
```
SERVER_URL ACTIVITIES_FILE_PATH [-D]
```
where:
- `SERVER_URL` - address of the server to connect to
- `ACTIVITIES_FILE_PATH` - path to a directory containing activities.json file
- `-D` - an optional flag for removing all prior activities

For example:
```
./gradlew :importer:run --args "http://localhost:8080 ../../activities -D"
```
