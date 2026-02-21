<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="d-flex flex-wrap mb-1">
		<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
		<a href="ReprotsController?filename=RptEntreprise" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
			Imprimer</a>
	</div>
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

	<c:if test="${totalPages > 1}">
		<nav aria-label="Pagination des entreprises" class="mt-2">
			<ul class="pagination justify-content-center">
				<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
					<a class="page-link" href="?mode=list&page=${currentPage - 1}" data-loading-link="true">Precedent</a>
				</li>
				<c:forEach begin="1" end="${totalPages}" var="p">
					<li class="page-item ${currentPage == p ? 'active' : ''}">
						<a class="page-link" href="?mode=list&page=${p}" data-loading-link="true">${p}</a>
					</li>
				</c:forEach>
				<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
					<a class="page-link" href="?mode=list&page=${currentPage + 1}" data-loading-link="true">Suivant</a>
				</li>
			</ul>
		</nav>
	</c:if>
