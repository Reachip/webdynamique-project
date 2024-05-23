import { showAlert, Alert } from './alerts.js';

document.addEventListener("DOMContentLoaded", function () {
    const userToken = localStorage.getItem("scoobycards-user-token");
    const hasToken = !!userToken;
    if (hasToken) {
        fetch("http://127.0.0.1:8080/cards/user", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + userToken,
                "Content-type": "application/json; charset=UTF-8"
            }
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        const template = document.querySelector("#row");

                        fetch("http://127.0.0.1:8080/stores", {
                            method: "GET",
                            headers: {
                                "Content-type": "application/json; charset=UTF-8"
                            }
                        })
                            .then(response => {
                                if (response.ok) {
                                    response.json().then(stores => {
                                        for (const card of data) {
                                            const clone = document.importNode(template.content, true);

                                            const newContent = clone.firstElementChild.innerHTML
                                                .replace(/{{family}}/g, card.family)
                                                .replace(/{{affinity}}/g, card.affinity)
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

                                            /* Handle card preview */
                                            const rows = document.querySelectorAll('.card-row');
                                            const preview = document.querySelector('#card-preview');
                                            const previewImage = document.querySelector('#preview-image');
                                            const previewName = document.querySelector('#preview-name');
                                            const previewDescription = document.querySelector('#preview-description');
                                            const previewFamily = document.querySelector('#preview-family');
                                            const previewAffinity = document.querySelector('#preview-affinity');
                                            const previewHp = document.querySelector('#preview-hp');
                                            const previewEnergy = document.querySelector('#preview-energy');
                                            const previewDefense = document.querySelector('#preview-defense');
                                            const previewAttack = document.querySelector('#preview-attack');
                                            const previewRadios = document.querySelector('#preview-radios');

                                            var radioInputs = ``;
                                            let isFirstRadio = true;
                                            stores.forEach(store => {
                                                radioInputs += `<input type="radio" id="store-radio-${store.id}" name="store" value="${store.id}" data-card="${card.id}" ${isFirstRadio ? "checked" : ""}><label for="${store.id}">${store.name}</label><br>`;
                                                isFirstRadio = false;
                                            });

                                            rows.forEach(row => {
                                                row.querySelector(".button-details").addEventListener("click", () => {
                                                    previewImage.src = row.querySelector('.row-image').src;
                                                    previewName.textContent = row.querySelector('span').textContent;
                                                    previewDescription.textContent = row.children[1].textContent;
                                                    previewFamily.textContent = row.children[2].textContent;
                                                    previewAffinity.textContent = row.children[3].textContent;
                                                    previewHp.textContent = row.children[4].textContent;
                                                    previewEnergy.textContent = row.children[5].textContent;
                                                    previewDefense.textContent = row.children[6].textContent;
                                                    previewAttack.textContent = row.children[7].textContent;
                                                    previewRadios.innerHTML = radioInputs;

                                                    preview.classList.remove('hidden');
                                                });
                                            });

                                            const sellButtons = document.querySelectorAll(".button-sell");
                                            sellButtons.forEach(sellButton => {
                                                sellButton.addEventListener("click", function (event) {
                                                    let storeId, cardId;
                                                    const radios = event.target.parentElement.querySelectorAll("input[type='radio']");
                                                    for (let i = 0; i < radios.length; i++) {
                                                        const radio = radios[i];
                                                        if (radio.checked) {
                                                            storeId = radio.value;
                                                            cardId = radio.getAttribute("data-card");
                                                            break;
                                                        }
                                                    }

                                                    fetch("http://127.0.0.1:8080/store/sell", {
                                                        method: "POST",
                                                        body: JSON.stringify({
                                                            cardId: cardId,
                                                            storeId: storeId
                                                        }),
                                                        headers: {
                                                            "Authorization": "Bearer " + userToken,
                                                            "Content-type": "application/json; charset=UTF-8"
                                                        }
                                                    })
                                                        .then(response => {
                                                            if (response.ok) {
                                                                showAlert(Alert.SUCCESS, "Carte ajoutée à la vente.");
                                                                setTimeout(() => {
                                                                    window.location.reload();
                                                                }, 2000);
                                                            } else {
                                                                showAlert(Alert.ERROR, "Échec de la mise en vente de la carte.");
                                                            }
                                                        })
                                                        .catch(error => {
                                                            console.error("Fetch error:", error);
                                                            showAlert(Alert.ERROR, "Une erreur est survenue.");
                                                        });
                                                });
                                            });
                                        }
                                    });
                                }
                            });
                    }).catch(error => {
                        console.error("Error when parsing JSON:", error);
                    });
                } else {
                    showAlert(Alert.ERROR, "Une erreur est survenue. Impossible de charger la liste des cartes.");
                }
            })
            .catch(error => {
                console.error("Fetch error:", error);
                showAlert(Alert.ERROR, "Une erreur est survenue. Impossible de charger la liste des cartes.");
            });
    } else {
        window.location.replace("./login.html");
    }
});