# Car-control_demo

A production-grade, architectural Cross-Platform Proof of Concept (PoC) for vehicle rentals and logistical fleet management, built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform (CMP)**. 

This project demonstrates how to achieve **over 80% code reuse** across business logic, networking, data persistence, and the declarative UI layer for both **Android and iOS** form factors.

## 🎯 Purpose & Compliance (NDA)
This project is an architectural Cross-Platform Proof of Concept (PoC) derived directly from a live, high-load enterprise commercial product. 

To strictly respect intellectual property and Non-Disclosure Agreements (NDA) while proving deep architectural capabilities, the following methodology was executed:
* **Legacy Migration & Modernization:** Individual functional modules were systematically refactored within the original commercial production app—migrating the entire core infrastructure from a legacy stack (**Retrofit / MVP / Gson / Java time & util**) to a modern, KMP-ready foundation (**Ktor / MVI / Kotlin Serialization / Kotlin Date-Time**).
* **Codebase Isolation:** This modernized, production-proven business logic and architecture was then cleanly decoupled and copied directly into this Compose Multiplatform environment.
* **Data Layer Decoupling:** Live enterprise backend connections were substituted with a high-fidelity, local JSON-driven mock engine. Sensitive business modules (such as live booking engines and analytical chart pipelines) were intentionally omitted.

As a result, the code quality, data structures, and state management flows within this repository are 100% equivalent to a live, production-grade enterprise application.

## 📱 Implemented Features & Logistics Workflows
* **Order & Ticket Lifecycle:** Dedicated, state-driven screens displaying active, pending, and historical rental orders, order detail views, and e-vouchers (transport validation tickets).
* **Fleet Tracking via OpenStreetMap (OSM):** Native map integration rendering detailed route visualizations. The codebase contains full production logic for dynamic layout telemetry ingestion (polling interval array appending). For demonstration efficiency, the UI is configured to ingest historical vehicle logs from local JSON to instantly render completed routes with 'Start' and 'End' markers, avoiding real-time waiting bottlenecks during technical evaluation.
* **Offline-First Resilience:** Architecture optimized for zero-latency UI rendering and localized data operations, vital for transport operations in low-connectivity field zones.

## 🛠 Architectural Stack
The codebase follows strict clean architecture and unidirectional data flow principles:
* **UI & Presentation:** Compose Multiplatform for cross-platform UI rendering with **MVI (Model-View-Intent)** state management for predictable state flows.
* **Dependency Injection:** Koin (Multiplatform distribution).
* **Asynchronous Execution:** Kotlin Coroutines & Channels for structured concurrency and reactive state streams.
* **Data & Networking:** Room Database (Multiplatform) for robust local persistence, Jetpack DataStore for preferences, and a pre-configured Ktor HTTP Client layer ready for REST API integration.
* **Navigation:** Type-safe Jetpack Compose Navigation.

## ⚙️ Project Structure & Requirements
* **Android:** Open in Android Studio, sync Gradle, and run the `:composeApp` configuration.
* **iOS:** Open `iosApp/iosApp.xcworkspace` in Xcode (macOS environment required) or deploy directly via Android Studio using the KMP plugin.

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).

---
*Developed by an independent Senior Mobile Systems Engineer based in Central Europe (CET). Technical deep-dives and architecture walkthroughs are available for evaluation upon request.*
