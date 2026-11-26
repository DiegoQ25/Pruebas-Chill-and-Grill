import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

// 1. Login
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'Luci')
WebUI.setEncryptedText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Contrasea'), 
    'RePBp35xg5z7GlcnRouTRA==')
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// 2. Abrir modal y seleccionar Mesa 4 por texto visible (más seguro que por value)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 5)
WebUI.selectOptionByLabel(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    '.*Mesa 4.*', true)

// Dejar vacío el campo de comensal y confirmar
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))

// 4. Aserciones
WebUI.delay(2)
WebUI.verifyTextPresent('Mesa 4', false)
WebUI.verifyTextNotPresent('Andrés Maldonado', false)

// 4. Cerrar sesión y navegador
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()