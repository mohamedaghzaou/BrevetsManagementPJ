<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
    <a href="ReprotsController?filename=RptDomaine" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
        Imprimer</a>

    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Nom du Domaine</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${domaines}" var="domaine">
                <tr>
                    <td scope="col">${domaine.nom}</td>
                    <td><a class="text-success" href="?mode=updating&id=${domaine.num}"><i class="fa fa-edit"></i></a>
                    </td>
                    <td><a href="?mode=delete&id=${domaine.num}"><i class="fa fa-trash"></i></a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>