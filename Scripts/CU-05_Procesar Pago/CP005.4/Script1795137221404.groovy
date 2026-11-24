import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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
WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.4/Page_Control de Acceso  POS/input_Usuario ID'), 
    usuarioElegido.usuario)

WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.4/Page_Control de Acceso  POS/input_Contrasea'), 
    usuarioElegido.password)

WebUI.sendKeys(findTestObject('Object Repository_CU05_Procesar Pago/CP005.4/Page_Control de Acceso  POS/input_Contrasea'), 
    Keys.chord(Keys.ENTER))

WebUI.delay(2)

// ==========================================
// 2. VERIFICAR QUE NO EXISTAN COMANDAS PAGADAS VISIBLES
// ==========================================
// Busca si existe algún elemento o etiqueta con texto "Pagada" en el Punto de Venta
TestObject comandaPagada = new TestObject('ComandaPagada')
comandaPagada.addProperty('xpath', ConditionType.EQUALS, "//*[contains(translate(text(), 'PAGADA', 'pagada'), 'pagada')]")

// Valida que NO esté presente en pantalla (impidiendo seleccionarla y cobrarla dos veces)
WebUI.verifyElementNotPresent(comandaPagada, 5)
WebUI.comment("Validación exitosa: no se visualizan comandas pagadas en el Punto de Venta, impidiendo el doble cobro.")