package br.ufc.crateus.eda.st.btree;

public class Page<K> {

private boolean botton;
	
	Page(boolean booton) {
		this.botton = booton;
	}
	
	public void close() {
		
	}
	
	public void insert(K key)  {
		
	}
	
	public void enter(Page<K> page) {
		
	}
	
	public boolean isExternal() {
		return botton;
	}
	
	public boolean holds(K key) {
		return false;
	}
	
	Page<K> next(K key) {
		return null;
	}
	
	public boolean hasOverflowed() {
		return false;
	}
	
	public Page<K> split() {
		return null;
	}
	
	Iterable<K> keys() {
		return null;
	}

}
