package com.haroo.employee.domain;

import lombok.Data;

@Data
public class EmployeeVO {

	private int em_no;// ��� ��ȣ
	private int po_no;// ��å ��ȣ
	private int de_no;// �μ� ��ȣ
	private String em_name;// ����̸�
	private String em_ext;// ���� ��ȭ
	private String em_phone;// �޴� ��ȭ
	private String em_email;// �̸���
	private String em_first;// �Ի���
	private String em_last;// �����
	private int au_no;// ���� ��ȣ
	private String em_pw;// ��й�ȣ


}
