package br.ufc.crateus.eda.st.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.ufc.crateus.eda.st.STEntry;

public class BinarySearchST<K extends Comparable<K>, V> implements OrderedST<K, V> {

	private List<Entry<K, V>> list = new ArrayList<>();

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K min() {
		return null;
	}

	@Override
	public K max() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMax() {
		// TODO Auto-generated method stub

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

}
