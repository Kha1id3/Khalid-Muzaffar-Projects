

import SwiftUI

struct K {
    
    struct Information {
        static let appName = "Pinder"
        static let description = "Find perfect match for your pet"
    }
    
    struct Identifiers {
        static let CFBundleVersion = "CFBundleVersion"
        static let CFBundleShortVersionString = "CFBundleShortVersionString"
    }
    
    struct URL {
        static let baseURL = "https://curious-smock-frog.cyclic.app"
//        static let baseURL = "https://animedating-server.herokuapp.com"
        static let startURL = "\(baseURL)/start"
        static let charactersURL = "\(baseURL)/api/v1/characters"
        static let suggestionURL = "\(baseURL)/api/v1/suggestions"
    }
 
    struct Icon {
        static let profile = "person.circle"
        static let matchPage = "heart"
        static let message = "message"
        static let like = "heart.circle"
        static let dismiss = "xmark.circle"
    }
    
}

