<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>
	<h5><fmt:message key="invention.update.title" bundle="${i18n}" /></h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="updated" />
			<jsp:param name="successMessageKey" value="invention.update.success" />
			<jsp:param name="errorStatus" value="notUpdated" />
			<jsp:param name="errorMessageKey" value="common.update.error" />
		</jsp:include>
		<input type="hidden" name="op" value="update">
		<input type="hidden" name="id" value="${invention.num}">
		<input type="hidden" name="page" value="${param.page}">

		<div class="form-group">
			<label for="description"><fmt:message key="field.description" bundle="${i18n}" /> :</label>
			<input type="text" class="form-control ${not empty fieldErrors.description ? 'is-invalid' : ''}" id="description"
				placeholder="<fmt:message key='placeholder.enterDescription' bundle='${i18n}'/>" name="description" value="${invention.descriptif}">
			<c:if test="${not empty fieldErrors.description}">
				<small class="text-danger">${fieldErrors.description}</small>
			</c:if>
		</div>

		<div class="form-group">
			<label for="resume"><fmt:message key="field.summary" bundle="${i18n}" /> :</label>
			<textarea cols="10" rows="10" class="form-control ${not empty fieldErrors.resume ? 'is-invalid' : ''}" id="resume"
				placeholder="<fmt:message key='placeholder.enterSummary' bundle='${i18n}'/>" name="resume">${invention.resume}</textarea>
			<c:if test="${not empty fieldErrors.resume}">
				<small class="text-danger">${fieldErrors.resume}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="domaine"><fmt:message key="nav.domaine" bundle="${i18n}" /> :</label>
			<select class="form-control ${not empty fieldErrors.domaine ? 'is-invalid' : ''}" name="domaine" id="domaine">
				<c:forEach items="${domaines}" var="d">
					<option value="${d.num}" ${d.num==invention.domaine.num ? 'selected':''}>${d.nom}</option>
				</c:forEach>
			</select>
			<c:if test="${not empty fieldErrors.domaine}">
				<small class="text-danger">${fieldErrors.domaine}</small>
			</c:if>
		</div>

		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>

	</form>
</div>