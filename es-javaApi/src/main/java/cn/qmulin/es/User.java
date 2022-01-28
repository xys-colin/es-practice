package cn.qmulin.es;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xys
 * @date: 2022/1/23 16:05
 */
@Data
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private String sex;
    private int age;
}
