package com.haroo.board.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haroo.board.domain.BoardVO;
import com.haroo.board.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testInsert() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("���� �ۼ��ϴ� ��");
		board.setContents("���� �ۼ��ϴ� ����");
		board.setWriter("newbie");
		board.setEmNo(19362300);
		
		mapper.insert(board);
		
		log.info(board);
	}

	@Test
	public void testInsertSelectKey() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("���� �ۼ��ϴ� �� select key");
		board.setContents("���� �ۼ��ϴ� ���� select key");
		board.setWriter("�ڹ���");
		board.setEmNo(19362300);
		
		mapper.insertSelectKey(board);
		
		log.info(board);
	}
	
	@Test
	public void testRead() {
		
		BoardVO board = mapper.read(81L);
		log.info(board);
	}
	
	@Test
	public void testDelete() {
		log.info("DELETE COUNT : " + mapper.delete(3L));
	}
	
	@Test
	public void testUpdate() {
		
		BoardVO board = new BoardVO();
		
		board.setBdNo(5L);
		board.setTitle("������ ����");
		board.setContents("������ ����");
		board.setWriter("�ڹ���");
		board.setEmNo(19362300);
		
		int count = mapper.update(board);
		
		log.info(board);
	}
	
	@Test
	public void testPaging() {
		
		Criteria cri = new Criteria();
		cri.setPageNum(3);
		cri.setAmount(10);
		List<BoardVO> list = mapper.getListWithPaging(cri);
		
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		
		Criteria cri = new Criteria();
		cri.setKeyword("����");
		cri.setType("TC");
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testHitCount() {
		
		log.info(mapper.hitCount(109L));
	}
	
}
