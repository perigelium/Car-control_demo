# Logistics & Fleet Management Architecture Showcase

A production-grade architectural showcase demonstrating a highly scalable, robust native Android core with 
a structurally decoupled Multiplatform logic layer. Derived from commercial vehicle rental and transport ticketing workflows,
this repository highlights enterprise-level data resilience, clean architecture, and modern declarative UI practices.

<br>

## 🎯 Purpose & Production-Proven Foundations (NDA Compliant)

This project is a decoupled architectural showcase based on field-tested commercial software. To strictly respect intellectual property 
and Non-Disclosure Agreements (NDA) while demonstrating high-level engineering capabilities, the following engineering choices were made:

    • Legacy Migration & Modernization: The underlying core infrastructure was systematically refactored and modernized from 
    a legacy corporate stack (Retrofit / MVP / Java util) into a clean, 
    modern foundation utilizing Ktor, MVI, Kotlin Serialization, and Kotlinx Date-Time.
    • Architectural Isolation: The production-proven business logic and data structures were isolated and decoupled into 
    a clean repository, ensuring a modular codebase ready for seamless cross-platform scaling (KMP).
    • Data Layer Decoupling: Production enterprise backend endpoints were substituted with 
    a high-fidelity local JSON-driven mock engine to simulate data stress-tests. Sensitive proprietary modules 
    (such as live booking engines and analytics chart pipelines) were intentionally omitted.

As a result, the code quality, thread management, and state propagation models 
within this repository are 100% equivalent to a live, production-grade enterprise application.

<br>

## 📱 Implemented Features & Logistics Workflows

    • Order & Ticket Lifecycle: State-driven screens displaying active, pending, and historical rental orders, detailed item views, 
    and e-vouchers (transport validation tickets) managed via predictable MVI state streams.
    • Fleet Tracking via OpenStreetMap (OSM): Map integration rendering detailed route visualizations. 
    The codebase contains full production logic for dynamic vehicle telemetry processing. 
    For demonstration efficiency, the engine is configured to process localized telemetry logs 
    to instantly render historical routes with 'Start' and 'End' markers, avoiding real-time waiting bottlenecks during technical evaluation.
    • Offline-First Resilience: Local database structures optimized for zero-latency UI rendering and localized operations, 
    vital for transport operations in low-connectivity field zones.

<br>

## 🛠 Architectural Stack & Engineering Standards

The codebase follows strict clean architecture, unidirectional data flow (UDF), and SOLID principles:

    • UI & Presentation: Jetpack Compose (using Compose Multiplatform for flexible rendering) paired with MVI (Model-View-Intent) 
    for robust state tracking and predictable UI behavior.
    • Dependency Injection: Koin (Multiplatform distribution) for clean, low-overhead module configuration.
    • Asynchronous Execution: Kotlin Coroutines & Channels for structured concurrency, background thread pooling, and reactive data streams.
    • Data & Networking: Room Database (Multiplatform) for robust local persistence, Jetpack DataStore for secure preference caching, 
    and a pre-configured Ktor HTTP Client layer ready for REST API integration.
    • Navigation: Type-safe Jetpack Compose Navigation.

<br>

⚙️ Project Structure & Requirements

    Android: Open in Android Studio, sync Gradle, and run the :composeApp configuration.
    iOS: Open iosApp/iosApp.xcworkspace in Xcode (macOS environment required) or 
    deploy directly via Android Studio using the KMP plugin.

<br>

📱 Video Demo

<details>
  <summary>🎬 Click to expand and watch the Simulator in action</summary>
  <br>
  <video src="https://github.com/user-attachments/assets/7fd7bc25-b95a-463c-a41e-db5b9d8e4d2c" controls width="100%"></video>
</details>
<br>

📄 License

This project is open-source and available under the MIT License.
