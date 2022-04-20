# BUILDER IMAGE
FROM node:alpine AS builder

WORKDIR /usr/src/app

ENV NODE_ENV=production

COPY package* ./

RUN npm i

COPY . ./

COPY .env.production.local .env.local

RUN npm run build


# PRODUCTION IMAGE
FROM caddy:alpine

COPY Caddyfile ./

COPY --from=builder /usr/src/app/build ./build

EXPOSE 3000

ENTRYPOINT ["caddy", "run", "--config", "./Caddyfile"] 
