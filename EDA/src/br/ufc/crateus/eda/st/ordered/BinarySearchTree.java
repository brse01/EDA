package br.ufc.crateus.eda.st.ordered;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedST<K, V> {

	protected Node root;

	protected class Node {
		K key;
		V value;
		int count;
		Node left, right;

		public Node(K key, V value, int count) {
			this.key = key;
			this.value = value;
			this.count = count;
		}
	}

	@Override
	public V get(K key) {
		Node node = getNode(key);
		return (node != null) ? node.value : null;
	}

	private Node getNode(K key) {
		Node r = root;
		while (r != null) {
			int cmp = key.compareTo(r.key);
			if (cmp < 0)
				r = r.left;
			else if (cmp > 0)
				r = r.right;
			else
				break;
		}
		return r;
	}

	@Override
	public void put(K key, V value) {
		root = put(root, key, value);
	}

	private Node put(Node r, K key, V value) {
		if (r == null)
			return new Node(key, value, 1);
		int cmp = key.compareTo(r.key);
		if (cmp < 0)
			r.left = put(r.left, key, value);
		else if (cmp > 0)
			r.right = put(r.right, key, value);
		else
			r.value = value;
		r.count = 1 + size(r.left) + size(r.right);
		return r;
	}

	@Override
	public void delete(K key) {
		root = delete(root, key);
	}

	protected Node delete(Node r, K key) {
		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp < 0)
			r.left = delete(r.left, key);
		else if (cmp > 0)
			r.right = delete(r.right, key);
		else {
			if (r.left == null)
				return r.right;
			if (r.right == null)
				return r.left;

			Node tmp = r;
			r = min(r.right);
			r.left = tmp.left;
			r.right = deleteMin(tmp.right);
		}

		r.count = size(r.left) + size(r.right) + 1;
		return r;
	}

	@Override
	public boolean contains(K key) {
		if (auxDepth(root, key) > 0)
			return true;
		return false;
	}

	@Override
	public int size() {
		return size(root);
	}

	protected int size(Node node) {
		return (node != null) ? node.count : 0;
	}

	@Override
	public boolean isEmpty() {
		return root != null;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> keys = new LinkedList<>();
		inorder(root, keys);
		return keys;
	}

		

	@Override
	public K min() {
		Node min = min(root);
		return (min != null) ? min.key : null;
	}

	protected Node min(Node r) {
		if (r == null)
			return null;
		Node min = min(r.left);
		return (min != null) ? min : r;
	}

	@Override
	public K max() {
		Node max = max(root);
		return (max != null) ? max.key : null;
	}

	private Node max(Node r) {
		if (r == null)
			return null;
		Node max = max(r.right);
		return (max != null) ? max : r;
	}

	@Override
	public K floor(K key) {
		Node node = floor(root, key);
		return (node != null) ? node.key : null;
	}

	private Node floor(Node r, K key) {
		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return r;
		if (cmp < 0)
			return floor(r.left, key);
		Node t = floor(r.right, key);
		return (t != null) ? t : r;
	}

	@Override
	public K ceiling(K key) {
		Node node = ceiling(root, key);
		return (node != null) ? node.key : null;
	}

	private Node ceiling(Node r, K key) {
		if (r == null)
			return null;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return r;
		if (cmp > 0)
			return ceiling(r.right, key);
		Node t = ceiling(r.left, key);
		return (t != null) ? t : r;
	}

	@Override
	public int rank(K key) {
		return rank(root, key);
	}

	private int rank(Node r, K key) {
		if (r == null)
			return 0;
		int cmp = key.compareTo(r.key);
		if (cmp == 0)
			return size(r.left);
		if (cmp < 0)
			return rank(r.left, key);
		return 1 + size(r.left) + rank(r.right, key);
	}

	@Override
	public K select(int i) {
		Node r = select(root, i);
		return (r != null) ? r.key : null;
	}

	private Node select(Node r, int k) {
		if (r == null)
			return null;
		int t = size(r.left);
		if (t > k)
			return select(r.left, k);
		else if (t < k)
			return select(r.right, k - t - 1);
		else
			return r;
	}

	@Override
	public void deleteMin() {
		root = deleteMin(root);
	}

	protected Node deleteMin(Node r) {
		if (r.left == null)
			return r.right;
		r.left = deleteMin(r.left);
		r.count = 1 + size(r.left) + size(r.right);
		return r;
	}

	@Override
	public void deleteMax() {
		root = deleteMax(root);
	}

	private Node deleteMax(Node r) {
		if (r.right == null)
			return r.left;
		r.right = deleteMax(r.right);
		r.count = 1 + size(r.left) + size(r.right);
		return r;
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

	void inorder(Node r, Queue<K> keys) {
		if (r != null) {
			inorder(r.left, keys);
			keys.add(r.key);
			inorder(r.right, keys);
		}
	}

	@SuppressWarnings("unused")
	private int height() {
		return auxHeight(root);
	}

	private int auxHeight(Node r) {
		int left = 0, right = 0;
		if (r == null)
			return -1;
		if (r.left != null)
			left = auxHeight(r.left);
		if (r.right != null)
			right = auxHeight(r.right);
		return (left >= right) ? left + 1 : right + 1;
	}

	private int depth(K key) {
		return auxDepth(root, key);
	}

	private int auxDepth(Node r, K key) {
		if (r == null)
			return 0;
		int cmp = key.compareTo(r.key);
		if (cmp > 0)
			return auxDepth(r.right, key) + 1;
		else if (cmp < 0)
			return auxDepth(r.left, key) + 1;
		else
			return 1;
	}

	private void aux(int n, V value) {
		for (int i = 0; i < n; i++) {
			System.out.print("  ");
		}
		System.out.println(value);
	}

	private void auxPrint(Node r) {
		if (r != null) {
			aux(depth(r.key), r.value);
			auxPrint(r.left);
			auxPrint(r.right);
		} else
			System.out.println("_");
	}

	@SuppressWarnings("unused")
	private void toPrint() {
		auxPrint(root);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void arrayToTree(Node r, List<K> array) {
		int e = 0, d = array.size();
		int m = d / 2;

		for (int i = m; i > e; i--) {
			put(array.get(i), (V) array.get(i));
		}

		for (int i = m + 1; i < d; i++) {
			put(array.get(i), (V) array.get(i));
		}

	}

}
