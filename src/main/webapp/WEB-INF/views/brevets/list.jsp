<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
	<a href="ReprotsController?filename=RptBrevet" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
		Imprimer</a>

	<div class="row brevetCard">
		<c:forEach items="${brevets}" var="brevet">
			<div class="col-lg-4 mb-4">
				<div class="card p-3 shadow-sm">
					<p><i class="fa fa-info-circle mr-2 text-primary"></i> ${brevet.description}</p>
					<p><i class="fa fa-calendar mr-2 text-info"></i> ${brevet.dateDepot} - ${brevet.dateValidation}</p>
					<p><i class="fa fa-user mr-2 text-success"></i> ${brevet.inventeur}</p>
					<p><i class="fa fa-lightbulb-o mr-2 text-warning"></i> ${brevet.invention}</p>
					<hr>
					<div class="d-flex justify-content-end">
						<a class="text-success mr-3" href="?mode=updating&id=${brevet.num}"><i class="fa fa-edit"></i>
							Edit</a>
						<a class="text-danger" href="?mode=delete&id=${brevet.num}"><i class="fa fa-trash"></i>
							Delete</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>