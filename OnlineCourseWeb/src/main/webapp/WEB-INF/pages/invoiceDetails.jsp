<%-- 
    Document   : invoiceDetails
    Created on : Aug 24, 2024, 6:09:06 PM
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
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <!-- Main content -->
                    <div class="invoice p-3 mb-3">
                        <c:set var="currentDate" value="<%= new java.util.Date()%>" />
                        <!-- title row -->
                        <div class="row">
                            <div class="col-12">
                                <h4>
                                    <i class="fas fa-globe"></i> AdminLTE, Inc.
                                    <small class="float-right">Date: <fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/></small>
                                </h4>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- info row -->

                        <div class="row invoice-info">
                            <c:set var="currentPayerName" value="" />
                            <c:set var="currentPayerEmail" value="" />
                            <c:set var="currentInvoiceNumber" value="" />
                            <c:set var="currentPayerPhone" value="" />
                            <c:set var="currentPayerName1" value="" />
                            <div class="col-sm-4 invoice-col">
                                From
                                <address>
                                    <strong>Admin</strong><br>
                                    Phone: 0941265205<br>
                                    Email: 2151050087dat@ou.edu.vn<br>
                                    Address: Le Van Luong, Phuoc Kien<br>
                                    Nha Be, TP Ho Chi Minh<br>
                                </address>
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-4 invoice-col">
                                To
                                <address>
                                    <c:forEach var="dto" items="${enrollmentDTOs}">
                                        <c:if test="${dto.payerName != currentPayerName}">
                                            <strong>${dto.payerName}</strong><br>
                                            <c:set var="currentPayerName" value="${dto.payerName}" />
                                        </c:if>

                                        <c:if test="${dto.payerPhone != currentPayerPhone}">
                                            Phone: ${dto.payerPhone}<br>
                                            <c:set var="currentPayerPhone" value="${dto.payerPhone}" />
                                        </c:if>
                                        <c:if test="${dto.payerEmail != currentPayerEmail}">
                                            Email: ${dto.payerEmail}<br>
                                            <c:set var="currentPayerEmail" value="${dto.payerEmail}" />
                                        </c:if>
                                    </c:forEach>
                                    Address: Le Van Luong, Phuoc Kien<br>
                                    Nha Be, TP Ho Chi Minh<br>
                                </address>
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-4 invoice-col">
                                <c:forEach var="dto" items="${enrollmentDTOs}">
                                    <c:if test="${dto.invoiceNumber != currentInvoiceNumber}">
                                        <strong>Invoice Number: </strong>${dto.invoiceNumber}<br>
                                        <c:set var="currentInvoiceNumber" value="${dto.invoiceNumber}" />
                                    </c:if>
                                    <c:if test="${dto.payerName != currentPayerName1}">
                                        <strong>PayerName: </strong>${dto.payerName}<br>
                                        <c:set var="currentPayerName1" value="${dto.payerName}" />
                                    </c:if>

                                </c:forEach>
                                <b>Payment Due:</b> <fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/><br>

                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->


                        <!-- Table row -->
                        <div class="row">
                            <div class="col-12 table-responsive">
                                <c:if test="${not empty enrollmentDTOs}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Payer Name</th>
                                                <th>Payer Email</th>
                                                <th>User</th>
                                                <th>Course Title</th>
                                                <th>Price Course</th>
                                                <th>Create Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="dto" items="${enrollmentDTOs}">
                                                <tr>                                            
                                                    <td>${dto.payerName}</td>
                                                    <td>${dto.payerEmail}</td>
                                                    <td>${dto.userId.firstName}${dto.userId.lastName}</td>
                                                    <td>${dto.courseId.title}</td>
                                                    <td>${dto.courseId.price}</td>
                                                    <td>${dto.createdDate}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                                <c:if test="${empty enrollmentDTOs}">
                                    <h1>No enrollment details available.</h1>
                                </c:if>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->

                        <div class="row">
                            <!-- accepted payments column -->
                            <div class="col-6">
                                <p class="lead">Payment Methods:</p>
                                <img src="../../dist/img/credit/visa.png" alt="Visa">
                                <img src="../../dist/img/credit/mastercard.png" alt="Mastercard">
                                <img src="../../dist/img/credit/american-express.png" alt="American Express">
                                <img src="../../dist/img/credit/paypal2.png" alt="Paypal">


                            </div>
                            <!-- /.col -->
                            <div class="col-6">

                                <p class="lead">Amount Due: <fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/></p>

                                <div class="table-responsive">
                                    <c:set var="currentTotalAmount" value="" />

                                    <table class="table">
                                        <c:forEach var="dto" items="${enrollmentDTOs}">

                                            <c:if test="${dto.totalAmount != currentTotalAmount}">
                                                <tr>
                                                    <th style="width:50%">Total:</th>
                                                    <td>${dto.totalAmount}</td>
                                                </tr>

                                                <c:set var="currentTotalAmount" value="${dto.totalAmount}" />
                                            </c:if>
                                        </c:forEach>
                                    </table>

                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                        <div class="row no-print">
                            <div class="col-12">
                                <button onclick="window.print()" type="button" class="btn btn-primary float-right" style="margin-right: 5px;">
                                    <i class="fas fa-download"></i> Print PDF
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- /.invoice -->
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</div>
