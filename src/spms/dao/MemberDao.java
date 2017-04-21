package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MemberDao {
	Connection connection;
//	
//	public void setConnection(Connection connection){
//		this.connection = connection;
//	}
//	
//	DBConnectionPool connPool;
//	public void setConnectionPool(DBConnectionPool connPool){
//		this.connPool = connPool;
//	}
//	
	DataSource ds;
	public void setDataSource(DataSource ds){
		this.ds = ds;
	}
	public List<Member> selectList() throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
//			stmt = connPool.getConnection().createStatement();
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT MNO, MNAME, EMAIL, CRE_DATE"
					+ " FROM MEMBERS"
					+ " ORDER BY MNO ASC");
			
			ArrayList<Member> members = new ArrayList<>();
			
			while(rs.next()){
				members.add(new Member().setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE")));
			}
			
			return members;
		} catch (Exception e){
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e){}
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	public int insert(Member member) throws Exception{
		PreparedStatement stmt = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("insert into members(email, pwd, mname, cre_date, mod_date)"
					+ "values(?, ?, ?, now(), now())");
			stmt.setString(1,  member.getEmail());
			stmt.setString(2,  member.getPassword());
			stmt.setString(3,  member.getName());		
			return stmt.executeUpdate();		
		} finally{
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	public int delete(int no) throws Exception{
		PreparedStatement stmt = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("DELETE FROM MEMBERS"
					+ " WHERE MNO=?");
			stmt.setInt(1, no);
			
			return stmt.executeUpdate();
		} finally{
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	public Member selectOne(int no) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" + 
			" where MNO=" + Integer.toString(no));
			
			rs.next();			
			return new Member().setNo(rs.getInt("MNO"))
					.setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL"))
					.setCreatedDate(rs.getDate("CRE_DATE"));
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e){}
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}		
	}
	
	public int update(Member member) throws Exception{
		PreparedStatement stmt = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now()"
					+ " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			
			return stmt.executeUpdate();
		} finally {
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	public Member exist(String email, String password) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME, EMAIL FROM MEMBERS"
					+ " WHERE EMAIL=? AND PWD = ?");
	
			stmt.setString(1, email);
			stmt.setString(2, password);
		
			rs = stmt.executeQuery();
			
			Member member = null;
			if(rs.next()){
				member = new Member()
				.setEmail(rs.getString("EMAIL"))
				.setName(rs.getString("MNAME"));
			}
			return member;		
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e){}
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
}
