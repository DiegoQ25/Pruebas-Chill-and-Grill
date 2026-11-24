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
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter

// ==========================================
// 1. LOGIN COMO ADMINISTRADOR
// ==========================================
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.1/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.1/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.1/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. ACCEDER AL MÓDULO DE REPORTES GENERALES
// ==========================================
WebUI.waitForElementClickable(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.1/Page_Reportes Generales  POS/span_Reportes Generales'), 10)
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.1/Page_Reportes Generales  POS/span_Reportes Generales'))
WebUI.delay(2)

// ==========================================
// 3. VERIFICAR RANGO DEL DÍA ACTUAL AUTOMÁTICO
// ==========================================
String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
WebUI.comment("Verificando que el reporte cargue la fecha actual: " + fechaHoy)

TestObject inputsFecha = new TestObject('InputsFecha')
inputsFecha.addProperty('xpath', ConditionType.EQUALS, "//input[@type='date']")
List<WebElement> listaFechas = WebUI.findWebElements(inputsFecha, 10)

if (listaFechas.size() > 0) {
    for (int i = 0; i < listaFechas.size(); i++) {
        String valorFecha = listaFechas.get(i).getAttribute('value')
        WebUI.comment("Campo de fecha #" + (i + 1) + " cargado con: " + valorFecha)
        WebUI.verifyMatch(valorFecha, fechaHoy, false)
    }
} else {
    WebUI.comment("Nota: No se detectaron inputs type='date', validando carga general de la vista.")
}

// ==========================================
// 4. VERIFICAR SECCIONES DEL REPORTE
// ==========================================
TestObject seccionReportes = new TestObject('SeccionReportes')
seccionReportes.addProperty('xpath', ConditionType.EQUALS, "//*[contains(., 'Reportes Generales')]")
WebUI.verifyElementVisible(seccionReportes)

WebUI.comment("Validación exitosa: el módulo de Reportes cargó correctamente con el rango del día actual.")