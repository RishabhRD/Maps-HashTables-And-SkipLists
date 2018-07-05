package secure;

public interface SortedMap<K, V> extends Map<K, V> {
      Entry<K,V> firstEntry();
      Entry<K,V> lastEntry();
      Entry<K,V> ceilingEntry(K k);
      Entry<K,V> floorEntry(K k);
      Entry<K,V> lowerEntry(K k);
      Entry<K,V> higherEntry(K k);
      Iterable<Entry<K,V>> subMap(K k1, K k2);
 }
