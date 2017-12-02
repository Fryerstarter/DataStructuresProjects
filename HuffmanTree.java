/*
Christopher Kile
Project 5
n00656049
HuffmanTree
*/

import java.util.*;
import java.io.*;

public class HuffmanTree{
	public static void main(String[] args){
   Scanner input = new Scanner(System.in);
   mainInput inputProcessor = new mainInput();
   huffmanTree theTree = new huffmanTree();
   StringBuffer prefix = new StringBuffer();
   String[] encoding = new String[7];
   char userInput;
	String fileName = args[0];
   boolean test = true;
   priorityQ mainQueue = new priorityQ(7);
   ArrayList<String> inputStore = inputProcessor.inputStorage(fileName);
	ArrayList<Character> inputFiltered = inputProcessor.inputFilter(inputStore);
	ArrayList<Node> inputCount = inputProcessor.inputCounter(inputFiltered);
   
   

   while(test){
   userInput = inputProcessor.printMenu(input);
   
   switch(userInput){
   case 'a' :
   
   
	
	for(int i = 0; i < inputCount.size(); i++){
		mainQueue.insert(inputCount.get(i));
	}
	
	
	while(mainQueue.getItems()>1){
		Node temp1 = mainQueue.remove();
		Node temp2 = mainQueue.remove();
		Node newNode = new Node();
		newNode.setLeftChild(temp1);
		newNode.setRightChild(temp2);
		newNode.setFrequency(temp1.getFrequency() + temp2.getFrequency());
		mainQueue.insert(newNode);
		
	}
	theTree.setRoot(mainQueue.remove());
   theTree.displayTree();
   break;
   
   case 'b' :
   
   theTree.getCodes(theTree.getRoot(), prefix, encoding);
   theTree.printCodes(encoding);
   break;
   case 'c' :
   //ArrayList<String> inputStore = inputProcessor.inputStorage(fileName);
      StringBuilder encodedStringBuilder = theTree.encodeFile(inputFiltered, encoding);
      String encodedString = encodedStringBuilder.toString();

      theTree.encodedStringPrinter(encodedString);
         break;
   case 'd' :
   StringBuilder encodedStringBuilderr = theTree.encodeFile(inputFiltered, encoding);
      String encodedStringg = encodedStringBuilderr.toString();
   theTree.reCode(encodedStringg, theTree);
   
   
   break;
   case 'q' : 
   
   System.exit(0);
   
   break;
   
   }
   
	
	
	
	
   
	
	}//end input while loop
	
	}//end main
}//end main class


//////////////////////////////////////////////////////////////////////////////////


class Node{
	private char storedChar;
	private int frequency;
	private Node leftChild;
	private Node rightChild;
   
   public Node(){
   
   }
	
	public Node(int frequency){
		this.frequency = frequency;
		storedChar = '-';
	}

	public Node(char storedChar, int frequency){
		this.storedChar = storedChar;
		this.frequency = frequency;
		
	}
	
	public Node getLeftChild(){
		return leftChild;
	}
	
	public void setLeftChild(Node leftChild){
		this.leftChild = leftChild;
	}
	
	public Node getRightChild(){
		return rightChild;
	}
	
	public void setRightChild(Node leftChild){
		this.rightChild = leftChild;
	}
	
	public char getStoredChar(){
		return storedChar;
	}

	public void setStoredChar(char storedChar){
		this.storedChar = storedChar;
	}
	
	public int getFrequency(){
	return frequency;
	}
	
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	
	public void displayNode(){
 
		System.out.println("{" + storedChar + "," + frequency + "}");

  } 
	public void incrementFrequency(){
		frequency++;
	}
   
	public boolean lessThan(Node temp){
		return(temp.getFrequency()<this.getFrequency());
	}
   
   public boolean isLeaf(){
   return(leftChild == null && rightChild == null);
   }  
}

//////////////////////////////////////////////////////////////////////////////////

class priorityQ{
private int maxSize;
private Node[] queueArray;
private int nItems;
//------------------------------------------------------------
public priorityQ(int s){
maxSize = s;
queueArray = new Node[maxSize];
nItems = 0;
}//end constructor

//------------------------------------------------------------

public void insert(Node tree){
	int j;
	if(nItems==0){
			queueArray[nItems++] = tree;
	}else{
		for(j=nItems - 1; j>=0; j--){
			if(queueArray[j].getFrequency()<tree.getFrequency()){
				queueArray[j+1] = queueArray[j];
			}else
				break;
          }
		queueArray[j+1] = tree;
		nItems++;
		}
	}
//end insert
public int getItems(){
	return nItems;
}

//------------------------------------------------------------

public Node remove(){
	return queueArray[--nItems];
}

//------------------------------------------------------------

public Node peekMin(){
	return queueArray[nItems-1];
}

//------------------------------------------------------------

public boolean isEmpty(){
	return (nItems == 0);
}

//------------------------------------------------------------

public boolean isFull(){
	return (nItems == maxSize);
}

//------------------------------------------------------------
public void printer(){
	for(int i = 0; i < nItems; i++){
		System.out.println(queueArray[i].getFrequency());
	}
}


}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class huffmanTree{
	private Node root;
//------------------------------------------------------------	
	public huffmanTree(){
		root = null;
	}
	
//------------------------------------------------------------
public Node getRoot(){
	return root;
}

public void setRoot(Node root){
	this.root = root;
}
//------------------------------------------------------------

public void reCode(String encodedString, huffmanTree theTree){
   Node current = theTree.getRoot();
   int i = 0;
  while(i<encodedString.length()){
   while(!(current.isLeaf())){
      if(encodedString.charAt(i) == '0'){
         current = current.getLeftChild();
         i++;
         
      }else if(encodedString.charAt(i) == '1'){
         current = current.getRightChild();
         i++;
         
      }
   }
   System.out.print(current.getStoredChar());
   current = theTree.getRoot();
   

}

System.out.println("");
}

public void encodedStringPrinter(String encodedString){
   int i = 0;
   while(i<encodedString.length()){
   for(int k = 0; k < 3; k++){
      for(int j = 0; j < 4; j++){
      if(encodedString.length()>i){
      System.out.print(encodedString.charAt(i));
      i++;
      }else
      break;
      }
      System.out.print(" ");
      }
      System.out.println("");
   }
System.out.println("");
}


public void printCodes(String[] encoding){
   System.out.println("A\t\t"+ encoding[0]);
   System.out.println("B\t\t"+ encoding[1]);
   System.out.println("C\t\t"+ encoding[2]);
   System.out.println("D\t\t"+ encoding[3]);
   System.out.println("E\t\t"+ encoding[4]);
   System.out.println("F\t\t"+ encoding[5]);
   System.out.println("G\t\t"+ encoding[6]);
   System.out.println("");

}

public StringBuilder encodeFile(ArrayList<Character> input, String[] encoding){
   StringBuilder encoded = new StringBuilder();
 for(int i = 0; i < input.size(); i++){
   switch(input.get(i)){
    case 'A': encoded.append(encoding[0]);
    break;
    case 'B': encoded.append(encoding[1]);
    break;
    case 'C' : encoded.append(encoding[2]);
    break;
    case 'D' : encoded.append(encoding[3]);
    break;
    case 'E' : encoded.append(encoding[4]);
    break;
    case 'F' : encoded.append(encoding[5]);
    break;
    case 'G' : encoded.append(encoding[6]);
    break;

   
   
   }
 }  
   return encoded;
}

public void getCodes(Node tree, StringBuffer prefix, String[] encoding) {

        if (tree.isLeaf()) {
            
            switch(tree.getStoredChar()){
            case 'A': encoding[0] = prefix.toString();
            break;
            case 'B': encoding[1] = prefix.toString();
            break;
            case 'C' : encoding[2] = prefix.toString();
            break;
            case 'D' : encoding[3] = prefix.toString();
            break;
            case 'E' : encoding[4] = prefix.toString();
            break;
            case 'F' : encoding[5] = prefix.toString();
            break;
            case 'G' : encoding[6] = prefix.toString();
            break;

            }

            // print out character, frequency, and code for this leaf (which is just the prefix)
            //System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

        } else if (!(tree.isLeaf())) {
            Node node = tree;

            // traverse left
            prefix.append('0');
            getCodes(node.getLeftChild(), prefix, encoding);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            getCodes(node.getRightChild(), prefix, encoding);
            prefix.deleteCharAt(prefix.length()-1);
        }
        
        return;
    }
      
   
 
//------------------------------------------------------------
	public void insert(char storedChar, int frequency){
		Node newNode = new Node();
      newNode.setStoredChar(storedChar);
		newNode.setFrequency(frequency);
		if(root==null){
			root = newNode;
		}else{
			Node current = root;
			Node parent;
			while(true){
				parent = current;
				if(frequency < current.getFrequency()){
					current = current.getLeftChild();
					if(current == null){
						parent.setLeftChild(newNode);
						return;
					}
				}else{
					current = current.getRightChild();
					if(current == null){
						parent.setRightChild(newNode);
						return;
					}
				}
			}
			
		}
	}
//--------------------------------------------------------------------------------

public void inOrder(Node localRoot){
	
	if(localRoot!=null){
		inOrder(localRoot.getLeftChild());
      
		//root operation here
		inOrder(localRoot.getRightChild());
	}
}
//------------------------------------------------------------

//the below function is taken from the tree.java file from LaFore's data structures book and modified for the purposes of this program


	public void displayTree()
      {
      Stack globalStack = new Stack();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
      "......................................................");
      while(isRowEmpty==false)
         {
         Stack localStack = new Stack();
         isRowEmpty = true;

         for(int j=0; j<nBlanks; j++)
            System.out.print(' ');

         while(globalStack.isEmpty()==false)
            {
            Node temp = (Node)globalStack.pop();
            if(temp != null)
               {
               if((temp.getStoredChar()) != '\u0000'){
               System.out.print("{"+temp.getStoredChar() + "," + temp.getFrequency()+"}");
               
               }
               else{
               System.out.print("{_,"+ temp.getFrequency()+"}");
               
               }
               localStack.push(temp.getLeftChild());
               localStack.push(temp.getRightChild());

               if(temp.getLeftChild() != null ||
                                   temp.getRightChild() != null)
                  isRowEmpty = false;
               }
            else
               {
               System.out.print("-");
               localStack.push(null);
               localStack.push(null);
               }
            for(int j=0; j<nBlanks*2-2; j++)
               System.out.print(' ');
            }  // end while globalStack not empty
         System.out.println();
         nBlanks /= 2;
         while(localStack.isEmpty()==false)
            globalStack.push( localStack.pop() );
         }  // end while isRowEmpty is false
      System.out.println(
      "......................................................");
      System.out.println("");
      }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class mainInput{
	
	
	public ArrayList<String> inputStorage(String fileName){
		ArrayList<String> input = new ArrayList<>();
       try
		{
      //
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine())!=null){
				input.add(line);
			}
			reader.close();
		} catch (Exception e){
			System.out.println("Exception occured trying to read " + fileName);
			e.printStackTrace();
		}
		return input;
      //System.out.println(input.get(0).toString());
	}
//-------------------------------------------------------------------------------------------------------------------------
public char printMenu(Scanner input){
   char userInput;
   System.out.println("------------------------------------------------------");
   System.out.println("Please choose from an option below:");
   System.out.println("------------------------------------------------------");
   System.out.println("a. Print Huffman Tree");
   System.out.println("b. Print Code Table");
   System.out.println("c. Binary Encoding");
   System.out.println("d. A-G using Huffman Tree");
   System.out.println("q. Exit Program");
   System.out.println("------------------------------------------------------");
   System.out.println("");
   userInput = input.next().charAt(0);
   System.out.println("");
   return userInput;
}

  public ArrayList<Character> inputFilter(ArrayList<String> input){    
  
      ArrayList<Character> importantInput = new ArrayList<>();
      
      for(int i = 0; i < input.size(); i++){
         for(int j = 0; j<input.get(i).length(); j++){
         switch(input.get(i).charAt(j)) {
            case 'A': importantInput.add('A');
            break;
            case 'B':importantInput.add('B');
            break;
            case 'C' : importantInput.add('C');
            break;
            case 'D' : importantInput.add('D');
            break;
            case 'E' : importantInput.add('E');
            break;
            case 'F' : importantInput.add('F');
            break;
            case 'G' : importantInput.add('G');
            break;
           
         }
         }
      }//end of input loop
	  return importantInput;
  }
//-------------------------------------------------------------------------------------------------------------------------
  public ArrayList<Node> inputCounter(ArrayList<Character> filteredInput){
	  ArrayList<Node> inputCount = new ArrayList<>();
	  Node aCount = new Node('A', 0);
	  Node bCount = new Node('B', 0);
	  Node cCount = new Node('C', 0);
	  Node dCount = new Node('D', 0);
	  Node eCount = new Node('E', 0);
	  Node fCount = new Node('F', 0);
	  Node gCount = new Node('G', 0);
	  

         for(int j = 0; j<filteredInput.size(); j++){
         switch(filteredInput.get(j)) {
            case 'A': aCount.incrementFrequency();
            break;
            case 'B': bCount.incrementFrequency();
            break;
            case 'C' : cCount.incrementFrequency();
            break;
            case 'D' : dCount.incrementFrequency();
            break;
            case 'E' : eCount.incrementFrequency();
            break;
            case 'F' : fCount.incrementFrequency();
            break;
            case 'G' : gCount.incrementFrequency();
            break;
           
			}//exit switch
         }//exit for loop
      
	  inputCount.add(aCount);
	  inputCount.add(bCount);
	  inputCount.add(cCount);
	  inputCount.add(dCount);
	  inputCount.add(eCount);
	  inputCount.add(fCount);
	  inputCount.add(gCount);
	  return inputCount;
	  
  }
 //-------------------------------------------------------------------------------------------------------------------------
	  
}//end mainInput