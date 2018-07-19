### 高仿spring

1.创建resource类

2.创建definition类

3.创建factory类

4.创建reader类

5.创建application类

6.过程
```
1.创建ClassPathXmlApplicationContext类，参数是配置文件
2.ClassPathXmlApplicationContext类里面保存配置文件名称，构造方法构造AutowireCapableBeanFactory
3.步骤二执行super(),在AbstractApplicationContext中定义AbstractBeanFactory，把AutowireCapableBeanFactory的值赋值给AbstractBeanFactory
4.AbstractApplicationContext类refresh
5.通过AbstractApplicationContext的AbstractBeanFactory，loadBeanDefinitions（抽象方法）
6.ClassPathXmlApplicationContext实现父类的抽象方法loadBeanDefinitions
  6.1 XmlBeanDefinitionReader读取内容，参数ResourceLoader,new XmlBeanDefinitionReader的时候执行super()
  6.2 对AbstractBeanDefinitionReader里面的ioc副本，ResourceLoader初始化
  6.3 XmlBeanDefinitionReader执行loadBeanDefinitions，参数是步骤2的配置文件名称变量
  6.4 AbstractBeanDefinitionReader中获取ResourceLoader，执行方法getResource，返回UrlResource对象
  6.5 URL resource=ResourceLoader.getClass().getClassLoader().getResource(location);获取URL对象
  6.6 用URL对象获取UrlResource对象，返回6.4的待用
  6.7 用UrlResource执行getInputStream方法获取InputStream对象
  6.8 执行doLoadBeanDefinitions，参数6.7
      6.8.1 通过InputStream对象获取Document对象
      6.8.2 doc.getDocumentElement()得到Element对象，得到bean的id和class和property对象的值
      6.8.3 beanDefinition对象除了bean属性都构建好了
      6.8.4 AbstractBeanDefinitionReader的副本ioc构建完成，6.1
7.ClassPathXmlApplicationContext类里面循环AbstractBeanDefinitionReader的副本ioc，构建AbstractBeanFactory中的ioc和beanNames
8.AbstractApplicationContext方法执行refresh方法，循环AbstractBeanFactory中的beanNames
9.通过AbstractBeanFactory中的ioc和beanName得到对象，构建BeanDefinition的bean属性
10.AbstractApplicationContext通过name得到BeanDefinition的bean属性对象，执行方法
```