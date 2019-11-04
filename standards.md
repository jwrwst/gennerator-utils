# java开发规范
## 命名规范
> 【强制】类名双驼峰命名；方法名、变量名单驼峰命名 
> 【强制】常量名全大写，多个单词以下划线分隔
> 【强制】接口的命名规则与类名相似,但接口要使用大写字母I开头：例如：IStudentService；
> 【强制】接口实现类与接口名相同，但以Impl结尾
> 【强制】对外暴露的接口名不使用驼峰命名，采用restful风格，能见名知义
> 【强制】包名全小写、不包含下划线；并且不要以多个单词组合，包名统一使用单数形式
> 【强制】代码杜绝不规范的缩写，避免望文不知义
> 【推荐】为了达到代码自解释的目标，任何自定义编程元素在命名时，使用尽量完整的单词组合来表达其意。

## 代码规范
> 【强制】业务层返回类型统一，返回指定规范类型。 例如：Result<T>
> 【推荐】方法入参的合法性校验进行剥离，单独出来
> 【强制】常用方法不要自己实现，使用公共类完成
> 【强制】方法设计要保证原子性，调用方法层级嵌套不要太深，尽量不超过3层；
> 【推荐】表达异常的分支时，少用 if-else 方式 ，这种方式可以改写成：
    if (condition) {
        ...
        return obj;
    }
    // 接着写 else 的业务逻辑代码; 如果非得使用 if()...else if()...else... 方式表达逻辑，超过3层的 if-else 的逻辑判断代码可以使用卫语句、策略模式、状态模式等来实现
> 【强制】避免在一个语句中给多个变量赋相同的值
> 【强制】避免用一个对象访问一个类的静态变量或方法，应该用类名去调用
> 【强制】if后面必须添加大括号【｛】，保证代码的可读性和拓展
> 【强制】给实体entity属性设置值时，不要依赖数据库默认值，要明确写出来
> 【强制】单个方法不要超出80行，单个类文件不要超过1500行
> 【强制】类序列化
> 【强制】POJO 类中布尔类型的变量，都不要加 is 前缀，否则部分框架解析会引起序列化错误
> 【强制】dao层方法前缀，save/remove逻辑删/delete真删/update/get
> 【强制】java代码中避免直接使用数字,直接比较，要把字段枚举出来，用枚举值进行比较; 枚举类至少包含code值和desc描述
> 【强制】枚举类名建议带上 Enum 后缀，枚举成员名称需要全大写，单词间用下划线隔开。
> 【强制】一个实体可对应一个枚举类，该枚举类中可能有多字段需要枚举。例如：见最下方。
> 【强制】一个java源文件只存储一个java类，底层类库除外
> 【强制】所有的覆写方法，必须加@Override 注解
> 【强制】待办事宜TODO，未完成的功能必须写TODO(内容包含：标记人，标记时间)，功能完成以后，删除TODO；
> 【强制】变量名尽量避免用与类名相同
> 【强制】不允许catch后不做任何信息输出
> 【强制】类、方法、变量 不要用拼音或拼音缩写形式命名
> 【推荐】如果模块、接口、类、方法使用了设计模式，在命名时需体现出具体模式。例如： public class OrderFactory; public class LoginProxy; public class ResourceObserver;
> 【推荐】接口类中的方法和属性不要加任何修饰符号 （public 也不要加 ） ，保持代码的简洁性，并加上有效的 Javadoc 注释。尽量不要在接口里定义变量
> 【强制】所有的相同类型的包装类对象之间值的比较，全部使用 equals 方法比较。
> 【推荐】循环体内，字符串的连接方式，使用 StringBuilder 的 append 方法进行扩展。
> 【推荐】避免set属性值的时候，set方法中直接调用其它方法，采用变量接受并set
> 【推荐】关于公用字段需添加到数据字典进行统一使用，避免不同数字代表相同的含义，【比如：0，1分别代表男女，有些人命名为1，2代表男女】
         添加到数据字典第一是为了保持系统内统一使用，二是为了避免前端过多判断，方便使用, 例如：性别、民族、年份、季节等等....
> 【强制】代码中要应用常量类，避免直接写1、2、3、这样无意义的状态数字、一个字段一个常量接口类型【例如：是否删除状态、审核状态不要写到一个interface中，常量类中写两个interface】

#controller相关
> 【强制】controller接口，必填参数，必须做参数校验
> 【强制】controller层不要写业务逻辑，只做接参和调用；
> 【强制】controller-> @RequestMapping("/{version}/{xx/entity}/");  controller-> method->的mapping需要指定请求方式：PostMapping/GetMapping/DeleteMapping/PutMapping
> 【强制】java接口版本迭代时，老版本的接口方法或即将作废的方法需要标记即将作废——@Deprecated；并清晰地说明采用的新接口或者新服务是什么
> 【推荐】controler类命名使用 xxControllerV1.1方式命名，即实体名称/业务名称+Controller+【版本号】。例如：CustomerControllerV1.0/TrackControllerV1.0
> 【强制】controller方法命名，跳转到某页面采用to+对应的操作【例如：toQueryCtiyPage/toAddCityPage/toModifyCityPage/toRemoveCityPage】

#编码与软件相关
> 【强制】开发过程统一使用utf-8编码
> 【强制】jdk/tomcat等软件，组内需保持一致，资源统一共享磁盘获取
> 【推荐】插件 p3c0 检测代码规范性
> 【推荐】开发工具统一使用idea对应的版本，且采用默认快捷键方式，方便交流沟通调试

#注释相关 ----  TODO统一设置模板
> 【强制】类属性注释必须加上
> 【强制】注释的双斜线与注释内容之间有且仅有一个空格。
> 【强制】单行注释需要放在该行的上方，不要放在该行的后面
> 【强制】代码的格式展示要有相应的缩进，以便提高代码的可读性;
> 【强制】类、类属性、类方法的注释必须使用javadoc规范，使用多行注释格式，不得使用//XX方式，多行注释时必须包含三个部分：描述、作者、时间

## 数据库规范
> 【强制】数据库字段名长度建议不要超过15个字符
> 【强制】表字段名风格统一，全部小写，多个单词组成时则使用英文下划线分隔
> 【推荐】表名组成，业务名称/模块名+表的作用
> 【强制】表名、表字段避免使用关键字。如 desc 、 range 、 match 、 delayed 等
> 【强制】数据库名、表名、字段名必须使用小写字母或数字 ， 禁止出现数字开头，禁止两个下划线中间只出现数字；数据库名、表名不使用复数名词，对应于 DO 类名也是单数形式
> 【推荐】表结构设计每个表名需要有一个前缀，可通过前缀来区分表隶属于哪个模块
> 【推荐】实体与表名【不含表名前缀,前缀可作为java包来划分模块】必须一致；表字段与实体属性，必须保持一致
> 【强制】创建表时，必须对表和表字段进行注释说明，可枚举字段对应的值必须将枚举值枚举在注释中,超过6个可考虑存放在数据字典
> 【强制】 varchar 是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长度大于此值，定义字段类型为 text ，独立出来一张表，用主键来对应，避免影响其它字段索引效率。
> 【强制】小数类型为 decimal ，禁止使用 float 和 double 。
> 【强制】可枚举的字段要使用tinyint来做
> 【强制】任何字段如果未非负数，必须是unsigned
> 【强制】表达是与否概念的字段，数据类型是 unsigned tinyint(0 表示否, 1 表示是)
> 【强制】表必备五字段： id, creator_id, create_time, modifier_id, modified_time, deleted(0否, 1是)。
         其中 id 必为主键，类型为 int unsigned 、单表时自增、步长为 1
         create_time、modified_time 均为 datetime 类型，前者现在时表示主动创建，后者过去分词表示被动更新。
> 【强制】业务上具有唯一特性的字段，即使是多个字段的组合，也必须建成唯一索引。
> 【推荐】如果修改字段含义或对字段表示的状态追加时，需要及时更新字段注释。
> 【推荐】不要更新无改动的字段，一是易出错 ； 二是效率低 ； 三是增加 binlog 存储
> 【推荐】单表行数超过 500 万行或者单表容量超过 2 GB ，才推荐进行分库分表。
> 【推荐】创建人ID、创建人、创建时间、修改人ID、修改人、修改时间等基本信息放到表的最后，把有主外键关联的放在主键的下方依次往下排列

## mybatis xml细节
> 【强制】sql语句统一写在xml中
> 【强制】mapper.xml中注释统一使用<!-- -->
> 【强制】mapper.xml中新增方法统一做主键ID返回值处理

## jsp/html等前端页面细节
> 必填参数前端js必须进行参数校验，避免不必要的发送后台请求；
> jsp/html页面存放路径必须与包名相对应; 页面名称与跳转到该页面的方法名保持一致，方便调试找页面
> 所有HTML标签必须闭合
> 页面代码的屏蔽注释必须使用<%-- --%>而不是<!-- -->。需要在页面源码中显示注释信息的情况除外。


领域模型命名规约
1 ） 数据对象： xxxDO ， xxx 即为数据表名。
2 ） 数据传输对象： xxxDTO ， xxx 为业务领域相关的名称。
3 ） 展示对象： xxxVO ， xxx 一般为网页名称。
4 ） POJO 是 DO / DTO / BO / VO 的统称，禁止命名成 xxxPOJO 

> 【强制】一个实体可对应一个枚举类，该枚举类中可能有多字段需要枚举。例如：见下方。
例如：
public interface Constants {
    enum Catalog{
        ALL(0, "test");
        Catalog(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
        private Integer code;
        private String message;
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
    enum Status{
        YES(0, "test2");
        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
        private Integer code;
        private String message;
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
}

#拓展
> 分支管理的概念性和流程，建议采用gitflow的工作流程进行分支管理
> 对项目模块化插件化的设计概念
