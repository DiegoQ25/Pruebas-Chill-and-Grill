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

// Imports añadidos para la selección aleatoria de categoría
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.Select as Select

// ---------- Datos aleatorios ----------
Random rnd = new Random()

List<String> platillos = ['Hamburguesa', 'Pizza', 'Tacos', 'Ensalada', 'Alitas', 'Papas', 'Sandwich', 'Hot Dog']
String nombrePlatillo = platillos[rnd.nextInt(platillos.size())] + ' ' + (rnd.nextInt(9000) + 1000)

WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Control de Acceso  POS/input_Usuario ID'), 
    'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Control de Acceso  POS/input_Contrasea'), 
    '/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Reportes Generales  POS/a_Catlogo del Men'))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Catlogo del Men  POS/a_Nuevo platillo'))

// ---------- Formulario de nuevo platillo ----------
WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Nuevo platillo  POS/input_Nombre'), 
    nombrePlatillo)

TestObject selectCategoria = findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Nuevo platillo  POS/select_Categora')

// Leer las opciones reales del select y elegir una al azar (ignora la opción vacía)
WebElement elemento = WebUiCommonHelper.findWebElement(selectCategoria, 10)
List<String> valores = new Select(elemento).getOptions()
	.collect { it.getAttribute('value') }
	.findAll { it != null && it.trim() != '' }

String categoriaElegida = valores[rnd.nextInt(valores.size())]

WebUI.selectOptionByValue(selectCategoria, categoriaElegida, false)

WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Nuevo platillo  POS/textarea_Descripcin (opcional)'), 
    'Esta es una descripcion improvisada')

WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Nuevo platillo  POS/input_Precio de venta'), 
    '0')

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Yael/Page_Nuevo platillo  POS/button_Registrar platillo'))

