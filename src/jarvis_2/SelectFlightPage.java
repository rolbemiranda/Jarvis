package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectFlightPage {

    private static GenericFunctions _GenericFunctions;

    public SelectFlightPage() {
        _GenericFunctions = new GenericFunctions();
    }

    public void check_AgreementBox(WebDriver wd) {
        Boolean blnSiteB = false;
        String strView = "";

        if (Global.SelectVariant.equals("A")) {
            blnSiteB = _GenericFunctions.IsElementExistXpath(wd,
                    ".//*[@id='ControlGroupSelectFlightView_AgreementInputSelectFlightView_CheckBoxAgreement']");

            if (blnSiteB) {
                strView = "ControlGroupSelectFlightView_AgreementInputSelectFlightView_CheckBoxAgreement";
            } else {
                strView = "ControlGroupSelectView_AgreementInputSelectView_CheckBoxAgreement";
            }
            wd.findElement(By.id(strView)).click();
        }

    }

    public void check_AgreementBox_Orig(WebDriver wd) {
        wd.findElement(By.id("ControlGroupSelectView_AgreementInputSelectView_CheckBoxAgreement")).click();
    }

    public void select_FareAB(WebDriver wd, String locator, String locString, String data, String BundleFare) {
        if (Global.SelectVariant.equals("A")) {
            select_Fare(wd, locator, locString, data);
        } else {
            select_BundleFare(wd, locator, locString, data, BundleFare);
        }
    }

    public void select_Fare(WebDriver wd, String locator, String locString, String data) {
        String strWebLocation = "";
        String strView = "";
        Boolean blnExist = false;
        Boolean blnSiteB = false;
        Boolean blnSiteC = false;
        blnSiteB = _GenericFunctions.IsElementExistXpath(wd,
                ".//*[@id='ControlGroupSelectFlightView_AgreementInputSelectFlightView_CheckBoxAgreement']");

        //Fare Bundle View
        blnSiteC = _GenericFunctions.IsElementExistXpath(wd,
                ".//*[@id='priceTrigger']");

        if (blnSiteB) {
            strView = "ControlGroupSelectFlightView_AvailabilityInputSelectFlightView_";
        } else {
            if (blnSiteC) {
                strView = "ControlGroupSelectBundleView_AvailabilityInputSelectBundleView_";
            } else {
                strView = "ControlGroupSelectView_AvailabilityInputSelectView_";
            }
        }

        switch (data.toUpperCase()) {
            case "DEPART": {
                for (int intIndex = 1; intIndex < 100; intIndex++) {
                    if (locString.equals("NULL")) {
                        strWebLocation = strView + "RadioButtonMkt1Fare" + intIndex;
                        blnExist = _GenericFunctions.IsElementExist(wd, strWebLocation);
                        if (blnExist) {
                            intIndex = 101;
                        }
                    } else {
                        strWebLocation = locString;
                        blnExist = true;
                        intIndex = 101;;
                    }
                }
                //Click Fare if it exists
                if (blnExist) {
                    wd.findElement(By.id(strWebLocation)).click();
                    _GenericFunctions.wait("4000");
                    wd.findElement(By.id(strWebLocation)).click();
                }
                break;
            }
            case "RETURN": {
                if (Global.IsRoundTrip) {
                    for (int intIndex = 1; intIndex < 100; intIndex++) {
                        if (locString.equals("NULL")) {
                            strWebLocation = strView + "RadioButtonMkt2Fare" + intIndex;
                            blnExist = _GenericFunctions.IsElementExist(wd, strWebLocation);
                            if (blnExist) {
                                intIndex = 101;
                            }
                        } else {
                            strWebLocation = locString;
                            blnExist = true;
                            intIndex = 101;
                        }
                    }
                }
                //Click Fare if it exists
                if (blnExist) {
                    wd.findElement(By.id(strWebLocation)).click();
                    _GenericFunctions.wait("4000");
                    wd.findElement(By.id(strWebLocation)).click();
                }
            }


        }
    }

    public void select_BundleFare(WebDriver wd, String locator, String locString, String data, String BundleFare) {
        String strWebLocation = "";
        String strTableLocation = "";
        String IsBundleFareAvailable = "";
        String strView = "";
        Boolean blnExist = false;
        Boolean blnSiteB = false;
        Boolean blnSiteC = false;
        Integer intCount = 1;
        Integer CtrTotal = 100;

        switch (data.toUpperCase()) {
            case "DEPART": {
                strWebLocation = ".//*[@id='tableMarket1']";
                for (intCount = 1; intCount < CtrTotal; intCount++) {
                    switch (BundleFare.toUpperCase()) {
                        case "LIGHT":
                            strTableLocation = "/tr[" + intCount + "]/td[2]/label";
                            break;
                        case "COMBO":
                            strTableLocation = "/tr[" + intCount + "]/td[3]/label";
                            break;
                        case "COMBO_PLUS":
                            strTableLocation = "/tr[" + intCount + "]/td[4]/label";
                            break;
                    }
                    //Click Fare if it exists
                    blnExist = _GenericFunctions.IsElementExistXpath(wd, strWebLocation + strTableLocation);
                    if (blnExist) {
                        IsBundleFareAvailable = _GenericFunctions.ExtractTextFromTable(wd, "", strWebLocation, "." + strTableLocation);
                        if (!IsBundleFareAvailable.toUpperCase().contains("NOT EXIST")) {
                            if (!IsBundleFareAvailable.toUpperCase().contains("NOT AVAILABLE")) {
                                wd.findElement(By.xpath(strWebLocation + strTableLocation)).click();
                                _GenericFunctions.wait("1500");
                                Global.ContinueExecution = true;
                                break;
                            }
                        }
                    } else {
                        Global.ContinueExecution = false;
                    }
                }
                break;
            }
            case "RETURN": {
                strWebLocation = ".//*[@id='tableMarket2']";
                for (intCount = 1; intCount < CtrTotal; intCount++) {
                    if (Global.IsRoundTrip) {
                        switch (BundleFare.toUpperCase()) {
                            case "LIGHT":
                                strTableLocation = "/tr[" + intCount + "]/td[2]/label";
                                break;
                            case "COMBO":
                                strTableLocation = "/tr[" + intCount + "]/td[3]/label";
                                break;
                            case "COMBO_PLUS":
                                strTableLocation = "/tr[" + intCount + "]/td[4]/label";
                                break;
                        }
                        //Click Fare if it exists
                        blnExist = _GenericFunctions.IsElementExistXpath(wd, strWebLocation + strTableLocation);
                        if (blnExist) {
                            IsBundleFareAvailable = _GenericFunctions.ExtractTextFromTable(wd, "", strWebLocation, "." + strTableLocation);
                            if (!IsBundleFareAvailable.toUpperCase().contains("NOT EXIST")) {
                                if (!IsBundleFareAvailable.toUpperCase().contains("NOT AVAILABLE")) {
                                    wd.findElement(By.xpath(strWebLocation + strTableLocation)).click();
                                    _GenericFunctions.wait("1500");
                                    Global.ContinueExecution = true;
                                    break;
                                }
                            }

                        } else {
                            Global.ContinueExecution = false;
                        }
                    }
                }
                break;
            }
        }
    }

    public void select_Fare_Orig(WebDriver wd, String locator, String locString, String data) {
        String strWebLocation = "";
        Boolean blnExist = false;
        switch (data.toUpperCase()) {
            case "DEPART": {
                for (int intIndex = 1; intIndex < 100; intIndex++) {
                    if (locString.equals("NULL")) {
                        strWebLocation = "ControlGroupSelectView_AvailabilityInputSelectView_RadioButtonMkt1Fare" + intIndex;
                        blnExist = _GenericFunctions.IsElementExist(wd, strWebLocation);
                        if (blnExist) {
                            intIndex = 101;
                        }
                    } else {
                        strWebLocation = locString;
                        blnExist = true;
                        intIndex = 101;;
                    }
                }
                //Click Fare if it exists
                if (blnExist) {
                    wd.findElement(By.id(strWebLocation)).click();
                    _GenericFunctions.wait("4000");
                    wd.findElement(By.id(strWebLocation)).click();
                }
                break;
            }
            case "RETURN": {
                if (Global.IsRoundTrip) {
                    for (int intIndex = 1; intIndex < 100; intIndex++) {
                        if (locString.equals("NULL")) {
                            strWebLocation = "ControlGroupSelectView_AvailabilityInputSelectView_RadioButtonMkt2Fare" + intIndex;
                            blnExist = _GenericFunctions.IsElementExist(wd, strWebLocation);
                            if (blnExist) {
                                intIndex = 101;
                            }
                        } else {
                            strWebLocation = locString;
                            blnExist = true;
                            intIndex = 101;
                        }
                    }
                }
                //Click Fare if it exists
                if (blnExist) {
                    wd.findElement(By.id(strWebLocation)).click();
                    _GenericFunctions.wait("4000");
                    wd.findElement(By.id(strWebLocation)).click();
                }
            }
        }
    }

    public Boolean SwitchMyFlightSync(WebDriver wd) {
        Boolean isExist = false;
        System.out.println("Checking if Switch My Flight Option exist.");
        for (int i = 0; i < 11; i++) {
            isExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSelectView_LinkButtonSubmit']");

            if (isExist) {
                System.out.println("Switch My Flight Option exist.");
                i = 12;
                isExist = true;

            } else {
                _GenericFunctions.wait("1000");
                isExist = false;
            }
        }
        return isExist;
    }

    public void PassengerPageSync(WebDriver wd) {
        WebDriverWait wait = new WebDriverWait(wd, 120);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Passenger Page to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='CONTROLGROUPPASSENGER_ButtonSubmit']")));
            System.out.println("Passenger Page appeared.");
            Global.ContinueExecution = true;
        } catch (Exception e) {
            System.out.println("System can't find Passenger Page.");
            Global.ContinueExecution = false;
        }
    }

    //If switch my flight page does not exist, it will proceed to passenger page
    public void confirm_SelectFlight(WebDriver wd) throws Exception {
        Boolean blnExist;

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "BBB_SelectFlightPage");
        }

        //Click Switch My Flight Button
        blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='SwitchMyFlightProxyButton']");
        if (blnExist) {
            wd.findElement(By.xpath(".//*[@id='SwitchMyFlightProxyButton']")).click();

            //Wait Until Switch My Flight Window Exists
            //SwitchMyFlightSync(wd);
        } else {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSelectBundleView"
                    + "_AvailabilityInputSelectBundleView_ButtonSubmit']");

            //Fare Bundle Button
            if (blnExist) {
                wd.findElement(By.xpath(".//*[@id='ControlGroupSelectBundleView"
                        + "_AvailabilityInputSelectBundleView_ButtonSubmit']")).click();
            } else {
                wd.findElement(By.xpath(".//*[@id='ControlGroupSelectView_ButtonSubmit']")).click();
            }
            //Switch my flight is unavailable, hence system will proceed to passenger page
            PassengerPageSync(wd);
            Global.SelectVariant = "A";
        }
    }

    public void confirm_SwitchMyFlight(WebDriver wd) {
        Boolean isExist;
        //Wait Until Switch My Flight Window Exists
        isExist = SwitchMyFlightSync(wd);

        if (isExist) {
            //Click Switch My Flight Button
            wd.findElement(By.id("ControlGroupSelectView_LinkButtonSubmit")).click();
        }

        //Wait Until Passenger Page Exists
        PassengerPageSync(wd);
    }

    public void SwitchMyFlight(WebDriver wd, String DepRet, String data) {
        //Wait Until Switch My Flight Window Exists
        SwitchMyFlightSync(wd);

        //Choose Option
        switch (data.toUpperCase()) {
            case "NO":
                break;
            case "YES":
                switch (DepRet.toUpperCase()) {
                    case "DEPART":
                        wd.findElement(By.id("ControlGroupSelectView_AvailabilityInputSelectView_DecisionRadioButton0")).click();
                        break;
                    case "RETURN":
                        wd.findElement(By.id("ControlGroupSelectView_AvailabilityInputSelectView_DecisionRadioButton1")).click();
                        break;
                }
                break;
        }

    }

    public void ClickEditSearch(WebDriver wd) {
        wd.findElement(By.xpath(".//*[@id='editSearch']")).click();
        _GenericFunctions.wait("3000");
    }
}
