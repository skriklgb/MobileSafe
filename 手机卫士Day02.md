1.自定义控件编写流程
	创建一个默认就能获取焦点的TextView
	1.创建一个类继承至TextView,FocusTextView
	2.重写其构造方法
		//使用在通过java代码创建控件
		public FocusTextView(Context context) {
			super(context);
		}
		
		//由系统调用(带属性+上下文环境构造方法)
		public FocusTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		//由系统调用(带属性+上下文环境构造方法+布局文件中定义样式文件构造方法)
		public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}
	3.将原有TextView上的isFocus方法默认修改为,能够获取焦点
		//重写获取焦点的方法,由系统调用,调用的时候默认就能获取焦点
		@Override
		public boolean isFocused() {
			return true;
		}
	4.使用过程
		获取当前类的全路径名称,作为xml中的标签存在,其余属性的使用方式和TextView一致
2,GridView使用,和ListView使用方式类似
	列数(3列)
	数据填充(模块名称,模块图片)
	数据适配器一致的

3,自定义组合控件
	1.将已经编写好的布局文件,抽取到一个类中去做管理,下次还需要使用此布局结构的时候,
	  直接使用组合控件对应的对象.

	2.将组合控件的布局,抽取到单独的一个xml中

	3.通过一个单独的类,去加载此段布局文件.

	4.checkBox是否选中,决定SettingItemView是否开启,isCheck(){return checkbox.isCheck()}方法

	5.提供一个SettingItemView,切换选中状态的方法setCheck(boolean isCheck)

4,设置界面,事件传递过程
	SettingActivity对应布局文件的跟布局获取点击事件
	此事件传递给SettingItemView

	1.点击在SettingItemView非CheckBox区域,事件就由SettingItemView去做响应
	2.点击在SettingItemView中CheckBox区域,事件就由SettingItemView传递给CheckBox,由CheckBox去做响应
	   CheckBox响应当前的点击事件,则SettingItemView就不能再去响应此事件,不能调用onClick方法,去改变状态

	   解决此问题的方案为:不让checkBox响应点击事件
5,自定义属性,(设置中心,有多个条目,在复用SettingItemView的时候,每一个条目对应的标示,描述内容都不一致)
	1.查看源码,定义属性时候做法
		sdk所在目录\platforms\android-16\data\res\values\attrs.xml

		<resource>
			<declare-styleable name="TextView">
				<attr name="text" format="string"/>
			</declare-styleable>
		<resource>
	2.给SettingItemView定义属性,工程res\values\attrs.xml
		<resource>
			<declare-styleable name="SettingItemView全类名">
				<attr name="destitle" format="string"/>
				<attr name="desoff" format="string"/>
				<attr name="deson" format="string"/>
			</declare-styleable>
		<resource>
	3.自定义属性的使用
		定义名空间namespace
		mobilesafe替换掉原有android
		com.itheima.mobilesafe74必须这样编写,替换掉了android,代表当前应用自定义属性
		xmlns:mobilesafe="http://schemas.android.com/apk/res/com.itheima.mobilesafe74"

		<com.itheima.mobilesafe74.view.SettingItemView
		    xmlns:mobilesafe="http://schemas.android.com/apk/res/com.itheima.mobilesafe74"
		    android:id="@+id/siv_update"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    mobilesafe:destitle="自动更新设置"
		    mobilesafe:desoff="自动更新已关闭"
		    mobilesafe:deson="自动更新已开启">
	4.获取属性值
	    mobilesafe:destitle="自动更新设置"
	    mobilesafe:desoff="自动更新已关闭"
	    mobilesafe:deson="自动更新已开启"
	    以上的是哪个属性都需要给自定义组合控件(SettingItemView)内部的两个TextView去使用,获取属性值
	
	//通过属性索引获取属性名称&属性值
	for(int i=0;i<attrs.getAttributeCount();i++){
		Log.i(tag, "name = "+attrs.getAttributeName(i));
		Log.i(tag, "value = "+attrs.getAttributeValue(i));
		Log.i(tag, "分割线 ================================= ");
	}
	//通过属性获取属性名称&名空间
	mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
	mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
	mDeson = attrs.getAttributeValue(NAMESPACE, "deson");

	5.将获取的属性值,设置给title,des
		des根据是否点中决定
		true	deson
		false	desoff
	难
6,自定义对话框
	设置一条xml---->view对象

	设置密码
		设置密码
		确认密码
		确认
		取消
	确认密码
		确认密码
		确认
		取消
6,密码加密(了解)
	md5加密:将字符串转换成	32位的字符串(16进制字符(0~f))	不可逆	

	java工程


	123	202cb962ac59075b964b07152d234b70


	
	



## 手机卫士Day02 ##
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

- 自定义组合控件SettingItemView

	1. 布局文件中完成item样式
	2. 创建自定义SettingItemView,继承RelativeLayout, 在构造方法中完成布局加载
	3. 设置item点击事件,Checkbox切换,文字变化
	4. 在SP中记录item状态, 在SplashActivity中判断item状态,决定是否升级

- SettingItemView自定义属性

	1. 删除代码中对文本的动态设置, 改为在布局文件中设置
	2. 在布局文件中增加新的命名空间
	
		    xmlns:itheima="http://schemas.android.com/apk/res/com.itheima.mobilesafeteach"

	3. 参照系统源码attrs.xml, 找到定义TextView属性的位置,拷贝相关代码
	4. 创建attrs.xml, 定义相关属性

			 <!-- 自定义属性 -->
		    <declare-styleable name="SettingItemView">
		        <attr name="title" format="string" />
		        <attr name="desc_on" format="string" />
		        <attr name="desc_off" format="string" />
		    </declare-styleable>

	5. 读取自定义属性的值, 更新TextView的内容

		    int count = attrs.getAttributeCount();
		    for (int i = 0; i < count; i++) {
			Log.d("Test", "name=" + attrs.getAttributeName(i) + "; value="
					+ attrs.getAttributeValue(i));
		    }

			String title = attrs.getAttributeValue(NAMESPACE, "title");

- 自定义组合控件小结

		 1.声明一个View对象 继承相对布局，或者线性布局 或者其他的ViewGroup
 		 2.在自定义的View对象里面重写它的构造方法。在构造方法里面就把布局都初始化完毕
 		 3.根据业务需求 添加一些api方法，扩展自定义的组合控件
 		 4.布局文件里面 可以自定义一些属性

- 防盗模块自定义对话框, 低版本样式适配

	1.检测密码是否已经设置, 弹出设置密码框或输入密码框

	2.自定义对话框布局文件

		  <TextView
	        android:layout_width="match_parent"
	        android:layout_height="40dp"
	        android:background="#66ff6600"
	        android:gravity="center"
	        android:orientation="vertical"
	        android:text="设置密码"
	        android:textSize="20sp" >
   		 </TextView>

    	<EditText
	        android:id="@+id/et_password"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:inputType="textPassword" />

	3. 按钮响应事件处理

		对比密码是否相同, 相同的话保存在sp中,进入手机防盗页,否则给错误提示

	4. 2.3版本对话框有黑色背景, 需要进行适配

			首先, 将布局文件的整体背景设置为白色
			 android:background="#ffffff"

			其次, 为了去掉残余边框, 需要将布局的边距设置为0
			alertDialog = builder.create();
			alertDialog.setView(view, 0, 0, 0, 0);
			alertDialog.show();
		
- md5介绍

		为了安全保存密码, 使用到了md5算法, md5是一种不可逆的加密算法

		public static void main(String[] args) {
		try {
			String password = "123456";
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(password.getBytes());

			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				int i = b & 0xff;// 将字节转为整数
				String hexString = Integer.toHexString(i);// 将整数转为16进制

				if (hexString.length() == 1) {
					hexString = "0" + hexString;// 如果长度等于1, 加0补位
				}

				sb.append(hexString);
			}

			System.out.println(sb.toString());//打印得到的md5

			} catch (NoSuchAlgorithmException e) {
				// 如果算法不存在的话,就会进入该方法中
				e.printStackTrace();
			}
		}

> 登录网站: http://www.cmd5.com/ 验证md5准确性

> 演示md5如何暴力破解

> 为避免暴力破解, 可以对算法加盐

		什么是加盐? 

		比如以前我们只是把password进行md5加密, 现在可以给password加点盐,这个盐可以是一个固定的字符串,比如用户名username, 然后我们计算一下md5(username+password), 保存在服务器的数据库中, 即使这个md5泄露, 被人破解后也不是原始的密码, 一定程度上增加了安全性
