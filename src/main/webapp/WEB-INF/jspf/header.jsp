<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!doctype html>
	<html lang="en">

	<head>
		<title>Gestion des Brevets</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>

		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

		<link rel="stylesheet" href="css/style.css" type="text/css">
		<link rel="stylesheet" href="css/mystyle.css" type="text/css">

		<link rel="icon" type="image/x-icon" href="images/favicon.ico">

	</head>

	<body>
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
									class="fa fa-home mr-2"></i> Home</a></li>
						<li class="${ destination == 'brevets' ? 'colorlib-active' : ''}"><a href="brevets?mode=list"><i
									class="fa fa-certificate mr-2"></i> Brevet</a></li>
						<li class="${ destination == 'inventions' ? 'colorlib-active' : ''}"><a
								href="inventions?mode=list"><i class="fa fa-lightbulb-o mr-2"></i> Invention</a></li>
						<li class="${ destination == 'inventeur' ? 'colorlib-active' : ''}"><a
								href="inventeurs?mode=list"><i class="fa fa-users mr-2"></i> Inventeur</a></li>
						<li class="${ destination == 'entreprise' ? 'colorlib-active' : ''}"><a
								href="enterprises?mode=list"><i class="fa fa-building mr-2"></i> Entreprise</a></li>
						<li class="${ destination == 'domaine' ? 'colorlib-active' : ''}"><a
								href="domaines?mode=list"><i class="fa fa-globe mr-2"></i> Domaine</a></li>
					</ul>
				</nav>
			</aside>

			<div id="colorlib-main">
				<section class="ftco-section pt-1 mb-2 ftco-intro">
					<div class="container-fluid px-3 px-md-0">
						<div class="row">
							<h1 class="display-6 text-right" style="margin-bottom: 0;">Gestion Des Brevets</h1>
						</div>