package genericByYadavRahul;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utility {
	private WebDriver driver;
	private ExtentReports repo;
	public ExtentTest test;

	public Utility(String filename) {
		// TODO Auto-generated constructor stub
		report(filename);
	}

	private void report(String filename) {
		// TODO Auto-generated method stub
		try {
			File obj = new File("REPORT\\" + filename + ".html");
			ExtentSparkReporter spark = new ExtentSparkReporter(obj);
			repo = new ExtentReports();
			repo.attachReporter(spark);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void createTest(String testnameID) {
		// TODO Auto-generated method stub
		try {
			test = repo.createTest(testnameID);
		} catch (Exception e) {
			// TODO: handle exceptio
			System.out.println("test variable not initilized   some exception occoured ");
			e.printStackTrace();
		}
	}

	public void flushed() {
		// TODO Auto-generated method stub
		try {
			repo.flush();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("report not generated due to abnormal execution of flush method");
		}
	}

	public void browserLaunch(String browsername) {
		try {
			if (browsername.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			} else if (browsername.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (browsername.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browsername.equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			} else {
				test.log(Status.INFO, "INVALID Browser name:-" + browsername);
			}
			test.log(Status.INFO, browsername + "Browser launched Sucessfully !!");
		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.INFO, browsername + "Browser launched Sucessfully !!");
		}
	}

	public void _hitURL(String url) {
		try {
			driver.get(url);
			test.log(Status.INFO, url + "  launched  sucessfully!");
		} catch (Exception e) {
			// TODO: handle exception
			driver.navigate().to(url);
			test.log(Status.INFO, url + "  launched  sucessfully!");
		} catch (Throwable e) {
			// TODO: handle exceptions
			test.log(Status.INFO, url + " not launched !");
		}
	}

	public void sS(String filename) {
		// TODO Auto-generated method stub
		try {
			File from = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File to = new File("ScreenShot\\" + filename + ".jpg");
			try {
				Files.copy(from, to);
				test.log(Status.INFO, "ScreenShot captured as file name :- " + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				test.log(Status.INFO, "ScreenShot can't coppy to desired folder");
			}
		} catch (Exception e) {
			// TODO: handle exception
			test.log(Status.INFO, "ScreenShot  not captured ");

		}
	}
	// public static WebDriver driver1=null;

	public WebElement getElement(String locatorname, String locatorvalue) {
		WebElement we = null;
		try {
			if (locatorname.equalsIgnoreCase("xpath"))
				return driver.findElement(By.xpath(locatorvalue));
			else if (locatorname.equalsIgnoreCase("id"))
				return driver.findElement(By.id(locatorvalue));
			else if (locatorname.equalsIgnoreCase("name"))
				return driver.findElement(By.name(locatorvalue));
			else if (locatorname.equalsIgnoreCase("css"))
				return driver.findElement(By.cssSelector(locatorvalue));
			else if (locatorname.equalsIgnoreCase("classname"))
				return driver.findElement(By.className(locatorvalue));
			else if (locatorname.equalsIgnoreCase("linktext"))
				return driver.findElement(By.linkText(locatorvalue));
			else if (locatorname.equalsIgnoreCase("partialLinkText"))
				return driver.findElement(By.partialLinkText(locatorvalue));
			else if (locatorname.equalsIgnoreCase("tagname"))
				return driver.findElement(By.tagName(locatorvalue));
			else
				test.log(Status.INFO, "INVALID locator type :-" + locatorname);

		} catch (NoSuchElementException e) {
			// TODO: handle exception
			test.log(Status.FAIL, "There is non such element present on ui");
		} catch (Exception e) {
			// TODO: handle exception
			test.log(Status.FAIL, " WEbElement not created ");
		}
		return we;
	}

	public void send(String locatorname, String locatorvalue, String elementName, String valueToSend) {
		// TODO Auto-generated method stub
		WebElement we = null;
		try {
			we = getElement(locatorname, locatorvalue);
			we.clear();
			we.sendKeys(valueToSend);
			test.log(Status.INFO, "entered " + valueToSend + "in " + elementName);
		} catch (ElementNotInteractableException e) {
			new Actions(driver).sendKeys(we).perform();
			test.log(Status.INFO, "entered " + valueToSend + "in " + elementName);
		} catch (Exception e) {
			JavascriptExecutor jsobj = ((JavascriptExecutor) driver);
			jsobj.executeScript("arguments[0].value='" + valueToSend + "';", we);
			test.log(Status.INFO, "entered " + valueToSend + "in " + elementName);
		} catch (Throwable e) {
			test.log(Status.INFO, "can't enter value in this textbox .");
		}

	}

	public String date_time(ExtentTest test) {
		String datetime = null;
		try {
			DateFormat df = new SimpleDateFormat("MM_dd_yyyy-HH_MM_SS");
			return datetime = df.format(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			test.log(Status.FAIL, "abnormal termination of method ,thus exception handeled ");
		}
		return datetime;

	}

	public void setImplicitWait(int maxTimeout) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(maxTimeout));
	}

	public void sizeOfElement(String locatortype, String locatorvalue, String elementname, int expectedHeight,
			int expectedWidth) {
		try {
			WebElement ge = getElement("name", "user_name");
			Dimension dimensionObj = ge.getSize();
			int actualHeight = dimensionObj.getHeight();
			if (actualHeight == expectedHeight) {
				test.log(Status.PASS, elementname + " height is same as expected");
			} else {
				test.log(Status.FAIL, elementname + " height mis-matched {ActualHeight =" + actualHeight
						+ "  ExpectedHeight =" + expectedHeight + "}\"");
			}
			int actualwidth = dimensionObj.getWidth();
			if (dimensionObj.getWidth() == expectedWidth) {
				test.log(Status.PASS, elementname + " width is same as expected");
			} else {
				test.log(Status.FAIL, elementname + " width mis-matched {ActualWidth =" + actualwidth
						+ "  ExpectedWidth =" + expectedWidth + "}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.INFO, "Exception handled");
		}
	}

	public void click(String locatortype, String locatorvalue, String elementname) {
		try {
			WebElement we = getElement(locatortype, locatorvalue);
			if (we.isDisplayed() == true) {
				test.log(Status.INFO, elementname + "text box is displayed");
				if (we.isEnabled() == true) {
					test.log(Status.INFO, elementname + "text box is enabled");
					we.click();
					test.log(Status.INFO, elementname + "clicked performed successfully");
				} else {
					test.log(Status.FAIL, elementname + "text box is not enabled");
				}
			} else {
				test.log(Status.FAIL, elementname + "text box is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sS("Vtiger/REPORT/crm.html");

	}
}
