<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${status == param.successStatus}">
	<div class="alert alert-success" role="alert"><fmt:message key="${param.successMessageKey}" bundle="${i18n}" /></div>
</c:if>
<c:if test="${status == param.errorStatus}">
	<div class="alert alert-danger" role="alert"><fmt:message key="${param.errorMessageKey}" bundle="${i18n}" /></div>
</c:if>
<c:if test="${not empty globalError}">
	<div class="alert alert-danger" role="alert">${globalError}</div>
</c:if>
<c:if test="${not empty fieldErrors}">
	<div class="alert alert-warning" role="alert"><fmt:message key="form.validation.fixFields" bundle="${i18n}" /></div>
</c:if>
