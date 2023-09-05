package helpers;

import base.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import utils.extentreports.ExtentTestManager;
import utils.logs.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utilities.ExcelUtil.currentDir;
import static helpers.ValidatePaymentMessageFields.formattingInputDate;


public class CreateMultiDebitMultiCreditPain001 extends BasePage {
    public CreateMultiDebitMultiCreditPain001(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

    /** Methods*/
    //create header
    public String createHeaderForMultiDebitMultiCredit(String MessageIdentification,String CreationDateTime,String NumberOfTransactionAtMsgLevel,
                                                       String ControlSumAtMsgLevel,String InitiatingPartyName,String CountryOfResidence){
        String HeaderForMultiDebitMultiCredit = null;
        try{
            HeaderForMultiDebitMultiCredit = "<ns2:Document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">\n" +
                    "    <ns2:CstmrCdtTrfInitn>\n" +
                    "        <ns2:GrpHdr>\n" +
                    "            <ns2:MsgId>"+setTagValue(MessageIdentification)+"</ns2:MsgId>\n" +
                    "            <ns2:CreDtTm>"+setTagValue(CreationDateTime)+"</ns2:CreDtTm>\n" +
                    "            <ns2:NbOfTxs>"+setTagValue(NumberOfTransactionAtMsgLevel)+"</ns2:NbOfTxs>\n" +
                    "            <ns2:CtrlSum>"+setTagValue(ControlSumAtMsgLevel)+"</ns2:CtrlSum>\n" +
                    "            <ns2:InitgPty>\n" +
                    "                <ns2:Nm>"+setTagValue(InitiatingPartyName)+"</ns2:Nm>\n" +
                    "                <ns2:CtryOfRes>"+setTagValue(CountryOfResidence)+"</ns2:CtryOfRes>\n" +
                    "            </ns2:InitgPty>\n" +
                    "        </ns2:GrpHdr>";

        }
        catch(Exception e){
            System.out.println(e);
        }
        return HeaderForMultiDebitMultiCredit;

    }

    //create 1 block
    public String createPaymentBlock(String PaymentInformationIdentification, String PaymentMethod, String BatchBooking,String NumberOfTransactionAtPmtInfLevel,
                                     String CategoryPurposeCode, String CodeType, String RequestedExecutionDate, String DebtorName, String DebtorAccountId,
                                     String DebtorAccountType,	String DebtorCurrency,	String DebtorBankId,	String DebtorPostalCode,	String DebtorBranchId, String DebtorChargeBearer,
                                     String  CreditorInstructionId_1,	String CreditorEndtoEndId_1,	String CreditorInstructedAmountCCy_1,	String CreditorInstructedAmount_1,	String CreditorBankId_1,
                                     String CreditorPostalCode_1,	String CreditorBranchId_1,	String CreditorName_1,	String CreditorAccountNumber_1,	String CreditorAccountCurrency_1,	String CreditorAccountType_1,
                                     String CreditorRemittanceInfo_1,	String CreditorRefInfo_1,	String  AdditionalRemittanceInfo_1){
        String PaymentInfoBlock = null;
        try{
            PaymentInfoBlock="\n" +
                    "         <ns2:PmtInf>\n" +
                    "            <ns2:PmtInfId>"+setTagValue(PaymentInformationIdentification)+"</ns2:PmtInfId>\n" +
                    "            <ns2:PmtMtd>"+setTagValue(PaymentMethod)+"</ns2:PmtMtd>\n" +
                    "            <ns2:BtchBookg>"+setTagValue(BatchBooking)+"</ns2:BtchBookg>\n" +
                    "            <ns2:NbOfTxs>"+setTagValue(NumberOfTransactionAtPmtInfLevel)+"</ns2:NbOfTxs>\n" +
                    "            <ns2:PmtTpInf>\n" +
                    "                <ns2:CtgyPurp>\n" +
                    "                    <ns2:"+setTagValue(CodeType)+">"+setTagValue(CategoryPurposeCode)+"</ns2:"+setTagValue(CodeType)+">\n" +
                    "                </ns2:CtgyPurp>\n" +
                    "            </ns2:PmtTpInf>\n" +
                    "            <ns2:ReqdExctnDt>"+setTagValue(RequestedExecutionDate)+"</ns2:ReqdExctnDt>\n" +
                    "            <ns2:Dbtr>\n" +
                    "                <ns2:Nm>"+setTagValue(DebtorName)+"</ns2:Nm>\n" +
                    "            </ns2:Dbtr>\n" +
                    "            <ns2:DbtrAcct>\n" +
                    "                <ns2:Id>\n" +
                    "                    <ns2:Othr>\n" +
                    "                        <ns2:Id>"+setTagValue(DebtorAccountId)+"</ns2:Id>\n" +
                    "                    </ns2:Othr>\n" +
                    "                </ns2:Id>\n" +
                    "                <ns2:Tp>\n" +
                    "                    <ns2:Prtry>"+setTagValue(DebtorAccountType)+"</ns2:Prtry>\n" +
                    "                </ns2:Tp>\n" +
                    "                <ns2:Ccy>"+setTagValue(DebtorCurrency)+"</ns2:Ccy>\n" +
                    "            </ns2:DbtrAcct>\n" +
                    "            <ns2:DbtrAgt>\n" +
                    "                <ns2:FinInstnId>\n" +
                    "                    <ns2:ClrSysMmbId>\n" +
                    "                        <ns2:MmbId>"+setTagValue(DebtorBankId)+"</ns2:MmbId>\n" +
                    "                    </ns2:ClrSysMmbId>\n" +
                    "                    <ns2:PstlAdr>\n" +
                    "                        <ns2:PstCd>"+setTagValue(DebtorPostalCode)+"</ns2:PstCd>\n" +
                    "                    </ns2:PstlAdr>\n" +
                    "                </ns2:FinInstnId>\n" +
                    "                <ns2:BrnchId>\n" +
                    "                    <ns2:Id>"+setTagValue(DebtorBranchId)+"</ns2:Id>\n" +
                    "                </ns2:BrnchId>\n" +
                    "            </ns2:DbtrAgt>\n";
                    if(!DebtorChargeBearer.isEmpty()) {
                        PaymentInfoBlock = PaymentInfoBlock + "            <ns2:ChrgBr>" + setTagValue(DebtorChargeBearer) + "</ns2:ChrgBr>\n" +
                                "            <ns2:CdtTrfTxInf>\n" +
                                "                <ns2:PmtId>\n" +
                                "                    <ns2:InstrId>" + setTagValue(CreditorInstructionId_1) + "</ns2:InstrId>\n" +
                                "                    <ns2:EndToEndId>" + setTagValue(CreditorEndtoEndId_1) + "</ns2:EndToEndId>\n" +
                                "                </ns2:PmtId>\n" +
                                "                <ns2:Amt>\n" +
                                "                    <ns2:InstdAmt Ccy=\"" + setTagValue(CreditorInstructedAmountCCy_1) + "\">" + setTagValue(CreditorInstructedAmount_1) + "</ns2:InstdAmt>\n" +
                                "                </ns2:Amt>\n" +
                                "                <ns2:CdtrAgt>\n" +
                                "                    <ns2:FinInstnId>\n" +
                                "                        <ns2:ClrSysMmbId>\n" +
                                "                            <ns2:MmbId>" + setTagValue(CreditorBankId_1) + "</ns2:MmbId>\n" +
                                "                        </ns2:ClrSysMmbId>\n" +
                                "                        <ns2:PstlAdr>\n" +
                                "                            <ns2:PstCd>" + setTagValue(CreditorPostalCode_1) + "</ns2:PstCd>\n" +
                                "                        </ns2:PstlAdr>\n" +
                                "                    </ns2:FinInstnId>\n" +
                                "                    <ns2:BrnchId>\n" +
                                "                        <ns2:Id>" + setTagValue(CreditorBranchId_1) + "</ns2:Id>\n" +
                                "                    </ns2:BrnchId>\n" +
                                "                </ns2:CdtrAgt>\n" +
                                "                <ns2:Cdtr>\n" +
                                "                    <ns2:Nm>" + setTagValue(CreditorName_1) + "</ns2:Nm>\n" +
                                "                </ns2:Cdtr>\n" +
                                "                <ns2:CdtrAcct>\n" +
                                "                    <ns2:Id>\n" +
                                "                        <ns2:Othr>\n" +
                                "                            <ns2:Id>" + setTagValue(CreditorAccountNumber_1) + "</ns2:Id>\n" +
                                "                        </ns2:Othr>\n" +
                                "                    </ns2:Id>\n" +
                                "                    <ns2:Tp>\n" +
                                "                        <ns2:Prtry>" + setTagValue(CreditorAccountType_1) + "</ns2:Prtry>\n" +
                                "                    </ns2:Tp>\n" +
                                "                    <ns2:Ccy>" + setTagValue(CreditorAccountCurrency_1) + "</ns2:Ccy>\n" +
                                "                </ns2:CdtrAcct>\n" +
                                "                <ns2:RmtInf>\n" +
                                "                    <ns2:Ustrd>" + CreditorRemittanceInfo_1 + "</ns2:Ustrd>\n" +
                                "                    <ns2:Strd>\n" +
                                "                        <ns2:CdtrRefInf>\n" +
                                "                            <ns2:Ref>" + CreditorRefInfo_1 + "</ns2:Ref>\n" +
                                "                        </ns2:CdtrRefInf>\n";
                    }else {
                        PaymentInfoBlock = PaymentInfoBlock + "            <ns2:CdtTrfTxInf>\n" +
                                "                <ns2:PmtId>\n" +
                                "                    <ns2:InstrId>" + setTagValue(CreditorInstructionId_1) + "</ns2:InstrId>\n" +
                                "                    <ns2:EndToEndId>" + setTagValue(CreditorEndtoEndId_1) + "</ns2:EndToEndId>\n" +
                                "                </ns2:PmtId>\n" +
                                "                <ns2:Amt>\n" +
                                "                    <ns2:InstdAmt Ccy=\"" + setTagValue(CreditorInstructedAmountCCy_1) + "\">" + setTagValue(CreditorInstructedAmount_1) + "</ns2:InstdAmt>\n" +
                                "                </ns2:Amt>\n" +
                                "                <ns2:CdtrAgt>\n" +
                                "                    <ns2:FinInstnId>\n" +
                                "                        <ns2:ClrSysMmbId>\n" +
                                "                            <ns2:MmbId>" + setTagValue(CreditorBankId_1) + "</ns2:MmbId>\n" +
                                "                        </ns2:ClrSysMmbId>\n" +
                                "                        <ns2:PstlAdr>\n" +
                                "                            <ns2:PstCd>" + setTagValue(CreditorPostalCode_1) + "</ns2:PstCd>\n" +
                                "                        </ns2:PstlAdr>\n" +
                                "                    </ns2:FinInstnId>\n" +
                                "                    <ns2:BrnchId>\n" +
                                "                        <ns2:Id>" + setTagValue(CreditorBranchId_1) + "</ns2:Id>\n" +
                                "                    </ns2:BrnchId>\n" +
                                "                </ns2:CdtrAgt>\n" +
                                "                <ns2:Cdtr>\n" +
                                "                    <ns2:Nm>" + setTagValue(CreditorName_1) + "</ns2:Nm>\n" +
                                "                </ns2:Cdtr>\n" +
                                "                <ns2:CdtrAcct>\n" +
                                "                    <ns2:Id>\n" +
                                "                        <ns2:Othr>\n" +
                                "                            <ns2:Id>" + setTagValue(CreditorAccountNumber_1) + "</ns2:Id>\n" +
                                "                        </ns2:Othr>\n" +
                                "                    </ns2:Id>\n" +
                                "                    <ns2:Tp>\n" +
                                "                        <ns2:Prtry>" + setTagValue(CreditorAccountType_1) + "</ns2:Prtry>\n" +
                                "                    </ns2:Tp>\n" +
                                "                    <ns2:Ccy>" + setTagValue(CreditorAccountCurrency_1) + "</ns2:Ccy>\n" +
                                "                </ns2:CdtrAcct>\n" +
                                "                <ns2:RmtInf>\n" +
                                "                    <ns2:Ustrd>" + setTagValue(CreditorRemittanceInfo_1) + "</ns2:Ustrd>\n" +
                                "                    <ns2:Strd>\n" +
                                "                        <ns2:CdtrRefInf>\n" +
                                "                            <ns2:Ref>" + setTagValue(CreditorRefInfo_1) + "</ns2:Ref>\n" +
                                "                        </ns2:CdtrRefInf>\n";
                    }
                        if (!AdditionalRemittanceInfo_1.isEmpty()) {
                            PaymentInfoBlock = PaymentInfoBlock +
                                    "                        <ns2:AddtlRmtInf>" + setTagValue(AdditionalRemittanceInfo_1) + "</ns2:AddtlRmtInf>\n" +
                                    "                    </ns2:Strd>\n" +
                                    "                </ns2:RmtInf>\n" +
                                    "            </ns2:CdtTrfTxInf>\n" +
                                    "        </ns2:PmtInf>";
                        } else {
                            PaymentInfoBlock = PaymentInfoBlock +
                                    "                    </ns2:Strd>\n" +
                                    "                </ns2:RmtInf>\n" +
                                    "            </ns2:CdtTrfTxInf>\n" +
                                    "        </ns2:PmtInf>";
                        }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return PaymentInfoBlock;

    }

    //end of file
    public String createEndOfFile(){
        String EndOfFile = null;
        try{
            EndOfFile ="\n" +
                    "    </ns2:CstmrCdtTrfInitn>\n" +
                    "</ns2:Document>";
        }
        catch(Exception e){
            System.out.println(e);
        }
        return EndOfFile;
    }

    //join blocks
    public String identifyNoOfPaymentBlocks(int noOfBlocks, String P1_PaymentInformationIdentification, String P1_PaymentMethod, String P1_BatchBooking, String P1_NumberOfTransaction, String P1_ControlSumAtPaymentInfoLevel, String P1_CategoryPurposeCode, String P1_CodeType, String P1_RequestedExecutionDate,
                                            String P1_DebtorName, String P1_DebtorAccountId, String P1_DebtorAccountType, String P1_DebtorCurrency, String P1_DebtorBankId, String P1_DebtorPostalCode, String P1_DebtorBranchId, String P1_ChargeBearer, String P1_DebtorForcePostingOption, String P1_DebtorOriginationReference,

                                            String P1_CreditorInstructionId_1, String P1_CreditorEndtoEndId_1, String P1_CreditorInstructedAmountCCy_1, String P1_CreditorInstructedAmount_1, String P1_CreditorBankId_1, String P1_CreditorPostalCode_1, String P1_CreditorBranchId_1, String P1_CreditorName_1, String P1_CreditorAccountNumber_1, String P1_CreditorAccountCurrency_1, String P1_CreditorAccountType_1, String P1_CreditorRemittanceInfo_1, String P1_CreditorRefInfo_1, String P1_AdditionalRemittanceInfo_1,
                                            String P1_CreditorInstructionId_2, String P1_CreditorEndtoEndId_2, String P1_CreditorInstructedAmountCCy_2, String P1_CreditorInstructedAmount_2, String P1_CreditorBankId_2, String P1_CreditorPostalCode_2, String P1_CreditorBranchId_2, String P1_CreditorName_2, String P1_CreditorAccountNumber_2, String P1_CreditorAccountCurrency_2, String P1_CreditorAccountType_2, String P1_CreditorRemittanceInfo_2, String P1_CreditorRefInfo_2, String P1_AdditionalRemittanceInfo_2,
                                            String P1_CreditorInstructionId_3, String P1_CreditorEndtoEndId_3, String P1_CreditorInstructedAmountCCy_3, String P1_CreditorInstructedAmount_3, String P1_CreditorBankId_3, String P1_CreditorPostalCode_3, String P1_CreditorBranchId_3, String P1_CreditorName_3, String P1_CreditorAccountNumber_3, String P1_CreditorAccountCurrency_3, String P1_CreditorAccountType_3, String P1_CreditorRemittanceInfo_3, String P1_CreditorRefInfo_3, String P1_AdditionalRemittanceInfo_3,


                                            String P2_PaymentInformationIdentification, String P2_PaymentMethod, String P2_BatchBooking, String P2_NumberOfTransaction, String P2_ControlSumAtPaymentInfoLevel, String P2_CategoryPurposeCode, String P2_CodeType, String P2_RequestedExecutionDate,
                                            String P2_DebtorName, String P2_DebtorAccountId, String P2_DebtorAccountType, String P2_DebtorCurrency, String P2_DebtorBankId, String P2_DebtorPostalCode, String P2_DebtorBranchId, String P2_ChargeBearer, String P2_DebtorForcePostingOption, String P2_DebtorOriginationReference,

                                            String P2_CreditorInstructionId_1, String P2_CreditorEndtoEndId_1, String P2_CreditorInstructedAmountCCy_1, String P2_CreditorInstructedAmount_1, String P2_CreditorBankId_1, String P2_CreditorPostalCode_1, String P2_CreditorBranchId_1, String P2_CreditorName_1, String P2_CreditorAccountNumber_1, String P2_CreditorAccountCurrency_1, String P2_CreditorAccountType_1, String P2_CreditorRemittanceInfo_1, String P2_CreditorRefInfo_1, String P2_AdditionalRemittanceInfo_1,
                                            String P2_CreditorInstructionId_2, String P2_CreditorEndtoEndId_2, String P2_CreditorInstructedAmountCCy_2, String P2_CreditorInstructedAmount_2, String P2_CreditorBankId_2, String P2_CreditorPostalCode_2, String P2_CreditorBranchId_2, String P2_CreditorName_2, String P2_CreditorAccountNumber_2, String P2_CreditorAccountCurrency_2, String P2_CreditorAccountType_2, String P2_CreditorRemittanceInfo_2, String P2_CreditorRefInfo_2, String P2_AdditionalRemittanceInfo_2,
                                            String P2_CreditorInstructionId_3, String P2_CreditorEndtoEndId_3, String P2_CreditorInstructedAmountCCy_3, String P2_CreditorInstructedAmount_3, String P2_CreditorBankId_3, String P2_CreditorPostalCode_3, String P2_CreditorBranchId_3, String P2_CreditorName_3, String P2_CreditorAccountNumber_3, String P2_CreditorAccountCurrency_3, String P2_CreditorAccountType_3, String P2_CreditorRemittanceInfo_3, String P2_CreditorRefInfo_3, String P2_AdditionalRemittanceInfo_3,


                                            String P3_PaymentInformationIdentification, String P3_PaymentMethod, String P3_BatchBooking, String P3_NumberOfTransaction, String P3_ControlSumAtPaymentInfoLevel, String P3_CategoryPurposeCode, String P3_CodeType, String P3_RequestedExecutionDate,
                                            String P3_DebtorName, String P3_DebtorAccountId, String P3_DebtorAccountType, String P3_DebtorCurrency, String P3_DebtorBankId, String P3_DebtorPostalCode, String P3_DebtorBranchId, String P3_ChargeBearer, String P3_DebtorForcePostingOption, String P3_DebtorOriginationReference,

                                            String P3_CreditorInstructionId_1, String P3_CreditorEndtoEndId_1, String P3_CreditorInstructedAmountCCy_1, String P3_CreditorInstructedAmount_1, String P3_CreditorBankId_1, String P3_CreditorPostalCode_1, String P3_CreditorBranchId_1, String P3_CreditorName_1, String P3_CreditorAccountNumber_1, String P3_CreditorAccountCurrency_1, String P3_CreditorAccountType_1, String P3_CreditorRemittanceInfo_1, String P3_CreditorRefInfo_1, String P3_AdditionalRemittanceInfo_1,
                                            String P3_CreditorInstructionId_2, String P3_CreditorEndtoEndId_2, String P3_CreditorInstructedAmountCCy_2, String P3_CreditorInstructedAmount_2, String P3_CreditorBankId_2, String P3_CreditorPostalCode_2, String P3_CreditorBranchId_2, String P3_CreditorName_2, String P3_CreditorAccountNumber_2, String P3_CreditorAccountCurrency_2, String P3_CreditorAccountType_2, String P3_CreditorRemittanceInfo_2, String P3_CreditorRefInfo_2, String P3_AdditionalRemittanceInfo_2,
                                            String P3_CreditorInstructionId_3, String P3_CreditorEndtoEndId_3, String P3_CreditorInstructedAmountCCy_3, String P3_CreditorInstructedAmount_3, String P3_CreditorBankId_3, String P3_CreditorPostalCode_3, String P3_CreditorBranchId_3, String P3_CreditorName_3, String P3_CreditorAccountNumber_3, String P3_CreditorAccountCurrency_3, String P3_CreditorAccountType_3, String P3_CreditorRemittanceInfo_3, String P3_CreditorRefInfo_3, String P3_AdditionalRemittanceInfo_3){
        String NewPaymentInfoBlock = null;
        try{
            //create arrays
            String[] PaymentInformationIdentification={P1_PaymentInformationIdentification,P2_PaymentInformationIdentification,P3_PaymentInformationIdentification};
            String[] PaymentMethod={P1_PaymentMethod,P2_PaymentMethod,P3_PaymentMethod};
            String[] BatchBooking={P1_BatchBooking,P2_BatchBooking,P3_BatchBooking};
            String[] NumberOfTransaction={P1_NumberOfTransaction,P2_NumberOfTransaction,P3_NumberOfTransaction};
            String[] CategoryPurposeCode={P1_CategoryPurposeCode,P2_CategoryPurposeCode,P3_CategoryPurposeCode};
            String[] CodeType={P1_CodeType,P2_CodeType,P3_CodeType};
            String[] RequestedExecutionDate={P1_RequestedExecutionDate,P2_RequestedExecutionDate,P3_RequestedExecutionDate};

            String[] DebtorName={P1_DebtorName,P2_DebtorName,P3_DebtorName};
            String[] DebtorAccountId={P1_DebtorAccountId,P2_DebtorAccountId,P3_DebtorAccountId};
            String[] DebtorAccountType={P1_DebtorAccountType,P2_DebtorAccountType,P3_DebtorAccountType};
            String[] DebtorCurrency={P1_DebtorCurrency,P2_DebtorCurrency,P3_DebtorCurrency};
            String[] DebtorBankId={P1_DebtorBankId,P2_DebtorBankId,P3_DebtorBankId};
            String[] DebtorPostalCode={P1_DebtorPostalCode,P2_DebtorPostalCode,P3_DebtorPostalCode};
            String[] DebtorBranchId={P1_DebtorBranchId,P2_DebtorBranchId,P3_DebtorBranchId};
            String[] DebtorChargeBearer={P1_ChargeBearer,P2_ChargeBearer,P3_ChargeBearer};

            String[] CreditorInstructionId_1={P1_CreditorInstructionId_1,P2_CreditorInstructionId_1,P3_CreditorInstructionId_1};
            String[] CreditorEndtoEndId_1={P1_CreditorEndtoEndId_1,P2_CreditorEndtoEndId_1,P3_CreditorEndtoEndId_1};
            String[] CreditorInstructedAmountCCy_1={P1_CreditorInstructedAmountCCy_1,P2_CreditorInstructedAmountCCy_1,P3_CreditorInstructedAmountCCy_1};
            String[] CreditorInstructedAmount_1={P1_CreditorInstructedAmount_1,P2_CreditorInstructedAmount_1,P3_CreditorInstructedAmount_1};
            String[] CreditorBankId_1={P1_CreditorBankId_1,P2_CreditorBankId_1,P3_CreditorBankId_1};
            String[] CreditorPostalCode_1={P1_CreditorPostalCode_1,P2_CreditorPostalCode_1,P3_CreditorPostalCode_1};
            String[] CreditorBranchId_1={P1_CreditorBranchId_1,P2_CreditorBranchId_1,P3_CreditorBranchId_1};
            String[] CreditorName_1={P1_CreditorName_1,P2_CreditorName_1,P3_CreditorName_1};
            String[] CreditorAccountNumber_1={P1_CreditorAccountNumber_1,P2_CreditorAccountNumber_1,P3_CreditorAccountNumber_1};
            String[] CreditorAccountCurrency_1={P1_CreditorAccountCurrency_1,P2_CreditorAccountCurrency_1,P3_CreditorAccountCurrency_1};
            String[] CreditorAccountType_1={P1_CreditorAccountType_1,P2_CreditorAccountType_1,P3_CreditorAccountType_1};
            String[] CreditorRemittanceInfo_1={P1_CreditorRemittanceInfo_1,P2_CreditorRemittanceInfo_1,P3_CreditorRemittanceInfo_1};
            String[] CreditorRefInfo_1={P1_CreditorRefInfo_1,P2_CreditorRefInfo_1,P3_CreditorRefInfo_1};
            String[] AdditionalRemittanceInfo_1={P1_AdditionalRemittanceInfo_1,P2_AdditionalRemittanceInfo_1,P3_AdditionalRemittanceInfo_1};

            System.out.println("Inside identifyNoOfPaymentBlocks");
            NewPaymentInfoBlock= "";

                for(int i =0; i<noOfBlocks ;i++){
                    String PaymentBlock=createPaymentBlock(PaymentInformationIdentification[i],PaymentMethod[i],BatchBooking[i],NumberOfTransaction[i],
                            CategoryPurposeCode[i],CodeType[i],RequestedExecutionDate[i],
                            DebtorName[i],DebtorAccountId[i],DebtorAccountType[i],DebtorCurrency[i], DebtorBankId[i],DebtorPostalCode[i],DebtorBranchId[i],DebtorChargeBearer[i],
                            CreditorInstructionId_1[i],CreditorEndtoEndId_1[i],CreditorInstructedAmountCCy_1[i],CreditorInstructedAmount_1[i],CreditorBankId_1[i],
                            CreditorPostalCode_1[i],CreditorBranchId_1[i],CreditorName_1[i],CreditorAccountNumber_1[i],CreditorAccountCurrency_1[i],CreditorAccountType_1[i],
                            CreditorRemittanceInfo_1[i],CreditorRefInfo_1[i],AdditionalRemittanceInfo_1[i]);

                    NewPaymentInfoBlock=NewPaymentInfoBlock+PaymentBlock;
                }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return NewPaymentInfoBlock;
    }

    public static String setTagValue(String tag){
        try{

            //1. if tag value is blank, send back blank
            if(tag.isEmpty()){
                //return same value
            }

//            //2. if tag value is not AUTO or FORMAT, send back the value as it is
//            else if(!tag.startsWith("AUTO") && !tag.startsWith("FORMAT") && !tag.startsWith("DATETIME") && !tag.startsWith("DATE")){
//                //return same value
//            }

            //3. if tag value is 'AUTO,n' , write code to create random n digits long random text
            else if(tag.startsWith("AUTO")){
                String[] parts =tag.split(",");
                int length = Integer.parseInt(parts[1]);
                String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
                StringBuilder allowedCharactersUniqueTxt = new StringBuilder();
                Random rnd = new Random();
                while (allowedCharactersUniqueTxt.length() < length) {
                    int index = (int) (rnd.nextFloat() * allowedCharacters.length());
                    allowedCharactersUniqueTxt.append(allowedCharacters.charAt(index));
                }
                tag = allowedCharactersUniqueTxt.toString();

            }

            //4. if tag is 'FORMAT,format' write code create own format
            else if(tag.startsWith("FORMAT")){
                String[] parts =tag.split(",");

                Random randomObj = new Random();
                int randomNumber = randomObj.nextInt(100000);

                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                Date now = new Date();
                String strDate = date.format(now);

                parts[1]=parts[1].replace("DATE",strDate);
                parts[1]=parts[1].replace("SERIES",Integer.toString(randomNumber));

                tag=parts[1];

            }
            else if(tag.endsWith("FORMAT")){

                String[] parts =tag.split(",");

                Random randomObj = new Random();
                int randomNumber = randomObj.nextInt(100000);

                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                Date now = new Date();
                String strDate = date.format(now);

                parts[0]=parts[0].replace("DATE",strDate);
                parts[0]=parts[0].replace("SERIES",Integer.toString(randomNumber));

                tag=parts[0];
            }
            //5. if tag is 'DATETIME' call method with date time format
            else if(tag.startsWith("DATETIME")){
                tag = getDateFormatForSettlementNotification()+"T00:00:00";
                tag = formattingInputDate(tag,"yyyy-MM-dd'T'HH:mm:ss");
            }

            //6. if tag is 'DATE' call method with date format
            else if(tag.startsWith("DATE")){
                tag = getDateFormatForSettlementNotification();
            }

            //7. default
            else{
              //  System.out.println("in default");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return tag;


    }

    public void createXMLfile(String myfile) throws IOException {
        try{
            Writer fileWriter = new FileWriter(currentDir+"\\testOutputs\\multidebitfile\\test.xml", false); //overwrites file
            fileWriter.write(myfile);
            fileWriter.flush();
            fileWriter.close();
         }
        catch(Exception e){
        ExtentTestManager.getTest().log(Status.FAIL, "test fail "+e);
        }
    }

    public String[] findTagValueInPainMessage(String painMessage,String painMessageTagName){
        String painMessageTagNameArr[] = new String[2];
        //Extract tag values from Pain001 message for further processing
        if(painMessage.contains("ns2:")) {
            painMessageTagNameArr[0] = "<ns2:" + painMessageTagName + ">";
            painMessageTagNameArr[1] = "</ns2:" + painMessageTagName + ">";
        }else {
            painMessageTagNameArr[0] = "<" + painMessageTagName + ">";
            painMessageTagNameArr[1] = "</" + painMessageTagName + ">";
        }
        Map<String, Stack<Integer>> painMessageTagLocationMap = findTagsWithValueInPaymentMessage(painMessageTagNameArr, painMessage);

        String []painMessageTagValueArr = new String[painMessageTagLocationMap.get(painMessageTagNameArr[0]).size()];
        for(int i =0;i<painMessageTagLocationMap.get(painMessageTagNameArr[0]).size();i++){
            painMessageTagValueArr[i] = painMessage.substring(painMessageTagLocationMap.get(painMessageTagNameArr[0]).get(i) + (painMessageTagNameArr[0]).length(),
                    painMessageTagLocationMap.get(painMessageTagNameArr[1]).get(i) );
        }
        return painMessageTagValueArr;
    }

    public Map<String, Stack<Integer>> findTagsWithValueInPaymentMessage(String[] messageTags, String painMessage) {
        Map<String, Stack<Integer>> dest = new HashMap<>();

        for (String tag : messageTags) {
            Stack<Integer> locations = new Stack<>();

            for (int loc = -1; (loc = painMessage.indexOf(tag, loc + 1)) != -1;) {
                locations.add(loc);
            }

            dest.put(tag, locations);
        }

        return dest;
    }
}
