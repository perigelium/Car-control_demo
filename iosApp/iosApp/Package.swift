//
// Created by lex on 7. 10. 26.
//

import Foundation
// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "SharedUI",
    platforms: [
        .iOS(.v15) // Adjust to match your Compose Multiplatform minimum deployment target
    ],
    products: [
        .library(
            name: "SharedUI",
            targets: ["SharedUITarget"]
        ),
    ],
    dependencies: [],
    targets: [
        // 1. The remote binary target pointing directly to your hosted zip file
        .binaryTarget(
            name: "SharedUIFramework",
            url: "https://github.com/perigelium/Car-control_demo",
            checksum: "81086baad8736bb17ca53912616af1dbb9f6532227213bb52aa216b50db6cae6"
        ),
        // 2. A wrapper target to cleanly expose the binary to Xcode
        .target(
            name: "SharedUITarget",
            dependencies: [
                .target(name: "SharedUIFramework")
            ]
        )
    ]
)
