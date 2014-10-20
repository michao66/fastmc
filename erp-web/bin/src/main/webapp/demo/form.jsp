<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.inc.jsp"%>

<form class="form-horizontal form-bordered form-label-stripped form-validation" action="/prototype/auth/privilege!doSave"
	method="post">
	<input type="hidden" name="id" value="" id="id"/>
	<input type="hidden" name="version" value="0" id="version"/>
	<input type="hidden" name="struts.token.name" value="token" />
<input type="hidden" name="token" value="49489REIFT9JNJVILY8XPEYCLTRJP88M" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-privilege-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">分类</label>
					<div class="controls">
						<input type="text" name="category" value="" id="category"/>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">类型</label>
					<div class="controls">
						    
    <label class="radio-inline">
<input type="radio" name="type" id="typeURL" checked="checked" value="URL"/>			
			URL
	</label>
    
    <label class="radio-inline">
<input type="radio" name="type" id="typeBTN" value="BTN"/>			
			按钮
	</label>
    
    <label class="radio-inline">
<input type="radio" name="type" id="typeMENU" value="MENU"/>			
			菜单
	</label>

					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">代码</label>
					<div class="controls">
						<input type="text" name="code" value="" id="code"/>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">名称</label>
					<div class="controls">
						<input type="text" name="title" value="" id="title"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group tooltipster" title="排序号越大，则越先URL匹配">
					<label class="control-label">排序号</label>
					<div class="controls">
						<input type="text" name="orderRank" value="100" id="orderRank"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group tooltipster" title="可以一个权限关联多个URL,一行一个URL。系统默认按照左匹配规则验证，如/abc表示/abc**">
					<label class="control-label">匹配URL列表</label>
					<div class="controls">
						<textarea name="url" cols="" rows="3" id="url"></textarea>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
