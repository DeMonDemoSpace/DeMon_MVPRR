# DeMon_MVPRR
一个基于Retrofit2.0+RxJava2.0的MVP模式（Contract）的网络请求优化框架。   

个人主站文章介绍：<https://demonliu623.github.io/2017/09/23/RxJava2-0-Retrofit2-0-MVP%E6%A8%A1%E5%BC%8F-DeMon-MVPRR%E6%A1%86%E6%9E%B6%E4%BD%BF%E7%94%A8%E5%8F%8A%E8%AF%B4%E6%98%8E/>
CSDN文章介绍链接：<http://blog.csdn.net/DeMonliuhui/article/details/78071485>

### 声明
基于Retrofit2.0+RxJava2.0的原创框架，欢迎开源，欢迎复制粘贴，欢迎点击star&fork，谢谢支持！    
第一次写框架，难免有错误，欢迎联系指正：    
E-Mail：DeMonLiu126@126.com    
CSDN：<http://blog.csdn.net/demonliuhui>    
个人主页：<https://demonliu623.github.io/>  

### DeMon_MVPRR的优点

1. 在Retrofit的基础上利用OKHttp配置了缓存及超时策略。
2. 封装了RxJava的订阅及线程调度过程，减少了RxJava使用过程中的代码量。
3. 利用RxJava的订阅过程结合Handler机制，在网络请求耗时的时候封装了一个ProgressDialog,增加了用户体验。
4. 根据谷歌官方的MVP文档，采用Contract管理Presenter和View接口，使用起来更方便。
5. 封装了基础的Model，Presenter，View interface，View（Activity），进一步解耦，方便了MVP的调用，减少了实例化操作，释放内存。

### 效果示例
![](https://im.ezgif.com/tmp/ezgif-1-fbcb2ee857.gif) 

### DeMon_MVPRR使用说明

DeMon_MVPRR框架Demo示例，GitHub地址： 
><https://github.com/DeMonLiu623/MVPRRDemo> 

#### 引入依赖

工程build.gradle中，添加：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

项目build.gradle中，添加：
```
dependencies {
    ....
    compile 'com.github.DeMonLiu623:DeMon_MVPRR:v1.3'
    ...
}
```

#### 添加网络相关权限
添加网络相关权限：

```xml
<uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

#### 框架包含依赖

```
dependencies {
    compile 'io.reactivex.rxjava2:rxjava:2.1.3' //RxJava2.0所需依赖
    compile 'com.squareup.retrofit2:retrofit:2.3.0' //Retrofit2.0所需依赖
    compile 'com.squareup.retrofit2:converter-gson:2.3.0' //结果转为实体类所需依赖
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0' //RxJava2.0+Retrofit2.0适配依赖
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'//Rxandroid2.0线程调度依赖
    compile 'com.squareup.okhttp3:okhttp:3.9.0' //OKHttp3.0依赖
    compile 'com.squareup.okhttp3:logging-interceptor:3.9.0' //OKHttp优化策略依赖
    compile 'com.squareup.retrofit2:converter-scalars:2.3.0' //结果转为基本类型所需依赖
    compile 'com.jakewharton:butterknife:8.5.1' //组件视图绑定依赖
    compile 'com.jakewharton:butterknife-compiler:8.5.1'//组件视图绑定依赖
    compile 'com.android.support:design:26.0.0-alpha1' //design依赖
}
```
添加本框架的依赖后，上面依赖无需重复添加。

### 框架实现说明

框架目录结构如下：  
![](http://img.blog.csdn.net/20170923161018015?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvRGVNb25saXVodWk=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)  

主要讲解各个类的作用，不再贴出代码详解。  
看代码请直接戳[DeMon_MVPRR](https://github.com/DeMonLiu623/DeMon_MVPRR)  
代码如有不明请戳[DeMon_MVPRR框架基础知识笔记](http://blog.csdn.net/column/details/17254.html)  

##### BaseApplication.java
主要包含一个getContext()方法，用于获取全局的上下文。

##### BaseApi.java
主要利用OKHttp配置了缓存及超市策略，封装了获取Retrofit实例化对象过程。  
两个方法：

1. BaseApi.getRetrofit(String baseUrl)获取策略优化的Retrofit实例。
2. BaseApi.getSimpleRetrofit(String baseUrl)获取简单的无策略优化的Retrofit实例。

>PS:为了适应大部分情况，适应了OKHttp增加了Json头部数据格式，本框架默认将请求数据打包成Json格式发送，因此本框架不适用于不允许发送Json数据请求的Api。

##### BaseModel.java
封装了线程管理和订阅的过程。

##### BasePresenter.java
BasePresenter的代码比较重要，贴出来。

```java
public abstract class BasePresenter<T extends BaseView> {
    protected WeakReference<T> mView;
    protected BaseModel mModel = new BaseModel();
    protected Context mContext;

    public BasePresenter(T view) {
        mView = new WeakReference<>(view);
    }

    public T getView() {
        return mView.get();
    }

    //释放view及model的内存空间
    public void onDestroy() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
    }
}
```
1. 采用WeakReference管理View，便于View的释放。
2. 实例化BaseModel，方便调用，减少实例化次数。
3. Context对象会在BaseActivity中使用，方便子类调用。
4. getView()获取实例化后的View interface对象，便于接口调用。
5. onDestroy()及时释放view及model的内存空间.


##### BaseActivity.java&BaseTopBarActivity.java
1. 结合Presenter，封装了Presenter初始化过程
2. 重写onDestroy()方法释放View interface的所占用的内存。
3. 采用抽象类+方法，减少了创建Activity过程的代码量。
4. 实例化了Context对象，减少实例化次数。
5. BaseTopBarActivity在BaseActivity的基础上增加了标题栏的封装，减少我们在使用标题栏过程中大量的xml布局代码。

继承了BaseTopBarActivity的子类，可以调用如下方法修改标题栏属性中：
1.setTopBarText(String text)标题栏标题内容，默认为空
2.setTopBarColor(int colorId) 标题栏标题内容的字体颜色，默认为白色
3.setToolbarBackground(int colorId) 标题栏背景颜色，默认为系统主题颜色

>PS：继承了BaseTopBarActivity的子类Activty应该使用NoActionBar的主题，避免标题栏冲突。

##### BaseView.java
用于实例化View interface对象。

##### ObserverOnNextListener.java
用于监听订阅过程中onNext(T t)方法，接口回调获取请求结果。

##### ProgressCancelListener.Java
1. 用于监听请求失败时，关闭ProgressDialog.
2. 用户手动关闭ProgressDialog时，终端并取消网络请求

##### ProgressDialogHandler.java
继承Handler，实现ProgressDialog功能，初始实话ProgressDialog。

##### ProgressObserver.java
RxJava的订阅过程。  
1. 控制ProgressDialog的显示与隐藏。  
2. 利用ObserverOnNextListener监听onNext(T t)实现请求结果回调。

##### NetWorkUtil.java
监听及判断网络状态，用于根据网络状态配置OkHttp策略。

