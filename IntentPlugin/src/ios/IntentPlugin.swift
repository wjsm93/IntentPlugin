@objc(IntentPlugin) class IntentPlugin : CDVPlugin {
  @objc(openApp:)
  func openApp(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let occArg = command.arguments[0] as? String ?? ""

    if occArg.count > 0 {

      let onePayScheme = "onepay://"
      let onePayAppStore = "https://itunes.apple.com/cl/app/onepay/id1218407961?mt=8"
      
      let onePayApp = NSURL(string: onePayScheme)
      
      // Validar que la url esté bien formulada
      guard let url = URL(string: onePayAppStore) else {
        return //be safe
      }

      var components = URLComponents(string: onePayScheme)

      var comQueryItems: [AnyHashable] = []
      comQueryItems.append(URLQueryItem(name: "occ", value: occArg))

      components?.queryItems = comQueryItems as? [URLQueryItem]
      
      // Si la app está instalada
      if UIApplication.shared.canOpenURL(onePayApp! as URL) {
        if let anURL = components?.url {
            UIApplication.shared.openURL(anURL)
        }
      } else {
        // Si la app no está instalada, abrir enlace de AppStore
        if #available(iOS 10.0, *) {
          UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
          UIApplication.shared.openURL(url)
        }
      }
        
      pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: msg
      )
    }

    self.commandDelegate!.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
