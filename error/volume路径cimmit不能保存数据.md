### dockerfile中的volume导致无法commit的时候保存数据

<br/>

>原因在于volume 在启动时每次都会执行，假设不指定的情况下，会在/var/lib/docker/volumes生成文件夹来对于dockerfile中对应的volume的文件，而其他路径下的文件将能够保存

* 解决方案:

  * 备份数据 ： docker run -it -volumes-from 容器id -v 主机目录:/backup 镜像Id:tag tar -cvf 压缩包名.tar 容器需要备份的目录

    * 还原数据主需要将tar -cvf改成 tar -xvf

  * 容器启动时指定虚拟机目录对应容器主机目录 docker -run -v 主机目录：容器目录

    

