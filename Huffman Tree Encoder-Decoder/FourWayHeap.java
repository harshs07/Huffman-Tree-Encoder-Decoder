import java.util.HashMap;

public class FourWayHeap implements Heap {
	HeapNode[] hn;
	int size,displacement;
	
	public FourWayHeap(int capacity){
		displacement = 3;
		hn = new HeapNode[capacity];
		size = displacement;
	}
	
	public void build_MinHeap(HashMap<Integer,Integer> freqTable){
		int i=size;
		for(int k: freqTable.keySet()){
			this.hn[i]=new HeapNode();
			this.hn[i].data=k;
			this.hn[i].frequency=freqTable.get(k);
			i++;
			this.size++;
		}
		
		if(size>displacement)
			size--;
		
		for(i=parent(size);i>=displacement;i--){
			MIN_HEAPIFY(i);
		}
	}
	
	public void MIN_HEAPIFY(int i){
		int smallest = i;
		int c1 = child(1,i);
		int c2 = child(2,i);
		int c3 = child(3,i);
		int c4 = child(4,i);
		if(c1<=this.size && hn[i].frequency>hn[c1].frequency){
			smallest=c1;
		}
		
		if(c2<=this.size && hn[smallest].frequency>hn[c2].frequency){
			smallest=c2;
		}
		
		if(c3<=this.size && hn[smallest].frequency>hn[c3].frequency){
			smallest=c3;
		}
		
		if(c4<=this.size && hn[smallest].frequency>hn[c4].frequency){
			smallest=c4;
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
		int parent=parent(i);
		HeapNode temp;
		while(parent>=displacement && hn[parent].frequency>hn[i].frequency){
			temp = hn[i];
			hn[i]=hn[parent];
			hn[parent]=temp;
			i=parent;
			parent=(int)Math.ceil((i-1)/4.0);
		}
	}
	
	@Override
	public void insert(int data, int freq, HeapNode left, HeapNode right) {
		size++;
		hn[size] = new HeapNode();
		this.hn[size].data=data;
		this.hn[size].frequency=Integer.MAX_VALUE;
		this.hn[size].left=left;
		this.hn[size].right=right;
		Heap_DecreaseKey(size,freq);
	}

	@Override
	public HeapNode removeMin() {
		if(size<displacement) return null;
		HeapNode min = hn[displacement];
		hn[displacement]=hn[size--];
		MIN_HEAPIFY(displacement);
		return min;
	}

	public HeapNode heapMin(){
		return hn[displacement];
	}
	
	public int parent(int i) {
		if(i>displacement)
			return ((i-displacement-1)/4)+displacement;
		return -1;
	}
	
	public int child(int i, int j) {
		return ((j-displacement)*4)+i+displacement;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	public void printHeap(){
		for(int i=displacement;i<=size;i++){
			System.out.println(hn[i].data+"---->"+hn[i].frequency);
		}
	}

	@Override
	public void insert(HeapNode node) {
		// TODO Auto-generated method stub
		
	}
	
	public void makeRoot(HeapNode newNode) {
		hn[displacement]=newNode;
		MIN_HEAPIFY(displacement);
	}

	@Override
	public int getDisplacement() {
		// TODO Auto-generated method stub
		return displacement;
	}

}
