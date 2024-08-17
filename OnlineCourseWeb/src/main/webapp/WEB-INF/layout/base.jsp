
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AdminLTE 3 | Dashboard</title>

        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <link rel="stylesheet" href="<c:url value='/plugins/fontawesome-free/css/all.min.css' />">
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="<c:url value='/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/icheck-bootstrap/icheck-bootstrap.min.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/jqvmap/jqvmap.min.css' />">
        <link rel="stylesheet" href="<c:url value='/dist/css/adminlte.min.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/overlayScrollbars/css/OverlayScrollbars.min.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/daterangepicker/daterangepicker.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/summernote/summernote-bs4.min.css' />">
    </head>
    <body class="hold-transition sidebar-mini layout-fixed" data-panel-auto-height-mode="height">  
        <div class="wrapper">    
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="content" />
            <tiles:insertAttribute name="footer" />

            <!-- Control Sidebar -->
            <aside class="control-sidebar control-sidebar-dark">
                <!-- Control sidebar content goes here -->
            </aside>
            <!-- /.control-sidebar -->
        </div>
        <!-- ./wrapper -->

        <script src="<c:url value='/plugins/jquery/jquery.min.js' />"></script>

        <script src="<c:url value='/plugins/jquery-ui/jquery-ui.min.js' />"></script>

        <script>
            $.widget.bridge('uibutton', $.ui.button);
        </script>

        <script src="<c:url value='/plugins/bootstrap/js/bootstrap.bundle.min.js' />"></script>

        <script src="<c:url value='/plugins/chart.js/Chart.min.js' />"></script>

        <script src="<c:url value='/plugins/sparklines/sparkline.js' />"></script>

        <script src="<c:url value='/plugins/jqvmap/jquery.vmap.min.js' />"></script>
        <script src="<c:url value='/plugins/jqvmap/maps/jquery.vmap.usa.js' />"></script>
        <!-- jQuery Knob Chart -->
        <script src="<c:url value='/plugins/jquery-knob/jquery.knob.min.js' />"></script>
        <!-- daterangepicker -->
        <script src="<c:url value='/plugins/moment/moment.min.js' />"></script>
        <script src="<c:url value='/plugins/daterangepicker/daterangepicker.js' />"></script>
        <!-- Tempusdominus Bootstrap 4 -->
        <script src="<c:url value='/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js' />"></script>
        <!-- Summernote -->
        <script src="<c:url value='/plugins/summernote/summernote-bs4.min.js' />"></script>
        <!-- overlayScrollbars -->
        <script src="<c:url value='/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js' />"></script>
        <!-- AdminLTE App -->
        <script src="<c:url value='/dist/js/adminlte.js' />"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="<c:url value='/dist/js/demo.js' />"></script>
        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="<c:url value='/dist/js/pages/dashboard.js' />"></script>
        <script>
            // Thiết lập thời gian không hoạt động tối đa (15 phút)
            const MAX_INACTIVE_TIME = 2 * 60 * 1000; // 15 phút
            let lastActivityTime = Date.now();

            // Cập nhật thời gian hoạt động cuối cùng khi người dùng thực hiện một hành động
            function updateLastActivityTime() {
                lastActivityTime = Date.now();
            }

            // Kiểm tra thời gian không hoạt động của người dùng
            function checkInactivity() {
                const currentTime = Date.now();
                const inactiveTime = currentTime - lastActivityTime;

                if (inactiveTime >= MAX_INACTIVE_TIME) {
                    // Yêu cầu người dùng đăng nhập lại
                    alert('Phiên đăng nhập của bạn đã hết hạn. Vui lòng đăng nhập lại.');
                    // Chuyển hướng đến trang đăng nhập (cập nhật URL theo hệ thống của bạn)
                    window.location.href = "<c:url value="/login"/>";
                } else {
                    // Thiết lập lại thời gian kiểm tra sau 1 phút
                    setTimeout(checkInactivity, 2 * 60 * 1000);
                }
            }

            // Lắng nghe các sự kiện hoạt động của người dùng
            window.addEventListener('mousemove', updateLastActivityTime);
            window.addEventListener('keydown', updateLastActivityTime);
            window.addEventListener('click', updateLastActivityTime);

            // Bắt đầu kiểm tra thời gian không hoạt động sau 1 phút
            setTimeout(checkInactivity, 2 * 60 * 1000);

        </script>

    </body>
</html>