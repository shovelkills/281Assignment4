package nz.ac.auckland.se281.a4;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nz.ac.auckland.se281.a4.ds.Graph;
import nz.ac.auckland.se281.a4.ds.Node;

//*******************************
//YOU SHOUD MODIFY THE SPECIFIED 
//METHODS  OF THIS CLASS
//HELPER METHODS CAN BE ADDED
//REQUIRED LIBRARIES ARE ALREADY 
//IMPORTED -- DON'T ADD MORE
//*******************************
public class TweetGraph extends Graph {

	protected List<Tweet> tweets;
	// Change this to map
	protected Map<TwitterHandle, List<Tweet>> nodeTweets;

	public TweetGraph(List<String> edges, List<Tweet> tweets, Map<TwitterHandle, List<Tweet>> map) throws Exception {
		super(edges);
		this.tweets = tweets;
		// changed to LinkedHashMap for fixed order
		this.nodeTweets = new LinkedHashMap<>();
		nodeTweets = map;
	}

	public List<Tweet> getTweets(Node n) {
		return nodeTweets.get(n);
	}

	public List<String> getTweetsTexts(TwitterHandle n) {
		List<String> texts = new ArrayList<>(); // Only allowed to use ArrayList HERE !!!
		for (Tweet t : getTweets(n)) {
			texts.add(t.getTextString());
		}
		return texts;
	}

	// search for a keyword in a tweet starting from a given node
	public String searchTweet(TwitterHandle user, String tweetKeyword) {
		List<Node<String>> connectedNode = depthFirstSearch(user, true);
		List<List<Tweet>> connectedTwitterHandles = new ArrayList<List<Tweet>>();
		String tweet;
		String message;
		for (Node<String> Node : connectedNode) {// Gets all the connected nodes
			connectedTwitterHandles.add(getTweets(Node));
		}
		for (List<Tweet> listTweet : connectedTwitterHandles) {// Checks the list of tweets that are connected
			for (Tweet tweets : listTweet) {
				if (tweets.getTextString().contains(tweetKeyword)) {
					tweet = "The tweet string found is: " + tweets.getTextString();
					message = "User " + user.getName() + " {" + user.getID() + "} " + "tweeted " + tweetKeyword;// Builds
																												// the
																												// message
					return tweet + "\n" + message;
				}
			}
		}

		return "No sucesssor of " + user.getName() + " tweeted " + tweetKeyword;// The message if the word is not found
	}
}
