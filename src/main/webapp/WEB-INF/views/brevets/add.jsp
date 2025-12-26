<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Ajouter un Brevets</h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='added' }">
			<div class="alert alert-success" role="alert">Brevet Ajouter
				avec sucess</div>
		</c:if>
		<c:if test="${status=='Notadde' }">
			<div class="alert alert-success" role="alert">Problem dans
				l'ajout</div>
		</c:if>

		<input type="hidden" name="op" value="add">
		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control" id="description" required
				placeholder="Entrer description" name="description">
		</div>
		<div class="form-group">
			<label for="datedepot">Date Depot :</label> <input type="date"
				class="form-control" id="datedepot" required name="datedepot">
		</div>
		<div class="form-group">
			<label for="datevalidation">Date Validation :</label> <input
				type="date" class="form-control" id="datevalidation" required
				name="datevalidation">
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Inventeur :</label> <select
				class="form-control" required name="inventeur" id="inventeur">
				<c:forEach items="${inventeurs}" var="inventeur">
					<option value="${inventeur.num}">${inventeur.nom }
						${inventeur.prenom }</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Invention :</label> <select
				class="form-control" required name="invention" id="invention">
				<c:forEach items="${inventions}" var="invention">
					<option value="${invention.num}">${invention}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>


	</form>
</div>
