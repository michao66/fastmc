/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.fastmc.core.jpa.specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fastmc.core.RestrictionNames;
import cn.fastmc.core.jpa.specification.support.PropertyFilterSpecification;
import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter.MatchType;

/**
 * Specification工具类,帮助通过PropertyFilter和属性名创建Specification
 * 
 * @author maurice
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Specifications {
	
	/**
	 * 通过属性过滤器集合，创建Specification
	 * 
	 * @param filters 属性过滤器集合
	 * 
	 * @return {@link Specification}
	 */
	public static Specification get(GroupPropertyFilter filters) {
		return new PropertyFilterSpecification(filters);
	}


	
	
	
    /**
	 * 通过类属性名称，创建Specification
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * 
	 * @return {@link Specification}
	 */
	public static Specification get(String propertyName,Object value) {
		return get(propertyName, value,MatchType.EQ);
	}
	
	/**
	 * 通过类属性名称，创建Specification
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * @param restrictionName 约束条件名称，{@link RestrictionNames}
	 * 
	 * @return {@link Specification}
	 */
	public static Specification get(String propertyName, Object value,MatchType restrictionName) {
		return new PropertyFilterSpecification(propertyName, value,restrictionName);
	}

	/**
	 * 获取属性名字路径
	 * 
	 * @param propertyName 属性名
	 * @param root Query roots always reference entities
	 * 
	 * @return {@link Path}
	 */
	public static Path<?> getPath(String propertyName,Root<?> root) {
		
		Path<?> path = null;
		
		if (StringUtils.contains(propertyName, ".")) {
			String[] propertys = StringUtils.splitByWholeSeparator(propertyName, ".");
			path = root.get(propertys[0]);
			for (int i = 1; i < propertys.length; i++) {
				path = path.get(propertys[i]);
			}
		} else {
			path = root.get(propertyName);
		}
		
		return path;
	}
	
}
