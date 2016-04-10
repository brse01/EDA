package br.ufc.crateus.eda.st.Ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.ufc.crateus.eda.st.STEntry;

public class ArrayListST<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private List<Entry<K, V>> list = new ArrayList<>();

	private Entry<K, V> getEntry(K key) {
		for (Entry<K, V> e : list)
			if (e.getKey().equals(key))
				return e;
		return null;
	}

	@Override
	public V get(K key) {
		Entry<K, V> e = getEntry(key);
		return (e != null) ? e.getValue() : null;
	}

	@Override
	public void put(K key, V value) {
		int x = 1, i;
		Entry<K, V> e = getEntry(key);
		if (value != null) {
			if (e == null) {
				e = new STEntry<>(key, value);
				if (list.size() > 0) {
					for (i = 0; i < list.size() || x != 0; i++) {
						if (key.compareTo(list.get(i).getKey()) >= 0)
							x = 0;
					}
					list.add(i, e);										
				} else {
					list.add(0, e);
				}

			} else
				e.setValue(value);
		} else {
			if (e != null)
				list.remove(e);
		}

	}

	@Override
	public void delete(K key) {
		put(key, null);
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterable<K> keys() {
		List<K> keys = new ArrayList<>();
		for (Entry<K, V> e : list)
			keys.add(e.getKey());
		return keys;
	}

	@Override
	public K min() {
		Entry<K, V> e = list.get(0);
		return e.getKey();
	}

	@Override
	public K max() {
		Entry<K, V> e = list.get(list.size() - 1);
		return e.getKey();
	}

	@Override
	public K floor(K key) {
		K aux = null;
		for (Entry<K, V> e : list) {
			if (key.compareTo(e.getKey()) <= 0)
				aux = e.getKey();
		}
		return aux;
	}

	@Override
	public K ceiling(K key) {
		K aux = null;
		for (Entry<K, V> e : list) {
			if (key.compareTo(e.getKey()) >= 0) {
				aux = e.getKey();
			}
		}
		return aux;
	}

	@Override
	public int rank(K key) {
		int lo = 0, hi = list.size();
		System.out.println("key escolhida:>" + key);
		while (lo <= hi) {
			int m = lo + (hi - lo) / 2;
			System.out.println("list:" + list.get(m).getKey());
			int cmp = key.compareTo(list.get(m).getKey());
			System.out.println(cmp);
			if (cmp < 0) {
				hi = m - 1;
			} else if (cmp > 0) {
				lo = m + 1;
			} else {
				return m;
			}
		}
		return lo;
	}

	@Override
	public K select(int i) {
		Entry<K, V> e = list.get(i);
		return e.getKey();
	}

	@Override
	public void deleteMin() {
		put(min(), null);
	}

	@Override
	public void deleteMax() {
		put(max(), null);
	}

	@Override
	public int size(K lo, K hi) {
		int cont = 0;
		for (Entry<K, V> e : list) {
			if (lo.compareTo(e.getKey()) > 0 && hi.compareTo(e.getKey()) < 0) {
				cont++;
			}
		}
		return cont;
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		List<K> keys = new ArrayList<>();
		for (Entry<K, V> e : list) {
			if (lo.compareTo(e.getKey()) >= 0 && hi.compareTo(e.getKey()) <= 0)
				keys.add(e.getKey());
		}
		return keys;
	}

	public static void main(String[] args) {

		OrderedST<String, Integer> st = new ArrayListST<String, Integer>();
		st.put("João", 23);
		st.put("Maria", 40);
		st.put("Uálison", 25);
		/*
		st.put("Bruno", 25);
		st.put("Ayrton", 21);
		st.put("Bruno", 25);
		st.delete("Ayrton");
		st.put("Bruno", 22);
		st.put("Felipe", 17);
		st.put("Rodrigo", 11);
		st.put("Zé Cota", 54);
		 */
		int i = 1;
		for (String key : st.keys()) {
			System.out.println((i++) + ". Key = " + key + ", Value = " + st.get(key));
		}
		System.out.println(st.max());
	}

}
