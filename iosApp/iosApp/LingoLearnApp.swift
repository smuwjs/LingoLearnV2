import SwiftUI

@main
struct LingoLearnApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        Text("LingoLearn V2 - Loading...")
            .font(.largeTitle)
            .foregroundColor(.blue)
    }
}
