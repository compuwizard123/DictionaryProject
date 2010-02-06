import java.util.ArrayList;


public class Entry implements Comparable<Entry>, Insertable {
	ArrayList<String> definitions = new ArrayList<String>();
	String word;
	
	public Entry(String word, String definition) {
		this.word = word;
		definitions.add(definition);
	}

	public int compareTo(Entry item) {
		return item.getEntry().compareTo(this.word);
	}

	public ArrayList<String> getDefinitions() {
		return definitions;
	}

	public boolean insert(Object o) {
		
		return false;
	}
	
	public String getEntry() {
		return this.word;
	}
}
