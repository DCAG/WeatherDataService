/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService;

/**
 * 
 * @author Amir
 *This is a factory from which we create / choose to use different weather services.
 */
public class WeatherDataServiceFactory {
	public static final int NULL_SERVICE = -1;
	public static final int OPEN_WEATHER_MAP = 0;
	/**
	 * 
	 * @param weatherDataServiceID
	 * the service type / source
	 * @return
	 * a weather service object that can be used to retrieve weather data in POJO from remote server.
	 */
	public static IWeatherDataService getWeatherDataService(int weatherDataServiceID)
	{
		if(weatherDataServiceID == NULL_SERVICE) // null service is nothing
		{
			return null;
		}
		
		if(weatherDataServiceID == OPEN_WEATHER_MAP) //currently supported only one weather service.
		{
			return OpenWeatherMapService.getInstance(); //used in a singletone pattern.
		}
		
		return null; //default
	}
}
