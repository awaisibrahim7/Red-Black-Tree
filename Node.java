
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodríguez
 *
 * Programming Assignment #1
 *
 * Create a Heap class using nodes and then use it
 * for a Restaurant seating program
 * 
 * Name: Awais Ibrahim
 */
/* 
 * This class implements a Node of a generic type that has a value,
 *  a link to the nodes on its left and right and a link to its parent.
 */
public class Node<K extends Comparable, V extends Comparable> 
{
	private K key;
	private V value;
	boolean color;
	private Node<K,V> left;
	private Node<K,V> right;
	private Node<K,V> parent;
	public static final boolean RED = true;
	private static final boolean BLACK = false;
	/*
	 * This first constructor takes in a key and a value and creates a single
	 * node with no links to any children or a parent.
	 */
	public Node(K key, V value)
	{
		this.key = key;
		this.value = value;
		this.color = BLACK;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	/*
	 * This second constructor takes in a key, a value and a color and creates a single
	 * node with no links to any children or a parent.
	 */
	public Node(K key, V value, boolean color)
	{
		this.key = key;
		this.value = value;
		this.color = color;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	/*
	 * This next constructor creates a node with a key, a value, as well as
	 * a left, right and parent node.
	 */
	public Node(K key, V value, boolean color, Node<K,V> left, Node<K,V> right, Node<K,V> parent)
	{
		this.key = key;
		this.value = value;
		this.color = color;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	/*
	 * This method is a "getter" in which it returns the value of the 
	 * private instance variable value.
	 */
	public V getValue() 
	{
		return value;
	}
	/*
	 * This method returns the private instance variable key.
	 */
	public K getKey() 
	{
		return key;
	}
	/*
	 * This method returns the private instance variable color.
	 */
	public boolean getColor()
	{
		return color;
	}
	
	/*
	 * This method is a "setter" in which it sets the value of the 
	 * private instance variable key to the parameter key.
	 */
	public void setKey(K key) 
	{
		this.key = key;
	}
	/*
	 * This method is a "setter" in which it sets the value of the 
	 * private instance variable value to the parameter value.
	 */
	public void setValue(V value) 
	{
		this.value = value;
	}
	
	public void setColor(boolean color)
	{
		this.color = color;
	}
	/*
	 * This method is a "getter" in which it returns the value of the 
	 * private instance variable left returning the left node.
	 */
	public Node<K,V> getLeft() 
	{
		return left;
	}
	/*
	 * This method is a "getter" in which it returns the value of the 
	 * private instance variable right and returns the right node.
	 */
	public Node<K,V> getRight() 
	{
		return right;
	}
	/*
	 * This method is a "getter" in which it returns the value of the 
	 * private instance variable parent of the current node.
	 */
	public Node<K,V> getParent()
	{
		return parent;
	}
	/*
	 * This method is a "setter" in which it sets the value of the 
	 * private instance variable left.
	 */
	public void setLeft(Node<K,V> left) 
	{
		this.left = left;
		left.setParent(this);
	}
	/*
	 * This method is a "setter" in which it sets the value of the 
	 * private instance variable right.
	 */
	public void setRight(Node<K,V> right) 
	{
		this.right = right;
		right.setParent(this);
	}
	/*
	 * This method checks if the current node has a left node
	 * returning either true or false.
	 */
	public boolean hasLeft()
	{
		try
		{
			if(getLeft() != null)
				return true;
			else
				return false;
		}
		catch(NullPointerException e)
		{
			return false;
		}
	}
	/*
	 * This method checks if the current node has a right node
	 * returning either true or false.
	 */
	public boolean hasRight()
	{
			if(getRight() != null)
				return true;
			else
				return false;
	}
	/*
	 * This method checks if the current node is the left node
	 * of the current node.
	 */
	public boolean isLeftOf(Node<K,V> parent)
	{
		if(parent.getLeft() == this)
			return true;
		else
			return false;
	}
	//Checks if this node is a left child.
	public boolean isLeft()
	{
		if(this.isLeftOf(this.getParent()))
			return true;
		else
			return false;
	}
	public boolean isRight()
	{
		if(this.isRightOf(this.getParent()))
			return true;
		else
			return false;
	}
	/*
	 * This method checks if the current node is the right node
	 * of the current node.
	 */
	public boolean isRightOf(Node<K,V> parent)
	{
		if(parent.getRight() == this)
			return true;
		else
			return false;
	}
	/*
	 * This method sets the parent
	 * of the current node.
	 */
	public void setParent(Node<K,V> parent)
	{
		this.parent = parent;
	}
	/*
	 * This method checks if the current node is a leaf node
	 * meaning it has no children.
	 */
	 public boolean isLeaf()
	  {
		  if(this.getLeft() == null && this.getRight() == null)
			  return true;
		  else
			  return false;
	  }
	 public boolean isInternal()
	 {
		 if((this.isLeft() && this.parent.isRight()) || (this.isRight() && this.parent.isLeft()))
			 return true;
		 else 
			 return false;
	 }
	 
	 public boolean isExternal()
	 {
		 if((this.isLeft() && this.parent.isLeft()) || (this.isRight() && this.parent.isRight()))
			 return true;
		 else 
			 return false;
	 }
	 /*
		 * This method compares two node to see if one is greater than
		 * the other or equal to it.
		 */
	 public int compareTo(Node<K,V> current)
	 {
		 if(value.compareTo(current.getValue()) > 0)
			 return 1;
		 else if(value.compareTo(current.getValue()) == 0 )
			 return 0;
		 else
			 return -1;
	 }
}

