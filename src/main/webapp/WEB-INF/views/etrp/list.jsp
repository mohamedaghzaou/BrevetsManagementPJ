<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<jsp:include page="/WEB-INF/jspf/list-toolbar.jsp">
		<jsp:param name="addHref" value="?mode=adding" />
		<jsp:param name="printHref" value="ReprotsController?filename=RptEntreprise" />
	</jsp:include>
	<c:if test="${status == 'deleted'}">
		<div class="alert alert-success" role="alert">Entreprise supprimee avec succes.</div>
	</c:if>
	<c:if test="${not empty globalError}">
		<div class="alert alert-danger" role="alert">${globalError}</div>
	</c:if>

	<div class="table-responsive">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col"><i class="fa fa-building mr-1"></i> NOM ENTREPRISE</th>
					<th scope="col"><i class="fa fa-briefcase mr-1"></i> ACTIVITE</th>
					<th scope="col"><i class="fa fa-money mr-1"></i> CA</th>
					<th scope="col"><i class="fa fa-map-marker mr-1"></i> VILLE</th>
					<th scope="col"></th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty entreprises}">
					<tr>
						<td colspan="6" class="text-center text-muted py-4">Aucune entreprise disponible.</td>
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
						<td><a href="?mode=delete&id=${entreprise.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="cette entreprise"><i class="fa fa-trash"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<jsp:include page="/WEB-INF/jspf/simple-pagination.jsp">
		<jsp:param name="basePath" value="enterprises" />
		<jsp:param name="ariaLabel" value="Pagination des entreprises" />
	</jsp:include>
