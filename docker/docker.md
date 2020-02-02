## docker


### 什么是docker？
> Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows 机器上，也可以实现虚拟化。容器是完全使用沙箱机制，相互之间不会有任何接口。


### docker的应用场景
* web应用的自动化打包和发布
* 自动化测试和持续集成、发布
* 在服务型环境中部署和调整数据库或其他的后台应用
* 从头编译或者扩展现有的平台搭建自己的环境


在centos 7 中安装docker

1. 安装必要的系统工具
```
yum install -y yum-utils device-mapper-persistent-data lvm2
```
2. 添加软件源信息
```
yum-config-manager --add-repo  https://download.docker.com/linux/centos/docker-ce.repo
```
3. 更新并安装Docker-ce
```
 yum install docker-ce docker-ce-cli containerd.io
```
4. 启动docker
```
systemctl start docker
```
5. 开始helloworld
```
docker rum hello-world
```


### docker命令

* docker pull 镜像名：拉取指定镜像到本地
比如下拉一个最新centos
docker pull centos:latest
* docker run 镜像名：使用指定镜像开启一个容器
启动一个centos容器
docker run -it centos /bin/bash
-i 交互式操作
-t 终端 退出终端则 exit
-d 后台启动
-p 端口映射 比如 -p 8080：6379 本地的8080端口对应docker容器的6379端口
-v 文件映射，本地文件对应docker容器启动的文件
--name 启动时对容器命名
* docker ps:查看正在运行的容器
	-a 查看全部容器
* docker stop 容器id：停止某个镜像
* docker restart 容器id：重启
* docker attach 容器id：比如后台启动的容器可以进入，假设退出，那么容器将停止
* docker exec 容器id：进入容器，退出不关闭容器
* docker export 镜像名 > 本地文件名:导入容器快照到本地文件
cat 文件名 | docker import - 镜像名称：tag标签（版本名称）
* docker images ： 查看本地存在的镜像
	* REPOSITORY：表示镜像的仓库源
	* TAG：镜像的标签（类似于版本号）
	* IMAGE ID：镜像ID
	* CREATED：镜像创建时间
	* SIZE：镜像大小
* docker rm -f 镜像： 删除镜像
* docker port 容器id：查看容器的端口映射
* docker logs 容器id:查看容器的日志
-f == tail -f 实现查看文件最末尾的日志
* docker top 容器id：查看容器内部运行的进程
* docker inspect  容器id:查看docker的底层信息
* docker search 镜像名称：搜索镜像
	* NAME: 镜像仓库源的名称
	* DESCRIPTION: 镜像的描述
	* OFFICIAL: 是否 docker 官方发布
	* stars: 类似 Github 里面的 star，表示点赞、喜欢的意思。
	* AUTOMATED: 自动构建。
* docker rmi 镜像id：删除本地镜像
* docker commit -m="" -a="" 容器id 镜像描述:镜像标签 ：修改了镜像，将镜像内容保存到一个本地镜像
* docker tag 镜像名称 描述名：tag：给镜像添加新的标签
*  docker network create 自定义网络名：创建网络
	*  -d 参数指定 Docker 网络类型，有 bridge、overlay
* docker login ：登录远程仓库
* docker logout ：退出远程仓库
* docker push ：上传本地镜像到远程仓库



### Dockerfile
> Dockerfile 是一个用来构建镜像的文本文件，文本内容包含了一条条构建镜像所需的指令和说明

* FROM：定制的镜像都是基于 FROM 的镜像，比如FROM centos就是基于centos的镜像。
* RUN：用于执行后面跟着的命令行命令。有以下俩种格式：
	* RUN ["可执行文件", "参数1", "参数2"]
	* RUN 命令行命令
* CMD：在docker run的时候执行，上面的run是docker build时候执行
 docker build -t 镜像描述：镜像标签 .
* ADD：ADD 指令和 COPY 的使用格式一致（同样需求下，官方推荐使用 COPY）。功能也类似，不同之处如下：
	* ADD 的优点：在执行 <源文件> 为 tar 压缩文件的话，压缩格式为 gzip, bzip2 以及 xz 的情况下，会自动复制并解压到 <目标路径>。
	* ADD 的缺点：在不解压的前提下，无法复制 tar 压缩文件。会令镜像构建缓存失效，从而可能会令镜像构建变得比较缓慢。具体是否使用，可以根据是否需要自动解压来决定。
* COPY：从上下文目录中复制文件或者目录到容器里指定路径。
* ENV：设置环境变量
* ARG：设置环境变量，只对dockerfile有效
* VOLUME：匿名数据卷
* EXPOSE：声明端口
* WORKDIR：指定工作目录
* USER：指定用户和用户组
* HEALTHCHECK：指令监控容器的运行状态
* ONBUILD：延迟构建的命令


### Docker-compose
> Compose 是用于定义和运行多容器 Docker 应用程序的工具。通过 Compose，您可以使用 YML 文件来配置应用程序需要的所有服务。然后，使用一个命令，就可以从 YML 文件配置中创建并启动所有服务。

Compose 使用的三个步骤：
* 使用 Dockerfile 定义应用程序的环境。
* 使用 docker-compose.yml 定义构成应用程序的服务，这样它们可以在隔离环境中一起运行。
* 最后，执行 docker-compose up 命令来启动并运行整个应用程序。


### docker-machine
> Docker Machine 是一种可以让您在虚拟主机上安装 Docker 的工具，并可以使用 docker-machine 命令来管理主机。


### Swarm 集群管理
> Docker Swarm 是 Docker 的集群管理工具。它将 Docker 主机池转变为单个虚拟 Docker 主机。 Docker Swarm 提供了标准的 Docker API，所有任何已经与 Docker 守护程序通信的工具都可以使用 Swarm 轻松地扩展到多个主机。

<p>

> ##### 原理：
> 如下图所示，swarm 集群由管理节点（manager）和工作节点（work node）构成。
> * swarm mananger：负责整个集群的管理工作包括集群配置、服务管理等所有跟集群有关的工作。
> * work node：即图中的 available node，主要负责运行相应的服务来执行任务（task）。

![swarm集群管理](../swarm集群管理.png)































