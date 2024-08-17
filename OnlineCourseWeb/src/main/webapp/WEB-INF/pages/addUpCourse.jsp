<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:url value='/courses/add-up' var="action"/>
<div class="content-wrapper ">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Add Course</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="<c:url value="/" />">Home</a></li>
                        <li class="breadcrumb-item active">Add Course</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div >
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">General</h3>

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
                            <label for="title">Course Title</label>
                            <form:input path="title" id="title" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="description">Course Description</label>
                            <form:textarea path="description" id="description" class="form-control" rows="4" />
                        </div>
                        <div class="form-group">
                            <label for="timeExperted">Time Expected</label>
                            <form:input path="timeExperted" id="timeExperted" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="price">Price</label>
                            <form:input path="price" id="price" type="number" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label for="status">Status</label>
                            <form:select path="status" id="status" class="form-control custom-select">
                                <form:option value="ACTIVE">Active</form:option>
                                <form:option value="INACTIVE">Inactive</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for="courseType">Course Type</label>
                            <form:select path="courseType" id="courseType" class="form-control custom-select">
                                <form:option value="ONLINE">Online</form:option>
                                <form:option value="ON_OFF">On/Off</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for="categoryId">Category</label>
                            <form:select path="categoryId" id="categoryId" class="form-control custom-select">
                                <form:options items="${categories}" itemValue="id" itemLabel="name" />
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for="instructorId">Instructor</label>
                            <form:select path="instructorId" id="instructorId" class="form-control custom-select">
                                <form:options items="${instructors}" itemValue="id" itemLabel="userId" />
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for="file">Course Image</label>
                            <form:input path="file" id="file" type="file" class="form-control" />
                        </div>
                        
                        <!-- Hi?n th? hình ?nh n?u có -->
                        <c:if test="${not empty addCourseDTO.img}">
                            <div class="form-group">
                                <label>Current Image</label><br>
                                <img src="<c:url value='${addCourseDTO.img}' />" alt="Course Image" class="img-thumbnail" style="max-width: 200px;"/>
                            </div>
                        </c:if>
                        
                        <div class="row">
                            <div class="col-12">
                                <a href="#" class="btn btn-secondary">Cancel</a>
                                <input type="submit" value="Create new Course" class="btn btn-success float-right">
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
