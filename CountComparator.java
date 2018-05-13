import java.util.Comparator;

public class CountComparator implements Comparator<WordCount>{
	public int compare(WordCount c1, WordCount c2) {
		if (c1 != null && c2 != null) {
			return (int) (c2.i - c1.i);
		} else {
			return 0;
		}
	}
}
