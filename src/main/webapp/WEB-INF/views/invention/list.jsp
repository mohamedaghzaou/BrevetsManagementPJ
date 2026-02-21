<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="d-flex flex-wrap mb-1">
		<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
		<a href="ReprotsController?filename=RptInvention" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
			Imprimer</a>
	</div>

	<div class="row inventions">
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
					<a href="?mode=delete&id=${i.num}&page=${currentPage}"><i class="fa fa-trash"></i> Delete</a>
				</div>
			</div>
		</c:forEach>
	</div>

	<c:if test="${totalPages > 1}">
		<nav aria-label="Pagination des inventions" class="mt-2">
			<ul class="pagination justify-content-center">
				<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
					<a class="page-link" href="?mode=list&page=${currentPage - 1}">Precedent</a>
				</li>
				<c:forEach begin="1" end="${totalPages}" var="p">
					<li class="page-item ${currentPage == p ? 'active' : ''}">
						<a class="page-link" href="?mode=list&page=${p}">${p}</a>
					</li>
				</c:forEach>
				<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
					<a class="page-link" href="?mode=list&page=${currentPage + 1}">Suivant</a>
				</li>
			</ul>
		</nav>
	</c:if>
