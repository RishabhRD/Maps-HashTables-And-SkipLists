package secure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {
    ArrayList<MapEntry<K,V>> list = new ArrayList<>();
    public UnsortedTableMap() {}
    private int findIndex(K key) {

    	for(int i=0; i<list.size();i++) {
    		if(list.get(i).getKey().equals(key)) return i;
    	}
    	return -1;
    }
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		if(j==-1)
		  return null;
		return list.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		
		if(j==-1) {
			list.add(new MapEntry<K,V>(key,value));		
			return null;
		}else {
			return list.get(j).setValue(value);
		}
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		int n = size();
		if(j==-1) return null;
		V answer = list.get(j).getValue();
		if(j!=n-1) {
			list.set(j, list.get(n-1));
		}
		list.remove(n-1);
		return answer;
	}
	private class EntryIterator implements Iterator<Entry<K,V>>{
		private int j=0;
		public boolean hasNext() {
			return j<list.size();
		}
		public Entry<K,V> next(){
			if(j==list.size()) throw new NoSuchElementException();
			return list.get(j++);
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	private class EntryIterable implements  Iterable<Entry<K,V>>{

		@Override
		public Iterator<Entry<K, V>> iterator() {
			// TODO Auto-generated method stub
			return new EntryIterator();
		}
		
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return new EntryIterable();
	}

}
