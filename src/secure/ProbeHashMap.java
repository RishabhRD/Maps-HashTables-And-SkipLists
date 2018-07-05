package secure;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K,V>[] table;
    private MapEntry<K,V> delete = new MapEntry<>(null,null);
    public ProbeHashMap() {
    	super();
    }
    public ProbeHashMap(int cap) {
    	super(cap);
    }
    public ProbeHashMap(int cap,int prime) {
    	super(cap,prime);
    }
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		ArrayList<Entry<K,V>> list = new ArrayList<>();
		for(int h=0; h<capacity;h++){
			if(!isAvailable(h))
				list.add(table[h]);
		}
		return list;
	}

	@Override
	protected void createTable() {
		// TODO Auto-generated method stub
		table = (MapEntry<K,V>[])new MapEntry[capacity];
	}
	private boolean isAvailable(int j) {
		return (table[j]==null||table[j]==delete);
	}
	private int findSlot(int h,K k) {
		int avail=-1;
		int j =h;
		do {
			if(isAvailable(j)) {
				if(avail==-1)
					avail =j;
				if(table[j]==null)
					break;        //Search failed
			} else if(table[j].getKey().equals(k))
				return j;
			j = (j+1)%capacity;   //You can change this scheme for double hashing.
		}while(j!=h);
		return -(avail+1);        //Search failed
	}

	@Override
	protected V bucketGet(int h, K k) {
		// TODO Auto-generated method stub
		int j =findSlot(h,k);
		if(j<0)return null;
		return table[j].getValue();
	}

	@Override
	protected V bucketPut(int h, K key, V value) {
		// TODO Auto-generated method stub
		int j = findSlot(h,key);
		if(j>=0) {
			return table[j].setValue(value);
		}
		table[-(j+1)] = new MapEntry<>(key,value);
		n++;
		return null;
	}

	@Override
	protected V bucketRemove(int h, K key) {
		// TODO Auto-generated method stub
		int j = findSlot(h,key);
		if(j<0) return null;
		V answer = table[j].getValue();
		table[j] = delete;
		n--;
		return answer;
	}
	
	

}
