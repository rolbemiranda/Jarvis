package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ManageMyBooking {

    private static GenericFunctions _GenericFunctions;
    private static SelectFlightPage _SelectFlightPage;
    private static PassengerPage _PassengerPage;

    public ManageMyBooking() {
        _GenericFunctions = new GenericFunctions();
        _SelectFlightPage = new SelectFlightPage();
        _PassengerPage = new PassengerPage();
    }

    public void RetrieveBookingSync(WebDriver wd) {
        System.out.println("waiting for Retrieve Booking Page to appear");
        Boolean blnExist;
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupRetrieveBookingView"
                    + "_BookingRetrieveInputRetrieveBookingView_ButtonSubmit']");
            if (blnExist) {
                System.out.println("Retrieve Booking Page appeared");
                break;
            } else {
                System.out.println("Retrieve Booking Page doesn't appear");
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void MMBHomePageSync(WebDriver wd) {
        Boolean blnExist;
        System.out.println("waiting for MMB Home Page to appear");
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='printPage']");
            if (blnExist) {
                break;
            } else {
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void SearchChangePageSync(WebDriver wd) {
        Boolean blnExist;
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSearchChangeView"
                    + "_AvailabilitySearchInputSearchChangeView_TextBoxMarketOrigin1']");
            if (blnExist) {
                break;
            } else {
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void MMBLogin(WebDriver wd, String LName, String FName, String PNR) throws Exception {
        if (LName.toUpperCase().equals("NULL")) {
            LName = Global.AdultLastName.get(0);
        }

        //Input Login Details
        wd.findElement(By.id("ControlGroupRetrieveBookingView_BookingRetrieveInputRetrieveBookingView"
                + "_TextBoxBookingReferenceNo")).sendKeys(PNR);
        wd.findElement(By.id("ControlGroupRetrieveBookingView_BookingRetrieveInputRetrieveBookingView"
                + "_TextBoxPassengerFirstName")).sendKeys(FName);
        wd.findElement(By.id("ControlGroupRetrieveBookingView_BookingRetrieveInputRetrieveBookingView"
                + "_TextBoxPassengerLastName")).sendKeys(LName);

        //Initialize PNR
        Global.Booking_PNR = PNR;

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_Retrieve Booking Page");
        }

        //Click Retrieve Booking Button
        wd.findElement(By.xpath(".//*[@id='ControlGroupRetrieveBookingView"
                + "_BookingRetrieveInputRetrieveBookingView_ButtonSubmit']")).click();

        //Home Page Sync
        MMBHomePageSync(wd);

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "BBB_MMB Home Page");
        }
    }

    public void MMBMode(WebDriver wd, String MMBMode) {

        switch (MMBMode.toUpperCase()) {
            case "FLIGHT":
                wd.findElement(By.xpath(".//*[@id='ChangeControl_LinkButtonChangeFlights']")).click();
                SearchChangePageSync(wd);
                Global.MMBMode = "FLIGHT";
                break;
            case "SEAT":
                wd.findElement(By.xpath(".//*[@id='ChangeControl_LinkButtonChangeSeat']")).click();
                SearchChangePageSync(wd);
                Global.MMBMode = "SEAT";
                break;
            case "ADDON":
                wd.findElement(By.xpath(".//*[@id='ChangeControl_LinkButtonAddOns']")).click();
                SearchChangePageSync(wd);
                Global.MMBMode = "ADDON";
                break;
        }
    }

    public void ChangeDepartingFlight(WebDriver wd, String Day, String Month, String EnableChange) {
        Boolean IsExist = false;
        if (EnableChange.toUpperCase().equals("YES")) {
            IsExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSearchChangeView"
                    + "_AvailabilitySearchInputSearchChangeView_CheckBoxChangeMarket_1']");
            if (IsExist) {
                wd.findElement(By.xpath(".//*[@id='ControlGroupSearchChangeView"
                        + "_AvailabilitySearchInputSearchChangeView_CheckBoxChangeMarket_1']")).click();

                new Select(wd.findElement(By.id("ControlGroupSearchChangeView_AvailabilitySearchInputSearchChangeView"
                        + "_DropDownListMarketMonth1"))).selectByVisibleText(Month);

                new Select(wd.findElement(By.id("ControlGroupSearchChangeView_AvailabilitySearchInputSearchChangeView"
                        + "_DropDownListMarketDay1"))).selectByVisibleText(Day);
            }
        }

    }

    public void ChangeArrivingFlight(WebDriver wd, String Day, String Month, String EnableChange) {
        Boolean IsExist = false;
        if (EnableChange.toUpperCase().equals("YES")) {
            IsExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSearchChangeView"
                    + "_AvailabilitySearchInputSearchChangeView_CheckBoxChangeMarket_2']");
            if (IsExist) {
                wd.findElement(By.xpath(".//*[@id='ControlGroupSearchChangeView"
                        + "_AvailabilitySearchInputSearchChangeView_CheckBoxChangeMarket_2']")).click();
                new Select(wd.findElement(By.id("ControlGroupSearchChangeView_AvailabilitySearchInputSearchChangeView"
                        + "_DropDownListMarketMonth2"))).selectByVisibleText(Month);
                new Select(wd.findElement(By.id("ControlGroupSearchChangeView_AvailabilitySearchInputSearchChangeView"
                        + "_DropDownListMarketDay2"))).selectByVisibleText(Day);
            }
        }

    }

    public void ChangeFlight_NextStep(WebDriver wd) throws Exception {
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCCA_Select Change");
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupSearchChangeView_LinkButtonSubmit']")).click();
        SelectChangePageSync(wd);
    }

    public void SelectChangePageSync(WebDriver wd) {
        Boolean blnExist;
        System.out.println("waiting for Select Change Page to appear");
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupSelectChangeView"
                    + "_AgreementInputSelectChangeView_CheckBoxAgreement']");
            if (blnExist) {
                break;
            } else {
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void changeFlight_AgreementBox(WebDriver wd) {
        wd.findElement(By.id("ControlGroupSelectChangeView_AgreementInputSelectChangeView"
                + "_CheckBoxAgreement")).click();
    }

    public void selectChangeFare(WebDriver wd, String locator, String locString, String data) {
        String strWebLocation = "";
        Boolean blnExist = false;
        switch (data.toUpperCase()) {
            case "DEPART": {
                for (int intIndex = 1; intIndex < 100; intIndex++) {
                    if (locString.equals("NULL")) {
                        strWebLocation = "ControlGroupSelectChangeView_AvailabilityInputSelectChangeView_RadioButtonMkt1Fare" + intIndex;
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
                            strWebLocation = "ControlGroupSelectChangeView_AvailabilityInputSelectChangeView_RadioButtonMkt2Fare" + intIndex;
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

    public void confirm_SelectChange(WebDriver wd) throws Exception {
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "CCCB_Select Change");
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupSelectChangeView_ButtonSubmit']")).click();
        _SelectFlightPage.PassengerPageSync(wd);
    }

    public void ChangeAddon(WebDriver wd) throws Exception {
        Boolean IsExist;
        _SelectFlightPage.PassengerPageSync(wd);

        IsExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ssrAlert1']/div[2]/input[1]");
        if (IsExist) {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDDA_Passenger_SSR Warning Window");
            }
            wd.findElement(By.xpath(".//*[@id='ssrAlert1']/div[2]/input[1]")).click();
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDDB_Passenger_Change Addon Page");
        }

        //Click Submit Passenger Page
        wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPASSENGER_ButtonSubmit']")).click();

        //Click okay if insurance is not selected
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //Wait Until the NEW Auto-Seat Assignment Page Exists
        _PassengerPage.SeatAssignmentSync(wd);
    }

    public void ChangeSeat(WebDriver wd) throws Exception {
        Boolean IsExist;
        //Wait Until the NEW Auto-Seat Assignment Page Exists
        _PassengerPage.SeatAssignmentSync(wd);

        //Click OK Notification
        IsExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='seatReAssignPopup']/div[3]/input");
        if (IsExist) {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "EEE_Change Seat Notification");
            }
            wd.findElement(By.xpath(".//*[@id='seatReAssignPopup']/div[3]/input")).click();
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "FFF_Change Seat Page");
        }

    }

    public void IsRoundTrip(String IsRoundTrip) {
        if (IsRoundTrip.toUpperCase().equals("YES")) {
            Global.IsRoundTrip = true;
        }
    }
}
