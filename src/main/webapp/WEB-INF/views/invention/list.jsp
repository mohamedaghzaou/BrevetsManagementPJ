<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/WEB-INF/jspf/list-toolbar.jsp">
	<jsp:param name="addHref" value="?mode=adding" />
	<jsp:param name="printHref" value="ReprotsController?filename=RptInvention" />
</jsp:include>
<c:if test="${status == 'deleted'}">
	<div class="alert alert-success" role="alert" data-toast="true"><fmt:message key="invention.deleted.success" bundle="${i18n}" /></div>
</c:if>
<c:if test="${status == 'updated'}">
	<div class="alert alert-success" role="alert" data-toast="true"><fmt:message key="invention.update.success" bundle="${i18n}" /></div>
</c:if>
<c:if test="${not empty globalError}">
	<div class="alert alert-danger" role="alert" data-toast="true">${globalError}</div>
</c:if>

<div class="d-flex justify-content-end align-items-center mb-2">
	<label for="inventionSort" class="mb-0 mr-2 text-muted small"><fmt:message key="filter.sortBy" bundle="${i18n}" /></label>
	<select id="inventionSort" class="form-control form-control-sm w-auto js-card-sort" data-target="#inventionCardList">
		<option value=""><fmt:message key="filter.sortBy" bundle="${i18n}" /></option>
		<option value="description"><fmt:message key="field.description" bundle="${i18n}" /></option>
		<option value="resume"><fmt:message key="field.summary" bundle="${i18n}" /></option>
		<option value="domaine"><fmt:message key="nav.domaine" bundle="${i18n}" /></option>
	</select>
</div>

<div class="row inventions" id="inventionCardList">
	<c:if test="${empty inventions}">
		<div class="col-12">
			<div class="card p-4 text-center shadow-sm">
				<h6 class="mb-2"><fmt:message key="invention.empty.title" bundle="${i18n}" /></h6>
				<p class="text-muted mb-0"><fmt:message key="invention.empty.subtitle" bundle="${i18n}" /></p>
			</div>
		</div>
	</c:if>
	<c:forEach items="${inventions}" var="i">
		<div class="invention col-lg-6" data-card-item="true" data-sort-description="${fn:escapeXml(i.descriptif)}" data-sort-resume="${fn:escapeXml(i.resume)}" data-sort-domaine="${fn:escapeXml(i.domaine.nom)}">
			<p><i class="fa fa-file-text-o mr-2"></i> ${i.resume}</p>
			<p>
				<i class="fa fa-info-circle mr-2"></i> ${i.descriptif}, <strong><i class="fa fa-globe mr-1"></i>
					${i.domaine.nom}</strong>
			</p>
			<hr>
			<div>
				<a href="?mode=updating&id=${i.num}&page=${currentPage}"><i class="fa fa-edit"></i> <fmt:message key="common.update" bundle="${i18n}" /></a>
				<a href="?mode=delete&id=${i.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="<fmt:message key='invention.item.label' bundle='${i18n}'/>"><i class="fa fa-trash"></i> <fmt:message key="common.delete" bundle="${i18n}" /></a>
			</div>
		</div>
	</c:forEach>
</div>

<jsp:include page="/WEB-INF/jspf/simple-pagination.jsp">
	<jsp:param name="basePath" value="inventions" />
	<jsp:param name="ariaLabel" value="Pagination des inventions" />
</jsp:include>
