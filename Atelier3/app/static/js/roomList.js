import { showAlert, Alert } from './alerts.js';

const userToken = localStorage.getItem("scoobycards-user-token");

document.addEventListener("DOMContentLoaded", function () {
    fetch(`http://127.0.0.1:8080/rooms`, {
        method: "GET"
    })
        .then(response => {
            if (response.ok) {
                response.json().then(data => {
                    const template = document.querySelector("#row");
                    const roomContainer = document.querySelector("#table-rooms-body");
                    roomContainer.querySelectorAll('.room-row').forEach(row => {
                        row.remove();
                    });

                    for (const room of data) {
                        const clone = document.importNode(template.content, true);
                        
                        const newContent = clone.firstElementChild.innerHTML
                            .replace(/{{room}}/g, room.name)
                            .replace(/{{player}}/g, room.owner.username)
                            .replace(/{{bet}}/g, room.bet)
                        clone.firstElementChild.innerHTML = newContent;

                        roomContainer.appendChild(clone);

                        /* Handle room preview */
                        const rows = document.querySelectorAll('.room-row');
                        const preview = document.querySelector('#room-preview');
                        const previewImage = document.querySelector('#preview-image');
                        const previewName = document.querySelector('#preview-name');
                        const previewDescription = document.querySelector('#preview-description');
                        const previewFamily = document.querySelector('#preview-family');
                        const previewAffinity = document.querySelector('#preview-affinity');
                        const previewHp = document.querySelector('#preview-hp');
                        const previewEnergy = document.querySelector('#preview-energy');
                        const previewDefense = document.querySelector('#preview-defense');
                        const previewAttack = document.querySelector('#preview-attack');
                        const previewPrice = document.querySelector('#preview-price');
                        const id = document.querySelector('#image-id');

                        rows.forEach(row => {
                            row.querySelector(".button-details").addEventListener("click", () => {
                                // previewImage.src = row.querySelector('.row-image').src;
                                // previewName.textContent = row.querySelector('span').textContent;
                                // previewDescription.textContent = row.children[2].textContent;
                                // previewFamily.textContent = row.children[3].textContent;
                                // previewAffinity.textContent = row.children[4].textContent;
                                // previewHp.textContent = row.children[5].textContent;
                                // previewEnergy.textContent = row.children[6].textContent;
                                // previewDefense.textContent = row.children[7].textContent;
                                // previewAttack.textContent = row.children[8].textContent;
                                // previewPrice.textContent = row.children[9].textContent.replace('$', '');
                                // id.dataset.imageId = row.querySelector('.hidden').dataset.id;

                                preview.classList.remove('hidden');
                            });
                        });
                    }

                    document.querySelectorAll(".button-play").forEach(playButton => {
                        if (userToken == null) {
                            playButton.classList.add("unclickable")
                            return;
                        }

                        playButton.addEventListener("click", () => {
                            const roomId = parseInt(playButton.parentNode.parentNode.querySelector("#image-id").dataset.imageId);

                            fetch("http://127.0.0.1:8080/room", {
                                method: "POST",
                                body: JSON.stringify({
                                    roomId
                                }),
                                headers: {
                                    "Authorization": "Bearer " + userToken,
                                    "Content-type": "application/json; charset=UTF-8"
                                }
                            })
                                .then(() => {
                                    // //
                                });
                        })
                    })
                }).catch(error => {
                    console.error("Error when parsing JSON:", error);
                });
            } else {
                showAlert(Alert.ERROR, "Une erreur est survenue. Impossible de charger la liste des parties.");
            }
        })
        .catch(error => {
            console.error("Fetch error:", error);
            showAlert(Alert.ERROR, "Une erreur est survenue. Impossible de charger la liste des parties.");
        });
});