package com.echo.quick.model.dao.interfaces;

import com.echo.quick.pojo.Words_Log;
import com.echo.quick.pojo.Words_Status;

import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 16:29
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 16:29
 * 修改备注：
 */

public interface IWordsLogDao {

    /**
     * 方法名称：查询生词
     * 方法描述: 返回生词本所有的生词
     * 参数1： 参数说明
     * @return list
     **/
    List<Words_Log> select();

    /**
     * 方法名称：selectCount
     * 方法描述: 获得数据库条数
     * 参数1： 参数说明
     * @return [int]
     **/
    int selectCount();

    /**
     * 方法名称：selectNum
     * 方法描述: 根据单词返回num
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    int selectLeftNum(Words_Status w);

    /**
     * 方法名称：selectNum
     * 方法描述: 根据单词返回num
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    int selectRightNum(Words_Status w);

    /**
     * 方法名称：更新或添加生词本
     * 方法描述: 传入一个生词,返回save结果
     * 参数1： 生词对象
     * @return [boolean]
     **/
    boolean update(Words_Log words);

    /**
     * 方法名称：通过id更新左滑num
     * 方法描述: 传入单词对应的单词更新对应的num
     * 参数1： String word， int num
     * @return Boolean
     **/
    boolean updateLeftNum(String word, int num);

    /**
     * 方法名称：通过id更新右滑num
     * 方法描述: 传入单词对应的单词更新对应的num
     * 参数1： String word， int num
     * @return Boolean
     **/
    boolean updateRightNum(String word, int num);

    /**
     * 方法名称：删除生词
     * 方法描述:
     * 参数1： 单词id
     *
     **/
    void delete(String id);

}
