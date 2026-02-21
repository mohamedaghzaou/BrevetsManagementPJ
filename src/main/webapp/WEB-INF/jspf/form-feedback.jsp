<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${status == param.successStatus}">
	<div class="alert alert-success" role="alert">${param.successMessage}</div>
</c:if>
<c:if test="${status == param.errorStatus}">
	<div class="alert alert-danger" role="alert">${param.errorMessage}</div>
</c:if>
<c:if test="${not empty globalError}">
	<div class="alert alert-danger" role="alert">${globalError}</div>
</c:if>
<c:if test="${not empty fieldErrors}">
	<div class="alert alert-warning" role="alert">Veuillez corriger les champs en erreur.</div>
</c:if>
