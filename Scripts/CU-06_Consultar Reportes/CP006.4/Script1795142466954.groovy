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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import java.util.Random as Random
import java.util.Arrays as Arrays
import java.time.LocalDate as LocalDate
import java.time.DayOfWeek as DayOfWeek
import java.time.format.DateTimeFormatter as DateTimeFormatter

Random rand = new Random()

// ==========================================
// 1. LOGIN COMO ADMINISTRADOR
// ==========================================
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.sendKeys(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Control de Acceso  POS/input_Contrasea'), Keys.chord(Keys.ENTER))

// ==========================================
// 2. SELECCIONAR UN FIN DE SEMANA ALEATORIO (SÁBADO Y DOMINGO)
// ==========================================
LocalDate hoy = LocalDate.now()
LocalDate primerDiaMes = hoy.withDayOfMonth(1)
List<LocalDate> sabadosDisponibles = new ArrayList<>()

// Recorre el mes actual buscando sábados cuyo domingo no supere la fecha de hoy
LocalDate fechaCursor = primerDiaMes
while (!fechaCursor.isAfter(hoy.minusDays(1))) {
    if (fechaCursor.getDayOfWeek() == DayOfWeek.SATURDAY) {
        sabadosDisponibles.add(fechaCursor)
    }
    fechaCursor = fechaCursor.plusDays(1)
}

// Respaldo por si se ejecuta en los primeros días del mes y aún no hay un fin de semana completo
if (sabadosDisponibles.isEmpty()) {
    LocalDate ultimoSabado = hoy.minusDays(2)
    while (ultimoSabado.getDayOfWeek() != DayOfWeek.SATURDAY) {
        ultimoSabado = ultimoSabado.minusDays(1)
    }
    sabadosDisponibles.add(ultimoSabado)
}

LocalDate sabadoElegido = sabadosDisponibles.get(rand.nextInt(sabadosDisponibles.size()))
LocalDate domingoElegido = sabadoElegido.plusDays(1)

DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
String fechaInicio = sabadoElegido.format(formato)
String fechaFin = domingoElegido.format(formato)

WebUI.comment("Fin de semana seleccionado (días de descanso): " + fechaInicio + " (Sábado) a " + fechaFin + " (Domingo)")

// ==========================================
// 3. INYECTAR FECHAS CON JAVASCRIPT Y BUSCAR VENTAS
// ==========================================
TestObject objDesde = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Reportes Generales  POS/input_Desde la fecha')
TestObject objHasta = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Reportes Generales  POS/input_Hasta la fecha')

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

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Reportes Generales  POS/button_Buscar Ventas'))
WebUI.delay(2)

// ==========================================
// 4. VERIFICAR QUE NO HUBO VENTAS Y NO ARROJÓ ERROR
// ==========================================
WebUI.verifyElementVisible(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.4/Page_Reportes Generales  POS/p_Sin ventas en el rango seleccionado'))
WebUI.comment("Validación exitosa: el sistema mostró el rango sin ventas correctamente y sin errores.")