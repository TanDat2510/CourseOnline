<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="baseUrl" value="?kw=${param.kw}&status=${param.status}&courseType=${param.courseType}" />

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="card-body clearfix float-sm-left">
                        <a href="<c:url value='/courses/add-up'/>" class="btn btn-lg btn-success float-right mr-5" style="font-size: 1.25rem;">
                            <i class="fas fa-plus p-2"></i> <b>Add Course</b>
                        </a>
                    </div>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Courses</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->

    <div class="card container">
        <div class="card-header border-transparent">
            <h3 class="card-title">Courses</h3>

            <div class="card-tools">
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                </button>
            </div>
        </div>
        <!-- /.card-header -->

        <!-- Search and Filter Form -->
        <div class="card-body">
            <form method="GET" action="<c:url value='/courses'/>">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="kw">Search by Title</label> <!-- Label in English -->
                        <input type="text" name="kw" id="kw" class="form-control" placeholder="Search by title..." value="${param.kw}">
                    </div>
                    <div class="col-md-3">
                        <label for="status">Status</label> <!-- Label in English -->
                        <select name="status" id="status" class="form-control">
                            <option value="">All Status</option>
                            <c:forEach var="status" items="${courseStatusList}">
                                <option value="${status}" ${param.status == status ? 'selected' : ''}>
                                    ${status}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label for="courseType">Course Type</label> <!-- Label in English -->
                        <select name="courseType" id="courseType" class="form-control">
                            <option value="">All Course Types</option>
                            <c:forEach var="type" items="${courseTypesList}">
                                <option value="${type}" ${param.courseType == type ? 'selected' : ''}>
                                    ${type}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label for="fromPrice">From Price</label> <!-- Label in English -->
                        <input type="number" name="fromPrice" id="fromPrice" class="form-control" placeholder="From Price" value="${param.fromPrice}">
                    </div>
                    <div class="col-md-3">
                        <label for="toPrice">To Price</label> <!-- Label in English -->
                        <input type="number" name="toPrice" id="toPrice" class="form-control" placeholder="To Price" value="${param.toPrice}">
                    </div>
                    <div class="col-md-3">
                        <label for="instructorId">Instructor</label> <!-- Label in English -->
                        <select name="instructorId" id="instructorId" class="form-control">
                            <option value="">All Instructors</option>
                            <c:forEach var="instructor" items="${instructors}">
                                <option value="${instructor.id}" ${param.instructorId == instructor.id ? 'selected' : ''}>
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
                            <th>ID</th>
                            <th>Title</th>
                            <th>Status</th>
                            <th>Course Type</th>
                            <th>Price</th>
                            <th>Instructor</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="course" items="${courses}" varStatus="status">
                            <tr id="course${course.id}">
                                <td>${status.index + 1}</td>
                                <td>${course.title}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${course.status eq 'INACTIVE'}">
                                            <span class="badge badge-danger">${course.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-success">${course.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><span class="badge badge-info">${course.courseType}</span></td>
                                <td>
                                    <fmt:formatNumber value="${course.price}" type="number" groupingUsed="true" /> VND
                                </td>
                                <td>${course.instructorId.userId.lastName} ${course.instructorId.userId.firstName}</td>
                                <td>
                                    <a href="<c:url value="/courses/add-up/${course.id}"/>" class="text-primary mr-2" title="Edit"><i class="fas fa-edit"></i></a>
                                    <a href="#" class="text-success mr-2" title="View"><i class="fas fa-eye"></i></a>
                                        <c:url value="/api/courses/${course.id}/" var="cD" />
                                        <c:url value="course${course.id}" var="cE" />
                                    <a href="#" onclick="deleteCourse('${cD}', '${cE}')" class="text-danger" title="Delete"><i class="fas fa-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
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
    <!-- /.content -->
</div>
