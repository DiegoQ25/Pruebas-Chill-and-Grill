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

WebUI.setEncryptedText(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Control de Acceso  POS/input_Contrasea'), 
    '/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.setText(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Control de Acceso  POS/input_Usuario ID'), 'admin')

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Reportes Generales  POS/a_Bitcora'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/td_12_11_2026 04_50_09 PM'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/td_Samuel Solis'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/span_auth.login_exitoso'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/td_usuarios 1'))

WebUI.doubleClick(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/td_usuarios 1'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/span_'))

WebUI.doubleClick(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/span_'))

WebUI.click(findTestObject('Object Repository_CU07_Cancelar Comanda/Page_Bitcora  POS/main_Bitcora de auditora'))

