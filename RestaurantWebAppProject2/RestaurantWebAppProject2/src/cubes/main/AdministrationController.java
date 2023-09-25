package cubes.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cubes.main.dao.CategoryDAO;
import cubes.main.dao.ProductDAO;
import cubes.main.dao.TagDAO;
import cubes.main.entity.Category;
import cubes.main.entity.Product;
import cubes.main.entity.Tag;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private TagDAO tagDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
//	!!!!!!    CATEGORY    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	@RequestMapping("/category-list")
	public String getCategoryList(Model model) {
		
		List<Category> list = categoryDAO.getCategoryList();
		
		list.forEach(c -> System.out.println(c.toString()));
		
		model.addAttribute("categoryList", list);
		
		return "category-list";
	}
	
	@RequestMapping("/category-form")
	public String getCategoryForm(Model model) {
	
		Category category = new Category();
		model.addAttribute("category", category);
		
		return "category-form";
		
	}
	
	@RequestMapping("/category-save")
	public String saveCategory(@ModelAttribute Category category) {
		
		categoryDAO.saveCategory(category);
		
		return "redirect:/administration/category-list";
	}
	
	@RequestMapping("/category-form-update")
	public String getCategoryUpdate(@RequestParam int id, Model model) {
		
		Category category = categoryDAO.getCategory(id);
		model.addAttribute("category", category);
		
		categoryDAO.saveCategory(category);
		
		return "category-form";
	}
	
	@RequestMapping("/category-delete")
	public String getCategoryDelete(@RequestParam int id) {
		
		categoryDAO.deleteCategory(id);
		
		return "redirect:/administration/category-list";
	}
	
// 	!!!!!!    TAG    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("/tag-list")
	public String getTagList(Model model) {
		
		List<Tag> tagList = tagDAO.getTagList();
		model.addAttribute("tagList", tagList);
		
		return "tag-list";
	}
	
	@RequestMapping("/tag-form")
	public String getTagForm(Model model) {
		
		Tag tag = new Tag();
		model.addAttribute("tag", tag);
		
		return "tag-form";
	}
	
	@RequestMapping("/tag-save")
	public String saveTag(@ModelAttribute Tag tag) {
		
		tagDAO.saveTag(tag);
		
		return "redirect:/administration/tag-list";
	}
	
	@RequestMapping("/tag-form-update")
	public String getTagFormUpdate(@RequestParam int id, Model model) {
		
		Tag tag = tagDAO.getTag(id);
		model.addAttribute("tag", tag);
		
		tagDAO.saveTag(tag);
		
		return "tag-form";
		
	}
	
	@RequestMapping("/tag-delete")
	public String getTagDelete(@RequestParam int id, Model model) {
		
		tagDAO.deleteTag(id);
		
		return "redirect:/administration/tag-list";
	}
	
//	!!!!!!    PRODUCT	    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	

	@RequestMapping("/product-list")
	public String getProductList(Model model) {
		
		List<Product> productList = productDAO.getProductList();
				
		model.addAttribute("productList", productList);
		
		return "product-list";
	}
	
	@RequestMapping("/product-form")
	public String getProductForm(Model model) {
		
		Product product = new Product();
		List<Category> categoryList = categoryDAO.getCategoryList();
		List<Tag> tagList = tagDAO.getTagList();
		

		model.addAttribute("product", product);
		model.addAttribute("categoryList", categoryList);		
		model.addAttribute("tagList", tagList);		
		
		
		return "product-form";
	}
	
	@RequestMapping("/product-save")
	public String saveproduct(@ModelAttribute Product product) {
		
		Category category = categoryDAO.getCategory(product.getCategory().getId());
	
		System.out.println("ANKA : " + product.getTags().toString());
				
		List<Integer> ids= new ArrayList<Integer>();
		
		for(Tag tag : product.getTags()) {
			ids.add(Integer.parseInt(tag.getName()));
		}
		
		List<Tag> tags = tagDAO.getTagsById(ids);
		product.setCategory(category);
		product.setTags(tags);
		
		
		productDAO.saveProduct(product);
		
		
		return "redirect:/administration/product-list";
	}
	
	@RequestMapping("/product-form-update")
	public String getProductFormUpdate(@RequestParam int id, Model model) {
		

		//Product product = productDAO.getProductWithTag(id);		
		Product product = productDAO.getProduct(id);
		List<Category> categoryList = categoryDAO.getCategoryList();
		List<Tag> tagList = tagDAO.getTagList();
		
		model.addAttribute("product", product);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("tagList", tagList);
				
		return "product-form";
	}
	@RequestMapping("/product-delete")
	public String getProductDelete(@RequestParam int id) {
		
		productDAO.deleteProduct(id);
		
		return "redirect://administration/product-list";
	}
	

}
