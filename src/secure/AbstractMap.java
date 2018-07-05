package secure;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	public boolean isEmpty() {
		return size()==0;
	}
	protected static class MapEntry<K,V> implements Entry<K,V>{
        private K k;
        private V v;
        public MapEntry(K key,V value) {
        	k = key;
        	v = value;
        }
		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return k;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return v;
		}
		protected void setKey(K key) {
			k = key;
			
		}
		protected V setValue(V value) {
			V val = v;
			v = value;
			return val;
		}
		
	}
	private class KeyIterator implements Iterator<K>{
        private Iterator<Entry<K,V>> entries = entrySet().iterator();
        
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return entries.hasNext();
		}

		@Override
		public K next() {
			// TODO Auto-generated method stub
			return entries.next().getKey();
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	private class KeyIterable implements Iterable<K>{

		@Override
		public Iterator<K> iterator() {
			// TODO Auto-generated method stub
			return new KeyIterator();
		}
		
	}
	public Iterable<K> keySet(){
		return new KeyIterable();
	}
	private class ValueIterator implements Iterator<V>{
        private Iterator<Entry<K,V>> entries = entrySet().iterator();
        
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return entries.hasNext();
		}

		@Override
		public V next() {
			// TODO Auto-generated method stub
			return entries.next().getValue();
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	private class ValueIterable implements Iterable<V>{

		@Override
		public Iterator<V> iterator() {
			// TODO Auto-generated method stub
			return new ValueIterator();
		}
		
	}
	public Iterable<V> values(){
		return new ValueIterable();
	}

}
