package org.xzframework.activity.service;

import org.xzframework.activity.define.model.ProcessDefinition;

/**
 * 对流程定义的操作
 *
 * @author ldwqh0@outlook.com
 */
public interface ProcessDefineService<ID> {

    /**
     * 查找流程定义
     *
     * @return
     */
    ProcessDefinition findByName(String name);

}
