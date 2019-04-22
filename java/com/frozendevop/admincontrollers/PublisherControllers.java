package com.frozendevop.admincontrollers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.frozendevop.data_access_object.ImageRepo;
import com.frozendevop.data_access_object.PublisherRepo;
import com.frozendevop.models.Employee;
import com.frozendevop.models.Image;
import com.frozendevop.models.Publisher;
import com.frozendevop.security.Password;

@Controller
public class PublisherControllers {
	
	@Autowired
	ImageRepo imageRepo;
	
	@Autowired
	PublisherRepo publisherRepo;
	
	@Autowired
	Password password;
	
	private String uploadDirectory=System.getProperty("user.dir");
	
	@Lookup
	public Password getPasswordObject()
	{
		return null;
	}
	private int securityCheck(HttpSession session, HttpServletResponse response)
	{
		Publisher publisher=(Publisher)session.getAttribute("frozen-login-publisher");
		int c=1;
		if(publisher==null)
		{
			c=0;
		}
		return c;
	}
	
	@RequestMapping("/publisher")
	public ModelAndView publisher(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int c=securityCheck(session, response);
		if(c==1)
		{
			mv=new ModelAndView("publisher");
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
		}
		return mv;
	}
	
	@RequestMapping("/publisher/logout")
	public void publisherlogout(HttpSession session,HttpServletResponse response) throws IOException
	{
		session.invalidate();
		response.sendRedirect("/publisher");
	}
	
	@RequestMapping("/publisher/updateDetails")
	@ResponseBody
	public String updateDetails(@RequestParam("name") String name,@RequestParam("phone") String phone,
			@RequestParam("password") String pass,@RequestParam("address") String address,@RequestParam("website") String website,
			HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Publisher publisher=(Publisher)session.getAttribute("frozen-login-publisher");
			password=getPasswordObject();
			password.setEmail(publisher.getEmail());
			password.setPhone(publisher.getPhone());
			password.setPass(pass);
			password.setKey(publisher.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(publisher.getPassword()))
			{
				if(!publisher.getPhone().equals(phone))
				{
					publisher.setPhone(phone);
					password=getPasswordObject();
					password.setEmail(publisher.getEmail());
					password.setPhone(phone);
					password.setPass(pass);
					password.setKey(publisher.getEkey());
					password.generatePassword();
					publisher.setPassword(password.getEncryptedPassword());
				}
				publisher.setName(name);
				publisher.setAddress(address);
				publisher.setWebsite(website);
				publisherRepo.save(publisher);
				session.setAttribute("frozen-login-publisher", publisher);
				return "1";
			}
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return "0";
	}
	
	@RequestMapping("/publisher/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestParam("oldpass") String oldpass,@RequestParam("newpass") String newpass,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Publisher publisher=(Publisher)session.getAttribute("frozen-login-publisher");
			password=getPasswordObject();
			password.setEmail(publisher.getEmail());
			password.setPhone(publisher.getPhone());
			password.setPass(oldpass);
			password.setKey(publisher.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(publisher.getPassword()))
			{
				password=getPasswordObject();
				password.setEmail(publisher.getEmail());
				password.setPhone(publisher.getPhone());
				password.setPass(newpass);
				password.setKey(publisher.getEkey());
				password.generatePassword();
				publisher.setPassword(password.getEncryptedPassword());
				publisherRepo.save(publisher);
				return "1";
			}
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return "0";
	}
	
	public Image getNewImage()
	{
		Random rand=new Random();
		Image image=new Image();
		int c=0;
		while(c==0)
		{
			String code="IMG_"+rand.nextInt(1000000)+"_"+rand.nextInt(1000000)+".jpg";
			File file=new File(uploadDirectory+"/image/"+code);
			if(!file.exists())
			{
				image.setImgsrc(code);
				c=1;
			}
		}
		return image;
	}
	
	@RequestMapping("/publisher/image/delete/{imgsrc}")
	public void deleteImage(@PathVariable("imgsrc") String imgsrc,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			imageRepo.deleteById(imgsrc);
			deleteImageFile(imgsrc);
			
			response.sendRedirect("/publisher");
			
		}
	}
	
	private void deleteImageFile(String imgsrc) 
	{
		File file=new File(uploadDirectory+"/src/main/webapp/image/"+imgsrc);
		file.delete();
	}

	@PostMapping("/publisher/image/add")
	public void addImage(@RequestParam("file") MultipartFile file,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Publisher publisher=(Publisher)session.getAttribute("frozen-login-publisher");
			Image oldimage=(Image) imageRepo.findByUpid(publisher.getEmail());
			if(oldimage!=null)
				deleteImageFile(oldimage.getImgsrc());
			Image image=getNewImage();
			Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
			try
			{
				Files.write(fileNameAndPath, file.getBytes());
				image.setUpid(publisher.getEmail());
				imageRepo.save(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/publisher");
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
}
