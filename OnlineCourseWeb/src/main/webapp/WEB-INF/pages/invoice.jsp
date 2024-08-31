<%-- 
    Document   : invoice
    Created on : Aug 24, 2024, 3:15:20 PM
    Author     : TAN DAT
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="baseUrl" value="?kw=${param.kw}&status=${param.status}" />

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="card-body clearfix float-sm-left">

                    </div>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Invoice</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->



    <!-- Main content -->

    <div class="card container">
        <div class="card-header border-transparent">
            <h3 class="card-title">Invoice</h3>
            <div class="card-tools">
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                </button>
            </div>
        </div>
        <!-- /.card-header -->
        <!-- Search and Filter Form -->
        <div class="card-body">
            <form method="GET" action="<c:url value='/invoice'/>">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="kw">Search by invoice number </label>
                        <input type="text" name="kw" id="kw" class="form-control" placeholder="Search by expertise..." value="${param.kw}">
                    </div>

                </div>
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label for="status">Status</label>
                        <select name="status" id="status" class="form-control">
                            <option value="">All Status</option>
                            <c:forEach var="statusOption" items="${statusOptions}">
                                <option value="${statusOption}" ${param.status == statusOption ? 'selected' : ''}>
                                    ${statusOption}
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
                            <th>Invoice Number</th>
                            <th>Payer Name</th>
                            <th>Payer Email</th>
                            <th>Total Amount</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="invoice" items="${invoice}" varStatus="index">
                            <tr id="${invoice.id}">
                                <td>${index.index + 1}</td>
                                <td>${invoice.invoiceNumber}</td>
                                <td>${invoice.payerName}</td>
                                <td>${invoice.payerEmail}</td>
                                <td>${invoice.totalAmount}VND</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${invoice.status == false}">
                                            <span class="badge badge-primary">NOT_YET</span>
                                        </c:when>
                                        <c:when test="${invoice.status == true}">
                                            <span class="badge badge-success">PAID</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="<c:url value="/invoice/view-details/${invoice.id}"/>" class="text-success mr-2" title="View"><i class="fas fa-eye"></i></a>                                </td>
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
</div>