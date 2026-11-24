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
import java.util.Random as Random

Random rand = new Random()

// ==========================================
// 1. LISTA DE EMPLEADOS ACTIVOS Y LOGIN ALEATORIO
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
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Control de Acceso  POS/input_Usuario ID'), 
    usuarioElegido.usuario)

WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Control de Acceso  POS/input_Contrasea'), 
    usuarioElegido.password)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. ABRIR CUENTA NUEVA CON MESA ALEATORIA (SIN PLATILLOS)
// ==========================================
WebUI.waitForElementClickable(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

int indiceMesa = rand.nextInt(7)
WebUI.selectOptionByIndex(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    indiceMesa)
WebUI.comment("Índice de mesa seleccionado: " + indiceMesa)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.3/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// ==========================================
// 3. VERIFICAR QUE EL BOTÓN COBRAR ESTÉ DESACTIVADO
// ==========================================
TestObject btnCobrar = new TestObject('BtnCobrarDinamico')
btnCobrar.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Cobrar')]")

WebUI.waitForElementVisible(btnCobrar, 10)
WebUI.scrollToElement(btnCobrar, 5)
WebUI.verifyElementNotClickable(btnCobrar)
WebUI.comment("Validación exitosa: el botón Cobrar se encuentra desactivado sin platillos.")
