<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/jspf/list-toolbar.jsp">
    <jsp:param name="addHref" value="?mode=adding" />
    <jsp:param name="printHref" value="ReprotsController?filename=RptDomaine" />
</jsp:include>
<c:if test="${status == 'deleted'}">
    <div class="alert alert-success" role="alert" data-toast="true"><fmt:message key="domaine.deleted.success" bundle="${i18n}" /></div>
</c:if>
<c:if test="${status == 'updated'}">
    <div class="alert alert-success" role="alert" data-toast="true"><fmt:message key="domaine.update.success" bundle="${i18n}" /></div>
</c:if>
<c:if test="${not empty globalError}">
    <div class="alert alert-danger" role="alert" data-toast="true">${globalError}</div>
</c:if>

<div class="table-responsive">
    <table class="table js-sortable-table">
        <thead class="thead-dark">
            <tr>
                <th scope="col" class="sortable-th" data-sortable="true" data-sort-type="text"><fmt:message key="domaine.table.name" bundle="${i18n}" /><span class="sort-indicator"></span></th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty domaines}">
                <tr>
                    <td colspan="3" class="text-center text-muted py-4"><fmt:message key="domaine.empty.title" bundle="${i18n}" /></td>
                </tr>
            </c:if>
            <c:forEach items="${domaines}" var="domaine">
                <tr data-sort-row="true">
                    <td scope="col">${domaine.nom}</td>
                    <td><a class="text-success" href="?mode=updating&id=${domaine.num}&page=${currentPage}"><i class="fa fa-edit"></i></a>
                    </td>
                    <td><a href="?mode=delete&id=${domaine.num}&page=${currentPage}" data-confirm-delete="true" data-delete-label="<fmt:message key='domaine.item.label' bundle='${i18n}'/>"><i class="fa fa-trash"></i></a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/jspf/simple-pagination.jsp">
    <jsp:param name="basePath" value="domaines" />
    <jsp:param name="ariaLabel" value="Pagination des domaines" />
</jsp:include>
