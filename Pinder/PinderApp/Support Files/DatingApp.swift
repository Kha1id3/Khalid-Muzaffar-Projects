import SwiftUI

@main
struct TestApp: App {
    
    @StateObject private var model: HomeViewModel
    @StateObject private var chatModel = ChatViewModel() // Initialize ChatViewModel here
    
    init() {
        self._model = StateObject(wrappedValue: HomeViewModel())
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(model)
                
                .environmentObject(chatModel) // Provide ChatViewModel to the
        }
    }
}
