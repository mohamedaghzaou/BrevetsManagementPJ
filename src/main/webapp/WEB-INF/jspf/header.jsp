<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${param.lang == 'fr' || param.lang == 'en'}">
	<c:set var="appLang" value="${param.lang}" scope="session" />
</c:if>
<c:if test="${empty sessionScope.appLang}">
	<c:set var="appLang" value="fr" scope="session" />
</c:if>
<fmt:setLocale value="${sessionScope.appLang}" scope="session" />
<fmt:setBundle basename="i18n.messages" var="i18n" scope="request" />
	<!doctype html>
	<html lang="${sessionScope.appLang}">

	<head>
		<title><fmt:message key="app.title" bundle="${i18n}" /></title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

		<link rel="stylesheet" href="css/style.css" type="text/css">
		<link rel="stylesheet" href="css/mystyle.css" type="text/css">

		<link rel="icon" type="image/x-icon" href="images/favicon.ico">

	</head>

	<body>
		<div class="top-lang-switch" style="position:fixed;top:12px;right:12px;z-index:2147483647;display:flex;gap:6px;padding:4px;background:rgba(255,255,255,0.98);border:1px solid #dfe3e8;border-radius:999px;box-shadow:0 6px 16px rgba(0,0,0,0.1);">
			<a class="btn btn-sm js-lang-switch ${sessionScope.appLang == 'fr' ? 'btn-dark' : 'btn-outline-dark'} lang-switch-btn" href="#"
				data-lang="fr" aria-current="${sessionScope.appLang == 'fr' ? 'true' : 'false'}">FR</a>
			<a class="btn btn-sm js-lang-switch ${sessionScope.appLang == 'en' ? 'btn-dark' : 'btn-outline-dark'} lang-switch-btn" href="#"
				data-lang="en" aria-current="${sessionScope.appLang == 'en' ? 'true' : 'false'}">EN</a>
		</div>
		<div id="colorlib-page">
			<a href="#" class="js-colorlib-nav-toggle colorlib-nav-toggle"><i></i></a>
			<aside id="colorlib-aside" role="complementary" class="js-fullheight">
				<div class="text-center mb-4">
					<img src="images/logo.png" alt="Logo"
						style="width: 120px; height: auto; border-radius: 10px; margin-top: 20px;">
				</div>
				<nav id="colorlib-main-menu" role="navigation">
					<ul>
						<li class="${ destination == 'home' ? 'colorlib-active' : ''}"><a href="home"><i
									class="fa fa-home mr-2"></i> <fmt:message key="nav.home" bundle="${i18n}" /></a></li>
						<li class="${ destination == 'brevets' ? 'colorlib-active' : ''}"><a href="brevets?mode=list"><i
									class="fa fa-certificate mr-2"></i> <fmt:message key="nav.brevet" bundle="${i18n}" /></a></li>
						<li class="${ destination == 'inventions' ? 'colorlib-active' : ''}"><a
								href="inventions?mode=list"><i class="fa fa-lightbulb-o mr-2"></i> <fmt:message key="nav.invention" bundle="${i18n}" /></a></li>
						<li class="${ destination == 'inventeur' ? 'colorlib-active' : ''}"><a
								href="inventeurs?mode=list"><i class="fa fa-users mr-2"></i> <fmt:message key="nav.inventeur" bundle="${i18n}" /></a></li>
						<li class="${ destination == 'entreprise' ? 'colorlib-active' : ''}"><a
								href="enterprises?mode=list"><i class="fa fa-building mr-2"></i> <fmt:message key="nav.entreprise" bundle="${i18n}" /></a></li>
						<li class="${ destination == 'domaine' ? 'colorlib-active' : ''}"><a
								href="domaines?mode=list"><i class="fa fa-globe mr-2"></i> <fmt:message key="nav.domaine" bundle="${i18n}" /></a></li>
					</ul>
				</nav>
			</aside>

			<div id="colorlib-main">
				<section class="ftco-section pt-1 mb-2 ftco-intro">
					<div class="container-fluid px-3 px-md-0">
						<div class="row justify-content-center">
							<h1 class="page-title w-100 text-center"><fmt:message key="app.title.main" bundle="${i18n}" /></h1>
						</div>
