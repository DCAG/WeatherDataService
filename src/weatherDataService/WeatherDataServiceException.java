/*
 * Amir Granot,
 * H.I.T. 2015 Summer
 * Programming within the Internet environment.
 */
package weatherDataService;

/**
 * 
 * @author Amir
 * an exception that is unique to this weather services collection and operation library
 */
public class WeatherDataServiceException extends Exception {

	private static final long serialVersionUID = 5617847234923194646L;

	public WeatherDataServiceException(){
		super();
	}
	/**
	 * 
	 * @param message
	 * any message
	 */
	public WeatherDataServiceException(String message)
	{
		super(message);
	}
}
