package com.echo.quick.model.dao.interfaces;

import com.echo.quick.utils.Words;

import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 11:14
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 11:14
 * 修改备注：
 */

public interface OnlineWord {

    List<Words> postToWord(HashMap<String, String> map);

}
