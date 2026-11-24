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
// 1. LOGIN CON EMPLEADO ALEATORIO
// ==========================================
def empleados = [
    [usuario: 'carlosm', password: 'Carlos123'],
    [usuario: 'Mike',    password: 'Mike1234']
]

def usuarioElegido = empleados[rand.nextInt(empleados.size())]

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.comment("Iniciando sesión con el empleado: " + usuarioElegido.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.5/Page_Control de Acceso  POS/input_Usuario ID'), 
    usuarioElegido.usuario)

WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.5/Page_Control de Acceso  POS/input_Contrasea'), 
    usuarioElegido.password)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.5/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.delay(2)

// ==========================================
// 2. VERIFICAR QUE NO EXISTAN COMANDAS CANCELADAS VISIBLES
// ==========================================
TestObject comandaCancelada = new TestObject('ComandaCancelada')
comandaCancelada.addProperty('xpath', ConditionType.EQUALS, "//*[contains(translate(text(), 'CANCELADA', 'cancelada'), 'cancelada')]")

// Valida que NO esté presente en pantalla (impidiendo seleccionarla y cobrarla)
WebUI.verifyElementNotPresent(comandaCancelada, 5)
WebUI.comment("Validación exitosa: no se visualizan comandas canceladas en el Punto de Venta, impidiendo su cobro.")