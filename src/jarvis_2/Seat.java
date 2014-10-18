package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Seat {

    /**
     * @param argsCompare_Text_Table
     */
    private static GenericFunctions _GenericFunctions;
    private static PassengerPage _PassengerPage;

    public Seat() {
        _GenericFunctions = new GenericFunctions();
        _PassengerPage = new PassengerPage();
    }

    public int CountNewSeatCtr(WebDriver wd) {
        String strPaxSeat = null;
        Boolean blnExist;
        int LegCount = 0;
        for (LegCount = 1; LegCount < 6; LegCount++) {
            strPaxSeat = ".//*[@id='seatMapMainBody']/div[38]/table[" + LegCount + "]";
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strPaxSeat);
            if (!blnExist) {
                break;
            }
        }
        return LegCount;
    }

    public int CountSeatCtr(WebDriver wd) {
        String strPaxSeat = null;
        Boolean blnExist;
        int LegCount = 0;
        for (LegCount = 0; LegCount < 6; LegCount++) {
            strPaxSeat = "ControlGroupUnitMapView_UnitMapViewControl_EquipmentConfiguration_" + LegCount + "_PassengerNumber_0";
            blnExist = _GenericFunctions.IsElementExist(wd, strPaxSeat);
            if (!blnExist) {
                break;
            }
        }
        return LegCount;
    }

    public void select_Seat_Confirmed(WebDriver wd) throws Exception {
        Boolean isExist;
        String[] arrSeatLetter;
        String strSeatLetter = "A==B==C==D==E==F";
        String strSeatLocation, style, strContent, strPaxSeat;
        arrSeatLetter = strSeatLetter.split("==");
        int intIterationSelection = 1;
        int intAdultCount = Integer.parseInt(Global.AdultCount);
        int intChildCount = Integer.parseInt(Global.ChildCount);
        int intTotalCount = intAdultCount + intChildCount;

        /*if (Global.IsRoundTrip) {
         intIterationSelection = 2;
         }*/

        //Count Flight Legs
        intIterationSelection = CountSeatCtr(wd);

        for (int Trip = 0; Trip < intIterationSelection; Trip++) {
            int TripCounter = Trip + 1;
            System.out.println("Selecting seat for Segment" + TripCounter);
            for (int intTotalPax = 0; intTotalPax < intTotalCount; intTotalPax++) {
                strPaxSeat = "ControlGroupUnitMapView_UnitMapViewControl_EquipmentConfiguration_" + Trip + "_PassengerNumber_" + intTotalPax;
                wd.findElement(By.id(strPaxSeat)).click();
                SeatMapSync(wd);

                //logs
                int PaxCounter = intTotalPax + 1;
                System.out.println("Looking for Open seat for Pax" + PaxCounter);

                //Total Row
                for (int intSeat = 14; intSeat < 31; intSeat++) {
                    //Total Columns
                    for (int i = 0; i < arrSeatLetter.length; i++) {
                        strSeatLocation = Trip + "_Y_1_" + intSeat + arrSeatLetter[i];
                        //Check if Seat Map Exist
                        isExist = SeatMapSync(wd);
                        if (isExist) {
                            //_GenericFunctions.wait("1000");
                            style = wd.findElement(By.id(strSeatLocation)).getAttribute("style");
                            //Check if seat is open
                            if (style.contains("Open")) {

                                System.out.println("Selecting seat for Pax" + PaxCounter);
                                wd.findElement(By.id(strSeatLocation)).click();

                                //Store Amount
                                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='confirmScroll']", "./table[1]/tbody/tr[2]/td[2]");
                                _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");

                                //Click Confirm
                                wd.findElement(By.id("ssrConfirmButton")).click();
                                i = 6;
                                intSeat = 31;
                            }
                        }
                    }
                }
            }
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_SeatSelectionPage");
        }

        proceed_With_Seat_Selection(wd);
    }

    public void select_NewSeatMap(WebDriver wd, String SeatType) throws Exception {
        Boolean isExist;

        String[] arrSeatLetter;
        String strSeatLetter = "A==B==C==D==E==F";
        String strSeatLocation, style, strContent, strPaxSeat, strTripp;
        arrSeatLetter = strSeatLetter.split("==");
        int intTrip, TripTemp, TripCounter;
        int intIterationSelection = 1;
        int intSeatStart = 14;
        int intAdultCount = Integer.parseInt(Global.AdultCount);
        int intChildCount = Integer.parseInt(Global.ChildCount);
        int intTotalCount = intAdultCount + intChildCount;

        switch (SeatType.toUpperCase()) {
            case "UPFRONT":
                intSeatStart = 2;
                break;
            case "STANDARD":
                intSeatStart = 14;
                break;
        }

        //Count Flight Legs
        intIterationSelection = CountNewSeatCtr(wd);

        for (int Trip = 0; Trip < intIterationSelection - 1; Trip++) {
            TripTemp = Trip;
            TripCounter = TripTemp + 1;
            System.out.println("Selecting seat for Segment" + TripCounter);

            for (int intTotalPax = 1; intTotalPax < intTotalCount + 1; intTotalPax++) {
                strPaxSeat = ".//*[@id='seatMapMainBody']/div[38]/table[" + TripCounter + "]/tbody/tr["
                        + intTotalPax + "]/th/label";
                wd.findElement(By.xpath(strPaxSeat)).click();
                //SeatMapSync(wd);
                if (intTotalPax == 1) {
                    _GenericFunctions.wait("10000");
                }
                UnitMapSync(wd);

                //logs
                int PaxCounter = intTotalPax ;
                System.out.println("Looking for Open seat for Pax" + PaxCounter);
                _GenericFunctions.wait("1500");

                //Total Row
                for (int intSeat = intSeatStart; intSeat < 31; intSeat++) {
                    //Total Columns
                    for (int i = 0; i < arrSeatLetter.length; i++) {
                        strSeatLocation = Trip + "_Y_1_" + intSeat + arrSeatLetter[i];
                        //Check if Seat Map Exist
                        isExist = SeatMapSync(wd);
                        if (isExist) {
                            //_GenericFunctions.wait("1000");
                            style = wd.findElement(By.id(strSeatLocation)).getAttribute("style");
                            //Check if seat is open
                            if (style.contains("Open")) {

                                System.out.println("Selecting seat for Pax" + PaxCounter);
                                wd.findElement(By.id(strSeatLocation)).click();

                                //Store Amount
                                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='confirmScroll']", "./table[1]/tbody/tr[2]/td[2]");
                                _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");

                                //Click Confirm
                                wd.findElement(By.id("ssrConfirmButton")).click();
                                i = 6;
                                intSeat = 31;
                            }
                        }
                    }
                }
            }
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_SeatSelectionPage");
        }
    }

    public void select_Seat_UnConfirmed(WebDriver wd) throws Exception {
        Boolean isExist;
        String[] arrSeatLetter;
        String strSeatLetter = "A==B==C==D==E==F";
        String strSeatLocation, style, strContent, strPaxSeat;
        arrSeatLetter = strSeatLetter.split("==");
        int intIterationSelection = 1;
        int intAdultCount = Integer.parseInt(Global.AdultCount);
        int intChildCount = Integer.parseInt(Global.ChildCount);
        int intTotalCount = intAdultCount + intChildCount;

        /*if (Global.IsRoundTrip) {
         intIterationSelection = 2;
         }*/

        //Count Flight Legs
        intIterationSelection = CountSeatCtr(wd);

        for (int Trip = 0; Trip < intIterationSelection; Trip++) {
            int TripCounter = Trip + 1;
            System.out.println("Selecting seat for Segment" + TripCounter);
            for (int intTotalPax = 0; intTotalPax < intTotalCount; intTotalPax++) {
                strPaxSeat = "ControlGroupUnitMapView_UnitMapViewControl_EquipmentConfiguration_" + Trip + "_PassengerNumber_" + intTotalPax;
                wd.findElement(By.id(strPaxSeat)).click();

                SeatMapSync(wd);

                //logs
                int PaxCounter = intTotalPax + 1;
                System.out.println("Looking for Open seat for Pax" + PaxCounter);

                //Total Row
                for (int intSeat = 14; intSeat < 31; intSeat++) {
                    //Total Columns
                    for (int i = 0; i < arrSeatLetter.length; i++) {
                        strSeatLocation = Trip + "_Y_1_" + intSeat + arrSeatLetter[i];
                        //Check if Seat Map Exist
                        isExist = SeatMapSync(wd);
                        if (isExist) {
                            //_GenericFunctions.wait("1000");
                            style = wd.findElement(By.id(strSeatLocation)).getAttribute("style");
                            //Check if seat is open
                            if (style.contains("Open")) {

                                System.out.println("Selecting seat for Pax" + PaxCounter);
                                wd.findElement(By.id(strSeatLocation)).click();

                                //Store Amount
                                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='confirmScroll']", "./table[1]/tbody/tr[2]/td[2]");
                                _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");

                                //Click Confirm
                                wd.findElement(By.id("ssrConfirmButton")).click();
                                i = 6;
                                intSeat = 31;
                            }
                        }
                    }
                }
            }
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_SeatSelectionPage");
        }
        proceed_WithOut_Seat_Selection(wd);
    }

    public Boolean UnitMapSync(WebDriver wd) {
        Boolean blnExist;
        WebDriverWait wait = new WebDriverWait(wd, 100);
        //Wait Until Switch My Flight Window Exists
        try {
            //System.out.println("waiting for Seat Map to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("unitMapView")));
            //System.out.println("Seat Map appeared.");
            blnExist = true;
        } catch (Exception e) {
            System.out.println("System can't find Seat Map.");
            blnExist = false;
        }
        return blnExist;
    }

    public Boolean SeatMapSync(WebDriver wd) {
        Boolean blnExist;
        WebDriverWait wait = new WebDriverWait(wd, 100);
        //Wait Until Switch My Flight Window Exists
        try {
            //System.out.println("waiting for Seat Map to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nose")));
            //System.out.println("Seat Map appeared.");
            blnExist = true;
        } catch (Exception e) {
            System.out.println("System can't find Seat Map.");
            blnExist = false;
        }
        return blnExist;
    }

    public void proceed_With_Seat_Selection(WebDriver wd) throws Exception {
        wd.findElement(By.id("ControlGroupUnitMapView_UnitMapViewControl_LinkButtonAssignUnit")).click();
        //Wait Until Payment Page Exists
        PaymentPageSync(wd);
    }

    public void auto_AcceptContinue(WebDriver wd) throws Exception {
        Boolean blnExist = false;
        String strElement, strText;
        int intCtr;
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_AutoSeatAssignment AcceptContinue");
        }

        for (intCtr = 2; intCtr < 10; intCtr++) {
            strElement = ".//*[@id='SkySales']/div[2]/div[" + intCtr + "]/a";
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strElement);
            if (blnExist) {
                strText = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='SkySales']",
                        "." + "/div[2]/div[" + intCtr + "]/a");
                if (strText.trim().toUpperCase().contains("ACCEPT")) {
                    wd.findElement(By.xpath(strElement)).click();
                    break;
                }
            }
        }

        if (!blnExist) {
            auto_Continue(wd);
        }

        //Click Continue Without Booking Seats
        ContinueWithoutBookingSeats(wd);

        //Wait Until Payment Page Exists
        PaymentPageSync(wd);
    }

    public void auto_SkipWithoutSelectingSeats(WebDriver wd) throws Exception {

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_AutoSeatAssignment Skip");
        }

        //Click Link Skip Without Selecting Seats
        wd.findElement(By.xpath(".//*[@id='ControlGroupNewUnitMapView_NewUnitMapViewControl_LinkButtonCancelAllPaxSeats']")).click();

        //Wait Until Payment Page Exists
        PaymentPageSync(wd);
    }

    public void auto_Continue(WebDriver wd) throws Exception {
        Boolean blnExist;
        String strElement, strText;
        int intCtr;
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_AutoSeatAssignment Continue");
        }
        //Click Continue Button
        for (intCtr = 6; intCtr < 20; intCtr++) {
            strElement = ".//*[@id='SkySales']/div[2]/div[" + intCtr + "]/a";
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strElement);
            if (blnExist) {
                //strText = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='SkySales']",
                //"." + "/div[2]/div[" + intCtr + "]/a");
                strText = wd.findElement(By.xpath(strElement)).getText();
                if (!strText.trim().toUpperCase().contains("ACCEPT")) {
                    if (strText.trim().toUpperCase().contains("CONTINUE")
                            || strText.trim().toUpperCase().contains("LANJUTKAN")
                            || strText.trim().toUpperCase().contains("ดำเนินการต่อไป")
                            || strText.trim().toUpperCase().contains("继续")
                            || strText.trim().toUpperCase().contains("繼續")) {
                        wd.findElement(By.xpath(strElement)).click();
                        break;
                    }
                }
            }
        }

        //Click Continue Without Booking Seats
        ContinueWithoutBookingSeats(wd);

        //Wait Until Payment Page Exists
        PaymentPageSync(wd);
    }

    public void ContinueWithoutBookingSeats(WebDriver wd) throws Exception {
        Boolean isExist;

        for (int i = 0; i < 5; i++) {
            isExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ControlGroupNewUnitMapView_NewUnitMapViewControl_LinkButtonAssignUnit']");
            if (isExist) {

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDZ_Seats not Reserved Popup");
                }

                i = 6;
                wd.findElement(By.xpath(".//*[@id='ControlGroupNewUnitMapView_NewUnitMapViewControl_LinkButtonAssignUnit']")).click();
            } else {
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void proceed_WithOut_Seat_Selection(WebDriver wd) throws Exception {

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "DDD_SeatSelectionPage");
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupUnitMapView_UnitMapViewControl_LinkButtonCancelAllPaxSeats']")).click();

        //Wait Until Payment Page Exists
        PaymentPageSync(wd);
    }

    public void PaymentPageSync(WebDriver wd) throws Exception {
        //Click WebCheckin Option
        _PassengerPage.ClickOkWebCheckin(wd);

        WebDriverWait wait = new WebDriverWait(wd, 120);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Payment Page to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(
                    "CONTROLGROUPPAYMENTBOTTOM_LinkButtonSubmit")));
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CONTROLGROUPPAYMENTBOTTOM"
            //        + "_ContactInputView_CheckBoxAgreement")));
            System.out.println("Payment Page appeared.");
            Global.ContinueExecution = true;
        } catch (Exception e) {
            System.out.println("System can't find Payment Page.");
            Global.ContinueExecution = false;
        }
    }
}
