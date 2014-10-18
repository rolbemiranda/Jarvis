package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class FrontendFunctions {

    private static GenericFunctions _GenericFunctions;

    public FrontendFunctions() {
        _GenericFunctions = new GenericFunctions();
    }

    public void FrontendSync(WebDriver wd) {
        System.out.println("waiting for Front page to load....");
        Boolean blnExist;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='selOriginPicker']");
            if (blnExist) {
                System.out.println("Tigerair frontpage has loaded completely..");
                Global.ContinueExecution = true;
                break;
            } else {
                System.out.println("Tigerair frontpage doesn't appear");
                _GenericFunctions.wait("1000");
                Global.ContinueExecution = false;
            }
        }

        if (Global.ContinueExecution) {
            for (int i = 0; i < 100; i++) {
                blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='submitSearch']");
                if (blnExist) {
                    System.out.println("Tigerair frontpage has loaded completely..");
                    Global.ContinueExecution = true;
                    break;
                } else {
                    System.out.println("Tigerair frontpage doesn't appear");
                    _GenericFunctions.wait("1000");
                    Global.ContinueExecution = false;
                }
            }
        }

        if (Global.ContinueExecution) {
            for (int i = 0; i < 100; i++) {
                blnExist = _GenericFunctions.IsElementExistXpath(wd, "html/body/article[1]");
                if (blnExist) {
                    System.out.println("Tigerair frontpage has loaded completely..");
                    Global.ContinueExecution = true;
                    break;
                } else {
                    System.out.println("Tigerair frontpage doesn't appear");
                    _GenericFunctions.wait("1000");
                    Global.ContinueExecution = false;
                }
            }
        }


        long estimatedTime = System.currentTimeMillis() - startTime;
        _GenericFunctions.JarvisVerificationPoint("FrontendSync", "it took " + (estimatedTime / 1000) + " seconds to load th home page",
                "NULL", "NULL", "NULL");
    }

    public void openDateFilter(WebDriver wd, String Type) {
        Boolean blnExist = false;
        String strButton = "", strList = "";
        switch (Type.toUpperCase()) {
            case "DEPART":
                strButton = ".//*[@id='dateDepart']";
                strList = ".//*[@id='ui-datepicker-div']";
                break;
            case "RETURN":
                strButton = ".//*[@id='dateReturn']";
                strList = ".//*[@id='ui-datepicker-div']";
                break;
        }
        for (int i = 0; i < 5; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strButton);
            if (blnExist) {
                wd.findElement(By.xpath(strButton)).click();
                _GenericFunctions.wait("1500");
                blnExist = _GenericFunctions.IsElementExistXpath(wd, strList);
                if (blnExist) {
                    Global.ContinueExecution = true;
                } else {
                    Global.ContinueExecution = false;
                    _GenericFunctions.wait("1000");
                }
            }
        }
    }

    public void openMarketFilter(WebDriver wd, String Type) {
        Boolean blnExist = false;
        String strButton = "", strList = "";
        switch (Type.toUpperCase()) {
            case "ORIGIN":
                strButton = "selOriginPicker";
                strList = "livefilter-list-origin";
                break;
            case "DESTINATION":
                strButton = "selDestPicker";
                strList = "livefilter-list-destination";
                break;
        }

        for (int i = 0; i < 10; i++) {
            blnExist = _GenericFunctions.IsElementExist(wd, strButton);
            if (blnExist) {
                wd.findElement(By.id("dateDepart")).click();
                _GenericFunctions.wait("1000");
                wd.findElement(By.id(strButton)).click();
                wd.findElement(By.id(strButton)).click();
                _GenericFunctions.wait("2000");
                blnExist = _GenericFunctions.IsElementExist(wd, strList);
                if (blnExist) {
                    Global.ContinueExecution = true;
                    break;
                } else {
                    Global.ContinueExecution = false;
                    _GenericFunctions.wait("5000");
                }
            } else {
                _GenericFunctions.wait("1000");
            }

        }

    }

    public void checkIfMarketExist(WebDriver wd, String Market, String Type, String ExpectedResult) {
        Boolean blnExist = false;
        String strButton = "", ActualResult = "";

        switch (Type.toUpperCase()) {
            case "ORIGIN":
                strButton = ".//*[@id='origin-" + Market + "']";
                break;
            case "DESTINATION":
                strButton = ".//*[@id='dest-" + Market + "']";
                break;
        }
        if (Global.MarketCheckIndicator) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strButton);
            if (blnExist) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
                _GenericFunctions.wait("500");
            }
        } else {
            ActualResult = Type + " Market Dropdown failed. Please check.";
            Global.MarketCheckIndicator = false;
        }

        if (!ActualResult.toUpperCase().equals(ExpectedResult.toUpperCase())) {
            Global.MarketCheckIndicator = false;
        }

        _GenericFunctions.JarvisVerificationPoint("checkIfMarketExist", "Check if Market Exist: " + Market,
                ExpectedResult, ActualResult, Market + " is missing.");
    }

    public void ResetMarketCheckIndicator() {
        Global.MarketCheckIndicator = true;
    }

    public void selectMarketViaClick(WebDriver wd, String Market, String Type, String ExpectedResult) throws Exception {
        Boolean blnExist = false;
        String strButton = "", ActualResult = "";
        switch (Type.toUpperCase()) {
            case "ORIGIN":
                strButton = ".//*[@id='origin-" + Market + "']";
                break;
            case "DESTINATION":
                strButton = ".//*[@id='dest-" + Market + "']";
                break;
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_MarketList" + Type);
        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, strButton);
        if (blnExist) {
            wd.findElement(By.xpath(strButton)).click();
            Global.ContinueExecution = true;
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
            Global.ContinueExecution = false;
        }

        _GenericFunctions.JarvisVerificationPoint("selectMarketViaClick", "Select Market",
                ExpectedResult, ActualResult, "Missing Market");

    }

    public void selectMarketViaTypeTab(WebDriver wd, String Input, String Type) {
        Boolean blnExist = false;
        String strTextbox = "", ActualResult = "";
        switch (Type.toUpperCase()) {
            case "ORIGIN":
                strTextbox = ".//*[@id='selOriginPicker']";
                break;
            case "DESTINATION":
                strTextbox = ".//*[@id='selDestPicker']";
                break;
        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, strTextbox);
        if (blnExist) {
            wd.findElement(By.xpath(strTextbox)).clear();
            wd.findElement(By.xpath(strTextbox)).sendKeys(Input);
            wd.findElement(By.xpath(strTextbox)).sendKeys(Keys.TAB);
            _GenericFunctions.wait("3000");
            Global.ContinueExecution = true;
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
            Global.ContinueExecution = false;
        }

        _GenericFunctions.JarvisVerificationPoint("selectMarketViaClick", "Select Market",
                "TRUE", ActualResult, "Missing Market");
    }

    public void selectPassengerCount(WebDriver wd, String Type, String Count) {
        Boolean blnExist = false;
        String strTextbox = "", ActualResult = "";
        switch (Type.toUpperCase()) {
            case "ADULT":
                strTextbox = ".//*[@id='selAdult']";
                break;
            case "CHILD":
                strTextbox = ".//*[@id='selChild']";
                break;
            case "INFANT":
                strTextbox = ".//*[@id='selInfant']";
                break;
        }
        blnExist = wd.findElement(By.xpath(strTextbox)).isEnabled();
        if (blnExist) {
            wd.findElement(By.xpath(strTextbox)).sendKeys(Count);
            wd.findElement(By.xpath(strTextbox)).sendKeys(Keys.TAB);
            Global.ContinueExecution = true;
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
            Global.ContinueExecution = false;
        }
        _GenericFunctions.JarvisVerificationPoint("selectPassengerCount", "Select Passenger Count",
                "TRUE", ActualResult, "Missing " + Type + " Textbox");
    }

    public void checkIfPassengerCountExists(WebDriver wd, String Type, String Count, String ExpectedResult) {
        Boolean blnExist = false, blnCheck = false;
        String strTextbox = "", ActualResult = "";
        switch (Type.toUpperCase()) {
            case "ADULT":
                strTextbox = ".//*[@id='selAdult']";
                break;
            case "CHILD":
                strTextbox = ".//*[@id='selChild']";
                break;
            case "INFANT":
                strTextbox = ".//*[@id='selInfant']";
                break;
        }
        blnExist = wd.findElement(By.xpath(strTextbox)).isEnabled();
        if (blnExist) {
            blnCheck = new Select(wd.findElement(By.xpath(strTextbox))).getOptions().toString().contains(Count);
            Global.ContinueExecution = true;

            if (blnCheck) {
                ActualResult = "TRUE";
            } else {
                ActualResult = "FALSE";
            }


        } else {
            ActualResult = "FALSE";
            Global.ContinueExecution = false;
        }
        _GenericFunctions.JarvisVerificationPoint("checkIfPassengerCountExists", "Check Passenger Count",
                ExpectedResult, ActualResult, Type + " Count option: " + Count + " doesn't exist.");
    }

    public void selectDate(WebDriver wd, String Type, String Month, String Year, String Day) {
        Boolean blnExist = false;
        String strSeq = "", strDayLocation = "", strDayTable = ".//*[@id='ui-datepicker-div']";
        String strActual = "";

        switch (Type.toUpperCase()) {
            case "DEPART":
                strSeq = "2";

                break;
            case "RETURN":
                strSeq = "2";
                break;
        }
        openDateFilter(wd, Type);
        new Select(wd.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div[2]/div/div/select[1]")))
                .selectByValue(Month);
        new Select(wd.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div[2]/div/div/select[2]")))
                .selectByValue(Year.replace(".0", ""));

        for (int i = 1; i < 7; i++) {
            for (int ii = 1; ii < 8; ii++) {
                strDayLocation = "/div[" + strSeq + "]/table/tbody/tr[" + i + "]/td[" + ii + "]/a";
                blnExist = _GenericFunctions.IsElementExistXpath(wd, strDayTable + strDayLocation);
                if (blnExist) {
                    strActual = wd.findElement(By.xpath(strDayTable + strDayLocation)).getText();
                    if (strActual.toUpperCase().equals(Day.toUpperCase())) {
                        wd.findElement(By.xpath(strDayTable + strDayLocation)).click();
                        strActual = "SELECTED";
                        openMarketFilter(wd, "ORIGIN");
                        break;
                    }
                }
            }
            if (strActual.toUpperCase().equals("SELECTED")) {
                break;
            }
        }
    }

    public void checkIfSearchIsActivated(WebDriver wd, String ExpectedResult) {
        Boolean blnExist = false;
        String strSearch = ".//*[@id='submitSearch']";
        String ActualResult = "";

        blnExist = wd.findElement(By.xpath(strSearch)).isEnabled();
        if (blnExist) {
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("checkIfSearchIsActivated", "Check if Search Now button is activated",
                ExpectedResult, ActualResult, "Search Button Activation error.");
    }

    public void CheckIfRoundtrip(WebDriver wd, String ExpectedResult) {
        String ActualResult = "";
        String strSearch = ".//*[@id='ControlGroupSelectBundleView"
                + "_AvailabilitySearchInputSelectBundleView_RoundTrip']";
        Boolean blnSelected = false;

        blnSelected = wd.findElement(By.xpath(strSearch)).isSelected();
        if (blnSelected) {
            ActualResult = "roundTrip";
        } else {
            ActualResult = "onewaytrip";
        }
        _GenericFunctions.JarvisVerificationPoint("CheckIfRoundtrip", "Check if current search is roundtrip/Oneway",
                ExpectedResult.toUpperCase(), ActualResult.toUpperCase(),
                "Expected flight type is " + ExpectedResult);
    }

    public void ClickSearchNow(WebDriver wd) {
        Boolean blnExist = false;
        String strSearch = ".//*[@id='submitSearch']";
        String ActualResult = "";

        blnExist = wd.findElement(By.xpath(strSearch)).isEnabled();
        if (blnExist) {
            wd.findElement(By.xpath(strSearch)).click();
            ActualResult = "TRUE";
            Global.ContinueExecution = true;
        } else {
            ActualResult = "FALSE";
            Global.ContinueExecution = false;
        }

        _GenericFunctions.JarvisVerificationPoint("ClickSearchNow", "Click Search Now",
                "TRUE", ActualResult, "Search Button is disabled");
    }

    public void checkFrontendIntegration(WebDriver wd, String Type, String ExpectedValue) {
        String strElement = "", strValue = "", ActualResult = "";
        Boolean blnExist = false;
        switch (Type.toUpperCase()) {
            case "DEPARTING":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleVieworiginStation1']";
                break;
            case "ARRIVING":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleViewdestinationStation1']";
                break;
            case "DEPART_DAY":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListMarketDay1']";
                break;
            case "RETURN_DAY":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListMarketDay2']";
                break;
            case "DEPART_MONTH":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListMarketMonth1']";
                break;
            case "RETURN_MONTH":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListMarketMonth2']";
                break;
            case "ADULT":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListPassengerType_ADT']";
                break;
            case "CHILD":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListPassengerType_CHD']";
                break;
            case "INFANT":
                strElement = ".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilitySearchInputSelectBundleView_DropDownListPassengerType_INFANT']";
                break;
        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, strElement);
        if (blnExist) {
            ActualResult = wd.findElement(By.xpath(strElement)).getAttribute("value").toUpperCase().trim();
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("checkFrontendIntegration", "Click Search Now",
                ExpectedValue.toUpperCase().trim(), ActualResult, "Frontend passes wrong value on " + Type + ". Please check.");
    }
}
