# syntax=docker/dockerfile:1

FROM node:18.0-slim
ENV NODE_ENV=production
RUN apt-get update || : && apt-get install python3 -y

COPY ["package.json", "package-lock.json*", "./"]
COPY . .
ENTRYPOINT ["npm", "run", "start"]