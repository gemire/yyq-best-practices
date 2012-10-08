package com.yyq.dao;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author YYQ
 * 定义Hibernate实体类Id基类，
 * 使用统一的主键生成策略。
 * MappedSuperclass标注是必须的。
 * 所有hibernate实体类继承该类，以统一各表的主键及主键生成方式。
 * 实体类先从数据库里导出，再将其继承自该类。
 * 另外，用MyEclipse Hibernate从数据库导出实体类时就可以设置主键生成方式了,
 * 如要使用32位UUID的话选择uuid.hex。
 * 2010-9-27添加：
 * 使用自己的主键生成器，原先是11位的16进制当前时间+11位的16进制系统纳秒级时间+10位16进制随机数，
 * 后来觉得后面的10位16进制随机数不加也不会造成主键的重复，所以去掉了，
 * 现在的主键生成策略是：11位的16进制当前时间+11位的16进制系统纳秒级时间，长度为22位。
 */
@MappedSuperclass
public abstract class IdEntity {
	private String id;
	@Id
	@GeneratedValue(generator="myIdGenerator")
	@GenericGenerator(name="myIdGenerator", strategy="com.yyq.orm.IdGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 22)
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
