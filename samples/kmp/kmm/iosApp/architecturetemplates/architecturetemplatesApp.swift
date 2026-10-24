//
//  architecturetemplatesApp.swift
//  architecturetemplates
//
//  Created by Vladimir Tarasov on 14.04.2024.
//

import SwiftUI
import shared

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
