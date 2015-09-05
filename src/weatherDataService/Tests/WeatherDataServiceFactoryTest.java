/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import weatherDataService.IWeatherDataService;
import weatherDataService.OpenWeatherMapService;
import weatherDataService.WeatherDataServiceFactory;

public class WeatherDataServiceFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * Test the initialisation of different weather services.
	 */
	@Test
	public void testGetWeatherDataService() {
		IWeatherDataService openWeatherMap = WeatherDataServiceFactory.getWeatherDataService(WeatherDataServiceFactory.OPEN_WEATHER_MAP);
		assertTrue("creation of an instance of openWeatherMapService", openWeatherMap!=null && (openWeatherMap instanceof OpenWeatherMapService));
		assertTrue("creation of an instance of null-servce", WeatherDataServiceFactory.getWeatherDataService(WeatherDataServiceFactory.NULL_SERVICE)==null);
	}

}
