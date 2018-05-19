import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterController {
	static boolean isChooseSearchType = false;
	static boolean isNeedRetweet = false;
	static boolean isSelectLanguage = false;
	static boolean isSearchTweet = false;
	static boolean isSearchUserTimeline = false;
	static boolean isInputLocation = false;
	static boolean isSearchByDate = false;
	
	static String input = "";
	static int checkInput = 0;
	static int option = 1;
	static int roundNumber = 1;
	static String fileName = "Tweets.txt";
	static File f = new File(fileName);
	
	static Scanner sc = new Scanner(System.in);
	
	static TwitterKey tk1 = new TwitterKey("FgZ2RfP7iXAt5n7ZicOqVPi7n", 
			"6JL8DwmgKB9AGHJenBGrrer5HkcLtCTA3y9OjTaLuTjUYCGaQ8", 
			"546477718-P2Y8SAOJirOv1Tpop5RTgsBnWH3LluzWA31v3hZO", 
			"J4FRKGuI7tK76V287dXhy5SPe77vhPi7tYRBi9v0EVkDI");
	static ConfigurationBuilder cb = new ConfigurationBuilder();

	static ConfigurationBuilder cb2 = new ConfigurationBuilder();

	public static void run() throws IOException{
		cb.setOAuthConsumerKey(tk1.getCustomerKey())
			.setOAuthConsumerSecret(tk1.getCustomerSecret())
			.setOAuthAccessToken(tk1.getTokenKey())
			.setOAuthAccessTokenSecret(tk1.getTokenSecret());
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		TweetsFinder tf1 = new TweetsFinder(tf);
		tf1.setFileName(fileName);
		
		while(true){
			f.delete();
			while(!isSearchTweet && !isSearchUserTimeline) {
				System.out.println("What you need to search.");
				System.out.println("Enter 1: Search user timeline. (the 20 most recent statuses posted in the last 24 hours from the user)");
				System.out.println("Enter 2: Search tweet with hashtag");
				checkInput = sc.nextInt();
				if(checkInput == 1) {
					isSearchTweet = false;
					isSearchUserTimeline = true;
				}
				else if(checkInput == 2) {
					isSearchTweet = true;
					isSearchUserTimeline = false;
				}
				else {
					System.out.println("ERROR: Please input the number again.");
					isSearchTweet = false;
					isSearchUserTimeline = false;
				}
			}
			
			if(isSearchTweet) {
				isSearchUserTimeline = false;
				System.out.println("\n\nPlease enter hashtag that you want to search. (For example #SIT)");
				input = sc.next();
				input = input.toLowerCase();
				
				while(!isInputLocation) {
					System.out.println("\n\nDo you want to search with specific location. (This function can search some of tweets because some tweet do not have a location in tweet.)");
					System.out.println("Enter 1: If you want to search with specific location. (Use Latitude and Longitude to search.)");
					System.out.println("Enter 2: If you do not want to search with specific location.");
					checkInput = sc.nextInt();
					if(checkInput == 1) {
						System.out.println("Please enter Latitide. eg.-22.55615, 0.0");
						double latitude = sc.nextDouble();
						System.out.println("Please enter Longitide. eg.-22.55615, 0.0");
						double longitude = sc.nextDouble();
						System.out.println("Please enter radius. (radius in Kilometer) eg.1000, 500.");
						int radius = sc.nextInt();
						tf1.setLocation(latitude, longitude, radius, true);
						isInputLocation = true;
					}
					else if(checkInput == 2) {
						tf1.setLocation(0.0, 0.0, 0, false);
						isInputLocation = true;
					}
					else {
						System.out.println("ERROR: Please enter again.");
						isInputLocation = false;
					}
				}
				
				while(!isSearchByDate) {
					System.out.println("\n\nDo you want to search by period of date. (This function can not search that tweet more than 7 days ago.");
					System.out.println("Enter 1: If you want to search by period of date.");
					System.out.println("Enter 2: If you do not want to define period of date.");
					checkInput = sc.nextInt();
					if(checkInput == 1){
						System.out.println("Please enter starting date. (Date should be formatted as YYYY-MM-DD)");
						String startDate = sc.next();
						System.out.println("Please enter ending date. (Date should be formatted as YYYY-MM-DD)");
						String endingDate = sc.next();
						tf1.setStartandEndDate(startDate, endingDate, true);
						isSearchByDate = true;
					}
					else if(checkInput == 2) {
						isSearchByDate = true;
					}
					else {
						System.out.println("ERROR: Please enter again.");
						isSearchByDate = false;
					}
				}
				
				while(!isSelectLanguage) {
					System.out.println("\n\nPlease Select language that you need to search from Twitter.");
					System.out.println("Enter 1: English");
					System.out.println("Enter 2: Thai");
					System.out.println("Enter 3: Other. (Input your language by ISO 639-1 code. Ex.en, th, etc.)");
					System.out.println("Enter other mean choose all language.");
					System.out.println("***Word counting function can use only English language.");
					checkInput = sc.nextInt();
					if(checkInput == 1){
						tf1.setLanguage("en");
					}
					else if(checkInput == 2) {
						tf1.setLanguage("th");
					}
					else if(checkInput == 3) {
						tf1.setLanguage(sc.next());
					}
					else {
						tf1.setLanguage("");
					}
					isSelectLanguage = true;
				}
				
				while(!isChooseSearchType){
					System.out.println("\n\nPlease choose one");
					System.out.println("Enter 1 if you need to search include retweet");
					System.out.println("Enter 2 if you need to search exclude retweet");
					checkInput = sc.nextInt();
					System.out.println("Finding...");
					if(checkInput == 1){
						isChooseSearchType = true;
						isNeedRetweet = true;
					}else if(checkInput == 2){
						isChooseSearchType = true;
						isNeedRetweet = false;
					}
					else{
						isChooseSearchType = false;
						System.out.println("ERROR: Please input only 1 or 2");
					}
				}
				
				tf1.findTweets(input, isNeedRetweet);
				while(option!=3){
					System.out.println("\n\nSelect an option.");
					System.out.println("Enter 1: Show how many tweets hashtag has.");
					System.out.println("Enter 2: Show top 10 word that people said about the hashtag.");
					System.out.println("Enter 3: Search new hashtag.");
					
					option = sc.nextInt();
					if(option == 1){
						System.out.println(tf1.getTweetsCounting());
					}else if(option == 2){
						WordFrequency wf = new WordFrequency();
						wf.readFile(fileName, input);
					}else if(option == 3){
						isInputLocation = false;
						isSearchByDate = false;
						isSelectLanguage = false;
						isChooseSearchType = false;
						option = 0;
						break;
					}
					
				}
				
			}
			if(isSearchUserTimeline){
				isSearchTweet = false;
				System.out.println("\n\nPlease enter user's ID");
				input = sc.next();
				tf1.findUserTimeline(input);
			}			
		}
		
	}
}
