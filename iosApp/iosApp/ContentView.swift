import SwiftUI
import shared

struct ContentView: View {
	let socketProcessor = SocketEventsProcessor()

	var body: some View {
        Text("Tap Tap").onTapGesture {
            socketProcessor.connect()
            socketProcessor.sendData(eventRequest: Events.Request.onTap, data: "Hello from iOS")
            socketProcessor.onTapEventReceived = { (data) -> Void  in
                print("onTapEventReceived \(data)")
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
