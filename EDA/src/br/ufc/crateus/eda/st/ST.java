package br.ufc.crateus.eda.st;

public interface ST<k, v> {	
		v get(k key);		
		void put(k key, v val);
		void delete(k key);
		boolean contains(k key);
		int size();
		boolean isEmpty();
		Iterable<k> keys();	
}
