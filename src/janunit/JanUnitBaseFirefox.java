package janunit;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import janunit.model.Input;
import janunit.model.Parameter;

public class JanUnitBaseFirefox {

	private final static Logger logger = Logger.getLogger(JanUnitBaseFirefox.class.getName());

	private static final int WAIT_TIME = 3000;
	
	protected WebDriver webDriver;
	protected WebDriverWait wait;
	
	@Before 
	public void setUp() {
		
		System.setProperty("webdriver.gecko.driver", "/home/jan/janunit/geckodriver-0.19.0");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		
		
		ProfilesIni profileIni = new ProfilesIni();
	    FirefoxProfile profile = profileIni.getProfile("default"); 
	    profile.setPreference("network.proxy.type", 1);
	    profile.setPreference("network.proxy.http", "localhost.ssl");
	    profile.setPreference("network.proxy.http_port", 8080);
	    profile.setPreference("network.proxy.ssl", "localhost.ssl");
	    profile.setPreference("network.proxy.ssl_port", 8080);
	    profile.setPreference("network.proxy.no_proxies_on", "");

	    FirefoxOptions options = new FirefoxOptions().setLogLevel(Level.WARNING);

	    //"moz:firefoxOptions", options
	    
	    
	    options.setProfile(profile);
		options.setHeadless(true);		
	
		webDriver = new FirefoxDriver(options);	
		wait = new WebDriverWait(webDriver, WAIT_TIME);
	}

	@After 
	public void tearDown() {
		webDriver.close();
	}
	
	protected String waitForResultSource(String id) {
	//	logger.log(Level.WARNING, "TEXT: " + wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).getAttribute("innerText"));
	//	logger.log(Level.WARNING, "HTML: " + wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).getAttribute("innerHTML"));
		
		return 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id))).getAttribute("innerText");
	}

	protected void navigate(String path) {
		webDriver.navigate().to(path);
	}
	
	protected void navigateAndWaitFor(String path, String id) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}

	protected void submit(String submit) {
		webDriver.findElement(By.id(submit)).submit();
	}

	protected void navigateAndSubmitForm(String url, List<Input> inputs, String submit) throws InterruptedException {
		navigate(url);		
	    populateForm(inputs);	        
		submit(submit);
		
		//just in case of staleness... shouldn't need this but we do... grrr
		Thread.sleep(1000);
	}

	protected void navigateAndSubmitGet(String url) throws InterruptedException {
		
		navigate(url);		
		
		//just in case of staleness... shouldn't need this but we do... grrr
		Thread.sleep(1000);
	}

	protected String buildURL(String base, String path, List<Parameter> parameters) {

		logger.log(Level.INFO, "build url: " + parameters.size());
		String url = base + path;
		
		for(int i = 0; i < parameters.size(); i++) {
			logger.log(Level.INFO, "adding parameter: " + parameters.get(i).getName());
			if(i == 0) {
				url += "?";
			}
			else {
				url += "&";
			}
			url += parameters.get(i).getName();
			url += "=";
			url += parameters.get(i).getValue();
		}
		return url;
	}

	
	protected void populateForm(List<Input> inputs) {
		for(Input input : inputs) {    
		    try {
		    	populate(input.getName(), input.getValue());
		    } 
		    catch(Exception e) {
		    	logger.log(Level.WARNING, "skipping element: " + input.getName());
		      }
	    }
	}

	protected void populate(String key, String value) {
		webDriver.findElement(By.id(key)).clear();
		webDriver.findElement(By.id(key)).sendKeys(value);
	}
	

	
	protected String checkAlert() {
		String message = "NO_ALERT_MESSAGE";
	    try {
	    	FluentWait fwait = new FluentWait(webDriver);
	    	fwait.withTimeout(5000, TimeUnit.MILLISECONDS);
	    	fwait.pollingEvery(500, TimeUnit.MILLISECONDS);
	    	fwait.ignoring(NoSuchElementException.class);

	    	fwait.until(ExpectedConditions.alertIsPresent());

	    	Alert alert = webDriver.switchTo().alert();
	        message = alert.getText();
	    	logger.log(Level.INFO, "ALERT MESSAGE: " + message);
	    	
	        alert.accept();        
	    } catch (NoAlertPresentException nae) {
	    	//logger.log(Level.WARNING, "no alert yet: " + nae.getMessage());
	    	//swallow
		}	    
	    catch (Exception e) {
	    	//logger.log(Level.WARNING, "arrroooooooga: " + e.getMessage());
	    	//swallow
	    }
	    return message;
	}
/*
	protected String checkAlert() {
		String message = "NO_ALERT_MESSAGE";
	    try {
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = webDriver.switchTo().alert();
	        message = alert.getText();
	        alert.accept();        
	    } catch (NoAlertPresentException nae) {
	    	logger.log(Level.WARNING, "no alert yet: " + nae.getMessage());
		}	    
	    catch (Exception e) {
	    	logger.log(Level.WARNING, "arrroooooooga: " + e.getMessage());
	    	
	    }
	    return message;
	}
	*/
}