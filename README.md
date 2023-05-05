# DO188-apps
Create Images with Containerfiles
Objectives

After completing this section, you should be able to create a containerfile using basic commands.
Creating Images with Containerfiles

A Containerfile lists a set of instructions to construct a container image. Once built, the image is used to create any number of containers.

Each instruction causes some change that is captured in a resulting image layer. These layers are stacked together to form the resulting container image.
Note

You might have seen or used Dockerfiles instead of Containerfiles. These files are largely the same and mainly differ in historical context.

This course uses Containerfiles because they are not associated with any specific container runtime engine. Podman supports both Dockerfiles and Containerfiles.
Choosing a Base Image

When building the image, the instructions within the Containerfile are run in order and are applied on top of a base image. A base image is the image from which your Containerfile and its resulting image is built.

The base image you choose determines the Linux distribution, as well as any or all of the following components:

    Package manager

    Init system

    Filesystem layout

    Preinstalled dependencies and runtimes

The base image can also influence image size, vendor support, and processor compatibility, among other factors.

Red Hat provides container images intended as a common starting point for containers known as universal base images (UBI). Specifically, these UBIs come in four variants: standard, init, minimal, and micro. Additionally, Red Hat also provides UBI-based images that include popular runtimes, such as Python and Node.js.

All of these UBIs use Red Hat Enterprise Linux (RHEL) at their core and are available from the Red Hat Container Catalog. The main differences are as follows:

Standard

    This is the primary UBI, which includes DNF, systemd, and utilities such as gzip and tar.
Init

    Eases running multiple applications within a single container by managing them with systemd.
Minimal

    This image is smaller than the init image and still provides nice-to-have features. This image uses the microdnf minimal package manager instead of the full-sized version of DNF.
Micro

    This is the smallest available UBI because it only includes the bare minimum number of packages. For example, this image does not include a package manager.

Containerfile Instructions

Containerfiles use a small domain-specific language (DSL) consisting of basic instructions for crafting container images. The following are the most common instructions.

FROM

    Takes the name of the base image as an argument.
WORKDIR

    Sets the current working directory within the container. Later instructions run within this directory.
COPY

    Copies files from the build host into the file system of the resulting container image. Relative paths respect the host’s current working directory, known as the build context, and the working directory within the container as defined by WORKDIR. It is not possible to copy a remote file by using its URL with this Containerfile instruction.
ADD

    Copies files or folders from a local or remote source and adds them to the container’s file system. If used to copy local files, those must be in the working directory. The ADD instruction also unpacks local .tar archive files to the destination image directory.
RUN

    Runs a command in the container and commits the resulting state of the container to a new layer within the image.
ENTRYPOINT

    Sets the executable to run when the container is started.
CMD

    Runs a command when the container is started. This command is passed to the executable defined by ENTRYPOINT. Base images define a default ENTRYPOINT, which is usually a shell executable, such as Bash.

Note

Neither ENTRYPOINT nor CMD run while building a container image. They are executed within a container as it is initialized.

USER

    Changes the active user within the container. Later instructions run as this user, including the CMD instruction. It is a good practice to define a different user other than root for security reasons.
LABEL

    Adds a key-value pair to the metadata of the image for organization and image selection.
EXPOSE

    Adds a port to the metadata for the image indicating that an application within the container will bind to this port. Note that this does not bind the port on the host and is for documentation purposes.
ENV

    Defines environment variables that are available in the container. You can declare multiple ENV instructions within the Containerfile. You can use the env command inside the container to view each of the environment variables.
ARG

    Defines build-time variables, typically to make a customizable container build. Developers commonly configure the ENV instructions by using the ARG instruction. This is useful for preserving the build-time variables for run time.
VOLUME

    Defines where to store data outside of the container. The value designates the path where Podman mounts the directory inside of the container. You can define more than one path to create multiple volumes.

Each Containerfile instruction runs in an independent container using an intermediate image built from every previous command. This means each instruction is independent from other instructions in the Containerfile. The following is an example Containerfile for building a simple Apache web server container:

# This is a comment line 1
FROM        registry.redhat.io/ubi8/ubi:8.6 2
LABEL       description="This is a custom httpd container image" 3
RUN         yum install -y httpd 4
EXPOSE      80 5
ENV         LogLevel "info" 6
ADD         http://someserver.com/filename.pdf /var/www/html 7
COPY        ./src/   /var/www/html/ 8
USER        apache 9
ENTRYPOINT  ["/usr/sbin/httpd"] 10
CMD         ["-D", "FOREGROUND"] 11

1
	

Lines that begin with a hash, or pound, sign (#) are comments.

2
	

The FROM instruction declares that the new container image extends the registry.redhat.io/ubi8/ubi:8.6ubi8/ubi:8.6 container base image.

3
	

The LABEL instruction is responsible for adding generic metadata to an image.

4
	

RUN executes commands in a new layer on top of the current image. The shell that is used to execute commands is /bin/sh.

5
	

EXPOSE indicates that the container listens on the specified network port at runtime. The EXPOSE instruction defines metadata only; it does not make ports accessible from the host.

6
	

ENV is responsible for defining environment variables that are available in the container.

7
	

The ADD instruction copies files or folders from a local or remote source and adds them to the container’s file system.

8
	

COPY copies files from the working directory and adds them to the container’s file system.

9
	

USER specifies the username or the UID to use when running the container image for the RUN, CMD, and ENTRYPOINT instructions

10
	

ENTRYPOINT specifies the default command to execute when the image runs in a container.

11
	

CMD provides the default arguments for the ENTRYPOINT instruction.
====================

podman-volume comand
====================

Description:
  Imports contents into a podman volume from specified tarball (.tar, .tar.gz, .tgz, .bzip, .tar.xz, .txz).

Usage:
  podman volume import VOLUME [SOURCE]

Examples:
  podman volume import my_vol /home/user/import.tar
  cat ctr.tar | podman import volume my_vol -
-------------------
podman volume export

Allow content of volume to be exported into external tar.

Usage:
  podman volume export [options] VOLUME

Options:
  -o, --output string   Write to a specified file (default: stdout, which must be redirected) (default "/dev/stdout")
Ex: 
podman volume export persisting-volume > /tmp/persisting-volume.tar.gz
#persisting-volume is podman volume
#path_file=/tmp/, file will be archived and compressed in '.gz' type


==========================
Container troubleshooting
=========================
1- Troubleshoot Container Networking
=========================
1.1 Incorrect port mapping
"Ports inside container=Application port opens" == "Podman port mapping congfiguration"

#check port podman mapping
podman port CONTAINER
#Check port inside container
podman exec -it CONTAINER ss -pant
#explain:
    -p: display the process using the socket

    -a: display listening and established connections

    -n: display IP addresses

    -t: display TCP sockets
#in case of there is not ss command on Container then we can use nsenter to push ss commnad into container ad bellow:

podman inspect CONTAINER -f "{{.State.Pid}}"

sudo nsenter -n -t CONTAINER_PID ss -pant

-------------------------------------
1.2 Container netowrk connectivity issues
#check podman network

podman network ls
podman network inspect NETWORK_Container --format='{{.Config.NetworkSettings.Networks}}'
#ensure that :  "dns_enabled": true,

#IN case of dns_enabled": fasle then need to delete the network and recreate network and check 'dns_enabled" info. If it still is "fasle" then check packages 'podman-plugins' and sure that it was installed on server.

===================================
2.Troubleshoot Bind Mount

When you use bind mounts, you must configure file permissions and SELinux access manually. SELinux is an additional security mechanism used by Red Hat Enterprise Linux (RHEL) and other Linux distributions.

#Consider the following bind mount example:
========================================================================
[user@host ~]$ podman run -p 8080:8080 --volume /www:/var/www/html \
  registry.access.redhat.com/ubi8/httpd-24:latest
[user@host ~]$ podman unshare ls -l /www/
total 4
-rw-rw-r--. 1 root root 21 Jul 12 15:21 index.html
[user@host ~]$ podman unshare ls -ld /www/
drwxrwxr-x. 1 root root 20 Jul 12 15:21 /www/
=========================================================================
The output shows the SELinux context label system_u:object_r:default_t:s0:c228,c359, which has the default_t type. A container must have the container_file_t SELinux type to have access to the bind mount. SELinux is out of scope for this course.

To fix the SELinux configuration, add the :z or :Z option to the bind mount:

    Lower case z lets different containers share access to a bind mount.

    Upper case Z provides the container with exclusive access to the bind mount.
=======================================================================
[user@host ~]$ podman run -p 8080:8080 --volume /www:/var/www/html:Z \
  registry.access.redhat.com/ubi8/httpd-24:latest
[user@host ~]$ ls -Zd /www
system_u:object_r:container_file_t:s0:c240,c717 /www
=======================================================================

