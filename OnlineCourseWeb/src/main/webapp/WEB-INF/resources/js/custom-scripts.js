document.addEventListener('DOMContentLoaded', function () {
    function autoHideAlert(selector) {
        const alert = document.querySelector(selector);
        if (alert) {
            setTimeout(function () {
                alert.style.opacity = '0';
                setTimeout(function () {
                    alert.style.display = 'none';
                }, 500);
            }, 5000);
        }
    }

    autoHideAlert('.alert-success');
    autoHideAlert('.alert-danger');
});

