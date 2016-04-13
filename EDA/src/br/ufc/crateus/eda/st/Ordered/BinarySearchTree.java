package br.ufc.crateus.eda.st.Ordered;

import br.ufc.crateus.eda.st.ST;

public class BinarySearchTree<K extends Comparable<K>, V> implements ST<K, V> {
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
		Node auxiliar = getNode(root,key);		
		return (auxiliar !=null)? auxiliar.value : null;
	}

	public Node getNode(Node r, K key) {		
		while (r != null) {
			int cmp = r.key.compareTo(key);
			if (cmp > 0) r = r.left;
			else if (cmp < 0) r= r.right;
			else return r;
		}
		return null;
	}

	@Override
	public void put(K key, V val) {
		// TODO Auto-generated method stub
	}

	
	
	@Override
	public void delete(K key) {		
		
	}

	@Override
	public boolean contains(K key) {
		Node auxiliar = getNode(root, key);
		return (auxiliar !=null)? true : false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

}
