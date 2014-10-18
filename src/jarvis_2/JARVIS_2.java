// <editor-fold defaultstate="collapsed" desc="JARVIS - TIGER AUTOMATED TESTING TOOL">
/**
 *************************************************************************************************************
 *************************************************************************************************************
 * Program Name: JARVIS - TIGER AUTOMATED TESTING TOOL********************************************************
 * Created By: Kass Miranda***********************************************************************************
 * Date Created: March 2013***********************************************************************************
 * Version: 3.0***********************************************************************************************
 * ************************************************************************************************************
 * ***********************************************************************************************************
 */
package jarvis_2;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="LIBRARIES">
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.openqa.selenium.WebDriver;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
// </editor-fold>
public class JARVIS_2 {

// <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    private static Agency _Agency;
    private static BrowserFunctions _BrowserFunctions;
    private static EmailNotification_1 _EmailNotification_1;
    private static ConfirmationPage _ConfirmationPage;
    private static GenericFunctions _GenericFunctions;
    private static FrontendFunctions _FrontendFunctions;
    private static PassengerPage _PassengerPage;
    private static ManageMyBooking _ManageMyBooking;
    private static PaymentPage _PaymentPage;
    private static PromoPage _PromoPage;
    private static SearchFlightPage _SearchFlightPage;
    private static Seat _Seat;
    private static SelectFlightPage _SelectFlightPage;
    private static VerificationFunctions _VerificationFunctions;
    private static WebCheckin _WebCheckin;

    // </editor-fold>
    public static void main(String[] args) throws Exception {

// <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
        _Agency = new Agency();
        _BrowserFunctions = new BrowserFunctions();
        _EmailNotification_1 = new EmailNotification_1();
        _ConfirmationPage = new ConfirmationPage();
        _FrontendFunctions = new FrontendFunctions();
        _GenericFunctions = new GenericFunctions();
        _ManageMyBooking = new ManageMyBooking();
        _PassengerPage = new PassengerPage();
        _PaymentPage = new PaymentPage();
        _PromoPage = new PromoPage();
        _SearchFlightPage = new SearchFlightPage();
        _Seat = new Seat();
        _SelectFlightPage = new SelectFlightPage();
        _VerificationFunctions = new VerificationFunctions();
        _WebCheckin = new WebCheckin();
        String[][] scenarios;
        String[][] steps;
        String fPath = _GenericFunctions.GetAbsolutePath();
        scenarios = _GenericFunctions.excelRead(fPath + "\\DATA\\Control_File.xls");
        WebDriver wd = null;
        String PNR = "";
        // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="RESULT FOLDER">
        //Create Results Folder
        String FolderTimeStamp = _GenericFunctions.GenerateTimeStamp();
        Global.FolderTimeStamp = FolderTimeStamp;
        String ResultsFolder = fPath + "\\RESULTS\\" + FolderTimeStamp;
        new File(ResultsFolder).mkdirs();

        //Create Screenshot Folder
        String ScreenshotResultsFolder = fPath + "\\RESULTS\\" + FolderTimeStamp + "\\SCREENSHOTS\\";
        new File(ScreenshotResultsFolder).mkdirs();
        // </editor-fold>

        //Main Loop : Count Scenario to Run
        for (int imain = 1; imain < scenarios.length; imain++) {
            // <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
            String RunScenario = scenarios[imain][0];
            String TestCategory = scenarios[imain][1];
            String Scenario_Filename = scenarios[imain][2];
            String TestCase_ID = scenarios[imain][3];
            Global.TestCase_ID = TestCase_ID;
            Global.OverallTestingStatus = "PASSED";
            Global.ContinueExecution = true;
            Global.MCC = "Not Available";
            // </editor-fold>

            if (RunScenario.equalsIgnoreCase("Y")) {
                System.out.println(imain + " of " + (scenarios.length - 1) + ": " + Scenario_Filename
                        + " is currently running.");
                steps = _GenericFunctions.excelRead(fPath + "\\DATA\\" + TestCategory + "\\"
                        + Scenario_Filename);

                //Steps Loop : Count Steps to Run
                for (int idata = 1; idata < steps.length; idata++) {
                    // <editor-fold defaultstate="collapsed" desc="INSTANTIATE">
                    //Initialize Parameters
                    String Flag = steps[idata][0];
                    String StepName = steps[idata][1];
                    String Keyword = steps[idata][2];
                    String param1 = steps[idata][3];
                    String param2 = steps[idata][4];
                    String param3 = steps[idata][5];
                    String param4 = steps[idata][6];

                    //Initialized Results File
                    String ResultsFileName = ResultsFolder + "\\" + TestCase_ID + "_Results.xls";
                    String ScreenshotFileName = ResultsFolder + "\\" + TestCase_ID + "_SCREENSHOT.docx";

                    //Store Results Path/Files to Global
                    Global.Scenario_Filename = Scenario_Filename;
                    Global.ComputationFileName = ResultsFolder + "\\" + TestCase_ID + "_Computation.txt";
                    Global.ResultsFolder = ResultsFolder;
                    Global.ScreenshotFileName = ScreenshotFileName;
                    Global.ResultsFileName = ResultsFileName;

                    //Create Result file on start of the program
                    if (idata == 1) {
                        _GenericFunctions.CreateOutputFile(ResultsFileName);
                    }
                    // </editor-fold>
                    if (Global.ContinueExecution) {

                        if (Flag.equalsIgnoreCase("Y")) {
                            //Tracking Tool
                            System.out.println(Global.TestCase_ID + ": " + idata + " of "
                                    + (steps.length - 1) + ": " + StepName);
                            Global.iData = idata;
                            Global.Keyword = Keyword;

                            switch (Keyword.toUpperCase()) {
                                // <editor-fold defaultstate="expanded" desc="CONFIRM FUNCTIONS">
                                case "AG_CONFIRM_SEARCHPAGE":
                                    _Agency.ag_confirm_SearchPage(wd);
                                    break;
                                case "CHANGEFLIGHT_NEXTSTEP":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.ChangeFlight_NextStep(wd);
                                    }
                                    break;
                                case "CHANGEADDON":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")
                                            || Global.MMBMode.toUpperCase().equals("ADDON")
                                            || Global.MMBMode.toUpperCase().equals("BAGGAGE")) {
                                        _ManageMyBooking.ChangeAddon(wd);
                                    }
                                    break;
                                case "CHANGESEAT":
                                    _ManageMyBooking.ChangeSeat(wd);
                                    break;
                                case "CONFIRM_SEARCHPAGE":
                                    _SearchFlightPage.confirm_SearchPage(wd);
                                    break;
                                case "CONFIRM_SEARCHPAGEAB":
                                    _SearchFlightPage.confirm_SearchPageAB(wd);
                                    break;
                                case "CONFIRM_SEARCHPAGE_BUNDLE":
                                    _SearchFlightPage.confirm_SearchPage_Bundle(wd);
                                    break;
                                case "CONFIRM_SELECTFLIGHT":
                                    _SelectFlightPage.confirm_SelectFlight(wd);
                                    break;
                                case "CONFIRM_PASSENGERPAGE":
                                    _PassengerPage.confirm_PassengerPage(wd);
                                    break;
                                case "CONFIRM_PASSENGERINDEMNITY":
                                    _PassengerPage.confirm_PassengerIndemnity(wd);
                                    break;
                                case "CONFIRM_SELECTCHANGE":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.confirm_SelectChange(wd);
                                    }
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="AGENCY FUNCTIONS">
                                case "AGENCY_LOGIN":
                                    _Agency.Agency_Login(wd, param1, param2, param3);
                                    break;
                                case "AG_ONEWAYTRIP":
                                    Global.IsRoundTrip = _Agency.ag_OneWayTrip(wd);
                                    break;
                                case "AG_ROUNDTRIP":
                                    Global.IsRoundTrip = _Agency.ag_roundTrip(wd);
                                    break;
                                case "AG_SELECT_MARKET":
                                    _Agency.ag_select_Market(wd, param1, param2, param3);
                                    break;
                                case "AG_SELECT_FLIGHTDATE":
                                    _Agency.ag_select_FlightDate(wd, param1, param2, param3);
                                    break;
                                case "AG_ENTER_PASSENGER_COUNT":
                                    _Agency.ag_enter_Passenger_Count(wd, param1, param2, param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="BROWSER FUNCTIONS">
                                case "CLOSE_BROWSER":
                                    _BrowserFunctions.close_browser(wd, Global.TestCase_ID);
                                    break;
                                case "GET_PAGE_SOURCE":
                                    _BrowserFunctions.get_Page_Source(wd);
                                    break;
                                case "NAVIGATE_TO":
                                    _BrowserFunctions.navigate_to(wd, param3);
                                    break;
                                case "OPEN_BROWSER":
                                    wd = _BrowserFunctions.open_browser(param1, param2, param3, param4);
                                    break;
                                case "REFRESHPAGE":
                                    _BrowserFunctions.RefreshPage(wd);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="EMAIL FUNCTIONS">
                                case "SENDEMAIL":
                                    _EmailNotification_1.Send(param1, param2, param3);
                                    break;
                                case "SMOKETEST_START_EMAIL":
                                    _EmailNotification_1.SendEmailHasCommenced(param1, param2, param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="FRONTEND FUNCTIONS">
                                case "CHECKIFMARKETEXIST":
                                    _FrontendFunctions.checkIfMarketExist(wd, param2, param3, param4);
                                    break;
                                case "CHECKFRONTENDINTEGRATION":
                                    _FrontendFunctions.checkFrontendIntegration(wd, param3, param4);
                                    break;
                                case "CHECKIFROUNDTRIP":
                                    _FrontendFunctions.CheckIfRoundtrip(wd, param4);
                                    break;
                                case "CHECKIFPASSENGERCOUNTEXISTS":
                                    _FrontendFunctions.checkIfPassengerCountExists(wd, param2, param3, param4);
                                    break;
                                case "CHECKIFSEARCHISACTIVATED":
                                    _FrontendFunctions.checkIfSearchIsActivated(wd, param4);
                                    break;
                                case "CLICKSEARCHNOW":
                                    _FrontendFunctions.ClickSearchNow(wd);
                                    break;
                                case "FRONTENDSYNC":
                                    _FrontendFunctions.FrontendSync(wd);
                                    break;
                                case "OPENMARKETFILTER":
                                    _FrontendFunctions.openMarketFilter(wd, param3);
                                    break;
                                case "OPENDATEFILTER":
                                    _FrontendFunctions.openDateFilter(wd, param3);
                                    break;
                                case "RESETMARKETCHECKINDICATOR":
                                    _FrontendFunctions.ResetMarketCheckIndicator();
                                    break;
                                case "SELECTDATE":
                                    _FrontendFunctions.selectDate(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECTMARKETVIACLICK":
                                    _FrontendFunctions.selectMarketViaClick(wd, param2, param3, param4);
                                    break;
                                case "SELECTMARKETVIATYPETAB":
                                    _FrontendFunctions.selectMarketViaTypeTab(wd, param2, param3);
                                    break;
                                case "SELECTPASSENGERCOUNT":
                                    _FrontendFunctions.selectPassengerCount(wd, param2, param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="GENERIC FUNCTIONS">
                                //CLICK FUNCTION
                                case "SELECTLANGUAGE":
                                    _GenericFunctions.SelectLanguage(wd, param3);
                                    break;
                                case "SELECTMCC":
                                    _GenericFunctions.SelectMCC(wd, param3);
                                    break;
                                case "SCHEDULER":
                                    _GenericFunctions.Scheduler(param2, param3, param4);
                                    break;
                                case "CLICK_ELEMENT":
                                    _GenericFunctions.click_element(wd, param1, param2, ResultsFileName, Global.TestCase_ID, StepName);
                                    break;

                                //EXTRACT FUNCTIONS
                                case "GET_INFO_ITINERARY_CONFIRMATION_PAGE":
                                    _ConfirmationPage.get_Info_Itinerary_Confirmation_Page(wd, param1, param2, param3, ResultsFileName, Global.TestCase_ID, StepName);
                                    break;
                                case "GET_TEXT_VALUE":
                                    PNR = _GenericFunctions.getTxtValue(wd, param1, param2);
                                    _GenericFunctions.WriteResults(ResultsFileName, param3, Keyword, PNR, "FAIL", "FAIL");
                                    break;

                                //VERIFICATION FUNCTIONS    
                                case "COMPARE_TEXT_TABLE":
                                    _GenericFunctions.CompareTextValueTable(wd, StepName, param2, param3, param4);
                                    break;
                                case "COMPARE_TEXT_ELEMENT":
                                    _GenericFunctions.CompareTextValueElement(wd, StepName, param3, param4);
                                    break;

                                //INPUT FUNCTION	
                                case "SEND_KEYS":
                                    _GenericFunctions.send_keys(wd, param1, param2, param3);
                                    break;
                                case "SEND_KEYS_TAB":
                                    _GenericFunctions.send_keys_tab(wd, param1, param2, param3);

                                //SYNC FUNCTIONS
                                case "VERIFY_ELEMENT":
                                    _GenericFunctions.verify_element(wd, param1, param2, param3, ResultsFileName, Global.TestCase_ID, StepName);
                                    break;
                                case "WAIT":
                                    _GenericFunctions.wait(param3);
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="MMB FUNCTIONS">
                                case "RETRIEVEBOOKINGSYNC":
                                    _ManageMyBooking.RetrieveBookingSync(wd);
                                    break;
                                case "MMBLOGIN":
                                    _ManageMyBooking.MMBLogin(wd, param1, param2, param3);
                                    break;
                                case "MMBMODE":
                                    _ManageMyBooking.MMBMode(wd, param3);
                                    break;
                                case "CHANGEDEPARTINGFLIGHT":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.ChangeDepartingFlight(wd, param1, param2, param3);
                                    }
                                    break;
                                case "CHANGEARRIVINGFLIGHT":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.ChangeArrivingFlight(wd, param1, param2, param3);
                                    }
                                    break;

                                case "SELECTCHANGEFARE":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.selectChangeFare(wd, param1, param2, param3);
                                    }
                                    break;
                                case "CHANGEFLIGHT_AGREEMENTBOX":
                                    if (Global.MMBMode.toUpperCase().equals("FLIGHT")) {
                                        _ManageMyBooking.changeFlight_AgreementBox(wd);
                                    }
                                    break;
                                case "ISROUNDTRIP":
                                    _ManageMyBooking.IsRoundTrip(param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="PASSENGER PAGE FUNCTIONS">
                                case "STORE_BAGGAGE":
                                    _PassengerPage.store_Baggage(wd, param1, param2, param3, ResultsFileName, Global.TestCase_ID, StepName, Global.ComputationFileName);
                                    break;
                                case "SELECT_BAGGAGE_MEAL":
                                    _PassengerPage.select_Baggage_Meal(wd, param1, param2, param3);
                                    break;
                                case "SELECT_ALL_SEGMENT":
                                    _PassengerPage.select_All_Segment(wd, param3);
                                    break;
                                case "SELECT_MEAL":
                                    _PassengerPage.select_Meal(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_CAR":
                                    _PassengerPage.select_Car(wd, param3, param4);
                                    break;
                                case "SELECT_BAGGAGE":
                                    _PassengerPage.select_Baggage(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_SPORTSEQUIPMENT":
                                    _PassengerPage.select_SportsEquipment(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_GOLFBAG":
                                    _PassengerPage.select_GolfBag(wd, param1, param2, param3, param4);
                                    break;
                                case "DESELECT_BOARDMEFIRST":
                                    _PassengerPage.deselect_BoardMeFirst(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECTPAXSTAFFTRAVEL":
                                    _PassengerPage.SelectPAXStaffTravel(wd);
                                    break;
                                case "SELECT_QUEUEJUMP":
                                    _PassengerPage.select_QueueJump(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_TRAVELINSURANCE":
                                    _PassengerPage.select_TravelInsurance(wd, param3, param4);
                                    break;
                                case "SELECT_TIGERPLUS":
                                    _PassengerPage.select_TigerPlus(wd, param1, param3, param4);
                                    break;
                                case "SELECT_TIGERCONNECT":
                                    _PassengerPage.select_TigerConnect(wd, param1, param3, param4);
                                    break;
                                case "ENTERADULTDETAILS":
                                    _PassengerPage.EnterAdultDetails(wd, param3);
                                    break;
                                case "EDITADULTDOB":
                                    _PassengerPage.EditAdultDob(wd, param1, param2, param3, param4);
                                    break;
                                case "EDITCHILDDOB":
                                    _PassengerPage.EditChildDob(wd, param1, param2, param3, param4);
                                    break;
                                case "EDITINFANTDOB":
                                    _PassengerPage.EditInfantDob(wd, param1, param2, param3, param4);
                                    break;

                                case "ENTERCHILDDETAILS":
                                    _PassengerPage.EnterChildDetails(wd, param3);
                                    break;
                                case "ENTERINFANTDETAILS":
                                    _PassengerPage.EnterInfantDetails(wd);
                                    break;
                                case "ENTERPASSPORTCOUNTRYADT":
                                    _PassengerPage.EnterPassportCountryAdt(wd, param3);
                                    break;
                                case "ENTERPASSPORTCOUNTRYCHD":
                                    _PassengerPage.EnterPassportCountryChd(wd, param3);
                                    break;
                                case "ENTERPASSPORTCOUNTRYINF":
                                    _PassengerPage.EnterPassportCountryInf(wd, param3);
                                    break;
                                case "ENTERMONTHADT":
                                    _PassengerPage.EnterMonthAdt(wd, param3);
                                    break;
                                case "ENTERMONTHCHD":
                                    _PassengerPage.EnterMonthChd(wd, param3);
                                    break;
                                case "ENTERMONTHINF":
                                    _PassengerPage.EnterMonthInf(wd, param3);
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="PAYMENT PAGE FUNCTION">
                                case "ISINFANTFEETAXABLE":
                                    _PaymentPage.isInfantFeeTaxable(param3);
                                    break;
                                case "COMPARETOTALAMOUNT":
                                    _PaymentPage.CompareTotalAmount(wd, param3);
                                    break;
                                case "CHECK_FARECONDITION":
                                    _PaymentPage.check_FareCondition(wd);
                                    break;
                                case "VOUCHER_PAYMENT":
                                    _PaymentPage.voucher_Payment(wd, param3);
                                    break;
                                case "AGENCY_HOLD":
                                    _PaymentPage.Agency_hold(wd);
                                    break;
                                case "AGENCY_CREDIT":
                                    _PaymentPage.Agency_Credit(wd);
                                    break;
                                case "STORE_BAGGAGE_AMOUNT":
                                    _PaymentPage.store_Baggage_Amount(wd);
                                    break;
                                case "STORE_SPORTS_EQUIPMENT":
                                    _PaymentPage.store_Sports_Equipment(wd);
                                    break;
                                case "STORE_TIGERPLUS":
                                    _PaymentPage.store_TigerPlus(wd);
                                    break;
                                case "STORE_OTHER_FEES_TAXES":
                                    _PaymentPage.store_Other_Fees_Taxes(wd);
                                    break;
                                case "STORE_FARE":
                                    _PaymentPage.store_Fare(wd);
                                    break;
                                case "STORE_TAXES":
                                    _PaymentPage.store_Taxes(wd);
                                    break;
                                case "STORE_SALESTAX":
                                    _PaymentPage.store_SalesTax(wd);
                                    break;
                                case "STORE_BOARDMEFIRST":
                                    _PaymentPage.store_BoardMeFirst(wd);
                                    break;
                                case "STORE_MEALS":
                                    _PaymentPage.store_Meals(wd);
                                    break;
                                case "STORE_TRAVELINSURANCE":
                                    _PaymentPage.store_TravelInsurance(wd);
                                    break;
                                case "ENTERCONTACTDETAILS":
                                    _PaymentPage.EnterContactDetails(wd);
                                    break;
                                case "ENTERAGENTDETAILS":
                                    _PaymentPage.EnterAgentDetails(wd);
                                    break;
                                case "ENTERCONTACTCOUNTRY":
                                    _PaymentPage.EnterContactCountry(wd, param3);
                                    break;
                                case "COMPUTE_TOTAL":
                                    _PaymentPage.compute_Total();
                                    break;
                                case "CREDITCARD_PAYMENT":
                                    _PaymentPage.CreditCard_Payment(wd, param1, param2, param3, param4);
                                    break;
                                case "AXS_PAYMENT":
                                    _PaymentPage.AXS_Payment(wd);
                                    break;
                                case "CASHSENSE_PAYMENT":
                                    _PaymentPage.CashSense_Payment(wd);
                                    break;
                                case "CLICKPAY_PAYMENT":
                                    _PaymentPage.ClickPay_Payment(wd);
                                    break;
                                case "CASHPAY_PAYMENT":
                                    _PaymentPage.CashPay_Payment(wd, param3);
                                    break;
                                case "CHECKPAYMENTTABS":
                                    _PaymentPage.CheckPaymentTabs(wd, param3, param4);
                                    break;
                                case "ATM_RI_PAYMENT":
                                    _PaymentPage.ATM_RI_Payment(wd, param3);
                                    break;
                                case "PAYMENT_BIN_TESTING":
                                    _PaymentPage.Payment_Bin_Testing(wd, param2, param3);
                                    break;
                                case "ACTIVATESMSITINERARY":
                                    _PaymentPage.ActivateSMSItinerary(wd, param3, param4);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="PROMO FUNCTIONS">
                                case "AEONPROMOLOGIN":
                                    _PromoPage.AEONPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "ANZPROMOLOGIN":
                                    _PromoPage.ANZPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "BPIPROMOLOGIN":
                                    _PromoPage.BPIPromoLogin(wd, param3);
                                    break;
                                case "CITIBANKIDLOGIN":
                                    _PromoPage.CitibankIDLogin(wd, param1, param2, param3);
                                    break;
                                case "CITIBANKSGPROMOLOGIN":
                                    _PromoPage.CitibankSGPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "CITIBANKPROMOLOGIN":
                                    _PromoPage.CitibankPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "HSBCPROMOLOGIN":
                                    _PromoPage.HSBCPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "INTRANET_LOGIN":
                                    _Agency.Intranet_Login(wd, param1, param2, param3);
                                    break;
                                case "ICICIPROMOLOGIN":
                                    _PromoPage.ICICIPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "KRUNGTHAIPROMOLOGIN":
                                    _PromoPage.KrungthaiPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "MANDIRIBENEFITSLOGIN":
                                    _PromoPage.MandiriBenefitsLogin(wd, param1, param2, param3);
                                    break;
                                case "MANDIRIPROMOLOGIN":
                                    _PromoPage.MandiriPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "MAYBANKPROMOLOGIN":
                                    _PromoPage.MaybankPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "METROBANKPROMOLOGIN":
                                    _PromoPage.MetroBankPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "MELBOURNESTORMLOGIN":
                                    _PromoPage.MelbourneStormLogin(wd, param1, param2, param3);
                                    break;
                                case "NTUCBENEFITSLOGIN":
                                    _PromoPage.NTUCbenefitslogin(wd, param1, param2, param3);
                                    break;
                                case "NTUCPROMOLOGIN":
                                    _PromoPage.NTUCPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "OCBCPROMOLOGIN":
                                    _PromoPage.OCBCPromoLogin(wd, param1, param2, param3);
                                    break;
                                case "OCBCPROMOLOGIN2":
                                    _PromoPage.OCBCPromoLogin2(wd, param1, param2, param3);
                                    break;
                                case "SCBLOGIN":
                                    _PromoPage.SCBLogin(wd, param1, param2, param3);
                                    break;
                                case "SCBOPTIONPAGE":
                                    _PromoPage.SCBOptionPage(wd, param3);
                                    break;
                                case "SCBPROMOLOGIN":
                                    _PromoPage.SCBPromoLogin(wd);
                                    break;
                                case "STAFFTRAVEL":
                                    _PromoPage.StaffTravel(wd, param1, param2);
                                    break;
                                case "WEBPLOGIN":
                                    _PromoPage.WEBPLogin(wd, param1, param2, param3);
                                    break;
                                case "ENTERADULTDETAILS_SCB":
                                    _PromoPage.EnterAdultDetails_SCB(wd, param3);
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="SCREENSHOT FUNCTIONS">
                                case "ACTIVATE_SCREENSHOT":
                                    _GenericFunctions.ACTIVATE_SCREENSHOT(param3);
                                    break;
                                case "SAVE_SCREENSHOT":
                                    _GenericFunctions.SAVE_SCREENSHOT();
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="SEAT PAGE FUNCTIONS">
                                case "PROCEED_WITH_SEAT_SELECTION":
                                    _Seat.proceed_With_Seat_Selection(wd);
                                    break;
                                case "PROCEED_WITHOUT_SEAT_SELECTION":
                                    _Seat.proceed_WithOut_Seat_Selection(wd);
                                    break;
                                case "SELECT_SEAT_CONFIRMED":
                                    _Seat.select_Seat_Confirmed(wd);
                                    break;
                                case "SELECT_SEAT_UNCONFIRMED":
                                    _Seat.select_Seat_UnConfirmed(wd);
                                    break;
                                case "SELECT_NEWSEATMAP":
                                    _Seat.select_NewSeatMap(wd, param1);
                                    break;
                                case "AUTO_CONTINUE":
                                    _Seat.auto_Continue(wd);
                                    break;
                                case "AUTO_ACCEPTCONTINUE":
                                    _Seat.auto_AcceptContinue(wd);
                                    break;
                                case "AUTO_SKIPWITHOUTSELECTINGSEATS":
                                    _Seat.auto_SkipWithoutSelectingSeats(wd);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="SEARCH FLIGHT FUNCTIONS">
                                case "ENTER_PASSENGER_COUNT":
                                    _SearchFlightPage.enter_Passenger_Count(wd, param1, param2, param3);
                                    break;
                                case "ONEWAYTRIP":
                                    Global.IsRoundTrip = SearchFlightPage.OneWayTrip(wd);
                                    break;
                                case "ROUNDTRIP":
                                    Global.IsRoundTrip = SearchFlightPage.roundTrip(wd);
                                    break;
                                case "SEARCHPAGESYNC":
                                    _SearchFlightPage.SearchPageSync(wd);
                                    break;
                                case "SEARCHFLIGHTBUNDLESYNC":
                                    _SearchFlightPage.SearchFlightBundleSync(wd);
                                    break;
                                case "SELECTCURRENCY":
                                    _SearchFlightPage.SelectCurrency(wd, param3);
                                    break;
                                case "SELECT_FARETYPE_TRAVELTYPE":
                                    _SearchFlightPage.select_FareType_TravelType(wd, param2, param3);
                                    break;
                                case "SELECT_MARKET":
                                    _SearchFlightPage.select_Market(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_FLIGHTDATE":
                                    _SearchFlightPage.select_FlightDate(wd, param1, param2, param3);
                                    break;
                                case "SELECT_FROM_DATEPICKER":
                                    _SearchFlightPage.select_from_datePicker(wd, param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="SELECT FLIGHT FUNCTIONS">
                                case "CHECK_AGREEMENTBOX":
                                    _SelectFlightPage.check_AgreementBox(wd);
                                    break;
                                case "CLICKEDITSEARCH":
                                    _SelectFlightPage.ClickEditSearch(wd);
                                    break;
                                case "CONFIRM_SWITCHMYFLIGHT":
                                    _SelectFlightPage.confirm_SwitchMyFlight(wd);
                                    break;
                                case "SELECT_FARE":
                                    _SelectFlightPage.select_Fare(wd, param1, param2, param3);
                                    break;
                                case "SELECT_FAREAB":
                                    _SelectFlightPage.select_FareAB(wd, param1, param2, param3, param4);
                                    break;
                                case "SELECT_BUNDLEFARE":
                                    _SelectFlightPage.select_BundleFare(wd, param1, param2, param3, param4);
                                    break;
                                case "SWITCHMYFLIGHT":
                                    _SelectFlightPage.SwitchMyFlight(wd, param2, param3);
                                    break;
                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="VERIFICATION FUNCTIONS">
                                case "CHECK_ZEROPRICEBAGGAGE":
                                    _VerificationFunctions.Check_ZeroPriceBaggage(wd, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICEMEAL":
                                    _VerificationFunctions.Check_ZeroPriceMeal(wd, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICESPORTSEQUIPMENT":
                                    _VerificationFunctions.Check_ZeroPriceSportsEquipment(wd, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICEBMF":
                                    _VerificationFunctions.Check_ZeroPriceBMF(wd, param2, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICETIGERPLUS":
                                    _VerificationFunctions.Check_ZeroPriceTigerPlus(wd, param2, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICEQUEUEJUMP":
                                    _VerificationFunctions.Check_ZeroPriceQueueJump(wd, param2, param3, param4);
                                    break;
                                case "CHECK_ZEROPRICEINSURANCE":
                                    _VerificationFunctions.Check_ZeroPriceInsurance(wd, param4);
                                    break;
                                case "DCCVALIDATION":
                                    _VerificationFunctions.DCCValidation(wd, param3, param4);
                                    break;
                                case "VERIFY_IFCHECKED":
                                    _VerificationFunctions.Verify_IfChecked(wd, param2, param3, param4);
                                    break;
                                case "CHECK_SELECTEDVALUEONELEMENT":
                                    _VerificationFunctions.Check_SelectedValueOnElement(wd, param2, param3, param4);
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="WEBCHECKIN FUNCTIONS">
                                case "CHECKINPASSENGER":
                                    _WebCheckin.CheckinPassenger(wd, param2, param3, param4);
                                    break;
                                case "CONFIRMCHECKIN":
                                    _WebCheckin.ConfirmCheckin(wd);
                                    break;
                                case "RETRIEVEYOURFLIGHT":
                                    _WebCheckin.RetrieveYourFlight(wd, param3, param4);
                                    break;
                                case "SELECTCARRIER":
                                    _WebCheckin.SelectCarrier(wd, param3);
                                    break;
                                case "UPGRADEBAGGAGE":
                                    _WebCheckin.UpgradeBaggage(wd, param1, param2, param3, param4);
                                    break;
                                case "WEBCHECKINMMBMODE":
                                    _WebCheckin.WebCheckinMMBMode(wd, param3);
                                    break;
                                case "WEBCHECKINSYNC":
                                    _WebCheckin.WebCheckinSync(wd);
                                    break;

                                // </editor-fold>

                                // <editor-fold defaultstate="collapsed" desc="SWITCH DEFAULT">
                                default:
                                    System.out.println("LINE: " + idata + " - " + Keyword
                                            + ": NOT FOUND ON THE VALID KEYWORD LIST.");
                                    System.exit(0);
                                // </editor-fold>
                            }
                        }
                    } else {
                        //Capture Screenshot
                        if (Global.ActivateScreenshot) {
                            _GenericFunctions.CAPTURE_SCREENSHOT(wd, "ZZZ_Error Screen");
                        }

                        _EmailNotification_1.SendEmailHasFailed(Global.EmailAddress, Scenario_Filename, Global.iData, Global.Keyword, ResultsFolder);
                        System.out.println("WARNING: Jarvis Test has stopped at line " + Global.iData
                                + " where current step " + Global.Keyword + " is being executed."
                                + "Check your current screen for possible errors.");
                        _GenericFunctions.wait("5000");
                        //_GenericFunctions.CloseNotification(wd, "Jarvis Test has stopped at line " + Global.iData
                        //        + " where current step " + Global.Keyword + " is being executed."
                        //        + "Check your current screen for possible errors.");
                        idata = steps.length + 1;
                    }
                }
            }
        }
        //<editor-fold defaultstate="collapsed" desc="EXIT NOTIFICATION">
        _GenericFunctions.CloseNotification(wd,
                "Program has been completed. "
                + "Want to quit the current run?");
        //</editor-fold>
    }
}
