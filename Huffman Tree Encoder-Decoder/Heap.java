import java.util.HashMap;

public interface Heap {
	public void insert(int data, int freq, HeapNode left, HeapNode right);	
	public HeapNode removeMin();
	public int getSize();
	public void Heap_DecreaseKey(int i,int key);
	public void insert(HeapNode node);
	public void build_MinHeap(HashMap<Integer,Integer> freqTable);
	public int getDisplacement();
	public HeapNode heapMin();
	public void makeRoot(HeapNode newNode);
}
