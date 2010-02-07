import java.util.ArrayList;
import java.util.Iterator;


public class Entry implements Comparable<Entry>, Insertable {
	ArrayList<String> definitions = new ArrayList<String>();
	String word;
	
	public Entry(String word, String definition) {
		this.word = word;
		this.definitions.add(definition);
	}

	public int compareTo(Entry item) {
		return item.getEntry().compareTo(this.word);
	}

	public ArrayList<String> getDefinitions() {
		return definitions;
	}

	public boolean insert(Object o) {
		ArrayList<String> defs = ((Entry) o).getDefinitions();
		Iterator<String> i = defs.iterator();
		while(i.hasNext()) {
			definitions.add(((String) i.next()));
		}
		return true;
	}
	
	public String getEntry() {
		return this.word;
	}
}
