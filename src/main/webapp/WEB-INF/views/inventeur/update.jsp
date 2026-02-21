<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier un Inventeur</h5>
</div>
<div class="row">
	<form class="col-sm-8" method="post" data-loading="true">
		<c:if test="${ status=='updated' }">
			<div class="alert alert-success" role="alert">Inventeur modifié avec succès</div>
		</c:if>
		<c:if test="${status=='notUpdated'}">
			<div class="alert alert-danger" role="alert">Problème lors de la mise à jour</div>
		</c:if>
		<c:if test="${not empty globalError}">
			<div class="alert alert-danger" role="alert">${globalError}</div>
		</c:if>
		<c:if test="${not empty fieldErrors}">
			<div class="alert alert-warning" role="alert">Veuillez corriger les champs en erreur.</div>
		</c:if>

		<input type="hidden" name="op" value="update">
		<input type="hidden" name="id" value="${inventeur.num }">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="nom">Nom :</label> <input type="text"
				class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom"
				placeholder="Entrer Nom" value="${inventeur.nom }" name="nom">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="prenom">Prenom :</label> <input type="text"
				class="form-control ${not empty fieldErrors.prenom ? 'is-invalid' : ''}" id="prenom" value="${inventeur.prenom }"
				placeholder="Entrer Prenom" name="prenom">
			<c:if test="${not empty fieldErrors.prenom}">
				<small class="text-danger">${fieldErrors.prenom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="email">Email :</label> <input type="email"
				class="form-control ${not empty fieldErrors.email ? 'is-invalid' : ''}" id="email" placeholder="Entrer Email"
				value="${inventeur.email }" name="email">
			<c:if test="${not empty fieldErrors.email}">
				<small class="text-danger">${fieldErrors.email}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="adresse">Adresse :</label> <input type="text"
				class="form-control ${not empty fieldErrors.adresse ? 'is-invalid' : ''}" id="adresse"
				placeholder="Entrer Adresse" value="${inventeur.adresse }" name="adresse">
			<c:if test="${not empty fieldErrors.adresse}">
				<small class="text-danger">${fieldErrors.adresse}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datenaiss">Date de naissance :</label> <input type="date"
				class="form-control ${not empty fieldErrors.datenaiss ? 'is-invalid' : ''}" id="datenaiss"
				name="datenaiss" value="${inventeur.date_nais }" >
			<c:if test="${not empty fieldErrors.datenaiss}">
				<small class="text-danger">${fieldErrors.datenaiss}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="entreprise">Entreprise :</label> <select 
				class="form-control ${not empty fieldErrors.entreprise ? 'is-invalid' : ''}" name="entreprise" id="entreprise">
				<c:forEach items="${entreprises}" var="e">
					<option value="${e.num}" ${e.num == inventeur.entreprise.num ? 'selected':'' }>${e.nom}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.entreprise}">
				<small class="text-danger">${fieldErrors.entreprise}</small>
			</c:if>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>
		<a href="?mode=list&page=${empty param.page ? 1 : param.page}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> Retour a la liste</a>
		</form>
</div>
