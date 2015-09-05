/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService.Tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import weatherDataService.WeatherData;

public class WeatherDataTest {

	WeatherData weatherData;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		weatherData = new WeatherData();
		weatherData.setTemperature(300.0);
		weatherData.setSunrise((long)1441449726);
		weatherData.setSunset((long)1441311197);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * function to test the transformation between temperatures units
	 */
	@Test
	public void testGetTemperatureDegrees() {
		assertEquals("kelvin to fehrnheit", 80.33, weatherData.getTemperatureDegrees(WeatherData.TEMPERATURE_FAHRENHEIT_DEGREES), 1.0);
		assertEquals("kelvin to celcius", 26.85, weatherData.getTemperatureDegrees(WeatherData.TEMPERATURE_CELCIUS_DEGREES), 1.0);
	}
	
	/**
	 * this is a Test :is it dark now? - this is inaccurate because its relative to the NOW time.
	 * @throws Exception
	 */
	@Test
	public void testIsDark() throws Exception { // the test will not be accurate, because it depends on NOW
		assertTrue("is it dark",weatherData.isDark());
	}
	
	/**
	 * this is a Test: much time until sunset/sunrise? - this is inaccurate because its relative to the NOW time.
	 */
	@Test
	public void testGetTimeToDaylightChange()  // the test will not be accurate, because it depends on NOW
	{
		DateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		System.out.println(fullDateFormat.format(weatherData.getSunsetDate().getTime()));
		assertTrue("how much time until sunset or sunrise",weatherData.getTimeToDaylightChange()==null);
	}
}
