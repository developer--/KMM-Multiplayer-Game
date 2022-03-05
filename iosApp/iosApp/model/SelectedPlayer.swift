//
//  SelectedPlayer.swift
//  iosApp
//
//  Created by TBC on 06.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct SelectedPlayer : Codable {
    var id: String
    var selected: Bool
    
    static func fromJson(json: String) -> SelectedPlayer? {
        let jsonData = Data(json.utf8)
        let decoder = JSONDecoder()
        do {
            let player = try decoder.decode(SelectedPlayer.self, from: jsonData)
            return player
        } catch {
            print(error.localizedDescription)
        }
        return nil
    }
}
