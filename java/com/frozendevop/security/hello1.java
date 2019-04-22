package com.frozendevop.security;
//37
public class hello1 {
	public static void main(String[] args) {
		
		Password pass=new Password();
		pass.setEmail("kaushikdey11493@gmail.com");
		pass.setPhone("7003641714");
		pass.setPass("kDiC@11493");
		pass.setKey("11550 11550 11550 11550");
		pass.generatePassword();
		String np="101061005410106100961004710067101161004710104101141005010066101031005310063101041005110048101061004810048100991005410051101001004810056101201005110050100481004810051100561005010063101021010810096101041010710045100981011010108";
		String np1=pass.getEncryptedPassword();
		for(int i=0;i<np.length();i++)
		{
			int c1=np.charAt(i);
			int c2=np1.charAt(i);
			if(c1!=c2)
			{
				System.out.println("Not same");
				break;
			}
		}
		System.out.println("Same");
	}
}
