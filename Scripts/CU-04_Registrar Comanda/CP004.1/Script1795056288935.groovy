import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1. Abrir navegador primero y luego navegar a la URL
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

// 2. Iniciar sesión como Empleado (Luci)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'Luci')
WebUI.setEncryptedText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Contrasea'),
	'RePBp35xg5z7GlcnRouTRA==')
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// 3. Abrir modal de nueva cuenta y seleccionar Mesa 2
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 5)
WebUI.selectOptionByValue(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'),
	'2', false)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))

// 4. ASERCIONES (Validación real del Resultado Esperado de CP004.1)
WebUI.delay(1) // Breve pausa para que renderice la nueva pestaña
WebUI.verifyTextPresent('Mesa 2', false)
// El segundo parámetro en 'true' activa expresiones regulares (acepta $0.00 o $ 0.00)
WebUI.verifyTextPresent('\\$\\s?0\\.00', true)

// 5. Cerrar sesión y navegador
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()