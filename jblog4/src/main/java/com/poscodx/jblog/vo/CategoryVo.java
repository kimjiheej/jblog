package com.poscodx.jblog.vo;

public class CategoryVo {

	private Long no;
	private String name;
	private String description;
	private String reg_date;
	private String id;
	
	private Long postCount;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPostCount() {
		return postCount;
	}

	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}

	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", reg_date=" + reg_date
				+ ", id=" + id + ", postCount=" + postCount + "]";
	}
	
	
	
	

}
