import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main{
	  
	  static char[][] key = {{},{},{'a','b','c'},{'d','e','f'},{'g','h','i'},{'j','k','l'},{'m','n','o'},{'p','q','r','s'},{'t','u','v'},{'w','x','y','z'}};
	  static int resultcnt = 0;
	  static String result[] = new String[100000];// max 100000 result 
	  static String tmpres = "";
	  public static void main(String args[]) throws IOException{
		  String argNumStr = null;
		  boolean general = true;
		  if(args.length < 1){
			  argNumStr = "2568376268427";
			//  argNumStr = "9428273968";
		  }
		  else if(args.length < 2){
			  argNumStr = args[0];
		  }
		  TrieNode root = new TrieNode();
		  LoadDic1(root);//load wordsrank.txt
		  if(!general)
			  LoadDic2(root);//load words2.txt
		  searchResult(root,root,argNumStr,0);
		  for(int i=0;i<resultcnt;i++){
			 System.out.println(result[i]); 
		  }
	  }
	  public static boolean isLowercaseStr(String str){
		  char []strarr =str.toCharArray();
		  for(char c : strarr){
			  if(c > 'z' || c < 'a')return false;
		  }
		  return true;
	  }
	  public static void LoadDic1(TrieNode root) throws IOException{
		  	BufferedReader br = null;
		  	File file = new File("wordsrank.txt");
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			int cnt  = 0;
			while((line = br.readLine())!=null) {
				String[] sa = line.split("\\s+");
				String str = sa[1].toLowerCase();
			//	System.out.println(sa[3]);
				int freq = Integer.parseInt(sa[3]);
				if(isLowercaseStr(str)){
					insert(root,str);
					setFreqent(root,str,freq);
				}
			}
	  }
	  public static void LoadDic2(TrieNode root) throws IOException{
		  BufferedReader br = null;
		  	File file = new File("words2.txt");
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			int cnt = 0;
			while((line = br.readLine())!=null) {
				String[] sa = line.split("\\s+");
				String str = sa[0].toLowerCase();
				if(isLowercaseStr(str)&&str.length()>1){
					insert(root,str);
				}
			}
	  }
	  
	  public static void insert(TrieNode root,String s){
		  if(root == null || s.isEmpty())return;
		  TrieNode p = root;
		  for(int i=0;i<s.length();i++){
			  char si = s.charAt(i);
			  if(p.isNULL(si-'a')){
				  TrieNode temp = new TrieNode();
				  p.setNext(si-'a', temp);
				  p = p.nextNode(si-'a');
			  }
			  else{
				  p = p.nextNode(si-'a');
			  }
		  }
		  p.setEndflag();
	  }
	  
	  /*For certain word, add frequent in the path of trie tree*/
	  public static void setFreqent(TrieNode root,String s,int freq){
		  TrieNode p = root;
		  for(int i=0;i<s.length()&&p!=null;i++){
			  char si = s.charAt(i);
			  p.addFreq(freq);
			//  System.out.println(p.getFreq());
			  p = p.nextNode(si-'a');
		  }
		  p.addFreq(freq);
	  }
	  /*p is the pointer of TrieTree, idx is the index of String s*/
	  public static void searchResult(TrieNode root,TrieNode p,String s,int idx){
		  if(resultcnt >= 10)return;
		  if(idx >= s.length()){
			  result[resultcnt++] = tmpres;
			  return;
		  }
		  if(p == null)return;
		  if(p.isString())
			  searchResult(root,root,s,idx);
		  int num = (s.charAt(idx)-'0');
		  /**sort char by frequent**/
		  char sortch[] = new char[5];
		  int len = key[num].length;
		  for(int i=0;i<len;i++)
			  sortch[i] = key[num][i];
		  
		  
		  for(int i=0;i<len;i++){
			  for(int j=0;j<len;j++){
				  int cni = sortch[i]-'a', cnj = sortch[j]-'a';
				  int frei = ((p.nextNode(cni) == null) ? 0:p.nextNode(cni).getFreq());
				  int frej = ((p.nextNode(cnj) == null) ? 0:p.nextNode(cnj).getFreq());
				  if(frei < frej){
					  char tmpch = sortch[i];
					  sortch[i] = sortch[j];
					  sortch[j] = tmpch;
				  }
			  }
		  }
		  /**sort char by frequent**/
		 
		  for(int i=0;i<len;i++){
			  int charnum = sortch[i] - 'a';
			  if(p == root)
				  tmpres += (char)(sortch[i]-32);
			  else 
				  tmpres += sortch[i];
			  searchResult(root,p.nextNode(charnum),s,idx+1);
			  tmpres = tmpres.substring(0, tmpres.length()-1);
		  }
	  }
	  
}