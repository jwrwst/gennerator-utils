# java开发规范
#一、命名风格
> 1.【强制】类名双驼峰命名；方法名、变量名单驼峰命名
> 2.【强制】常量名全大写，多个单词以下划线分隔
> 3.【强制】包名全小写、不包含下划线；并且不要以多个单词组合，包名统一使用单数形式
> 4.【强制】代码杜绝不规范的缩写，避免望文不知义
> 5.【强制】类、方法、变量 不要用拼音或拼音缩写形式命名
> 6.【强制】变量名尽量避免与类名相同
> 7.【强制】接口的命名规则与类名相似,但接口要使用大写字母I开头：例如：IStudentService；
> 8.【强制】接口实现类与接口名相同，但以Impl结尾
> 9.【强制】枚举类名建议带上 Enum 后缀，枚举成员名称需要全大写，单词间用下划线隔开。
> 10.【推荐】为了达到代码自解释的目标，任何自定义编程元素在命名时，使用尽量完整的单词组合来表达其意。
> 11.【推荐】如果模块、接口、类、方法使用了设计模式，在命名时需体现出具体模式。
    说明：将设计模式体现在名字中，有利于阅读者快速理解架构设计理念。
    例如： public class OrderFactory; // 工厂模式 
          public class LoginProxy; // 代理模式
          public class ResourceObserver; // 观察者模式
> 12.【强制】对外暴露的接口名不使用驼峰命名，采用restful风格，能见名知义; 
         跳转到某页面采用：/to/+对应的操作, 例如：/customer/to/save; /customer是controller上的@RequestMapping定义的
> 13.【强制】领域模型命名规约
   1） 数据对象： xxxDO ， xxx 即为数据表名。
   2） 数据传输对象： xxxDTO ， xxx 为业务领域相关的名称。
   3） 展示对象： xxxVO ， xxx 一般为网页名称。
   4） POJO 是 DO / DTO / BO / VO 的统称，禁止命名成 xxxPOJO 

#二、代码规范
> 1.【强制】DAO不允许调用Service; Service中不允许调用Service; 如果公用请抽离出来。
> 2.【强制】所有的覆写方法，必须加@Override 注解。
    说明：可以准确判断是否覆盖成功。另外，如果在抽象类中对方法签名进行修改，其实现类会马上编译报错。
> 3.【强制】所有的相同类型的包装类对象之间值的比较，全部使用 equals 方法比较。
    正例："test".equals(object);
    反例： object.equals("test");
> 4.【强制】单个方法不要超出80行，单个类文件不要超过1500行
    说明：包括方法签名、结束右大括号、方法内代码、注释、空行、回车及任何不可见字符的总
    行数不超过 80 行。
    正例：代码逻辑分清红花和绿叶，个性和共性，绿叶逻辑单独出来成为额外方法，使主干代码
    更加清晰；共性逻辑抽取成为共性方法，便于复用和维护。
> 5.【强制】dao/service层方法前缀，save/delete物理删除/remove逻辑删除/update/find/findList
> 6.【强制】controller层方法前缀，add/remove/edit/get


> 7.【推荐】避免set属性值的时候，set方法中直接调用其它方法，采用变量接受并set
    说明：在set中调用其他方法，不容易发现和排查问题，不便于调试。
> 8.【强制】POJO 类中布尔类型的变量，都不要加 is 前缀，否则部分框架解析会引起序列化错误【备注：POJO 是 DO / DTO / BO / VO 的统称，禁止命名成 xxxPOJO 】
> 9.【强制】给实体entity属性设置值时，不要依赖数据库默认值，要明确写出来; 且实体中不允许写自定义属性。
    说明：便于阅读理解和排查问题；避免对数据库默认值的依赖；
> 10.【强制】controller-> @RequestMapping("/{version}/{xx/entity}/");controller-> method->的mapping需要指定请求方式：PostMapping/GetMapping/DeleteMapping/PutMapping
> 11.【强制】方法设计要保证原子性，调用方法层级嵌套不要太深，尽量不超过3层；
    说明：保证代码的整洁；便于维护
> 12.【强制】表达异常的分支时，少用 if-else 方式 ，这种方式可以改写成：
    if (condition) {
        ...
        return obj;
    }
    // 接着写 else 的业务逻辑代码; 如果非得使用 if()...else if()...else... 方式表达逻辑，超过3层的 if-else 的逻辑判断代码可以使用卫语句、策略模式、状态模式等来实现
    其中卫语句示例如下：
    public void today() {
        if (isBusy()) {
            System.out.println(“change time.”);
            return;
        }
        if (isFree()) {
            System.out.println(“go to travel.”); 
            return;
        }
        System.out.println(“stay at home to learn Alibaba Java Coding Guidelines.”);
        return;
    }
> 13.【推荐】接口类中的方法和属性不要加任何修饰符号 （public 也不要加 ） ，保持代码的简洁性，并加上有效的 Javadoc 注释。尽量不要在接口里定义变量。
    正例：接口方法签名 void commit();
         接口基础常量 String COMPANY = " alibaba " ;
    反例：接口方法定义 public abstract void f();
    说明： JDK 8 中接口允许有默认实现，那么这个 default 方法，是对所有实现类都有价值的默认实现。
> 14.【强制】序列化类新增属性时，请不要修改 serialVersionUID 字段，避免反序列失败; 如果完全不兼容升级，避免反序列化混乱，那么请修改 serialVersionUID 值。
    说明：注意 serialVersionUID 不一致会抛出序列化运行时异常。
> 15.【强制】不允许catch后不做任何信息输出。
    说明：避免捕获异常后无法发现，给排查问题带来不便。
> 16.【强制】if后面必须添加大括号【｛】，保证代码的可读性和拓展。
> 17.【强制】一个java源文件只存储一个java类，底层类库除外。
> 18.【强制】避免用一个对象访问一个类的静态变量或方法，应该用类名去调用。
> 19.【推荐】循环体内，字符串的连接方式，使用StringBuilder/StringBuffer的append 方法进行扩展。
> 20.【推荐】测试方法要放在独立的包下，且类名以Test结尾；方法名以test开头
> 21.【推荐】controller层不要写业务逻辑，只做接参和调用；
    说明：
        1.保证controller代码整洁；
        2.这样做方便服务层方法的复用；
        3.业务和验证信息都在服务层做了，验证方法同样可以复用。
> 22.【推荐】方法入参的合法性校验进行剥离独立出来 或 统一使用validation验证框架
    说明：
        1.可以保证方法的原子性(一个方法只做一件事情)，方便复用，同时也便于维护。
        2.将验证逻辑与业务逻辑分离。
> 23.【强制】java接口版本迭代时，老版本的接口方法或即将作废的方法需要标记即将作废——@Deprecated；并清晰地说明采用的新接口或者新服务是什么
    说明：
        1.避免调用时，无法区分即将作废的方法，造成后期不必要的修改工作量。
        2.方便以后他人维护查找方便，节省维护成本。
> 24.【推荐】controler类命名使用 xxControllerV1.1方式命名，即实体名称/业务名称+Controller+【版本号】。例如：CustomerControllerV1.0/TrackControllerV1.0
    说明：
        1.采用实体名/业务名命名有利于对该controller的理解，每个人看到都能快速上手。
        2.版本号是对新老接口的区分和接口版本的管理
> 25.【强制】业务层返回类型统一，返回指定规范类型。 例如：Result<T>
> 26.【强制】待办事宜TODO，未完成的功能必须写TODO(内容包含：标记人，标记时间)，功能完成以后，删除TODO。
> 27.【强制】一个实体可对应一个枚举类，该类中可能有多字段需要枚举，杜绝魔法值。例如：见最下方。
> 28.【推荐】java代码中避免直接使用数字直接比较，要把字段枚举出来，用枚举值进行比较; 枚举类至少包含code值和desc描述。
> 29.【推荐】关于公用字段需添加到数据字典进行统一使用，避免不同数字代表相同的含义，【比如：0，1分别代表男女，有些人命名为1，2代表男女】。
         添加到数据字典第一是为了保持系统内统一使用，二是为了避免前端过多判断，方便使用, 例如：性别、民族、年份、季节等等....
> 30.【强制】代码的格式展示要有相应的缩进，以便提高代码的可读性; 可采用idea默认格式化进行处理。
> 31.【强制】单行注释的双斜线与注释内容之间有且仅有一个空格。
> 32.【强制】单行注释需要放在该行的上方，不要放在该行的后面。
> 33.【强制】类、类属性、类方法的注释必须使用javadoc规范，使用文档注释格式，不得使用//XX方式，文档注释时必须包含三个部分：描述、作者、时间。
> 34.【强制】非空判断，统一使用封装好的项目中StringUtils工具类，工具类中继承org.apache.commons.lang3.StringUtils。
> 35.【强制】公共方法必须放到工具类中；
> 36.【强制】禁止使用废弃方法；不用的属性或者变量及时删除。

#三、并发处理
> 1.【强制】创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
> 2.【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
        说明：使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，解决资源不足的问题。如果不使用线程池，
        有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。

> 3.【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
  说明： Executors 返回的线程池对象的弊端如下：
    1） FixedThreadPool 和 SingleThreadPool :
        允许的请求队列长度为 Integer.MAX_VALUE ，可能会堆积大量的请求，从而导致 OOM 。
    2） CachedThreadPool 和 ScheduledThreadPool :
        允许的创建线程数量为 Integer.MAX_VALUE ，可能会创建大量的线程，从而导致 OOM 。
        
> 4.【参考】volatile 解决多线程内存不可见问题。对于一写多读，是可以解决变量同步问题，
  但是如果多写，同样无法解决线程安全问题。如果是 count ++操作，使用如下类实现：
  AtomicInteger count =  new AtomicInteger(); count . addAndGet( 1 );  如果是 JDK 8，推
  荐使用 LongAdder 对象，比 AtomicLong 性能更好 （ 减少乐观锁的重试次数 ） 。
  
> 5.【参考】HashMap 在容量不够进行 resize 时由于高并发可能出现死链，导致 CPU 飙升，在
  开发过程中可以使用其它数据结构或加锁来规避此风险。
  
#四、异常日志
> 1.【强制】Java 类库中定义的可以通过预检查方式规避的 RuntimeException 异常不应该通过catch 的方式来处理，比如： NullPointerException ， IndexOutOfBoundsException 等等。
    说明：无法通过预检查的异常除外，比如，在解析字符串形式的数字时，不得不通过 catch NumberFormatException 来实现。
    正例：if (obj != null) {...}
    反例：try { obj.method(); } catch (NullPointerException e) {…}
> 2.【推荐】方法的返回值可以为 null ，不强制返回空集合，或者空对象等，必须添加注释充分说明什么情况下会返回 null 值。
    说明：本手册明确防止 NPE 是调用者的责任。即使被调用方法返回空集合或者空对象，对调用者来说，也并非高枕无忧，必须考虑到远程调用失败、序列化失败、运行时异常等场景返回null 的情况。
> 3.【强制】不要在 finally 块中使用 return 。
    说明： finally 块中的 return 返回后方法结束执行，不会再执行 try 块中的 return 语句。
    
#五、数据库规范
> 1.【强制】字段名长度建议不要超过15个字符。
> 2.【强制】表名、表字段避免使用关键字。如 desc 、 range 、 match 、 delayed 等。
> 3.【强制】表字段名风格统一，全部小写，多个单词组成时则使用英文下划线分隔。
> 4.【强制】数据库名、表名、字段名必须使用小写字母或数字，禁止出现数字开头，禁止两个下划线中间只出现数字；数据库名、表名不使用复数名词，对应于DO类名也是单数形式。
> 5.【推荐】表结构设计每个表名需要有一个前缀，可通过前缀来区分表隶属于哪个模块，如：业务名称/模块名+表的作用。
> 6.【推荐】实体与表名必须一致【前缀可作为java包来划分模块，实体不含表名前缀】；表字段与实体属性，必须保持一致。字段is开头表达是否字段例外。
> 7.【强制】创建表时，必须对表和表字段进行注释说明，可枚举字段对应的值必须将枚举值枚举在注释中, 超过6个可考虑存放在数据字典。
> 8.【强制】varchar是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长度大于此值，定义字段类型为text ，独立出来一张表，用主键来对应，避免影响其它字段索引效率。
> 9.【强制】小数类型为 decimal ，禁止使用 float 和 double 。
> 10.【强制】可枚举的字段要使用tinyint来做
> 11.【强制】任何字段如果未非负数，必须是unsigned
> 12.【强制】表达是与否概念的字段，数据类型是 unsigned tinyint(0 表示否, 1 表示是)
        说明：任何字段如果为非负数，必须是 unsigned 。
> 13.【强制】业务上具有唯一特性的字段，即使是多个字段的组合，也必须建成唯一索引。
> 14.【推荐】如果修改字段含义或对字段表示的状态追加时，需要及时更新字段注释。
> 15.【推荐】不要更新无改动的字段，一是易出错 ； 二是效率低 ； 三是增加 binlog 存储
> 16.【推荐】单表行数超过 500 万行或者单表容量超过 2 GB ，才推荐进行分库分表。
> 17.【强制】表必备六字段： id, creator_id, create_at, modifier_id, modified_at, deleted(0否, 1是)。
         其中 id 必为主键，类型为 int unsigned 、单表时自增、步长为 1
         create_at、modified_at 均为 datetime 类型，前者现在时表示主动创建，后者过去分词表示被动更新。
> 18.【推荐】创建人ID、创建人、创建时间、修改人ID、修改人、修改时间等基本信息放到表的最后，把有主外键关联的放在主键的下方依次往下排列

#六、mybatis xml细节
> 1.【强制】sql语句统一写在xml中
> 2.【强制】mapper.xml中注释统一使用<!-- -->
> 3.【强制】mapper.xml中新增方法统一做主键ID返回值处理

#七、jsp/html等前端页面细节
> 1.必填参数前端js必须进行参数校验，避免不必要的发送后台请求；
> 2.jsp/html页面存放路径必须与包名相对应; 页面名称与跳转到该页面的方法名保持一致，方便调试找页面
> 3.所有HTML标签必须闭合
> 4.页面代码的屏蔽注释必须使用<%-- --%>而不是<!-- -->。需要在页面源码中显示注释信息的情况除外。

#八、编码与软件相关
> 1.【强制】开发过程统一使用utf-8编码
> 2.【强制】jdk/tomcat等软件，组内需保持一致，资源统一共享磁盘获取
> 3.【推荐】插件 p3c 检测代码规范性
> 4.【推荐】lombok插件。可以不用手动生成getter 和 setter
> 5.【推荐】开发工具统一使用idea对应的版本，且采用默认快捷键方式，方便交流沟通调试


#拓展-分支管理
> 分支管理的概念性和流程，建议采用gitflow的工作流程进行分支管理

#拓展-项目插件化
> 对项目模块化插件化的设计概念

# 枚举类：
> 【推荐】一个实体可对应一个interface类，该枚举类中可能有多字段需要枚举——接口组织枚举。具体使用如下：
public interface UserEnum {
    enum Level{
        L1(0, "普通会员"),
        L2(1, "vip会员");
        Catalog(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
        private Integer code;
        private String message;
        public Integer getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
    enum Status{
        NO(0, "正常"),
        YES(1, "禁用");
        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
        private Integer code;
        private String message;
        public Integer getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
}
