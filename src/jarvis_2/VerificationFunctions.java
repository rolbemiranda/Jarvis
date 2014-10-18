package jarvis_2;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class VerificationFunctions {

    private static GenericFunctions _GenericFunctions;

    public VerificationFunctions() {
        _GenericFunctions = new GenericFunctions();
    }

    public void ContinueExecutionCheck_ByID(WebDriver wd, String locString, String data) {
        String strValue;
        strValue = _GenericFunctions.getSelectedValue(wd, locString, "ID");
        if (strValue.toUpperCase().contains(data.toUpperCase())) {
            Global.ContinueExecution = true;
        } else {
            Global.ContinueExecution = false;
        }
    }

    public void ContinueExecutionCheck(WebDriver wd, String locString, String data) {
        String strValue;
        strValue = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
        if (strValue.toUpperCase().contains(data.toUpperCase())) {
            Global.ContinueExecution = true;
        } else {
            Global.ContinueExecution = false;
        }
    }

    public void Check_SelectedValueOnElement(WebDriver wd, String ElementType, String Seq, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        Boolean blnExist, blnChecked;
        String value;

        switch (ElementType.toUpperCase()) {
            case "BAGGAGE":
                ElementID = "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_" + Seq;
                break;
            case "MEAL":
                ElementID = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView"
                        + "_MEALSINPUTDROPDOWNLIST_" + Seq;
                break;
        }
        ActualResult = _GenericFunctions.getSelectedValue(wd, ElementID, "ID");
        _GenericFunctions.JarvisVerificationPoint("Check_SelectValueOnElement", ElementType, ExpectedResults,
                ActualResult, ElementType + " doesn't exist in Passenger page. Please check.");
    }

    public void Check_ZeroPriceMeal(WebDriver wd, String ObjectToCheck, String ExpectedResults) {
        String ActualResult = "FALSE";
        String ElementID = "";
        Boolean blnExist;
        switch (ObjectToCheck.toUpperCase()) {
            case "DEPART_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView"
                        + "_MEALSINPUTDROPDOWNLIST_0_0_0";
                break;
            case "DEPART_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView"
                        + "_MEALSINPUTDROPDOWNLIST_0_0_1";
                break;
            case "RETURN_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView"
                        + "_MEALSINPUTDROPDOWNLIST_0_1_0";
                break;
            case "RETURN_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView"
                        + "_MEALSINPUTDROPDOWNLIST_0_1_1";
                break;
        }
        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);

        if (blnExist) {
            List<WebElement> Options = new Select(wd.findElement(By.id(ElementID))).getOptions();
            for (WebElement option : Options) {
                if (option.getText().trim().contains("@ 0.00")) {
                    ActualResult = "TRUE";
                    break;
                } else {
                    ActualResult = "FALSE";
                }
            }
            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceMeal", ObjectToCheck, ExpectedResults,
                    ActualResult, ObjectToCheck + " has 0.00 pricing. Please check.");
        } else {
            ActualResult = "FALSE";
            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceMeal", ObjectToCheck, ExpectedResults,
                    ActualResult, ObjectToCheck + " doesn't exist in Passenger page. Please check.");
        }


    }

    public void Check_ZeroPriceBaggage(WebDriver wd, String ObjectToCheck, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        Boolean blnExist;
        switch (ObjectToCheck.toUpperCase()) {
            case "DEPART_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_0_0_0";
                break;
            case "DEPART_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_0_0_1";
                break;
            case "RETURN_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_0_1_0";
                break;
            case "RETURN_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_0_1_1";
                break;
        }
        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);

        if (blnExist) {
            List<WebElement> Options = new Select(wd.findElement(By.id(ElementID))).getOptions();

            for (WebElement option : Options) {
                if (option.getText().trim().contains("@ 0.00")) {
                    ActualResult = "TRUE";
                    break;
                } else {
                    ActualResult = "FALSE";
                }
            }
            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceBaggage", ObjectToCheck, ExpectedResults,
                    ActualResult, ObjectToCheck + " has 0.00 pricing. Please check.");
        }

    }

    public void Check_ZeroPriceSportsEquipment(WebDriver wd, String ObjectToCheck, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        String ElementID_Combo = "";
        String PriceTable = "";
        String PriceLocation = "";
        Boolean blnExist;
        switch (ObjectToCheck.toUpperCase()) {
            case "DEPART_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_0_0_0";
                PriceTable = ".//*[@id='speqSSRFCont_0']";
                PriceLocation = "./div/div/table/tbody/tr[1]/td[3]";
                break;
            case "DEPART_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_0_0_1";

                PriceTable = ".//*[@id='speqSSRFCont_0']";
                PriceLocation = "./div/div/table/tbody/tr[2]/td[3]";
                break;
            case "RETURN_LEG1":
                ElementID = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_0_1_0";
                ElementID_Combo = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_0_0_1";

                blnExist = _GenericFunctions.IsElementExist(wd, ElementID_Combo);
                if (blnExist) {
                    PriceTable = ".//*[@id='speqSSRFCont_0']";
                    PriceLocation = "./div/div/table/tbody/tr[3]/td[3]";
                } else {
                    PriceTable = ".//*[@id='speqSSRFCont_0']";
                    PriceLocation = "./div/div/table/tbody/tr[2]/td[3]";
                }


                break;
            case "RETURN_LEG2":
                ElementID = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_0_1_1";

                PriceTable = ".//*[@id='speqSSRFCont_0']";
                PriceLocation = "./div/div/table/tbody/tr[4]/td[3]";
                break;
        }
        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);

        if (blnExist) {
            String Content = _GenericFunctions.ExtractTextFromTable(wd, "", PriceTable, PriceLocation);
            if (Content.trim().contains("@ 0.00")) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceSportsEquipment", ObjectToCheck, ExpectedResults,
                    ActualResult, ObjectToCheck + " has 0.00 pricing. Please check.");
        }

    }

    public void Check_ZeroPriceBMF(WebDriver wd, String ExpectedPrice, String Seq, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        String PriceTable = "";
        String PriceLocation = "";
        Boolean blnExist;

        ElementID = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                + "_BOARDMEFIRSTCHECKBOX_" + Seq;
        PriceTable = ".//*[@id='bmfTable']";
        PriceLocation = "./tbody/tr[1]/td[3]";


        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);
        if (blnExist) {
            String Content = _GenericFunctions.ExtractTextFromTable(wd, "", PriceTable, PriceLocation);
            if (Content.trim().contains(ExpectedPrice.trim())) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceBMF", "BMF", ExpectedResults,
                    ActualResult, "Expected BMF Price is " + ExpectedPrice + "Actual Price: " + Content
                    + ". Please check.");
        } else {
            ActualResult = "FALSE";
            _GenericFunctions.JarvisVerificationPoint("Verify_IfExist", "BMF", ExpectedResults,
                    ActualResult, "BMF checkbox does not exist. " + ". Please check.");

        }

    }

    public void Check_ZeroPriceTigerPlus(WebDriver wd, String ExpectedPrice, String Seq, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        String PriceTable = "";
        String PriceLocation = "";
        Boolean blnExist;

        ElementID = "CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView_TIGERPLUSCHECKBOX_" + Seq;;
        PriceTable = ".//*[@id='plusSSRFCont_0']";
        PriceLocation = "./div/div/table/tbody/tr[1]/td[3]";


        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);
        if (blnExist) {
            String Content = _GenericFunctions.ExtractTextFromTable(wd, "", PriceTable, PriceLocation);
            if (Content.trim().contains(ExpectedPrice.trim())) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceTigerPlus", "Tiger Plus", ExpectedResults,
                    ActualResult, "Expected Tiger Plus Price is " + ExpectedPrice + "Actual Price: " + Content
                    + ". Please check.");
        } else {
            ActualResult = "FALSE";
            _GenericFunctions.JarvisVerificationPoint("Verify_IfExist", "Tiger Plus", ExpectedResults,
                    ActualResult, "Tiger Plus checkbox does not exist. " + ". Please check.");

        }

    }

    public void Check_ZeroPriceQueueJump(WebDriver wd, String ExpectedPrice, String Seq, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        String PriceTable = "";
        String PriceLocation = "";
        Boolean blnExist;

        ElementID = "CONTROLGROUPPASSENGER_SSRQueueJumpOfferViewPassengerView"
                + "_QUEUEJUMPCHECKBOX_" + Seq;
        PriceTable = ".//*[@id='queueJumpTable']";
        PriceLocation = "./tbody/tr[1]/td[3]";


        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);
        if (blnExist) {
            String Content = _GenericFunctions.ExtractTextFromTable(wd, "", PriceTable, PriceLocation);
            if (Content.trim().contains(ExpectedPrice.trim())) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceQueueJump", "QueueJump", ExpectedResults,
                    ActualResult, "Expected QueueJump Price is " + ExpectedPrice + "Actual Price: " + Content
                    + ". Please check.");
        } else {
            ActualResult = "FALSE";
            _GenericFunctions.JarvisVerificationPoint("Verify_IfExist", "QueueJump", ExpectedResults,
                    ActualResult, "QueueJump checkbox does not exist. " + ". Please check.");
        }

    }

    public void Verify_IfChecked(WebDriver wd, String ElementType, String Seq, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        Boolean blnExist, blnChecked;

        switch (ElementType.toUpperCase()) {
            case "QUEUEJUMP":
                ElementID = "CONTROLGROUPPASSENGER_SSRQueueJumpOfferViewPassengerView"
                        + "_QUEUEJUMPCHECKBOX_" + Seq;
                break;
            case "BMF":
                ElementID = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                        + "_BOARDMEFIRSTCHECKBOX_" + Seq;
                break;
            case "SPORTS":
                ElementID = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_" + Seq;
                break;
            case "TIGERPLUS":
                ElementID = "CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView"
                        + "_TIGERPLUSCHECKBOX_" + Seq;
                break;
            case "SMS":
                ElementID = "CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxSMSItinerary";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, ElementID);
        if (blnExist) {
            blnChecked = wd.findElement(By.id(ElementID)).isSelected();
            if (blnChecked) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint("Verify_IfChecked", ElementType, ExpectedResults,
                    ActualResult, ElementType + " checkbox is Checked. " + ". Please check.");
        } else {
            ActualResult = "FALSE";
            _GenericFunctions.JarvisVerificationPoint("Verify_IfExist", ElementType, ExpectedResults,
                    ActualResult, ElementType + " checkbox does not exist. " + ". Please check.");

        }

    }

    public void Check_ZeroPriceInsurance(WebDriver wd, String ExpectedResults) {
        String ActualResult = "";
        String ElementID = "";
        String AceElementID = "";
        String PriceTable = "";
        String PriceLocation = "";
        Boolean blnExist;

        //Check if Insurance is available
        blnExist = _GenericFunctions.IsElementExist(wd, "insuranceOfferYes");
        if (blnExist) {
            //Check if Ace Insurance Exist
            AceElementID = "aceInsuranceLogoTop";
            blnExist = _GenericFunctions.IsElementExist(wd, AceElementID);
            if (blnExist) {
                PriceTable = ".//*[@id='passengerMainBody']";
                PriceLocation = "/div[4]/fieldset/div[1]/p[2]/span[1]/price";
                //Tiger Purrtection
            } else {
                PriceTable = ".//*[@id='InsuranceTable']";
                PriceLocation = "/tbody/tr[2]/td/div/span/p/span[1]/price";
            }
            blnExist = _GenericFunctions.IsElementExistXpath(wd, PriceTable + PriceLocation);
            if (blnExist) {
                String Content = _GenericFunctions.ExtractTextFromTable(wd, "", PriceTable, "." + PriceLocation);
                if (Content.trim().contains("0.00")) {
                    ActualResult = "TRUE";
                } else {
                    ActualResult = "FALSE";
                }
            } else {
                ActualResult = "FALSE";
            }
            _GenericFunctions.JarvisVerificationPoint("Check_ZeroPriceInsurance", "Insurance", ExpectedResults,
                    ActualResult, "Insurance has 0.00 pricing. Please check.");
        }
    }

    public void DCCValidation(WebDriver wd, String CCType, String ExpectedResults) {
        Boolean blnExist = false;
        String strCardNumber = Global.MASTERCARDNUMBER_Prod;
        String ActualResult = "";
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_DropDownListPaymentMethodCode")).sendKeys(CCType);
        _GenericFunctions.wait("3000");
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_TextBoxACCTNO")).clear();
        _GenericFunctions.wait("5000");
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_TextBoxACCTNO")).sendKeys(strCardNumber);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_TextBoxACCTNO")).sendKeys(Keys.TAB);

        _GenericFunctions.wait("5000");

        blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='dcc']");
        if (blnExist) {
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("DCC Validation", "DCC Validation", ExpectedResults,
                ActualResult, "DCC Information doesn't appear.");
    }
}
