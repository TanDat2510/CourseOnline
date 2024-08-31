<%-- 
    Document   : stats
    Created on : Aug 28, 2024, 1:13:51 AM
    Author     : TAN DAT
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>ChartJS</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">ChartJS</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <!-- Column Chart -->
                <div class="col-md-6">
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Column chart statistics by Month</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-tool" data-card-widget="remove">
                                    <i class="fas fa-times"></i>
                                </button>      
                            </div>
                        </div>
                        <div class="card-body">
                            <canvas id="revenueColumnChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                            <script>
                                const columnLabels = [];
                                const columnData = [];

                                <c:forEach var="stat" items="${revenueStats}">
                                columnLabels.push("${stat[1]}-${stat[0]}"); // Year-Month
                                    columnData.push("${stat[2]}");   // Total Revenue
                                </c:forEach>

                                    const columnCtx = document.getElementById('revenueColumnChart').getContext('2d');
                                    const revenueColumnChart = new Chart(columnCtx, {
                                        type: 'bar', // Type of chart: bar chart
                                        data: {
                                            labels: columnLabels, // Year-Month
                                            datasets: [{
                                                    label: 'Total Revenue',
                                                    data: columnData, // Revenue values
                                                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                                    borderColor: 'rgba(54, 162, 235, 1)',
                                                    borderWidth: 1
                                                }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true // Start y-axis from zero
                                                }
                                            }
                                        }
                                    });
                            </script>
                        </div>
                    </div>
                </div>

                <!-- Area Chart -->
                <div class="col-md-6">
                    <div class="card card-danger">
                        <div class="card-header">
                            <h3 class="card-title">Domain chart statistics by Month</h3>
                            <div class="card-tools">
                                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-tool" data-card-widget="remove">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <canvas id="revenueAreaChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                            <script>
                                const areaLabels = [];
                                const areaData = [];

                                <c:forEach var="stat" items="${revenueStats}">
                                areaLabels.push("${stat[1]}-${stat[0]}"); // Year-Month
                                    areaData.push("${stat[2]}");   // Total Revenue
                                </c:forEach>

                                    const areaCtx = document.getElementById('revenueAreaChart').getContext('2d');
                                    const revenueAreaChart = new Chart(areaCtx, {
                                        type: 'line', // Line chart will be used for area chart
                                        data: {
                                            labels: areaLabels, // Year-Month
                                            datasets: [{
                                                    label: 'Total Revenue',
                                                    data: areaData, // Revenue values
                                                    fill: true, // Fill the area under the line
                                                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                                    borderColor: 'rgba(54, 162, 235, 1)',
                                                    borderWidth: 1,
                                                    tension: 0.4 // Smooth line
                                                }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true // Start y-axis from zero
                                                }
                                            }
                                        }
                                    });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>