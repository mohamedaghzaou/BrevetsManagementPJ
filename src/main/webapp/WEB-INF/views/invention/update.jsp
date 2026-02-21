<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier un Invention</h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='updated' }">
			<div class="alert alert-success" role="alert">Invention
				Modifier avec success</div>
		</c:if>
		<c:if test="${status=='notUpdated' }">
			<div class="alert alert-success" role="alert">Problem dans mise
				à jour</div>
		</c:if>
		<input type="hidden" name="op" value="update"> <input
			 type="hidden" name="id" value="${invention.num}">
		<input type="hidden" name="page" value="${param.page}">

		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control" id="description" required
				placeholder="Entrer description" name="description"
				value="${invention.descriptif}">
		</div>

		<div class="form-group">
			<label for="resume">Resume :</label> <textarea  cols="10" rows="10"
				class="form-control" id="resume" placeholder="Entrer resume"
				 required name="resume">${invention.resume}</textarea>
		</div>
		<div class="form-group">
			<label for="domaine">Domaine :</label> <select class="form-control"
				required name="domaine" id="domaine">
				<c:forEach items="${domaines}" var="d">
					<option value="${d.num}"
						${d.num==invention.domaine.num ? 'selected':''}>${d.nom}</option>
				</c:forEach>

			</select>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>
		<a href="?mode=list&page=${empty param.page ? 1 : param.page}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> Retour a la liste</a>

	</form>
</div>

