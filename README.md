# Awesome-WanAndroid

<center>

![image](https://diycode.b0.upaiyun.com/user/avatar/2468.jpg)

</center>

### 致力于打造一款极致体验的WanAndroid客户端，知识和美是可以并存的哦QAQn(*≧▽≦*)n

## 项目

Awesome WanAndroid项目基于Material Design + MVP + Rxjava2 + Retrofit + Dagger2 + GreenDao + Glide

这是一款会让您觉得很nice的技术学习APP，所用技术基本涵盖了当前Android开发中常用的主流技术框架，阅读内容主要面向想在Android开发领域成为专家的朋友们。

#### 一些诚恳的提议：

- Android Studio 上提示缺失Dagger生成的类，可以直接编译项目，会由Dagger2自动生成

- 本项目还有一些不够完善的地方，如发现有Bug，欢迎[issue](https://github.com/JsonChao/Awesome-WanAndroid/issues)、Email([chao.qu521@gmail.com]())、PR

- 项目中的API均来自于[WanAndroid网站](http://www.wanandroid.com)，纯属共享学习之用，不得用于商业用途！！大家有任何疑问或者建议的可以联系[chao.qu521@gmail.com]()

## 预览

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF1.gif?raw=true"><img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF2.gif?raw=true">
</figure>

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF3.gif?raw=true"><img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF4.gif?raw=true">
</figure>

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF5.gif?raw=true"><img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/251756e695e2bc0a966089c4f05075234e744c76/screenshots/GIF6.gif?raw=true">
</figure>

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/7856e3f54ddb601e4533ea52f60f697942a97f14/screenshots/JPG1.jpg?raw=true"><img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/7856e3f54ddb601e4533ea52f60f697942a97f14/screenshots/JPG2.jpg?raw=true">
</figure>

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/7856e3f54ddb601e4533ea52f60f697942a97f14/screenshots/JPG3.jpg?raw=true"><img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/7856e3f54ddb601e4533ea52f60f697942a97f14/screenshots/JPG4.jpg?raw=true">
</figure>

<figure class="half">
<img src="https://github.com/JsonChao/Awesome-WanAndroid/blob/7856e3f54ddb601e4533ea52f60f697942a97f14/screenshots/JPG5.jpg?raw=true">
</figure>





## 下载APK（Android5.0或以上）

<center>

![image](https://qr.api.cli.im/qr?data=https%253A%252F%252Fgithub.com%252FJsonChao%252FAwesome-WanAndroid%252Fraw%252Fb4f748036fe4300ac40c44886d8ffc7d6f0cbb67%252Fapp%252Fapp-release.apk&level=H&transparent=false&bgcolor=%23ffffff&forecolor=%23000000&blockpixel=12&marginblock=1&logourl=&size=280&kid=cliim&key=1eb52addc4010915a974d2f82cdb71f2)

</center>

## 技术点

- 项目代码尽力遵循了阿里巴巴Java开发规范和阿里巴巴Android开发规范，并有良好的注释。

- 使用Rxjava2结合Retrofit2进行网络请求。

- 使用Rxjava2的操作符对事件流进行进行转换、延时、过滤等操作，其中使用Compose操作符结合RxUtils工具类简化线程切换调用的代码数量。

- 使用Dagger2无耦合地将Model注入Presenter、Presenter注入View，更高效地实现了MVP模式。

- 使用BasePresenter对事件流订阅的生命周期做了集成管理。

- 使用Material Design中的Behavior集合ToolBar实现了响应式的“上失下现”特效。

- 多处使用了滑动到顶部的悬浮按钮，提升阅读的便利性。

- 使用SmartRefreshLayout丰富的刷新动画将项目的美提升了一个档次。

- 使用了腾讯Bugly，以便对项目进行Bug修复和CI。

- 项目中多处使用了炫目的动画及特效。

- 更多请Clone本项目进行查看。。。


## 版本

### V1.0.0

1.提交Awesome WanAndroid第一版 

## 感谢

### API： 

鸿洋大大提供的[WanAndroid API](http://www.wanandroid.com/blog/show/2)

### APP：

[GeekNews](https://github.com/codeestX/GeekNews)提供了Dagger2配合MVP的架构思路

[Toutiao](https://github.com/iMeiji/Toutiao)提供的MD特效实现思路

[diycode](https://github.com/GcsSloop/diycode)提供的智能滑动悬浮按钮实现思路

[Eyepetizer-in-Kotlin](https://github.com/LRH1993/Eyepetizer-in-Kotlin)提供的搜索界面切换特效实现思路

此外，还参考了不少国内外牛人的项目，感谢开源！

### 界面设计：

[花瓣](https://huaban.com/) 提供了很美的UI界面设计，感谢花瓣

### icon：

[iconfont](http://www.iconfont.cn/) 阿里巴巴对外开放的很棒的icon资源

### 优秀的第三方开源库：

#### Rx

[Rxjava](https://github.com/ReactiveX/RxJava)

[RxAndroid](https://github.com/ReactiveX/RxAndroid)

#### Network

[Retrofit](https://github.com/square/retrofit)

[OkHttp](https://github.com/square/okhttp)

[Gson](https://github.com/google/gson)

#### Image Loader

[Glide](https://github.com/bumptech/glide)

#### DI

[Dagger2](https://github.com/google/dagger)

[ButterKnife](https://github.com/JakeWharton/butterknife)

#### DB

[GreenDao](https://github.com/greenrobot/greenDAO)

#### UI

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)

[Lottie-android](https://github.com/airbnb/lottie-android)

### 还有上面没列举的一些优秀的第三方开源库，感谢开源，愿我们一同成长
    
### License

Copyright 2018 JsonChao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.