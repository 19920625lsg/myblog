/***********************************************************
 * @Description : 
 * @author      : 梁山广(Laing Shan Guang)
 * @date        : 2017/12/18 下午11:28
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
package com.huawei.l00379880.myblogbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {
    String title;
    private Long typeId;
    private boolean recommended;
}
