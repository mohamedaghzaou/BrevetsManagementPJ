<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="d-flex flex-wrap mb-2">
		<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
		<a href="ReprotsController?filename=RptBrevet" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
			Imprimer</a>
	</div>

	<c:if test="${status == 'deleted'}">
		<div class="alert alert-success" role="alert">Brevet supprime avec succes.</div>
	</c:if>
	<c:if test="${not empty globalError}">
		<div class="alert alert-danger" role="alert">${globalError}</div>
	</c:if>

	<form method="get" action="brevets" class="card p-3 shadow-sm mb-3" data-loading="true">
		<input type="hidden" name="mode" value="list">

		<div class="form-row">
			<div class="form-group col-md-4">
				<label for="keyword"><i class="fa fa-search mr-1"></i> Recherche</label>
				<input type="text" class="form-control" id="keyword" name="keyword"
					value="${param.keyword}" placeholder="Description du brevet">
			</div>
			<div class="form-group col-md-4">
				<label for="inventeurId"><i class="fa fa-user mr-1"></i> Inventeur</label>
				<select id="inventeurId" name="inventeurId" class="form-control">
					<option value="">Tous</option>
					<c:forEach items="${inventeurs}" var="inventeur">
						<option value="${inventeur.num}" ${param.inventeurId == inventeur.num ? 'selected' : ''}>
							${inventeur.nom} ${inventeur.prenom}
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group col-md-4">
				<label for="entrepriseId"><i class="fa fa-building mr-1"></i> Entreprise</label>
				<select id="entrepriseId" name="entrepriseId" class="form-control">
					<option value="">Toutes</option>
					<c:forEach items="${entreprises}" var="entreprise">
						<option value="${entreprise.num}" ${param.entrepriseId == entreprise.num ? 'selected' : ''}>
							${entreprise.nom}
						</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-row">
			<div class="form-group col-md-3">
				<label for="domaineId"><i class="fa fa-tags mr-1"></i> Domaine</label>
				<select id="domaineId" name="domaineId" class="form-control">
					<option value="">Tous</option>
					<c:forEach items="${domaines}" var="domaine">
						<option value="${domaine.num}" ${param.domaineId == domaine.num ? 'selected' : ''}>
							${domaine.nom}
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group col-md-3">
				<label for="dateDepotFrom"><i class="fa fa-calendar mr-1"></i> Depot du</label>
				<input type="date" class="form-control" id="dateDepotFrom" name="dateDepotFrom"
					value="${param.dateDepotFrom}">
			</div>
			<div class="form-group col-md-3">
				<label for="dateDepotTo">Depot au</label>
				<input type="date" class="form-control" id="dateDepotTo" name="dateDepotTo"
					value="${param.dateDepotTo}">
			</div>
			<div class="form-group col-md-3">
				<label for="sortBy"><i class="fa fa-sort mr-1"></i> Trier par</label>
				<select id="sortBy" name="sortBy" class="form-control">
					<option value="dateDepot" ${param.sortBy == 'dateDepot' || empty param.sortBy ? 'selected' : ''}>Date depot</option>
					<option value="dateValidation" ${param.sortBy == 'dateValidation' ? 'selected' : ''}>Date validation</option>
					<option value="description" ${param.sortBy == 'description' ? 'selected' : ''}>Description</option>
					<option value="inventeur" ${param.sortBy == 'inventeur' ? 'selected' : ''}>Inventeur</option>
					<option value="entreprise" ${param.sortBy == 'entreprise' ? 'selected' : ''}>Entreprise</option>
					<option value="domaine" ${param.sortBy == 'domaine' ? 'selected' : ''}>Domaine</option>
					<option value="num" ${param.sortBy == 'num' ? 'selected' : ''}>Numero</option>
				</select>
			</div>
		</div>

		<div class="form-row">
			<div class="form-group col-md-3">
				<label for="dateValidationFrom"><i class="fa fa-calendar-check-o mr-1"></i> Validation du</label>
				<input type="date" class="form-control" id="dateValidationFrom" name="dateValidationFrom"
					value="${param.dateValidationFrom}">
			</div>
			<div class="form-group col-md-3">
				<label for="dateValidationTo">Validation au</label>
				<input type="date" class="form-control" id="dateValidationTo" name="dateValidationTo"
					value="${param.dateValidationTo}">
			</div>
			<div class="form-group col-md-2">
				<label for="sortDir">Ordre</label>
				<select id="sortDir" name="sortDir" class="form-control">
					<option value="desc" ${param.sortDir == 'desc' || empty param.sortDir ? 'selected' : ''}>Decroissant</option>
					<option value="asc" ${param.sortDir == 'asc' ? 'selected' : ''}>Croissant</option>
				</select>
			</div>
			<div class="form-group col-md-4 d-flex align-items-end">
				<button type="submit" class="btn btn-primary"><i class="fa fa-filter mr-1"></i> Appliquer</button>
				<a href="?mode=list" class="btn btn-outline-secondary ml-2">Reinitialiser</a>
			</div>
		</div>
	</form>

	<p class="text-muted mb-3">
		<i class="fa fa-list mr-1"></i> Resultats: ${totalResults} brevet(s)
		<c:if test="${hasActiveFilters}">
			<span class="ml-2 d-inline-flex flex-wrap align-items-center">
				<i class="fa fa-filter mr-1"></i> Filtres:
				<c:forEach items="${activeFilters}" var="filter">
					<a class="badge badge-light border mr-1 mb-1" href="${filter.removeUrl}" data-loading-link="true">
						${filter.label} <i class="fa fa-times ml-1"></i>
					</a>
				</c:forEach>
			</span>
		</c:if>
	</p>

	<div class="row brevetCard">
		<c:if test="${empty brevets}">
			<div class="col-12">
				<div class="card p-4 text-center shadow-sm">
					<h6 class="mb-2">Aucun brevet trouve.</h6>
					<p class="text-muted mb-3">Essayez de modifier vos filtres ou reinitialisez la recherche.</p>
					<a href="?mode=list" class="btn btn-outline-secondary" data-loading-link="true">Reinitialiser les filtres</a>
				</div>
			</div>
		</c:if>
		<c:forEach items="${brevets}" var="brevet">
			<c:url var="deleteUrl" value="brevets">
				<c:param name="mode" value="delete" />
				<c:param name="id" value="${brevet.num}" />
				<c:param name="keyword" value="${param.keyword}" />
				<c:param name="inventeurId" value="${param.inventeurId}" />
				<c:param name="entrepriseId" value="${param.entrepriseId}" />
				<c:param name="domaineId" value="${param.domaineId}" />
				<c:param name="dateDepotFrom" value="${param.dateDepotFrom}" />
				<c:param name="dateDepotTo" value="${param.dateDepotTo}" />
				<c:param name="dateValidationFrom" value="${param.dateValidationFrom}" />
				<c:param name="dateValidationTo" value="${param.dateValidationTo}" />
				<c:param name="sortBy" value="${param.sortBy}" />
				<c:param name="sortDir" value="${param.sortDir}" />
				<c:param name="page" value="${currentPage}" />
			</c:url>
			<div class="col-12 col-sm-6 col-md-4 brevet-col mb-4">
				<div class="card p-3 shadow-sm">
					<p><i class="fa fa-info-circle mr-2 text-primary"></i> ${brevet.description}</p>
					<p><i class="fa fa-calendar mr-2 text-info"></i> ${brevet.dateDepot} - ${brevet.dateValidation}</p>
					<p><i class="fa fa-user mr-2 text-success"></i> ${brevet.inventeur}</p>
					<p><i class="fa fa-building mr-2 text-secondary"></i> ${brevet.inventeur.entreprise.nom}</p>
					<p><i class="fa fa-lightbulb-o mr-2 text-warning"></i> ${brevet.invention}</p>
					<p><i class="fa fa-tags mr-2 text-dark"></i> ${brevet.invention.domaine.nom}</p>
					<hr>
					<div class="d-flex justify-content-end">
						<a class="text-success mr-3" href="?mode=updating&id=${brevet.num}&page=${currentPage}"><i class="fa fa-edit"></i>
							Edit</a>
						<a class="text-danger" href="${deleteUrl}" data-confirm-delete="true" data-delete-label="ce brevet"><i class="fa fa-trash"></i>
							Delete</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<c:if test="${totalPages > 1}">
		<nav aria-label="Pagination des brevets" class="mt-2">
			<ul class="pagination justify-content-center">
				<c:url var="prevPageUrl" value="brevets">
					<c:param name="mode" value="list" />
					<c:param name="page" value="${currentPage - 1}" />
					<c:param name="keyword" value="${param.keyword}" />
					<c:param name="inventeurId" value="${param.inventeurId}" />
					<c:param name="entrepriseId" value="${param.entrepriseId}" />
					<c:param name="domaineId" value="${param.domaineId}" />
					<c:param name="dateDepotFrom" value="${param.dateDepotFrom}" />
					<c:param name="dateDepotTo" value="${param.dateDepotTo}" />
					<c:param name="dateValidationFrom" value="${param.dateValidationFrom}" />
					<c:param name="dateValidationTo" value="${param.dateValidationTo}" />
					<c:param name="sortBy" value="${param.sortBy}" />
					<c:param name="sortDir" value="${param.sortDir}" />
				</c:url>
				<c:url var="nextPageUrl" value="brevets">
					<c:param name="mode" value="list" />
					<c:param name="page" value="${currentPage + 1}" />
					<c:param name="keyword" value="${param.keyword}" />
					<c:param name="inventeurId" value="${param.inventeurId}" />
					<c:param name="entrepriseId" value="${param.entrepriseId}" />
					<c:param name="domaineId" value="${param.domaineId}" />
					<c:param name="dateDepotFrom" value="${param.dateDepotFrom}" />
					<c:param name="dateDepotTo" value="${param.dateDepotTo}" />
					<c:param name="dateValidationFrom" value="${param.dateValidationFrom}" />
					<c:param name="dateValidationTo" value="${param.dateValidationTo}" />
					<c:param name="sortBy" value="${param.sortBy}" />
					<c:param name="sortDir" value="${param.sortDir}" />
				</c:url>

				<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
					<a class="page-link ${currentPage != 1 ? 'js-loading-link' : ''}" href="${currentPage == 1 ? '#' : prevPageUrl}" data-loading-link="true">Precedent</a>
				</li>

				<c:forEach begin="1" end="${totalPages}" var="p">
					<c:url var="pageUrl" value="brevets">
						<c:param name="mode" value="list" />
						<c:param name="page" value="${p}" />
						<c:param name="keyword" value="${param.keyword}" />
						<c:param name="inventeurId" value="${param.inventeurId}" />
						<c:param name="entrepriseId" value="${param.entrepriseId}" />
						<c:param name="domaineId" value="${param.domaineId}" />
						<c:param name="dateDepotFrom" value="${param.dateDepotFrom}" />
						<c:param name="dateDepotTo" value="${param.dateDepotTo}" />
						<c:param name="dateValidationFrom" value="${param.dateValidationFrom}" />
						<c:param name="dateValidationTo" value="${param.dateValidationTo}" />
						<c:param name="sortBy" value="${param.sortBy}" />
						<c:param name="sortDir" value="${param.sortDir}" />
					</c:url>
					<li class="page-item ${currentPage == p ? 'active' : ''}">
						<a class="page-link js-loading-link" href="${pageUrl}" data-loading-link="true">${p}</a>
					</li>
				</c:forEach>

				<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
					<a class="page-link ${currentPage != totalPages ? 'js-loading-link' : ''}" href="${currentPage == totalPages ? '#' : nextPageUrl}" data-loading-link="true">Suivant</a>
				</li>
			</ul>
		</nav>
	</c:if>
