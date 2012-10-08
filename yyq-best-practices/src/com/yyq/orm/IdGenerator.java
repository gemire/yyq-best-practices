package com.yyq.orm;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 自定义主键生成器，
 * 策略：11位的16进制当前时间+11位的16进制系统纳秒级时间，长度为22位。
 * @author YYQ
 */
public class IdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		StringBuffer sb = new StringBuffer();
		sb.append(Long.toHexString(System.currentTimeMillis()));
		sb.append(Long.toHexString(System.nanoTime()));
		return sb.toString();
	}
}
