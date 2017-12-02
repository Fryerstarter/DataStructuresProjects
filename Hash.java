/*
Christopher Kile
n00656049
4-1-2017
COP 3530
Project 6

I think there is a bug in the probe length for the deletions due to mismatch between insertion and deletion probe lengths. Still tracking it down.
*/
import java.io.*;
import java.util.*;
class Hash{
	public static void main(String[] args){
		
		mainInput inputProcessor = new mainInput();
		String[] file1 = inputProcessor.inputStorage(args[0]);
		String[] file2 = inputProcessor.inputStorage(args[1]);
		String[] file3 = inputProcessor.inputStorage(args[2]);
		
		hashTable linearHash = new hashTable(file1.length);
		for(int i = 0; i < file1.length; i++){
			linearHash.insertLinear(file1[i]);
		}
		
      hashTable quadraticHash = new hashTable(file1.length);
      for(int i = 0; i < file1.length; i++){
         quadraticHash.insertQuad(file1[i]);
      }
      
      
      System.out.println();
      System.out.println("Insertions");
      linearHash.linearPrinter();
      System.out.println();
      System.out.println();
      quadraticHash.linearPrinter();
      System.out.println();
      System.out.println();
      System.out.println("Searches");
      System.out.println();
      linearHash.searchLinear(file2);
      System.out.println();
      quadraticHash.searchQuad(file2);
      System.out.println();
      System.out.println();
      System.out.println("Deletions");
      System.out.println();
      linearHash.deleteLinear(file3);
      System.out.println();
      quadraticHash.deleteQuad(file3);
		
		
		
	}
}

class hashObject{
private int insertionProbeLength;
private String inputString;
private int deletionProbeLength;



public hashObject(String key){
inputString = key;
}

public hashObject(int length, String key){
insertionProbeLength = length;
deletionProbeLength = 1;
inputString = key;
}

public int getDeletionProbeLength(){
	return deletionProbeLength;
}

public void setDeletion(int deletionProbeLength){
	this.deletionProbeLength = deletionProbeLength;
}

public int getInsertionProbeLength(){
return insertionProbeLength;
}

public void setInsertionProbeLength(int length){
insertionProbeLength = length;
}

public String getKey(){
return inputString;
}

public void setKey(String inputString){
this.inputString = inputString;
}

}

class hashTable{
	private hashObject[] hashArray;
	private int size;
	
	public hashTable(int size){
		int temp = getPrime(2*size);
		
		hashArray = new hashObject[temp];
		this.size = temp;
	}
   
	public void linearPrinter(){
		double average = 0;
		double sum = 0;
		double num = 0;
      int spaces = 40;
		System.out.println("index   string                                 probe length for insertion");
		for(int i = 0; i < size; i++){
      if(hashArray[i] != null && hashArray[i].getKey() != "-1"){
			System.out.print("   " + i + "    " + hashArray[i].getKey()); 
         for(int j = 0; j < 40-(hashArray[i].getKey().length()); j++)
         System.out.print(" ");
        System.out.println("       " + hashArray[i].getInsertionProbeLength()); 
			sum += (double)hashArray[i].getInsertionProbeLength();
			num++;
         }
         
      }
      average = sum / num;
      System.out.println("                                        ave:           " + average);	
	}
   
   /*public void printer(){
      
   System.out.println("index       string      probe length for insertion");
   for(int i = 0; i < size; i++){
      if(hashArray[i] != null){
      //System.out.println(i + "       "+ hashArray[i] + "            "+ hashArray[i].getInsertionProbeLength());
      }
         }
  
     }
   */
	
   public int getSize(){
   return size;
   }
   
	public int hash(String input){
	
		int hashValue = 0;
		for(int i = 0; i < input.length(); i++){
			int hashChar = (input.charAt(i) - 96);
			hashValue = (hashValue * 27 + hashChar) % size;
			
		}
      return hashValue;
		
	}
	
      
	public void insertLinear(String key){
		int temp = hash(key);
      int probeLength = 1;
		int tempConstant = 1;
		while(hashArray[temp] !=null && !(hashArray[temp].equals("-1"))){
			++temp;
         probeLength++;
			temp%=size;
		}
      hashObject tempHashed = new hashObject(probeLength, key);
		hashArray[temp] = tempHashed;
			
	}
	
	public void insertQuad(String key){
		int temp = hash(key);
      int probeLength = 1;
		int i = 1;
		while(hashArray[temp] !=null && !(hashArray[temp].equals("-1"))){
			temp += i * i;
			i++;
         probeLength++;
			temp%=size;
			
		}
      hashObject tempHashed = new hashObject(probeLength, key);

		hashArray[temp] = tempHashed;
		
	}
	
   public void searchLinear(String[] file2){
		double aveSucc = 0;
      double aveFail = 0;
		double sumSucc = 0;
      double sumFail = 0;
		double numSucc = 0;
      double numFail = 0;
      int blanks = 40;
		int temp2 = 0;
		System.out.println("String                                  Succcess Failure Probe length for success  Probe length for failure");

		for(int i = 0; i < file2.length; i++){
			int temp = hash(file2[i]);
			boolean flag = false;
			while(hashArray[temp] != null){
				temp2++;

				if(hashArray[temp].getKey().equals(file2[i])){
					String temp1 = hashArray[temp].getKey();
					
					
				flag = true;
            break;
				}
				++temp;
			
				temp%=size;
							}
		if(temp2==0){
      temp2=1;
      }
			if(flag){ 
         sumSucc+=temp2;
         numSucc++;
				System.out.print(file2[i]);
            for(int k = 0; k <(blanks - file2[i].length()); k++)
               System.out.print(" ");
           System.out.println("  yes                    " + temp2);
			}else{
         sumFail+=temp2;
         numFail++;
				System.out.print(file2[i]);
            for(int k = 0; k <(blanks - file2[i].length()); k++)
               System.out.print(" ");
           System.out.println("           yes                                  " + temp2);
			}
		
		}
      String tempSucc;
      if(numSucc == 0){
      tempSucc = "x.xx";
      }else{
      aveSucc = sumSucc / numSucc;
      tempSucc = Double.toString(aveSucc);
     }
     String tempFail;
      if(numFail == 0){
      tempFail = "x.xx";
      }else{
      aveFail = sumFail / numFail;
      tempFail = Double.toString(aveFail);
     }

      
      
      System.out.println("average probe length:                                            " + tempSucc + "                    " + tempFail);
    
		
	}


   public void searchQuad(String[] file2){
   double aveSucc = 0;
      double aveFail = 0;
		double sumSucc = 0;
      double sumFail = 0;
		double numSucc = 0;
      double numFail = 0;
      int blanks = 40;
      
		int j = 1;
		int temp2 = 0;
		System.out.println("String                                  Succcess Failure Probe length for success  Probe length for failure");

		for(int i = 0; i < file2.length; i++){
			int temp = hash(file2[i]);
			boolean flag = false;
			while(hashArray[temp] != null){
				temp2++;
				if(hashArray[temp].getKey().equals(file2[i])){
										
				flag = true;
            break;
				}
				temp += j * j;
            j++;
			   
				temp%=size;
				
			}
         if(temp2==0){
      temp2=1;
      }

			
			if(flag){ 
         sumSucc+=temp2;
         numSucc++;
				System.out.print(file2[i]);
            for(int k = 0; k <(blanks - file2[i].length()); k++)
               System.out.print(" ");
           System.out.println("  yes                    " + temp2);
			}else{
         sumFail+=temp2;
         numFail++;
				System.out.print(file2[i]);
            for(int k = 0; k <(blanks - file2[i].length()); k++)
               System.out.print(" ");
           System.out.println("           yes                                  " + temp2);
			}
			
		}
      
    String tempSucc;
      if(numSucc == 0){
      tempSucc = "x.xx";
      }else{
      aveSucc = sumSucc / numSucc;
      tempSucc = Double.toString(aveSucc);
     }
     String tempFail;
      if(numFail == 0){
      tempFail = "x.xx";
      }else{
      aveFail = sumFail / numFail;
      tempFail = Double.toString(aveFail);
     }

      
      
      System.out.println("average probe length:                                            " + tempSucc + "                    " + tempFail);

	}
		
	
	
	public void deleteLinear(String[] file3){
      double aveSucc = 0;
      double aveFail = 0;
		double sumSucc = 0;
      double sumFail = 0;
		double numSucc = 0;
      double numFail = 0;
		int blanks = 40;
		int temp2 = 0;
		System.out.println("String                                  Succcess Failure Probe length for success  Probe length for failure");
		for(int i = 0; i < file3.length; i++){
			int temp = hash(file3[i]);
			boolean flag = false;
         temp2=0;
			while(hashArray[temp] != null){
				temp2++;
				if(hashArray[temp].getKey().equals(file3[i])){
					String temp1 = hashArray[temp].getKey();
					hashArray[temp].setDeletion(temp2);
					hashArray[temp].setKey(Integer.toString(-1));
					
				flag = true;
            break;
				}
            
				
			   temp++;
				temp%=size;
				
			}
			if(temp2==0){
          temp2=1;
         }

			if(flag){ 
         sumSucc+=temp2;
         numSucc++;
				System.out.print(file3[i]);
            for(int k = 0; k <(blanks - file3[i].length()); k++)
               System.out.print(" ");
           System.out.println("  yes                    " + temp2);
			}else{
         sumFail+=temp2;
         numFail++;
				System.out.print(file3[i]);
            for(int k = 0; k <(blanks - file3[i].length()); k++)
               System.out.print(" ");
           System.out.println("           yes                                  " + temp2);
			}
			
		}
     
		String tempSucc;
      if(numSucc == 0){
      tempSucc = "x.xx";
      }else{
      aveSucc = sumSucc / numSucc;
      tempSucc = Double.toString(aveSucc);
     }
     String tempFail;
      if(numFail == 0){
      tempFail = "x.xx";
      }else{
      aveFail = sumFail / numFail;
      tempFail = Double.toString(aveFail);
     }

      
      
      System.out.println("average probe length:                                            " + tempSucc + "                    " + tempFail);
	}
	
	public void deleteQuad(String[] file3){
   double aveSucc = 0;
      double aveFail = 0;
		double sumSucc = 0;
      double sumFail = 0;
		double numSucc = 0;
      double numFail = 0;
      int blanks = 40;

		int j = 0;
		int temp2 = 0;
		System.out.println("String                                  Succcess Failure Probe length for success  Probe length for failure");
		for(int i = 0; i < file3.length; i++){
			int temp = hash(file3[i]);
         temp2 = 0;
			boolean flag = false;
			while(hashArray[temp] != null){
				temp2++;
				if(hashArray[temp].getKey().equals(file3[i])){
					String temp1 = hashArray[temp].getKey();
					//hashArray[temp].setDeletion(temp2);
					hashArray[temp].setKey(Integer.toString(-1));
					
				flag = true;
            break;
				}
				temp += j * j;
            j++;
			   
				temp%=size;
				
			}
			if(temp2==0){
      temp2=1;
      }

			if(flag){ 
         sumSucc+=temp2;
         numSucc++;
				System.out.print(file3[i]);
            for(int k = 0; k <(blanks - file3[i].length()); k++)
               System.out.print(" ");
           System.out.println("  yes                    " + temp2);
			}else{
         sumFail+=temp2;
         numFail++;
				System.out.print(file3[i]);
            for(int k = 0; k <(blanks - file3[i].length()); k++)
               System.out.print(" ");
           System.out.println("           yes                                  " + temp2);
			}
			
		}
      
     
      
      String tempSucc;
      if(numSucc == 0){
      tempSucc = "x.xx";
      }else{
      aveSucc = sumSucc / numSucc;
      tempSucc = Double.toString(aveSucc);
     }
     String tempFail;
      if(numFail == 0){
      tempFail = "x.xx";
      }else{
      aveFail = sumFail / numFail;
      tempFail = Double.toString(aveFail);
     }

      
      
      System.out.println("average probe length:                                            " + tempSucc + "                    " + tempFail);

	}
	
	private int getPrime(int min){
		for(int j = min+1; true; j++){
			if(isPrime(j))
				return j;
		}
	}
	
	private boolean isPrime(int n){
   for(int j = 2; (j*j <= n); j++){
		if(n%j == 0){
			return false;
		}
    }
		return true;
	}
	
	
	
	
}

class mainInput{
	//borrowed from project 5 and modified for this program
	
	public String[] inputStorage(String fileName){
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
      String[] temp = new String[input.size()];
      for(int i = 0; i < input.size(); i++){
      temp[i] = input.get(i);
      
      }
      
		return temp;
      
      //System.out.println(input.get(0).toString());
	}
//-------------------------------------------------------------------------------------------------------------------------

 //-------------------------------------------------------------------------------------------------------------------------
	  
}