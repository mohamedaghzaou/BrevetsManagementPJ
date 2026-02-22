<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="d-flex flex-wrap mb-2 list-toolbar-sticky">
	<a href="${param.addHref}" class="btn btn-dark mb-1 js-shortcut-add"><i class="fa fa-plus mr-1"></i> <fmt:message key="common.add" bundle="${i18n}" /></a>
	<a href="${param.printHref}" class="btn btn-light mb-1 ml-1"><i class="fa fa-print mr-1"></i> <fmt:message key="common.print" bundle="${i18n}" /></a>
</div>
