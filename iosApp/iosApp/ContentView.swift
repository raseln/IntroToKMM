import SwiftUI
import shared

struct ContentView: View {
    
    // @ObservedObject property wrapper is used to subscribe to the view model.
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("Rocket Launch Info")
            .navigationBarItems(trailing:
                Button("Reload") {
                    self.viewModel.loadLaunches(forceReload: true)
            })
        }
    }

    private func listView() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let launches):
            return AnyView(List(launches, id: \.self) { launch in
                NavigationLink {
                    LaunchDetailScreen(launchId: launch.id, viewModel: viewModel)
                } label: {
                    RocketLaunchRow(rocketLaunch: launch)
                }
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}

extension ContentView {
    
    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }
    
    enum LoadableDetails {
        case loading
        case result(LaunchDetail)
        case error(String)
    }
    
    // ViewModel is declared as an extension to ContentView, as they are closely connected
    class ViewModel: ObservableObject {
        
        let sdk: SpaceXSDK
        @Published var launches = LoadableLaunches.loading
        @Published var details = LoadableDetails.loading
        
        init(sdk: SpaceXSDK) {
            self.sdk = sdk
            self.loadLaunches(forceReload: false)
        }

        func loadLaunches(forceReload: Bool) {
            self.launches = .loading
            sdk.getLaunches(forceReload: forceReload, completionHandler: { launches, error in
                if let launches = launches {
                    self.launches = .result(launches)
                } else {
                    self.launches = .error(error?.localizedDescription ?? "error")
                }
            })
        }
        
        func getLaunchDetails(launchId: String) {
            self.details = .loading
            sdk.getLaunchById(id: launchId, completionHandler: { details, error in
                DispatchQueue.main.async {
                    if let details = details {
                        self.details = .result(details)
                    } else {
                        self.details = .error(error?.localizedDescription ?? "Error fetching details")
                    }
                }
            })
        }
    }
}



struct RocketLaunchRow: View {
    var rocketLaunch: RocketLaunch

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("**Launch Name:** \(rocketLaunch.name)")
                HStack() {
                    Text("Launch Status: ").bold()
                    Text(launchText).foregroundColor(launchColor)
                }
                Text("**Launch Date:** \(String(rocketLaunch.dateUTC))")
                Text("**Launch Details:** \(rocketLaunch.details ?? "")")
            }
            Spacer()
        }
    }
}

extension RocketLaunchRow {
    
   private var launchText: String {
       if let isSuccess = rocketLaunch.success {
           return isSuccess.boolValue ? "Successful" : "Unsuccessful"
       } else {
           return "No data"
       }
   }

   private var launchColor: Color {
       if let isSuccess = rocketLaunch.success {
           return isSuccess.boolValue ? Color.green : Color.red
       } else {
           return Color.gray
       }
   }
}

