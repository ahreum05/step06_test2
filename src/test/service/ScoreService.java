package test.service;

import java.util.List;

import test.bean.ScoreVO;

public interface ScoreService {
	public int insertScore(ScoreVO vo); 		// 성적 등록
	public int updateScore(ScoreVO vo); 		// 성적 수정
	public int deleteScore(String studno); 		// 성적 삭제
	public ScoreVO getScore(String studno); 	// 성적 1명 검색
	public List<ScoreVO> getScoreList(); 			//성적 목록 검색
	public boolean studnocheck(String studno); 	// 학번 중복 확인

}
