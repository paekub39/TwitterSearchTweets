import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetsFinder {
	private String hashtag,customerKey,customerSecret,tokenKey,tokenSecret;
	static int tweetsCounting;
	static String tweetsResult = "";
	String fileName = "";
	
	static ConfigurationBuilder cb = new ConfigurationBuilder();
	static BufferedWriter bw = null;
    static FileWriter fw = null;
	
	public TweetsFinder() {
	}

	public TweetsFinder(String customerKey, String customerSecret, String tokenKey,
			String tokenSecret) {
		this.customerKey = customerKey;
		this.customerSecret = customerSecret;
		this.tokenKey = tokenKey;
		this.tokenSecret = tokenSecret;
	}
	
	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getCustomerSecret() {
		return customerSecret;
	}

	public void setCustomerSecret(String customerSecret) {
		this.customerSecret = customerSecret;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void findTweets(String hashtag , boolean isSearchWithRetweet) throws IOException{
		cb.setOAuthConsumerKey(customerKey)
			.setOAuthConsumerSecret(customerSecret)
			.setOAuthAccessToken(tokenKey)
			.setOAuthAccessTokenSecret(tokenSecret);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
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
            query.setLang("en");
            QueryResult result;
            do {
                result = twitter.search(query);
                
                tweets = result.getTweets();
                
                for (Status tweet : tweets) {
                	tweetsCounting++;
                	tweetsResult=tweet.getText() + " ";
                	System.out.println(tweet.getUser().getScreenName() + " - " + tweet.getText() + " - " + tweetsCounting);
                	bw.write(tweetsResult);
                	if(tweetsCounting >= 15000){
                		System.out.println("This hash tag has more than 15000 tweets. \nOur program cannot find tweets any more. Because Twitter has limit the amoung of tweets.");
                		break;
                	}
                }      
            } while ((query = result.nextQuery()) != null); 
            System.out.println("Finish searched.");
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
