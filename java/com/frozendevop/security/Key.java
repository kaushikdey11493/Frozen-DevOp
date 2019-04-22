package com.frozendevop.security;

import java.util.Random;

class Key 
{
	private String encryptedKey;
	private int key;

	protected Key()
	{
		Random rand = new Random();
		key = rand.nextInt(10000)+77;
		System.out.println("key : "+key);
		encryptedKey=encryptKey();
	}
	
	protected String getEncryptedKey() {
		return encryptedKey;
	}

	protected int getKey() {
		return key;
	}

	private String encryptKey()
	{
		int ch,a,i;
		String k=""+key,k1="";
		for(i=0;i<k.length()-1;i++)
		{
			ch=(int)k.charAt(i);
			a=ch+11493;
			k1=k1+a+" ";
		}
		ch=(int)k.charAt(i);
		a=ch+11493;
		k1=k1+a;
		return k1;
	}
	
	protected Key(String k)
	{
		this.encryptedKey=k;
		key = decryptKey(k);
	}

	private int decryptKey(String k) {
		
		int i,a=0;
		String k1="";
		String s[]=k.split(" ");
		for(i=0;i<s.length;i++)
		{
			a=Integer.parseInt(s[i])-11493;
			//System.out.println(a);
			k1=k1+(char)a;				
		}
		//System.out.println("key : "+k1);
		return Integer.parseInt(k1);
	}
	
}
