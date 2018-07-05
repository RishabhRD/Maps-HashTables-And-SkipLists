package secure;

import java.util.ArrayList;

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    private UnsortedTableMap<K,V>[] table;
    public ChainHashMap() {
    	super();
    }
    public ChainHashMap(int cap) {
    	super(cap);
    }
    public ChainHashMap(int cap,int p) {
    	super(cap,p);
    }
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(int h=0; h<capacity;h++) {
			if(table[h]!=null)
				for(Entry<K,V> e : table[h].entrySet()) {
					buffer.add(e);
				}
		}
		return buffer;
	}

	@Override
	protected void createTable() {
		// TODO Auto-generated method stub
		table =(UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
	}

	@Override
	protected V bucketGet(int h, K k) {
		// TODO Auto-generated method stub
		UnsortedTableMap<K,V> bucket = table[h];
		if(bucket==null)return null;
		return bucket.get(k);
	}

	@Override
	protected V bucketPut(int h, K key, V value) {
		// TODO Auto-generated method stub
		UnsortedTableMap<K,V> bucket = table[h];
		if(bucket==null)
			bucket = table[h] = new UnsortedTableMap<>();
		int oldSize = bucket.size();
		V answer = bucket.put(key, value);
		n+=(bucket.size()-oldSize);
		return answer;
	}

	@Override
	protected V bucketRemove(int h, K key) {
		// TODO Auto-generated method stub
		UnsortedTableMap<K,V> bucket = table[h];
		if(bucket==null)return null;
		V ans = bucket.get(key);
		int oldSize = bucket.size();
		bucket.remove(key);
		n-=(bucket.size()-oldSize);
		return ans;
	}

}
