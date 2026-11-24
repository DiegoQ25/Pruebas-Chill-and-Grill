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
import org.openqa.selenium.By as By
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
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.waitForElementClickable(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Reportes Generales  POS/span_Reportes Generales'), 10)
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Reportes Generales  POS/span_Reportes Generales'))
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
// 3. INYECTAR FECHAS CON JAVASCRIPT Y BUSCAR VENTAS
// ==========================================
TestObject objDesde = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Reportes Generales  POS/input_Desde la fecha')
TestObject objHasta = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Reportes Generales  POS/input_Hasta la fecha')

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

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.8/Page_Reportes Generales  POS/button_Buscar Ventas'))
WebUI.delay(2)

// ==========================================
// 4. VERIFICAR ORDEN DESCENDENTE (MAYOR A MENOR) DE PLATILLOS VENDIDOS
// ==========================================
TestObject filasPlatillos = new TestObject('FilasPlatillos')
filasPlatillos.addProperty('xpath', ConditionType.EQUALS, "(//table)[1]//tbody//tr")
List<WebElement> filas = WebUI.findWebElements(filasPlatillos, 10)

List<Integer> piezasVendidas = new ArrayList<>()

for (WebElement fila : filas) {
    String textoFila = fila.getText().trim().toLowerCase()
    
    // Ignora filas de mensajes vacíos o de la tabla de comandas
    if (textoFila.contains("pagada") || textoFila.contains("cancelada") || textoFila.contains("abierta") || textoFila.contains("sin ventas")) {
        continue
    }
    
    List<WebElement> celdas = fila.findElements(By.tagName("td"))
    for (WebElement celda : celdas) {
        String valor = celda.getText().trim()
        // Detecta la celda que contiene únicamente el número entero de piezas vendidas
        if (valor.matches("\\d+")) {
            piezasVendidas.add(Integer.parseInt(valor))
            break
        }
    }
}

WebUI.comment("Piezas vendidas detectadas en orden: " + piezasVendidas.toString())

if (piezasVendidas.isEmpty()) {
    WebUI.comment("No se registraron platillos vendidos en el rango aleatorio (" + fechaInicio + " a " + fechaFin + ").")
} else {
    boolean ordenadoDescendente = true
    for (int i = 0; i < piezasVendidas.size() - 1; i++) {
        if (piezasVendidas.get(i) < piezasVendidas.get(i + 1)) {
            ordenadoDescendente = false
            break
        }
    }
    
    if (ordenadoDescendente) {
        KeywordUtil.markPassed("Validación exitosa: los platillos están ordenados estrictamente de mayor a menor por piezas vendidas " + piezasVendidas.toString() + ".")
    } else {
        KeywordUtil.markFailed("Error de ordenamiento: la lista de piezas vendidas no está de mayor a menor " + piezasVendidas.toString() + ".")
    }
}