package ${package}.provider.${model_package_name}.mapper;
import ${package}.api.entity.${model_package_name}.${className};
import cn.cemarose.frog.common.service.dao.CrudDao;
import cn.cemarose.frog.system.api.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>Discription:[${info}DAO接口]</p>
 * Created on ${date}
 * @author ${uname}
 */
@Mapper
public interface ${className}Mapper extends CrudDao<${className}>{
	
}
