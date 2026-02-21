<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<jsp:include page="/WEB-INF/jspf/list-toolbar.jsp">
		<jsp:param name="addHref" value="?mode=adding" />
		<jsp:param name="printHref" value="ReprotsController?filename=RptInventeur" />
	</jsp:include>
	<c:if test="${status == 'deleted'}">
		<div class="alert alert-success" role="alert">Inventeur supprime avec succes.</div>
	</c:if>
	<c:if test="${not empty globalError}">
		<div class="alert alert-danger" role="alert">${globalError}</div>
	</c:if>
	<div class="table-responsive">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col"><i class="fa fa-user mr-1"></i> NOM & PRENOM</th>
					<th scope="col"><i class="fa fa-envelope mr-1"></i> EMAIL</th>
					<th scope="col"><i class="fa fa-map-marker mr-1"></i> ADRESSE</th>
					<th scope="col"><i class="fa fa-calendar mr-1"></i> Date de naissance</th>
					<th scope="col"><i class="fa fa-building mr-1"></i> ENTREPRISE</th>
					<th scope="col"></th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty inventeurs}">
					<tr>
						<td colspan="7" class="text-center text-muted py-4">Aucun inventeur disponible.</td>
					</tr>
				</c:if>
				<c:forEach items="${inventeurs}" var="inventeur">
					<tr>
						<td scope="col">${inventeur.nom} ${inventeur.prenom}</td>
						<td scope="col">${inventeur.email}</td>
						<td scope="col">${inventeur.adresse}</td>
						<td scope="col">${inventeur.date_nais}</td>
						<td scope="col">${inventeur.entreprise.nom}</td>
						<td><a class="text-success" href="?mode=updating&id=${inventeur.num}&page=${currentPage}"><i class="fa fa-edit"></i></a>
						</td>
						<td><a href="?mode=delete&id=${inventeur.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="cet inventeur"><i class="fa fa-trash"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<jsp:include page="/WEB-INF/jspf/simple-pagination.jsp">
		<jsp:param name="basePath" value="inventeurs" />
		<jsp:param name="ariaLabel" value="Pagination des inventeurs" />
	</jsp:include>
