
import SwiftUI

struct AppInfoView: View {
    
    private var appVersion: String {
        let dictionary = Bundle.main.infoDictionary!
        let version = dictionary[K.Identifiers.CFBundleShortVersionString] as! String
        let build = dictionary[K.Identifiers.CFBundleVersion] as! String
        
        return "\(version) (\(build))"
    }
    
    var body: some View {
        RowAppInfoView(ItemOne: "Application", ItemTwo: "Pinder App | SwiftUI")
        RowAppInfoView(ItemOne: "Developers", ItemTwo: "Khalid/Mert/Ziad")

    }
}

struct RowAppInfoView: View {
    
    var ItemOne: String
    var ItemTwo: String
    var isMultiline = false
    
    var body: some View {
        if isMultiline {
            VStack(alignment: .leading, spacing: 10) {
                Text(ItemOne).foregroundColor(Color.gray)
                Text(ItemTwo)
            }
        } else {
            VStack {
                HStack {
                    Text(ItemOne).foregroundColor(Color.gray)
                    Spacer()
                    Text(ItemTwo)
                }
            }
        }
    }
}
