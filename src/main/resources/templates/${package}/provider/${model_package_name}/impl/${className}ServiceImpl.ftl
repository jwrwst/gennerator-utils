package ${package}.provider.${model_package_name}.impl;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import cn.cemarose.frog.common.api.Paging;
import cn.cemarose.frog.system.api.entity.ResData;
import cn.cemarose.frog.system.api.enumeration.ExecuteCode;
import cn.cemarose.frog.system.api.exception.ResultUtil;
import cn.cemarose.frog.system.api.exception.base.BusinessException;
import cn.cemarose.frog.system.provider.${package}.mapper.${className}Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import ${package}.dao.I${className}Mapper;
import ${package}.model.${className};
import ${package}.api.I${className}Service;

/**
 * <p>Discription:[${info}服务接口实现类]</p>
 * Created on ${date}
 * @author ${uname}
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements I${className}Service {
	@Resource
	private ${className}Mapper ${classNameLower}Mapper;

    @Override
    public ResData<PageInfo<${className}>> queryByPage(Paging page, ${className} ${classNameLower}) throws BusinessException {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<${className}> list = ${classNameLower}Mapper.findList(${classNameLower});
        return ResultUtil.success(new PageInfo<>(list));
    }

    @Override
    public ${className} selectOneByCondition(${className} ${classNameLower}) {
        return ${classNameLower}Mapper.get(${classNameLower});
    }

    @Override
    public ResData<${className}> queryById(String id) throws BusinessException {
        ${className} ${classNameLower} = userAddressMapper.get(id);
        return ResultUtil.success(${classNameLower});
    }

    @Override
    public ResData<${className}> save(${className} ${classNameLower}) throws BusinessException{
        ${classNameLower}Mapper.insert(${classNameLower});
        return ResultUtil.success();
    }

    @Override
    public ResData<${className}> update(${className} ${classNameLower}) throws BusinessException{
        ${classNameLower}Mapper.update(${classNameLower});
        return ResultUtil.success();
    }

}