package br.ufc.crateus.eda.st.Ordered;

import java.rmi.server.SocketSecurityException;
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
		Entry<K, V> e = getEntry(key);
		if (value != null) {
			if (e == null) {
				e = new STEntry<>(key, value);

				if (list.size() == 0) {
					list.add(e);
				} else {
					list.add(rank(e.getKey()), e);
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
		Entry<K, V> e;
		if (list.size() > 0) {
			e = list.get(0);
		} else
			return null;

		return e.getKey();
	}

	@Override
	public K max() {
		Entry<K, V> e;
		if (list.size() > 0) {
			e = list.get(list.size() - 1);
		} else
			return null;
		return e.getKey();
	}

	@Override
	public K floor(K key) {
		K aux = null;		 
		for (Entry<K, V> e : list) {
			if (e.getKey().compareTo(key) < 0)
				aux = e.getKey();
		}
		return aux;
	}

	@Override
	public K ceiling(K key) {
		K aux = null;
		int vz= 0;
		for (Entry<K, V> e : list) {
			if (e.getKey().compareTo(key) > 0) {
				aux = e.getKey(); vz =1;
			}
			if(vz ==1) break;
		}
		return aux;
	}

	@Override
	public int rank(K key) {
		int lo = 0, hi = list.size() - 1;
		while (lo <= hi) {
			int m = (hi - lo) / 2;
			int cmp = key.compareTo(list.get(m).getKey());
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
		if (i <= list.size()) {
			Entry<K, V> e = list.get(i);
			return e.getKey();
		} else
			return null;
	}

	@Override
	public void deleteMin() {
		if (list.size() > 0) {
			put(min(), null);
		}
	}

	@Override
	public void deleteMax() {
		if (list.size() > 0) {
			put(max(), null);
		}
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
			if (e.getKey().compareTo(lo) > 0 && e.getKey().compareTo(hi) < 0)
				keys.add(e.getKey());
		}
		return keys;
	}

	public static void main(String[] args) {

		OrderedST<String, Integer> st2 = new ArrayListST<>();
		st2.put("Eraldo", 1);
		st2.put("Fernando", 3);
		st2.put("Amanda", 3);
		st2.put("Bruno", 3);

		for (String per : st2.keys()) {
			System.out.println("Key = " + per + ", Value = " + st2.get(per));
		}

		System.out.println("\n" + st2.floor("Eraldo"));

	}

}