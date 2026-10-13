# Logistics & Fleet Management Architecture Showcase

A production-grade architectural showcase demonstrating a highly scalable, robust native Android core with 
a structurally decoupled Multiplatform logic layer. Derived from commercial vehicle rental and transport ticketing workflows,
this repository highlights enterprise-level data resilience, clean architecture, and modern declarative UI practices.

<br>


Architectural Intent & Enterprise Legacy Rescue (NDA Compliant)

This repository serves as a production-grade architectural blueprint demonstrating the systematic modernization of a 
high-load commercial fleet rental and transport ticketing application.
To fully respect intellectual property and Non-Disclosure Agreements (NDAs) of the original enterprise software, 
this project isolates the architecture while decoupling the data layers. 
It provides an explicit answer to the question: How do you safely migrate a massive, 
mission-critical live application into a modern, cross-platform stack without breaking production?

<br>

The Modernization Framework Applied Here:

• The Legacy Baseline: The original commercial application operated on an outdated corporate stack 
(Java / MVP Architecture / Retrofit / Heavy XML Layouts & ViewBindings).
• The Refactoring Strategy: The underlying core infrastructure was systematically deconstructed. 
Business logic was extracted into a decoupled multiplatform layer, and the UI was completely rewritten into declarative patterns.
• The Production Isolation: Enterprise backend endpoints were substituted with a high-fidelity local JSON-driven mock engine 
to simulate high-throughput data stress tests without exposing proprietary APIs. 
Sensitive analytical chart pipelines and booking engines were intentionally omitted.
As a result, the code quality, structured concurrency models, and state propagation flows within this repository 
are 100% equivalent to a live, production-grade enterprise system.

<br>

Implemented Core Flows & Modernized Workflows

• Predictable Order & Ticket Lifecycles: Implements high-reliability, 
state-driven screens displaying active, pending, and historical rental orders. 
Features complex e-vouchers (transport validation tickets) managed via robust, 
unidirectional MVI state streams to eliminate state-drift and rendering race conditions.
• Visual Fleet Operations (OpenStreetMap Integration): A fully decoupled map rendering engine 
capable of drawing high-density route visualizations. 
The codebase contains full production-grade logic for processing vehicle coordinate feeds. 
For benchmarking efficiency, it utilizes localized simulation data 
to instantly render historical routes with dynamic "Start" and "End" markers, 
eliminating real-time testing bottlenecks during engineering evaluations.
• Zero-Latency Offline-First Resilience: Engineered with an advanced 
local data synchronization and persistence architecture. 
This guarantees flawless UI rendering and uninterrupted transactional operations, 
mimicking field environments where transport drivers encounter low-connectivity zones.

<br>

Modernized Architectural Stack & Engineering Standards

The codebase enforces strict separation of concerns, clean architecture boundaries, 
and predictable data flows across all layers:
• UI & Presentation Layer: Written 100% in Jetpack Compose (utilizing Compose Multiplatform) 
paired with MVI (Model-View-Intent) to ensure a completely deterministic user interface behavior 
and eliminate legacy View lifecycle bugs.
• Dependency Injection Architecture: Configured via Koin (Multiplatform distribution) 
for low-overhead, modular dependency graphs, replacing manual injection.
• Structured Concurrency: Powered by Kotlin Coroutines, Flow, and Channels to handle heavy background processing, 
state buffering, and high-performance thread pooling safely.
• Persistence & Data Engines: Powered by Room Database (Multiplatform) for resilient local data storage, 
Jetpack DataStore for secure preference caching, 
and a highly customizable Ktor HTTP Client layer pre-configured for modern, reactive REST API communication.
• Type-Safe Routing: Implements the latest type-safe Jetpack Compose Navigation patterns, 
stripping away legacy XML-based string arguments and fragment-transaction vulnerabilities.

<br>

Modularization & Scaling Strategy

This project demonstrates how a legacy monolith is broken down into clean, decoupled multiplatform modules. 
By isolating the core business logic from platform-specific UI lifecycles, 
this system architecture is structurally optimized to scale seamlessly into a Kotlin Multiplatform (KMP) environment, 
allowing the sharing of entire business layers with iOS and desktop clients without code duplication.

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
