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

public class SplayTree<T extends Comparable<? super T>> implements Iterable {
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
	public Iterator<BinaryNode> iterator() {
		return new preOrderTreeIterator(root);
	}
	
	/**
	 * Iterator that returns the nodes in preorder
	 * 
	 * @return 	an iterator to traverse the nodes in preorder
	 */
	public Iterator<T> inOrderIterator() {
		return new inOrderTreeIterator(root);
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
		Iterator<BinaryNode> i = iterator();
		ArrayList<Object> treeArrayList = new ArrayList<Object>();
		while(i.hasNext()) {
			treeArrayList.add(i.next().element);
		}
		//return (ArrayList<Object>) root.toArrayList(new ArrayList<T>());
		return treeArrayList;
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
		Iterator<BinaryNode> i = iterator();
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
		leftTree = null;
		rightTree = null;
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(root != null) {
			root = assembleTree(root.splay(item));
			
			int rootCompare = item.compareTo(root.element);
			if(rootCompare == 0) {
			    if(item instanceof Insertable){
			    	return ((Insertable)root.element).insert(item);
			    } else {
			    	return false;
				}
			}
			BinaryNode node = new BinaryNode(item);
			if(rootCompare > 0) {
				if(root.right != null) {
					node.right = root.right;
					root.right = null;
				}
				node.left = root;
			}
			if(rootCompare < 0) {
				if(root.left != null) {
					node.left = root.left;
					root.left = null;
				}
				node.right = root;
			}
			root = node;
			modCount++;
		} else {
			root = new BinaryNode(item);
			modCount++;
		}
		

		return true;
	}
	
	/**
	 * Removes the provided item from the SplayTree
	 * 
	 * @param item	the item that will be removed from the SplayTree
	 * @return		true if remove successful; false if not
	 * @exception	IllegalArgumentException if item is null
	 */
	public boolean remove(T item) {
		leftTree = null;
		rightTree = null;
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(root != null) {
			root = assembleTree(root.splay(item));
			
			if(item.compareTo(root.element) != 0) {
				return false;
			}
			BinaryNode node;
			if(root.left != null) {
				leftTree = null;
				rightTree = null;
				node = assembleTree(root.left.splay(findMax(root.left).element));
				node.right = root.right;
				root = node;
				//node.right = rightTree;
				//node.left = leftTree;
			} else {
				root = root.right;
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Find method that returns a pointer to the item provided
	 * 
	 * @param item item to be found in the BinaryNode
	 * @return pointer to item if found; null if not found
	 */
	public T find(T item) {
		leftTree = null;
		rightTree = null;
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(root != null) {
			root = assembleTree(root.splay(item));
			
			if(item.compareTo(root.element) == 0) {
				return root.element;
			}
		}
		return null;
	}
	
	
	private BinaryNode assembleTree(BinaryNode node) {
		BinaryNode A = node.left;
		BinaryNode B = node.right;
		if(leftTree != null) {
			node.left = leftTree;
			findMax(leftTree).right = A;
		}
		if(rightTree != null) {
			node.right = rightTree;
			findMin(rightTree).left = B;
		}
		return node;
	}
	
	private BinaryNode findMin(BinaryNode node) {
		while(node.left != null) {
			node = node.left;
		}
		return node;
	}
	
	private BinaryNode findMax(BinaryNode node) {
		while(node.right != null) {
			node = node.right;
		}
		return node;
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
		//public String toString() {
		//	return element.toString();
		//}
		
		public String toString() {
			String temp = this.element + ", ";
			if(left != null) {
				temp += left.element;
			} else {
				temp += "null";
			}
			temp += ", ";
			if(right != null) {
				temp += right.element;
			} else {
				temp += "null";
			}
			return temp;
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
		
		private BinaryNode splay(T item) {
			int thisCompare = item.compareTo(element);
			if(thisCompare == 0) {
				return this;
			} else if(thisCompare < 0) {	
				if(left != null && left.left != null && item.compareTo(left.element) < 0) {
					return this.rightZigZig(this).splay(item);
				} else if((left != null && left.left == null && item.compareTo(left.element) <= 0) || (left != null && item.compareTo(left.element) > 0)){
					return this.rightZig(this).splay(item);
				} else {
					//return parent
					return this;
				}
			} else {
				if(right != null && right.right != null && item.compareTo(right.element) > 0 ){
					return this.leftZigZig(this).splay(item);
				} else if((right != null && right.right == null && item.compareTo(right.element) >= 0) || (right != null && item.compareTo(right.element) < 0)) {
					return this.leftZig(this).splay(item);
				} else {
					//return parent
					return this;
				}
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
			//System.out.println("rightzig");
			BinaryNode x = new BinaryNode(node.element);
			x.right = node.right;
			BinaryNode y = node.left;
			
			if(rightTree != null) {
				findMin(rightTree).left = x;
			} else {
				rightTree = new BinaryNode(x.element);
				rightTree.right = x.right;
			}
			
			node = y;
			return node;
		}
		
		/**
		 * Method that rotates the tree left around the specified
		 * BinaryNode right child.
		 * @param node The node to rotate around
		 * @return node An BinaryNode that has been rotated
		 */
		public BinaryNode leftZig(BinaryNode node) {
			//System.out.println("leftzig");
			BinaryNode x = new BinaryNode(node.element);
			x.left = node.left;
			BinaryNode y = node.right;
			
			if(leftTree != null) {
				findMax(leftTree).right = x;
			} else {
				leftTree = new BinaryNode(x.element);
				leftTree.left = x.left;
			}
			
			node = y;
			return node;
		}
		
		public BinaryNode rightZigZig(BinaryNode node) {
			//System.out.println("rightzigzig");
			BinaryNode x = new BinaryNode(node.element);
			x.right = node.right;
			if(node.left.right != null) {
				x.left = node.left.right;
			} else {
				x.left = null;
			}
			BinaryNode y = new BinaryNode(node.left.element);
			y.right = x;
			
			BinaryNode z = node.left.left;
			if(rightTree != null) {
				findMin(rightTree).left = y;
			} else {
				rightTree = new BinaryNode(y.element);
				rightTree.right = x;
			}
			node = z;
			return node;
		}
		
		public BinaryNode leftZigZig(BinaryNode node) {
			//System.out.println("leftzigzig");
			BinaryNode x = new BinaryNode(node.element);
			x.left = node.left;
			if(node.right.left != null) {
				x.right = node.right.left;
			} else {
				x.right = null;
			}
			BinaryNode y = new BinaryNode(node.right.element);
			y.left = x;
			
			BinaryNode z = node.right.right;
			if(leftTree != null) {
				findMax(leftTree).right = y;
			} else {
				leftTree = new BinaryNode(y.element);
				leftTree.left = x;
			}
			node = z;
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
	private class preOrderTreeIterator implements Iterator<BinaryNode> {
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
		public BinaryNode next() {
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
			return item;
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
