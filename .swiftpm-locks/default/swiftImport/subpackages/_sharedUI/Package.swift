// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_sharedUI",
  platforms: [
    .iOS("15.0")
  ],
  products: [
    .library(
      name: "_sharedUI",
      type: .none,
      targets: ["_sharedUI"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/firebase/firebase-ios-sdk.git",
      from: "12.5.0"
    )
  ],
  targets: [
    .target(
      name: "_sharedUI",
      dependencies: [
        .product(
          name: "FirebaseAnalytics",
          package: "firebase-ios-sdk"
        )
      ]
    )
  ]
)
