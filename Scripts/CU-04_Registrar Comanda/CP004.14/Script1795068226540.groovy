import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.setText(findTestObject('Page_Control de Acceso  POS/input_Usuario ID (12)'), 'admin')

WebUI.setEncryptedText(findTestObject('Page_Control de Acceso  POS/input_Contrasea (12)'), '/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.click(findTestObject('Page_Control de Acceso  POS/button_Iniciar Sesin (11)'))

WebUI.click(findTestObject('Page_Reportes Generales  POS/a_Catlogo del Men'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/a_Nuevo platillo'))

WebUI.setText(findTestObject('Page_Nuevo platillo  POS/input_Nombre'), 'Comal Mixto')

WebUI.selectOptionByValue(findTestObject('Page_Nuevo platillo  POS/select_Categora'), '1', false)

WebUI.setText(findTestObject('Page_Nuevo platillo  POS/input_Precio de venta'), '400')

WebUI.click(findTestObject('Page_Nuevo platillo  POS/main_Nuevo platillo'))

WebUI.click(findTestObject('Page_Nuevo platillo  POS/button_Registrar platillo'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_9,999,999.99'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_40.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_199.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_40.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_30.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_150.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/span_Activo'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/button_Inactivo'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_9,999,999.99'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_40.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_400.00'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/a_Editar'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/td_Activo'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/main_Comal Mixto ahora est Inactivo'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/div_Grill Admin'))

WebUI.click(findTestObject('Page_Catlogo del Men  POS/button_Cerrar Sesin'))

WebUI.setText(findTestObject('Page_Control de Acceso  POS/input_Usuario ID (12)'), 'Luci')

WebUI.setEncryptedText(findTestObject('Page_Control de Acceso  POS/input_Contrasea (12)'), 'RePBp35xg5z7GlcnRouTRA==')

WebUI.click(findTestObject('Page_Control de Acceso  POS/button_Iniciar Sesin (11)'))

WebUI.click(findTestObject('Page_Punto de Venta  POS/button_Tacos Grill'))

WebUI.click(findTestObject('Page_Punto de Venta  POS/aside_CUENTAS ABIERTAS (2)'))

WebUI.click(findTestObject('Page_Punto de Venta  POS/button_Salir (11)'))

WebUI.doubleClick(findTestObject('Page_Punto de Venta  POS/button_Salir (11)'))

