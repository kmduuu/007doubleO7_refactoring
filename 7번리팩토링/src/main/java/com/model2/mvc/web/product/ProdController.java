package com.model2.mvc.web.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

import oracle.jdbc.proxy.annotation.Post;


//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProdController {
	
	///Field
	@Autowired
	@Qualifier("prodServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProdController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	/*@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}*/
	//addProductView.do를 productController 거치지 않고 들어가기 때문에 필요 X
	
	@GetMapping(value="addProduct")
	public String addProduct() throws Exception {
		// ModelAttribute => 많은 값들이 필요할 때... 사용
		
		System.out.println("/product/addProduct : GET");
		//Business Logic
		
		return "forward:/product/addProductView.jsp";
	}
	
	@PostMapping(value="addProduct")
	
	public String addProduct( @ModelAttribute("product") Product product, @RequestParam(value="file", required = false)MultipartFile file) throws Exception {
		// ModelAttribute => 많은 값들이 필요할 때... get, set을 encapsulation화 되어있음, product라고 쓴다면... 가져온 값을 product에 값 세팅한다?
		// binding ==> get, set을 해준다는 의미
		
		
		System.out.println(product);
		
		System.out.println("/post/addProduct : POST");
		
		file.transferTo(new File("C:\\Users\\Bitcamp\\git\\007doubleO7\\7번리팩토링\\src\\main\\webapp\\images\\uploadFiles\\"+file.getOriginalFilename()));		
		product.setFileName(file.getOriginalFilename());
		System.out.println(file);
		// 애초에 여기서부터 값이 null
		
		productService.addProduct(product);
		System.out.println("13131"+product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@GetMapping(value="getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		// RequestParam => 한 개의 값만 필요할 때... 사용
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	
	@RequestMapping( value="updateProductView", method=RequestMethod.GET )
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model , HttpServletRequest request) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping( value="updateProduct", method=RequestMethod.POST )
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	@RequestMapping(value = "listProduct")
	public String getProductList( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		System.out.println("돌아돌아 ");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		String menu = request.getParameter("menu");
		System.out.println("11111111"+menu);
		search.setPageSize(pageSize);	
		
		// Business logic 수행
		Map<String , Object> map = productService.getProductList(search);
		System.out.println("벤치위에");
		System.out.println(map);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		
		request.setAttribute("menu", menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		System.out.println(menu);
		
		return "forward:/product/listProduct.jsp";
	}
}