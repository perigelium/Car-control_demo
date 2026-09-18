import SwiftUI
import sharedUI

@main
struct ComposeApp: App {

    init() {
        KoinKt.doInitKoin { _ in }

        //UserDefaults.standard.register(defaults: ["UserAgent": "CarControlTrackerApp/3.0 (contact@restrans.eu)"])
        URLProtocol.registerClass(OsmURLProtocol.self)
    }

    var body: some Scene {
        WindowGroup {
            ContentView().ignoresSafeArea(.all)
        }
    }
}

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Updates will be handled by Compose
    }
}

// MARK: - OpenStreetMap Network Interceptor
class OsmURLProtocol: URLProtocol {
    override class func canInit(with request: URLRequest) -> Bool {
        guard let urlString = request.url?.absoluteString else { return false }
        return urlString.contains("tile.openstreetmap.org") && URLProtocol.property(forKey: "OsmHandled", in: request) == nil
    }

    override class func canonicalRequest(for request: URLRequest) -> URLRequest {
        return request
    }

    override func startLoading() {
        // FIXED: Explicitly cast request properties to allow absolute control over HTTP headers
        guard let requestCopy = (request as NSURLRequest).mutableCopy() as? NSMutableURLRequest else { return }

        // Tag request to prevent redirection loops
        URLProtocol.setProperty(true, forKey: "OsmHandled", in: requestCopy)

        // Explicit identity agent string to fulfill OSM technical policy terms
        requestCopy.setValue("CarControlTrackerApp/3.0 (contact@restrans.eu)", forHTTPHeaderField: "User-Agent")

        // Establish standard fallback configuration mapping values
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)

        let task = session.dataTask(with: requestCopy as URLRequest) { [weak self] data, response, error in
            guard let self = self else { return }

            if let error = error {
                self.client?.urlProtocol(self, didFailWithError: error)
                return
            }
            if let response = response {
                self.client?.urlProtocol(self, didReceive: response, cacheStoragePolicy: .notAllowed)
            }
            if let data = data {
                self.client?.urlProtocol(self, didLoad: data)
            }
            self.client?.urlProtocolDidFinishLoading(self)
        }
        task.resume()
    }


/*     override func startLoading() {
        guard let mutableRequest = (request as NSURLRequest).mutableCopy() as? NSMutableURLRequest else { return }
        URLProtocol.setProperty(true, forKey: "OsmHandled", in: mutableRequest)

        mutableRequest.addValue("CarControlTrackerApp/3.0 (contact@restrans.eu)", forHTTPHeaderField: "User-Agent")

        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: mutableRequest as URLRequest) { [weak self] data, response, error in
            guard let self = self else { return }
            if let response = response { self.client?.urlProtocol(self, didReceive: response, cacheStoragePolicy: .notAllowed) }
            if let data = data { self.client?.urlProtocol(self, didLoad: data) }
            if let error = error { self.client?.urlProtocol(self, didFailWithError: error) }
            self.client?.urlProtocolDidFinishLoading(self)
        }
        task.resume()
    } */

    override func stopLoading() {}
}

