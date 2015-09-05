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
import weatherDataService.Location;
import weatherDataService.WeatherData;
import weatherDataService.WeatherDataServiceException;
import weatherDataService.WeatherDataServiceFactory;


public class OpenWeatherMapServiceTest {
	Location location;
	IWeatherDataService openWeatherMap;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		location = new Location("jerusalem");
		openWeatherMap = WeatherDataServiceFactory.getWeatherDataService(WeatherDataServiceFactory.OPEN_WEATHER_MAP);
	}

	@After
	public void tearDown() throws Exception {
		location = null;
	}

	/**
	 *Test the weather service handler to see if it retrieves initialises and assigned the data into the ordered object correctly
	 *(JSON to POJO)    
	 */
	@Test
	public void testGetWeatherData() { //i assume the data received is logical (e.g. direction can be any degree between 0 and 360).
		String expected = "jerusalem";
		try {
			WeatherData weatherData = openWeatherMap.getWeatherData(location);
			System.out.println(weatherData.getCountryCode());
			assertTrue("checking json respose of city name", weatherData.getCityName().equalsIgnoreCase(expected));
			
			Double windDirection = weatherData.getWindDirection();
			assertTrue("checking json respose of wind speed",windDirection==null || (0<=windDirection & windDirection<=360));
		} catch (WeatherDataServiceException e) {
			e.printStackTrace();
		}
	}

}
