package br.ufc.crateus.eda.st.btree;

public class BTreeSet<K extends Comparable<K>, V> {
	private Page<K, Object> root;

	public BTreeSet(K sentinel, V value) {
		root = new Page<>(true);
		root.insert(sentinel, value);
	}

	public boolean contains(K key) {
		return contains(root, key);
	}

	private boolean contains(Page<K, Object> r, K key) {
		if (r.isExternal())
			r.holds(key);
		return contains(r.next(key), key);
	}

	public void add(K key, V value) {
		add(root, key, value);
		if (root.hasOverflowed()) {
			Page<K, Object> left = root;
			Page<K, Object> right = root.split();
			root = new Page<>(false);
			root.enter(left);
			root.enter(right);
		}
	}

	private void add(Page<K, Object> r, K key, V value) {
		if (r.isExternal())
			r.insert(key, value);
		Page<K, Object> next = r.next(key);
		if (next != null) {
			add(next, key, value);
			if (next.hasOverflowed())
				r.enter(next.split());
		}
	}

	@SuppressWarnings("unused")
	private K min() {
		return min(root);
	}

	private K min(Page<K, Object> r) {
		if (r.isExternal()) {
			int i = r.st.rank(r.st.min());
			return r.st.select(i + 1);
		} else
			return min(r.next(r.st.min()));
	}

	@SuppressWarnings("unused")
	private K max() {
		return max(root);
	}

	private K max(Page<K, Object> r) {
		if (r.isExternal()) {
			int i = r.st.rank(r.st.max());
			return r.st.select(i - 1);
		} else
			return max(r.next(r.st.max()));
	}

	@SuppressWarnings("unused")
	private Object floor(K key, Page<K, Object> r) {
		Page<K, Object> next = r.next(key);
		return next.st.floor(key);
	}

	@SuppressWarnings("unused")
	private Object ceiling(K key, Page<K, Object> r) {
		Page<K, Object> next = r.next(key);
		return next.st.ceiling(key);
	}

	@SuppressWarnings("unused")
	private Object get(K key) {
		return get(root, key);
	}

	@SuppressWarnings("unchecked")
	private Object get(Page<K, Object> r, K key) {
		if (r.isExternal()) {
			return get(r.next(key), key);
		} else {
			return (V) r.st.get(key);
		}
	}

	@SuppressWarnings("unused")
	private void deleteMax(){
		root.st.deleteMin();
	}
	
	@SuppressWarnings("unused")
	private void deleteMin(){
		root.st.deleteMin();
	}
	
	public static void main(String[] args) {
		BTreeSet<String, String> pg = new BTreeSet<>("*", "");
		pg.add("H", "");
		pg.add("A", "");
		pg.add("G", "");

		
	}

}
