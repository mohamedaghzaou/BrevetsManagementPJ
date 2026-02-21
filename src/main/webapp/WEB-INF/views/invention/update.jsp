<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier une Invention</h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="updated" />
			<jsp:param name="successMessage" value="Invention modifiee avec succes." />
			<jsp:param name="errorStatus" value="notUpdated" />
			<jsp:param name="errorMessage" value="Probleme lors de la mise a jour." />
		</jsp:include>
		<input type="hidden" name="op" value="update">
		<input type="hidden" name="id" value="${invention.num}">
		<input type="hidden" name="page" value="${param.page}">

		<div class="form-group">
			<label for="description">Description :</label>
			<input type="text" class="form-control ${not empty fieldErrors.description ? 'is-invalid' : ''}" id="description"
				placeholder="Entrer description" name="description" value="${invention.descriptif}">
			<c:if test="${not empty fieldErrors.description}">
				<small class="text-danger">${fieldErrors.description}</small>
			</c:if>
		</div>

		<div class="form-group">
			<label for="resume">Resume :</label>
			<textarea cols="10" rows="10" class="form-control ${not empty fieldErrors.resume ? 'is-invalid' : ''}" id="resume"
				placeholder="Entrer resume" name="resume">${invention.resume}</textarea>
			<c:if test="${not empty fieldErrors.resume}">
				<small class="text-danger">${fieldErrors.resume}</small>
			</c:if>
		</div>
		<div class="form-group">
			<label for="domaine">Domaine :</label>
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
