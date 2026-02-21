<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier un Brevets</h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post" data-loading="true">
		<c:if test="${ status=='updated' }">
			<div class="alert alert-success" role="alert">Brevet modifie avec succes.</div>
		</c:if>
		<c:if test="${status=='notUpdated' }">
			<div class="alert alert-danger" role="alert">Probleme lors de la mise a jour du brevet.</div>
		</c:if>
		<c:if test="${not empty globalError}">
			<div class="alert alert-danger" role="alert">${globalError}</div>
		</c:if>
		<c:if test="${not empty fieldErrors}">
			<div class="alert alert-warning" role="alert">Veuillez corriger les champs en erreur.</div>
		</c:if>

		<input type="hidden" name="op" value="update"> <input
			type="hidden" name="num" value="${brevet.num }">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control ${not empty fieldErrors.description ? 'is-invalid' : ''}" id="description"
				placeholder="Entrer description" name="description"
				value="${brevet.description }">
			<c:if test="${not empty fieldErrors.description}">
				<small class="text-danger">${fieldErrors.description}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datedepot">Date Depot :</label> <input type="date"
				class="form-control ${not empty fieldErrors.datedepot ? 'is-invalid' : ''}" id="datedepot"
				value="${brevet.dateDepot }" name="datedepot">
			<c:if test="${not empty fieldErrors.datedepot}">
				<small class="text-danger">${fieldErrors.datedepot}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datevalidation">Date Validation :</label> <input
				type="date" class="form-control ${not empty fieldErrors.datevalidation ? 'is-invalid' : ''}" id="datevalidation"
				value="${brevet.dateValidation }" name="datevalidation">
			<c:if test="${not empty fieldErrors.datevalidation}">
				<small class="text-danger">${fieldErrors.datevalidation}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Inventeur :</label> <select
				class="form-control ${not empty fieldErrors.inventeur ? 'is-invalid' : ''}" name="inventeur" id="inventeur">
				<c:forEach items="${inventeurs}" var="inventeur">
					<option ${brevet.inventeur.num==inventeur.num ? 'selected':'' }
						value="${inventeur.num}">${inventeur.nom }
						${inventeur.prenom }</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.inventeur}">
				<small class="text-danger">${fieldErrors.inventeur}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Invention :</label> <select
				class="form-control ${not empty fieldErrors.invention ? 'is-invalid' : ''}" name="invention" id="invention">
				<c:forEach items="${inventions}" var="invention">
					<option ${brevet.invention.num==invention.num ? 'selected':'' } value="${invention.num}">${invention}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.invention}">
				<small class="text-danger">${fieldErrors.invention}</small>
			</c:if>
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
