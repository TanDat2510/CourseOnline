<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="baseUrl" value="?" />

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <!-- Add any header content here if needed -->
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Enrollments</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="card container">
        <div class="card-header border-transparent">
            <h3 class="card-title">Enrollments</h3>

            <div class="card-tools">
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                </button>
            </div>
        </div>
        <!-- /.card-header -->

        <!-- Search and Filter Form -->
        <div class="card-body">
            <form method="GET" action="<c:url value='/enrollments'/>">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label for="fromCreatedDate">From Enrollment Date</label>
                        <input type="date" id="fromCreatedDate" name="fromCreatedDate" class="form-control" placeholder="From Enrollment Date" value="${params.fromCreatedDate}">
                    </div>
                    <div class="col-md-3">
                        <label for="toCreatedDate">To Enrollment Date</label>
                        <input type="date" id="toCreatedDate" name="toCreatedDate" class="form-control" placeholder="To Enrollment Date" value="${params.toCreatedDate}">
                    </div>
                    <div class="col-md-3">
                        <label for="fromUpdatedDate">From Updated Date</label>
                        <input type="date" id="fromUpdatedDate" name="fromUpdatedDate" class="form-control" placeholder="From Updated Date" value="${params.fromUpdatedDate}">
                    </div>
                    <div class="col-md-3">
                        <label for="toUpdatedDate">To Updated Date</label>
                        <input type="date" id="toUpdatedDate" name="toUpdatedDate" class="form-control" placeholder="To Updated Date" value="${params.toUpdatedDate}">
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-4">
                        <label for="kw">Search by course title</label>
                        <input type="text" id="kw" name="kw" class="form-control" placeholder="Search by course title..." value="${params.kw}">
                    </div>
                    <div class="col-md-4">
                        <label for="status">Status</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">All Statuses</option>
                            <c:forEach var="status" items="${enrollmentStatusList}">
                                <option value="${status}" ${params.status == status ? 'selected' : ''}>
                                    ${status}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                     <div class="col-md-4">
                        <label>&nbsp;</label>
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
                            <th>Student</th>
                            <th>Course</th>
                            <th>Status</th>
                            <th>Enrollment Date</th>
                            <th>Updated Date</th> <!-- Thêm tiêu ?? c?t -->
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Duy?t qua danh sách các ghi danh -->
                        <c:forEach var="enrollment" items="${enrollments}" varStatus="index">
                            <tr id="enrollment${enrollment.id}">
                                <td>${index.index + 1}</td>
                                <td>${enrollment.userId.lastName} ${enrollment.userId.firstName}</td>
                                <td>${enrollment.courseId.title}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${enrollment.status eq 'IN_PROGRESS'}">
                                            <span class="badge badge-primary">${enrollment.status}</span>
                                        </c:when>
                                        <c:when test="${enrollment.status eq 'COMPLETED'}">
                                            <span class="badge badge-success">${enrollment.status}</span>
                                        </c:when>
                                        <c:when test="${enrollment.status eq 'CANCELED'}">
                                            <span class="badge badge-danger">${enrollment.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-secondary">${enrollment.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatDate value="${enrollment.createdDate}" pattern="dd-MM-yyyy hh:mm:ss" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${enrollment.updatedDate}" pattern="dd-MM-yyyy hh:mm:ss" />
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
