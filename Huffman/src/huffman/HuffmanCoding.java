package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;


/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */

    public void makeSortedList() {
        StdIn.setFile(fileName);

	/* Your code goes here */
    
    ArrayList<Character> chark = new ArrayList<>();

    while(StdIn.hasNextChar() == true){ //add all chars 
        chark.add(StdIn.readChar());
    }



    int size = chark.size();
    if(chark.size() == 0){
        return;
    }

    int counter = 1;
    
    
    if(size >1){
    for(int i =1; i<size; i++){
        if(chark.get(i-1) != chark.get(i)){ //checking to see if it consists of one character
            counter++;
        }
    
    }
    }

Collections.sort(chark);


size = chark.size();

    //count the frequency
ArrayList<Double> frek = new ArrayList<>();
 int count = 0;

for(int i =0; i<size; i++){
    for(int j =0; j<size; j++){
        if(chark.get(i) == chark.get(j)){
            count++;
        }
    }
    double prob = count;
    prob = prob/size;
    frek.add(prob);
    count =0;
    
}

//double rref = 1.0/size;

ArrayList<Character> Fchar = new ArrayList<>();
ArrayList<Double> Fprob = new ArrayList<>();

if(size == 1 || size == 0){
    
} else{

for(int i =0; i<chark.size()-1; i++){
    if(chark.get(i) != chark.get(i+1)){
        Fchar.add(chark.get(i));
        Fprob.add(frek.get(i));
    }
}
Fchar.add(chark.get(size-1));
Fprob.add(frek.get(size-1));
}




sortedCharFreqList = new ArrayList<>();
for(int i =0; i<Fchar.size(); i++){
    
    CharFreq temp = new CharFreq(Fchar.get(i),Fprob.get(i));
    sortedCharFreqList.add(temp);
}



char x;  
if(counter == 1){
     x = chark.get(0);
    int y = (int)x;
    if(y == 127){
        y =0;
        x=(char)y;
        CharFreq filler = new CharFreq(x, 0.0);
        sortedCharFreqList.add(filler);
    } else{
    y++;
    x = (char)y;
    CharFreq filler = new CharFreq(x, 0.0);
    sortedCharFreqList.add(filler);

    }
}

Collections.sort(sortedCharFreqList);

    


    








    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */

private void thedequer(Queue<CharFreq> source,Queue<TreeNode> target,TreeNode tempo,CharFreq temp1,ArrayList<TreeNode> array){

    if(target.isEmpty() == true && source.isEmpty() == false){ //if it was empty automatically dequeue the next node in source (since nothing to compare to)
        temp1 = source.dequeue();
        tempo = new TreeNode();
        tempo.setData(temp1);
        array.add(tempo); //temporarily storing value so we can create the sum of the two nodes
        return; //just added a node so count updates
    }

    if(source.isEmpty() == true){ 
        tempo = target.dequeue();
        array.add(tempo);
        return;
    }

    if(target.isEmpty() == false && source.isEmpty() == false){
        if(source.peek().getProbOcc() <= target.peek().getData().getProbOcc()){
            temp1 = source.dequeue();
            tempo = new TreeNode();
            tempo.setData(temp1);
            array.add(tempo); //temporarily storing value so we can create the sum of the two nodes
            return;
        } else {
            tempo = target.dequeue();
            array.add(tempo);
            return;
        }
    
    

    }

}

    public void makeTree() {

	/* Your code goes here */

    
    Queue<CharFreq> source = new Queue<>(); //step 1 create empty source and target queues
    Queue<TreeNode> target = new Queue<>();

    for(int i =0; i<sortedCharFreqList.size(); i++){ // step 2+3 enqueuing sortedCharfreq into source
    source.enqueue(this.sortedCharFreqList.get(i));
}

//int sizer = source.size();

if(source.size() == 1){ //ensuring file has at least two nodes
    huffmanRoot = new TreeNode();
    huffmanRoot.setData(source.dequeue());
    return;
}


    ArrayList<TreeNode>  array= new ArrayList<>();
   
    int nodecount = 0;

    
    TreeNode tempo = new TreeNode();
    CharFreq temp1 = new CharFreq();
    CharFreq summer = new CharFreq();
    double sum = 0.0;

    
    while(source.isEmpty() != true){ //loop iterating through till source queue is empty
    nodecount =0; //keeping track of how many nodes we dequeued
    array.removeAll(array);//takes all elements out of array after finding two (resetting it)
    while(nodecount != 2){ // running through the if statemants till we get two nodes
   
    thedequer(source, target, tempo, temp1, array); //remove one 
    nodecount++;
    thedequer(source, target, tempo, temp1, array); //remove two
    nodecount++;
    


    sum = array.get(0).getData().getProbOcc() + array.get(1).getData().getProbOcc();
    summer = new CharFreq(null, sum);
    TreeNode enny = new TreeNode(summer, array.get(0), array.get(1));
    target.enqueue(enny);
    array.removeAll(array);

    }
   

    
    }

while(target.size() != 1){
    TreeNode enny = target.dequeue();
    TreeNode enny2 = target.dequeue();
    sum = enny.getData().getProbOcc() + enny2.getData().getProbOcc();
    summer = new CharFreq(null,sum);
    TreeNode enny3 = new TreeNode(summer, enny, enny2);
    target.enqueue(enny3);
}


huffmanRoot = target.dequeue();




}






private void inorderchars(TreeNode root, ArrayList<Character> sorchar){ //finding the inorder traversal of the leadnodes
    if(root == null){
    return;
    }

    if(root.getLeft() != null){
        inorderchars(root.getLeft(), sorchar);

    }

    if(root.getRight() != null){
        inorderchars(root.getRight(), sorchar);
    }

    if(root.getLeft() == null && root.getRight() == null){
        char temp = root.getData().getCharacter();
        sorchar.add(temp);
    }
}




private String findcode(TreeNode root, String daCode, ArrayList<String> charry){
    if(root == null){ //if root is null we dont do anything and return nothing;
        return null;
    }

    findcode(root.getLeft(), daCode +"0", charry); //traverse the leftside each left adds a zero
    findcode(root.getRight(), daCode + "1", charry);//traverse the rightside each right adds 1 
    if(root.getLeft() == null && root.getRight() == null){ //if we hit a lead node update the code
       String temp = daCode;
       charry.add(temp);
    }

    return daCode;
}
    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

	/* Your code goes here */
    encodings = new String[128];
   // ArrayList<Integer> future = new ArrayList<>();
    ArrayList<String> induct = new ArrayList<>();
    ArrayList<Character>  sorchar = new ArrayList<>();


    String s = "";
    s = findcode(huffmanRoot, s, induct);
    inorderchars(huffmanRoot, sorchar);
    

  for(int i =0; i<sorchar.size(); i++){
    char temp1 = sorchar.get(i);
    int temp2 = (int)temp1;

    encodings[temp2] = induct.get(i);

  }



}

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        ArrayList<Character> textwords= new ArrayList<>(); //has all the characters in the text in order

        while(StdIn.hasNextChar() == true){ //add all chars 
            textwords.add(StdIn.readChar());
        }

        String[] dacodes = getEncodings(); // get the codes for what each character means
        String bity = ""; //this is what we will be adding 
        for(int i =0; i<textwords.size(); i++){
           char temp = textwords.get(i); //get the current char 
           int index = (int)temp; //find what index of encodings it is
           bity += dacodes[index]; //add it to the end of the string
           
        }

        writeBitString(encodedFile, bity); //create the encoded file

        
	/* Your code goes here */

    

    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     * 
     */



    public void decode(String encodedFile, String decodedFile) {
        String bity = readBitString(encodedFile);
        StdOut.setFile(decodedFile);

	/* Your code goes here */
    

     //getting codes
    
    
    char[] text = bity.toCharArray(); //all the numbers in a single line
    TreeNode travy = huffmanRoot; // node for traversing
    ArrayList<Character> trans = new ArrayList<>(); //this is where the actual words go


    for(int i =0; i<text.length; i++){
        if(travy.getLeft() != null && travy.getRight() != null){ //if its not a leaf execute
        if(text[i] == '1'){ //if it 1 we go right
            travy = travy.getRight();
        } else { //if zero we go left
            travy = travy.getLeft();
        }
    }

        if(travy.getLeft() == null && travy.getRight() == null){ //if its a leaf node we execute it
            trans.add(travy.getData().getCharacter()); //add the character to end the array
            travy = huffmanRoot;
        }
        
    
    }

    

    String theactualtext = "";

    for(int i =0; i<trans.size();i++){ //turning array into string
        theactualtext+=trans.get(i).toString();
    }

    StdOut.print(theactualtext); //print it out



    

    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
