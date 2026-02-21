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
		<c:if test="${status=='invalidDates' }">
			<div class="alert alert-danger" role="alert">Date Depot doit etre inferieure ou egale a Date Validation.</div>
		</c:if>

		<input type="hidden" name="op" value="add">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control" id="description" required
				placeholder="Entrer description" name="description" value="${param.description}">
		</div>
		<div class="form-group">
			<label for="datedepot">Date Depot :</label> <input type="date"
				class="form-control" id="datedepot" required name="datedepot" value="${param.datedepot}">
		</div>
		<div class="form-group">
			<label for="datevalidation">Date Validation :</label> <input
				type="date" class="form-control" id="datevalidation" required
				name="datevalidation" value="${param.datevalidation}">
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Inventeur :</label> <select
				class="form-control" required name="inventeur" id="inventeur">
				<c:forEach items="${inventeurs}" var="inventeur">
					<option value="${inventeur.num}" ${param.inventeur == inventeur.num ? 'selected' : ''}>${inventeur.nom }
						${inventeur.prenom }</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Invention :</label> <select
				class="form-control" required name="invention" id="invention">
				<c:forEach items="${inventions}" var="invention">
					<option value="${invention.num}" ${param.invention == invention.num ? 'selected' : ''}>${invention}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>
		<a href="?mode=list&page=${empty param.page ? 1 : param.page}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> Retour a la liste</a>


	</form>
</div>

<script type="text/javascript">
	(function() {
		const depotInput = document.getElementById('datedepot');
		const validationInput = document.getElementById('datevalidation');

		if (!depotInput || !validationInput) {
			return;
		}

		function validateDates() {
			validationInput.min = depotInput.value || '';
			if (depotInput.value && validationInput.value && validationInput.value < depotInput.value) {
				validationInput.setCustomValidity('Date Validation doit etre superieure ou egale a Date Depot.');
			} else {
				validationInput.setCustomValidity('');
			}
		}

		depotInput.addEventListener('change', validateDates);
		validationInput.addEventListener('change', validateDates);
		validateDates();
	})();
</script>
