<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Ajouter une Entreprise</h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post" data-loading="true">
		<c:if test="${ status=='added' }">
			<div class="alert alert-success" role="alert">Entreprise ajoutee avec succes.</div>
		</c:if>
		<c:if test="${status=='Notadded' }">
			<div class="alert alert-danger" role="alert">Probleme lors de l'ajout de l'entreprise.</div>
		</c:if>
		<c:if test="${not empty globalError}">
			<div class="alert alert-danger" role="alert">${globalError}</div>
		</c:if>
		<c:if test="${not empty fieldErrors}">
			<div class="alert alert-warning" role="alert">Veuillez corriger les champs en erreur.</div>
		</c:if>

		<input type="hidden" name="op" value="add">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="nom">Nom :</label>
			<input type="text" class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom"
				required placeholder="Entrer Nom" name="nom" value="${not empty entreprise ? entreprise.nom : param.nom}">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="activite">Activite :</label>
			<input type="text" class="form-control ${not empty fieldErrors.activite ? 'is-invalid' : ''}" id="activite"
				required placeholder="Entrer Activite" name="activite"
				value="${not empty entreprise ? entreprise.activite : param.activite}">
			<c:if test="${not empty fieldErrors.activite}">
				<small class="text-danger">${fieldErrors.activite}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ca">Ca :</label>
			<input type="number" step="0.01" class="form-control ${not empty fieldErrors.ca ? 'is-invalid' : ''}"
				id="ca" required placeholder="Entrer Ca" name="ca"
				value="${not empty entreprise ? entreprise.ca : param.ca}">
			<c:if test="${not empty fieldErrors.ca}">
				<small class="text-danger">${fieldErrors.ca}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ville">Ville :</label>
			<input type="text" class="form-control ${not empty fieldErrors.ville ? 'is-invalid' : ''}" id="ville"
				required placeholder="Entrer Ville" name="ville"
				value="${not empty entreprise ? entreprise.ville : param.ville}">
			<c:if test="${not empty fieldErrors.ville}">
				<small class="text-danger">${fieldErrors.ville}</small>
			</c:if>
		</div>
		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>
		<a href="?mode=list&page=${empty param.page ? 1 : param.page}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> Retour a la liste</a>
	</form>
</div>
