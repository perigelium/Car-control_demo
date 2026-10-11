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
            url: "https://github.com{{ github.repository }}/releases/download/v6/sharedUI.xcframework.zip",
            checksum: "96a94db7d8913cce04506aca3a5fdf83621a5020155324e16917e1c4a92e83ea"
        ),
        .target(
            name: "sharedUITarget",
            dependencies: [
                .target(name: "sharedUIFramework")
            ]
        )
    ]
)

