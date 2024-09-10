<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper" style="overflow-y: auto">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6"></div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Stats</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <!-- AREA CHART -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Registration Count Over Time Chart</h3>

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
                            <div class="chart">
                                <canvas id="areaChart" style="max-width: 100%;"></canvas>
                            </div>
                     
                        <!-- /.card-body -->
                        <form action="<c:url value='/stats' />" method="get" class="form-inline" style="justify-content: center; margin-top: 20px;">
                            <h4>Time Filter: </h4>
                            <div class="form-group mx-sm-3 mb-2">
                                <label for="year" class="sr-only">Year:</label>
                                <input type="number" id="year" name="year" class="form-control" placeholder="Enter year" value="${param.year != null ? param.year : currentYear}" />
                            </div>
                            <div class="form-group mx-sm-3 mb-2">
                                <label for="period" class="sr-only">Period:</label>
                                <select id="period" name="period" class="form-control">
                                    <option value="MONTH" <c:if test="${param.period eq 'MONTH'}">selected</c:if>>Month</option>
                                    <option value="YEAR" <c:if test="${param.period eq 'YEAR'}">selected</c:if>>Year</option>
                                    <option value="QUARTER" <c:if test="${param.period eq 'QUARTER'}">selected</c:if>>Quarter</option>
                                    </select>
                                </div>
                                
                                <button type="submit" class="btn btn-primary mb-2">Filter</button>
                            </form>
                        </div>
                        </div>
                        <!-- /.card -->

                        <!-- DONUT CHART -->
                        <div class="card card-danger">
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
                            
                            <!-- /.card-body -->

                        </div>
                        <!-- /.card -->

                        

                    </div>
                    <!-- /.col (LEFT) -->
                    <div class="col-md-6">
                        <!-- LINE CHART -->
                        <div class="card card-info">
                            <div class="card-header">
                                <h3 class="card-title">Chart of Registration Count by Course</h3>

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
                                <div class="chart">
                                    <canvas id="lineChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                                </div>
                          
                            <!-- /.card-body -->
                            <!-- FILTER FORM -->
                                <form action="<c:url value='/stats' />" method="get" class="form-inline" style="justify-content: center; margin-top: 20px;">
                                    <h4>Time Filter: </h4>
                                    <div class="form-group mx-sm-3 mb-2">
                                        <label for="fromCreatedDate" class="sr-only">From Date:</label>
                                        <input type="date" id="fromCreatedDate" name="fromCreatedDate" class="form-control" placeholder="From Date" value="${param.fromCreatedDate}" />
                                    </div>
                                    <div class="form-group mx-sm-3 mb-2">
                                        <label for="toCreatedDate" class="sr-only">To Date:</label>
                                        <input type="date" id="toCreatedDate" name="toCreatedDate" class="form-control" placeholder="To Date" value="${param.toCreatedDate}" />
                                    </div>
                                    <button type="submit" class="btn btn-primary mb-2">Filter</button>
                                </form>
                        </div>
                        </div>
                        <!-- /.card -->

                       

                        <!-- STACKED BAR CHART -->
                        <div class="card card-success">
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
                        <!-- /.card -->

                    </div>
                    <!-- /.col (RIGHT) -->
                </div>
                <!-- /.row -->
            </div><!-- /.container-fluid -->
                </section>
                        <!-- /.content -->
                        </div>
                            
    <script>
                $(function () {
                                var areaChartCanvas = document.getElementById('areaChart').getContext('2d');
                                var labels = [];
                var data = [];
                var labelText = 'Number of enrollment';
                        // Thay ??i nhãn d?a trên param.period     <c:choose>
        <c:when test="${param.period eq 'MONTH'}">
                        labelText += ' (Monthly)';
                     </c:when>
        <c:when test="${param.period eq 'YEAR'}">
        labelText += ' (Yearly)';
                        </c:when>
        <c:when test="${param.period eq 'QUARTER'}">
                        labelText += ' (Quarterly)';
        </c:when>
               <c:otherwise>
        labelText += ' (Monthly)';<!-- Giá tr? m?c ??nh -->
                        </c:otherwise>
    </c:choose>

                        // Duy?t qua enrollmentStats ?? l?y d? li?u
                        <c:forEach var="stat" items="${enrollmentStats}">
                        <c:choose>
                            <c:when test="${param.period eq 'MONTH'}">
        labels.push("Month ${stat[0]}");<!-- Ví d? tháng -->
            </c:when>
                                <c:when test="${param.period eq 'YEAR'}">
                                labels.push("Year ${stat[0]}");<!-- Ví d? n?m -->
                                </c:when>
                                <c:when test="${param.period eq 'QUARTER'}">
                                labels.push("Quarter ${stat[0]}");<!-- Ví d? quý -->
                            </c:when>
                                <c:otherwise>
                                labels.push("Month ${stat[0]}"); <!-- Giá tr? m?c ??nh -->
                                </c:otherwise>
                                    </c:choose>
                                    data.push(${stat[1]}); <!-- S? l??ng -->
                                    </c:forEach>
                                    
                                    var areaChartData = {
                                labels: labels,                            datasets: [
              {
                       label: labelText,
//                       backgroundColor: 'rgba(60,141,188,0.9)',
                       borderColor: 'rgba(60,141,188,0.8)',
                       borderWidth: 3,
                       data: data
                   }
               ]
           };
           var areaChartOptions = {
               maintainAspectRatio: true,
               responsive: true,
               legend: {
                   display: true
               },
               scales: {
                   x: {
                       grid: {
                           display: false,
                       }
                   },
                   y: {
                       grid: {
                           display: false,
                       },
                       ticks: {
                           beginAtZero: true, // ??t giá tr? b?t ??u c?a tr?c y là 0
                       }
                   }
               }
           };
           new Chart(areaChartCanvas, {
               type: 'line', // ??i ki?u thành 'bar'
               data: areaChartData,
               options: areaChartOptions
           });
       });
       
</script>
<script>
                            $(function(){
                            var areaChartCanvas = document.getElementById('lineChart').getContext('2d');
                                    var labels = [];
                                    var data = [];
                                            var labelText = 'Number enrollments of course';
                                            // Thay ??i nhãn d?a trên param.period


                                            // Duy?t qua enrollmentStats ?? l?y d? li?u
                                <c:forEach var="stat" items="${enrollmentByCourseStats}">

                                    labels.push("${stat[0]}");<!-- Ví d? tháng -->
                                data.push(${stat[1]}); <!-- S? l??ng -->
                                </c:forEach>

var areaChartData = {
                                            labels: labels,
                                            datasets: [
                                            {
                                            label: labelText,
                                                    backgroundColor: 'rgba(60,141,188,0.9)',
                                                    borderColor: 'rgba(60,141,188,1)',
                                                    borderWidth: 1,
                                                    data: data
                                            }
                                            ]
                            };
var areaChartOptions = {
                    maintainAspectRatio: true,
                    responsive: true,
                    legend: {
                    display: true
                    },
                    scales: {
                    x: {
                    grid: {
                    display: true,
                    }
               },
               y: {
                   grid: {
                       display: true,
                   },
                   ticks: {
                       beginAtZero: true, // ??t giá tr? b?t ??u c?a tr?c y là 0
                   }
               }
           }
};
new Chart(areaChartCanvas, {
           type: 'bar', // ??i ki?u thành 'bar'
           data: areaChartData,
           options: areaChartOptions
});
});
</script>


