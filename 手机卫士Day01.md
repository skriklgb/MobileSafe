#手机卫士Day01#

## 项目介绍 ##

> 演示功能有：

- 启动页面
- 主页
- 手机防盗（注意：演示时模拟器要提前设置有联系人）；
- 通讯卫士：黑名单的管理：电话拦截、短信拦截的演示；
- 软件管理：列出系统的所有软件，启动软件、卸载软件、系统的卸载失败（需要root权限这个后面也会介绍）
- 进程管理：列出系统中正在运行的程序；演示杀死软件
- 窗口小部件：添加桌面；
- 流量统计：模拟器并不支持，在真机上才能演示，只做个UI效果；
- 手机杀毒：检查手机安装的软件，发现那个是病毒，提醒用户就杀掉；
- 系统优化：清除系统的垃圾，刚开始运行，没用多余数据；
- 高级工具：归属地查询；常用号码查询；短信备份；

## svn工具使用 ##

> 为什么要安装svn服务器?

	方便学生从老师的电脑随时checkout代码,也方便学生更有效得管理自己的代码

- 安装VisualSVN Server
- VisualSVN Server的使用

	- 创建仓库
	- 创建用户,针对不同用户设置不同权限
	- checkout代码,commit代码
	- 从已有的仓库中引入项目

## 代码组织结构 ##

- 按照业务模块划分

		办公软件

    	--  开会            com.itheima.meeting
     	--  发放工资	       com.itheima.money
     	--  出差    	       com.itheima.travel

		网盘 

    	-- 上传		com.sina.vdisk.upload
    	-- 下载		com.sina.vdisk.download
     	-- 文件分享  com.sina.vdisk.share

- 按照组件划分

 		界面		     com.itheima.mobilesafe.activity
 		自定义UI	     com.itheima.mobilesafe.ui
 		业务逻辑代码   com.itheima.mobilesafe.engine
		数据库持久化 	  com.itheima.mobilesafe.db
 		              com.itheima.mobilesafe.db.dao
 		广播接收者      com.itheima.mobilesafe.receiver
 		长期在后台运行  com.itheima.mobilesafe.service
 		公用的api工具类 com.itheima.mobilesafe.utils

## 创建新项目 ##

> minSdkVersion、targetSdkVersion、maxSdkVersion、target API level四个数值到底有什么区别?

> minSdkVersion, maxSdkVersion是项目支持的最低sdk版本和最高sdk版本. 在安装apk前,系统会对这个两值进行判断, 决定当前系统是否可以安装,一般maxSdkVersion不会设置

> target API level是项目编译时的sdk版本

> targetSdkVersion会告诉系统,此版本已经经过充分测试,那么程序运行在该版本的系统是,就不会做过多额外的兼容性判断.运行效率会高一些

## Splash页面 ##

- Splash页面作用
	
	1. 展示品牌logo
	2. 程序初始化
	3. 检查版本更新
	4. 校验程序合法性,比如某些app会判断用户是否联网, 没有联网就无法进入页面
	
- Splash布局文件

		 <TextView
	        android:id="@+id/tv_version"
	        android:textColor="#000000"
	        android:textSize="20sp"
	        android:shadowColor="#ff0000"
	        android:shadowDx="1"
	        android:shadowDy="1"
	        android:shadowRadius="1"
	        android:layout_centerInParent="true"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:text="版本  1.0" />
	
- 获取版本信息

		versionName和versionCode的区别和用处

		//获取版本信息
		private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			Log.d(TAG, "versionName=" + versionName + "; versionCode=" + versionCode);
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		    return "";
		}

- 版本校验

> 服务器端json数据

	{
    "version_name": "2.0",
    "version_code": 2,
    "description": "最新版手机卫士,快来下载体验吧!",
    "download_url": "http://10.0.2.2:8080/mobilesafe2.0.apk"
	}

	注意: 保存文本为 "UTF-8 无BOM" 格式

> 读取服务器数据流

	URL url = new URL(getString(R.string.server_url));
	HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
	conn.setRequestMethod("GET");// 请求方法
	conn.setConnectTimeout(5000);// 请求超时

	int code = conn.getResponseCode();

	if (code == 200) {
		InputStream in = conn.getInputStream();
		String result = StreamTools.readFromStream(in);
						
		JSONObject json = new JSONObject(result);
		String versionName = json.optString("version_name",
				null);
		int versionCode = json.getInt("version_code");
		String description = json.optString("description");
		String downloadUrl = json.getString("download_url");

		Log.d(TAG, "description:" + description);
		}


	/**
	 * @param is 输入流
	 * @return String 返回的字符串
	 * @throws IOException 
	 */
	public static String readFromStream(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String result = baos.toString();
		baos.close();
		return result;
	}

- 更新弹窗
- 页面延时2秒后再跳转

		long end = System.currentTimeMillis();
		long elapse = end - start;
		if (elapse < 2000) {
			try {
				Thread.sleep(2000 - elapse);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		handler.sendMessage(msg);

- 添加AlphaAnimation动画效果

		//开启渐变动画
		AlphaAnimation anim = new AlphaAnimation(0.3f, 1f);
		anim.setDuration(2000);
		rlRoot.startAnimation(anim);

- 打一个2.0的apk包, 替换下载链接

- 下载apk

	- 判断SDcard是否挂载代码：

			if(Environment.getExternalStorageState().equal(Environment.MEDIA_MOUNTED))

	- 使用xutils框架进行下载

			// 下载apk
			HttpUtils hu = new HttpUtils();
			hu.download(downloadUrl, localPath, new RequestCallBack<File>() {
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					//下载进度回调
				}
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					//下载成功	
				}
				@Override
				public void onFailure(HttpException error, String msg) {
					//下载失败
				}
			});


- 安装apk

> 查看PackageInstaller源码, 查看AndroidManifest.xml文件中Activity的配置, 从而决定在跳转系统安装界面的Activity时应该传哪些参数.

	// 安装apk
	Intent intent = new Intent();
	intent.setAction(Intent.ACTION_VIEW);
	intent.addCategory(Intent.CATEGORY_DEFAULT);
	intent.setDataAndType(
			Uri.fromFile(t),
			"application/vnd.android.package-archive");
	startActivity(intent);

> 安装失败

	 在Android手机里不允许有两个应用程序有相同的包名；

 	假设A应用的包名：com.itheima.mobilesafeA;
	 A应用已经在系统中存在了。

 	这个时候再去安装一个应用B ，他的包名也叫 con.itheima.mobilesafeA
 	系统就会去检查这两应用的签名是否相同。如果相同，B会把A给覆盖安装掉；
	 如果不相同 B安装失败；

 	要想自动安装成功，必须保证应用程序不同版本的签名完成一样。

- 签名

> 默认签名

	直接在eclipse里运行项目是, 会采用默认签名debug.keystore. 查找方式: Window->Preference->Android->Build, 可以看到默认签名文件的路径, 默认是: C:\Users\tt\.android\debug.keystore

	默认签名的特点: 
	1. 不同电脑,默认签名文件都不一样
	2. 有效期比较短, 默认是1年有效期
	3. 有默认密码: android, 别名:androiddebugkey

> 正式签名

	正式签名特点:
	1. 发布应用市场时, 统一使用一个签名文件
	2. 有效期比较长, 一般25年以上
	3. 正式签名文件比较重要,需要开发者妥善保存签名文件和密码

> 使用正式签名文件,分别打包1.0和2.0, 安装运行1.0版本,测试升级是否成功

> 签名文件丢失后, 肿么办?

	1. 让用户卸载旧版本, 重新在应用市场上下载最新版本, 会导致用户流失
	2. 更换包名, 重新发布, 会出现两个手机卫士, 运行新版手机卫士, 卸载旧版本
	3. 作为一名有经验的开发人员, 最好不要犯这种低级错误!

- 细节处理

	- 进度条样式的版本兼容问题
			
			Application主题设置为android:theme="@style/AppTheme"

		    <style name="AppTheme" parent="AppBaseTheme">
		         <item name="android:windowNoTitle">true</item>//隐藏标题
		    </style>

			分别在两种版本模拟器上运行看效果
			
	- 点击物理返回键的bug 

			// builder.setCancelable(false);//流氓手段,让用户点击返回键没有作用, 不建议采纳
			// 点击物理返回键,取消弹窗时的监听
			builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
			}
			});
	- getApplicationContext和Activity.this的区别

			Context是Activity的父类
			父类有的方法, 子类一定有, 子类有的方法,父类不一定有

			当show一个Dialog时, 必须传Activity对象, 否则会出异常
			android.view.WindowManager$BadTokenException: Unable to add window -- token null is not for an application
			因为Dialog必须依赖Activity为载体才能展示出来, 所以必须将Activity对象传递进去

			以后在使用Context的时候, 尽量传递Activity对象, 这样比较安全

	- 用户取消安装apk, 卡死在Splash页面

			在跳转系统安装页面时,startActivityForResult(intent, 0), 在onActivityResult中跳转主页面


- 主页面GridView搭建

		<!--标题-->
	 	 <TextView
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:text="功能列表"
        android:background="#8866ff00"
        android:textSize="22sp"
        android:gravity="center"
        />

   		 <GridView
        android:id="@+id/gv_home"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:numColumns="3"
        android:verticalSpacing="15dp">
   		 </GridView>
	
- 自定义获取焦点的TextView,走马灯效果

		// 让系统认为,当前控件一直处于获取焦点的状态
		@Override
		public boolean isFocused() {
			return true;
		}

		  <com.itheima.mobilesafeteach.ui.FocusedTextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:singleLine="true"
	        android:ellipsize="marquee"
	        android:textSize="16sp"
	        android:textColor="#000000"
	        android:text="我是您的手机安全卫士, 我会时刻保护您手机的安全! 啊哈哈哈哈" />





1,SplashActivity
	版本名称的展示,从清单配置文件中获取版本名称,PackageManager
	服务端新版本的检测(本地版本号<服务器版本号)
	展示logo

2,手机防盗
	sim卡绑定:每一款手机都会有相应的卡的序列号,一旦替换掉原有电话卡,序列号会发生改变

3,手机卫士分包
	基础课程分包方式,每一个类包名,
	com.itheima.db
	com.itheima.db.dao 组件方式


	业务逻辑划分包名
		icbc

		com.icbc.money
		com.icbc.meeting
		com.icbc.travel

	组件划分(四大组件:activity,服务,广播,内容提供者(内容解析者))

		mobilesafe

		com.itheima.mobilesafe.activity
		com.itheima.mobilesafe.service


		数据库操作
		com.itheima.mobilesafe.db
		com.itheima.mobilesafe.db.dao

		工具类
		com.itheima.mobilesafe.utils

		自定义控件(android原有控件,不能满足需求),自定义组合控件
		com.itheima.mobilesafe.view
		com.itheima.mobilesafe.ui.widget

4,svn账号密码
	创建代码仓库
	分配用户权限

	https://192.168.13.99/svn/heima74/
	账号:all
	密码:123456
	只读

	提交工程步骤,将.svn拷贝到工程同级目录下
	然后点击add选项(自动编译生成的文件不需要(不能)提交),
	后续再点击commit选项,在点击完commit服务才会有相应代码

5,应用去头
	方式一:每个类都需要去添加此代码
		在setContentView(R.layout.activity_splash);
		前设置以下代码
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	方式二:统一去掉所有activity的头

	  @android:style/Theme.Light.NoTitleBar方式去头,使用老版本主题样式
		修改默认样式文件为
	    <style name="AppTheme" parent="AppBaseTheme">
		<!-- 在去头的同时,还保留高版本的样式主题 -->
		<item name="android:windowNoTitle">true</item>
	    </style>

6,packageManager获取当前应用版本名称,版本号
	版本名称:用于展示(阴影效果,颜色)

	版本号:用于和服务端的apk比对,

		如果服务器端的版本号>本地版本号 弹出对话框提示用户更新
		服务器版本号<=本地版本号	直接进入应用程序主界面

7,网络请求发送
	客户端发送请求
	服务端以流的形式返回数据,数据需转换成字符串,(json)
8,json解析
	看见什么解析什么,解析相应字段的时候,一定要去copy不要手写
9,json解析异常情况
	json一旦解析出现错误,通过断点调试是最好的调错方式
	断点调试常用按钮:
		绿色:跳转到下一个断点
		红色:断开本次断点调试
		下一步:执行当前代码,执行到下一步
		跳转到方法内部执行
10,xutils使用过程
	1,导入xutils的jar包
	2,添加xutils需要使用的权限
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	3,获取HttpUtils对象,下载指定链接地址的apk
	4,HttpUtils对象调用download(下载链接地址,下载后放置文件的路径,下载过程中方法的回调
		onStart()
		onloading()
		onSuccesd()
		onFail() )
	5,下载apk后的安装过程,
11,打包生成apk过程
	1,生成签名文件,并且指定所在位置
	2,使用生成的签名文件,给工程打包生成一个apk
12,安装apk注意事项
	升级
	1,注意事项:将原有应用覆盖掉,包名一致

	2,签名一致???
	从Eclipse运行至手机上的应用,使用的是bin目录下的应用,使用debug.keystore签名应用

	手机卫士版本一,右键运行至手机的,所以使用签名是debug.keystore
	手机卫士版本二,单独打包,生成相应签名文件heima74keystore

	生成一个heima74keystore作为签名文件的apk


	签名一致,包名不同:生成两个手机卫士apk,包名是应用的唯一性标志
	签名不同,包名一致:覆盖安装失败

	1.0	keyStore
	2.0

	keyStore+密码妥善保存,svn服务器

	