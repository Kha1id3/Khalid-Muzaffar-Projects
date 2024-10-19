import XCTest

class HomeViewUITests: XCTestCase {
    
    override func setUpWithError() throws {
        continueAfterFailure = false
        let app = XCUIApplication()
        app.launch()
        
    }

    func testCardSwipe() throws {
        let app = XCUIApplication()
        
       p
        let card = app.otherElements["cardView"].firstMatch
        
        
        let exists = NSPredicate(format: "exists == 1")
        expectation(for: exists, evaluatedWith: card, handler: nil)
        waitForExpectations(timeout: 5, handler: nil)
        
        
        card.swipeLeft()
        
        
        XCTAssert(app.otherElements["dislikeImage"].exists)
        
        
        card.swipeRight()
        
       
        XCTAssert(app.otherElements["likeImage"].exists)
    }
}
