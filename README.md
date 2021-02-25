<!-- Header and logo -->
<p align="center">
  <a href="https://godzilla.serum-390.app/">
    <img src="https://raw.githubusercontent.com/wiki/serum-390/soen390-team16-godzilla/images/main-on-transparent-min.png"
         alt="Godzilla ERP"
         height="378" />
  </a>
</p>

<!-- Tagline: a little explanation about the Godzilla app -->
<p align="center">
    <em>Godzilla ERP, high performance, easy to learn, open source Enterprise Resource Management system</em>
</p>

<!-- CI/CD builds -->
<p align="center">
  <a href="https://github.com/serum-390/soen390-team16-godzilla/actions?query=workflow%3A%22Maven+build%22" target="_blank">
    <img src="https://github.com/serum-390/soen390-team16-godzilla/workflows/Maven%20build/badge.svg" alt="Maven Build">
  </a>
  <a href="https://github.com/serum-390/soen390-team16-godzilla/actions?query=workflow%3ADocker" target="_blank">
    <img src="https://github.com/serum-390/soen390-team16-godzilla/workflows/Docker/badge.svg" alt="Docker Build">
  </a>
</p>

---

*Visit the live demo environment here: <https://godzilla.serum-390.app/>*

*Login:*
- *Username:* `demo`
- *Password:* `demo`


**SOEN-390 Team-16**

**Documentation**: <https://github.com/serum-390/soen390-team16-godzilla/wiki>

**Contributing**: <https://github.com/Serum-390/godzilla/wiki/Contributors>

<!-- **Source Code**: <https://github.com/serum-390/soen390-team16-godzilla> -->

---

## Getting Started

To get started with the app, checkout the [Quickstart wiki page](https://github.com/Serum-390/godzilla/wiki).

### Docker

```bash
# Spin up a development database
docker-compose up -d

# Spin up the app
docker run --name godzilla -d -p 8080:8080 ghcr.io/serum-390/godzilla
```
