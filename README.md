# API de Autenticação com Spring Boot + JWT + Spring Security

### O que tem funcionando hoje (100% testado):

- Cadastro de usuários (`POST /api/usuarios`)
- Login com CPF e senha (`POST /api/login`) → devolve token JWT
- Geração de token JWT com expiração de 8 horas
- Endpoints protegidos automaticamente com filtro JWT
- Spring Security configurado do jeito enterprise (stateless, sem sessão)
- Exemplo de rota protegida: `GET /api/perfil` → só entra com token válido
- Banco H2 em memória (local) e PostgreSQL pronto pro Render

### Próximos passos (já na fila):
- [ ] BCrypt pra criptografar as senhas
- [ ] Chave secreta do JWT no application.properties + variáveis de ambiente
- [ ] Deploy no Render com PostgreSQL real
- [ ] Front-end simples pra consumir a API

### Tecnologias usadas:
- Java 17 + Spring Boot 3.5.4
- Spring Data JPA + H2 (dev) / PostgreSQL (prod)
- JJWT 0.12.6
- Spring Security + filtro personalizado

### Como rodar:
```bash
git clone https://github.com/seu-usuario/auth-api.git
./mvnw spring-boot:run
