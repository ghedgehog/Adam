/***************adaptor使用说明,begin****************/

1、下载并安装v6.11.2 node，下载地址：https://nodejs.org/en/download/，默认安装即可;

2、命令行运行："node -v",显示"v6.11.2"表示安装成功;

3、命令行运行："npm install -g cnpm --registry=https://registry.npm.taobao.org",配置npm下载镜像地址为："https://registry.npm.taobao.org";

4、在adaptor的根目录命令行运行:"cnpm install"安装项目依赖模块;

5、在adaptor的根目录命令行运行:"node index.js"运行adaptor;

/***************adaptor使用说明,end****************/
//TODO:
###如果与webService的网络异常，需要对实时数据进行存历史，恢复通信后进行历史回补；

###连接uasever的连接超时时间设置，不起uaserver进行配置,看是否会卡死,链接超时时间不起作用；

###增加断言，判断各个函数的入参，增强程序的稳定性；

###用pSpace守护程序或者deamon守护该进程的运行；

###可以将uaServiceMethod、httpClient和interface封装成一个个的类;

###调研一下ClientMonitoredItemGroup这个方法的使用;


###初始启动版本控制

//DOING:
###监听并推送报警

###测点删除以后怎么取消测点的监视项，现在知道通过ClientMonitoredItem的terminate可以取消监视项；

//DONE:
###增加实时数据的质量戳和时间戳;

###增加设备配置

###订阅redis中发布的驱动、通道、设备、测点改动通知，修改完地址空间后,调用所修改的驱动下面的commit方法；
###实际上只有设备和变量有改动时需要进行commit；
###新增测点时需要添加监视项；

###修复bug,datahub同时推送三条消息时，adaptor处理异常;