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

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/login')

WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Control de Acceso  POS/input_Usuario ID'), 
    'admin')

WebUI.setEncryptedText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Control de Acceso  POS/input_Contrasea'), 
    '/5S6MFFLcE4mlsixtc6/Tg==')

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Reportes Generales  POS/span_Reportes Generales'))

WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Reportes Generales  POS/input_Desde la fecha'), 
    '2026-11-10')

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Reportes Generales  POS/button_Buscar Ventas'))

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.6/Page_Reportes Generales  POS/div_Flujo Operativo (Comandas)'))

