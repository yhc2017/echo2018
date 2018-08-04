package com.echo.quick.model.dao.interfaces;

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

public interface IWordsStatusDao {

    /**
     * 方法名称：查询生词
     * 方法描述: 返回生词本所有的生词
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    List<Words_Status> select();


    /**
     * 方法名称：查询生词
     * 方法描述: 通过状态返回生词本属于这个状态的生词，例如生词，熟词
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    List<Words_Status> selectByStatus(String status);

    /**
     * 方法名称：
     * 方法描述: 查有多少没背的单词
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    int selectCount();

    /**
     * 方法名称：查询生词
     * 方法描述: 通过状态返回生词本属于这个状态的生词，例如生词，熟词
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/
    List<Words_Status> selectNotGrasp();


    /**
     * 方法名称：更新或添加生词本
     * 方法描述: 传入一个生词,返回save结果
     * 参数1： 生词对象
     * @return [boolean]
     **/
    boolean update(Words_Status wordsNew);


    /**
     * 方法名称：更新或添加生词本
     * 方法描述: 传入一个生词,返回save结果
     * 参数1： 生词对象
     * @return [boolean]
     **/
    boolean updateByWord(Words_Status wordsNew);

    /**
     * 方法名称：删除生词
     * 方法描述:
     * 参数1： 单词
     * @return [返回类型说明]
     **/
    void delete(String Word);

    boolean isExist(String word);

}
