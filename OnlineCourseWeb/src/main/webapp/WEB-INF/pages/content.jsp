<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <!-- Bao g?m CSS và JavaScript c?n thi?t -->


    <style>
        .modal-dialog.modal-xl {
            max-width: 40%; /* T?ng kích th??c modal */
        }
        .modal-content {
            height: auto; /* ?i?u ch?nh chi?u cao c?a modal n?u c?n */
            padding-bottom: 50px;
        }
        #myChart {
            max-width: 100%;
            height: 100%; /* ??m b?o canvas chi?m toàn b? không gian c?a modal */

        }
    </style>
</head>

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <a href="<c:url value='content/add-up'/>" class="btn btn-sm btn-success float-left p-3">
                        <i class="fas fa-plus-circle"></i> <!-- Bi?u t??ng thêm n?i dung -->
                    </a>
                    <button type="button" class="btn btn-sm btn-success float-left p-3 ml-2" data-toggle="modal" data-target=".bd-example-modal-xl">
                        <i class="fas fa-chart-bar"></i> <!-- Bi?u t??ng th?ng kê -->
                    </button>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right" >
                        <li class="breadcrumb-item"><a href="<c:url value="/"/>">Home</a></li>
                        <li class="breadcrumb-item active"><a href="<c:url value="/courses"/>">Courses</a></li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>


    <!-- Modal -->
    <div class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-labelledby="myExtraLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl" role="document">
            <div class="modal-content">
                <div class="modal-header" style="background-color: cyan">
                    <h5 class="modal-title">Content Chart</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- Bi?u ?? -->
                    <canvas id="myChart"></canvas>

                    <!-- B?ng hi?n th? d? li?u -->
                    <table class="table table-bordered mt-4">
                        <thead>
                            <tr>
                                <th>Content Type</th>
                                <th>Count</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stat" items="${statsContent}">
                                <tr>
                                    <td>${stat[0]}</td> <!-- entityType -->
                                    <td>${stat[1]} Content</td> <!-- S? l??ng -->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Bi?u ?? -->

    <!-- List content -->
    <c:forEach var="item" items="${contents}" varStatus="status">
        <div class="card collapsed-card" id="content${item.id}" style="margin: 20px;">
            <div class="card-header border-transparent bg-cyan" >
                <h3 class="card-title">${status.index + 1}. ${item.title}</h3>
                <div class="card-tools">
                    <button type="button" class="btn btn-tool" data-card-widget="collapse">
                        <c:choose>
                            <c:when test="${item.entityType == 'LECTURE'}">
                                <i class="fas fa-book"></i> <!-- Icon for lecture -->
                            </c:when>
                            <c:when test="${item.entityType == 'VIDEO'}">
                                <i class="fas fa-video"></i> <!-- Icon for video -->
                            </c:when>
                            <c:when test="${item.entityType == 'INFORMATION'}">
                                <i class="fas fa-info"></i> <!-- Icon for information -->
                            </c:when>    
                            <c:otherwise>
                                <i class="fas fa-plus"></i> <!-- Default icon -->
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </div>
            <div class="card-body border-transparent bg-gradient-light" style="position: relative;">
                <c:choose>
                    <c:when test="${item.entityType eq 'LECTURE'}">
                        <p>${item.content.content}</p>
                    </c:when>
                    <c:when test="${item.entityType eq 'VIDEO'}">
                        <a href="${item.content.url}" target="_blank">${item.content.url}</a>
                    </c:when>
                    <c:when test="${item.entityType eq 'INFORMATION'}">
                        <p>${item.content.content}</p>
                    </c:when>    
                    <c:otherwise>
                        <p>No content available.</p>
                    </c:otherwise>
                </c:choose>
                <a href="<c:url value='content/${item.id}/add-up'/>" class="btn btn-link" style="position: absolute; right: 50px; bottom: 10px;">
                    <i class="fas fa-edit"></i> <!-- Edit icon -->
                </a>
                <c:url value="/api/courses/${item.courseId}/content/${item.id}" var="cD" />
                <c:url value="content${item.id}" var="cE" />
                <a href="#" onclick="deleteContent('${cD}', '${cE}')" class="btn btn-link text-danger" style="position: absolute; right: 10px; bottom: 10px;" onclick="return confirm('Are you sure you want to delete this content?');">
                    <i class="fas fa-trash"></i> <!-- Delete icon -->
                </a>
            </div>
        </div>
    </c:forEach>
</div>

<!-- K?ch b?n JavaScript ?? v? bi?u ?? -->
<script>
    $(function () {
        // Kh?i t?o các m?ng ?? ch?a labels và data
        var labels = [];
        var data = [];

        // Duy?t qua statsContent ?? l?y entityType và s? l??ng
    <c:forEach var="stat" items="${statsContent}">
        labels.push("${stat[0]}"); // entityType
        data.push(${stat[1]}); // S? l??ng
    </c:forEach>

        var ctx = document.getElementById('myChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'pie', // Lo?i bi?u ??
            data: {
                labels: labels, // Nhãn cho các m?c
                datasets: [{
                        label: '# of Contents',
                        data: data, // D? li?u c?a bi?u ??
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(201, 203, 207, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(255, 205, 86, 1)',
                            'rgba(201, 203, 207, 1)'
                        ],
                        borderWidth: 1
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
</script>
