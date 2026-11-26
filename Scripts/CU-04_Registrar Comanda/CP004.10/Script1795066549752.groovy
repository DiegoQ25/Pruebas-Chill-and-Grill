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

// 2. Abrir cuenta (Mostrador)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// 3. Doble clic rápido sobre el platillo
WebUI.doubleClick(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/btn_Generico_Texto', [('texto') : 'Flan']))
WebUI.delay(1)

// 4. Aserciones: se agregó 1 unidad ($95.00) y NO se duplicó ($190.00)
WebUI.verifyTextPresent('95.00', false)
WebUI.verifyTextNotPresent('190.00', false)

WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()