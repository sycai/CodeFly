function checkValidSession(func) {
    $.ajax({
        type: 'GET',
        url: '/verification',
        success: function(data) {
            var obj = JSON.parse(data);
            if (obj.userActive === true) {
                func();
            } else {
                window.location.href = '/login';
            }
        },
        error: function() {
           console.log('Error: ');
        }
    });
}