<%-- 
    Document   : instructor.jsp
    Created on : Aug 17, 2024, 3:31:34 PM
    Author     : TAN DAT
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="baseUrl" value="?kw=${param.kw}" />
<style>
    /* CSS ?? ?i?u ch?nh ?? r?ng c?a c?c c?t */
    .column-name {
        width: 20%; /* Thay ??i theo nhu c?u */
    }
    .column-description {
        width: 35%; /* Thay ??i theo nhu c?u */
    }
    .column-id, .column-expertise {
        width: 15%; /* C?n ch?nh c?t ID v? Expertise */
    }
</style>


<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="card-body clearfix float-sm-left">
                        <a href="<c:url value='/instructor/add-up'/>" class="btn btn-lg btn-success float-right mr-5" style="font-size: 1.25rem;">
                            <i class="fas fa-plus p-2"></i> <b>Add Instructor</b>
                        </a>
                    </div>
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
    <div class="card container">
        <div class="card-header border-transparent">
            <h3 class="card-title">Instructor</h3>

            <div class="card-tools">
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                </button>
            </div>
        </div>
        <!-- /.card-header -->
        <!-- Search and Filter Form -->
        <div class="card-body">
            <form method="GET" action="<c:url value='/instructor'/>">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="kw">Search by Expertise</label> <!-- Label in English -->
                        <input type="text" name="kw" id="kw" class="form-control" placeholder="Search by expertise..." value="${param.kw}">
                    </div>


                </div>
                <div class="row mb-3">


                    <div class="col-md-3">
                        <label for="userId">Instructor</label>
                        <select name="userId" id="instructorId" class="form-control">
                            <option value="">All Instructors</option>
                            <c:forEach var="instructor" items="${instructors}">
                                <option value="${instructor.userId.id}" ${param.userId == instructor.userId ? 'selected' : ''}>
                                    ${instructor.fullName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label>&nbsp;</label> <!-- Empty label to push the Filter button down -->
                        <button type="submit" class="btn btn-primary btn-block">Filter</button>
                    </div>
                </div>
            </form>

        </div>


        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table m-0">
                    <thead>
                        <tr>
                            <th class="column-id">ID</th>
                            <th class="column-name">Name</th>
                            <th class="column-expertise">Expertise</th>
                            <th class="column-description">Description</th>
                            <th></th> <!-- Th?m c?t Actions -->
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Duy?t qua danh s?ch c?c giang vien -->
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
                                        <c:url value="instructor ${instructor.id}" var="cE" />
                                    <a href="#" onclick="deleteUserInstructor('${cD}', '${cE}')" class="text-danger" title="Delete"><i class="fas fa-trash"></i></a>
                                </td>
                                <!-
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- phan trang-->
        <div class="card-footer clearfix">
            <ul class="pagination pagination-sm m-0 float-right">
                <c:if test="${params.page > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${baseUrl}&page=${params.page - 1}">&laquo;</a>
                    </li>
                </c:if>
                <c:forEach var="i" begin="1" end="${pageTotal}">
                    <li class="page-item ${i == params.page ? 'active' : ''}">
                        <a class="page-link" href="${baseUrl}&page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${params.page < pageTotal}">
                    <li class="page-item">
                        <a class="page-link" href="${baseUrl}&page=${params.page + 1}">&raquo;</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<!-- /.content -->
</div>
