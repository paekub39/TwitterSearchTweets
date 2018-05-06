import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TwitterController {
	static boolean isChooseSearchType = false;
	static boolean isNeedRetweet = false;
	static String input = "";
	static int checkRetweet = 0;
	static int option = 1;
	static int roundNumber = 1;
	static String fileName = "Tweets.txt";
	static File f = new File(fileName);
	
	static Scanner sc = new Scanner(System.in);
	
	static TwitterKey tk1 = new TwitterKey("FgZ2RfP7iXAt5n7ZicOqVPi7n", 
			"6JL8DwmgKB9AGHJenBGrrer5HkcLtCTA3y9OjTaLuTjUYCGaQ8", 
			"546477718-P2Y8SAOJirOv1Tpop5RTgsBnWH3LluzWA31v3hZO", 
			"J4FRKGuI7tK76V287dXhy5SPe77vhPi7tYRBi9v0EVkDI");

	static TweetsFinder tf1 = new TweetsFinder(tk1.getCustomerKey(), tk1.getCustomerSecret(), tk1.getTokenKey(), tk1.getTokenSecret());

	public static void run() throws IOException{
		tf1.setFileName(fileName);
		while(true){
			f.delete();
			System.out.println("Please enter hashtag that you want to search. (For example #SIT)");
			input = sc.next();
			input = input.toLowerCase();
			while(!isChooseSearchType){
				System.out.println("Please choose one");
				System.out.println("Enter 1 if you need to search include retweet");
				System.out.println("Enter 2 if you need to search exclude retweet");
				checkRetweet = sc.nextInt();
				System.out.println("Finding...");
				if(checkRetweet == 1){
					isChooseSearchType = true;
					isNeedRetweet = true;
				}else if(checkRetweet == 2){
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
				System.out.println("Select an option.");
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
					break;
				}
				
			}
			
		}
		
	}
}
