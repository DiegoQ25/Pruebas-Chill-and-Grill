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

// 1. IMPORTAR LA LIBRERÍA PARA GENERAR DATOS ALEATORIOS
import org.apache.commons.lang3.RandomStringUtils
import com.kms.katalon.core.util.KeywordUtil

// 2. GENERAR LOS DATOS ALEATORIOS ANTES DE EMPEZAR EL FLUJO
String randomNombre = "Empleado " + RandomStringUtils.randomAlphabetic(6) // Ej: Empleado XyZqWe
String randomUsuario = "user" + RandomStringUtils.randomAlphanumeric(5).toLowerCase() // Ej: user3a9b2
String randomPassword = RandomStringUtils.randomAlphanumeric(10) // Ej: aB81xPqZ9m

// --- INICIO DE TU FLUJO ---
WebUI.openBrowser(null)

WebUI.callTestCase(findTestCase('LoginS/Login_Admin'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowTitle('Reportes Generales — POS')

WebUI.click(findTestObject('Page_Reportes Generales  POS/a_Gestin de Empleados'))

WebUI.click(findTestObject('Page_Gestin de Empleados  POS/a_Nuevo empleado'))

// 3. REEMPLAZAR LOS VALORES FIJOS POR LAS VARIABLES ALEATORIAS
WebUI.setText(findTestObject('Page_Nuevo empleado  POS/input_Nombre completo'), randomNombre)

WebUI.setText(findTestObject('Page_Nuevo empleado  POS/input_Usuario'), randomUsuario)

// NOTA: Se cambió WebUI.setEncryptedText por WebUI.setText porque la contraseña ya no viene cifrada de Katalon
WebUI.setText(findTestObject('Page_Nuevo empleado  POS/input_Contrasea'), randomPassword)

WebUI.setText(findTestObject('Page_Nuevo empleado  POS/input_Confirmar contrasea'), randomPassword)

WebUI.click(findTestObject('Page_Nuevo empleado  POS/button_Registrar empleado'))

// 4. (OPCIONAL) IMPRIMIR EN LA CONSOLA DE KATALON PARA SABER QUÉ CREÓ
KeywordUtil.logInfo("EMPLEADO CREADO -> Nombre: ${randomNombre} | Usuario: ${randomUsuario} | Clave: ${randomPassword}")