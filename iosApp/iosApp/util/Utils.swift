//
//  Utils.swift
//  iosApp
//
//  Created by TBC on 06.03.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit


func getDeviceId() -> String {
    return UIDevice.current.identifierForVendor!.uuidString
}
