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

Random rand = new Random()
int numAleatorio = rand.nextInt(9000) + 1000 // Número de 4 dígitos (1000 - 9999)
String nuevaDescripcion = "Prueba de modificacion de texto #${numAleatorio}"

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

// 2. SELECCIONAR UN PLATILLO ALEATORIO DE LA TABLA
WebUI.waitForElementVisible(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Catlogo del Men  POS/a_Editar'), 5)

List<WebElement> botonesEditar = DriverFactory.getWebDriver().findElements(By.xpath("//a[normalize-space()='Editar']"))
int indicePlatillo = rand.nextInt(botonesEditar.size())
botonesEditar.get(indicePlatillo).click()

// 3. MODIFICAR SOLO LA DESCRIPCIÓN
TestObject campoDescripcion = findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/textarea_Descripcin (opcional)')
WebUI.waitForElementVisible(campoDescripcion, 5)
WebUI.setText(campoDescripcion, nuevaDescripcion)

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/button_Guardar cambios'))

// 4. IMPRIMIR EN CONSOLA
KeywordUtil.logInfo("PRUEBA CP003.16 (Cambio de descripción) -> Platillo #${indicePlatillo + 1} | Nueva descripción: '${nuevaDescripcion}'")