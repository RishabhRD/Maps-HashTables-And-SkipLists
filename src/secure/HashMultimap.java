package secure;


import java.util.ArrayList;
import java.util.List;

public class HashMultimap<K, V> {
    Map<K,List<V>> map = new ChainHashMap<>();
    int total = 0;
    public HashMultimap() {}
    public int size() {
    	return total;
    }
    public boolean isEmpty() {
    	return total==0;
    }
    Iterable<V> get(K key){
    	List<V> secondary = map.get(key);
    	if(secondary!=null)
    		return secondary;
    	return new ArrayList<>();
    }
    void put(K key,V value) {
    	List<V> secondary = map.get(key);
    	if(secondary == null) {
    		secondary = new ArrayList<>();
    		map.put(key, secondary);
    	}
    	secondary.add(value);
    	total ++;
    }
    boolean remove(K key, V value) {
    	boolean wasRemoved = false;
    	List<V> list= map.get(key);
    	if(list!=null) {
    		wasRemoved = list.remove(value);
    		if(wasRemoved) {
    			total--;
    			if(list.isEmpty()) {
    				map.remove(key);
    			}
    		}
    	}
    	return wasRemoved;
    }
    Iterable<V> removeAll(K key){
    	List<V> list = map.get(key);
    	if(list!=null) {
    		total-=list.size();
    		map.remove(key);
    	}else
    		list = new ArrayList<>();
    	return list;
    }
    Iterable<Entry<K,V>> entries(){
    	List<Entry<K,V>> list = new ArrayList<>();
    	for(Entry<K, List<V>> sec : map.entrySet()) {
    		K key = sec.getKey();
    		for(V value : sec.getValue()) {
    			list.add(new AbstractMap.MapEntry<K,V>(key,value));
    		}
    	}
    	return list;
    }
    
    
}
