

import Foundation


class ChatStorageManager {
    static let shared = ChatStorageManager()
    private let fileManager = FileManager.default
    private let documentsDirectory: URL
    
    init() {
        documentsDirectory = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
    }
    
    func saveMessages(_ messages: [String], for characterId: String) {
        let fileURL = documentsDirectory.appendingPathComponent("\(characterId).json")
        do {
            let data = try JSONEncoder().encode(messages)
            try data.write(to: fileURL, options: .atomicWrite)
        } catch {
            print("Failed to save messages for \(characterId): \(error)")
        }
    }
    
    func loadMessages(for characterId: String) -> [String] {
        let fileURL = documentsDirectory.appendingPathComponent("\(characterId).json")
        do {
            let data = try Data(contentsOf: fileURL)
            let messages = try JSONDecoder().decode([String].self, from: data)
            return messages
        } catch {
            print("Failed to load messages for \(characterId): \(error)")
            return []
        }
    }
}
