<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="card shadow-sm border-0">
	<div class="card-body py-4">
		<h4 class="mb-3"><i class="fa fa-exclamation-triangle text-danger mr-2"></i>${errorTitle}</h4>
		<p class="mb-4 text-muted">${errorMessage}</p>
		<a href="home" class="btn btn-dark mr-2"><i class="fa fa-home mr-1"></i><fmt:message key="nav.home" bundle="${i18n}" /></a>
		<a href="javascript:history.back()" class="btn btn-outline-secondary"><i class="fa fa-arrow-left mr-1"></i><fmt:message key="common.back" bundle="${i18n}" /></a>
	</div>
</div>
