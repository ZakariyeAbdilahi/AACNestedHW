package structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Zakariye Abdilahi
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K,V> implements Iterable<KVPair<K,V>>{

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+
  private KVPair<K,V>[] array;
  int size;
  int capacity;
  // +--------+------------------------------------------------------------
  // | Constructors|
  // +-------------+
  public AssociativeArray(){
    this.array = (KVPair<K,V>[]) new KVPair[10];
    this.size = 0;
    this.capacity = 10;
  }
  public AssociativeArray(KVPair<K,V>[] arr, int size, int cap){
    this.array = arr;
    this.size = size;
    this.capacity = cap;
  }

  // +--------+------------------------------------------------------------
  // | Methods|
  // +--------+
  /**
   * Set the value associated with a given key. 
   * If there is already another value associated with the given key, 
   * this new value replaces that value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null){
      throw new NullKeyException();
    }
    int pos = this.getKeyPos(key);
    if (pos > -1) {
      this.array[pos].set(key, value);
      return;
    }
    this.array[size] = new KVPair<K,V>(key, value);
    this.size++;
    this.resize();
    return;
  }
  /**
   * Get the position of a key or -1 if it doesnt exist
   */
  public int getKeyPos(K key){
    for (int i = 0; i < this.size; i++) {
      if (this.array[i].getkey().equals(key)) {
        return i;
      }
    }
    return -1;
  }
  /**
   * Get the value associated with a given key. If there is no such key,
   *  throws an exception.
   */
  public V get(K key) throws KeyNotFoundException{
      int pos = this.getKeyPos(key);
      if (pos > -1) {
        V val = this.array[pos].getvalue();
        return val;
      }
      throw new KeyNotFoundException("Key not found");
  }
  

  /* 
   * Determines if the given key appears in the associative array.
  */
  public boolean hasKey(K key){
    int pos = this.getKeyPos(key);
    if (pos > -1) {
      return true;
    }
    return false;
  }

  /*
   * Remove the key/value pair associated with the given key. 
   * If the key does not appear in the associative array, does nothing.
   */
  public void remove(K key){
    int pos = this.getKeyPos(key);
    if (pos == -1) { // key not found
      return;
    }
    if (this.size() == 1){ // edge case, 1 key in array
      this.array[0] = null;
      this.size--;
      return;
    }
    // Find the object and store the last object in the array in its place
    this.array[pos] = this.array[size-1];
    this.array[size-1] = null;
    this.size--;
  }

  /*
   * Determine how many key/value pairs are currently stored in the associative array.
   */
  public int size(){
    return this.size;
  }
  /*
   * Returns a copy of the associative array
   */
  public AssociativeArray<K,V> clone(){
    KVPair[] copy = new KVPair[this.capacity];
    for (int i = 0; i < this.size; i++){
      K key = this.array[i].getkey();
      V val = this.array[i].getvalue();
      copy[i] = new KVPair<K,V>(key, val);
    }
    AssociativeArray result = new AssociativeArray<K,V>(copy, this.size, this.capacity);
    return result;
  }
  /*
   * Return a string of the form "{ key0: value0, key1: value1, ... keyn: valuen }" 
   * If the array is empty, you should return "{}".
   */
  public String toString(){
    if (this.size==0){
      return "{}";
    }
    String result = "{ ";
    for (int i = 0; i < this.size-1; i++) {
      result = result + this.array[i].toString();
      result = result + ", ";
    }
    result = result + this.array[this.size-1];
    result = result + " }";
    return result;
  }
  // +--------+------------------------------------------------------------
  // | Private Methods|
  // +----------------+
  /*
  * double the size of this.array and copy everything over
  */
  private void resize(){
    if (this.size < this.capacity){
      return;
    }
    KVPair<K,V>[] newArray = new KVPair[this.capacity*2];
    for (int i = 0; i < size; i++) {
      newArray[i] = this.array[i];
    }
    this.array = newArray;
    this.capacity = this.capacity*2;
  }
  @Override
  public Iterator<KVPair<K,V>> iterator() {
      return new AssociativeArrayIterator();
  }
  private class AssociativeArrayIterator implements Iterator<KVPair<K,V>> {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
          return currentIndex < size;
      }
      @Override
      public KVPair<K,V> next() {
          if (!hasNext()) {
              throw new NoSuchElementException();
          }
          return array[currentIndex++];
      }
  }
}