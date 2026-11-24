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

// 1. IMPORTAR UTILIDADES PARA JSON, ALEATORIEDAD Y LOGS
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration
import groovy.json.JsonSlurper
import java.io.File
import java.util.Random

// 2. CARGAR NOMBRES Y APELLIDOS DESDE EL JSON (USUARIO SE DEJA VACÍO)
String rutaJson = RunConfiguration.getProjectDir() + "/Data Files/datos_nombres.json"
def datos = new JsonSlurper().parse(new File(rutaJson))

def nombres = datos.nombres
def apellidos = datos.apellidos

Random rand = new Random()
String nombrePila = nombres[rand.nextInt(nombres.size())]
String apellido = apellidos[rand.nextInt(apellidos.size())]

String nombreCompleto = "${nombrePila} ${apellido}" // Ej: Diego Quijano
String password = "${nombrePila.toLowerCase()}123"  // Ej: diego123

// --- INICIO DEL FLUJO ---
WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/input_Usuario ID'),
	'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	'/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Reportes Generales  POS/a_Gestin de Empleados'))

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Gestin de Empleados  POS/a_Nuevo empleado'))

// 3. LLENAR NOMBRE Y CONTRASEÑA, DEJANDO "USUARIO" VACÍO
WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Nuevo empleado  POS/input_Nombre completo'),
	nombreCompleto)

WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Nuevo empleado  POS/input_Contrasea'),
	password)

WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Nuevo empleado  POS/input_Confirmar contrasea'),
	password)

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Diego/Page_Nuevo empleado  POS/button_Registrar empleado'))

// 4. IMPRIMIR EN CONSOLA
KeywordUtil.logInfo("PRUEBA CP002.3 (Usuario vacío) -> Nombre: ${nombreCompleto} | Clave: ${password}")