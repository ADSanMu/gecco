package com.geccocrawler.gecco.annotation;

import java.lang.annotation.*;

/**
 * 请求的是js或者css的内容
 *
 * @author huchengyi
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsCss {

}
