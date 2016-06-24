package br.ufc.crateus.eda.st.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.ufc.crateus.eda.st.STEntry;

public class BinarySearchST<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private List<Entry<K, V>> list = new ArrayList<>();

	@Override
	public V get(K key) {
		return null;
	}

	@Override
	public void put(K key, V value) {
		if (value != null) {
			int i = rank(key);
			Entry<K, V> e = list.get(i);
			if (e.getKey().equals(key)) {
				e.setValue(value);
			} else
				list.add(i, new STEntry<K, V>(key, value));
		} else
			delete(key);
	}

	@Override
	public void delete(K key) {
		list.remove(rank(key));
	}

	@Override
	public boolean contains(K key) {
		return list.contains(key);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Iterable<K> keys() {
		return null;
	}

	@Override
	public K min() {
		return list.get(0).getKey();
	}

	@Override
	public K max() {
		return list.get(list.size() - 1).getKey();
	}

	@Override
	public K floor(K key) {
		if (key.compareTo(min()) < 0)
			return null;
		int i = rank(key);
		if (key.equals(select(i)))
			return key;
		else
			return select(i - 1);
	}

	@Override
	public K ceiling(K key) {
		if (key.compareTo(max()) > 0)
			return null;
		int i = rank(key);
		return list.get(i).getKey();
	}

	@Override
	public int rank(K key) {
		int lo = 0, hi = size() - 1;

		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(select(mid));
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}

		return lo;
	}

	@Override
	public K select(int i) {
		return list.get(i).getKey();
	}

	@Override
	public void deleteMin() {
		delete(min());
	}

	@Override
	public void deleteMax() {
		delete(max());
	}

	@Override
	public int size(K lo, K hi) {
		return 0;
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		return null;
	}

}
