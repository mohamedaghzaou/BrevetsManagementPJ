<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<button type="submit" class="btn btn-dark"><fmt:message key="common.submit" bundle="${i18n}" /></button>
<button type="reset" class="btn btn-light"><fmt:message key="common.clear" bundle="${i18n}" /></button>
<a href="${param.returnHref}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> <fmt:message key="common.backToList" bundle="${i18n}" /></a>