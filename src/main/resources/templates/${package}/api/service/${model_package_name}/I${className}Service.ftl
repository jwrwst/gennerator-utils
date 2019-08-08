package ${package}.api.service.${model_package_name};
import cn.cemarose.frog.common.api.Paging;
import cn.cemarose.frog.system.api.entity.ResData;
import cn.cemarose.frog.system.api.entity.user.UserInfo;
import cn.cemarose.frog.system.api.exception.base.BusinessException;
import com.github.pagehelper.PageInfo;
import ${package}.${className};

/**
 * <p>Discription:[${info}服务接口]</p>
 * Created on ${date}
 * @author ${uname}
 */
public interface I${className}Service {
 	/**
     * 分页查询
     * @param page
     * @param ${classNameLower}
     * @return
     * @throws BusinessException
     */
    ResData<PageInfo<${className}>> queryByPage(Paging page, ${className} ${classNameLower}) throws BusinessException;
	
	/**
     * 根据条件查询
     * @param ${classNameLower}
     * @return
     * @throws BusinessException
     */
	public ResData<${className}> selectOneByCondition(${className} ${classNameLower});
	
	/**
     * 根据ID查询
     * @param id
     * @return
     * @throws BusinessException
     */
	ResData<${className}> queryById(String id) throws BusinessException;

	/**
     * 新增
     * @param ${classNameLower}
     * @return
     * @throws BusinessException
     */
    ResData<${className}> save(${className} ${classNameLower}) throws BusinessException;

	/**
     * 更新
     * @param ${classNameLower}
     * @return
     * @throws BusinessException
     */
    ResData<${className}> update(${className} ${classNameLower}) throws BusinessException;

}
