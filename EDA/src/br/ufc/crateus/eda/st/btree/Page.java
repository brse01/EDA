package br.ufc.crateus.eda.st.btree;

import br.ufc.crateus.eda.st.ordered.BinarySearchST;

public class Page<K extends Comparable<K>, V> {

	private int M = 10;
	BinarySearchST<K, Object> st;
	private boolean botton;

	public Page(boolean botton) {
		this.botton = botton;
		this.st = new BinarySearchST<>();

	}

	public void close() {

	}

	public void insert(K key, V value) {
		if (!hasOverflowed()) {
			st.put(key, value);
		}
	}

	public void enter(Page<K, V> page) {
		page.st.put(page.st.min(), page);
	}

	public boolean isExternal() {
		return botton;
	}

	public boolean holds(K key) {
		return st.contains(key);
	}

	@SuppressWarnings("unchecked")
	Page<K, Object> next(K key) {
		return (Page<K, Object>) st.get(st.floor(key));
	}

	public boolean hasOverflowed() {
		return (st.size() == M) ? true : false;
	}

	public Page<K, Object> split() {
		Page<K, Object> tmp = new Page<>(true);
		int i = st.size();
		for (int j = i / 2; j < i; j++) {
			tmp.st.put(st.select(i), st.get(st.select(i)));
		}

		int m = i / 2;
		K kMeio = st.select(m);
		int j = st.size() - 1;

		while (!kMeio.equals(st.select(j))) {
			st.delete(st.select(j));
			j--;
		}
		st.delete(st.select(j));
		return tmp;
	}

	Iterable<K> keys() {
		return null;
	}

}