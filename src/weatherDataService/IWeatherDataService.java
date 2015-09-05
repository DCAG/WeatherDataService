/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService;

/**
 * 
 * @author Amir
 * this interface will be implemented by weather data service class (like open-weather-map for example) 
 */
public interface IWeatherDataService
{
	/**
	 * 
	 * @param location
	 * sends a location to retrieve the weather data relevant to this location (city, country, zip code - this is an interface any implementation is acceptable).
	 * @return
	 * an object representation of the data received from the server.
	 * @throws WeatherDataServiceException
	 */
	public WeatherData getWeatherData(Location location) throws WeatherDataServiceException;
	/**
	 * send a request for any location.
	 * @return
	 * true if there is any response (preferable with code 200).
	 */
	public boolean testConnection();
}
