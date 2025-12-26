<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
	<a href="ReprotsController?filename=RptInvention" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
		Imprimer</a>

	<div class="row inventions">
		<c:forEach items="${inventions}" var="i">
			<div class="invention col-lg-6">
				<p><i class="fa fa-file-text-o mr-2"></i> ${i.resume}</p>
				<p>
					<i class="fa fa-info-circle mr-2"></i> ${i.descriptif}, <strong><i class="fa fa-globe mr-1"></i>
						${i.domaine.nom}</strong>
				</p>
				<hr>
				<div>
					<a href="?mode=updating&id=${i.num}"><i class="fa fa-edit"></i> Update</a>
					<a href="?mode=delete&id=${i.num}"><i class="fa fa-trash"></i> Delete</a>
				</div>
			</div>
		</c:forEach>
	</div>