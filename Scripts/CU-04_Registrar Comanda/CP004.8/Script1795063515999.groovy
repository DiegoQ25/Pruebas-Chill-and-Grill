import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

// 1. Iniciar sesión como Empleado (Luci)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'Luci')
WebUI.setEncryptedText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Contrasea'), 'RePBp35xg5z7GlcnRouTRA==')
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// 2. Abrir cuenta vacía (Mostrador)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// 3. Aserción: botón "A Cocina" deshabilitado
WebUI.verifyElementNotClickable(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/btn_Generico_Texto', [('texto') : 'A Cocina']))

WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()