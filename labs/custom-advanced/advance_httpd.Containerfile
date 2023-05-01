FROM ubi9/ubi:latest

ENV APP_ROOT=/var/www/html/ \
    APP_PORT=8080 \
    APP_USER=1001 
    
ARG APP_CONFIG=/etc/httpd/conf/httpd.conf \
    APP_SCRIPT=/var/run/httpd \
    APP_LOG=/var/log/httpd

RUN dnf install -y httpd && dnf clean all && \
    sed -i -e 's/^Listen 80/Listen ${APP_PORT}/' ${APP_CONFIG} && \
    chgrp -R 0 ${APP_SCRIPT} ${APP_LOG} && \
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
