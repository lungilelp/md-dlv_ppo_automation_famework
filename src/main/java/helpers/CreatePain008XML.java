package helpers;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import utils.logs.Log;

public class CreatePain008XML extends BasePage {
    public CreatePain008XML(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    String Pain008Message = null;

    //Message Group header formation
    public String createPain008GroupHeader(String MessageIdentification, String CreationDateTime,
                                           String NumberOfTransactions,String ControlSumAtMsgLevel,
                                           String name,String CountryOfResidence)
    {
        Pain008Message =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.008.001.04\">\n" +
                "    <CstmrDrctDbtInitn>\n" +
                "        <GrpHdr>\n" +
                "            <MsgId>" + MessageIdentification + "</MsgId>\n" +
                "            <CreDtTm>" + CreationDateTime + "</CreDtTm>\n" +
                "            <NbOfTxs>" + NumberOfTransactions + "</NbOfTxs>\n" +
                "            <CtrlSum>" + ControlSumAtMsgLevel + " </CtrlSum>\n" +
                "            <InitgPty>\n" +
                "                <Nm>" + name + "</Nm>\n" +
                "                <CtryOfRes>" + CountryOfResidence +"</CtryOfRes>\n" +
                "            </InitgPty>\n" +
                "        </GrpHdr>\n";

        return Pain008Message;
    }

    //Payment information section formation
    public String createPain008CreditorPaymentInformation(String pain008Message, String PaymentInformationIdentification, String PaymentMethod,
                                                          String BatchBooking, String RequestedCollectionDate, String CreditorName,
                                                          String CreditorPhoneNo, String CreditorAccountId, String CreditorBankId,
                                                          String CreditorPostalCode, String CreditorBranchId){

        String processingOption = (BatchBooking.equalsIgnoreCase("itemised") ? "false" : "true");
        String creditorInformation = "        <PmtInf>\n" +
                "            <PmtInfId>" + PaymentInformationIdentification + "</PmtInfId>\n" +
                "            <PmtMtd>" + PaymentMethod + "</PmtMtd>\n" +
                "            <BtchBookg>" +processingOption + "</BtchBookg>\n" +
                "            <ReqdColltnDt>" + RequestedCollectionDate + "</ReqdColltnDt>\n" +
                "            <Cdtr>\n" +
                "                <Nm>" + CreditorName +"</Nm>\n" +
                "                <CtctDtls>\n" +
                "                    <PhneNb>" + CreditorPhoneNo + "</PhneNb>\n" +
                "                </CtctDtls>\n" +
                "            </Cdtr>\n" +
                "            <CdtrAcct>\n" +
                "                <Id>\n" +
                "                    <Othr>\n" +
                "                        <Id>" + CreditorAccountId + "</Id>\n" +
                "                    </Othr>\n" +
                "                </Id>\n" +
                "            </CdtrAcct>\n" +
                "            <CdtrAgt>\n" +
                "                <FinInstnId>\n" +
                "                    <ClrSysMmbId>\n" +
                "                        <MmbId>" + CreditorBankId +"</MmbId>\n" +
                "                    </ClrSysMmbId>\n" +
                "                    <PstlAdr>\n" +
                "                        <PstCd>" + CreditorPostalCode +"</PstCd>\n" +
                "                    </PstlAdr>\n" +
                "                </FinInstnId>\n" +
                "                <BrnchId>\n" +
                "                    <Id>" + CreditorBranchId + "</Id>\n" +
                "                </BrnchId>\n" +
                "            </CdtrAgt>\n";
        Pain008Message = pain008Message.concat(creditorInformation);
        return Pain008Message;
    }
    public String createPain008DirectDebitTxnInfo(String pain008Message,String DebtorEndtoEndId, String PaymentTypeInfoServiceLevelPrtry,String PaymentTypeInfoLocalInstrumentPrtry,
                                                  String PaymentTypeInfoSequenceType, String PaymentTypeInfoCategoryPurposeCodePrtry, String InstructedAmount,
                                                  String InstructedCurrency, String ChargeBearer, String MandateId, String DebtorBankId,String DebtorPostalCode,
                                                  String DebtorBranchId,String DebtorName, String DebtorAccountId, String DebtorAccountTypePrtry, String DebtorRemittanceInfo, 	String DebtorRelatedDate
    ){
        String directDebitTransactionInfo = "            <DrctDbtTxInf>\n" +
                "                <PmtId>\n" +
                "                    <EndToEndId>" + DebtorEndtoEndId + "</EndToEndId>\n" +
                "                </PmtId>\n" +
                "                <PmtTpInf>\n" +
                "                    <SvcLvl>\n" +
                "                        <Prtry>" + PaymentTypeInfoServiceLevelPrtry + "</Prtry>\n" +
                "                    </SvcLvl>\n" +
                "                    <LclInstrm>\n" +
                "                        <Prtry>" + PaymentTypeInfoLocalInstrumentPrtry + "</Prtry>\n" +
                "                    </LclInstrm>\n" +
                "                    <SeqTp>" + PaymentTypeInfoSequenceType + "</SeqTp>\n" +
                "                    <CtgyPurp>\n" +
                "                        <Prtry>" + PaymentTypeInfoCategoryPurposeCodePrtry + "</Prtry>\n" +
                "                    </CtgyPurp>\n" +
                "                </PmtTpInf>\n" +
                "                <InstdAmt Ccy=\""+ InstructedCurrency + "\">"+ InstructedAmount +"</InstdAmt>\n" +
                "                <ChrgBr>" + ChargeBearer + "</ChrgBr>\n" +
                "                <DrctDbtTx>\n" +
                "                    <MndtRltdInf>\n" +
                "                        <MndtId>" + MandateId +"</MndtId>\n" +
                "                    </MndtRltdInf>\n" +
                "                </DrctDbtTx>\n" +
                "                <DbtrAgt>\n" +
                "                    <FinInstnId>\n" +
                "                        <ClrSysMmbId>\n" +
                "                            <MmbId>" + DebtorBankId + "</MmbId>\n" +
                "                        </ClrSysMmbId>\n" +
                "                        <PstlAdr>\n" +
                "                            <PstCd>" + DebtorPostalCode + "</PstCd>\n" +
                "                        </PstlAdr>\n" +
                "                    </FinInstnId>\n" +
                "                    <BrnchId>\n" +
                "                        <Id>" + DebtorBranchId + "</Id>\n" +
                "                    </BrnchId>\n" +
                "                </DbtrAgt>\n" +
                "                <Dbtr>\n" +
                "                    <Nm>" + DebtorName +"</Nm>\n" +
                "                </Dbtr>\n" +
                "                <DbtrAcct>\n" +
                "                    <Id>\n" +
                "                        <Othr>\n" +
                "                            <Id>" + DebtorAccountId + "</Id>\n" +
                "                        </Othr>\n" +
                "                    </Id>\n" +
                "                    <Tp>\n" +
                "                        <Prtry>" + DebtorAccountTypePrtry + "</Prtry>\n" +
                "                    </Tp>\n" +
                "                </DbtrAcct>\n" +
                "                <RmtInf>\n" +
                "                    <Ustrd>" + DebtorRemittanceInfo + "</Ustrd>\n" +
                "                    <Strd>\n" +
                "                        <RfrdDocInf>\n" +
                "                            <RltdDt>" + DebtorRelatedDate + "</RltdDt>\n" +
                "                        </RfrdDocInf>\n" +
                "                    </Strd>\n" +
                "                </RmtInf>\n" +
                "            </DrctDbtTxInf>\n";

        Pain008Message = pain008Message.concat(directDebitTransactionInfo);
        return Pain008Message;
    }

    public String createPain008FooterInformation(String pain008Message){
        String pain008MsgFooter = "        </PmtInf>\n" +
                "    </CstmrDrctDbtInitn>\n" +
                "</Document>";

        Pain008Message = Pain008Message.concat(pain008MsgFooter);
        return Pain008Message;
    }
}
