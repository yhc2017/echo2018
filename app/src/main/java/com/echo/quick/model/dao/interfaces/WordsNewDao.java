package com.echo.quick.model.dao.interfaces;

import com.echo.quick.pojo.Words_New;

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

public interface WordsNewDao {

    /**
     * 方法名称：查询生词
     * 方法描述: 返回生词本所有的生词
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    public List<Words_New> select();

    /**
     * 方法名称：更新或添加生词本
     * 方法描述: 传入一个生词,返回save结果
     * 参数1： 生词对象
     * @return [boolean]
     **/
    public boolean update(Words_New wordsNew);

    /**
     * 方法名称：删除生词
     * 方法描述:
     * 参数1： 单词id
     * @return [返回类型说明]
     **/
    public void delete(String id);

    public boolean isExist(String wordId);

}
