package br.ufc.crateus.eda.st.hashing;

import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.ST;

public class LinearProbingHashST<K, V> implements ST<K, V> {
	int m;
	private K[] keys;
	private V[] values;
	int length = 0;

	@SuppressWarnings("unchecked")
	public LinearProbingHashST(int m) {
		this.m = m;
		keys = (K[]) new Object[m];
		values = (V[]) new Object[m];
		length = 0;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	@Override
	public V get(K key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return values[i];
		return null;
	}

	@Override
	public void put(K key, V value) {
		if ((double) length / m > 0.5)
			resize();

		int i;
		for (i = hash(key); keys[i] != null && !key.equals(keys[i]); i = (i + 1) % m)
			;
		if (keys[i] == null) {
			keys[i] = key;
			length++;
		}
		values[i] = value;
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

	private void resize() {
		LinearProbingHashST<K, V> hash = new LinearProbingHashST<>(2 * m);
		for (int i = 0; i < m; i++)
			if (keys[i] != null)
				hash.put(keys[i], values[i]);
		this.keys = hash.keys;
		this.values = hash.values;
		this.m = hash.m;
	}

	@Override
	public Iterable<K> keys() {
		List<K> aux = new ArrayList<>();
		for (int i = 0; i < keys.length; i++) {
			aux.add(keys[i]);
		}
		return aux;
	}
}
