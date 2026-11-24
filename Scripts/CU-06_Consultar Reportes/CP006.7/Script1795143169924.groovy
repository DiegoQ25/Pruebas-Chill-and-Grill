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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import java.util.Arrays as Arrays
import java.util.Random as Random
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter

// ==========================================
// 1. LOGIN COMO ADMINISTRADOR
// ==========================================
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. GENERAR RANGO DE FECHAS ALEATORIO DEL MES ACTUAL (SIN SUPERAR HOY)
// ==========================================
Random rand = new Random()
LocalDate hoy = LocalDate.now()
int diaActual = hoy.getDayOfMonth()

int dia1 = rand.nextInt(diaActual) + 1
int dia2 = rand.nextInt(diaActual) + 1
int diaInicio = Math.min(dia1, dia2)
int diaFin = Math.max(dia1, dia2)

DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
String fechaInicio = hoy.withDayOfMonth(diaInicio).format(formato)
String fechaFin = hoy.withDayOfMonth(diaFin).format(formato)

WebUI.comment("Periodo aleatorio seleccionado: " + fechaInicio + " a " + fechaFin)

// ==========================================
// 3. INYECTAR FECHAS CON JAVASCRIPT Y BUSCAR
// ==========================================
TestObject objDesde = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Reportes Generales  POS/input_Desde la fecha')
TestObject objHasta = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Reportes Generales  POS/input_Hasta la fecha')

WebUI.waitForElementVisible(objDesde, 10)
WebElement elDesde = WebUiCommonHelper.findWebElement(objDesde, 10)
WebElement elHasta = WebUiCommonHelper.findWebElement(objHasta, 10)

String jsSetFecha = """
    arguments[0].value = arguments[1];
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
"""

WebUI.executeJavaScript(jsSetFecha, Arrays.asList(elDesde, fechaInicio))
WebUI.executeJavaScript(jsSetFecha, Arrays.asList(elHasta, fechaFin))

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.7/Page_Reportes Generales  POS/button_Buscar Ventas'))
WebUI.delay(2)

// ==========================================
// 4. OBTENER EL VALOR DEL INDICADOR "COMANDAS CANCELADAS"
// ==========================================
TestObject objCanceladas = new TestObject('ComandasCanceladasValor')
objCanceladas.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(), 'Comandas canceladas') or contains(text(), 'Comandas Canceladas')]/following::p[1]")
WebUI.waitForElementVisible(objCanceladas, 10)

String textoCanceladas = WebUI.getText(objCanceladas).trim()
int canceladasMostradas = Integer.parseInt(textoCanceladas.replaceAll("[^0-9]", ""))
WebUI.comment("Comandas canceladas mostradas en el indicador: " + canceladasMostradas)

// ==========================================
// 5. CONTABILIZAR SOLO LAS COMANDAS CON ESTATUS "CANCELADA" EN EL HISTORIAL
// ==========================================
TestObject filasHistorial = new TestObject('FilasHistorial')
filasHistorial.addProperty('xpath', ConditionType.EQUALS, "//table//tbody//tr")
List<WebElement> filas = WebUI.findWebElements(filasHistorial, 10)

int totalCanceladas = 0

for (WebElement fila : filas) {
    String textoMinusculas = fila.getText().trim().toLowerCase()
    
    if (textoMinusculas.contains("cancelada")) {
        totalCanceladas++
    }
}

WebUI.comment("Total de comandas con estatus 'cancelada' en el historial: " + totalCanceladas)

// ==========================================
// 6. VERIFICAR QUE COINCIDAN EXACTAMENTE
// ==========================================
if (canceladasMostradas == totalCanceladas) {
    KeywordUtil.markPassed("Validación exitosa: Comandas canceladas (" + canceladasMostradas + ") coincide exactamente con las comandas anuladas en el historial (" + totalCanceladas + ").")
} else {
    KeywordUtil.markFailed("Diferencia detectada: el indicador muestra " + canceladasMostradas + ", pero se contabilizaron " + totalCanceladas + " comandas canceladas en el historial.")
}