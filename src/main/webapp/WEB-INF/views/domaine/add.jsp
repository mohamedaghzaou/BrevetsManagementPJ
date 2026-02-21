<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
	<h5>Ajouter un Domaine</h5>
</div>
<div class="row">
	<form class="col-sm-8" method="post" data-loading="true">
		<jsp:include page="/WEB-INF/jspf/form-feedback.jsp">
			<jsp:param name="successStatus" value="added" />
			<jsp:param name="successMessage" value="Domaine ajoute avec succes." />
			<jsp:param name="errorStatus" value="notAdded" />
			<jsp:param name="errorMessage" value="Probleme lors de l'ajout du domaine." />
		</jsp:include>
		<input type="hidden" name="op" value="add">
		<input type="hidden" name="page" value="${param.page}">

		<div class="form-group">
			<label for="nom">Nom du Domaine :</label>
			<input type="text" class="form-control ${not empty fieldErrors.nom ? 'is-invalid' : ''}" id="nom" name="nom" required
				placeholder="Entrer Nom du Domaine" value="${not empty domaine ? domaine.nom : param.nom}">
			<c:if test="${not empty fieldErrors.nom}">
				<small class="text-danger">${fieldErrors.nom}</small>
			</c:if>
		</div>

		<jsp:include page="/WEB-INF/jspf/form-buttons.jsp">
			<jsp:param name="returnHref" value="?mode=list&amp;page=${empty param.page ? 1 : param.page}" />
		</jsp:include>
	</form>
</div>
