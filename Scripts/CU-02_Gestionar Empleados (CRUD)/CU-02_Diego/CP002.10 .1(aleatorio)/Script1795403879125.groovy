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

// 1. IMPORTAR UTILIDADES
import com.kms.katalon.core.util.KeywordUtil
import java.util.Random

// 2. GENERAR NOMBRE ALEATORIO PARA BUSCAR EN EL FILTRO
def nombres = [
	'Diego', 'Carlos', 'Valeria', 'Fernando', 'Camila',
	'Mateo', 'Sofia', 'Andres', 'Lucia', 'Gabriel',
	'Daniela', 'Ricardo', 'Mariana', 'Javier', 'Elena',
	'Sergio', 'Natalia', 'Alejandro', 'Paula', 'Eduardo'
]

def apellidos = [
	'Quijano', 'Mendoza', 'Ramirez', 'Castillo', 'Morales',
	'Herrera', 'Vargas', 'Salazar', 'Navarro', 'Delgado',
	'Ortega', 'Fuentes', 'Paredes', 'Rojas', 'Guzman',
	'Torres', 'Flores', 'Cruz', 'Reyes', 'Medina'
]

Random rand = new Random()
String nombrePila = nombres[rand.nextInt(nombres.size())]
String apellido = apellidos[rand.nextInt(apellidos.size())]
String nombreABuscar = "${nombrePila} ${apellido}" // Ej: Diego Quijano

// --- INICIO DEL FLUJO ---
WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/input_Usuario ID'),
	'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	'/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.sendKeys(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Reportes Generales  POS/a_Gestin de Empleados'))

// 3. INGRESAR EL NOMBRE ALEATORIO EN EL FILTRO Y BUSCAR
WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Gestin de Empleados  POS/input_Ej. Carlos'),
	nombreABuscar)

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Gestin de Empleados  POS/button_Filtrar'))

// 4. VERIFICAR SI HUBO COINCIDENCIA Y REGISTRARLO EN CONSOLA
boolean encontrado = WebUI.verifyTextPresent(nombreABuscar, false, FailureHandling.OPTIONAL)

if (encontrado) {
	KeywordUtil.logInfo("PRUEBA CP002.10 -> Se buscó '${nombreABuscar}' y SÍ coincidió con un empleado existente.")
} else {
	KeywordUtil.logInfo("PRUEBA CP002.10 -> Se buscó '${nombreABuscar}' y NO se encontraron coincidencias.")
}