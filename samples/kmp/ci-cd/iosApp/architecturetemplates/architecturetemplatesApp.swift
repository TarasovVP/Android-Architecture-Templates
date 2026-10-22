//
//  architecturetemplatesApp.swift
//  architecturetemplates
//
//  Created by Vladimir Tarasov on 14.04.2024.
//

import SwiftUI
import mobile

@main
struct architecturetemplatesApp: App {

    init() {
        KoinIOSKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
