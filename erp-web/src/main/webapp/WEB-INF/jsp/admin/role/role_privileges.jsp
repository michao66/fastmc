<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>

<form class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/auth/role/doUpdateRelatedPrivilegeR2s.action" method="post">
	<input type="hidden" name="id" value="${param.ID}"/>

	
	<div class="form-actions">
		<button class="btn blue" type="submit">
			<i class="icon-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<table class="table table-striped table-advance table-bordered table-hover">
			<thead>
				<tr>
					<th><input type="checkbox"
						onclick="$(this).closest('table').find('tbody :checkbox').attr('checked',this.checked)" /> 权限分类</th>
					<th>角色代码</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${privileges}">
					<tr>
						<td class="active" style="width: 150px"><input type="checkbox"
							onclick="$(this).closest('tr').find('.checkbox-privileges :checkbox').attr('checked',this.checked)" />
							 ${item.key}
						</td>
						<td class="checkbox-privileges">
						      <c:forEach var="child" items="${item.value}">
								<div class="col-md-3 ${child.extraAttributes.related?'text-success':''}">
								<label class="checkbox-inline">	 <input type="checkbox" name="r2ids" value="${child.id}" <c:if test="${child.extraAttributes.related}">"checked=checked"</c:if>> ${child.title}</label>
								</div>
							</c:forEach>
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit">
			<i class="icon-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
