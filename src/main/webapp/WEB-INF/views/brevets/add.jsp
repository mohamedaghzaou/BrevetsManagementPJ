<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<h5><fmt:message key="brevets.add.title" bundle="${i18n}" /></h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="added" />
			<jsp:param name="successMessageKey" value="brevets.add.success" />
			<jsp:param name="errorStatus" value="Notadde" />
			<jsp:param name="errorMessageKey" value="brevets.add.error" />
		</jsp:include>

		<input type="hidden" name="op" value="add">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="description"><fmt:message key="field.description" bundle="${i18n}" /> :</label> <input type="text"
				class="form-control ${not empty fieldErrors.description ? 'is-invalid' : ''}" id="description"
				placeholder="<fmt:message key='placeholder.enterDescription' bundle='${i18n}'/>" name="description"
				value="${not empty brevet ? brevet.description : param.description}">
			<c:if test="${not empty fieldErrors.description}">
				<small class="text-danger">${fieldErrors.description}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datedepot"><fmt:message key="field.dateDepot" bundle="${i18n}" /> :</label> <input type="date"
				class="form-control ${not empty fieldErrors.datedepot ? 'is-invalid' : ''}" id="datedepot" name="datedepot"
				value="${not empty brevet ? brevet.dateDepot : param.datedepot}">
			<c:if test="${not empty fieldErrors.datedepot}">
				<small class="text-danger">${fieldErrors.datedepot}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datevalidation"><fmt:message key="field.dateValidation" bundle="${i18n}" /> :</label> <input
				type="date" class="form-control ${not empty fieldErrors.datevalidation ? 'is-invalid' : ''}"
				id="datevalidation" name="datevalidation"
				value="${not empty brevet ? brevet.dateValidation : param.datevalidation}">
			<c:if test="${not empty fieldErrors.datevalidation}">
				<small class="text-danger">${fieldErrors.datevalidation}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="inventeur"><fmt:message key="nav.inventeur" bundle="${i18n}" /> :</label> <select
				class="form-control ${not empty fieldErrors.inventeur ? 'is-invalid' : ''}" name="inventeur" id="inventeur">
				<c:forEach items="${inventeurs}" var="inventeur">
					<option value="${inventeur.num}" ${(not empty brevet and not empty brevet.inventeur and brevet.inventeur.num == inventeur.num) || param.inventeur == inventeur.num ? 'selected' : ''}>${inventeur.nom }
						${inventeur.prenom }</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.inventeur}">
				<small class="text-danger">${fieldErrors.inventeur}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="invention"><fmt:message key="nav.invention" bundle="${i18n}" /> :</label> <select
				class="form-control ${not empty fieldErrors.invention ? 'is-invalid' : ''}" name="invention" id="invention">
				<c:forEach items="${inventions}" var="invention">
					<option value="${invention.num}" ${(not empty brevet and not empty brevet.invention and brevet.invention.num == invention.num) || param.invention == invention.num ? 'selected' : ''}>${invention}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.invention}">
				<small class="text-danger">${fieldErrors.invention}</small>
			</c:if>
		</div>

		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>

	</form>
</div>

<script type="text/javascript">
	(function() {
		const depotInput = document.getElementById('datedepot');
		const validationInput = document.getElementById('datevalidation');
		const validationMessage = '<fmt:message key="brevets.validation.dateOrder" bundle="${i18n}" />';

		if (!depotInput || !validationInput) {
			return;
		}

		function validateDates() {
			validationInput.min = depotInput.value || '';
			if (depotInput.value && validationInput.value && validationInput.value < depotInput.value) {
				validationInput.setCustomValidity(validationMessage);
			} else {
				validationInput.setCustomValidity('');
			}
		}

		depotInput.addEventListener('change', validateDates);
		validationInput.addEventListener('change', validateDates);
		validateDates();
	})();
</script>
