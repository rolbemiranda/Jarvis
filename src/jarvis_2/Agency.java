package jarvis_2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author kassmiranda
 */
public class Agency {

    private static GenericFunctions _GenericFunctions;
    private static SearchFlightPage _SearchFlightPage;

    public Agency() {
        _SearchFlightPage = new SearchFlightPage();
        _GenericFunctions = new GenericFunctions();
    }

    public void Agency_Login(WebDriver wd, String sUsername, String sPassword, String AgencyType) {
        String Username = null, Password = null;

        //Wait Until Agency Login Page Exist
        AgencyLoginPageSync(wd, ".//*[@id='ControlGroupLoginAgentView_AgentLoginView_TextBoxUserID']");

        if (sUsername.toUpperCase().equals("NULL")) {
            switch (AgencyType.toUpperCase()) {

                //TASGSWT
                case "TAMA":
                    Username = "KDTAMA";
                    Password = "Testing!2347";
                    break;

                case "TAHO":
                    Username = "KDHOLD";
                    Password = "Testing!2345";
                    break;

                case "TAAP":
                    Username = "KDTAAP";
                    Password = "Testing!2345";
                    break;

                case "TAW3":
                    Username = "KDTAW3";
                    Password = "Testing!2345";
                    break;
            }
        } else {
            Username = sUsername;
            Password = sPassword;
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupLoginAgentView_AgentLoginView_TextBoxUserID']")).clear();
        wd.findElement(By.xpath(".//*[@id='ControlGroupLoginAgentView_AgentLoginView_TextBoxUserID']")).sendKeys(Username);
        wd.findElement(By.xpath(".//*[@id='ControlGroupLoginAgentView_AgentLoginView_PasswordFieldPassword']")).clear();
        wd.findElement(By.xpath(".//*[@id='ControlGroupLoginAgentView_AgentLoginView_PasswordFieldPassword']")).sendKeys(Password);
        wd.findElement(By.xpath(".//*[@id='ControlGroupLoginAgentView_AgentLoginView_ButtonLogIn']")).click();

        //Wait Until Search page Exist
        AgencyLoginPageSync(wd, ".//*[@id='AvailabilitySearchInputUpdateProfileAgentViewdestinationStation1']");

    }

    public void Intranet_Login(WebDriver wd, String URL, String strClose, String LoginType) {
        String Username = null, Password = null;

        if (strClose.toUpperCase().equals("YES")) {
            wd = new FirefoxDriver();
        }
        wd.manage().window().maximize();
        wd.get(URL + "IntranetSearch.aspx");

        //Wait Until Agency Login Page Exist
        AgencyLoginPageSync(wd, ".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_TextBoxUserID']");

        switch (LoginType.toUpperCase()) {
            case "KASS":
                Username = "1328";
                Password = "Tiger123";
                break;

            case "RI":
                Username = "13010023";
                Password = "TigerRI123";
                break;

            case "DG":
                Username = "1262";
                Password = "sweetness";
                break;
        }

        wd.findElement(By.xpath(".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_TextBoxUserID']")).clear();
        wd.findElement(By.xpath(".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_TextBoxUserID']")).sendKeys(Username);
        wd.findElement(By.xpath(".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_PasswordFieldPassword']")).clear();
        wd.findElement(By.xpath(".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_PasswordFieldPassword']")).sendKeys(Password);
        wd.findElement(By.xpath(".//*[@id='ControlGroupMainIntranetSearchView"
                + "_IntranetLoginIntranetSearchView_ButtonLogIn']")).click();

        //Wait Until Search page Exist
        AgencyLoginPageSync(wd, ".//*[@id='ControlGroupSearchView"
                + "_AvailabilitySearchInputSearchView_DropDownListFareTypes']");

    }

    public void AgencyLoginPageSync(WebDriver wd, String LocString) {
        WebDriverWait wait = new WebDriverWait(wd, 100);
        //Wait Until Switch My Flight Window Exists
        try {
            System.out.println("waiting for Agency Search Page to appear");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocString)));
            System.out.println("Agency Search appeared.");
        } catch (Exception e) {
            System.out.println("System can't find Agency Search Page.");
        }
    }

    public static Boolean ag_roundTrip(WebDriver wd) {
        wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_RoundTrip']")).click();
        return true;
    }

    public static Boolean ag_OneWayTrip(WebDriver wd) {
        wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_RoundTrip']")).click();
        //wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputPassengerView_OneWay']")).click();
        wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_OneWay']")).click();
        return false;
    }

    public void ag_select_Market(WebDriver wd, String locator, String DepRet, String data) {
        switch (DepRet.toUpperCase()) {
            case "RETURN":
                wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentViewdestinationStation1']")).sendKeys(data);
                break;
            case "DEPART":
                wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentVieworiginStation1']")).sendKeys(data);
                break;
        }
    }

    public void ag_select_FlightDate(WebDriver wd, String locator, String DepRet, String data) {
        switch (DepRet.toUpperCase()) {
            case "RETURN YEAR":
                if (Global.IsRoundTrip.booleanValue()) {
                    wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListMarketMonth2']")).sendKeys(data);
                }
                break;
            case "RETURN DAY":
                if (Global.IsRoundTrip.booleanValue()) {
                    wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListMarketDay2']")).sendKeys(data);
                }
                break;
            case "DEPART YEAR":
                wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListMarketMonth1']")).sendKeys(data);
                break;
            case "DEPART DAY":
                wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListMarketDay1']")).sendKeys(data);
                break;
        }
    }

    public void ag_enter_Passenger_Count(WebDriver wd, String locator, String PassengerType, String data) {
        String CurrSelected = "";
        String locString = "";
        switch (PassengerType.toUpperCase()) {
            case "ADULT":
                locString = ".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListPassengerType_ADT']";
                Global.AdultCount = data;
                break;

            case "CHILD":
                locString = ".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListPassengerType_CHD']";
                Global.ChildCount = data;
                break;

            case "INFANT":
                locString = ".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_DropDownListPassengerType_INFANT']";
                Global.InfantCount = data;
                break;
        }

        while (CurrSelected.toUpperCase().compareTo(data.replace(".0", "").toUpperCase()) != 0) {
            wd.findElement(By.xpath(locString)).sendKeys(data);
            wd.findElement(By.xpath(locString)).sendKeys(Keys.RETURN);
            CurrSelected = _GenericFunctions.getSelectedValue(wd, locString, "XPATH");
        }
    }

    public void ag_confirm_SearchPage(WebDriver wd) throws Exception {

        //Capture Screenshot
        if (Global.ActivateScreenshot) {
            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "AAA_SelectFlightPage");
        }
        Global.SelectVariant = "A";

        //Click Continue Button
        wd.findElement(By.xpath(".//*[@id='AvailabilitySearchInputUpdateProfileAgentView_ButtonSubmit']")).click();

        //Wait Until Select Flight Exist
        AgencyLoginPageSync(wd, ".//*[@id='ControlGroupSelectView_AgreementInputSelectView_CheckBoxAgreement']");

    }
}
