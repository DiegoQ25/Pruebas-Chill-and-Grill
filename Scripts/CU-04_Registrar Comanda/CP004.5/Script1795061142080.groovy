import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

// Login
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.setText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'Luci')
WebUI.setEncryptedText(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/input_Contrasea'), 'RePBp35xg5z7GlcnRouTRA==')
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// 1. Abrir cuenta en Mesa 5 (usa una libre)
WebUI.waitForElementVisible(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))
WebUI.selectOptionByValue(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), '5', false)
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// Opcional: selecciona la categoría si el platillo no sale en la vista inicial
// WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/btn_Generico_Texto', [('texto') : 'Tacos']))
// WebUI.delay(1)

// Busca sin tilde ('picaña') para evitar fallos por 'Volcán' vs 'Volcan'
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/btn_Generico_Texto', [('texto') : 'picaña']))
// 2. Dar clic sobre un platillo del menú para agregarlo
WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/btn_Generico_Texto', [('texto') : 'Volcán de picaña']))
WebUI.delay(1)

// 3. Aserciones: el platillo se agregó y el total se recalculó (en tu matriz vale $60.00)
WebUI.verifyTextPresent('(?i).*picaña.*', true)
WebUI.verifyTextPresent('60.00', false)

WebUI.click(findTestObject('Object Repository_CU04_Registrar Comanda/Page_Punto de Venta  POS/button_Salir'))
WebUI.closeBrowser()