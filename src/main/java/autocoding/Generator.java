package autocoding;

import autocoding.common.Column;
import autocoding.common.Table;
import autocoding.utils.CamelCaseUtils;
import autocoding.utils.FileHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class Generator {
    private static Logger logger = LoggerFactory.getLogger(Generator.class);
    private Properties properties = null;

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator();
//		generator.gen("preferential","优惠","preferential","wang");
        generator.gen("t_area", "区域表", "area", "wang");
        logger.debug("模版文件生成完毕……");
    }


    public Generator() throws Exception {
        properties = new Properties();
        String fileDir = this.getClass().getClassLoader().getResource("generator.xml").getFile();
        properties.loadFromXML(new FileInputStream(fileDir));
    }

    public Table parseTable(String tableName) throws Exception {
        String driverName = properties.getProperty("jdbc.driver");
        String userName = properties.getProperty("jdbc.username");
        String userPwd = properties.getProperty("jdbc.password");
        String dbURL = properties.getProperty("jdbc.url");

        String catalog = properties.getProperty("jdbc.catalog");
        String schema = properties.getProperty("jdbc.schema");
        schema = schema == null ? "%" : schema;
        String column = "%";

        logger.debug("driver>>" + driverName);
        logger.debug("url>>" + dbURL);
        logger.debug("name>>" + userName);
        logger.debug("password>>" + userPwd);
        logger.debug("catalog>>" + catalog);
        logger.debug("schema>>" + schema);

        Class.forName(driverName);
        Connection conn = java.sql.DriverManager.getConnection(dbURL, userName, userPwd);
        DatabaseMetaData dmd = conn.getMetaData();

        ResultSet rs = dmd.getColumns(catalog, schema, tableName, column);
        List<Column> columns = new ArrayList<>();
        while (rs.next()) {
            Column newColumn = new Column();
            newColumn.setLabel(rs.getString("REMARKS"));
            String name = rs.getString("COLUMN_NAME");
            newColumn.setName(CamelCaseUtils.toCamelCase(name));
            newColumn.setDbName(name);

            String dbType = rs.getString("TYPE_NAME");
            String type = properties.getProperty(dbType);
            newColumn.setDbType(dbType);
            newColumn.setType(type == null ? "String" : type);

            newColumn.setLength(rs.getInt("COLUMN_SIZE"));
            newColumn.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
            newColumn.setNullable(rs.getBoolean("NULLABLE"));
            columns.add(newColumn);
        }

        List<Column> pkColumns = new ArrayList<>();
        ResultSet pkrs = dmd.getPrimaryKeys(catalog, schema, tableName);
        while (pkrs.next()) {
            Column c = new Column();
            String name = pkrs.getString("COLUMN_NAME");
            c.setName(CamelCaseUtils.toCamelCase(name));
            c.setDbName(name);
            pkColumns.add(c);
        }

        conn.close();

        Table t = new Table();

        String prefiex = properties.getProperty("tableRemovePrefixes");
        String name = tableName;
        if (prefiex != null && !"".equals(prefiex)) {
            name = tableName.split(prefiex)[1];
        }
        t.setName(CamelCaseUtils.toCamelCase(name));
        t.setDbName(tableName);
        t.setColumns(columns);
        t.setPkColumns(pkColumns);
        return t;
    }

    /**
     * <p>Discription:[生成映射文件和实体类]</p>
     * @param tableName 要声称映射文件和实体类的表名称
     * @throws Exception
     */
    public void gen(String tableName, String info, String model_package_name, String uname) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);

        String outRoot = properties.getProperty("outRoot");
        String basepackage = properties.getProperty("basepackage");
        //获取当前日期
        SimpleDateFormat sm_date = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sm_year = new SimpleDateFormat("yyyy年");

        Map<String, Object> root = new HashMap<>();
        Random random = new Random();
        String suid = random.nextInt(1000000) + "" + System.currentTimeMillis() + "L";
        Table table = this.parseTable(tableName);
        logger.info("className:{}, classNameLower:{}", table.getNameUpper(), table.getName());
        root.put("table", table);
        root.put("suid", suid);
        root.put("uname", uname);
        root.put("info", info);
        root.put("className", table.getNameUpper());
        root.put("classNameLower", table.getName());
        root.put("package", basepackage);
        root.put("model_package_name", model_package_name);
        root.put("date", sm_date.format(new Date()));
        root.put("year", sm_year.format(new Date()));

        String templateDir = this.getClass().getClassLoader().getResource("templates").getPath();

        File tdf = new File(templateDir);
        List<File> files = FileHelper.findAllFile(tdf);

        for (File fileTemplate : files) {
            String parentDir = "";
            if (fileTemplate.getParentFile().compareTo(tdf) != 0) {
                parentDir = fileTemplate.getParent().split("templates")[1];
            }
            cfg.setClassForTemplateLoading(this.getClass(), "/templates" + parentDir);

            Template template = cfg.getTemplate(fileTemplate.getName());
            template.setEncoding("UTF-8");

            String parentFileDir = FileHelper.genFileDir(parentDir, root);
            parentFileDir = parentFileDir.replace(".", "/");
            String file = FileHelper.genFileDir(fileTemplate.getName(), root).replace(".ftl", ".java");

            File newFile = FileHelper.makeFile(outRoot + parentFileDir + "/" + file);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8"));
            template.process(root, out);
            logger.debug("已生成文件：" + outRoot + parentFileDir + "/" + file);
        }
    }

}
