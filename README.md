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


https://user-images.githubusercontent.com/7534778/156894143-cfd03e97-0329-476c-bea9-d3563bbf40f9.mp4

Todo
- Display winner in Rally mode
- Radnomize stone falls in Crash Out mode
- Make UI for iOS
- etc
