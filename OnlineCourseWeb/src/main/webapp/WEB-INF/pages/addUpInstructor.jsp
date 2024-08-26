<%-- 
    Document   : addUpInstructor
    Created on : Aug 22, 2024, 10:32:36 PM
    Author     : TAN DAT
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value='/instructor/add-up' var="action"/>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1></h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="<c:url value="/" />">Home</a></li>
                        <li class="breadcrumb-item active">
                            <c:choose>
                                <c:when test="${not empty AddUserDTO.id}">
                                    Update Instructor
                                </c:when>
                                <c:otherwise>
                                    Add Instructor
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <c:if test="${not empty errMsg}">
            <div class="alert alert-danger">
                ${errMsg}
            </div>
        </c:if>
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">
                ${successMsg}
            </div>
        </c:if>
        <div>
            <div class="card card-primary">
                <div class="card-header">
                    <c:choose>
                        <c:when test="${not empty AddUserDTO.id}">
                            <h3 class="card-title">Update Instructor</h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="card-title">Add Instructor</h3>
                        </c:otherwise>
                    </c:choose>

                    <div class="card-tools">
                        <button type="button" class="btn btn-tool" data-card-widget="collapse" title="Collapse">
                            <i class="fas fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <!-- Form th?m kh?a h?c -->
                    <form:form method="POST" modelAttribute="AddUserDTO" enctype="multipart/form-data" action="${action}">
                        <form:hidden path="id" />                        
                        <form:hidden path="idInstructor" />



                        <div class="form-group">
                            <label for="title">First name</label>
                            <form:input path="firstName" id="firstName" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="title">Last name</label>
                            <form:input path="lastName" id="lastName" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="title">Email</label>
                            <form:input path="email" id="email" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="title">Phone</label>
                            <form:input path="phone" id="phone" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="timeExperted">Username</label>
                            <form:input path="username" id="username" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="price">Password</label>
                            <form:input path="password" id="password" type="password" class="form-control" />
                        </div>


                        <div class="form-group">
                            <label for="price">Expertise</label>
                            <form:input path="expertise" name="expertise" id="expertise" type="expertise" class="form-control" />
                        </div> 
                        <div class="form-group">
                            <label for="price">Description</label>
                            <form:input path="description" name="description" id="description" type="description" class="form-control" />
                        </div>

<!--                        <div class="form-group">
                            <label for="price">ROLE</label>
                            <form:input path="userRole" name="userRole" id="userRole" type="text" class="form-control" />
                        </div>-->
                        
                        <div class="form-group">
                            <label for="file">Avatar</label>
                            <form:input path="file" id="file" type="file" class="form-control" />
                        </div>

                        <!-- Hi?n th? h?nh ?nh n?u c? -->
                        <c:if test="${not empty AddUserDTO.avatar}">
                            <div class="form-group">
                                <label>Avatar</label><br>
                                <img src="<c:url value='${AddUserDTO.avatar}' />" alt="Instructor Image" class="img-thumbnail" style="max-width: 200px;"/>
                            </div>
                        </c:if>

                        <div class="row">
                            <div class="col-12">
                                <a href="<c:url value="/instructor"/>" class="btn btn-secondary">Cancel</a>

                                <c:choose>
                                    <c:when test="${not empty AddUserDTO.id}">
                                        <input type="submit" value="Update Instructor" class="btn btn-success float-right">

                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="Create New Instructor" class="btn btn-success float-right">

                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </form:form>
                </div>
                <!-- /.card-body -->
            </div>
            <!-- /.card -->
        </div>
    </section>
    <!-- /.content -->
</div>