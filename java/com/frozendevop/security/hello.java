package com.frozendevop.security;

public class hello {

	public static void main(String[] args) {
		
		Password pass=new Password();
		pass.setEmail("kaushikdey11493@gmail.com");
		pass.setPhone("7003641714");
		pass.setPass("kDiC@11493");
		pass.generatePassword();
		System.out.println("Encrypted Password : "+pass.getEncryptedPassword()+
				"\nLength of encrypted password : "+pass.getEncryptedPassword().length()+
				"\nEncrypted Key : "+pass.getEncryptedKey());
	}

}
