import structures.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates a mapping of all AACCategories
 * 
 * @author Zakariye Abdilahi
 * @author Catie Baker
 * Help obtained: Evening Tutors
 */
public class AACMappings {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+
  private String catName;
  private AACCategory home;
  private AACCategory category;
  private AssociativeArray<String, AACCategory> allCategories;
  private AssociativeArray<String, String> imgCatMap;
  // +--------+------------------------------------------------------------
  // | Constructors|
  // +-------------+
  public AACMappings(String fileInput) throws Exception {
    // initialize our home category, allCategories, imgCatMap and open file to read from
    this.home = new AACCategory("homeCat");
    this.allCategories = new AssociativeArray<String, AACCategory>();
    this.imgCatMap = new AssociativeArray<String, String>();
    File file = new File(fileInput);
    Scanner scanner = new Scanner(file);
    String input = scanner.nextLine();
    while (input != null) {
      if (input.startsWith(">")) {
        // Parsing item lines
        String[] parts = input.substring(1).trim().split(" ", 2);
        String imgLoc = parts[0];
        String textToSpeak = parts[1];
        // add the image to the current category and update the category
        this.category.addItem(imgLoc, textToSpeak);
        allCategories.set(this.catName, this.category);
        
    } else {
        // Parsing category line
        String[] parts = input.trim().split(" ", 2);
        String imgLoc = parts[0];
        String curCat = parts[1];
        // add the location to a map of all image locations and the category they represent
        this.imgCatMap.set(imgLoc, curCat);
        this.catName = curCat;
        // add the image location and name to our home category
        this.home.addItem(imgLoc, curCat);
        this.category = new AACCategory(curCat);
        // create a new category and update allCategories to include it
        this.allCategories.set(catName, this.category);

    }
    try {
      input = scanner.nextLine();
    } catch (Exception e) {
      input = null;
    }
    }
    scanner.close();
    this.catName = "";
    this.category = this.home;
  }
  // +--------+------------------------------------------------------------
  // | Methods|
  // +--------+
  /* 
  * Adds the mapping to the current category 
  * (or the default category if that is the current category)
  */
  public void add(String imageLoc, String text) throws Exception {
    if (this.catName.equals("")){
      // if its home then we are creating a category
      // so we add our location and category to the imgCatMap
      this.imgCatMap.set(imageLoc, text);
      // we now add the category to our home
      this.home.addItem(imageLoc, text);
      this.category = new AACCategory(text);
      // create new category and add it to our list of categories
      this.allCategories.set(text, this.category);
    } else {
      // otherwise we are an image to the current category
      this.category.addItem(imageLoc, text);
      // update allCategories
      allCategories.set(this.catName, this.category);
    }
  }
  /*
  * Returns the current category
  */
  public String getCurrentCategory() {
    return this.catName;
  }
  /*
    * Provides an array of all the images in the current category
    */
  public String[] getImageLocs() {
    if (this.catName.equals("")) {
      return this.home.getImages();
    }
    return this.category.getImages();
  }
  /*
    * Given the image location selected, it determines the associated text with the image.
    */
  public String getText(String imageLoc) throws Exception {
    if (this.catName.equals("")) {
      this.catName = this.imgCatMap.get(imageLoc);
      this.category = this.allCategories.get(this.catName);
      return this.catName;
    }
    return this.category.getText(imageLoc);
  }
/*
  * Determines if the image represents a category or text to speak
  */
  public boolean isCategory(String imageLoc) {
    return this.imgCatMap.hasKey(imageLoc);
  }
  /*
  * Resets the current category of the AAC back to the home category
  */
  public void reset() {
    this.catName = "";
    this.category = this.home;
  }
  /*
  * Writes the ACC mappings stored to a file.
  */
  public void writeToFile(String filename) throws IOException {
    String catLoc, location, name;
    String itemsInCat = "";
    // Open file for writing
    FileWriter writer = new FileWriter(filename);
    for (KVPair<String, String> pair : this.imgCatMap) {
      // go through each image location mapped to a category that we have so far
       location = pair.getkey();
       name = pair.getvalue();
       try {
        // get the string of a particular category which includes all its items
        itemsInCat = allCategories.get(name).toString();
      } catch (KeyNotFoundException e) {
        e.printStackTrace();
      }
      // combine the location and name and add the newline
      // then we write to our file as appropriate
       catLoc = location + " " + name + "\n";
       writer.write(catLoc); 
       writer.write(itemsInCat);
    }
    writer.close();
  }
}

