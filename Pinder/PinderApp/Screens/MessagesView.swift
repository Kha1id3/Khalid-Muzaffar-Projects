import SwiftUI

struct MessagesView: View {
    @EnvironmentObject private var homeViewModel: HomeViewModel
    @EnvironmentObject private var chatViewModel: ChatViewModel
        
        var body: some View {
            NavigationView {
                List {
                    ForEach(homeViewModel.matches, id: \.id) { matchedCharacter in
                        NavigationLink(destination: ChatView(character: matchedCharacter)) {
                            MessageComponent(name: matchedCharacter.name,
                                             image: matchedCharacter.imageName,
                                             messageBody: "Let's chat!") 
                        }
                    }
                }
                .searchable(text: $homeViewModel.searchText)
                .navigationTitle("Messages")
                .navigationBarTitleTextColor(.blue)
        }
    }
}

struct MessagesView_Previews: PreviewProvider {
    static var previews: some View {
        MessagesView()
            .environmentObject(HomeViewModel())
    }
}
