package helpers;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import utils.logs.Log;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package helpers
 */
public class Validate1stACKModule extends BasePage {
    public Validate1stACKModule(WebDriver driver) {

        super(driver);
        Log.info("The current working class: "+this.getClass().getName());
    }

}
