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
import java.time.format.DateTimeFormatter as DateTimeFormatter

Random rand = new Random()

// ==========================================
// 1. LOGIN COMO ADMINISTRADOR
// ==========================================
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. GENERAR DOS FECHAS DISTINTAS DEL MES ACTUAL (INICIO > FIN)
// ==========================================
LocalDate hoy = LocalDate.now()
int diaActual = hoy.getDayOfMonth()

// Asegura que haya al menos 2 días de diferencia para poder invertirlas
int diaMenor = (diaActual > 1) ? (rand.nextInt(diaActual - 1) + 1) : 1
int diaMayor = (diaActual > 1) ? (diaMenor + rand.nextInt(diaActual - diaMenor) + 1) : 1

DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
String fechaMenor = (diaActual > 1) ? hoy.withDayOfMonth(diaMenor).format(formato) : hoy.minusDays(1).format(formato)
String fechaMayor = hoy.withDayOfMonth(diaMayor).format(formato)

WebUI.comment("Capturando rango invertido a propósito -> Desde: " + fechaMayor + " | Hasta: " + fechaMenor)

// ==========================================
// 3. INYECTAR FECHAS INVERTIDAS CON JAVASCRIPT Y BUSCAR
// ==========================================
TestObject objDesde = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Reportes Generales  POS/input_Desde la fecha')
TestObject objHasta = findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Reportes Generales  POS/input_Hasta la fecha')

WebUI.waitForElementVisible(objDesde, 10)

WebElement elDesde = WebUiCommonHelper.findWebElement(objDesde, 10)
WebElement elHasta = WebUiCommonHelper.findWebElement(objHasta, 10)

String jsSetFecha = """
    arguments[0].value = arguments[1];
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
"""

// Se coloca la fecha MAYOR en 'Desde' y la fecha MENOR en 'Hasta'
WebUI.executeJavaScript(jsSetFecha, Arrays.asList(elDesde, fechaMayor))
WebUI.executeJavaScript(jsSetFecha, Arrays.asList(elHasta, fechaMenor))

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.3/Page_Reportes Generales  POS/button_Buscar Ventas'))
WebUI.delay(2)

// ==========================================
// 4. VERIFICAR QUE EL SISTEMA INVIRTIÓ EL RANGO AUTOMÁTICAMENTE
// ==========================================
String valorDesdeFinal = WebUI.getAttribute(objDesde, 'value')
String valorHastaFinal = WebUI.getAttribute(objHasta, 'value')

WebUI.comment("Rango corregido por el sistema -> Desde: " + valorDesdeFinal + " | Hasta: " + valorHastaFinal)

WebUI.verifyMatch(valorDesdeFinal, fechaMenor, false)
WebUI.verifyMatch(valorHastaFinal, fechaMayor, false)

WebUI.comment("Validación exitosa: el sistema invirtió automáticamente el rango de fechas al orden correcto.")