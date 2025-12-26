<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Ajouter un Inventeur</h5>
</div>
<div class="row">

	<form class="col-sm-8" method="post">
		<c:if test="${ status=='added' }">
			<div class="alert alert-success" role="alert">Inventeur ajouté avec succès</div>
		</c:if>
		<c:if test="${status=='Notadde' }">
			<div class="alert alert-danger" role="alert">Problème lors de l'ajout</div>
		</c:if>
		<input type="hidden" name="op" value="add">
		<div class="form-group">
			<label for="nom">Nom :</label> <input type="text"
				class="form-control" pattern="^[a-zA-Z]+$" id="nom" required
				placeholder="Entrer Nom" name="nom">
		</div>
		<div class="form-group">
			<label for="prenom">Prenom :</label> <input type="text"
				class="form-control" id="prenom" pattern="^[a-zA-Z]+$"
				placeholder="Entrer Prenom" required name="prenom">
		</div>
		<div class="form-group">
			<label for="email">Email :</label> <input type="email"
				class="form-control" id="email" placeholder="Entrer Email"
				required name="email">
		</div>
		<div class="form-group">
			<label for="adresse">Adresse :</label> <input type="text"
				class="form-control" id="adresse" placeholder="Entrer Adresse"
				required name="adresse">
		</div>
		<div class="form-group">
			<label for="datenaiss">Date de naissance :</label> <input type="date"
				class="form-control" id="datenaiss" required name="datenaiss">
		</div>
		<div class="form-group">
			<label for="entreprise">Entreprise :</label> <select
				class="form-control" required name="entreprise" id="entreprise">
				<c:forEach items="${entreprises}" var="e">
					<option value="${e.num}">${e.nom}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>
	</form>
</div>
