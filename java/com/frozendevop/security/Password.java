package com.frozendevop.security;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class Password
{
	private String email;
	private String phone;
	private String pass;
	private Key key;
	private String encryptedPassword;
	private int max;
	
	public Password() {
		key = new Key();
		System.out.println("Password Object Created");
	}

	public void setEmail(String email) {
		this.email = email;
		if(email.length()>max)
		{
			max=email.length();
		}
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
		if(phone.length()>max)
		{
			max=phone.length();
		}
	}
	
	public void setPass(String pass) {
		this.pass = pass;
		if(pass.length()>max)
		{
			max=pass.length();
		}
	}
	
	public void setKey(String k)
	{
		key = new Key(k);
	}
	
	public void generatePassword()
	{
		encryptPassword();
	}
	
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	public String getEncryptedKey() {
		return key.getEncryptedKey();
	}

	private void encryptPassword()
	{
		int length=max;
		//System.out.println("Maximum length : "+length);
		int i=0,ch=0,k=key.getKey();
		//System.out.println("Key got during encryption : "+k);
		encryptedPassword="";
		while(i!=length) {
			
			if(i<email.length())
			{
				ch=(int)email.charAt(i);
				ch=ch+k;
				encryptedPassword=encryptedPassword+ch;
			}
			
			if(i<phone.length())
			{
				ch=(int)phone.charAt(i);
				ch=ch+k;
				encryptedPassword=encryptedPassword+ch;
			}
			
			if(i<pass.length())
			{
				ch=(int)pass.charAt(i);
				ch=ch+k;
				encryptedPassword=encryptedPassword+ch;
			}
			i++;
		}
		
	}
	
}
