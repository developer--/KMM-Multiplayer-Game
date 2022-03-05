# KMM-Multiplayer-Game

Sample usage is pretty simmilar:

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
