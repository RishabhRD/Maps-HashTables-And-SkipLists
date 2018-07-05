package secure;

import java.util.ArrayList;
import java.util.Comparator;

public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();
    public SortedTableMap() {
    	super();
    }
    public SortedTableMap(Comparator<K> c) {
    	super(c);
    }
    private int findIndex(K key,int low,int high) {
    	if(low>high) return high+1;
    	int mid = (low+high)/2;
    	int comp = compare(key,table.get(mid));
    	if(comp==0)
    		return mid;
    	else if(comp<0)
    		return findIndex(key,low,mid-1);
    	else
    		return findIndex(key,mid+1,high);
    }
    private int findIndex(K key) {
    	return findIndex(key,0,table.size()-1);
    }
    private Entry<K,V> safeEntry(int j){
    	if(j<0||j>=table.size())
    		return null;
    	return table.get(j);
    }
	@Override
	public Entry<K, V> firstEntry() {
		// TODO Auto-generated method stub
		return safeEntry(0);
	}

	@Override
	public Entry<K, V> lastEntry() {
		// TODO Auto-generated method stub
		return safeEntry(table.size()-1);
	}

	@Override
	public Entry<K, V> ceilingEntry(K k) {
		// TODO Auto-generated method stub
		return safeEntry(findIndex(k));
	}

	@Override
	public Entry<K, V> floorEntry(K k) {
		// TODO Auto-generated method stub
		int j = findIndex(k);
		if(j==size()||compare(k,table.get(j))!=0) j--;
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> lowerEntry(K k) {
		// TODO Auto-generated method stub
		int j = findIndex(k)-1;
		return safeEntry(j);
	}

	@Override
	public Entry<K, V> higherEntry(K k) {
		// TODO Auto-generated method stub
		int j = findIndex(k);
		if(j<size()&&compare(k,table.get(j))==0) j++;
		return safeEntry(j);
	}
    private Iterable<Entry<K,V>> snapshot(int start,K stop){
    	ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    	int j = start;
    	while(j<table.size()&&(stop==null||compare(stop,table.get(j))>0))
    		buffer.add(table.get(j++));
    	return buffer;
    }
	@Override
	public Iterable<Entry<K, V>> subMap(K k1, K k2) {
		// TODO Auto-generated method stub
		return snapshot(findIndex(k1),k2);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return table.size();
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		if(j==size()||compare(key,table.get(j))!=0) return null;
		return table.get(j).getValue();
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		if(j<size()&&compare(key,table.get(j))==0) {
			return table.get(j).setValue(value);
		}
		table.add(j,new MapEntry<K,V>(key,value));
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		int j = findIndex(key);
		if(j<size()||compare(key,table.get(j))!=0) {
			return null;
		}
		return table.remove(j).getValue();
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return snapshot(0,null);
	}

}
