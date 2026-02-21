<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div>
	<h5><fmt:message key="domaine.update.title" bundle="${i18n}" /></h5>
</div>
<div class="row">
	<form class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="updated" />
			<jsp:param name="successMessageKey" value="domaine.update.success" />
			<jsp:param name="errorStatus" value="notUpdated" />
			<jsp:param name="errorMessageKey" value="domaine.update.error" />
		</jsp:include>
		<input type="hidden" name="op" value="update">
		<input type="hidden" name="num" value="${domaine.num}">
		<input type="hidden" name="page" value="${param.page}">

		<div class="form-group">
			<label for="nom"><fmt:message key="domaine.table.name" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom" name="nom"
				value="${domaine.nom}" required placeholder="<fmt:message key='placeholder.enterDomainName' bundle='${i18n}'/>">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>

		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>
	</form>
</div>