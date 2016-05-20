package br.ufc.crateus.eda.utils;



public class ListArray<T> {
	T[] vet;
	int cap;
	int numElem = 0;
	
	@SuppressWarnings("unchecked")
	public ListArray(int cap) {
		this.cap = cap;
		this.vet = (T[]) new Object[cap];
	}
	
	public ListArray() {
		this(10);
	}
	
	@SuppressWarnings("unchecked")
	public void add(T t) {
		if (numElem == cap) {
			cap *= 2;
			T[] aux = (T[]) new Object[cap];
			for (int i = 0; i < numElem; i++) {
				aux[i] = vet[i];
			}
			vet = aux;
		}
		vet[numElem++] = t;
	}
	
	public T get(int i) {
		return (i < numElem)? vet[i] : null;
	}
	
	public int size() {
		return numElem;
	}
	
	public static void main(String[] args) {
		ListArray<Integer> ar = new ListArray<>(3);
		
		ar.add(1);
		ar.add(2);
		ar.add(3);
		ar.add(4);
		ar.add(5);
		ar.add(6);
		
		for (int i = 0; i < ar.size(); i++)
			System.out.println(ar.get(i));
	}
}







