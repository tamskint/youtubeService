package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

public class ServiceYoutube {
	
	  private   HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	  private   JsonFactory JSON_FACTORY = new JacksonFactory();
	  private   long NUMBER_OF_VIDEOS_RETURNED = 10;
	  private  YouTube youtube;
	
	
	public List<String> ListVideos(String chaine){
		  List<String>list=new ArrayList<String>();
		  try {
	        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
	        public void initialize(HttpRequest request) throws IOException {}
	      }).setApplicationName("youtube-cmdline-search-sample").build();	      
	      YouTube.Search.List search = youtube.search().list("id,snippet");
	      String apiKey = "AIzaSyA6wePKNM87cV06KtAp2HbyixKVJIfXRLc";
	      search.setKey(apiKey);
	      search.setQ(chaine);
	      search.setType("video");
	      search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
	      search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
	      SearchListResponse searchResponse = search.execute();
	      List<SearchResult> searchResultList = searchResponse.getItems();
	      if (searchResultList != null) {   
	        Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
	        //String query = queryTerm;
		    while (iteratorSearchResults.hasNext()) {
		      SearchResult singleVideo = iteratorSearchResults.next();
		      ResourceId rId = singleVideo.getId();		      
		      if (rId.getKind().equals("youtube#video")) {
		    	  list.add(singleVideo.getSnippet().getTitle());
		      }
		    }
		    
	      }
	      
	      
	    } catch (GoogleJsonResponseException e) {
	      System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	          + e.getDetails().getMessage());
	    } catch (IOException e) {
	      System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	    } catch (Throwable t) {
	      t.printStackTrace();
	    }
		  return list;
	  }
	

}
