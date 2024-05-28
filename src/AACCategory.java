import structures.*;

/**
 * Creates a category of images and their texts
 * 
 * @author Zakariye Abdilahi
 * @author Catie Baker
 * Help obtained: Evening Tutors
 */
public class AACCategory{
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+
  private String name;
  private AssociativeArray<String, String> imageMap;
  // +--------+------------------------------------------------------------
  // | Constructors|
  // +-------------+
  /*
   *Creates a new empty category with the given name 
   */
  public AACCategory(String nameInput){
    this.name = nameInput;
    this.imageMap = new AssociativeArray<String, String>();
    //stub
  }
  // +--------+------------------------------------------------------------
  // | Methods|
  // +--------+
  /* 
   * Adds the mapping of the imageLoc to the text to the category.
  */
 public void addItem(String imageLoc, String text) throws NullKeyException{
  this.imageMap.set(imageLoc, text);
  }
/*
 * Returns the name of the category
 */
 public String getCategory() {
  return this.name;
 }
 /*
  * Returns an array of all the images in the category
  */
 public String[] getImages() {
  String[] images = new String[this.imageMap.size()];
  int size = 0;
  for (KVPair<String, String> pair : this.imageMap) {
    images[size++] = pair.getkey();
  }
  return images;
 }
 /*
  * Returns the text associated with the given image loc in this category
  */
 public String getText(String imageLoc) throws KeyNotFoundException{
  if (this.hasImage(imageLoc)) {
    return this.imageMap.get(imageLoc);
  }
  throw new KeyNotFoundException();
 }
 /*
  * Determines if the provided images is stored in the category
  */
  public boolean hasImage(String imageLoc) {
    return this.imageMap.hasKey(imageLoc);
  }

  /*
   * String version of the category
   */
  public String toString() {
    String result = "";
    for (KVPair<String, String> pair : this.imageMap) {
      result = result + ">" + pair.getkey() + " " + pair.getvalue() + "\n";
    }
    return result;
  }
}

