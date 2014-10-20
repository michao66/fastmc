package cn.fastmc.core.jpa.specification.support;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter.MatchType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 实现spring data jpa的{@link Specification}接口，通过该类支持{@link PropertyFilter}
 * 以及表达式查询方法
 * 
 * @author maurice
 *
 * @param <T>
 *            orm对象
 */
public class PropertyFilterSpecification<T> implements Specification<T> {
	 private final Logger logger = LoggerFactory.getLogger(PropertyFilterSpecification.class);
	private GroupPropertyFilter filters;

	public PropertyFilterSpecification() {

	}

	/**
	 * 通过属性过滤器集合构建
	 * 
	 * @param filters
	 *            集合
	 */
	public PropertyFilterSpecification(GroupPropertyFilter filters) {
		this.filters = filters;
	}

	public PropertyFilterSpecification(String propertyName,Object value,MatchType restrictionName){
		GroupPropertyFilter filter = GroupPropertyFilter.buildDefaultOrGroupFilter();
		filter.append(new PropertyFilter(MatchType.BW, propertyName,value));
	    this.filters = filter ;
	}
	

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
            return buildPredicatesFromFilters(filters,root,query,builder,false);   
	}

	protected Predicate buildPredicatesFromFilters(GroupPropertyFilter groupPropertyFilter,Root root,CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having) {
		if (groupPropertyFilter == null) {
			return null;
		}
		List<Predicate> predicates = buildPredicatesFromFilters(groupPropertyFilter.getFilters(), root, query, builder, having);
		if (CollectionUtils.isNotEmpty(groupPropertyFilter.getGroups())) {
			for (GroupPropertyFilter group : groupPropertyFilter.getGroups()) {
				if (CollectionUtils.isEmpty(group.getFilters())
						&& CollectionUtils.isEmpty(group.getForceAndFilters())) {
					continue;
				}
				Predicate subPredicate = buildPredicatesFromFilters(group,root, query, builder, having);
				if (subPredicate != null) {
					predicates.add(subPredicate);
				}
			}
		}
		Predicate predicate = null;
		if (CollectionUtils.isNotEmpty(predicates)) {
			if (predicates.size() == 1) {
				predicate = predicates.get(0);
			} else {
				if (groupPropertyFilter.getGroupType().equals(
						GroupPropertyFilter.GROUP_OPERATION_OR)) {
					predicate = builder.or(predicates
							.toArray(new Predicate[predicates.size()]));
				} else {
					predicate = builder.and(predicates
							.toArray(new Predicate[predicates.size()]));
				}
			}
		}

		List<Predicate> appendAndPredicates = buildPredicatesFromFilters(groupPropertyFilter.getForceAndFilters(), root, query, builder, having);
		if (CollectionUtils.isNotEmpty(appendAndPredicates)) {
			if (predicate != null) {
				appendAndPredicates.add(predicate);
			}
			predicate = builder.and(appendAndPredicates
					.toArray(new Predicate[appendAndPredicates.size()]));
		}

		return predicate;
	}
	
	 /**
     * 根据条件集合对象组装JPA规范条件查询集合对象，基类默认实现进行条件封装组合
     * 子类可以调用此方法在返回的List<Predicate>额外追加其他PropertyFilter不易表单的条件如exist条件处理等
     * 
     * @param filters
     * @param root
     * @param query
     * @param builder
     * @return
     */
    private <X> List<Predicate> buildPredicatesFromFilters(final Collection<PropertyFilter> filters, Root<X> root,
            CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having) {
        List<Predicate> predicates = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(filters)) {
            for (PropertyFilter filter : filters) {
                if (!filter.hasMultiProperties()) { // 只有一个属性需要比较的情况.
                    Predicate predicate = buildPredicate(filter.getPropertyName(), filter, root, query, builder, having);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                } else {// 包含多个属性需要比较的情况,进行or处理.
                    List<Predicate> orpredicates = Lists.newArrayList();
                    for (String param : filter.getPropertyNames()) {
                        Predicate predicate = buildPredicate(param, filter, root, query, builder, having);
                        if (predicate != null) {
                            orpredicates.add(predicate);
                        }
                    }
                    if (orpredicates.size() > 0) {
                        predicates.add(builder.or(orpredicates.toArray(new Predicate[orpredicates.size()])));
                    }
                }
            }
        }
        return predicates;
    }
    @SuppressWarnings("unchecked")
    private <X> Predicate buildPredicate(String propertyName, PropertyFilter filter, Root<X> root,
            CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having) {
        Object matchValue = filter.getMatchValue();
        String[] names = StringUtils.split(propertyName, ".");

        if (matchValue == null) {
            return null;
        }
        if (having && propertyName.indexOf("(") == -1) {
            return null;
        }
        if (!having && propertyName.indexOf("(") > -1) {
            return null;
        }
        if (matchValue instanceof String) {
            if (StringUtils.isBlank(String.valueOf(matchValue))) {
                return null;
            }
        }

        if (filter.getMatchType().equals(MatchType.FETCH)) {
            if (names.length == 1) {
                JoinType joinType = JoinType.INNER;
                if (matchValue instanceof String) {
                    joinType = Enum.valueOf(JoinType.class, (String) matchValue);
                } else {
                    joinType = (JoinType) filter.getMatchValue();
                }

                // Hack for Bug: https://jira.springsource.org/browse/DATAJPA-105
                // 如果是在count计算总记录，则添加join；否则说明正常分页查询添加fetch
                if (!Long.class.isAssignableFrom(query.getResultType())) {
                    root.fetch(names[0], joinType);
                } else {
                    root.join(names[0], joinType);
                }
            } else {
                JoinType[] joinTypes = new JoinType[names.length];
                if (matchValue instanceof String) {
                    String[] joinTypeSplits = StringUtils.split(String.valueOf(matchValue), ".");
                    Assert.isTrue(joinTypeSplits.length == names.length, filter.getMatchType() + " 操作属性个数和Join操作个数必须一致");
                    for (int i = 0; i < joinTypeSplits.length; i++) {
                        joinTypes[i] = Enum.valueOf(JoinType.class, joinTypeSplits[i].trim());
                    }
                } else {
                    joinTypes = (JoinType[]) filter.getMatchValue();
                    Assert.isTrue(joinTypes.length == names.length);
                }

                // Hack for Bug: https://jira.springsource.org/browse/DATAJPA-105
                // 如果是在count计算总记录，则添加join；否则说明正常分页查询添加fetch
                if (!Long.class.isAssignableFrom(query.getResultType())) {
                    Fetch fetch = root.fetch(names[0], joinTypes[0]);
                    for (int i = 1; i < names.length; i++) {
                        fetch.fetch(names[i], joinTypes[i]);
                    }
                } else {
                    Join join = root.join(names[0], joinTypes[0]);
                    for (int i = 1; i < names.length; i++) {
                        join.join(names[i], joinTypes[i]);
                    }
                }
            }

            return null;
        }

        Predicate predicate = null;
        Expression expression = null;

        // 处理集合子查询
        Subquery<Long> subquery = null;
        Root subQueryFrom = null;
        if (filter.getSubQueryCollectionPropetyType() != null) {
            subquery = query.subquery(Long.class);
            subQueryFrom = subquery.from(filter.getSubQueryCollectionPropetyType());
            Path path = subQueryFrom.get(names[1]);
            if (names.length > 2) {
                for (int i = 2; i < names.length; i++) {
                    path = path.get(names[i]);
                }
            }
            expression = (Expression) path;
        } else {
            expression = buildExpression(root, builder, propertyName, null);
        }

        if ("NULL".equalsIgnoreCase(String.valueOf(matchValue))) {
            return expression.isNull();
        } else if ("EMPTY".equalsIgnoreCase(String.valueOf(matchValue))) {
            return builder.or(builder.isNull(expression), builder.equal(expression, ""));
        } else if ("NONULL".equalsIgnoreCase(String.valueOf(matchValue))) {
            return expression.isNotNull();
        } else if ("NOEMPTY".equalsIgnoreCase(String.valueOf(matchValue))) {
            return builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
        }

        // logic operator
        switch (filter.getMatchType()) {
        case EQ:
            // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
            // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
            if (matchValue instanceof Date) {
                DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0
                        && dateTime.getSecondOfMinute() == 0) {
                    return builder.and(builder.greaterThanOrEqualTo(expression, dateTime.toDate()),
                            builder.lessThan(expression, dateTime.plusDays(1).toDate()));
                }
            }
            predicate = builder.equal(expression, matchValue);
            break;
        case NE:
            // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
            // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
            if (matchValue instanceof Date) {
                DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0
                        && dateTime.getSecondOfMinute() == 0) {
                    return builder.or(builder.lessThan(expression, dateTime.toDate()),
                            builder.greaterThanOrEqualTo(expression, dateTime.plusDays(1).toDate()));
                }
            }
            predicate = builder.notEqual(expression, matchValue);
            break;
        case BK:
            predicate = builder.or(builder.isNull(expression), builder.equal(expression, ""));
            break;
        case NB:
            predicate = builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
            break;
        case NU:
            if (matchValue instanceof Boolean && (Boolean) matchValue == false) {
                predicate = builder.isNotNull(expression);
            } else {
                predicate = builder.isNull(expression);
            }
            break;
        case NN:
            if (matchValue instanceof Boolean && (Boolean) matchValue == false) {
                predicate = builder.isNull(expression);
            } else {
                predicate = builder.isNotNull(expression);
            }
            break;
        case CN:
            predicate = builder.like(expression, "%" + matchValue + "%");
            break;
        case NC:
            predicate = builder.notLike(expression, "%" + matchValue + "%");
            break;
        case BW:
            predicate = builder.like(expression, matchValue + "%");
            break;
        case BN:
            predicate = builder.notLike(expression, matchValue + "%");
            break;
        case EW:
            predicate = builder.like(expression, "%" + matchValue);
            break;
        case EN:
            predicate = builder.notLike(expression, "%" + matchValue);
            break;
        case BT:
            Assert.isTrue(matchValue.getClass().isArray(), "Match value must be array");
            Object[] matchValues = (Object[]) matchValue;
            Assert.isTrue(matchValues.length == 2, "Match value must have two value");
            // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
            // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
            if (matchValues[0] instanceof Date) {
                DateTime dateFrom = new DateTime(((Date) matchValues[0]).getTime());
                DateTime dateTo = new DateTime(((Date) matchValues[1]).getTime());
                if (dateFrom.getHourOfDay() == 0 && dateFrom.getMinuteOfHour() == 0
                        && dateFrom.getSecondOfMinute() == 0) {
                    return builder.and(builder.greaterThanOrEqualTo(expression, dateFrom.toDate()),
                            builder.lessThan(expression, dateTo.plusDays(1).toDate()));

                }
            } else {
                return builder.between(expression, (Comparable) matchValues[0], (Comparable) matchValues[1]);
            }
            predicate = builder.equal(expression, matchValue);
            break;
        case GT:
            predicate = builder.greaterThan(expression, (Comparable) matchValue);
            break;
        case GE:
            predicate = builder.greaterThanOrEqualTo(expression, (Comparable) matchValue);
            break;
        case LT:
            // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
            // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
            if (matchValue instanceof Date) {
                DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0
                        && dateTime.getSecondOfMinute() == 0) {
                    return builder.lessThan(expression, dateTime.plusDays(1).toDate());
                }
            }
            predicate = builder.lessThan(expression, (Comparable) matchValue);
            break;
        case LE:
            // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
            // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
            if (matchValue instanceof Date) {
                DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0
                        && dateTime.getSecondOfMinute() == 0) {
                    return builder.lessThan(expression, dateTime.plusDays(1).toDate());
                }
            }
            predicate = builder.lessThanOrEqualTo(expression, (Comparable) matchValue);
            break;
        case IN:
            if (matchValue.getClass().isArray()) {
                predicate = expression.in((Object[]) matchValue);
            } else if (matchValue instanceof Collection) {
                predicate = expression.in((Collection) matchValue);
            } else {
                predicate = builder.equal(expression, matchValue);
            }
            break;
        case ACLPREFIXS:
            List<Predicate> aclPredicates = Lists.newArrayList();
            Collection<String> aclCodePrefixs = (Collection<String>) matchValue;
            for (String aclCodePrefix : aclCodePrefixs) {
                if (StringUtils.isNotBlank(aclCodePrefix)) {
                    aclPredicates.add(builder.like(expression, aclCodePrefix + "%"));
                }

            }
            if (aclPredicates.size() == 0) {
                return null;
            }
            predicate = builder.or(aclPredicates.toArray(new Predicate[aclPredicates.size()]));
            break;
        default:
            break;
        }

        //处理集合子查询
        if (filter.getSubQueryCollectionPropetyType() != null) {
            //String owner = StringUtils.uncapitalize(entityClass.getSimpleName());
            //subQueryFrom.join(owner);
            //subquery.select(subQueryFrom.get(owner)).where(predicate);
            //predicate = builder.in(root.get("id")).value(subquery);
        }

        Assert.notNull(predicate, "Undefined match type: " + filter.getMatchType());
        return predicate;
    }
    

    private Expression<?> buildExpression(Root<?> root, CriteriaBuilder criteriaBuilder, String name, String alias) {
        Expression<?> expr = parseExpr(root, criteriaBuilder, name, null);
        if (alias != null) {
            expr.alias(alias);
        }
        return expr;
    }

    private Expression parseExpr(Root<?> root, CriteriaBuilder criteriaBuilder, String expr,
            Map<String, Expression<?>> parsedExprMap) {
        if (parsedExprMap == null) {
            parsedExprMap = Maps.newHashMap();
        }
        Expression<?> expression = null;
        if (expr.indexOf("(") > -1) {
            int left = 0;
            char[] chars = expr.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(') {
                    left = i;
                }
            }
            String leftStr = expr.substring(0, left);
            String op = null;
            char[] leftStrs = leftStr.toCharArray();
            for (int i = leftStrs.length - 1; i > 0; i--) {
                if (leftStrs[i] == '(' || leftStrs[i] == ')' || leftStrs[i] == ',') {
                    op = leftStr.substring(i + 1);
                    break;
                }
            }
            if (op == null) {
                op = leftStr;
            }
            String rightStr = expr.substring(left + 1);
            String arg = StringUtils.substringBefore(rightStr, ")");
            String[] args = arg.split(",");
            //logger.debug("op={},arg={}", op, arg);
            if (op.equalsIgnoreCase("case")) {
                Case selectCase = criteriaBuilder.selectCase();

                Expression caseWhen = parsedExprMap.get(args[0]);

                String whenResultExpr = args[1];
                Object whenResult = parsedExprMap.get(whenResultExpr);
                if (whenResult == null) {
                    Case<Long> whenCase = selectCase.when(caseWhen, new BigDecimal(whenResultExpr));
                    selectCase = whenCase;
                } else {
                    Case<Expression<?>> whenCase = selectCase.when(caseWhen, whenResult);
                    selectCase = whenCase;
                }
                String otherwiseResultExpr = args[2];
                Object otherwiseResult = parsedExprMap.get(otherwiseResultExpr);
                if (otherwiseResult == null) {
                    expression = selectCase.otherwise(new BigDecimal(otherwiseResultExpr));
                } else {
                    expression = selectCase.otherwise((Expression<?>) otherwiseResult);
                }
            } else {
                Object[] subExpressions = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    subExpressions[i] = parsedExprMap.get(args[i]);
                    if (subExpressions[i] == null) {
                        String name = args[i];
                        try {
                            Path<?> item = null;
                            if (name.indexOf(".") > -1) {
                                String[] props = StringUtils.split(name, ".");
                                item = root.get(props[0]);
                                for (int j = 1; j < props.length; j++) {
                                    item = item.get(props[j]);
                                }
                            } else {
                                item = root.get(name);
                            }
                            subExpressions[i] = (Expression) item;
                        } catch (Exception e) {
                            subExpressions[i] = new BigDecimal(name);
                        }
                    }
                }
                try {
                    //criteriaBuilder.quot();
                    expression = (Expression) MethodUtils.invokeMethod(criteriaBuilder, op, subExpressions);
                } catch (Exception e) {
                    logger.error("Error for aggregate  setting ", e);
                }
            }

            String exprPart = op + "(" + arg + ")";
            String exprPartConvert = exprPart.replace(op + "(", op + "_").replace(arg + ")", arg + "_")
                    .replace(",", "_");
            expr = expr.replace(exprPart, exprPartConvert);
            parsedExprMap.put(exprPartConvert, expression);

            if (expr.indexOf("(") > -1) {
                expression = parseExpr(root, criteriaBuilder, expr, parsedExprMap);
            }
        } else {
            String name = expr;
            Path<?> item = null;
            if (name.indexOf(".") > -1) {
                String[] props = StringUtils.split(name, ".");
                item = root.get(props[0]);
                for (int j = 1; j < props.length; j++) {
                    item = item.get(props[j]);
                }
            } else {
                item = root.get(name);
            }
            expression = item;
        }
        return expression;
    }

}
