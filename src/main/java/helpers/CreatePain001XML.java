package helpers;


import base.BasePage;
import org.openqa.selenium.WebDriver;
import utils.logs.Log;

public class CreatePain001XML extends BasePage {
    public CreatePain001XML(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }
    String Pain001Message = null;

    //Message Group header formation
    public String createPain001GroupHeader(String MessageIdentification, String CreationDateTime,
                                           String NumberOfTransactions,String ControlSumAtMsgLevel,
                                           String name,String CountryOfResidence)
    {
        Pain001Message = "<ns2:Document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">\n" +
                "   <ns2:CstmrCdtTrfInitn>\n" +
                "       <ns2:GrpHdr>\n" +
                "           <ns2:MsgId>" + MessageIdentification  + "</ns2:MsgId>\n" +
                "           <ns2:CreDtTm>" + CreationDateTime + "</ns2:CreDtTm>\n" +
                "           <ns2:NbOfTxs>"+ NumberOfTransactions + "</ns2:NbOfTxs>\n" +
                "           <ns2:CtrlSum>" + ControlSumAtMsgLevel + "</ns2:CtrlSum>\n" +
                "           <ns2:InitgPty>\n" +
                "               <ns2:Nm>" + name +"</ns2:Nm>\n" +
                "               <ns2:CtryOfRes>"+CountryOfResidence+"</ns2:CtryOfRes>\n" +
                "           </ns2:InitgPty>\n" +
                "   </ns2:GrpHdr>\n";

        return Pain001Message;
    }

    //Payment information section formation
    public String createPain001PaymentInformation( String name, String pain001Message, String PaymentInformationIdentification,String PaymentMethod,
                                                   String BatchBooking ,String NumberOfTransaction,String ControlSum,
                                                   String CategoryPurposeCode,String CodeType,String RequestedExecutionDate,String DebtorName,
                                                   String DebtorAccountId,String DebtorCurrency,String DebtorBankId,String DebtorPostalCode,
                                                   String DebtorBranchId,String ChargeBearer,String DebtorForcePostingOption){

        String processingOption = (BatchBooking.equalsIgnoreCase("itemised") ? "false" : "true");
        String paymentInformation = "  <ns2:PmtInf>\n" +
                "      <ns2:PmtInfId>"+ PaymentInformationIdentification +"</ns2:PmtInfId>\n" +
                "      <ns2:PmtMtd>"+ PaymentMethod +"</ns2:PmtMtd>\n" +
                "      <ns2:BtchBookg>" + processingOption +"</ns2:BtchBookg>\n" +
                "      <ns2:NbOfTxs>" + NumberOfTransaction + "</ns2:NbOfTxs>\n" +
                "      <ns2:CtrlSum>" + ControlSum + "</ns2:CtrlSum>\n" +
                "      <ns2:PmtTpInf>\n" +
                "        <ns2:CtgyPurp>\n" ;
        if (CodeType.equalsIgnoreCase("Code")) {
            paymentInformation = paymentInformation +
                    "          <ns2:Cd>" + CategoryPurposeCode + "</ns2:Cd>\n";
        } else {
            paymentInformation = paymentInformation +
                    "          <ns2:Prtry>" + CategoryPurposeCode + "</ns2:Prtry>\n";
        }
        paymentInformation = paymentInformation + "        </ns2:CtgyPurp>\n" +
                "      </ns2:PmtTpInf>\n" +
                "      <ns2:ReqdExctnDt>" + RequestedExecutionDate + "</ns2:ReqdExctnDt>\n" +
                "      <ns2:Dbtr>\n" +
                "        <ns2:Nm>" + DebtorName + "</ns2:Nm>\n" ;

        if(name.equals("SAP")) {
            paymentInformation = paymentInformation +
                "      <ns2:PstlAdr>\n" +
                "           <ns2:AdrLine>Address Line Postal address</ns2:AdrLine>\n" +
                "      </ns2:PstlAdr>\n";
        }
        paymentInformation = paymentInformation +
                "      </ns2:Dbtr>\n" +
                "      <ns2:DbtrAcct>\n" +
                "        <ns2:Id>\n" +
                "          <ns2:Othr>\n" +
                "            <ns2:Id>" + DebtorAccountId + "</ns2:Id>\n" +
                "          </ns2:Othr>\n" +
                "        </ns2:Id>\n" +
                "        <ns2:Ccy>" + DebtorCurrency + "</ns2:Ccy>\n" +
                "      </ns2:DbtrAcct>\n" +
                "      <ns2:DbtrAgt>\n" +
                "        <ns2:FinInstnId>\n" +
                "          <ns2:ClrSysMmbId>\n" +
                "            <ns2:MmbId>" + DebtorBankId + "</ns2:MmbId>\n" +
                "          </ns2:ClrSysMmbId>\n" +
                "          <ns2:PstlAdr>\n" +
                "            <ns2:PstCd>" + DebtorPostalCode + "</ns2:PstCd>\n" +
                "          </ns2:PstlAdr>\n" +
                "        </ns2:FinInstnId>\n" +
                "        <ns2:BrnchId>\n" +
                "          <ns2:Id>" + DebtorBranchId + "</ns2:Id>\n" +
                "        </ns2:BrnchId>\n" +
                "      </ns2:DbtrAgt>\n";
        if(!ChargeBearer.equals("")) {
            paymentInformation = paymentInformation + "      <ns2:ChrgBr>" + ChargeBearer + "</ns2:ChrgBr>\n";
        };
        if(DebtorForcePostingOption == "YES"){
            paymentInformation = paymentInformation + "      <ns2:InstrForDbtrAgt>FORCE</ns2:InstrForDbtrAgt>";
        }

        Pain001Message = pain001Message.concat(paymentInformation);
        return Pain001Message;
    }
    public String createPain001CreditTransferTxnInfo(String pain001Message, String name,String CreditorEndtoEndId,String CreditorInstructedAmountCCy,String CreditorInstructedAmount,String CreditorBankId,
                                                     String CreditorPostalCode,String CreditorBranchId,	String CreditorName,	String CreditorAccountNumber,String CreditorAccountCurrency,
                                                     String CreditorOriginationRef,String CreditorDestinationRef, String CreditorUserId,	String CreditorAuthorizerId,
                                                     String CreditorSystemId, String CreditorBusinessId, String CreditorFundsTransferType,	String CreditorFundsTransferChildType,
                                                     String CreditorCustomerNumber,String CreditorEntityCIF,	String CreditorListName,	String CreditorBankName,	String CreditorBranchName){
        String creditorTransferTransactionInfo = "      <ns2:CdtTrfTxInf>\n" +
                "        <ns2:PmtId>\n" ;
        if(name.equals("SAP")) {
            creditorTransferTransactionInfo = creditorTransferTransactionInfo +
                "        <ns2:InstrId>InstructionId</ns2:InstrId>\n";
        };

        creditorTransferTransactionInfo=   creditorTransferTransactionInfo+
                "        <ns2:EndToEndId>" + CreditorEndtoEndId + "</ns2:EndToEndId>\n" +
                "        </ns2:PmtId>\n" +
                "        <ns2:Amt>\n" +
                "          <ns2:InstdAmt Ccy=\""+ CreditorInstructedAmountCCy + "\">"+ CreditorInstructedAmount +"</ns2:InstdAmt>\n" +
                "        </ns2:Amt>\n" +
                "        <ns2:CdtrAgt>\n" +
                "          <ns2:FinInstnId>\n" +
                "            <ns2:ClrSysMmbId>\n" +
                "              <ns2:MmbId>" + CreditorBankId + "</ns2:MmbId>\n" +
                "            </ns2:ClrSysMmbId>\n" +
                "            <ns2:PstlAdr>\n" +
                "              <ns2:PstCd>" + CreditorPostalCode + "</ns2:PstCd>\n" +
                "            </ns2:PstlAdr>\n" +
                "          </ns2:FinInstnId>\n" +
                "          <ns2:BrnchId>\n" +
                "            <ns2:Id>" + CreditorBranchId + "</ns2:Id>\n" +
                "          </ns2:BrnchId>\n" +
                "        </ns2:CdtrAgt>\n" +
                "        <ns2:Cdtr>\n" +
                "          <ns2:Nm>" + CreditorName +  "</ns2:Nm>\n" +
                "        </ns2:Cdtr>\n" +
                "        <ns2:CdtrAcct>\n" +
                "          <ns2:Id>\n" +
                "            <ns2:Othr>\n" +
                "              <ns2:Id>" + CreditorAccountNumber + "</ns2:Id>\n" +
                "            </ns2:Othr>\n" +
                "          </ns2:Id>\n" +
                "          <ns2:Ccy>" + CreditorAccountCurrency + "</ns2:Ccy>\n" +
                "        </ns2:CdtrAcct>\n" +
                "        <ns2:RmtInf>\n" +
                "          <ns2:Ustrd>" + CreditorOriginationRef + "</ns2:Ustrd>\n" +
                "          <ns2:Strd>\n" +
                "            <ns2:CdtrRefInf>\n" +
                "              <ns2:Ref>" + CreditorDestinationRef + "</ns2:Ref>\n" +
                "            </ns2:CdtrRefInf>\n" +
                "          </ns2:Strd>\n" +
                "        </ns2:RmtInf>\n" ;

        if((!name.equalsIgnoreCase("SAP"))&&(!name.equalsIgnoreCase("AAO"))
                &&(!name.equalsIgnoreCase("AAH"))) {
            creditorTransferTransactionInfo = creditorTransferTransactionInfo +
                    "       <ns2:SplmtryData>\n" +
                    "          <ns2:Envlp>\n" +
                    "            <ns2:Identification>\n" +
                    "              <ns2:UserId>" + CreditorUserId + "</ns2:UserId>\n" +
                    "              <ns2:AuthorizerId>" + CreditorAuthorizerId + "</ns2:AuthorizerId>\n" +
                    "              <ns2:SystemId>" + CreditorSystemId + "</ns2:SystemId>\n" +
                    "              <ns2:BusinessId>" + CreditorBusinessId + "</ns2:BusinessId>\n" +
                    "              <ns2:FundsTransferType>" + CreditorFundsTransferType + "</ns2:FundsTransferType>\n" +
                    "              <ns2:FundsTransferChildType>" + CreditorFundsTransferChildType + "</ns2:FundsTransferChildType>\n" +
                    "              <ns2:CustomerNumber>" + CreditorCustomerNumber + "</ns2:CustomerNumber>\n" +
                    "              <ns2:EntityCIF>" + CreditorEntityCIF + "</ns2:EntityCIF>\n" +
                    "              <ns2:ListName>" + CreditorListName + "</ns2:ListName>\n" +
                    "              <ns2:BankName>" + CreditorBankName + "</ns2:BankName>\n" +
                    "              <ns2:BranchName>" + CreditorBranchName + "</ns2:BranchName>\n" +
                    "            </ns2:Identification>\n" +
                    "         </ns2:Envlp>\n" +
                    "        </ns2:SplmtryData>\n" ;
        }


        if((!name.equalsIgnoreCase("AAO"))&&(!name.equalsIgnoreCase("AAH"))){
            creditorTransferTransactionInfo = creditorTransferTransactionInfo +"      </ns2:CdtTrfTxInf>\n";
        }


        Pain001Message = pain001Message.concat(creditorTransferTransactionInfo);
        return Pain001Message;
    }

    public String createPain001FooterInformation(String pain001Message){
        String pain001MsgFooter = "    </ns2:PmtInf>\n" +
                "  </ns2:CstmrCdtTrfInitn>\n" +
                "</ns2:Document>";

        Pain001Message = pain001Message.concat(pain001MsgFooter);
        return Pain001Message;
    }

    public String createEboxPain001Message(String MessageId,String strWorkflowReferenceUniqueTxt,String NoOfTransactions,
                                           String ControlSum,String OriginationChannel,String PaymentInfoId,String PaymentMethod,
                                           String ServiceLevel,String OriginatorName,String OriginatorId,String OriginatorAccountNo,
                                           String OrgFinancialInstitution,String OriginatorBranchId,String TransactionId,String Amount,
                                           String Currency,String DestFinancialInstitution,String DestinationBrnchId,String DestinationName,
                                           String DestinationAccountNumber,String PurpOfPay,String AddRmtInf){
        String eboxPain001 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.05\">\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <GrpHdr>\n" +
                "            <MsgId>" + MessageId + strWorkflowReferenceUniqueTxt + "</MsgId>\n" +
                "            <CreDtTm>" + getDateFormatForSettlementNotification() + "T00:00:00</CreDtTm>\n" +
                "            <NbOfTxs>" + NoOfTransactions + "</NbOfTxs>\n" +
                "            <CtrlSum>" + ControlSum + "</CtrlSum>\n" +
                "            <InitgPty>\n" +
                "                <Nm>" + OriginationChannel + "</Nm>\n" +
                "            </InitgPty>\n" +
                "        </GrpHdr>\n" +
                "        <PmtInf>\n" +
                "            <PmtInfId>" + PaymentInfoId + strWorkflowReferenceUniqueTxt + "</PmtInfId>\n" +
                "            <PmtMtd>" + PaymentMethod + "</PmtMtd>\n" +
                "            <PmtTpInf>\n" +
                "                <SvcLvl>\n" +
                "                    <Prtry>" + ServiceLevel + "</Prtry>\n" +
                "                </SvcLvl>\n" +
                "            </PmtTpInf>\n" +
                "            <ReqdExctnDt>" + getDateFormatForSettlementNotification() + "</ReqdExctnDt>\n" +
                "            <Dbtr>\n" +
                "                <Nm>" + OriginatorName + "</Nm>\n" +
                "                <Id>\n" +
                "                    <OrgId>\n" +
                "                        <Othr>\n" +
                "                            <Id>" + OriginatorId + strWorkflowReferenceUniqueTxt + "</Id>\n" +
                "                        </Othr>\n" +
                "                    </OrgId>\n" +
                "                </Id>\n" +
                "            </Dbtr>\n" +
                "            <DbtrAcct>\n" +
                "                <Id>\n" +
                "                    <Othr>\n" +
                "                        <Id>" + OriginatorAccountNo + "</Id>\n" +
                "                    </Othr>\n" +
                "                </Id>\n" +
                "            </DbtrAcct>\n" +
                "            <DbtrAgt>\n" +
                "                <FinInstnId>\n" +
                "                    <Nm>" + OrgFinancialInstitution + "</Nm>\n" +
                "                </FinInstnId>\n" +
                "                <BrnchId>\n" +
                "                    <Id>" + OriginatorBranchId + "</Id>\n" +
                "                </BrnchId>\n" +
                "            </DbtrAgt>\n" +
                "            <CdtTrfTxInf>\n" +
                "                <PmtId>\n" +
                "                    <EndToEndId>" + TransactionId + strWorkflowReferenceUniqueTxt + "</EndToEndId>\n" +
                "                </PmtId>\n" +
                "                <Amt>\n" +
                "                    <InstdAmt Ccy=\"" + Currency + "\">" + Amount + "</InstdAmt>\n" +
                "                </Amt>\n" +
                "                <CdtrAgt>\n" +
                "                    <FinInstnId>\n" +
                "                        <Nm>" + DestFinancialInstitution + "</Nm>\n" +
                "                    </FinInstnId>\n" +
                "                    <BrnchId>\n" +
                "                        <Id>" + DestinationBrnchId + "</Id>\n" +
                "                    </BrnchId>\n" +
                "                </CdtrAgt>\n" +
                "                <Cdtr>\n" +
                "                    <Nm>" + DestinationName + "</Nm>\n" +
                "                </Cdtr>\n" +
                "                <CdtrAcct>\n" +
                "                    <Id>\n" +
                "                        <Othr>\n" +
                "                            <Id>" + DestinationAccountNumber + "</Id>\n" +
                "                        </Othr>\n" +
                "                    </Id>\n" +
                "                </CdtrAcct>\n" +
                "                <Purp>\n" +
                "                    <Prtry>" + PurpOfPay + "</Prtry>\n" +
                "                </Purp>\n" +
                "                <RltdRmtInf>\n" +
                "                <RmtLctnElctrncAdr>"+AddRmtInf+"</RmtLctnElctrncAdr>\n" +
                "                </RltdRmtInf>\n" +
                "                <RmtInf>\n" +
                "                    <Ustrd>" + AddRmtInf + "</Ustrd>\n" +
                "                </RmtInf>\n" +
                "            </CdtTrfTxInf>\n" +
                "        </PmtInf>\n" +
                "    </CstmrCdtTrfInitn>\n" +
                "</Document>\n";

        return eboxPain001;
    }

    /*

    public String createInverseRatePain001GrpHrd(String MsgId, String CreDtTm, String CtrlSum, String InitgPty){
        String Pain001Message="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.001.001.10\">\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <GrpHdr>\n" +
                "            <MsgId>"+MsgId+"</MsgId>\n" +
                "            <CreDtTm>"+CreDtTm+"</CreDtTm>\n" +
                "            <NbOfTxs>1</NbOfTxs>\n" +
                "            <CtrlSum>"+CtrlSum+"</CtrlSum>\n" +
                "            <InitgPty>\n" +
                "                <Nm>"+InitgPty+"</Nm>\n" +
                "            </InitgPty>\n" +
                "        </GrpHdr>\n";

        return Pain001Message;
    };

    public String createInverseRatePain001DebtorSection(String pain001Message,String PmtInfId, String PmtMtd, String BtchBookg, String CtrlSum, String SvcLvlCd,
                                                        String LclInstrmCd, String ReqdExctnDt, String DbtrNm, String PstlAdrAdrTpCd,
                                                        String DbtrAcctIdOthrId, String DbtrAcctIdOthrCcy, String DbtrAgtFinInstnIdBICFI,
                                                        String DbtrAgtFinInstnIdMmbId, String DbtrAgtFinInstnIdPstCd, String DbtrAgtFinInstnIdBrnchId,
                                                        String UltmtDbtrAdrTpCd){
        String inverseRatePain001DebtorSection="<PmtInf>\n" +
                "            <PmtInfId>"+PmtInfId+"</PmtInfId>\n" +
                "            <PmtMtd>"+PmtMtd+"</PmtMtd>\n" +
                "            <BtchBookg>"+BtchBookg+"</BtchBookg>\n" +
                "            <NbOfTxs>1</NbOfTxs>\n" +
                "            <CtrlSum>"+CtrlSum+"</CtrlSum>\n" +
                "            <PmtTpInf>\n" +
                "                <SvcLvl>\n" +
                "                    <Cd>"+SvcLvlCd+"</Cd>\n" +
                "                </SvcLvl>\n" +
                "                <LclInstrm>\n" +
                "                    <Cd>"+LclInstrmCd+"</Cd>\n" +
                "                </LclInstrm>\n" +
                "            </PmtTpInf>\n" +
                "            <ReqdExctnDt>\n" +
                "                <Dt>"+ReqdExctnDt+"</Dt>\n" +
                "            </ReqdExctnDt>\n" +
                "            <Dbtr>\n" +
                "                <Nm>"+DbtrNm+"</Nm>\n" +
                "                <PstlAdr>\n" +
                "                    <AdrTp>\n" +
                "                        <Cd>"+PstlAdrAdrTpCd+"</Cd>\n" +
                "                    </AdrTp>\n" +
                "                </PstlAdr>\n" +
                "            </Dbtr>\n" +
                "            <DbtrAcct>\n" +
                "                <Id>\n" +
                "                    <Othr><!-- Baps GL Account -->\n" +
                "                        <Id>"+DbtrAcctIdOthrId+"</Id>\n" +
                "                    </Othr>\n" +
                "                </Id>\n" +
                "                <Ccy>"+DbtrAcctIdOthrCcy+"</Ccy>\n" +
                "            </DbtrAcct>\n" +
                "            <DbtrAgt>\n" +
                "                <FinInstnId>\n" +
                "                    <BICFI>"+DbtrAgtFinInstnIdBICFI+"</BICFI>\n" +
                "                    <ClrSysMmbId>\n" +
                "                        <MmbId>"+DbtrAgtFinInstnIdMmbId+"</MmbId>\n" +
                "                    </ClrSysMmbId>\n" +
                "                    <PstlAdr>\n" +
                "                        <PstCd>"+DbtrAgtFinInstnIdPstCd+"</PstCd>\n" +
                "                    </PstlAdr>\n" +
                "                </FinInstnId>\n" +
                "                <BrnchId>\n" +
                "                    <Id>"+DbtrAgtFinInstnIdBrnchId+"</Id>\n" +
                "                </BrnchId>\n" +
                "            </DbtrAgt>\n" +
                "            <UltmtDbtr>\n" +
                "                <PstlAdr>\n" +
                "                    <AdrTp>\n" +
                "                        <Cd>"+UltmtDbtrAdrTpCd+"</Cd>\n" +
                "                    </AdrTp>\n" +
                "                </PstlAdr>\n" +
                "            </UltmtDbtr>\n";

        Pain001Message = pain001Message.concat(inverseRatePain001DebtorSection);
        return Pain001Message;

    };

    public String createInverseRatePain001CreditorSection(String pain001Message,String InstrId, String EndToEndId, String SvcLvlCd, String LclInstrmCd, String CtgyPurpCd,
                                                          String InstdAmtCCy, String InstdAmt, String ChrgBr, String UltmtDbtrAdrTpCd, String CdtrAgtFinInstnIdBICFI,
                                                          String CdtrAgtFinInstnIdNm, String CdtrAgtFinInstnIdPstlAdrCtry, String CdtrAgtFinInstnIdPstlAdrAdrLine,
                                                          String CdtrAgtCdtrNm, String CdtrAgtCdtrPstlAdrStrtNm, String CdtrAgtCdtrPstlAdrCtry, String CdtrAgtCdtrPstlAdrAdrLine,
                                                          String CdtrAgtCdtrPstlAdrAdrLine1, String CdtrCtryOfRes, String CdtrAcctId, String CdtrAcctCcy, String RgltryRptgDtlsInf,
                                                          String RmtInfUstrd, String RmtInfStrdCdtrRefInf){
        String inverseRatePain001CreditorSection="<CdtTrfTxInf>\n" +
                "                <PmtId>\n" +
                "                    <InstrId>"+InstrId+"</InstrId>\n" +
                "                    <EndToEndId>"+EndToEndId+"</EndToEndId>\n" +
                "                </PmtId>\n" +
                "                <PmtTpInf>\n" +
                "                    <SvcLvl>\n" +
                "                        <Cd>"+SvcLvlCd+"</Cd>\n" +
                "                    </SvcLvl>\n" +
                "                    <LclInstrm>\n" +
                "                        <Cd>"+LclInstrmCd+"</Cd>\n" +
                "                    </LclInstrm>\n" +
                "                    <CtgyPurp>\n" +
                "                        <Cd>"+CtgyPurpCd+"</Cd>\n" +
                "                    </CtgyPurp>\n" +
                "                </PmtTpInf>\n" +
                "                <Amt>\n" +
                "                    <InstdAmt Ccy=\""+InstdAmtCCy+"\">"+InstdAmt+"</InstdAmt><!-- converted amount coming from Baps-->\n" +
                "                </Amt>\n" +
                "                <ChrgBr>"+ChrgBr+"</ChrgBr>\n" +
                "                <UltmtDbtr>\n" +
                "                    <PstlAdr>\n" +
                "                        <AdrTp>\n" +
                "                            <Cd>"+UltmtDbtrAdrTpCd+"</Cd>\n" +
                "                        </AdrTp>\n" +
                "                    </PstlAdr>\n" +
                "                </UltmtDbtr>\n" +
                "                <CdtrAgt>\n" +
                "                    <FinInstnId>\n" +
                "                        <BICFI>"+CdtrAgtFinInstnIdBICFI+"</BICFI>\n" +
                "                        <Nm>"+CdtrAgtFinInstnIdNm+"</Nm>\n" +
                "                        <PstlAdr>\n" +
                "                            <Ctry>"+CdtrAgtFinInstnIdPstlAdrCtry+"</Ctry>\n" +
                "                            <AdrLine>"+CdtrAgtFinInstnIdPstlAdrAdrLine+"</AdrLine>\n" +
                "                        </PstlAdr>\n" +
                "                    </FinInstnId>\n" +
                "                </CdtrAgt>\n" +
                "                <Cdtr>\n" +
                "                    <Nm>"+CdtrAgtCdtrNm+"</Nm>\n" +
                "                    <PstlAdr>\n" +
                "                        <StrtNm>"+CdtrAgtCdtrPstlAdrStrtNm+"</StrtNm>\n" +
                "                        <Ctry>"+CdtrAgtCdtrPstlAdrCtry+"</Ctry>\n" +
                "                        <AdrLine>"+CdtrAgtCdtrPstlAdrAdrLine+"</AdrLine>\n" +
                "                        <AdrLine>"+CdtrAgtCdtrPstlAdrAdrLine1+"</AdrLine>\n" +
                "                    </PstlAdr>\n" +
                "                    <CtryOfRes>"+CdtrCtryOfRes+"</CtryOfRes>\n" +
                "                </Cdtr>\n" +
                "                <CdtrAcct>\n" +
                "                    <Id>\n" +
                "                        <Othr>\n" +
                "                            <Id>"+CdtrAcctId+"</Id>\n" +
                "                        </Othr>\n" +
                "                    </Id>\n" +
                "                    <Ccy>"+CdtrAcctCcy+"</Ccy>\n" +
                "                </CdtrAcct>\n" +
                "                <RgltryRptg>\n" +
                "                    <Dtls>\n" +
                "                        <Inf>"+RgltryRptgDtlsInf+"</Inf>\n" +
                "                    </Dtls>\n" +
                "                </RgltryRptg>\n" +
                "                <RmtInf>\n" +
                "                    <Ustrd>"+RmtInfUstrd+"</Ustrd>\n" +
                "                    <Strd>\n" +
                "                        <CdtrRefInf>\n" +
                "                            <Ref>"+RmtInfStrdCdtrRefInf+"</Ref>\n" +
                "                        </CdtrRefInf>\n" +
                "                    </Strd>\n" +
                "                </RmtInf>\n";

        Pain001Message = pain001Message.concat(inverseRatePain001CreditorSection);
        return Pain001Message;
    };

     */

    public String createInverseRatePain001SupplementaryDet(String pain001Message,String SplmtryDbtrNm, String SplmtryDbtrAcct, String SplmtryDbtrAcctTpPrtry,
                                                           String SplmtryDbtrAcctCcy, String SplmtryDbtrAgtMmbId, String SplmtryDbtrAgtPstCd,
                                                           String SplmtryDbtrAgtBrnchId){
        String inverseRatePain001SupplementaryDet=
                "       <ns2:SplmtryData>\n" +
                "          <ns2:Envlp>\n" +
                "            <ns2:OrigDbtrInfo>\n" +
                "               <DbtrNm>"+SplmtryDbtrNm+"</DbtrNm>\n" +
                "               <DbtrAcct>"+SplmtryDbtrAcct+"</DbtrAcct>\n" +
                //"             <DbtrAcctTpPrtry>"+SplmtryDbtrAcctTpPrtry+"</DbtrAcctTpPrtry>\n" +
                //"             <DbtrAcctCcy>"+SplmtryDbtrAcctCcy+"</DbtrAcctCcy>\n" +
                "               <DbtrAgtMmbId>"+SplmtryDbtrAgtMmbId+"</DbtrAgtMmbId>\n" +
                "               <DbtrAgtPstCd>"+SplmtryDbtrAgtPstCd+"</DbtrAgtPstCd>\n" +
                "               <DbtrAgtBrnchId>"+SplmtryDbtrAgtBrnchId+"</DbtrAgtBrnchId>\n" +
                "            </ns2:OrigDbtrInfo>\n" +
                "          </ns2:Envlp>\n" +
                "       </ns2:SplmtryData>\n"+
                "      </ns2:CdtTrfTxInf>\n";

        Pain001Message = pain001Message.concat(inverseRatePain001SupplementaryDet);
        return Pain001Message;
    };

}

