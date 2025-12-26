<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
	<a href="ReprotsController?filename=RptEntreprise" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
		Imprimer</a>

	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th scope="col"><i class="fa fa-building mr-1"></i> NOM ENTREPRISE</th>
				<th scope="col"><i class="fa fa-briefcase mr-1"></i> ACTIVITE</th>
				<th scope="col"><i class="fa fa-money mr-1"></i> CA</th>
				<th scope="col"><i class="fa fa-map-marker mr-1"></i> VILLE</th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entreprises}" var="entreprise">
				<tr>
					<td scope="col">${entreprise.nom}</td>
					<td scope="col">${entreprise.activite}</td>
					<td scope="col">${entreprise.ca}</td>
					<td scope="col">${entreprise.ville}</td>
					<td><a class="text-success" href="?mode=updating&id=${entreprise.num}"><i
								class="fa fa-edit"></i></a></td>
					<td><a href="?mode=delete&id=${entreprise.num}"><i class="fa fa-trash"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>