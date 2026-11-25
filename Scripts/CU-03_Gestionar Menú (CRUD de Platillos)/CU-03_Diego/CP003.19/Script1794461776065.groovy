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

// 1. IMPORTAR UTILIDADES PARA SELECCIÓN ALEATORIA Y LOGS
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.util.Random
import java.util.Locale

// 2. GENERAR PRECIO NEGATIVO ALEATORIO (INCLUYENDO -0)
Random rand = new Random()
String precioNegativo

if (rand.nextInt(5) == 0) {
	// 20% de probabilidad de probar exactamente -0
	precioNegativo = "-0"
} else {
	// 80% de probabilidad de probar un número negativo entre -1.00 y -200.50
	int pesos = 1 + rand.nextInt(200)
	def centavosOpciones = [0, 50]
	int centavos = centavosOpciones[rand.nextInt(centavosOpciones.size())]
	precioNegativo = String.format(Locale.US, "-%d.%02d", pesos, centavos) // Ej: -25.00
}

// --- INICIO DEL FLUJO ---
WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.setText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Usuario ID'),
	'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	'/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.sendKeys(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Control de Acceso  POS/input_Contrasea'),
	Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Reportes Generales  POS/a_Catlogo del Men'))

// 3. SELECCIONAR UN PLATILLO ALEATORIO DE LA TABLA
WebUI.waitForElementVisible(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Catlogo del Men  POS/a_Editar'), 5)

List<WebElement> botonesEditar = DriverFactory.getWebDriver().findElements(By.xpath("//a[normalize-space()='Editar']"))
int indicePlatillo = rand.nextInt(botonesEditar.size())
botonesEditar.get(indicePlatillo).click()

// 4. INGRESAR EL PRECIO NEGATIVO
TestObject campoPrecio = findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/input_Precio de venta')
WebUI.waitForElementVisible(campoPrecio, 5)
WebUI.setText(campoPrecio, precioNegativo)

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/button_Guardar cambios'))

// 5. IMPRIMIR EN CONSOLA
KeywordUtil.logInfo("PRUEBA CP003.19 (Precio negativo) -> Platillo #${indicePlatillo + 1} | Precio ingresado: ${precioNegativo}")