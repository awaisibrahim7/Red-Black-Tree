
public interface Tree<K extends Comparable<K>, V> {
  public void add(K key, V value);
  public V remove(K key);
  public V lookup(K key);
  public String toPrettyString();
}
