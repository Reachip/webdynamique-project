document.addEventListener('DOMContentLoaded', function () {
    fetch('http://tp.cpe.fr:8083/cards', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error(response.status + ' ' + response.statusText);
        }
        return response.json();
    })
        .then(data => {
            const randomCard = selectRandomCard(data);
            
            let template = document.querySelector("#card-template");
            let clone = document.importNode(template.content, true);

            newContent = clone.firstElementChild.innerHTML
                .replace(/{{name}}/g, randomCard.name)
                .replace(/{{description}}/g, randomCard.description)
                .replace(/{{family}}/g, randomCard.family)
                .replace(/{{affinity}}/g, randomCard.affinity)
                .replace(/{{imgUrl}}/g, randomCard.imgUrl)
                .replace(/{{smallImgUrl}}/g, randomCard.smallImgUrl)
                .replace(/{{id}}/g, randomCard.id)
                .replace(/{{energy}}/g, randomCard.energy)
                .replace(/{{hp}}/g, randomCard.hp)
                .replace(/{{defence}}/g, randomCard.defence)
                .replace(/{{attack}}/g, randomCard.attack)
                .replace(/{{price}}/g, randomCard.price)
                .replace(/{{userId}}/g, randomCard.userId);

            clone.firstElementChild.innerHTML = newContent;
        
            let cardContainer= document.querySelector("#card-container");
            cardContainer.appendChild(clone);
        })
        .catch(error => {
            console.error('Error getting cards: ' + error);
        });

    function selectRandomCard(cards) {
        const randomIndex = Math.floor(Math.random() * cards.length);
        return cards[randomIndex];
    }

});