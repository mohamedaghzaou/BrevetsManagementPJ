<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<h5><fmt:message key="inventeur.update.title" bundle="${i18n}" /></h5>
</div>
<div class="row">
	<form class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="updated" />
			<jsp:param name="successMessageKey" value="inventeur.update.success" />
			<jsp:param name="errorStatus" value="notUpdated" />
			<jsp:param name="errorMessageKey" value="common.update.error" />
		</jsp:include>

		<input type="hidden" name="op" value="update">
		<input type="hidden" name="id" value="${inventeur.num }">
		<input type="hidden" name="page" value="${param.page}">
		<div class="form-group">
			<label for="nom"><fmt:message key="field.lastName" bundle="${i18n}" /> :</label> <input type="text"
				class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom"
				placeholder="<fmt:message key='placeholder.enterLastName' bundle='${i18n}'/>" value="${inventeur.nom }" name="nom">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="prenom"><fmt:message key="field.firstName" bundle="${i18n}" /> :</label> <input type="text"
				class="form-control ${not empty fieldErrors.prenom ? 'is-invalid' : ''}" id="prenom" value="${inventeur.prenom }"
				placeholder="<fmt:message key='placeholder.enterFirstName' bundle='${i18n}'/>" name="prenom">
			<c:if test="${not empty fieldErrors.prenom}">
				<small class="text-danger">${fieldErrors.prenom}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="email"><fmt:message key="field.email" bundle="${i18n}" /> :</label> <input type="email"
				class="form-control ${not empty fieldErrors.email ? 'is-invalid' : ''}" id="email" placeholder="<fmt:message key='placeholder.enterEmail' bundle='${i18n}'/>"
				value="${inventeur.email }" name="email">
			<c:if test="${not empty fieldErrors.email}">
				<small class="text-danger">${fieldErrors.email}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="adresse"><fmt:message key="field.address" bundle="${i18n}" /> :</label> <input type="text"
				class="form-control ${not empty fieldErrors.adresse ? 'is-invalid' : ''}" id="adresse"
				placeholder="<fmt:message key='placeholder.enterAddress' bundle='${i18n}'/>" value="${inventeur.adresse }" name="adresse">
			<c:if test="${not empty fieldErrors.adresse}">
				<small class="text-danger">${fieldErrors.adresse}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="datenaiss"><fmt:message key="field.birthDate" bundle="${i18n}" /> :</label> <input type="date"
				class="form-control ${not empty fieldErrors.datenaiss ? 'is-invalid' : ''}" id="datenaiss"
				name="datenaiss" value="${inventeur.date_nais }" >
			<c:if test="${not empty fieldErrors.datenaiss}">
				<small class="text-danger">${fieldErrors.datenaiss}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="entreprise"><fmt:message key="nav.entreprise" bundle="${i18n}" /> :</label> <select
				class="form-control ${not empty fieldErrors.entreprise ? 'is-invalid' : ''}" name="entreprise" id="entreprise">
				<c:forEach items="${entreprises}" var="e">
					<option value="${e.num}" ${e.num == inventeur.entreprise.num ? 'selected':'' }>${e.nom}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.entreprise}">
				<small class="text-danger">${fieldErrors.entreprise}</small>
			</c:if>
		</div>

		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>
		</form>
</div>