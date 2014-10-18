package jarvis_2;

import java.util.Date;
import java.io.IOException;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class PassengerPage {

    private static GenericFunctions _GenericFunctions;

    public PassengerPage() {
        _GenericFunctions = new GenericFunctions();
    }

    public void select_Car(WebDriver wd, String AddCar, String ExpectedResults) {
        Boolean blnExist = false;
        String ActualResult = "";

        //Check if Car Service Exist
        if (AddCar.toUpperCase().equals("YES")) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='btnCarSearch']");
            if (blnExist) {
                wd.findElement(By.xpath(".//*[@id='btnCarSearch']")).click();
                CarSync(wd);
                wd.findElement(By.xpath(".//*[@id='carContainer']/div/"
                        + "cargrid/div[3]/table/tbody/tr[2]/td[2]/div/div[2]/div[2]")).click();
                _GenericFunctions.wait("2000");
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }
        }
        _GenericFunctions.JarvisVerificationPoint("Passenger Page", "Car Rental Check", ExpectedResults,
                ActualResult, " Missing Car Rental");
    }
    
    public void SelectPAXStaffTravel(WebDriver wd)
    {
        for (int i = 2; i < Integer.parseInt(Global.AdultCount) + 1 ; i ++)
        {
            wd.findElement(By.xpath(".//*[@id='nominee_" + i + "']")).click();
        }
    }

    public void PassengerDetailsSync(WebDriver wd) {
        System.out.println("waiting for Passenger details to appear");
        Boolean blnExist;
        for (int i = 0; i < 100; i++) {
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListTitle_0");
            if (blnExist) {
                System.out.println("Passenger details appeared");
                break;
            } else {
                System.out.println("Passenger details  doesn't appear");
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void CarSync(WebDriver wd) {
        System.out.println("waiting for Car Window to appear");
        Boolean blnExist;
        for (int i = 0; i < 100; i++) {
            blnExist = _GenericFunctions.IsElementExist(wd, "carDiv");
            if (blnExist) {
                System.out.println("Car Window appeared");
                break;
            } else {
                System.out.println("Car Window  doesn't appear");
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void select_Baggage_Meal(WebDriver wd, String locator, String locString, String data) {
        String CurrSelected = "";
        switch (locator) {
            case "xpath":
                for (int i = 0; i < 20; i++) {
                    wd.findElement(By.xpath(locString)).sendKeys(Keys.ARROW_UP);
                }
                while (CurrSelected.toUpperCase().contains(data.toUpperCase()) == false) {
                    wd.findElement(By.xpath(locString)).sendKeys(Keys.ARROW_DOWN);
                    CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                }
                break;

            case "name":
                for (int i = 0; i < 20; i++) {
                    wd.findElement(By.name(locString)).sendKeys(Keys.ARROW_UP);
                }
                while (CurrSelected.toUpperCase().contains(data.toUpperCase())) {
                    wd.findElement(By.name(locString)).sendKeys(Keys.ARROW_DOWN);
                    CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                }
                break;

            case "id":
                for (int i = 0; i < 20; i++) {
                    wd.findElement(By.id(locString)).sendKeys(Keys.ARROW_UP);
                }
                while (CurrSelected.toUpperCase().contains(data.toUpperCase())) {
                    wd.findElement(By.id(locString)).sendKeys(Keys.ARROW_DOWN);
                    CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                }
                break;
        }
    }

    public void select_All_Segment(WebDriver wd, String Data) {
        String locString = "lockBaggageTypeSelection_" + Data;
        wd.findElement(By.id(locString)).click();
    }

    public void confirm_PassengerPage(WebDriver wd) throws Exception {
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCC_PassengerPage");
        }

        //Click Switch My Flight Button
        wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPASSENGER_ButtonSubmit']")).click();

        //Click okay if insurance is not selected
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //Click okay if car  is not selected
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //Wait Until the NEW Auto-Seat Assignment Page Exists
        SeatAssignmentSync_New(wd);
    }

    public void confirm_PassengerIndemnity(WebDriver wd) throws Exception {
        Boolean blnExist;
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCC_PassengerPage");
        }

        //Tick the Indemnity checkbox

        //check if TR Indemnity message or TT Indemnity message
        blnExist = _GenericFunctions.IsElementExistXpath(wd, "//*[@id='CheckBoxAgreement1']");

        if (!blnExist) {
            // TR - 
            // TT - I/We agree that a child under 15 years of age must be accompanied on the same booking by an adult passenger, 18 years of age or older. If the accompanying passenger is under 18 years of age, the child may travel only if he or she produces, at the point of check-in at each departure airport, the carrier’s indemnity form signed by his or her parent or legal guardian. The indemnity form is provided by the carrier at the point of check-in. Prevailing laws apply; please refer to our Conditions of Carriage.
            wd.findElement(By.xpath(".//*[@id='CheckBoxAgreement']")).click();

        } //TT  I/We agree that a child aged 12 to14 years inclusive may fly unaccompanied. The respective parent or guardian must remain at the airport until the flight has departed. Please refer to our Condition of Carriage
        else {
            wd.findElement(By.xpath(".//*[@id='CheckBoxAgreement1']")).click();
        }

        //Capture the Indemnity message
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCC_Indemnity");
        }

        //Click Continue Button
        wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPASSENGER_ButtonSubmit']")).click();

        //Check for anu validation error
        blnExist = _GenericFunctions.IsElementExistXpath(wd, "//select[@class='wLrgsYearDOB validationError']");
        //exit the method

        if (blnExist) {
            wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_DropDownListBirthDateYear_0']")).click();

            //Capture the Validation error
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCC_AgeValidation");
            }
            return;
        }

        //Click okay if insurance is not selected
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //Click okay if car  is not selected
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //Wait Until the NEW Auto-Seat Assignment Page Exists
        SeatAssignmentSync_New(wd);
    }

    public void ClickOkWebCheckin(WebDriver wd) throws Exception {
        Boolean blnExist;
        String btnWebCheckin = ".//*[@id='wcPopup']/div[2]/input";

        //Click WebCheckin Option
        for (int i = 0; i < 6; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, btnWebCheckin);
            if (blnExist) {
                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "UUU_WebCheckin");
                }
                wd.findElement(By.xpath(btnWebCheckin)).click();
                break;
            }
            _GenericFunctions.wait("1000");
        }
    }

    public void SeatAssignmentSync(WebDriver wd) {
        System.out.println("waiting for Seat Assignment Page to appear");
        Boolean blnExist;
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExist(wd, "ControlGroupUnitMapView_UnitMapViewControl"
                    + "_LinkButtonAssignUnit");
            if (blnExist) {
                System.out.println("Seat Assignment Page appeared");
                break;
            } else {
                System.out.println("Seat Assignment Page doesn't appear");
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void SeatAssignmentSync_New(WebDriver wd) {
        System.out.println("waiting for Seat Assignment Page to appear");
        Boolean blnExist;
        for (int i = 0; i < 20; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='unitMapView']");
            if (blnExist) {
                System.out.println("Seat Assignment Page appeared");
                Global.ContinueExecution = true;
                break;
            } else {
                System.out.println("Seat Assignment Page doesn't appear");
                _GenericFunctions.wait("1000");
                Global.ContinueExecution = false;
            }
        }
    }

    public void select_Meal(WebDriver wd, String DepRet, String Sequence, String Data, String ExpectedResults) {
        String ActualResult = "";
        String locString = "";
        String Flag = "PASSED";
        int intIndex;
        String[] strIndex;
        Boolean blnExist = false;
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST"
                        + "_" + Sequence.replace(".0", "") + "_0_0";
                break;
            case "DEPART_LEG2":
                locString = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST"
                        + "_" + Sequence.replace(".0", "") + "_0_1";
                break;
            case "RETURN_LEG1":
                locString = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST"
                        + "_" + Sequence.replace(".0", "") + "_1_0";
                break;
            case "RETURN_LEG2":
                locString = "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST"
                        + "_" + Sequence.replace(".0", "") + "_1_1";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);

        if (blnExist) {
            if (Data.toUpperCase().equals("FIRST")) {
                new Select(wd.findElement(By.id(locString))).selectByIndex(1);
                ActualResult = "TRUE";
            } else if (Data.toUpperCase().contains("INDEX")) {
                strIndex = Data.split("_");
                intIndex = Integer.parseInt(strIndex[1]);
                new Select(wd.findElement(By.id(locString))).selectByIndex(intIndex);
                ActualResult = "TRUE";
            } else {
                wd.findElement(By.id(locString)).sendKeys(Data);
                ActualResult = "TRUE";
            }
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Meals Check", ExpectedResults,
                ActualResult, " Missing Meals");
    }

    public void select_TravelInsurance(WebDriver wd, String YesNo, String ExpectedResults) {
        String ActualResult = "FALSE";
        String Flag = "PASSED";
        String locString = "";
        Boolean blnExist = false;
        switch (YesNo.toUpperCase()) {
            case "YES":
                locString = "insuranceOfferYes";
                break;
            case "NO":
                locString = "insuranceOfferNo";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);
        if (blnExist) {
            wd.findElement(By.id(locString)).click();
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger Page", "Insurance Check", ExpectedResults,
                ActualResult, " Missing Insurance");
    }

    public void select_Baggage(WebDriver wd, String DepRet, String Sequence, String Data, String ExpectedResults) {
        String locString = "";
        String CurrSelected = "";
        String Flag = "PASSED";
        Boolean blnContinue = true;
        Boolean blnExist = false;
        String ActualResult = "";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_" + Sequence.replace(".0", "") + "_0_0']";
                break;
            case "DEPART_LEG2":
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                        + "_BAGSINPUTDROPDOWNLIST_" + Sequence.replace(".0", "") + "_0_1']";
                break;
            case "RETURN_LEG1":
                if (Global.IsRoundTrip) {
                    locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                            + "_BAGSINPUTDROPDOWNLIST_" + Sequence.replace(".0", "") + "_1_0']";
                } else {
                    blnContinue = false;
                }
                break;
            case "RETURN_LEG2":
                if (Global.IsRoundTrip) {
                    locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView"
                            + "_BAGSINPUTDROPDOWNLIST_" + Sequence.replace(".0", "") + "_1_1']";
                } else {
                    blnContinue = false;
                }
                break;
        }

        if (blnContinue) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, locString);
            if (blnExist) {
                ActualResult = "TRUE";
                for (int i = 0; i < 20; i++) {
                    wd.findElement(By.xpath(locString)).sendKeys(Keys.ARROW_UP);
                }
                CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                while (CurrSelected.toUpperCase().contains(Data.toUpperCase()) == false) {
                    wd.findElement(By.xpath(locString)).sendKeys(Keys.ARROW_DOWN);
                    CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                    _GenericFunctions.clickOnSkySales(wd);
                }
            } else {
                ActualResult = "FALSE";
            }
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Baggage Check", ExpectedResults,
                ActualResult, " Missing Baggage");
    }

    public void select_SportsEquipment(WebDriver wd, String DepRet, String Sequence, String Data,
            String ExpectedResults) {
        String locString = "";
        String ActualResult = "";
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String Flag = "PASSED";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_" + Sequence.replace(".0", "") + "_0_0";
                break;
            case "DEPART_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_" + Sequence.replace(".0", "") + "_0_1";
                break;
            case "RETURN_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_" + Sequence.replace(".0", "") + "_1_0";
                break;
            case "RETURN_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView"
                        + "_EQUIPMENTCHECKBOX_" + Sequence.replace(".0", "") + "_1_1";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);
        //blnExist = wd.findElement(By.id(locString)).isDisplayed();
        if (blnExist) {
            blnChecked = wd.findElement(By.id(locString)).isSelected();
            if (!blnChecked) {
                wd.findElement(By.id(locString)).click();
            }
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Sports Equipment Check",
                ExpectedResults, ActualResult, " Missing Sports Equipment");

    }

    public void select_GolfBag(WebDriver wd, String DepRet, String Sequence, String Data,
            String ExpectedResults) {
        String locString = "";
        String ActualResult = "";
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String Flag = "PASSED";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRGolfBagOfferViewPassengerView"
                        + "_GOLFBAGCHECKBOX_" + Sequence.replace(".0", "") + "_0_0";
                break;
            case "DEPART_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRGolfBagOfferViewPassengerView"
                        + "_GOLFBAGCHECKBOX_" + Sequence.replace(".0", "") + "_0_1";
                break;
            case "RETURN_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRGolfBagOfferViewPassengerView"
                        + "_GOLFBAGCHECKBOX_" + Sequence.replace(".0", "") + "_1_0";
                break;
            case "RETURN_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRGolfBagOfferViewPassengerView"
                        + "_GOLFBAGCHECKBOX_" + Sequence.replace(".0", "") + "_1_1";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);
        //blnExist = wd.findElement(By.id(locString)).isDisplayed();
        if (blnExist) {
            blnChecked = wd.findElement(By.id(locString)).isSelected();
            if (!blnChecked) {
                wd.findElement(By.id(locString)).click();
            }
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Golf Bag Check",
                ExpectedResults, ActualResult, " Missing Golf Bag");

    }

    public void select_TigerConnect(WebDriver wd, String DepRet, String Sequence, String ExpectedResults) {
        String locString = "";
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String ActualResult;
        String Flag = "PASSED";
        switch (DepRet.toUpperCase()) {
            case "DEPART":
                //Depart Flight Leg 1
                locString = ".//*[@id='CONTROLGROUPPASSENGER_ThruLegInputViewPassengerView_THRURADIOBUTTON_"
                        + Sequence.replace(".0", "") + "_0_0']";
                break;
            case "RETURN":
                //Depart Flight Leg 1
                locString = ".//*[@id='CONTROLGROUPPASSENGER_ThruLegInputViewPassengerView_THRURADIOBUTTON_"
                        + Sequence.replace(".0", "") + "_1_0']";
                break;
        }
        blnExist = _GenericFunctions.IsElementExistXpath(wd, locString);
        if (blnExist) {
            blnChecked = wd.findElement(By.xpath(locString)).isSelected();
            if (!blnChecked) {
                wd.findElement(By.xpath(locString)).click();
            }
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Tiger Connect Check",
                ExpectedResults, ActualResult, " Missing Tiger Connect");
    }

    public void select_TigerPlus(WebDriver wd, String DepRet, String Sequence, String ExpectedResults) {
        String locString = "";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                //Depart Flight Leg 1
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView"
                        + "_TIGERPLUSCHECKBOX_" + Sequence.replace(".0", "") + "_0_0']";
                Click_TigerPlus(wd, locString, Sequence, ExpectedResults);
                break;
            case "DEPART_LEG2":
                //Depart Flight Leg 2
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView"
                        + "_TIGERPLUSCHECKBOX_" + Sequence.replace(".0", "") + "_0_1']";
                Click_TigerPlus(wd, locString, Sequence, ExpectedResults);
                break;
            case "RETURN_LEG1":
                //Arriving Flight Leg 1
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView"
                        + "_TIGERPLUSCHECKBOX_" + Sequence.replace(".0", "") + "_1_0']";
                Click_TigerPlus(wd, locString, Sequence, ExpectedResults);
                break;
            case "RETURN_LEG2":
                //Arriving Flight Leg 2
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView"
                        + "_TIGERPLUSCHECKBOX_" + Sequence.replace(".0", "") + "_1_1']";
                Click_TigerPlus(wd, locString, Sequence, ExpectedResults);
                break;
        }
    }

    public void Click_TigerPlus(WebDriver wd, String locString, String Sequence, String ExpectedResults) {
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String ActualResult = "";
        String Flag = "PASSED";
        blnExist = _GenericFunctions.IsElementExistXpath(wd, locString);
        if (blnExist) {
            blnChecked = wd.findElement(By.xpath(locString)).isSelected();
            if (!blnChecked) {
                wd.findElement(By.xpath(locString)).click();
            }
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Tiger Plus Check",
                ExpectedResults, ActualResult, " Missing Tiger Plus");
    }

    public void deselect_BoardMeFirst(WebDriver wd, String Select, String DepRet, String Sequence,
            String ExpectedResults) {
        String locString = "";
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String ActualResult = "";
        String Flag = "PASSED";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                        + "_BOARDMEFIRSTCHECKBOX_" + Sequence.replace(".0", "") + "_0_0";
                break;
            case "DEPART_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                        + "_BOARDMEFIRSTCHECKBOX_" + Sequence.replace(".0", "") + "_0_1";
                break;
            case "RETURN_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                        + "_BOARDMEFIRSTCHECKBOX_" + Sequence.replace(".0", "") + "_1_0";
                break;
            case "RETURN_LEG2":
                locString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView"
                        + "_BOARDMEFIRSTCHECKBOX_" + Sequence.replace(".0", "") + "_1_1";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);
        if (blnExist) {
            if (Select.toUpperCase().equals("N")) {
                blnChecked = wd.findElement(By.id(locString)).isSelected();
                if (blnChecked) {
                    wd.findElement(By.id(locString)).click();
                }
            } else {
                blnChecked = wd.findElement(By.id(locString)).isSelected();
                if (!blnChecked) {
                    wd.findElement(By.id(locString)).click();
                }
            }
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "Tiger BMF", ExpectedResults,
                ActualResult, " Missing BMF");
    }

    public void select_QueueJump(WebDriver wd, String Select, String DepRet, String Sequence, String ExpectedResults) {
        String locString = "";
        Boolean blnExist = false;
        Boolean blnChecked = false;
        String ActualResult = "";
        String Flag = "PASSED";
        switch (DepRet.toUpperCase()) {
            case "DEPART_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRQueueJumpOfferViewPassengerView"
                        + "_QUEUEJUMPCHECKBOX_" + Sequence.replace(".0", "") + "_0_0";
                break;
            case "RETURN_LEG1":
                locString = "CONTROLGROUPPASSENGER_SSRQueueJumpOfferViewPassengerView"
                        + "_QUEUEJUMPCHECKBOX_" + Sequence.replace(".0", "") + "_1_0";
                break;
        }

        blnExist = _GenericFunctions.IsElementExist(wd, locString);
        if (blnExist) {
            if (Select.toUpperCase().equals("N")) {
                blnChecked = wd.findElement(By.id(locString)).isSelected();
                if (blnChecked) {
                    wd.findElement(By.id(locString)).click();
                }
            } else {
                blnChecked = wd.findElement(By.id(locString)).isSelected();
                if (!blnChecked) {
                    wd.findElement(By.id(locString)).click();
                }
            }
            //if (Select.toUpperCase().equals("N")) {

            //}                
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("Passenger: " + Sequence, "QueueJump", ExpectedResults,
                ActualResult, " Missing QueueJump");
    }

    public void store_Baggage(WebDriver wd, String locator, String locString, String Data,
            String ResultsFileName, String TestCaseName, String StepName,
            String ComputationFileName) throws IOException {
        String strContent = _GenericFunctions.getTxtValue(wd, locator, locString);
        String[] arrContent = strContent.split("@");
        _GenericFunctions.TextWriter(ComputationFileName, arrContent[1] + "=");
    }

    public void InputPassportDetails(WebDriver wd, String strView, String PaxType, int i) {
        Boolean blnExist;
        String AdultNationality = "";
        String AdultPExpiryDay = "";
        String AdultPExpiryMonth = "";
        String AdultPExpiryYear = "";
        String AdultPNumber = "";

        switch (PaxType.toUpperCase()) {
            case "ADT":
                AdultNationality = Global.AdultNationality.get(i);
                AdultPExpiryDay = Global.AdultPExpiryDay.get(i);
                AdultPExpiryMonth = Global.AdultPExpiryMonth.get(i);
                AdultPExpiryYear = Global.AdultPExpiryYear.get(i);
                AdultPNumber = Global.AdultPNumber.get(i);
                break;

            case "CHD":
                AdultNationality = Global.ChildNationality.get(i);
                AdultPExpiryDay = Global.ChildPExpiryDay.get(i);
                AdultPExpiryMonth = Global.ChildPExpiryMonth.get(i);
                AdultPExpiryYear = Global.ChildPExpiryYear.get(i);
                AdultPNumber = Global.ChildPNumber.get(i);
                break;

            case "INF":
                AdultNationality = Global.InfantNationality.get(i);
                AdultPExpiryDay = Global.InfantPExpiryDay.get(i);
                AdultPExpiryMonth = Global.InfantPExpiryMonth.get(i);
                AdultPExpiryYear = Global.InfantPExpiryYear.get(i);
                AdultPNumber = Global.InfantPNumber.get(i);
                break;
        }

        //Check if Passport details exist
        blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                + "_PassengerInputViewPassengerView_DropDownListDocumentCountry0_0");
        if (blnExist) {
            if (PaxType.equals("INF")) {
                new Select(wd.findElement(By.id(strView + "DropDownListDocumentCountry0_" + i + "_" + i)))
                        .selectByIndex(1);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.InputData_ById(wd, strView + "DropDownListDocumentDateDay0_" + i + "_" + i,
                        AdultPExpiryDay);
                _GenericFunctions.clickOnSkySales(wd);
                new Select(wd.findElement(By.id(strView + "DropDownListDocumentDateMonth0_" + i + "_" + i)))
                        .selectByIndex(1);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.InputData_ById(wd, strView + "DropDownListDocumentDateYear0_" + i + "_" + i,
                        AdultPExpiryYear);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxDocumentNumber0_" + i + "_" + i,
                        AdultPNumber);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.clickOnSkySales(wd);
            } else {
                new Select(wd.findElement(By.id(strView + "DropDownListDocumentCountry0_" + i)))
                        .selectByIndex(1);
                _GenericFunctions.InputData_ById(wd, strView + "DropDownListDocumentDateDay0_" + i,
                        AdultPExpiryDay);
                _GenericFunctions.clickOnSkySales(wd);
                new Select(wd.findElement(By.id(strView + "DropDownListDocumentDateMonth0_" + i)))
                        .selectByIndex(1);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.InputData_ById(wd, strView + "DropDownListDocumentDateYear0_" + i,
                        AdultPExpiryYear);
                _GenericFunctions.clickOnSkySales(wd);
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxDocumentNumber0_" + i,
                        AdultPNumber);
                _GenericFunctions.clickOnSkySales(wd);
            }
        }
    }

    public void EnterAdultDetails(WebDriver wd, String AdultName) throws ParseException {
        int intTotalCount = Integer.parseInt(Global.AdultCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        _GenericFunctions.GenerateLastName();
        for (int i = 0; i < intTotalCount; i++) {
            PassengerDetailsSync(wd);
            System.out.println("Entering Passenger Details for Adult#: " + (i + 1));
            new Select(wd.findElement(By.id(strView + "DropDownListTitle_" + i)))
                    .selectByVisibleText(Global.AdultTitle.get(i));
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxFirstName_" + i);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxFirstName_" + i);
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxLastName_" + i);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxLastName_" + i);

            //Input First Name
            if (!AdultName.toUpperCase().equals("NULL")) {
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxFirstName_" + i, AdultName);
            } else {
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxFirstName_" + i,
                        Global.AdultFirstName.get(i));
            }
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxFirstName_" + i);
            _GenericFunctions.InputData_ById(wd, strView + "TextBoxLastName_" + i,
                    Global.UniversalLName + Global.AdultLastName.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxLastName_" + i);
            //_GenericFunctions.ClickElement_ById(wd, "SkySales");
            _GenericFunctions.clickOnSkySales(wd);



            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + i)))
                    .selectByVisibleText(Global.AdultBDAY.get(i));
            _GenericFunctions.clickOnSkySales(wd);

            if (AdultName.toUpperCase().contains("TRANSLATION")) {
                new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i))).selectByIndex(1);
            } else {
                new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i)))
                        .selectByVisibleText(Global.AdultBMonth.get(i));
                _GenericFunctions.clickOnSkySales(wd);
            }

            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + i)))
                    .selectByVisibleText(Global.AdultBYear.get(i));
            _GenericFunctions.clickOnSkySales(wd);

            //Check if Passport details exist
            InputPassportDetails(wd, strView, "ADT", i);
        }
    }

    public void EnterChildDetails(WebDriver wd, String ChildName) throws ParseException {
        Boolean blnExist = false;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        _GenericFunctions.GenerateLastName();


        for (int i = intStartChild; i < intTotalCount; i++) {
            System.out.println("Entering Passenger Details for Child#: " + ((i + 1) - intStartChild));
            new Select(wd.findElement(By.id(strView + "DropDownListTitle_" + i)))
                    .selectByVisibleText(Global.ChildTitle.get(i));
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxFirstName_" + i);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxFirstName_" + i);
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxLastName_" + i);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxLastName_" + i);

            //Input First Name
            if (!ChildName.toUpperCase().equals("NULL")) {
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxFirstName_" + i, ChildName);
            } else {
                _GenericFunctions.InputData_ById(wd, strView + "TextBoxFirstName_" + i,
                        Global.ChildFirstName.get(i));
            }

            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxFirstName_" + i);

            //Input Last Name
            _GenericFunctions.InputData_ById(wd, strView + "TextBoxLastName_" + i,
                    Global.UniversalLName + Global.ChildLastName.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxLastName_" + i);
            //_GenericFunctions.ClickElement_ById(wd, "SkySales");
            _GenericFunctions.clickOnSkySales(wd);
            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + i)))
                    .selectByVisibleText(Global.ChildBDAY.get(i));

            if (ChildName.toUpperCase().contains("TRANSLATION")) {
                new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i))).selectByIndex(1);
            } else {
                new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i)))
                        .selectByVisibleText(Global.ChildBMonth.get(i));
            }

            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + i)))
                    .selectByVisibleText(Global.ChildBYear.get(i));
            _GenericFunctions.clickOnSkySales(wd);

            //Check if Passport details exist
            InputPassportDetails(wd, strView, "CHD", i);
        }
    }

    public void EnterInfantDetails(WebDriver wd) throws ParseException {
        Boolean blnExist = false;
        int intTotalCount = Integer.parseInt(Global.InfantCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        _GenericFunctions.GenerateLastName();
        for (int i = 0; i < intTotalCount; i++) {
            System.out.println("Entering Passenger Details for Infant#: " + (i + 1));
            //Input Infant Name
            _GenericFunctions.InputData_ById(wd, strView + "_DropDownListAssign" + i + "_" + i, "Adult " + (i + 1));
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxFirstName_" + i + "_" + i);
            _GenericFunctions.ClearData_ById(wd, strView + "TextBoxLastName_" + i + "_" + i);
            _GenericFunctions.InputData_ById(wd, strView + "TextBoxFirstName_"
                    + "" + i + "_" + i, Global.InfantFirstName.get(i));
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxFirstName_" + i + "_" + i);
            _GenericFunctions.InputData_ById(wd, strView + "TextBoxLastName_"
                    + "" + i + "_" + i, Global.UniversalLName + Global.InfantLastName.get(i));
            _GenericFunctions.ClickElement_ById(wd, strView + "TextBoxLastName_" + i + "_" + i);
            _GenericFunctions.clickOnSkySales(wd);
            new Select(wd.findElement(By.id(strView + "DropDownListGender_" + i + "_" + i)))
                    .selectByVisibleText(Global.InfantGender.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            //_GenericFunctions.ClickElement_ById(wd, "SkySales");
            //Input Infant DOB
            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + i + "_" + i)))
                    .selectByVisibleText(Global.InfantBDAY.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i + "_" + i)))
                    .selectByVisibleText(Global.InfantBMonth.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + i + "_" + i)))
                    .selectByVisibleText(Global.InfantBYear.get(i));
            _GenericFunctions.clickOnSkySales(wd);
            //Check if Passport details exist
            InputPassportDetails(wd, strView, "INF", i);
        }
    }

    public void EnterMonthAdt(WebDriver wd, String Month) {
        Boolean blnExist = false;
        int intTotalCount = Integer.parseInt(Global.AdultCount);
        for (int i = 0; i < intTotalCount; i++) {
            wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                    + "_DropDownListBirthDateMonth_" + i)).sendKeys(Month);
            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentCountry0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentDateMonth0_" + i)).sendKeys(Month);
            }
        }
    }

    public void EnterPassportCountryAdt(WebDriver wd, String Country) {
        Boolean blnExist = false;
        int intTotalCount = Integer.parseInt(Global.AdultCount);
        for (int i = 0; i < intTotalCount; i++) {
            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentCountry0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentCountry0_" + i)).sendKeys(Country);
            }
        }
    }

    public void EnterPassportCountryChd(WebDriver wd, String Country) {
        Boolean blnExist = false;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = intStartChild; i < intTotalCount; i++) {

            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentCountry0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentCountry0_" + i)).sendKeys(Country);
            }
        }
    }

    public void EnterMonthChd(WebDriver wd, String Month) {
        Boolean blnExist = false;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = intStartChild; i < intTotalCount; i++) {
            wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                    + "_DropDownListBirthDateMonth_" + i)).sendKeys(Month);
            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentDateMonth0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentDateMonth0_" + i)).sendKeys(Month);
            }
        }
    }

    public void EnterMonthInf(WebDriver wd, String Month) {
        Boolean blnExist = false;
        int intTotalCount = Integer.parseInt(Global.InfantCount);
        for (int i = 0; i < intTotalCount; i++) {
            wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                    + "_DropDownListBirthDateMonth_" + i + "_" + i)).sendKeys(Month);
            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentDateMonth0_0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentDateMonth0_" + i + "_" + i)).sendKeys(Month);
            }
        }
    }

    public void EnterPassportCountryInf(WebDriver wd, String Country) {
        Boolean blnExist = false;
        int intTotalCount = Integer.parseInt(Global.InfantCount);
        for (int i = 0; i < intTotalCount; i++) {
            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER"
                    + "_PassengerInputViewPassengerView_DropDownListDocumentCountry0_0_0");
            if (blnExist) {
                wd.findElement(By.id("CONTROLGROUPPASSENGER_PassengerInputViewPassengerView"
                        + "_DropDownListDocumentCountry0_" + i + "_" + i)).sendKeys(Country);
            }
        }
    }

    public void EditAdultDob(WebDriver wd, String Passenger, String Day, String Month, String Year) throws ParseException {
        //int intTotalCount = Integer.parseInt(Global.AdultCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        // _GenericFunctions.GenerateLastName();
        //  for (int i = 0; i < intTotalCount; i++) {
        PassengerDetailsSync(wd);
        System.out.println("Updating Passenger DOB for Adult#: " + (Passenger));

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + Passenger)))
                .selectByVisibleText(Day);
        _GenericFunctions.clickOnSkySales(wd);

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + Passenger)))
                .selectByVisibleText(Month);
        _GenericFunctions.clickOnSkySales(wd);

        //Date currentDate = new Date();
        //int currentYear = currentDate.getYear();
        //int age = Integer.parseInt(DOB);
        //int yearOfBirth = currentYear - age;

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + Passenger)))
                .selectByVisibleText(Year);
        _GenericFunctions.clickOnSkySales(wd);

    }

    public void EditChildDob(WebDriver wd, String Passenger, String Day, String Month, String Year) throws ParseException {
        //int intTotalCount = Integer.parseInt(Global.AdultCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";

        //  int intStartChild = Integer.parseInt(Global.AdultCount);
        //   int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);

        // _GenericFunctions.GenerateLastName();
        //  for (int i = 0; i < intTotalCount; i++) {
        PassengerDetailsSync(wd);
        System.out.println("Updating Passenger DOB for Child#: " + (Passenger));

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + Passenger)))
                .selectByVisibleText(Day);
        _GenericFunctions.clickOnSkySales(wd);

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + Passenger)))
                .selectByVisibleText(Month);
        _GenericFunctions.clickOnSkySales(wd);

        //Date currentDate = new Date();
        //int currentYear = currentDate.getYear();
        //int age = Integer.parseInt(DOB);
        //int yearOfBirth = currentYear - age;

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + Passenger)))
                .selectByVisibleText(Year);
        _GenericFunctions.clickOnSkySales(wd);

    }

    public void EditInfantDob(WebDriver wd, String Passenger, String Day, String Month, String Year) throws ParseException {
        //int intTotalCount = Integer.parseInt(Global.AdultCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        // _GenericFunctions.GenerateLastName();
        //  for (int i = 0; i < intTotalCount; i++) {
        PassengerDetailsSync(wd);
        System.out.println("Updating Passenger DOB for Adult#: " + (Passenger));

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + Passenger + "_" + Passenger)))
                .selectByVisibleText(Day);
        _GenericFunctions.clickOnSkySales(wd);

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + Passenger + "_" + Passenger)))
                .selectByVisibleText(Month);
        _GenericFunctions.clickOnSkySales(wd);

        //Date currentDate = new Date();
        //int currentYear = currentDate.getYear();
        //int age = Integer.parseInt(DOB);
        //int yearOfBirth = currentYear - age;

        new Select(wd.findElement(By.id(strView + "DropDownListBirthDateDay_" + Passenger + "_" + Passenger)))
                .selectByVisibleText(Year);
        _GenericFunctions.clickOnSkySales(wd);

    }
    //}
}
