package jarvis_2;

import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PromoPage {

    private static GenericFunctions _GenericFunctions;
    private static SearchFlightPage _SearchFlightPage;
    private static PassengerPage _PassengerPage;

    public PromoPage() {
        _SearchFlightPage = new SearchFlightPage();
        _GenericFunctions = new GenericFunctions();
        _PassengerPage = new PassengerPage();
    }

    public void NTUCbenefitslogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupNTUCBenefitsLoginView_NTUCBenefitsLoginNTUCLoginView"
                + "_TextBoxUserId']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupNTUCBenefitsLoginView_NTUCBenefitsLoginNTUCLoginView"
                + "_LinkButtonNTUCSubmit']";

        //Promo URL
        URL = URL + "NTUCbenefitslogin.aspx";

        GenericPromoLogin(wd, URL, strClose, "S7827620G", strBINNumberTextBox, strSubmitButton);
    }

    public void NTUCPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupNTUCLoginView_NTUCLoginNTUCLoginView"
                + "_TextBoxUserId']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupNTUCLoginView_NTUCLoginNTUCLoginView"
                + "_LinkButtonNTUCSubmit']";

        //Promo URL
        URL = URL + "NTUCPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, "S7827620G", strBINNumberTextBox, strSubmitButton);
    }
     public void OCBCPromoLogin2(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView"
                + "_MultiLanguageCCPromoLoginCodeBehindView_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView"
                + "_MultiLanguageCCPromoLoginCodeBehindView_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "OCBCPromoLogin2.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void OCBCPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "OCBCPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void ICICIPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "ICICIPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void WEBPLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupProdTestLoginView"
                + "_ProdTestLoginControlView_TextBoxUserId']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupProdTestLoginView"
                + "_ProdTestLoginControlView_LinkButtonProdTestSubmit']";

        //Promo URL
        URL = URL + "ProdTestLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void KrungthaiPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "KrungthaiPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void AEONPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "AEONPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void MandiriPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView"
                + "_MultiLanguageCCPromoLoginCodeBehindView_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView"
                + "_MultiLanguageCCPromoLoginCodeBehindView_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "MandiriPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void MandiriBenefitsLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMandiriBenefitsLoginView"
                + "_MandiriBenefitsLoginCodeBehindView_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMandiriBenefitsLoginView"
                + "_MandiriBenefitsLoginCodeBehindView_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "MandiriBenefitsLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void CitibankIDLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "CitibankIDLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }
    
    
        public void MaybankPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "MaybankPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }
   

    public void CitibankSGPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "CitiSGPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void CitibankPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView4"
                + "_MultiLanguageCCPromoLoginCodeBehindView4_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView4"
                + "_MultiLanguageCCPromoLoginCodeBehindView4_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "CitibankPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void GenericPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID,
            String strBINNumberTextBox, String strSubmitButton) throws Exception {
        //Navigate to ANZ Promologin
        if (strClose.toUpperCase().equals("YES")) {
            wd = new FirefoxDriver();
        }
        wd.manage().window().maximize();
        wd.get(URL);

        //Wait Until Promo Page Exist
        PromoLoginPageSync(wd, strBINNumberTextBox);

        //Type IDEnterContactDetails
        wd.findElement(By.xpath(strBINNumberTextBox)).sendKeys(NTUC_ID);

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "A_LOGIN_" + NTUC_ID);
        }

        wd.findElement(By.xpath(strSubmitButton)).click();

        if (URL.toUpperCase().contains("NTUCPROMO")) {
            NTUCPromoAlertSync(wd);
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AC_MAINPAGE_" + NTUC_ID);

            }
            wd.findElement(By.xpath(".//*[@id='promoAdvisory']/div[2]/input")).click();
        }

        //Wait Until Search page Exist
        _SearchFlightPage.SearchPageSync(wd);

        if (!Global.ContinueExecution) {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "_FAILED_" + NTUC_ID);
            }
        } else {
            //Capture Screenshot
            if (Global.ActivateScreenshot) {
                _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AB_MAINPAGE_" + NTUC_ID);
            }
        }
        Global.ContinueExecution = true;

        if (strClose.toUpperCase().equals("YES")) {
            wd.close();
        }
    }

    public void ANZPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "ANZPromoLogin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void MetroBankPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView5"
                + "_MultiLanguageCCPromoLoginCodeBehindView5_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "metrobankpromologin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void HSBCPromoLogin(WebDriver wd, String URL, String strClose, String NTUC_ID) throws Exception {
        String strBINNumberTextBox, strSubmitButton;

        //Login TextBox
        strBINNumberTextBox = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']";

        //Promo Submit Button
        strSubmitButton = ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']";

        //Promo URL
        URL = URL + "hsbcpromologin.aspx";

        GenericPromoLogin(wd, URL, strClose, NTUC_ID, strBINNumberTextBox, strSubmitButton);
    }

    public void BPIPromoLogin(WebDriver wd, String NTUC_ID) {
        //Wait Until Promo Page Exist
        PromoLoginPageSync(wd, ".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']");

        wd.findElement(By.xpath(".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_TextBoxCardNumber']")).sendKeys(NTUC_ID);
        wd.findElement(By.xpath(".//*[@id='ControlGroupMultiLanguageCCPromoLoginView2"
                + "_MultiLanguageCCPromoLoginCodeBehindView2_LinkButtonCardSubmit']")).click();

        //Wait Until Search page Exist
        _SearchFlightPage.SearchPageSync(wd);

    }

    public void SCBLogin(WebDriver wd, String URL, String strClose, String LoginType) {
        String Username = null, Password = null, txtUname = null,
                txtPw = null, LoginButton = null, MainSync = null;

        if (strClose.toUpperCase().equals("YES")) {
            wd = new FirefoxDriver();
        }
        wd.manage().window().maximize();
        wd.get(URL + "scblogin.aspx");

        Username = "carlcruz@tigerairways.com";
        Password = "5252972a";
        txtUname = ".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView_TextBoxUserID']";
        txtPw = ".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView_PasswordFieldPassword']";
        LoginButton = ".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView_ButtonLogIn']";
        MainSync = ".//*[@id='ControlGroupMainSCBLandingView"
                + "_SCBLandingSCBLandingView_LinkButtonRedeemVouchers']";

        //Wait Until SCB Login Page Exist
        PromoLoginPageSync(wd, txtUname);

        wd.findElement(By.xpath(txtUname)).sendKeys(Username);
        wd.findElement(By.xpath(txtPw)).sendKeys(Password);
        wd.findElement(By.xpath(LoginButton)).click();

        //Wait Until Search page Exist
        PromoLoginPageSync(wd, MainSync);
    }
    
    
     public void StaffTravel(WebDriver wd, String URL, String strClose) {
        String Username = null, Password = null, txtUname = null,
                txtPw = null, LoginButton = null, MainSync = null;

        if (strClose.toUpperCase().equals("YES")) {
            wd = new FirefoxDriver();
        }
        wd.manage().window().maximize();
        wd.get(URL + "intranetsearch.aspx");

        Username = "1328";
        Password = "Tiger123";
        txtUname = ".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_TextBoxUserID']";
        txtPw = ".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_PasswordFieldPassword']";
        LoginButton = ".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_ButtonLogIn']";
        MainSync = ".//*[@id='ControlGroupSearchView"
                + "_AvailabilitySearchInputSearchView_DropDownListFareTypes']";

        //Wait Until SCB Login Page Exist
        PromoLoginPageSync(wd, txtUname);

        wd.findElement(By.xpath(txtUname)).sendKeys(Username);
        wd.findElement(By.xpath(txtPw)).sendKeys(Password);
        wd.findElement(By.xpath(LoginButton)).click();

        //Wait Until Search page Exist
        PromoLoginPageSync(wd, MainSync);
    }

    public void MelbourneStormLogin(WebDriver wd, String URL, String strClose, String LoginType) {
        String Username = null, Password = null, txtUname = null,
                txtPw = null, LoginButton = null, MainSync = null;

        if (strClose.toUpperCase().equals("YES")) {
            wd = new FirefoxDriver();
        }
        wd.manage().window().maximize();
        wd.get(URL + "MelbourneStormLogin.aspx");

        Username = "MelbourneStorm";
        Password = "MelbourneTiger1";
        txtUname = ".//*[@id='ControlGroupMelStormLoginView_MelStormLoginCodeBehindView_TextBoxCardNumber']";
        txtPw = ".//*[@id='ControlGroupMelStormLoginView_MelStormLoginCodeBehindView_TextBoxPromoCode']";
        LoginButton = ".//*[@id='ControlGroupMelStormLoginView_MelStormLoginCodeBehindView_LinkButtonCardSubmit']";
        MainSync = ".//*[@id='ControlGroupSearchView_AvailabilitySearchInputSearchView_RoundTrip']";

        //Wait Until SCB Login Page Exist
        PromoLoginPageSync(wd, txtUname);

        wd.findElement(By.xpath(txtUname)).sendKeys(Username);
        wd.findElement(By.xpath(txtPw)).sendKeys(Password);
        wd.findElement(By.xpath(LoginButton)).click();

        //Wait Until Search page Exist
        PromoLoginPageSync(wd, MainSync);
    }

    public void SCBPromoLogin(WebDriver wd) throws Exception {
        //Wait Until Promo Page Exist
        PromoLoginPageSync(wd, ".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView_TextBoxUserID']");

        wd.findElement(By.xpath(".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView"
                + "_TextBoxUserID']")).sendKeys("carlcruz@  tigerairways.com");
        wd.findElement(By.xpath(".//*[@id='ControlGroupSCBLoginView_SCBLoginSCBLoginView"
                + "_PasswordFieldPassword']")).sendKeys("5252972a");

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_PromoLoginPage");
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupSCBLoginView"
                + "_SCBLoginSCBLoginView_ButtonLogIn']")).click();

        //Wait Until Promo Main Page Exist
        PromoLoginPageSync(wd, ".//*[@id='ControlGroupMainSCBLandingView"
                + "_SCBLandingSCBLandingView_LinkButtonRedeemVouchers']");
    }

    public void SCBOptionPage(WebDriver wd, String Selection) {
        switch (Selection.toUpperCase()) {
            case "FREE FLIGHT":
                wd.findElement(By.xpath(".//*[@id='ControlGroupMainSCBLandingView"
                        + "_SCBLandingSCBLandingView_LinkButtonRedeemVouchers']")).click();
                break;
            case "REGULAR FLIGHT":
                wd.findElement(By.xpath(".//*[@id='ControlGroupMainSCBLandingView"
                        + "_SCBLandingSCBLandingView_LinkButtonBookMe']")).click();
                break;
        }

        //Wait Until Search page Exist
        _SearchFlightPage.SearchPageSync(wd);
    }

    public void PromoLoginPageSync(WebDriver wd, String LocString) {
        WebDriverWait wait = new WebDriverWait(wd, 100);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Promo Page to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocString)));
            System.out.println("Promo Page appeared.");
        } catch (Exception e) {
            System.out.println("System can't find Promo Page.");
        }
    }

    public void NTUCPromoAlertSync(WebDriver wd) {
        WebDriverWait wait = new WebDriverWait(wd, 15);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Promo Alert to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    ".//*[@id='promoAdvisory']/div[2]/input")));
            System.out.println("Search Promo Alert appeared.");
        } catch (Exception e) {
            System.out.println("System can't find Promo Alert.");
        }
    }

    public void ClickOkPromoAdvisory(WebDriver wd) throws Exception {
        Boolean blnExist;
        String btnWebCheckin = ".//*[@id='promoAdvisory']/div[2]/input";

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_Promo Alert");
        }

        //Click Ok Flight Advisory
        for (int i = 0; i < 3; i++) {
            blnExist = _GenericFunctions.IsElementExistXpath(wd, btnWebCheckin);
            if (blnExist) {
                wd.findElement(By.xpath(btnWebCheckin)).click();
                break;
            }
            _GenericFunctions.wait("1000");
        }

        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAB_NTUC Promo Home Page");
        }
    }

    public void EnterAdultDetails_SCB(WebDriver wd, String AdultName) throws ParseException {
        int intTotalCount = Integer.parseInt(Global.AdultCount);
        String strView = "CONTROLGROUPPASSENGER_PassengerInputViewPassengerView_";
        _GenericFunctions.GenerateLastName();
        for (int i = 1; i < intTotalCount; i++) {
            System.out.println("Entering Passenger Details for Adult#: " + (i + 1));
            _GenericFunctions.InputData_ById(wd, strView + "DropDownListTitle_" + i, Global.AdultTitle.get(i));
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

            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateMonth_" + i)))
                    .selectByVisibleText(Global.AdultBMonth.get(i));
            _GenericFunctions.clickOnSkySales(wd);

            new Select(wd.findElement(By.id(strView + "DropDownListBirthDateYear_" + i)))
                    .selectByVisibleText(Global.AdultBYear.get(i));
            _GenericFunctions.clickOnSkySales(wd);

            //Check if Passport details exist
            _PassengerPage.InputPassportDetails(wd, strView, "ADT", i);
        }
    }
}
