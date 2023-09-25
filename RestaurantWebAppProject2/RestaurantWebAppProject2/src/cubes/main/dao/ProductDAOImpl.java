package cubes.main.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cubes.main.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public List<Product> getProductList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Product> query = session.createQuery("from Product", Product.class);
		
		List<Product> productList = query.getResultList();
		
		return productList;
	}

	@Transactional
	@Override
	public void saveProduct(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(product);
		
	}

	@Transactional
	@Override
	public void deleteProduct(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Product where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
		
	}

	@Transactional
	@Override
	public Product getProduct(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Product product = session.get(Product.class, id);
		
		return product;
	}
	
	@Transactional
	@Override
	public Product getProductWithTag(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Product product = session.get(Product.class, id);
		Hibernate.initialize(product.getTags());
		
		return product;
	}
	
	
}
