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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import java.util.Random as Random
import java.util.Collections as Collections

Random rand = new Random()

// ==========================================
// 1. LOGIN COMO ADMINISTRADOR
// ==========================================
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. IR A PUNTO DE VENTA Y ABRIR CUENTA CON MESA ALEATORIA
// ==========================================
WebUI.waitForElementClickable(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Reportes Generales  POS/a_Punto de Venta'), 10)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Reportes Generales  POS/a_Punto de Venta'))

WebUI.waitForElementClickable(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

int indiceMesa = rand.nextInt(7)
WebUI.selectOptionByIndex(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    indiceMesa)
WebUI.comment("Índice de mesa seleccionado: " + indiceMesa)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.12/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// ==========================================
// 3. SELECCIONAR 3 O 4 PLATILLOS ALEATORIAMENTE
// ==========================================
TestObject objetoPlatillos = new TestObject('PlatillosDinamicos')
objetoPlatillos.addProperty('xpath', ConditionType.EQUALS, "//*[normalize-space(text())='Platillos']/following-sibling::*//button")

List<WebElement> listaPlatillos = WebUI.findWebElements(objetoPlatillos, 10)

if (listaPlatillos.size() > 0) {
    Collections.shuffle(listaPlatillos)
    int cantidadElegir = rand.nextBoolean() ? 3 : 4
    int limite = Math.min(cantidadElegir, listaPlatillos.size())
    
    WebUI.comment("Se agregarán " + limite + " platillos al azar.")
    
    for (int i = 0; i < limite; i++) {
        WebElement platillo = listaPlatillos.get(i)
        WebUI.comment("Agregando platillo: " + platillo.getText().replace('\n', ' '))
        platillo.click()
        WebUI.delay(1)
    }
}

// ==========================================
// 4. INTENTAR COBRAR
// ==========================================
TestObject btnCobrar = new TestObject('BtnCobrarDinamico')
btnCobrar.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Cobrar')]")

WebUI.waitForElementVisible(btnCobrar, 10)
WebUI.scrollToElement(btnCobrar, 5)
WebUI.delay(1)
WebUI.enhancedClick(btnCobrar)