package br.ufc.crateus.eda.st.ultils;

public class ListArray<T> {

	T[] vet;
	int cap;
	int numElem =0;
	
	@SuppressWarnings("unchecked")
	public ListArray(int cap){
		this.cap =cap;
		this.vet = (T[]) new Object[cap];		
	}	
	
	public ListArray(){
		this(10);		
	}
	
	@SuppressWarnings("unchecked")
	public void put(T t){
		// VERIFICAR SE JÁ ESTÁ ESGOTADO
		if(numElem == cap){  			 	
		cap *= 2;		
		T[] aux = (T[]) new Object[cap];		
		//System.arraycopy(T, 0, aux, 0, this.T.length);
		for(int i=0; i< numElem; i++){
			aux [i] = vet[i];			
		}
		vet = aux; // TROCANDO A REFERENCIAS.
		}
		//ADICIONA O NOVO NO ListArray.
		vet[numElem++] =t;
	}
	
	public T get(int i){
		return (i < numElem)? vet[i] : null;		
	}
	
	public int size(){
		return numElem;		
	}
	
	public static void main(String [] args){
		
		ListArray<Integer> ar = new ListArray<>(3);
		ar.put(1);
		ar.put(2);
		ar.put(3);
		ar.put(4);
		ar.put(5);
		ar.put(6);
		
		for (int i = 0; i < ar.size(); i++) {
			System.out.println(ar.get(i));
		}				
	}	
}
