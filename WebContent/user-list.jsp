<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<title>Gestion utilisateur</title>
<meta charset="UTF-8">
<link rel="stylesheet"href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class=" navbar navbar-expand-md navbar-dark" style="background-color: tomato">
			<ul class="navbar-nav">
				<li> <a href="<%=request.getContextPath()%>/list" class="nav-link"> Utilisateurs</a> </li>
			</ul>
    	</nav>
	</header>
	<br>

	<div class="row">
		<div class="container">	
		<jsp:include page="header.jsp"></jsp:include>
		<h3 class="text-center">Liste des utilisateurs</h3>
		<hr>
		<div class="container text-left">
		 <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Ajout utilisateur</a>
		</div>			
	
	<br>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>
					ID
				</th>
				<th>
					Name
				</th>
				<th>
					Email
				</th>
				<th>
					Country
				</th>
				<th>
					Action
				</th>
			</tr>
	</thead>
	<tbody>
		
			<c:forEach var="user" items="${listUser}">
				<tr>
					
					<td>
						<c:out value="${user.id}"></c:out>
					</td>
					<td>
						<c:out value="${user.name}"></c:out>
					</td>
					<td>
						<c:out value="${user.email}"></c:out>
					</td>
					<td>
						<c:out value="${user.country}"></c:out>
					</td>
					<td>
						<a href="edit?id=<c:out value='${user.id }'/>">Editer</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="delete?id=<c:out value='${user.id }'/>">Supprimer</a> 
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	
	
	
	
	
	</body>
	</html>