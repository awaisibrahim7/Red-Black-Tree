//Creates a binary search tree object
import java.util.*;
import java.util.LinkedList;
public class BST<K extends Comparable<K>, V extends Comparable> implements Tree<K,V> 
{
	private Node<K,V> root;
	private Node<K,V> found;
	public Node<K,V> added;
	private int size;
	
	public BST()
	{
		root = null;
		found = null;
		added = null;
		size = 0;
	}
	public BST(Node<K,V> root)
	{
		this.root = root;
		found = null;
		added = null;
		size = 1;
	}
	public Node<K,V> getRoot()
	{
		return root;
	}
	//returns the private instance variable found
		public Node<K,V> getFound()
		{
			return found;
		}
	//returns the private instance variable added
	public Node<K,V> getAdded()
	{
		return added;
	}
	//Sets the value for the private instance variable root
	public void setRoot(Node<K,V> root)
	{
		this.root = root;
	}
	//Adds a new node to the tree
	public void add(K key, V val)
	{
		Node<K,V> newNode = new Node<K,V>(key,val);
		boolean added = false;
		if(size == 0)
		{
			root = newNode;
			newNode.setColor(false);
		}
		else
		{
			Node<K,V> temp = root;
			while(added == false)
			{
				if(newNode.getKey().compareTo(temp.getKey()) <= 0)
				{
					if(temp.getLeft() == null)
					{
						temp.setLeft(newNode);
						newNode.setParent(temp);
						added = true;
					}
					else
						temp = temp.getLeft();
				}
				else
				{
					if(temp.getRight() == null)
					{
						temp.setRight(newNode);
						newNode.setParent(temp);
						added = true;
					}
					else
						temp = temp.getRight();
				}
			}
		}
		size++;
		this.added = newNode;
	}

	public V lookup(K key)
	{
		Node<K,V> temp = root;
		while(temp != null)
		{
			if(temp.getKey() == key)
			{
				found = temp;
				return temp.getValue();
			}
			else if(temp.getKey().compareTo(key) > 0)
				temp = temp.getLeft();
			else
				temp = temp.getRight();
		}
		return null;
	}
	public Node<K,V> find(K key)
	{
		Node<K,V> temp = root;
		while(temp != null)
		{
			if(temp.getKey() == key)
			{
				found = temp;
				return temp;
			}
			else if(temp.getKey().compareTo(key) > 0)
				temp = temp.getLeft();
			else
				temp = temp.getRight();
		}
		return null;
	}
	
	public V remove(K key)
	{
		V val = lookup(key);
		if(found == null)
			return null;
		else
		{
			if(found.isLeaf())
			{
				if(found.isLeftOf(found.getParent()))
					found.getParent().setLeft(null);
				else
					found.getParent().setRight(null);
			}
		
			else if(found.getRight() == null) 
			{
				if(found.isLeftOf(found.getParent()))
					found.getParent().setLeft(found.getLeft());
				else
					found.getParent().setRight(found.getLeft());
			}		
			else if(found.getLeft() == null)
			{
				if(found.isLeftOf(found.getParent()))
					found.getParent().setLeft(found.getRight());
				else
					found.getParent().setRight(found.getRight());
			}	
		
			else if(found.hasLeft() && found.hasRight())
			{
				Node<K,V> predecessor = findInorderPredecessor(found);
				found.setKey(predecessor.getKey());
				found.setValue(predecessor.getValue());
				if(predecessor.isRightOf(predecessor.getParent()))
					predecessor.getParent().setRight(null);
				else
					predecessor.getParent().setLeft(null);
			}
		}
		if(found != null)
			size--;
		return val;
	}
	public Node<K,V> findInorderPredecessor(Node<K,V> current)
	{
		Node<K,V> temp = current;
		temp = temp.getLeft();
		while(temp.getRight() != null)
			temp = temp.getRight();
		return temp;
	}
	public int size()
	{
		return size;
	}
	
	public String toPrettyString()
	{
		  LinkedList<Node<K,V>> q = new LinkedList<Node<K,V>>();
		  Node<K,V> temp = root;
		  Node<K,V> temp2;
		  String ans = "";
		  int levels = 0;
		  if(size == 0)
			  return null;
		  else
		  {
			  q.add(temp);
			  for(int i = 0; i < size; i++)
			  {
				  temp = q.get(i);
				  if(temp.hasLeft() == true)
					  q.add(temp.getLeft());
				  if(temp.hasRight() == true)
					  q.add(temp.getRight());
			  }
			  temp2 = q.get(size-1);
			  while(temp2.getParent() != root)
			  {
				  levels++;
				  temp2 = temp2.getParent();
			  }
			  for(int i = 0; i < size; i++)
			  {
				  Node<K,V> current = q.remove();
				  Node<K,V> temp3 = current;
				  int prevDepth = 0;
				  int depth = 0;
				  while(temp3 != root)
				  {
					  temp3 = temp3.getParent();
					  depth++;
				  }
				  if(depth != prevDepth)
					  ans += "/n";
				  if(current == root)
				  {
					  for(int j = 0; j < levels; j++)
						  ans += " ";
				  }
				  else if((current.isLeft() && current.isExternal()) || current.isRight() && current.isExternal())
				  {
					  for(int j = depth; j < levels; j++)
						  ans += " ";
				  }
				  else if((current.isRight() && current.isInternal()) || current.isLeft() && current.isInternal())
				  {
					  for(int j = 0; j < Math.ceil((double)(levels - depth)); j++)
						  ans += " ";
				  }
				  if(current.getColor() == true)
					  ans += ("*" + current.getKey() + "*");
				  else
					  ans += current.getKey();
			  }
			  
		  }
		  return ans;
	}
	
	 public String toString()
	  {
		  String ans = "";
		  Node<K,V> temp = root;
		  ans = ans + temp.getKey()+ " ";
		  ans += temp.getLeft().getKey();
		  ans = ans + " "  + temp.getRight().getKey();
		  ans += " " + temp.getLeft().getLeft().getKey();
		  ans += " " + temp.getLeft().getRight().getKey();
		  ans += " " + temp.getRight().getRight().getKey();

		  return ans;
	  }

public static void main(String[] args)
{
	int[] num = {6,3,12,2,21,4};
	BST<Integer,Integer> tree = new BST();
	for(int i = 0; i < num.length; i++)
	{
		tree.add(num[i], i);
		System.out.println("Size: " + tree.size());
	}
	tree.remove(12);
	//System.out.println(tree.lookup(21));
	System.out.println("Size: " + tree.size());
	System.out.println(tree);
}
}