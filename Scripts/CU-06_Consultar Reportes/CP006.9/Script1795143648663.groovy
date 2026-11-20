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

WebUI.navigateToUrl('https://grill-and-chill-production-1766.up.railway.app/admin/reportes')

WebUI.setText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/input_Usuario ID'), 
    'carlosm')

WebUI.setEncryptedText(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/input_Contrasea'), 
    'ttP+8wnU6BcqKsuswcBDhA==')

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Control de Acceso  POS/svg_h-4 w-4'))

WebUI.click(findTestObject('Object Repository_CU06_Consultar Reportes/CP006.9/Page_Forbidden/div_No tienes permiso para acceder a esta secci'))

