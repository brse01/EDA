package br.ufc.crateus.eda.st.btree;

public class BTreeSet<K> {

private Page<K> root;
	
	public BTreeSet(K sentinel) {
		root = new Page<>(true);
		root.insert(sentinel);
	}
	
	public boolean contains(K key) {
		return contains(root, key);
	}
	
	private boolean contains(Page<K> r, K key) {
		if (r.isExternal()) r.holds(key);
		return contains(r.next(key), key);
	}
	
	public void add(K key) {
		add(root, key);
		if (root.hasOverflowed()) {
			Page<K> left = root;
			Page<K> right = root.split();
			root = new Page<>(false);
			root.enter(left);
			root.enter(right);
		}
	}
	
	private void add(Page<K> r, K key) {
		if (r.isExternal()) r.insert(key);
		Page<K> next = r.next(key);
		add(next, key);
		if (next.hasOverflowed()) r.enter(next.split());
	}
}
