document.addEventListener('DOMContentLoaded', function () {
    const cardForm = document.querySelector('#cardForm');
    if (!cardForm) return;

    cardForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {};
        const fields = this.querySelectorAll(".field > input");
        fields.forEach(field => {
            formData[field.name] = (field.type === 'number') ? parseInt(field.value) : field.value;
        });

        fetch('http://tp.cpe.fr:8083/card', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.status + ' ' + response.statusText);
            }
            return response.json();
        })
            .then(data => {
                console.info(data)
            })
            .catch(error => {
                console.error('Error creating card: ' + error);
            });
    });
});