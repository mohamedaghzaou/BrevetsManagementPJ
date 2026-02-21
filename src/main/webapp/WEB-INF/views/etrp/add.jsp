<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<h5><fmt:message key="entreprise.add.title" bundle="${i18n}" /></h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="added" />
			<jsp:param name="successMessageKey" value="entreprise.add.success" />
			<jsp:param name="errorStatus" value="Notadded" />
			<jsp:param name="errorMessageKey" value="entreprise.add.error" />
		</jsp:include>

		<input type="hidden" name="op" value="add">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="nom"><fmt:message key="field.name" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom"
				required placeholder="<fmt:message key='placeholder.enterName' bundle='${i18n}'/>" name="nom" value="${not empty entreprise ? entreprise.nom : param.nom}">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="activite"><fmt:message key="field.activity" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.activite ? 'is-invalid' : ''}" id="activite"
				required placeholder="<fmt:message key='placeholder.enterActivity' bundle='${i18n}'/>" name="activite"
				value="${not empty entreprise ? entreprise.activite : param.activite}">
			<c:if test="${not empty fieldErrors.activite}">
				<small class="text-danger">${fieldErrors.activite}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ca"><fmt:message key="field.turnover" bundle="${i18n}" /> :</label>
			<input type="number" step="0.01" class="form-control ${not empty fieldErrors.ca ? 'is-invalid' : ''}"
				id="ca" required placeholder="<fmt:message key='placeholder.enterTurnover' bundle='${i18n}'/>" name="ca"
				value="${not empty entreprise ? entreprise.ca : param.ca}">
			<c:if test="${not empty fieldErrors.ca}">
				<small class="text-danger">${fieldErrors.ca}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ville"><fmt:message key="field.city" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.ville ? 'is-invalid' : ''}" id="ville"
				required placeholder="<fmt:message key='placeholder.enterCity' bundle='${i18n}'/>" name="ville"
				value="${not empty entreprise ? entreprise.ville : param.ville}">
			<c:if test="${not empty fieldErrors.ville}">
				<small class="text-danger">${fieldErrors.ville}</small>
			</c:if>
		</div>
		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>
	</form>
</div>