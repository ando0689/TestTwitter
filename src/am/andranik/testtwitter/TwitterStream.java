package am.andranik.testtwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TwitterStream implements Runnable, Constants{

    private ArrayList<JSONObject> twittsArray;
    
    //private String trackWord = "twitter";
    private int limit = 500;
    
    public TwitterStream(int limit){
    	twittsArray = new ArrayList<JSONObject>();
    	//this.trackWord = trackWord;
    	this.limit = limit;
    }

    public void run(){
    	
        try{
            System.out.println("Starting Twitter public stream consumer thread.");

            OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(CUNSUMER_KEY)
                    .apiSecret(CUNSUMER_SECRET)
                    .build();

            Token accessToken = new Token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

            System.out.println("Connecting to Twitter Public Stream");
            OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
            request.addHeader("version", "HTTP/1.1");
            request.addHeader("host", "stream.twitter.com");
            request.setConnectionKeepAlive(true);
            request.addHeader("user-agent", "Twitter Stream Reader");
           // request.addBodyParameter("track", trackWord);
            service.signRequest(accessToken, request);
            Response response = request.send();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));

            int twitts = 0;
            
            String line;
            while ((line = reader.readLine()) != null) {
            	JSONObject lineJson = null;
            	
            	try{
            		lineJson = new JSONObject(line);
	            	twitts++;
	            	
	            	if(twitts % 10 == 0){
	            		System.out.print(".");
	            	}
            	} catch (JSONException e){
            		e.printStackTrace();
            		System.out.println("Message: " + line);
            	}
     
            	twittsArray.add(lineJson);
            	
            	if(twitts == limit){
            		new Thread(new TwittsAnaliser(new ArrayList<JSONObject>(twittsArray))).start();
            		twittsArray.clear();
            		twitts = 0;
            	}
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        } 

    }
}
