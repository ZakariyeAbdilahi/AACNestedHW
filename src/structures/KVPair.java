/**
 * Implentation of a Key-Value pair
 * @author Zakariye Abdilahi 
 * @author Samuel A. Rebelsky
 */
package structures;
public class KVPair<K,V>{
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+
  K key;
  V value;
  public KVPair(K key, V val){
    this.key = key;
    this.value = val;
  }
  /**
   * Set the value associated with a given key. 
   */
  public void set(K key, V value){
    this.key = key;
    this.value = value;
  }
  /**
   * Get the value of the key. 
   */
  public K getkey(){
    return this.key;
  }

  /**
   * Get the value of the value. 
   */
  public V getvalue(){
    return this.value;
  }

  /*
   * Returns key: value
   */
  public String toString(){
    return this.key.toString() +": "+this.value.toString();
  }
}