/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService;

import java.net.*;
import javax.json.*;

/**
 * 
 * @author Amir
 * a weather service
 * connects to openweathermap.com to retrieve data based on location given (currently implemented only: [city] or [city + country])
 */
public class OpenWeatherMapService implements IWeatherDataService {
	
	private static final String byCityNameURL = "api.openweathermap.org/data/2.5/weather?q="; //City,Country is also accepted (city name and country code divided by comma, use ISO 3166 country codes)

	private static OpenWeatherMapService instance = null;
	
	/**
	 * this class uses a singletone pattern, so this Ctor is private.
	 */
	private OpenWeatherMapService()
	{
		//private CTOR as part of the singleton pattern 
	}
	
	/**
	 * initialises an open weather map service if one wasn't initialised already.
	 * @return
	 * the one and  only instance of this service in a runtime. 
	 */
	public static OpenWeatherMapService getInstance()
	{
		if(instance == null)
		{
			instance  = new OpenWeatherMapService();
		}
		return instance;
	}
	
	/**
	 * connects to the server of open weather map and retrieved using JSON the weather data relevant to requested location
	 * if a location is not found and is invalid usually a response with error 401, 404 or 500 arrives.
	 * 
	 * @param location - the location currently is only [city] or [city,countryCode] (city +","+ CC - country code in 2 letters)
	 * 
	 * @return
	 * a POJO of weatherData (the model on this project)
	 */
	public WeatherData getWeatherData(Location location) throws WeatherDataServiceException
	{
		WeatherData weatherData = new WeatherData();
		HttpURLConnection conn = null;
		JsonReader jsonReader = null;

		try {
			//get request
			URL url = new URL("http://"+byCityNameURL+location.city);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200)
			{
				throw new WeatherDataServiceException("Unable to get Json response from the server. HTTP error code : " + conn.getResponseCode());
			}
		
			//read the JSON
			jsonReader = Json.createReader(conn.getInputStream());
            JsonObject jsonObject = jsonReader.readObject();
            
            if(!jsonObject.containsKey("cod"))
            {
            	throw new WeatherDataServiceException("Mandatory key (\"cod\") is missing from the Json response.");
            }
            
            if(jsonObject.getInt("cod")!=200)
            {
            	String message = "";
            	if(jsonObject.containsKey("message"))
            	{
            		message = jsonObject.getString("message");
            	}
            	throw new WeatherDataServiceException("Json response is not valid (cod!=200). cod=" + jsonObject.getInt("cod") + ", message:" + message);
        	}
            
            //parse the JSON to POJO (WeatherData)
            
        	weatherData.setCityName(jsonObject.containsKey("name")?jsonObject.getString("name"):null);
        	
        	weatherData.setMeasureTime(jsonObject.containsKey("dt")?jsonObject.getJsonNumber("dt").longValue():null);
        	
        	if(jsonObject.containsKey("sys"))
        	{
        		JsonObject jsonSys =  jsonObject.getJsonObject("sys");
            	weatherData.setCountryCode(jsonSys.containsKey("country")?jsonSys.getString("country"):null);
            	weatherData.setSunrise(jsonSys.containsKey("sunrise")?jsonSys.getJsonNumber("sunrise").longValue():null);
            	weatherData.setSunset(jsonSys.containsKey("sunset")?jsonSys.getJsonNumber("sunset").longValue():null);
        	}
        	
        	if(jsonObject.containsKey("coord"))
        	{
        		JsonObject jsonCoord = jsonObject.getJsonObject("coord");
	            weatherData.setCoordinateLongitude(jsonCoord.containsKey("lon")?jsonCoord.getJsonNumber("lon").doubleValue():null);
	            weatherData.setCoordinateLatitude(jsonCoord.containsKey("lat")?jsonCoord.getJsonNumber("lat").doubleValue():null);
        	}
        	
        	if(jsonObject.containsKey("main"))
    		{
        		JsonObject jsonMain = jsonObject.getJsonObject("main");
        		weatherData.setTemperature(jsonMain.containsKey("temp")?jsonMain.getJsonNumber("temp").doubleValue():null);
        		weatherData.setHumidity(jsonMain.containsKey("humidity")?jsonMain.getJsonNumber("humidity").doubleValue():null);
    		}
        	
            if(jsonObject.containsKey("weather"))
            {
	            JsonArray jsonWeatherArray = jsonObject.getJsonArray("weather");
	            
	            for(int i=0; i<jsonWeatherArray.size(); i++)
	            {
	            	JsonObject weatherIter = jsonWeatherArray.getJsonObject(i);
	            	weatherData.getWeatherDescription().add(weatherIter.containsKey("description")?weatherIter.getString("description"):null);
	            }
            }

        	if(jsonObject.containsKey("wind"))
        	{
        		JsonObject jsonWind = jsonObject.getJsonObject("wind");
	            weatherData.setWindSpeed(jsonWind.containsKey("speed")?jsonWind.getJsonNumber("speed").doubleValue():null);
	            weatherData.setWindDirection(jsonWind.containsKey("deg")?jsonWind.getJsonNumber("deg").doubleValue():null);
        	}
        	
        	if(jsonObject.containsKey("clouds"))
        	{
        		JsonObject jsonClouds = jsonObject.getJsonObject("clouds");
        		weatherData.setCloudiness(jsonClouds.containsKey("all")?jsonClouds.getJsonNumber("all").doubleValue():null);
        	}
        	if(jsonObject.containsKey("rain"))
        	{
        		JsonObject jsonRain = jsonObject.getJsonObject("rain");
        		weatherData.setRainVolume(jsonRain.containsKey("3h")?jsonRain.getJsonNumber("3h").doubleValue():null);
        	}
        	if(jsonObject.containsKey("snow"))
        	{
        		JsonObject jsonSnow = jsonObject.getJsonObject("snow");
        		weatherData.setSnowVolume(jsonSnow.containsKey("3h")?jsonSnow.getJsonNumber("3h").doubleValue():null);
        	}
        	
            jsonReader.close();
            conn.disconnect();
		}
		catch (Exception e)
		{
			if(jsonReader !=null)
				jsonReader.close();
			if(conn != null)
				conn.disconnect();
			throw new WeatherDataServiceException(e.getClass().getName() +":" + e.getMessage());
		}
		finally
		{
			if(jsonReader !=null)
				jsonReader.close();
			if(conn != null)
				conn.disconnect();
		}
		
		return weatherData;
	}

	/*
	 * (non-Javadoc)
	 * @see weatherDataService.IWeatherDataService#testConnection()
	 */
	public boolean testConnection() {
		try {
			WeatherData wd = getWeatherData(new Location("London,GB"));
			return wd.getCityName().compareToIgnoreCase("London") == 0;
		} catch (WeatherDataServiceException e) {
			return false;
		}
	}

}
