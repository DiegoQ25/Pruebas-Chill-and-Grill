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

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app')

WebUI.setText(findTestObject('Page_Control de Acceso  POS/input_Usuario ID (2)'), 'Luci')

WebUI.setEncryptedText(findTestObject('Page_Control de Acceso  POS/input_Contrasea (2)'), 'RePBp35xg5z7GlcnRouTRA==')

WebUI.click(findTestObject('Page_Control de Acceso  POS/button_Iniciar Sesin (1)'))

WebUI.click(findTestObject('Page_Punto de Venta  POS/button_Abrir una cuenta nueva (2)'))

WebUI.selectOptionByValue(findTestObject('Page_Punto de Venta  POS/select_Pblico General (mostrador) (1)'), '4', false)

WebUI.click(findTestObject('Page_Punto de Venta  POS/button_Abrir cuenta (2)'))

WebUI.click(findTestObject('Page_Punto de Venta  POS/button_Salir (2)'))

