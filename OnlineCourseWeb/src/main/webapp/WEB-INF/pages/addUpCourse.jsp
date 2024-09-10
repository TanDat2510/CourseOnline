<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
<c:if test="${param.lang == 'vi'}">
    <fmt:setLocale value="vi_VN"/>
</c:if>
<c:if test="${param.lang == 'en'}">
    <fmt:setLocale value="en_US"/>
</c:if>
<fmt:setBundle basename="com.group8.lang.addUpCourse" var="bnd"/>
<c:url value='/courses/add-up' var="action"/>   
<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1></h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="<c:url value="/" />">Home</a></li>
                        <li class="breadcrumb-item active">
                            <c:choose>
                                <c:when test="${not empty addCourseDTO.id}">
                                    <fmt:message key="update.course" bundle="${bnd}"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="create.new.course" bundle="${bnd}"/>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <c:if test="${not empty errMsg}">
            <div class="alert alert-danger">
                ${errMsg}
            </div>
        </c:if>
        <div>
            <div class="card card-primary">
                <div class="card-header">
                    <c:choose>
                        <c:when test="${not empty addCourseDTO.id}">
                            <h3 class="card-title"><fmt:message key="update.course" bundle="${bnd}"/></h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="card-title"><fmt:message key="create.new.course" bundle="${bnd}"/></h3>
                        </c:otherwise>
                    </c:choose>

                    <div class="card-tools">
                        <button type="button" class="btn btn-tool" data-card-widget="collapse" title="Collapse">
                            <i class="fas fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <!-- Form thêm khóa h?c -->
                    <form:form method="POST" modelAttribute="addCourseDTO" enctype="multipart/form-data" action="${action}">
                        <form:hidden path="id" />
                        <form:hidden path="img" />

                        <div class="form-group">
                            <label for="title"><fmt:message key="course.title" bundle="${bnd}"/></label>
                            <form:input path="title" id="title" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="description"><fmt:message key="course.description" bundle="${bnd}"/></label>
                            <form:textarea path="description" id="description" class="form-control" rows="4"/>
       
                        </div>

                        <div class="form-group">
                            <label for="timeExperted"><fmt:message key="time.expected" bundle="${bnd}"/></label>
                            <form:input path="timeExperted" id="timeExperted" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="price"><fmt:message key="price" bundle="${bnd}"/></label>
                            <form:input path="price" id="price" type="number" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label for="courseType"><fmt:message key="course.type" bundle="${bnd}"/></label>
                            <form:select path="courseType" id="courseType" class="form-control custom-select">
                                <form:options items="${courseTypesList}" />
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label for="status"><fmt:message key="status" bundle="${bnd}"/></label>
                            <form:select path="status" id="status" class="form-control custom-select">
                                <form:options items="${courseStatusList}" />
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label for="categoryId"><fmt:message key="category" bundle="${bnd}"/></label>
                            <form:select path="categoryId" id="categoryId" class="form-control custom-select">
                                <form:options items="${categories}" itemValue="id" itemLabel="name" />
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label for="instructorId"><fmt:message key="instructor" bundle="${bnd}"/></label>
                            <form:select path="instructorId" id="instructorId" class="form-control custom-select">
                                <form:options items="${instructors}" itemValue="id" itemLabel="fullName" />
                            </form:select>
                        </div>

                        <div class="form-group">
                            <label for="file"><fmt:message key="course.image" bundle="${bnd}"/></label>
                            <form:input path="file" id="file" type="file" accept=".jpg,.png" class="form-control" />
                        </div>

                        <!-- Hi?n th? hình ?nh n?u có -->
                        <c:if test="${not empty addCourseDTO.img}">
                            <div class="form-group">
                                <label><fmt:message key="current.image" bundle="${bnd}"/></label><br>
                                <img src="<c:url value='${addCourseDTO.img}' />" alt="<fmt:message key='course.image' bundle='${bnd}'/>" class="img-thumbnail" style="max-width: 200px;"/>
                            </div>
                        </c:if>

                        <div class="row">
                            <div class="col-12">
                                <a href="<c:url value='/courses'/>" class="btn btn-secondary"><fmt:message key="cancel" bundle="${bnd}"/></a>
                                <c:choose>
                                    <c:when test="${not empty addCourseDTO.id}">
                                        <input type="submit" value="<fmt:message key='update.course' bundle="${bnd}"/>" class="btn btn-success float-right">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="<fmt:message key='create.new.course' bundle="${bnd}"/>" class="btn btn-success float-right">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </form:form>
                </div>
                <!-- /.card-body -->
            </div>
            <!-- /.card -->
        </div>
    </section>
    <!-- /.content -->
</div>
