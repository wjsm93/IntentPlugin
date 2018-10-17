@objc(IntentPlugin) class IntentPlugin : CDVPlugin {
  @objc(openApp:)
  func openApp(command: CDVInvokedUrlCommand) {

    // Error de plugin
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    // Declarar variable para primer parámetro
    let occArg = command.arguments as? String ?? ""

    // Si la Occ no viene vacía
    if occArg.count > 0 {

      let onePayScheme = "onepay://"
      let onePayAppStore = "https://itunes.apple.com/cl/app/onepay-transbank/id1432114499?mt=8"
      
      let onePayApp = NSURL(string: onePayScheme)
      
      // Validar que la url esté bien formulada
      guard let url = URL(string: onePayAppStore) else {
        return
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
      
      // Success de plugin
      pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: "Ok"
      )
    }

    // Enviar respuesta de plugin
    self.commandDelegate!.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
