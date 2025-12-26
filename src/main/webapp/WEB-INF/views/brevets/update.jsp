<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h5>Modifier un Brevets</h5>
</div>
<div class="row">

	<form action="" class="col-sm-8" method="post">
		<c:if test="${ status=='updated' }">
			<div class="alert alert-success" role="alert">Brevet Modifier
				avec success</div>
		</c:if>
		<c:if test="${status=='notUpdated' }">
			<div class="alert alert-success" role="alert">Problem dans mise
				 jour</div>
		</c:if>

		<input type="hidden" name="op" value="update"> <input
			type="hidden" name="num" value="${brevet.num }">
		<div class="form-group">
			<label for="description">Description :</label> <input type="text"
				class="form-control" id="description" required
				placeholder="Entrer description" name="description"
				value="${brevet.description }">
		</div>
		<div class="form-group">
			<label for="datedepot">Date Depot :</label> <input type="date"
				class="form-control" id="datedepot" value="${brevet.dateDepot }"
				required name="datedepot">
		</div>
		<div class="form-group">
			<label for="datevalidation">Date Validation :</label> <input
				type="date" class="form-control" id="datevalidation" required
				value="${brevet.dateValidation }" name="datevalidation">
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Inventeur :</label> <select
				class="form-control" required name="inventeur" id="inventeur">
				<c:forEach items="${inventeurs}" var="inventeur">
					<option ${brevet.inventeur.num==inventeur.num ? 'selected':'' }
						value="${inventeur.num}">${inventeur.nom }
						${inventeur.prenom }</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label for="exampleFormControlSelect1">Invention :</label> <select
				class="form-control" required name="invention" id="invention">
				<c:forEach items="${inventions}" var="invention">
					<option ${brevet.invention.num==invention.num ? 'selected':'' } value="${invention.num}">${invention}</option>
				</c:forEach>
			</select>
		</div>

		<button type="submit" class="btn btn-dark">Submit</button>
		<button type="reset" class="btn btn-light">Vide</button>


	</form>
</div>
