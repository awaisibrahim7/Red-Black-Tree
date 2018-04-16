//Creates a Red Black Tree that self balances itself.
public class RedBlackTree<K extends Comparable<K>, V extends Comparable> extends BST<K,V> implements Tree<K,V> 
{
	private Node<K,V> added = super.getAdded();
	private Node<K,V> found = super.getFound();

	public RedBlackTree()
	{
		super();
	}
	
	public RedBlackTree(Node<K,V> root)
	{
		super(root);
	}
	
	public void add(K key, V value)
	{
		Node<K,V> temp = null;
		Node<K,V> root = super.getRoot();
		Node<K,V> newNode = new Node<K,V>(key,value);
		Node<K,V> newNode2 = new Node<K,V>(null,null);
		Node<K,V> newNode3 = new Node<K,V>(null,null);
		if(super.getRoot() == null)
		{
			super.setRoot(newNode);
			root = newNode;
			return;
		}
		while(root != null)
		{
			temp = root;
			if(newNode.getKey().compareTo(root.getKey()) < 0)
				root = root.getLeft();
			else
				root = root.getRight();
		}
		newNode.setParent(temp);
		if(temp == null)
			super.setRoot(newNode);
		else if(newNode.getKey().compareTo(temp.getKey()) < 0)
			temp.setLeft(newNode);
		else
			temp.setRight(newNode);
		newNode.setLeft(newNode2);
		newNode.setRight(newNode3);
		newNode.setColor(true);
		fixTreeAdd(newNode);
	}
	
	public void addCase1()
	{
		Node<K,V> uncle = findUncle(added);
		Node<K,V> grandparent = findGrandparent(added);
		added.getParent().setColor(false);
		uncle.setColor(false);
		grandparent.setColor(true);
		fixTreeAdd(grandparent);
	}
	public void addCase2()
	{
		leftRotate(added);
		fixTreeAdd(added.getLeft());		
	}
	public void addCase3()
	{
		Node<K,V> grandparent = findGrandparent(added);
		rightRotate(grandparent);
	}
	
	public Node<K,V> findUncle(Node<K,V> current)
	{
		Node<K,V> grandparent = findGrandparent(current);
		if(grandparent == null)
			return null;
		if(current.getParent() == grandparent.getLeft())
			return grandparent.getRight();
		else
			return grandparent.getLeft();
	}
	public Node<K,V> findGrandparent(Node<K,V> current)
	{
		if((current != null) && (current.getParent() != null))
			return current.getParent().getParent();
		else
			return null;
	}
	
	public Node<K,V> findSibling(Node<K,V> current)
	{
		if(current.isLeftOf(current.getParent()))
			return (current.getParent().getRight());
		else if(current.isRightOf(current.getParent()))
			return (current.getParent().getLeft());
		else
			return null;
		
	}
	
	public V remove(K key)
	{
		V val = super.lookup(key);
		Node<K,V> temp = find(key);
		Node<K,V> temp1 = temp;
		Node<K,V> temp2;
		boolean originalColor = temp.getColor();
		if(val == null)
			return null;
		if(temp.getLeft() == null)
		{
			temp2 = temp.getRight();
			removeHelper(temp, temp.getRight());
		}
		else if(temp.getRight() == null)
		{
			temp2 = temp.getLeft();
			removeHelper(temp,temp.getLeft());
		}
		else
		{
			temp1 = findMin(temp.getRight());
			originalColor = temp1.getColor();
			temp2 = temp1.getRight();
			if(temp1.getParent() == temp)
				temp2.setParent(temp1);
			else
			{
				removeHelper(temp1, temp1.getRight());
				temp1.setRight(temp.getRight());
				temp1.getRight().setParent(temp1);
			}
			removeHelper(temp,temp1);
			temp1.setLeft(temp.getLeft());
			temp1.getLeft().setParent(temp1);
			temp1.setColor(temp.getColor());
		}
		if(originalColor == false)
			fixTreeRemove(temp2);
		return val;
	}
	public void removeHelper(Node<K,V> temp1, Node<K,V> temp2)
	{
		if(temp1.getParent() == null)
			super.setRoot(temp2);
		else if(temp1 == temp1.getParent().getLeft())
			temp1.getParent().setLeft(temp2);
		else
			temp1.getParent().setRight(temp2);
		temp2.setParent(temp1.getParent());
	}
	
	public Node<K,V> findMin(Node<K,V> current)
	{
		Node<K,V> curr = current;
		while(curr.getLeft() != null)
			curr = curr.getLeft();
		return curr;
	}
	public void removeCase1()
	{
		leftRotate(found.getParent());
		found.getParent().setColor(!(found.getParent().getColor()));
		findGrandparent(found).setColor(!(findGrandparent(found).getColor()));
	}
	
	public void removeCase2()
	{
		Node<K,V> sibling = findSibling(found);
		sibling.setColor(true);
		if(found.getParent().getColor() == true)
			fixTreeRemove(super.getRoot());
		else
			fixTreeRemove(found.getParent());
		found.getParent().setColor(false);
		
	}
	public void removeCase3()
	{
		Node<K,V> sibling = findSibling(found);
		rightRotate(sibling);
		sibling.setColor(true);
		findSibling(found).setColor(false);
		fixTreeRemove(found);
	}
	
	public void removeCase4()
	{
		Node<K,V> sibling = findSibling(found);
		leftRotate(sibling);
		sibling.getRight().setColor(!(sibling.getColor()));
	}
	
	public void fixTreeAdd(Node<K,V> current)
	{
		Node<K,V> uncle = findUncle(current);
		if(current == super.getRoot())
			return;
		else
		{
			while(current.getParent().getColor() == true)
			{
				if(current.getParent() == current.getParent().getParent().getLeft())
				{
					uncle = current.getParent().getParent().getRight();
					if(uncle.getColor() == true)
					{
						current.getParent().setColor(false);
						uncle.setColor(false);
						current.getParent().getParent().setColor(true);
						current = current.getParent().getParent();
					}
					else if(current == current.getParent().getRight())
					{
						current = current.getParent();
						leftRotate(current);
					}
					current.getParent().setColor(false);
					current.getParent().getParent().setColor(true);
					rightRotate(current.getParent().getParent());
				}
				else
				{
					uncle = current.getParent().getParent().getLeft();
					if(uncle.getColor() == true)
					{
						current.getParent().setColor(false);
						uncle.setColor(false);
						current.getParent().getParent().setColor(true);
						current = current.getParent().getParent();
					}
					else if(current == current.getParent().getLeft())
					{
						current = current.getParent();
						leftRotate(current);
					}
					current.getParent().setColor(false);
					current.getParent().getParent().setColor(true);
					rightRotate(current.getParent().getParent());
				}
				super.getRoot().setColor(false);
			}
		}
		
	}
	public void fixTreeRemove(Node<K,V> current)
	{
		Node<K,V> sibling = findSibling(current);
		while(current != super.getRoot() && current.getColor() == false)
		{
			if(current == current.getParent().getLeft())
			{
				sibling = current.getParent().getRight();
				if(sibling.getColor() == true)
				{
					sibling.setColor(false);
					current.getParent().setColor(true);
					leftRotate(current.getParent());
					sibling = current.getParent().getRight();
				}
				if(sibling.getLeft().getColor() == false && sibling.getRight().getColor() == false)
				{
					sibling.setColor(true);
					current = current.getParent();
				}
				else if(sibling.getRight().getColor() == false)
				{
					sibling.getLeft().setColor(false);
					sibling.setColor(true);
					rightRotate(sibling);
					sibling = current.getParent().getRight();
				}
				sibling.setColor(current.getParent().getColor());
				current.getParent().setColor(false);
				sibling.getRight().setColor(false);
				leftRotate(current.getParent());
				current = super.getRoot();
			}
			else
			{
				sibling = current.getParent().getLeft();
				if(sibling.getColor() == true)
				{
					sibling.setColor(false);
					current.getParent().setColor(true);
					leftRotate(current.getParent());
					sibling = current.getParent().getRight();
				}
				if(sibling.getLeft().getColor() == false && sibling.getRight().getColor() == false)
				{
					sibling.setColor(true);
					current = current.getParent();
				}
				else if(sibling.getLeft().getColor() == false)
				{
					sibling.getRight().setColor(false);
					sibling.setColor(true);
					rightRotate(sibling);
					sibling = current.getParent().getLeft();
				}
				sibling.setColor(current.getParent().getColor());
				current.getParent().setColor(false);
				sibling.getLeft().setColor(false);
				leftRotate(current.getParent());
				current = super.getRoot();
			}
		}
		current.setColor(false);
	}
	public void leftRotate(Node<K,V> current)
	{
		Node<K,V> temp = current.getRight();
		current.setRight(temp.getLeft());
		if(temp.getLeft() != null)
			temp.getLeft().setParent(current);
		temp.setParent(current.getParent());
		if(current.getParent() == null)
			super.setRoot(temp);
		else if(current == current.getParent().getLeft())
		current.getParent().setLeft(temp);
		else
			current.getParent().setRight(temp);
		temp.setLeft(current);
		current.setParent(temp);
		
	}
	public void rightRotate(Node<K,V> current)
	{
		Node<K,V> temp = current.getLeft();
		current.setLeft(temp.getRight());
		if(temp.getRight() != null)
			temp.getRight().setParent(current);
		temp.setParent(current.getParent());
		if(current.getParent() == null)
			super.setRoot(temp);
		else if(current == current.getParent().getLeft())
		current.getParent().setLeft(temp);
		else
			current.getParent().setRight(temp);
		temp.setRight(current);
		current.setParent(temp);
	}
	
	
	
	public static void main(String[] args)
	{
		RedBlackTree<Integer, Integer> t = new RedBlackTree<Integer, Integer>();
		t.add(7, 0);
		t.add(12, 1);
		t.add(6, 2);
		//t.toPrettyString();
		t.remove(12);
		
	}
}
