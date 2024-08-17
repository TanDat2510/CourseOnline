<%-- 
    Document   : instructor
    Created on : Aug 14, 2024, 3:42:43 PM
    Author     : TAN DAT
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Instructor</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Instructor</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    <!-- Main content -->
    <div class="card-body clearfix">
        <a href="<c:url value="/instructor/add-up"/>" class="btn btn-sm btn-info float-right mr-5">Add Instructor</a>
    </div>
    <div class="card container">
        <div class="card-header border-transparent">
            <h3 class="card-title">Courses</h3>

            <div class="card-tools">
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                </button>
                <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        </div>
        <!-- /.card-header -->
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table m-0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Expertise</th>
                            <th>Description</th>
                            <th></th> <!-- Thêm c?t Actions -->
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Duy?t qua danh sách các giang vien -->
                        <c:forEach var="instructor" items="${instructor}">
                            <tr>
                                <td><a href="#">${instructor.id}</a></td>
                                <td>${instructor.userId.lastName} ${instructor.userId.firstName}</td>
                                <td><span class="badge badge-info">${instructor.expertise}</span></td>

                                <td>${instructor.description} </td>
                                <td>
                                    <a href="<c:url value="/instructor/add-up/${instructor.id}"/>" class="text-primary mr-2" title="Edit"><i class="fas fa-edit"></i></a>
                                    <a href="#" class="text-success mr-2" title="View"><i class="fas fa-eye"></i></a>
                                        <c:url value="/api/instructor/${instructor.id}/" var="cD" />
                                    <c:url value="instructor${instructor.id}" var="cE" />
                                    <a href="#" onclick="deleteInstructor('${cD}','${cE}')" class="text-danger" title="Delete"><i class="fas fa-trash"></i></a>
                                </td>
<!--                                <td>
                                    <a href="<c:url value="/instructor/add-up/${instructor.id}"/>" class="text-primary mr-2" title="Edit"><i class="fas fa-edit"></i></a>
                                    <a href="#" class="text-success mr-2" title="View"><i class="fas fa-eye"></i></a>
                                    <a href="#" class="text-danger" title="Delete"><i class="fas fa-trash"></i></a>
                                </td>-->
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
    </div>
    <!-- /.content -->
</div>
