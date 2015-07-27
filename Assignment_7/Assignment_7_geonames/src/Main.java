import java.util.List;
import java.util.Scanner;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WeatherObservation;
import org.geonames.WebService;

//http://www.geonames.org/source-code/javadoc/allclasses-noframe.html

public class Main 
{

	public static void header(){
		System.out.println("***************************************************************************************");
		System.out.println("*                   |--------------------------|                                      *");
		System.out.println("*                   | Assignment 7 on geonames |                                      *");
		System.out.println("*                   |--------------------------|                                      *");
		System.out.println("***************************************************************************************");
	}
	
	public static void main(String[] args) 
	{
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
	
		header();
		String userAvailable;
		String userName = "demo";
//		System.out.println("***************************************************************************************");
		
		System.out.print("Do You Have A User Name (Yes or No)? ");
		userAvailable = reader.nextLine();
			if(userAvailable.equalsIgnoreCase("yes") || userAvailable.equalsIgnoreCase("Y")){
				System.out.print("Enter your registered username: ");
				userName = reader.nextLine();
			}else if(userAvailable.equalsIgnoreCase("no") || userAvailable.equalsIgnoreCase("N")){
				System.out.println("Get a User Name: http://www.geonames.org/manageaccount " + userName);
				System.out.println("And relaunch App");
				System.exit(0);
			}else{
				System.out.println("Incorrect Ans, Using: '" + userName + "' as Username");
				
			}

		WebService.setUserName(userName);
				
		System.out.println("***************************************************************************************");
		
		String searchQ = "USA"; //The search criteria
		System.out.println("Toponym - a place name, especially one derived from a topographical feature.");
		System.out.print("Enter a Toponym: ");
		searchQ = reader.nextLine();

		System.out.println("***************************************************************************************");
			
		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		searchCriteria.setQ(searchQ);
		ToponymSearchResult search_Result = null;
		
		try {
			search_Result = WebService.search(searchCriteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total Number of Results: " + search_Result.getTotalResultsCount());
		System.out.print("How many to print? max 99: " );
		
		List<Toponym> resultList = search_Result.getToponyms();
		
		int resultsToPrint = reader.nextInt();
		
		if(resultsToPrint >= 99){
			resultsToPrint = resultList.size();
		}	
		
		System.out.println("***************************************************************************************");
		
		for(int i=0;i<resultsToPrint;i++){
			  Toponym toponym = (Toponym) resultList.get(i);
			  System.out.println("-> "+(i)+" CountryName: "+ toponym.getCountryName() + " Region: "+toponym.getName());
		} 
		
	  	System.out.println("***************************************************************************************");
	
	  	System.out.print("Select record to get more Info: " );
	  	int recordSelected = reader.nextInt();
		
	  	System.out.println("***************************************************************************************");
	  	
	  	
	  	try {
			// Use search criteria to obtain nearest latitude and longitude of postal code
	  		Toponym toponym = (Toponym) resultList.get(recordSelected);
			WeatherObservation weather = new WeatherObservation(); 
			weather = WebService.findNearByWeather(toponym.getLatitude(), toponym.getLongitude());
			
	  		String name = toponym.getName();
	  		String countryName = toponym.getCountryName();
	  		
	  		System.out.println("CountryName------------: " + countryName);
	  		System.out.println("Region-----------------: " + name);
			System.out.println("Latitude---------------: " + toponym.getLatitude() + " - Longitude: "+ toponym.getLatitude());				
			System.out.println("The Time of Observation: " + weather.getObservationTime());
			System.out.println("The Temperature was----: " + weather.getTemperature()+"C - "+ ((weather.getTemperature() * 9 / 5.0) + 32 ) + "F");
			System.out.println("The Humidity was-------: " + weather.getHumidity()+"%");
			System.out.println("Dew Point--------------: " + weather.getDewPoint() +"%");
			System.out.println("Was it cloudy----------? " + weather.getClouds());
			System.out.println("Wind Speed-------------: " + weather.getWindSpeed()+"mph");

			System.out.println("***************************************************************************************");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	
}