// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "sharedUI",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .library(
            name: "sharedUI",
            targets: ["sharedUITarget"]
        ),
    ],
    dependencies: [],
    targets: [
        .binaryTarget(
            name: "sharedUIFramework",
            url: "https://github.com{{ github.repository }}/releases/download/v8/sharedUI.xcframework.zip",
            checksum: "d53be69d1c1e3b1b163376b9b0ddb175b7b8420d51e6745f0658728aa876abe9"
        ),
        .target(
            name: "sharedUITarget",
            dependencies: [
                .target(name: "sharedUIFramework")
            ]
        )
    ]
)

