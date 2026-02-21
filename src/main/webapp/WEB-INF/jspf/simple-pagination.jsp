<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${totalPages > 1}">
	<nav aria-label="${param.ariaLabel}" class="mt-2">
		<ul class="pagination justify-content-center">
			<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
				<a class="page-link" href="${param.basePath}?mode=list&page=${currentPage - 1}" data-loading-link="true"><fmt:message key="pagination.previous" bundle="${i18n}" /></a>
			</li>
			<c:forEach begin="1" end="${totalPages}" var="p">
				<li class="page-item ${currentPage == p ? 'active' : ''}">
					<a class="page-link" href="${param.basePath}?mode=list&page=${p}" data-loading-link="true">${p}</a>
				</li>
			</c:forEach>
			<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
				<a class="page-link" href="${param.basePath}?mode=list&page=${currentPage + 1}" data-loading-link="true"><fmt:message key="pagination.next" bundle="${i18n}" /></a>
			</li>
		</ul>
	</nav>
</c:if>