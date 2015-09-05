/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Amir
 * This is the model or the relevant weather data that can be received from weather services.
 * because the data received from the weather service is raw and maybe unknown or unrelatable for human i've added some function
 * for getters to parse the data better. (e.g. epoch time to Calendar)  - an addition to the regular getters and setters
 */
public class WeatherData
{
	public static final int TEMPERATURE_KELVIN_DEGREES = 0;
	public static final int TEMPERATURE_CELCIUS_DEGREES = 1;
	public static final int TEMPERATURE_FAHRENHEIT_DEGREES = 2;
	
	//location data:
	private String cityName;//name
	private String countryCode;//sys.country
	private Double coordinateLongitude;//coord.lon
	private Double coordinateLatitude;//coord.lat
	
	//collection time
	private Long measureTime;//dt
	
	//weather
	private Double temperature;//main.temp - temperature is in KELVIN degrees
	private List<String> weatherDescription;//weather.description
	
	private Long sunrise;//sys.sunrise
	private Long sunset;//sys.sunset
	
	private Double humidity;//main.humidity
	private Double windSpeed;//wind.speed
	private Double windDirection;//wind.deg - range 0<x<360
	private Double cloudiness;//clouds.all - percent(%)
	private Double rainVolume;//rain.3h
	private Double snowVolume;//snow.3h
	
	/**
	 * CTOR - every variable is initialised with null value - parsed from JSON, data can be missing, null handling is easy.
	 */
	public WeatherData()
	{
		
		//location data:
		cityName = null;//name
		countryCode = null;//sys.country
		coordinateLongitude = null;//coord.lon
		coordinateLatitude = null;//coord.lat
		
		//collection time
		measureTime = null;//dt
		
		//weather
		temperature = null;//main.temp
		weatherDescription = new ArrayList<String>();//weather.description
		
		sunrise = null;//sys.sunrise
		sunset = null;//sys.sunset
		
		humidity = null;//main.humidity
		windSpeed = null;//wind.speed
		windDirection = null;//wind.deg - range 0<x<360
		cloudiness = null;//clouds.all - percent(%)
		rainVolume = null;//rain.3h
		snowVolume = null;//snow.3h
		
	}
	
	/**
	 * 
	 * @return Calendar object of the time last measured all the weather data (by the weather service).
	 */
	public Calendar getMeasureTimeCalendar()
	{
		if(this.measureTime!=null)
		{
			Calendar measureTime = Calendar.getInstance();
			measureTime.setTimeInMillis(this.measureTime*1000);
			return measureTime;
		}
		return null;
	}
	
	/**
	 * 
	 * @param scale
	 * the required scale or unit of the temperature
	 * (raw data is received in Kelvin scale)
	 * @return
	 * a double number representation of the temperature.
	 */
	public Double getTemperatureDegrees(int scale)
	{
		if(this.temperature!=null){
			if(scale == TEMPERATURE_KELVIN_DEGREES)
				return this.temperature;
			if(scale == TEMPERATURE_FAHRENHEIT_DEGREES)
				return this.temperature*1.8 - 459.67;
			if(scale == TEMPERATURE_CELCIUS_DEGREES)
				return this.temperature - 273.15;
		}
		//default is kelvin:
		return this.temperature;
	}
	
	/**
	 * 
	 * @return Calendar object of the sunset time (converted from epoch)
	 */
	public Calendar getSunsetDate()
	{
		if(this.sunset!=null)
		{
			Calendar sunset = Calendar.getInstance();
			sunset.setTimeInMillis(this.sunset*1000);
			return sunset;
		}
		return null;
	}
	
	/**
	 * 
	 * @return Calendar object of the sunrise time (converted from epoch)
	 */
	public Calendar getSunriseDate()
	{
		if(this.sunrise!=null)
		{
			Calendar sunrise = Calendar.getInstance();
			sunrise.setTimeInMillis(this.sunrise*1000);
			return sunrise;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * the duration left until the next change in lighting (next sunset or sunrise)
	 */
	public Duration getTimeToDaylightChange()
	{
		Calendar now = Calendar.getInstance();
		Long interval;
		
		if(isDark())
			interval = now.getTimeInMillis() - this.sunset*1000;
		else
			interval = now.getTimeInMillis() - this.sunrise*1000;
			
		if(interval > 0)
			return Duration.ofMillis(interval);
		else
			return null;
	}
	
	/**
	 * 
	 * @return boolean value representing:
	 * is it dark outside
	 */
	public boolean isDark()
	{
		Calendar sunset = getSunsetDate();
		Calendar sunrise = getSunriseDate();
		Calendar now = Calendar.getInstance();
		
		if(sunset.before(sunrise)) // sunset < sunrise
		{
			if(now.after(sunset) & now.before(sunrise)) // sunset < now < sunrise
				return true;
			else // (sunset < sunrise < now) or (now < sunset < sunrise)
				return false;
		}
		else //sunrise < sunset
		{
			if(now.after(sunrise) & now.before(sunset))//sunrise < now < sunset 
				return false;
			else //(sunrise < sunset < now) or (now < sunrise < sunset)
				return true;
		}
	}
	
	//getters and setters:
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Double getCoordinateLongitude() {
		return coordinateLongitude;
	}
	public void setCoordinateLongitude(Double coordinateLongitude) {
		this.coordinateLongitude = coordinateLongitude;
	}
	public Double getCoordinateLatitude() {
		return coordinateLatitude;
	}
	public void setCoordinateLatitude(Double coordinateLatitude) {
		this.coordinateLatitude = coordinateLatitude;
	}
	public Long getMeasureTime() {
		return measureTime;
	}
	public void setMeasureTime(Long measureTime) {
		this.measureTime = measureTime;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public List<String> getWeatherDescription() {
		return weatherDescription;
	}
	public void setWeatherDescription(List<String> weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	public Long getSunrise() {
		return sunrise;
	}
	public void setSunrise(Long sunrise) {
		this.sunrise = sunrise;
	}
	public Long getSunset() {
		return sunset;
	}
	public void setSunset(Long sunset) {
		this.sunset = sunset;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Double getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(Double windDirection) {
		this.windDirection = windDirection;
	}
	public Double getCloudiness() {
		return cloudiness;
	}
	public void setCloudiness(Double cloudiness) {
		this.cloudiness = cloudiness;
	}
	public Double getRainVolume() {
		return rainVolume;
	}
	public void setRainVolume(Double rainVolume) {
		this.rainVolume = rainVolume;
	}
	public Double getSnowVolume() {
		return snowVolume;
	}
	public void setSnowVolume(Double snowVolume) {
		this.snowVolume = snowVolume;
	}
	
	/**
	 * default eclipse generated toString method
	 */
	@Override
	public String toString() {
		return "WeatherData [cityName=" + cityName + ", countryCode=" + countryCode + ", coordinateLongitude="
				+ coordinateLongitude + ", coordinateLatitude=" + coordinateLatitude + ", measureTime=" + measureTime
				+ ", temperature=" + temperature + ", weatherDescription=" + weatherDescription + ", sunrise=" + sunrise
				+ ", sunset=" + sunset + ", humidity=" + humidity + ", windSpeed=" + windSpeed + ", windDirection="
				+ windDirection + ", cloudiness=" + cloudiness + ", rainVolume=" + rainVolume + ", snowVolume="
				+ snowVolume + "]";
	}
}
