package jarvis_2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage {

    private static GenericFunctions _GenericFunctions;

    public ConfirmationPage() {
        _GenericFunctions = new GenericFunctions();
    }

    //***************************************
    //Variable Definition
    // arrData[0] - Split Indicator
    // arrData[1] - Field to be tested/extracted
    // arrData[2] - Expected Results
    // arrData[3] - Activate Compare
    //***************************************
    public String get_Info_Itinerary_Confirmation_Page(WebDriver wd, String locator, String locString, String data,
            String ResultsFileName, String TestCaseName, String StepName) {
        String[] arrData = data.split("==");
        String strActualResults;
        String Flag = "FAILED";

        strActualResults = _GenericFunctions.ExtractTextFromTable(wd, locator, locString, arrData[1]);
        //Verification
        if (arrData[3].equalsIgnoreCase("YES")) {
            if (strActualResults.toUpperCase().compareTo(arrData[2].toUpperCase()) != 0) {
                Flag = "PASSED";
            }
        } else {
            Flag = "Not Applicable";
        }

        _GenericFunctions.WriteResults(Global.ResultsFileName, StepName, StepName, arrData[2], strActualResults, Flag);
        return strActualResults;

    }

    public void ConfirmationPageSync(WebDriver wd, String LocString) {
        WebDriverWait wait = new WebDriverWait(wd, 1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocString)));
        } catch (Exception e) {
        }
    }
}
