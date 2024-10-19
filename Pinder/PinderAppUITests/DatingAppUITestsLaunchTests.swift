//
//  DatingAppUITestsLaunchTests.swift
//  DatingAppUITests
//
//  Created by Khalid Muzaffar on 30/01/2024.
//

import XCTest
@testable import Pinder
final class DatingAppUITestsLaunchTests: XCTestCase {

    override class var runsForEachTargetApplicationUIConfiguration: Bool {
        true
    }

    override func setUpWithError() throws {
        continueAfterFailure = false
    }

    func testLaunch() throws {
        let app = XCUIApplication()
        app.launch()

        
        let attachment = XCTAttachment(screenshot: app.screenshot())
        attachment.name = "Launch Screen"
        attachment.lifetime = .keepAlways
        add(attachment)
    }
}
