# DeMon_MVPRR
[![](https://jitpack.io/v/DeMonLiu623/DeMon_MVPRR.svg)](https://jitpack.io/#DeMonLiu623/DeMon_MVPRR)  

优雅的封装MVP模式下的Retrofit2.0+RxJava2.0。    

代码详解：[MVP模式下的Retrofit2.0+RxJava2.0封装过程](<https://blog.csdn.net/DeMonliuhui/article/details/99735442>)


#### 权限

```
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

#### 引用

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
	        implementation 'com.github.DeMonLiu623:DeMon_MVPRR:v2.0.0'
	}
```

#### App

```java
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //如果你Application已经继承，可以如下设置
        //BaseApp.setContext(getApplicationContext());
    }
}

```

#### 准备测试接口
测试api，淘宝商品搜索建议：https://suggest.taobao.com/  
  访问<http://www.bejson.com/knownjson/webInterface/>可见

#### Retrofit单例
Retrofit单例，设置base_url,自定义一些拦截器如Token等。


```java
public class Api {

    //测试api，淘宝商品搜索建议
    //访问http://www.bejson.com/knownjson/webInterface/可见
    private static String base_url = "https://suggest.taobao.com/";

    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Api.class) {
                if (retrofit == null) {
                    new Api();
                }
            }
        }
        return retrofit;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        retrofit = baseApi.getRetrofit(base_url, new TokenInterceptor());
    }

   
}

```

##### 自定义的Token拦截器

```java
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder().header("assess-token", "123456").build();
    }

}


```

#### Retrofit Service接口
根据Api服务接口，添加Retrofit Service接口。

```java
public interface ApiService {
    @POST("sug?")
    Observable<TaobaoBean> getTaobao(@Body Map<String, Object> map);


    @FormUrlEncoded
    @POST("sug?")
    Observable<TaobaoBean> getTaobao(@Field("code") String code, @Field("q") String q);
}

```


#### 重写BaseObserver，处理请求结果
1. 重写BaseObserver，处理请求结果,可以在此对请求结果进行预处理，自定义ProgressDialog。
2. 自定义一个接口，回调结果给Presenter。

```java
public class IObserver<T> extends BaseObserver<T> {
    private OnRequestListener<T> listener;

    public IObserver(Context context, OnRequestListener<T> listener) {
        super(context);
        this.listener = listener;
    }

    public IObserver(Context context, OnRequestListener<T> listener, boolean isShow) {
        super(context, isShow);
        this.listener = listener;
    }


    @Override
    protected void onNextResult(T t) {
        listener.onSucceed(t);
    }

    @Override
    protected void onErrorResult(Throwable e) {
        listener.onError(e.getMessage());
    }

    //加载进度框，重写可自定义
    @Override
    public ProgressDialog initDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("数据加载中");
        return dialog;
    }
}
```

#### 自定义的结果回调
```java
public interface OnRequestListener<T> {
    void onSucceed(T t);

    void onError(String s);
}
```

#### 重写Model
1. 根据Retrofit实例和Retrofit Service实例化Service，解决多个不同的Api的情况。
2. 根据Retrofit Service添加订阅，进行网络请求，及监听的结果回调。
3. 根据实际情况可以控制请求是否显示dialog。

```java
/**
 * 网络请求model
 */
public class Model extends BaseModel {
    private static final String TAG = "Model";
    private static Model instance = new Model();


    private BaseService baseService = Api.getRetrofit().create(BaseService.class);

    private ApiService apiService = Api.getRetrofit().create(ApiService.class);

    public static Model getInstance() {
        return instance;
    }


    public void get(String url, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener);
        addSubcription(baseService.get(url), observer);
    }

    public void getTaobao(Map<String, Object> maps, OnRequestListener<TaobaoBean> listener) {
        Observer<TaobaoBean> observer = new IObserver<>(mContext, listener);
        addSubcription(apiService.getTaobao(maps), observer);
    }

    public void getTaobao(String q, OnRequestListener<TaobaoBean> listener) {
        Observer<TaobaoBean> observer = new IObserver<>(mContext, listener);
        addSubcription(apiService.getTaobao("utf-8", q), observer);
    }

    public void post(String url, Map<String, Object> maps, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener);
        addSubcription(baseService.post(url, maps), observer);
    }


    /**
     * 不显示dialog
     *
     * @param url
     * @param maps
     * @param listener
     */
    public void postNoDialog(String url, Map<String, Object> maps, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener, false);
        addSubcription(baseService.post(url, maps), observer);
    }


    /**
     * 文件上传
     *
     * @param url
     * @param file
     * @param name
     * @param listener
     */
    public void uploadFile(String url, File file, String name, OnRequestListener<String> listener) {
        Observer<String> observer = new IObserver<>(mContext, listener);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", name, requestFile);
        addSubcription(baseService.uploadsFile(url, filePart), observer);

    }
}
```

#### MVP的Java使用
##### Contract
根据官方建议增加Contract管理View接口和Presenter。

```java
public interface JavaJsonContract {
    interface View extends BaseView {
        void result(String s);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void Taobao(String q);
    }
}
```

##### Presenter

```
public class JavaJsonPresenter extends JavaJsonContract.Presenter {
    @Override
    public void Taobao(String q) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "utf-8");
        map.put("q", q);
        Model.getInstance().post("sug?", map, new OnRequestListener<String>() {
            @Override
            public void onSucceed(String s) {
                getView().result(s);
            }

            @Override
            public void onError(String s) {
                Log.i(TAG, "onError: " + s);
            }

        });

    }
}
```

##### View

```java
public class JavaJsonActivity extends BaseMvpActivity<JavaJsonPresenter> implements JavaJsonContract.View {

    private EditText et;
    private TextView tv;

    @Override
    protected int bindLayout() {
        return R.layout.activity_json;
    }

    @Override
    protected void initCreate() {
        et = findViewById(R.id.etContent);
        tv = findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(mContext, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.Taobao(s);
            }
        });

    }

    @Override
    public void result(String s) {
        tv.setText(s);
    }
}
```

#### MVP的Kotlin使用

##### Contract

```
class KotlinBeanContract {

    interface View : BaseView {
        fun result(bean: TaobaoBean)
    }


    abstract class Presenter : BasePresenter<View>() {
        abstract fun Taobao(s: String)
    }

}
```

##### Presenter

```
class KotlinBeanPresenter : KotlinBeanContract.Presenter() {
    override fun Taobao(s: String) {
        Model.getInstance().getTaobao(s,object :OnRequestListener<TaobaoBean>{
            override fun onSucceed(t: TaobaoBean) {
                mView.result(t)
            }

            override fun onError(s: String?) {

            }
        })
    }
}
```

##### View

```
class KotlinBeanActivity : BaseMvpActivity<KotlinBeanPresenter>(), KotlinBeanContract.View {
    override fun bindLayout(): Int {
        return R.layout.activity_json
    }

    override fun initCreate() {
        btn.setOnClickListener {
            val s = etContent.text.toString()
            if (s.isNullOrEmpty()) {
                Toast.makeText(mContext, "搜索内容不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mPresenter.Taobao(s)
        }
    }

    override fun result(bean: TaobaoBean) {
        text.text = ""
        for (i in 0 until bean.result.size) {
            val list = bean.result[i]
            for (j in 0 until list.size) {
                text.append(list[j] + "\n")
            }
        }
    }
}
```

#### 更多
请见[Demo](<https://github.com/DeMonLiu623/DeMon_MVPRR/tree/master/app>)使用代码，针对各种情景下都有详细使用代码。


#### BUG or 问题
请在issues留言，定期回复。


##### MIT License

```
Copyright (c) 2019 DeMon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```