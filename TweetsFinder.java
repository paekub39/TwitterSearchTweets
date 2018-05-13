import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetsFinder {
	private String hashtag;
	static int tweetsCounting = 0;
	static String tweetsResult = "";
	String fileName = "";
	static Boolean isSetLang = false;
	String language = "";
	String input = "";
	
	static TwitterFactory tf;
	static BufferedWriter bw = null;
    static FileWriter fw = null;
	
	public TweetsFinder() {
	}

	public TweetsFinder(TwitterFactory cb) {
		TweetsFinder.tf = cb;
	}
	
	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void findUserTimeline(String input) {
		this.input = input;
		Twitter twitterUser = tf.getInstance();
		try{
			List<Status> status = twitterUser.getUserTimeline(input);
	        List<String> tweets  = new ArrayList<String>();
	         
	        for (Status status2 : status)
	        {
	            tweets.add(status2.getText());
	            System.out.println("---Tweet---"+status2.getText());    
	        }
		}catch (TwitterException te){
            System.out.println("Error occured "+te);
        }
        System.out.println("Tweets Retrieved");
	}

	public void findTweets(String hashtag , boolean isSearchWithRetweet) throws IOException{

		Twitter twitter = tf.getInstance();
		fw = new FileWriter(fileName);
        bw = new BufferedWriter(fw);
		
		try {
        	List<Status> tweets = null;
        	tweetsCounting = 0;
            Query query = new Query();
            if(isSearchWithRetweet){
            	query.setQuery(hashtag);
            }else{
            	query.setQuery(hashtag + " +exclude:retweets");
            }
            query.setCount(100);
            query.setLang(language);
            /*
            if(isSetLang){
            	query.setLang("");
            }
            else{
            	query.setLang("");
            }
            */
            QueryResult result;
            do {
                result = twitter.search(query);
                
                tweets = result.getTweets();
                
                for (Status tweet : tweets) {
                	tweetsCounting++;
                	tweetsResult=tweet.getText() + " ";
                	System.out.println(tweet.getUser().getScreenName() + " - " + tweet.getText());
                	bw.write(tweetsResult);
                	if(tweetsCounting >= 1000){
                		System.out.println("\n\nThis hash tag has more than 1000 tweets. \nWe limit tweet search only 1000 tweets");
                		break;
                	}
                }
            } while (((query = result.nextQuery()) != null) && (tweetsCounting < 1000)); 
            System.out.println("Finish searched.\n");
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
       bw.close();
	}

	
	public int getTweetsCounting() {
		return tweetsCounting;
	}


	

}
