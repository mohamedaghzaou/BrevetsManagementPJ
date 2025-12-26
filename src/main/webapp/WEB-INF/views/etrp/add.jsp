<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Ajouter un Brevets</h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='added' }">
			<div class="alert alert-success" role="alert">Entreprise Ajouter
				avec sucess</div>
		</c:if>
		<c:if test="${status=='Notadded' }">
			<div class="alert alert-success" role="alert">Problem dans
				l´ajout</div>
		</c:if>

		<input type="hidden" name="op" value="add">
		<div class="form-group">
			<label for="nom">Nom :</label> <input type="text"
				class="form-control" id="nom" required placeholder="Entrer Nom"
				name="nom">
		</div>
		<div class="form-group">
			<label for="activite">Activite :</label> <input type="text"
				class="form-control" id="activite" required
				placeholder="Entrer Activite" name="activite">
		</div>
		<div class="form-group">
			<label for="ca">Ca :</label> <input type="number" class="form-control" 
				id="ca" required placeholder="Entrer Ca" name="ca">
		</div>
		<div class="form-group">
			<label for="ville">Ville :</label> <input type="text"
				class="form-control" id="ville" required placeholder="Entrer Ville"
				name="ville">
		</div>
		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>


	</form>
</div>