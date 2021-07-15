package application;


import java.util.List;
import java.util.Date;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class ProgramMain {

	public static void main(String[] args) {
		
		
		SellerDao sellerDao = DaoFactory.CreateSellerDao();
		
		System.out.println("==== TEST 1: seller findById =======");
		Seller seller  = sellerDao.findbyid(3);
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment=====");
		Department department = new Department(2,null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for(Seller obj:list) {
			System.out.println(obj);
		}
	
		System.out.println("\n=== TEST 3: seller findAll=====");
		list = sellerDao.findAll();
		for(Seller obj:list) {
			System.out.println(obj);
	}
		System.out.println("\n=== TEST 4: insert Seller=====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com",new Date(),4000.00,department);
		sellerDao.insert(newSeller);		
		System.out.println("Inserted! new id=" + newSeller.getId());
	

}
}


