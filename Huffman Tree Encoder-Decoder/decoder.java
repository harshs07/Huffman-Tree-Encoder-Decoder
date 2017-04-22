import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class decoder {
	public static void main(String[] args){
		List<String> lines=new ArrayList<String>();
		HashMap<Integer,String>	codeTable = new HashMap<Integer,String>();
		long startTime = System.nanoTime();
		
		try {
			lines = Files.readAllLines(Paths.get(args[1]), StandardCharsets.UTF_8);
		
			for(int i=0;i<lines.size();i++){												
				String input = lines.get(i);
				codeTable.put(Integer.parseInt(input.split(" ")[0]), input.split(" ")[1]);
			}
		}
		catch (IOException e) {
			System.out.println("File read error");
		}
		
		decoder obj = new decoder();
		
		HeapNode hn = obj.buildDecodeTree(codeTable, null);
		
		String encodedInput = obj.readEncodedData(args[0]);
		obj.buildDecodedFile(encodedInput, hn);
		System.out.println("Time taken is "+(System.nanoTime()-startTime)/1000000000+" seconds");
		System.out.println("End of decoder");
	}
	
	public void buildDecodedFile(String encodedInput, HeapNode hn){
		HeapNode root = hn;
		try {
			StringBuilder sb= new StringBuilder();
			BufferedWriter writer = new BufferedWriter( new FileWriter("decoded.txt"));
			for(int i=0;i<encodedInput.length();){
				hn = root;
				while(hn!=null&&hn.data<0&&i<encodedInput.length()){
					if(encodedInput.charAt(i)=='0'){
						hn=hn.left;
					}		
					else{
						hn=hn.right;
					}
					i++;
				}
					sb.append(Integer.toString(hn.data)+"\n");
			}
			writer.write(sb.toString());
			writer.close();
		}
		catch (IOException e) {
			System.out.println("File creation error");
			e.printStackTrace();
		}
	}
	
	public String readEncodedData(String filename){
		 byte [] buffer =null;
		 File a_file = new File(filename);
		 try
		 {
		 FileInputStream fis = new FileInputStream(filename);
		 int length = (int)a_file.length();
		 buffer = new byte [length];
		 fis.read(buffer);
		 fis.close();
		 }
		 catch(IOException e){
			 System.out.println("File read error");
		 }
		BitSet bits = BitSet.valueOf(buffer);
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<buffer.length*8;i++){
			if(bits.get(i))
				sb.append(1);
			else
				sb.append(0);
		}
		return sb.toString();
	}
	
	public HeapNode buildDecodeTree(HashMap<Integer,String> codeTable, HeapNode hn){
		String code;
		HeapNode root=null;
		for(Integer k: codeTable.keySet()){
			code = codeTable.get(k);
			for(int i=0;i<code.length();i++){
				if(hn==null){
					hn = new HeapNode();
					root = hn;
					hn.data = -1;
				}
				if(code.charAt(i)=='0') {
					if(hn.left==null){
						hn.left = new HeapNode();
						hn.left.data = -1;
					}	
					hn=hn.left;
				}
				else {
					if(hn.right==null){
						hn.right = new HeapNode();
						hn.right.data = -1;
					}
					hn=hn.right;
				}
			}
			hn.data = k;
			hn = root;
		}
		return root;
	}
	
}



