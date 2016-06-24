package br.ufc.crateus.eda.st.hashing;

import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.ST;

public class SeparateChainingHashST<K, V> implements ST<K, V> {

	private int m;
	private Node[] table;
	private int count;

	public SeparateChainingHashST(int m) {
		this.m = m;
		table = new Node[m];
		count = 0;
	}

	private static class Node {
		Object key;
		Object value;
		Node next;

		Node(Object key, Object value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		int i = hash(key);
		for (Node l = table[i]; l != null; l = l.next)
			if (l.key.equals(key))
				return (V) l.value;
		return null;
	}

	@Override
	public void put(K key, V value) {
		int i = hash(key);
		for (Node l = table[i]; l != null; l = l.next) {
			if (l.key.equals(key)) {
				l.value = value;
				return;
			}
		}

		table[i] = new Node(key, value, table[i]);
		count++;
	}

	@Override
	public void delete(K key) {

	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<>(count);
		for (int i = 0; i < m; i++)
			for (Node l = table[i]; l != null; l = l.next)
				list.add((K) l.key);
		return list;
	}

}
