FROM ubi9/ubi:latest

#Can change the ARG value when run "podman build --build-arg CREATE_DATE=input-value" command     
ARG APP_CONFIG=/etc/httpd/conf/httpd.conf \
    APP_BIN_DIR=/var/run/httpd \
    APP_LOG=/var/log/httpd \
    CREATE_DATE=

ENV APP_ROOT=/var/www/html/ \
    APP_PORT=8080 \
    APP_USER=1001 \
    CREATE_DATE=${CREATE_DATE:-202305} 
#if there is no ARG value then if set dafault value is "202305"

RUN dnf install -y httpd && dnf clean all && \
    sed -i -e 's/^Listen 80/Listen ${APP_PORT}/' ${APP_CONFIG} && \
    chgrp -R 0 ${APP_BIN_DIR} ${APP_LOG} && \
    chmod -R g=u ${APP_SCRIPT} ${APP_LOG}


#ADD 

COPY index.html ${APP_ROOT}

LABEL io.openshift.expose-services="8080:http" \
      io.k8s.description="My http container app" \
      io.openshift.tags="httpd, web server, apache2, apache"

VOLUME ${APP_ROOT}

EXPOSE ${APP_PORT}


USER ${APP_USER}

ENTRYPOINT ["/usr/sbin/httpd"]

CMD ["-D", "FOREGROUND"]
