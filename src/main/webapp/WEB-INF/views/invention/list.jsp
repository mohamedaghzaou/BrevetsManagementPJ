<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="d-flex flex-wrap mb-1">
		<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
		<a href="ReprotsController?filename=RptInvention" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
			Imprimer</a>
	</div>
	<c:if test="${status == 'deleted'}">
		<div class="alert alert-success" role="alert">Invention supprimee avec succes.</div>
	</c:if>
	<c:if test="${not empty globalError}">
		<div class="alert alert-danger" role="alert">${globalError}</div>
	</c:if>

	<div class="row inventions">
		<c:if test="${empty inventions}">
			<div class="col-12">
				<div class="card p-4 text-center shadow-sm">
					<h6 class="mb-2">Aucune invention disponible.</h6>
					<p class="text-muted mb-0">Ajoutez une invention pour commencer.</p>
				</div>
			</div>
		</c:if>
		<c:forEach items="${inventions}" var="i">
			<div class="invention col-lg-6">
				<p><i class="fa fa-file-text-o mr-2"></i> ${i.resume}</p>
				<p>
					<i class="fa fa-info-circle mr-2"></i> ${i.descriptif}, <strong><i class="fa fa-globe mr-1"></i>
						${i.domaine.nom}</strong>
				</p>
				<hr>
				<div>
					<a href="?mode=updating&id=${i.num}&page=${currentPage}"><i class="fa fa-edit"></i> Update</a>
					<a href="?mode=delete&id=${i.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="cette invention"><i class="fa fa-trash"></i> Delete</a>
				</div>
			</div>
		</c:forEach>
	</div>

	<c:if test="${totalPages > 1}">
		<nav aria-label="Pagination des inventions" class="mt-2">
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
