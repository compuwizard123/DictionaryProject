import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;



public class Testing extends TestCase{

public static int points = 0; 
	 
	
/////////////////test of inserting into a SplayTree
	
	public void testInsertIntoEmptyTree(){
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(5);
		ArrayList<Object> test = new ArrayList<Object>();
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 2;
	}

	public void testSimpleInsertIntoTree(){
		// insert on left
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(3);
		ArrayList<Object> test = new ArrayList<Object>();
		test.add(3);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 2;
		
		// insert on right
		s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(7);
		test = new ArrayList<Object>();
		test.add(7);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 2;
		
		// insert duplicate
		assertFalse(s.insert(7));
		points += 2;
	}
	
	public void testInsertIntoTreeTwoZigs(){
		// two zigs
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(3);
		s.insert(4);
		ArrayList<Object> test = new ArrayList<Object>();
		test.add(4);
		test.add(3);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 10;
		
		// two zigs, symmetric case
		s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(7);
		s.insert(6);
		test = new ArrayList<Object>();
		test.add(6);
		test.add(5);
		test.add(7);
		assertEquals(test, s.toArrayList());
		points += 10;
	}
	
	public void testInsertIntoTreeZigZig(){
		// before zigzig
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(3);
		s.insert(4);
		s.insert(2);
		ArrayList<Object> test = new ArrayList<Object>();
		test.add(2);
		test.add(3);
		test.add(4);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 5;
		
		// zigzig		
		s.insert(6);
		test = new ArrayList<Object>();
		test.add(6);
		test.add(5);
		test.add(3);
		test.add(2);
		test.add(4);
		assertEquals(test, s.toArrayList());
		points += 5;
		
		// before zigizig, symmetric case
		s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(7);
		s.insert(6);
		s.insert(8);
		
		test = new ArrayList<Object>();
		test.add(8);
		test.add(7);
		test.add(6);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 5;
		
		s.insert(4);
		test = new ArrayList<Object>();
		test.add(4);
		test.add(5);
		test.add(7);
		test.add(6);
		test.add(8);
		assertEquals(test, s.toArrayList());
		points += 5;
	}
	
///////////////test of find for a SplayTree
	


	public void testFindOfElement(){
		
		// splay on right side (mostly)
		// zigzig
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(3);
		s.insert(2);
		s.insert(1);
		s.find(5);

		ArrayList<Object> test = new ArrayList<Object>();
		test.add(5);
		test.add(2);
		test.add(1);
		test.add(3);
		assertEquals(test, s.toArrayList());
		points += 2;

		// "zigzag"
		s.find(3);
		test = new ArrayList<Object>();
		test.add(3);
		test.add(2);
		test.add(1);
		test.add(5);
		assertEquals(test, s.toArrayList());		
		points += 2;
		
		// splay on left side (mostly)
		// zigzig
		s = new SplayTree<Integer>();
		s.insert(5);
		s.insert(6);
		s.insert(7);
		s.insert(8);
		s.find(5);

		test = new ArrayList<Object>();
		test.add(5);
		test.add(7);
		test.add(6);
		test.add(8);
		assertEquals(test, s.toArrayList());
		points += 2;
		
		// "zigzag"
		s.find(6);
		test = new ArrayList<Object>();
		test.add(6);
		test.add(5);
		test.add(7);
		test.add(8);
		assertEquals(test, s.toArrayList());
		points += 2;
	}
	
	public void testFindOfNoElement(){

		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(9);
		s.insert(7);
		s.insert(5);
		s.insert(3);
		s.insert(1);		
		s.find(8);

		ArrayList<Object> test = new ArrayList<Object>();
		test.add(9);
		test.add(3);
		test.add(1);
		test.add(7);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 3;
		
		s.find(2);		

		test = new ArrayList<Object>();
		test.add(1);
		test.add(3);
		test.add(9);
		test.add(7);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 3;
	}
	
///////////////test of removing from a SplayTree
	
	public void testRemoveFromEmptyTree(){
		SplayTree<Integer> s = new SplayTree<Integer>();
		assertFalse(s.remove(5));
		points += 2;
	}
	
	public void testRemoveFromOneElementTree(){
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(4);
		assertFalse(s.remove(5));
		assertTrue(s.toArrayList().size() == 1);
		assertTrue(s.remove(4));
		assertTrue(s.toArrayList().size() == 0);
		points += 2;
	}
	
	public void testRemoveFromTwoElementTree(){
		// no left node
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(4);
		s.insert(3);
		assertFalse(s.remove(2));
		assertTrue(s.toArrayList().size() == 2);
		assertTrue(s.remove(3));
		assertTrue(s.toArrayList().size() == 1);
		assertEquals(4, s.toArrayList().get(0));
		points += 3;
		
		// no right node
		s = new SplayTree<Integer>();
		s.insert(3);
		s.insert(4);
		assertFalse(s.remove(5));
		assertTrue(s.toArrayList().size() == 2);
		assertTrue(s.remove(4));
		assertEquals(3, s.toArrayList().get(0));
		assertTrue(s.toArrayList().size() == 1);
		points += 3;
	}
	
	public void testRemoveFromTree(){
		// elements are in the tree
		SplayTree<Integer> s = new SplayTree<Integer>();
		s.insert(4);
		s.insert(3);
		s.insert(2);
		s.insert(1);
		s.insert(6);
		s.insert(5);
		assertTrue(s.remove(5));
		ArrayList<Object> test = new ArrayList<Object>();
		test.add(4);
		test.add(2);
		test.add(1);
		test.add(3);
		test.add(6);
		assertEquals(test, s.toArrayList());
		points += 4;
		
		assertTrue(s.remove(4));
		test = new ArrayList<Object>();
		test.add(3);
		test.add(2);
		test.add(1);
		test.add(6);
		assertEquals(test, s.toArrayList());
		points += 4;
		
		// elements are not in tree
		s = new SplayTree<Integer>();
		s.insert(6);
		s.insert(4);
		s.insert(5);
		s.insert(3);
		s.insert(2);
		s.insert(1);

		assertFalse(s.remove(7));

		test = new ArrayList<Object>();
		test.add(6);
		test.add(2);
		test.add(1);
		test.add(4);
		test.add(3);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 4;
		
		assertFalse(s.remove(0));
		test = new ArrayList<Object>();
		test.add(1);
		test.add(2);
		test.add(6);
		test.add(4);
		test.add(3);
		test.add(5);
		assertEquals(test, s.toArrayList());
		points += 3;
	}
	
	public void testDictionary(){
		SplayTree<Entry> s = new SplayTree<Entry>();
		s.insert(new Entry("tree", "green"));
		s.insert(new Entry("fun", "data structures"));
		s.insert(new Entry("hmm", "donuts"));
		s.insert(new Entry("tree", "balanced"));
		s.insert(new Entry("tree", "red/black"));
		ArrayList<Object> t = s.toArrayList();
		assertEquals(3, t.size());
		ArrayList<String> defs = ((Entry) t.get(0)).getDefinitions();
		ArrayList<String> vals = new ArrayList<String>();
		vals.add("green");
		vals.add("balanced");
		vals.add("red/black");
		assertEquals(vals, defs);
		defs = ((Entry) t.get(1)).getDefinitions();
		vals = new ArrayList<String>();
		vals.add("donuts");
		assertEquals(vals, defs);
		defs = ((Entry) t.get(2)).getDefinitions();
		vals = new ArrayList<String>();
		vals.add("data structures");
		assertEquals(vals, defs);
		points += 13;
	}
	
	
	public void testNothing(){
		System.out.println(points);
	}

	public static void main(String args[]) {
		junit.swingui.TestRunner.run(Testing.class);
	}
}


