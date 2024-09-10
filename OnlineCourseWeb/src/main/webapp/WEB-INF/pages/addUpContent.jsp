<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value='/courses/${courseId}/content/add-up' var="action"/>

<c:if test="${param.lang == 'vi'}">
    <fmt:setLocale value="vi_VN"/>
</c:if>
<c:if test="${param.lang == 'en'}">
    <fmt:setLocale value="en_US"/>
</c:if>
<fmt:setBundle basename="com.group8.lang.addUpContent" var="bnd"/>
<div class="content-wrapper" style="overflow-y: auto;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>
                        <fmt:message key="${addContentDTO.id != null ? 'update.content' : 'add.new.content'}" bundle="${bnd}"/>
                    </h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="<c:url value="/" />">Home</a></li>
                        <li class="breadcrumb-item active">
                            <fmt:message key="${addContentDTO.id != null ? 'update.content' : 'add.new.content'}" bundle="${bnd}"/>
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
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">
                ${successMsg}
            </div>
        </c:if>
        <div>
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">
                        <fmt:message key="content.form" bundle="${bnd}"/>
                    </h3>
                </div>
                <div class="card-body">
                    <form:form method="POST" modelAttribute="addContentDTO" enctype="multipart/form-data" action="${action}">
                        <form:hidden path="id" />

                        <div class="form-group">
                            <label for="title">
                                <fmt:message key="content.title" bundle="${bnd}"/>
                            </label>
                            <form:input path="title" id="title" class="form-control" />
                        </div>

                        <form:hidden path="courseId" id="courseId" value="${courseId}" />

                        <!-- Content Type and Fields -->
                        <c:choose>
                            <c:when test="${empty addContentDTO.content['id']}">
                                <div class="form-group">
                                    <label for="entityType">
                                        <fmt:message key="content.type" bundle="${bnd}"/>
                                    </label>
                                    <form:select path="entityType" id="entityType" class="form-control custom-select" onchange="toggleContentFields(this.value)">
                                        <form:option value="">Select Type</form:option>
                                        <form:option value="LECTURE"><fmt:message key="lecture" bundle="${bnd}"/></form:option>
                                        <form:option value="VIDEO"><fmt:message key="video" bundle="${bnd}"/></form:option>
                                        <form:option value="INFORMATION"><fmt:message key="information" bundle="${bnd}"/></form:option>
                                    </form:select>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form:hidden path="entityType" />
                            </c:otherwise>
                        </c:choose>
                        <form:hidden path="content['id']" />

                        <!-- Fields for LECTURE -->
                        <div id="lectureFields" style="display:none;">
                            <div class="form-group">
                                <label for="lectureContent"><fmt:message key="lecture.content" bundle="${bnd}"/></label>
                                <form:textarea path="content['lectureContent']" id="lectureContent" class="form-control" rows="4" />
                                <script>
                                    CKEDITOR.replace('lectureContent');
                                </script>
                            </div>
                        </div>

                        <!-- Fields for VIDEO -->
                        <div id="videoFields" style="display:none;">
                            <div class="form-group">
                                <label for="videoUrl">
                                    <fmt:message key="video.url" bundle="${bnd}"/>
                                </label>
                                <form:input path="content['videoUrl']" id="videoUrl" class="form-control" />
                            </div>
                        </div>

                        <!-- Fields for VIDEO -->
                        <div id="inforFields" style="display:none;">
                            <div class="form-group">
                                <label for="inforContent"><fmt:message key="information.content" bundle="${bnd}"/></label>
                                <form:textarea path="content['inforContent']" id="inforContent" class="form-control" rows="4" />
                                <script>
                                    CKEDITOR.replace('inforContent');
                                </script>
                            </div>
                        </div>    

                        <div class="form-group">
                            <label for="status">
                                <fmt:message key="status" bundle="${bnd}"/>
                            </label>
                            <form:select path="status" id="status" class="form-control custom-select">
                                <form:option value="true">Active</form:option>
                                <form:option value="false">Inactive</form:option>
                            </form:select>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <a href="<c:url value="/content"/>" class="btn btn-secondary">
                                    <fmt:message key="cancel" bundle="${bnd}"/>
                                </a>
                                <c:choose>
                                    <c:when test="${addContentDTO.id != null}">
                                        <c:set var="submitButtonLabel" value="update.content"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="submitButtonLabel" value="submit.content"/>
                                    </c:otherwise>
                                </c:choose>

                                <input type="submit" value="<fmt:message key='${submitButtonLabel}' bundle='${bnd}'/>" class="btn btn-success float-right">

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

<script>
    function toggleContentFields(type) {
        if (type === 'LECTURE') {
            document.getElementById('lectureFields').style.display = 'block';
            document.getElementById('videoFields').style.display = 'none';
            document.getElementById('inforFields').style.display = 'none';
        } else if (type === 'VIDEO') {
            document.getElementById('lectureFields').style.display = 'none';
            document.getElementById('videoFields').style.display = 'block';
            document.getElementById('inforFields').style.display = 'none';
        } else if (type === 'INFORMATION') {
            document.getElementById('lectureFields').style.display = 'none';
            document.getElementById('videoFields').style.display = 'none';
            document.getElementById('inforFields').style.display = 'block';
        } else {
            document.getElementById('lectureFields').style.display = 'none';
            document.getElementById('videoFields').style.display = 'none';
            document.getElementById('inforFields').style.display = 'none';

        }
    }

    // Initialize the correct content fields based on entityType
    document.addEventListener("DOMContentLoaded", function () {
        toggleContentFields("${addContentDTO.entityType}");
    });
</script>
