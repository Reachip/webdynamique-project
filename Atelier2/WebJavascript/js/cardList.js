document.addEventListener("DOMContentLoaded", function () {
    fetch("http://127.0.0.1:8080/cards", {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
        .then(response => {
            if (response.ok) {
                response.json().then(data => {
                    const template = document.querySelector("#row");

                    for (const card of data) {
                        let clone = document.importNode(template.content, true);

                        newContent = clone.firstElementChild.innerHTML
                            .replace(/{{family}}/g, card.family)
                            .replace(/{{imgUrl}}/g, card.imgUrl)
                            .replace(/{{name}}/g, card.name)
                            .replace(/{{description}}/g, card.description)
                            .replace(/{{hp}}/g, card.hp)
                            .replace(/{{energy}}/g, card.energy)
                            .replace(/{{attack}}/g, card.attack)
                            .replace(/{{defense}}/g, card.defense)
                            .replace(/{{price}}/g, card.price);
                        clone.firstElementChild.innerHTML = newContent;

                        let cardContainer = document.querySelector("#table-cards-body");
                        cardContainer.appendChild(clone);
                    }
                }).catch(error => {
                    console.error("Error when parsing JSON:", error);
                });
            } else {
                showAlert(Alert.ERROR, "Impossible de récupérer les cartes.");
            }
        })
        .catch(error => {
            console.error("Fetch error:", error);
            showAlert(Alert.ERROR, "Une erreur est survenue.");
        });
});