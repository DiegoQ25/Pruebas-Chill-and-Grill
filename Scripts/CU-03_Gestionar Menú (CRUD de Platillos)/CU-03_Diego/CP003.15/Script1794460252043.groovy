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

// 1. IMPORTAR UTILIDADES PARA ELEMENTOS DINÁMICOS Y ALEATORIEDAD
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select
import java.util.Random

Random rand = new Random()

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

// 3. SELECCIONAR UNA CATEGORÍA ALEATORIA (DISTINTA A LA ACTUAL Y SIN "Selecciona...")
TestObject objSelect = findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/select_Categora')
WebUI.waitForElementVisible(objSelect, 5)

Select selectCategoria = new Select(WebUiCommonHelper.findWebElement(objSelect, 5))
String categoriaActual = selectCategoria.getFirstSelectedOption().getText().trim()

// Filtrar opciones válidas: ignorar "Selecciona..." (value vacío) y la categoría actual
List<WebElement> opcionesValidas = selectCategoria.getOptions().findAll { opcion ->
	String valor = opcion.getAttribute('value')
	String texto = opcion.getText().trim()
	return valor != null && !valor.isEmpty() && texto != categoriaActual && !texto.contains('Selecciona')
}

WebElement opcionElegida = opcionesValidas[rand.nextInt(opcionesValidas.size())]
String nuevaCategoria = opcionElegida.getText().trim()
selectCategoria.selectByValue(opcionElegida.getAttribute('value'))

WebUI.click(findTestObject('Object Repository_CU03_Gestionar Menu/Object Repository_CU03_Diego/Page_Editar platillo  POS/button_Guardar cambios'))

// 4. IMPRIMIR EN CONSOLA
KeywordUtil.logInfo("PRUEBA CP003.15 (Cambio de categoría) -> Platillo #${indicePlatillo + 1} | Anterior: '${categoriaActual}' -> Nueva: '${nuevaCategoria}'")