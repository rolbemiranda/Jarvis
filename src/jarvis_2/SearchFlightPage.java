package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchFlightPage {

    private static GenericFunctions _GenericFunctions;
    private static VerificationFunctions _VerificationFunctions;

    public SearchFlightPage() {
        _GenericFunctions = new GenericFunctions();
        _VerificationFunctions = new VerificationFunctions();
        new SelectFlightPage();
    }

    public void SelectCurrency(WebDriver wd, String MCC) {
        Boolean blnExist = false;
        String ActualResult = "FALSE";
        String locString = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                + "_DropDownListMCCCurrency']";
        blnExist = _GenericFunctions.IsElementExistXpath(wd, locString);

        if (blnExist) {
            new Select(wd.findElement(By.xpath(locString))).selectByVisibleText(MCC);
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("SelectCurrency", "SelectCurrency Check", "TRUE",
                ActualResult, " SelectCurrency Meals");

        Global.MCC = MCC;
    }

    public void select_FareType_TravelType(WebDriver wd, String FareType, String TravelType) throws Exception {
        Boolean blnExist = false;
        String ActualResult = "FALSE";
        String cmbFareType = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                + "_DropDownListFareTypes']";
        String cmbTravelType = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                + "_DropDownTravelType']";
        blnExist = _GenericFunctions.IsElementExistXpath(wd, cmbFareType);

        if (blnExist) {
            new Select(wd.findElement(By.xpath(cmbFareType))).selectByVisibleText(FareType);
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, TravelType);

        if (blnExist) {
            new Select(wd.findElement(By.xpath(cmbTravelType))).selectByVisibleText(TravelType);
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_StaffTravelSearchPage");
        }

        _GenericFunctions.JarvisVerificationPoint("select_FareType_TravelType", "select_FareType_TravelType Check", "TRUE",
                ActualResult, " select_FareType_TravelType");

    }

    public static Boolean roundTrip(WebDriver wd) {
        wd.findElement(By.id("ControlGroupSearchView_AvailabilitySearchInputSearchView_RoundTrip")).click();
        return true;
    }

    public static Boolean OneWayTrip(WebDriver wd) {
        wd.findElement(By.id("ControlGroupSearchView_AvailabilitySearchInputSearchView_RoundTrip")).click();
        wd.findElement(By.xpath(".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_OneWay']"
                + "")).click();
        return false;
    }

    public void SearchPageSync(WebDriver wd) {
        WebDriverWait wait = new WebDriverWait(wd, 30);
        //Wait Until Switch My FroundTriplight Window Exists
        try {
            System.out.println("waiting for Search Flight Page to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='"
                    + "ControlGroupSearchView_AvailabilitySearchInputSearchVieworiginStation1']")));
            System.out.println("Search Flight Page appeared.");
            Global.ContinueExecution = true;
        } catch (Exception e) {
            System.out.println("System can't find Search Flight Page.");
            Global.ContinueExecution = false;
        }
    }

    public void select_from_datePicker(WebDriver wd, String data) {
        String CurrMonth = "";
        String CurrYear = "";
        String[] arrData = data.split("=");
        for (int i = 1; i < 100; i++) {
            CurrMonth = _GenericFunctions.getSelectedValue(wd, ".//*[@id='ui-datepicker-div']"
                    + "/div[1]/div/div/select[1]", "XPATH");
            CurrYear = _GenericFunctions.getSelectedValue(wd, ".//*[@id='ui-datepicker-div']"
                    + "/div[1]/div/div/select[2]", "XPATH");
            if (CurrMonth == arrData[0] && CurrYear == arrData[1]) {
                break;
            }
            wd.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div[2]/div/a/span")).click();
        }

    }

    public void select_Market(WebDriver wd, String locator, String DepRet, String data, String blnCheck) {
        String strElement = "";
        String strValue;
        switch (DepRet.toUpperCase()) {
            case "RETURN":
                strElement = ".//*[@id='ControlGroupSearchView"
                        + "_AvailabilitySearchInputSearchViewdestinationStation1']";
                break;
            case "DEPART":
                strElement = ".//*[@id='ControlGroupSearchView"
                        + "_AvailabilitySearchInputSearchVieworiginStation1']";
                break;
        }
        wd.findElement(By.xpath(strElement)).sendKeys(data);

        if (!blnCheck.toUpperCase().equals("NO")) {
            strValue = _GenericFunctions.getSelectedValue(wd, strElement, "XPATH");
            if (strValue.toUpperCase().contains(data.toUpperCase())) {
                Global.ContinueExecution = true;
            } else {
                Global.ContinueExecution = false;
            }
        }
    }

    public void confirm_SearchPage_Bundle(WebDriver wd) throws Exception {

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_SelectFlightPage");
        }

        //Click Continue Button
        wd.findElement(By.xpath(".//*[@id='ControlGroupSearchView_ButtonSubmit']")).click();

        //Click Additional Popup
        //ClickRoulette(wd);

        //Flight Advisory
        ClickOkFlightAdvisory(wd);

        //Wait Until Switch My Flight Window Exists
        SearchFlightBundleSync(wd);
        Global.SelectVariant = "B";
    }

    public void confirm_SearchPage(WebDriver wd) throws Exception {

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_SelectFlightPage");
        }

        //Click Continue Button
        wd.findElement(By.xpath(".//*[@id='ControlGroupSearchView_ButtonSubmit']")).click();

        //Implemented last Sept 2013
        //ClickRoulette(wd);

        //Flight Advisory
        ClickOkFlightAdvisory(wd);

        //Wait Until Switch My Flight Window Exists
        SearchFlightSync(wd);
        Global.SelectVariant = "A";
    }

    public void confirm_SearchPageAB(WebDriver wd) throws Exception {
        Boolean blnExist = false;
        Boolean blnExist2 = false;
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_SelectFlightPage");
        }

        //Click Continue Button
        wd.findElement(By.xpath(".//*[@id='ControlGroupSearchView_ButtonSubmit']")).click();
        System.out.println("waiting for Search Flight to appear");

        //Flight Advisory
        ClickOkFlightAdvisory(wd);

        for (int i = 0; i < 20; i++) {        //Check for A Variant
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='"
                    + "ControlGroupSelectView_AgreementInputSelectView_CheckBoxAgreement']");
            if (blnExist) {
                Global.SelectVariant = "A";
                break;
            } else {
                blnExist2 = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='priceTrigger']");
                if (blnExist2) {
                    Global.SelectVariant = "B";
                    break;
                } else {
                    _GenericFunctions.wait("1000");
                }
            }
        }

        if (Global.SelectVariant.equals("A")) {
            SearchFlightSync(wd);
        } else {
            SearchFlightBundleSync(wd);
        }
    }

    public void ClickRoulette(WebDriver wd) {
        Boolean blnExist;
        String btnWebCheckin = ".//*[@id='divRoulette']/div[2]/input";

        //Click Ok Flight Advisory
        for (int i = 0; i < 3; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, btnWebCheckin);
            if (blnExist) {
                wd.findElement(By.xpath(btnWebCheckin)).click();
                break;
            }
            _GenericFunctions.wait("1000");
        }
    }

    public void ClickOkFlightAdvisory(WebDriver wd) {
        Boolean blnExist;
        String btnWebCheckin = ".//*[@id='flightAdvisory']/div[2]/input";

        //Click Ok Flight Advisory
        for (int i = 0; i < 3; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, btnWebCheckin);
            if (blnExist) {
                wd.findElement(By.xpath(btnWebCheckin)).click();
                blnExist = false;
                break;
            }
            _GenericFunctions.wait("1000");

        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='btnFlighAdvisoryOK']");
        if (blnExist) {
            wd.findElement(By.xpath(".//*[@id='btnFlighAdvisoryOK']")).click();
            _GenericFunctions.wait("1000");
        }
    }

    public void SearchFlightBundleSync(WebDriver wd) {
        WebDriverWait wait = new WebDriverWait(wd, 120);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Search Flight to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='priceTrigger']")));
            System.out.println("Select Flights Page appeared.");
            Global.ContinueExecution = true;
        } catch (Exception e) {
            System.out.println("System can't find Select Flights Page.");
            Global.ContinueExecution = false;
        }
    }

    public void SearchFlightSync(WebDriver wd) {
        //2mins wait
        WebDriverWait wait = new WebDriverWait(wd, 120);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Search Flight to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='"
                    + "ControlGroupSelectView_AgreementInputSelectView_CheckBoxAgreement']")));
            System.out.println("Select Flights Page appeared.");
            Global.ContinueExecution = true;
        } catch (Exception e) {
            System.out.println("System can't find Select Flights Page.");
            Global.ContinueExecution = false;
        }
    }

    public void select_FlightDate(WebDriver wd, String locator, String DepRet, String data) {
        String strElement = "";
        String strValue;
        switch (DepRet.toUpperCase()) {
            case "RETURN YEAR":
                strElement = "ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListMarketMonth2";
                break;
            case "RETURN DAY":
                strElement = "ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListMarketDay2";
                break;
            case "DEPART YEAR":
                strElement = "ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListMarketMonth1";
                break;
            case "DEPART DAY":
                strElement = "ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListMarketDay1";
                break;
        }

        if (!Global.IsRoundTrip.booleanValue()) {
            if (DepRet.toUpperCase().contains("DEPART")) {
                new Select(wd.findElement(By.id(strElement))).selectByVisibleText(data);
                _VerificationFunctions.ContinueExecutionCheck_ByID(wd, strElement, data);
            }
        } else {
            new Select(wd.findElement(By.id(strElement))).selectByVisibleText(data);
            _VerificationFunctions.ContinueExecutionCheck_ByID(wd, strElement, data);
        }


    }

    public void enter_Passenger_Count(WebDriver wd, String locator, String PassengerType, String data) {
        String CurrSelected = "";
        String locString = "";
        String strValue;
        switch (PassengerType.toUpperCase()) {
            case "ADULT":
                locString = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListPassengerType_ADT']";
                Global.AdultCount = data;
                break;

            case "CHILD":
                locString = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListPassengerType_CHD']";
                Global.ChildCount = data;
                break;

            case "INFANT":
                locString = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView"
                        + "_DropDownListPassengerType_INFANT']";
                Global.InfantCount = data;
                break;
        }

        while (CurrSelected.toUpperCase().compareTo(data.replace(".0", "").toUpperCase()) != 0) {
            wd.findElement(By.xpath(locString)).sendKeys(data);
            wd.findElement(By.xpath(locString)).sendKeys(Keys.RETURN);
            CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
        }
        _VerificationFunctions.ContinueExecutionCheck(wd, locString, data);

    }
}
