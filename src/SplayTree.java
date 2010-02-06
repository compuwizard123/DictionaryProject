import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A SplayTree implementation class
 * @author risdenkj
 * 
 */

public class SplayTree<T extends Comparable<? super T>> implements Iterable<T> {
	private BinaryNode root, leftTree, rightTree;
	private int modCount = 0;
	
	/**
	 * Constructs a SplayTree
	 * Sets the root to null
	 */
	public SplayTree() {
		root = null;
	}

	/**
	 * Checks if the SplayTree has no nodes
	 * 
	 * @return 	true if the SplayTree has no nodes; false if has nodes
	 */
	public boolean isEmpty() {
		return root == null ? true : false;
	}
	
	/**
	 * Default iterator method returns the nodes in order
	 * 
	 * @return 	an iterator to traverse the nodes in order 
	 */
	public Iterator<T> iterator() {
		return new inOrderTreeIterator(root);
	}
	
	/**
	 * Iterator that returns the nodes in preorder
	 * 
	 * @return 	an iterator to traverse the nodes in preorder
	 */
	public Iterator<T> preOrderIterator() {
		return new preOrderTreeIterator(root);
	}
	
	/**
	 * Method that returns an ArrayList representation of the SplayTree
	 * 
	 * @return 	ArrayList with the nodes in order
	 */
	public ArrayList<Object> toArrayList() {
		if(root == null) {
			return new ArrayList<Object>();
		}
		return (ArrayList<Object>) root.toArrayList(new ArrayList<T>());
	}
	
	/**
	 * Method that returns an Array representation of the SplayTree
	 * 
	 * @return 	Array with the nodes in order
	 */
	public Object[] toArray() {
		return toArrayList().toArray();
	}

	/**
	 * Method to determine the height of the SplayTree
	 * 
	 * @return 	height of the SplayTree; -1 if SplayTree is empty
	 */
	public int height(){
		return !isEmpty() ? root.height() : -1;
	}
	
	/**
	 * Method that returns a String representation of the SplayTree
	 * 
	 * @return 	string in [element, element] format with the SplayTree BinaryNodes in order 
	 */
	public String toString() {
		String temp = "";
		if(root == null) {
			return temp;
		}
		Iterator<T> i = iterator();
		while(i.hasNext()) {
			temp += "[" + i.next() + "]";
			if(i.hasNext()) {
				temp += ", ";
			}
		}
		return temp;
	}
	
	/**
	 * Method to determine the size of the SplayTree
	 * 
	 * @return 	size of the SplayTree; 0 if SplayTree is empty
	 */
	public int size() {
		return !isEmpty() ? root.size() : 0;
	}
	
	/**
	 * Returns a boolean value representing whether the tree was modified
	 * or not. The item argument must be of the same type that was used 
	 * when initiating the SplayTree class.
	 *
	 * @param item	the item to be inserted into the SplayTree
	 * @return      true if the tree was modified, false if not
	 * @exception	IllegalArgumentException if item is null
	 */
	public boolean insert(T item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(root != null) {
			return root.insert(item);
		} else {
			root = new BinaryNode(item);
			modCount++;
			return true;
		} 
	}
	
	/**
	 * Removes the provided item from the SplayTree
	 * 
	 * @param item	the item that will be removed from the SplayTree
	 * @return		true if remove successful; false if not
	 * @exception	IllegalArgumentException if item is null
	 */
	public boolean remove(T item) {
		modWrapper mod = new modWrapper();
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(root != null) {
			root = root.remove(item, mod);
		}
		if(mod.getValue()) {
			modCount++;
		}
		return mod.getValue();
	}
	
	/**
	 * Get method that returns a pointer to the item provided
	 * 
	 * @param item item to be found in the SplayTree
	 * @return pointer to item if found; null if not found
	 * @exception IllegalArgumentException if item is null
	 */
	public BinaryNode find(T item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		return root.find(item);
	}
	
	/**
	 * A BinaryNode Implementation Class
	 * @author risdenkj
	 * 
	 */
	private class BinaryNode {
		private T element;
		private BinaryNode left,right;
		
		/**
		 * Constructs a BinaryNode
		 * Sets the left and right children to null
		 * 
		 * @param initelement	The element that becomes the BinaryNode
		 */
		public BinaryNode(T initelement) {
			element = initelement;
			left = null;
			right = null;
		}
		
		/**
		 * Returns the string representation of the current BinaryNode
		 * 
		 * @return	string of the current BinaryNode
		 */
		public String toString() {
			return element.toString();
		}
		
		/**
		 * Recursive method that returns an ArrayList of the BinaryNode and its children
		 * 
		 * @param list	the ArrayList that elements should be added onto
		 * @return 	ArrayList of the BinaryNode and its children
		 */
		public ArrayList<T> toArrayList(ArrayList<T> list) {
			if(left != null) {
				left.toArrayList(list);
			}
			list.add(element);
			if(right != null) {
				right.toArrayList(list);
			}
			return list;
		}
		
		/**
		 * Method that determines the height of the BinaryNode
		 * 
		 * @return 	height of the BinaryNode
		 */
		public int height() {
			int leftheight = 0, rightheight = 0;
			if(left != null) {
				leftheight = 1 + left.height();
			}
			if(right != null) {
				rightheight = 1 + right.height();
			}
			if(leftheight > rightheight) {
				return leftheight;
			} else { 
				return rightheight;
			}
		}
		
		/**
		 * Method that determines the size of the BinaryNode 
		 * 
		 * @return 	size of the BinaryNode
		 */
		public int size() {
			int size = 1;
			if(left != null) {
				size += left.size();
			}
			if(right != null) {
				size += right.size();
			}
			return size;
		}
		
		/**
		 * Inserts the provided element as a child to the BinaryNode
		 * The item becomes a left child if less than current BinaryNode
		 * The item becomes a right child if greater than current BinaryNode
		 * If the insert is successful adds 1 to the modCount
		 * 
		 * @param item	item to be inserted as a child to the BinaryNode
		 * @return 	true if insert successful; false if not
		 */
		public boolean insert(T item) {
			if(element.compareTo(item) < 0) {
				if(right != null) {
					return right.insert(item);
				} else {
					right = new BinaryNode(item);
					modCount++;
					return true;
				}
			} else if(element.compareTo(item) > 0){
				if(left != null) {
					return left.insert(item);
				} else {
					left = new BinaryNode(item);
					modCount++;
					return true;
				}
			} else {
				return false;
			}
		}
		
		/**
		 * Removes the provided item from the BinaryNode
		 * In the event of the BinaryNode having two children, the
		 * algorithm finds the largest left child.
		 * 
		 * @param item 	the item that will be removed from the BinaryNode
		 * @param mod 	ModWrapper boolean that will be set to true if remove successful
		 * @return 	BinaryNode that is removed
		 */
		public BinaryNode remove(T item, modWrapper mod) {
			if(left == null && right == null) {
				if(item.compareTo(element) == 0) {
					mod.setTrue();
					return null;
				}
				return this;
			} else if(right == null) {
				if(item.compareTo(element) < 0) {
					left = left.remove(item, mod);
				}
				mod.setTrue();
				return left;
			} else if(left == null) {
				if(item.compareTo(element) > 0) {
					right = right.remove(item, mod);
				}
				mod.setTrue();
				return right;
			} else {
				if(item.compareTo(element) > 0) {
					right = right.remove(item,mod);
				} else if(item.compareTo(element) < 0) {
					left = left.remove(item, mod);
				} else {
					T temp = element;
					BinaryNode largestChildNode = findLargestChild(left);
					element = largestChildNode.element;
					largestChildNode.element = temp;
					left = left.remove(temp, mod);
				}
				return this;
			}
		}
		
		/**
		 * Method that finds the largest left child
		 * 
		 * @param node	BinaryNode to look for largest left child
		 * @return 	the largest left child of the provided BinaryNode
		 */
		public BinaryNode findLargestChild(BinaryNode node) {
			while(node.right != null) {
				node = node.right;
			}
			return node;
		}
		
		/**
		 * Find method that returns a pointer to the item provided
		 * 
		 * @param item item to be found in the BinaryNode
		 * @return pointer to item if found; null if not found
		 */
		public BinaryNode find(T item) {
			if(item.compareTo(element) > 0) {
				return right.find(item);
			} else if(item.compareTo(element) < 0) {
				return left.find(item);
			} else {
				return this;
			}
		}
		
		/**
		 * Method that rotates the tree right around the specified
		 * AVLNode left child.
		 * 
		 * @param node The node to rotate around
		 * @return node An AVLNode that has been rotated
		 */
		public BinaryNode rightZig(BinaryNode node) {
			BinaryNode temp1 = node.left;
			BinaryNode temp2 = new BinaryNode(node.element); 
			temp2.right = node.right;
			temp2.left = temp1.right;
			node.left = temp1.left;
			node.element = temp1.element;
			node.right = temp2;
            
			return node;
		}
		
		/**
		 * Method that rotates the tree left around the specified
		 * BinaryNode right child.
		 * @param node The node to rotate around
		 * @return node An BinaryNode that has been rotated
		 */
		public BinaryNode leftZig(BinaryNode node) {
			BinaryNode temp1 = node.right;
			BinaryNode temp2 = new BinaryNode(node.element); 
			temp2.left = node.left;
			temp2.right = temp1.left;
			node.right = temp1.right;
			node.element = temp1.element;
			node.left = temp2;
			
			return node;
		}
		
		public BinaryNode rightZigZig(BinaryNode node) {
			return node;
		}
		
		public BinaryNode leftZigZig(BinaryNode node) {
			return node;
		}
	}
	
	/**
	 * Creates a wrapper for the mod boolean
	 * @author risdenkj
	 * 
	 */
	private class modWrapper {
		private boolean mod = false;
		
		public void setTrue() {
			this.mod = true;
		}
		
		public void setFalse() {
			this.mod = false;
		}

		public boolean getValue() {
			return mod;
		}
	}
	
	/**
	 * A preorder SplayTree iterator implementation class
	 * @author risdenkj
	 * 
	 */
	private class preOrderTreeIterator implements Iterator<T> {
		private Stack<BinaryNode> list = new Stack<BinaryNode>();
		private BinaryNode node = null;
		private int mod;
		
		/**
		 * Constructs a preOrderTreeIterator
		 * Sets the modification boolean flag to false
		 * 
		 * @param node	BinaryNode to start the iterator from
		 */
		public preOrderTreeIterator(BinaryNode node) {
			if(node != null) {
				list.push(node);
				this.mod = modCount;
			}
		}
		
		/**
		 * Checks if there is another element in the SplayTree that hasn't been accessed
		 * 
		 * @return	true if there is another element to return; false if not
		 */
		public boolean hasNext() {
			return !list.empty();
		}

		/**
		 * Method that returns the next BinaryNode element from the SplayTree
		 * 
		 * @return BinaryNode	element in the SplayTree
		 * @exception 	ConcurrentModificationException if the SplayTree was modified after initializing the iterator
		 * @exception 	NoSuchElementException if there are no more elements to return
		 */
		public T next() {
			if(this.mod != modCount) {
				throw new ConcurrentModificationException();
			}
			BinaryNode item = null;
			
			if(!list.empty()) {
				item = list.pop();
			} else {
				throw new NoSuchElementException();
			}
			
			if(item.right != null) {
				list.push(item.right);
			}
			if(item.left != null) {
				list.push(item.left);
			}
			node = item;
			return item.element;
		}
		
		/**
		 * Removes an element from the SplayTree
		 * 
		 * @exception	IllegalStateException if next() not called before
		 */
		public void remove() {
			if(node == null) {
				throw new IllegalStateException();
			}
			if(SplayTree.this.remove(node.element)) {
				node = null;
				mod++;
			}
		}
	}
	
	/**
	 * An in order SplayTree iterator implementation class
	 * @author risdenkj
	 * 
	 */
	private class inOrderTreeIterator implements Iterator<T> {
		private Stack<BinaryNode> list = new Stack<BinaryNode>();
		private BinaryNode node = null;
		private int mod;
		
		/**
		 * Constructs an inOrderTreeIterator
		 * Sets the modification boolean flag to false
		 * 
		 * @param node	BinaryNode to start the iterator from
		 */
		public inOrderTreeIterator(BinaryNode node) {
			this.mod = modCount;
			checkLeft(node);
		}
		
		/**
		 * Checks if there is another element in the SplayTree that hasn't been accessed
		 * 
		 * @return 	true if there is another element to return; false if not
		 */
		public boolean hasNext() {
			return !list.empty();
		}
		
		/**
		 * Method that returns the next BinaryNode element from the SplayTree
		 * 
		 * @return BinaryNode	element in the SplayTree
		 * @exception 	ConcurrentModificationException if the SplayTree was modified after initializing the iterator
		 * @exception 	NoSuchElementException if there are no more elements to return
		 */
		public T next() {
			if(this.mod != modCount) {
				throw new ConcurrentModificationException();
			}
			BinaryNode item = null;
			if(list.empty()) {
				throw new NoSuchElementException();
			}
			item = list.pop();
			checkLeft(item.right);
			node = item;
			return item.element;
		}

		/**
		 * Checks if the provided BinaryNode has a left child
		 * 
		 * @param node	node to to check if it has a left child
		 */
		public void checkLeft(BinaryNode node) {
			while(node != null) {
				list.push(node);
				node = node.left;
			}
		}
		
		/**
		 * Removes an element from the SplayTree
		 * 
		 * @exception	IllegalStateException if next() not called before
		 */
		public void remove() {
			if(node == null) {
				throw new IllegalStateException();
			}
			if(SplayTree.this.remove(node.element)) {
				node = null;
				mod++;
			}
		}
	}
}
