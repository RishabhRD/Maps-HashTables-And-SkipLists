package secure;

import java.util.Comparator;

public abstract class AbstractSortedMap<K,V> extends AbstractMap<K,V> implements SortedMap<K,V>{
	protected Comparator<K> c ;
	public AbstractSortedMap(Comparator<K> c) {
		this.c =c;
	}
	public AbstractSortedMap() {
		this(new DefaultComparator<K>());
	}
	public int compare(K k1,Entry<K,V> e) {
		return c.compare(k1, e.getKey());
	}
}
