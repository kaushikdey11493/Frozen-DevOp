package com.frozendevop.admincontrollers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.frozendevop.data_access_object.CategoryRepo;
import com.frozendevop.data_access_object.EmployeeRepo;
import com.frozendevop.data_access_object.ImageRepo;
import com.frozendevop.data_access_object.ProductRepo;
import com.frozendevop.data_access_object.PublisherRepo;
import com.frozendevop.models.Category;
import com.frozendevop.models.CategoryList;
import com.frozendevop.models.Employee;
import com.frozendevop.models.EmployeeList;
import com.frozendevop.models.Image;
import com.frozendevop.models.Product;
import com.frozendevop.models.ProductList;
import com.frozendevop.models.Publisher;
import com.frozendevop.models.PublisherList;
import com.frozendevop.security.Password;

@Controller
public class AdministratorControllers
{
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	ImageRepo imageRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	PublisherRepo publisherRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	
	@Autowired
	Password password;
	
	private String uploadDirectory=System.getProperty("user.dir");
	
	@Lookup
	public Password getPasswordObject()
	{
		return null;
	}
	
	private int securityCheck(HttpSession session, HttpServletResponse response) throws IOException {
		
		String s=(String)session.getAttribute("frozen-login-as");
		Employee employee=(Employee)session.getAttribute("frozen-login-employee");
		int c=1;
		if(s==null || employee==null)
		{
			c=0;
		}
		return c;
	}
	
	
	@RequestMapping("/administrator")
	public ModelAndView administrator(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			mv=new ModelAndView();
			mv.setViewName("administrator");
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/logout")
	public void administratorlogout(HttpSession session,HttpServletResponse response) throws IOException
	{
		session.invalidate();
		response.sendRedirect("/administrator");
	}
	
	@RequestMapping("/administrator/updateDetails")
	@ResponseBody
	public String updateDetails(@RequestParam("name") String name,@RequestParam("phone") String phone,@RequestParam("password") String pass,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setPass(pass);
			password.setKey(employee.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(employee.getPassword()))
			{
				if(!employee.getPhone().equals(phone))
				{
					employee.setPhone(phone);
					password=getPasswordObject();
					password.setEmail(employee.getEmail());
					password.setPhone(phone);
					password.setPass(pass);
					password.setKey(employee.getEkey());
					password.generatePassword();
					employee.setPassword(password.getEncryptedPassword());
				}
				employee.setName(name);
				employeeRepo.save(employee);
				session.setAttribute("frozen-login-employee", employee);
				return "1";
			}
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return "0";
	}
	
	@RequestMapping("/administrator/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestParam("oldpass") String oldpass,@RequestParam("newpass") String newpass,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setPass(oldpass);
			password.setKey(employee.getEkey());
			password.generatePassword();
			if(password.getEncryptedPassword().equals(employee.getPassword()))
			{
				password=getPasswordObject();
				password.setEmail(employee.getEmail());
				password.setPhone(employee.getPhone());
				password.setPass(newpass);
				password.setKey(employee.getEkey());
				password.generatePassword();
				employee.setPassword(password.getEncryptedPassword());
				employeeRepo.save(employee);
				return "1";
			}
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return "0";
	}
	
	@RequestMapping("/administrator/employees")
	public ModelAndView employees(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Employee> list=employeeRepo.findAll();
			EmployeeList employeeList=new EmployeeList();
			employeeList.setList(list);
			mv=new ModelAndView();
			mv.setViewName("employees");
			mv.addObject("emplist", employeeList);
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/employee/form")
	public ModelAndView employeeFrom(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			mv=new ModelAndView("addEmployee");
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/employee/verifyEmployee")
	public void verifyEmployee(HttpSession session,HttpServletResponse response,Employee employee,@RequestParam("file") MultipartFile file) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setPass(employee.getPassword());
			password.generatePassword();
			employee.setPassword(password.getEncryptedPassword());
			employee.setEkey(password.getEncryptedKey());
			employee.setDate(new Date().toString());
			employeeRepo.save(employee);
			if(!file.isEmpty())
			{
				Image image=getNewImageObject();
				Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
				try
				{
					Files.write(fileNameAndPath, file.getBytes());
					System.out.println(fileNameAndPath);
					image.setUpid(employee.getEmail());
					imageRepo.save(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			response.sendRedirect("/administrator/employees");
		}
		else
		{
			response.sendRedirect("/administration");
						
		}
	}
	
	private Image getNewImageObject()
	{
		Random rand=new Random();
		Image image=new Image();
		int c=0;
		while(c==0)
		{
			String code="IMG_"+rand.nextInt(1000000)+"_"+rand.nextInt(1000000)+".jpg";
			File file=new File(uploadDirectory+"/src/main/webapp/image/"+code);
			if(!file.exists())
			{
				image.setImgsrc(code);
				c=1;
			}
		}
		return image;
	}
	
	@RequestMapping("/administrator/image/delete/{imgsrc}")
	public void deleteImage(@PathVariable("imgsrc") String imgsrc,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			imageRepo.deleteById(imgsrc);
			deleteImageFile(imgsrc);
			
			response.sendRedirect("/administrator");
			
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	private void deleteImageFile(String imgsrc) 
	{
		File file=new File(uploadDirectory+"/src/main/webapp/image/"+imgsrc);
		file.delete();
	}

	@PostMapping("/administrator/image/add")
	public void addImage(@RequestParam("file") MultipartFile file,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			List<Image> limage=imageRepo.findByUpid(employee.getEmail());
			Image oldimage=null;
			if(!limage.isEmpty())
				oldimage=limage.get(0);
			if(oldimage!=null)
				deleteImageFile(oldimage.getImgsrc());
			Image image=getNewImageObject();
			Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
			try
			{
				Files.write(fileNameAndPath, file.getBytes());
				image.setUpid(employee.getEmail());
				imageRepo.save(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/administrator");
		}
		else
			response.sendRedirect("/administration");
	}
	
	@RequestMapping("/administrator/employee/search")
	@ResponseBody
	public String searchEmployee(@RequestParam("value") String value,HttpSession session,HttpServletResponse response) throws IOException
	{
		String res="";
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Employee> list=employeeRepo.search(value);
			
			for(Employee employee:list)
			{
				String imgsrc="";
				List<Image> limg=imageRepo.findByUpid(employee.getEmail());
				Image img=null;
				if(!limg.isEmpty())
					img=limg.get(0);
				if(img==null)
					imgsrc="/image/empty.png";
				else
					imgsrc="/image/"+img.getImgsrc();
				res=res+"<abbr title=\"Click to see details\" style=\"font-size:bold\">\r\n" + 
						"				<a href=\"/administrator/employee/"+employee.getEmail()+"\">" + 
						"					<div class=\"emp\">\r\n" + 
						"						<image class=\"img\" src=\""+imgsrc+"\" >\r\n" + 
						"						<div class=\"det\" >"+employee.getName()+"</div>\r\n" + 
						"						<div class=\"no\">"+employee.getPhone()+"</div>\r\n" + 
						"					</div>\r\n" + 
						"				</a>\r\n" + 
						"			</abbr>";
			}
		}
		else
		{
			return "0";
		}
		return res;
	}
	
	@RequestMapping("/administrator/employee/{email}")
	public ModelAndView employeeDetails(@PathVariable("email") String email, HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			mv=new ModelAndView("employeeDetails");
			Employee employee=employeeRepo.findById(email).orElse(null);
			if(employee!=null)
			{
				mv.addObject("employee", employee);
				mv.addObject("imageRepo", imageRepo);
			}
		}
		else
		{
			response.sendRedirect("/administration");
		}
		return mv;
	}
	
	@RequestMapping("/administrator/employee/delete/{email}")
	@Transactional
	public void deleteEmployee(@PathVariable("email") String email, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			employeeRepo.deleteById(email);
			List<Image> limage=imageRepo.findByUpid(email);
			Image image=null;
			if(!limage.isEmpty())
				image=limage.get(0);
			System.out.println("File deleted");
			if(image!=null)
			{
				System.out.println("Going to delete File");
				deleteImageFile(image.getImgsrc());
				System.out.println("File deleted");
				imageRepo.deleteByUpid(email);
				System.out.println("Image deleted");
			}
			response.sendRedirect("/administrator/employees");
		}
		else
		{
			response.sendRedirect("/administration");
		}
		
	}
	
	@RequestMapping("/administrator/publishers")
	public ModelAndView publisher(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Publisher> list=publisherRepo.findAll();
			PublisherList publisherList=new PublisherList();
			publisherList.setList(list);
			mv=new ModelAndView();
			mv.setViewName("publishers");
			mv.addObject("publisherList", publisherList);
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/publisher/{email}")
	public ModelAndView publisherDetails(@PathVariable("email") String email, HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Publisher publisher=publisherRepo.findById(email).orElse(null);
			if(publisher!=null)
			{
				mv=new ModelAndView("publisherDetails");
				mv.addObject("publisher", publisher);
				mv.addObject("imageRepo", imageRepo);
			}
			else
				response.sendRedirect("/administrator/publishers");
		}
		else
		{
			response.sendRedirect("/administration");
		}
		return mv;
	}
	
	@RequestMapping("/administrator/publisher/search")
	@ResponseBody
	public String searchPublisher(@RequestParam("value") String value,HttpSession session,HttpServletResponse response) throws IOException
	{
		String res="";
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Publisher> list=publisherRepo.search(value);
			
			for(Publisher publisher:list)
			{
				String imgsrc="";
				List<Image> limg=imageRepo.findByUpid(publisher.getEmail());
				Image img=null;
				if(!limg.isEmpty())
					img=limg.get(0);
				if(img==null)
					imgsrc="/image/empty.png";
				else
					imgsrc="/image/"+img.getImgsrc();
				res=res+"<abbr title=\"Click to see details\" style=\"font-size:bold\">\r\n" + 
						"				<a href=\"/administrator/employee/"+publisher.getEmail()+"\">" + 
						"					<div class=\"emp\">\r\n" + 
						"						<image class=\"img\" src=\""+imgsrc+"\" >\r\n" + 
						"						<div class=\"det\" >"+publisher.getName()+"</div>\r\n" + 
						"						<div class=\"no\">"+publisher.getPhone()+"</div>\r\n" + 
						"					</div>\r\n" + 
						"				</a>\r\n" + 
						"			</abbr>";
			}
		}
		else
		{
			return "0";
		}
		return res;
	}
	
	@RequestMapping("/administrator/publisher/form")
	public ModelAndView publisherForm(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			mv=new ModelAndView("addPublisher");
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/publisher/verifyPublisher")
	public void verifyPublisher(HttpSession session,HttpServletResponse response,Publisher publisher,@RequestParam("file") MultipartFile file) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			password=getPasswordObject();
			password.setEmail(publisher.getEmail());
			password.setPhone(publisher.getPhone());
			password.setPass(publisher.getPassword());
			password.generatePassword();
			publisher.setPassword(password.getEncryptedPassword());
			publisher.setEkey(password.getEncryptedKey());
			publisher.setDate(new Date().toString());
			publisherRepo.save(publisher);
			
			if(!file.isEmpty())
			{
				Image image=getNewImageObject();
				Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
				try
				{
					Files.write(fileNameAndPath, file.getBytes());
					System.out.println(fileNameAndPath);
					image.setUpid(publisher.getEmail());
					imageRepo.save(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			response.sendRedirect("/administrator/publishers");
		}
		else
		{
			response.sendRedirect("/administration");
						
		}
	}
	
	@RequestMapping("/administrator/publisher/delete/{email}")
	@Transactional
	public void deletePublisher(@PathVariable("email") String email, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			publisherRepo.deleteById(email);
			List<Image> limg=imageRepo.findByUpid(email);
			Image image=null;
			if(!limg.isEmpty())
				image=limg.get(0);
			System.out.println("File deleted");
			if(image!=null)
			{
				System.out.println("Going to delete File");
				deleteImageFile(image.getImgsrc());
				System.out.println("File deleted");
				imageRepo.deleteByUpid(email);
				System.out.println("Image deleted");
			}
			response.sendRedirect("/administrator/publishers");
		}
		else
		{
			response.sendRedirect("/administration");
		}
		
	}
	
	@RequestMapping("/administrator/categories")
	public ModelAndView category(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			mv=new ModelAndView();
			mv.setViewName("categories");
			mv.addObject("categoryRepo", categoryRepo);
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
		}
		return mv;
	}
	
	@RequestMapping("/administrator/category/form")
	public ModelAndView categoryForm(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Category> list=categoryRepo.findAll();
			CategoryList categoryList=new CategoryList();
			categoryList.setList(list);
			mv=new ModelAndView();
			mv.setViewName("addCategory");
			mv.addObject("categoryList", categoryList);
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	@RequestMapping("/administrator/category/verifyCategory")
	public void verifyCategory(HttpSession session,HttpServletResponse response,Category category,@RequestParam("file") MultipartFile file) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			category.setId(getNewCategoryId());
			if(category.getParent().equals(""))
			{
				category.setParent(null);
			}
			categoryRepo.save(category);
			
			if(!file.isEmpty())
			{
				Image image=getNewImageObject();
				Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
				try
				{
					Files.write(fileNameAndPath, file.getBytes());
					System.out.println(fileNameAndPath);
					image.setUpid(category.getId());
					imageRepo.save(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			response.sendRedirect("/administrator/categories");
		}
		else
		{
			response.sendRedirect("/administration");
						
		}
	}
	
	
	private String getNewCategoryId() {
		Random random=new Random();
		int id=0;
		while(true)
		{
			id=random.nextInt(1000000000);
			Category category=categoryRepo.findById(""+id).orElse(null);
			if(category==null)
			{
				break;
			}
		}
		return ""+id;
	}
	
	
	@RequestMapping("/administrator/category/{id}")
	public ModelAndView categoryDetails(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Category category=categoryRepo.findById(id).orElse(null);
			if(category!=null)
			{
				mv=new ModelAndView("categoryDetails");
				List<Category> list=categoryRepo.findAll();
				CategoryList categoryList=new CategoryList();
				categoryList.setList(list);
				
				Category parent=null;
				if(category.getParent()!=null)
					parent=categoryRepo.findById(category.getParent()).orElse(null);
				
				mv.addObject("categoryList",categoryList);
				mv.addObject("parent", parent);
				mv.addObject("category", category);
				mv.addObject("imageRepo", imageRepo);
			}
			else
				response.sendRedirect("/administrator/categories");
		}
		else
		{
			response.sendRedirect("/administrator");
		}
		return mv;
	}
	
	@RequestMapping("/administrator/category/updateDetails")
	@ResponseBody
	public String updateCategoryDetails(Category category,@RequestParam("password") String pass,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		System.out.println("Category received : "+category);
		System.out.println("Password received : "+pass);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setPass(pass);
			password.setKey(employee.getEkey());
			password.generatePassword();
			System.out.println("Passwor matched ? "+password.getEncryptedPassword().equals(employee.getPassword()));
			if(password.getEncryptedPassword().equals(employee.getPassword()))
			{
				categoryRepo.save(category);
				return "1";
			}
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return "0";
	}
	
	
	@PostMapping("/administrator/category/image/add")
	public void addCategoryImage(@RequestParam("id") String id,@RequestParam("file") MultipartFile file,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Image oldimage=null;
			List<Image> limg=imageRepo.findByUpid(id);
			if(!limg.isEmpty())
				oldimage=limg.get(0);
			if(oldimage!=null)
				deleteImageFile(oldimage.getImgsrc());
			Image image=getNewImageObject();
			Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
			try
			{
				Files.write(fileNameAndPath, file.getBytes());
				image.setUpid(id);
				imageRepo.save(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/administrator/category/"+id);
		}
		else
			response.sendRedirect("/administration");
	}
	
	
	@RequestMapping("/administrator/category/image/delete/{imgsrc}")
	public void deleteCategoryImage(@RequestParam("id") String id,@PathVariable("imgsrc") String imgsrc,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			imageRepo.deleteById(imgsrc);
			deleteImageFile(imgsrc);
			
			response.sendRedirect("/administrator/category/"+id);
			
		}
	}
	
	@RequestMapping("/administrator/category/search")
	@ResponseBody
	public String searchCategory(@RequestParam("value") String value,HttpSession session,HttpServletResponse response) throws IOException
	{
		String res="";
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Category> list=categoryRepo.search(value);
			
			for(Category category:list)
			{
				String imgsrc="";
				List<Image> limg=imageRepo.findByUpid(category.getId());
				Image img=null;
				if(!limg.isEmpty())
					img=limg.get(0);
				if(img==null)
					imgsrc="/image/empty.png";
				else
					imgsrc="/image/"+img.getImgsrc();
				res=res+"<abbr title=\"Click to see details of "+category.getName()+"\" style=\"font-size:bold\">\r\n" + 
						"				<a href=\"/administrator/category/"+category.getId()+"\">\r\n" + 
						"					<div class=\"emp\" >\r\n" + 
						"						\r\n" + 
						"						<image class=\"img\" src=\""+imgsrc +"\" >\r\n" + 
						"						<div class=\"det\" >"+category.getName()+"</div>\r\n" + 
						"					</div>\r\n" + 
						"				</a>\r\n" + 
						"			</abbr>"; 
						
			}
		}
		else
		{
			return "0";
		}
		return res;
	}
	
	@RequestMapping("/administrator/category/delete/{id}")
	@Transactional
	public void deleteCategory(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			categoryRepo.deleteById(id);
			List<Image> limage=imageRepo.findByUpid(id);
			System.out.println("File deleted");
			Image image=null;
			if(!limage.isEmpty())
				image=limage.get(0);
			if(image!=null)
			{
				System.out.println("Going to delete File");
				deleteImageFile(image.getImgsrc());
				System.out.println("File deleted");
				imageRepo.deleteByUpid(id);
				System.out.println("Image deleted");
			}
			response.sendRedirect("/administrator/categories");
		}
		else
		{
			response.sendRedirect("/administration");
		}
		
	}
	
	@RequestMapping("/administrator/products")
	public ModelAndView products(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			ProductList productList=new ProductList();
			List<Product> list=productRepo.findAll();
			productList.setList(list);
			mv=new ModelAndView("products");
			mv.addObject("productRepo", productRepo);	
			mv.addObject("imageRepo", imageRepo);
		}
		else
		{
			response.sendRedirect("/administration");
						
		}
		return mv;
	}
	
	@RequestMapping("/administrator/product/form")
	public ModelAndView productForm(HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Category> list=categoryRepo.findAll();
			CategoryList categoryList=new CategoryList();
			categoryList.setList(list);
			
			List<Publisher> list1=publisherRepo.findAll();
			PublisherList publisherList=new PublisherList();
			publisherList.setList(list1);
			
			mv=new ModelAndView();
			mv.setViewName("addProduct");
			mv.addObject("publisherList", publisherList);
			mv.addObject("categoryList", categoryList);
		}
		else
		{
			response.sendRedirect("/administration");
			
		}
		return mv;
	}
	
	
	@RequestMapping("/administrator/product/verifyProduct")
	public void verifyProduct(HttpSession session,HttpServletResponse response,Product product,@RequestParam("files") MultipartFile[] files) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			product.setPid(getNewProductId());
			product.setEmpid(employee.getEmail());
			System.out.println("Product received : "+product);
			if(product.getSprice()<product.getMprice())
				product.setDiscount(product.getMprice()-product.getSprice());
			employee.setNoofpro(employee.getNoofpro()+1);
			employeeRepo.save(employee);
			Publisher publisher=publisherRepo.findById(product.getPubid()).orElse(null);
			publisher.setNoofbooks(publisher.getNoofbooks()+1);
			publisherRepo.save(publisher);
			productRepo.save(product);
			
			System.out.println("File length : "+files.length);
			
			for(MultipartFile file : files)
			{
				if(!file.isEmpty())
				{
					Image image=getNewImageObject();
					Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
					try
					{
						Files.write(fileNameAndPath, file.getBytes());
						System.out.println(fileNameAndPath);
						image.setUpid(product.getPid());
						imageRepo.save(image);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			response.sendRedirect("/administrator/products");
		}
		else
		{
			response.sendRedirect("/administration");
						
		}
	}

	private String getNewProductId() {
		Random random=new Random();
		int id=0;
		while(true)
		{
			id=random.nextInt(1000000000);
			Product product=productRepo.findById(""+id).orElse(null);
			if(product==null)
			{
				break;
			}
		}
		return ""+id;
	}
	
	
	@RequestMapping("/administrator/product/{id}")
	public ModelAndView productDetails(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		ModelAndView mv=null;
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Product product=productRepo.findById(id).orElse(null);
			if(product!=null)
			{
				mv=new ModelAndView("productDetails");
				List<Category> list=categoryRepo.findAll();
				CategoryList categoryList=new CategoryList();
				categoryList.setList(list);
					
				mv.addObject("categoryList", categoryList);
				mv.addObject("product", product);
				mv.addObject("imageRepo", imageRepo);
			}
			else
				response.sendRedirect("/administrator/products");
		}
		else
		{
			response.sendRedirect("/administrator");
		}
		return mv;
	}
	
	@RequestMapping("/administrator/product/activate/{id}")
	public void activateProduct(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Product product=productRepo.findById(id).orElse(null);
			product.setActive("yes");
			productRepo.save(product);
			response.sendRedirect("/administrator/product/"+id);
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/deactivate/{id}")
	public void deactivateProduct(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Product product=productRepo.findById(id).orElse(null);
			product.setActive("no");
			productRepo.save(product);
			response.sendRedirect("/administrator/product/"+id);
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/delete/{id}")
	@Transactional
	public void deleteProduct(@PathVariable("id") String id, HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Product product=productRepo.findById(id).orElse(null);
			Publisher publisher=publisherRepo.findById(product.getPubid()).orElse(null);
			publisher.setNoofbooks(publisher.getNoofbooks()-1);
			productRepo.deleteById(id);
			List<Image> limage=imageRepo.findByUpid(id);
			System.out.println("File deleted");
			for(Image image : limage)
			{
				System.out.println("Going to delete File");
				deleteImageFile(image.getImgsrc());
				System.out.println("File deleted");
				System.out.println("Image deleted");
			}
			imageRepo.deleteByUpid(id);
			response.sendRedirect("/administrator/products");
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/image/add")
	public void addProductImage(HttpSession session,HttpServletResponse response,@RequestParam("id") String id,@RequestParam("files") MultipartFile[] files) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			for(MultipartFile file : files)
			{
				if(!file.isEmpty())
				{
					Image image=getNewImageObject();
					Path fileNameAndPath = Paths.get(uploadDirectory,"/src/main/webapp/image/"+image.getImgsrc());
					try
					{
						Files.write(fileNameAndPath, file.getBytes());
						System.out.println(fileNameAndPath);
						image.setUpid(id);
						imageRepo.save(image);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			response.sendRedirect("/administrator/product/"+id);
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/{id}/image/delete/{imgsrc}")
	public void deleteProductImage(@PathVariable("id") String id,@PathVariable("imgsrc") String imgsrc,HttpSession session,HttpServletResponse response) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			imageRepo.deleteById(imgsrc);
			deleteImageFile(imgsrc);
			
			response.sendRedirect("/administrator/product/"+id);
			
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/update")
	public void updateProduct(HttpSession session,HttpServletResponse response,Product product,@RequestParam("password") String password1) throws IOException
	{
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			Employee employee=(Employee)session.getAttribute("frozen-login-employee");
			password=getPasswordObject();
			password.setEmail(employee.getEmail());
			password.setPhone(employee.getPhone());
			password.setKey(employee.getEkey());
			password.setPass(password1);
			password.generatePassword();
			if(password.getEncryptedPassword().equals(employee.getPassword()))
			{
				if(product.getSprice()<product.getMprice())
					product.setDiscount(product.getMprice()-product.getSprice());
				System.out.println("Category : "+product.getCategory());
				productRepo.save(product);
			}
			response.sendRedirect("/administrator/product/"+product.getPid());
		}
		else
		{
			response.sendRedirect("/administration");
		}
	}
	
	@RequestMapping("/administrator/product/search")
	@ResponseBody
	public String searchProduct(@RequestParam("value") String value,HttpSession session,HttpServletResponse response) throws IOException
	{
		String res="";
		int sec=securityCheck(session,response);
		if(sec==1)
		{
			List<Product> list1=productRepo.search(value,"no");
			List<Product> list2=productRepo.search(value,"yes");
			
			if(!list1.isEmpty())
			{
				res="<div style='margin:50px 0 0 40%;font-size:25px;font-weight:bold;'>Inactive Products</div>";
				for(Product product : list1)
				{
					List<Image> limg=imageRepo.findByUpid(product.getPid());
					Image image=null;
					if(!limg.isEmpty())
						image=limg.get(0);
					String imgsrc="emptybook.jpg";
					if(image!=null)
						imgsrc=image.getImgsrc();
						
					res=res+"<abbr title='Click to see details' style='font-size:bold'>"+
						"<a href='/administrator/product/"+product.getPid() +"'>"+
							"<div class='pro' >"+
								"<image class='img' src='/image/"+imgsrc +"' >"+
								"<div class='det' >"+product.getPname() +"</div>"+
								"<div class='no'>ISBN "+product.getIsbn() +"</div>"+
								"<div class='price'>Rs. "+product.getSprice() +"</div>"+
								"<div class='author'>By "+product.getAuthor() +"</div>"+
							"</div>"+
						"</a>"+
					"</abbr>";
				}
			}
			if(!list2.isEmpty())
			{
				res=res+"<div style='margin:50px 0 0 40%;font-size:25px;font-weight:bold;'>Active Products</div>";
				for(Product product : list2)
				{
					List<Image> limg=imageRepo.findByUpid(product.getPid());
					Image image=null;
					if(!limg.isEmpty())
						image=limg.get(0);
					String imgsrc="emptybook.jpg";
					if(image!=null)
						imgsrc=image.getImgsrc();
						
					res=res+"<abbr title='Click to see details' style='font-size:bold'>"+
						"<a href='/administrator/product/"+product.getPid() +"'>"+
							"<div class='pro' >"+
								"<image class='img' src='/image/"+imgsrc +"' >"+
								"<div class='det' >"+product.getPname() +"</div>"+
								"<div class='no'>ISBN "+product.getIsbn() +"</div>"+
								"<div class='price'>Rs. "+product.getSprice() +"</div>"+
								"<div class='author'>By "+product.getAuthor() +"</div>"+
							"</div>"+
						"</a>"+
					"</abbr>";
				}
			}
		}
		else
		{
			return "0";
		}
		return res;
	}
}
