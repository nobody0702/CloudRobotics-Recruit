
public class TrieNode{
	private boolean isStr;
	private int freq;
	private TrieNode next[] = new TrieNode[26];
	public TrieNode(){
		this.isStr = false;
		freq = 0;
		for(int i=0;i<26;i++){
			this.next[i] = null;
		}
	}
	public boolean isNULL(int i){
		return this.next[i] == null;
	}
	public void setNext(int i,TrieNode t){
		this.next[i] = t;
	}
	public TrieNode nextNode(int i){
		return next[i];
	}
	public void setEndflag(){
		this.isStr = true;
	}
	public boolean isString(){
		return this.isStr;
	}
	public void addFreq(int x){
		this.freq += x;
	}
	public int getFreq(){
		return this.freq;
	}
}