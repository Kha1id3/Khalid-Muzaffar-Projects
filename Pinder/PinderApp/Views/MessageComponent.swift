import SwiftUI

struct MessageComponent: View {
    var name: String
    var image: String
    var messageBody: String
    
    var body: some View {
        HStack {
            Image(image)
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: 55, height: 55)
                .clipShape(Circle())
            
            VStack(alignment: .leading, spacing: 5) {
                Text(name)
                    .fontWeight(.bold)
                    .font(.title2)
                Text(messageBody)
                    .lineLimit(1)
                    .font(.callout)
                    .foregroundColor(.gray)
            }
            .padding(.leading, 5)
            
            Spacer()
        }
        .padding(.horizontal)
    }
}

struct MessageComponent_Previews: PreviewProvider {
    static var previews: some View {
        MessageComponent(name: "Jane Doe",
                         image: "defaultProfileImage", 
                         messageBody: "Hey, how's it going?")
            .previewLayout(.sizeThatFits)
    }
}
