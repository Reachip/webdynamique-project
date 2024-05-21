document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.querySelector("#form-login");
    const alertSuccess = document.querySelector(".alert-success");
    const alertError = document.querySelector(".alert-error");

    loginForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const surname = document.querySelector("#form-login-surname").value;
        const password = document.querySelector("#form-login-password").value;

        fetch("http://127.0.0.1:8080/login", {
            method: "POST",
            body: JSON.stringify({
                surname: surname,
                password: password
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
        .then(response => {
            if (response.ok) {
                alertSuccess.classList.remove("hidden");
                alertError.classList.add("hidden");
            
                response.json().then(data => {
                    localStorage.setItem("scoobycards-user-token", data.token);
                }).catch(error => {
                    console.error("Error when parsing JSON:", error);
                });
            } else {
                alertError.classList.remove("hidden");
                alertSuccess.classList.add("hidden");
            }
        });
    });
});