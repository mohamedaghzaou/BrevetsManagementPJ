<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier un Entreprise</h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='updated' }">
			<div class="alert alert-success" role="alert">Entreprise
				Modifier avec success</div>
		</c:if>
		<c:if test="${status=='notupdated' }">
			<div class="alert alert-success" role="alert">Problem dans mise
				à jour</div>
		</c:if>

		<input type="hidden" name="op" value="update"> <input
			type="hidden" name="num" value="${entreprise.num }">
		<div class="form-group">
			<label for="nom">Nom :</label> <input type="text"
				class="form-control" id="nom" required placeholder="Entrer Nom"
				value="${entreprise.nom }" name="nom">
		</div>
		<div class="form-group">
			<label for="activite">Activite :</label> <input type="text"
				value="${entreprise.activite }" class="form-control" id="activite"
				required placeholder="Entrer Activite" name="activite">
		</div>
		<div class="form-group">
			<label for="ca">Ca :</label> <input type="number" class="form-control"
				value="${entreprise.ca }" id="ca" required placeholder="Entrer Ca"
				name="ca">
		</div>
		<div class="form-group">
			<label for="ville">Ville :</label> <input type="text"
				class="form-control" id="ville" required placeholder="Entrer Ville"
				value="${entreprise.ville }" name="ville">
		</div>
		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>

	</form>
</div>