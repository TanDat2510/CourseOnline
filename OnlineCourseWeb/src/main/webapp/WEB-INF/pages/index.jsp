<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${param.lang == 'vi'}">
    <fmt:setLocale value="vi_VN"/>
</c:if>
<c:if test="${param.lang == 'en'}">
    <fmt:setLocale value="en_US"/>
</c:if>
<fmt:setBundle basename="com.group8.lang.index" var="bnd"/>
<div class="content-wrapper" style="overflow-y: auto; padding: 10px;">

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-info">
                        <div class="inner">
                            <h3>${courseNumber}<sup style="font-size: 20px"> <fmt:message bundle="${bnd}" key="course"/> </sup></h3>
                            <p><fmt:message bundle="${bnd}" key="total.courses"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="<c:url value='/courses'/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->

                <!-- Dòng mới với một cột duy nhất -->
                <div class="col-lg-9 col-12">
                    <!-- small box -->
                    <div class="small-box bg-gradient-gray-dark">
                        <div class="inner">
                            <h3 style="font-size: 35px; padding-tops: 20px;"> <fmt:message bundle="${bnd}" key="system.management"/> </h3>
                            <p><fmt:message bundle="${bnd}" key="important.info"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-information-circled"></i>
                        </div>
                        <a href="<c:url value="/"/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
            </div>

            <!-- /.row -->
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-warning">
                        <div class="inner">
                            <h3>${studentNumber}<sup style="font-size: 20px"> <fmt:message bundle="${bnd}" key="student"/> </sup></h3>
                            <p><fmt:message bundle="${bnd}" key="total.users"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person"></i>
                        </div>
                        <a class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-warning">
                        <div class="inner">
                            <h3>${instructorNumber}<sup style="font-size: 20px"> <fmt:message bundle="${bnd}" key="total.instructors"/> </sup></h3>
                            <p><fmt:message bundle="${bnd}" key="total.instructors"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person"></i>
                        </div>
                        <a href="#" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-6 col-9">
                    <!-- small box -->
                    <div class="small-box bg-gradient-gray-dark">
                        <div class="inner">
                            <h3 style="font-size: 35px; padding-tops: 40px;"> <fmt:message bundle="${bnd}" key="online.course.web"/> </h3>
                            <p><fmt:message bundle="${bnd}" key="user.web.page"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-information-circled"></i>
                        </div>
                        <a href="<c:url value="/"/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${enrollmentNumber}<sup style="font-size: 20px"> <fmt:message bundle="${bnd}" key="enrollment"/> </sup></h3>
                            <p><fmt:message bundle="${bnd}" key="total.enrollments"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="<c:url value="/enrollments"/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${enrollmentInProgressNumber}<sup style="font-size: 20px">%</sup></h3>
                            <p><fmt:message bundle="${bnd}" key="percentage.in.progress"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="<c:url value="/enrollments"/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-success">
                        <div class="inner">
                            <h3>${enrollmentCompletedNumber}<sup style="font-size: 20px"> <fmt:message bundle="${bnd}" key="average.process"/> </sup></h3>
                            <p><fmt:message bundle="${bnd}" key="average.process"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="<c:url value="/enrollments"/>" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-gradient-gray-dark">
                        <div class="inner text-center">
                            <i class="fas fa-user" style="font-size: 42px; color: #d9d9d9; margin-bottom: 10px;"></i>
                            <p><fmt:message bundle="${bnd}" key="admin.user"/>: <sec:authentication property="principal.lastName" /> <sec:authentication property="principal.firstName" /></p>
                        </div>
                        <a href="#" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
            </div>
            <div class="row">
                <div class="col-lg-3 col-6">
                    <!-- small box -->
                    <div class="small-box bg-gradient-orange">
                        <div class="inner">
                            <h3>65</h3>
                            <p><fmt:message bundle="${bnd}" key="unique.visitors"/></p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="#" class="small-box-footer">
                            <fmt:message bundle="${bnd}" key="more.info"/> <i class="fas fa-arrow-circle-right"></i>
                        </a>
                    </div>
                </div>
                <!-- ./col -->
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
