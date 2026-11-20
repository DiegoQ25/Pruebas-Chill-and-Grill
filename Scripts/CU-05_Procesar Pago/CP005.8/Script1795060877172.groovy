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

WebUI.setText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Control de Acceso  POS/input_Usuario ID'), 
    'SelenaG')

WebUI.setEncryptedText(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Control de Acceso  POS/input_Contrasea'), 
    'dGiN0va5NxZiMnBqBt4NIg==')

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Control de Acceso  POS/button_Iniciar Sesin'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/button_Abrir una cuenta nueva'))

WebUI.selectOptionByValue(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/select_Pblico General (mostrador)'), 
    '3', false)

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/button_Abrir cuenta'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/span_60.00'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/button_Guacamole con Chicharrn de Cachete'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/div_Taco de No Arrachera'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/span_395.00'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Punto de Venta  POS/button_Cobrar'))

WebUI.click(findTestObject('Object Repository_CU05_Procesar Pago/CP005.8/Page_Forbidden/div_403'))

