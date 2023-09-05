package Utilities;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

public class GeneralUtilities {

    public Boolean makeDirectory(String ppufilepath)
    {
        File file=new File(ppufilepath);
        if(!file.exists())
        {
            if(file.mkdir())
            {

                System.out.println("Directory Created");
                return true;
            }
        }
        return false;
    }

    public static String randomFixedNumber(String lengthLimit)
    {
        int stringSize= lengthLimit.length();
        double max = Double.parseDouble((1+String.format("%0"+stringSize+"d", stringSize)));
        double min = Double.parseDouble((1+String.format("%0"+(stringSize-1)+"d", stringSize)));
        double number =Math.floor(Math.random() * (max - min + 1) + min);
        BigInteger result = BigDecimal.valueOf(number).toBigInteger();
        return  ""+result;

    }


}
