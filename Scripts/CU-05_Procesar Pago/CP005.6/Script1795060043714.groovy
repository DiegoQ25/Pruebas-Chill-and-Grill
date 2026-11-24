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
// 1. SELECCIONAR DOS EMPLEADOS DISTINTOS AL AZAR
// ==========================================
def empleados = [
    [usuario: 'carlosm', password: 'Carlos123'],
    [usuario: 'Mike',    password: 'Mike1234']
]

// Mezcla la lista para que el orden sea aleatorio pero siempre sean dos usuarios diferentes
Collections.shuffle(empleados)
def empleado1 = empleados[0]
def empleado2 = empleados[1]

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

// ==========================================
// 2. LOGIN EMPLEADO 1
// ==========================================
WebUI.comment("Empleado 1 iniciando sesión: " + empleado1.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/input_Usuario ID'), 
    empleado1.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/input_Contrasea'), 
    empleado1.password)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 3. ABRIR CUENTA CON MESA ALEATORIA Y NOMBRE ÚNICO
// ==========================================
WebUI.waitForElementClickable(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

int indiceMesa = rand.nextInt(7)
WebUI.selectOptionByIndex(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    indiceMesa)

// Se asigna un nombre único al comensal para comprobar después que el otro empleado no lo ve
String nombreComensal = "ComandaPrivada_" + (1000 + rand.nextInt(9000))
TestObject inputComensal = new TestObject('InputComensal')
inputComensal.addProperty('xpath', ConditionType.EQUALS, "//input[@placeholder='Pedro']")
WebUI.setText(inputComensal, nombreComensal)
WebUI.comment("Cuenta creada por " + empleado1.usuario + ": " + nombreComensal)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

// ==========================================
// 4. SELECCIONAR 3 O 4 PLATILLOS ALEATORIAMENTE
// ==========================================
TestObject objetoPlatillos = new TestObject('PlatillosDinamicos')
objetoPlatillos.addProperty('xpath', ConditionType.EQUALS, "//*[normalize-space(text())='Platillos']/following-sibling::*//button")
List<WebElement> listaPlatillos = WebUI.findWebElements(objetoPlatillos, 10)

if (listaPlatillos.size() > 0) {
    Collections.shuffle(listaPlatillos)
    int cantidadElegir = rand.nextBoolean() ? 3 : 4
    int limite = Math.min(cantidadElegir, listaPlatillos.size())
    
    for (int i = 0; i < limite; i++) {
        listaPlatillos.get(i).click()
        WebUI.delay(1)
    }
}

// ==========================================
// 5. CERRAR SESIÓN DEL EMPLEADO 1
// ==========================================
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Punto de Venta  POS/button_Salir'))
WebUI.waitForElementVisible(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/input_Usuario ID'), 10)

// ==========================================
// 6. LOGIN EMPLEADO 2
// ==========================================
WebUI.comment("Empleado 2 iniciando sesión: " + empleado2.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/input_Usuario ID'), 
    empleado2.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/input_Contrasea'), 
    empleado2.password)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.6/Page_Control de Acceso  POS/button_Iniciar Sesin'))
WebUI.delay(2)

// ==========================================
// 7. VERIFICAR QUE EL EMPLEADO 2 NO VISUALIZA LA COMANDA
// ==========================================
TestObject comandaAjena = new TestObject('ComandaAjena')
comandaAjena.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(), '" + nombreComensal + "')]")

WebUI.verifyElementNotPresent(comandaAjena, 5)
WebUI.comment("Validación exitosa: " + empleado2.usuario + " no puede visualizar la comanda '" + nombreComensal + "' creada por " + empleado1.usuario + ".")