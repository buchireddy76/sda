package com.wwt.sda;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class DriverConfiguration {

	protected final WebDriver driver;

	public DriverConfiguration(WebDriver driver) {
		this.driver = driver;
	}

	// Setting the Driver Configuration
	public void driverConfiguration(Properties driverProperites) {
		driver.manage().window().maximize();
		String SELENIUMWAITTIME = driverProperites
				.getProperty("CONFIG_DRIVERWAITTIME");
		Integer SECONDS = new Integer(SELENIUMWAITTIME);

		driver.manage().timeouts().implicitlyWait(SECONDS, TimeUnit.SECONDS);
		String URI = driverProperites.getProperty("CONFIG_URL");
		driver.get(URI);
	}
}
