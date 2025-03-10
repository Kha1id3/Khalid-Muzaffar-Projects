
import Foundation

extension String {
    


    func parseRichTextElements() -> [RichText.Element] {
        let regex = try! NSRegularExpression(pattern: "\\*{1}(.*?)\\*{1}")
        let range = NSRange(location: 0, length: count)
        
    
        let matches: [NSTextCheckingResult] = regex.matches(in: self, options: [], range: range)
        let matchingRanges = matches.compactMap { Range<Int>($0.range) }
        
        var elements: [RichText.Element] = []
        
        let firstRange = 0..<(matchingRanges.count == 0 ? count : matchingRanges[0].lowerBound)
        
        self[firstRange].components(separatedBy: " ").forEach { (word) in
            guard !word.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else { return }
            elements.append(RichText.Element(content: String(word), isBold: false))
        }
        
        for (index, matchingRange) in matchingRanges.enumerated() {
            let isLast = matchingRange == matchingRanges.last
            
            let matchContent = self[matchingRange]
            elements.append(RichText.Element(content: matchContent, isBold: true))
            
            let endLocation = isLast ? count : matchingRanges[index + 1].lowerBound
            let range = matchingRange.upperBound..<endLocation
            self[range].components(separatedBy: " ").forEach { (word) in
                guard !word.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else { return }
                elements.append(RichText.Element(content: String(word), isBold: false))
            }
        }
        
        return elements
    }
    
 
    subscript(range: Range<Int>) -> String {
        let startIndex = index(self.startIndex, offsetBy: range.lowerBound)
        let endIndex = index(self.startIndex, offsetBy: range.upperBound)
        return String(self[startIndex..<endIndex])
    }
    
}
