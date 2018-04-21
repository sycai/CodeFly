function checkValidSession(obj) {
    if (obj.userActive === false) {
        window.location.href = "/login";
    }
}