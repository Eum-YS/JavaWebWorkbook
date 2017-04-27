package spms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	Connection connection;
	DataSource ds;
	public void setDataSource(DataSource ds){
		this.ds = ds;
	}
	
	@Override
	public List<Project> selectList() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT PNO, PNAME, STA_DATE, END_DATE, STATE"
					+ " FROM PROJECTS"
					+ " ORDER BY PNO DESC");
			
			ArrayList<Project> projects = new ArrayList<>();
			
			while(rs.next()){
				projects.add(new Project().setNo(rs.getInt("PNO"))
						.setTitle(rs.getString("PNAME"))
						.setStartDate(rs.getDate("STA_DATE"))
						.setEndDate(rs.getDate("END_DATE"))
						.setState(rs.getInt("STATE")));
			}
			
			return projects;
		} catch (Exception e){
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e){}
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	@Override
	public int insert(Project project) throws Exception {
		PreparedStatement stmt = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("insert into projects(pname, content, sta_date, end_date, state, cre_date, tags)"
					+ "values(?, ?, ?, ?, 0, now(), ?)");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());
			
			return stmt.executeUpdate();		
		} finally{
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}

	@Override
	public Project selectOne(int no) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try{
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select PNO, PNAME, CONTENT, STA_DATE, END_DATE, STATE, TAGS FROM PROJECTS" + 
			" where PNO=" + Integer.toString(no));
			
			rs.next();
			return new Project().setNo(rs.getInt("PNO"))
					.setTitle(rs.getString("PNAME"))
					.setContent(rs.getString("CONTENT"))
					.setStartDate(rs.getDate("STA_DATE"))
					.setEndDate(rs.getDate("END_DATE"))
					.setState(rs.getInt("STATE"))
					.setTags(rs.getString("TAGS"));
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e){}
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	@Override
	public int update(Project project) throws Exception {
		PreparedStatement stmt = null;
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE PROJECTS SET PNAME=?, CONTENT=?, STA_DATE=?, END_DATE=?, STATE=?, TAGS=?"
					+ " WHERE PNO=?");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new Date(project.getStartDate().getTime()));
			stmt.setDate(4, new Date(project.getEndDate().getTime()));
			stmt.setInt(5, project.getState());
			stmt.setString(6, project.getTags());
			stmt.setInt(7, project.getNo());
			
			return stmt.executeUpdate();
		} finally {
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
	
	@Override
	public int delete(int no) throws Exception {
		PreparedStatement stmt = null;
	
		try{
			connection = ds.getConnection();
			stmt = connection.prepareStatement("DELETE FROM PROJECTS"
					+ " WHERE PNO=?");
			stmt.setInt(1, no);
			return stmt.executeUpdate();
					
		} finally {
			try{ if(stmt != null) stmt.close(); } catch(Exception e){}
			try{ if(connection != null) connection.close(); } catch(Exception e){}
		}
	}
}
