<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/list-toolbar.jsp">
	<jsp:param name="addHref" value="?mode=adding" />
	<jsp:param name="printHref" value="ReprotsController?filename=RptEntreprise" />
</jsp:include>
<c:if test="${status == 'deleted'}">
	<div class="alert alert-success" role="alert"><fmt:message key="entreprise.deleted.success" bundle="${i18n}" /></div>
</c:if>
<c:if test="${not empty globalError}">
	<div class="alert alert-danger" role="alert">${globalError}</div>
</c:if>

<div class="table-responsive">
	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th scope="col"><i class="fa fa-building mr-1"></i> <fmt:message key="entreprise.table.name" bundle="${i18n}" /></th>
				<th scope="col"><i class="fa fa-briefcase mr-1"></i> <fmt:message key="field.activity" bundle="${i18n}" /></th>
				<th scope="col"><i class="fa fa-money mr-1"></i> <fmt:message key="field.turnover" bundle="${i18n}" /></th>
				<th scope="col"><i class="fa fa-map-marker mr-1"></i> <fmt:message key="field.city" bundle="${i18n}" /></th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty entreprises}">
				<tr>
					<td colspan="6" class="text-center text-muted py-4"><fmt:message key="entreprise.empty.title" bundle="${i18n}" /></td>
				</tr>
			</c:if>
			<c:forEach items="${entreprises}" var="entreprise">
				<tr>
					<td scope="col">${entreprise.nom}</td>
					<td scope="col">${entreprise.activite}</td>
					<td scope="col">${entreprise.ca}</td>
					<td scope="col">${entreprise.ville}</td>
					<td><a class="text-success" href="?mode=updating&id=${entreprise.num}&page=${currentPage}"><i
								class="fa fa-edit"></i></a></td>
					<td><a href="?mode=delete&id=${entreprise.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="<fmt:message key='entreprise.item.label' bundle='${i18n}'/>"><i class="fa fa-trash"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<jsp:include page="/WEB-INF/jspf/simple-pagination.jsp">
	<jsp:param name="basePath" value="enterprises" />
	<jsp:param name="ariaLabel" value="Pagination des entreprises" />
</jsp:include>