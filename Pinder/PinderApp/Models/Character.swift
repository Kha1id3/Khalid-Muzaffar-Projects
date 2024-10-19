import SwiftUI
struct Character: Identifiable, Hashable, Decodable {
    var id: Int
    var name: String
    var neighborhood: String
    var age: Int
    var imageName: String
    var matched: Bool = false

    enum CodingKeys: String, CodingKey {
        case id, name, neighborhood, age, imageName
    }
    
    var image: Image {
        Image(imageName)
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
        
    }

    static func == (lhs: Character, rhs: Character) -> Bool {
        lhs.id == rhs.id
    }
}
