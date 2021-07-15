package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;	
import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	 public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
		
	}
	 
			

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		
		
		try {
			st = conn.prepareStatement("insert into seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+"VALUES "
					+"(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4,obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
		//rows affected for know how many lines was changed;
			
			if(rowsAffected>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}	
			
				//resultSet with no  finally to close ResultSet from if condition;
				
				
					
				}
			
		
		else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}	
	
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	
		finally {
			DB.closeStatement(st);
			
		}
			

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletebyid(Integer id) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public Seller findbyid(Integer id) {
		
		PreparedStatement st=null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "from seller inner join department "
					+"ON seller.DepartmentId = Department.Id "
					+"where seller.Id = ? "
							);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
						Department dep = InstantiateDepartment(rs);
						Seller obj = InstatiateSeller(rs,dep);
						return obj;
						
						
		}
			return null;
			
	}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
			}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			}
		
	}
	
	private Seller InstatiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	public  Department InstantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
		 
	}

	
	@Override
	public List<Seller> findAll() {
		

		PreparedStatement st=null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " 
										+"from seller inner join department " 
										+"ON seller.DepartmentId = Department.Id "
									
										+"order by Name "
							);
			
			
		
			rs = st.executeQuery();
			List <Seller> list = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();  
			
			while(rs.next()) {
				//test if the object Department really exist:
				Department dep = map.get(rs.getInt("DepartmentId"));
				//include test//
				if(dep == null) {
					dep = InstantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				//instance for Department was created inside of the if//
				//inserted controller for avoid each object Seller create an object Department and 2 object seller point to
				//one department, creating map//
				//create a void map and keep any Departments there//
				//we will test every Department throw map.//
				Seller obj = InstatiateSeller(rs,dep);
				list.add(obj);
					
						
		}
			return list;
			
	}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
			}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			}
		
	}


	@Override
	public List<Seller> findByDepartment(Department department) {
			

		PreparedStatement st=null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " 
										+"from seller inner join department " 
										+"ON seller.DepartmentId = Department.Id "
										+"where DepartmentId = ? "
										+"order by Name "
							);
			
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			List <Seller> list = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();  
			
			while(rs.next()) {
				//test if the object Department really exist:
				Department dep = map.get(rs.getInt("DepartmentId"));
				//include test//
				if(dep == null) {
					dep = InstantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				//instance for Department was created inside of the if//
				//inserted controller for avoid each object Seller create an object Department and 2 object seller point to
				//one department, creating map//
				//create a void map and keep any Departments there//
				//we will test every Department throw map.//
				Seller obj = InstatiateSeller(rs,dep);
				list.add(obj);
					
						
		}
			return list;
			
	}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
			}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			}
	}

	
}	


