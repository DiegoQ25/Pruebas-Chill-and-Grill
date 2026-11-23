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
import java.util.Locale

// 2. GENERAR PRECIO ALEATORIO REALISTA (ENTRE 25.00 Y 350.00)
Random rand = new Random()
int pesos = 25 + rand.nextInt(326) // Entre 25 y 350
def centavosOpciones = [0, 50]     // Terminaciones realistas (.00 o .50)
int centavos = centavosOpciones[rand.nextInt(centavosOpciones.size())]

String nuevoPrecio = String.format(Locale.US, "%d.%02d", pesos, centavos) // Ej: 145.50

// --- INICIO DEL FLUJO ---
WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Usuario ID'),
	'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	'/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.sendKeys(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Gestin de Empleados  POS/a_Catlogo del Men'))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Catlogo del Men  POS/a_Editar'))

// 3. GUARDAR EL PRECIO ANTERIOR Y REEMPLAZARLO POR EL ALEATORIO
TestObject campoPrecio = findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/input_Precio de venta')
String precioAnterior = WebUI.getAttribute(campoPrecio, 'value')

WebUI.setText(campoPrecio, nuevoPrecio)

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/button_Guardar cambios'))

// 4. IMPRIMIR EN CONSOLA EL CAMBIO REALIZADO
KeywordUtil.logInfo("PRUEBA CP003.14 (Edición de Precio) -> Anterior: \$${precioAnterior} | Nuevo: \$${nuevoPrecio}")