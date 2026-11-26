import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1. Abrir navegador y entrar al sistema
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

// 2. Iniciar sesión como Empleado (Luci)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'Luci')
WebUI.setEncryptedText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Contrasea'), 
    'RePBp35xg5z7GlcnRouTRA==')
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// 3. Abrir modal, seleccionar Mesa 2 y escribir el nombre del comensal
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 5)
WebUI.selectOptionByValue(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    '2', false)

// IMPORTANTE: Cambia 'input_Nombre_comensal' por el nombre exacto con el que guardaste el objeto en tu carpeta
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/Page_Punto de Venta  POS/input_Nombre_comensal'), 'Andrés Maldonado')

WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))

// 4. Aserciones (Validaciones del resultado esperado)
WebUI.delay(1)
WebUI.verifyTextPresent('Mesa 2', false)
WebUI.verifyTextPresent('Andrés Maldonado', false)
WebUI.verifyTextPresent('\\$\\s?0\\.00', true)

// 5. Cerrar sesión y navegador
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()