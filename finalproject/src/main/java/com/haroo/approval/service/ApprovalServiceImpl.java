package com.haroo.approval.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haroo.approval.domain.ApprovalAttachVO;
import com.haroo.approval.domain.ApprovalLineVO;
import com.haroo.approval.domain.ApprovalVO;
import com.haroo.approval.domain.Criteria;
import com.haroo.approval.domain.EmpVO;
import com.haroo.approval.domain.FormVO;
import com.haroo.approval.domain.LeaveVO;
import com.haroo.approval.mapper.ApprovalAttachMapper;
import com.haroo.approval.mapper.ApprovalLineMapper;
import com.haroo.approval.mapper.ApprovalMapper;
import com.haroo.approval.mapper.ExpenseListMapper;
import com.haroo.approval.mapper.FormMapper;
import com.haroo.approval.mapper.LeaveMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ApprovalServiceImpl implements ApprovalService {
  
  @Autowired
  private ApprovalMapper mapper;
  
  @Autowired
  private ApprovalLineMapper lineMapper;
  
  @Autowired
  private LeaveMapper leaveMapper;
  
  @Autowired
  private ExpenseListMapper expenseMapper;
  
  @Autowired
  private ApprovalAttachMapper attachMapper;
  
  @Autowired
  private FormMapper formMapper;

  @Transactional
  @Override
  public void insertApproval(ApprovalVO approval) { // 상신
    
    log.info("insert approval...............");
    
    mapper.insert(approval);
    
    int foNo = approval.getFoNo();
    
    int apNo = approval.getApNo();
    
    approval.getLine().forEach(line -> {
      line.setApNo(apNo);
      lineMapper.insert(line);
    });
    
    
    if(foNo == 2) { // 품의서
      
      approval.getExpense().forEach(ex -> {
        ex.setApNo(apNo);
        expenseMapper.insert(ex);
      });
      
    } else if(foNo == 3) { // 휴가신청서
      
      LeaveVO leave = approval.getLeave();
      leave.setApNo(apNo);
      leave.setFoNo(3);
      leave.setLeTitle(approval.getApTitle());
      
      leaveMapper.insert(leave);
    }
    
    List<ApprovalAttachVO> attachs = approval.getAttachList();
    
    if(attachs == null || attachs.size() <= 0) {
      return;
    }
    
    attachs.forEach(attach -> {
      attach.setApNo(apNo);
      attachMapper.insert(attach);
    });
    
  }
  
  @Override
  public List<ApprovalVO> getReportList(int emNo, int status) { // 상신문서
    
    log.info("get report list...............status: " + status);
    
    return mapper.getReportList(emNo, status);
  }

  @Override
  public List<ApprovalVO> getReceiveList(int emNo, int status) { // 수신문서
    
    log.info("get receive list...............status: " + status);
    
    return mapper.getReceiveList(emNo, status);
  }
  
  @Override
  public List<ApprovalVO> getAllList() { // 전체문서
    
    log.info("get All list...............");
    
    return mapper.getAllList();
  }
  
  @Override
  public ApprovalVO readApproval(int apNo) { // 읽기
    
    log.info("read approval...............");
    
    ApprovalVO approval = mapper.read(apNo);
    approval.setLine(lineMapper.read(apNo));
    
    int foNo = approval.getFoNo();
    
    if(foNo == 2) {
      approval.setExpense(expenseMapper.read(apNo));
    } else if(foNo == 3) {
      approval.setLeave(leaveMapper.read(apNo));
    }
    
    approval.setAttachList(attachMapper.findByApNo(apNo));
    
    
    return approval;
  }



  @Override
  public Map<String, List<EmpVO>> getEmpList() { // 사원목록
    
    log.info("get employee list...............");
    
    List<EmpVO> list = lineMapper.getEmpList();
    
    Map<String, List<EmpVO>> map = new HashMap<String, List<EmpVO>>();
    
    list.forEach(emp -> {
      
      if(!map.containsKey(emp.getDeName())){
        map.put(emp.getDeName(), new ArrayList<EmpVO>());
        map.get(emp.getDeName()).add(emp);
      } else {
        map.get(emp.getDeName()).add(emp);
      }
    });
    
    return map;
  }

  @Transactional
  @Override
  public boolean sign(ApprovalLineVO apLine, int foNo) { // 결재하기
    
    log.info("sign...............");
    
    lineMapper.sign(apLine);
    mapper.updateStatus(apLine);
    
    int apNo = apLine.getApNo();
    
    ApprovalVO approval = mapper.read(apNo);
    
    int status = approval.getApStatus();
    
    if(foNo == 3) {
      leaveMapper.updateStatus(apNo, status);
    }
    
    if(status == 2) {
      return attachMapper.deleteAll(apNo) > 0;
    }
    
    return false;
  }

  @Transactional
  @Override
  public boolean takeback(int apNo, int foNo) { // 상신취소
    
    log.info("takeback..............." + apNo);
    
    attachMapper.deleteAll(apNo);
    
    if(foNo == 3) {
      leaveMapper.takeback(apNo);
    }
    
    return mapper.takeback(apNo) == 1;
    
  }

  @Override
  public List<ApprovalAttachVO> getAttachList(int apNo) {
    
    log.info("get attach List by apNo......"+apNo);
    
    return attachMapper.findByApNo(apNo);
  }

  @Override
  public List<ApprovalVO> getReportList(Criteria cri, int emNo, int status) {
    
    log.info("get Report List with Paging......" + cri);
    
    return mapper.getReportListWithPaging(cri, emNo, status);
  }

  @Override
  public int getReportTotal(Criteria cri, int emNo, int status) {
    
    log.info("get Report Count......");
    
    return mapper.getReportCount(cri, emNo, status);
        
  }

  @Override
  public int getReceiveTotal(Criteria cri, int emNo, int status) {
    
    log.info("get Receive Count......");
    
    return mapper.getReceiveCount(cri, emNo, status);
  }

  @Override
  public int getAllTotal(Criteria cri) {
    
    log.info("get All Count......");
    
    return mapper.getAllCount(cri);
  }

  @Override
  public List<ApprovalVO> getReceiveList(Criteria cri, int emNo, int status) {
    
    log.info("get Receive List with Paging......" + cri);
    
    return mapper.getReceiveListWithPaging(cri, emNo, status);
  }

  @Override
  public List<ApprovalVO> getAllList(Criteria cri) {
    
    log.info("get All List with Paging......" + cri);
    
    return mapper.getAllListWithPaging(cri);
  }

  @Override
  public int insertForm(FormVO form) {
    
    log.info("insert form......" + form);
    return formMapper.insert(form);
  }

  @Override
  public FormVO readForm(int foNo) {
    
    log.info("get form......" + foNo);
    
    return formMapper.read(foNo);
  }

  @Override
  public List<FormVO> getFormList() {
    
    log.info("get form list......");
    
    return formMapper.getList();
  }

  @Override
  public int modifyForm(FormVO form) {
    
    log.info("modify form......"+form.getFoNo());
    
    return formMapper.modify(form);
  }



}
