import SwiftUI
import shared



struct ContentView: View {
    @State private var startRaceMode = false
    
    @State private var isP1Selected = false
    @State private var isP2Selected = false

	let socketProcessor = SocketEventsProcessor()
    


	var body: some View {
        if !startRaceMode {
            VStack {
                Text("Rally").onTapGesture {
                    socketProcessor.connect()
                }.padding()
            
                Text("Crash Out!").onTapGesture {
                    startRaceMode = true
                    socketProcessor.onTapEventReceived = { (data) -> Void  in
                        print("onTapEventReceived \(data)")
                    }
                }.padding()
            
            }
        }else {
            raceModeView
        }
	}
    
    var raceModeView: some View {

        VStack {
            HStack {
                Text("P1")
                let color = isP1Selected ? Color.green : Color.red
                
                Circle().fill(color).frame(width: 20, height: 20)
                Image("red_car").resizable().frame(width: 80, height: 150).padding()
                    .onTapGesture {
                        do {
                            let playerInfo = PlayerInfo(playerId: "1", deviceId: getDeviceId())
                            let jsonData = try JSONEncoder().encode(playerInfo)
                            let json = String(data: jsonData, encoding: String.Encoding.utf8)!
                            socketProcessor.sendData(eventRequest: Events.Request.playerSelected, data: json)
                            
                        }catch {}
                    }
            }
            HStack {
                Text("P2")
                let color = isP2Selected ? Color.green : Color.red
                Circle().fill(color).frame(width: 20, height: 20)
                Image("yellow_car").resizable().frame(width: 80, height: 150).padding()
                    .onTapGesture {
                        do {
                            let playerInfo = PlayerInfo(playerId: "2", deviceId: getDeviceId())
                            let jsonData = try JSONEncoder().encode(playerInfo)
                            let json = String(data: jsonData, encoding: String.Encoding.utf8)!
                            socketProcessor.sendData(eventRequest: Events.Request.playerSelected, data: json)
                        }catch {}
                    }
            }
        }.onAppear {
            socketProcessor.connect()
            socketProcessor.onPlayerSelected = { (data) -> Void  in
                let selectedPlayer = SelectedPlayer.fromJson(json: data)
                if(selectedPlayer?.id == "1") {
                    isP1Selected = true
                }
                if(selectedPlayer?.id == "2") {
                    isP2Selected = true
                }
                print("onTapEventReceived \(selectedPlayer?.id) \(selectedPlayer?.selected)")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
