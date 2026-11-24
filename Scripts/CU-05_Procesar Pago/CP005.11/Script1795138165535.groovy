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
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/input_Usuario ID'), 
    usuarioElegido.usuario)
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/input_Contrasea'), 
    usuarioElegido.password)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/button_Iniciar Sesin'))

// ==========================================
// 2. ABRIR CUENTA CON MESA Y PLATILLOS ALEATORIOS
// ==========================================
WebUI.waitForElementClickable(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'), 10)
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

int indiceMesa = rand.nextInt(7)
WebUI.selectOptionByIndex(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    indiceMesa)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Punto de Venta  POS/button_Abrir cuenta'))
WebUI.delay(1)

TestObject objetoPlatillos = new TestObject('PlatillosDinamicos')
objetoPlatillos.addProperty('xpath', ConditionType.EQUALS, "//*[normalize-space(text())='Platillos']/following-sibling::*//button")
List<WebElement> listaPlatillos = WebUI.findWebElements(objetoPlatillos, 10)

String nombrePlatilloEditar = ""

if (listaPlatillos.size() > 0) {
    Collections.shuffle(listaPlatillos)
    int cantidadElegir = rand.nextBoolean() ? 3 : 4
    int limite = Math.min(cantidadElegir, listaPlatillos.size())
    
    for (int i = 0; i < limite; i++) {
        WebElement platillo = listaPlatillos.get(i)
        String textoCompleto = platillo.getText().trim()
        
        // Guarda el nombre del primer platillo elegido para editar su precio después
        if (i == 0) {
            nombrePlatilloEditar = textoCompleto.split('\n')[0].trim()
        }
        
        WebUI.comment("Agregando platillo: " + textoCompleto.replace('\n', ' '))
        platillo.click()
        WebUI.delay(1)
    }
}

// ==========================================
// 3. COBRAR Y GUARDAR DATOS DEL TICKET ORIGINAL
// ==========================================
TestObject btnCobrar = new TestObject('BtnCobrarDinamico')
btnCobrar.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Cobrar')]")
WebUI.waitForElementVisible(btnCobrar, 10)
WebUI.scrollToElement(btnCobrar, 5)
WebUI.delay(1)
WebUI.enhancedClick(btnCobrar)

TestObject linkVerTicket = new TestObject('LinkVerTicketDinamico')
linkVerTicket.addProperty('xpath', ConditionType.EQUALS, "//a[contains(., 'Ver ticket')]")
WebUI.waitForElementClickable(linkVerTicket, 10)
WebUI.click(linkVerTicket)
WebUI.delay(2)

String urlTicket = WebUI.getUrl()
TestObject tdTotal = new TestObject('TdTotalTicket')
tdTotal.addProperty('xpath', ConditionType.EQUALS, "(//td[contains(., '\$')])[last()]")
String totalOriginal = WebUI.getText(tdTotal).trim()

WebUI.comment("URL del ticket guardada: " + urlTicket)
WebUI.comment("Total original congelado en el ticket: " + totalOriginal)

// ==========================================
// 4. CERRAR SESIÓN E INICIAR COMO ADMINISTRADOR
// ==========================================
TestObject linkVolverPOS = new TestObject('LinkVolverPOSDinamico')
linkVolverPOS.addProperty('xpath', ConditionType.EQUALS, "//a[contains(., 'Volver al POS')]")
WebUI.click(linkVolverPOS)
WebUI.delay(1)

TestObject btnSalir = new TestObject('BtnSalirDinamico')
btnSalir.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Salir')]")
WebUI.waitForElementClickable(btnSalir, 10)
WebUI.click(btnSalir)

WebUI.waitForElementVisible(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/input_Usuario ID'), 10)
WebUI.comment("Iniciando sesión como Administrador: admin")
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/input_Contrasea'), 'Admin123')
WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.11/Page_Control de Acceso  POS/button_Iniciar Sesin'))
WebUI.delay(2)

// ==========================================
// 5. CAMBIO DE PRECIO POR EL ADMINISTRADOR
// ==========================================
TestObject linkCatalogo = new TestObject('LinkCatalogoMenu')
linkCatalogo.addProperty('xpath', ConditionType.EQUALS, "//a[contains(., 'Catálogo del Menú')]")
WebUI.waitForElementClickable(linkCatalogo, 10)
WebUI.click(linkCatalogo)
WebUI.delay(2)

WebUI.comment("Buscando en el catálogo el platillo a modificar: " + nombrePlatilloEditar)
TestObject btnEditarPlatillo = new TestObject('BtnEditarPlatillo')
btnEditarPlatillo.addProperty('xpath', ConditionType.EQUALS, "//tr[contains(., '" + nombrePlatilloEditar + "')]//*[normalize-space(.)='Editar']")
WebUI.waitForElementClickable(btnEditarPlatillo, 10)
WebUI.scrollToElement(btnEditarPlatillo, 5)
WebUI.click(btnEditarPlatillo)
WebUI.delay(2)

// Lee el precio actual y le suma 10.00 para garantizar un cambio real
TestObject inputPrecio = new TestObject('InputPrecioVenta')
inputPrecio.addProperty('xpath', ConditionType.EQUALS, "//label[contains(., 'Precio de venta')]/following::input[1]")
WebUI.waitForElementVisible(inputPrecio, 10)

String precioActualStr = WebUI.getAttribute(inputPrecio, 'value')
double nuevoPrecio = Double.parseDouble(precioActualStr) + 10.00
String nuevoPrecioStr = String.format(Locale.US, "%.2f", nuevoPrecio)

WebUI.comment("Cambiando precio de '" + nombrePlatilloEditar + "' de " + precioActualStr + " a " + nuevoPrecioStr)
WebUI.setText(inputPrecio, nuevoPrecioStr)

TestObject btnGuardarCambios = new TestObject('BtnGuardarCambios')
btnGuardarCambios.addProperty('xpath', ConditionType.EQUALS, "//button[contains(., 'Guardar cambios')]")
WebUI.click(btnGuardarCambios)
WebUI.delay(2)

// ==========================================
// 6. REVISAR EL TICKET Y VERIFICAR SNAPSHOT JSON
// ==========================================
WebUI.navigateToUrl(urlTicket)
WebUI.delay(2)

String totalDespues = WebUI.getText(tdTotal).trim()
WebUI.verifyMatch(totalDespues, totalOriginal, false)
WebUI.comment("Validación exitosa: tras cambiar el precio de '" + nombrePlatilloEditar + "', el ticket mantuvo el monto original (" + totalOriginal + ") gracias al snapshot JSON.")