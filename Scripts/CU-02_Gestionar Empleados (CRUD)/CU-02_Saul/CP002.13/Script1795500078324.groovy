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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
// Imports adicionales necesarios para manipular la tabla
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.openBrowser(null)

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.setText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Control de Acceso  POS/input_Usuario ID'), 
    'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Control de Acceso  POS/input_Contrasea'), 
    '/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Reportes Generales  POS/a_Gestin de Empleados'))

// --- INICIO DE LÓGICA DE SELECCIÓN ALEATORIA ---
WebUI.waitForPageLoad(10)

WebDriver driver = DriverFactory.getWebDriver()

List<String> usuariosExcluidos = ['admin', 'carlosm']

int indiceColumnaUsuario = 2 // 0 = ID, 1 = Nombre completo, 2 = Usuario

List<WebElement> todasLasFilas = driver.findElements(By.cssSelector('table tbody tr'))

List<WebElement> filasValidas = todasLasFilas.findAll({ WebElement fila ->
        List<WebElement> celdas = fila.findElements(By.tagName('td'))

        if (celdas.size() <= indiceColumnaUsuario) {
            return false
        }
        
        String usuarioId = (celdas[indiceColumnaUsuario]).getText().trim().toLowerCase()

        return !(usuariosExcluidos.contains(usuarioId))
    })

if (filasValidas.isEmpty()) {
    KeywordUtil.markFailedAndStop('No hay usuarios disponibles para editar después de excluir a admin y carlosm.')
}

// Elegir fila al azar y hacer clic en su enlace "Editar"
WebElement filaElegida = filasValidas.get(new Random().nextInt(filasValidas.size()))

String usuarioElegido = (filaElegida.findElements(By.tagName('td'))[indiceColumnaUsuario]).getText().trim()

WebUI.comment("Usuario elegido al azar: $usuarioElegido")

WebElement botonEditar = filaElegida.findElement(By.xpath('.//a[contains(text(), \'Editar\')]'))

botonEditar.click()

// --- CAMBIO DINÁMICO DE ROL ---
TestObject selectRolObj = findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Editar empleado  POS/select_Rol')

WebUI.waitForElementVisible(selectRolObj, 10)

// Leer el valor actual del <select> y alternarlo
WebElement selectElement = WebUI.findWebElement(selectRolObj, 10)

String rolActual = selectElement.getAttribute('value').trim().toLowerCase()

String nuevoRol = rolActual == 'admin' ? 'empleado' : 'admin'

WebUI.comment("Cambiando rol de $usuarioElegido: $rolActual -> $nuevoRol")

WebUI.selectOptionByValue(selectRolObj, nuevoRol, false)

// --- FIN DE LÓGICA ---
WebUI.click(findTestObject('Object Repository_CU02_Gestionar Empleados/Object Repository_CU02_Saul/Page_Editar empleado  POS/button_Guardar cambios'))

