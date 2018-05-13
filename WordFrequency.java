import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class WordFrequency {
	Map words = new HashMap<String, WordCount>();
	//String delim = " \t\n.,:;?!-/()[]\"\'";
	String delim = " ";
	public void clearHashtag(String fileName, String Hashtag){
		
	}
	public void readFile(String fileName, String hashtag) throws IOException{
		String line, word;
		WordCount count;
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			while((line = in.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line, delim);
				while (st.hasMoreTokens()){
					word = st.nextToken().toLowerCase();
					count = (WordCount) words.get(word);
					if(count == null){
						words.put(word, new WordCount(word,1));
					}else{
						count.i++;
					}
				}
			}
			words.remove(hashtag);
			in.close();
		} finally{	
		}
		int rankingCount = 0;
		int rankingLength = 10;
		List list = new ArrayList<WordCount>(words.values());
		Collections.sort(list, new CountComparator());
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			count = (WordCount) iter.next();
			word = count.word;
			System.out.println(word + (word.length() < 8 ? "\t\t" : "\t") + count.i);
			if(rankingCount == rankingLength){
				break;
			}
			rankingCount++;
		}
	}
	
}
