package test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import test.bean.ScoreVO;

@Repository
public class ScoreDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "C##jspexam";
	String password = "m1234";

	// sql
	String score_insert = "insert into score values (?, ?, ?, ?, ?, ?, ?, sysdate)";
	String score_update = "update score set name=?, kor=?, eng=?, mat=? , tot=? , avg=?" + " where studno=?";
	String score_delete = "delete score where studno=?";
	String score_get = "select * from score where studno=?";
	String score_list = "select * from score";

	public ScoreDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertScore(ScoreVO vo) {
		int result = 0;
		conn = getConnection();

		try {
			pstmt = conn.prepareStatement(score_insert);
			pstmt.setString(1, vo.getStudno());
			pstmt.setString(2, vo.getName());
			pstmt.setInt(3, vo.getKor());
			pstmt.setInt(4, vo.getEng());
			pstmt.setInt(5, vo.getMat());
			pstmt.setInt(6, vo.getTot());
			pstmt.setDouble(7, vo.getAvg());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// 출력
	public List<ScoreVO> ScoreList() {
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(score_list);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ScoreVO vo = new ScoreVO();
				vo.setStudno(rs.getString("studno"));
				vo.setName(rs.getString("name"));
				vo.setKor(rs.getInt("kor"));
				vo.setEng(rs.getInt("eng"));
				vo.setMat(rs.getInt("mat"));
				vo.setTot(rs.getInt("tot"));
				vo.setAvg(rs.getDouble("avg"));
				vo.setLogtime(rs.getString("logtime"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int updateScore(ScoreVO vo) {
		int result = 0;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(score_update);
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getKor());
			pstmt.setInt(3, vo.getEng());
			pstmt.setInt(4, vo.getMat());
			pstmt.setInt(5, vo.getTot());
			pstmt.setDouble(6, vo.getAvg());
			pstmt.setString(7, vo.getStudno());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// 삭제
	public int deleteScore(String studno) {
		int result = 0;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(score_delete);
			pstmt.setString(1, studno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// 상세보기 : 1명 데이터 확인
	public ScoreVO getScore(String studno) {
		ScoreVO vo = null;
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(score_get);
			pstmt.setString(1, studno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new ScoreVO();
				vo.setStudno(rs.getString("studno"));
				vo.setName(rs.getString("name"));
				vo.setKor(rs.getInt("kor"));
				vo.setEng(rs.getInt("eng"));
				vo.setMat(rs.getInt("mat"));
				vo.setTot(rs.getInt("tot"));
				vo.setAvg(rs.getDouble("avg"));
				vo.setLogtime(rs.getString("logtime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
	
	// 학번 중복 확인
		public boolean studnocheck(String studno) {
	        boolean check = false;
	        conn = getConnection();
	        try {
	            pstmt = conn.prepareStatement(score_get);
	            pstmt.setString(1, studno);
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	            	check = true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close();
	        }
	        return check;
	    }

}
