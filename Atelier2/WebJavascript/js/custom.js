document.addEventListener("DOMContentLoaded", function () {
  const header = document.createElement("header");

  const navbar = document.createElement("nav");
  navbar.classList.add("navbar");

  const logoTitleContainer = document.createElement("div");
  logoTitleContainer.classList.add("logo-title-container");

  const logo = document.createElement("img");
  logo.classList.add("logo");
  logo.src = 'assets/logo.png';

  const title = document.createElement("div");
  title.classList.add("title");
  title.classList.add("scooby");
  title.textContent = "ScoobyCards";

  logoTitleContainer.appendChild(logo);
  logoTitleContainer.appendChild(title);

  const navLinks = document.createElement("ul");
  navLinks.classList.add("nav-links");

  const links = [
    { text: "<i class=\"fa-solid fa-house\"></i> Accueil", href: "index.html" },
    { text: "<i class=\"fa-solid fa-list\"></i> Liste des cartes", href: "cards.html" },
    { text: "<i class=\"fa-solid fa-layer-group\"></i> Mes cartes", href: "cardList.html" }
  ];

  links.forEach(link => {
    const li = document.createElement("li");
    const a = document.createElement("a");
    a.innerHTML = link.text;
    a.href = link.href;
    li.appendChild(a);
    navLinks.appendChild(li);
  });

  const headerBadgesContainer = document.createElement("div");
  headerBadgesContainer.classList.add("header-badges-container");

  const usernameBadge = document.createElement("a");
  usernameBadge.classList.add("header-badge");
  usernameBadge.classList.add("pointer");
  usernameBadge.classList.add("no-decoration");
  usernameBadge.innerHTML = "<i class=\"fa-solid fa-login\"></i> Se connecter";
  usernameBadge.href = "login.html";

  const balanceBadge = document.createElement("div");
  balanceBadge.classList.add("header-badge");
  balanceBadge.innerHTML = "Solde : 100,00 €";
  balanceBadge.classList.add("hidden");
  
  headerBadgesContainer.appendChild(usernameBadge);
  headerBadgesContainer.appendChild(balanceBadge);

  navbar.appendChild(logoTitleContainer);
  navbar.appendChild(navLinks);
  navbar.appendChild(headerBadgesContainer);
  header.appendChild(navbar);

  document.body.insertBefore(header, document.body.firstChild);
});