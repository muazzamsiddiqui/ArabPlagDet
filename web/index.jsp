<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Arabic Plagiarism Detection Tool</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="author" content="" />

	<!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
	<link rel="shortcut icon" href="favicon.ico">

	<link href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700" rel="stylesheet">

	<!-- Animate.css -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/icomoon.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
	<!-- Flexslider  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/flexslider.css">
	<!-- Flaticons  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/flaticon/font/flaticon.css">
	<!-- Owl Carousel -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css">
	<!-- Theme style  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

	<!-- Modernizr JS -->
	<script src="${pageContext.request.contextPath}/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

</head>
<body>
	<div id="colorlib-page">
		<a href="#" class="js-colorlib-nav-toggle colorlib-nav-toggle"><i></i></a>
		<aside id="colorlib-aside" role="complementary" class="border js-fullheight">
			<h1 id="colorlib-logo"><a href="index.html">Arabic Plagiarism Detection</a></h1>
			<nav id="colorlib-main-menu" role="navigation">
				<ul>
					<li class="colorlib-active"><a href="index.jsp">Home</a></li>
					<li><a href="publications.html">Publications</a></li>
					<li><a href="corpus.html">Corpus</a></li>
					<li><a href="people.html">People</a></li>
					<li><a href="acknowledgement.html">Acknowledgement</a></li>
				</ul>
			</nav>

			<div class="colorlib-footer">
				<p><small>&copy; <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
					<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --> </span> <span>Demo Images: <a href="http://nothingtochance.co/" target="_blank">nothingtochance.co</a></span></small></p>
					<ul>
						<li><a href="#"><i class="icon-facebook2"></i></a></li>
						<li><a href="#"><i class="icon-twitter2"></i></a></li>
						<li><a href="#"><i class="icon-instagram"></i></a></li>
						<li><a href="#"><i class="icon-linkedin2"></i></a></li>
					</ul>
				</div>

			</aside>

			<div id="colorlib-main">

				<div id="get-in-touch" class="colorlib-bg-color">

						<img src="${pageContext.request.contextPath}/images/books.jpg" class="img-responsive" alt="HTML5 Bootstrap Template by colorlib.com">
						<div class="colorlib-narrow-content">
							<div class="row">
								<div class="col-md-6 animate-box" data-animate-effect="fadeInLeft">
									<h2>Plagiarism Detection</h2>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-md-offset-3 col-md-pull-3 animate-box" data-animate-effect="fadeInLeft">
									<p class="colorlib-lead">Upload a file in MS Word or plain text format to check for plagiarism</p>
									<form action="LoadFile" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
										<input type="file" name="file" id="file" size="50" accept=".doc, .docx, .txt"/>
										<br>
										<p><input type="submit" value="Upload" name="upload" id="upload" class="btn btn-primary btn-learn"/></p>
									</form>
								</div>

							</div>
					</div>
				</div>
			</div>
		</div>

		<!-- jQuery -->
		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<!-- jQuery Easing -->
		<script src="${pageContext.request.contextPath}/js/jquery.easing.1.3.js"></script>
		<!-- Bootstrap -->
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<!-- Waypoints -->
		<script src="${pageContext.request.contextPath}/js/jquery.waypoints.min.js"></script>
		<!-- Flexslider -->
		<script src="${pageContext.request.contextPath}/js/jquery.flexslider-min.js"></script>
		<!-- Sticky Kit -->
		<script src="${pageContext.request.contextPath}/js/sticky-kit.min.js"></script>
		<!-- Owl carousel -->
		<script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
		<!-- Counters -->
		<script src="${pageContext.request.contextPath}/js/jquery.countTo.js"></script>


		<!-- MAIN JS -->
		<script src="${pageContext.request.contextPath}/js/main.js"></script>

	</body>
	</html>
