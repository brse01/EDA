package br.ufc.crateus.eda.st.Ordered;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import br.ufc.crateus.eda.st.STEntry;




public class ArrayListST<K, V>  implements OrderedST<K, V>{

	
	private List<Entry<K, V>> list = new ArrayList<>();

	private Entry<K, V> getEntry(K key) {
		for (Entry<K, V> e : list)
			if (e.getKey().equals(key))
				return e;
		return null;
	}
	
	@Override
	public V get(K key) {
		Entry<K,V> e = getEntry(key);		
		return (e != null)? e.getValue() : null;
	}

	@Override
	public void put(K key, V value) {
		
		Entry<K, V> e = getEntry(key); 
		if (value != null) {
			if (e == null) {								
				e = new STEntry<>(key, value);
				for(int i=0; i< list.size(); i++){
				Entry<K,V> aux = list.get(i);				
				}
				
				list.add(e);
			}
			else e.setValue(value);
		}
		else {
			if (e != null) list.remove(e);
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
		List<K> keys = new LinkedList<>();
		for (Entry<K, V> e : list) 
			keys.add(e.getKey());
		return keys;	
		}

	@Override
	public K min() {	
		Entry<K,V> e = list.get(0);		
		return e.getKey();
	}

	
	@Override
	public K max() {		
		Entry<K,V> e= list.get(list.size()-1);
		return e.getKey();
	}

	@Override
	public K floor(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K ceiling(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int rank(K key) {
		int lo=0, hi = list.size() -1;
		while(lo <=hi){
			int mid = lo+ (hi-lo)/2;									
		}
		
		return 0;
	}

	@Override
	public K select(int i) {
		return null;
	}

	@Override
	public void deleteMin() {		
		put(min(), null);
	}

	@Override
	public void deleteMax() {
		put(max(),null);		
	}

	@Override
	public int size(K lo, K hi) {
		int cont=0;			
	for (Entry<K, V> x : list){		
	} 							
		return cont;
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {		
		List<K> keys = new ArrayList<>();
		for (Entry<K, V> e : list) 
			keys.add(e.getKey());		
		return keys;
	}
	
	public static void main(String[] args) {
		
		OrderedST<String, Integer> st = new ArrayListST<String, Integer>();
		st.put("João", 23);	
		st.put("Maria", 40);
		
		System.out.println("Quantidade:" +st.size()+"\n");
		
		for (String key : st.keys()) {
			System.out.println("1. Key = " + key + ", Value = " + st.get(key));
		}		
		st.put("Uálison", 25);
		st.put("Ayrton", 21);
		st.put("Bruno", 25);
		for (String key : st.keys()) {
			System.out.println("2. Key = " + key + ", Value = " + st.get(key));
		}
		
		st.delete("Ayrton");
		st.put("Bruno", 22);
		st.put("Felipe", 17);
		
		for (String key : st.keys()) {
			System.out.println("3. Key = " + key + ", Value = " + st.get(key));
		}

		System.out.println("MINIMO:"+ st.min() +":"+ "MAXIMO:"+ st.max());
		System.out.println("Quantidade:" +st.size());
				
	}
	
	}

		

