package com.frozendevop.admincontrollers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frozendevop.data_access_object.AddressRepo;
import com.frozendevop.data_access_object.CartRepo;
import com.frozendevop.data_access_object.CategoryRepo;
import com.frozendevop.data_access_object.EmployeeRepo;
import com.frozendevop.data_access_object.ImageRepo;
import com.frozendevop.data_access_object.OrderRepo;
import com.frozendevop.data_access_object.ProductRepo;
import com.frozendevop.data_access_object.PublisherRepo;
import com.frozendevop.data_access_object.UserRepo;
import com.frozendevop.models.Address;
import com.frozendevop.models.Cart;
import com.frozendevop.models.Category;
import com.frozendevop.models.Employee;
import com.frozendevop.models.Image;
import com.frozendevop.models.Order;
import com.frozendevop.models.Product;
import com.frozendevop.models.Publisher;
import com.frozendevop.models.User;

@Controller
public class ExtractData
{

	@Autowired
	AddressRepo addressRepo;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	ImageRepo imageRepo;
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	PublisherRepo publisherRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@RequestMapping("/extract")
	@ResponseBody
	public String extract() throws IOException
	{
		System.out.println("Extraction started - AddressRepo");
		List<Address> list1=addressRepo.findAll();
		FileWriter fw=new FileWriter("Address");
		BufferedWriter bw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(bw);
		for(Address address : list1)
		{
			pw.println(address.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - CartRepo");
		List<Cart> list2=cartRepo.findAll();
		fw=new FileWriter("Cart");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Cart cart : list2)
		{
			pw.println(cart.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - CategoryRepo");
		List<Category> list3=categoryRepo.findAll();
		fw=new FileWriter("Category");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Category category : list3)
		{
			pw.println(category.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - EmployeeRepo");
		List<Employee> list4=employeeRepo.findAll();
		fw=new FileWriter("Employee");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Employee employee : list4)
		{
			pw.println(employee.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - ImageRepo");
		List<Image> list5=imageRepo.findAll();
		fw=new FileWriter("Image");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Image image : list5)
		{
			pw.println(image.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - OrderRepo");
		List<Order> list6=orderRepo.findAll();
		fw=new FileWriter("Order");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Order order : list6)
		{
			pw.println(order.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - ProductRepo");
		List<Product> list7=productRepo.findAll();
		fw=new FileWriter("Product");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Product product : list7)
		{
			pw.println(product.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - PublisherRepo");
		List<Publisher> list8=publisherRepo.findAll();
		fw=new FileWriter("Publisher");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(Publisher publisher : list8)
		{
			pw.println(publisher.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		System.out.println("Extraction started - UserRepo");
		List<User> list9=userRepo.findAll();
		fw=new FileWriter("User");
		bw=new BufferedWriter(fw);
		pw=new PrintWriter(bw);
		for(User user : list9)
		{
			pw.println(user.getString());
		}
		pw.close();
		bw.close();
		fw.close();
		System.out.println("Extraction ended");
		
		return "";
	}
	
}
