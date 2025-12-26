<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div>
        <h5>Ajouter un Domaine</h5>
    </div>
    <div class="row">
        <form class="col-sm-8" method="post">
            <c:if test="${status == 'added'}">
                <div class="alert alert-success" role="alert">Domaine ajouté avec succès !</div>
            </c:if>
            <input type="hidden" name="op" value="add">

            <div class="form-group">
                <label for="nom">Nom du Domaine :</label>
                <input type="text" class="form-control" id="nom" name="nom" required
                    placeholder="Entrer Nom du Domaine">
            </div>

            <button type="submit" class="btn btn-dark">Submit</button>
            <button type="reset" class="btn btn-light">Vide</button>
        </form>
    </div>