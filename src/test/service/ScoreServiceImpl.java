package test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.bean.ScoreVO;
import test.dao.ScoreDAO;

@Service("service")
public class ScoreServiceImpl implements ScoreService{
	@Autowired
	ScoreDAO dao;
	
	@Override
	public int insertScore(ScoreVO vo) {
		return dao.insertScore(vo);
	}

	@Override
	public int updateScore(ScoreVO vo) {
		return dao.updateScore(vo);
	}

	@Override
	public int deleteScore(String studno) {
		return dao.deleteScore(studno);
	}

	@Override
	public ScoreVO getScore(String studno) {
		return dao.getScore(studno);
	}

	@Override
	public List<ScoreVO> getScoreList() {
		return dao.ScoreList();
	}

	@Override
	public boolean studnocheck(String studno) {
		return dao.studnocheck(studno);
	}

}
