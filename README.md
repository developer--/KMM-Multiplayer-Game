# KMM-Multiplayer-Game

Sample usage is pretty similar:

In Android
```Kotlin
val socketEventsProcessor = SocketEventsProcessor()
socketEventsProcessor.connect()
socketEventsProcessor.sendData(Events.Request.ON_TAP, "Hello from Android")
socketEventsProcessor.onTapEventReceived = { response -> String
    println("Response from socket server in Android $response")
}
```
In iOS
```Swift
let socketProcessor = SocketEventsProcessor()
socketProcessor.connect()
socketProcessor.sendData(eventRequest: Events.Request.onTap, data: "Hello from iOS")
socketProcessor.onTapEventReceived = { (response) -> Void  in
   print("Response from socket server in iOS \(data)")
}
```


https://user-images.githubusercontent.com/7534778/156894143-cfd03e97-0329-476c-bea9-d3563bbf40f9.mp4

iOS with SwiftUI (iOS dev help would be great)

<img width="273" height="600" alt="Screenshot 2022-03-06 at 01 15 21" src="https://user-images.githubusercontent.com/7534778/156900137-02ead31f-f56d-445c-af16-f709fc50248b.png">

Todo
- Display winner in Rally mode
- Radnomize stone falls in Crash Out mode
- Make UI for iOS
- etc

Back-End repo https://github.com/developer--/Multiplayer-Game-Server

