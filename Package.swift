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
            url: "https://github.com{{ github.repository }}/releases/download/v7/sharedUI.xcframework.zip",
            checksum: "af8eb5403fde29eaa392db8c72e4e5cce812ae29d864a136c776929b236a9250"
        ),
        .target(
            name: "sharedUITarget",
            dependencies: [
                .target(name: "sharedUIFramework")
            ]
        )
    ]
)

