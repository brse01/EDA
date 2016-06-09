package br.ufc.crateus.eda.st.btree;

public class Page<K extends Comparable<K>,V> {
	public static final int M = 6;
	private boolean botton;
	Node root;
	List<Node> list = new BinarySearchST<>();

	public class Node{
		K key;
		V value;
		Page<K,V> next;

			public Node(Node r,K key, V value){
				root = r;
				this.key = key;
				this.value = value;
				bst.put(key,value);
			}

	}
	
	Page(boolean booton) {
		this.botton = booton;
	}
	
	public void close() {
		
	}
	
	public void insert(K key)  {
		
	}
	
	public void enter(Page<K> page) {
		
	}
	
	public boolean isExternal() {
		return botton;
	}
	
	public boolean holds(K key) {
		return false;
	}
	
	Page<K> next(K key) {
		return null;
	}
	
	public boolean hasOverflowed() {
		return false;
	}
	
	public Page<K> split() {
		return null;
	}
	
	Iterable<K> keys() {
		return null;
	}

}
