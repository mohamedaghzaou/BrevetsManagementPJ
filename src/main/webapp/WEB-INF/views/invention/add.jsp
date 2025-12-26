<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Ajouter un Invention</h5>
</div>
<div class="row">
	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='added' }">
			<div class="alert alert-success" role="alert">invention Ajouter
				avec sucess</div>
		</c:if>
		<c:if test="${status=='Notadded' }">
			<div class="alert alert-success" role="alert">Problem dans
				l´ajout</div>
		</c:if>

		<input type="hidden" name="op" value="add">
		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control" id="description" required
				placeholder="Entrer description" name="description">
		</div>

		<div class="form-group">
			<label for="resume">Resume :</label>  <textarea  cols="10" rows="10"
				class="form-control" id="resume" placeholder="Entrer resume"
				 required name="resume"></textarea>
		</div>
		<div class="form-group">
			<label for="domaine">Domaine :</label> <select class="form-control"
				required name="domaine" id="domaine">
				<c:forEach items="${domaines}" var="d">
					<option value="${d.num}">${d.nom}</option>
				</c:forEach>
			</select>
		</div>
		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>



	</form>
</div>