```text
    * com.github.sunnus3.example.spring..*.*(..))

```
<table>
    <tr>
        <td>*</td>
        <td>表示返回值的类型任意</td>
    </tr>
    <tr>
        <td>com.github.sunnus3.example.spring.UserServiceImpl</td>
        <td>AOP所切的服务的包路径，即需要进行横切的业务类</td>
    </tr>
    <tr>
        <td>..</td>
        <td>表示当前包及子类</td>
    </tr>
    <tr>
         <td>*</td>
         <td>表示类名, * 即所有类</td>
    </tr>
    <tr>
        <td>.*(..)</td>
        <td>表示任何方法名，括号表示参数，两点表示任何参数类型</td>
    </tr>    
</table>

```text
    public* * (..) 匹配所有 public 修饰符的方法
    * set*(..) 匹配所有 set 开头的方法
```