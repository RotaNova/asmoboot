package com.rotanava.framework.config.dubbo;

import cn.hutool.core.lang.tree.Tree;
import org.apache.dubbo.common.lang.Prioritized;
import org.apache.dubbo.metadata.definition.builder.TypeBuilder;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @description: 解决 tree 这个类dubbo无法创建TypeDefinition
 * @author: jintengzhou
 * @date: 2021-06-18 14:58
 */
public class TreeTypeBuilder implements TypeBuilder {
    @Override
    public boolean accept(Type type, Class<?> clazz) {
        return clazz == Tree.class;
    }

    @Override
    public TypeDefinition build(Type type, Class<?> clazz, Map<Class<?>, TypeDefinition> typeCache) {
        TypeDefinition td = new TypeDefinition(clazz.getCanonicalName());
        typeCache.put(clazz, td);
        return td;
    }


    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public int compareTo(Prioritized that) {
        return TypeBuilder.super.compareTo(that);
    }

}