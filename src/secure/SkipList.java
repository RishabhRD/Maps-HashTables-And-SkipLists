package secure;
import java.util.ArrayList;
import java.util.Random;

public class SkipList<K, V> extends AbstractSortedMap<K, V> {
	
	public Node<K,V> min ;
	private Node<K,V> max;
	private Node<K,V> start;
	private int size;
	private int height = 0;
	protected static class Node<K,V> {
		private Node<K,V> left,right,above,below;
		private MapEntry<K,V> element;
		public Node(MapEntry<K,V> element,Node<K,V> left,Node<K,V> right,Node<K,V> above,Node<K,V> below) {
			this.left = left;
			this.right = right;
			this.above = above;
			this.below = below;
			this.element = element;
		}
		public MapEntry<K,V> setElement(MapEntry<K,V> entry) {
			MapEntry<K,V> ans = element;
			element = entry;
			return ans;
		}
		public MapEntry<K, V> getElement() {
			return element;
		}
		public void setNext(Node<K,V> next) {
			this.right = next;
		}
		public void setPrev(Node<K,V> prev) {
			this.left = prev;
		}
		public void setAbove(Node<K,V> above) {
			this.above = above;
		}
		public void setBelow(Node<K,V> below) {
			this.below = below;
		}
		public  Node<K,V> prev(){
			return left;
		}
		public  Node<K,V> next(){
			return right;
		}
		public  Node<K,V> above(){
			return above;
		}
		public  Node<K,V> below(){
			return below;
		}
	}
    public SkipList(K min, K max) {
    	this.min = createSenital(min,null,null,null,null);
    	this.max = createSenital(max,this.min,null,null,null);
    	this.min.setNext(this.max);
    	
    	start = this.min;
    	size=0;
    }
    private Node<K,V> createSenital(K key,Node<K,V> left, Node<K,V> right,Node<K,V> above,Node<K,V> below){
    	return new Node<K,V>(new MapEntry<K,V>(key,null),left,right,above,below);
    }
    private Node<K,V> skipSearch(K key){    //like greatest integer function
    	
    	Node<K,V> walk = start;
    	
    	while(walk.below()!=null) {
    		walk = walk.below();
    		while(compare(key, walk.next().getElement())>=0) {
    			walk = walk.next();
    		}
    	}
    	return walk;
    }
    private Node<K,V> insertAfterAbove(Node<K,V> p,Node<K,V> q,MapEntry<K,V> entry){
        Node<K,V> node ;    	
        Node<K,V> after;
        Node<K,V> up;
        if(p==null) after = null;
        else after = p.next();
        if(q==null) up = null;
        else up = q.above();
        node = new Node<>(entry,p,after,up,q);	
    	if(p!=null)   p.setNext(node);
    	if(q!=null)   q.setAbove(node);
    	if(after!=null)
    	   after.setPrev(node);
    	if(up!=null)
    	   up.setBelow(node);
    	return node;
    }
    private Node<K,V> skipInsert(MapEntry<K,V> entry){
    	Random coin = new Random();
    	Node<K,V> walk = skipSearch(entry.getKey());
    	Node<K,V> cur =null;    //current node of new entry tower
    	int currentHeight = -1; //current height of new entry tower
    	do {
    		currentHeight++;
    		if(currentHeight>=height) {
    			
    			height++;
    			Node<K,V> t = start.next();
    			Node<K,V> newStart= insertAfterAbove(null,start,min.getElement());
    			start.setAbove(newStart);
    			start = newStart;
    			Node<K,V> newMax =insertAfterAbove(start,t,max.getElement());
    			start.setNext(newMax);
    		}
    		
    		
    		cur = insertAfterAbove(walk,cur,entry);
    		
    		while(walk.above()==null) {
    			walk = walk.prev();
    			
    		}
    		walk = walk.above();
    		
    		
    	}while(!coin.nextBoolean());
    	size++;
    	return cur;
    }
	@Override
	public Entry<K, V> firstEntry() {
		// TODO Auto-generated method stub
		Node<K,V> ans = min.next();
		if(ans==max) return null;
		return ans.getElement();
	}

	@Override
	public Entry<K, V> lastEntry() {
		// TODO Auto-generated method stub
		Node<K,V> ans = max.prev();
		if(ans==min)return null;
		return ans.getElement();
	}

	@Override
	public Entry<K, V> ceilingEntry(K k) {
		// TODO Auto-generated method stub
		Node<K,V> node = ceilingNode(k);
		if(node==null) return null;
		return node.getElement();
	}

	@Override
	public Entry<K, V> floorEntry(K k) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(k);
		if(node==max||node==min)
			return null;
		return node.getElement();
	}

	@Override
	public Entry<K, V> lowerEntry(K k) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(k);
		if(node.getElement().getKey()==k) {
			node = node.prev();
		}
		if(node==min||node==max)
			return null;
		return node.getElement();
	}

	@Override
	public Entry<K, V> higherEntry(K k) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(k);
		node = node.next();
		if(node==min||node==max)
			return null;
		return node.getElement();
	}
	private Node<K,V> ceilingNode(K k){
		Node<K,V> node = skipSearch(k);
		if(node.getElement().getKey()==k)
			return node;
		node = node.next();
		if(node==max||node==min)
			return null;
		return node;
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K k1, K k2) {
		// TODO Auto-generated method stub
		return snapshot(ceilingNode(k1),k2);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(key);
		if(node.getElement().getKey()!=key) {
			return null;
		}
		return node.getElement().getValue();
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(key);
		if(node.getElement().getKey()==key) {
			V ans = node.getElement().getValue();
			while(true) {
				node.getElement().setValue(value);
				if(node.above()!=null)
					break;
				node = node.above();
			}
			
			return ans;
		}
		skipInsert(new MapEntry<K,V>(key,value));
		
		return null;
		
	}
	private Iterable<Entry<K,V>> snapshot(Node<K,V> start,K stop){
    	ArrayList<Entry<K,V>> buffer = new ArrayList<>();
    	Node<K,V> st = start;
    	while(compare(stop,st.getElement())>0) {
    		buffer.add(st.getElement());
    		st = st.next();
    		
    	}
    		
    	return buffer;
    }

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		Node<K,V> node = skipSearch(key);
		Node<K,V> prev,next;
		if(node.getElement().getKey()!=key) {
			return null;
		}
		V ans = node.getElement().getValue();
		size--;
		while(true) {
			prev = node.prev();
			next = node.next();
			if(prev!=null)
				prev.setNext(next);
			if(next!=null)
				next.setPrev(prev);
			if(node.above()==null) break;
			node=node.above();
			
		}
		node=null;
		return ans;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		
		return snapshot(min.next(),max.getElement().getKey());
	}

}
