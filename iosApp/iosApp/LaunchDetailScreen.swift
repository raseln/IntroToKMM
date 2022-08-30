//
//  LaunchDetail.swift
//  iosApp
//
//  Created by Md Ahsan Ullah Rasel on 2022/08/11.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LaunchDetailScreen: View {
    
    var launchId: String
    @ObservedObject private(set) var viewModel: ContentView.ViewModel
    
    var body: some View {
        detailView()
            .onAppear {
                viewModel.getLaunchDetails(launchId: launchId)
            }
            .navigationTitle("Launch Details")
    }
    
    private func detailView() -> AnyView {
        switch viewModel.details {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        case .result(let details):
            var launchColor: Color = Color.gray
            var launchText: String = "Couldn't fetch launch details"
            if let isSuccess = details.success {
                if isSuccess.boolValue {
                    launchText = "Successful"
                    launchColor = Color.green
                } else {
                    launchText = "Unsuccessful"
                    launchColor = Color.red
                }
            }
            return AnyView(
                HStack {
                    VStack(alignment: .leading, spacing: 10.0) {
                        if let imageUrl = details.links.patch.small {
                            if #available(iOS 15.0, *) {
                                AsyncImage(url: URL(string: imageUrl))
                                    .frame(minWidth: 0, idealWidth: 400, maxWidth: 400, minHeight: 300, idealHeight: 300, maxHeight: 300, alignment: .center)
                            } else {
                                // Fallback on earlier versions
                            }
                        }
                        Text("**Launch Name:** \(details.name)")
                        HStack() {
                            Text("Launch Status: ").bold()
                            Text(launchText).foregroundColor(launchColor)
                        }
                        Text("**Launch Date:** \(String(details.dateUTC))")
                        Text("**Launch Details:** \(details.details ?? "")")
                        if let reason = details.failures.first(where: { failure in
                            failure.reason != nil
                        })?.reason {
                            Text("**Failure Reason:** \(reason)")
                        }
                        if let articleUrl = details.links.article {
                            Link("Read article about it", destination: URL(string: articleUrl)!)
                        }
                        if let webcastUrl = details.links.webcast {
                            Link("Watch video about it", destination: URL(string: webcastUrl)!)
                        }
                        if let wikipedia = details.links.wikipedia {
                            Link("Learn more...", destination: URL(string: wikipedia)!)
                        }
                    }
                    .padding()
                }
            )
        }
    }
}
