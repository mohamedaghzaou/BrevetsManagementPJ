<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div>
        <h5>Modifier le Domaine</h5>
    </div>
    <div class="row">
        <form class="col-sm-8" method="post">
            <c:if test="${status == 'updated'}">
                <div class="alert alert-success" role="alert">Domaine modifié avec succès !</div>
            </c:if>
            <input type="hidden" name="op" value="update">
			<input type="hidden" name="num" value="${domaine.num}">
			<input type="hidden" name="page" value="${param.page}">

            <div class="form-group">
                <label for="nom">Nom du Domaine :</label>
                <input type="text" class="form-control" id="nom" name="nom" value="${domaine.nom}" required
                    placeholder="Entrer Nom du Domaine">
            </div>

            <button type="submit" class="btn btn-dark">Submit</button>
			<button type="reset" class="btn btn-light">Vide</button>
			<a href="?mode=list&page=${empty param.page ? 1 : param.page}" class="btn btn-outline-secondary ml-2"><i class="fa fa-arrow-left mr-1"></i> Retour a la liste</a>
        </form>
    </div>

