<%-- 
    Document   : addUpInstructor
    Created on : Aug 14, 2024, 3:43:10 PM
    Author     : TAN DAT
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value='/instructor/add-up' var="action"/>
<div class="content-wrapper ">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Add Instructor</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="<c:url value="/" />">Home</a></li>
                        <li class="breadcrumb-item active">Add Instructor</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>


    <!-- Main content -->
    <section class="content">
        <div >
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">General</h3>

                    <div class="card-tools">
                        <button type="button" class="btn btn-tool" data-card-widget="collapse" title="Collapse">
                            <i class="fas fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="card-body">

                    <!-- Form thêm khóa h?c -->
                    <form:form method="POST" modelAttribute="AddUserDTO" enctype="multipart/form-data" action="${action}">
                        <form:hidden path="id" />

                        <div class="form-group">
                            <label for="title">First name</label>
                            <form:input path="firstName" id="firstName" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="title">Last name</label>
                            <form:input path="lastName" id="lastName" class="form-control" />
                        </div><!-- comment -->
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


                        <!--                        <div class="form-group">
                                                    <label for="price">Role</label>
                        <form:input path="userRole" id="userRole" type="text" value="ROLE_INSTRUCTOR" disabled="true" class="form-control" />
                    </div>-->
                        <div class="form-group">
                            <label for="price">Expertise</label>
                            <form:input path="expertise" name="expertise" id="expertise" type="expertise" class="form-control" />
                        </div> 
                        <div class="form-group">
                            <label for="price">Description</label>
                            <form:input path="description" name="description" id="description" type="description" class="form-control" />
                        </div>


                        <div class="form-group">
                            <label for="file">Avatar</label>
                            <form:input path="file" id="file" type="file" class="form-control" />
                        </div>

                        <!-- Hi?n th? hình ?nh n?u có -->
                        <c:if test="${not empty AddUserDTO.avatar}">
                            <div class="form-group">
                                <label>Avatar</label><br>
                                <img src="<c:url value='${AddUserDTO.avatar}' />" alt="Course Image" class="img-thumbnail" style="max-width: 200px;"/>
                            </div>
                        </c:if>
                        
                        <div class="row">
                            <div class="col-12">
                                <a href="<c:url value="/instructor"/>" class="btn btn-secondary">Cancel</a>
                                <c:choose>
                                    <c:when test="${not empty addUserDTO.id}">
                                        <input type="submit" value="Update Course" class="btn btn-success float-right">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="Create New Course" class="btn btn-success float-right">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

<!--                        <div class="row">
                            <div class="col-12">
                                <a href="#" class="btn btn-secondary">Cancel</a>
                                <input type="submit" value="Create new Instrutor" class="btn btn-success float-right">
                            </div>
                        </div>-->
                    </form:form>
                </div>
                <!-- /.card-body -->
            </div>
            <!-- /.card -->
        </div>
    </section>
    <!-- /.content -->
</div>
