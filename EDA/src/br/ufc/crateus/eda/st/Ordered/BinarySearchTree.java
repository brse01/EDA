package br.ufc.crateus.eda.st.Ordered;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private Node root;

	class Node {
		K key;
		V value;
		Node left, right;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

	}

	@Override
	public V get(K key) {
		Node r = getNode(root, key);
		return (r != null) ? r.value : null;
	}

	private Node getNode(Node r, K key) {
		while (r != null) {
			int cmp = r.key.compareTo(key);
			if (cmp > 0)
				r = r.left;
			else if (cmp < 0)
				r = r.right;
			else
				return r;
		}
		return null;
	}

	@Override
	public void put(K key, V val) {
		put(root, key, val);
	}

	
	private Node put(Node r, K key, V value) {
		if (r == null)
			return new Node(key, value);
		int cmp = key.compareTo(r.key);
		if (cmp < 0)
			r.left = put(r.left, key, value);
		else if (cmp > 0)
			r.right = put(r.right, key, value);
		else
			r.value = value;
		return r;
	}

	@Override
	public void delete(K key) {

	}

	@Override
	public boolean contains(K key) {
		Node auxiliar = getNode(root, key);
		return (auxiliar != null) ? true : false;
	}

	
	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return (root != null) ? true : false;
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K min() {
		Node min = min(root);
		return (min != null) ? min.key : null;
	}

	private Node min(Node r) {
		if (r == null)
			return null;
		if (r.left == null)
			return r;
		return min(r.left);
	}

	@Override
	public K max() {
		Node max = max(root);
		return (max != null) ? max.key : null;

	}

	private Node max(Node r) {
		if (r == null)
			return null;
		if (r.left == null)
			return r;
		return max(r.left);
	}

	@Override
	public K floor(K key) {
		Node r = floor(root, key);
		return (r != null) ? r.key : null;
	}

	private Node floor(Node r, K key) {
		if(r ==null) return null;
		if (key.equals(r.key)) return r;
		if (key.compareTo(r.key) < 0) r.left = floor(r.left, key);		
		return r;
	}

	
	private Node floor2(Node r, K key){
		if(r ==null) return null;
		int cmp = key.compareTo(r.key);
		if(cmp == 0) return r;
		if(cmp <0) return floor2(r.left,key);
		Node t = floor2(r.left,key);
		if(t!=null) return t;
		else return r;
	}
	
	@Override
	public K ceiling(K key) {
		return null;
	}

	@Override
	public int rank(K key) {
		return 0;
	}

	@Override
	public K select(int i) {
		return null;
	}
	
	private void delete(Node r) {
	
	}
	
	@Override
	public void deleteMin() {		
       delete(min(root));		
	}

	@Override
	public void deleteMax() {
		delete(max(root));
	}

	@Override
	public int size(K lo, K hi) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		// TODO Auto-generated method stub
		return null;
	}

}
