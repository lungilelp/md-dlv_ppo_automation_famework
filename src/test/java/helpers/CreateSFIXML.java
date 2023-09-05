package helpers;

import static helpers.CreateMultiDebitMultiCreditPain001.setTagValue;

public class CreateSFIXML {

    String SFIXSDRequest = null;

    //SFI File Group header formation
    public String createSFIGroupHeader(String MessageIdentification, String CreationDateTime,
                                           String NumberOfTransactions,String ControlSum,
                                           String HashTotal,String FileId) {
        SFIXSDRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Request>\n" +
                "    <GroupHeader>\n" +
                "        <MessageIdentification>" + setTagValue(MessageIdentification) + "</MessageIdentification>\n" +
                "        <CreationDateTime>" + setTagValue(CreationDateTime) + "</CreationDateTime>\n" +
                "        <NumberofTransactions>" + setTagValue(NumberOfTransactions) + "</NumberofTransactions>\n" +
                "        <ControlSum>" + setTagValue(ControlSum) + "</ControlSum>\n" ;

        if(!FileId.equalsIgnoreCase("")){
            SFIXSDRequest = SFIXSDRequest + "        <FileID>" + setTagValue(FileId) + "</FileID>\n" +
                    "        <HashTotal>" + setTagValue(HashTotal) + "</HashTotal>\n" +
                    "    </GroupHeader>\n";
        }else{
            SFIXSDRequest = SFIXSDRequest +
                    "        <HashTotal>" + setTagValue(HashTotal) + "</HashTotal>\n" +
                    "    </GroupHeader>\n";
        }


        return SFIXSDRequest;
    }

    //SFI File Payment Information Block Common Details Formation
    public String createSFIPaymentInfoCommonDetails(String sfiXSDRequest,String PaymentMethod, String ProcessingOption,
                                       String ServiceLevel,String LocalInstrument,
                                       String ForcePost) {
        String paymentInformation = "    <PaymentInformation>\n" +
                "        <PaymentMethod>" + setTagValue(PaymentMethod) + "</PaymentMethod>\n" +
                "        <ProcessingOption>" + setTagValue(ProcessingOption) + "</ProcessingOption>\n" +
                "        <ServiceLevel>" + setTagValue(ServiceLevel) + "</ServiceLevel>\n" +
                "        <LocalInstrument>" + setTagValue(LocalInstrument) + "</LocalInstrument>\n" +
                "        <ForcePost>" + setTagValue(ForcePost) + "</ForcePost>\n";

        SFIXSDRequest = sfiXSDRequest.concat(paymentInformation);
        return SFIXSDRequest;
    }

    //SFI File Transaction Detail Block Common Details Formation
    public String createSFICommonTransactionDetail(String sfiXSDRequest,String ValueDate, String ChannelInstructionId,
                                                    String Narrative,String PaymentPurpose,
                                                    String TransactionCode,String TransactionCountry) {
        String transactionDetail = "        <TransactionDetail>\n" +
                "            <ValueDate>" +  setTagValue(ValueDate) + "</ValueDate>\n" +
                "            <ChannelInstructionId>" +  setTagValue(ChannelInstructionId) + "</ChannelInstructionId>\n" +
                "            <Narrative>" +  setTagValue(Narrative) + "</Narrative>\n" +
                "            <PaymentPurpose>" +  setTagValue(PaymentPurpose) + "</PaymentPurpose>\n" +
                "            <TransactionCode>" +  setTagValue(TransactionCode) + "</TransactionCode>\n" +
                "            <TransactionCountry>" +  setTagValue(TransactionCountry) + "</TransactionCountry>\n";

        SFIXSDRequest = sfiXSDRequest.concat(transactionDetail);
        return SFIXSDRequest;
    }

    //SFI File Origination Details Block Common Details Formation
    public String createSFIOriginationDetails(String sfiXSDRequest,String OriginatorId, String OriginatorName,
                                                   String OriginatingSortCode,String OriginatingAccountNumber,
                                                   String OriginatingAccountType,String OriginatorAccountCurrency,
                                              String OriginatorReference,String OriginatorTransactionCode) {
        String originationDetails = "            <OriginationDetails>\n" +
                "                <OriginatorId>" + setTagValue(OriginatorId) + "</OriginatorId>\n" +
                "                <OriginatorName>" + setTagValue(OriginatorName) + "</OriginatorName>\n" +
                "                <OriginatingSortCode>" + setTagValue(OriginatingSortCode) + "</OriginatingSortCode>\n" +
                "                <OriginatingAccountNumber>" + setTagValue(OriginatingAccountNumber) + "</OriginatingAccountNumber>\n" +
                "                <OriginatingAccountType>" + setTagValue(OriginatingAccountType) + "</OriginatingAccountType>\n" +
                "                <OriginatorAccountCurrency>" + setTagValue(OriginatorAccountCurrency) + "</OriginatorAccountCurrency>\n" +
                "                <OriginatorReference>" + setTagValue(OriginatorReference) + "</OriginatorReference>\n" +
                "                <OriginatorTransactionCode>" + setTagValue(OriginatorTransactionCode) + "</OriginatorTransactionCode>\n" +
                "            </OriginationDetails>\n";

        SFIXSDRequest = sfiXSDRequest.concat(originationDetails);
        return SFIXSDRequest;
    }

    //SFI File Destination Detail Block Formation
    public String createSFIDestinationDetails(String sfiXSDRequest,String TransactionId, String DestinationName,
                                              String DestinationSortCode,String DestinationAccountNumber,
                                              String DestinationAccountType,String TransferAmount,
                                              String TransferCurrency,String DestinationReference,
                                              String DestinationNarrative,String DestinationTransactionCode) {
        String destinationDetails = "            <DestinationDetails>\n" +
                "                <TransactionId>" +  setTagValue(TransactionId) + "</TransactionId>\n" +
                "                <DestinationName>" +  setTagValue(DestinationName) + "</DestinationName>\n" +
                "                <DestinationSortCode>" + setTagValue(DestinationSortCode) + "</DestinationSortCode>\n" +
                "                <DestinationAccountNumber>" + setTagValue(DestinationAccountNumber)  + "</DestinationAccountNumber>\n" +
                "                <DestinationAccountType>" + setTagValue(DestinationAccountType) + "</DestinationAccountType>\n" +
                "                <TransferAmount>" + setTagValue(TransferAmount) + "</TransferAmount>\n" +
                "                <TransferCurrency>" + setTagValue(TransferCurrency) + "</TransferCurrency>\n" +
                "                <DestinationReference>" + setTagValue(DestinationReference) + "</DestinationReference>\n" +
                "                <DestinationNarrative>" + setTagValue(DestinationNarrative) + "</DestinationNarrative>\n" +
                "                <DestinationTransactionCode>" + setTagValue(DestinationTransactionCode) + "</DestinationTransactionCode>\n" +
                "            </DestinationDetails>\n";

        SFIXSDRequest = sfiXSDRequest.concat(destinationDetails);
        return SFIXSDRequest;
    }

    //SFI File Footer Block Formation
    public String createSFIFooter(String sfiXSDRequest) {
        String footerDetails = "        </TransactionDetail>\n" +
                "    </PaymentInformation>\n" +
                "</Request>";

        SFIXSDRequest = sfiXSDRequest.concat(footerDetails);
        return SFIXSDRequest;
    }
}
