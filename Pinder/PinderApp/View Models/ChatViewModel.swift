import Foundation
import SwiftUI

class ChatViewModel: ObservableObject {
    @Published var messages: [String] = []
}

