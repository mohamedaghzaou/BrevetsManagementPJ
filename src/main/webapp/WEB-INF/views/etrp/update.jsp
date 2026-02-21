<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<h5><fmt:message key="entreprise.update.title" bundle="${i18n}" /></h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="updated" />
			<jsp:param name="successMessageKey" value="entreprise.update.success" />
			<jsp:param name="errorStatus" value="notupdated" />
			<jsp:param name="errorMessageKey" value="common.update.error" />
		</jsp:include>

		<input type="hidden" name="op" value="update">
		<input type="hidden" name="num" value="${entreprise.num }">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="nom"><fmt:message key="field.name" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom"
				required placeholder="<fmt:message key='placeholder.enterName' bundle='${i18n}'/>" value="${entreprise.nom }" name="nom">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="activite"><fmt:message key="field.activity" bundle="${i18n}" /> :</label>
			<input type="text" value="${entreprise.activite }"
				class="form-control ${not empty fieldErrors.activite ? 'is-invalid' : ''}" id="activite"
				required placeholder="<fmt:message key='placeholder.enterActivity' bundle='${i18n}'/>" name="activite">
			<c:if test="${not empty fieldErrors.activite}">
				<small class="text-danger">${fieldErrors.activite}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ca"><fmt:message key="field.turnover" bundle="${i18n}" /> :</label>
			<input type="number" step="0.01" class="form-control ${not empty fieldErrors.ca ? 'is-invalid' : ''}"
				value="${entreprise.ca }" id="ca" required placeholder="<fmt:message key='placeholder.enterTurnover' bundle='${i18n}'/>" name="ca">
			<c:if test="${not empty fieldErrors.ca}">
				<small class="text-danger">${fieldErrors.ca}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="ville"><fmt:message key="field.city" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.ville ? 'is-invalid' : ''}" id="ville"
				required placeholder="<fmt:message key='placeholder.enterCity' bundle='${i18n}'/>" value="${entreprise.ville }" name="ville">
			<c:if test="${not empty fieldErrors.ville}">
				<small class="text-danger">${fieldErrors.ville}</small>
			</c:if>
		</div>
		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>

	</form>
</div>