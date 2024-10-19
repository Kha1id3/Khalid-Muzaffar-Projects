

import SwiftUI

struct HeaderView: View {
    
    @EnvironmentObject private var homeViewModel: HomeViewModel
    
    var body: some View {
        Button(action: {
            homeViewModel.showSuggestion.toggle()
        }) {
            Text(K.Information.appName)
                .font(.system(size: 34, weight: .heavy, design: .rounded))
                .foregroundColor(.blue)
        }
        .disabled(homeViewModel.suggestions.isEmpty)

    }
}

struct HeaderView_Previews: PreviewProvider {
    static var previews: some View {
        HeaderView()
            .previewLayout(.fixed(width: 375, height: 80))
            .environmentObject(HomeViewModel())
    }
}
