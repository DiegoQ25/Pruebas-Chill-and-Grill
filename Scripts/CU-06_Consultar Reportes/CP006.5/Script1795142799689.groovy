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
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.5/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.5/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.5/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.waitForElementClickable(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.5/Page_Reportes Generales  POS/span_Reportes Generales'), 10)
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.5/Page_Reportes Generales  POS/span_Reportes Generales'))
WebUI.delay(2)

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
TestObject objDesde = new TestObject('InputDesde')
objDesde.addProperty('xpath', ConditionType.EQUALS, "(//input[@type='date'])[1]")

TestObject objHasta = new TestObject('InputHasta')
objHasta.addProperty('xpath', ConditionType.EQUALS, "(//input[@type='date'])[2]")

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

TestObject btnBuscar = new TestObject('BtnBuscarVentas')
btnBuscar.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Buscar Ventas')]")
WebUI.click(btnBuscar)
WebUI.delay(2)

// ==========================================
// 4. OBTENER EL VALOR MOSTRADO EN "INGRESOS NETOS"
// ==========================================
TestObject objIngresosNetos = new TestObject('IngresosNetos')
objIngresosNetos.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(), 'Ingresos Netos')]/following::p[contains(., '\$')][1]")
WebUI.waitForElementVisible(objIngresosNetos, 10)

String textoIngresos = WebUI.getText(objIngresosNetos).trim()
double ingresosMostrados = Double.parseDouble(textoIngresos.replaceAll("[^0-9.]", ""))
WebUI.comment("Ingresos Netos mostrados en el indicador: " + textoIngresos + " (" + ingresosMostrados + ")")

// ==========================================
// 5. SUMAR SOLO LAS COMANDAS CON ESTATUS "PAGADA" EN EL HISTORIAL
// ==========================================
TestObject filasHistorial = new TestObject('FilasHistorial')
filasHistorial.addProperty('xpath', ConditionType.EQUALS, "//table//tbody//tr")
List<WebElement> filas = WebUI.findWebElements(filasHistorial, 10)

double sumaPagadas = 0.0
int conteoPagadas = 0
int conteoOmitidas = 0

for (WebElement fila : filas) {
    String textoFila = fila.getText().trim()
    String textoMinusculas = textoFila.toLowerCase()
    
    if (textoMinusculas.contains("pagada")) {
        def matcher = (textoFila =~ /\$([0-9,]+\.\d{2})/)
        if (matcher.find()) {
            String montoStr = ""
            matcher.reset()
            while (matcher.find()) {
                montoStr = matcher.group(1)
            }
            double monto = Double.parseDouble(montoStr.replace(",", ""))
            sumaPagadas += monto
            conteoPagadas++
        }
    } else if (textoMinusculas.contains("cancelada") || textoMinusculas.contains("abierta")) {
        conteoOmitidas++
    }
}

sumaPagadas = Math.round(sumaPagadas * 100.0) / 100.0
WebUI.comment("Comandas pagadas sumadas: " + conteoPagadas + " | Comandas canceladas/abiertas omitidas: " + conteoOmitidas)
WebUI.comment("Total calculado solo de pagadas: \$" + String.format(Locale.US, "%.2f", sumaPagadas))

// ==========================================
// 6. VERIFICAR QUE COINCIDAN EXACTAMENTE
// ==========================================
if (Math.abs(ingresosMostrados - sumaPagadas) < 0.01) {
    KeywordUtil.markPassed("Validación exitosa: Ingresos Netos (\$" + ingresosMostrados + ") coincide exactamente con la suma de comandas pagadas (\$" + sumaPagadas + ").")
} else {
    KeywordUtil.markFailed("Diferencia detectada: Ingresos Netos muestra \$" + ingresosMostrados + ", pero la suma de comandas pagadas es \$" + sumaPagadas + ".")
}