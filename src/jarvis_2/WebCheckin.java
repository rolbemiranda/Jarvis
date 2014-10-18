package jarvis_2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Date;

/**
 *
 * @author kassmiranda
 */
public class WebCheckin {

    private static GenericFunctions _GenericFunctions;
    private static PassengerPage _PassengerPage;

    public WebCheckin() {
        _GenericFunctions = new GenericFunctions();
        _PassengerPage = new PassengerPage();
    }

    public void WebCheckinSync(WebDriver wd) {
        WebDriverWait wait = new WebDriverWait(wd, 1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(".//*[@id='wbciCarrierSelectOk']")));
        } catch (Exception e) {
        }
    }

    public void WCGenericSync(WebDriver wd, String ElementXpath) {
        WebDriverWait wait = new WebDriverWait(wd, 1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ElementXpath)));
        } catch (Exception e) {
        }
    }

    public void SelectCarrier(WebDriver wd, String Carrier) throws Exception {
        Boolean blnExist = false;
        String ActualResult;
        String CarrierDropDown = ".//*[@id='wbciCarrierSelect']";
        String RetrieveFlightButton = ".//*[@id='CONTROLGROUPWEBCHECKINVIEW"
                + "_WebCheckinRetrieveBookingInputWebCheckinView_ButtonSubmit']";

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZA_Carrier Selection Page");
        }

        blnExist = _GenericFunctions.IsElementExistXpath(wd, CarrierDropDown);
        if (blnExist) {
            new Select(wd.findElement(By.xpath(CarrierDropDown))).selectByVisibleText(Carrier);
            Global.WebCheckinCarrier = Carrier;
            Global.IsWebcheckin = true;
            wd.findElement(By.xpath(".//*[@id='wbciCarrierSelectOk']")).click();
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }
        _GenericFunctions.JarvisVerificationPoint("Webcheckin Page", "Webcheckin Carrier Dropdown Check",
                "TRUE", ActualResult, "Missing Webcheckin Carrier Dropdown");
    }

    public void RetrieveYourFlight(WebDriver wd, String PNR, String DepartureCity) throws Exception {
        Boolean blnExist;
        int intSeq = 0;
        String ActualResult,
                BookingReference = "",
                CmbDepartureCity = "",
                View = "CONTROLGROUPWEBCHECKINVIEW_WebCheckinRetrieveBookingInputWebCheckinView",
                RetrieveFlightButton = View + "_ButtonSubmit";

        switch (Global.WebCheckinCarrier.toUpperCase()) {
            case "TIGERAIR SINGAPORE":
                BookingReference = ".//*[@id='" + View + "_TextBoxBookingReferenceNo']";
                CmbDepartureCity = ".//*[@id='" + View + "_DropDopwnListStations']";
                intSeq = 0;
                break;
            case "TIGERAIR MANDALA":
                BookingReference = ".//*[@id='" + View + "_TextBoxBookingReferenceNoRI']";
                CmbDepartureCity = ".//*[@id='" + View + "_DropDopwnListStationsRI']";
                intSeq = 2;
                break;
            case "TIGERAIR AUSTRALIA":
                BookingReference = ".//*[@id='" + View + "_TextBoxBookingReferenceNoTT']";
                CmbDepartureCity = ".//*[@id='" + View + "_DropDopwnListStationsTT']";
                intSeq = 1;
                break;
        }
        // if the Param3 = null, use the PNR generated in the creation of test data
        if (PNR.toUpperCase().contains("NULL")) {
            PNR = Global.Booking_PNR;
        }

        wd.findElement(By.xpath(BookingReference)).sendKeys(PNR);
        new Select(wd.findElement(By.xpath(CmbDepartureCity))).selectByVisibleText(DepartureCity);

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZB_Retrieve Booking Page");
        }
        //click of Retrieve flight button
        _GenericFunctions.wait("2000");
        wd.findElements(By.className("btnbooking")).get(intSeq).click();;
        
        
        //check if validation error exist
        blnExist = _GenericFunctions.IsElementExistXpath(wd, "//*[@id='selectMainBody']/div[1]");       
              
        // if no validation occur execute , else exit method
        if (!blnExist) {
            WCGenericSync(wd, ".//*[@id='checkToContinue']");

            blnExist = _GenericFunctions.IsElementExistXpath(wd, RetrieveFlightButton);
            if (blnExist) {
                ActualResult = "FALSE";
                Global.ContinueExecution = false;
            } else {
                
                ActualResult = "TRUE";
            }
            _GenericFunctions.JarvisVerificationPoint("Webcheckin Page", "Retrieve Booking Check",
                    "TRUE", ActualResult, "Unable to Retrieve booking");
        }
        else {
           if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZC_CheckValidationError");
             }
        }
    }

    public void CheckinPassenger(WebDriver wd, String WhatSegment, String PassengerToCheckin, String YESNO) throws Exception {
        Boolean blnExist = false;
        String Seq = "", SegmentCheckbox, StringI;
        int Ctr = 0,
                i = 0;

        switch (WhatSegment.toUpperCase()) {
            case "FIRST":
                WhatSegment = "0";
                break;
            case "SECOND":
                WhatSegment = "1";
                break;
            case "THIRD":
                WhatSegment = "2";
                break;
            case "FOURTH":
                WhatSegment = "3";
                break;
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZD_Select Passenger to Checkin ");
        }

        if (YESNO.toUpperCase().equals("YES")) {
            if (!PassengerToCheckin.toUpperCase().equals("ALL")) {
                SegmentCheckbox = "ControlGroupViewFlightView_CheckInPassengerInputViewFlightView"
                        + "_CheckBoxPaxJourney" + PassengerToCheckin + "_" + WhatSegment;
                wd.findElement(By.id(SegmentCheckbox)).click();
            } else {
                for (i = 0; i < 10; i++) {
                    StringI = Integer.toString(i);
                    SegmentCheckbox = "ControlGroupViewFlightView_CheckInPassengerInputViewFlightView"
                            + "_CheckBoxPaxJourney" + StringI + "_" + WhatSegment;
                    blnExist = _GenericFunctions.IsElementExist(wd, SegmentCheckbox);
                    if (blnExist) {
                        wd.findElement(By.id(SegmentCheckbox)).click();
                    } else {
                        //break;
                    }
                }
            }
        }

    }

    public void ConfirmCheckin(WebDriver wd) throws Exception {
        Date d1 = null;

        wd.findElement(By.xpath(".//*[@id='checkToContinue']")).click();
        wd.findElement(By.xpath(".//*[@id='ControlGroupViewFlightView"
                + "_CheckInPassengerInputViewFlightView_LinkButtonSubmit']")).click();

        //TT 
        if (Global.WebCheckinCarrier.toUpperCase().equals("TIGERAIR AUSTRALIA")) {
            WCGenericSync(wd, ".//*[@id='ControlGroupCheckInNewView_LinkButtonSubmit']");
        } else {
            //TR or RI
            WCGenericSync(wd, ".//*[@id='ControlGroupCheckInView_LinkButtonSubmit']");
        }
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZE_Hazmat Page");

        }

        if (Global.WebCheckinCarrier.toUpperCase().equals("TIGERAIR AUSTRALIA")) {
            wd.findElement(By.xpath(".//*[@id='notCarrying']")).click();
            wd.findElement(By.xpath(".//*[@id='ControlGroupCheckInNewView"
                    + "_AgreementViewCheckInNewPageView_CheckBoxAgreement']")).click();
            wd.findElement(By.id("ControlGroupCheckInNewView_LinkButtonSubmit")).click();
        } else {
            //For TR and RI Only
            wd.findElement(By.xpath(".//*[@id='ControlGroupCheckInView"
                    + "_AgreementViewCheckInPageView_CheckBoxAgreement']")).click();
            d1 = new Date();
            System.out.println("start time:" + d1);
            wd.findElement(By.xpath(".//*[@id='ControlGroupCheckInView_LinkButtonSubmit']")).click();

        }

        WCGenericSync(wd, ".//*[@id='printPage']");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            Date d2 = new Date();
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            System.out.print("time difference: ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            System.out.println();
            System.out.println("End time:" + d2);

            // write Timestamps logs
            FileWriter fw = new FileWriter("WBCI Logs\\WebCheckinLogs.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.append("Start time: " + d1);
            bw.append(" Time elapsed: " + diffMinutes + " minutes, " + diffSeconds + " seconds.");
            bw.append(" End time: " + d2);
            bw.close();


            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZZF_Boarding Pass");
        }
    }

    public void WebCheckinMMBMode(WebDriver wd, String MMBMode) {

        switch (MMBMode.toUpperCase()) {
            case "BAGGAGE":
                wd.findElement(By.id("LinkButtonBag")).click();
                WCGenericSync(wd, ".//*[@id='CONTROLGROUPPASSENGER_ButtonSubmit']");
                Global.MMBMode = "BAGGAGE";
                Global.IsRoundTrip = true;
                break;
            case "SEAT":
                wd.findElement(By.id("LinkButtonSeat")).click();
                WCGenericSync(wd, ".//*[@id='ControlGroupUnitMapView_UnitMapViewControl_LinkButtonAssignUnit']");
                Global.MMBMode = "SEAT";
                break;
        }
    }

    public void UpgradeBaggage(WebDriver wd, String DepRet, String Sequence, String Data, String ExpectedResults) {
        if (Global.MMBMode.toUpperCase().equals("BAGGAGE")) {
            _PassengerPage.select_Baggage(wd, DepRet, Sequence, Data, ExpectedResults);
        }
    }
}
