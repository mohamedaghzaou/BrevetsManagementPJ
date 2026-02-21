<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="d-flex flex-wrap mb-1">
		<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
		<a href="ReprotsController?filename=RptInventeur" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
			Imprimer</a>
	</div>
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

	<c:if test="${totalPages > 1}">
		<nav aria-label="Pagination des inventeurs" class="mt-2">
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
