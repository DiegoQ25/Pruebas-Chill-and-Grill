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

WebUI.openBrowser(null)

WebUI.callTestCase(findTestCase('LoginS/Login_Admin'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_Reportes Generales  POS/a_Gestin de Empleados'))

WebUI.click(findTestObject('Page_Gestin de Empleados  POS/a_Nuevo empleado'))

WebUI.setText(findTestObject('Page_Nuevo empleado  POS/input_Nombre completo'), 'Saul Garcia')

WebUI.click(findTestObject('Page_Nuevo empleado  POS/input_Nombre completo'))

WebUI.setEncryptedText(findTestObject('Page_Nuevo empleado  POS/input_Contrasea'), 'QyW8tQW2lbi0hOm+QvJHOQ==')

WebUI.setEncryptedText(findTestObject('Page_Nuevo empleado  POS/input_Confirmar contrasea'), 'QyW8tQW2lbi0hOm+QvJHOQ==')

WebUI.click(findTestObject('Page_Nuevo empleado  POS/button_Registrar empleado'))

