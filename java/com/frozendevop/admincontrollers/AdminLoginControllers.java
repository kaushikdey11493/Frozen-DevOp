package com.frozendevop.admincontrollers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frozendevop.data_access_object.EmployeeRepo;
import com.frozendevop.data_access_object.PublisherRepo;
import com.frozendevop.models.Employee;
import com.frozendevop.models.LoginDetail;
import com.frozendevop.models.Publisher;
import com.frozendevop.security.Password;

@Controller
public class AdminLoginControllers
{
	@Autowired
	EmployeeRepo emprepo;
	
	@Autowired
	PublisherRepo publisherRepo;
	
	@Autowired
	Password password;
	
	
	@Lookup
	public Password getPasswordObject()
	{
		return null;
	}
	
	@RequestMapping("/administration")
	public String administration()
	{
		return "administration";
	}

	@RequestMapping("/administratorLogin")
	public ModelAndView administratorLogin(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		if(session.getAttribute("frozen-login-employee")!=null)
		{
			//System.out.println("Redirected : "+session.getAttribute("frozen-login-as")+"\nUser : "+session.getAttribute("frozen-login-employee"));
			response.sendRedirect("/administrator");
		}
		else
		{
			mv=new ModelAndView();
			mv.setViewName("adminlogin");
			session.setAttribute("frozen-login", "administrator-verify");
			System.out.println("Session : "+session.getAttribute("frozen-login"));
		}
		return mv;
	}
	
	@PostMapping("/administrator-verify")
	public void administratorVerify(HttpSession session,HttpServletResponse response,LoginDetail loginDetail) throws IOException
	{
		Employee employee=emprepo.findById(loginDetail.getUid()).orElse(null);
		if(employee==null)
		{
			System.out.println("Redircted");
			session.setAttribute("error", 1);
			response.sendRedirect("/administratorLogin");
		}
		else
		{
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setPass(loginDetail.getPass());
			password.setKey(employee.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(employee.getPassword()))
			{
				System.out.println("Password Matched");
				session.setAttribute("frozen-login-as", "administrator");
				session.setAttribute("frozen-login-employee", employee);
				response.sendRedirect("/administrator");
			}
			else
			{
				System.out.println("Redircted");
				session.setAttribute("error", 1);
				response.sendRedirect("/administratorLogin");
			}
		}
				
	}
	
	@RequestMapping("/publisherLogin")
	public ModelAndView publisherLogin(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		if(session.getAttribute("frozen-login-publisher")!=null)
		{
			response.sendRedirect("/publisher");
		}
		else
		{
			mv=new ModelAndView();
			mv.setViewName("adminlogin");
			session.setAttribute("frozen-login", "publisher-verify");
			System.out.println("Session : "+session.getAttribute("frozen-login"));
		}
		return mv;
	}
	
	@PostMapping("/publisher-verify")
	public void publisherVerify(HttpSession session,HttpServletResponse response,LoginDetail loginDetail) throws IOException
	{
		Publisher publisher=publisherRepo.findById(loginDetail.getUid()).orElse(null);
		if(publisher==null)
		{
			System.out.println("Redircted");
			session.setAttribute("error", 1);
			response.sendRedirect("/publisherLogin");
		}
		else
		{
			password=getPasswordObject();
			password.setEmail(publisher.getEmail());
			password.setPhone(publisher.getPhone());
			password.setPass(loginDetail.getPass());
			password.setKey(publisher.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(publisher.getPassword()))
			{
				System.out.println("Password Matched");
				session.setAttribute("frozen-login-publisher", publisher);
				response.sendRedirect("/publisher");
			}
			else
			{
				System.out.println("Redircted");
				session.setAttribute("error", 1);
				response.sendRedirect("/publisherLogin");
			}
		}
				
	}
	
	
}
