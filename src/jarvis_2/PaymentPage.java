package jarvis_2;
// <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
// </editor-fold>

public class PaymentPage {

    private static GenericFunctions _GenericFunctions;
    private static ConfirmationPage _ConfirmationPage;
    private static Seat _Seat;

    public PaymentPage() {
        _GenericFunctions = new GenericFunctions();
        _ConfirmationPage = new ConfirmationPage();
        _Seat = new Seat();
    }

    public void compute_Total() throws IOException, ParseException {
        String strContent;
        String[] arrContent;
        double AmountTotal = 0;
        double intParAmount;
        strContent = _GenericFunctions.readFile(Global.ComputationFileName);
        arrContent = strContent.split("=");

        for (int i = 0; i < arrContent.length; i++) {
            intParAmount = Double.parseDouble(arrContent[i]);
            AmountTotal = AmountTotal + intParAmount;
        }
        Global.AmountTotal = AmountTotal;
        System.out.println("TOTAL PNR AMOUNT DUE IS: " + AmountTotal);
    }

    public void CompareTotalAmount(WebDriver wd, String strPage) {
        String Flag = "FAILED";
        String strContent;
        String strTableName = "";
        String strValueLocation = "";
        Double intActualAmount;
        switch (strPage.toUpperCase()) {
            case "PAYMENT PAGE":
                strTableName = ".//*[@id='paymentInputContent']";
                strValueLocation = "./div[4]/p[2]/span[2]";
                break;
        }
        strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strTableName, strValueLocation);
        intActualAmount = Double.parseDouble(strContent.replaceAll("[^\\d.]", ""));

        if (intActualAmount.equals(Global.AmountTotal)) {
            Flag = "PASSED";
        }
        _GenericFunctions.WriteResults(Global.ResultsFileName, "compute_Total", Global.TestCase_ID, Global.AmountTotal.toString(), intActualAmount.toString(), Flag);
    }

    public void PaymentSync(WebDriver wd, String LocString) {
        WebDriverWait wait = new WebDriverWait(wd, 1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocString)));
        } catch (Exception e) {
        }
    }

    public void check_CarTrawler_PaySameCC(WebDriver wd) {
        Boolean blnSiteB = false;
        Boolean blnChecked = false;
        Boolean blnExist = false;
        String strView = "";
        strView = "autoFill1";
        blnExist = _GenericFunctions.IsElementExist(wd, strView);
        if (blnExist) {
            blnChecked = wd.findElement(By.id(strView)).isSelected();
            if (!blnChecked) {
                wd.findElement(By.id(strView)).click();
            }
        }
    }

    public void check_FareCondition(WebDriver wd) {
        Boolean blnSiteB = false;
        Boolean blnChecked = false;
        String strView = "";
        blnSiteB = _GenericFunctions.IsElementExistXpath(wd,
                ".//*[@id='CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxAgreement']");
        strView = "CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxAgreement";
        if (blnSiteB) {
            blnChecked = wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxAgreement")).isSelected();
            if (!blnChecked) {
                wd.findElement(By.id(strView)).click();
            }
        }
    }

    public void SubmitPayment(WebDriver wd) {
        //Check if Pay Same CC exist
        check_CarTrawler_PaySameCC(wd);
        //Click Submit Payment
        check_FareCondition(wd);
        wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPAYMENTBOTTOM_LinkButtonSubmit']")).click();
    }

    public void CreditCard_Payment(WebDriver wd, String IsThreeDS, String TabName,
            String CCType, String ExpectedResults) throws Exception {
        String strCardNumber = "";

        if (TabName.equals("NULL")) {
            TabName = "Credit Card";
        }

        wd.findElement(By.id(TabName)).click();
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_DropDownListPaymentMethodCode");
        switch (CCType.toUpperCase()) {
            case "VISA":
                switch (IsThreeDS.toUpperCase()) {
                    case "NO":
                        strCardNumber = Global.VISANUMBER;
                        break;
                    case "NEW_TEST":
                    case "TEST":
                        strCardNumber = Global.VISANUMBER_ThreeDS;
                        break;
                    case "PRODUCTION":
                        strCardNumber = Global.VISANUMBER;
                        break;
                    case "CAR_VISA":
                        strCardNumber = "4263971921001307";
                        break;
                }
                break;
            case "MASTERCARD":
                switch (IsThreeDS.toUpperCase()) {
                    case "NO":
                        strCardNumber = Global.MASTERCARDNUMBER;
                        break;
                    case "NEW_TEST":
                    case "TEST":
                        strCardNumber = Global.MASTERCARDNUMBER_ThreeDS;
                        break;
                    case "PRODUCTION":
                        strCardNumber = Global.MASTERCARDNUMBER_Prod;
                        break;
                    case "NIKUNJ":
                        strCardNumber = "5178057713900087";
                        break;
                    case "CAR_MC":
                        strCardNumber = "5425232820001308";
                        break;
                }
                break;
        }


        if (!CCType.toUpperCase().equals("PROMO")) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_DropDownListPaymentMethodCode")).sendKeys(CCType);
            PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxACCTNO");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxACCTNO")).clear();
            _GenericFunctions.wait("5000");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxACCTNO")).sendKeys(strCardNumber);
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxACCTNO")).sendKeys(Keys.TAB);
        }

        if (!IsThreeDS.toUpperCase().equals("PRODUCTION")) {
            if (!IsThreeDS.toUpperCase().equals("NIKUNJ")) {
                wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                        + "_TextBoxCC__AccountHolderName")).sendKeys("Kass Miranda");
                wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                        + "_DropDownListEXPDAT_Month")).sendKeys("04");
                wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                        + "_DropDownListEXPDAT_Year")).sendKeys("2015");
                wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                        + "_TextBoxCC__VerificationCode")).sendKeys("123");
            }

        }

        //Production Credit Card
        if (IsThreeDS.toUpperCase().equals("NIKUNJ")) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxCC__AccountHolderName")).sendKeys("Nikunj Shanti");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_DropDownListEXPDAT_Month")).sendKeys("05");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_DropDownListEXPDAT_Year")).sendKeys("2015");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxCC__VerificationCode")).sendKeys("954");
        }

        if (IsThreeDS.toUpperCase().equals("PRODUCTION")) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxCC__AccountHolderName")).sendKeys("Han Twee Leng");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_DropDownListEXPDAT_Month")).sendKeys("05");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_DropDownListEXPDAT_Year")).sendKeys("2018");
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                    + "_TextBoxCC__VerificationCode")).sendKeys("903");
        }

        //Check Use Contact Address
        wd.findElement(By.xpath(".//*[@id='Payment_UserContactAddress']")).click();

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "XXX_PaymentPage");
        }

        SubmitPayment(wd);

        //Click 3DS OK Button
        try {
            wd.switchTo().alert().accept();
        } catch (Exception e) {
        }

        //3DS for Test Environment
        switch (IsThreeDS.toUpperCase()) {
            case "TEST":
            case "CAR_MC":
            case "CAR_VISA":
                TestThreeDSPageSync(wd, ExpectedResults);
                //TestThreeDSPageSync2(wd, ExpectedResults);
                //Click 3DS OK Button
                try {
                    wd.switchTo().alert().accept();
                } catch (Exception e) {
                }
                break;
            case "NEW_TEST":
                TestNewThreeDSPageSync(wd, ExpectedResults);
                break;
            case "PRODUCTION":
                ProdThreeDSPageSync(wd, ExpectedResults);
                _Seat.PaymentPageSync(wd);
                break;
        }

        //Wait until Confirmation Page Exist
        if (!IsThreeDS.toUpperCase().equals("PRODUCTION")) {
            ConfirmationPageSync(wd);
        }
    }

    public void ProdThreeDSPageSync(WebDriver wd, String ExpectedResults) throws Exception {
        System.out.println("waiting for ThreeDS Page to appear");
        Boolean blnExist;
        String ActualResult = "";
        String Flag = "PASSED";
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd,
                    ".//*[@id='tbl-box']/table/tbody/tr/td/table/tbody/"
                    + "tr[6]/td[2]/table/tbody/tr[1]/td/input");
            if (blnExist) {
                System.out.println("ThreeDS Page appeared");

//                //Input Invalid OTP
//                wd.findElement(By.xpath(".//*[@id='tbl-box']/table/tbody/tr/td/"
//                        + "table/tbody/tr[5]/td[2]/input")).sendKeys("000000");
//
//                //Click Proceed
//                wd.findElement(By.xpath(".//*[@id='tbl-box']/table/tbody/tr/td/"
//                        + "table/tbody/tr[6]/td[2]/table/tbody/tr[1]/td/input")).click();
//
//                _GenericFunctions.wait("3000");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "YYY_ThreeDS Page");
                }

                //Click Cancel
                wd.findElement(By.xpath(".//*[@id='tbl-box']/table/tbody/tr/td/"
                        + "table/tbody/tr[6]/td[2]/table/tbody/tr[3]/td/input")).click();

                _GenericFunctions.wait("2000");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZ_Payment Page with Error");
                }

                ActualResult = "TRUE";
                break;
            } else {
                System.out.println("ThreeDS Page doesn't appear");
                _GenericFunctions.wait("10000");
                ActualResult = "FALSE";
            }
        }

        _GenericFunctions.JarvisVerificationPoint("3DS Page", "3DS Check", ExpectedResults,
                ActualResult, " Missing 3DS Page");
    }

    public void TestThreeDSPageSync2(WebDriver wd, String ExpectedResults) throws Exception {
        System.out.println("waiting for ThreeDS Page to appear");
        Boolean blnExist;
        String ActualResult = "";
        String Flag = "PASSED";
        for (int i = 0; i < 21; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='PasswordTextBox']");
            if (blnExist) {
                System.out.println("ThreeDS Page appeared");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "YYY_ThreeDS Page");
                }
                _GenericFunctions.wait("2000");
                wd.findElement(By.xpath(".//*[@id='PasswordTextBox']")).sendKeys("0001");
                wd.findElement(By.xpath(".//*[@id='SubmitButton']")).click();

                for (int ii = 0; ii < 21; ii++) {
                    blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='ContinueButton']");
                    if (blnExist) {
                        wd.findElement(By.xpath(".//*[@id='ContinueButton']")).click();
                        try {
                            wd.switchTo().alert().accept();
                        } catch (Exception e) {
                        }
                        ActualResult = "TRUE";
                        break;
                    }
                }
            } else {
                System.out.println("ThreeDS Page doesn't appear");
                _GenericFunctions.wait("10000");
                ActualResult = "FALSE";
            }

            _GenericFunctions.JarvisVerificationPoint(
                    "3DS Page", "3DS Check", ExpectedResults,
                    ActualResult, " Missing 3DS Page");
        }
    }

    public void TestThreeDSPageSync(WebDriver wd, String ExpectedResults) throws Exception {
        System.out.println("waiting for ThreeDS Page to appear");
        Boolean blnExist;
        String ActualResult = "";
        String Flag = "PASSED";
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='TestCasesListBox']");
            if (blnExist) {
                System.out.println("ThreeDS Page appeared");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "YYY_ThreeDS Page");
                }
                wd.findElement(By.xpath(".//*[@id='TestCasesListBox']/option[1]")).click();
                wd.findElement(By.xpath(".//*[@id='SubmitButton']")).click();
                ActualResult = "TRUE";
                break;
            } else {
                System.out.println("ThreeDS Page doesn't appear");
                _GenericFunctions.wait("10000");
                ActualResult = "FALSE";
            }
        }

        _GenericFunctions.JarvisVerificationPoint("3DS Page", "3DS Check", ExpectedResults,
                ActualResult, " Missing 3DS Page");
    }

    public void TestNewThreeDSPageSync(WebDriver wd, String ExpectedResults) throws Exception {
        System.out.println("waiting for ThreeDS Page to appear");
        Boolean blnExist;
        String ActualResult = "";
        String Flag = "PASSED";
        for (int i = 0; i < 11; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='PasswordTextBox']");
            if (blnExist) {
                System.out.println("ThreeDS Page appeared");


                wd.findElement(By.xpath(".//*[@id='PasswordTextBox']")).sendKeys("0001");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "YYY_ThreeDS_Page_1");
                }

                wd.findElement(By.xpath(".//*[@id='SubmitButton']")).click();
                PaymentSync(wd, "ContinueButton");

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "YYY_ThreeDS_Page_2");
                }

                wd.findElement(By.xpath(".//*[@id='ContinueButton']")).click();
                try {
                    wd.switchTo().alert().accept();
                } catch (Exception e) {
                }
                ActualResult = "TRUE";
                break;
            } else {
                System.out.println("ThreeDS Page doesn't appear");
                _GenericFunctions.wait("10000");
                ActualResult = "FALSE";
            }
        }

        _GenericFunctions.JarvisVerificationPoint("3DS Page", "3DS Check", ExpectedResults,
                ActualResult, " Missing 3DS Page");
    }

    public void ConfirmationPageSync(WebDriver wd) throws Exception {
        System.out.println("waiting for Confirmation Page to appear");
        Boolean blnExist;
        for (int i = 0; i < 15; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='printPage']");
            if (blnExist) {
                System.out.println("Confirmation Page appeared");
                Global.ContinueExecution = true;
                break;
            } else {
                System.out.println("Confirmation Page doesn't appear");
                _GenericFunctions.wait("10000");
                Global.ContinueExecution = false;
            }
        }
        //CloseRockLivePopup(wd);

        //Store PNR
        if (!Global.IsWebcheckin) {
            Global.Booking_PNR = GrabPNR(wd);
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZ_Confirmation Page");
        }
    }

    public String GrabPNR(WebDriver wd) {
        String strPNR;
        strPNR = _GenericFunctions.ExtractTextFromTable(wd, "", ".//*[@id='mainContent']", "./div[2]/div[3]/table/tbody/tr[3]/td[3]");
        return strPNR;
    }

    public void CloseRockLivePopup(WebDriver wd) {
        Boolean isExist;
        _GenericFunctions.wait("5000");
        for (int i = 0; i < 11; i++) {
            //Close Rock Live

            isExist = _GenericFunctions.IsElementExistXpath(wd, ".//*[@id='submitButton']");
            if (isExist) {
                wd.findElement(By.xpath(".//*[@id='__titleBar__']/a[4]")).click();
            } else {
                _GenericFunctions.wait("1000");
            }
        }
    }

    public void Payment_Bin_Testing(WebDriver wd, String CCType, String strBinNumber) throws Exception {
        String strCCNumber;
        Boolean blnExist = false;
        if (CCType.length() > 3) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM"
                    + "_PaymentInputViewPaymentView_DropDownListPaymentMethodCode")).sendKeys(CCType);
            _GenericFunctions.wait("10000");
        }
        //Generate CC Number
        strCCNumber = GenerateCCNumber(strBinNumber);

        if (strCCNumber.length() < 16) {
            strCCNumber = GenerateCCNumber(strBinNumber);
        }

        //Input CC Number
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxACCTNO")).clear();
        _GenericFunctions.wait("1000");
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxACCTNO")).sendKeys(strCCNumber);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxACCTNO")).sendKeys(Keys.TAB);
        _GenericFunctions.wait("2000");

        blnExist = _GenericFunctions.IsElementExist(wd, "divError_CONTROLGROUPPAYMENTBOTTOM"
                + "_PaymentInputViewPaymentView_TextBoxACCTNO_ccNumberInvalid");

        if (blnExist) {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "PaymentPageBinTest_FAILED_" + strBinNumber);
            }
        } else {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "PaymentPageBinTest_PASSED_" + strBinNumber);
            }
        }
    }

    public static boolean luhnTest(String number) {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for (int i = 0; i < reverse.length(); i++) {
            int digit = Character.digit(reverse.charAt(i), 10);
            if (i % 2 == 0) {//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            } else {//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if (digit >= 5) {
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

    public String GenerateCCNumber(String lngCCnumber) {
        String strCC = null;
        Boolean blnValid = false;
        int digitCtr, ccValidCtr;
        for (int i = 1; i < 200000; i++) {
            strCC = String.valueOf(lngCCnumber) + String.valueOf(1) + String.valueOf(i);
            //strCC = String.valueOf(lngCCnumber) + String.valueOf(i);
            blnValid = luhnTest(strCC);
            if (blnValid) {
                for (int e = 0; e < 10; e++) {
                    digitCtr = strCC.length();
                    if (digitCtr <= 15) {
                        strCC = strCC + "0";
                    } else {
                        e = 11;
                    }
                    i = 200001;
                }
                //digitCtr = strCC.length();
                //ccValidCtr = 16 - digitCtr;
                //for (int e = 0; e < ccValidCtr; e++) {
                //    strCC = strCC + "0";
                //    i = 11;
                //}
            }
        }
        return strCC;
    }

    public void AXS_Payment(WebDriver wd) throws Exception {
        wd.findElement(By.id("axsTabLogoImage")).click();
        PaymentSync(wd, "axsStationsImg");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }

        SubmitPayment(wd);

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void ClickPay_Payment(WebDriver wd) throws Exception {
        wd.findElement(By.id("ClickPay")).click();
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_LinkButtonProceedClickPay");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHHA_PaymentPage");
        }

        //Click Proceed
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM"
                + "_PaymentInputViewPaymentView_LinkButtonProceedClickPay")).click();
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxMandiriCardNumber");


        //Input Clickpay Card Details
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_TextBoxMandiriCardNumber")).sendKeys("4616999900000028");
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView"
                + "_TextBoxMandiriToken")).sendKeys("000000");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHHB_PaymentPage");
        }

        SubmitPayment(wd);

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void ATM_RI_Payment(WebDriver wd, String Type) throws Exception {
        wd.findElement(By.id("ATM")).click();
        PaymentSync(wd, "radioBCA");

        switch (Type.toUpperCase()) {
            case "BCA":
                wd.findElement(By.id("radioBCA")).click();
                break;
            case "MANDIRI":
                wd.findElement(By.id("radioMAN")).click();
                break;
            case "BNI":
                wd.findElement(By.id("radioBNI")).click();
                break;
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }

        wd.findElement(By.id("chkTerms")).click();

        SubmitPayment(wd);

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void CashPay_Payment(WebDriver wd, String Type) throws Exception {
        wd.findElement(By.id("CashPay")).click();
        PaymentSync(wd, "radioTripleStar");

        switch (Type.toUpperCase()) {
            case "TRIPLESTAR":
                wd.findElement(By.id("radioTripleStar")).click();
                break;
            case "CEBUANA":
                wd.findElement(By.id("radioCebuanaLhuillier")).click();
                break;
            case "GPRS":
                wd.findElement(By.id("radioGPRS")).click();
                break;
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }

        wd.findElement(By.id("chkTerms")).click();
        SubmitPayment(wd);

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void CashSense_Payment(WebDriver wd) throws Exception {
        wd.findElement(By.id("Cashsense")).click();
        PaymentSync(wd, "divCashsense");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }

        wd.findElement(By.id("chkTerms")).click();
        SubmitPayment(wd);

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void Agency_hold(WebDriver wd) throws Exception {
        wd.findElement(By.id("Agency Hold")).click();
        SubmitPayment(wd);
        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void Agency_Credit(WebDriver wd) throws Exception {
        wd.findElement(By.id("AgencyAccount")).click();
        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHHI_Agency Credit Payment");
        }
        SubmitPayment(wd);
        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void voucher_Payment(WebDriver wd, String Data) throws Exception {
        Boolean isExist;
        wd.findElement(By.id("Voucher")).click();
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxVoucherAccount_VO_ACCTNO");
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_TextBoxVoucherAccount_VO_ACCTNO")).sendKeys(Data);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_PaymentInputViewPaymentView_ButtonLookup")).click();

        for (int i = 0; i < 11; i++) {
            isExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPAYMENTBOTTOM_LinkButtonSubmit");
            if (isExist) {

                //Capture Screenshot
                if (Global.ActivateScreenshot) {
                    _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
                }
                SubmitPayment(wd);

                try {
                    wd.switchTo().alert().accept();
                } catch (Exception e) {
                }
                i = 11;
            } else {
                _GenericFunctions.wait("1000");
            }
        }

        //Wait until Confirmation Page Exist
        ConfirmationPageSync(wd);
    }

    public void isInfantFeeTaxable(String YesNo) {
        Global.InfantFeeTaxable = YesNo.toUpperCase();
    }

    public void store_SalesTax(WebDriver wd) throws IOException {
        Boolean blnExist;
        int DepRet = 1;
        String strTableName, strLabelLocation, strPriceLocation, strContent;
        if (Global.IsRoundTrip) {
            DepRet = 2;
        }

        for (int intDepRetStart = 1; intDepRetStart < DepRet + 1; intDepRetStart++) {
            strTableName = ".//*[@id='taxAndFeeContainerId_" + intDepRetStart + "_1']";
            //Click Taxes and Fees
            wd.findElement(By.xpath(".//*[@id='taxAndFeeShowHideId_" + intDepRetStart + "_1']/td[2]")).click();
            _GenericFunctions.wait("3000");
            for (int intRow = 1; intRow < 100; intRow++) {
                //Check if Table Exists
                strLabelLocation = "/div[" + intRow + "]";
                strPriceLocation = "/span[" + intRow + "]";
                blnExist = _GenericFunctions.IsElementExistXpath(wd, strTableName + strLabelLocation);
                if (blnExist) {
                    //Check if it's a sales tax
                    strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strTableName, "." + strLabelLocation);
                    if (strContent.contains("Sales Tax")) {
                        //Sales Tax for Infant Fee should not be included
                        if (Global.InfantFeeTaxable == "YES") {
                            //Store negative values
                            strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strTableName, "." + strPriceLocation);
                            _GenericFunctions.TextWriter(Global.ComputationFileName, "-" + strContent.replaceAll("[^\\d.]", "") + "=");
                        }

                    }
                } else {
                    intRow = 101;
                }
            }

        }

    }

    public void store_Taxes(WebDriver wd) throws IOException {
        String DepTableName, RetTableName, strContent;
        DepTableName = ".//*[@id='taxAndFeeShowHideId_1_1']";
        RetTableName = ".//*[@id='taxAndFeeShowHideId_2_1']";

        strContent = _GenericFunctions.ExtractTextFromTable(wd, "", DepTableName, "./td[2]");
        _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");

        if (Global.IsRoundTrip) {
            strContent = _GenericFunctions.ExtractTextFromTable(wd, "", RetTableName, "./td[2]");
            _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
        }
    }

    public void store_Other_Fees_Taxes(WebDriver wd) throws IOException {
        Boolean blnExist;
        String strTableName, strLabelLocation, strValueLocation, strContent;
        strTableName = ".//*[@id='priceDisplayBody']";

        //Spoilage, Upgrade Fee, Booking Fee are covered
        for (int i = 2; i < 100; i++) {
            strLabelLocation = "/table[3]/tbody/tr[" + i + "]/td[1]";
            strValueLocation = "/table[3]/tbody/tr[" + i + "]/td[2]";
            blnExist = _GenericFunctions.IsElementExistXpath(wd, strTableName + strLabelLocation);

            if (blnExist) {
                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strTableName, "." + strLabelLocation);
                if (!strContent.contains("Seat fees")) {
                    strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strTableName, "." + strValueLocation);
                    _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
                }
            } else {
                i = 101;
            }
        }
    }

    public void store_Fare(WebDriver wd) throws IOException {
        String strDepTableLocation, strDepTableName, strRetTableLocation, strRetTableName;
        String strContent;
        int intAdultCount = Integer.parseInt(Global.AdultCount);
        int intChildCount = Integer.parseInt(Global.ChildCount);
        int intInfantCount = Integer.parseInt(Global.InfantCount);
        int intTotalCount = intAdultCount + intChildCount + intInfantCount;
        for (int i = 1; i < intTotalCount + 1; i++) {
            strDepTableLocation = "./table[1]/tbody/tr[" + i + "]/td[3]";
            strDepTableName = ".//*[@id='priceDisplayBody']";
            strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strDepTableName, strDepTableLocation);
            _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
        }

        if (Global.IsRoundTrip) {
            for (int i = 1; i < intTotalCount + 1; i++) {
                strRetTableLocation = "./table[2]/tbody/tr[" + i + "]/td[3]";
                strRetTableName = ".//*[@id='priceDisplayBody']";
                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", strRetTableName, strRetTableLocation);
                _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
            }
        }
    }

    public void store_TravelInsurance(WebDriver wd) throws IOException {
        String strContent, tableName, ValueLocation;
        Boolean blnChecked, blnExist;
        tableName = ".//*[@id='passengerMainBody']";

        //Check if it's an ACE insurance or AIG
        blnExist = _GenericFunctions.IsElementExist(wd, "aceInsuranceLogoTop");
        if (blnExist) {
            ValueLocation = "./div[3]/fieldset/div[1]/p[2]/span[1]/price";
        } else {
            ValueLocation = "./div[3]/fieldset/div[1]/span[1]/p/span[1]/price";
        }

        blnExist = _GenericFunctions.IsElementExist(wd, "insuranceOfferYes");
        if (blnExist) {
            blnChecked = wd.findElement(By.id("insuranceOfferYes")).isSelected();
            if (blnChecked) {
                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", tableName, ValueLocation);
                _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
            }
        }
    }

    public void store_BoardMeFirst(WebDriver wd) throws IOException {
        String tableName, ValueLocation, strContent, seq, DepLocString, RetLocString;
        Boolean blnExist, blnChecked;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = 0; i < intTotalCount; i++) {
            seq = Integer.toString(i).replace(".0", "");
            DepLocString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView_BOARDMEFIRSTCHECKBOX_" + seq + "_0_0";
            RetLocString = "CONTROLGROUPPASSENGER_SSRBoardMeFirstOfferViewPassengerView_BOARDMEFIRSTCHECKBOX_" + seq + "_1_0";
            tableName = ".//*[@id='bmfTable']";
            ValueLocation = "./tbody/tr[1]/td[3]";

            //Check if Departure BMF Exist and Checked
            blnExist = _GenericFunctions.IsElementExist(wd, DepLocString);

            if (blnExist) {
                //Get BMF Price
                strContent = _GenericFunctions.ExtractTextFromTable(wd, "", tableName, ValueLocation);

                blnChecked = wd.findElement(By.id(DepLocString)).isSelected();
                if (blnChecked) {
                    _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
                }

                //Check if Return BMF Exist and Checked
                blnExist = _GenericFunctions.IsElementExist(wd, RetLocString);

                if (blnExist) {
                    blnChecked = wd.findElement(By.id(RetLocString)).isSelected();
                    if (blnChecked) {
                        _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
                    }
                }
            }
        }
    }

    public void store_Meals(WebDriver wd) throws IOException {
        String DepLocString, RetLocString, strContent, seq;
        Boolean blnExist = false;
        String[] arrContentMeal;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = 0; i < intTotalCount; i++) {
            seq = Integer.toString(i).replace(".0", "");
            DepLocString = ".//*[@id='CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST_" + seq + "_0_0']";
            RetLocString = ".//*[@id='CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST_" + seq + "_1_0']";

            //Check if Dep Meal Exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST_0_0_0");
            if (blnExist) {
                strContent = _GenericFunctions.getSelectedValue(wd, DepLocString, "XPATH");
                //Check if No Meal is selected - DEPARTURE
                if (strContent.toUpperCase().trim().contains("COMBO") == false) {
                    arrContentMeal = strContent.split("@");
                    _GenericFunctions.TextWriter(Global.ComputationFileName, arrContentMeal[1].replaceAll("[^\\d.]", "") + "=");
                    //Check if Return selection exists
                    blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER_MealLegInputViewPassengerView_MEALSINPUTDROPDOWNLIST_0_1_0");
                    if (blnExist) {
                        strContent = _GenericFunctions.getSelectedValue(wd, RetLocString, "XPATH");
                        //Check if No Meal is selected - RETURN
                        if (strContent.toUpperCase().trim().contains("COMBO") == false) {
                            arrContentMeal = strContent.split("@");
                            _GenericFunctions.TextWriter(Global.ComputationFileName, arrContentMeal[1].replaceAll("[^\\d.]", "") + "=");
                        }
                    }
                }
            }
        }

    }

    public void store_TigerPlus(WebDriver wd) throws IOException {
        String seq, LocString, tableName, ValueLocation, strContent, LocID;
        Boolean blnChecked, blnExist;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = 0; i < intTotalCount; i++) {
            seq = Integer.toString(i).replace(".0", "");
            LocString = ".//*[@id='CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView_TIGERPLUSCHECKBOX_" + seq + "_0_0']";
            LocID = "CONTROLGROUPPASSENGER_SSRTigerPlusOfferViewPassengerView_TIGERPLUSCHECKBOX_" + seq + "_0_0";
            tableName = ".//*[@id='plusSSRFCont_" + seq + "']";
            ValueLocation = "./div/div/table/tbody/tr/td[3]";

            blnExist = _GenericFunctions.IsElementExist(wd, LocID);
            //Check if element Exist
            if (blnExist) {
                blnChecked = wd.findElement(By.xpath(LocString)).isSelected();
                //Check if element is Checked
                if (blnChecked) {
                    strContent = _GenericFunctions.ExtractTextFromTable(wd, "", tableName, ValueLocation);
                    _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
                }
            }

        }
    }

    public void store_Sports_Equipment(WebDriver wd) throws IOException {
        String DepLocString, RetLocString, tableName, ComboDepLocString, ComboRetLocString;
        String ValueLocation;
        String seq;
        Boolean blnExist = false;
        int ComboCount = 1;
        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = 0; i < intTotalCount; i++) {
            seq = Integer.toString(i).replace(".0", "");

            DepLocString = ".//*[@id='CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView_EQUIPMENTCHECKBOX_" + seq + "_0_0']";
            ComboDepLocString = ".//*[@id='CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView_EQUIPMENTCHECKBOX_" + seq + "_0_1']";
            RetLocString = ".//*[@id='CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView_EQUIPMENTCHECKBOX_" + seq + "_1_0']";
            ComboRetLocString = ".//*[@id='CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView_EQUIPMENTCHECKBOX_" + seq + "_1_1']";
            tableName = ".//*[@id='speqSSRFCont_" + seq + "']";
            ValueLocation = "./div/div/table/tbody/tr[" + ComboCount + "]/td[3]";

            //Departing Flight
            blnExist = _GenericFunctions.IsElementExistXpath(wd, DepLocString);
            if (blnExist) {
                StoreSportsEquipment(wd, tableName, ValueLocation, DepLocString);
                ComboCount = ComboCount + 1;
                ValueLocation = "./div/div/table/tbody/tr[" + ComboCount + "]/td[3]";
            }

            //Departing Combo
            blnExist = _GenericFunctions.IsElementExistXpath(wd, ComboDepLocString);
            if (blnExist) {
                StoreSportsEquipment(wd, tableName, ValueLocation, ComboDepLocString);
                ComboCount = ComboCount + 1;
                ValueLocation = "./div/div/table/tbody/tr[" + ComboCount + "]/td[3]";
            }

            //Check if Passport details exist
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER_SSRSportsEquipmentOfferViewPassengerView_EQUIPMENTCHECKBOX_0_1_0");
            if (blnExist) {
                //Returning Flight
                blnExist = _GenericFunctions.IsElementExistXpath(wd, RetLocString);
                if (blnExist) {
                    StoreSportsEquipment(wd, tableName, ValueLocation, RetLocString);
                    ComboCount = ComboCount + 1;
                    ValueLocation = "./div/div/table/tbody/tr[" + ComboCount + "]/td[3]";
                }

                //Returning Combo
                blnExist = _GenericFunctions.IsElementExistXpath(wd, ComboRetLocString);
                if (blnExist) {
                    StoreSportsEquipment(wd, tableName, ValueLocation, ComboRetLocString);
                    ComboCount = ComboCount + 1;
                }

            }

            //Reset Count
            ComboCount = 1;
        }
    }

    public void StoreSportsEquipment(WebDriver wd, String tableName, String ValueLocation, String locString) throws IOException {
        Boolean blnChecked;
        String strContent;
        //Combo Flight

        blnChecked = wd.findElement(By.xpath(locString)).isSelected();
        if (blnChecked) {
            strContent = _GenericFunctions.ExtractTextFromTable(wd, "", tableName, ValueLocation);
            _GenericFunctions.TextWriter(Global.ComputationFileName, strContent.replaceAll("[^\\d.]", "") + "=");
        }
    }

    public void store_Baggage_Amount(WebDriver wd) throws IOException {

        String locString;
        String strContent;
        String seq;
        Boolean blnExist = false;

        int intStartChild = Integer.parseInt(Global.AdultCount);
        int intTotalCount = intStartChild + Integer.parseInt(Global.ChildCount);
        for (int i = 0; i < intTotalCount; i++) {
            seq = Integer.toString(i).replace(".0", "");

            //Departing Flight
            locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_0_0']";
            strContent = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
            String[] arrContent = strContent.split("@");
            _GenericFunctions.TextWriter(Global.ComputationFileName, arrContent[1].replaceAll("[^\\d.]", "") + "=");

            //Combo Flight
            blnExist = _GenericFunctions.IsElementExist(wd, ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_0_1']");
            if (blnExist) {
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_0_1']";
                strContent = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                arrContent = strContent.split("@");
                _GenericFunctions.TextWriter(Global.ComputationFileName, arrContent[1].replaceAll("[^\\d.]", "") + "=");
            }


            //Return Flight
            blnExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_0_1_0");
            if (blnExist) {
                locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_1_0']";
                strContent = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                arrContent = strContent.split("@");
                _GenericFunctions.TextWriter(Global.ComputationFileName, arrContent[1].replaceAll("[^\\d.]", "") + "=");

                //Combo Flight
                blnExist = _GenericFunctions.IsElementExist(wd, ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_1_1']");
                if (blnExist) {
                    locString = ".//*[@id='CONTROLGROUPPASSENGER_SSRBaggageInputViewPassengerView_BAGSINPUTDROPDOWNLIST_" + seq + "_1_1']";
                    strContent = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
                    arrContent = strContent.split("@");
                    _GenericFunctions.TextWriter(Global.ComputationFileName, arrContent[1].replaceAll("[^\\d.]", "") + "=");
                }
            }
        }

    }

    public void EnterContactDetails(WebDriver wd) throws Exception {
        Boolean IsExist;
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListTitle");
        new Select(wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListTitle")))
                .selectByVisibleText(Global.ContactTitle);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxFirstName"))
                .sendKeys(Global.ContactFirstName);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxFirstName")).click();
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxLastName"))
                .sendKeys(Global.ContactLastName);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxLastName")).click();
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxAddressLine1"))
                .sendKeys(Global.AddressLine1);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxAddressLine2"))
                .sendKeys(Global.AddressLine2);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxAddressLine3"))
                .sendKeys(Global.AddressLine3);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxCity"))
                .sendKeys(Global.City);
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListCountry"))
                .sendKeys(Global.Country);
        if (wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListStateProvince"))
                .isEnabled()) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListStateProvince"))
                    .sendKeys(Global.StateProvince);
        }
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxPostalCode"))
                .sendKeys(Global.PostalCode);
        //Check what phone element is available. Changes made for SMS Itinerary
        IsExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPAYMENTBOTTOM_ContactInputView"
                + "_TextBoxHomePhoneNew");
        if (IsExist) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxHomePhoneNew"))
                    .sendKeys(Global.HomePhone);
        } else {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxHomePhone"))
                    .sendKeys(Global.HomePhone);
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxOtherPhone"))
                    .sendKeys(Global.OtherPhone);
        }
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxEmailAddress"))
                .sendKeys(Global.EmailAddress);

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }
    }

    public void EnterAgentDetails(WebDriver wd) throws Exception {
        Boolean IsExist;
        PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListTitle");

        //Check what phone element is available. Changes made for SMS Itinerary
        IsExist = _GenericFunctions.IsElementExist(wd, "CONTROLGROUPPAYMENTBOTTOM_ContactInputView"
                + "_TextBoxHomePhoneNew");
        if (IsExist) {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxHomePhoneNew"))
                    .sendKeys(Global.HomePhone);

        } else {
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxHomePhone"))
                    .sendKeys(Global.HomePhone);
            wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_TextBoxOtherPhone"))
                    .sendKeys(Global.OtherPhone);
        }

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "HHH_PaymentPage");
        }
    }

    public void EnterContactCountry(WebDriver wd, String Country) {
        wd.findElement(By.id("CONTROLGROUPPAYMENTBOTTOM_ContactInputView_DropDownListCountry")).sendKeys(Country);
    }

    public void ActivateSMSItinerary(WebDriver wd, String Activate, String ExpectedResults) {
        Boolean IsExist;
        String Flag = "PASSED";
        String ActualResult;

        IsExist = _GenericFunctions.IsElementExistXpath(wd,
                ".//*[@id='CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxSMSItinerary']");
        if (IsExist) {
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        //Check SMS Itinerary if Flag is YES
        if (Activate.toUpperCase().equals("YES")) {
            if (IsExist) {
                wd.findElement(By.xpath(".//*[@id='CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxSMSItinerary']")).click();
                //_GenericFunctions.wait("5000");
                PaymentSync(wd, "CONTROLGROUPPAYMENTBOTTOM_ContactInputView_CheckBoxSMSItinerary");
            }
        }
        _GenericFunctions.JarvisVerificationPoint("Payment Page", "SMS Itinerary Check", ExpectedResults,
                ActualResult, " Missing SMS Itinerary");
    }

    public void CheckPaymentTabs(WebDriver wd, String TabName, String ExpectedResults) {
        Boolean IsExist;
        String Flag = "PASSED";
        String ActualResult;
        IsExist = _GenericFunctions.IsElementExist(wd, TabName);

        if (IsExist) {
            ActualResult = "TRUE";
        } else {
            ActualResult = "FALSE";
        }

        _GenericFunctions.JarvisVerificationPoint("Payment Page", TabName + " Check", ExpectedResults,
                ActualResult, TabName + " Payment Tab Missing");
    }
}
