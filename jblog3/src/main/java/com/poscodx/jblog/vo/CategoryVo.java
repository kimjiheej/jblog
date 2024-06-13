package com.poscodx.jblog.vo;

public class CategoryVo {

	private Long no;
	private String name;
	private String description;
	private String reg_date;
	private Long blog_id;
	

	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public Long getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(Long blog_id) {
		this.blog_id = blog_id;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", reg_date=" + reg_date
				+ ", blog_id=" + blog_id + "]";
	}

}
