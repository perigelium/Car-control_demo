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
            url: "https://placeholder-url-string",
            checksum: "placeholder-checksum-string"
        ),
        .target(
            name: "sharedUITarget",
            dependencies: [
                .target(name: "sharedUIFramework")
            ]
        )
    ]
)

