package com.frozendevop.usercontroller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frozendevop.data_access_object.AddressRepo;
import com.frozendevop.data_access_object.CartRepo;
import com.frozendevop.data_access_object.CategoryRepo;
import com.frozendevop.data_access_object.ImageRepo;
import com.frozendevop.data_access_object.OrderRepo;
import com.frozendevop.data_access_object.ProductRepo;
import com.frozendevop.data_access_object.PublisherRepo;
import com.frozendevop.data_access_object.UserRepo;
import com.frozendevop.models.Cart;
import com.frozendevop.models.Category;
import com.frozendevop.models.CategoryList;
import com.frozendevop.models.LoginDetail;
import com.frozendevop.models.Order;
import com.frozendevop.models.Product;
import com.frozendevop.models.ProductList;
import com.frozendevop.models.User;
import com.frozendevop.security.Password;

@Controller
public class UserController
{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ImageRepo imageRepo;
	
	@Autowired
	PublisherRepo publisherRepo;
	
	@Autowired
	AddressRepo addressRepo;
	
	@Autowired
	Password password;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	OrderRepo orderRepo;
	
	@Lookup
	public Password getPasswordObject()
	{
		return null;
	}
	
	private int securityCheck(HttpSession session, HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		User user=(User)session.getAttribute("frozen-user");
		int c=0;
		if(user==null)
		{
			Cookie cookie1=null,cookie2=null;
			Cookie cookies[]=request.getCookies();
			String email="";
			String password="";
			System.out.println("Cookies : "+cookies);
			if(cookies!=null)
			{
				for(Cookie cookie : cookies)
				{
					if(cookie.getName().equals("frozen-user-email"))
					{
						email=cookie.getValue();
						cookie1=cookie;
						c++;
					}
					if(cookie.getName().equals("frozen-user-password"))
					{
						password=cookie.getValue();
						cookie2=cookie;
						c++;
					}
					if(c==2)
						break;
				}
				if(c==2)
				{
					user=userRepo.findById(email).orElse(null);
					if(user.getPassword().equals(password))
					{
						c=1;
						session.setAttribute("frozen-user", user);
						response.addCookie(cookie1);
						response.addCookie(cookie2);
					}
				}
			}
			
		}
		else
		{
			c=1;
		}
		return c;
	}
	
	
	@RequestMapping("/")
	public ModelAndView home(@CookieValue(name="frozen-user-email",defaultValue="null") String cookie_email,@CookieValue(name="frozen-user-password",defaultValue="null") String cookie_pass, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		ModelAndView mv=null;
		securityCheck(session, response, request);
		mv= new ModelAndView();
		mv.setViewName("home");
		mv.addObject("categoryRepo", categoryRepo);
		mv.addObject("imageRepo",imageRepo);
		mv.addObject("productRepo", productRepo);
		return mv;
	}
	

	@RequestMapping("/product/{id}")
	public ModelAndView productDetails(HttpServletRequest request,HttpServletResponse response,HttpSession session,@PathVariable("id") String id) throws IOException
	{
		securityCheck(session, response, request);
		ModelAndView mv=new ModelAndView("productDetails");
		Product product=productRepo.findById(id).orElse(null);
		List<Category> list=categoryRepo.findAll();
		CategoryList categoryList=new CategoryList();
		categoryList.setList(list);
		mv.addObject("categoryList", categoryList);
		mv.addObject("product", product);
		mv.addObject("imageRepo", imageRepo);
		mv.addObject("publisherRepo", publisherRepo);
		return mv;
	}
	
	@RequestMapping("/user/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		ModelAndView mv=null;
		if(securityCheck(session, response, request)==1)
		{
			response.sendRedirect("/");
		}
		else
		{
			mv=new ModelAndView("login");
		}
		return mv;
	}
	
	@PostMapping("/user/login/verify")
	public void loginVerify(HttpServletResponse response,HttpSession session,LoginDetail loginDetail) throws IOException
	{
		User user=userRepo.findById(loginDetail.getUid()).orElse(null);
		if(user!=null)
		{
			password=getPasswordObject();
			password.setEmail(user.getEmail());
			password.setPhone(user.getPhone());
			password.setPass(loginDetail.getPass());
			password.setKey(user.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(user.getPassword()))
			{
				session.setAttribute("frozen-user", user);
				Cookie cookie1=new Cookie("frozen-user-email", user.getEmail());
				cookie1.setMaxAge(60*60*24*30);
				cookie1.setPath("/");
				Cookie cookie2=new Cookie("frozen-user-password", user.getPassword());
				cookie2.setMaxAge(60*60*24*30);
				cookie2.setPath("/");
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				response.sendRedirect("/");
			}
			else
			{
				session.setAttribute("error", 1);
				response.sendRedirect("/user/login");
			}
		}
		else
		{
			session.setAttribute("error", 1);
			response.sendRedirect("/user/login");
		}
		
	}
		
	@RequestMapping("/user/register")
	public ModelAndView register(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		ModelAndView mv=null;
		if(securityCheck(session, response, request)==1)
		{
			response.sendRedirect("/");
		}
		else
		{
			mv=new ModelAndView("register");
		}
		return mv;
	}
	
	@PostMapping("/user/register/verify")
	public void registerVerify(HttpServletResponse response,HttpSession session,User user) throws IOException
	{
		password=getPasswordObject();
		password.setEmail(user.getEmail());
		password.setPhone(user.getPhone());
		password.setPass(user.getPassword());
		password.generatePassword();
		user.setPassword(password.getEncryptedPassword());
		user.setEkey(password.getEncryptedKey());
		userRepo.save(user);
		
		session.setAttribute("frozen-user", user);
		Cookie cookie1=new Cookie("frozen-user-email", user.getEmail());
		cookie1.setMaxAge(60*60*24*30);
		cookie1.setPath("/");
		Cookie cookie2=new Cookie("frozen-user-password", user.getPassword());
		cookie2.setMaxAge(60*60*24*30);
		cookie2.setPath("/");
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		response.sendRedirect("/");
	}
	
	@RequestMapping("/user")
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		ModelAndView mv=null;
		if(securityCheck(session, response, request)==1)
		{
			mv=new ModelAndView("user");
			mv.addObject("imageRepo",imageRepo);
			mv.addObject("addressRepo",addressRepo);
		}
		else
		{
			response.sendRedirect("/user/login");
		}
		
		return mv;
	}
	
	@RequestMapping("/user/cart")
	public ModelAndView cart(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		ModelAndView mv=null;
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			mv=new ModelAndView("cart");
			mv.addObject("cartRepo", cartRepo);
			mv.addObject("orderRepo", orderRepo);
			mv.addObject("productRepo", productRepo);
			mv.addObject("imageRepo", imageRepo);
		}
		
		return mv;
	}
	
	@RequestMapping("/cart/add/{pid}")
	public void addToCart(@PathVariable("pid") String pid,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		User user=(User)session.getAttribute("frozen-user");
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			Cart cart=new Cart();
			cart.setCid(getNewCartId());
			cart.setUid(user.getEmail());
			cart.setPid(pid);
			cartRepo.save(cart);
			response.sendRedirect("/user/cart");
		}
	}
	
	@RequestMapping("/order/add/{pid}")
	public void addToOrder1(@PathVariable("pid") String pid,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		User user=(User)session.getAttribute("frozen-user");
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			Order order=new Order();
			order.setOid(getNewCartId());
			order.setUid(user.getEmail());
			order.setPid(pid);
			orderRepo.save(order);
			response.sendRedirect("/user/cart");
		}
	}
	
	private String getNewCartId() {
		Random random=new Random();
		int id=0;
		while(true)
		{
			id=random.nextInt(1000000000);
			Cart cart=cartRepo.findById(""+id).orElse(null);
			if(cart==null)
			{
				break;
			}
		}
		return ""+id;
	}
	
	@RequestMapping("/user/cart/remove/{cid}")
	public void removeFromCart(@PathVariable("cid") String cid,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		User user=(User)session.getAttribute("frozen-user");
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			cartRepo.deleteById(cid);
			response.sendRedirect("/user/cart");
		}
	}
	
	@RequestMapping("/user/order/add/{cid}")
	public void addToOrder(@PathVariable("cid") String cid,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		User user=(User)session.getAttribute("frozen-user");
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			Cart cart=cartRepo.findById(cid).orElse(null);
			cartRepo.deleteById(cid);
			Order order=new Order();
			order.setOid(cid);
			order.setPid(cart.getPid());
			order.setUid(cart.getUid());
			orderRepo.save(order);
			response.sendRedirect("/user/cart");
		}
	}
	
	@RequestMapping("/user/order/remove/{oid}")
	public void removeFromOrder(@PathVariable("oid") String oid,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		if(securityCheck(session, response, request)==0)
		{
			response.sendRedirect("/user/login");
		}
		else
		{
			orderRepo.deleteById(oid);
			response.sendRedirect("/user/cart");
		}
	}
	
	@RequestMapping("/category/{cid}")
	public ModelAndView category(HttpServletRequest request,HttpServletResponse response,HttpSession session,@PathVariable("cid") String cid) throws IOException
	{
		securityCheck(session, response, request);
		ModelAndView mv=new ModelAndView("category");
		Category category=categoryRepo.findById(cid).orElse(null);
		mv.addObject("category", category);
		List<Product> list=productRepo.findByCategoryAndActive(cid,"yes");
		ProductList productList=new ProductList();
		productList.setList(list);
		mv.addObject("productList", productList);
		mv.addObject("imageRepo", imageRepo);
		mv.addObject("publisherRepo", publisherRepo);
		return mv;
	}
	
	@RequestMapping("/user/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
		session.invalidate();
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			System.out.println("Inside Cookeie logout");
			for(Cookie cookie : cookies)
			{
				System.out.println(cookie.getName()+" : "+cookie.getValue()+" : "+cookie.getPath());
				if(cookie.getName().equals("frozen-user-email"))
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				if(cookie.getName().equals("frozen-user-password") )
				{
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		response.sendRedirect("/");
	}
}
