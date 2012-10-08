/**
 * 
 */
package com.yyq.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * @author YYQ
 * 泛型Dao上的Service标注
 */
@Inherited
@Service
@Target(ElementType.TYPE)
public @interface YYQService {
	
}
