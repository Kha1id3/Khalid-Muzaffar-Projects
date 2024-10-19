import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject private var homeViewModel: HomeViewModel
    
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Image(systemName: K.Icon.matchPage)
                    Text("Home")
                }
            
            MessagesView()
                .tabItem {
                    Image(systemName: K.Icon.message)
                    Text("Messages")
                }
            
            ProfileView()
                .tabItem {
                    Image(systemName: K.Icon.profile)
                    Text("Profile")
                }
        }
        .overlay {
            if homeViewModel.showMatchSheet, let match = homeViewModel.match {
                MatchView(imageName: match.imageName)
            }
        }


        .accentColor(.blue)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(HomeViewModel())
    }
}
