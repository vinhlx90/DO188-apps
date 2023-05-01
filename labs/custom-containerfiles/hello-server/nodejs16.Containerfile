FROM ubi8/nodejs-16

LABEL org.opencontainers.iamge.authors="LeXuanVinh"
LABEL environment="production"
LABEL com.example.version="0.0.1"

ENV SERVER_PORT=3000
ENV NODE_ENV="production"

WORKDIR /opt/app-root/src

COPY . .

EXPOSE $SERVER_PORT

RUN npm install

CMD npm start
