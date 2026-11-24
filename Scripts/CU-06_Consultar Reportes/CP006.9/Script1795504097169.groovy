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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.util.Random as Random
import java.util.Arrays as Arrays
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter

Random rand = new Random()

// ==========================================
// 1. LOGIN CON EMPLEADO ALEATORIO
// ==========================================
def empleados = [
    [usuario: 'carlosm', password: 'Carlos123'],
    [usuario: 'Mike',    password: 'Mike1234']
]

def usuarioElegido = empleados[rand.nextInt(empleados.size())]

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.comment("Iniciando sesión con el empleado: " + usuarioElegido.usuario)
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/input_Usuario ID'), 
    usuarioElegido.usuario)

WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/input_Contrasea'), 
    usuarioElegido.password)

WebUI.sendKeys(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/input_Contrasea'), 
    Keys.chord(Keys.ENTER))

WebUI.delay(2)

// ==========================================
// 2. INTENTAR ACCEDER A LA URL DE REPORTES
// ==========================================
String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
String urlReportes = "https://grill-and-chill-production-1766.up.railway.app/admin/reportes?desde=" + fechaHoy + "&hasta=" + fechaHoy

WebUI.comment("Intentando acceder sin permisos a: " + urlReportes)

// Captura el código HTTP real usando la sesión activa del navegador
String jsFetchStatus = """
    var callback = arguments[arguments.length - 1];
    fetch(arguments[0], { credentials: 'same-origin' })
        .then(res => callback(res.status))
        .catch(err => callback(-1));
"""
def statusCode = WebUI.executeJavaScript(
    "var xhr = new XMLHttpRequest(); xhr.open('GET', arguments[0], false); xhr.send(null); return xhr.status;",
    Arrays.asList(urlReportes)
)

// Navega visualmente a la URL para dejar evidencia en pantalla
WebUI.navigateToUrl(urlReportes)
WebUI.delay(2)

// ==========================================
// 3. VERIFICAR RESPUESTA HTTP 403 / ACCESO DENEGADO
// ==========================================
String textoPagina = WebUI.executeJavaScript("return document.body.innerText;", null).toLowerCase()
WebUI.comment("Código HTTP recibido: " + statusCode)

boolean es403 = (statusCode == 403) || textoPagina.contains("403") || textoPagina.contains("denegado") || textoPagina.contains("forbidden") || textoPagina.contains("no autorizado")

if (es403) {
    KeywordUtil.markPassed("Validación exitosa: el sistema denegó el acceso a Reportes para el empleado '" + usuarioElegido.usuario + "' (HTTP " + statusCode + ").")
} else {
    KeywordUtil.markFailed("Fallo de seguridad: se esperaba HTTP 403 / Acceso denegado, pero se obtuvo HTTP " + statusCode + ".")
}