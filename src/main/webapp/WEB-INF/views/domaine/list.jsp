<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div class="d-flex flex-wrap mb-1">
        <a href="?mode=adding" class="btn btn-dark mb-1"><i class="fa fa-plus mr-1"></i> Ajouter</a>
        <a href="ReprotsController?filename=RptDomaine" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i>
            Imprimer</a>
    </div>
    <c:if test="${status == 'deleted'}">
        <div class="alert alert-success" role="alert">Domaine supprime avec succes.</div>
    </c:if>
    <c:if test="${not empty globalError}">
        <div class="alert alert-danger" role="alert">${globalError}</div>
    </c:if>

    <div class="table-responsive">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Nom du Domaine</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty domaines}">
                    <tr>
                        <td colspan="3" class="text-center text-muted py-4">Aucun domaine disponible.</td>
                    </tr>
                </c:if>
                <c:forEach items="${domaines}" var="domaine">
                    <tr>
                        <td scope="col">${domaine.nom}</td>
                        <td><a class="text-success" href="?mode=updating&id=${domaine.num}&page=${currentPage}"><i class="fa fa-edit"></i></a>
                        </td>
                        <td><a href="?mode=delete&id=${domaine.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="ce domaine"><i class="fa fa-trash"></i></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${totalPages > 1}">
        <nav aria-label="Pagination des domaines" class="mt-2">
            <ul class="pagination justify-content-center">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?mode=list&page=${currentPage - 1}" data-loading-link="true">Precedent</a>
                </li>
                <c:forEach begin="1" end="${totalPages}" var="p">
                    <li class="page-item ${currentPage == p ? 'active' : ''}">
                        <a class="page-link" href="?mode=list&page=${p}" data-loading-link="true">${p}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="?mode=list&page=${currentPage + 1}" data-loading-link="true">Suivant</a>
                </li>
            </ul>
        </nav>
    </c:if>
