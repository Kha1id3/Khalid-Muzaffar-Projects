import SwiftUI

@MainActor
final class HomeViewModel: ObservableObject {
    
    @Published var characters: [Character] = []
    @Published var suggestions: [String] = []
    @Published var matches:[Character] = []
    @Published var cardViews: [CardView] = []
    private var cardIndex = 0
    @Published var match: Character?
    @Published var showSuggestion = false
    @Published var showMatchSheet = false
    @Published var cardRemovalTransition = AnyTransition.trailingBottom
    @Published var dragState = DragState.inactive
    let dragAreaThreshold: CGFloat = 65.0
    @Published var searchText = ""
    
    
    
    init() {
        loadLocalData()
    }
    
    var searchResults: [Character] {
        guard !searchText.isEmpty else { return characters }
        return characters.filter { $0.name.contains(searchText) }
    }
    
    func moveCards() {
        guard cardIndex < characters.count - 1 else { return }
        cardViews.removeFirst()
        cardIndex += 1
        
        if cardIndex < characters.count - 4 {
            let newCardView = CardView(character: characters[cardIndex + 4])
            cardViews.append(newCardView)
        }
    }
    
    func isTopCard(cardView: CardView) -> Bool {
        guard let index = cardViews.firstIndex(where: { $0.id == cardView.id }) else {
            return false
        }
        return index == 0
    }
    
    func generateRandomSuggestion() -> String {
        suggestions.randomElement() ?? "No Suggestion"
    }
    
    
    private func loadLocalData() {
      
        characters = [
            Character(id: 1, name: "Peter", neighborhood: "Wonderland", age: 1, imageName: "dog"),
            Character(id: 2, name: "Lodos", neighborhood: "Varna", age: 2, imageName: "lodos"),
            Character(id: 3, name: "Fluffy", neighborhood: "CottonLand", age: 3, imageName: "dog2"),
            Character(id: 4, name: "Sky", neighborhood: "Cybercity", age: 4, imageName: "cat"),
            Character(id: 5, name: "Romeo", neighborhood: "Verona", age: 5, imageName: "dog3"),
            Character(id: 6, name: "Juliet", neighborhood: "Verona", age: 6, imageName: "cat2"),
            Character(id: 7, name: "Dante", neighborhood: "Land of Wano", age: 7, imageName: "dog4"),
            Character(id: 8, name: "Kocia", neighborhood: "Koln", age: 8, imageName: "cat3"),
            Character(id: 9, name: "Smieterling", neighborhood: "Berlin", age: 9, imageName: "cat4"),
            Character(id: 10, name: "Serona", neighborhood: "Pyramids", age: 10, imageName: "cat5"),
            Character(id: 11, name: "Dora", neighborhood: "Jeddah", age: 8, imageName: "dora"),
            Character(id: 12, name: "Flora", neighborhood: "Wroclaw", age: 9, imageName: "flora"),
            Character(id: 13, name: "Sushi", neighborhood: "Jeddah", age: 10, imageName: "sushi"),
            Character(id: 14, name: "Jerry", neighborhood: "Jedaah", age: 7, imageName: "jerry"),
         
            
            
           
        ]
        
       
        cardViews = characters.prefix(4).map { CardView(character: $0) }
        
        
        suggestions = ["bark! bark!", "~meow ~meow", "auuuuuu!"]
    }
    
    func addMatch(character: Character) {
            
            if let index = characters.firstIndex(where: { $0.id == character.id }) {
                characters[index].matched = true
                matches.append(characters[index])
            }
        }
    
    
    
}
