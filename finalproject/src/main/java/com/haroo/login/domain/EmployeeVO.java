package com.haroo.login.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeVO {

	private int emNo;// ��� ��ȣ
	private int poNo;// ��å ��ȣ
	private int deNo;// �μ� ��ȣ
	private String emName;// ����̸�
	private String emExt;// ���� ��ȭ
	private String emPhone;// �޴� ��ȭ
	private String emEmail;// �̸���
	private String emFirst;// �Ի���
	private String emLast;// �����
	private int auNo;// ���� ��ȣ
	private String emPw;// ��й�ȣ

}