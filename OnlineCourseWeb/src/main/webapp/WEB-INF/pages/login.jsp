<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${param.lang == 'vi'}">
    <fmt:setLocale value="vi_VN"/>
</c:if>
<c:if test="${param.lang == 'en'}">
    <fmt:setLocale value="en_US"/>
</c:if>
<fmt:setBundle basename="com.group8.lang.login" var="bnd"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><fmt:message bundle="${bnd}" key="title"/></title>
        <link rel="stylesheet" href="<c:url value="/dist/css/adminlte.min.css"/>">
        <link rel="stylesheet" href="<c:url value='/plugins/fontawesome-free/css/all.min.css' />">
        <link rel="stylesheet" href="<c:url value='/plugins/icheck-bootstrap/icheck-bootstrap.min.css' />">
        <style>
            .language-selector {
                position: absolute;
                top: 10px;
                right: 10px;
            }
            .language-selector label {
                margin-right: 10px;
            }
        </style>
    </head>
    <body class="hold-transition login-page">
        <div class="language-selector">
            <form action="" method="get">
                <label>
                    <input type="radio" name="lang" value="vi" <c:if test="${param.lang == 'vi'}">checked</c:if> onclick="this.form.submit()">
                    Vietnamese
                </label>
                <label>
                    <input type="radio" name="lang" value="en" <c:if test="${param.lang == 'en'}">checked</c:if> onclick="this.form.submit()">
                    English
                </label>
            </form>
        </div>
        <div class="login-box">
            <div class="login-logo">
                <a href="#"><b><fmt:message bundle="${bnd}" key="admin.title"/></b></a>
            </div>
            <div class="card">
                <div class="card-body login-card-body">
                    <p class="login-box-msg"><fmt:message bundle="${bnd}" key="signin.message"/></p>

                    <form action="<c:url value="/login"/>" method="post">
                        <div class="input-group mb-3">
                            <input type="text" name="username" class="form-control" placeholder="<fmt:message bundle='${bnd}' key='username.placeholder'/>" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" name="password" class="form-control" placeholder="<fmt:message bundle='${bnd}' key='password.placeholder'/>" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="remember" name="remember-me">
                                    <label for="remember">
                                        <fmt:message bundle="${bnd}" key="remember.me"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-4">
                                <button type="submit" class="btn btn-primary btn-block">
                                    <fmt:message bundle="${bnd}" key="signin.button"/>
                                </button>
                            </div>
                        </div>
                    </form>

                    <p class="mb-1">
                        <a href="#"><fmt:message bundle="${bnd}" key="forgot.password"/></a>
                    </p>
                </div>
            </div>  
        </div>

        <script src="<c:url value="/plugins/jquery/jquery.min.js"/>"></script>
        <script src="<c:url value='/plugins/bootstrap/js/bootstrap.bundle.min.js' />"></script>
        <script src="<c:url value='/dist/js/adminlte.js' />"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has('error') && urlParams.get('error') === 'true') {
                    alert('<fmt:message bundle="${bnd}" key="login.error"/>');
                }
            });
        </script>
    </body>
</html>
