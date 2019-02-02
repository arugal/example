# 组件说明

## HandlerMapping
    
   根据 request 找到相应的处理器 Handler 和 Intecepter 拦截器
    
## HandlerApapter
    
   在 HandlerApapter 接口中提供了一个 handler 方法，该方法用 handler 来处理逻辑，处理之后返回一个 ModelAndView
  
## HandlerExceptionResolver
    
   根据异常设置 ModelAndView,然后将结果交给 render 方法进行渲染

## ViewResolver

   将 String 类型的逻辑视图根据 local 解析为 View 视图，View 实际上是用来渲染页面的，也就是说将程序返回的结果填入到具体的模版内，生成具体的视图文件，比如：jsp,ftl,html等
   
### 针对单一视图类型的解析器

   + InternalResourceViewResolver 解析 jsp
   + FreeMarkerViewResolver 解析 FreeMarker

### 针对同时解析多种类型视图的解析器

   + BeanNameViewResolver 需要同时使用视图名和对应的 local 来解析视图，它需要将每一个视图名对应的视图类型配置到相应的 properties 文件中
   + XmlViewResolver 和BeanNameViewResolver有点差不多，XmlViewResolver使用的是xml格式的配置文件。
   + ResourceBundleViewResolver 这个其实就是根据viewName从Spring容器中查找bean，再根据这个bean来找到对应的视图。
   
## LocalResolver
   
   从 request 中解析 local 
     
## ThemeResolver

    

## RequestToViewNameTranslator
  
  

## MultipartResolver

  

## FlashMapManager