package br.ufc.crateus.eda.string;
public class TST<V> {
	private Node root;
	
	private class Node {
		char ch;
		V value;
		Node left, mid, right;
		Node(char ch) {
			this.ch = ch;
		}
	}
	
	public V get(String key) {
		Node node = get(root, key, 0);
		return (node != null)? node.value : null;
	}
	
	private Node get(Node r, String key, int i) {
		if (r == null) return null;
		char ch = key.charAt(i);
		if (ch < r.ch) return get(r.left, key, i);
		else if (ch > r.ch) return get(r.right, key, i);
		else if (i == key.length() - 1) return r;
		else return get(r.mid, key, i + 1);
	}
	
	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}
	
	private Node put(Node r, String key, V value, int i) {
		char ch = key.charAt(i);
		if (r == null) r = new Node(ch);
		if (ch < r.ch) r.left = put(r.left, key, value, i);
		else if (ch > r.ch) r.right = put(r.right, key, value, i);
		else if (i == key.length() - 1) r.value = value;
		else r.mid = put(r.mid, key, value, i + 1);
		return r;
	}
}