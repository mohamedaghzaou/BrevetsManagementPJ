<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
	<a href="ReprotsController?filename=RptInventeur" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
		Imprimer</a>
	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th scope="col"><i class="fa fa-user mr-1"></i> NOM & PRENOM</th>
				<th scope="col"><i class="fa fa-envelope mr-1"></i> EMAIL</th>
				<th scope="col"><i class="fa fa-map-marker mr-1"></i> ADRESSE</th>
				<th scope="col"><i class="fa fa-calendar mr-1"></i> Date de naissance</th>
				<th scope="col"><i class="fa fa-building mr-1"></i> ENTREPRISE</th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${inventeurs}" var="inventeur">
				<tr>
					<td scope="col">${inventeur.nom} ${inventeur.prenom}</td>
					<td scope="col">${inventeur.email}</td>
					<td scope="col">${inventeur.adresse}</td>
					<td scope="col">${inventeur.date_nais}</td>
					<td scope="col">${inventeur.entreprise.nom}</td>
					<td><a class="text-success" href="?mode=updating&id=${inventeur.num}"><i class="fa fa-edit"></i></a>
					</td>
					<td><a href="?mode=delete&id=${inventeur.num}"><i class="fa fa-trash"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>