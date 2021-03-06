package com.haroo.approval.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

  private int page; // 현재페이지
  private int amount; // 페이지당 글개수
  
  private String type; // 검색범위
  private String keyword; // 검색어
  
  public Criteria() {
    this(1,10); // 디폴트
  }
  
  public Criteria(int page, int amount) {
    this.page = page;
    this.amount = amount;
  }
  
  public String[] getTypeArr() {
    
    return type == null ? new String[] {} : type.split("");
  }
}
