<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/auth/user/doUpdateRelatedRoleR2s.action" method="post">
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
					<th class="table-checkbox"><input type="checkbox"
						onclick="$(this).closest('table').find('tbody .table-checkbox :checkbox').attr('checked',this.checked)" /></th>
					<th>角色名称</th>
					<th>角色代码</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${roles}">
					<tr class='select-table-checkbox ${item.extraAttributes.related?'text-primary':''}' >
						<td class="table-checkbox">
						<input type="checkbox" name="r2ids" value="${item.id}" <c:if test="${item.extraAttributes.related}">checked="checked"</c:if>>
					   </td>
						<td>${item.title}</td>
						<td>${item.code}</td>
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