import java.util.HashMap;

public class HuffmanTree {

	public static void buildHuffmanTree(Heap bh){
		while(bh.getSize()>bh.getDisplacement()){
		HeapNode temp1 = bh.removeMin();
		HeapNode temp2 = bh.heapMin();
		HeapNode newNode = new HeapNode();
		newNode.frequency = temp1.frequency + temp2.frequency;
		newNode.left = temp1;
		newNode.right = temp2;
		newNode.data = -1;
		bh.makeRoot(newNode);
		}
	}
	
	//Overloaded this method for pairing heap
	public static void buildHuffmanTreeForPairingHeap(Heap bh){
		while(bh.getSize()>bh.getDisplacement()){
		HeapNode temp1 = bh.removeMin();
		HeapNode temp2 = bh.removeMin();
		bh.insert(-1, temp1.frequency+temp2.frequency, temp1, temp2);
		}
	}
	
	public static void printHuffmanTree(HeapNode hn){
		if(hn!=null){
			System.out.println(hn.data+" --> "+hn.frequency);
			printHuffmanTree(hn.left);
			printHuffmanTree(hn.right);
		}
	}
	
	
	
	public static void buildCodeTable(HeapNode hn, HashMap<Integer,String> hm, String s){
		 if(hn!=null){
			 if(hn.data!=-1){
				 hm.put(hn.data, s);
			 }
			 else{
				 buildCodeTable(hn.left,hm,s+"0");
				 buildCodeTable(hn.right,hm,s+"1");
			 }
		 }
	}
	
}
