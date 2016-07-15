package br.ufc.crateus.eda.st.hashing;

import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.ST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;

public class SeparateTreeHash<K extends Comparable<K>, V> implements ST<K, V> {

	private int m;
	private BinarySearchTree<K, V>[] table;
	int length = 0;

	@SuppressWarnings("unchecked")
	public SeparateTreeHash(int m) {
		this.m = m;
		table = (BinarySearchTree<K, V>[]) new BinarySearchTree[m];
		length = 0;
	}

	@Override
	public V get(K key) {
		for (int i = hash(key); table[i] != null; i = (i + 1) % m) {
			BinarySearchTree<K, V> obj = table[i];
			if (obj != null)
				if (obj.contains(key))
					return (V) table[i].get(key);

		}
		return null;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	@Override
	public void put(K key, V value) {
		if (length >= m * 4)
			resize();
		int i = hash(key);
		BinarySearchTree<K, V> obj = table[i];
		if (obj == null) {
			obj = new BinarySearchTree<>();
			obj.put(key, value);
			table[i] = obj;
		} else {
			obj.put(key, value);
			table[i] = obj;

		}

		length++;
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public boolean isEmpty() {
		return length == 0;
	}

	@Override
	public Iterable<K> keys() {
		List<K> aux = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			if (table[i] != null)
				for (K key : table[i].keys())
					aux.add(key);
		}
		return aux;
	}

	private void resize() {
		SeparateTreeHash<K, V> aux = new SeparateTreeHash<>(m * 2);
		for (K key : keys()) {
			aux.put(key, get(key));
		}
		this.m = aux.m;
		this.length = aux.length;
		this.table = aux.table;
	}
}