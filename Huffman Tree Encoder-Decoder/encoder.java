import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;


public class encoder {
	public static void main(String[] args) { 
		FourWayHeap fwh; 
		
		HashMap<Integer,Integer> freqTable = new HashMap<Integer,Integer>();
		HashMap<Integer,String>	codeTable = new HashMap<Integer,String>();
		
		long startTime = System.nanoTime();
		encoder encode = new encoder();
		String inputFile = args[0];
		
		encode.buildFreqTable(freqTable, inputFile);
		fwh = new FourWayHeap(freqTable.size()+5);
		
		fwh.build_MinHeap(freqTable);															//Build the heap
		HuffmanTree.buildHuffmanTree(fwh);														//Build the Huffman Tree	
		HuffmanTree.buildCodeTable(fwh.hn[fwh.displacement],codeTable,"");						//Build code table
		
		StringBuilder code = encode.buildBinaryString(codeTable,inputFile);
		String filename = "encoded.bin";
		encode.buildEncodedFile(code.toString(), filename);
		
		
		String codeTableFilename = "code_table.txt";
		encode.buildCodeTableFile(codeTableFilename, codeTable);	
		System.out.println("Time taken is "+(System.nanoTime()-startTime)/1000000000+ " seconds");
		
		System.out.println("End of Encoder");
	}
	
	public StringBuilder buildBinaryString(HashMap<Integer,String> codeTable, String filename){
		StringBuilder sb = new StringBuilder();
		
		String currLine = null;  
	      try {
	         
	         BufferedReader br = new BufferedReader((new FileReader(filename)));
	         
	         while ((currLine = br.readLine()) != null) {
	        	 sb.append(codeTable.get(Integer.parseInt(currLine)));
	         } 
	         br.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
		
		return sb;
	}
	
	public void buildFreqTable(HashMap<Integer,Integer> freqTable, String filename){
		String currLine = null;  
	      try {
	      
	         BufferedReader br = new BufferedReader((new FileReader(filename)));
	         
	         while ((currLine = br.readLine()) != null) {
	        	 Integer count = freqTable.get(Integer.parseInt(currLine));
	        	 if(count!=null){
	        		 freqTable.put(Integer.parseInt(currLine), ++count);
	        	 }
	        	 else{
	        		 freqTable.put(Integer.parseInt(currLine), 1);
	        	 }
	         } 
	         br.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public void buildCodeTableFile(String filename, HashMap<Integer,String>	codeTable){
		try {
			BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
			for(Integer k:codeTable.keySet()){
				writer.write(Integer.toString(k)+" "+codeTable.get(k)+"\n");
			}
			writer.close();
		}
		catch (IOException e) {
			System.out.println("File creation error");
		}
	}
	
	public void buildEncodedFile(String code, String filename){
		FileOutputStream fos;
		BitSet bs = new BitSet();
		for(int i=0;i<code.length();i++){
			if(code.charAt(i)=='1')
			bs.set(i);
		}
		
		byte[] by = bs.toByteArray();		
		
		try {	
			fos = new FileOutputStream(filename);
			fos.write(by);
			fos.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
}
