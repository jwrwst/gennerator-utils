package ${package}.api.entity.${model_package_name};
import lombok.Data;
import java.io.Serializable;

/**
 * <p>Discription:[${info}实体类]</p>
 * Created on ${date}
 * @author ${uname}
 */
@Data
public class ${className} implements Serializable {
	private static final long serialVersionUID = 1L;

<#list table.columns as column>
	/**
	 * ${column.label}
	 */
	private ${column.type} ${column.name};
</#list>
	

}
