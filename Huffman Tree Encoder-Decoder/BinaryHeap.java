import java.util.HashMap;

public class BinaryHeap implements Heap {
	HeapNode[] hn;
	int size;
	
	public BinaryHeap(int capacity){
		this.hn = new HeapNode[capacity];
		size=0;
	}
	
	public int getSize(){
		return size;
	}
	
	public void build_MinHeap(HashMap<Integer,Integer> freqTable){
		int i=1;
		for(int k: freqTable.keySet()){
			this.hn[i]=new HeapNode();
			this.hn[i].data=k;
			this.hn[i].frequency=freqTable.get(k);
			i++;
			this.size++;
		}
		
		for(i=size/2;i>=1;i--){
			MIN_HEAPIFY(i);
		}
	}
	
	public void MIN_HEAPIFY(int i){
		int smallest;
		int l = 2*i;
		int r = 2*i+1;
		if(l<=this.size && hn[i].frequency>hn[l].frequency){
			smallest=l;
		}
		else smallest = i; 
		if(r<=this.size && hn[smallest].frequency>hn[r].frequency){
		smallest=r;
		}
		if(smallest!=i){
			HeapNode temp = hn[i];
			hn[i]=hn[smallest];
			hn[smallest]=temp;
			MIN_HEAPIFY(smallest);
		}				
	}
	
	public void Heap_DecreaseKey(int i,int key){
		if(hn[i].frequency<key) return;
		hn[i].frequency=key;
		int parent=i/2;
		HeapNode temp;
		while(parent>=1&&hn[parent].frequency>hn[i].frequency){
			temp = hn[i];
			hn[i]=hn[parent];
			hn[parent]=temp;
			i=parent;
			parent=i/2;
		}
	}
	
	public void insert(int data, int freq, HeapNode left, HeapNode right){
		size++;
		hn[size] = new HeapNode();
		//System.out.println(hn[0].data);
		this.hn[size].data=data;
		this.hn[size].frequency=Integer.MAX_VALUE;
		this.hn[size].left=left;
		this.hn[size].right=right;
		Heap_DecreaseKey(size,freq);
	}
	
	public HeapNode removeMin(){
		if(size<1) return null;
		HeapNode min = hn[1];
		hn[1]=hn[size--];
		MIN_HEAPIFY(1);
		return min;
	}
	
	public HeapNode heapMin(){
		return hn[1];
	}
	
	public void printHeap(){
		for(int i=1;i<=size;i++){
			System.out.println(hn[i].data+"---->"+hn[i].frequency);
		}
	}

	@Override
	public void insert(HeapNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDisplacement() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void makeRoot(HeapNode newNode) {
		// TODO Auto-generated method stub
		hn[1]=newNode;
		MIN_HEAPIFY(1);
	}
	
}
