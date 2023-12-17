import java.util.List;
import java.util.Objects;
import java.util.*;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */


	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		// TODO Finish initializing instance fields
		if (initialCapacity < 0) {throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);}
		if (loadFactor <= 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);}
		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;
		this.size = 0;

		// if you use Separate Chaining
		buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];

		// if you use Linear Probing
		//entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}
	
	@SuppressWarnings("unchecked")
	public void expandCapacity() {
	        List<HashMapEntry<K, V>>[] newData = (List<HashMapEntry<K, V>>[])(new List[this.capacity * 2]);
	        List<HashMapEntry<K, V>>[] og = this.buckets;

	        this.buckets = newData;
	        //this.size = 0;
	        for(int i = 0; i < og.length; i++) {
	            if(og[i] == null) { 
					continue; }
	            for(HashMapEntry<K, V> h: og[i]) {
	                this.set(h.key, h.value);}}}
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// can also use key.hashCode() assuming key is not null
		if (key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		if (this.loadFactor > DEFAULT_LOAD_FACTOR) {expandCapacity();}
		HashMapEntry<K, V> newEntry = new HashMapEntry<>(key, value);
		int hashkey = Math.abs(key.hashCode()) % this.capacity;
	
		if (containsKey(key)) {
			return false;}
		
		if (this.buckets[hashkey] == null) {
			this.buckets[hashkey] = new ArrayList<>();
			this.size++;
			this.buckets[hashkey].add(newEntry);
			return true;}
		else if (this.buckets[hashkey] != null)	{
			this.buckets[hashkey].add(newEntry);
			this.size++;
			return true;}
		return false;}
	

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {

		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}

		int hashkey = Math.abs(key.hashCode()) % this.capacity;

		if (containsKey(key) == false) {
			return false;}

		for (int i = 0; i < this.buckets[hashkey].size(); i++){
			HashMapEntry<K, V> input = this.buckets[hashkey].get(i);

			if (input.getKey().equals(key)) {
				input.setValue(newValue);
				return true;}}
		return false;}

	@Override
	public boolean remove(K key) throws IllegalArgumentException{

		if (key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		
		int hashkey = Math.abs(key.hashCode()) % this.capacity;
		
		if (containsKey(key) == false) {return false;}
		for (int i = 0; i < this.buckets[hashkey].size(); i++){
			if (this.buckets[hashkey].get(i).getKey().equals(key)){
				this.buckets[hashkey].remove(i);
				this.size--;
				return true;}}
		return false;}

	@Override
	public void set(K key, V value) throws IllegalArgumentException{

		if (key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		
		if (this.loadFactor > DEFAULT_LOAD_FACTOR) {expandCapacity();}

		int hashkey = Math.abs(key.hashCode()) % this.capacity;
		
        if(this.buckets[hashkey] == null) {
            this.buckets[hashkey] = new ArrayList<HashMapEntry<K, V>>();
            this.buckets[hashkey].add(new HashMapEntry<K, V>(key, value));
            this.size++;}
        else {
            for(HashMapEntry<K, V> entry: this.buckets[hashkey]){
                if(entry.getKey().equals(key)) { 
                    entry.setValue(value);
                    return;}}
            this.buckets[hashkey].add(new HashMapEntry<K, V>(key, value));
            this.size++;}}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}
		int hashkey = Math.abs(key.hashCode()) % this.capacity;
		
		if (this.buckets[hashkey] == null) {
			return null;}
		else {	
			for (int i = 0; i < this.buckets[hashkey].size(); i++){
				if (this.buckets[hashkey].get(i).getKey().equals(key)){
					return this.buckets[hashkey].get(i).getValue();}}}
		return null;}

	@Override
	public int size() {return this.size;}

	@Override
	public boolean isEmpty() {
		
		if (this.size() == 0) {return true;}
		else {return false;}}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		

		if (key == null) {throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);}

		int hashkey = Math.abs(key.hashCode()) % this.capacity;
		
		if (this.buckets[hashkey] == null) {return false;}

		for (int i = 0; i < this.buckets[hashkey].size(); i++) {
			if (this.buckets[hashkey].get(i).getKey().equals(key))
			{return true;}}
		return false;}
//
	@Override
	@SuppressWarnings("unchecked")
	public List<K> keys() {

		List<K> list = (List<K>) new ArrayList<HashMapEntry<K, V>>();
		
		if (this.size == 0) {
			return list;}

		else {
			for (int i = 0; i < this.buckets.length; i++) {
				if (this.buckets[i] != null) {
					for (int j = 0; j < this.buckets[i].size(); j++) {
						list.add(this.buckets[i].get(j).getKey());}}}
			return list;}}
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;}
		@Override
		public K getKey() {
			return key;}
		@Override
		public V getValue() {
			return value;}
		@Override
		public void setValue(V value) {
			this.value = value;}}}
