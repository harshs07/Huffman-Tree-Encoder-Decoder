import java.util.HashMap;
import java.util.LinkedList;

public class PairingHeap implements Heap {
	
	HeapNode root;
	
	public PairingHeap(){
		root = null;
	}
	
	public void insert(HeapNode node){	
			root =  meld(root,node);
	}
	
	private HeapNode meld(HeapNode root1, HeapNode root2){
		if(root1 == null) return root2;
		if(root2 == null) return root1;
		if(root1.frequency<=root2.frequency){
			if(root1.child!=null){
				root1.child.leftSib = root2;
				root2.rightSib = root1.child;
				root1.child = root2;
			}
			else
				root1.child = root2;	
			return root1;
		}
		else{
			if(root2.child!=null){
				root2.child.leftSib = root1;
				root1.rightSib = root2.child;
				root2.child = root1;
			}
			else
				root2.child = root1;
			return root2;
		}
	}
	
	public HeapNode removeMin(){
		//Using multi pass scheme
		if(root == null) return null;
		HeapNode min = new HeapNode();
		min.data = root.data;
		min.frequency = root.frequency;
		min.left = root.left;
		min.right = root.right;
		HeapNode temp;
		HeapNode child = root.child;
		if(child==null){
			root = null;
		}
		else{
			child.leftSib = null;
			LinkedList<HeapNode> multiPass = new LinkedList<HeapNode>();
			while(child!=null){
				temp = child.rightSib;
				child.leftSib = child.rightSib = null;
				multiPass.add(child);
				child = temp;
			}
			while(multiPass.size()>1){
				multiPass.offer(meld (multiPass.poll() , multiPass.poll()));
			}
			root = multiPass.poll();
		}
		return min;
	}

	@Override
	public void insert(int data, int freq, HeapNode left, HeapNode right) {
		// TODO Auto-generated method stub
		HeapNode hn = new HeapNode();
		hn.data = data;
		hn.frequency = freq;
		hn.left = left;
		hn.right = right;
		insert(hn);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		if(root!=null && root.child!=null)
			return 2;
		return 0;
	}

	@Override
	public void Heap_DecreaseKey(int i, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void build_MinHeap(HashMap<Integer,Integer> freqTable) {
		// TODO Auto-generated method stub
		for(Integer k: freqTable.keySet()){
			HeapNode hn = new HeapNode();
			hn.data = k;
			hn.frequency = freqTable.get(k);
			this.insert(hn);
		}
	}

	@Override
	public int getDisplacement() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public HeapNode heapMin() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void makeRoot(HeapNode newNode) {
		// TODO Auto-generated method stub
		
	}
	
}
